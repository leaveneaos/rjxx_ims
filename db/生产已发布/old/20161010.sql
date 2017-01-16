ALTER TABLE `t_jyspmx` DROP PRIMARY KEY;
ALTER TABLE `t_jyspmx`
ADD COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ,
ADD PRIMARY KEY (`id`);
ALTER TABLE `t_jyspmx`
DROP INDEX `FK_T_JYSPMX_lrry`,
DROP INDEX `FK_T_JYSPMX_xgry`,
ADD UNIQUE INDEX `unique_1` (`djh`, `spmxxh`) ;



--liuyuanya 2016.10.17 10:30
--状态标志改为有效标志
ALTER TABLE `t_xf`
CHANGE COLUMN `ztbz` `yxbz`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态标识 0：待复核；1：已生效；2：已失效' AFTER `zfr`;
ALTER TABLE `t_xf`
CHANGE COLUMN `xfid` `id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '销方ID' FIRST ;


--修改t_kpd
ALTER TABLE `t_kpd`
ADD COLUMN `id`  int NOT NULL AUTO_INCREMENT AFTER `xgry`,
ADD COLUMN `yxbz`  varchar(1) NULL COMMENT '有效标志：0，无效；1，有效' AFTER `id`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

--税控盘表增加有效标志
ALTER TABLE `t_skp`
ADD COLUMN `yxbz`  varchar(1) NULL COMMENT '有效标志：0，无效；1，有效' AFTER `gsdm`;

ALTER TABLE `t_skp`
ADD COLUMN `dpmax`  numeric(18,2) NULL COMMENT '电子发票最大开票限额' AFTER `yxbz`,
ADD COLUMN `fpfz`  numeric(18,2) NULL COMMENT '电子发票开票阈值' AFTER `dpmax`,
ADD COLUMN `ppmax`  numeric(18,2) NULL COMMENT '普票开票最大限额' AFTER `fpfz`,
ADD COLUMN `ppfz`  numeric(18,2) NULL COMMENT '普票开票阈值' AFTER `ppmax`;
ALTER TABLE `t_skp`
ADD COLUMN `id`  int NOT NULL AUTO_INCREMENT AFTER `ppfz`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);
ALTER TABLE `t_skp`
ADD COLUMN `zpmax`  decimal(18,2) NULL COMMENT '专票最大开票限额' AFTER `id`,
ADD COLUMN `zpfz`  decimal(18,2) NULL COMMENT '专票阈值' AFTER `zpmax`,
ADD COLUMN `kpdip`  varchar(50) NULL COMMENT '开票点ip地址' AFTER `zpfz`;


--修改t_sm
ALTER TABLE `t_sm`
ADD COLUMN `yxbz`  varchar(1) NULL COMMENT '有效标志：0，无效；1，有效' AFTER `xgry`;
ALTER TABLE `t_sp`
CHANGE COLUMN `smdm` `smid`  int(11) NOT NULL COMMENT '税目id' AFTER `spmc`;


--修改spvo视图
drop view spvo;
CREATE 
VIEW `spvo`AS 
SELECT
	`a`.`id` AS `id`,
	`a`.`spdm` AS `spdm`,
	`a`.`spmc` AS `spmc`,
	`a`.`smid` AS `smid`,
	`a`.`spfldm` AS `spfldm`,
	`a`.`spggxh` AS `spggxh`,
	`a`.`spdw` AS `spdw`,
	`a`.`spdj` AS `spdj`,
	`a`.`gsdm` AS `gsdm`,
	`a`.`yxbz` AS `yxbz`,
	`a`.`lrsj` AS `lrsj`,
	`a`.`lrry` AS `lrry`,
	`a`.`xgsj` AS `xgsj`,
	`a`.`xgry` AS `xgry`,
	`c`.`smmc` AS `smmc`,
	`c`.`sl` AS `sl`
FROM
	(`t_sp` `a` JOIN `t_sm` `c`)
WHERE
	(
		(`a`.`smid` = `c`.`id`)
		AND (`a`.`yxbz` = '1')
	) ;


--修改spbm 
ALTER TABLE `spbm`
ADD COLUMN `yxbz`  varchar(1) NULL DEFAULT '1' COMMENT '有效标志：0，无效；1，有效' AFTER `zzstsgl`;

--liuyuanya 2016.10.17 15:30
--修改t_kpls
ALTER TABLE `t_kpls`
ADD COLUMN `yxbz`  varchar(1) NULL DEFAULT '1' COMMENT '有效标志：0，无效；1，有效' AFTER `printflag`;



--liuyuanya 2016.10.24 15:00

ALTER TABLE `t_skp`
ADD COLUMN `kpdmc`  varchar(50) NULL COMMENT '开票点名称' AFTER `yxbz`;
