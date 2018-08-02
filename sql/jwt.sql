/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : jwt

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-29 20:31:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `billing_record`
-- ----------------------------
DROP TABLE IF EXISTS `billing_record`;
CREATE TABLE `billing_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `COMPANY_NAME` varchar(100) DEFAULT NULL COMMENT '所属公司',
  `REG_NAME` varchar(50) DEFAULT NULL COMMENT '注册人',
  `INDIVIDUAL_NAME` varchar(255) DEFAULT NULL COMMENT '个体名称',
  `CREDIT_CODE` varchar(100) DEFAULT NULL COMMENT '信用代码',
  `TAX_AMOUNT` double(9,2) DEFAULT NULL COMMENT '含税金额',
  `NO_TAX_AMOUNT` double(9,2) DEFAULT NULL COMMENT '不含税金额',
  `TAX` double(9,2) DEFAULT NULL COMMENT '税金',
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of billing_record
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_authority`
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_desc` varchar(255) DEFAULT NULL,
  `auth_key` varchar(255) DEFAULT NULL,
  `auth_name` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES ('6', '用户编辑', 'user:edit', '用户编辑', '1', '2018-06-15 13:47:25', 'admin', '0', '2018-06-15 13:47:25', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('7', '用户查询', 'user:view', '用户查询', '1', '2018-06-15 13:47:22', 'admin', '0', '2018-06-15 13:47:22', 'admin', '2');
INSERT INTO `sys_authority` VALUES ('8', '用户审核', 'user:aduit', '用户审核', '2', '2018-06-15 13:49:27', 'admin', '0', '2018-06-15 13:49:27', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('9', '角色查询', 'role:view', '角色查询', '3', '2018-06-15 13:50:31', 'admin', '0', '2018-06-15 13:50:31', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('10', '角色编辑', 'role:edit', '角色编辑', '4', '2018-06-15 13:50:53', 'admin', '0', '2018-06-15 13:50:53', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('11', '菜单查询', 'resource:view', '菜单查询', '5', '2018-06-15 13:51:07', 'admin', '0', '2018-06-15 13:51:07', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('12', '菜单编辑', 'resource:edit', '菜单编辑', '6', '2018-06-15 13:51:16', 'admin', '0', '2018-06-15 13:51:16', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('13', '权限查询', 'authority:view', '权限查询', '7', '2018-06-15 13:51:38', 'admin', '0', '2018-06-15 13:51:38', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('14', '权限编辑', 'authority:edit', '权限编辑', '8', '2018-06-15 13:51:50', 'admin', '0', '2018-06-15 13:51:50', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('15', '报表导入查询', 'billingRecord:view', '报表导入查询', '9', '2018-06-15 13:55:10', 'admin', '0', '2018-06-15 13:55:10', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('16', '报表导入编辑', 'billingRecord:edit', '报表导入编辑', '10', '2018-06-15 13:55:23', 'admin', '0', '2018-06-15 13:55:23', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('17', '京东采购价查询', 'purchase:view', '京东采购价查询', '11', '2018-07-28 16:44:33', 'admin', '0', '2018-07-28 16:44:33', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('18', '京东采购价编辑', 'purchase:edit', '京东采购价编辑', '12', '2018-07-28 16:44:43', 'admin', '0', '2018-07-28 16:44:43', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('19', '采购返利编辑', 'purchaseRebate:view', '采购返利查询', '13', '2018-07-28 17:13:24', 'admin', '0', '2018-07-28 17:13:24', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('20', '采购返利编辑', 'purchaseRebate:edit', '采购返利编辑', '14', '2018-07-28 17:13:52', 'admin', '0', '2018-07-28 17:13:52', 'admin', '2');
INSERT INTO `sys_authority` VALUES ('21', '优惠卷查询', 'coupon:view', '优惠卷查询', '15', '2018-07-28 19:22:09', 'admin', '0', '2018-07-28 19:22:09', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('22', '优惠卷编辑', 'coupon:edit', '优惠卷编辑', '16', '2018-07-28 19:23:43', 'admin', '0', '2018-07-28 19:23:43', 'admin', '2');
INSERT INTO `sys_authority` VALUES ('23', '销售查询', 'sale:view', '销售查询', '17', '2018-07-28 19:44:35', 'admin', '0', '2018-07-28 19:44:35', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('24', '销售编辑', 'sale:edit', '销售编辑', '18', '2018-07-28 19:44:47', 'admin', '0', '2018-07-28 19:44:47', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('25', '快车明细查询', 'expressTrain:view', '快车明细查询', '19', '2018-07-28 19:45:07', 'admin', '0', '2018-07-28 19:45:07', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('26', '快车明细编辑', 'expressTrain:edit', '快车明细编辑', '20', '2018-07-28 19:45:24', 'admin', '0', '2018-07-28 19:45:24', 'admin', '1');
INSERT INTO `sys_authority` VALUES ('27', '小胖子报表导出', 'xuhlManage:view', '小胖子报表导出', '21', '2018-07-28 22:12:32', 'admin', '0', '2018-07-28 22:12:32', 'admin', '1');

-- ----------------------------
-- Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_desc` varchar(255) DEFAULT NULL,
  `resource_name` varchar(255) DEFAULT NULL,
  `icon_cls` varchar(255) DEFAULT NULL,
  `menu_url` varchar(255) DEFAULT NULL,
  `type` int(3) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgk85s9salorg3h1ugatqf13wp` (`pid`),
  CONSTRAINT `sys_authority_parent_fk1` FOREIGN KEY (`pid`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '系统管理', '系统管理', 'icon-2', '', '0', '1', null, '2018-06-05 16:56:25', 'admin', '0', '2018-06-05 16:56:25', 'admin', '3');
INSERT INTO `sys_resource` VALUES ('2', '用户管理', '用户管理', 'icon-user', '/user/userManager', '1', '2', '1', '2018-06-01 14:26:29', 'admin', '0', '2018-06-01 14:26:29', 'admin', '5');
INSERT INTO `sys_resource` VALUES ('3', '角色管理', '角色管理', 'icon-group', '/role/roleManager', '1', '3', '1', '2018-06-08 14:59:00', 'admin', '0', '2018-06-08 14:59:00', 'admin', '3');
INSERT INTO `sys_resource` VALUES ('4', '菜单管理', '菜单管理', 'icon-menu', '/resource/resourceManager', '1', '4', '1', '2018-06-05 10:25:41', 'admin', '0', '2018-06-05 10:25:41', 'admin', '10');
INSERT INTO `sys_resource` VALUES ('9', '报表管理', '报表管理', 'icon-chart_bar', '', '0', '5', null, '2018-06-05 10:26:08', 'admin', '0', '2018-06-05 10:26:08', 'admin', '1');
INSERT INTO `sys_resource` VALUES ('10', '报表导入', '报表导入', 'icon-chart_pie', '/billingRecord/billingRecordPage', '1', '6', '9', '2018-06-19 10:15:51', 'admin', '0', '2018-06-19 10:15:51', 'admin', '4');
INSERT INTO `sys_resource` VALUES ('11', '权限管理', '权限管理', 'icon-vcard', '/authority/authorityManager', '1', '7', '1', '2018-06-15 13:54:09', 'admin', '0', '2018-06-15 13:54:09', 'admin', '3');
INSERT INTO `sys_resource` VALUES ('12', 'Api接口文档', 'Api接口文档', 'icon-server', '/swagger-ui.html', '1', '8', '1', '2018-06-20 13:49:09', 'admin', '0', '2018-06-20 13:49:09', 'admin', '1');
INSERT INTO `sys_resource` VALUES ('13', '小胖子管理', '小胖子管理', 'icon-map', '', '0', '9', null, '2018-07-28 16:42:15', 'admin', '0', '2018-07-28 16:42:15', 'admin', '1');
INSERT INTO `sys_resource` VALUES ('14', '京东采购价', '京东采购价', 'icon-chart_bar', '/purchase/purchasePage', '1', '10', '13', '2018-07-28 16:43:28', 'admin', '0', '2018-07-28 16:43:28', 'admin', '1');
INSERT INTO `sys_resource` VALUES ('15', '采购返利', '采购返利', 'icon-chart_bar', '/purchaseRebate/purchaseRebatePage', '1', '11', '13', '2018-07-28 17:12:56', 'admin', '0', '2018-07-28 17:12:56', 'admin', '1');
INSERT INTO `sys_resource` VALUES ('16', '优惠卷', '优惠卷', 'icon-chart_bar', '/coupon/couponPage', '1', '12', '13', '2018-07-28 19:21:52', 'admin', '0', '2018-07-28 19:21:52', 'admin', '1');
INSERT INTO `sys_resource` VALUES ('17', '销售', '销售', 'icon-chart_bar', '/sale/salePage', '1', '13', '13', '2018-07-28 19:43:01', 'admin', '0', '2018-07-28 19:43:01', 'admin', '2');
INSERT INTO `sys_resource` VALUES ('18', '快车明细', '快车明细', 'icon-chart_bar', '/expressTrain/expressTrainPage', '1', '14', '13', '2018-07-28 19:44:13', 'admin', '0', '2018-07-28 19:44:13', 'admin', '1');
INSERT INTO `sys_resource` VALUES ('19', '小胖子数据报表', '小胖子数据报表', 'icon-chart_bar', '/xuhlManage/xuhlManagePage', '1', '15', '13', '2018-07-28 22:12:14', 'admin', '0', '2018-07-28 22:12:14', 'admin', '1');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_desc` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin', '2018-05-23 10:51:09', 'admin', '0', '2018-06-05 10:14:01', 'admin', '2');
INSERT INTO `sys_role` VALUES ('5', '系统管理员', '系统管理员', '2018-05-23 13:59:47', 'admin', '0', '2018-06-20 13:49:20', 'admin', '11');
INSERT INTO `sys_role` VALUES ('6', '普通用户', '普通用户', '2018-05-23 13:59:03', 'admin', '0', '2018-06-19 15:48:05', 'admin', '5');
INSERT INTO `sys_role` VALUES ('11', '执行机构', 'ACTUATOR', '2018-06-19 17:19:42', 'admin', '0', '2018-06-19 17:21:01', 'admin', '4');
INSERT INTO `sys_role` VALUES ('12', '京东采购价管理员', '京东采购价管理员', '2018-07-28 16:46:35', 'admin', '0', '2018-07-28 16:46:35', 'admin', '1');
INSERT INTO `sys_role` VALUES ('13', '采购返利管理员', '采购返利管理员', '2018-07-28 17:14:11', 'admin', '0', '2018-07-28 17:14:11', 'admin', '1');
INSERT INTO `sys_role` VALUES ('14', '优惠卷管理员', '优惠卷管理员', '2018-07-28 19:22:33', 'admin', '0', '2018-07-28 19:22:33', 'admin', '1');
INSERT INTO `sys_role` VALUES ('15', '销售管理员', '销售管理员', '2018-07-28 19:45:39', 'admin', '0', '2018-07-28 19:45:39', 'admin', '1');
INSERT INTO `sys_role` VALUES ('16', '快车明细管理员', '快车明细管理员', '2018-07-28 19:45:52', 'admin', '0', '2018-07-28 19:45:52', 'admin', '1');
INSERT INTO `sys_role` VALUES ('17', '小胖子报表管理', '小胖子报表管理', '2018-07-28 22:12:57', 'admin', '0', '2018-07-28 22:12:57', 'admin', '1');

