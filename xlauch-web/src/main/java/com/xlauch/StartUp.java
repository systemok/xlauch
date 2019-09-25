package com.xlauch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * 类描述    : 启动文件 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : StartUp <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/25 15:32  <br/>
 * @version 0.1
 */
@ServletComponentScan
@SpringBootApplication
public class StartUp  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StartUp.class);
	    app.run(args);
	}

}