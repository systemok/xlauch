package com.xlauch.web.service.deve.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveObjectColumn;
import com.xlauch.web.mapper.deve.DeveObjectColumnMapper;
import com.xlauch.web.service.deve.IDeveObjectColumnService;
import com.xlauch.core.supers.service.SuperServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  类描述: 二次开发-模块管理--字段管理 服务实现类
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
@Service
public class DeveObjectColumnServiceImpl extends SuperServiceImpl<DeveObjectColumnMapper, DeveObjectColumn> implements IDeveObjectColumnService {

    @Autowired
    private DeveObjectColumnMapper deveObjectColumnMapper ;

    @Override
    public DataGrid selectDeveObjectColumnList() {
        ActionContext<DeveObjectColumn> actionContext = ActionContext.getContext() ;
        Page<DeveObjectColumn> page = actionContext.getPage() ;
        page.setRecords(deveObjectColumnMapper.selectDeveObjectColumnList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    public List<DeveObjectColumn> selectByObjectId(int objId) {
        return deveObjectColumnMapper.selectByObjectId(objId);
    }

    @Override
    public List<DeveObjectColumn> selectByMethodId(int methodId) {
        EntityWrapper<DeveObjectColumn> ew = new EntityWrapper<DeveObjectColumn>();
        ew.where("method_id = {0}", methodId).orderBy("seq asc");
        return selectList(ew);
    }
}
