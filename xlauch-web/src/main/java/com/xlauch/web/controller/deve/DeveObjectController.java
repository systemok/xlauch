package com.xlauch.web.controller.deve;


import com.xlauch.core.config.mybatis.DbContextHolder;
import com.xlauch.utils.util.db.MetaData;
import com.xlauch.web.config.shiro.Users;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.web.service.deve.IDeveObjectService;
import com.xlauch.web.entity.deve.DeveObject;

/**
 * <p>
 * 类描述: 二次开发-对象表 前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-12-28
 */
@Controller
@RequestMapping("/deve/deveObject")
public class DeveObjectController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveObjectController.class);

    @Autowired
    private IDeveObjectService deveObjectService;


    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "deve/deveObjectList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveObjectService.selectDeveObjectList();
        return dataGrid;
    }


    /**
     * 获取数据库对象
     *
     * @param dataSource
     * @return
     */
    @RequestMapping("/listDB")
    @ResponseBody
    public List<MetaData> listDB(String dataSource , String type){
        List<MetaData> metaDataList = null ;
        if (StringUtils.isNotEmpty(type)) {
            metaDataList = deveObjectService.queryTables(dataSource , type.split(","));
        }else {
            metaDataList = deveObjectService.queryTables(dataSource , null);
        }
        return metaDataList ;
    }

    /**
     * 验证code是否重复
     * @param code
     * @return
     */
    @RequestMapping("/codeCheck")
    @ResponseBody
    public boolean checkCodeIsExists(String code) {
        if (StringUtils.isNotEmpty(code)) {
            return deveObjectService.selectByCode(code) == null ;
        }

        return false ;
    }

    /**
     *
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "deve/deveObjectAdd";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveObject deveObject) {
        //新增
        deveObjectService.addDeveObject(deveObject);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
        return "deve/deveObjectEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveObject deveObject) {
        //修改
        deveObject.setUpdateTime(new Date());
        deveObject.setUpdateUser(Integer.parseInt(Users.getCurrentUser().getUserId() + ""));
        deveObjectService.updateById(deveObject);
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
        deveObjectService.deleteBatch(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
