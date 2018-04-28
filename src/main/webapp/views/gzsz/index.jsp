<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>手工开具</title>
<meta name="description" content="手工开具">
<meta name="keywords" content="手工开具">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />

<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
	.admin-content {
	  min-height: 0px;
	}
	.selected {
		background-color: lightgray;
	}
	.star {
		color: red;
	}
	.data-buy {
		padding-top: 35px;
	}
	.data-cte {
		text-align: center;
		margin-top: 5px;
	}
	.botm {
		text-align: center;
	}
	.botm button {
		margin: 10px 100px;
	}
	tbody td input {
		width: 90px;
	}
	tbody td #spmc {
		width: 200px;
	}
</style>
</head>
<body>
	<div class="row-content am-cf">
	    <div class="row">
	        <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
	            <div class="widget am-cf">
					<!--[if lte IE 9]>
					<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>以获得更好的体验！</p>
					<![endif]-->
					<div class="am-cf admin-main">
						<!-- content start -->
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">基础信息</strong> / <strong>规则设置</strong>
							</div>
						</div>

						<div class="admin-content" style="border: 1px solid #ccc; margin-top: 10px;">
							<c:forEach items="${csbs }" var="csb" varStatus="i">
							<c:choose>
							<c:when test="${csb.id =='35'}">
							 	  <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="text-align: left;">
									<div class="am-u-sm-4 am-u-md-4 am-u-lg-4">
					                    	<span style="vertical-align: middle;">${csb.csmc }</span>
											<input type="text" name="${csb.csm }" value="${csb.mrz}">
											</span>
									</div>
								</div>
							 </c:when>
							 <c:when test="${csb.id == '34'}">
							 	<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="text-align: left;">
									<div class="am-u-sm-4 am-u-md-4 am-u-lg-4">
					                    	<span style="vertical-align: middle;">${csb.csmc }</span>
											<input type="text" name="${csb.csm }" value="${csb.mrz}">
											</span>
									</div>
								</div>
							 
							 </c:when>
							 <c:when test="${csb.id == '56'}">
							 	<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="text-align: left;">
									<div class="am-u-sm-4 am-u-md-4 am-u-lg-4">
					                    	<span style="vertical-align: middle;">${csb.csmc }</span>
											<input type="text" name="${csb.csm }" value="${csb.mrz}">
											</span>
									</div>
								</div>
							 
							 </c:when>
							 
							 
							 
							 
							 
							 
							 
							 
							 
							 <c:otherwise>
	                           <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="text-align: left;">
									<div class="am-u-sm-4 am-u-md-4 am-u-lg-4">
					                    	<span style="vertical-align: middle;">${csb.csmc }</span>
					                    	<span style="margin-left:120px;">
											<input type="radio" name="${csb.csm }" checked="checked" value="1">是
											<input type="radio" name="${csb.csm }" value="0">否
											</span>
									</div>
								</div>
                      		 </c:otherwise>
                      		 </c:choose>
						</c:forEach>
							
							
							
							<div class="am-u-sm-4 botm" >
								<button id="kj" type="button" class="am-btn am-btn-secondary">保存</button>
								<!-- <button id="cz" type="button" class="am-btn am-btn-danger">重 置</button> -->
							</div>
						</div>
						<!-- content end -->









						<!-- loading do not delete this -->
						<div
							class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn"
							tabindex="-1">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">正在载入...</div>
								<div class="am-modal-bd">
									<span class="am-icon-spinner am-icon-spin"></span>
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>


	<!--[if lt IE 9]>
		<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
		<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
	<![endif]-->

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.form.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
    <script src="assets/js/sgkj.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<%--<script src="//code.jquery.com/jquery-1.9.1.js"></script>--%>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
	<script>



	</script>

</body>
</html>
