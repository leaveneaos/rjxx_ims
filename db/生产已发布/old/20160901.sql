CREATE TABLE `t_kppmxx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `xfsh` varchar(20) DEFAULT NULL COMMENT '销方税号',
  `gsdm` varchar(255) DEFAULT NULL COMMENT '公司代码',
  `skr` varchar(255) DEFAULT 'auto',
  `fhr` varchar(255) DEFAULT 'auto',
  `kpr` varchar(255) DEFAULT 'auto',
  `xfyh` varchar(255) DEFAULT 'auto' COMMENT '销方银行',
  `xfyhzh` varchar(255) DEFAULT 'auto' COMMENT '销方银行账号',
  `xfdz` varchar(255) DEFAULT 'auto' COMMENT '销方地址',
  `xfdh` varchar(255) DEFAULT 'auto' COMMENT '销方电话',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_1` (`xfsh`,`gsdm`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='开票票面信息';

-- ----------------------------
-- Records of t_kppmxx
-- ----------------------------
INSERT INTO `t_kppmxx` VALUES ('1', '310106550096887', 'af', '系统', '系统', '系统', null, '', '', null);

ALTER TABLE `t_sp`
ADD COLUMN `id`  int NOT NULL AUTO_INCREMENT FIRST ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);

CREATE
OR REPLACE VIEW jylsvo AS SELECT
	a.*, b.clztmc,
	c.fpczlxmc,
	d.fpzlmc
FROM
	t_jyls a,
	t_clzt b,
	t_fpczlx c,
	t_fpzl d
WHERE
	a.clztdm = b.clztdm
AND a.fpczlxdm = c.fpczlxdm
AND a.fpzldm = d.fpzldm;


