package com.xlauch.web.mapper.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.sys.SysParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: SysParam(系统参数表) Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-11-24
 * @version 0.1
 */
public interface SysParamMapper extends SuperMapper<SysParam> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<SysParam> selectSysParamList(Page<SysParam> page, Map paramMap);
}