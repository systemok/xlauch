package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveExpPriview;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述:  服务类
 * </p>
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
public interface IDeveExpPriviewService extends SuperService<DeveExpPriview> {

    /**
     * 列表分页查询
     * @return
     */
    public DataGrid selectDeveExpPriviewList();


    /**
     * 根据导出ID 获取预览对象
     * @param expId
     * @param isDebug
     * @return
     */
    public DeveExpPriview getDeveExpPriview(int expId , boolean isDebug);


    /**
     * 根据导出ID 获取DataGrid格式数据
     * @param expId
     * @param isDebug
     * @return
     */
    public DataGrid selectDataGrid(int expId , boolean isDebug);


    /**
     * 导出数据
     * @param expId
     * @param fileType
     * @return
     * @throws Exception
     */
    public String exportData(int expId , String fileType) throws Exception;

    /**
     * 导出对象
     * @param idList
     * @return
     * @throws Exception
     */
    public String exportObject(List<Integer> idList) throws Exception;

    /**
     * 导入对象
     * @param filePath
     * @throws Exception
     */
    public void importObject(String filePath)  throws Exception;


    /**
     * 生成页面
     * @param expId
     * @param templatePath
     * @param outPath
     * @return
     */
    public Map generPage(int expId , String templatePath , String outPath) ;

}
