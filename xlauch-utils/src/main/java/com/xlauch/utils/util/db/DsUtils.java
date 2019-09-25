package com.xlauch.utils.util.db;


import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.QuerySQL;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 类描述 : 数据源工具类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/26
 */
public class DsUtils {

    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(DsUtils.class);

    /**
     * 获取数据库元信息
     *
     * @param dataSource
     * @param types
     * @return
     */
    public static List<MetaData> queryTables(DataSource dataSource, String[] types) {
        List<MetaData> metaDataList = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            String tableSchema = conn.getCatalog();
            DatabaseMetaData databaseMetaData = conn.getMetaData();

            //"TABLE","VIEW"
            //String[] types = {"VIEW"};
            ResultSet results = databaseMetaData.getTables(null, tableSchema, null, types);
            if (results == null) {
                return metaDataList;
            }

            while (results.next()) {
                //表名、视图名
                String tableName = results.getString("TABLE_NAME");
                //备注
                String tableComment = results.getString(4);
                //类型：表或视图
                String tableType = results.getString("TABLE_TYPE");
                //主键
                String pkName = "";
                if ("TABLE".equalsIgnoreCase(tableType)) {
                    ResultSet crs = databaseMetaData.getPrimaryKeys(null, tableSchema, tableName);
                    if (crs != null) {
                        while (crs.next()) {
                            pkName = crs.getString("COLUMN_NAME");
                            break;
                        }
                    }

                    close(crs, null, null);
                }

                MetaData metaData = new MetaData(tableName, tableType, pkName, tableComment);
                metaDataList.add(metaData);

            }
            close(results, null, conn);
            return metaDataList;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 获取字段列表
     *
     * @param dbType
     * @param tableName
     * @param dataSource
     * @return
     */
    public static List<DbColumn> getColumns(String dbType, String tableName, DataSource dataSource) {
        List<DbColumn> columnList = new ArrayList<>();
        try {
            /**
             * 获取数据库连接、数据库名、数据库类型
             */
            Connection conn = dataSource.getConnection();
            String tableSchema = conn.getCatalog();
            QuerySQL querySQL = getQuerySQL(dbType);

            /**
             * 根据数据库类型获取字段查询语句
             */
            String tableFieldsSql = querySQL.getTableFieldsSql();
            if (QuerySQL.POSTGRE_SQL == querySQL) {
                tableFieldsSql = String.format(tableFieldsSql, tableSchema, tableName);
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            }

            /**
             * 迭代获取数据
             */
            PreparedStatement preparedStatement = conn.prepareStatement(tableFieldsSql);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                String key = results.getString(querySQL.getFieldKey());
                // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                boolean isId = StringUtils.isNotEmpty(key) && key.toUpperCase().equals("PRI");
                //字段名
                String ename = results.getString(querySQL.getFieldName());
                //字段备注
                String cname = results.getString(querySQL.getFieldComment());
                if(StringUtils.isEmpty(cname)){
                    cname = ename ;
                }
                //字段长度
                int len = getLen(results.getString(querySQL.getFieldType()));

                DbColumn dbColumn = new DbColumn(ename , cname , len ,isId);
                columnList.add(dbColumn);
            }

            close(results, preparedStatement, conn);
        } catch (Exception e) {

        }

        return columnList;
    }

    /**
     * 获取字段长度
     * @param fieldType
     * @return
     */
    private static int getLen(String fieldType) {
        if ("date".equalsIgnoreCase(fieldType) || "datetime".equalsIgnoreCase(fieldType)){
            return 20 ;
        }

        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(fieldType);
        String num = m.replaceAll("").trim();
        if(StringUtils.isEmpty(num)) {
            return 0 ;
        }
        return Integer.parseInt(num);
    }

    /**
     * 获取当前的SQL类型
     *
     * @return DB类型
     */
    private static QuerySQL getQuerySQL(String dbType) {
        for (QuerySQL qs : QuerySQL.values()) {
            if (qs.getDbType().equals(dbType)) {
                return qs;
            }
        }
        return QuerySQL.MYSQL;
    }


    /**
     * 关闭资源
     *
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("关闭ResultSet出现错误，请检查！", e);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            logger.error("关闭Statement出现错误，请检查！", e);
        }

        try {
            if (conn != null) {
                logger.debug("关闭数据库连接:" + conn);
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            logger.error("关闭Connection出现错误，请检查！", e);
        }
    }
}
