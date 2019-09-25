package com.xlauch.web.config.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 类描述    :  图形验证码配置  <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : KaptchaConfig <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/8 14:53  <br/>
 * @version 0.1
 */
@Configuration
public class KaptchaConfig {


    @Value(value = "${kaptcha.border:yes}")
    String isBorder;

    @Value(value = "${kaptcha.border.color:105,179,90}")
    String borderColor;

    @Value(value = "${kaptcha.textproducer.font.color:blue}")
    String fontColor;

    @Value(value = "${kaptcha.image.width:125}")
    String imgWidth;

    @Value(value = "${kaptcha.image.height:45}")
    String imgHeight;

    @Value(value = "${kaptcha.textproducer.font.size:45}")
    String fontSize;

    @Value(value = "${kaptcha.session.key:code}")
    String sessionKey;

    @Value(value = "${kaptcha.textproducer.char.string:0123456789}")
    String charString;

    @Value(value = "${kaptcha.textproducer.char.length:4}")
    String charLength;

    @Value(value = "${kaptcha.textproducer.font.names:宋体,楷体,微软雅黑}")
    String fontName;


    @Bean("codeConfig")
    public Config getCodeConfig(){
        Properties kaptchaProperties = new Properties();
        kaptchaProperties.setProperty("kaptcha.border", isBorder);
        kaptchaProperties.setProperty("kaptcha.border.color", borderColor);
        kaptchaProperties.setProperty("kaptcha.textproducer.font.color", fontColor);
        kaptchaProperties.setProperty("kaptcha.image.width", imgWidth);
        kaptchaProperties.setProperty("kaptcha.image.height", imgHeight);
        kaptchaProperties.setProperty("kaptcha.textproducer.font.size", fontSize);
        kaptchaProperties.setProperty("kaptcha.session.key", sessionKey);
        kaptchaProperties.setProperty("kaptcha.textproducer.char.string", charString);
        kaptchaProperties.setProperty("kaptcha.textproducer.char.length", charLength);
        kaptchaProperties.setProperty("kaptcha.textproducer.font.names", fontName);
        Config config = new Config(kaptchaProperties);
        return config;
    }

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(getCodeConfig());
        return defaultKaptcha;
    }

}
