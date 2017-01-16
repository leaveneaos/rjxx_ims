---liuyan 注册给公司人员发送邮件
CREATE TABLE `t_xxts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `xm` varchar(255) DEFAULT NULL,
  `yx` varchar(255) DEFAULT NULL,
  `yxbz` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

INSERT INTO t_zdpp VALUES(28,"kpddm","开票点代码");


--liuyuanya 12.28 测试环境已执行
CREATE TABLE `t_wxfs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gsdm` varchar(50) DEFAULT NULL COMMENT '公司代码',
  `djh` int(11) DEFAULT NULL COMMENT '单据号',
  `openid` varchar(100) DEFAULT NULL COMMENT '微信openid',
  `issuccess` varchar(255) DEFAULT NULL COMMENT '是否发送成功',
  `returnmsg` varchar(255) DEFAULT NULL COMMENT '返回信息',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) COMMENT='微信模板信息发送记录';



--liuyan 2017.1.4
CREATE TABLE `t_tqlj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gsdm` varchar(255) DEFAULT NULL,
  `yxbz` varchar(255) DEFAULT NULL,
  `ppmc` varchar(255) DEFAULT NULL,
  `imgurl` varchar(255) DEFAULT NULL,
  `ppdm` varchar(255) DEFAULT NULL,
  `gsmc` varchar(255) DEFAULT NULL,
  `tqlj` varchar(255) DEFAULT NULL COMMENT '提取链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


--注册公司 2017.1.4
CREATE TABLE `t_gszc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gsmc` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `lxr` varchar(255) DEFAULT NULL COMMENT '联系人',
  `zw` varchar(255) DEFAULT NULL COMMENT '职位',
  `lxdh` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `sscs` varchar(255) DEFAULT NULL COMMENT '所属城市',
  `lrsj` datetime DEFAULT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;




ALTER TABLE `t_tqlj`
ADD COLUMN `sort`  varchar(255) NULL COMMENT '排序字段' AFTER `tqlj`;



INSERT INTO t_zdpp(zdm,zdzwm) VALUES('hjse','合计税额')
INSERT INTO t_zdpp(zdm,zdzwm) VALUES('hjje','合计金额')


--liuyan 1.11
ALTER TABLE `t_smtq`
MODIFY COLUMN `kpddm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `gsdm`,
MODIFY COLUMN `openid`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `kpddm`;






