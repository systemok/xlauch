package com.xlauch.web.controller.sys;


import com.xlauch.web.common.utils.SysParamUtils;
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
import com.xlauch.web.service.sys.ISysParamService;
import com.xlauch.web.entity.sys.SysParam;

/**
 * <p>
 *      类描述: SysParam(系统参数表) 前端控制器
 * </p>
 *
 * @author 伊凡
 * @since 2017-11-24
 * @version 0.1
 */
@Controller
@RequestMapping("/sys/sysParam")
public class SysParamController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(SysParamController.class);

    @Autowired
    private ISysParamService sysParamService ;

    @Autowired
    private SysParamUtils sysParamUtils ;


    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "sys/sysParamList" ;
    }

    /**
    * 分页查询
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = sysParamService.selectSysParamList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "sys/sysParamAdd" ;
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(SysParam sysParam) {
        //新增
        sysParam.setCreatetime(new Date());
        sysParam.setCreateuser(Users.getCurrentUser().getUserName());
        sysParamService.insert(sysParam);
        //添加到缓存
        sysParamUtils.addCache(sysParam);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
         return "sys/sysParamEdit" ;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(SysParam sysParam) {
        //修改
        sysParam.setUpdatetime(new Date());
        sysParam.setUpdateuser(Users.getCurrentUser().getUserName());
        sysParamService.updateById(sysParam);

        //重置缓存
        sysParamUtils.addCache(sysParam);
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
        sysParamService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
