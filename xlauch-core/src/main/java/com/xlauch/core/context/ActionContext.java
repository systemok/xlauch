package com.xlauch.core.context;/**
 * Created by dell on 2017/11/10.
 */

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.utils.util.TextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述    : 请求上下文<br/>
 * 项目名称  : deve 项目<br/>
 * 类名称    : ActionContext.java <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/10 11:20  <br/>
 * @version 0.1
 */
public class ActionContext<T> {

    public static final String USER_ID = "USER_ID";

    /**
     * 线程变量
     */
    private static ThreadLocal<ActionContext> local = new ThreadLocal();

    /**
     * 请求
     */
    private HttpServletRequest request = null;

    /**
     * 响应
     */
    private HttpServletResponse response;

    /**
     * 参数
     */
    private Map<String, Object> dataMap;

    /**
     * 私有构造
     */
    private ActionContext() {
        this.dataMap = new HashMap<String, Object>();
    }


    /**
     * 获取上下文
     *
     * @return
     */
    public static ActionContext getContext() {
        ActionContext context = (ActionContext) local.get();
        if (context == null) {
            context = new ActionContext();
            local.set(context);
        }
        return context;
    }

    /**
     * 初始化上下文
     *
     * @param request
     * @param response
     */
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            if (request.getParameter(name) != null) {
                this.dataMap.put(name, request.getParameter(name));
            }
        }
    }

    /**
     * 设置参数值
     * @param key
     * @param value
     */
    public void setData(String key , Object value) {
        this.dataMap.put(key , value) ;
    }


    /**
     * 获取参数值
     * @param key
     * @return
     */
    public Object getParam(String key) {
        return getParam(key, null);
    }


    /**
     * 获取参数值
     * @param key
     * @return
     */
    public String getString(String key) {
        return request.getParameter(key);
    }

    /**
     * 获取参数值
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getParam(String key, Object defaultValue) {
        Object value = this.dataMap.get(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    /**
     * 销毁
     */
    public void destory() {
        local.remove();
    }


    /**
     * 获取当前页
     * @return
     */
    public int getCurrentPage() {
        int currentPage = 1 ;
        try {
            currentPage = TextUtil.getInt(request.getParameter("page")) ;
        }catch (Exception e) {
        }
        return currentPage ;
    }

    /**
     * 获取分页大小
     * @return
     */
    public int getPageSize() {
        int pageSize = 20 ;
        try {
            pageSize = TextUtil.getInt(request.getParameter("rows")) ;
        }catch (Exception e) {
        }
        return pageSize ;
    }

    /**
     * 获取偏移位置
     * @return
     */
    public int getOffset() {
        return (getCurrentPage() - 1) * getPageSize() ;
    }

    /**
     * 获取分页对象
     * @return
     */
    public Page<T> getPage() {
        return new Page<T>(getCurrentPage() , getPageSize()) ;
    }

    /**
     * 获取参数集合
     * @return
     */
    public Map<String , Object> getDataMap() {
        return this.dataMap ;
    }

    /**
     * 获取真实路径
     * @param path		路径
     * @return	String	真实路径
     */
    public String getRealPath(String path){
        return request.getSession().getServletContext().getRealPath(path);
    }

}
