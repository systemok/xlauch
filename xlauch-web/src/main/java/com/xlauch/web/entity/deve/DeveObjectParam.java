package com.xlauch.web.entity.deve;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : 二次开发-导出参数管理 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveObjectParam.java <br/>
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_object_param")
public class DeveObjectParam extends Model<DeveObjectParam> {

    private static final long serialVersionUID = 1L;

	@TableId(value="param_id", type= IdType.AUTO)
	private Integer paramId;
    /**
     * 方法主键
     */
	@TableField("method_id")
	private Integer methodId;
    /**
     * 参数名
     */
	private String ename;
    /**
     * 参数标题
     */
	private String cname;
    /**
     * 是否模糊查询
     */
	@TableField("like_able")
	private Integer likeAble;
    /**
     * 模糊查询类型  full 全模糊 left 左模糊 right 右模糊
     */
	@TableField("like_type")
	private String likeType;
    /**
     * 是否数据字典
     */
	@TableField("dict_able")
	private Integer dictAble;
    /**
     * 数据字典类型
     */
	@TableField("dict_type")
	private String dictType;
    /**
     * 显示宽度
     */
	private Integer width;
    /**
     * 触发事件
     */
	private String event;
    /**
     * 表单类型
     */
	@TableField("form_type")
	private String formType;
    /**
     * 表单内容
     */
	@TableField("form_str")
	private String formStr;
    /**
     * 排序号
     */
	private Integer seq;
    /**
     * 参数查询语句
     */
	private String sqlx;
    /**
     * 默认值
     */
	@TableField("default_value")
	private String defaultValue;


	@Override
	protected Serializable pkVal() {
		return this.paramId;
	}

}
