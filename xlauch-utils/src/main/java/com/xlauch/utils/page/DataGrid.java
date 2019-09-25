package com.xlauch.utils.page;

import com.xlauch.utils.util.TextUtil;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.*;

/**
 * 类描述    : 模拟easyUI对应的datagrid<br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DataGrid <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 9:52  <br/>
 * @version 0.1
 */
public class DataGrid implements Serializable{


    /**
     * 描述：总记录数
     */
    private long total=0 ;

    /**
     * 描述：返回的分页记录
     */
    private List rows = Collections.emptyList() ;

    /**
     * 描述：参数集合
     */
    private Map paramMap = Collections.emptyMap() ;

    /**
     * 描述：构造
     */
    public DataGrid(){
    }

    /**
     * 描述：构造
     */
    public DataGrid(Map paramMap){
        this.paramMap = paramMap;
    }

    /**
     * 描述：分页对象
     * @return
     */
    public RowBounds createRowBounds(){
        RowBounds rowBounds=new RowBounds(TextUtil.getInt(paramMap.get("_pageBegin")), TextUtil.getInt(paramMap.get("_pageSize")));
        return  rowBounds;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    public Object getParam(String key) {
        return paramMap.get(key);
    }

    public void setParam(String key, Object value) {
        this.paramMap.put(key, value);
    }

    public int getStart() {
        return TextUtil.getInt(paramMap.get("_pageBegin"));
    }

    public void setStart(int start) {
        paramMap.put("_pageBegin", start);
    }

    public int getLimit() {
        return TextUtil.getInt(paramMap.get("_pageSize"));
    }

    public void setLimit(int limit) {
        paramMap.put("_pageSize", limit);
    }

}
