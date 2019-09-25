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
import com.xlauch.web.service.deve.IDeveExpParamService;
import com.xlauch.web.entity.deve.DeveExpParam;

/**
 * <p>
 *      类描述: 导出参数 前端控制器
 * </p>
 *
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
@Controller
@RequestMapping("/deve/deveExpParam")
public class DeveExpParamController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveExpParamController.class);

    @Autowired
    private IDeveExpParamService deveExpParamService ;


    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit(Model model, int expId) {
        model.addAttribute("expId", expId);
        return "deve/deveExpParamList" ;
    }

    /**
    * 分页查询
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveExpParamService.selectDeveExpParamList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit(Model model, int expId) {
        model.addAttribute("expId", expId);
        return "deve/deveExpParamAdd" ;
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveExpParam deveExpParam) {
        //新增
        deveExpParamService.insert(deveExpParam);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
         return "deve/deveExpParamEdit" ;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveExpParam deveExpParam) {
        //修改
        deveExpParamService.updateById(deveExpParam);
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
        deveExpParamService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
