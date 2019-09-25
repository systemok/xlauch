package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveObjectColumn;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.List;

/**
 * <p>
 *  类描述: 二次开发-模块管理--字段管理 服务类
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
public interface IDeveObjectColumnService extends SuperService<DeveObjectColumn> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveObjectColumnList();

    /**
     * 根据对象ID获取字段列表
     * @param objId
     * @return
     */
    public List<DeveObjectColumn> selectByObjectId(int objId) ;

    /**
     * 根据方法ID获取字段列表
     * @param methodId
     * @return
     */
    public List<DeveObjectColumn> selectByMethodId(int methodId) ;

}
