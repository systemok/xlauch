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
 * 类描述    : 导出参数 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveExpParam.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-11-16
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_exp_param")
public class DeveExpParam extends Model<DeveExpParam> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="param_id", type= IdType.AUTO)
	private Integer paramId;
    /**
     * 导出模板主键
     */
	@TableField("exp_id")
	private Integer expId;
    /**
     * 参数名
     */
	private String name;
    /**
     * 参数标题
     */
	private String title;
    /**
     * 是否模糊查询 1：是 0：否
     */
	private Integer likeable;
    /**
     * 模糊查询类型  full 全模糊 left 左模糊 right 右模糊
     */
	@TableField("like_type")
	private String likeType;
    /**
     * 是否数据字典  1：是 0：否
     */
	private Integer dictable;
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
     * 表单内容
     */
	private String form;
    /**
     * 表单类型
     */
	@TableField("form_type")
	private String formType;
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
	@TableField("defalut_value")
	private String defalutValue;


	@Override
	protected Serializable pkVal() {
		return this.paramId;
	}

}
