<%@page import="com.rjxx.taxeasy.domains.Jyls"%>
<%@page import="com.rjxx.taxeasy.controller.KpController"%>
<%@page import="com.rjxx.taxeasy.domains.Jyspmx"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="models.*"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<style type="text/css" rel="stylesheet">

.printdiv
{ 
  width:938px; 
  height:100%;
}
.table-overAuto>tbody {
    display:block;
    height:130px;
    overflow-y:auto;
}



@page {
    /*size: 297mm 210mm;*/
    size: 240mm ${210+(x-7)*6}mm
}

@media (max-width: 240mm) {
    @page {
        /*size: 297mm 210mm;*/
        size: 240mm ${210+(x-7)*6}mm;
    }
}

.printed{
    font-family: SimSun;
}
.printed span{
    font-family: SimSun;
    font-size: 9pt;
}
/*.filled{
    font-family: SimSun;
    font-size: 9pt;
}*/
/*body {
    margin-left: 1px;
    margin-right: 1px;
    !*font-family: Arial Unicode MS;*!
    font-size: 13px;
}*/

span {
    color: black;
}

#div {
    width: 570px;
    height: 595px;
}

#bodyer {
    height: 400px;
}

h2 {
    color: #9E520A;
}

.header_t1 {
    height: 93px;
    width: 100%;
    margin-left: 2px;
    padding: 0px;
    margin-top: 5px;
}

.boyder_t1 {
    border: 1px #9E520A solid;
    margin-left: 2px;
    margin-top: 2px;
    width: 100%;
}

.boyder_t2 {

    height: 80px;
    border-bottom: 1px solid #9E520A;
    border-collapse: collapse;

}

.boyder_td1 {
    width: 25px;
    height: 105px;
    border-right: 1px solid #da731b;
    font-size: 10pt;
    color: #9E520A;
}

.boyder_td2 {
    width: 50%;
    height: 105px;
    border-right: 1px solid #da731b;
}

.boyder_tr1 {
    text-align: center;
    vertical-align: top;
    border-top: 0px solid #da731b;
}

.td {
    /*border-right: 1px solid #da731b;*/
    font-size: 10pt;
    color: #9E520A;
}

.mxtd {
    border-right: 1px solid #da731b;
    /*font-size: 10pt;*/
    padding: 2px;
}

.boyder_t3 {
    width: 100%;
    border-bottom: 1px solid #da731b;
}
.boyder_tzidingui{
    border-bottom: none;
}
.boyder_tzidingui1{
    border-top: none;
}
.footer_t1 {
    margin-left: 2px;

    height: 65px;
}

.titletd {
    font-size: 10pt;
    color: #9E520A;
    text-align: left;
}

.notd {
    font-size: 10pt;

    color: black;
}

.notdjym {
    font-size: 10pt;
    color: black;
    width: 200px;
    padding: 5px 5px;
}

.title {
    font-size: 18pt;

    color: #9E520A;

}


</style>
</head>
<body>
<%
    List<Jyspmx> list = (List<Jyspmx>)session.getAttribute("cffplList");
 	 		if(null==list){list = new ArrayList();}
    Jyls jyls = (Jyls)session.getAttribute("jyls");
    if(null==jyls){jyls = new Jyls();}
    List zwlist = (List)session.getAttribute("zwlist");
    if(null==zwlist){zwlist = new ArrayList();}
    String fpzl = "";
     if(jyls.getFpzldm()!=null&&jyls.getFpzldm().equals("01")){
    	fpzl = "增值税专用发票";
    }else if(jyls.getFpzldm()!=null&&jyls.getFpzldm().equals("02")){
    	fpzl = "增值税普通发票";
    }
    else if(jyls.getFpzldm()!=null&&jyls.getFpzldm().equals("12")){
    	fpzl = "增值税电子普通发票";
    } 
