package com.xlauch.web.controller.deve;


import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.web.BaseController;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.util.file.FileDownLoadUtil;
import com.xlauch.utils.util.file.FileUtil;
import com.xlauch.web.common.utils.SysParamUtils;
import com.xlauch.web.entity.deve.DeveFileInfo;
import com.xlauch.web.service.deve.IDeveExpResourceService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 类描述:  前端控制器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-11-27
 */
@Controller
@RequestMapping("/deve/deveExpResource")
public class DeveExpResourceController extends BaseController {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(DeveExpResourceController.class);

    @Autowired
    private IDeveExpResourceService deveExpResourceService;


    @Autowired
    private SysParamUtils sysParamUtils;


    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/listInit")
    public String listInit(HttpServletRequest request) {
        //获取系统路径文件目录
        List<Map<String, Object>> dirList = deveExpResourceService.queryFileList(null, false);
        request.setAttribute("dirList", JSON.toJSONString(dirList));

        //获取工程路径文件目录
        String projectPath = getRealPath("/");
        List<Map<String, Object>> projectList = deveExpResourceService.queryFileList(projectPath, false);
        if (projectList == null || projectList.size() == 0) {
            //windows下
            if ("\\".equals(File.separator)) {
                projectPath = Class.class.getClass().getResource("/").getPath().substring(1);
            }else {
                projectPath = Class.class.getClass().getResource("/").getPath();
            }

            projectList = deveExpResourceService.queryFileList(projectPath, false);
        }


        request.setAttribute("projectList", JSON.toJSONString(projectList));
        request.setAttribute("projectPath", projectPath.replaceAll("\\\\","/"));

        return "deve/deveExpResourceList";
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public DataGrid list() {
        DataGrid dataGrid = deveExpResourceService.selectDeveExpResourceList();


        return dataGrid;
    }


    /**
     * 展开树结构，获取下级数据
     *
     * @param path
     * @return
     * @throws Exception
     */
    @RequestMapping("/listTree")
    @ResponseBody
    public List<Map<String, Object>> listTree(String path) throws Exception {
        List<Map<String, Object>> fileList = deveExpResourceService.queryFileList(path, false);
        return fileList;
    }


    /**
     * 查询明细
     *
     * @throws Exception
     */
    @RequestMapping("/listDataGrid")
    @ResponseBody
    public DataGrid listDataGrid(String path) throws Exception {
        List<Map<String, Object>> fileList = deveExpResourceService.queryFileList(path, true);
        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(fileList);
        dataGrid.setTotal(fileList.size());
        return dataGrid;
    }

    /**
     * 搜索
     *
     * @throws Exception
     */
    @RequestMapping("/searchDataGrid")
    @ResponseBody
    public DataGrid searchDataGrid(String path, String searchTxt) throws Exception {
        List<Map<String, Object>> fileList = null;
        //搜索内容为空
        if (StringUtils.isEmpty(searchTxt)) {
            fileList = deveExpResourceService.queryFileList(path, true);
        } else {
            fileList = deveExpResourceService.searchFileList(path, searchTxt);
        }
        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(fileList);
        dataGrid.setTotal(fileList.size());
        return dataGrid;
    }


    /**
     * 判断文件路径是否存在
     *
     * @param path
     * @return
     */
    @RequestMapping("/checkPath")
    @ResponseBody
    public Map checkPath(String path) {
        Map resMap = ResponseCode.writeSuccess();
        if (StringUtils.isEmpty(path)) {
            resMap = ResponseCode.writeFail("29100");
        } else {
            File file = new File(path);
            if (!file.exists()) {
                resMap = ResponseCode.writeFail("29100");
            }
        }
        return resMap;
    }


    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping("/addInit")
    public String addInit() {
        return "deve/deveExpResourceAdd";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(String path, String fileName, boolean isFolder) {
        if (StringUtils.isEmpty(path)) {
            //29100 = 文件路径不存在
            return ResponseCode.writeFail("29100");
        } else if (StringUtils.isEmpty(fileName)) {
            //29101 = 文件名为空
            return ResponseCode.writeFail("29101");
        }

        //添加文件夹
        if (isFolder) {
            return deveExpResourceService.addFolder(path + File.separator + fileName);
        }

        //添加文件
        return deveExpResourceService.addFile(path + File.separator + fileName);
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/editInit")
    public String editInit(String path, String encoding , Model model) throws Exception {
        if (StringUtils.isEmpty(path)) {
            //29100 = 文件路径不存在
            ResponseCode.bussException("29100");
        }

        String content = deveExpResourceService.readFile(path , encoding) ;
        model.addAttribute("path" , path) ;
        model.addAttribute("content" , content) ;
        model.addAttribute("encoding" , encoding) ;
        return "deve/deveExpResourceEdit";
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(String path, String content) throws Exception {
        if (StringUtils.isEmpty(path)) {
            //29100 = 文件路径不存在
            ResponseCode.bussException("29100");
        }

        deveExpResourceService.writeFile(path , content);
        return ResponseCode.writeSuccess();
    }

    /**
     * 修改名称
     *
     * @return
     */
    @RequestMapping("/editName")
    @ResponseBody
    public Map editName(String path, String oldName, String newName) {
        if (StringUtils.isEmpty(path)) {
            //29100 = 文件路径不存在
            return ResponseCode.writeFail("29100");
        } else if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            //29101 = 文件名为空
            return ResponseCode.writeFail("29101");
        }

        return deveExpResourceService.renameResouce(path, oldName, newName);
    }


    /**
     * 文件上传初始化
     *
     * @param path
     * @return
     */
    @RequestMapping("/uploadInit")
    public String uploadInit(String path, HttpServletRequest request) {
        if (StringUtils.isEmpty(path)) {
            //29100 = 文件路径不存在
            ResponseCode.bussException("29101");
        }
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String pathId = UUID.randomUUID() + "";
        request.setAttribute("pathId", pathId);
        request.getSession().setAttribute(pathId, path);
        return "deve/deveExpResourceUpload";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map handleFileUpload(String pathId, HttpServletRequest request) {
        String fileUploadPath = (String) request.getSession().getAttribute(pathId);

        //29106 = 文件不存在或者已被删除!
        if (StringUtils.isEmpty(fileUploadPath)) {
            return ResponseCode.writeFail("29106");
        }

        //文件目录不存在
        File uploadPath = new File(fileUploadPath);
        if (!uploadPath.exists()) {
            return ResponseCode.writeFail("29106");
        }


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    //防止文件名重复造成的覆盖，以下自动重命名
                    String saveName = getFileReName(fileUploadPath, file.getOriginalFilename());
                    String pathFull = fileUploadPath + File.separator + saveName;


                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(pathFull)));
                    stream.write(bytes);
                    stream.close();


                    DeveFileInfo fileInfo = new DeveFileInfo();
                    //文件大小
                    fileInfo.setSize(FileUtil.FormatFileSize(file.getSize()));
                    //完整路径
                    fileInfo.setPathAll(pathFull);
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
     * 文件下载
     *
     * @param paths
     * @param response
     */
    @RequestMapping("/download")
    public void download(String paths, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(paths)) {
            //文件下载路径
            String downLoadPath = "";

            String[] pathArr = paths.split(",");
            File first = new File(pathArr[0]);

            //单个文件下载、不是文件夹
            if (pathArr.length == 1 && first.exists() && first.isFile()) {
                downLoadPath = pathArr[0];
            } else {
                downLoadPath = sysParamUtils.getSysParamValue("FileDownloadPath");
                //29107 = 文件上传目录未配置!
                if (StringUtils.isEmpty(downLoadPath)) {
                    downLoadPath = getRealPath("/download");
                }
                downLoadPath = downLoadPath + File.separator + UUID.randomUUID() + ".zip";


                File[] files = new File[pathArr.length];
                int count = 0;

                for (int i = 0; i < pathArr.length; i++) {
                    File f = new File(pathArr[i]);
                    if (f.exists()) {
                        files[count++] = f;
                    }
                }
                ZipUtil.zip(new File(downLoadPath), true, files);
            }

            try {
                FileDownLoadUtil.download(response, downLoadPath);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(String paths) throws Exception {
        if (StringUtils.isEmpty(paths)) {
            //29100 = 文件路径不存在
            return ResponseCode.writeFail("29100");
        }

        return deveExpResourceService.deleteResouce(paths);
    }

    /**
     * 文件名存在时，自动重命名
     *
     * @param uploadPath
     * @param name
     * @return
     */
    public String getFileReName(String uploadPath, String name) {
        // 文件名存在时，自动重命名
        File f = new File(uploadPath, name);
        int count = 1;
        // 获取文件后缀
        String ext = FilenameUtils.getExtension(name);
        String rename = name;
        while (f.exists()) {
            if (!StringUtils.isEmpty(ext)) {
                rename = name.substring(0, name.lastIndexOf("."));
                rename = rename + "(" + count + ")." + ext;
            } else {
                rename = rename + "(" + count + ")";
            }

            f = new File(uploadPath, rename);
            count++;
        }

        return rename;
    }
}
