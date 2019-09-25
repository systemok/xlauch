package com.xlauch.web.service.sys;

import com.xlauch.web.entity.sys.SysParam;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

/**
 * <p>
 *  类描述: SysParam(系统参数表) 服务类
 * </p>
 * @author 伊凡
 * @since 2017-11-24
 * @version 0.1
 */
public interface ISysParamService extends SuperService<SysParam> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectSysParamList();


}
