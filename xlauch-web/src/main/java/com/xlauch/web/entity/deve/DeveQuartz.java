package com.xlauch.web.entity.deve;

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
 * 类描述    : 定时任务 实体类 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : DeveQuartz.java <br/>
 * </p>
 * @author 伊凡
 * @since 2017-12-12
 * @version 0.1
 */
@Data
@Accessors(chain = true)
@TableName("t_deve_quartz")
public class DeveQuartz extends Model<DeveQuartz> {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
	@TableField("job_name")
	private String jobName;
    /**
     * 任务分组名
     */
	@TableField("job_group")
	private String jobGroup;
    /**
     * 触发器名称
     */
	@TableField("tri_name")
	private String triName;
    /**
     * 触发器分组
     */
	@TableField("tri_group")
	private String triGroup;
    /**
     * 开始时间
     */
	@TableField("begin_time")
	private String beginTime;
    /**
     * 结束时间
     */
	@TableField("end_time")
	private String endTime;
    /**
     * 下次执行时间
     */
	@TableField("next_fire_time")
	private String nextFireTime;
    /**
     * 轮循表达式
     */
	private String cronExpression;
    /**
     * 执行类
     */
	@TableField("class_name")
	private String className;
    /**
     * 状态
     */
	private Integer state;
    /**
     * 备注
     */
	private String desc;


	@Override
	protected Serializable pkVal() {
		return this.jobName;
	}

}
