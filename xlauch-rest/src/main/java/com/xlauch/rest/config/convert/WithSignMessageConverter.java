package com.xlauch.rest.config.convert;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.xlauch.core.exception.BussException;
import com.xlauch.core.supers.dto.SuperDto;
import com.xlauch.rest.config.JwtTokenUtil;
import com.xlauch.rest.config.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * <p>
 * 带签名的http信息转化器
 * </p>
 *
 * @author huangxy
 * @since 2018/1/6 15:41
 * @version 0.1
 */
@Slf4j
public class WithSignMessageConverter extends FastJsonHttpMessageConverter4 {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        InputStream in = inputMessage.getBody();
        Object o = JSON.parseObject(in, super.getFastJsonConfig().getCharset(), SuperDto.class, super.getFastJsonConfig().getFeatures());

        //先转化成原始的v对象
        SuperDto baseTransferEntity = (SuperDto) o;

        //校验签名
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);

        String object = baseTransferEntity.getObject();
        String json = new String(Base64Utils.decodeFromString(object));
        String encrypt = SecureUtil.md5(object + md5KeyFromToken);

        if (encrypt.equals(baseTransferEntity.getSign())) {
            log.info("签名校验成功!");
        } else {
            log.error("签名校验失败,数据被改动过!");
            throw new BussException("123");
        }

        //校验签名后再转化成应该的对象
        return JSON.parseObject(json, type);
    }
}
