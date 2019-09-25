package com.xlauch.web.config.beetl;


import com.xlauch.web.config.beetl.func.BeetlDictFun;
import com.xlauch.web.config.beetl.func.BeetlDictInitFun;
import com.xlauch.web.config.beetl.tag.ButtonPermissionTag;
import com.xlauch.web.config.beetl.tag.DictTag;
import org.beetl.core.Function;
import org.beetl.core.Tag;
import org.beetl.core.TagFactory;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述    : beetl 配置文件 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : BeetlConf <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/25 16:06  <br/>
 * @version 0.1
 */
@Configuration
public class BeetlConf {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 模板跟目录 ，比如 "templates"
     */
    @Value("${beetl.templatesPath}")
    String templatesPath;

    /**
     * 模板后缀 ， 比如 ".btl, .html"
     */
    @Value("${beetl.suffix}")
    String suffix ;

    @Bean
    public BeetlDictFun getBeetlDictFun(){
        return new BeetlDictFun();
    }

    /**
     * 扩展：自定义数据字典函数
     * @return
     */
    @Bean
    public BeetlDictInitFun getBeetlDictInitFun(){
        return new BeetlDictInitFun();
    }


    /**
     * 扩展：自定义数据字典标签
     * @return
     */
    public TagFactory getDictTagFactory() {
        return new TagFactory() {
            @Override
            public Tag createTag() {
                return applicationContext.getBean(DictTag.class);
            }
        };
    }

    /**
     * 扩展：自定义权限增删改标签
     * @return
     */
    public TagFactory getButtonPermissionTagFactory() {
        return new TagFactory() {
            @Override
            public Tag createTag() {
                return applicationContext.getBean(ButtonPermissionTag.class);
            }
        };
    }




    /**
     * 配置beetl
     * @return
     */
    @Bean(name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();

        //获取Spring Boot 的ClassLoader
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(loader==null){
            loader = BeetlConf.class.getClassLoader();
        }

        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

        //注入容器管理的自定义函数
        Map<String, Function> funMap = new HashMap(3);
        funMap.put("dict", getBeetlDictFun());
        funMap.put("initDict", getBeetlDictInitFun());
        beetlGroupUtilConfiguration.setFunctions(funMap);

        //注入容器管理的自定义标签
        Map<String , TagFactory> tagMap = new HashMap<>();
        tagMap.put("dictTag" , getDictTagFactory());
        tagMap.put("shiroa" , getButtonPermissionTagFactory());
        beetlGroupUtilConfiguration.setTagFactorys(tagMap);


        try {
            ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader, templatesPath);
            beetlGroupUtilConfiguration.setResourceLoader(cploder);
            beetlGroupUtilConfiguration.init();
            //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
            beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);
            beetlGroupUtilConfiguration.getGroupTemplate().registerFunctionPackage("shiro", new BeetlShiroExt());

            //加载beetl配置文件
            beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
            return beetlGroupUtilConfiguration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 配置视图解析器
     * @param beetlGroupUtilConfiguration
     * @return
     */
    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(
            @Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {

        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setSuffix(suffix);
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

}
