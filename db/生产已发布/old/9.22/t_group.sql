/*
Navicat MySQL Data Transfer

Source Server         : dzfp211
Source Server Version : 50541
Source Host           : 120.26.45.211:3306
Source Database       : taxeasy

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2016-09-20 14:48:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `yhid` int(11) DEFAULT NULL,
  `xfid` int(11) DEFAULT NULL,
  `ztbz` varchar(1) DEFAULT NULL,
  `remark2` varchar(255) DEFAULT NULL,
  `remark3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_group
-- ----------------------------
INSERT INTO `t_group` VALUES ('6', '11', '4', '0', null, null);
INSERT INTO `t_group` VALUES ('7', '12', '4', '0', null, null);
