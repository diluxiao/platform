package com.haiwen.platform.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haiwen.platform.service.entity.Role;

import java.util.List;

/**
 * 系统角色表 服务器层
 *
 * @author hc
 * @date 2019-04-27
 */
public interface RoleService extends IService<Role> {

    /**
     * @Author hecan
     * @Description 角色查询
     * @Date  1:51
     * @Param
     * @return
     */
    List<Role> selectRoleByUserId(Long userId);
}

