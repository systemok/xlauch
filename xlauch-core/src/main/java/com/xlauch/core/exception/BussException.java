package com.xlauch.core.exception;


import com.xlauch.core.config.errorcodemsg.ResponseCode;

/**
 * 
 * 类描述    : 自定义异常类，一般业务异常<br/>
 * 项目名称: xlauch 项目<br/>
 * 类名称    : BussException.java <br/>
 * @author 伊凡  413916057@qq.com<br/>
 * 创建日期: 2017年5月2日 上午9:06:13  <br/>
 * @version 0.1
 */
public class BussException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6578114193903129086L;
	
	

	public BussException(){
		super();
	}
	
	/**
	 * 构造
	 * @param code
	 */
	public BussException(String code) {
		super();
		this.code = code;
		this.msg = ResponseCode.getCodeMsg(code);
		this.view = ErrorConstants.VIEW_ERROR ;
	}
	

	/**
	 * 构造
	 * @param code
	 * @param view
	 */
	public BussException(String code,  String view) {
		super();
		this.code = code;
		this.msg = ResponseCode.getCodeMsg(code);
		this.view = view;
	}


	private String code;
	
	private String msg ; 

	private String view ; 

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the view
	 */
	public String getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(String view) {
		this.view = view;
	}
	

}