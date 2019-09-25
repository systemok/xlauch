package com.xlauch.web.job;

import com.xlauch.core.config.redis.RedisManager;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 类描述：
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/8.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SimpleRecoveryStatefulJob extends SimpleRecoveryJob {

    public SimpleRecoveryStatefulJob() {
        super();
    }
}