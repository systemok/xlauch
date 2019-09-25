package com.xlauch.web.service.deve;

import com.xlauch.web.entity.deve.DeveQuartz;
import com.xlauch.core.supers.service.SuperService;
import com.xlauch.utils.page.DataGrid;
import org.quartz.SchedulerException;

import java.util.Map;

/**
 * <p>
 * 类描述: 定时任务 服务类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017-12-12
 */
public interface IDeveQuartzService extends SuperService<DeveQuartz> {

    /**
     * 列表分页查询
     *
     * @param jobName
     * @return
     * @throws Exception
     */
    public DataGrid selectDeveQuartzList(String jobName) throws Exception;

    /**
     * 新增任务
     *
     * @param deveQuartz
     * @return
     * @throws Exception
     */
    public Map addJob(DeveQuartz deveQuartz) throws Exception;

    /**
     * 修改任务
     *
     * @param deveQuartz
     * @param oldName
     * @return
     * @throws Exception
     */
    public Map editJob(DeveQuartz deveQuartz, String oldName) throws Exception;

    /**
     * 删除任务
     *
     * @param jobNames
     * @return
     * @throws Exception
     */
    public Map deleteJobs(String jobNames) throws Exception;

    /**
     * 暂停任务
     *
     * @param jobNames
     * @return
     * @throws Exception
     */
    public Map pauseJobs(String jobNames) throws Exception;

    /**
     * 恢复任务
     *
     * @param jobNames
     * @return
     * @throws Exception
     */
    public Map resumeJobs(String jobNames) throws Exception;

    /**
     * 暂停所有任务
     *
     * @return
     * @throws Exception
     */
    public Map pauseAll() throws Exception;

    /**
     * 恢复所有任务
     *
     * @return
     * @throws Exception
     */
    public Map resumeAll() throws Exception;

}
