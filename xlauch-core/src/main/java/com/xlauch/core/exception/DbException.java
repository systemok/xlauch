package com.xlauch.core.exception;

/**
 * 类描述    : 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DbException <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:34  <br/>
 * @version 0.1
 */
public class DbException extends RuntimeException {

	private static final long serialVersionUID = 3583566093089790852L;

	public DbException() {
		super();
	}

	public DbException(String message) {
		super(message);
	}

	public DbException(Throwable cause) {
		super(cause);
	}

	public DbException(String message, Throwable cause) {
		super(message, cause);
	}
}
