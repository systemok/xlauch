package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveFileInfo;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

/**
 * <p>
 *  类描述: 文件信息表 服务类
 * </p>
 * @author 伊凡
 * @since 2017-12-05
 * @version 0.1
 */
public interface IDeveFileInfoService extends SuperService<DeveFileInfo> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveFileInfoList();


}
