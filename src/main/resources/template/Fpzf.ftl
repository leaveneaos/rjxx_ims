<?xml version="1.0" encoding="gbk"?>
<business id="10009" comment="发票作废">
    <body yylxdm="1">
    <kpzdbs>${kpdip}</kpzdbs>
    <fplxdm>${fplxdm}</fplxdm>
    <zflx>${zflx!"1"}</zflx>
    <fpdm>${kpls.fpdm!}</fpdm>
    <fphm>${kpls.fphm!}</fphm>
    <hjje>${(kpls.hjje!?string('#.######'))!}</hjje>
    <zfr>${zfr!}</zfr>
    </body>
</business>
