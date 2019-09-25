package com.xlauch.utils.util;

import java.io.Reader;
import java.sql.Clob;

/**
 * <p>
 * 	类描述:用于对项目中大字段进行处理，转换为字符串
 * </p>
 *
 * @author 伊凡
 * @since 2017/11/20
 * @version 0.1
 */ 
 
public final class ClobUtils {

	/**
	 * 转换大字段为字符串数据
	 * @param clob		大字段
	 * @return String	大字段内容
	 */
	public static String ClobToString(Clob clob) {
		if (clob == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			Reader is = clob.getCharacterStream();
			int i = 0;
			char[] cArr = new char[1000];
			i = is.read(cArr);
			while (i != -1) {
				sb.append(cArr, 0, i);
				i = is.read(cArr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
	
	/**
	 * 转换大字段为字符串数据
	 * @param reader	输入流
	 * @return String	大字段内容
	 */
	public static String ClobToString(Reader reader) {
		if (reader == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			int i = 0;
			char[] cArr = new char[1000];
			i = reader.read(cArr);
			while (i != -1) {
				sb.append(cArr, 0, i);
				i = reader.read(cArr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	
}
