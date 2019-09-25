package com.xlauch.core.config.mybatis;


/**
 * <p>
 *  类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/11/17
 */
public class DbContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     * @param dataSourceName
     */
    public static void setDbType(String dataSourceName) {
        contextHolder.set(dataSourceName);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDbType() {
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
    }


}
