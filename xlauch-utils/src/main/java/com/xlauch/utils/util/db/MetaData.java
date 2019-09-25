package com.xlauch.utils.util.db;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 类描述 : 数据库元数据
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018/1/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型：表或视图
     */
    private String type;

    /**
     * 主键名称
     */
    private String pkName;

    /**
     * 备注
     */
    private String comment;

}
