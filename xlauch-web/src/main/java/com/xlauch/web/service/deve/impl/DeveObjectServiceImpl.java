package com.xlauch.web.service.deve.impl;


import com.alibaba.fastjson.JSON;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.config.mybatis.DataSourceConfig;
import com.xlauch.core.config.mybatis.DbContextHolder;
import com.xlauch.utils.util.db.DbColumn;
import com.xlauch.utils.util.db.DsUtils;
import com.xlauch.utils.util.db.MetaData;
import com.xlauch.web.config.shiro.Users;
import com.xlauch.web.entity.deve.DeveObjectColumn;
import com.xlauch.web.entity.deve.DeveObjectMethod;
import com.xlauch.web.entity.deve.DeveObjectParam;
import com.xlauch.web.mapper.deve.DeveObjectColumnMapper;
import com.xlauch.web.mapper.deve.DeveObjectMethodMapper;
import com.xlauch.web.mapper.deve.DeveObjectParamMapper;
import com.xlauch.web.service.deve.IDeveObjectColumnService;
import com.xlauch.web.service.deve.IDeveObjectMethodService;
import com.xlauch.web.service.deve.IDeveObjectParamService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.deve.DeveObject;
import com.xlauch.web.mapper.deve.DeveObjectMapper;
import com.xlauch.web.service.deve.IDeveObjectService;
import com.xlauch.core.supers.service.SuperServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 类描述: 二次开发-对象表 服务实现类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-12-28
 */
@Service
public class DeveObjectServiceImpl extends SuperServiceImpl<DeveObjectMapper, DeveObject> implements IDeveObjectService {

    /**
     * 对象管理Mapper 接口
     */
    @Autowired
    private DeveObjectMapper deveObjectMapper;

    /**
     * 对象方法Mapper 接口
     */
    @Autowired
    private DeveObjectMethodMapper deveObjectMethodMapper;

    /**
     * 对象字段Mapper 接口
     */
    @Autowired
    private DeveObjectColumnMapper deveObjectColumnMapper;

    /**
     * 对象字段 服务类
     */
    @Autowired
    private IDeveObjectColumnService deveObjectColumnService;

    /**
     * 对象方法 服务类
     */
    @Autowired
    private IDeveObjectMethodService deveObjectMethodService;

    /**
     * 对象参数Mapper 接口
     */
    @Autowired
    private DeveObjectParamMapper deveObjectParamMapper;

    /**
     * 对象参数 服务类
     */
    @Autowired
    private IDeveObjectParamService deveObjectParamService;

    @Override
    public DataGrid selectDeveObjectList() {
        ActionContext<DeveObject> actionContext = ActionContext.getContext();
        Page<DeveObject> page = actionContext.getPage();
        page.setRecords(deveObjectMapper.selectDeveObjectList(page, actionContext.getDataMap()));
        return pageToDataGrid(page);
    }


    @Override
    public List<MetaData> queryTables(String dataSource, String[] types) {
        // 设置数据源，查询记录
        DbContextHolder.setDbType(dataSource);
        return DsUtils.queryTables(getDataSource(), types);
    }

    @Override
    @Transactional
    public void addDeveObject(DeveObject deveObject) {
        deveObject.setStatus(1);
        deveObject.setAddTime(new Date());
        deveObject.setAddUser(Integer.parseInt(Users.getCurrentUser().getUserId() + ""));
        initDeveObject(deveObject);
    }

