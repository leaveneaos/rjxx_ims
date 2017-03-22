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
</head>
<body>
<%
    List<Jyspmx> list = (List<Jyspmx>)session.getAttribute("cffplList");
	if(null==list){list = new ArrayList();}
    Jyls jyls = (Jyls)session.getAttribute("jyls");
    if(null==jyls){jyls = new Jyls();}
    List zwlist = (List)session.getAttribute("zwlist");
    if(null==zwlist){zwlist = new ArrayList();}
%>

<div id="tt" style="width:900px;min-height:200px; margin:0 auto;background-color: white;">
          <%
            double je = 0.00;
            double se = 0.00;
            double jshj = 0.00;
    %>
    <div title="发票预览" style="padding:20px;">
        <table class="d2" style="width: 100%;">
            <tr>
                <td>
                    <div class="boyder">
                        <table class="boyder_t1"
                               style="border: solid 1px  #da731b; border-collapse: collapse;border: 1px #9E520A solid;margin-left: 2px;margin-top: 2px;width: 100%;" cellpadding="0"
                               cellspacing="0">
                            <tr>
                                <td class="boyder_t2" style="height: 105px;border-bottom: 1px solid #9E520A;border-collapse: collapse;">
                                    <table style="width: 100%;height: 100%;table-layout:fixed;" cellspacing="0" cellpadding="0">
                                        <tr style="height:0;">
                                            <th style="width:25px"></th>
                                            <th></th>
                                            <th style="width:25px"></th>
                                            <th style="width:300px"></th>
                                        </tr>
                                        <tr>
                                            <td class="boyder_td1" align="center" style="width: 25px;height: 105px;border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">购<br/>买<br/>方<br/></td>
                                            <td class="boyder_td2" style=" width: 50%;height: 105px;border-right: 1px solid #da731b;">
                                                <table cellpadding="2px" style="width: 100%;table-layout:fixed;">
                                                    <tr>
                                                        <td class="titletd" style="text-align:left; font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">名&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;称：<span style="color: black;"><%=jyls==null?"":jyls.getGfmc()%></span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="titletd" style="text-align:left; font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">纳税人识别号：<span style="color: black;"><%=jyls==null?"":(jyls.getGfsh()==null?"":jyls.getGfsh())%></span></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="titletd" style="text-align:left; font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">地&#160;&#160;址、电&#160;&#160;话：<span style="color: black;"><%=jyls==null?"":(jyls.getGfdz()==null?"":jyls.getGfdz())%>&#160;&#160;&#160;&#160;<%=jyls==null?"":(jyls.getGfdh()==null?"":jyls.getGfdh())%></span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="titletd" style="text-align:left; font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">开户行及账号：<span style="color: black;"><%=jyls==null?"":(jyls.getGfyh()==null?"":jyls.getGfyh())%>&#160;&#160;&#160;&#160;<%=jyls==null?"":(jyls.getGfyhzh()==null?"":jyls.getGfyhzh())%></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td class="boyder_td1" style="width: 25px;height: 105px;border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;text-align: center">密<br/><br/> 码<br/><br/>区
                                            </td>
                                            <td width="200px" align="center"
                                                style="font-family: Courier New; font-size: 12pt;padding: 5px 5px;vertical-align: top;WORD-WRAP: break-word">

                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <table class="boyder_t3" style="width: 100%;height: 100%; table-layout: fixed;border-bottom: 1px solid #da731b;"
                                           cellpadding="0" cellspacing="0">
                                        <tr class="boyder_tr1" style="text-align: center;vertical-align: top;border-top: 0px solid #da731b;">
                                            <td class="td" width="200x" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">货物或应税劳务、服务名称</td>
                                            <td width="100px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">规格型号</td>
                                            <td width="70px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">单位</td>
                                            <td width="100px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">数量</td>
                                            <td width="110px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">单价</td>
                                            <td width="130px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">金额</td>
                                            <td width="30px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">税率</td>
                                            <td width="130px" style="font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">税额</td>
                                        </tr>

                                        <%
                                            for(int j=0;j<list.size();j++){
                                            	Jyspmx jyspmx = list.get(j);
                                                	KpController kp = new KpController();
                                                   je=kp.add(je, jyspmx.getSpje());
                                                   se=kp.add(se,jyspmx.getSpse());
                                                   jshj = kp.add(je,se);
                                        %>
                                        <tr>
                                            <td class="mxtd" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;"><%=jyspmx.getSpmc()%></td>
                                            <td class="mxtd" width="64px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;"><%=jyspmx.getSpggxh()==null?"":jyspmx.getSpggxh()%></td>
                                            <td class="mxtd" width="50px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;"><%=jyspmx.getSpdw()==null?"":jyspmx.getSpdw()%></td>
                                            <td class="mxtd" width="30px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">
                                                <%=jyspmx.getSps()==null?"":jyspmx.getSps()%>
                                            </td>
                                            <td class="mxtd" width="110px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">
                                                <%=jyspmx.getSpdj()==null?"":new DecimalFormat("0.00").format(jyspmx.getSpdj())%>
                                            </td>
                                            <td class="mxtd" width="110px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">
                                                <%=jyspmx.getSpje()==null?"":new DecimalFormat("0.00").format(jyspmx.getSpje())%>
                                            </td>
                                            <td class="mxtd" width="30px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;"><%=jyspmx.getSpsl()%>
                                            </td>
                                            <td width="150px" style="text-align:right;font-size: 9pt;"><%=new DecimalFormat("0.00").format(jyspmx.getSpse())%></td>
                                        </tr>

                                        <%
                                            }
                                        %>

                                        <tr class="boyder_tr1" style="text-align: center;vertical-align: top;border-top: 0px solid #da731b; margin-bottom: 20px">
                                            <td  style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/>合&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;计</td>
                                            <td  style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
                                            <td  style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
                                            <td  style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
                                            <td  style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
                                            <td  style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;text-align:right"><br/><br/><br/><span style="color: black;">￥<%=new DecimalFormat("#.00").format(je)%></span>
                                            </td>
                                            <td style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
                                            <td style="text-align:right;font-size: 9pt;"><br/><br/><br/>￥<%=new DecimalFormat("#.00").format(se)%></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr height="30px">
                                <td>
                                    <table class="boyder_t3" cellpadding="2px" cellspacing="0" style="width: 100%;border-bottom: 1px solid #da731b;">
                                        <tr>
                                            <td width="336.5px" align="center" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">价税合计(大写)</td>
                                            <td align="left" width="260px" valign="center">
                                                <%=zwlist.size()==0?"":zwlist.get(0)%>
                                            </td>
                                            <td align="center"><span style="color: #9E520A;">(小写)</span>￥<%=new DecimalFormat("#.00").format(jshj)%></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                </td></tr></table>
    </div>
</div>
</body>
</html>

