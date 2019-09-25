package com.xlauch.web.config.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 类描述：自定义MyJobFactory，解决spring不能在quartz中注入bean的问题
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/8.
 */
@Component
public class MyJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        capableBeanFactory.autowireBean(jobInstance); //这一步解决不能spring注入bean的问题
        return jobInstance;
    }

}