%>
	            <div class="tab-page" id="tabPage-dzfp" style="display: block;font-size: 12px;">
	                      <%
            double je = 0.00;
            double se = 0.00;
            double jshj = 0.00;
    %>
             <%-- <h1 id="fpcc_dzfp" style="padding: 5px 0px; text-align: center; color: rgb(87, 75, 157);"><%=fpzl%></h1>--%>
                    <table class="d2" style="width: 800px;table-layout:fixed;">
                        <tr>
                            <td>
                    <div class="header">
                        <table class="header_t1">
                            <tr>
                                <td align="left" valign="bottom" class="titletd" style="width: 210px;">
                                    <img style="width: 80px;height: 80px;margin-left: 42px;"
                                         src="data:image/jpg;base64,${base64Image}"/>
                                </td>
                                <td width="320px" rowspan="2" align="center" valign="top" class="title printed"
                                    style="background: url('${imagePath}/${sfmc}.png'); background-position: center; background-repeat: no-repeat;background-size: 120px 80px;">
                                    <label style="display:inline-block;">
                                        <nobr><%=fpzl%></nobr>
                                        <div style="width:100%;margin-top:20px;border-top:1px solid #da731b;border-bottom:1px solid #da731b;height:3px;"></div>
                                    </label>
                                </td>

                                <td rowspan="2" align="left" style="width: 270px;">
                                    <table width="100%">
                                        <tr>
                                            <td class="titletd printed">发票代码：<span class="filled">${yfpdm}</span>
                                            </td>
                                            <td class="notd"></td>
                                        </tr>
                                        <tr>
                                            <td class="titletd printed">发票号码：<span class="filled">${yfphm}</span></td>
                                            <td class="notd"></td>
                                        </tr>
                                        <tr>
                                            <td class="titletd printed">开票日期：<span class="filled">${kprq}</span></td>
                                            <td class="notd"></td>
                                        </tr>
                                        <tr>
                                            <td class="titletd printed">校 验 码：<span class="filled">${jym}</span></td>
                                            <td class="notd"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td align="left" valign="bottom" class="titletd printed">
                                    机器编号：<span class="filled">${jqbh}</span>
                                </td>
                            </tr>

                        </table>
                    </div>

                    <!--        <table style="width: 850px;margin:0 auto;" border="0" cellspacing="0" cellpadding="0">
                             <tbody><tr height="30">
                               <td class="align_left">发票代码：<span class="content_td_blue" id="fpdm_dzfp"></span></td>
                               <td>&nbsp;</td>
                               <td class="align_left">发票号码：<span class="content_td_blue" id="fphm_dzfp"></span></td>
                               <td>&nbsp;</td>
                               <td class="align_left">开票日期：<span class="content_td_blue" id="kprq_dzfp"></span></td>
                               <td>&nbsp;</td>
                             </tr>
                           </tbody></table> -->
                    <div class="boyder" style="position: relative">
                        <span style="position: absolute;width: 1px;height: 173px;border-left: 1px solid #da731b;top: 106px;left: 231px"></span>
                        <span style="position: absolute;width: 1px;height: 173px;border-left: 1px solid #da731b;top: 106px;left: 332px"></span>
                        <span style="position: absolute;width: 1px;height: 173px;border-left: 1px solid #da731b;top: 106px;left: 377px"></span>
                        <span style="position: absolute;width: 1px;height: 173px;border-left: 1px solid #da731b;top: 106px;left: 457px"></span>
                        <span style="position: absolute;width: 1px;height: 173px;border-left: 1px solid #da731b;top: 106px;left: 532px"></span>
                        <span style="position: absolute;width: 1px;height: 173px;border-left: 1px solid #da731b;top: 106px;left: 652px"></span>
                        <span style="position: absolute;width: 1px;height: 173px;border-left: 1px solid #da731b;top: 106px;left: 692px"></span>
                        <table class="boyder_t1"
                               style="border: solid 1px  #da731b; border-collapse: collapse" cellpadding="0"
                               cellspacing="0">
                            <tr>
                                <td class="boyder_t2">
                                    <table style="width: 100%;height: 100%;table-layout:fixed;" cellspacing="0"
                                           cellpadding="0">
                                        <tr style="height:0;">
                                            <th style="width:25px"></th>
                                            <th></th>
                                            <th style="width:25px"></th>
                                            <th style="width:310px"></th>
                                        </tr>
                                        <tr>
                                            <td class="boyder_td1 printed" align="center">购<br/>买<br/>方</td>
                                            <td class="boyder_td2">
                                                <table cellpadding="2px" style="width: 100%;table-layout:fixed;">
                                                    <tr>
                                                        <td class="titletd printed">名&nbsp;&nbsp;&nbsp;&nbsp;称：<span style="font-size: ${gfmcSize}pt"><![CDATA[${gfmc}]]><%=jyls.getGfmc() %></span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="titletd printed">纳税人识别号：<span><%=jyls.getGfsh() %></span></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="titletd printed">地 址、电 话：<span style="font-size: ${gfdzdhSize}pt"><%=jyls.getGfdz() %><%=jyls.getGfdh() %></span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="titletd printed">
                                                            开户行及账号：<span style="font-size: ${gfyhzhSize}pt"><%=jyls.getGfyh() %><%=jyls.getGfyhzh() %></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td class="boyder_td1 printed" style="text-align: center">密<br/><br/> 码<br/><br/>区
                                            </td>
                                            <td width="200px" align="center"
                                                style="font-family: 'SimSun';font-size: 11pt;padding: 5px 5px;line-height: 150%;vertical-align: top;WORD-WRAP: break-word;word-break: break-all;">
                                                <![CDATA[${fpmw1}]]><br/><![CDATA[${fpmw2}]]><br/><![CDATA[${fpmw3}]]><br/><![CDATA[${fpmw4}]]>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <table class="boyder_t3 boyder_tzidingui" style="width: 100%;height: 100%; table-layout: fixed;"
                                           cellpadding="0" cellspacing="0">
                                        <tr class="boyder_tr1 printed">
                                            <td class="td" width="228x">货物或应税劳务、服务名称</td>
                                            <td width="102px" class="td">规格型号</td>
                                            <td width="45px" class="td">单位</td>
                                            <td width="80px" class="td">数量</td>
                                            <td width="75px" class="td">单价</td>
                                            <td width="120px" class="td">金额</td>
                                            <td width="40px" class="td">税率</td>
                                            <td class="titletd" width="120px">税额</td>
                                        </tr>
                                    </table>
                                        <table class="boyder_t3 boyder_tzidingui table-overAuto" style="width: 100%;height: 100%; table-layout: fixed;position: relative"
                                               cellpadding="0" cellspacing="0">


                                        <%
                                            for(int j=0;j<list.size();j++){
                                                Jyspmx jyspmx = list.get(j);
                                                KpController kp = new KpController();
                                                je=kp.add(je, jyspmx.getSpje());
                                                se=kp.add(se,jyspmx.getSpse());
                                                jshj = kp.add(je,se);
                                        %>

                                        <tr>
                                            <td class="filled" width="228x"><span class="content_td_blue"><%=jyspmx.getSpmc()%></span></td>
                                            <td class="filled" width="102px"><span class="content_td_blue"><%=jyspmx.getSpggxh()==null?"":jyspmx.getSpggxh()%> </span></td>
                                            <td class="filled" width="45px"><span class="content_td_blue"><%=jyspmx.getSpdw()==null?"":jyspmx.getSpdw()%></span></td>
                                            <td class="filled" width="80px"><span class="content_td_blue">  <%=jyspmx.getSps()==null?"":jyspmx.getSps()%></span></td>
                                            <td class="filled" width="75px"><span class="content_td_blue">  <%=jyspmx.getSpdj()==null?"":new DecimalFormat("0.00").format(jyspmx.getSpdj())%></span></td>
                                            <td class="filled" width="120px" style="text-align:right"><span class="content_td_blue">  <%=jyspmx.getSpje()==null?"":new DecimalFormat("0.00").format(jyspmx.getSpje())%></span></td>
                                            <td class="filled" width="40px" style="text-align:right"><span class="content_td_blue"><%=jyspmx.getSpsl()%></span></td>
                                            <td width="120px" class="filled" style="text-align:right"><span class="content_td_blue"><%=new DecimalFormat("0.00").format(jyspmx.getSpse())%></span></td>
                                        </tr>
                                        <%
                                            }
                                        %>

                                        </table>
                                    <table class="boyder_t3 " style="width: 100%;height: 100%; table-layout: fixed;"
                                           cellpadding="0" cellspacing="0">
                            <tr class="boyder_tr1">
                                <td class="td printed" width="228x">合&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;计
                                </td>
                                <td class="td" width="102px"></td>
                                <td class="td" width="45px"></td>
                                <td class="td" width="80px"></td>
                                <td class="td" width="75px"></td>
                                <td class="td" width="120px" style="text-align:right">
                                    <span class="filled">
                                        ￥<%=new DecimalFormat("#.00").format(je)%>
                                    </span>
                                </td>
                                <td class="td" width="40px"></td>
                                <td style="text-align:right"  width="120px">
                                    <span class="filled">
                                    ￥<%=new DecimalFormat("#.00").format(se)%>
                                        </span>
                                </td>
                            </tr>
                        </table>
                        </td>
                        </tr>

                        <tr height="">
                            <td>
                                <table class="boyder_t3" cellpadding="2px" cellspacing="0">
                                    <tr>
                                        <td width="205px" align="center" class="td printed">价税合计（大写）</td>
                                        <td align="left" width="245px" valign="center" class="filled">
                                            <%=zwlist.size()==0?"":zwlist.get(0)%>
                                        </td>
                                        <td align="center" width="350px"><span style="color: #9E520A;" class="printed">（小写）</span><span class="filled">￥<%=new DecimalFormat("#.00").format(jshj)%></span></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <table style="width: 100%;height: 100%" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td class="boyder_td1 printed" style="text-align: center">销<br/>售<br/>方</td>
                                        <td class="boyder_td2" style="width:430px">
                                            <table cellpadding="2px" style="width: 100%;table-layout:fixed;">
                                                <tr>
                                                    <td class="titletd printed">名&nbsp;&nbsp;&nbsp;&nbsp;称：<span style="font-size: ${xfmcSize}pt"><%=jyls.getXfmc() %></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="titletd printed">纳税人识别号：<span><%=jyls.getXfsh() %></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="titletd printed">地 址、电 话：<span style="font-size: ${xfdzdhSize}pt"><%=jyls.getXfdz() %><%=jyls.getXfdh() %></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="titletd printed">
                                                        开户行及账号：<span style="font-size: ${xfyhzhSize}pt"><%=jyls.getXfyh() %><%=jyls.getXfyhzh() %></span>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td class="boyder_td1 printed" style="text-align: center">备<br/><br/><br/>注</td>
                                        <td valign="top" style="width:320px" class="filled"><%=jyls.getBz() %></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        </table>
                    </div>

                    <div>
                        <table class="footer_t1" style="width: 100%;height: 100%">
                            <tr>
                                <td class="titletd printed" style="width: 30%;">收款人：<span>${skr}</span>

                                </td>
                                <td class="titletd printed" style="width: 22%;">复核：<span>${fhr}</span>

                                </td>
                                <td class="titletd printed" style="width: 23%;">开票人：<span>${kpr}</span>

                                </td>
                                <td class="titletd printed" style="width: 25%;">
                                    销货方：（章）
                                    <img style="position: absolute;bottom: 0px;left: 622px;width: 158px;height:auto;" src="${imagePath}/${xfsh}.png">

                                    </img>
                                </td>
                            </tr>
                        </table>
                    </div>

                            </td>
                        </tr>
                    </table>
                </div>
</body>
</html>