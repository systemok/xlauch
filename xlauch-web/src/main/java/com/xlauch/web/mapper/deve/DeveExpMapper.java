package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveExp;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 通用查询、导出 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
public interface DeveExpMapper extends SuperMapper<DeveExp> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveExp> selectDeveExpList(Page<DeveExp> page, Map paramMap);
}