    /**
     * 初始化对象
     *
     * @param deveObject
     */
    private void initDeveObject(DeveObject deveObject) {
        // ------------------------- 对象处理 -------------------------------
        deveObject.setAddTime(new Date());
        if (ObjectUtils.allNotNull(deveObject.getObjId())) {
            deveObject.updateById();
        } else {
            //保存对象
            deveObject.insert();
        }

        // ------------------------- 字段处理 -------------------------------
        // 生成字段列表
        List<DeveObjectColumn> columnList = generDeveObjectColumnList(deveObject);
        //弹窗高度
        int height = columnList.stream().map(DeveObjectColumn::getHeight).reduce(10, (a, b) -> {
            if (b == null || b <= 30) {
                b = 30;
            }
            return a + b;
        });
        // 初始化字典
        String initDictStr = columnList.stream()
                .map(DeveObjectColumn::getDictStr)
                .filter(StringUtils::isNotEmpty)
                .distinct()
                .collect(Collectors.joining(","));
        // 把列表转成json字符串
        String columnStr = JSON.toJSONString(columnList);

        //@todo ------------------------- 参数处理 -------------------------------
        List<DeveObjectParam> paramList = generDeveObjectParamList(null, columnList);
        // 把列表转成json字符串
        String paramStr = JSON.toJSONString(paramList);


        // ------------------------- 方法处理 -------------------------------
        List<DeveObjectColumn> allColumnList = new ArrayList<DeveObjectColumn>();
        List<DeveObjectParam> allParamList = new ArrayList<DeveObjectParam>();
        List<DeveObjectMethod> methodList = generDeveObjectMethodList(deveObject);

        for (DeveObjectMethod deveObjectMethod : methodList) {
            // 初始化字典
            deveObjectMethod.setInitDict(initDictStr);
            deveObjectMethod.setViewSql(generSQL(deveObjectMethod.getMethodType(), columnList, deveObject));
            deveObjectMethod.setHeight(height);
            // 查询类方法根据参数列表设置查询语句
            boolean isQuery = paramList.size() > 0 && ("query".equalsIgnoreCase(deveObjectMethod.getMethodType()) || "list".equalsIgnoreCase(deveObjectMethod.getMethodType()));
            if (isQuery) {
                String viewSql = deveObjectMethod.getViewSql();
                viewSql += " where 1 = 1 " ;
                String sqlx = paramList.stream().map(p -> {
                    return "#{" + p.getEname() + "}" ;
                }).collect(Collectors.joining(" "));
                deveObjectMethod.setViewSql(viewSql + sqlx) ;
            }
            deveObjectMethod.insert();


            // 替换方法ID
            List<DeveObjectColumn> tmpColumnList = JSON.parseArray(columnStr, DeveObjectColumn.class);
            tmpColumnList.forEach(c -> c.setMethodId(deveObjectMethod.getMethodId()));
            allColumnList.addAll(tmpColumnList);

            // 查询类方法才设置参数
            if (isQuery) {
                List<DeveObjectParam> tmpParamList = JSON.parseArray(paramStr, DeveObjectParam.class);
                tmpParamList.forEach(p -> p.setMethodId(deveObjectMethod.getMethodId()));
                allParamList.addAll(tmpParamList);
            }
        }
        //保存所有字段
        deveObjectColumnService.insertBatch(allColumnList);
        //保存所有参数
        if (allParamList != null && allParamList.size() > 0 ) {
            deveObjectParamService.insertBatch(allParamList);
        }
    }

    /**
     * 生成字段列表
     *
     * @param deveObject
     * @return
     */
    private List<DeveObjectColumn> generDeveObjectColumnList(DeveObject deveObject) {
        // 保存当前数据源
        String orginDataSource = DbContextHolder.getDbType();

        // 根据配置的数据源，获取字段列表
        String dataSource = deveObject.getDataSource();
        DbContextHolder.setDbType(dataSource);

        List<DbColumn> columnList = DsUtils.getColumns(DataSourceConfig.getDbType(dataSource), deveObject.getDbName(), getDataSource());

        //还原数据源
        DbContextHolder.setDbType(orginDataSource);

        //获取预设字段列表
        List<DeveObjectColumn> prepareColumnList = deveObjectColumnService.selectByMethodId(-1);
        if (!ObjectUtils.allNotNull(columnList, prepareColumnList)) {
            //29131 = 预设字段列表为空！
            ResponseCode.bussException("29131");
        }

        List<DeveObjectColumn> deveObjectColumnList = new ArrayList<>();
        int seq = 1;
        for (DbColumn column : columnList) {
            deveObjectColumnList.add(generDeveObjectColumn(prepareColumnList, null, column, seq++));
        }
        return deveObjectColumnList;
    }


