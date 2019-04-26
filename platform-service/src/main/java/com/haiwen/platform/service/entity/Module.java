package com.haiwen.platform.service.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;

/**
 * 系统模块表
 *
 * @author hc
 * @date 2019-04-27
 */
@Data
@TableName("sys_module")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 服务编码
     */
    private String serviceCode;

    /**
     * 模块编码
     */
    private String moduleName;

    /**
     * 模块映射路径
     */
    private String moduleMapping;

    /**
     * 模块描述
     */
    private String description;

}
