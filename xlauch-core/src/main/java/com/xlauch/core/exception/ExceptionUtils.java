package com.xlauch.core.exception;

import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.utils.util.FastJsonUtils;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * 类描述    : 异常工具类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ExceptionUtils.java <br/>
 * @author 伊凡  413916057@qq.com<br/>
 * 创建日期: 2017年5月4日 下午1:58:18  <br/>
 * @version 0.1
 */
public class ExceptionUtils {
	
	
	
	/**
	 * 判断是否是ajax 请求
	 * @Param ： request
	 * @Date   :  2017/4/28 16:12
	 * @return :
	 */
	public static boolean isAjax(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		String with = request.getHeader("X-Requested-With");
		
		return (accept != null && accept.contains("application/json")) ||
				(with != null && with.contains("XMLHttpRequest"));
	}
	
	
	/**
	 * 响应视图异常
	 * @param request
	 * @param e
	 * @return
	 */
	public static ModelAndView responseViewError(HttpServletRequest request , Exception e){
		String code = ErrorConstants.CODE_ERROR ;
		String msg = ResponseCode.getCodeMsg(code);
		String view = ErrorConstants.VIEW_ERROR;
		
		//判断是否是自定义异常
		if (e instanceof  BussException) {
			BussException ex = (BussException) e ;
			code = ex.getCode() ; 
			msg = ex.getMsg() ; 
			view = ex.getView() ; 
		}else if(e instanceof BussException){
			code = ((BussException) e).getCode() ;
			msg = ResponseCode.getCodeMsg(code);
		} else if(e instanceof BadSqlGrammarException){
			//10000 = 请检查SQL语句是否正确！
			code = "10000";
			msg = ResponseCode.getCodeMsg(code);
		} else if(e instanceof NullPointerException){
			//10005 = 空指针异常！
			code = "10005";
			msg = ResponseCode.getCodeMsg(code);
		}
		
		return responseViewError(request , code , msg , view);
	}
	
	/**
	 * 响应视图异常
	 * @param request
	 * @param code
	 * @param msg
	 * @param view
	 * @return
	 */
	public static ModelAndView responseViewError(HttpServletRequest request , String code , String msg , String view){
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", request.getRequestURL());
		mav.addObject("code", code);
		mav.addObject("msg", msg);
		mav.setViewName(view);
		return mav;
	}
	
	
	
	 
	/**
	 * 响应JSON异常
	 * @param response
	 * @param e
	 * @throws Exception
	 */
	public static void responseJsonError(HttpServletResponse response,  Exception e) throws Exception{
		String code = ErrorConstants.CODE_ERROR ;
		String msg = e.getMessage() ;
		
		//判断是否是自定义异常
		if (e instanceof  BussException) {
			BussException ex = (BussException) e ;
			code = ex.getCode() ; 
			msg = ex.getMsg() ; 
		} else if(e instanceof BussException){
			code = ((BussException) e).getCode() ;
		} else if(e instanceof BadSqlGrammarException){
			//10000 = 请检查SQL语句是否正确！
			code = "10000";
		} else if(e instanceof NullPointerException){
			//10005 = 空指针异常！
			code = "10005";
		}  
		
		responseJsonError(response ,code );
	}
	
	

	/**
	 * 响应JSON异常
	 * @param response
	 * @param code
	 * @throws IOException
	 */
	public static void responseJsonError(HttpServletResponse response, String code) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(getJsonErrorMsg(code));
		writer.flush();
	}

	
	/**
	 * 获取json格式错误信息
	 * @param code
	 * @return
	 */
	private static String getJsonErrorMsg(String code) {
		Map resMap = ResponseCode.writeFail(code);
        resMap.put("rows", new ArrayList());
        return FastJsonUtils.toJSONString(resMap);
        /*
        
		
		
		StringBuffer str = new StringBuffer();
		str.append("{");
		str.append("\"code\":").append(code).append(",");
		str.append("\"msg\":\"").append(msg).append("\",");
		str.append("\"rows\":[],");
		str.append("\"total\":").append(0).append("");
		str.append("}");
		return str.toString();*/
	}


	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Throwable ex) {
		if (ex instanceof RuntimeException) {
			return (RuntimeException) ex;
		} else {
			return new RuntimeException(ex);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable ex) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 获取组合本异常信息与底层异常信息的异常描述, 适用于本异常为统一包装异常类，底层异常才是根本原因的情况。
	 */
	public static String getErrorMessageWithNestedException(Throwable ex) {
		Throwable nestedException = ex.getCause();
		return new StringBuilder().append(ex.getMessage()).append(" nested exception is ")
				.append(nestedException.getClass().getName()).append(":").append(nestedException.getMessage())
				.toString();
	}

	/**
	 * 获取异常的Root Cause.
	 */
	public static Throwable getRootCause(Throwable ex) {
		Throwable cause;
		while ((cause = ex.getCause()) != null) {
			ex = cause;
		}
		return ex;
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
}