-- ----------------------------
-- Table structure for `sys_role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority` (
  `role_id` int(11) NOT NULL,
  `auth_id` int(11) NOT NULL,
  KEY `FK5mvy8agra2gt49q126dixqeum` (`auth_id`),
  KEY `FKbcglbkyva6m7rsl9iay16t5os` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_authority
-- ----------------------------
INSERT INTO `sys_role_authority` VALUES ('1', '1');
INSERT INTO `sys_role_authority` VALUES ('1', '2');
INSERT INTO `sys_role_authority` VALUES ('1', '3');
INSERT INTO `sys_role_authority` VALUES ('1', '4');
INSERT INTO `sys_role_authority` VALUES ('1', '6');
INSERT INTO `sys_role_authority` VALUES ('1', '7');
INSERT INTO `sys_role_authority` VALUES ('6', '7');
INSERT INTO `sys_role_authority` VALUES ('6', '9');
INSERT INTO `sys_role_authority` VALUES ('6', '11');
INSERT INTO `sys_role_authority` VALUES ('6', '13');
INSERT INTO `sys_role_authority` VALUES ('6', '15');
INSERT INTO `sys_role_authority` VALUES ('6', '16');
INSERT INTO `sys_role_authority` VALUES ('5', '6');
INSERT INTO `sys_role_authority` VALUES ('5', '7');
INSERT INTO `sys_role_authority` VALUES ('5', '8');
INSERT INTO `sys_role_authority` VALUES ('5', '9');
INSERT INTO `sys_role_authority` VALUES ('5', '10');
INSERT INTO `sys_role_authority` VALUES ('5', '11');
INSERT INTO `sys_role_authority` VALUES ('5', '12');
INSERT INTO `sys_role_authority` VALUES ('5', '13');
INSERT INTO `sys_role_authority` VALUES ('5', '14');
INSERT INTO `sys_role_authority` VALUES ('5', '15');
INSERT INTO `sys_role_authority` VALUES ('5', '16');
INSERT INTO `sys_role_authority` VALUES ('12', '17');
INSERT INTO `sys_role_authority` VALUES ('12', '18');
INSERT INTO `sys_role_authority` VALUES ('13', '19');
INSERT INTO `sys_role_authority` VALUES ('13', '20');
INSERT INTO `sys_role_authority` VALUES ('14', '21');
INSERT INTO `sys_role_authority` VALUES ('14', '22');
INSERT INTO `sys_role_authority` VALUES ('15', '23');
INSERT INTO `sys_role_authority` VALUES ('15', '24');
INSERT INTO `sys_role_authority` VALUES ('16', '25');
INSERT INTO `sys_role_authority` VALUES ('16', '26');
INSERT INTO `sys_role_authority` VALUES ('17', '27');

-- ----------------------------
-- Table structure for `sys_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  KEY `FK5mvy8agra2gt49q126dixqeum` (`resource_id`),
  KEY `FKbcglbkyva6m7rsl9iay16t5os` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1');
INSERT INTO `sys_role_resource` VALUES ('1', '2');
INSERT INTO `sys_role_resource` VALUES ('1', '3');
INSERT INTO `sys_role_resource` VALUES ('1', '4');
INSERT INTO `sys_role_resource` VALUES ('1', '5');
INSERT INTO `sys_role_resource` VALUES ('1', '8');
INSERT INTO `sys_role_resource` VALUES ('6', '2');
INSERT INTO `sys_role_resource` VALUES ('6', '9');
INSERT INTO `sys_role_resource` VALUES ('6', '10');
INSERT INTO `sys_role_resource` VALUES ('5', '1');
INSERT INTO `sys_role_resource` VALUES ('5', '2');
INSERT INTO `sys_role_resource` VALUES ('5', '3');
INSERT INTO `sys_role_resource` VALUES ('5', '4');
INSERT INTO `sys_role_resource` VALUES ('5', '9');
INSERT INTO `sys_role_resource` VALUES ('5', '10');
INSERT INTO `sys_role_resource` VALUES ('5', '11');
INSERT INTO `sys_role_resource` VALUES ('5', '12');
INSERT INTO `sys_role_resource` VALUES ('12', '13');
INSERT INTO `sys_role_resource` VALUES ('12', '14');
INSERT INTO `sys_role_resource` VALUES ('13', '15');
INSERT INTO `sys_role_resource` VALUES ('14', '16');
INSERT INTO `sys_role_resource` VALUES ('15', '17');
INSERT INTO `sys_role_resource` VALUES ('16', '18');
INSERT INTO `sys_role_resource` VALUES ('17', '19');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auto_login_key` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `aduit_status` int(11) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2', null, '$2a$08$Mu8E6GcCdg.pQ0.UitxKcOaZGgp5OqhCToxFJuYXyj4at0.NaIN6C', '2018-05-23 10:53:59', '', '0', '2018-07-28 22:13:04', 'admin', '7', '1', 'admin');
INSERT INTO `sys_user` VALUES ('3', null, '$2a$08$Mu8E6GcCdg.pQ0.UitxKcOaZGgp5OqhCToxFJuYXyj4at0.NaIN6C', '2018-05-23 11:11:03', 'admin', '0', '2018-06-11 14:40:05', 'admin', '3', '1', 'user');
INSERT INTO `sys_user` VALUES ('10', null, '$2a$08$Mu8E6GcCdg.pQ0.UitxKcOaZGgp5OqhCToxFJuYXyj4at0.NaIN6C', '2018-06-04 16:42:51', 'admin', '0', '2018-06-06 16:58:08', 'admin', '5', '1', '测试用户');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKb40xxfch70f5qnyfw8yme1n1s` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('3', '6');
INSERT INTO `sys_user_role` VALUES ('10', '6');
INSERT INTO `sys_user_role` VALUES ('10', '14');
INSERT INTO `sys_user_role` VALUES ('2', '5');
INSERT INTO `sys_user_role` VALUES ('2', '11');
INSERT INTO `sys_user_role` VALUES ('2', '12');
INSERT INTO `sys_user_role` VALUES ('2', '13');
INSERT INTO `sys_user_role` VALUES ('2', '14');
INSERT INTO `sys_user_role` VALUES ('2', '15');
INSERT INTO `sys_user_role` VALUES ('2', '16');
INSERT INTO `sys_user_role` VALUES ('2', '17');

-- ----------------------------
-- Table structure for `xuhl_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `xuhl_coupon`;
CREATE TABLE `xuhl_coupon` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `IS_DELETE` int(11) DEFAULT NULL,
  `SKU_CODE` varchar(20) DEFAULT NULL COMMENT '税金',
  `SKU_NAME` varchar(200) DEFAULT NULL,
  `CONCESSION_VOLUME_REDUCTION` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=640 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `xuhl_express_train`
-- ----------------------------
DROP TABLE IF EXISTS `xuhl_express_train`;
CREATE TABLE `xuhl_express_train` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `IS_DELETE` int(11) DEFAULT NULL,
  `SKU_CODE` varchar(20) DEFAULT NULL COMMENT '税金',
  `TOTAL_COST` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=403 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `xuhl_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `xuhl_purchase`;
CREATE TABLE `xuhl_purchase` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `IS_DELETE` int(11) DEFAULT NULL,
  `SKU_CODE` varchar(20) DEFAULT NULL COMMENT '税金',
  `SKU_NAME` varchar(200) DEFAULT NULL,
  `NUM` int(11) DEFAULT NULL,
  `PURCHASE_PRICE` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34702 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `xuhl_purchase_rebate`
-- ----------------------------
DROP TABLE IF EXISTS `xuhl_purchase_rebate`;
CREATE TABLE `xuhl_purchase_rebate` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `IS_DELETE` int(11) DEFAULT NULL,
  `SKU_CODE` varchar(20) DEFAULT NULL COMMENT '税金',
  `SKU_NAME` varchar(200) DEFAULT NULL,
  `TYPE` int(3) DEFAULT NULL,
  `NUM` int(11) DEFAULT NULL,
  `PURCHASE_REBATE_PRICE` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36631 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `xuhl_sale`
-- ----------------------------
DROP TABLE IF EXISTS `xuhl_sale`;
CREATE TABLE `xuhl_sale` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` varchar(36) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `IS_DELETE` int(11) DEFAULT NULL,
  `SKU_CODE` varchar(20) DEFAULT NULL COMMENT '税金',
  `SKU_NAME` varchar(200) DEFAULT NULL,
  `SALES_VOLUME` int(11) DEFAULT NULL,
  `SALES_PRICE` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=115843 DEFAULT CHARSET=utf8;

