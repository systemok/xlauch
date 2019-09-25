package com.xlauch.web.entity.deve;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.xlauch.utils.util.db.DbColumn;
import com.xlauch.web.common.utils.DefaultFormGener;
import com.xlauch.web.common.utils.FormGener;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : 二次开发-模块管理--字段管理 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveObjectColumn.java <br/>
 * </p>
 * @author 伊凡
 * @since 2018-01-06
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_object_column")
public class DeveObjectColumn extends Model<DeveObjectColumn> {

    private static final long serialVersionUID = 1L;

	/**
	 * 可用
	 */
	private static final Integer ENABLE = 1 ;
	/**
	 * 不可用
	 */
	private static final Integer DISABLE = 0 ;


    /**
     * 字段id
     */
	@TableId(value="column_id", type= IdType.AUTO)
	private Integer columnId;
    /**
     * 方法id
     */
	@TableField("method_id")
	private Integer methodId;
    /**
     * 字段名
     */
	private String ename;
    /**
     * 字段标题
     */
	private String cname;
    /**
     * 字段显示宽度
     */
	private Integer width;
    /**
     * 字段显示高度
     */
	private Integer height;
    /**
     * 表单类型
     */
	@TableField("form_type")
	private String formType;
    /**
     * 表单字符
     */
	@TableField("form_str")
	private String formStr;
    /**
     * 默认值
     */
	@TableField("default_value")
	private String defaultValue;
    /**
     * 占用列数
     */
	private Integer cols;
    /**
     * 序列
     */
	private Integer seq;
    /**
     * 是否显示 1:是,0:否
     */
	@TableField("show_able")
	private Integer showAble;
    /**
     * 是否冻结
     */
	@TableField("frozen_able")
	private Integer frozenAble;
    /**
     * 是否排序 1：是 0：否
     */
	@TableField("order_able")
	private Integer orderAble;
    /**
     * 是否需要字典字段转换  1:是,0:否
     */
	@TableField("dict_able")
	private Integer dictAble;
    /**
     * 字典转换内容对照
     */
	@TableField("dict_str")
	private String dictStr;
    /**
     * 字段对齐方式
     */
	private String align;
    /**
     * 是否是复选框
     */
	@TableField("checkbox_able")
	private Integer checkboxAble;
    /**
     * 字段样式
     */
	private String css;
	/**
	 * UI校验器
	 */
	@TableField("uiCheck")
	private String uiCheck;
	/**
	 * 被验证为无效时提示
	 */
	@TableField("invalidMessage")
	private String invalidMessage;
    /**
     * 格式化
     */
	private String formatter;
    /**
     * 字段事件
     */
	private String enent;
	/**
	 * 是否主键
	 */
	@TableField(exist=false)
	private boolean isPk ;

	/**
	 * 构造方法
	 */
	public DeveObjectColumn(){
	}

	/**
	 * 构造方法
	 * @param methodId
	 * @param dbColumn
	 * @param seq
	 */
	public DeveObjectColumn(Integer methodId , DbColumn dbColumn , int seq) {
		this.methodId = methodId ;
		this.ename = dbColumn.getEname() ;
		this.cname = dbColumn.getCname() ;
		this.width = dbColumn.getLen() ;
		//this.width = (dbColumn.getLen() / 50 + 1 ) * 100 ;
		this.seq = seq ;
		this.showAble = ENABLE ;
		this.frozenAble = DISABLE ;
		this.orderAble = ENABLE;
		this.dictAble = DISABLE ;
		this.align = "center" ;
		this.checkboxAble = DISABLE ;
		if(dbColumn.isPk()){
			this.isPk = true ;
			this.checkboxAble = ENABLE ;
		}

		FormGener formGener = new DefaultFormGener();
		formGener.generForm(this);
	}

	@Override
	protected Serializable pkVal() {
		return this.columnId;
	}

}
