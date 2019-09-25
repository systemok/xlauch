package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveQuartz;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 定时任务 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-12-12
 * @version 0.1
 */
public interface DeveQuartzMapper extends SuperMapper<DeveQuartz> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveQuartz> selectDeveQuartzList(Page<DeveQuartz> page, Map paramMap);
}