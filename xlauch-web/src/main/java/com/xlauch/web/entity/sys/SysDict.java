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
 * 类描述    : 系统字典表 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : SysDict.java <br/>
 * </p>
 * @author huangxy
 * @since 2017-11-21
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_dict")
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="dictid", type= IdType.AUTO)
	private Integer dictid;
    /**
     * 字典名
     */
	private String name;
    /**
     * 字典值
     */
	private String val;
    /**
     * 字典编码
     */
	private String code;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 扩展值1
     */
	private String ext1;
    /**
     * 扩展值2
     */
	private String ext2;
    /**
     * 扩展值3
     */
	private String ext3;


	@Override
	protected Serializable pkVal() {
		return this.dictid;
	}

}
