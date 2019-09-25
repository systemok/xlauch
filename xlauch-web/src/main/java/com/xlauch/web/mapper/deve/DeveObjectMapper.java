package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveObject;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 二次开发-对象表 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-12-28
 * @version 0.1
 */
public interface DeveObjectMapper extends SuperMapper<DeveObject> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveObject> selectDeveObjectList(Page<DeveObject> page, Map paramMap);
}