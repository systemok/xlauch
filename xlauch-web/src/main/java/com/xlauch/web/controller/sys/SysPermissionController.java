package com.xlauch.web.controller.sys;


import com.alibaba.fastjson.JSON;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.exception.ExceptionUtils;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.web.entity.sys.SysPermission;
import com.xlauch.web.service.sys.ISysPermissionService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author huangxy
 * @since 2017-11-09
 */
@Controller
@RequestMapping("/sys/sysPermission")
@Log4j
public class SysPermissionController extends BaseController{

    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 跳转列表页面
     * @Date 2017-11-13
     */
    @RequestMapping
    public String listInit(){
        return "/sys/sysPermissionList" ;
    }

    /**
     * 查询列表
     * @Date 2017-11-13
     * @return :DataGrid
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = iSysPermissionService.treeGrid();
        return iSysPermissionService.treeGrid() ;
    }

    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit(Model model) {
        Map paramMap = new HashMap();
        paramMap.put("parentId", 0);
        paramMap.put("adminId", -1);
        List<Map> sysPermissions = iSysPermissionService.getSubMenuList(paramMap);
        {
            Map all = new HashMap();
            all.put("id", 0);
            all.put("pid", -1);
            all.put("vurl", "");
            all.put("name", "顶级");
            all.put("icon", "");
            sysPermissions.add(all);
        }
        model.addAttribute("menuList", JSON.toJSONString(sysPermissions));
        return "/sys/sysPermissionAdd";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(SysPermission sysPermission) {
        //新增
        iSysPermissionService.insert(sysPermission);
        return ResponseCode.writeSuccess();
    }


    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit(Model model) {
        Map paramMap = new HashMap();
        paramMap.put("parentId", 0);
        paramMap.put("adminId", -1);
        List<Map> sysPermissions = iSysPermissionService.getSubMenuList(paramMap);
        {
            Map all = new HashMap();
            all.put("id", 0);
            all.put("pid", -1);
            all.put("vurl", "");
            all.put("name", "所有");
            all.put("icon", "");
            sysPermissions.add(all);
        }
        model.addAttribute("menuList", JSON.toJSONString(sysPermissions));
        return "/sys/sysPermissionEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(SysPermission sysPermission) {
        iSysPermissionService.updateAllColumnById(sysPermission);
        return ResponseCode.writeSuccess();
    }


    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(String ids) throws Exception {
        //29000: 删除主键值不允许为空
        if (StringUtils.isEmpty(ids)) {
            ResponseCode.bussException("29000");
        }

        List<Integer> idList = getIdList(ids) ;
        iSysPermissionService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(1);
    }

    /**
     * 图标页面
     *
     * @return
     */
    @RequestMapping("/iconInit")
    public String iconInit(SysPermission sysPermission) {
        iSysPermissionService.updateAllColumnById(sysPermission);
        return "/sys/sysPermissionIcon";
    }

    @RequestMapping(value = "/icons", method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request) {
        String pre = "/res/jquery-easyui-1.5.3/themes/myicons/menu/";
        String imagePath = null;
        try {
            imagePath = this.getClass().getClassLoader().getResource("/").toURI().getPath()+ "static"+pre;
        } catch (URISyntaxException e) {
            log.error(ExceptionUtils.getStackTraceAsString(e));
        }
        ;
        File dirs = new File(imagePath);
        List<String> imageList = new ArrayList<String>();
        for (String name : dirs.list()) {
            imageList.add(pre + name);
        }
        return imageList;
    }

}
