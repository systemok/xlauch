package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveExpParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 导出参数 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
public interface DeveExpParamMapper extends SuperMapper<DeveExpParam> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveExpParam> selectDeveExpParamList(Page<DeveExpParam> page, Map paramMap);
}