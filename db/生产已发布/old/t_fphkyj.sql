/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-26 14:25:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fphkyj
-- ----------------------------
DROP TABLE IF EXISTS `t_fphkyj`;
CREATE TABLE `t_fphkyj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sjrxm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dwmc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sjdz` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `yhm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lxdh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `kplsh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `djh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `xfid` int(11) DEFAULT NULL,
  `ddh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fpdm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fphm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gfmc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sfyj` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `kprq` date DEFAULT NULL,
  `hkbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '0 是邮寄 1是自提',
  `ztsj` datetime DEFAULT NULL,
  `jshj` double(18,6) DEFAULT NULL,
  `kpdid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开票点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




//宁一张表

/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-26 14:26:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_hkpzgl
-- ----------------------------
DROP TABLE IF EXISTS `t_hkpzgl`;
CREATE TABLE `t_hkpzgl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gsdm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `xfsh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `xfmc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sfhk` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '0 不支持 1 支持',
  `sfsh` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '0 不审核 1 审核',
  `sfzt` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '0 不自提 1 自提',
  `sfyj` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '0 不邮寄 1 邮寄',
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `lrry` int(255) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `xgry` int(255) DEFAULT NULL,
  `skpid` int(11) DEFAULT NULL COMMENT '税控盘id',
  `xfid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;






UPDATE `privileges`  SET yxbz = ztbz;