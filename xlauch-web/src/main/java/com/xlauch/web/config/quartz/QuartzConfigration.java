package com.xlauch.web.config.quartz;

import com.xlauch.core.config.mybatis.DruidUtils;
import com.xlauch.core.exception.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>
 * 类描述：
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/8.
 */
@Slf4j
@Configuration
public class QuartzConfigration {


    @Autowired
    private MyJobFactory myJobFactory;  //自定义的factory

    @Autowired
    private QuartzProperties properties;

    //获取工厂bean
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        try {
            schedulerFactoryBean.setQuartzProperties(properties.getQuartzProperties());
            schedulerFactoryBean.setJobFactory(myJobFactory);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTraceAsString(e));
        }
        return schedulerFactoryBean;
    }

    //指定quartz.properties
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/application-dev-qz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }

    //创建schedule
    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

}
