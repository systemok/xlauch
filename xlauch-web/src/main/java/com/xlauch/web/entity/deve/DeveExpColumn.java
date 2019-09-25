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
 * 类描述    : 导出字段 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveExpColumn.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-11-15
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_exp_column")
public class DeveExpColumn extends Model<DeveExpColumn> {

    private static final long serialVersionUID = 1L;

	@TableId(value="column_id", type= IdType.AUTO)
	private Integer columnId;
    /**
     * 字段名
     */
	private String name;
    /**
     * 字段标题
     */
	private String title;
    /**
     * 字段宽度
     */
	private Integer width;
    /**
     * 导出表主键
     */
	@TableField("exp_id")
	private Integer expId;
    /**
     * 排序号
     */
	private Integer seq;
    /**
     * 对齐
     */
	private String align;
    /**
     * 是否复选框 1:是  0:否
     */
	private Integer checkable;
    /**
     * 是否排序 1:是  0:否
     */
	private Integer orderable;
    /**
     * 是否显示 1:是  0:否
     */
	private Integer showable;
    /**
     * 是否冻结 1:是  0:否
     */
	private Integer forzenable;
    /**
     * 字段格式化
     */
	private String formater;


	@Override
	protected Serializable pkVal() {
		return this.columnId;
	}

}
