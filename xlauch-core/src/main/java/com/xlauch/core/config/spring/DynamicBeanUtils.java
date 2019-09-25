package com.xlauch.core.config.spring;


import com.xlauch.core.config.errorcodemsg.ResponseCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

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
public class DynamicBeanUtils {

    private static ApplicationContext applicationContext;

    private static ConfigurableApplicationContext configurableContext;

    private static BeanDefinitionRegistry beanDefinitionRegistry;


    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        DynamicBeanUtils.applicationContext = applicationContext;
        DynamicBeanUtils.configurableContext = (ConfigurableApplicationContext) applicationContext;
        DynamicBeanUtils.beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
    }


    /**
     * 注册bean
     *
     * @param beanId
     * @param object
     * @param properties
     * @return
     */
    public static boolean registerBean(String beanId, Object object, Map<String, Object> properties) {
        try {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(object.getClass().getName());
            //BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(object.getClass());

            /**
             * 设置属性
             */
            if (ObjectUtils.allNotNull(properties)) {
                properties.keySet().forEach(key -> beanDefinitionBuilder.addPropertyValue(key, properties.get(key)));
            }

            // get the BeanDefinition
            BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            // register the bean
            beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
        } catch (Exception e) {
            //10011 = 动态创建Bean失败!
            ResponseCode.bussException("10011");
        }
        return true;
    }

    /**
     * 获取bean
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        try {
            return  applicationContext.getBean(requiredType) ;
        }catch (Exception e) {
            return null ;
        }
    }


}
