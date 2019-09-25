package com.xlauch.web.service.deve.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveExpParam;
import com.xlauch.web.mapper.deve.DeveExpParamMapper;
import com.xlauch.web.service.deve.IDeveExpParamService;
import com.xlauch.core.supers.service.SuperServiceImpl;


/**
 * <p>
 *  类描述: 导出参数 服务实现类
 * </p>
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
@Service
public class DeveExpParamServiceImpl extends SuperServiceImpl<DeveExpParamMapper, DeveExpParam> implements IDeveExpParamService {

    @Autowired
    private DeveExpParamMapper deveExpParamMapper ;

    @Override
    public DataGrid selectDeveExpParamList() {
        ActionContext<DeveExpParam> actionContext = ActionContext.getContext() ;
        Page<DeveExpParam> page = actionContext.getPage() ;
        page.setRecords(deveExpParamMapper.selectDeveExpParamList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }
}
