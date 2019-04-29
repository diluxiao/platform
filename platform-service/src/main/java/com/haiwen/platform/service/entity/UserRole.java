package com.haiwen.platform.service.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haiwen.platform.service.base.BaseEntity;
import lombok.Data;

/**
 * 系统用户角色关系表
 *
 * @author hc
 * @date 2019-04-27
 */
@Data
@TableName("sys_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

}
