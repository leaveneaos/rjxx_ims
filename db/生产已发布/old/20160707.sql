create or replace
VIEW `skpvo` AS
SELECT
t1.skph AS skph,
t2.xfmc AS xfmc,
t1.bz AS bz,
t1.fpyz AS fpyz,
t2.xfid
from ((`t_skp` `t1` join `t_xf` `t2`) join `t_kpd` `t3`)
where ((`t1`.`skph` = `t3`.`skph`) and (`t2`.`xfid` = `t3`.`xfid`))
group by `t1`.`skph`,`t2`.`xfmc`,`t1`.`bz`,`t1`.`fpyz` ;


alter table t_skp add xfid int(11) comment '销方id';

alter table t_yh add appkey varchar(32) comment '授权key';
alter table t_yh add secret varchar(32) comment '授权秘钥';