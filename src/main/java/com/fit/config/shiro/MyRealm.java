package com.fit.config.shiro;

import com.fit.entity.TBlogger;
import com.fit.service.TBloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @AUTO 自定义Realm
 * @Author AIM
 * @DATE 2024/12/6
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private TBloggerService bloggerService;

    /**
     * 为当限前登录的用户授予角色和权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 验证当前登录的用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        TBlogger blogger = new TBlogger();
        blogger.setUsername(userName);
        blogger = bloggerService.get(blogger);
        if (blogger != null) {
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger); // 当前用户信息存到session中
            return new SimpleAuthenticationInfo(blogger.getUsername(), blogger.getPassword(), "xx");
        } else {
            return null;
        }
    }
}