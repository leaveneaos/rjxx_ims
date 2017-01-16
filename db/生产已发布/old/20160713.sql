create or replace view kplsvo as
select `ls`.`kplsh` AS `id`,`ls`.`gsdm`,`ls`.`pdfurl` AS `href`,`mx`.`kprq` AS `kprq`,`ls`.`gfdh` AS `sj`,`ls`.`gfemail` AS `yx`,`ls`.`jylsh` AS `jylsh`,`ls`.`gfmc` AS `gfmc`,`ls`.`jylssj` AS `jylssj`,`ls`.`fpdm` AS `fpdm`,`ls`.`fphm` AS `fphm`,sum(`mx`.`spje`) AS `je`,sum(`mx`.`spse`) AS `se`,sum((`mx`.`spje` + `mx`.`spse`)) AS `jshj`,`ls`.`fpzldm` AS `fpzldm`,`ls`.`fpczlxdm` AS `fpczlxdm`,`ls`.`zfr` AS `zfr`,`ls`.`hzyfpdm` AS `hzyfpdm`,`ls`.`hzyfphm` AS `hzyfphm`,`ls`.`djh` AS `djh`,`ls`.`gfsh` AS `gfsh`,`jy`.`ddh` AS `ddh`,`ls`.`gfyh` AS `gfyh`,`ls`.`gfyhzh` AS `gfyhzh`,`ls`.`hkbz` AS `hkbz`,`ls`.`kpr` AS `kpr` from ((`t_kpls` `ls` join `t_kpspmx` `mx`) join `t_jyls` `jy`) where ((`ls`.`kplsh` = `mx`.`kplsh`) and (`ls`.`djh` = `mx`.`djh`) and (`ls`.`djh` = `jy`.`djh`)) group by `ls`.`kplsh`,`ls`.`pdfurl`,`mx`.`kprq`,`ls`.`gfdh`,`ls`.`gfemail`,`ls`.`jylsh`,`ls`.`gfmc`,`ls`.`jylssj`,`ls`.`fpdm`,`ls`.`fphm`,`ls`.`gsdm`,`ls`.`fpczlxdm`,`ls`.`fpzldm`,`ls`.`zfr`,`ls`.`hzyfpdm`,`ls`.`hzyfphm`,`ls`.`gfsh`,`jy`.`ddh`,`ls`.`gfyh`,`ls`.`gfyhzh`,`ls`.`hkbz`,`ls`.`kpr`;

CREATE or replace
VIEW `kplsvo2`AS
select a.djh,a.ddh,a.fpdm,a.fphm,a.je,a.se,a.jshj,a.gfmc,a.kprq,a.kpr,a.href,'12' fpzldm,gsdm from kplsvo a where a.fpzldm = '12' and a.hkbz = '0' and a.fpczlxdm = '11'
union all
select a.djh,a.ddh,a.fpdm,a.fphm,a.spje_bhs,a.spse,a.jshj,a.gfmc,date(a.dyrq),'系统',null,'02',gsdm from t_kj_zzfp a where a.fphm is not null;
