package com.xlauch.web.controller.sys;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.web.service.sys.ISysDictService;
import com.xlauch.web.entity.sys.SysDict;

/**
 * <p>
 *      类描述: 系统字典表 前端控制器
 * </p>
 *
 * @author huangxy
 * @since 2017-11-21
 * @version 0.1
 */
@Controller
@RequestMapping("/sys/sysDict")
public class SysDictController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(SysDictController.class);

    @Autowired
    private ISysDictService sysDictService ;


    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "sys/sysDictList" ;
    }

    /**
    * 分页查询
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = sysDictService.selectSysDictList();
        return dataGrid;
    }

    /**
     * 根据字典编码获取字典列表
     * @param code
     * @return
     */
    @RequestMapping("/listByCode")
    @ResponseBody
    public List<SysDict> selectSysDictByCodeCache(String code){
        if (StringUtils.isNotEmpty(code)) {
            return sysDictService.selectSysDictByCodeCache(code) ;
        }
        return new ArrayList<>();
    }

    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "sys/sysDictAdd" ;
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(SysDict sysDict) {
        //新增
        sysDictService.insert(sysDict);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
         return "sys/sysDictEdit" ;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(SysDict sysDict) {
        //修改
        sysDictService.updateById(sysDict);
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
        sysDictService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
