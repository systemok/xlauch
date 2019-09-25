package com.xlauch.web.common.utils;

import com.alibaba.fastjson.JSON;
import com.xlauch.core.config.redis.RedisManager;
import com.xlauch.utils.util.FastJsonUtils;
import com.xlauch.web.entity.sys.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * <p>
 * 类描述 : 系统参数工具类，用于获取
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/5
 */
@Configuration
public class SysParamUtils {


    @Autowired
    private RedisManager redisManager;

    @Value("sys.param.key")
    private String redisKey ;

    /**
     * 添加缓存
     * @param paramList
     */
    public void addCache(List<SysParam> paramList ){
        if(paramList != null ) {
            paramList.forEach(sysParam -> redisManager.hset(redisKey , sysParam.getName() , JSON.toJSONString(sysParam)));
        }
    }

    /**
     * 添加缓存
     * @param sysParam
     */
    public void addCache(SysParam sysParam) {
        if(sysParam != null) {
            redisManager.hset(redisKey , sysParam.getName() , JSON.toJSONString(sysParam));
        }
    }


    /**
     * 获取缓存
     * @param name
     * @return
     */
    public SysParam getSysParam(String name) {
        String str = redisManager.hget(redisKey , name);
        if (str == null) {
            return null  ;
        }
        return FastJsonUtils.toBean(str , SysParam.class);
    }

    /**
     * 获取缓存值
     * @param name
     * @return
     */
    public String getSysParamValue(String name) {
        SysParam sysParam = getSysParam(name) ;
        if (sysParam == null) {
            return null  ;
        }
        return sysParam.getValue() ;
    }





}
