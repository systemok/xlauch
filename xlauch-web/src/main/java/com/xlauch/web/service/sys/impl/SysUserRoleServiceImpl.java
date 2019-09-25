package com.xlauch.web.service.sys.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.sys.SysUserRole;
import com.xlauch.web.mapper.sys.SysUserRoleMapper;
import com.xlauch.web.service.sys.ISysUserRoleService;
import com.xlauch.core.supers.service.SuperServiceImpl;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  类描述: 用户角色关联表 服务实现类
 * </p>
 * @author huangxy
 * @since 2017-11-24
 * @version 0.1
 */
@Service
public class SysUserRoleServiceImpl extends SuperServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper ;

    @Override
    public DataGrid selectSysUserRoleList() {
        ActionContext<SysUserRole> actionContext = ActionContext.getContext() ;
        Page<SysUserRole> page = actionContext.getPage() ;
        page.setRecords(sysUserRoleMapper.selectSysUserRoleList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    public List selectUserRoles() {
        return sysUserRoleMapper.selectUserRoles(ActionContext.getContext().getDataMap());
    }
}
