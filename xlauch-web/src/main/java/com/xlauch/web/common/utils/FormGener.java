package com.xlauch.web.common.utils;

import com.xlauch.utils.util.db.DbColumn;
import com.xlauch.web.entity.deve.DeveObjectColumn;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 类描述： 表单生成器
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018/1/24
 */

public interface FormGener {

    /**
     * 生成表单
     * @param deveObjectColumn
     */
    public void generForm(DeveObjectColumn deveObjectColumn) ;

}
