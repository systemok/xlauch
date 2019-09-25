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
import com.xlauch.web.service.deve.IDeveObjectColumnService;
import com.xlauch.web.entity.deve.DeveObjectColumn;

/**
 * <p>
 *      类描述: 二次开发-模块管理--字段管理 前端控制器
 * </p>
 *
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
@Controller
@RequestMapping("/deve/deveObjectColumn")
public class DeveObjectColumnController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveObjectColumnController.class);

    @Autowired
    private IDeveObjectColumnService deveObjectColumnService ;


    /**
     * 跳转列表页面
     * @param methodId
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit(Integer methodId, Model model) {
        if (methodId == null) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        model.addAttribute("methodId", methodId);
        return "deve/deveObjectColumnList" ;
    }

    /**
    * 分页查询
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveObjectColumnService.selectDeveObjectColumnList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit(Integer methodId, Model model) {
        if (methodId == null) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }
        model.addAttribute("methodId", methodId);
        return "deve/deveObjectColumnAdd" ;
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveObjectColumn deveObjectColumn) {
        //新增
        if (deveObjectColumn.getDictAble() == 1 && StringUtils.isNotEmpty(deveObjectColumn.getDictStr())){
            deveObjectColumn.setFormatter("formatter: _DICT." + deveObjectColumn.getDictStr().toUpperCase());
        }
        deveObjectColumnService.insert(deveObjectColumn);
        return ResponseCode.writeSuccess();
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
         return "deve/deveObjectColumnEdit" ;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveObjectColumn deveObjectColumn) {
        //修改
        if (deveObjectColumn.getDictAble() == 1 && StringUtils.isNotEmpty(deveObjectColumn.getDictStr())){
            deveObjectColumn.setFormatter("formatter: _DICT." + deveObjectColumn.getDictStr().toUpperCase());
        }
        deveObjectColumnService.updateAllColumnById(deveObjectColumn);
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
        deveObjectColumnService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
