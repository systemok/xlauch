package com.xlauch.rest.config;

import com.xlauch.core.context.ActionContext;
import com.xlauch.rest.config.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 类描述：jwt授权拦截器
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/21.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getParameterMap().toString());
        System.out.println(request.getContentType());
        if (request.getServletPath().equals("/" + jwtProperties.getAuthPath())) {
            return true;
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    return false;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                return false;
            }
        } else {
            //header没有带Bearer字段
            return false;
        }

        //校验签名
        String token = request.getHeader(jwtProperties.getHeader()).substring(7);

        //设置用户ID至上下文
        String userId = jwtTokenUtil.getUsernameFromToken(token);
        if (userId != null){
            ActionContext.getContext().setData(ActionContext.USER_ID, userId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        ActionContext.getContext().destory();
    }
}
