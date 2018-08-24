<?xml version="1.0" encoding="gbk"?>
<business id="10008" comment="发票开具">
    <body yylxdm="1">
    <kpzdbs>${skp.kpdip}</kpzdbs>
    <fplxdm>${fplxdm}</fplxdm>
    <fpqqlsh>${kpls.kplsh}</fpqqlsh>
    <kplx>${kplx!}</kplx>
    <tspz>${tspz}</tspz>
    <xhdwsbh>${kpls.xfsh}</xhdwsbh>
    <xhdwmc>${kpls.xfmc!}</xhdwmc>
    <xhdwdzdh>${kpls.xfdz!} ${kpls.xfdh!}</xhdwdzdh>
    <xhdwyhzh>${kpls.xfyh!} ${kpls.xfyhzh!} </xhdwyhzh>
    <ghdwsbh>${kpls.gfsh!}</ghdwsbh>
    <ghdwmc><![CDATA[${kpls.gfmc!}]]></ghdwmc>
    <ghdwdzdh>${kpls.gfdz!} ${kpls.gfdh!}</ghdwdzdh>
    <ghdwyhzh>${kpls.gfyh!} ${kpls.gfyhzh!}</ghdwyhzh>
    <qdbz>${kpls.sfdyqd!"0"}</qdbz>
    <zsfs>${kpls.zsfs!"0"}</zsfs>
    <fyxm count="${count}">
        <#list jyspmxList! as jyspmx>
            <#assign xh=jyspmx_index+1 />
        <group xh="${xh}">
            <fphxz>${jyspmx.fphxz!0}</fphxz>
            <spmc><![CDATA[${jyspmx.spmc!}]]></spmc>
            <spsm></spsm>
            <ggxh>${jyspmx.spggxh!}</ggxh>
            <dw>${jyspmx.spdw!}</dw>
            <spsl>${(jyspmx.sps?string('#.###############'))!}</spsl>
            <dj>${(jyspmx.spdj?string('#.###############'))!}</dj>
            <je>${jyspmx.spje?string('#.######')}</je>
            <sl>${jyspmx.spsl?string('#0.00####')}</sl>
            <se>${jyspmx.spse?string('#.######')}</se>
            <hsbz>${kpls.hsbz!0}</hsbz>
            <spbm>${jyspmx.spdm!}</spbm>
            <zxbm></zxbm>
            <yhzcbs><#if (jyspmx.yhzcbs)??><#if jyspmx.yhzcbs!="">${jyspmx.yhzcbs!}<#else>0</#if><#else>${jyspmx.yhzcbs!"0"}</#if></yhzcbs>
            <lslbs>${jyspmx.lslbz!}</lslbs>
            <zzstsgl>${jyspmx.yhzcmc!}</zzstsgl>
        </group>
        </#list>
    </fyxm>
    <hjje>${(kpls.hjje!?string('#.######'))!}</hjje>
    <hjse>${(kpls.hjse!?string('#.######'))!}</hjse>
    <jshj>${(kpls.jshj?string('#.######'))!}</jshj>
    <kce><#if (kce)??>${(kce!?string('#.######'))!}<#else>${kce!}</#if></kce>
    <bz>${kpls.bz}</bz>
    <skr>${kpls.skr!}</skr>
    <fhr>${kpls.fhr!}</fhr>
    <kpr>${kpls.kpr!}</kpr>
    <tzdbh>${kpls.hztzdh!}</tzdbh>
    <yfpdm>${kpls.hzyfpdm!}</yfpdm>
    <yfphm>${kpls.hzyfphm!}</yfphm>
    </body>
</business>
