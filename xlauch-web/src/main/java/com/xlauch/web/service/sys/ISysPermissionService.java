package com.xlauch.web.service.sys;

import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.web.entity.sys.SysPermission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author huangxy
 * @since 2017-11-09
 */
public interface ISysPermissionService extends SuperService<SysPermission> {

    public List<Map> getSubMenuList(Map paramMap);

    public List<Map> getTopMenuList(Map paramMap);

    public DataGrid treeGrid();

}
