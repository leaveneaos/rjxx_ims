<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>待办事项</title>
<meta name="description" content="待办事项">
<meta name="keywords" content="待办事项">
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
</head>
<body>
	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<%@ include file="../../pages/menus.jsp"%>
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">首页</strong> / <strong>待办事项</strong>
				</div>
			</div>
			<hr />

			<div class="am-g">
				<div class="am-u-sm-12" style="top: 10; left: 0">
					<div class="am-panel am-panel-default">
						<div class="am-panel-hd am-cf" style="height: 45px">
							<span>待办事项汇总</span>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">开票单审核</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">发票开具</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">发票作废</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">发票红冲</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">发票重开</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">发票换开</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">发票重打</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">电子票发送</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
						<div class="am-collapse am-in am-u-sm-3 am-u-end"
							style="height: 140px; overflow: auto;">
							<div style="text-align: center;padding-top:30px">发票邮寄</div>
							<div style="text-align: center"><a href="#">4</a></div>
						</div>
					</div>
				</div>
			</div>
			<div style="padding-left:18px">
			<span><strong>已处理汇总</strong></span>
			</div>
			<br>
			<div class="am-u-sm-12">
				<div class="am-scrollable-horizontal">
					<table class="fpds-table am-table am-table-bordered am-table-striped am-text-nowrap">
						<thead>
							<tr>
								<th>日期</th>
								<th>开票单审核</th>
								<th>发票开具</th>
								<th>发票作废</th>
								<th>发票红冲</th>
								<th>发票重开</th>
								<th>发票换开</th>
								<th>发票重打</th>
								<th>电子票发送</th>
								<th>发票邮寄</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>
		</div>
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

	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<%@ include file="../../pages/foot.jsp"%>
	<div data-am-widget="gotop" class="am-gotop am-gotop-fixed">
		<a href="#top" title="回到顶部"> <i
			class="am-gotop-icon am-icon-hand-o-up"></i>
		</a>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/fpcx_4.js"></script>
</body>
</html>
