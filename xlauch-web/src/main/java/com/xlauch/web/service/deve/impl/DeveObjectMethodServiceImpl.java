package com.xlauch.web.service.deve.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveObjectMethod;
import com.xlauch.web.mapper.deve.DeveObjectMethodMapper;
import com.xlauch.web.service.deve.IDeveObjectMethodService;
import com.xlauch.core.supers.service.SuperServiceImpl;


/**
 * <p>
 *  类描述: 二次开发--模块管理--模块方法表 服务实现类
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
@Service
public class DeveObjectMethodServiceImpl extends SuperServiceImpl<DeveObjectMethodMapper, DeveObjectMethod> implements IDeveObjectMethodService {

    @Autowired
    private DeveObjectMethodMapper deveObjectMethodMapper ;

    @Override
    public DataGrid selectDeveObjectMethodList() {
        ActionContext<DeveObjectMethod> actionContext = ActionContext.getContext() ;
        Page<DeveObjectMethod> page = actionContext.getPage() ;
        page.setRecords(deveObjectMethodMapper.selectDeveObjectMethodList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }
}
