ALTER TABLE `t_tqmtq`
ADD COLUMN `gfemail`  varchar(255) NULL COMMENT '购方邮箱' AFTER `gsdm`;

--liuyaunya 12.16
--生成未发布，增加openid
ALTER TABLE `t_smtq`
MODIFY COLUMN `gsdm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid' AFTER `yxbz`,
ADD COLUMN `openid`  varchar(500) NULL AFTER `kpddm`;

ALTER TABLE `t_tqmtq`
ADD COLUMN `openid`  varchar(500) NULL COMMENT '微信openID' AFTER `gsdm`;


--2016-12-16 王勇  短信发送sql
alter table t_jyls add column dxzt VARCHAR(2) default '0';


insert into t_csb values(10,'sfktdx','是否开通短信通知','3','2',1,now(),1,now(),'1','1','否');


insert into t_csb values(11,'sfzdfs','是否自动发送短信通知','3','2',1,now(),1,now(),'1','1','否');


CREATE TABLE `t_dxfs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gsdm` varchar(50) DEFAULT NULL COMMENT '公司代码',
  `djh` int(11) DEFAULT NULL,
  `sjhm` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `dxnr` varchar(255) DEFAULT NULL COMMENT '短信内容',
  `mbdm` varchar(50) DEFAULT NULL COMMENT '模板代码',
  `lrsj` datetime DEFAULT NULL,
  `returnid` varchar(255) DEFAULT NULL COMMENT '短信返回id',
  PRIMARY KEY (`id`)
)

alter table t_kpls modify column errorReason varchar(500);


--liuyuanya 11-19
--新增公司代码
ALTER TABLE `t_wxkb`
ADD COLUMN `gsdm`  varchar(50) NULL COMMENT '公司代码' AFTER `expires_in`;

update t_wxkb SET gsdm = 'rjxx' 

INSERT INTO `t_wxkb` (`id`, `access_token`, `scsj`, `expires_in`, `gsdm`) VALUES ('3', 'hYrZGb6sAOFTGVOMp5HlK3_slgDbE16eYazpqWU111dxY2StwJBEHn3XG3_KhoWnC6w38Y0LHwkpWTo8C7AdOICjMiXK_tey87iB67l5rTgrxJ6rtJvR2uGqF-C5zjx3GBUhADALGZ', '2016-12-05 09:41:28', '7200', 'wljqr');


---测试环境已执行   2016-12-23 王勇
CREATE TABLE `t_yjjl` (
`id`  int NOT NULL AUTO_INCREMENT ,
`sjryx`  varchar(255) NULL COMMENT '收件人邮箱' ,
`gsdm`  varchar(255) NULL COMMENT '公司代码' ,
`type`  varchar(255) NULL COMMENT '邮件类型' ,
`lrsj`  datetime NULL ,
`ref_Id`  varchar(255) NULL COMMENT '引用id(djh)' ,
`returnId`  varchar(500) NULL COMMENT '接口返回' ,
`yjnr`  varchar(255) NULL COMMENT '邮件内容' ,
`yjbt`  varchar(255) NULL COMMENT '邮件标题' ,
PRIMARY KEY (`id`)
);


--liuyuanya 12.21
--新增模板消息模板id参数       测试环境已执行
INSERT INTO `t_csb` (
	`csm`,
	`csmc`,
	`csjb`,
	`cslx`,
	`lrry`,
	`lrsj`,
	`xgry`,
	`xgsj`,
	`yxbz`,
	`cszlx`,
	`mrz`
)
VALUES
	(
		'mbxxmbid',
		'模板消息模板id',
		'3',
		'1',
		'1',
		'2016-12-16 16:28:18',
		'1',
		'2016-12-16 16:28:22',
		'1',
		'1',
		'Rdatmf7JV1J-AJhSD1sdBpV1OWuTpwQ5QLmjFVLCHIU'
	);



CREATE TABLE `t_gszc` (
`id`  int NOT NULL AUTO_INCREMENT ,
`gsmc`  varchar(255) NULL COMMENT '公司名称' ,
`lxr`  varchar(255) NULL COMMENT '联系人' ,
`zw`  varchar(255) NULL COMMENT '职位' ,
`lxdh`  varchar(255) NULL COMMENT '联系电话' ,
`sscs`  varchar(255) NULL COMMENT '所属城市' ,
`lrsj`  datetime NULL COMMENT '录入时间' ,
PRIMARY KEY (`id`)
)
;

UPDATE t_xf SET kpr ='俞梦妮' WHERE gsdm = 'sqj'



ALTER TABLE `t_tqmtq`
ADD COLUMN `gfsjh`  varchar(255) NULL AFTER `gfemail`;


ALTER TABLE `roleprivs`
ADD COLUMN `id`  int NOT NULL AUTO_INCREMENT AFTER `xgry`,
ADD PRIMARY KEY (`id`);

CREATE TABLE `t_zdpp` (
`id`  int NOT NULL AUTO_INCREMENT ,
`zdm`  varchar(255) NULL ,
`zdzwm`  varchar(255) NULL ,
PRIMARY KEY (`id`)
)
;

INSERT INTO t_zdpp VALUES(1,"jylsh","交易流水号");
INSERT INTO t_zdpp VALUES(2,"jylssj","交易流水时间");
INSERT INTO t_zdpp VALUES(3,"xfsh","销方税号");
INSERT INTO t_zdpp VALUES(4,"xfmc","销方名称");
INSERT INTO t_zdpp VALUES(5,"xfyh","销方银行");
INSERT INTO t_zdpp VALUES(6,"xfyhzh","销方银行账号");
INSERT INTO t_zdpp VALUES(7,"xfdz","销方地址");
INSERT INTO t_zdpp VALUES(8,"xfdh","销方电话");
INSERT INTO t_zdpp VALUES(9,"xfyb","销方邮编");
INSERT INTO t_zdpp VALUES(10,"gfsh","购方税号");
INSERT INTO t_zdpp VALUES(11,"gfmc","购方名称");
INSERT INTO t_zdpp VALUES(12,"gfyh","购方银行");
INSERT INTO t_zdpp VALUES(13,"gfyhzh","购方银行账号");
INSERT INTO t_zdpp VALUES(14,"gfdz","购方地址");
INSERT INTO t_zdpp VALUES(15,"gfdh","购方电话");
INSERT INTO t_zdpp VALUES(16,"gfemail","购方email");
INSERT INTO t_zdpp VALUES(17,"bz","备注");
INSERT INTO t_zdpp VALUES(18,"skr","收款人");
INSERT INTO t_zdpp VALUES(19,"kpr","开票人");
INSERT INTO t_zdpp VALUES(20,"fhr","复核人");
INSERT INTO t_zdpp VALUES(21,"ssyf","所属月份");
INSERT INTO t_zdpp VALUES(22,"yfpdm","原发票代码");
INSERT INTO t_zdpp VALUES(23,"yfphm","原发票号码");
INSERT INTO t_zdpp VALUES(24,"ykpjshj","已开票价税合计");
INSERT INTO t_zdpp VALUES(25,"tqm","提取码");
INSERT INTO t_zdpp VALUES(26,"gfsjh","购方手机号");
INSERT INTO t_zdpp VALUES(27,"skpid","开票点");

---测试环境已执行   2016-12-23 王勇
ALTER TABLE `t_yjjl`
MODIFY COLUMN `yjnr`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件内容' AFTER `returnId`;

--查询自定义列表
CREATE TABLE `t_yh_cxzdyl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `yhid` int(11) DEFAULT NULL,
  `dygn` varchar(255) DEFAULT NULL COMMENT '对应的功能',
  `zddm` varchar(255) DEFAULT NULL COMMENT '字段代码',
  `yxbz` varchar(1) DEFAULT '1' COMMENT '1-有效，0-无效',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `lrry` int(11) DEFAULT NULL COMMENT '录入人员',
  `xgry` int(11) DEFAULT NULL COMMENT '修改人员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8 COMMENT='用户查询自定义列保存';

--导出自定义列表
CREATE TABLE `t_yh_dczdyl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `yhid` int(11) DEFAULT NULL,
  `dygn` varchar(255) DEFAULT NULL COMMENT '对应的功能',
  `zddm` varchar(255) DEFAULT NULL COMMENT '字段代码',
  `yxbz` varchar(1) DEFAULT '1' COMMENT '1-有效，0-无效',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `lrry` int(11) DEFAULT NULL COMMENT '录入人员',
  `xgry` int(11) DEFAULT NULL COMMENT '修改人员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COMMENT='用户导出自定义列保存';

