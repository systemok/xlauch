package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveExpResource;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述:  服务类
 * </p>
 * @author 伊凡
 * @since 2017-11-27
 * @version 0.1
 */
public interface IDeveExpResourceService extends SuperService<DeveExpResource> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveExpResourceList();


    /**
     * 获取文件夹下所有路径
     * @param dirPath
     * @param inclueFile
     * @return
     */
    public List<Map<String, Object>> queryFileList(String dirPath , boolean inclueFile)  ;



    /**
     * 根据关键字搜索
     * @param dirPath
     * @param searchTxt
     * @return
     */
    public List<Map<String, Object>> searchFileList(String dirPath , String searchTxt)  ;



    /**
     * 添加文件夹
     * @param path
     * @return
     */
    public Map addFolder(String path) ;

    /**
     * 添加文件
     * @param path
     * @return
     */
    public Map addFile(String path) ;


    /**
     * 文件重命名
     * @param path
     * @param oldName
     * @param newName
     * @return
     */
    public Map renameResouce(String path, String oldName, String newName) ;


    /**
     * 删除文件资源
     * @param paths
     * @return
     */
    public Map deleteResouce(String paths) ;

    /**
     * 读取文件内容
     * @param path
     * @param encoding
     * @return
     */
    public String readFile(String path, String encoding) throws Exception;

    /**
     * 写入文件
     * @param path
     * @param content
     */
    public void writeFile(String path, String content) throws Exception;
}
