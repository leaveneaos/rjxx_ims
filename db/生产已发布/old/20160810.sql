ALTER TABLE `t_sp`
MODIFY COLUMN `spdm`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品代码' FIRST ;


ALTER TABLE `t_kpspmx`
MODIFY COLUMN `spdm`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品代码' AFTER `fphxz`;

ALTER TABLE `t_jyspmx`
MODIFY COLUMN `spdm`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品代码' AFTER `fphxz`;

ALTER TABLE `t_jyspmx` DROP FOREIGN KEY `FK_T_JYSPMX_lrry`;

ALTER TABLE `t_jyspmx` DROP FOREIGN KEY `FK_T_JYSPMX_spdm`;

ALTER TABLE `t_jyspmx` DROP FOREIGN KEY `FK_T_JYSPMX_xgry`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_hcry`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_kpddm`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `fk_t_kpspmx_kplsh`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_kpry`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_lrry`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_skph`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_spdm`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_xgry`;

ALTER TABLE `t_kpspmx` DROP FOREIGN KEY `FK_T_KPSPMX_zfry`;

ALTER TABLE `t_kpls`
ADD COLUMN `kprq`  datetime NULL COMMENT '开票日期' AFTER `gsdm`,
ADD COLUMN `zfrq`  datetime NULL DEFAULT NULL COMMENT '作废日期' AFTER `kprq`,
ADD COLUMN `hcrq`  datetime NULL DEFAULT NULL COMMENT '红冲日期' AFTER `zfrq`,
ADD COLUMN `kpry`  int(10) NULL DEFAULT NULL COMMENT '开票人员' AFTER `hcrq`,
ADD COLUMN `zfry`  int(10) NULL DEFAULT NULL COMMENT '作废人员' AFTER `kpry`,
ADD COLUMN `hcry`  int(10) NULL DEFAULT NULL COMMENT '红冲人员' AFTER `zfry`,
ADD COLUMN `kpddm`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开票点代码' AFTER `hcry`,
ADD COLUMN `skph`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '税控盘号' AFTER `kpddm`;


ALTER TABLE `t_kpls` DROP FOREIGN KEY `FK_T_KPLS_djh`;

ALTER TABLE `t_kpls` DROP FOREIGN KEY `FK_T_KPLS_fpczlxdm`;

ALTER TABLE `t_kpls` DROP FOREIGN KEY `FK_T_KPLS_fpzldm`;

ALTER TABLE `t_kpls` DROP FOREIGN KEY `FK_T_KPLS_gfid`;

ALTER TABLE `t_kpls` DROP FOREIGN KEY `FK_T_KPLS_lrry`;

ALTER TABLE `t_kpls` DROP FOREIGN KEY `FK_T_KPLS_xfid`;

ALTER TABLE `t_kpls` DROP FOREIGN KEY `FK_T_KPLS_xgry`;

ALTER TABLE `t_kpls`
ADD COLUMN `jshj`  double(18,2) NULL DEFAULT NULL COMMENT '价税合计' AFTER `skph`;

ALTER TABLE `t_kpls`
ADD COLUMN `hjje`  double(18,2) NULL COMMENT '合计金额' AFTER `jshj`,
ADD COLUMN `hjse`  double(18,2) NULL COMMENT '合计税额' AFTER `hjje`;

insert into t_fpczlx values ('13','纸质发票换开',CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,1);

ALTER TABLE `t_kpspmx`
MODIFY COLUMN `fpdm`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '发票代码' AFTER `spse`,
MODIFY COLUMN `fphm`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '发票号码' AFTER `fpdm`,
MODIFY COLUMN `kprq`  datetime NULL COMMENT '开票日期' AFTER `fphm`;


ALTER TABLE `t_jyls`
ADD COLUMN `tqm`  varchar(255) NULL COMMENT '提取码' AFTER `gsdm`;

create or replace view spvo as
select a.*,b.spflmc,c.smmc,c.sl from t_sp a,t_spfl b,t_sm c where a.spfldm = b.spfldm and a.smdm = c.smdm and a.yxbz = '1';

ALTER TABLE `t_sp`
MODIFY COLUMN `gsdm`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司代码' AFTER `spdj`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`spdm`, `gsdm`);

create or replace view kplsvo as
select a.*,a.hjje je,a.hjse se,b.ddh,b.tqm from t_kpls a,t_jyls b where a.djh = b.djh;

CREATE
OR REPLACE VIEW kplsvo2 AS SELECT
	a.kplsh,
	a.djh,
	a.ddh,
	a.fpdm,
	a.fphm,
	a.je,
	a.se,
	a.jshj,
	a.gfmc,
	a.kprq,
	a.kpr,
	a.pdfurl,
	gsdm,
	a.fpzldm,
	a.fpczlxdm,
	a.hzyfpdm,
	a.hzyfphm,
	CASE
WHEN (
	fpczlxdm = '11'
	AND hzyfphm IS NOT NULL
	AND hkbz = '0'
) THEN
	'被红冲'
WHEN (
	fpczlxdm = '11'
	AND hzyfphm IS NOT NULL
	AND hkbz = '1'
) THEN
	'被换开'
ELSE
	'正常'
END AS fpzt
FROM
	kplsvo a
UNION ALL
	SELECT
		a.djh,
		a.djh AS djh1,
		a.ddh,
		a.fpdm,
		a.fphm,
		a.spje_bhs,
		a.spse,
		a.jshj,
		a.gfmc,
		date(a.dyrq),
		'系统',
		NULL,
		gsdm,
		'02',
		'11',
		NULL,
		NULL,
		'正常'
	FROM
		t_kj_zzfp a
	WHERE
		a.fphm IS NOT NULL;



-- ----------------------------
-- Table structure for t_gsxx
-- ----------------------------
DROP TABLE IF EXISTS `t_gsxx`;
CREATE TABLE `t_gsxx` (
  `gsdm` varchar(255) NOT NULL COMMENT '公司代码',
  `gsmc` varchar(255) DEFAULT NULL COMMENT '公司名称',
  PRIMARY KEY (`gsdm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_gsxx
-- ----------------------------
INSERT INTO `t_gsxx` VALUES ('af', '爱芙趣商贸（上海）有限公司');
INSERT INTO `t_gsxx` VALUES ('zydc', '上海中原物业顾问有限公司');