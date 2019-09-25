package com.xlauch.web.controller.sys;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.web.service.sys.ISysUserRoleService;
import com.xlauch.web.entity.sys.SysUserRole;

/**
 * <p>
 *      类描述: 用户角色关联表 前端控制器
 * </p>
 *
 * @author huangxy
 * @since 2017-11-24
 * @version 0.1
 */
@Controller
@RequestMapping("/sys/sysUserRole")
public class SysUserRoleController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(SysUserRoleController.class);

    @Autowired
    private ISysUserRoleService sysUserRoleService ;


    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "sys/sysUserRoleList" ;
    }

    /**
    * 分页查询
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = sysUserRoleService.selectSysUserRoleList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "sys/sysUserRoleAdd" ;
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(SysUserRole sysUserRole) {
        //新增
        sysUserRoleService.insert(sysUserRole);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
         return "sys/sysUserRoleEdit" ;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(SysUserRole sysUserRole) {
        //修改
        sysUserRoleService.updateById(sysUserRole);
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
        sysUserRoleService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
