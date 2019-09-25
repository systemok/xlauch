package com.xlauch.web.service.sys;

import com.xlauch.web.entity.sys.SysUser;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.List;

/**
 * <p>
 *  类描述: 系统用户表 服务类
 * </p>
 * @author huangxy
 * @since 2017-11-16
 * @version 0.1
 */
public interface ISysUserService extends SuperService<SysUser> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectSysUserList();

    /**
     * <p>
     * 方法描述：新增用户
     * </p>
     *
     * @param sysUser
     * @param roleIdList
     * @author huangxy
     * @since 2017/11/24 14:08
     * @version 0.1
     */
    public void add(SysUser sysUser, List<Integer> roleIdList);

    /**
     * <p>
     * 方法描述：修改用户
     * </p>
     *
     * @param sysUser
     * @param roleIdList
     * @author huangxy
     * @since 2017/11/24 14:08
     * @version 0.1
     */
    public void edit(SysUser sysUser, List<Integer> roleIdList);

}
