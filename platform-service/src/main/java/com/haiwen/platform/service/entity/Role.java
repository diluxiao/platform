package com.haiwen.platform.service.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haiwen.platform.service.base.BaseEntity;
import lombok.Data;

/**
 * 系统角色表
 *
 * @author hc
 * @date 2019-04-27
 */
@Data
@TableName("sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态(1：正常  2：冻结 ）
     */
    private Integer status;

}
