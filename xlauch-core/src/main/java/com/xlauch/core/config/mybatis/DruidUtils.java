package com.xlauch.core.config.mybatis;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.sql.PagerUtils;

/**
 * 类描述    : druid 工具类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DruidUtils <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/24 11:24  <br/>
 * @version 0.1
 */
public class DruidUtils {

	
	/**
	 * 密码加密
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encryptPassword(String password) throws Exception {
		return ConfigTools.encrypt( password);
	}
	
	
	/**
	 * 密码解密
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String decryptPassword(String password) throws Exception {
		return ConfigTools.decrypt(password) ;
	}
	
	
	/**
	 * 获取查询总记录数SQL
	 * @param sql
	 * @param dbType
	 * @return
	 */
	public static String getCountSQL(String sql , String dbType) {
		return PagerUtils.count(sql, dbType);
	}



	public static void main(String[] args) throws Exception {
		System.out.println(encryptPassword("root"));
		System.out.println(encryptPassword("Mysql*bg*user"));
	}

}
