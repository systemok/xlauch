package com.xlauch.rest.controller.auth;

import cn.hutool.crypto.SecureUtil;
import com.xlauch.core.config.errorcodemsg.ResponseCode;
import com.xlauch.rest.config.JwtTokenUtil;
import com.xlauch.rest.entity.auth.BizUser;
import com.xlauch.rest.service.auth.IBizUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类描述：授权测试
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/21.
 */
@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IBizUserService bizUserService;


    @RequestMapping(value = "/${jwt.auth-path}")
    public Map createAuthenticationToken(String userName, String password) {
        Map paramMap = new HashMap();
        paramMap.put("username", userName);
        paramMap.put("pswd", SecureUtil.md5(password));
        List<BizUser> userList = bizUserService.selectByMap(paramMap);
        if (userList!= null && userList.size()>0) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(userList.get(0).getUserId()+"", randomKey);
            Map resultMap = new HashMap();
            resultMap.put("randomKey", randomKey);
            resultMap.put("token", token);
            return ResponseCode.writeSuccessResult(resultMap);
        } else {
            return ResponseCode.writeFail("123");
        }
    }

    @RequestMapping(value = "/v1/userlsit")
    public List getUserList(@RequestBody String user){
        System.out.println(user);
        return bizUserService.selectByMap(new HashMap<>());
    }

}
