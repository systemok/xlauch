package com.xlauch.core.exception;

/**
 * 
 * 类描述 : 定义异常常量<br/>
 * 项目名称: xlauch 项目<br/>
 * 类名称 : ErrorConstants.java <br/>
 * 
 * @author 伊凡 413916057@qq.com<br/>
 *         创建日期: 2017年4月28日 下午4:53:51 <br/>
 * @version 0.1
 */
public class ErrorConstants {
	
	


	/**
	 * 500异常码
	 */
	public final static int INT_CODE_ERROR = 500;

	/**
	 * 404 异常码
	 */
	public final static int INT_CODE_NOTFOUND = 404;

	/**
	 * 403 异常码
	 */
	public final static int INT_CODE_FORBIDDEN = 403;
	
	
	

	/**
	 * 500异常码
	 */
	public final static String CODE_ERROR = "500";

	/**
	 * 404 异常码
	 */
	public final static String CODE_NOTFOUND = "404";

	/**
	 * 403 异常码
	 */
	public final static String CODE_FORBIDDEN = "403";

	/**
	 * 错误页面
	 */
	public final static String VIEW = "error";

	/**
	 * 500错误页面
	 */
	public final static String VIEW_ERROR = "common/error/error";

	/**
	 * 404错误页面
	 */
	public final static String VIEW_NOTFOUND = "common/error/error";

	/**
	 * 403错误页面
	 */
	public final static String VIEW_FORBIDDEN = "common/error/error";

	/**
	 * 404错误信息
	 */
	public final static String MSG_NOTFOUND = "无法找到该页面，请联系管理员！";

	/**
	 * 403错误信息
	 */
	public final static String MSG_FORBIDDEN = "您无权限访问该页面，请联系管理员";

	/**
	 * 500错误信息
	 */
	public final static String MSG_ERROR = "系统异常，请联系管理员";


}
