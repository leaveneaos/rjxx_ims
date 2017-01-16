/*
Navicat MySQL Data Transfer

Source Server         : dzfp211
Source Server Version : 50541
Source Host           : 120.26.45.211:3306
Source Database       : taxeasy

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2016-09-20 14:47:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for roleprivs
-- ----------------------------
DROP TABLE IF EXISTS `roleprivs`;
CREATE TABLE `roleprivs` (
  `roleid` int(11) NOT NULL COMMENT '角色id',
  `privid` int(11) DEFAULT NULL COMMENT '菜单id',
  `ButtonPrivs` varchar(20) DEFAULT NULL,
  `ztbz` varchar(1) DEFAULT NULL COMMENT '状态标识',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `lrry` int(11) DEFAULT NULL COMMENT '录入人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `xgry` int(11) DEFAULT NULL COMMENT '修改人员'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roleprivs
-- ----------------------------
INSERT INTO `roleprivs` VALUES ('2', '2', 'smc', '1', null, null, '2016-09-19 11:08:18', '2', '2016-09-19 11:08:18', '2');
INSERT INTO `roleprivs` VALUES ('2', '3', 'smc', '1', null, null, '2016-09-19 11:08:18', '2', '2016-09-19 11:08:18', '2');
INSERT INTO `roleprivs` VALUES ('2', '4', 'smc', '1', null, null, '2016-09-19 11:08:18', '2', '2016-09-19 11:08:18', '2');
INSERT INTO `roleprivs` VALUES ('3', '4', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '5', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '6', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '7', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '8', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '9', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '10', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '11', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '12', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '13', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '14', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '15', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '16', 'smc', '1', null, null, '2016-09-19 16:49:26', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roleprivs` VALUES ('3', '17', 'smc', '1', null, null, '2016-09-19 16:49:27', '2', '2016-09-19 16:49:27', '2');
INSERT INTO `roleprivs` VALUES ('3', '18', 'smc', '1', null, null, '2016-09-19 16:49:27', '2', '2016-09-19 16:49:27', '2');
INSERT INTO `roleprivs` VALUES ('3', '19', 'smc', '1', null, null, '2016-09-19 16:49:27', '2', '2016-09-19 16:49:27', '2');
INSERT INTO `roleprivs` VALUES ('4', '4', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '5', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '6', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '7', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '8', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '9', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '10', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '11', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '12', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('4', '13', 'smc', '1', null, null, '2016-09-19 16:59:28', '2', '2016-09-19 16:59:28', '2');
INSERT INTO `roleprivs` VALUES ('1', '1', 'smc', '1', null, null, '2016-09-20 14:30:59', '2', '2016-09-20 14:30:59', '2');
INSERT INTO `roleprivs` VALUES ('1', '3', 'smc', '1', null, null, '2016-09-20 14:30:59', '2', '2016-09-20 14:30:59', '2');
INSERT INTO `roleprivs` VALUES ('1', '4', 'smc', '1', null, null, '2016-09-20 14:30:59', '2', '2016-09-20 14:30:59', '2');
INSERT INTO `roleprivs` VALUES ('1', '5', 'smc', '1', null, null, '2016-09-20 14:30:59', '2', '2016-09-20 14:30:59', '2');
INSERT INTO `roleprivs` VALUES ('1', '6', 'smc', '1', null, null, '2016-09-20 14:30:59', '2', '2016-09-20 14:30:59', '2');
INSERT INTO `roleprivs` VALUES ('1', '7', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '10', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '11', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '12', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '15', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '16', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '17', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '20', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
INSERT INTO `roleprivs` VALUES ('1', '22', 'smc', '1', null, null, '2016-09-20 14:31:00', '2', '2016-09-20 14:31:00', '2');
