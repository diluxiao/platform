package com.haiwen.platform.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiwen.platform.service.entity.Permission;
import com.haiwen.platform.service.entity.Role;

import java.util.List;

/**
 * 系统权限表 服务器层
 *
 * @author hc
 * @date 2019-04-27
 */
public interface PermissionService extends IService<Permission> {

    /**
     * @Author hecan
     * @Description 获取权限
     * @Date  2:07
     * @Param
     * @return
     * @param roleList
     */
    List<Permission> listByUserId(List<Role> roleList);
}

