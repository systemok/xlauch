package com.xlauch.web.config.beetl.func;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述    : 扩展beetl函数，自定义获取数据字典标签<br/> <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : BeetlDictFun <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/25 16:06  <br/>
 * @version 0.1
 */
public class BeetlDictFun implements Function {

	private static Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();

	static {
		Map<String, String> sexMap = new HashMap<String, String>();
		sexMap.put("1", "男");
		sexMap.put("0", "女");

		dictMap.put("sex", sexMap);
	}

	/**
	 * 描述：函数方法调用
	 * @param params
	 * @param arg1
	 * @return
	 */
	@Override
	public Object call(Object[] params, Context arg1) {
		String type = (String) params[0];
		String key = (String) params[1];
		return dictMap.get(type).get(key);
	}

}
