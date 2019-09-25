package com.xlauch.web.entity.deve;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    :  实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveExpPriview.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
@Data
@Accessors(chain = true)
public class DeveExpPriview  {

    private static final long serialVersionUID = 1L;

	/**
	 * 导出实体
	 */
	private DeveExp deveExp ;

	/**
	 * 导出 -- 参数列表
	 */
	private List<DeveExpParam> paramList ;

	/**
	 * 导出 -- 字段列表
	 */
	private List<DeveExpColumn> columnList ;

	/**
	 * 字段别名映射对照
	 */
	private Map<String , String> alias ;
}
