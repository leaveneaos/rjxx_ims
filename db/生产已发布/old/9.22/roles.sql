/*
Navicat MySQL Data Transfer

Source Server         : dzfp211
Source Server Version : 50541
Source Host           : 120.26.45.211:3306
Source Database       : taxeasy

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2016-09-20 14:48:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `privilegeIds` varchar(200) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `ztbz` varchar(1) DEFAULT NULL COMMENT '状态标识',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `lrry` varchar(255) DEFAULT NULL COMMENT '录入人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `xgry` varchar(255) DEFAULT NULL COMMENT '修改人员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '管理员', '系统管理员', '1,3,4,5,6,7,10,11,12,15,16,17,20,22', '2', '1', null, '1', '2016-09-20 14:30:59', '2');
INSERT INTO `roles` VALUES ('2', '开票员', '开票员', '3,2,4', '3', '1', null, '1', null, null);
INSERT INTO `roles` VALUES ('3', '操作员', '对电子发票进行操作的人员', '4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19', '4', '1', '2016-09-19 11:08:17', '2', '2016-09-19 16:49:26', '2');
INSERT INTO `roles` VALUES ('4', '系统管理员', '水电费水电费', '4,5,6,7,8,9,10,11,12,13', '5', '1', '2016-09-19 16:59:04', '2', '2016-09-19 16:59:28', '2');
