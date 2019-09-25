package com.xlauch.web.controller.deve;


import com.xlauch.utils.util.file.FileDownLoadUtil;
import com.xlauch.utils.util.file.FileUtil;
import com.xlauch.web.common.utils.SysParamUtils;
import com.xlauch.web.entity.deve.DeveFileInfo;
import com.xlauch.web.service.deve.IDeveExpColumnService;
import com.xlauch.web.service.deve.IDeveExpParamService;
import com.xlauch.web.service.deve.IDeveExpService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.web.service.deve.IDeveExpPriviewService;
import com.xlauch.web.entity.deve.DeveExpPriview;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 类描述:  前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-11-16
 */
@Controller
@RequestMapping("/deve/deveExpPriview")
public class DeveExpPriviewController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveExpPriviewController.class);

    /**
     * 导出 预览管理服务类
     */
    @Autowired
    private IDeveExpPriviewService deveExpPriviewService;

    @Autowired
    private SysParamUtils sysParamUtils;

    /**
     * 跳转预览页面
     *
     * @param expCode
     * @param isDebug
     * @param model
     * @return
     */
    @RequestMapping("")
    public String priviewInit(String expCode, boolean isDebug, Model model) {
        int expId = genreExpId(expCode);
        DeveExpPriview deveExpPriview = deveExpPriviewService.getDeveExpPriview(expId, isDebug);
        model.addAttribute("priview", deveExpPriview);
        return deveExpPriview.getDeveExp().getPagePath();
        //return "deve/deveExpPriviewList" ;
    }

    /**
     * 获取预览数据
     *
     * @param expCode
     * @param isDebug
     * @return
     */
    @RequestMapping("/privew")
    @ResponseBody
    public DataGrid priview(String expCode, boolean isDebug) {
        int expId = genreExpId(expCode);
        DataGrid dataGrid = deveExpPriviewService.selectDataGrid(expId, isDebug);
        return dataGrid;
    }

    /**
     * 生成页面
     * @param expCode
     * @return
     */
    @RequestMapping("/generPage")
    @ResponseBody
    public Map generPage(String expCode ) {
        int expId = genreExpId(expCode);
        //获取工程路径文件目录
        String pagePath = getRealPathByClass("/pages/deve/deveExpPriview/") + "exp"+expCode +".html";
        return deveExpPriviewService.generPage(expId , "deve/deveExpPriviewList.html", pagePath);
    }

    /**
     * 导出数据
     *
     * @param expCode
     * @param fileType
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportData")
    public void exportData(String expCode, String fileType, HttpServletResponse response) throws Exception {
        int expId = genreExpId(expCode);
        String filePath = deveExpPriviewService.exportData(expId, fileType);
        if (StringUtils.isNotEmpty(filePath)) {
            FileDownLoadUtil.download(response, filePath);
        }
    }


    /**
     * 导出对象
     */
    @RequestMapping("/exportObject")
    public void export(String ids, HttpServletResponse response) throws Exception {
        List<Integer> idList = getIdList(ids) ;
        String filePath = deveExpPriviewService.exportObject(idList);
        if (StringUtils.isNotEmpty(filePath)) {
            FileDownLoadUtil.download(response, filePath);
        }
    }

    /**
     * 导入对象
     * @param filePath
     * @return
     */
    @RequestMapping("/importObject")
    @ResponseBody
    public Map importObject(String filePath) throws Exception {
        //根据上传的文件导入对象
        deveExpPriviewService.importObject(filePath);
        return ResponseCode.writeSuccess();
    }

    /**
     * 生成ID
     *
     * @return :
     * @Param ： code
     * @Date :  2017/2/15 10:05
     */
    public int genreExpId(String code) {
        try {
            int res = Integer.parseInt(code.substring(4));
            return res - 100000;
        } catch (Exception ex) {
            //10004 = 请检查传入参数是否规范！ 组件code值不对
            ResponseCode.bussException("10004");
        }
        return -1;
    }


}