    /**
     * 生成字段
     *
     * @param prepareColumnList
     * @param methodId
     * @param dbColumn
     * @param seq
     * @return
     */
    private DeveObjectColumn generDeveObjectColumn(List<DeveObjectColumn> prepareColumnList, Integer methodId, DbColumn dbColumn, int seq) {
        DeveObjectColumn deveObjectColumn = new DeveObjectColumn();
        if (prepareColumnList != null && prepareColumnList.size() > 0) {
            DeveObjectColumn sourceColumn = new DeveObjectColumn();

            List<DeveObjectColumn> findList = new ArrayList<>();
            for (DeveObjectColumn column : prepareColumnList) {
                //字段名称相同
                if (column.getEname().equalsIgnoreCase(dbColumn.getEname())) {
                    findList.add(column);
                    break;
                }
                //字段备注没有逗号
                if (column.getCname().split(",").length == 1) {
                    if (column.getCname().toUpperCase().contains(dbColumn.getCname().toUpperCase()) ||
                            dbColumn.getCname().toUpperCase().contains(column.getCname().toUpperCase())) {
                        findList.add(column);
                        break;
                    }
                } else {
                    for (String key : column.getCname().split(",")) {
                        if (dbColumn.getCname().toUpperCase().contains(key.toUpperCase())) {
                            findList.add(column);
                            break;
                        }
                    }
                }
            }

            //未找到匹配，返回textbox格式
            if (findList == null || findList.size() == 0) {
                sourceColumn = prepareColumnList.get(0);
            } else {
                sourceColumn = findList.get(0);
            }


            //复制字段
            try {
                BeanUtils.copyProperties(sourceColumn, deveObjectColumn);

                //设置名称、方法ID、排序
                deveObjectColumn.setCname(dbColumn.getCname());
                deveObjectColumn.setEname(dbColumn.getEname());
                deveObjectColumn.setColumnId(null);
                deveObjectColumn.setMethodId(methodId);
                deveObjectColumn.setSeq(seq);
                //替换宽度
                replaceColumnFormStr(deveObjectColumn);
//                deveObjectColumn.setUiCheck(replaceWidth(deveObjectColumn.getUiCheck(), dbColumn.getLen()));
//                deveObjectColumn.setFormStr(replaceWidth(deveObjectColumn.getFormStr(), dbColumn.getLen()));
                //是否主键
                if (dbColumn.isPk()) {
                    deveObjectColumn.setCheckboxAble(1);
                    deveObjectColumn.setPk(true);
                }
            } catch (Exception e) {
                //29130 = 拷贝方法字段失败！
                ResponseCode.bussException("29130");
            }
        } else {
            deveObjectColumn = new DeveObjectColumn(methodId, dbColumn, seq);
        }
        return deveObjectColumn;
    }

    /**
     * 生成参数列表
     *
     * @param methodId
     * @param columnList
     * @return
     */
    //@todo
    private List<DeveObjectParam> generDeveObjectParamList(Integer methodId, List<DeveObjectColumn> columnList) {
        List<DeveObjectParam> paramList = new ArrayList<>();
        //获取预设字段列表
        List<DeveObjectParam> prepareParamnList = deveObjectParamService.selectByMethodId(-1);
        if (prepareParamnList != null && prepareParamnList.size() > 0) {
            for (DeveObjectColumn deveObjectColumn : columnList) {
                String ename = deveObjectColumn.getEname();
                String cname = ObjectUtils.defaultIfNull(deveObjectColumn.getCname(), deveObjectColumn.getEname());
                //预设参数英文包含字段名，或者字段中文名包含预设参数中文名
                for (DeveObjectParam param : prepareParamnList) {
                    if (param.getEname().toUpperCase().contains(ename.toUpperCase()) || cname.contains(param.getCname())) {
                        DeveObjectParam deveObjectParam = new DeveObjectParam();
                        BeanUtils.copyProperties(param, deveObjectParam);
                        //设置名称、方法ID、排序
                        deveObjectParam.setEname(ename);
                        deveObjectParam.setParamId(null);
                        deveObjectParam.setMethodId(methodId);
                        replaceParamFormStr(deveObjectParam);
                        paramList.add(deveObjectParam);
                        break;
                    }
                }
            }
        }

        return paramList;
    }

    /**
     * 获取预设的方法列表，对象等于“-1”的方法为系统预设方法
     *
     * @param deveObject
     * @return
     */
    private List<DeveObjectMethod> generDeveObjectMethodList(DeveObject deveObject) {
        Map<String, Object> params = new HashMap<>();
        params.put("obj_id", -1);
        List<DeveObjectMethod> deveObjectMethodList = deveObjectMethodMapper.selectByMap(params);
        if (deveObjectMethodList == null || deveObjectMethodList.size() == 0) {
            //29132 = 预设方法列表为空！
            ResponseCode.bussException("29132");
        }

        if ("VIEW".equals(deveObject.getType())) {
            deveObjectMethodList = deveObjectMethodList.stream().filter(deveObjectMethod -> {
                return deveObjectMethod.getMethodType().equals("query");
            }).collect(Collectors.toList());
        }

        deveObjectMethodList.forEach(deveObjectMethod -> {
            //设置对象id
            deveObjectMethod.setObjId(deveObject.getObjId());
            deveObjectMethod.setMethodId(null);
        });
        return deveObjectMethodList;
    }

