package com.xlauch.web.entity.deve;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述    :  实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveObjectPriview.java <br/>
 * </p>
 * @author 伊凡
 * @since 2018-01-09
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_object_priview")
public class DeveObjectPriview extends Model<DeveObjectPriview> {

    private static final long serialVersionUID = 1L;

	private Integer id;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
