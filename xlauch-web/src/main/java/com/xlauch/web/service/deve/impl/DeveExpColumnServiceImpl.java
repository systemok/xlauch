package com.xlauch.web.service.deve.impl;


import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.config.mybatis.DataSourceConfig;
import com.xlauch.core.config.mybatis.DbContextHolder;
import com.xlauch.web.entity.deve.DeveExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveExpColumn;
import com.xlauch.web.mapper.deve.DeveExpColumnMapper;
import com.xlauch.web.service.deve.IDeveExpColumnService;
import com.xlauch.core.supers.service.SuperServiceImpl;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  类描述: 导出字段 服务实现类
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
@Service
public class DeveExpColumnServiceImpl extends SuperServiceImpl<DeveExpColumnMapper, DeveExpColumn> implements IDeveExpColumnService {

    @Autowired
    private DeveExpColumnMapper deveExpColumnMapper ;

    @Override
    public DataGrid selectDeveExpColumnList() {
        ActionContext<DeveExpColumn> actionContext = ActionContext.getContext() ;
        Page<DeveExpColumn> page = actionContext.getPage() ;
        page.setRecords(deveExpColumnMapper.selectDeveExpColumnList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }


    @Override
    public Map addDeveExpColumnBatch(DeveExp deveExp){
        // 保存当前数据源
        String orginDataSource = DbContextHolder.getDbType();
        DbContextHolder.setDbType(deveExp.getDatasource());
        String dbType = DataSourceConfig.getDbType("");
        List<DeveExpColumn> columnList = new ArrayList<DeveExpColumn>();
        try {
            String sql = deveExp.getSqlx();
            ResultSetMetaData rsmd = getResultSetMetaData(sql);
            int colCount = rsmd.getColumnCount() ;

            for(int i = 1 ; i <= colCount ; i++){
                String label = rsmd.getColumnLabel(i);

                //字段名称
                String colName = rsmd.getColumnName(i);
                //字段类型名称
                String colType = rsmd.getColumnTypeName(i);
                //字段类型
                int type = rsmd.getColumnType(i);
                //字段长度
                int colLen = rsmd.getPrecision(i);


                DeveExpColumn deveExpColumn = new DeveExpColumn();
                deveExpColumn.setName(label);

                deveExpColumn.setTitle(label);
                //deveExpColumn.setTitle(PropUtil.getProperty("codemsg/column-name", colName, colName));
                deveExpColumn.setAlign("center");
                deveExpColumn.setExpId(deveExp.getExpId());
                deveExpColumn.setCheckable(0);
                deveExpColumn.setForzenable(0);
                deveExpColumn.setOrderable(1);
                deveExpColumn.setShowable(1);
                deveExpColumn.setSeq(i);
                deveExpColumn.setWidth(100);

                columnList.add(deveExpColumn);
            }

            DbContextHolder.setDbType(orginDataSource);
            //批量插入
            insertBatch(columnList);
        } catch (Exception e) {
            //29004 = 查询SQL配置错误,请检查!
            ResponseCode.bussException("29004");
        }

        return ResponseCode.writeSuccessResult(columnList.size());
    }
}
