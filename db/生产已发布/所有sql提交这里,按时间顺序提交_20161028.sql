/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 11:19:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fpzt
-- ----------------------------
DROP TABLE IF EXISTS `t_fpzt`;
CREATE TABLE `t_fpzt` (
  `fpztdm` varchar(2) CHARACTER SET utf8 DEFAULT NULL,
  `fpztmc` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_fpzt
-- ----------------------------
INSERT INTO `t_fpzt` VALUES ('00', '正常发票', '1');
INSERT INTO `t_fpzt` VALUES ('01', '部分红冲', '2');
INSERT INTO `t_fpzt` VALUES ('02', '已红冲', '3');
INSERT INTO `t_fpzt` VALUES ('03', '已换开', '4');
INSERT INTO `t_fpzt` VALUES ('04', '正在开具', '5');
INSERT INTO `t_fpzt` VALUES ('05', '开具失败', '6');



alter table t_kpls alter column fpztdm set default '04';
alter table t_kpls add column errorReason VARCHAR(100);




/*
 * 2016/11/3  liuyan
 */

/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 10:29:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_csb
-- ----------------------------
DROP TABLE IF EXISTS `t_csb`;
CREATE TABLE `t_csb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `csm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数名',
  `csmc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中文名称',
  `csjb` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数级别',
  `cslx` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '系统参数或者用户参数',
  `lrry` int(255) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` int(255) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cszlx` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数值类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;









/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 10:29:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_cszb
-- ----------------------------
DROP TABLE IF EXISTS `t_cszb`;
CREATE TABLE `t_cszb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `csid` int(11) DEFAULT NULL,
  `gsdm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `xfid` int(11) DEFAULT NULL,
  `kpdid` int(11) DEFAULT NULL,
  `yxbz` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lrry` int(255) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` int(255) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `csz` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 15:06:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_tqjl
-- ----------------------------
DROP TABLE IF EXISTS `t_tqjl`;
CREATE TABLE `t_tqjl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `djh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tqsj` datetime DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `llqxx` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '浏览器信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



insert into `privileges` values(28,2,'发票重开审核','am-icon-pencil',29,'/fpcksh','1',null,null,null,null,'1');
insert into `privileges` values(30,2,'发票换开审核','am-icon-pencil',27,'/fphksh','1',null,null,null,null,'1');
insert into `privileges` values(31,3,'开票申请查询','am-icon-search',30,'/kpsqcx','1',null,null,null,null,'1');
insert into `privileges` values(32,3,'发票提取查询','am-icon-search',31,'/fptqcx','1',null,null,null,null,'1');


insert into roleprivs values(1,28,'smc','1',null,null,now(),2,now(),2);
insert into roleprivs values(1,30,'smc','1',null,null,now(),2,now(),2);
insert into roleprivs values(1,31,'smc','1',null,null,now(),2,now(),2);

insert into t_fpczlx values('23','重新开具',now(),1,now(),1);

insert into t_fpzt values('06','重新开具',7);
insert into t_fpzt values('07','已申请',8);



--liuyuanya 20161108 17:00
--模板增加gsdm
ALTER TABLE `t_drmb`
ADD COLUMN `gsdm`  varchar(20) NULL COMMENT '公司代码' AFTER `yxbz`;
ALTER TABLE `t_drmb`
ADD COLUMN `gxbz`  varchar(1) NULL COMMENT '贡献标志：0，不共享；1，共享' AFTER `gsdm`;
ALTER TABLE `t_drmb`
ADD COLUMN `lrsj`  datetime DEFAULT NULL COMMENT '录入时间' AFTER `gxbz`,
ADD COLUMN `lrry`  int(11) NULL COMMENT '录入人员' AFTER `lrsj`,
ADD COLUMN `xgsj`  datetime DEFAULT NULL COMMENT '修改时间' AFTER `lrry`,
ADD COLUMN `xgry`  int(11) NULL COMMENT '修改人员' AFTER `xgsj`;

CREATE TABLE `t_xf_mb` (
`id`  int NOT NULL COMMENT '主键id' ,
`xfsh`  varchar(30) NULL COMMENT '销方税号' ,
`mbid`  int NULL COMMENT '模板id' ,
`yxbz`  varchar(1) NULL COMMENT '有效标志：0，无效；1，有效' ,
`lrry`  int NULL COMMENT '录入人员' ,
`lrsj`  datetime NULL DEFAULT NULL COMMENT '录入时间' ,
`xgry`  int NULL DEFAULT NULL COMMENT '修改人员' ,
`xgsj`  datetime NULL COMMENT '修改时间' ,
PRIMARY KEY (`id`)
)
;
ALTER TABLE `t_xf_mb`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id' FIRST ;

--新增发票夹表
CREATE TABLE `t_fpj` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `djh` int(11) DEFAULT NULL COMMENT '单据号',
  `unionid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '微信id',
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效标志：0，无效；1，有效',
  `lrry` int(11) DEFAULT NULL COMMENT '录入人员',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgry` int(11) DEFAULT NULL COMMENT '修改人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);


/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-09 11:38:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_smtq
-- ----------------------------
DROP TABLE IF EXISTS `t_smtq`;
CREATE TABLE `t_smtq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ddh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `jylssj` datetime DEFAULT NULL,
  `zje` decimal(10,0) DEFAULT NULL,
  `gfmc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `nsrsbh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `khh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `khhzh` varchar(255) CHARACTER SET utf8 DEFAULT '',
  `yx` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sj` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `fpzt` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '请查看代码表',
  `yxbz` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `gsdm` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;





--kangzhongxu 20161114 dzfp-service系统新增表及新增字段


CREATE TABLE `t_jyxx` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) NOT NULL,
  `order_time` varchar(14) NOT NULL,
  `price` double(10,2) NOT NULL,
  `sign` varchar(32) NOT NULL,
  `store_no` varchar(20) DEFAULT NULL,
  `gsdm` varchar(255) DEFAULT NULL,
  `lrry` varchar(20) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` varchar(20) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Dindex` (`order_no`,`gsdm`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='交易信息表';



ALTER TABLE `t_gsxx`
ADD COLUMN `appkey`  varchar(32) NULL AFTER `secret`,
ADD COLUMN `secret`  varchar(32) NULL AFTER `appkey`;


ALTER TABLE `t_jyspmx`
ADD COLUMN `xfid`  int(10) NULL AFTER `skpid`;
=======
/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 11:19:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fpzt
-- ----------------------------
DROP TABLE IF EXISTS `t_fpzt`;
CREATE TABLE `t_fpzt` (
  `fpztdm` varchar(2) CHARACTER SET utf8 DEFAULT NULL,
  `fpztmc` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_fpzt
-- ----------------------------
INSERT INTO `t_fpzt` VALUES ('00', '正常发票', '1');
INSERT INTO `t_fpzt` VALUES ('01', '部分红冲', '2');
INSERT INTO `t_fpzt` VALUES ('02', '已红冲', '3');
INSERT INTO `t_fpzt` VALUES ('03', '已换开', '4');
INSERT INTO `t_fpzt` VALUES ('04', '正在开具', '5');
INSERT INTO `t_fpzt` VALUES ('05', '开具失败', '6');



alter table t_kpls alter column fpztdm set default '04';
alter table t_kpls add column errorReason VARCHAR(100);




/*
 * 2016/11/3  liuyan
 */

/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 10:29:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_csb
-- ----------------------------
DROP TABLE IF EXISTS `t_csb`;
CREATE TABLE `t_csb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `csm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数名',
  `csmc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中文名称',
  `csjb` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数级别',
  `cslx` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '系统参数或者用户参数',
  `lrry` int(255) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` int(255) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cszlx` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数值类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;









/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 10:29:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_cszb
-- ----------------------------
DROP TABLE IF EXISTS `t_cszb`;
CREATE TABLE `t_cszb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `csid` int(11) DEFAULT NULL,
  `gsdm` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `xfid` int(11) DEFAULT NULL,
  `kpdid` int(11) DEFAULT NULL,
  `yxbz` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lrry` int(255) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` int(255) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `csz` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


/*
Navicat MySQL Data Transfer

Source Server         : dzfp-dev
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-03 15:06:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_tqjl
-- ----------------------------
DROP TABLE IF EXISTS `t_tqjl`;
CREATE TABLE `t_tqjl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `djh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tqsj` datetime DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `llqxx` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '浏览器信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



insert into `privileges` values(28,2,'发票重开审核','am-icon-pencil',29,'/fpcksh','1',null,null,null,null,'1');
insert into `privileges` values(30,2,'发票换开审核','am-icon-pencil',27,'/fphksh','1',null,null,null,null,'1');
insert into `privileges` values(31,3,'开票申请查询','am-icon-search',30,'/kpsqcx','1',null,null,null,null,'1');
insert into `privileges` values(32,3,'发票提取查询','am-icon-search',31,'/fptqcx','1',null,null,null,null,'1');


insert into roleprivs values(1,28,'smc','1',null,null,now(),2,now(),2);
insert into roleprivs values(1,30,'smc','1',null,null,now(),2,now(),2);
insert into roleprivs values(1,31,'smc','1',null,null,now(),2,now(),2);

insert into t_fpczlx values('23','重新开具',now(),1,now(),1);

insert into t_fpzt values('06','重新开具',7);
insert into t_fpzt values('07','已申请',8);



--liuyuanya 20161108 17:00
--模板增加gsdm
ALTER TABLE `t_drmb`
ADD COLUMN `gsdm`  varchar(20) NULL COMMENT '公司代码' AFTER `yxbz`;
ALTER TABLE `t_drmb`
ADD COLUMN `gxbz`  varchar(1) NULL COMMENT '贡献标志：0，不共享；1，共享' AFTER `gsdm`;
ALTER TABLE `t_drmb`
ADD COLUMN `lrsj`  datetime DEFAULT NULL COMMENT '录入时间' AFTER `gxbz`,
ADD COLUMN `lrry`  int(11) NULL COMMENT '录入人员' AFTER `lrsj`,
ADD COLUMN `xgsj`  datetime DEFAULT NULL COMMENT '修改时间' AFTER `lrry`,
ADD COLUMN `xgry`  int(11) NULL COMMENT '修改人员' AFTER `xgsj`;

CREATE TABLE `t_xf_mb` (
`id`  int NOT NULL COMMENT '主键id' ,
`xfsh`  varchar(30) NULL COMMENT '销方税号' ,
`mbid`  int NULL COMMENT '模板id' ,
`yxbz`  varchar(1) NULL COMMENT '有效标志：0，无效；1，有效' ,
`lrry`  int NULL COMMENT '录入人员' ,
`lrsj`  datetime NULL DEFAULT NULL COMMENT '录入时间' ,
`xgry`  int NULL DEFAULT NULL COMMENT '修改人员' ,
`xgsj`  datetime NULL COMMENT '修改时间' ,
PRIMARY KEY (`id`)
)
;
ALTER TABLE `t_xf_mb`
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id' FIRST ;

--新增发票夹表
CREATE TABLE `t_fpj` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `djh` int(11) DEFAULT NULL COMMENT '单据号',
  `unionid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '微信id',
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效标志：0，无效；1，有效',
  `lrry` int(11) DEFAULT NULL COMMENT '录入人员',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgry` int(11) DEFAULT NULL COMMENT '修改人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);


/*
Navicat MySQL Data Transfer

Source Server         : dzfp
Source Server Version : 50621
Source Host           : 192.168.1.200:3306
Source Database       : dzfp-dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-11-09 11:38:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_smtq
-- ----------------------------
DROP TABLE IF EXISTS `t_smtq`;
CREATE TABLE `t_smtq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ddh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `jylssj` datetime DEFAULT NULL,
  `zje` decimal(10,0) DEFAULT NULL,
  `gfmc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `nsrsbh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `khh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `khhzh` varchar(255) CHARACTER SET utf8 DEFAULT '',
  `yx` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sj` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `fpzt` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '请查看代码表',
  `yxbz` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `gsdm` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--王勇  2016-11-11
alter table t_skp modify skph varchar(50) null;
alter table t_xf add dzpzdje NUMERIC(18,2);
alter table t_xf add dzpfpje NUMERIC(18,2);
alter table t_xf add zpfpje NUMERIC(18,2);
alter table t_xf add zpzdje NUMERIC(18,2);
alter table t_xf add ppzdje NUMERIC(18,2);
alter table t_xf add ppfpje NUMERIC(18,2);


insert into roleprivs values(1,32,'smc','1',null,null,now(),2,now(),2);




/*
 *参数表 11.11 liuyan
 */
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('1', 'spbmbbh', '商品编码版本号', '3', '2', '1', '2016-11-02 11:48:04', '1', '2016-11-02 11:48:11', '1', '2');
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('2', 'sfhksh', '换开是否审核', '2', '2', '1', '2016-11-02 11:52:59', '1', '2016-11-02 11:53:02', '1', '1');
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('3', 'sflxkp', '是否离线开票', '2', '2', '1', '2016-11-08 16:37:12', '1', '2016-11-08 16:37:26', '1', '1');

INSERT INTO `privileges` (`id`, `privilegeTypeId`, `name`, `description`, `sort`, `urls`, `ztbz`, `lrsj`, `lrry`, `xgsj`, `xgry`, `yxbz`) VALUES ('29', '4', '参数配置', 'am-icon-cog', '28', '/csb', '1', NULL, NULL, NULL, NULL, '1');
INSERT INTO `roleprivs` (`roleid`, `privid`, `ButtonPrivs`, `yxbz`, `description`, `sort`, `lrsj`, `lrry`, `xgsj`, `xgry`) VALUES ('1', '29', 'smc', '1', NULL, NULL, '2016-11-02 13:38:46', '2', '2016-11-02 13:38:51', '2');

alter table t_skp add kpddm VARCHAR(50);



--kangzhongxu dzfp-service新增表及新增表字段


CREATE TABLE `t_jyxx` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) NOT NULL,
  `order_time` varchar(14) NOT NULL,
  `price` double(10,2) NOT NULL,
  `sign` varchar(32) NOT NULL,
  `store_no` varchar(20) DEFAULT NULL,
  `gsdm` varchar(255) DEFAULT NULL,
  `lrry` varchar(20) DEFAULT NULL,
  `lrsj` datetime DEFAULT NULL,
  `xgry` varchar(20) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Dindex` (`order_no`,`gsdm`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='交易信息表';


ALTER TABLE `t_gsxx`
ADD COLUMN `appkey`  varchar(255) NULL AFTER `secret_key`;


ALTER TABLE `t_jyspmx`
ADD COLUMN `xfid`  int(10) NULL AFTER `skpid`;

--wangyong 2016-11-16
--修改发票状态表中的重新开具为被重开
update t_fpzt set fpztmc = '被重开' where id=7;
--liuyan 2016-11-16
--smtq表加kpddm
ALTER TABLE `t_smtq`
ADD COLUMN `kpddm`  varchar(255) NULL COMMENT '开票点代码' AFTER `gsdm`;

--liuyuanya 2016-11-23
--参数表中新增重开是否审核字段
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('4', 'sfcksh', '重开是否审核', '3', '2', '1', '2016-11-08 16:37:12', '1', '2016-11-08 16:37:12', '1', '1');
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('5', 'sfzczt', '是否支持自提', '3', '2', '1', '2016-11-17 13:45:45', '1', '2016-11-17 13:45:49', '1', '1');
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('6', 'sfzcyj', '是否支持邮寄', '3', '2', '1', '2016-11-17 13:46:29', '1', '2016-11-17 13:46:33', '1', '1');
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('8', 'sfzchk', '是否支持换开', '3', '2', '1', '2016-11-17 17:18:05', '1', '2016-11-17 17:18:09', '1', '1');
INSERT INTO `t_csb` (`id`, `csm`, `csmc`, `csjb`, `cslx`, `lrry`, `lrsj`, `xgry`, `xgsj`, `yxbz`, `cszlx`) VALUES ('9', 'sfzcck', '是否支持重开', '3', '2', '1', '2016-11-18 13:16:27', '1', '2016-11-18 13:16:31', '1', '1');



--创建换开纸质票取票表
CREATE TABLE `t_zzfpqp` (
`id`  int NOT NULL AUTO_INCREMENT COMMENT '主键id' ,
`qpfs`  varchar(1) NULL COMMENT '取票方式' ,
`djh`  int NULL COMMENT '单据号' ,
`sjr`  varchar(100) NULL COMMENT '收件人' ,
`dwmc`  varchar(255) NULL COMMENT '单位名称' ,
`sjdz`  varchar(255) NULL COMMENT '收件地址' ,
`yb`  varchar(20) NULL COMMENT '邮编' ,
`lxdh`  varchar(50) NULL COMMENT '联系电话' ,
`ztdm`  varchar(255) NULL COMMENT '自提店面' ,
`qssj`  varchar(50) NULL COMMENT '起始时间' ,
`jssj`  varchar(50) NULL COMMENT '结束时间' ,
`yxbz`  varchar(1) NULL COMMENT '有效标志：0，无效；1，有效' ,
PRIMARY KEY (`id`)
)
;ALTER TABLE `t_zzfpqp`
MODIFY COLUMN `qpfs`  varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '取票方式：0，邮寄；1，自提' AFTER `id`;

--修改t_fpj
ALTER TABLE `t_fpj`
ADD COLUMN `zhlx`  varchar(50) NULL COMMENT '账号类型' AFTER `xgsj`,
ADD COLUMN `spfid`  int NULL COMMENT '受票方id' AFTER `zhlx`;

--新增模板菜单
INSERT INTO `privileges` (`id`, `privilegeTypeId`, `name`, `description`, `sort`, `urls`, `ztbz`, `lrsj`, `lrry`, `xgsj`, `xgry`, `yxbz`) VALUES ('34', '2', '模板设置', 'am-icon-cog', '33', '/mbsz', '1', NULL, NULL, NULL, NULL, '1');

--新增模板菜单权限
INSERT INTO `roleprivs` (`roleid`, `privid`, `ButtonPrivs`, `yxbz`, `description`, `sort`, `lrsj`, `lrry`, `xgsj`, `xgry`) VALUES ('1', '34', 'smc', '1', NULL, NULL, '2016-11-21 16:49:47', '2', '2016-11-21 16:49:47', '2');

--kzx20161124 kpls表新增字段
ALTER TABLE `t_kpls`
ADD COLUMN `fp_ewm`  text NULL AFTER `ewm`;
--tqjl  liuyan 11/24
ALTER TABLE `t_tqjl`
ADD COLUMN `jlly`  varchar(255) NULL COMMENT '记录来源' AFTER `llqxx`;

--spfyx liuyan 11/25
CREATE TABLE `t_spfyx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spfid` int(11) DEFAULT NULL COMMENT '受票方id',
  `glzhlx` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '关联账号类型',
  `glzh` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '关联账号',
  `glid` int(11) DEFAULT NULL COMMENT '关联账号表id',
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `yxbz` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '有效标志',
  `lrsj` datetime DEFAULT NULL,
  `djh` int(30) DEFAULT NULL COMMENT '单据号',
  PRIMARY KEY (`id`)
)