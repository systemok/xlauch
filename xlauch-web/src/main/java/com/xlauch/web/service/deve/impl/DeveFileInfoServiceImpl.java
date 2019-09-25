package com.xlauch.web.service.deve.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveFileInfo;
import com.xlauch.web.mapper.deve.DeveFileInfoMapper;
import com.xlauch.web.service.deve.IDeveFileInfoService;
import com.xlauch.core.supers.service.SuperServiceImpl;


/**
 * <p>
 *  类描述: 文件信息表 服务实现类
 * </p>
 * @author 伊凡
 * @since 2017-12-05
 * @version 0.1
 */
@Service
public class DeveFileInfoServiceImpl extends SuperServiceImpl<DeveFileInfoMapper, DeveFileInfo> implements IDeveFileInfoService {

    @Autowired
    private DeveFileInfoMapper deveFileInfoMapper ;

    @Override
    public DataGrid selectDeveFileInfoList() {
        ActionContext<DeveFileInfo> actionContext = ActionContext.getContext() ;
        Page<DeveFileInfo> page = actionContext.getPage() ;
        page.setRecords(deveFileInfoMapper.selectDeveFileInfoList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }
}
