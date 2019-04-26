package com.haiwen.platform.service.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiwen.platform.service.entity.Permission;
import com.haiwen.platform.service.entity.Role;
import com.haiwen.platform.service.entity.RolePermission;
import com.haiwen.platform.service.mapper.PermissionMapper;
import com.haiwen.platform.service.service.PermissionService;
import com.haiwen.platform.service.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author hc
 * @since 2019-04-27
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * @param roleList
     * @return
     */
    @Override
    public List<Permission> listByUserId(List<Role> roleList) {
        List<Permission> list = new ArrayList<>();
        Set<Permission> set = new TreeSet<>();
        roleList.forEach(role -> {
            List<RolePermission> rolePermissionList = rolePermissionService.list(Wrappers.<RolePermission>query().eq("role_id", role.getId()));
            rolePermissionList.forEach(rolePermission -> {
                Permission permission = this.baseMapper.selectById(rolePermission.getPermissionId());
                set.add(permission);
            });
        });
        set.forEach(permission -> {
            list.add(permission);
        });
        return list;
    }
}
