package com.xlauch.web.service.sys;

import com.xlauch.web.entity.sys.SysRole;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.List;

/**
 * <p>
 *  类描述: 角色表 服务类
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
public interface ISysRoleService extends SuperService<SysRole> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectSysRoleList();

    /**
     * 新增并授权
     * @return
     */
    public void insertAndGrant(SysRole sysRole, List<Integer> permissionids);

    /**
     * 修改角色并授权
     * @return
     */
    public void updateAndGrant(SysRole sysRole, List<Integer> permissionids);

    /**
     * 分配权限
     * @return
     */
    public boolean grantPermission(List<Integer> idList, Long roleId);


}
