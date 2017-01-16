/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-18 09:31:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fpkc
-- ----------------------------
CREATE TABLE `t_fpkc` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `xfid` int(10) DEFAULT NULL COMMENT '销方id',
  `skpid` int(10) DEFAULT NULL COMMENT '税控盘id',
  `fpdm` varchar(20) DEFAULT NULL COMMENT '发票代码',
  `fphms` varchar(20) DEFAULT NULL COMMENT '起始发票号码',
  `fphmz` varchar(20) DEFAULT NULL COMMENT '终止发票号码',
  `lrry` int(20) DEFAULT NULL COMMENT '录入人员',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgry` int(20) DEFAULT NULL COMMENT '修改人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `yxbz` varchar(2) DEFAULT NULL COMMENT '有效标志  1：有效，0：失效',
  `gsdm` varchar(10) DEFAULT NULL COMMENT '公司代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;



/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-18 09:31:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mkgl
-- ----------------------------
CREATE TABLE `t_mkgl` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `yhid` int(10) DEFAULT NULL COMMENT '用户id',
  `pzid` int(10) DEFAULT NULL COMMENT '配置id',
  `mkmc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区块名称',
  `mkbl` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区块比例',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `lrry` int(10) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` int(10) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `yxbz` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-18 09:31:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mkpz
-- ----------------------------
CREATE TABLE `t_mkpz` (
  `id` int(10) NOT NULL COMMENT '主键',
  `url` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区块内容对应的rul',
  `urlmc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区块内容名称',
  `defaultflag` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '默认标识，1：默认，0：非默认',
  `mkbl` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区块比例',
  `lrry` int(10) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` int(10) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `yxbz` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效标志',
  `mkmc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_mkpz
-- ----------------------------
INSERT INTO `t_mkpz` VALUES ('1', '/fpkc', '发票库存管理', '1', 'am-u-sm-6 am-u-md-6 am-u-lg-6', null, null, null, null, '1', '发票库存简况');





alter table t_kpls add printflag varchar(2)

