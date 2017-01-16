ALTER TABLE `t_spfl`
ADD COLUMN `gsdm`  varchar(50) NULL COMMENT '公司代码' AFTER `spflmc`;

ALTER TABLE `t_sp`
ADD COLUMN `gsdm`  varchar(50) NULL COMMENT '公司代码' AFTER `spdj`;

create or replace view spvo AS
select a.spdm,a.spmc,a.smdm,a.spfldm,a.spggxh,a.spdw,a.spdj,a.gsdm,a.yxbz,b.spflmc,c.smmc,c.sl from t_sp a,t_spfl b,t_sm c where a.spfldm = b.spfldm and a.smdm = c.smdm;