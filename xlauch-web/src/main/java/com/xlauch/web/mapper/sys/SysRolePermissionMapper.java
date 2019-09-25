package com.xlauch.web.mapper.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.sys.SysRolePermission;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 角色权限表 Mapper 接口
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
public interface SysRolePermissionMapper extends SuperMapper<SysRolePermission> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<SysRolePermission> selectSysRolePermissionList(Page<SysRolePermission> page, Map paramMap);
}