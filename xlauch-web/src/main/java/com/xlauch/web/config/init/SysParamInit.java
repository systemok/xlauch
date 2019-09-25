package com.xlauch.web.config.init;

import com.alibaba.fastjson.JSON;
import com.xlauch.core.config.redis.RedisManager;
import com.xlauch.web.common.utils.SysParamUtils;
import com.xlauch.web.entity.sys.SysParam;
import com.xlauch.web.service.sys.ISysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>
 * 类描述 : 初始化系统参数
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/5
 */
@Configuration
public class SysParamInit {

    @Autowired
    private ISysParamService sysParamService;

    @Autowired
    private SysParamUtils sysParamUtils ;

    /**
     * 读取系统参数并设置缓存
     */
    @Bean
    public String paramsCacheInit() {
        List<SysParam> paramList = sysParamService.selectByMap(null);
        if (paramList != null) {
            sysParamUtils.addCache(paramList);
        }

        return "";
    }

}
