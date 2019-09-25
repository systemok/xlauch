package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveExp;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

/**
 * <p>
 *  类描述: 通用查询、导出 服务类
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
public interface IDeveExpService extends SuperService<DeveExp> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveExpList();


}
