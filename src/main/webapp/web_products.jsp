<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                <img src="<%=request.getContextPath()%>/img/logo.png" alt="TaxEasy" />
            </div>
            <div class="am-u-sm-8 menu  am-padding-0">
                <div class="am-btn-group am-btn-group-justify">
                    <a class="am-btn am-btn-default"  href="<c:url value="/login/login"/>" role="button">首页</a>/
                    <a class="am-btn am-btn-default  am-text-primary"  href="#" role="button">产品</a>/
                    <a class="am-btn am-btn-default" href="<%=request.getContextPath()%>/web_services.jsp"  role="button">服务</a>/
                    <a class="am-btn am-btn-default" href="<%=request.getContextPath()%>/web_validate.jsp" role="button">发票查验</a>
                </div>
            </div>
        </div>
    </div>
</header>

<div class="get products">
    <div class="am-container  ">
        <div class="am-g">
            <div class="am-u-sm-12  description am-padding  am-text-center">
                <h1 class="am-text-primary">开启全新发票管理模式</h1>
                <p>对传统的发票管理说再见吧!</p>
        </div>

    </div>
</div>
</div>

<div class="tzgg  products_area_2">
    <div class="am-g  am-container">
        <div class="am-u-sm-4">
            <img src="<%=request.getContextPath()%>/img/products_5.png" width="100%" alt=""/>
        </div>
        <div class="am-u-sm-8  right_area">
            <p>
                本产品为国内领先的 "增值稅电子普通发票" SaaS云服务平台,为中小企业用户提供 "电子发票" 应用的第三方服务,是企业与税务局之前电子
            发票数据传输的桥梁.
            </p>
            <p>
                本产品符合国家税务总局相关技术标准要求,支持航天信息,百望金赋两家厂商的税控服务器、税控盘接入方式.
            </p>
        </div>
    </div>

</div>


<div class="products_3  ">
    <div class="am-g">
        <div class="am-u-sm-2">
            &nbsp;
        </div>
        <div class="am-u-sm-4">
            <h2>
                电子发票管理流程
            </h2>
            <p>
                提供电子发票的全生命周期管理，从发票的开具、发送到红冲、换开提供简单快捷的操作功能，以及多种API接口，支持全后台自动处理。结合推广应用经验，提供多种售后服务工具，提高用户的满意度。
            </p>
        </div>
        <div class="am-u-sm-6">
            <img src="<%=request.getContextPath()%>/img/products_6.png" alt=""/>
        </div>
    </div>
</div>


<div class="products_4  am-text-middle">
    <div class="am-g">
        <div class="am-u-sm-2">
            &nbsp;
        </div>
        <div class="am-u-sm-4">
            <h2>
                数据查询统计
            </h2>
            <p>
                提供丰富的数据统计报表，支持多种图形的展现方式，并提供订阅功能，支持用户的个性化需求定制需求。
            </p>
        </div>
        <div class="am-u-sm-6">
            <img src="<%=request.getContextPath()%>/img/products_7.png" alt=""/>
        </div>
    </div>
</div>

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
</body>
</html>
