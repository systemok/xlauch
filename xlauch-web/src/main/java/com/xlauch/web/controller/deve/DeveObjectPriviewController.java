package com.xlauch.web.controller.deve;


import com.xlauch.core.context.ActionContext;
import com.xlauch.web.entity.deve.DeveObject;
import com.xlauch.web.entity.deve.DeveObjectMethod;
import org.apache.commons.lang3.ObjectUtils;
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
import com.xlauch.web.service.deve.IDeveObjectPriviewService;
import com.xlauch.web.entity.deve.DeveObjectPriview;

/**
 * <p>
 * 类描述:  前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018-01-09
 */
@Controller
@RequestMapping("/deve/deveObjectPriview")
public class DeveObjectPriviewController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveObjectPriviewController.class);

    @Autowired
    private IDeveObjectPriviewService deveObjectPriviewService;


    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit(String _DEVE_OBJCODE, Model model) {
        DeveObject deveObject = deveObjectPriviewService.loadObjectByCode(_DEVE_OBJCODE);
        model.addAttribute("deveObject", deveObject);
        model.addAttribute("method", deveObject.getDefaultMethod());
        return "deve/deveObjectPriviewList";
        //  return "deve/deveExpList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(Integer _DEVE_METHODID, String _DEVE_OBJCODE) {
        if(!ObjectUtils.allNotNull(_DEVE_METHODID , _DEVE_OBJCODE)){
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }

        DataGrid dataGrid = deveObjectPriviewService.selectDeveObjectPriviewList(_DEVE_METHODID,_DEVE_OBJCODE );
        return dataGrid;
    }


    /**
     * 跳转添加、修改页面
     *
     * @return
     */
    @RequestMapping("/addOrUpdateInit")
    public String addOrUpdateInit(Integer _DEVE_METHODID, String _DEVE_OBJCODE, Model model) {
        if(!ObjectUtils.allNotNull(_DEVE_METHODID , _DEVE_OBJCODE)){
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        DeveObjectMethod method = deveObjectPriviewService.loadMethod(_DEVE_METHODID , _DEVE_OBJCODE);
        model.addAttribute("method" , method) ;
        return "deve/deveObjectPriviewAdd";
    }


    /**
     * 新增、修改
     *
     * @return
     */
    @RequestMapping("/addOrUpdate")
    @ResponseBody
    public Map addOrUpdate(Integer _DEVE_METHODID, String _DEVE_OBJCODE) {
        if(!ObjectUtils.allNotNull(_DEVE_METHODID , _DEVE_OBJCODE)){
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        deveObjectPriviewService.addOrUpdateOrDelete(_DEVE_METHODID , _DEVE_OBJCODE);
        //新增
        return ResponseCode.writeSuccess();
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/execute")
    @ResponseBody
    public Map execute(Integer _DEVE_METHODID, String _DEVE_OBJCODE) throws Exception {
        if(!ObjectUtils.allNotNull(_DEVE_METHODID , _DEVE_OBJCODE)){
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        long res = deveObjectPriviewService.addOrUpdateOrDelete(_DEVE_METHODID , _DEVE_OBJCODE);
        //新增
        return ResponseCode.writeSuccessResult(res);
    }

}
