package com.xlauch.web.service.sys.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.core.supers.service.SuperServiceImpl;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.util.TextUtil;
import com.xlauch.web.entity.deve.DeveExp;
import com.xlauch.web.entity.sys.SysPermission;
import com.xlauch.web.mapper.sys.SysPermissionMapper;
import com.xlauch.web.service.sys.ISysPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author huangxy
 * @since 2017-11-09
 */
@Service
public class SysPermissionServiceImpl extends SuperServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<Map> getSubMenuList(Map paramMap) {
        return sysPermissionMapper.getSubMenuList(paramMap);
    }

    @Override
    public List<Map> getTopMenuList(Map paramMap) {
        return sysPermissionMapper.getTopMenuList(paramMap);
    }

    @Override
    public DataGrid treeGrid() {
        ActionContext<DeveExp> actionContext = ActionContext.getContext() ;
        Page<DeveExp> page = actionContext.getPage() ;
        DataGrid dataGrid = new DataGrid();
        List<Map> list = sysPermissionMapper.treeGrid(actionContext.getDataMap());
        Map top = new HashMap();
        top.put("permissionId", 0);
        top.put("name", "顶级");
        top.put("status", "顶级");
        list.add(top);
        dataGrid.setRows(list);
        dataGrid.setTotal(page.getTotal());
        return dataGrid;
    }


}
