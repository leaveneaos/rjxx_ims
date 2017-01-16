CREATE TABLE `t_dr_pz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `yhid` int(11) NOT NULL COMMENT '用户id',
  `zdm` varchar(50) DEFAULT NULL COMMENT '字段名',
  `pzlx` varchar(50) DEFAULT NULL COMMENT '配置类型，auto-默认设置，config-对应导入文件',
  `pzz` varchar(100) DEFAULT NULL COMMENT '配置的值',
  `lrsj` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_yhid` (`yhid`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT='导入配置';