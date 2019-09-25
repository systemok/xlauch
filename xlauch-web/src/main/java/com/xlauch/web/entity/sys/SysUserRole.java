package com.xlauch.web.entity.sys;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    : 用户角色关联表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : SysUserRole.java <br/>
 * </p>
 * @author huangxy
 * @since 2017-11-24
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 角色ID
     */
	@TableField("role_id")
	private Long roleId;


	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
