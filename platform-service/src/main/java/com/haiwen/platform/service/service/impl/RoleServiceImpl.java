package com.haiwen.platform.service.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiwen.platform.service.entity.Role;
import com.haiwen.platform.service.entity.UserRole;
import com.haiwen.platform.service.mapper.RoleMapper;
import com.haiwen.platform.service.service.RoleService;
import com.haiwen.platform.service.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author hc
 * @since 2019-04-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<Role> selectRoleByUserId(Long userId) {
        List<Role> list = new ArrayList<>();
        List<UserRole> userRoleList = userRoleService.list(Wrappers.<UserRole>query().eq("user_id", userId));
        userRoleList.forEach(userRole -> {
            Role role = this.baseMapper.selectById(userRole.getRoleId());
            list.add(role);
        });
        return list;
    }
}
