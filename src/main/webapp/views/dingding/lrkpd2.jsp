<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入开票单2</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">    
  
    <link rel="stylesheet" href="css/mui.min.css">
    <link rel="stylesheet" href="css/index.css">
    <script src="js/mui.min.js"></script>
   
  </head>  
  <body>  
  	<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"><span style="font-size: 15px;">开票通</span></a>
		</header>
		<div class="mui-content">
			<div class="mui-card">
				<div class="mui-card-header">
					<div class="zuo">
						<div class="z1">上海容津信息技术有限公司（普票）</div>
						<div class="z2">上海市漕宝路光大会展中心E座2802室</div>
					</div>
					<a href="" class="you">修改</a>
				</div>
				<div class="mui-card-header">
					<div class="zuo">
						<div class="z1">商品名称：xxxxx</div>
						<div class="z2">
							<div class="z2z">数量</div>
							<div class="z2z">税额</div>
							<div class="z2z">金额</div>
							<div class="z2z">税率</div>
						</div>
					</div>
					<a href="" class="you">修改</a>
				</div>
				<div class="mui-card-header">
					<div class="zuo">
						<div class="z1">商品名称：xxxxx</div>
						<div class="z2">
							<div class="z2z">数量</div>
							<div class="z2z">税额</div>
							<div class="z2z">金额</div>
							<div class="z2z">税率</div>
						</div>
					</div>
					<a href="" class="you">修改</a>
				</div>
				<div class="mui-card-header">
					<div class="zuo">
						<div class="z1">商品名称：xxxxx</div>
						<div class="z2">
							<div class="z2z">数量</div>
							<div class="z2z">税额</div>
							<div class="z2z">金额</div>
							<div class="z2z">税率</div>
						</div>
					</div>
					<a href="" class="you">修改</a>
				</div>
			</div>
    </div>

    
    <nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item" href="#tabbar-with-chat">
				<span class="mui-tab-label">价税合计：45.00</span>
			</a>
			<a class="mui-tab-item" href="#tabbar-with-map">
				<span class="mui-tab-label">返回</span>
			</a>
			<a class="mui-tab-item" href="#tabbar-with-map" onclick="qkp();">
				<span class="mui-tab-label">去开票</span>
			</a>
		</nav>
  </body>  
  <script>
     function qkp(){
    	 window.location.href="dingqkp";
     }
  </script>
</html>  