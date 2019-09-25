package com.xlauch.rest.config;

import com.xlauch.rest.config.convert.DefaultFastjsonConfig;
import com.xlauch.rest.config.convert.WithSignMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * <p>
 * 类描述：拦截器配置
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/21.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).addPathPatterns("/v1/**");
        super.addInterceptors(registry);
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

}
