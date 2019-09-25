package com.xlauch.web.service.sys.impl;


import cn.hutool.crypto.SecureUtil;
import com.xlauch.utils.util.TextUtil;
import com.xlauch.web.entity.sys.SysUserRole;
import com.xlauch.web.service.sys.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.sys.SysUser;
import com.xlauch.web.mapper.sys.SysUserMapper;
import com.xlauch.web.service.sys.ISysUserService;
import com.xlauch.core.supers.service.SuperServiceImpl;

import java.util.*;


/**
 * <p>
 *  类描述: 系统用户表 服务实现类
 * </p>
 * @author huangxy
 * @since 2017-11-16
 * @version 0.1
 */
@Service
public class SysUserServiceImpl extends SuperServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Override
    public DataGrid selectSysUserList() {
        ActionContext<SysUser> actionContext = ActionContext.getContext() ;
        Page<SysUser> page = actionContext.getPage() ;
        page.setRecords(sysUserMapper.selectSysUserList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    public void add(SysUser sysUser, List<Integer> roleIdList) {
        sysUser.setPswd(SecureUtil.md5(sysUser.getPswd()));
        sysUser.setCreateTime(new Date());
        //新增
        this.insert(sysUser);
        List<SysUserRole> sysUserRoleList = new ArrayList();
        for(Integer roleId : roleIdList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(TextUtil.getLong(roleId));
            sysUserRoleList.add(sysUserRole);
        }
        sysUserRoleService.insertBatch(sysUserRoleList);
    }

    @Override
    public void edit(SysUser sysUser, List<Integer> roleIdList) {
        this.updateById(sysUser);
        Map delMap = new HashMap();
        delMap.put("user_id", sysUser.getUserId());
        sysUserRoleService.deleteByMap(delMap);
        List<SysUserRole> sysUserRoleList = new ArrayList();
        for(Integer roleId : roleIdList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(TextUtil.getLong(roleId));
            sysUserRoleList.add(sysUserRole);
        }
        sysUserRoleService.insertBatch(sysUserRoleList);
    }
}
