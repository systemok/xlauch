package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveObjectMethod;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 二次开发--模块管理--模块方法表 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
public interface DeveObjectMethodMapper extends SuperMapper<DeveObjectMethod> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveObjectMethod> selectDeveObjectMethodList(Page<DeveObjectMethod> page, Map paramMap);


    /**
     * 根据对象ID删除方法
     * @param objId
     */
    void deleteByObjectId(int objId) ;
}