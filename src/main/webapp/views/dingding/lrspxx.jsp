<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入商品信息</title>  
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
			<div class="mui-content-padded">
			<h5 class="mui-content-padded">*商品名称</h5>
			<select class="mui-btn mui-btn-block">
				<option value="item-1">商品1</option>
				<option value="item-2">商品2</option>
				<option value="item-3">商品3</option>
				<option value="item-4">商品4</option>
				<option value="item-5">商品5</option>
			</select>
		</div>
	    <div class="mui-content-padded" style="margin: 5px;">    
	      <form class="mui-input-group">
			    <div class="mui-input-row">
			        <label>规格型号</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>单位</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>数量</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>单价</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>金额(不含税)</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>金额(含税)</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>税率</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>税额</label>
			        <input type="text" class="mui-input-clear" placeholder="">
			    </div>
			</form>
	    </div> 
    </div>

    
    <nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item" href="#tabbar-with-chat">
				<span class="mui-tab-label">价税合计：45.00</span>
			</a>
			<a class="mui-tab-item" href="#tabbar-with-map">
				<span class="mui-tab-label">继续添加</span>
			</a>
			<a class="mui-tab-item" href="#tabbar-with-map" onclick="lrkpd2();">
				<span class="mui-tab-label">完成（2）</span>
			</a>
		</nav>
  </body>  
  <script>
      function lrkpd2(){
     	 window.location.href="dinglrkpd2";
      }
  </script>
</html>  