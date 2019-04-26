package com.haiwen.platform.service.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;

/**
 * 角色权限关系表
 *
 * @author hc
 * @date 2019-04-27
 */
@Data
@TableName("sys_role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

}
