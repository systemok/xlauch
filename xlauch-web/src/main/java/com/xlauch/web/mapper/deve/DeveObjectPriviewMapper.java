package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveObjectPriview;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述:  Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2018-01-09
 * @version 0.1
 */
public interface DeveObjectPriviewMapper extends SuperMapper<DeveObjectPriview> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveObjectPriview> selectDeveObjectPriviewList(Page<DeveObjectPriview> page, Map paramMap);
}