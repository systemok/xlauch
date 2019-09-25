package com.xlauch.rest.mapper.auth;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.rest.entity.auth.BizUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 普通用户表 Mapper 接口
 * </p>
 * @author huangxy
 * @since 2018-01-06
 * @version 0.1
 */
public interface BizUserMapper extends SuperMapper<BizUser> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<BizUser> selectBizUserList(Page<BizUser> page, Map paramMap);
}