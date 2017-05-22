<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>首页</title>
    <script src="js/mui.min.js"></script>
    <link href="css/mui.min.css" rel="stylesheet"/>
    <link href="css/index.css" rel="stylesheet"/>
    <script src="js/jquery.1.7.2.min.js"></script>
</head>
<body>
     <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>
	 <div class="mui-content">
			 <ul class="mui-table-view mui-grid-view">
			 <li class="mui-table-view-cell mui-media mui-col-xs-12">
				 <a href="#">

					 <img src="img/64.png">
					 <div class="mui-media-body">泰易开票通云服务平台</div>
				 </a>
			 </li>
			 </ul>
			 <div class="jieshao">开票通2.0，发票管理专家，从此开票再无忧</div>
			 <hr />
			 <br /><br /><br />

			 <div class="mui-button-row">
				 <button id='alertBtn' type="button" class="mui-btn mui-btn-primary" >进入系统</button>
			 </div>
			 <div class="t1">或复制以下网址到浏览器登录</div>
			 <div class="t2">http://test.datarj.com/ims/login/login</div>
			 <div class="t2"><a href="">联系我们</a></div>
			 <div class="t2"><a href="">021-5571833</a></div>

	 </div>

</body>
<script>
	$(function(){
	    $("#alertBtn").click(function(){
             window.location.href= 'http://test.datarj.com/ims/login/login';
		});
	});
</script>
</html>