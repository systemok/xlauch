package com.xlauch.web.service.sys.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xlauch.utils.util.TextUtil;
import com.xlauch.web.entity.sys.SysRolePermission;
import com.xlauch.web.mapper.sys.SysRolePermissionMapper;
import com.xlauch.web.service.sys.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.sys.SysRole;
import com.xlauch.web.mapper.sys.SysRoleMapper;
import com.xlauch.web.service.sys.ISysRoleService;
import com.xlauch.core.supers.service.SuperServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  类描述: 角色表 服务实现类
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
@Service
public class SysRoleServiceImpl extends SuperServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper ;


    @Autowired
    private ISysRolePermissionService iSysRolePermissionService ;

    @Override
    public DataGrid selectSysRoleList() {
        ActionContext<SysRole> actionContext = ActionContext.getContext() ;
        Page<SysRole> page = actionContext.getPage() ;
        page.setRecords(sysRoleMapper.selectSysRoleList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    public void insertAndGrant(SysRole sysRole, List<Integer> permissionids) {
        this.insert(sysRole);
        this.grantPermission(permissionids, sysRole.getRoleId());
    }

    @Override
    public void updateAndGrant(SysRole sysRole, List<Integer> permissionids) {
        this.updateById(sysRole);
        this.grantPermission(permissionids, sysRole.getRoleId());
    }

    @Override
    @Transactional
    public boolean grantPermission(List<Integer> idList, Long roleId){
        {
            EntityWrapper<SysRolePermission> sysRolePermissionEntityWrapper = new EntityWrapper();
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            iSysRolePermissionService.delete(sysRolePermissionEntityWrapper);
        }
        List<SysRolePermission> sysRolePermissionList = new ArrayList();
        for(Integer id :idList){
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(TextUtil.getLong(id));
            sysRolePermissionList.add(sysRolePermission);
        }
        return iSysRolePermissionService.insertBatch(sysRolePermissionList, sysRolePermissionList.size());
    }
}
