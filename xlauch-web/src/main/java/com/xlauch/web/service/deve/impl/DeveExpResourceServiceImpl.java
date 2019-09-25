package com.xlauch.web.service.deve.impl;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.context.ActionContext;
import com.xlauch.core.supers.service.SuperServiceImpl;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.util.file.FileUtil;
import com.xlauch.web.entity.deve.DeveExpResource;
import com.xlauch.web.mapper.deve.DeveExpResourceMapper;
import com.xlauch.web.service.deve.IDeveExpResourceService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * <p>
 *  类描述:  服务实现类
 * </p>
 * @author 伊凡
 * @since 2017-11-27
 * @version 0.1
 */
@Service
public class DeveExpResourceServiceImpl extends SuperServiceImpl<DeveExpResourceMapper, DeveExpResource> implements IDeveExpResourceService {

    @Autowired
    private DeveExpResourceMapper deveExpResourceMapper ;

    @Override
    public DataGrid selectDeveExpResourceList() {
        ActionContext<DeveExpResource> actionContext = ActionContext.getContext() ;
        Page<DeveExpResource> page = actionContext.getPage() ;
        page.setRecords(deveExpResourceMapper.selectDeveExpResourceList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    public List<Map<String, Object>> queryFileList(String dirPath, boolean includeFile)  {
        File[] files = null;

        // 如果路径为空，默认为根路径
        if (dirPath == null) {
            files = File.listRoots();
        } else {
            File dir = new File(dirPath);
            if (dir.exists()) {
                files = dir.listFiles();
            }
        }
        return listFile(files , includeFile);
    }


    @Override
    public List<Map<String, Object>> searchFileList(String dirPath , String searchTxt) {
        File dir = new File(dirPath) ;
        //文件存在且是文件夹
        if(dir.exists() && dir.isDirectory()) {
            Set<File> fileSet = new HashSet<>();
            //文件文件名搜索
            Collection<File> listFilesByName = FileUtils.listFiles(dir, new AbstractFileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().toLowerCase().contains(searchTxt.toLowerCase()) ;
                }
            }, DirectoryFileFilter.INSTANCE);
            fileSet.addAll(listFilesByName);

            //fileSet.addAll(FileUtils.listFiles(dir , new AbstractFileFilter(file -> return file. ) ,DirectoryFileFilter.INSTANCE));

            //根据通配符搜索
            fileSet.addAll(FileUtils.listFiles(dir , new WildcardFileFilter(searchTxt) ,DirectoryFileFilter.INSTANCE));

            return listFile(fileSet.toArray(new File[0]) , true);
        }
        return new ArrayList<Map<String, Object>>();
    }

    @Override
    public Map addFolder(String path) {
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
            return ResponseCode.writeSuccess();
        }

        //29102 = 该文件夹已经存在，请更换名字!
        return ResponseCode.writeFail("29102");
    }

    @Override
    public Map addFile(String path) {
        File f = new File(path);

        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                //29103 = IO 异常，添加文件失败
                return ResponseCode.writeFail("29103");
            }
            return ResponseCode.writeSuccess();
        }

        //29102 = 该文件夹已经存在，请更换名字!
        return ResponseCode.writeFail("29102");
    }


    @Override
    public Map renameResouce(String path, String oldName, String newName) {
        String oldPath = path + File.separator + oldName;
        String newPath = path + File.separator + newName;

        if(!newName.equals(oldName)){
            File oldFile = new File(oldPath);
            File newFile = new File(newPath);
            if(oldFile.exists()){
                if(newFile.exists()){
                    //29104 = 新名称已经存在,请更换!
                    return ResponseCode.writeFail("29104");
                }else {
                    if(!oldFile.renameTo(newFile)){
                        return ResponseCode.writeFail("29105");
                    }
                }
            }else {
                return ResponseCode.writeFail("29106");
            }
        }

        return ResponseCode.writeSuccess();
    }

    @Override
    public Map deleteResouce(String paths) {
        String[] pathArr = paths.split(",");
        for(String path : pathArr){
            File file = new File(path);
            deleteFile(file);
        }

        return ResponseCode.writeSuccess();
    }

    @Override
    public String readFile(String path, String encoding) throws Exception {
        StringBuffer sbf = new StringBuffer() ;
        String content = FileUtil.readFile(path ,encoding) ;
        sbf.append(StringEscapeUtils.escapeHtml4(content));
//        System.out.println("" + content);
//        System.out.println("------------------------------------");
//        System.out.println("转换：" + StringEscapeUtils.escapeHtml4(content));
//        System.out.println("------------------------------------");
//        System.out.println("转换：" + StringEscapeUtils.unescapeHtml4(StringEscapeUtils.escapeHtml4(content)));
        return sbf.toString();
    }

    @Override
    public void writeFile(String path, String content) throws Exception {
        String encoding = FileUtil.getEncodeing(path) ;
        content = StringEscapeUtils.unescapeHtml4(content);
        FileUtils.writeStringToFile(new File(path) ,content , encoding);
    }

    public boolean deleteFile(File file){
        if(file.exists()){
            if(file.isFile()){
                file.delete();
            }else if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File f : files){
                    deleteFile(f);
                }
                file.delete();
            }
        }
        return false ;
    }


    /**
     * 把文件信息整理成集合
     * @param files
     * @param includeFile
     * @return
     */
    private List<Map<String, Object>> listFile(File[] files , boolean includeFile) {
        List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
        // 判断文件数组是否为空
        if (files != null) {
            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> dirList = new ArrayList<Map<String, Object>>();
            JFileChooser chooser = new JFileChooser();

            for (File file : files) {
                // 文件名称
                String name = file.getName();
                String path = file.getPath().replaceAll("\\\\","/") ;
                String parent = file.getParent() == null ? "" : file.getParent().replaceAll("\\\\","/");
                if (StringUtils.isEmpty(name)) {
                    name = file.getPath();
                }
                // 修改时间
                String updatetime = DateUtil.formatDateTime(DateUtil.date(file.lastModified()));
                /*long lupdatetime = file.lastModified();
                Date date = new Date();
                date.setTime(lupdatetime);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String updatetime = sd.format(date);*/

                // 封装文件信息
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", path);
                map.put("name", name);
                map.put("path", path);
                map.put("isParent", file.isDirectory());
                map.put("pid", parent);
                map.put("updatetime", updatetime);

                // 如果是文件，多加文件大小信息
                if (!file.isDirectory()) {
                    // 获取文件类型中文说明
                    String fileTypeName = chooser.getTypeDescription(file);

                    map.put("size", FileUtil.FormatFileSize(file.length()));
                    map.put("length", file.length());
                    map.put("type", fileTypeName);
                    map.put("postfix", FileUtil.getPostFix(name));
                    fileList.add(map);
                } else {
                    map.put("type", "文件夹");
                    map.put("length", 0);
                    dirList.add(map);
                }
            }

            // 添加
            allList.addAll(dirList);
            if (includeFile) {
                allList.addAll(fileList);
            }
        }

        return allList ;
    }
}
