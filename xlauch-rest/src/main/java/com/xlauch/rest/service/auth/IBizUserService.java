package com.xlauch.rest.service.auth;

import com.xlauch.rest.entity.auth.BizUser;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

/**
 * <p>
 *  类描述: 普通用户表 服务类
 * </p>
 * @author huangxy
 * @since 2018-01-06
 * @version 0.1
 */
public interface IBizUserService extends SuperService<BizUser> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectBizUserList();


}
