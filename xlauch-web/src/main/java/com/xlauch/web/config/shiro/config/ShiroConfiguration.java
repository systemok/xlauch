package com.xlauch.web.config.shiro.config;

import com.xlauch.web.config.shiro.ShiroDbRealm;
import com.xlauch.web.config.shiro.ShiroFormAuthenFilter;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类描述    : shiro配置类   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ShiroConfiguration <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:39  <br/>
 * @version 0.1
 */
@Configuration
public class ShiroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean(name = "rememberMeCookie")
    public SimpleCookie getSimpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMeTest");
        simpleCookie.setMaxAge(1209600);
        return simpleCookie;
    }

    @Bean(name = "rememberMeManager")
    public CookieRememberMeManager getCookieRememberMeManager(@Qualifier(value = "rememberMeCookie")SimpleCookie simpleCookie){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        return cookieRememberMeManager;
    }

    @Bean(name = "servletContainerSessionManager")
    public ServletContainerSessionManager getServletContainerSessionManager(){
        ServletContainerSessionManager servletContainerSessionManager = new ServletContainerSessionManager();
        return servletContainerSessionManager;
    }

    @Bean(name = "myShiroCasRealm")
    public ShiroDbRealm myShiroCasRealm() {
        ShiroDbRealm realm = new ShiroDbRealm();
        realm.setAuthorizationCachingEnabled (false);
        return realm;
    }

    @Bean
    public DefaultWebSubjectFactory getDefaultWebSubjectFactory(){
        return new DefaultWebSubjectFactory();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier(value = "myShiroCasRealm")ShiroDbRealm shiroDbRealm,
                                                                  @Qualifier(value = "servletContainerSessionManager")ServletContainerSessionManager servletContainerSessionManager,
                                                                  @Qualifier(value = "rememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(shiroDbRealm);
        dwsm.setSessionManager(servletContainerSessionManager);
        dwsm.setRememberMeManager(cookieRememberMeManager);
        dwsm.setSubjectFactory(getDefaultWebSubjectFactory());
        return dwsm;
    }

    /**
     * CAS过滤器
     *
     * @return
     */
    @Bean(name = "myLoginFilter")
    public ShiroFormAuthenFilter getCasFilter() {
        ShiroFormAuthenFilter casFilter = new ShiroFormAuthenFilter();
        return casFilter;
    }

    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier(value = "securityManager")DefaultWebSecurityManager securityManager,
                                                            @Qualifier(value = "myLoginFilter")ShiroFormAuthenFilter myLoginFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/main");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 添加casFilter到shiroFilter中
        Map<String, Filter> filters = new HashMap();
        filters.put("authc", myLoginFilter);
//        filters.put("logout", myCasLogoutFilter);
//        shiroFilterFactoryBean.setFilters(filters);

        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
        /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

//        filterChainDefinitionMap.put("/login", "authc");// shiro集成cas后，首先添加该规则
        filterChainDefinitionMap.put("/logout", "logout");

        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        filterChainDefinitionMap.put("/center", "user");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则

        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/code/**", "anon");
        filterChainDefinitionMap.put("/res/**", "anon");
        filterChainDefinitionMap.put("/**", "user");//anon 可以理解为不拦截

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }


    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager});
        return bean;
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     *
     * @return
     * @author SHANHY
     * @create  2016年1月13日
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

}
