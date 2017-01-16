<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>泰易电子发票云服务平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="/i/favicon.png">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/amazeui.min.css"/>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/web.css"/>
    <style type="text/css">
    </style>
</head>
<body>

<!--[if lte IE 8]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-fixed-top">
    <div class="am-container">
        <div class="am-g am-padding-top am-padding-bottom">
            <div class="am-u-sm-4  am-padding-0">
                <img src="<%=request.getContextPath()%>/img/logo.png" alt="TaxEasy"/>
            </div>
            <div class="am-u-sm-8 menu  am-padding-0">
                <div class="am-btn-group am-btn-group-justify">
                    <a class="am-btn am-btn-default" href="<c:url value="/login/login"/>" role="button">首页</a>/
                    <a class="am-btn am-btn-default" href="<%=request.getContextPath()%>/web_products.jsp"
                       role="button">产品</a>/
                    <a class="am-btn am-btn-default" href="<%=request.getContextPath()%>/web_services.jsp"
                       role="">服务</a>/
                    <a class="am-btn am-btn-default  am-text-primary" href="#" role="button">发票查验</a>
                </div>
            </div>
        </div>
    </div>
</header>

<div class="get validate">
    <div class="am-container  ">
        <div class="am-g">
            <div class="am-u-sm-12  description am-padding">
                <div class="dl  ">
                    <form class="am-form am-form-horizontal">
                        <div class="am-g ">
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <label for="jshj" class="am-u-sm-5 am-form-label">订单号: </label>
                                    <div class="am-u-sm-7">
                                        <input type="text" id="ddh" name="ddh" placeholder="请输入订单号">
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <label for="jshj" class="am-u-sm-5 am-form-label">订单金额: </label>
                                    <div class="am-u-sm-7">
                                        <input type="text" id="jshj" name="jshj" class="" placeholder="请输入订单金额">
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <label for="fpdm" class="am-u-sm-5 am-form-label">发票代码: </label>
                                    <div class="am-u-sm-7">
                                        <input type="text" id="fpdm" name="fpdm" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <label for="fphm" class="am-u-sm-5 am-form-label">发票号码: </label>
                                    <div class="am-u-sm-7">
                                        <input type="text" id="fphm" name="fphm" class="" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <label for="kprq" class="am-u-sm-5 am-form-label">开票日期: </label>
                                    <div class="am-u-sm-7">
                                        <input type="text" id="kprq" name="kprq" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group" title="">
                                    <label for="jym" class="am-u-sm-5 am-form-label">校验码(后6位): </label>
                                    <div class="am-u-sm-7">
                                        <input type="text" id="jym" name="jym" class="" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12 tj ">
                                <div class="am-u-sm-4">
                                    &nbsp;
                                </div>
                                <div class="am-u-sm-4">
                                    <button type="button" id="btnQuery" class="am-btn am-btn-primary  am-btn-block">查询
                                    </button>
                                </div>
                                <div class="am-u-sm-4">
                                    <div class="am-form-group  ">
                                        <a id="btnZwcx" class="am-u-sm-12 am-form-label  am-text-center"
                                           href="javascript:void(0)">真伪查询</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
            </div>

        </div>
    </div>
</div>

<%--<form id="fpcxForm" action="http://dzfp.tax.sh.gov.cn/invoice/invoicequery.do?method=doFpcy" method="post"--%>
<%--target="_blank">--%>
<%--<input type="hidden" name="fp_dm">--%>
<%--<input type="hidden" name="fp_hm">--%>
<%--<input type="hidden" name="search_nowtime">--%>
<%--<input type="hidden" name="kphjje">--%>
<%--<input type="hidden" name="yzm">--%>
<%--<input type="hidden" name="yzmText">--%>
<%--</form>--%>

<footer class="footer">
    <div class="am-container">
        <div class="am-g">
        
        <div style="width:60%;float: right;">
        	<div style="width:100%;height:50px;line-height: 60px;text-align: left;">技术咨询热线: 021-5571833</div>
        	<div style="width:100%;height:50px;line-height: 60px;text-align: left;">公司网址：<a href="http://www.datarj.com/index.php">http://www.datarj.com/index.php</a></div>
        	<div style="width:100%;height:50px;line-height: 60px;text-align: left;">© Copyright 2011-2015 上海容津信息技术有限公司 沪ICP备15020560号</div>
        </div>
        <div style="width:20%;float: right;">
        	<img src="<%=request.getContextPath()%>/img/fw.jpg" alt="" style="width:120px;height:120px;"/>
        	<p>服务号</p>
        	
        </div>
        <div style="width:20%;float: right;">
        	<img src="<%=request.getContextPath()%>/img/dy.jpg" alt="" style="width:120px;height:120px;"/>
        	<p>订阅号</p>
        </div>
            <!-- <div class="am-u-sm-3">
                <h1><span class="am-icon-phone-square"> </span></h1>
            </div>
            <div class="am-u-sm-3">
                <h1><span class="am-icon-weibo"></span> &nbsp;<span class="am-icon-weixin"></span></h1>
            </div>
            <div class="am-u-sm-3  am-text-sm  am-text-left">
                <p>Email: service@datarj.com</p>
                <p>Web: www.datarj.com</p>
            </div>
            <div class="am-u-sm-3  am-text-sm  am-text-left">
                <p>技术咨询热线: </p>
                <p>电话: 021-5571833</p>
            </div> -->
        </div>
    </div>
    <!-- <p class="am-text-sm">© Copyright 2011-2015 上海容津信息技术有限公司 沪ICP备15020560号</p> -->
</footer>


<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="assets/js/amazeui.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#btnQuery").click(function () {
            var ddh = $("#ddh").val();
            var jshj = $("#jshj").val();
            if (!ddh) {
                alert("请输入订单号");
                return;
            }
            if (!jshj) {
                alert("请输入订单金额");
                return;
            }
            var url = "<%=request.getContextPath()%>/dzfpcx/fpcx";
            $.post(url, {ddh: ddh, jshj: jshj}, function (res) {
                var success = res["success"];
                if (success) {
                    var data = res["data"];
                    var fpdm = data["fpdm"];
                    var fphm = data["fphm"];
                    var kprq = data["kprq"];
                    var jym = data["jym"]
                    $("#fpdm").val(fpdm);
                    $("#fphm").val(fphm);
                    $("#kprq").val(kprq.substring(0, 4) + kprq.substring(5, 7) + kprq.substring(8, 10));
                    $("#jym").val(jym);
//                    $("input[name=fp_dm]").val(fpdm);
//                    $("input[name=fp_hm]").val(fphm);
//                    var kprq = data["kprq"].substring(0, 10);
//                    $("input[name=search_nowtime]").val(kprq);
//                    $("input[name=kphjje]").val(data["jshj"]);
                    var pdfurl = data["pdfurl"];
                    window.open(pdfurl);
                } else {
                    alert(res["message"]);
                }
            }, "json")
        });
        //真伪查询
        $("#btnZwcx").click(function () {
//            var fpdm = $("input[name=fp_dm]").val();
//            if (!fpdm) {
//                alert("请先进行发票查询");
//                return;
//            }
//            $("#fpcxForm").submit();
            var url = "https://inv-veri.chinatax.gov.cn/#";
            window.open(url);
        });
    });
</script>
</body>
</html>