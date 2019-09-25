package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveObjectColumn;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 二次开发-模块管理--字段管理 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
public interface DeveObjectColumnMapper extends SuperMapper<DeveObjectColumn> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveObjectColumn> selectDeveObjectColumnList(Page<DeveObjectColumn> page, Map paramMap);

    /**
     * 根据对象ID获取字段列表
     * @param objId
     * @return
     */
    List<DeveObjectColumn> selectByObjectId(int objId) ;


    /**
     * 根据对象ID删除字段
     * @param objId
     */
    void deleteByObjectId(int objId) ;
}