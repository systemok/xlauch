package com.xlauch.web.service.sys;

import com.xlauch.web.entity.sys.SysDict;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.List;

/**
 * <p>
 *  类描述: 系统字典表 服务类
 * </p>
 * @author huangxy
 * @since 2017-11-21
 * @version 0.1
 */
public interface ISysDictService extends SuperService<SysDict> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectSysDictList();


    /**
     * <p>
     * 方法描述：获取字典带缓存
     * </p>
     *
     * @author huangxy
     * @since 2017/11/21 11:26
     * @version 0.1
     */
    public List<SysDict> selectSysDictByCodeCache(SysDict sysDict);

    /**
     * <p>
     * 方法描述：根据字典code获取列表
     * </p>
     *
     * @param code
     * @return
     */
    public List<SysDict> selectSysDictByCodeCache(String code);
}
