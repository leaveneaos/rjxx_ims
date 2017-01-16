
--liuyan 12.7  保存食其家提取码提取信息
DROP TABLE IF EXISTS `t_tqmtq`;
CREATE TABLE `t_tqmtq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ddh` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单号 也是提取码',
  `lrsj` date DEFAULT NULL COMMENT '录入时间',
  `zje` decimal(10,0) DEFAULT NULL COMMENT '金额',
  `gfmc` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '购方名称',
  `nsrsbh` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '纳税人识别号',
  `dz` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `dh` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话',
  `khh` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '开户行',
  `khhzh` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '开户行账号',
  `fpzt` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `yxbz` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '有效标志',
  `gsdm` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司代码',
  PRIMARY KEY (`id`)
)


---kangzhongxu1209 t_gsxx新增ws_url
ALTER TABLE `t_gsxx`
ADD COLUMN `ws_url`  text NULL AFTER `appkey`;

--liuyuanya 2016-12-12
ALTER TABLE `t_gsxx`
ADD COLUMN `wxappid`  varchar(500) NULL COMMENT '微信appid' AFTER `ws_url`,
ADD COLUMN `wxsecret`  varchar(500) NULL COMMENT '微信secret' AFTER `wxappid`;

---liuyan 12.12 修改金额增加小数点
ALTER TABLE `t_tqmtq`
MODIFY COLUMN `zje`  decimal(10,2) NULL DEFAULT NULL COMMENT '金额' AFTER `lrsj`;

ALTER TABLE `t_smtq`
MODIFY COLUMN `zje`  decimal(10,2) NULL DEFAULT NULL AFTER `jylssj`;

--liuyuanya 12.12 17:30
ALTER TABLE `t_skp`
ADD COLUMN `pid`  int NULL COMMENT '品牌id' AFTER `kpddm`;

CREATE TABLE `t_pp` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `ppdm` varchar(50) DEFAULT NULL COMMENT '品牌代码',
  `ppmc` varchar(50) DEFAULT NULL COMMENT '品牌名称',
  `ppurl` varchar(255) DEFAULT NULL COMMENT '品牌url',
  `yxbz` varchar(1) DEFAULT NULL COMMENT '有效标志：0，无效；1，有效',
  `lrry` int(11) DEFAULT NULL COMMENT '录入人员',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  `xgry` int(11) DEFAULT NULL COMMENT '修改人员',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);
ALTER TABLE `t_pp`
ADD COLUMN `gsdm`  varchar(20) NULL COMMENT '公司代码' AFTER `xgsj`;

INSERT INTO `t_pp` (`id`, `ppdm`, `ppmc`, `ppurl`, `yxbz`, `lrry`, `lrsj`, `xgry`, `xgsj`, `gsdm`) VALUES ('1', '1001', '食其家', NULL, '1', '1', '2016-12-12 17:58:20', '1', '2016-12-12 17:58:24', 'sqj');
INSERT INTO `t_pp` (`id`, `ppdm`, `ppmc`, `ppurl`, `yxbz`, `lrry`, `lrsj`, `xgry`, `xgsj`, `gsdm`) VALUES ('2', '1002', '乌冬面', NULL, '1', '1', '2016-12-12 17:58:47', '1', '2016-12-12 17:58:53', 'sqj');
INSERT INTO `t_pp` (`id`, `ppdm`, `ppmc`, `ppurl`, `yxbz`, `lrry`, `lrsj`, `xgry`, `xgsj`, `gsdm`) VALUES ('3', '1003', '滨寿司', NULL, '1', '1', '2016-12-12 17:59:44', '1', '2016-12-12 17:59:48', 'sqj');

--liuyan 12.13 插入跳转url

UPDATE t_pp SET ppurl='/smtq/smtq1.html' WHERE ppmc='食其家'
UPDATE t_pp SET ppurl='/smtq/sqjsssmtq.html' WHERE ppmc='滨寿司'
UPDATE t_pp SET ppurl='/smtq/sqjwdmsmtq.html' WHERE ppmc='乌冬面'
