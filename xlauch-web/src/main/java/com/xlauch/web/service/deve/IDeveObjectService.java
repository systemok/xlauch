package com.xlauch.web.service.deve;

import com.xlauch.utils.util.db.MetaData;
import com.xlauch.web.entity.deve.DeveObject;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类描述: 二次开发-对象表 服务类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-12-28
 */
public interface IDeveObjectService extends SuperService<DeveObject> {

    /**
     * 列表分页查询
     *
     * @return
     */
    public DataGrid selectDeveObjectList();

    /**
     * 获取数据源下所有表或者视图
     *
     * @param dataSource
     * @param types
     * @return
     */
    public List<MetaData> queryTables(String dataSource, String[] types);


    /**
     * 新增对象
     * @param deveObject
     */
    public void addDeveObject(DeveObject deveObject);


    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) ;

    /**
     * 删除对象
     * @param deveObject
     */
    public void deleteDeveObject(DeveObject deveObject);


    /**
     * 根据编码查询
     * @param code
     * @return
     */
    public DeveObject selectByCode(String code);
}
