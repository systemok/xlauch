package com.xlauch.web.controller.sys;


import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.web.entity.sys.SysUser;
import com.xlauch.web.entity.sys.SysUserRole;
import com.xlauch.web.service.sys.ISysUserRoleService;
import com.xlauch.web.service.sys.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *      类描述: 系统用户表 前端控制器
 * </p>
 *
 * @author huangxy
 * @since 2017-11-16
 * @version 0.1
 */
@Controller
@RequestMapping("/sys/sysUser")
public class SysUserController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(SysUserController.class);

    @Autowired
    private ISysUserService sysUserService ;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "sys/sysUserList" ;
    }

    /**
    * 分页查询
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = sysUserService.selectSysUserList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "sys/sysUserAdd" ;
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(SysUser sysUser, String roleIds) {
        List<Integer> roleIdList = getIdList(roleIds) ;
        //新增
        sysUserService.add(sysUser, roleIdList);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
         return "sys/sysUserEdit" ;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(SysUser sysUser, String roleIds) {
        List<Integer> roleIdList = getIdList(roleIds) ;
        //修改
        sysUserService.edit(sysUser, roleIdList);
        return ResponseCode.writeSuccess();
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(String ids) throws Exception {
        log.info("delete:" + ids);
        //29000: 删除主键值不允许为空
        if (StringUtils.isEmpty(ids)) {
            ResponseCode.bussException("29000");
        }

        List<Integer> idList = getIdList(ids) ;
        sysUserService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

    /**
     * 获取用户角色
     *
     * @return
     */
    @RequestMapping("/userRole")
    @ResponseBody
    public Map userRole(Long userId) {
        //查询
        List<SysUserRole> sysUserRoleList = sysUserRoleService.selectUserRoles();
        List roleIdList = new ArrayList();
        for(SysUserRole sysUserRole1 : sysUserRoleList){
            roleIdList.add(sysUserRole1.getRoleId());
        }
        return ResponseCode.writeSuccessResult(roleIdList);
    }

}
