package com.xlauch.web.config.init;

import com.xlauch.core.config.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 类描述：清除REDIS缓存
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/11/21.
 */
@Configuration
public class RedisClear {

    @Autowired
    RedisManager redisManager;

    @Value("sys.dict.key")
    private String dictKey;

    @Bean
    public String clearDict(){
        redisManager.del(dictKey);
        return "";
    }

}
