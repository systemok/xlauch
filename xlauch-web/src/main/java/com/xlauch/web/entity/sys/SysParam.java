package com.xlauch.web.entity.sys;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : SysParam(系统参数表) 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : SysParam.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-11-24
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_param")
public class SysParam extends Model<SysParam> {

    private static final long serialVersionUID = 1L;

    @TableId("param_id")
	private int paramId;
    /**
     * 参数名称
     */
	private String name;
    /**
     * 备注
     */
	private String note;
    /**
     * 参数值
     */
	private String value;
    /**
     * 参数值-扩展1
     */
	private String ext1;
    /**
     * 参数值-扩展2
     */
	private String ext2;
    /**
     * 参数值-扩展1
     */
	private String ext3;
    /**
     * 参数类型
     */
	private String type;
    /**
     * 能否修改（0：不允许 1：允许）
     */
	private String editable;
    /**
     * 是否已伪删除（1：已删除，0：正常）（用于公司复制的数据，公司被删除时）
     */
	private Integer status;
    /**
     * 创建人员
     */
	private String createuser;
    /**
     * 创建日期
     */
	private Date createtime;
    /**
     * 修改人员
     */
	private String updateuser;
    /**
     * 修改日期
     */
	private Date updatetime;


	@Override
	protected Serializable pkVal() {
		return this.paramId;
	}

}
