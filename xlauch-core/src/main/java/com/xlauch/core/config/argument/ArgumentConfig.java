package com.xlauch.core.config.argument;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 类描述    : 自定义参数绑定<br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ArgumentConfig.java <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017年5月4日 上午11:09:20  <br/>
 * @version 0.1
 */
@Configuration
public class ArgumentConfig extends WebMvcConfigurerAdapter {

    /**
     * 描述：添加datagrid 参数绑定功能
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new DataGridArgumentResolver());
    }

    /**
     * 描述：配置fastjson转换
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        /**
         * 1.需要定义一个convert转换消息的对象
         * 2.创建配置信息，加入配置信息：比如是否需要格式化返回的json
         * 3.converter中添加配置信息
         * 4.convert添加到converters当中
         */
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);
    }

}
