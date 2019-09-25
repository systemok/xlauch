package com.xlauch.web.config.shiro;

import com.xlauch.core.exception.IncorrectCaptchaException;
import com.xlauch.utils.util.TextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 类描述    : shiro登录过滤器   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ShiroFormAuthenFilter <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:39  <br/>
 * @version 0.1
 */
public class ShiroFormAuthenFilter extends FormAuthenticationFilter {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FormAuthenticationFilter.class);
    public static final String DEFAULT_MANAGE_PARAM = "manage";
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
    public static final String DEFAULT_CHECK_CODE_PARAM = "checkCode";
    public static final String DEFAULT_PROJECTID_PARAM = "projectId";
    public static final String DEFAULT_ISAUTO_PARAM = "isAuto";
    private String manageParam = DEFAULT_MANAGE_PARAM;
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String checkCodeParam = DEFAULT_CHECK_CODE_PARAM;
    private String projectIdParam = DEFAULT_PROJECTID_PARAM;
    private String isAutoParam = DEFAULT_ISAUTO_PARAM;

    /**
     * 登录验证
     */
    @Override
    protected boolean executeLogin(ServletRequest request,
                                   ServletResponse response) throws Exception {
        ShiroUserNamePassToken token = createToken(request, response);
        try {
            doCaptchaValidate((HttpServletRequest)request, token.getCaptcha());
            Subject subject = getSubject(request, response);
            subject.login(token);//正常验证
            return onLoginSuccess(token, subject, request, response);
        }catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 覆盖默认实现（登录成功后自定义跳转）
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        super.issueSuccessRedirect(request, response);

        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String error = "登录失败，请重试.";
        String e1 = e.toString();
        if(StringUtils.isNotBlank(e1)){
            if (e1.contains("IncorrectCaptchaException")) {
                error = "验证码错误";
            }
            else if(e1.contains("DisabledAccountException")){
                error = "用户已被屏蔽,请登录其他用户";
            }
            else if(e1.contains("IncorrectCredentialsException") ||e1.contains("AuthenticationException")){
                error = "账号或密码错误,请重新输入";
            }
            else if(e1.contains("AccessPermisException")){
                error = "当前用户没有访问权限";
            }
        }
        try {
            request.setAttribute("msg",error);
            request.getRequestDispatcher("/loginfail").forward(request, response);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;//return true则response无法redirect、forward操作
    }

    @Override
    protected ShiroUserNamePassToken createToken(ServletRequest request, ServletResponse response){
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        //session中的图形码字符串
        String capt = (String) ((HttpServletRequest)request).getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        return new ShiroUserNamePassToken(username,password,rememberMe,host,captcha);
    }

    /**
     * 验证码校验
     * @param request
     * @param captcha
     */
    protected void doCaptchaValidate(HttpServletRequest request,String captcha) {
        //session中的图形码字符串
        String capt = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //比对
        if (capt != null && !capt.equalsIgnoreCase(captcha)) {
            throw new IncorrectCaptchaException("验证码错误！");
        }
    }

    public String getManageParam() {
        return manageParam;
    }
    protected String getManage(ServletRequest request){
        return WebUtils.getCleanParam(request, getManageParam());
    }

    public String getCaptchaParam() {
        return captchaParam;
    }
    protected String getCaptcha(ServletRequest request){
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

     public String getCheckCodeParam() {
        return checkCodeParam;
    }
    protected String getCheckCode(ServletRequest request){
        return WebUtils.getCleanParam(request, getCheckCodeParam());
    }

    public String getProjectIdParam() {
        return projectIdParam;
    }

    public String getIsAutoParam() {
        return isAutoParam;
    }

    protected String getProjectId(ServletRequest request){
        return  WebUtils.getCleanParam(request,getProjectIdParam());
    }

    public Boolean getIsAuto(ServletRequest request) {
        return WebUtils.isTrue(request, getIsAutoParam());
    }

}
