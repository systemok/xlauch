package com.xlauch.web.service.deve;

import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.web.entity.deve.DeveObjectParam;

import java.util.List;

/**
 * <p>
 *  类描述: 二次开发-导出参数管理 服务类
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
public interface IDeveObjectParamService extends SuperService<DeveObjectParam> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveObjectParamList();

    /**
     * 根据对象ID获取字段列表
     * @param objId
     * @return
     */
    public List<DeveObjectParam> selectByObjectId(int objId) ;

    /**
     * 根据方法ID获取字段列表
     * @param methodId
     * @return
     */
    public List<DeveObjectParam> selectByMethodId(int methodId) ;
}
