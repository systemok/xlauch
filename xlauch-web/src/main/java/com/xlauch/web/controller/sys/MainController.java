package com.xlauch.web.controller.sys;


import com.alibaba.fastjson.JSON;
import com.xlauch.web.config.shiro.ShiroUser;
import com.xlauch.web.config.shiro.Users;
import com.xlauch.web.entity.sys.SysPermission;
import com.xlauch.web.service.sys.ISysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述    : <br/>
 * 项目名称  : deve 项目<br/>
 * 类名称    : MainController.java <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/8 14:37  <br/>
 * @version 0.1
 */
@Controller
public class MainController {


    private static final Logger log = Logger.getLogger(MainController.class);

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @RequestMapping("/main")
    public String main() {
        log.info("/main -------------");

        return "main";
    }

    @RequestMapping("/top")
    public String top(Model model) {
        ShiroUser user = Users.getCurrentUser();
        Map paramMap = new HashMap();
        paramMap.put("parentId", 0);
        paramMap.put("adminId", user.getUserId());
        if(user.superAdmin) {
            paramMap.put("adminId", -1);
        }
        List<Map> sysPermissions = iSysPermissionService.getTopMenuList(paramMap);
        model.addAttribute("topMenuList", sysPermissions);
        model.addAttribute("userName", user.getNickName());
        return "top";
    }

    @RequestMapping("/center")
    public String center() {
        return "center";
    }


    @RequestMapping("/index")
    public String index() {
        return "index";
    }


    @RequestMapping("/left")
    public String left(Model model, String parentId) {
        ShiroUser user = Users.getCurrentUser();
        if(StringUtils.isBlank(parentId)){
            parentId = "1";
        }
        Map paramMap = new HashMap();
        paramMap.put("parentId", parentId);
        paramMap.put("adminId", user.getUserId());
        if(user.superAdmin) {
            paramMap.put("adminId", -1);
        }
        List<Map> sysPermissions = iSysPermissionService.getSubMenuList(paramMap);
        model.addAttribute("subMenuList", JSON.toJSONString(sysPermissions));
        return "left";
    }



}
