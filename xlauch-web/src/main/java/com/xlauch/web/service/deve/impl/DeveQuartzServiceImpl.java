package com.xlauch.web.service.deve.impl;

import cn.hutool.core.date.DateUtil;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.core.supers.service.SuperServiceImpl;
import com.xlauch.utils.page.DataGrid;
import com.xlauch.web.entity.deve.DeveQuartz;
import com.xlauch.web.mapper.deve.DeveQuartzMapper;
import com.xlauch.web.service.deve.IDeveQuartzService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.quartz.TriggerBuilder.newTrigger;


/**
 * <p>
 * 类描述: 定时任务 服务实现类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-12-12
 */
@Service
public class DeveQuartzServiceImpl extends SuperServiceImpl<DeveQuartzMapper, DeveQuartz> implements IDeveQuartzService {

    @Autowired
    private Scheduler scheduler ;

    private static final String JOB_GROUP = "JOB_GROUP";

    private static final String TRI_GROUP = "TRI_GROUP";

    @Override
    public DataGrid selectDeveQuartzList(String jobName) throws Exception {
        DataGrid dataGrid = new DataGrid() ;
        List<Map<String , Object>> jobList = queryJobList(jobName);
        dataGrid.setRows(jobList);
        dataGrid.setTotal(jobList.size());
        return dataGrid;
    }

    /**
     * 获取列表
     *
     * @param jobName
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> queryJobList(String jobName) throws Exception {
        List<Map<String, Object>> jobList = new ArrayList<Map<String, Object>>();
        ;

        //查询任务名称是否存在
        if (StringUtils.isNotEmpty(jobName)) {
            JobKey jobKey = new JobKey(jobName, JOB_GROUP);
            jobList.addAll(queryJobList(jobKey));
        } else {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            for (JobKey jobKey : jobKeys) {
                jobList.addAll(queryJobList(jobKey));
            }
        }
        return jobList;
    }

    /**
     * 根据jobkey获取列表
     *
     * @param jobKey
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> queryJobList(JobKey jobKey) throws Exception {
        ;
        List<Map<String, Object>> jobList = new ArrayList<Map<String, Object>>();

        List<? extends Trigger> list = scheduler.getTriggersOfJob(jobKey);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        for (Trigger trigger : list) {
            Map<String, Object> map = new HashMap<String, Object>();

            //任务名称、任务组
            map.put("jobName", jobKey.getName());
            map.put("jobGroup", jobKey.getGroup());

            //触发器名称、分组
            map.put("triName", trigger.getKey().getName());
            map.put("triGroup", trigger.getKey().getGroup());


            //开始时间
            if (trigger.getStartTime() != null) {
                map.put("beginTime", DateUtil.formatDateTime(trigger.getStartTime()));
            }
            //结束时间
            if (trigger.getEndTime() != null) {
                map.put("endTime", DateUtil.formatDateTime(trigger.getEndTime()));
            }
            //下次执行时间
            if (trigger.getNextFireTime() != null) {
                map.put("nextFireTime", DateUtil.formatDateTime(trigger.getNextFireTime()));
            }

            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                //执行表达式
                map.put("cronExpression", cronExpression);
            }

            //执行类
            map.put("className", jobDetail.getJobClass());

            //状态
            TriggerState state = scheduler.getTriggerState(trigger.getKey());
            map.put("state", state.ordinal());

            //备注
            map.put("desc", jobDetail.getDescription());

            jobList.add(map);
        }

        return jobList;
    }

    @Override
    public Map addJob(DeveQuartz deveQuartz) throws Exception {
        if (deveQuartz == null) {
            //10010 = 无效请求，参数为空,请检查!
            return ResponseCode.writeFail("10010");
        }

        ;
        JobKey jobKey = new JobKey(deveQuartz.getJobName(), JOB_GROUP);

        if (scheduler.checkExists(jobKey)) {
            //29110 = 该任务名称已存在，请重新配置!
            return ResponseCode.writeFail("29110");
        }
        Class clazz = null ;
        try {
            clazz = Class.forName(deveQuartz.getClassName());
        } catch (Exception e) {
            //29111 = 配置执行类不存在,请重新配置!
            return ResponseCode.writeFail("29111");
        }

        JobDetail job = JobBuilder.newJob(clazz)
                .withIdentity(jobKey)
                .withDescription(deveQuartz.getDesc())
                .build();

        if(StringUtils.isEmpty(deveQuartz.getEndTime())){
            deveQuartz.setEndTime("2099-12-31 23:59:59");
        }

        Trigger trigger = newTrigger()
                .withIdentity(deveQuartz.getJobName() + "_" + JOB_GROUP + "_TRI", TRI_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(deveQuartz.getCronExpression()))
                .startAt(DateUtil.parseDateTime(deveQuartz.getBeginTime()))
                .endAt(DateUtil.parseDateTime(deveQuartz.getEndTime()))
                .build();

        scheduler.scheduleJob(job, trigger);
        return ResponseCode.writeSuccess();
    }

    @Override
    public Map editJob(DeveQuartz deveQuartz , String oldName) throws Exception {
        if (deveQuartz == null) {
            //10010 = 无效请求，参数为空,请检查!
            return ResponseCode.writeFail("10010");
        }

        deleteJobs(oldName);
        addJob(deveQuartz);
        return ResponseCode.writeSuccess();
    }

    @Override
    public Map deleteJobs(String jobNames) throws Exception {
        if(StringUtils.isNotEmpty(jobNames)){
            String[] jobArr = jobNames.split(",");

            for (String jobName : jobArr) {
                scheduler.deleteJob(new JobKey(jobName , JOB_GROUP));
            }

            return ResponseCode.writeSuccess();
        }

        //10010 = 无效请求，参数为空,请检查!
        return ResponseCode.writeFail("10010");
    }

    @Override
    public Map pauseJobs(String jobNames) throws Exception {
        if(StringUtils.isNotEmpty(jobNames)){
            String[] jobArr = jobNames.split(",");

            for (String jobName : jobArr) {
                scheduler.pauseJob(new JobKey(jobName , JOB_GROUP));
            }

            return ResponseCode.writeSuccess();
        }

        //10010 = 无效请求，参数为空,请检查!
        return ResponseCode.writeFail("10010");
    }

    @Override
    public Map resumeJobs(String jobNames) throws Exception {
        if(StringUtils.isNotEmpty(jobNames)){
            String[] jobArr = jobNames.split(",");

            for (String jobName : jobArr) {
                scheduler.resumeJob(new JobKey(jobName , JOB_GROUP));
            }

            return ResponseCode.writeSuccess();
        }

        //10010 = 无效请求，参数为空,请检查!
        return ResponseCode.writeFail("10010");
    }

    @Override
    public Map pauseAll() throws Exception {
        scheduler.pauseAll();
        return ResponseCode.writeSuccess();
    }

    @Override
    public Map resumeAll() throws Exception {
        scheduler.resumeAll();
        return ResponseCode.writeSuccess();
    }
}
