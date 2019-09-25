package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveExpPriview;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述:  Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
public interface DeveExpPriviewMapper extends SuperMapper<DeveExpPriview> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveExpPriview> selectDeveExpPriviewList(Page<DeveExpPriview> page, Map paramMap);
}