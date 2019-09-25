package com.xlauch.web.controller.deve;


import com.xlauch.utils.util.file.FileUtil;
import com.xlauch.web.common.utils.SysParamUtils;
import com.xlauch.web.config.shiro.Users;
import com.xlauch.web.entity.sys.SysParam;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.web.service.deve.IDeveFileInfoService;
import com.xlauch.web.entity.deve.DeveFileInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 类描述: 文件信息表 前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-12-05
 */
@Controller
@RequestMapping("/deve/deveFileInfo")
public class DeveFileInfoController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveFileInfoController.class);

    @Autowired
    private IDeveFileInfoService deveFileInfoService;

    @Autowired
    private SysParamUtils sysParamUtils ;


    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit() {
        return "deve/deveFileInfoList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveFileInfoService.selectDeveFileInfoList();
        return dataGrid;
    }


    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "deve/deveFileInfoAdd";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(DeveFileInfo deveFileInfo) {
        //新增
        deveFileInfoService.insert(deveFileInfo);
        return ResponseCode.writeSuccess();
    }


    //文件上传相关代码
    @RequestMapping(value = "upload")
    @ResponseBody
    public String upload(@RequestParam("test") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "E://test//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/addBatch")
    @ResponseBody
    public Map handleFileUpload(HttpServletRequest request) {
        String fileUploadPath = sysParamUtils.getSysParamValue("FileUploadPath");
        //29107 = 文件上传目录未配置!
        if(StringUtils.isEmpty(fileUploadPath)){
            return ResponseCode.writeFail("29107");
        }

        //文件目录不存在，自动创建
        File uploadPath = new File(fileUploadPath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    //文件类型
                    String fileType = FilenameUtils.getExtension(file.getOriginalFilename()) ;
                    String saveName = UUID.randomUUID() + (fileType == null ? "" : "." + fileType) ;
                    String pathFull = fileUploadPath + File.separator + saveName ;


                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(pathFull)));
                    stream.write(bytes);
                    stream.close();



                    DeveFileInfo fileInfo = new DeveFileInfo();
                    //上传文件原始名称
                    fileInfo.setUploadName(file.getOriginalFilename());
                    //保存名称
                    fileInfo.setSaveName(saveName) ;
                    //文件大小
                    fileInfo.setSize(FileUtil.FormatFileSize(file.getSize()));
                    //文件类型
                    fileInfo.setType(fileType);
                    //MD5
                    fileInfo.setMd5("");
                    //是否完成
                    fileInfo.setFinish(1);

                    //完整路径
                    fileInfo.setPathAll(pathFull);


                    //创建时间
                    fileInfo.setCreatetime(new Date());
                    //创建人员
                    fileInfo.setCreateuser(Users.getCurrentUser().getUserName());

                    fileInfo.insert();

                    return ResponseCode.writeSuccessResult(fileInfo);
                } catch (Exception e) {
                    stream = null;
                    return ResponseCode.writeFail("29106");
                }
            }
        }

        return ResponseCode.writeFail("29106");
    }


    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit() {
        return "deve/deveFileInfoEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(DeveFileInfo deveFileInfo) {
        //修改
        deveFileInfoService.updateById(deveFileInfo);
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
        deveFileInfoService.deleteBatchIds(idList);
        return ResponseCode.writeSuccessResult(idList.size());
    }

}
