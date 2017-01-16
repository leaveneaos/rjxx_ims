/*
Navicat MySQL Data Transfer

Source Server         : dzfp211
Source Server Version : 50541
Source Host           : 120.26.45.211:3306
Source Database       : taxeasy

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2016-09-20 14:46:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for privileges
-- ----------------------------
DROP TABLE IF EXISTS `privileges`;
CREATE TABLE `privileges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `privilegeTypeId` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `urls` varchar(200) DEFAULT NULL,
  `ztbz` varchar(1) DEFAULT NULL COMMENT '状态标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privileges
-- ----------------------------
INSERT INTO `privileges` VALUES ('1', '1', '销方信息维护', null, '1', '/xfxxwh', '1');
INSERT INTO `privileges` VALUES ('2', '1', '税控设备注册', null, '2', '/sksbxxzc', '0');
INSERT INTO `privileges` VALUES ('3', '1', '商品税率管理', null, '3', '/spslgl', '1');
INSERT INTO `privileges` VALUES ('4', '2', '发票开具', null, '4', '/kp', '1');
INSERT INTO `privileges` VALUES ('5', '2', '发票红冲', null, '5', '/fphc', '1');
INSERT INTO `privileges` VALUES ('6', '2', '发票换开', null, '6', '/fphk', '1');
INSERT INTO `privileges` VALUES ('7', '2', '发票发送', null, '7', '/yjfs', '1');
INSERT INTO `privileges` VALUES ('8', '2', '交易流水导出', null, '8', '/jyls', '0');
INSERT INTO `privileges` VALUES ('9', '2', '异常处理', null, '9', '/yccl', '0');
INSERT INTO `privileges` VALUES ('10', '3', '发票查询', null, '10', '/fpcx', '1');
INSERT INTO `privileges` VALUES ('11', '3', '红字发票查询', null, '11', '/fphccx', '1');
INSERT INTO `privileges` VALUES ('12', '3', '换开发票查询', null, '12', '/fphkcx', '1');
INSERT INTO `privileges` VALUES ('13', '3', '订单状态查询', null, '13', '/ddcx', '0');
INSERT INTO `privileges` VALUES ('14', '3', '仪表盘', null, '14', '/ybp', '0');
INSERT INTO `privileges` VALUES ('15', '3', '分日统计报表', null, '15', '/frtjbb', '1');
INSERT INTO `privileges` VALUES ('16', '3', '分月统计报表', null, '16', '/fytjbb', '1');
INSERT INTO `privileges` VALUES ('17', '3', '时间段统计报表', null, '17', '/sjdtjbb', '1');
INSERT INTO `privileges` VALUES ('18', '3', '使用报告', null, '18', '/sybg', '0');
INSERT INTO `privileges` VALUES ('19', '3', '报表订阅', null, '19', '/bbdy', '0');
INSERT INTO `privileges` VALUES ('20', '4', '用户管理', null, '20', '/nyhgl', '1');
INSERT INTO `privileges` VALUES ('21', '4', '日志监控', null, '22', '/rzjk', '0');
INSERT INTO `privileges` VALUES ('22', '4', '角色管理', null, '21', '/role', '1');
