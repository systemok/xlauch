package com.xlauch.web.service.deve.impl;


import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.NameSQL;
import com.xlauch.utils.util.date.DateUtil;
import com.xlauch.web.config.shiro.Users;
import com.xlauch.web.entity.deve.DeveObject;
import com.xlauch.web.entity.deve.DeveObjectColumn;
import com.xlauch.web.entity.deve.DeveObjectMethod;
import com.xlauch.web.entity.deve.DeveObjectParam;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018/1/31
 */
public class NameSQLBuilder {


    /**
     * 创建NameSQL
     *
     * @param deveObjectMethod
     * @param deveObject
     * @return
     */
    public static NameSQL buildNameSQL(DeveObjectMethod deveObjectMethod, DeveObject deveObject) {
        String methodType = ObjectUtils.defaultIfNull(deveObjectMethod.getMethodType(), "QUERY").toUpperCase();
        if ("QUERY".equals(methodType) || "LIST".equals(methodType)) {
            return createNameSQLForQuery(deveObjectMethod);
        }
        return createNameSQLForUpdate(deveObjectMethod, deveObject);
    }


    /**
     * 创建查询类型命名SQL
     *
     * @param deveObjectMethod
     * @return
     */
    private static NameSQL createNameSQLForQuery(DeveObjectMethod deveObjectMethod) {
        ActionContext actionContext = ActionContext.getContext();
        String sql = deveObjectMethod.getViewSql();
        List<Object> params = new ArrayList<>();

        List<DeveObjectParam> paramList = deveObjectMethod.getParamList();
        if (paramList != null) {
            for (DeveObjectParam deveObjectParam : paramList) {
                // 参数名
                String paramName = deveObjectParam.getEname();
                // 是否模糊查询
                boolean isLike = ObjectUtils.defaultIfNull(deveObjectParam.getLikeAble(), 0) == 1;
                // 模糊查询方式
                String likeType = deveObjectParam.getLikeType();
                // 参数值
                Object paramValue = actionContext.getParam(paramName);

                // 参数值为空
                String fullValue = String.valueOf(paramValue);
                if (paramValue == null || StringUtils.isEmpty(String.valueOf(paramValue))) {
                    sql = sql.replace("#{" + paramName + "}", "");
                    sql = sql.replace("${" + paramName + "}", "");
                } else {
                    String paramSql = deveObjectParam.getSqlx();
                    sql = sql.replace("#{" + paramName + "}", paramSql);
                    sql = sql.replace("${" + paramName + "}", fullValue);

                    // 如果是模糊查找，需要重新拼装参数值，并设值回dataGrid
                    if (isLike) {
                        if ("full".equalsIgnoreCase(likeType)) {
                            fullValue = "%" + fullValue.toUpperCase() + "%";
                        } else if ("left".equalsIgnoreCase(likeType)) {
                            fullValue = "%" + fullValue.toUpperCase();
                        } else if ("right".equalsIgnoreCase(likeType)) {
                            fullValue = fullValue.toUpperCase() + "%";
                        }
                    }
                    params.add(fullValue);
                }
            }
        }

        return new NameSQL(sql, params);
    }


    /**
     * 创建更新类型命名SQL
     *
     * @param deveObjectMethod
     * @param deveObject
     * @return
     */
    public static NameSQL createNameSQLForUpdate(DeveObjectMethod deveObjectMethod, DeveObject deveObject) {
        ActionContext<String> actionContext = ActionContext.getContext();
        String sql = deveObjectMethod.getViewSql();
        List<Object> params = new ArrayList<>();

        String pkName = ObjectUtils.defaultIfNull(deveObject.getPkName(), "");
        String methodType = deveObjectMethod.getMethodType().toUpperCase();
        List<DeveObjectColumn> columnList = deveObjectMethod.getColumnList();
        //新增
        if ("REMOVE".equals(methodType) || "DELETE".equals(methodType)) {
            String ids = actionContext.getString("ids");
            //29000: 删除主键值不允许为空
            if (StringUtils.isEmpty(ids)) {
                ResponseCode.bussException("29000");
            }
            params.addAll(Arrays.asList(ids.split(",")));

            StringBuffer sbf = new StringBuffer() ;
            sbf.append("delete from ").append(deveObject.getDbName()).append(" where ").append(deveObject.getPkName());
            sbf.append(" in (").append(params.stream().map(s -> "?").collect(Collectors.joining(","))).append(")");

            sql = sbf.toString();
        } else {
            Object pkValue = null ;
            for (DeveObjectColumn deveObjectColumn : columnList) {
                // 获取表单值或者生成预设值
                Object value = actionContext.getParam(deveObjectColumn.getEname());
                boolean isEditAndPk = pkName.equals(deveObjectColumn.getEname()) && ("EDIT".equals(methodType) || "UPDATE".equals(methodType)) ;

                // 1、隐藏域 2、默认值不为空 3、修改方法时，主键不自动生成
                if ("hidden".equals(deveObjectColumn.getFormType()) && StringUtils.isNotEmpty(deveObjectColumn.getDefaultValue()) && !isEditAndPk) {
                    value = generDefaultValue(deveObjectColumn.getDefaultValue()) ;
                }

                // 如果是修改，主键值，最后添加
                if (isEditAndPk) {
                    pkValue = value ;
                }else {
                    params.add(value);
                }
            }

            // 如果是修改，主键值，最后添加
            if ("EDIT".equals(methodType) || "UPDATE".equals(methodType)){
                params.add(pkValue);
            }
        }


        return new NameSQL(sql, params);
    }


    /**
     * 生成默认值
     *
     * @param defaultVal
     * @return
     */
    private static Object generDefaultValue(String defaultVal) {
        if ("USERID".equals(defaultVal)) {
            return Users.getCurrentUser().getUserId();
        } else if ("USERNAME".equals(defaultVal)) {
            return Users.getCurrentUser().getUserName();
        } else if ("NOW".equals(defaultVal)) {
            return DateUtil.getTime();
        } else if ("DATE".equals(defaultVal)) {
            return DateUtil.getDate();
        } else if ("PK".equals(defaultVal)) {
            return null;
        } else if ("UUID".equals(defaultVal)) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }

        return defaultVal;
    }

}
