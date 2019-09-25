package com.xlauch.web.config.beetl.func;

import com.xlauch.web.entity.sys.SysDict;
import com.xlauch.web.service.sys.ISysDictService;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述    : beetl 扩张数据字典 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : BeetlDictInitFun <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/25 16:07  <br/>
 * @version 0.1
 */
public class BeetlDictInitFun implements Function {

	private final static String SPLIT_CHAR = "," ;

	/**
	 * 系统字典表 服务类
	 */
	@Autowired
	private ISysDictService dictService ;


	/**
	 * 描述：函数方法调用
	 * @param params
	 * @param arg1
	 * @return
	 */
	@Override
	public Object call(Object[] params, Context arg1) {
		StringBuffer sbf = new StringBuffer();
		
		
		String types = (String) params[0];
		
		//判断参数非空
		if(types != null){
			//创建字典对象
			sbf.append("var _DICT = {}; \n");
			
			//按类型封装子对象
			for(String type : types.split(SPLIT_CHAR)) {
				//子对象
				sbf.append("_DICT[\""+type+"\"] = {}; \n");

				List<SysDict> dictList = dictService.selectSysDictByCodeCache(type);
				if(dictList != null) {
					for (SysDict sysDict : dictList) {
						sbf.append("_DICT[\""+type+"\"][\""+sysDict.getVal()+"\"] = \"" + sysDict.getName() + "\"; \n");
					}
				}
				//封装对象标签获取方法
				sbf.append("_DICT."+type.toUpperCase()+" = function (value,row,index) {");
				sbf.append("if(!_DICT[\"" + type + "\"][value]){return value ;} ");
				sbf.append("return _DICT[\""+type+"\"][value] ;} \n\n");
			}
		}
		
		
		return sbf.toString() ;
	}

}
