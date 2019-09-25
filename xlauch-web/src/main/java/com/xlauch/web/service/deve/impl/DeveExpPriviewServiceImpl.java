package com.xlauch.web.service.deve.impl;


import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.config.mybatis.DataSourceConfig;
import com.xlauch.core.config.mybatis.DbContextHolder;
import com.xlauch.core.config.redis.RedisManager;
import com.xlauch.core.context.ActionContext;
import com.xlauch.core.supers.service.SuperServiceImpl;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.utils.page.NameSQL;
import com.xlauch.utils.util.FastJsonUtils;
import com.xlauch.utils.util.date.DateUtil;
import com.xlauch.utils.util.file.FileWriterUtils;
import com.xlauch.web.common.constants.FileType;
import com.xlauch.web.common.utils.SysParamUtils;
import com.xlauch.web.entity.deve.DeveExp;
import com.xlauch.web.entity.deve.DeveExpColumn;
import com.xlauch.web.entity.deve.DeveExpParam;
import com.xlauch.web.entity.deve.DeveExpPriview;
import com.xlauch.web.mapper.deve.DeveExpPriviewMapper;
import com.xlauch.web.service.deve.IDeveExpColumnService;
import com.xlauch.web.service.deve.IDeveExpParamService;
import com.xlauch.web.service.deve.IDeveExpPriviewService;
import com.xlauch.web.service.deve.IDeveExpService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Template;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 类描述:  服务实现类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-11-16
 */
@Service
public class DeveExpPriviewServiceImpl extends SuperServiceImpl<DeveExpPriviewMapper, DeveExpPriview> implements IDeveExpPriviewService {

    /**
     * redis 缓存管理
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * beetl 配置
     */
    @Autowired
    private BeetlGroupUtilConfiguration beetlGroupUtilConfiguration;

    /**
     * 导出 服务类
     */
    @Autowired
    private IDeveExpService deveExpService;

    /**
     * 导出 字段管理服务类
     */
    @Autowired
    private IDeveExpColumnService deveExpColumnService;

    /**
     * 导出 参数管理服务类
     */
    @Autowired
    private IDeveExpParamService deveExpParamService;

    /**
     * 导出 预览Mapper 接口
     */
    @Autowired
    private DeveExpPriviewMapper deveExpPriviewMapper;

    /**
     * 系统参数工具
     */
    @Autowired
    private SysParamUtils sysParamUtils;

    @Value("deve.exp.key")
    private String redisKey;

