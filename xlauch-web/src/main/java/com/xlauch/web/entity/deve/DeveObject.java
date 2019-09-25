package com.xlauch.web.entity.deve;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : 二次开发-对象表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveObject.java <br/>
 * </p>
 * @author 伊凡
 * @since 2018-01-23
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_object")
public class DeveObject extends Model<DeveObject> {

    private static final long serialVersionUID = 1L;

    /**
     * 对象主键
     */
	@TableId(value="obj_id", type= IdType.AUTO)
	private Integer objId;
    /**
     * 对象编码
     */
	private String code;
    /**
     * 对象名称
     */
	private String name;
    /**
     * 保存数据主表或查询数据视图
     */
	@TableField("db_name")
	private String dbName;
    /**
     * 类型  TABLE:表 VIEW:视图
     */
	private String type;
    /**
     * 主键
     */
	@TableField("pk_name")
	private String pkName;
    /**
     * 数据源
     */
	@TableField("data_source")
	private String dataSource;
    /**
     * 创建用户
     */
	@TableField("add_user")
	private Integer addUser;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 修改人员
     */
	@TableField("update_user")
	private Integer updateUser;
	/**
	 * 状态 1：可用 0：不可用
	 */
	private int status ;
    /**
     * 添加时间
     */
	@TableField("add_time")
	private Date addTime;
	/**
	 * 方法列表
	 */
	@TableField(exist=false)
	private List<DeveObjectMethod> methodList ;
	/**
	 * 默认方法
	 */
	@TableField(exist=false)
	private DeveObjectMethod defaultMethod ;

	@Override
	protected Serializable pkVal() {
		return this.objId;
	}

}
