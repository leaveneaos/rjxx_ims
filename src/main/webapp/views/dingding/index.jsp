<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>首页</title>
    <script src="js/mui.min.js"></script>
    <link href="css/mui.min.css" rel="stylesheet"/>
    <link href="css/index.css" rel="stylesheet"/>

</head>
<body>
	<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"><span style="font-size: 15px;">开票通</span></a>
	</header>
	<div class="mui-content">
		<ul class="mui-table-view mui-grid-view"">
		    <li class="mui-table-view-cell mui-media mui-col-xs-12">
		        <a href="#">
		        	<img class="mui-media-object" src="img/aa.png">
		        	<div class="mui-media-body">泰易开票通云服务平台</div>
		        </a>
		    </li>
		</ul>   
		<div class="jieshao">国内领先的 “销项发票管理”SaaS云服务平台；为企业用户提供“纸质发票+电子发票”全票种服务；支持航信、百望金赋两家厂商的税控服务器、税控盘接入方式；企业与税务局之间销项发票数据传输。</div>
    	<div class="t1">企业还未完成初始化信息登记，不能开具发票哦！</div>
    	<div class="t2"><a href="">不会操作？请点这里</a></div>
    	<div class="mui-button-row">
        <button id='alertBtn' type="button" class="mui-btn mui-btn-primary" >进入系统</button>
			<button type="button" class="mui-btn mui-btn-success" ><a href="dinglrkpd?corpid=<c:out value="${corpid}"/>">录入开票单</a></button>
    </div>
    	
    </div>
   
		<script type="text/javascript" charset="utf-8">
			//mui初始化
			mui.init({
				swipeBack: true //启用右滑关闭功能
			});
			document.getElementById("alertBtn").addEventListener('tap', function() {
				mui.alert('您还不是[开票通]系统管理员，不能【进入系统】哦！', function() {
					
				});
			});
			
		</script>
</body>
</html>