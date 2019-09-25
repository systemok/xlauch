package com.xlauch.core.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * 类描述    : 统一异常处理配置<br/>
 * 项目名称	: xlauch 项目<br/>
 * 类名称    : GlobalExceptionConfig.java <br/>
 * @author 伊凡  413916057@qq.com<br/>
 * 创建日期: 2017年5月2日 上午9:06:40  <br/>
 * @version 0.1
 */
@ControllerAdvice
public class GlobalExceptionConfig {

	private static final Logger log = Logger.getLogger(GlobalExceptionConfig.class);

	/**
	 * 返回异常页面，处理自定义的业务异常
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler({BussException.class , BussException.class ,Exception.class})
	public ModelAndView viewErrorHandler(HttpServletRequest request, Exception e , HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		log.error(ExceptionUtils.getStackTraceAsString(e));

		//ajax请求时，返回json格式
		if(ExceptionUtils.isAjax(request)){
			response.setStatus(ErrorConstants.INT_CODE_ERROR);
			ExceptionUtils.responseJsonError(response, e);
			return null;
		}

		return ExceptionUtils.responseViewError(request, e);
	}


}
