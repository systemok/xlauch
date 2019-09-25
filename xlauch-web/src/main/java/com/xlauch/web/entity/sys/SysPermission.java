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
 * 权限表
 * </p>
 *
 * @author huangxy
 * @since 2017-11-09
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_permission")
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="permission_id", type= IdType.AUTO)
	private Long permissionId;
	/**
	 * 父节点
	 */
	private Long parentPid;
	/**
	 * 权限编码
	 */
	private String permissionCode;
    /**
     * url地址
     */
	private String url;
    /**
     * url描述
     */
	private String name;
    /**
     * 图标
     */
	private String icon;
	/**
	 * 排序
	 */
	private String sortOrder;
    /**
     * 状态
     */
	private Integer status;
	/**
	 * 权限类型1:菜单，2按钮
	 */
	private Integer permissionType;


	@Override
	protected Serializable pkVal() {
		return this.permissionId;
	}

}
