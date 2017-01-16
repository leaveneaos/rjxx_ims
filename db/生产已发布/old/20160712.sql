ALTER TABLE `t_yh`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `yhjg`;

ALTER TABLE `t_xf`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `kpzdje`;


ALTER TABLE `t_skp`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `xfid`;

ALTER TABLE `t_jyls`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `gfsjr`;

ALTER TABLE `t_jyspmx`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `xgry`;

ALTER TABLE `t_xml`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `lrsj`;

ALTER TABLE `t_kpls`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `xgry`;

ALTER TABLE `t_kpspmx`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `xgry`;

ALTER TABLE `t_kj_zzfp`
ADD COLUMN `gsdm`  varchar(50) NULL  COMMENT '公司代码' AFTER `jjrdz`;

