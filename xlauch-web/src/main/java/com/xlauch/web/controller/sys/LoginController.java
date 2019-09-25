package com.xlauch.web.controller.sys;

import com.xlauch.core.exception.IncorrectCaptchaException;
import com.xlauch.web.config.shiro.ShiroUserNamePassToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述    : 登录 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : LoginController <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/8 14:53  <br/>
 * @version 0.1
 */
@Controller
public class LoginController {

    /**
     * PC端登录
     */
    @GetMapping(value={"/","/login",""})
    public String login(HttpServletRequest request,Model model) {
        String domain = "";
        String serverName = request.getServerName();
        boolean isAu = SecurityUtils.getSubject().isAuthenticated();
        boolean isRem = SecurityUtils.getSubject().isRemembered();
        if(isAu||isRem){
            return "redirect:/main";
        }

        return "login";
    }

    /**
     * PC端登录
     */
    @PostMapping(value={"/","/login",""})
    public String login1(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
        String host = request.getRemoteHost();
        //session中的图形码字符串
        String capt = (String) ((HttpServletRequest)request).getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //比对
        if (capt != null && !capt.equalsIgnoreCase(captcha)) {
            redirectAttributes.addFlashAttribute("msg", "验证码错误！");
            return "redirect:login";
        }
        ShiroUserNamePassToken token =  new ShiroUserNamePassToken(username,password,rememberMe,host,captcha);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            //验证是否登录成功
            if(currentUser.isAuthenticated()){
                //设置会话的过期时间--ms,默认是30分钟，设置负数表示永不过期
                return "redirect:main";
            }else{
                token.clear();
                return "redirect:main";
            }
        }catch (AuthenticationException e) {
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
            redirectAttributes.addFlashAttribute("msg", error);
            return "redirect:login";
        }
//        return "login";
    }

    @RequestMapping(value="loginfail")
    public String loginfail(HttpServletRequest request,Model model) {

        return "login";
    }


}
