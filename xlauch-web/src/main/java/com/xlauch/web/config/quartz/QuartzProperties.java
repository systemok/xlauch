package com.xlauch.web.config.quartz;

import com.xlauch.core.config.mybatis.DruidUtils;
import com.xlauch.core.exception.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * <p>
 * 类描述：
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/8.
 */
@Configuration
@Slf4j
public class QuartzProperties {

    @Value("${org.quartz.scheduler.instanceName}")
    private String instanceName;

    @Value("${org.quartz.scheduler.instanceId}")
    private String instanceId;

    @Value("${org.quartz.scheduler.skipUpdateCheck}")
    private String skipUpdateCheck;

    @Value("${org.quartz.threadPool.class}")
    private String threadClass;

    @Value("${org.quartz.threadPool.threadCount}")
    private String threadCount;

    @Value("${org.quartz.threadPool.threadPriority}")
    private String threadPriority;

    @Value("${org.quartz.jobStore.misfireThreshold}")
    private String misfireThreshold;

    @Value("${org.quartz.jobStore.class}")
    private String jobStoreClass;

    @Value("${org.quartz.jobStore.driverDelegateClass}")
    private String driverDelegateClass;

    @Value("${org.quartz.jobStore.useProperties}")
    private String useProperties;

    @Value("${org.quartz.jobStore.dataSource}")
    private String dataSource;

    @Value("${org.quartz.jobStore.tablePrefix}")
    private String tablePrefix;

    @Value("${org.quartz.jobStore.isClustered}")
    private String isClustered;

    @Value("${org.quartz.dataSource.qzDS.driver}")
    private String qzDSDriver;

    @Value("${org.quartz.dataSource.qzDS.URL}")
    private String qzDSURL;

    @Value("${org.quartz.dataSource.qzDS.user}")
    private String qzDSUser;

    @Value("${org.quartz.dataSource.qzDS.password}")
    private String qzDSPassword;

    @Value("${org.quartz.dataSource.qzDS.maxConnection}")
    private String maxConnection;

    @Value("${org.quartz.dataSource.qzDS.validateOnCheckout}")
    private String validateOnCheckout;

    @Value("${org.quartz.dataSource.qzDS.validationQuery}")
    private String validationQuery;

    public Properties getQuartzProperties(){
        if(!StringUtils.isBlank(qzDSPassword)){
            try {
                qzDSPassword = DruidUtils.decryptPassword(qzDSPassword);
            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTraceAsString(e));
            }
        }
        Properties properties = new Properties();
        properties.put("org.quartz.scheduler.instanceName", instanceName);
        properties.put("org.quartz.scheduler.instanceId", instanceId);
        properties.put("org.quartz.scheduler.skipUpdateCheck", skipUpdateCheck);
        properties.put("org.quartz.threadPool.class", threadClass);
        properties.put("org.quartz.threadPool.threadCount", threadCount);
        properties.put("org.quartz.threadPool.threadPriority", threadPriority);
        properties.put("org.quartz.jobStore.misfireThreshold", misfireThreshold);
        properties.put("org.quartz.jobStore.class", jobStoreClass);
        properties.put("org.quartz.jobStore.driverDelegateClass", driverDelegateClass);
        properties.put("org.quartz.jobStore.useProperties", useProperties);
        properties.put("org.quartz.jobStore.dataSource", dataSource);
        properties.put("org.quartz.jobStore.tablePrefix", tablePrefix);
        properties.put("org.quartz.jobStore.isClustered", isClustered);
        properties.put("org.quartz.dataSource.qzDS.driver", qzDSDriver);
        properties.put("org.quartz.dataSource.qzDS.URL", qzDSURL);
        properties.put("org.quartz.dataSource.qzDS.user", qzDSUser);
        properties.put("org.quartz.dataSource.qzDS.password", qzDSPassword);
        properties.put("org.quartz.dataSource.qzDS.maxConnection", maxConnection);
        properties.put("org.quartz.dataSource.qzDS.validateOnCheckout", validateOnCheckout );
        properties.put("org.quartz.dataSource.qzDS.validationQuery", validationQuery  );
        return properties;
    }

}
