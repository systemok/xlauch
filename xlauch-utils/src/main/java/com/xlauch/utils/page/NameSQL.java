package com.xlauch.utils.page;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/13
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述    : 命名SQL <br/>
 * 项目名称  : deve 项目<br/>
 * 类名称    : NameSQL.java <br/>
 * @author 伊凡  413916057@qq.com<br/>
 * 创建日期: 2017/12/13 16:00  <br/>
 * @version 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameSQL implements Serializable {

    /**
     * 查询的SQL
     */
    private String sql ;

    /**
     * 参数
     */
    private List<Object> params ;

}