--kzx 1.13
CREATE TABLE `t_jyxxsq` (
`sqlsh`  int(10) NOT NULL AUTO_INCREMENT COMMENT '请求单据号 TaxEasy系统自动生成，每次请求唯一。' ,
`kpddm`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开票点代码' ,
`jylsh`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易流水号 业务系统或电商平台生成，包括所有需要开票和不需要开票的全部交易流水号。' ,
`ddrq`  datetime NULL DEFAULT NULL COMMENT '订单申请时间 业务系统或电商平台生成。' ,
`ddh`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号 业务系统或电商平台生成。' ,
`fpzldm`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票种类代码 业务系统或电商平台生成，或提供业务规则由TaxEasy生成。代码见t_fpzl。' ,
`fpczlxdm`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票操作类型代码 业务系统或电商平台生成，或提供业务规则由TaxEasy生成。代码见t_fpczlx。' ,
`xfid`  int(10) NULL DEFAULT NULL COMMENT '销方ID' ,
`xfsh`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '销方税号' ,
`xfmc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '销方名称' ,
`xfyh`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销方银行' ,
`xfyhzh`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销方银行账号' ,
`xflxr`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销方联系人' ,
`xfdz`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销方地址' ,
`xfdh`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销方电话' ,
`xfyb`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销方邮编' ,
`gfid`  int(10) NULL DEFAULT NULL COMMENT '购方ID' ,
`gfsh`  varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方税号' ,
`gfmc`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方名称' ,
`gfyh`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方银行' ,
`gfyhzh`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方银行账号' ,
`gflxr`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方联系人' ,
`gfdz`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方地址' ,
`gfdh`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方电话' ,
`gfyb`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方邮编' ,
`gfemail`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址' ,
`sffsyj`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '开具类型(0、纸质开具；1、电子开具；2、部分退货暂不开具；3、部分折扣暂不开具' ,
`clztdm`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子发票处理状态代码' ,
`bz`  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`skr`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款人' ,
`kpr`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开票(红冲、作废)人' ,
`fhr`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '复核人' ,
`sfcp`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否自动拆分？（1、拆分；0、不拆分）' ,
`sfdyqd`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否打印清单，1 打印清单 0 不打印清单' ,
`zsfs`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '征税方式：0-普通征税，1-减按征税，2-差额征税' ,
`ssyf`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属月份' ,
`hztzdh`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专票红字通知单号' ,
`yfpdm`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '红冲(作废)对应原发票代码' ,
`yfphm`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '红冲(作废)对应原发票号码' ,
`hsbz`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '含税标志 0、不含税；1、含税' ,
`jshj`  double(18,2) NOT NULL COMMENT '价税合计 价税合计' ,
`ykpjshj`  double(18,2) NOT NULL DEFAULT 0.00 COMMENT '已开票价税合计 已开票价税合计' ,
`yxbz`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效标志 1、有效；0、无效' ,
`lrsj`  datetime NOT NULL COMMENT '录入时间 系统时间' ,
`lrry`  int(10) NULL DEFAULT NULL COMMENT '录入人员' ,
`xgsj`  datetime NOT NULL COMMENT '修改时间' ,
`xgry`  int(10) NULL DEFAULT NULL COMMENT '修改人员' ,
`gfsjr`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方收件人' ,
`gfsjrdz`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方收件人地址' ,
`gsdm`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司代码' ,
`tqm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提取码' ,
`skpid`  int(10) NULL DEFAULT NULL ,
`gfsjh`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '购方手机号' ,
`dxzt`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' ,
PRIMARY KEY (`sqlsh`),
UNIQUE INDEX `idx_t_jyxxsq_djh` (`sqlsh`) USING BTREE ,
INDEX `IDX_t_jyxxsq_xfid` (`xfid`) USING BTREE ,
INDEX `FK_t_jyxxsq_fpzldm` (`fpzldm`) USING BTREE ,
INDEX `FK_t_jyxxsq_fpczlxdm` (`fpczlxdm`) USING BTREE ,
INDEX `FK_t_jyxxsq_gfid` (`gfid`) USING BTREE ,
INDEX `FK_t_jyxxsq_clztdm` (`clztdm`) USING BTREE ,
INDEX `FK_t_jyxxsq_lrry` (`lrry`) USING BTREE ,
INDEX `FK_t_jyxxsq_xgry` (`xgry`) USING BTREE ,
INDEX `idx_jyxxsq_gsdm` (`gsdm`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='交易信息申请 该表用于记录全部交易申请信息。'
AUTO_INCREMENT=18475
ROW_FORMAT=COMPACT
;






CREATE TABLE `t_jymxsq` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`sqlsh`  int(10) NOT NULL COMMENT '单据号' ,
`ddh`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号 业务系统或电商平台生成。' ,
`kpddm`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开票点代码' ,
`hsbz`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '含税标志，1含税，0不含税' ,
`spmxxh`  int(3) NOT NULL COMMENT '商品明细序号' ,
`fphxz`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '0、正常行；1、折扣行；2、被折扣行' ,
`spdm`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品代码' ,
`spmc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称' ,
`spggxh`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品规格型号' ,
`spzxbm`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品自行编码' ,
`yhzcbs`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '优惠政策标识' ,
`yhzcmc`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠政策名称' ,
`lslbz`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '零税率标志' ,
`spdw`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品单位' ,
`sps`  double(18,6) NULL DEFAULT NULL COMMENT '商品数量' ,
`spdj`  double(18,6) NULL DEFAULT NULL COMMENT '商品单价' ,
`spje`  double(18,2) NOT NULL COMMENT '商品金额' ,
`spsl`  double(18,6) NOT NULL COMMENT '商品税率' ,
`spse`  double(18,2) NULL DEFAULT NULL COMMENT '商品税额' ,
`kce`  double(18,2) NULL DEFAULT NULL COMMENT '扣除金额' ,
`jshj`  double(18,2) NOT NULL COMMENT '价税合计' ,
`hzkpxh`  int(10) NULL DEFAULT NULL COMMENT '汇总开票序号' ,
`lrsj`  datetime NOT NULL COMMENT '录入时间' ,
`lrry`  int(10) NULL DEFAULT NULL COMMENT '录入人员' ,
`xgsj`  datetime NOT NULL COMMENT '修改时间' ,
`xgry`  int(10) NULL DEFAULT NULL COMMENT '修改人员' ,
`gsdm`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司代码' ,
`skpid`  int(10) NULL DEFAULT NULL ,
`xfid`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `unique_1` (`sqlsh`, `spmxxh`) USING BTREE ,
INDEX `IDX_t_jymxsq_spdm` (`spdm`) USING BTREE ,
INDEX `idx_t_jymxsqdjh` (`sqlsh`) USING BTREE ,
INDEX `idx_t_jymxsq_gsdm` (`gsdm`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='交易信息明细申请表'
AUTO_INCREMENT=9660
ROW_FORMAT=COMPACT
;



ALTER TABLE `t_tqmtq`
ADD COLUMN `llqxx`  varchar(255) NULL AFTER `gfsjh`;
ALTER TABLE `t_smtq`
ADD COLUMN `llqxx`  varchar(255) NULL AFTER `gfsjh`;