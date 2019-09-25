package com.xlauch.web.controller.sys;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xlauch.web.entity.sys.SysPermission;
import com.xlauch.web.entity.sys.SysRolePermission;
import com.xlauch.web.service.sys.ISysPermissionService;
import com.xlauch.web.service.sys.ISysRolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.web.service.sys.ISysRoleService;
import com.xlauch.web.entity.sys.SysRole;

/**
 * <p>
 *      类描述: 角色表 前端控制器
 * </p>
 *
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
@Controller
@RequestMapping("/sys/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(SysRoleController.class);

    @Autowired
    private ISysRoleService sysRoleService ;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService ;


    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "sys/sysRoleList" ;
    }

    /**
    * 分页查询
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = sysRoleService.selectSysRoleList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit(Model model) {
        Map paramMap = new HashMap();
        paramMap.put("parentId", 0);
        paramMap.put("adminId", -1);
        paramMap.put("button", 1);
        List<Map> sysPermissions = iSysPermissionService.getSubMenuList(paramMap);
        model.addAttribute("menuList", JSON.toJSONString(sysPermissions));
        return "sys/sysRoleAdd" ;
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(SysRole sysRole, String permissionIds) {
        //新增
        List<Integer> idList = getIdList(permissionIds) ;
        sysRoleService.insertAndGrant(sysRole, idList);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit(Model model) {
        Map paramMap = new HashMap();
        paramMap.put("parentId", 0);
        paramMap.put("adminId", -1);
        paramMap.put("button", 1);
        List<Map> sysPermissions = iSysPermissionService.getSubMenuList(paramMap);
        model.addAttribute("menuList", JSON.toJSONString(sysPermissions));
         return "sys/sysRoleEdit" ;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(SysRole sysRole, String permissionIds) {
        //修改
        List<Integer> idList = getIdList(permissionIds) ;
        sysRoleService.updateAndGrant(sysRole, idList);
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
        sysRoleService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

    /**
     * 权限分配页面
     *
     * @return
     */
    @RequestMapping("/grantInit")
    public String grantInit(Model model) {
        Map paramMap = new HashMap();
        paramMap.put("parentId", 0);
        paramMap.put("adminId", -1);
        paramMap.put("button", 1);
        List<Map> sysPermissions = iSysPermissionService.getSubMenuList(paramMap);
        model.addAttribute("menuList", JSON.toJSONString(sysPermissions));
        return "/sys/sysRolePermissions";
    }

    /**
     * 获取角色权限
     *
     * @return
     */
    @RequestMapping("/rolePermission")
    @ResponseBody
    public Map rolePermission(Long roleId) {
        EntityWrapper<SysRolePermission> sysRolePermissionEntityWrapper = new EntityWrapper();
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setRoleId(roleId);
        sysRolePermissionEntityWrapper.setEntity(sysRolePermission);
        return ResponseCode.writeSuccessResult(sysRolePermissionService.selectList(sysRolePermissionEntityWrapper));
    }

    /**
     * 授权
     *
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Map grant(String ids, Long roleId) throws Exception {
        log.info("grant:" + ids);
        //29000: 删除主键值不允许为空
        if (StringUtils.isEmpty(ids)) {
            ResponseCode.bussException("10009");
        }

        List<Integer> idList = getIdList(ids) ;
        sysRoleService.grantPermission(idList, roleId);
        return ResponseCode.writeSuccess();
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @RequestMapping("/roleList")
    @ResponseBody
    public List roleList(){
        Map paramMap = new HashMap();
        paramMap.put("status", 1);
        return sysRoleService.selectByMap(paramMap);
    }

}
