package com.xlauch.web.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 类描述    : 自定义用户信息   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ShiroUserNamePassToken <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:40  <br/>
 * @version 0.1
 */
public class ShiroUserNamePassToken extends UsernamePasswordToken {
    private String captcha;

    public ShiroUserNamePassToken(){
        super();
    }

    public ShiroUserNamePassToken(String username, String password, boolean rememberMe, String host, String captcha){
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
