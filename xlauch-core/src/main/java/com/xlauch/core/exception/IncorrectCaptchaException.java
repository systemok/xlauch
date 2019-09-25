package com.xlauch.core.exception;


import org.apache.shiro.authc.AuthenticationException;

/**
 * 类描述    : 此类继承 AuthenticationException，为的是在页面能更精准显示错误提示信息。   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : IncorrectCaptchaException <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:36  <br/>
 * @version 0.1
 */
public class IncorrectCaptchaException extends AuthenticationException {
    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}
