package com.xlauch.core.config.argument;

import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.util.TextUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述    :  DataGrid参数绑定  <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DataGridArgumentResolver <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/8 14:53  <br/>
 * @version 0.1
 */
public class DataGridArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().equals(DataGrid.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class) ;


        //迭代参数集合
        Map paramMap = new HashMap() ;
        Enumeration<String> pNames = request.getParameterNames();
        while(pNames.hasMoreElements()){
            String name = pNames.nextElement();
            if(null != request.getParameter(name)) {
                paramMap.put(name, request.getParameter(name));
            }
        }

        //处理分页信息
        //当前页面
        int currentPage = TextUtil.getInt(request.getParameter("page")) ;
        //每页条数
        int pageSize = TextUtil.getInt(request.getParameter("rows")) ;
        //起始行
        int pageBegin = (currentPage -1 ) * pageSize ;
        //结束行
        int pageEnd = currentPage * pageSize ;

        paramMap.put("_pageSize" , pageSize) ;
        paramMap.put("_pageBegin" , pageBegin) ;
        paramMap.put("_pageEnd" , pageEnd) ;

        DataGrid dataGrid = new DataGrid();
        dataGrid.setParamMap(paramMap);
        return dataGrid;
    }

}
