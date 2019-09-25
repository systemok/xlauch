package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveObjectMethod;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

/**
 * <p>
 *  类描述: 二次开发--模块管理--模块方法表 服务类
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
public interface IDeveObjectMethodService extends SuperService<DeveObjectMethod> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveObjectMethodList();


}
