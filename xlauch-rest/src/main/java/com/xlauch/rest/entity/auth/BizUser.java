package com.xlauch.rest.entity.auth;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * 类描述    : 普通用户表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : BizUser.java <br/>
 * </p>
 * @author huangxy
 * @since 2018-01-06
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_biz_user")
public class BizUser extends Model<BizUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="user_id", type= IdType.AUTO)
	private Long userId;
    /**
     * 用户号码
     */
	private String username;
    /**
     * 手机号码
     */
	private String mobile;
    /**
     * 用户昵称
     */
	private String nickname;
    /**
     * 邮箱|登录帐号
     */
	private String email;
    /**
     * 密码
     */
	private String pswd;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 最后登录时间
     */
	@TableField("last_login_time")
	private Date lastLoginTime;
    /**
     * 1:有效，0:禁止登录
     */
	private Long status;


	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
