package com.xlauch.web.entity.sys;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 类描述    : 角色权限表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : SysRolePermission.java <br/>
 * </p>
 * @author huangxy
 * @since 2017-11-23
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_role_permission")
public class SysRolePermission extends Model<SysRolePermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
	@TableField("role_id")
	private Long roleId;
    /**
     * 权限ID
     */
	@TableField("permission_id")
	private Long permissionId;


	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
