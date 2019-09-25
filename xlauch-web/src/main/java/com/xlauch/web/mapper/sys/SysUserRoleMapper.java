package com.xlauch.web.mapper.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.sys.SysUserRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 用户角色关联表 Mapper 接口
 * </p>
 * @author huangxy
 * @since 2017-11-24
 * @version 0.1
 */
public interface SysUserRoleMapper extends SuperMapper<SysUserRole> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<SysUserRole> selectSysUserRoleList(Page<SysUserRole> page, Map paramMap);

    List<SysUserRole> selectUserRoles(Map paramMap);
}