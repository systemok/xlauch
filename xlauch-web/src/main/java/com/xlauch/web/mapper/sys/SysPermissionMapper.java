package com.xlauch.web.mapper.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.web.entity.sys.SysPermission;
import com.xlauch.core.supers.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 权限表 Mapper 接口
 * </p>
 *
 * @author huangxy
 * @since 2017-11-09
 */
public interface SysPermissionMapper extends SuperMapper<SysPermission> {

    List<Map> getSubMenuList(Map paramMap);

    List<Map> getTopMenuList(Map paramMap);

    List<Map> treeGrid(Map paramMap);

}