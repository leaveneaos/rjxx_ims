<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>客户端下载</title>
    <meta name="description" content="客户端下载">
    <meta name="keywords" content="客户端下载">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath()%>/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/admin.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/amazeui.tree.min.css">
    <link rel="stylesheet" href="assets/css/amazeui.css" />
    <link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
    <link rel="stylesheet" href="assets/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
</head>
<body>
<header class="am-topbar admin-header">
    <div class="am-topbar-brand">
        <strong>泰易（TaxEasy）电子发票</strong>
        <small>v1.0</small>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
            data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <!--<li><a href="javascript:;"><span class="am-icon-envelope-o"></span> 收件箱 <span-->
                    <!--class="am-badge am-badge-warning">5</span></a></li>-->
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    <span class="am-icon-users"></span> 管理员 <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
                    <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
                    <!--<li><a href="#"><span class="am-icon-power-off"></span> </a></li>-->
                </ul>
            </li>
            <li><a href="<c:url value="/login/logout"/>"><span class="am-icon-power-off"></span>退出</a></li>
            <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span
                    class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
        </ul>
    </div>
</header>
	<div class="am-g doc-am-g" >
		 <div class="am-u-sm-8 am-u-md-8 am-u-lg-8" style="margin-top:50px; margin-left: 16.7%;">
		 <div style="border: 1px solid gray;min-height: 500px;">
		 	<div  style="background-color: gray; height: 50px;"></div>
		 	
		 	<div class="am-u-sm-2 am-u-md-2 am-u-lg-2" style="background-color:#00C957; height: 50px;color: white;text-align: center;line-height: 50px;">
				客户端下载
		 	</div>
				
		 	<div class="am-u-sm-10 am-u-md-10 am-u-lg-10" style="height: 50px;text-align: left;line-height:50px;border-bottom:1px solid gray;">
		 		客户端下载
		 	</div>
		 	<div class="am-u-sm-2 am-u-md-2 am-u-lg-2" style="border-right:1px solid gray;height: 398px;">
		 	</div>
		 	
		 	<div class="am-u-sm-10 am-u-md-10 am-u-lg-10 .am-u-end">
		 		<ul style="font-size: 13px;color: gray;list-style-type: none;">
		 			<li>开票通客户端是联通云平台数据。直连税控设备的专用工具软件，必须在开票机下载安装使用，具有以下功能</li>
		 			<li>1、可支持金税盘、税控盘等税控设备；</li>
		 			<li>2、与平台开票数据双向交互，开票结果回写平台；</li>
		 			<li>3、可完成开票、打印、作废、红冲等操作，无需人工值守；</li>
		 		</ul>
		 		<div class="am-u-sm-5 am-u-md-5 am-u-lg-5 am-u-sm-centered" style="margin-top: 100px;">
		 			<a href="http://invoice.datarj.com/update/kpt2/kpt2.0.0.0.zip"><button id="btnSaveUserInfo" style="width: 100%;" type="button" class="am-btn am-btn-success">下载客户端</button></a>
		 		</div>
		 	</div>
		 	
		 </div>
		 </div>
	</div>
</body>
</html>