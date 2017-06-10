<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>开票限额</title>
<meta name="description" content="用户管理">
<meta name="keywords" content="用户管理">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="../../assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/amazeui.tree.min.css">
<link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
<link rel="stylesheet" href="css/main.css" />
<script src="assets/js/loading.js"></script>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main" style="margin-top: 0px;">
		<!-- sidebar start -->
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf" style="background: ccccff">
					<strong class="am-text-default am-text-lg">1创建企业名片&nbsp;></strong>&nbsp;&nbsp;<strong
						class="am-text-default am-text-lg">2开票点信息</strong>&nbsp;>&nbsp;&nbsp;<strong
						class="am-text-default am-text-lg">3开票限额</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">4商品信息</strong>>&nbsp;&nbsp;<strong class="am-text-primary am-text-lg">5下载客户端</strong>
				</div>
			</div>
			<hr />
			<div class="am-modal-bd" style="border: none">
				<div class="am-g" style="margin: 0 auto;">
					<form id="frm" class="js-form-0 am-form am-form-horizontal">
						<img src="img/success.png">&nbsp;&nbsp;<span style="font-size: 25px;color: #FF9800;">恭喜您初始化成功了！</span>
						<h4>您可在“基础信息”内修改销售方信息、设备信息和商品信息；</h4>
						<h4>您只需在本平台内维护商品税收服务分类编码信息即可开具发票，无需在开票软件中进行二次维护；</h4>
						<h4>下载本地客户端，开启泰易之旅。</h4>
						<div class="am-form-group">
							<a href="http://invoice.datarj.com/update/kpt2/kpt2.0.0.0.zip" class="js-button  am-btn am-radius am-btn-primary">下载客户端</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a id="finish" class="js-button  am-btn am-radius am-btn-success">进入首页</a>
						</div>

					</form>
				</div>
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


	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd" id="msg"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>

	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd">你确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<%@ include file="../../pages/foot.jsp"%>

	<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
	<script src="assets/js/jquery.form.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>

	<script type="text/javascript">
		$(function(){
			$("#finish").click(function() {
				location.href = 'main';
			});
		});
	</script>
</body>
</html>
