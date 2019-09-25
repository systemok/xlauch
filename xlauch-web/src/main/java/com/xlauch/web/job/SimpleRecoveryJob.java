package com.xlauch.web.job;

import com.xlauch.core.config.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * <p>
 * 类描述：
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/8.
 */
@Slf4j
public class SimpleRecoveryJob implements Job {

    @Autowired
    RedisManager redisManager;

    private static final String COUNT = "count";

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public SimpleRecoveryJob() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code> fires that
     * is associated with the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();

        // if the job is recovering print a message
        if (context.isRecovering()) {
            log.info("SimpleRecoveryJob: " + jobKey + " RECOVERING at " + new Date());
        } else {
            log.info("SimpleRecoveryJob: " + jobKey + " starting at " + new Date());
        }

        // delay for ten seconds
        long delay = 10L * 1000L;
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            //
        }

        JobDataMap data = context.getJobDetail().getJobDataMap();
        int count;
        if (data.containsKey(COUNT)) {
            count = data.getInt(COUNT);
        } else {
            count = 0;
        }
        count++;
        data.put(COUNT, count);

        log.info("SimpleRecoveryJob: " + jobKey + " done at " + new Date() + "\n Execution #" + count);

    }

}