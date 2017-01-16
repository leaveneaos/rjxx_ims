alter table t_kpspmx add khcje NUMERIC(18,6);


alter table t_kpspmx add yhcje NUMERIC(18,6);


ALTER TABLE `t_kpspmx`
ADD COLUMN `id`  int NOT NULL AUTO_INCREMENT ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);



alter table t_fpkc add column fpkcl Integer(10);

insert into privileges values(26,5,"发票预警订阅",null,25,"/fpyjdy","1",null,null,null,null,"1");

/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-26 13:11:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fpyjdy
-- ----------------------------
CREATE TABLE `t_fpyjdy` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `yhid` int(10) DEFAULT NULL COMMENT '用户id',
  `gsdm` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司代码',
  `xfid` int(10) DEFAULT NULL COMMENT '销方id',
  `skpid` int(10) DEFAULT NULL COMMENT '税控盘id',
  `sfsy` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '是否首页订阅',
  `sfemail` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '是否email订阅',
  `yxbz` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效标志',
  `lrry` int(10) DEFAULT NULL COMMENT '录入人员',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgry` int(10) DEFAULT NULL COMMENT '修改人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `yjkcl` int(10) DEFAULT NULL COMMENT '库存预警阈值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



insert into privileges values(23,5,"发票库存管理",null,22,"/fpkc","1",null,null,null,null,"1");


insert into privilege_types values(5,"发票库存",null,5,null,null,null,null,"1");

alter table t_kpls add column skpid INTEGER(10);

alter table t_kpspmx add column skpid INTEGER(10);

alter table t_jyls add column skpid INTEGER(10);

alter table t_jyspmx add column skpid INTEGER(10);

insert into roleprivs values(1,23,"smc","1",null,null,NOW(),null,NOW(),null);

insert into roleprivs values(1,26,"smc","1",null,null,NOW(),null,NOW(),null);

alter table t_kpls alter column printflag set default '0';

=======
﻿alter table t_kpspmx add khcje NUMERIC(18,6);


alter table t_kpspmx add yhcje NUMERIC(18,6);


ALTER TABLE `t_kpspmx`
ADD COLUMN `id`  int NOT NULL AUTO_INCREMENT ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);



alter table t_fpkc add column fpkcl Integer(10);

insert into privileges values(26,5,"发票预警订阅",null,25,"/fpyjdy","1",null,null,null,null,"1");

/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-26 13:11:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fpyjdy
-- ----------------------------
CREATE TABLE `t_fpyjdy` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `yhid` int(10) DEFAULT NULL COMMENT '用户id',
  `gsdm` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司代码',
  `xfid` int(10) DEFAULT NULL COMMENT '销方id',
  `skpid` int(10) DEFAULT NULL COMMENT '税控盘id',
  `sfsy` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '是否首页订阅',
  `sfemail` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '是否email订阅',
  `yxbz` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效标志',
  `lrry` int(10) DEFAULT NULL COMMENT '录入人员',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgry` int(10) DEFAULT NULL COMMENT '修改人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `yjkcl` int(10) DEFAULT NULL COMMENT '库存预警阈值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



insert into privileges values(23,5,"发票库存管理",null,22,"/fpkc","1",null,null,null,null,"1");


insert into privilege_types values(5,"发票库存",null,5,null,null,null,null,"1");

alter table t_kpls add column skpid INTEGER(10);

alter table t_kpspmx add column skpid INTEGER(10);

alter table t_jyls add column skpid INTEGER(10);

alter table t_jyspmx add column skpid INTEGER(10);

insert into roleprivs values(1,23,"smc","1",null,null,NOW(),null,NOW(),null);

insert into roleprivs values(1,26,"smc","1",null,null,NOW(),null,NOW(),null);

alter table t_kpls alter column printflag set default '0';

alter table t_kpls add column fpztdm varchar(2) DEFAULT "00";


/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-28 10:15:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fpzt
-- ----------------------------
CREATE TABLE `t_fpzt` (
  `fpztdm` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fpztmc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_fpzt
-- ----------------------------
INSERT INTO `t_fpzt` VALUES ('00', '正常发票');
INSERT INTO `t_fpzt` VALUES ('01', '部分红冲');
INSERT INTO `t_fpzt` VALUES ('02', '已红冲');
INSERT INTO `t_fpzt` VALUES ('03', '已作废');


--liuyuanya 20161028 16:00
--新建模板表
CREATE TABLE `t_drmb` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mbmc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模板名称',
  `yhid` int(11) DEFAULT NULL COMMENT '用户id',
  `xfsh` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '销方税号',
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '有效标志：0，无效；1，有效',
  PRIMARY KEY (`id`)
)

--t_dr_pz表增加模板id
ALTER TABLE `t_dr_pz`
ADD COLUMN `mbid`  int(11) NULL COMMENT '模板id' AFTER `lrsj`;