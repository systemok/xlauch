package com.xlauch.web.entity.sys;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : 角色表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : SysRole.java <br/>
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
	@TableId(value="role_id", type= IdType.AUTO)
	private Long roleId;
    /**
     * 角色名称
     */
	private String name;
    /**
     * 角色状态
     */
	private Integer status;


	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
