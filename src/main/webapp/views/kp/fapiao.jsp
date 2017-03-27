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
/* reset--------------------------------------------------------------------*/
body,h1,h2,h3,h4,h5,h6,dl,dt,dd,ul,ol,li,th,td,p,blockquote,pre,form,fieldset,legend,input,button,textarea,hr{ margin:0; padding:0;}fieldset,img{ border:0;}q:before,q:after{ content:'';}abbr[title]{ border-bottom:1px dotted; cursor:help;}address,cite,dfn,em,var{ font-style:normal;}legend{ color:#000;}code,kbd,samp{ font-family:"Courier New",monospace;}hr{ border:none; height:1px;}h1,h2,h3,h4,h5,h6{ font-size:100%;}ol,ul {list-style:none outside none;}li {list-style: none;}
input, select, textarea, button { font-size: 100%; line-height: inherit;}button { cursor: pointer;}table, thead, tbody, tfoot, tr, th, td, caption, col, colgroup { text-align: inherit; line-height: inherit;	font-size: 100%;}table {border-collapse: collapse; border-spacing: 0;}thead {display: table-header-group;}tbody {display: table-row-group;}tfoot {display: table-footer-group;}tr {display: table-row;}th,td {display: table-cell;}caption {display: table-caption;}col {display: table-column;}
colgroup {display: table-column-group;}/* clear--------------------------------------------------------------------*/.clearfix:after,.main:after {content: ".";display: block;height: 0;clear: both;visibility: hidden;}.clearfix {display: inline-block;}/*IE7*/* html .clearfix { zoom: 1; display: inline-block;}/* Hides from IE-mac \*/.clearfix { display:block;}/* End hide from IE-mac */.zoom { zoom: 1; overow: hidden;}.clear { clear:both;}/* layout--------------------------------------------------------------------*/body { background-color:#f7f7f7; font:normal 12px "MicroSoft YaHei","SimHei"; color:#525252; text-align:center;}#header, #nav .nav_main, #banner, #content, .footMain_main, #footer {margin:0 auto;width:1000px;text-align:left;position:relative;}p, b { line-height:20px; clear:both; overflow:hidden;}h1, h2{ font:normal 18px "MicroSoft YaHei","SimHei"; margin-top:5px; clear:both; overflow:hidden;}h2{ font-size:16px; color:#69C; width:746px; margin:20px auto 5px; font-weight:bold;}a {color:#999999; text-decoration:none;}a:hover{color:#3667d3;}a:active { star:expression(this.onFocus=this.blur());}:focus { outline:0;}.title { font:bold 15px/38px "MicroSoft YaHei","SimHei"; height:40px; background-color:#3667d3; width:100%; color:#fff; overflow:hidden; clear:both;}.title span { margin-left:10px;}#content .cms_r { min-height:500px; margin:0 auto;}#content .cms_r .title { background:none; color:#666;}
/* 通用表格样式--------------------------------------------------------------------*/.comm_table{ text-align:center;}.comm_table,.comm_table td,.comm_table th{ border:1px solid #b8b7b7;padding:10px;}.lr_txt{width:100%; background:; border:0px; text-align:center;font-family:"MicroSoft YaHei";}.comm_btn_div{margin:20px auto; text-align:center;}.comm_table2{ margin:0 auto;}.comm_table2 td{padding:10px; text-align:left;}.comm_table2 td input[type=text]{padding:5px; width:230px;}.tip_common{ background:url(images/pencil.png) no-repeat left center; padding-left:30px; line-height:30px;}.tip_error{ background:url(images/cross.png) no-repeat left center;padding-left:30px;line-height:30px;}.tip_right{ background:url(images/ico_right2.jpg) no-repeat left center;padding-left:30px;line-height:30px;}.font_red{ color:#f00;}.chayan_div{background:#fff; padding:30px 0px;box-shadow:0 4px 5px rgba(0,0,0,0.3); border:1px solid #ccc;}.chayan_title{ background:#09F; height:40px; line-height:40px;}.td_left{ text-align:left;}.td_center{ text-align:center; background:#e0f2fe;}/*----resule table----*/.fppy_table,.fppy_table td{ border:1px solid #aaa; border-collapse:collapse; line-height:25px; background:#fafafa; margin:0 auto;}.fppy_table td.borderBottomNo{border:0px;}.fppy_table td.borderRightNo{border:0px;}.fppy_table td.borderNo{border:0px;}table.fppy_table_box{border:0px;border-collapse:collapse;}table.fppy_table_box td{border:0px solid #ccc; border-collapse:collapse;}.fppy_table_box td.borderTop{border-top:1px solid #aaa;}.fppy_table_box td.borderRight{ border-right:1px solid #aaa;}.align_center{ text-align:center;}.align_left{ text-align:left;}.content_td_blue{color:#574B9D;}button{box-shadow:0 1px 2px rgba(0,0,0,0.2); height:30px; line-height:30px; padding:0 20px;-moz-border-radius:2px; -webkit-border-radius:2px; border-radius:2px;font:normal 12px "MicroSoft YaHei","SimSun"; margin:0 5px; z-index:1000; position:relative;}
.gray_button{border:1px solid #c5c5c5; background:#f7f7f7;box-shadow:0 1px 2px rgba(0,0,0,0.2);}
.white_button{border:1px solid #888; background:#fff;box-shadow:0 5px 5px rgba(0,0,0,0.2);}
.blue_button{border:1px solid #005bbb; background:#007bd5; color:#fff;box-shadow:0 5px 5px rgba(0,0,0,0.2);}
.red_button{border:1px solid #b23a30; background:#dc3224;color:#fff;box-shadow:0 5px 5px rgba(0,0,0,0.2);}
.green_button{border:1px solid #427e3d; background:#58a952;color:#fff;box-shadow:0 5px 5px rgba(0,0,0,0.2);}
.black_button{border:1px solid #b8b8b8; background:#e0e0e0; color:#555;box-shadow:0 5px 5px rgba(0,0,0,0.2);}
.tab-page{display:none;}
/*打印样式设置*/
.printdiv
{ 
  width:938px; 
  height:100%;
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
     if(jyls.getFpzldm().equals("01")){
    	fpzl = "增值税专用发票";
    }else if(jyls.getFpzldm().equals("02")){
    	fpzl = "增值税普通发票";
    }
    else if(jyls.getFpzldm().equals("12")){
    	fpzl = "增值税电子普通发票";
    } 
%>
	            <div class="tab-page" id="tabPage-dzfp" style="display: block;">
	                      <%
            double je = 0.00;
            double se = 0.00;
            double jshj = 0.00;
    %>
              <h1 id="fpcc_dzfp" style="padding: 5px 0px; text-align: center; color: rgb(87, 75, 157);"><%=fpzl%></h1>
              <table style="width: 850px;margin:0 auto;" border="0" cellspacing="0" cellpadding="0">
                <tbody><tr height="30">                  
                  <td class="align_left">发票代码：<span class="content_td_blue" id="fpdm_dzfp">************</span></td>
                  <td>&nbsp;</td>
                  <td class="align_left">发票号码：<span class="content_td_blue" id="fphm_dzfp">********</span></td>
                  <td>&nbsp;</td>
                  <td class="align_left">开票日期：<span class="content_td_blue" id="kprq_dzfp">****年**月**日</span></td>
                  <td>&nbsp;</td>
                  <td class="align_left">校验码：<span class="content_td_blue" id="jym_dzfp">**************</span></td>
                  <td>&nbsp;</td>
                  <td class="align_left">机器编号：<span class="content_td_blue" id="sbbh_dzfp">***************</span></td>
                  <td>&nbsp;</td>
                </tr>
              </tbody></table>
              <table class="fppy_table" style="width: 850px;" border="0" cellspacing="0" cellpadding="0">
              <tbody><tr>
                <td width="20" class="align_center" rowspan="4">
                  <p>购</p>
                  <p>买</p>
                  <p>方</p>
                </td>
                <td width="85" class="align_left borderNo">名称：</td>
                <td class="align_left borderNo bgcolorWhite" nowrap=""><span class="content_td_blue" id="gfmc_dzfp"><%=jyls.getGfmc() %></span></td>
                <td width="20" class="align_center" rowspan="4"> 
                  <p>密</p>
                  <p>码</p>
                  <p>区</p>
                </td>
                <td width="350" class="align_left " id="password_dzfp" nowrap="" rowspan="4">&nbsp;</td>
                </tr>
                <tr>
                  <td class="align_left borderNo">纳税人识别号：</td>
                  <td class="align_left borderNo" nowrap=""><span class="content_td_blue" id="gfsbh_dzfp"><%=jyls.getGfsh() %></span></td>
                </tr>
                <tr>
                  <td class="align_left borderNo">地址、电话：</td>
                  <td class="align_left borderNo" nowrap=""><span class="content_td_blue" id="gfdzdh_dzfp"><%=jyls.getGfdz() %><%=jyls.getGfdh() %></span></td>
                </tr>
                <tr>
                  <td class="align_left borderNo">开户行及账号：</td>
                  <td class="align_left borderNo" nowrap=""><span class="content_td_blue" id="gfyhzh_dzfp"><%=jyls.getGfyh() %><%=jyls.getGfyhzh() %></span></td>
                </tr>
                
                <!--表头-->
                <tr>
                  <td colspan="5"><table class="fppy_table_box" style="width: 100%;" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr id="tab_head_dzfp">
                      <td width="30%" class="align_center borderRight">货物或应税劳务、服务名称</td>
                      <td width="10%" class="align_center borderRight">规格型号</td>
                      <td width="5%" class="align_center borderRight">单位</td>
                      <td width="10%" class="align_center borderRight">数量</td>
                      <td width="10%" class="align_center borderRight">单价</td>
                      <td width="15%" class="align_center borderRight">金额</td>
                      <td width="5%" class="align_center borderRight">税率</td>
                      <td width="15%" class="align_center">税额</td>
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
                      <td class="align_center borderRight"><span class="content_td_blue"><%=jyspmx.getSpmc()%></span></td>
                      <td class="align_center borderRight"><span class="content_td_blue"><%=jyspmx.getSpggxh()==null?"":jyspmx.getSpggxh()%> </span></td>
                      <td class="align_center borderRight"><span class="content_td_blue"><%=jyspmx.getSpdw()==null?"":jyspmx.getSpdw()%></span></td>
                      <td class="align_center borderRight"><span class="content_td_blue">  <%=jyspmx.getSps()==null?"":jyspmx.getSps()%></span></td>
                      <td class="align_center borderRight" style="text-align: right;"><span class="content_td_blue">  <%=jyspmx.getSpdj()==null?"":new DecimalFormat("0.00").format(jyspmx.getSpdj())%></span></td>
                      <td class="align_center borderRight" style="text-align: right;"><span class="content_td_blue">  <%=jyspmx.getSpje()==null?"":new DecimalFormat("0.00").format(jyspmx.getSpje())%></span></td>
                      <td class="align_center borderRight" style="text-align: right;"><span class="content_td_blue"><%=jyspmx.getSpsl()%></span></td>
                      <td class="align_center" style="text-align: right;"><span class="content_td_blue"><%=new DecimalFormat("0.00").format(jyspmx.getSpse())%></span></td>
                    </tr>
                      <%
                                            }
                                        %>
                    
                    <tr>
                      <td class="align_center borderRight">合计</td>
                      <td class="align_center borderRight">&nbsp;</td>
                      <td class="align_center borderRight">&nbsp;</td>
                      <td class="align_center borderRight">&nbsp;</td>
                      <td class="align_center borderRight">&nbsp;</td>
                      <td class="align_center borderRight" style="text-align: right;"><span class="content_td_blue" id="je_dzfp">￥<%=new DecimalFormat("#.00").format(je)%></span></td>
                      <td class="align_center borderRight">&nbsp;</td>
                      <td class="align_center" style="text-align: right;"><span class="content_td_blue" id="se_dzfp">￥<%=new DecimalFormat("#.00").format(se)%></span></td>
                    </tr>
                    <tr>
                      <td class="align_center borderRight borderTop">价税合计（大写）</td>
                      <td class="align_center borderTop" colspan="7"><span class="align_left"><span class="content_td_blue" id="jshjdx_dzfp"> <%=zwlist.size()==0?"":zwlist.get(0)%></span><span style="padding: 0px 20px;">（小写）</span><span class="content_td_blue" id="jshjxx_dzfp">￥<%=new DecimalFormat("#.00").format(jshj)%></span></span></td>
                    </tr>
                  </tbody></table>
                  </td>
                </tr>
                <!--表头结束-->
                <tr>
                  <td class="align_center" rowspan="4">
                    <p>销</p>
                    <p>售</p>
                    <p>方</p>
                  </td>
                  <td class="align_left borderNo">名称：</td>
                  <td class="align_left borderNo"><span class="content_td_blue" id="xfmc_dzfp"><%=jyls.getXfmc() %></span></td>
                  <td width="20" class="align_center" rowspan="4">
                    <p>备</p>
                    <p>注</p>
                  </td>
                  <td width="350" class="align_left" id="bz_dzfp" rowspan="4">机器编号:**********<p><%=jyls.getBz() %></p></td>
                </tr>
                <tr>
                  <td class="align_left borderNo">纳税人识别号：</td>
                  <td class="align_left borderNo"><span class="content_td_blue" id="xfsbh_dzfp"><%=jyls.getXfsh() %></span></td>
                </tr>
                <tr>
                  <td class="align_left borderNo">地址、电话：</td>
                  <td class="align_left borderNo"><span class="content_td_blue" id="xfdzdh_dzfp"><%=jyls.getXfdz() %><%=jyls.getXfdh() %></span></td>
                </tr>
                <tr>
                  <td class="align_left borderNo">开户行及账号：</td>
                  <td class="align_left borderNo"><span class="content_td_blue" id="xfyhzh_dzfp"><%=jyls.getXfyh() %><%=jyls.getXfyhzh() %></span></td>
                </tr>
              </tbody></table>
              
            </div>
</body>
</html>