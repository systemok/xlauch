package com.xlauch.web.controller.deve;


import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.config.redis.RedisManager;
import com.xlauch.core.supers.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xlauch.web.entity.deve.DeveExp;
import com.xlauch.utils.page.DataGrid;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import com.xlauch.web.service.deve.IDeveExpService;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 类描述    : 通用查询、导出 前端控制器 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveExpController.java <br/>
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-11-10
 */
@Controller
@RequestMapping("/deve/deveExp")
public class DeveExpController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveExpController.class);

    @Autowired
    private IDeveExpService deveExpService;

    /**
     * redis 缓存管理
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * redis 缓存的key
     */
    @Value("deve.exp.key")
    private String redisKey ;

    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "deve/deveExpList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveExpService.selectDeveExpList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "deve/deveExpAdd";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveExp deveExp) {
        //新增
        deveExp.setAddTime(new Date());
        deveExpService.insert(deveExp);
        //根据返回的ID更新编号
        deveExp.setCode(generCode(deveExp.getExpId()));
        deveExpService.updateById(deveExp);
        return ResponseCode.writeSuccess();
    }


    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
        return "deve/deveExpEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveExp deveExp) {
        deveExpService.updateById(deveExp);
        return ResponseCode.writeSuccess();
    }


    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(String ids) throws Exception {
        log.info("delete:" + ids);
        //29000: 删除主键值不允许为空
        if (StringUtils.isEmpty(ids)) {
            ResponseCode.bussException("29000");
        }

        List<Integer> idList = getIdList(ids) ;
        deveExpService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

    /**
     * 删除缓存
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteCache")
    @ResponseBody
    public Map deleteCache() throws Exception {
        redisManager.del(redisKey);
        return ResponseCode.writeSuccess();
    }




    /**
     * 生成编码，时间戳 + 4位随机数
     *
     * @return :
     * @Date :  2017/2/15 9:38
     */
    public String generCode(int id) {
        int random = id + 100000;
        String code = "exp_" + random;
        return code;
    }

}
