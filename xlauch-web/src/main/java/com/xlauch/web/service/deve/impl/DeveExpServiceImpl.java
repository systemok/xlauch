package com.xlauch.web.service.deve.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveExp;
import com.xlauch.web.mapper.deve.DeveExpMapper;
import com.xlauch.web.service.deve.IDeveExpService;
import com.xlauch.core.supers.service.SuperServiceImpl;


/**
 * <p>
 * 类描述    : 通用查询、导出 服务实现类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveExpServiceImpl.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
@Service
public class DeveExpServiceImpl extends SuperServiceImpl<DeveExpMapper, DeveExp> implements IDeveExpService {

    @Autowired
    private DeveExpMapper deveExpMapper ;

    @Override
    public DataGrid selectDeveExpList() {
        ActionContext<DeveExp> actionContext = ActionContext.getContext() ;
        Page<DeveExp> page = actionContext.getPage() ;
        page.setRecords(deveExpMapper.selectDeveExpList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }
}
