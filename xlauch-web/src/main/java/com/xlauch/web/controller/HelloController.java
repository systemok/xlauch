package com.xlauch.web.controller;/**
 * Created by dell on 2017/10/25.
 */

import com.xlauch.core.config.redis.RedisManager;
import com.xlauch.core.config.spring.DynamicBeanUtils;
import com.xlauch.core.config.spring.DynamicConfiguration;
import com.xlauch.core.supers.web.BaseController;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述    : <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : HelloController.java <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/25 16:09  <br/>
 * @version 0.1
 */
@Controller
public class HelloController extends BaseController {

    private static final Logger log = Logger.getLogger(HelloController.class);

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DynamicConfiguration dynamicConfiguration ;

    @Autowired
    private Scheduler scheduler;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        if (DynamicBeanUtils.getBean(Hello.class) == null) {
            System.out.println("null ----------");
        }

        Map<String, Object> properties = new HashMap<>() ;
        properties.put("name" , "方小洲");
        DynamicBeanUtils.registerBean("helloaaa",new Hello() , properties);

        Hello hello = DynamicBeanUtils.getBean(Hello.class);
        hello.sayHello();


        properties.put("name" , "方小洲222");
        DynamicBeanUtils.registerBean("helloaaa",new Hello() , properties);

        hello = DynamicBeanUtils.getBean(Hello.class);
        hello.sayHello();
        return hello.sayHello();
    }

    @RequestMapping("/hello2")
    @ResponseBody
    public String hello2() {
        applicationContext.getBean(DynamicConfiguration.class);
        System.out.println("|||||||||||||||||||||");
        applicationContext.getBean(DynamicConfiguration.class);
        return "sdfsdfs";
    }

    @RequestMapping("/hello3")
    @ResponseBody
    public String hello3() throws Exception {
        Map<String, Object> properties = new HashMap<>() ;
        properties.put("name" , "方小洲");

        dynamicConfiguration.registerBean("helloTT",new Hello() , properties);
        try {
            Hello hello = applicationContext.getBean(Hello.class);
            hello.sayHello();
            System.out.println("|||||||||||||||||||||");

            properties.put("name" , "方小2222洲");
            dynamicConfiguration.registerBean("helloTT",new Hello() , properties);
            hello = applicationContext.getBean(Hello.class);
            hello.sayHello();
        }catch (Exception e) {
            System.out.println("");
        }
        return "sdfsdfs";
    }




    @RequestMapping("/quartz")
    @ResponseBody
    public String quartzTest(){
       /* try {
            String jobName = "com.xlauch.web.job.SimpleRecoveryJob";
            String jobName2 = "com.xlauch.web.job.SimpleRecoveryStatefulJob";
            String jobGroup = "JOB_GROUP";
            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobKey jobKey2 = new JobKey(jobName2, jobGroup);
            scheduler.deleteJob(jobKey);
            scheduler.deleteJob(jobKey2);
            //创建JobDetail（数据库中job_name存的任务全路径，这里就可以动态的把任务注入到JobDetail中）
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobName))
                    .withIdentity(jobName, jobGroup).build();

            JobDetail jobDetail2 = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobName2))
                    .withIdentity(jobName2, jobGroup).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();
            CronTrigger trigger2 = TriggerBuilder.newTrigger().withIdentity(jobName2, jobGroup).withSchedule(scheduleBuilder).build();

            //把trigger和jobDetail注入到调度器
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.scheduleJob(jobDetail2, trigger2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        return "success";
    }
}
