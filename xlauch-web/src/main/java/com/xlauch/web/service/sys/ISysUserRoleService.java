package com.xlauch.web.service.sys;

import com.xlauch.web.entity.sys.SysUserRole;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 用户角色关联表 服务类
 * </p>
 * @author huangxy
 * @since 2017-11-24
 * @version 0.1
 */
public interface ISysUserRoleService extends SuperService<SysUserRole> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectSysUserRoleList();

    public List selectUserRoles();


}
