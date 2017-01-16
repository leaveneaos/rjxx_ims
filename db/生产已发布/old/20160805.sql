CREATE TABLE `t_rabbitmq` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `host` varchar(100) DEFAULT NULL COMMENT '主机地址',
  `port` int(6) DEFAULT '5672' COMMENT '端口',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `vhost` varchar(255) DEFAULT NULL COMMENT '虚拟主机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='rabbitmq的信息';

CREATE TABLE `t_xf_mq` (
  `mqid` int(3) NOT NULL COMMENT 'mq的id',
  `xfid` int(11) NOT NULL COMMENT '销方id',
  PRIMARY KEY (`mqid`,`xfid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into t_rabbitmq values(1,'121.40.78.222',5672,'af','134079b2ecdd720ec04f3d26f8123d1e','/af');
insert into t_rabbitmq values(2,'121.40.78.222',5672,'zydc','b70af78b5fd840b16f75fc0adec0c147','/zydc');