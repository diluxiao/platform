/*
Navicat MySQL Data Transfer

Source Server         : hecan
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : haiwen

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2019-04-23 01:18:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_address
-- ----------------------------
DROP TABLE IF EXISTS `sys_address`;
CREATE TABLE `sys_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` bigint(20) NOT NULL COMMENT '上级区域ID',
  `name` varchar(50) DEFAULT NULL COMMENT '地区名称',
  `postcode` varchar(50) DEFAULT NULL COMMENT '邮编',
  `code` varchar(16) DEFAULT NULL COMMENT '区域编码',
  `level` tinyint(1) DEFAULT NULL COMMENT '行政区域层级(1：省、2：市、3：区/县)',
  `sort` tinyint(3) DEFAULT NULL COMMENT '排序',
  `trace_id` varchar(32) DEFAULT '' COMMENT '日志跟踪id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '最后修改人编码',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标识(0-正常,1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区域地址表';

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `service_code` varchar(64) NOT NULL COMMENT '服务编码',
  `module_name` varchar(64) NOT NULL COMMENT '模块编码',
  `module_mapping` varchar(255) DEFAULT NULL COMMENT '模块映射路径',
  `description` varchar(512) DEFAULT NULL COMMENT '模块描述',
  `trace_id` varchar(32) DEFAULT '' COMMENT '日志跟踪id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '最后修改人编码',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统模块表';

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint(20) NOT NULL COMMENT '父id',
  `name` varchar(64) NOT NULL COMMENT '菜单名称',
  `module_id` bigint(20) DEFAULT NULL COMMENT '模块id',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `description` varchar(512) DEFAULT NULL COMMENT '菜单功能描述',
  `menu_type` int(11) DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
  `redirect` varchar(255) DEFAULT NULL COMMENT '一级菜单跳转地址',
  `perms` varchar(255) DEFAULT NULL COMMENT '菜单权限编码',
  `sort_no` int(10) DEFAULT NULL COMMENT '菜单排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `is_leaf` tinyint(2) DEFAULT NULL COMMENT '是否叶子节点:    1:是   0:不是',
  `hidden` tinyint(2) DEFAULT NULL COMMENT '是否隐藏路由: 0否,1是',
  `trace_id` varchar(32) DEFAULT '' COMMENT '日志跟踪id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '最后修改人编码',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标识(0-正常,1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(255) NOT NULL COMMENT '角色名称',
  `role_code` varchar(255) NOT NULL COMMENT '角色编码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(1：正常  2：冻结 ）',
  `trace_id` varchar(32) DEFAULT '' COMMENT '日志跟踪id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '最后修改人编码',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标识(0-正常,1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `trace_id` varchar(32) DEFAULT '' COMMENT '日志跟踪id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '最后修改人编码',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标识(0-正常,1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(100) NOT NULL COMMENT '登录账号',
  `realname` varchar(255) NOT NULL COMMENT '真实姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT '密码盐',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` tinyint(2) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `reg_ip` varchar(30) DEFAULT NULL COMMENT '注册IP',
  `last_login_ip` varchar(30) DEFAULT NULL COMMENT '最近登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  `login_count` int(10) DEFAULT NULL COMMENT '登录次数',
  `remark` varchar(100) DEFAULT NULL COMMENT '用户备注',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(1：正常  2：冻结 ）',
  `trace_id` varchar(32) DEFAULT '' COMMENT '日志跟踪id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '最后修改人编码',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标识(0-正常,1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `trace_id` varchar(32) DEFAULT '' COMMENT '日志跟踪id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT '' COMMENT '最后修改人编码',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标识(0-正常,1-删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色关系表';
