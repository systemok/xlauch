package com.xlauch.web.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 类描述    : 从Shiro中获取当前用户相关信息   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : Users <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:40  <br/>
 * @version 0.1
 */
public class Users {

    public static ShiroUser getCurrentUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentHost() {
        return SecurityUtils.getSubject().getSession().getHost();
    }

    public static Subject getSecurity() {
        return SecurityUtils.getSubject();
    }

}