    @Override
    public DataGrid selectDeveExpPriviewList() {
        ActionContext<DeveExpPriview> actionContext = ActionContext.getContext();
        Page<DeveExpPriview> page = actionContext.getPage();
        page.setRecords(deveExpPriviewMapper.selectDeveExpPriviewList(page, actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    public DeveExpPriview getDeveExpPriview(int expId, boolean isDebug) {
        //调试模式，不从缓存获取
        if (isDebug) {
            return getDeveExpPriview(expId);
        }

        DeveExpPriview deveExpPriview = null;
        // 先从缓存中获取对象
        String str = redisManager.hget(redisKey, expId + "");
        //String str = redisManager.get("deve:exp:exp_" + expId);
        if (StringUtils.isEmpty(str)) {
            // 缓存中对象为空，从数据库中读取，并保存到缓存
            deveExpPriview = getDeveExpPriview(expId);
            redisManager.hset(redisKey, expId + "", FastJsonUtils.toJSONString(deveExpPriview));
            //redisManager.set("deve:exp:exp_" + expId, FastJsonUtils.toJSONString(deveExpPriview));
        } else {
            deveExpPriview = FastJsonUtils.toBean(str, DeveExpPriview.class);
        }

        return deveExpPriview;
    }

    public DeveExpPriview getDeveExpPriview(int expId) {
        DeveExpPriview deveExpPriview = new DeveExpPriview();

        /**
         * 获取导出对象
         */
        DeveExp deveExp = deveExpService.selectById(expId);
        if (deveExp == null) {
            //29003 = 组件不存在 抛出异常
            ResponseCode.bussException("29003");
        }
        deveExpPriview.setDeveExp(deveExp);


        /**
         * 获取导出 -- 字段列表
         */
        EntityWrapper<DeveExpColumn> ew = new EntityWrapper<DeveExpColumn>();
        ew.where("exp_id = {0}", expId).orderBy("seq asc");
        List<DeveExpColumn> columnList = deveExpColumnService.selectList(ew);
        deveExpPriview.setColumnList(columnList);

        /**
         * 设置字段别名映射
         */
        if (columnList != null) {
            Map<String, String> alias = new HashMap<>();
            columnList.forEach(column -> alias.put(column.getName(), column.getTitle()));
            deveExpPriview.setAlias(alias);
        }

        /**
         * 获取导出 -- 参数列表
         */
        EntityWrapper<DeveExpParam> ew2 = new EntityWrapper<DeveExpParam>();
        ew2.where("exp_id = {0}", expId).orderBy("seq asc");
        List<DeveExpParam> paramList = deveExpParamService.selectList(ew2);
        deveExpPriview.setParamList(paramList);

        return deveExpPriview;
    }

    @Override
    public DataGrid selectDataGrid(int expId, boolean isDebug) {
        DeveExpPriview deveExpPriview = getDeveExpPriview(expId, isDebug);

        // 获取数据库类型
        String dbType = DataSourceConfig.getDbType(deveExpPriview.getDeveExp().getDatasource());
        // 创建命名SQL
        NameSQL nameSQL = createNameSQL(deveExpPriview);
        // 设置数据源，查询记录
        DbContextHolder.setDbType(deveExpPriview.getDeveExp().getDatasource());
        return pageForDataGridBySQL(dbType, nameSQL);
    }


    /**
     * 创建命名SQL
     *
     * @param deveExpPriview
     * @return
     */
    private NameSQL createNameSQL(DeveExpPriview deveExpPriview) {
        ActionContext actionContext = ActionContext.getContext();

        // 获取真实SQL
        String sql = deveExpPriview.getDeveExp().getSqlx();
        List<Object> params = new ArrayList<>();

        List<DeveExpParam> paramList = deveExpPriview.getParamList();
        if (paramList != null) {
            for (DeveExpParam deveExpParam : paramList) {
                // 参数名
                String paramName = deveExpParam.getName();
                // 是否模糊查询
                boolean isLike = deveExpParam.getLikeable() == 1;
                // 模糊查询方式
                String likeType = deveExpParam.getLikeType();
                // 参数值
                Object paramValue = actionContext.getParam(paramName);

                // 参数值为空
                String fullValue = String.valueOf(paramValue);
                if (paramValue == null || StringUtils.isEmpty(String.valueOf(paramValue))) {
                    sql = sql.replace("#{" + paramName + "}", "");
                    sql = sql.replace("${" + paramName + "}", "");
                } else {
                    String paramSql = deveExpParam.getSqlx();
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

    @Override
    public String exportObject(List<Integer> idList) throws Exception {
        String filePath = sysParamUtils.getSysParamValue("FileDownloadPath") + File.separator;
        //29108 = 文件下载目录未配置!
        if (StringUtils.isEmpty(filePath)) {
            ResponseCode.bussException("29108");
        }
        filePath = filePath + DateUtil.getDate() + ".xlauch";

        //获取要导出的对象列表
        List<DeveExpPriview> deveExpPriviewList = new ArrayList<DeveExpPriview>();
        idList.forEach(id -> {
            deveExpPriviewList.add(getDeveExpPriview(id, false));
        });

        //写入文件
        FileWriter writer = new FileWriter(filePath);
        if (ObjectUtils.allNotNull(deveExpPriviewList)) {
            String content = FastJsonUtils.toJSONString(deveExpPriviewList);
            writer.write(content);
        }

        return filePath;
    }

    @Override
    public void importObject(String filePath) throws Exception {
        FileReader reader = new FileReader(filePath);
        String conent = reader.readString();

        if (StringUtils.isNotEmpty(conent)) {
            try {
                List<DeveExpPriview> deveExpPriviewList = FastJsonUtils.toList(conent, DeveExpPriview.class);

                //批量更新或者插入
                deveExpPriviewList.forEach(deveExpPriview -> {
                    int oldId = deveExpPriview.getDeveExp().getExpId();
                    deveExpService.insertOrUpdate(deveExpPriview.getDeveExp());
                    //判断是更新，还是新插入，新插入的id与旧id不一致
                    int newId = deveExpPriview.getDeveExp().getExpId();
                    if (oldId != newId) {
                        int random = newId + 100000;
                        String code = "exp_" + random;
                        deveExpPriview.getDeveExp().setCode(code);

                        //更新编码
                        deveExpService.updateById(deveExpPriview.getDeveExp());
                        //更新字段列表中的id
                        deveExpPriview.getColumnList().forEach(column -> column.setExpId(deveExpPriview.getDeveExp().getExpId()));
                        //更新参数列表中的id
                        deveExpPriview.getParamList().forEach(param -> param.setExpId(deveExpPriview.getDeveExp().getExpId()));
                    }

                    //批量插入字段列表
                    if (ObjectUtils.allNotNull(deveExpPriview.getColumnList()) && deveExpPriview.getColumnList().size() > 0) {
                        deveExpColumnService.insertOrUpdateBatch(deveExpPriview.getColumnList());
                    }
                    //批量插入参数列表
                    if (ObjectUtils.allNotNull(deveExpPriview.getParamList()) && deveExpPriview.getParamList().size() > 0) {
                        deveExpParamService.insertOrUpdateBatch(deveExpPriview.getParamList());
                    }
                });
            } catch (Exception e) {
                //29109 = 导入对象失败，文件内容转换错误!
                ResponseCode.bussException("29109");
            }
        }
    }

    @Override
    public Map generPage(int expId, String templatePath, String outPath) {
        DeveExpPriview deveExpPriview = getDeveExpPriview(expId, false);
        if (deveExpPriview == null) {
            //29003 = 组件不存在
            ResponseCode.bussException("29003");
        }

        Template template = beetlGroupUtilConfiguration.getGroupTemplate().getTemplate(templatePath);
        template.getCtx().set("priview", deveExpPriview);
        template.getCtx().set("ctxPath", "${ctxPath}");

        try {
            File file = new File(outPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            template.renderTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            //29100 = 文件路径不存在
            return ResponseCode.writeFail("29100");
        }
        return ResponseCode.writeSuccess();
    }


    @Override
    public String exportData(int expId, String fileType) throws Exception {
        DeveExpPriview deveExpPriview = getDeveExpPriview(expId, false);
        if (deveExpPriview == null) {
            //29003 = 组件不存在
            ResponseCode.bussException("29003");
        }

        String filePath = sysParamUtils.getSysParamValue("FileDownloadPath") + File.separator;
        //29108 = 文件下载目录未配置!
        if (StringUtils.isEmpty(filePath)) {
            ResponseCode.bussException("29108");
        }

        // 创建命名SQL
        NameSQL nameSQL = createNameSQL(deveExpPriview);

        // 设置数据源，查询记录
        DbContextHolder.setDbType(deveExpPriview.getDeveExp().getDatasource());

        //数据
        List<Map<String, Object>> rows = selectBySQL(nameSQL.getSql(), nameSQL.getParams());


        switch (fileType.toLowerCase()) {
            case FileType.EXCEL:
                filePath = filePath + DateUtil.getDate() + ".xlsx";
                exportExcel(filePath, deveExpPriview, rows);
                break;
            default : filePath = filePath + DateUtil.getDate() + ".xlsx";
                exportExcel(filePath, deveExpPriview, rows);
                break;
        }

        return filePath;
    }

    private void exportExcel(String filePath, DeveExpPriview deveExpPriview, List<Map<String, Object>> rows) {
        FileWriterUtils.writeExcel(filePath, deveExpPriview.getDeveExp().getName(), rows, deveExpPriview.getAlias(), deveExpPriview.getDeveExp().getBeginRow(), deveExpPriview.getDeveExp().getBeginCol());
    }


}
