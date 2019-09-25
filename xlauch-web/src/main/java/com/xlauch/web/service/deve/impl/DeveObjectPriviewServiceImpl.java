package com.xlauch.web.service.deve.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.config.mybatis.DataSourceConfig;
import com.xlauch.core.config.mybatis.DbContextHolder;
import com.xlauch.core.context.ActionContext;
import com.xlauch.core.supers.service.SuperServiceImpl;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.page.NameSQL;
import com.xlauch.utils.util.date.DateUtil;
import com.xlauch.web.config.shiro.Users;
import com.xlauch.web.entity.deve.*;
import com.xlauch.web.mapper.deve.DeveObjectPriviewMapper;
import com.xlauch.web.service.deve.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 类描述:  服务实现类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018-01-09
 */
@Service
public class DeveObjectPriviewServiceImpl extends SuperServiceImpl<DeveObjectPriviewMapper, DeveObjectPriview> implements IDeveObjectPriviewService {

    /**
     * 预览Mapper 接口
     */
    @Autowired
    private DeveObjectPriviewMapper deveObjectPriviewMapper;

    /**
     * 对象表 服务类
     */
    @Autowired
    private IDeveObjectService deveObjectService;

    /**
     * 对象方法 服务类
     */
    @Autowired
    private IDeveObjectMethodService deveObjectMethodService;

    /**
     * 对象方法字段 服务类
     */
    @Autowired
    private IDeveObjectColumnService deveObjectColumnService;

    /**
     * 对象方法参数 服务类
     */
    @Autowired
    private IDeveObjectParamService deveObjectParamService;

    @Override
    public DeveObject loadObjectByCode(String code) {

        /**
         * 获取对象
         */
        DeveObject deveObject = deveObjectService.selectByCode(code);
        if (deveObject == null) {
            //29120 = 对象不存在，无法预览！
            ResponseCode.bussException("29120");
        } else if (deveObject.getStatus() == 0) {
            //29121 = 对象处于禁用状态，无法预览！
            ResponseCode.bussException("29121");
        }

        int objId = deveObject.getObjId();

        /**
         * 获取方法列表
         */
        List<DeveObjectColumn> columnList = deveObjectColumnService.selectByObjectId(objId);
        Map<Integer, List<DeveObjectColumn>> columnMap = new HashMap<Integer, List<DeveObjectColumn>>();
        if (ObjectUtils.allNotNull(columnList)) {
            columnMap = columnList.stream().collect(Collectors.groupingBy(DeveObjectColumn::getMethodId));
        }

        /**
         * 获取参数列表
         */
        List<DeveObjectParam> paramList = deveObjectParamService.selectByObjectId(objId);
        Map<Integer, List<DeveObjectParam>> paramMap = new HashMap<Integer, List<DeveObjectParam>>();
        if (ObjectUtils.allNotNull(paramList)) {
            paramMap = paramList.stream().collect(Collectors.groupingBy(DeveObjectParam::getMethodId));
        }

        /**
         * 获取方法列表
         */
        EntityWrapper<DeveObjectMethod> ewMethod = new EntityWrapper<DeveObjectMethod>();
        ewMethod.where("obj_id = {0}", objId).orderBy("seq asc");
        List<DeveObjectMethod> methodList = deveObjectMethodService.selectList(ewMethod);
        if (methodList == null || methodList.size() == 0) {
            //29122 = 对象暂无配置方法，无法预览！
            ResponseCode.bussException("29122");
        }
        //设置方法中的字段列表、参数列表
        methodList = methodList.stream().sorted(Comparator.comparing(DeveObjectMethod::getDefaultMethod)).collect(Collectors.toList());
        for (DeveObjectMethod method : methodList) {
            method.setColumnList(columnMap.get(method.getMethodId()));
            method.setParamList(paramMap.get(method.getMethodId()));
            //默认方法
            if (method.getDefaultMethod() == 1) {
                deveObject.setDefaultMethod(method);
            }
        }

        deveObject.setMethodList(methodList);
        return deveObject;
    }

    @Override
    public DeveObjectMethod loadMethod(final Integer methodId, String objCode) {
        //参数非空判断
        if (!ObjectUtils.allNotNull(methodId, objCode)) {
            //10010 = 无效请求，参数为空,请检查！
            ResponseCode.bussException("10010");
        }

        //循环获取方法
        DeveObject deveObject = loadObjectByCode(objCode);
        for (DeveObjectMethod method : deveObject.getMethodList()) {
            if (methodId.equals(method.getMethodId())) {
                return method;
            }
        }
        return null;
    }


    @Override
    public DataGrid selectDeveObjectPriviewList(Integer methodId, String objCode) {
        return execute(methodId, objCode);
    }

    @Override
    @Transactional
    public long addOrUpdateOrDelete(Integer methodId, String objCode) {
        DataGrid dataGrid = execute(methodId, objCode);
        return  dataGrid.getTotal() ;
    }




    /**
     * 处理新增、修改、删除
     *
     * @param methodId
     * @param objCode
     */
    public DataGrid execute(Integer methodId, String objCode) {
        //获取对象
        DeveObject deveObject = loadObjectByCode(objCode);
        //获取对象方法
        DeveObjectMethod deveObjectMethod = deveObject.getMethodList().stream().filter(method -> method.getMethodId().equals(methodId)).collect(Collectors.toList()).get(0);
        if (deveObjectMethod == null) {
            //29124 = 找不到配置方法！
            ResponseCode.bussException("29124");
        }


        DataGrid dataGrid = new DataGrid();

        // 获取数据库类型
        String dbType = DataSourceConfig.getDbType(deveObject.getDataSource());
        // 保存当前数据源
        String orginDataSource = DbContextHolder.getDbType();
        // 设置数据源，查询记录
        DbContextHolder.setDbType(deveObject.getDataSource());

        try {
            NameSQL nameSQL = NameSQLBuilder.buildNameSQL(deveObjectMethod, deveObject);
            System.out.println("---------------------------");
            System.out.println("sql :" + nameSQL.getSql());
            nameSQL.getParams().forEach(s -> System.out.print("'" + s + "',"));
            System.out.println();
            System.out.println("---------------------------");

            //查询
            String methodType = ObjectUtils.defaultIfNull(deveObjectMethod.getMethodType(), "QUERY").toUpperCase();
            if ("QUERY".equals(methodType) || "EXPORT".equals(methodType)) {
                dataGrid = pageForDataGridBySQL(dbType, nameSQL);
            }else {
                dataGrid.setTotal(executeSQL(nameSQL.getSql(), nameSQL.getParams()));
            }

        }catch (Exception e) {
            //10000 = 请检查SQL语句是否正确！
            ResponseCode.bussException("10000");
        }finally {
            //还原数据源
            DbContextHolder.setDbType(orginDataSource);
        }

        return dataGrid;
    }


}