    /**
     * 生成SQL
     *
     * @param methodType
     * @param columnList
     * @param deveObject
     * @return
     */
    private String generSQL(String methodType, List<DeveObjectColumn> columnList, DeveObject deveObject) {
        StringBuffer sbf = new StringBuffer();
        String commonColumns = columnList.stream().map(DeveObjectColumn::getEname).collect(Collectors.joining(","));
        String dbName = deveObject.getDbName();
        methodType = ObjectUtils.defaultIfNull(methodType, "QUEREY").toUpperCase();
        //查询
        if ("QUERY".equals(methodType) || "EXPORT".equals(methodType)) {
            sbf.append("select ").append(commonColumns).append(" from ").append(dbName);
        }
        //新增
        else if ("ADD".equals(methodType) || "INSERT".equals(methodType)) {
            String valueStr = columnList.stream().map(column -> {
                //return column.isPk() ? "null" : "?";
                return "?";
            }).collect(Collectors.joining(","));

            sbf.append("insert into ").append(dbName);
            sbf.append(" (").append(commonColumns).append(") values ( ").append(valueStr).append(")");
        }
        //修改
        else if ("EDIT".equals(methodType) || "UPDATE".equals(methodType)) {
            String valueStr = columnList.stream().map(column -> {
                return column.isPk() ? null : column.getEname() + " = ? ";
            }).filter(s -> s != null).collect(Collectors.joining(","));

            sbf.append("update ").append(dbName).append(" set ").append(valueStr);
            sbf.append(" where ").append(deveObject.getPkName()).append(" = ? ");
        }
        //删除
        else if ("REMOVE".equals(methodType) || "DELETE".equals(methodType)) {
            sbf.append("delete from ").append(dbName).append(" where ").append(deveObject.getPkName()).append(" = ? ");
        }

        return sbf.toString();
    }


    @Transactional()
    @Override
    public void deleteBatch(List<Integer> ids) {
        ids.forEach(id -> {
            DeveObject deveObjec = new DeveObject();
            deveObjec.setObjId(id);
            deleteDeveObject(deveObjec);
        });
    }

    /**
     * 删除
     *
     * @param deveObject
     */
    @Transactional
    @Override
    public void deleteDeveObject(DeveObject deveObject) {
        if (ObjectUtils.allNotNull(deveObject, deveObject.getObjId())) {
            int objId = deveObject.getObjId();
            //删除参数
            deveObjectParamMapper.deleteByObjectId(objId);
            //删除字段
            deveObjectColumnMapper.deleteByObjectId(objId);
            //删除方法
            deveObjectMethodMapper.deleteByObjectId(objId);
            //删除对象
            deveObject.deleteById();
        }
    }

    @Override
    public DeveObject selectByCode(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        List<DeveObject> deveObjectList = deveObjectMapper.selectByMap(params);
        if (deveObjectList != null && deveObjectList.size() > 0) {
            if (deveObjectList.size() > 1) {
                //10012 = 编码重复，存在多个对象！
                ResponseCode.bussException("10012");
            }
            return deveObjectList.get(0);
        }

        return null;
    }


    /**
     * 替换字段字符串
     *
     * @param deveObjectColumn
     */
    private void replaceColumnFormStr(DeveObjectColumn deveObjectColumn) {
        String uiChek = deveObjectColumn.getUiCheck();
        uiChek = replaceStr(uiChek, "length[0,", "]", deveObjectColumn.getWidth());
        deveObjectColumn.setUiCheck(uiChek);

        String formStr = deveObjectColumn.getFormStr();
        formStr = replaceStr(formStr, "length[0,", "]", deveObjectColumn.getWidth());
        formStr = replaceStr(formStr, "name=\"", "\"", deveObjectColumn.getEname());
        formStr = replaceStr(formStr, "id=\"", "\"", deveObjectColumn.getEname());
        deveObjectColumn.setFormStr(formStr);
    }

    /**
     * 替换参数字符串
     *
     * @param deveObjectParam
     */
    private void replaceParamFormStr(DeveObjectParam deveObjectParam) {
        String formStr = deveObjectParam.getFormStr() ;
        formStr = replaceStr(formStr, "name=\"", "\"", deveObjectParam.getEname());
        deveObjectParam.setFormStr(formStr) ;

        String sqlx = "and " + deveObjectParam.getEname() + " = ? ";
        if (deveObjectParam.getLikeAble() == 1) {
            sqlx = "and upper(" + deveObjectParam.getEname() + ") like ? ";
        }
        deveObjectParam.setSqlx(sqlx);
    }


    /**
     * 替换资源
     *
     * @param oldStr
     * @param prefix
     * @param suffix
     * @param newStr
     * @return
     */
    private String replaceStr(String oldStr, String prefix, String suffix, Object newStr) {
        if (StringUtils.isEmpty(oldStr)) {
            return "";
        }
        int len = prefix.length();

        try {
            int pos = oldStr.indexOf(prefix);
            String before = oldStr.substring(0, pos + len);
            String after = oldStr.substring(pos + len);
            pos = after.indexOf(suffix);
            after = after.substring(pos);

            return before + newStr + after;
        } catch (Exception e) {
            return oldStr;
        }
    }

}
