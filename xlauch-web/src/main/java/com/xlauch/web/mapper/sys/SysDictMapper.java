package com.xlauch.web.mapper.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.sys.SysDict;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 系统字典表 Mapper 接口
 * </p>
 * @author huangxy
 * @since 2017-11-21
 * @version 0.1
 */
public interface SysDictMapper extends SuperMapper<SysDict> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<SysDict> selectSysDictList(Page<SysDict> page, Map paramMap);
}