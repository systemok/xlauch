package com.xlauch.core.supers.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.page.NameSQL;

import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类描述    :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/11/10
 */
public interface SuperService<T> extends IService<T> {

    /**
     * 自定义扩展方法
     * @return
     */
    public DataGrid pageForDataGrid(Wrapper<T> wrapper) ;


    /**
     * 获取查询元信息
     * @param sql
     * @return
     */
    public ResultSetMetaData getResultSetMetaData(String sql) ;


    /**
     * 自定义扩展方法，根据SQL查询记录
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String ,Object>> selectBySQL(String sql , List<Object> params) ;


    /**
     * 自定义扩展方法，获取总记录数
     * @param sql
     * @param params
     * @return
     */
    public long getCountBySQL(String sql , List<Object> params) ;

    /**
     * 自定义扩展方法,分页查询，返回datagrid
     * @param dbType
     * @param sql
     * @param params
     * @return
     */
    public DataGrid pageForDataGridBySQL(String dbType , String sql , List<Object> params) ;

    /**
     * 自定义扩展方法,分页查询，返回datagrid
     * @param dyType
     * @param nameSQL
     * @return
     */
    public DataGrid pageForDataGridBySQL(String dyType , NameSQL nameSQL) ;


}
