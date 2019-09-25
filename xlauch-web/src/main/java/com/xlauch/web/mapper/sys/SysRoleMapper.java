package com.xlauch.web.mapper.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.sys.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 角色表 Mapper 接口
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
public interface SysRoleMapper extends SuperMapper<SysRole> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<SysRole> selectSysRoleList(Page<SysRole> page, Map paramMap);
}