package com.xlauch.web.controller.deve;


import com.xlauch.web.entity.deve.DeveExp;
import com.xlauch.web.service.deve.IDeveExpService;
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
import com.xlauch.web.service.deve.IDeveExpColumnService;
import com.xlauch.web.entity.deve.DeveExpColumn;

/**
 * <p>
 * 类描述: 导出字段 前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-11-15
 */
@Controller
@RequestMapping("/deve/deveExpColumn")
public class DeveExpColumnController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveExpColumnController.class);

    @Autowired
    private IDeveExpColumnService deveExpColumnService;

    @Autowired
    private IDeveExpService deveExpService ;


    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit(Model model, int expId) {
        model.addAttribute("expId", expId);
        return "deve/deveExpColumnList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveExpColumnService.selectDeveExpColumnList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit(Model model, int expId) {
        model.addAttribute("expId", expId);
        return "deve/deveExpColumnAdd";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveExpColumn deveExpColumn) {
        //新增
        deveExpColumnService.insert(deveExpColumn);
        return ResponseCode.writeSuccess();
    }


    /**
     * 批量生成字段列表
     * @param expId
     * @return
     * @throws Exception
     */
    @RequestMapping("/addBatch")
    @ResponseBody
    public Map addBatch(Integer expId) throws Exception {
        //29001: 组件ID不允许为空
        if(expId == null) {
            ResponseCode.bussException("29001");
        }
        //29003 = 组件不存在
        DeveExp deveExp  = deveExpService.selectById(expId);
        if(deveExp == null) {
            ResponseCode.bussException("29003");
        }

        return deveExpColumnService.addDeveExpColumnBatch(deveExp);
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
        return "deve/deveExpColumnEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveExpColumn deveExpColumn) {
        //修改
        deveExpColumnService.updateById(deveExpColumn);
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
        deveExpColumnService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
