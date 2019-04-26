package com.haiwen.platform.service.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;

/**
 * 系统权限表
 *
 * @author hc
 * @date 2019-04-27
 */
@Data
@TableName("sys_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 模块id
     */
    private Long moduleId;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 菜单功能描述
     */
    private String description;

    /**
     * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    private Integer menuType;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单权限编码
     */
    private String perms;

    /**
     * 菜单排序
     */
    private Integer sortNo;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否叶子节点:    1:是   0:不是
     */
    private Integer isLeaf;

    /**
     * 是否隐藏路由: 0否,1是
     */
    private Integer hidden;

}
