package com.xlauch.utils.util.db;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类描述 : 数据库字段
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018/1/6
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DbColumn {

    /**
     * 字段名
     */
    private String ename ;

    /**
     * 备注
     */
    private String cname ;

    /**
     * 长度
     */
    private int len ;

    /**
     * 是否主键
     */
    private boolean isPk ;
}
