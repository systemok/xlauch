package com.xlauch.core.exception;

import com.xlauch.core.config.errorcodemsg.ResponseCode;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 类描述    : 错误统一controller<br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : GlobalErrorController.java <br/>
 * @author 伊凡  413916057@qq.com<br/>
 * 创建日期: 2017年5月2日 上午9:06:29  <br/>
 * @version 0.1
 */
@Controller
public class GlobalErrorController implements ErrorController {

	private static final String ERROR_PATH = "/" + ErrorConstants.VIEW;

	
	/**
	 * 异常测试
	 * @return
	 */
	@RequestMapping("/error_test")
	public String errorTest() {
		return "error_test";
	}

	/**
	 * 异常测试
	 * @return
	 */
	@RequestMapping("/ex")
	public String toException() throws Exception {
		if (true) {
			throw new BussException("1234");
		}
		return "common/login";
	}

	/**
	 * 处理404错误
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(ERROR_PATH)
	public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setCharacterEncoding("utf-8");
		//ajax请求时，返回json格式
		if(ExceptionUtils.isAjax(request)){
			response.setStatus(404);
			response.getWriter().write(ResponseCode.writeFailString("404"));
			
			//ExceptionUtils.responseJsonError(response, ErrorConstants.CODE_NOTFOUND , ErrorConstants.MSG_NOTFOUND);
			return null;
		} 
		
		return ExceptionUtils.responseViewError(request, ErrorConstants.CODE_NOTFOUND , 
				ResponseCode.getCodeMsg("404") ,ErrorConstants.VIEW_NOTFOUND);
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
