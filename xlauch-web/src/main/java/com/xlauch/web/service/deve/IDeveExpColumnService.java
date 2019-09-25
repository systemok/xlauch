package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveExp;
import com.xlauch.web.entity.deve.DeveExpColumn;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.Map;

/**
 * <p>
 *  类描述: 导出字段 服务类
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
public interface IDeveExpColumnService extends SuperService<DeveExpColumn> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveExpColumnList();


    /**
     * 根据查询语句批量生成字段
     * @param deveExp
     * @return
     */
    public Map addDeveExpColumnBatch(DeveExp deveExp) ;


}
