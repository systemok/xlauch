package com.xlauch.web.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;

/**
 * 类描述    :  实体父类  <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : SuperEntity <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/27 17:18  <br/>
 * @version 0.1
 */
public class SuperEntity<T extends Model> extends Model<T> {

    /**
     * 主键ID , 这里故意演示注解可以无
     */
    private Long id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
