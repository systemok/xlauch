package com.xlauch.core.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 类描述    : 无效权限异常   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DisabledRoleException <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:35  <br/>
 * @version 0.1
 */
public class DisabledRoleException extends AuthenticationException {

    public DisabledRoleException() {
        super();
    }

    public DisabledRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisabledRoleException(String message) {
        super(message);
    }

    public DisabledRoleException(Throwable cause) {
        super(cause);
    }

}
