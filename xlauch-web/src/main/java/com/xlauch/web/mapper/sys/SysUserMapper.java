package com.xlauch.web.mapper.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.sys.SysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 系统用户表 Mapper 接口
 * </p>
 * @author huangxy
 * @since 2017-11-16
 * @version 0.1
 */
public interface SysUserMapper extends SuperMapper<SysUser> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<SysUser> selectSysUserList(Page<SysUser> page, Map paramMap);
}