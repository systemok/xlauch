package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveObject;
import com.xlauch.web.entity.deve.DeveObjectMethod;
import com.xlauch.web.entity.deve.DeveObjectPriview;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

/**
 * <p>
 *  类描述:  服务类
 * </p>
 * @author 伊凡
 * @since 2018-01-09
 * @version 0.1
 */
public interface IDeveObjectPriviewService extends SuperService<DeveObjectPriview> {

    /**
     * 列表分页查询
     * @param methodId
     * @param objCode
     * @return
     */
    public DataGrid selectDeveObjectPriviewList(Integer methodId, String objCode);

    /**
     * 获取对象
     * @param code
     * @return
     */
    public DeveObject loadObjectByCode(String code) ;

    /**
     * 获取方法
     * @param methodId
     * @param objCode
     * @return
     */
    public DeveObjectMethod loadMethod(Integer methodId, String objCode);

    /**
     * 新增、删除、修改
     * @param methodId
     * @param objCode
     * @return
     */
    public long addOrUpdateOrDelete(Integer methodId, String objCode);

}
