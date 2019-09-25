package com.xlauch.core.supers.web;

import com.xlauch.core.context.ActionContext;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述    : <br/>
 * 项目名称  : deve 项目<br/>
 * 类名称    : BaseController.java <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/10 11:41  <br/>
 * @version 0.1
 */
public class BaseController {

    /**
     * 获取参数值
     *
     * @param key
     * @return
     */
    public Object getParam(String key) {
        return getParam(key, null);
    }

    /**
     * 获取参数值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getParam(String key, Object defaultValue) {
        ActionContext actionContext = ActionContext.getContext();
        return actionContext.getParam(key, defaultValue);
    }

    /**
     * 获取删除Id集合
     *
     * @param ids
     * @return
     */
    public List getIdList(String ids) {
        List<Integer> idList = new ArrayList<>();
        for (String s : ids.split(",")) {
            idList.add(Integer.parseInt(s));
        }

        return idList;
    }

    /**
     * 获取真实路径
     *
     * @param path 路径
     * @return String    真实路径
     */
    public String getRealPath(String path) {
        ActionContext actionContext = ActionContext.getContext();
        return actionContext.getRealPath(path);
    }



    /**
     * 获取真实路径
     *
     * @param path
     * @return
     */
    public String getRealPathByClass(String path) {
        String projectPath = null ;
        //windows下
        if ("\\".equals(File.separator)) {
            projectPath = this.getClass().getClassLoader().getResource("/").getPath().substring(1);
        }else {
            projectPath = this.getClass().getClassLoader().getResource("/").getPath();
        }
        projectPath += path ;
        return projectPath ;
    }

}
