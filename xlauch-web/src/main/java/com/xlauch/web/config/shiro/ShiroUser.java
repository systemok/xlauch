package com.xlauch.web.config.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述    : 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ShiroUser <br/>
 *
 * @author huanxy<br/>
 *         创建日期: 2017/11/2 10:39  <br/>
 * @version 0.1
 */
@Data
@AllArgsConstructor
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -1383760761780840081L;
    public Long userId;
    public String userName;
    public boolean superAdmin;
    public String email;
    public String nickName;
    public List<Integer> roleIds;
    public List<String> permissions;

}
