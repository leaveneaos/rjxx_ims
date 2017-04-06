<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>账户详情</title>
<meta name="description" content="账户详情">
<meta name="keywords" content="user">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css">
<script src="assets/js/loading.js"></script>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
	<!-- sidebar start -->
	<!-- sidebar end -->
	<!-- content start -->
	<input type="hidden" id="djh" value="0">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="admin-content">
						<div class="am-u-sm-8 am-u-md-8 am-u-lg-8" style="margin-top:50px; margin-left: 16.7%;">
						  <div style="border: 1px solid #D3D3D3;min-height: 700px;">
						     <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="width：659px;height: 50px;text-align: left;line-height:50px;border-bottom:1px solid #D3D3D3;">
						 		账户类型  ：  <c:out value="${zhlxmc}"></c:out>   <div style="float:right"><a href="<%=request.getContextPath()%>/zhxxxq"  target="_blank">详情</a></div>
						 	 </div>
						     <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="width：659px;height: 50px;text-align: left;line-height:50px;border-bottom:1px solid #D3D3D3;">
						 		账户有效期     ：&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${yxqsrq}" type="both"/>--<fmt:formatDate value="${yxjzrq}" type="both"/>
						 		
						 	 </div>
						 	 <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="width：659px;height: 250px;text-align: left;line-height:50px;border-bottom:1px solid #D3D3D3;">
						 		授权信息     <br/>
						 		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;税号：<c:out value="${xfnum}"/>   <br/>
						 		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;税控设备：<c:out value="${kpdnum}"/><br/>
						 		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户数量：<c:out value="${yhnum}"/><br/>
						 		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开票数量：<c:out value="${kpnum}"/>                  
						 		
						 		
						 	 </div>
						 	 <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="width：659px;height: 250px;text-align: left;line-height:50px;border-bottom:1px solid #D3D3D3;">
						 		已使用           <br/>
						 		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已使用税号：<c:out value="${XfCount}"/>   <br/>
						 		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已使用税控设备：<c:out value="${skpcount}"/><br/>
						 		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已使用用户数量：<c:out value="${yhcount}"/><br/>
						 		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已开票数量：<c:out value="${kpcount}"/>
						 		          
						 	 </div>
						 	 <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="width：659px;height: 50px;text-align: left;line-height:50px;border-bottom:1px solid #D3D3D3;">
						 		账户余额:1000元(代金券)
						 	 </div>
						 	 <div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="width：659px;height: 50px;text-align: left;line-height:50px;">
						 		账户积分:1000分
						 	 </div>
						  </div>
						
						
						</div>

						
					</div>
				</div>
				<!-- loading do not delete this -->
									<!-- content end -->
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

<script type="text/javascript">
</script>
<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
  <div class="am-modal-dialog">
    <div id="alertt" class="am-modal-bd">
      Hello world！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
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
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>

</body>
</html>