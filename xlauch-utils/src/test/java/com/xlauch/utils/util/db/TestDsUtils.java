package com.xlauch.utils.util.db;


import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.generator.config.rules.QuerySQL;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/26
 */
public class TestDsUtils {

    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(TestDsUtils.class);

    private ResultSet rs ;

    private Connection conn ;

    private PreparedStatement stmt ;



    @Test
    public void getTables() {
        try {
            DatabaseMetaData databaseMetaData = conn.getMetaData() ;
            Console.log(databaseMetaData.getUserName());
            Console.log(databaseMetaData.getURL());
            String[] types = {"VIEW"} ;
            rs = databaseMetaData.getTables(null ,null , null ,  types );
            while (rs.next()) {
                Console.log(rs.getString("TABLE_NAME") + " == " + rs.getString("REMARKS"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTableInfoBySql() {
        QuerySQL querySQL = QuerySQL.MYSQL ;


        try{
            String tableCommentsSql = querySQL.getTableCommentsSql();
            stmt = conn.prepareStatement(tableCommentsSql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                String tableName = results.getString(querySQL.getTableName());
                String tableComment = results.getString(querySQL.getTableComment());
                System.out.println("------------------------------------------------");
                System.out.println(tableName + " = " + tableComment);

                PreparedStatement preparedStatement = conn.prepareStatement(String.format(querySQL.getTableFieldsSql(), tableName));
                ResultSet crs = preparedStatement.executeQuery();
                while (crs.next()){
                    String key = crs.getString(querySQL.getFieldKey());
                    boolean isId = StringUtils.isNotEmpty(key) && key.toUpperCase().equals("PRI");
                    String colName = crs.getString(querySQL.getFieldName());
                    String colTitle = crs.getString(querySQL.getFieldComment());
                    System.out.println(isId + " 名称:" + colName + " 备注:" + colTitle);
                }
                close(crs , preparedStatement , null);

                System.out.println("------------------------------------------------");
            }


        }catch (Exception e) {

        }
    }

    @Test
    public void testNum(){
        String a="varchar";
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        System.out.println( m.replaceAll("").trim());
    }

    @Test
    public void getTableInfoByDatabaseMetaData() {
        try{
            DatabaseMetaData databaseMetaData = conn.getMetaData() ;
            String tableSchema = conn.getCatalog();
            Properties props = new Properties();
            props.setProperty("REMARKS", "true");// 获取注释
            props.setProperty("COLUMN_DEF", "true");// 获取默认值
            conn.setClientInfo(props);
            String[] types = {"VIEW"};//"TABLE",
            ResultSet results = databaseMetaData.getTables(null , tableSchema , null , types );
            while (results.next()) {
                String tableName = results.getString("TABLE_NAME");
                String tableComment = results.getString(4);
                String tableType = results.getString("TABLE_TYPE");
                String pkName = "" ;
                ResultSet crs = databaseMetaData.getPrimaryKeys(null , null , tableName) ;
                if (crs != null) {
                    while (crs.next()) {
                        pkName = crs.getString("COLUMN_NAME");
                        break;
                    }
                }


                System.out.println("------------------------------------------------");
                System.out.println("表名： " +tableName + " 备注： " + tableComment + " pk : " + pkName + " type: " + tableType);



                System.out.println("------------------------------------------------");
            }


        }catch (Exception e) {

        }
    }

    @Test
    public void testGetColumnRemark() throws SQLException {

        String tableSchema = conn.getCatalog() ;
        String sql = "Select COLUMN_NAME 列名, DATA_TYPE 字段类型, COLUMN_COMMENT 字段注释\n" +
                "from INFORMATION_SCHEMA.COLUMNS where table_schema = '"+tableSchema+"'" ;


        System.out.println(conn.getSchema());
        stmt = conn.prepareStatement(sql) ;
        rs = stmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData() ;
        int columnCount = metaData.getColumnCount();
        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            // System.out.println("Remarks: "+ rs.getObject(12));
            JSONObject json = new JSONObject();
            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                json.put(columnName, value);
            }
            System.out.println(JSON.toJSONString(json));
        }

    }



    @Before
    public void init() throws SQLClientInfoException {
        this.conn = getConn() ;
        Properties props = null;
        props = new Properties();
        props.setProperty("REMARKS", "true");// 获取注释
        props.setProperty("COLUMN_DEF", "true");// 获取默认值
        this.conn.setClientInfo(props);
    }

    @After
    public void destory() {
        close(rs ,stmt , conn);
    }

    /**
     * 获取数据库连接
     * @return
     */
    private Connection getConn() {
        String url = "jdbc:mysql://39.106.64.79:3306/xlauch-demo?useUnicode=true&characterEncoding=utf-8&tinyInt1isBit=false" ;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url , "xlauch" , "Xlauch@123");
            return conn ;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null ;
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
