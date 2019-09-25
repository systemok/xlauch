package com.xlauch.web.service.sys;

import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.web.entity.sys.SysRolePermission;

/**
 * <p>
 *  类描述: 角色权限表 服务类
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
public interface ISysRolePermissionService extends SuperService<SysRolePermission> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectSysRolePermissionList();


}
