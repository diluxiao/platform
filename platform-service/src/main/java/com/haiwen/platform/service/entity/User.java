package com.haiwen.platform.service.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haiwen.platform.service.base.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户表
 *
 * @author hc
 * @date 2019-04-24
 */
@Data
@TableName("sys_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 注册IP
     */
    private String regIp;

    /**
     * 最近登录IP
     */
    private String lastLoginIp;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 用户备注
     */
    private String remark;

    /**
     * 状态(1：正常  2：冻结 ）
     */
    private Integer status;

}
