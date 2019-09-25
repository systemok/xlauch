package com.xlauch.core.config.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 类描述 : 数据源配置
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/11/17
 */
@Configuration
public class DataSourceConfig {

    /**
     * 数据源 -- 数据库类型映射
     */
    public static Map<String , String > dataSouceMap = new HashMap<>() ;


    /**
     * 配置动态数据源
     * @param dataSourcePrimary
     * @param dataSourceSecond
     * @return
     */
    @Primary
    @Bean
//    @Lazy
    public DynamicDataSource dataSource(@Qualifier("dataSourcePrimary") DataSource dataSourcePrimary ,
                                 @Qualifier("dataSourceSecond") DataSource dataSourceSecond) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        Map targetDataSources = new HashMap<>(2);
        targetDataSources.put("dataSourcePrimary" , dataSourcePrimary) ;
        targetDataSources.put("dataSourceSecond" , dataSourceSecond) ;

        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(dataSourcePrimary);
        return dynamicDataSource ;
    }


    /**
     * 主数据源
     * @return
     */
//    @Primary
    @Bean("dataSourcePrimary")
    @ConfigurationProperties("spring.datasource.druid.primary")
    public DataSource dataSourcePrimary() {
        dataSouceMap.put("dataSourcePrimary" , "mysql") ;
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 从数据源
     * @return
     */
    @Bean("dataSourceSecond")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource dataSourceSecond() {
        dataSouceMap.put("dataSourceSecond" , "mysql") ;
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 根据数据源名称获取数据库类型
     * @param dataSourceName
     * @return
     */
    public static String getDbType(String dataSourceName) {
        return dataSouceMap.get(dataSourceName) == null ? "mysql" : dataSouceMap.get(dataSourceName) ;
    }
}
