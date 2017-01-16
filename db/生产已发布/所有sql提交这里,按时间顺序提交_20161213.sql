--liuyuanya 12.14 13:00
ALTER TABLE `t_jyls`
ADD COLUMN `gfsjh`  varchar(50) NULL COMMENT '购方手机号' AFTER `skpid`;

ALTER TABLE `t_csb`
ADD COLUMN `mrz`  varchar(255) NULL COMMENT '默认值' AFTER `cszlx`;

UPDATE t_csb SET mrz = '12.0' WHERE csm='spbmbbh';
UPDATE t_csb SET mrz = '是' WHERE csm='sfhksh';
UPDATE t_csb SET mrz = '否' WHERE csm='sflxkp';
UPDATE t_csb SET mrz = '是' WHERE csm='sfcksh';
UPDATE t_csb SET mrz = '否' WHERE csm='sfzczt';
UPDATE t_csb SET mrz = '否' WHERE csm='sfzcyj';
UPDATE t_csb SET mrz = '否' WHERE csm='sfzchk';
UPDATE t_csb SET mrz = '否' WHERE csm='sfzcck';


--- 1214 kzx 新增功能节点

INSERT INTO `privileges` ( `privilegeTypeId`, `name`, `description`, `sort`, `urls`, 
`ztbz`, `lrsj`, `lrry`, `xgsj`, `xgry`, `yxbz`) VALUES ( '3', '交易信息查询', 'am-icon-
search', '34', '/jyxxcx', '1', NULL, NULL, NULL, NULL, '1');


INSERT INTO `roleprivs` (`roleid`, `privid`, `ButtonPrivs`, `yxbz`, `description`, `sort`, 
`lrsj`, `lrry`, `xgsj`, `xgry` ) VALUES ('1', '35' , 'smc', '1', NULL, NULL, '2016-12-14 
10:04:08', '4', '2016-12-14 10:04:11', '4');