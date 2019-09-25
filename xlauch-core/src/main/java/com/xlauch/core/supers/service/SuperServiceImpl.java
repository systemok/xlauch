package com.xlauch.core.supers.service;/**
 * <p>
 * 类描述    :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/11/10
 */

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.PagerUtils;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sun.rowset.CachedRowSetImpl;
import com.xlauch.core.config.mybatis.DbContextHolder;
import com.xlauch.core.config.mybatis.DynamicDataSource;
import com.xlauch.core.context.ActionContext;
import com.xlauch.core.exception.BussException;
import com.xlauch.core.exception.ExceptionUtils;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.page.NameSQL;
import com.xlauch.utils.util.ClobUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.tools.classfile.Attribute.Exceptions;

/**
 * 类描述    : <br/>
 * 项目名称  : deve 项目<br/>
 * 类名称    : SuperServiceImpl.java <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/10 15:09  <br/>
 * @version 0.1
 */
public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {

    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(SuperServiceImpl.class);

    @Autowired
    private DynamicDataSource dataSource;

    /**
     * 获取数据源
     * @return
     */
    public DynamicDataSource getDataSource(){
        return this.dataSource ;
    }

    /**
     * 自定义扩展方法
     *
     * @return
     */
    @Override
    public DataGrid pageForDataGrid(Wrapper<T> wrapper) {
        ActionContext actionContext = ActionContext.getContext();

        /**
         * 分页查询
         */
        Page<T> page = new Page<>(actionContext.getCurrentPage(), actionContext.getPageSize());
        page = selectPage(page, wrapper);

        return pageToDataGrid(page);
    }

    /**
     * page对象转换成DataGrid
     *
     * @param page
     * @return
     */
    public DataGrid pageToDataGrid(Page page) {
        /**
         * 封装datagrid
         */
        DataGrid dataGrid = new DataGrid();
        if (page != null) {
            dataGrid.setRows(page.getRecords());
            dataGrid.setTotal(page.getTotal());
        }
        return dataGrid;
    }


    /**
     * 获取查询元信息
     *
     * @param sql
     * @return
     */
    @Override
    public ResultSetMetaData getResultSetMetaData(String sql) {
        Connection connection = null;
        sql = sql.replaceAll("#\\{.+\\}", "");
        sql = sql.replaceAll("$\\{.+\\}", "?");
        try {
            connection = dataSource.getConnection();
            PreparedStatement pStemt = connection.prepareStatement(sql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            return rsmd;
        } catch (SQLException e) {
            logger.error(ExceptionUtils.getStackTraceAsString(e));
            throw new BussException("10000");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(ExceptionUtils.getStackTraceAsString(e));
                }
            }
        }
    }

    @Override
    public DataGrid pageForDataGridBySQL(String dbType, String sql, List<Object> params) {
        ActionContext actionContext = ActionContext.getContext();
        DataGrid dataGrid = new DataGrid() ;

        // 拼接分页SQL
        int offset = actionContext.getOffset() ;
        String listSQL = PagerUtils.limit(sql, dbType, offset, actionContext.getPageSize());
        String countSQL = PagerUtils.count(sql, dbType);

        //获取总记录数
        long total = getCountBySQL(countSQL, params);
        if(total == 0 ) {
            return dataGrid ;
        }

        //获取分页内容
        List<Map<String, Object>> rows = selectBySQL(listSQL, params);

        dataGrid.setTotal(total);
        dataGrid.setRows(rows);
        return dataGrid;
    }

    @Override
    public DataGrid pageForDataGridBySQL(String dyType , NameSQL nameSQL) {
        return pageForDataGridBySQL(dyType , nameSQL.getSql() , nameSQL.getParams()) ;
    }

    @Override
    public long getCountBySQL(String sql, List<Object> params) {
        //设值、调用
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 1; i <= params.size(); i++) {
                    pstmt.setObject(i, params.get(i - 1));
                }
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            logger.error(ExceptionUtils.getStackTraceAsString(e));
            throw new BussException("10000");
        } finally {
            close(rs, pstmt, connection);
        }

        return 0;
    }


    @Override
    public List<Map<String, Object>> selectBySQL(String sql, List<Object> params) {
        Connection connection = null;
        ResultSet rs = null;
        CachedRowSet crs = null;
        PreparedStatement pstmt = null;
        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 1; i <= params.size(); i++) {
                    pstmt.setObject(i, params.get(i - 1));
                }
            }
            rs = pstmt.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(rs);
            return rsToList(crs);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTraceAsString(e));
            throw new BussException("10000");
        } finally {
            close(rs, pstmt, connection);
        }
    }


    /**
     * 把游标对象变成集合列表
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> rsToList(ResultSet rs) throws Exception {
        if (rs != null) {
            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> tmp = new HashMap<String, Object>();
                for (int i = 1; i <= colCount; i++) {
                    String colName = rsmd.getColumnName(i);
                    Object colValue = rs.getObject(colName);
                    if (rsmd.getColumnType(i) == Types.CLOB) {
                        colValue = ClobUtils.ClobToString(rs.getClob(i));
                    }

                    tmp.put(colName, colValue);
                }
                dataList.add(tmp);
            }

            return dataList;
        }

        return new ArrayList<Map<String, Object>>();
    }


    /**
     * 执行SQL
     * @param sql
     * @param params
     * @return
     */
    public int executeSQL(String sql , List<Object> params) {
        Connection connection = null;
        ResultSet rs = null;
        CachedRowSet crs = null;
        PreparedStatement pstmt = null;
        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 1; i <= params.size(); i++) {
                    pstmt.setObject(i, params.get(i - 1));
                }
            }
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTraceAsString(e));
            throw new BussException("10000");
        } finally {
            close(rs, pstmt, connection);
        }
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
