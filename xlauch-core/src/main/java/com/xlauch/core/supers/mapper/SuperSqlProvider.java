package com.xlauch.core.supers.mapper;

import java.util.Map;

/**
 * 通用CRUD SQL拼装类
 *
 */
public class SuperSqlProvider {

    /**
     * 根据传入sql及参数查询
     * @param parameter
     * @return
     */
    public String selectByConditionWithSql(Map<String, Object> parameter) {
        String sql = String.valueOf(parameter.get("sql"));
        return sql;
    }


}
