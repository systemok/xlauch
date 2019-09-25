package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveExpColumn;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 导出字段 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
public interface DeveExpColumnMapper extends SuperMapper<DeveExpColumn> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveExpColumn> selectDeveExpColumnList(Page<DeveExpColumn> page, Map paramMap);
}