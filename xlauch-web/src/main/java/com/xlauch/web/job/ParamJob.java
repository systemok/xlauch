package com.xlauch.web.job;/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/12
 */

import com.xlauch.web.entity.sys.SysParam;
import com.xlauch.web.service.sys.ISysParamService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类描述    : <br/>
 * 项目名称  : deve 项目<br/>
 * 类名称    : ParamJob.java <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/12/12 15:27  <br/>
 * @version 0.1
 */
public class ParamJob implements Job {

    @Autowired
    private ISysParamService sysParamService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<SysParam> paramList = sysParamService.selectByMap(null);
        if (paramList != null) {
            paramList.forEach(sysParam -> System.out.println(sysParam.getName() + " = " + sysParam.getValue()));
        }else {
            System.out.println("无法获取系统参数");
        }
    }
}
