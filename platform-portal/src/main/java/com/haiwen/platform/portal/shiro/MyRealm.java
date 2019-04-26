package com.haiwen.platform.portal.shiro;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiwen.platform.service.entity.Permission;
import com.haiwen.platform.service.entity.Role;
import com.haiwen.platform.service.entity.User;
import com.haiwen.platform.service.service.PermissionService;
import com.haiwen.platform.service.service.RoleService;
import com.haiwen.platform.service.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 **/
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    @Lazy(value = true)
    private UserService userService;

    @Autowired
    @Lazy(value = true)
    private RoleService roleService;

    @Autowired
    @Lazy(value = true)
    private PermissionService permissionService;

    /**
     * @Author hecan
     * @Description 提供账户信息返回认证信息（用户的角色信息集合）
     * @Date  1:19
     * @Param
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //验证账号
        String username = (String) token.getPrincipal();
        Wrapper<User> query = new QueryWrapper<>();
        User user = userService.getOne(((QueryWrapper<User>) query).select("username", username));
        if (null == user) {
            throw new UnknownAccountException("账号不存在！");
        }

        if (user.getStatus() == 1) {
            throw new LockedAccountException("帐号已被锁定，禁止登录！");
        }
        // principal参数使用用户Id，方便动态刷新用户权限
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(username),
                getName()
        );
    }

    /**
     * @Author hecan
     * @Description 权限认证，为当前登录的Subject授予角色和权限（角色的权限信息集合）
     * @Date  1:19
     * @Param
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();

        //赋予角色
        List<Role> roleList = roleService.selectRoleByUserId(userId);
        roleList.forEach(role -> {
            info.addRole(role.getRoleName());
        });

        //授权
        List<Permission> permissionList =  permissionService.listByUserId(roleList);
        if(!CollectionUtils.isEmpty(permissionList)) {
            permissionList.forEach(permission -> {
                String permissionUrl = permission.getUrl();
                if (StringUtils.isNoneBlank(permissionUrl)) {
                    info.addStringPermission(permissionUrl);
                }
            });
        }
        return info;
    }
}
