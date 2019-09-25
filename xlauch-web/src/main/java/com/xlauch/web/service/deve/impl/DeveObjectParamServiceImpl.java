package com.xlauch.web.service.deve.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveObjectParam;
import com.xlauch.web.mapper.deve.DeveObjectParamMapper;
import com.xlauch.web.service.deve.IDeveObjectParamService;
import com.xlauch.core.supers.service.SuperServiceImpl;

import java.util.List;


/**
 * <p>
 *  类描述: 二次开发-导出参数管理 服务实现类
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
@Service
public class DeveObjectParamServiceImpl extends SuperServiceImpl<DeveObjectParamMapper, DeveObjectParam> implements IDeveObjectParamService {

    @Autowired
    private DeveObjectParamMapper deveObjectParamMapper ;

    @Override
    public DataGrid selectDeveObjectParamList() {
        ActionContext<DeveObjectParam> actionContext = ActionContext.getContext() ;
        Page<DeveObjectParam> page = actionContext.getPage() ;
        page.setRecords(deveObjectParamMapper.selectDeveObjectParamList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    public List<DeveObjectParam> selectByObjectId(int objId) {
        return deveObjectParamMapper.selectByObjectId(objId);
    }

    @Override
    public List<DeveObjectParam> selectByMethodId(int methodId) {
        EntityWrapper<DeveObjectParam> ew = new EntityWrapper<DeveObjectParam>();
        ew.where("method_id = {0}", methodId).orderBy("seq asc");
        return selectList(ew);
    }
}
