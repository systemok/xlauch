package com.xlauch.web.controller.deve;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.web.service.deve.IDeveObjectMethodService;
import com.xlauch.web.entity.deve.DeveObjectMethod;

/**
 * <p>
 * 类描述: 二次开发--模块管理--模块方法表 前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018-01-06
 */
@Controller
@RequestMapping("/deve/deveObjectMethod")
public class DeveObjectMethodController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveObjectMethodController.class);

    @Autowired
    private IDeveObjectMethodService deveObjectMethodService;


    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit(Integer objId, Model model) {
        if (objId == null) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        model.addAttribute("objId", objId);
        return "deve/deveObjectMethodList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveObjectMethodService.selectDeveObjectMethodList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addMainInit")
    public String addMainInit(Integer objId, Model model) {
        if (objId == null) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        model.addAttribute("objId", objId);
        return "deve/deveObjectMethodAddMain";
    }

    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit(Integer objId, Model model) {
        if (objId == null) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        model.addAttribute("objId", objId);
        return "deve/deveObjectMethodAdd";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveObjectMethod deveObjectMethod) {
        if (deveObjectMethod.getMethodId() == null){
            //新增
            deveObjectMethodService.insert(deveObjectMethod);
        }else {
            deveObjectMethodService.updateById(deveObjectMethod);
        }

        return ResponseCode.writeSuccess(deveObjectMethod.getMethodId()+"");
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editMainInit")
    public String editMainInit(Integer methodId, Model model) {
        if (methodId == null) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }

        DeveObjectMethod method = deveObjectMethodService.selectById(methodId);
        model.addAttribute("method", method);
        model.addAttribute("methodId", methodId);
        return "deve/deveObjectMethodEditMain";
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit(Integer methodId, Model model) {
        if (methodId == null) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }

        DeveObjectMethod method = deveObjectMethodService.selectById(methodId);
        model.addAttribute("method", method);
        return "deve/deveObjectMethodEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveObjectMethod deveObjectMethod) {
        //修改
        deveObjectMethod.updateAllColumnById();
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

        List<Integer> idList = getIdList(ids);
        deveObjectMethodService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
