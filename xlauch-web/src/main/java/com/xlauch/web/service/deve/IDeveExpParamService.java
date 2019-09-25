package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveExpParam;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

/**
 * <p>
 *  类描述: 导出参数 服务类
 * </p>
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
public interface IDeveExpParamService extends SuperService<DeveExpParam> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveExpParamList();


}
