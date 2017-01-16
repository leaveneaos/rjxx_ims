/*
Navicat MySQL Data Transfer

Source Server         : dzfp211
Source Server Version : 50541
Source Host           : 120.26.45.211:3306
Source Database       : taxeasy

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2016-09-20 14:46:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for privilege_types
-- ----------------------------
DROP TABLE IF EXISTS `privilege_types`;
CREATE TABLE `privilege_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privilege_types
-- ----------------------------
INSERT INTO `privilege_types` VALUES ('1', '基础数据', '', '1');
INSERT INTO `privilege_types` VALUES ('2', '业务处理', '', '2');
INSERT INTO `privilege_types` VALUES ('3', '查询统计', '123', '3');
INSERT INTO `privilege_types` VALUES ('4', '系统管理', '', '4');
