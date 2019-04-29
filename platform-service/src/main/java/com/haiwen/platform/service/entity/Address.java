package com.haiwen.platform.service.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haiwen.platform.service.base.BaseEntity;
import lombok.Data;

/**
 * 行政区域地址表
 *
 * @author hc
 * @date 2019-04-27
 */
@Data
@TableName("sys_address")
public class Address extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 上级区域ID
     */
    private Long pid;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 区域编码
     */
    private String code;

    /**
     * 行政区域层级(1：省、2：市、3：区/县)
     */
    private Boolean level;

    /**
     * 排序
     */
    private Integer sort;

}
