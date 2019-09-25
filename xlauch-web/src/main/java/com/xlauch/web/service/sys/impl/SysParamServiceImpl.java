package com.xlauch.web.service.sys.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.sys.SysParam;
import com.xlauch.web.mapper.sys.SysParamMapper;
import com.xlauch.web.service.sys.ISysParamService;
import com.xlauch.core.supers.service.SuperServiceImpl;


/**
 * <p>
 *  类描述: SysParam(系统参数表) 服务实现类
 * </p>
 * @author 伊凡
 * @since 2017-11-24
 * @version 0.1
 */
@Service
public class SysParamServiceImpl extends SuperServiceImpl<SysParamMapper, SysParam> implements ISysParamService {

    @Autowired
    private SysParamMapper sysParamMapper ;

    @Override
    public DataGrid selectSysParamList() {
        ActionContext<SysParam> actionContext = ActionContext.getContext() ;
        Page<SysParam> page = actionContext.getPage() ;
        page.setRecords(sysParamMapper.selectSysParamList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }
}
