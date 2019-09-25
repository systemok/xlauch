package com.xlauch.core.config.spring;


import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/28
 */
@Configuration
public class DynamicConfiguration {

    @Autowired
    private ApplicationContext applicationContext;


    @Bean
    public DefaultListableBeanFactory beanFactory(){
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        return beanFactory ;
    }




    /**
     *
     * @param beanName
     * @param object
     * @param properties
     * @return
     * @throws Exception
     */
    public String registerBean(String beanName, Object object, Map<String, Object> properties) throws Exception {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(object.getClass());

        /**
         * 设置属性
         */
        if (ObjectUtils.allNotNull(properties)) {
            properties.keySet().forEach(key -> beanDefinitionBuilder.addPropertyValue(key, properties.get(key)));
        }

        /**
         * 注册到spring容器中
         */
        beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        return null;
    }

    /**
     * 获取bean
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> requiredType) {
        try {
            return  applicationContext.getBean(requiredType) ;
        }catch (Exception e) {
            return null ;
        }
    }

}
