package com.xlauch.utils.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletResponse;

/**
 * 类描述    : 文件下载工具类<br/>
 * 项目名称: jeeframe_2.0  <br/>
 * 类名称    : FileDownLoadUtil.java <br/>
 * @author 伊凡  413916057@qq.com<br/>
 * 创建日期: 2016年3月21日 上午11:36:30  <br/>
 * @version 2.0
 */
public class FileDownLoadUtil {
	

	/**
	 * EXCEL
	 */
	public final static String EXCEL = "EXCEL";						

	/**
	 * WORD
	 */
	public final static String WORD = "WORD" ;						

	/**
	 * TXT
	 */
	public final static String TXT = "TXT" ;						

	/**
	 * CAL
	 */
	public final static String CAL = "CAL" ;						

	/**
	 * PDF
	 */
	public final static String PDF = "PDF" ;						

	/**
	 * DBF
	 */
	public final static String DBF = "DBF" ;						
	 
	
	/**
	 * 文件下载
	 * @param response
	 * @param encoding
	 * @param filePath
	 * @throws Exception
	 */
	public static void download(HttpServletResponse response , String encoding ,String filePath) throws Exception {
		response.reset();

		File file = new File(filePath); // 根据文件路径获得File文件
		if(!file.exists()){
			throw new FileNotFoundException(filePath) ;
		}
		
		String fileName = file.getName() ;
		String fileType = FileUtil.getPostFix(fileName);
		
		// 设置文件类型(这样设置就不止是下Excel文件了，一举多得)
		if (PDF.equals(fileType)) {
			response.setContentType("application/pdf;charset=" + encoding);
		} else if (EXCEL.equals(fileType)) {
			response.setContentType("application/msexcel;charset=" + encoding);
		} else if (WORD.equals(fileType)) {
			response.setContentType("application/msword;charset=" + encoding);
		}else if (TXT.equals(fileType)) {
			response.setContentType("text/plain;charset=" + encoding);
		}else {
			response.setContentType("multipart/form-data");  
		}
		// 文件名
		//fileName = URLEncoder.encode(fileName, encoding);  
		fileName = new String(fileName.getBytes(encoding), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ fileName + "\"");
		response.setContentLength((int) file.length());
		byte[] buffer = new byte[4096];// 缓冲区
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			// 遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); // 不可少
			response.flushBuffer();// 不可少
		} catch (Exception e) {
			e.printStackTrace();
			// 异常自己捕捉
		} finally {
			// 关闭流，不可少
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}
	
		
	/**
	 * 文件下载
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void download(HttpServletResponse response , String encoding ,String fileType ,String filePath, String fileName) throws Exception {
		response.reset();

		File file = new File(filePath); // 根据文件路径获得File文件
		// 设置文件类型(这样设置就不止是下Excel文件了，一举多得)
		if (PDF.equals(fileType)) {
			response.setContentType("application/pdf;charset=" + encoding);
		} else if (EXCEL.equals(fileType)) {
			response.setContentType("application/msexcel;charset=" + encoding);
		} else if (WORD.equals(fileType)) {
			response.setContentType("application/msword;charset=" + encoding);
		}else if (TXT.equals(fileType)) {
			response.setContentType("text/plain;charset=" + encoding);
		}else {
			response.setContentType("multipart/form-data");  
		}
		// 文件名
		//fileName = URLEncoder.encode(fileName, encoding);  
		fileName = new String(fileName.getBytes(encoding), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ fileName + "\"");
		response.setContentLength((int) file.length());
		byte[] buffer = new byte[4096];// 缓冲区
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			// 遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); // 不可少
			response.flushBuffer();// 不可少
		} catch (Exception e) {
			e.printStackTrace();
			// 异常自己捕捉
		} finally {
			// 关闭流，不可少
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}
	

	/**
	 * 文件下载
	 * @param response
	 * @param filePath
	 * @throws Exception
	 */
	public static void download(HttpServletResponse response ,String filePath) throws Exception {
		download(response, "GBK", filePath);
	} 
}
