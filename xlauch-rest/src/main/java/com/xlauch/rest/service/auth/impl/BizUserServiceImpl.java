package com.xlauch.rest.service.auth.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.rest.entity.auth.BizUser;
import com.xlauch.rest.mapper.auth.BizUserMapper;
import com.xlauch.rest.service.auth.IBizUserService;
import com.xlauch.core.supers.service.SuperServiceImpl;


/**
 * <p>
 *  类描述: 普通用户表 服务实现类
 * </p>
 * @author huangxy
 * @since 2018-01-06
 * @version 0.1
 */
@Service
public class BizUserServiceImpl extends SuperServiceImpl<BizUserMapper, BizUser> implements IBizUserService {

    @Autowired
    private BizUserMapper bizUserMapper ;

    @Override
    public DataGrid selectBizUserList() {
        ActionContext<BizUser> actionContext = ActionContext.getContext() ;
        Page<BizUser> page = actionContext.getPage() ;
        page.setRecords(bizUserMapper.selectBizUserList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }
}
