package com.xlauch.web.config.shiro;

import com.xlauch.web.entity.sys.SysPermission;
import com.xlauch.web.entity.sys.SysUser;
import com.xlauch.web.service.sys.ISysPermissionService;
import com.xlauch.web.service.sys.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述    :    <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ShiroDbRealm <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 10:39  <br/>
 * @version 0.1
 */
public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    protected ISysUserService iSysUserService;

    @Autowired
    private ISysPermissionService sysPermissionService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        ShiroUserNamePassToken token = (ShiroUserNamePassToken) authcToken;

        String roleName = "other";//默认角色名称
        Map paramMap = new HashMap();
        paramMap.put("username",token.getUsername());
        List<SysUser> userList = iSysUserService.selectByMap(paramMap);
        SysUser user = userList.get(0);
        if (null != user) {
            //管理员是否有效
            if(user.getStatus() == 0){
                throw new DisabledAccountException();
            }

            boolean superAdmin = false;
            if("admin".equals(user.getUsername())){
                superAdmin = true;
            }

            //管理员旗下的角色权限是否有效
//            List<Map> roleMap = userService.getRoleByAccount(token.getUsername());
            List<Map> roleMap =new ArrayList<>();
            List<Integer> roleIds = new ArrayList<>();
            /*if(roleMap!=null && roleMap.size()>0){
                for(Map map:roleMap){
                    roleIds.add(Integer.parseInt(String.valueOf(map.get("role_id"))));
                }
            }else{
                throw new DisabledRoleException();
            }*/

            Map permissionMap = new HashMap();
            permissionMap.put("parentId", 0);
            permissionMap.put("adminId", user.getUserId());
            permissionMap.put("button", 1);
            if(superAdmin) {
                permissionMap.put("adminId", -1);
            }
            List<Map> permissions = sysPermissionService.getSubMenuList(permissionMap);
            List<String> permissionList = new ArrayList();
            for(Map map : permissions){
                if(!StringUtils.isBlank(map.get("permissionCode")+"")){
                    permissionList.add(map.get("permissionCode")+"");
                }
            }

            ShiroUser shiroUser = new ShiroUser(user.getUserId(), user.getUsername(), superAdmin, user.getEmail(), user.getNickname(), roleIds, permissionList);

            return new SimpleAuthenticationInfo(shiroUser,
                    user.getPswd(), null, getName());

        } else{
            throw new AuthenticationException();
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();

        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!subject.isAuthenticated()&&!subject.isRemembered()) {
            doClearCache(principalCollection);
            subject.logout();
            return null;
        }

        ShiroUser shiroUser = (ShiroUser) subject.getPrincipals().getPrimaryPrincipal();
        // String userId = (String)
        // principalCollection.fromRealm(getName()).iterator().next();
        String userId = ""+shiroUser.userId;
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        // 添加角色及权限信息
        SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
        try {
//            sazi.addRole(""+shiroUser.roleId);
            sazi.addStringPermissions(shiroUser.getPermissions());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return sazi;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("MD5");
        setCredentialsMatcher(matcher);
    }


}
