package com.xlauch.web.controller.deve;


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
import com.xlauch.web.service.deve.IDeveQuartzService;
import com.xlauch.web.entity.deve.DeveQuartz;

/**
 * <p>
 * 类描述: 定时任务 前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-12-12
 */
@Controller
@RequestMapping("/deve/deveQuartz")
public class DeveQuartzController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveQuartzController.class);

    @Autowired
    private IDeveQuartzService deveQuartzService;


    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "deve/deveQuartzList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list(String jobName) throws Exception {
        DataGrid dataGrid = deveQuartzService.selectDeveQuartzList(jobName);
        return dataGrid;
    }


    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "deve/deveQuartzAdd";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveQuartz deveQuartz) throws Exception {
        //新增
        deveQuartzService.addJob(deveQuartz);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
        return "deve/deveQuartzEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveQuartz deveQuartz , String oldName) throws Exception {
        //修改
        deveQuartzService.editJob(deveQuartz , oldName);
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

        deveQuartzService.deleteJobs(ids);
        return ResponseCode.writeSuccessResult(ids.split(",").length);
    }

    /**
     * 暂停任务
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/pause")
    @ResponseBody
    public Map pauseJobs(String ids) throws Exception {
        deveQuartzService.pauseJobs(ids);
        return ResponseCode.writeSuccessResult(ids.split(",").length);
    }

    /**
     * 恢复任务
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/resume")
    @ResponseBody
    public Map resumeJobs(String ids) throws Exception {
        deveQuartzService.resumeJobs(ids);
        return ResponseCode.writeSuccessResult(ids.split(",").length);
    }

    /**
     * 暂停所有任务
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/pauseAll")
    @ResponseBody
    public Map pauseAll() throws Exception {
        return deveQuartzService.pauseAll();
    }

    /**
     * 恢复所有任务
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/resumeAll")
    @ResponseBody
    public Map resumeAll() throws Exception {
        return deveQuartzService.resumeAll();
    }

}
