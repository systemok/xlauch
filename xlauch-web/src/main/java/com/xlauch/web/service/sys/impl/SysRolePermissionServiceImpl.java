package com.xlauch.web.service.sys.impl;


import com.xlauch.web.entity.sys.SysRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.mapper.sys.SysRolePermissionMapper;
import com.xlauch.web.service.sys.ISysRolePermissionService;
import com.xlauch.core.supers.service.SuperServiceImpl;


/**
 * <p>
 *  类描述: 角色权限表 服务实现类
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
@Service
public class SysRolePermissionServiceImpl extends SuperServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper ;

    @Override
    public DataGrid selectSysRolePermissionList() {
        ActionContext<SysRolePermission> actionContext = ActionContext.getContext() ;
        Page<SysRolePermission> page = actionContext.getPage() ;
        page.setRecords(sysRolePermissionMapper.selectSysRolePermissionList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }
}
