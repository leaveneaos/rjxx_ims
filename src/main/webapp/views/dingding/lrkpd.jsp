<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入开票单</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">    
  
    <link rel="stylesheet" href="css/mui.min.css">
    <script src="js/mui.min.js"></script>
    
  </head>  
  <body>  
  	<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"><span style="font-size: 15px;">开票通</span></a>
		</header>
		<div class="mui-content">
	    <div class="mui-content-padded" style="margin: 5px;">    
	      <form class="mui-input-group">
			    <div class="mui-input-row">
			        <label>*销方名称</label>
			        <input type="text" class="mui-input-clear" placeholder="上海容津信息技术有限公司">
			    </div>
			    <div class="mui-input-row">
			        <label>*合同/订单号</label>
			        <input type="text" class="mui-input-clear" placeholder="请输入合同或订单号">
			    </div>
			    <div class="mui-input-row">
			        <label>*开票日期</label>
			        <input type="text" class="mui-input-clear" placeholder="2017-4-14">
			    </div>
			</form>
			<h5 class="mui-content-padded">*发票种类</h5>
			<div class="mui-card">
				<form class="mui-input-group">
					<div class="mui-input-row mui-radio">
						<label>电子发票</label>
						<input name="radio1" type="radio" checked>
					</div>
					<div class="mui-input-row mui-radio">
						<label>纸质普通</label>
						<input name="radio1" type="radio">
					</div>
					<div class="mui-input-row mui-radio">
						<label>纸质专用</label>
						<input name="radio1" type="radio">
					</div>
				</form>
			</div>
	    </div> 
	    <h5 class="mui-content-padded">备注：</h5>
	    <div class="mui-input-row" style="margin: 10px 15px;">
				<textarea id="textarea" rows="5" placeholder=""></textarea>
			</div>
    </div>
    
    
    <nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item" href="#tabbar-with-chat">
				<span class="mui-tab-label">返回</span>
			</a>
			<a class="mui-tab-item" href="#tabbar-with-map" onclick="lrgfxx();">
				<span class="mui-tab-label">下一步</span>
			</a>
		</nav>
  </body>
   
  <script>
     function lrgfxx(){
    	 window.location.href="dinglrgfxx";
     }
  </script>
</html>  