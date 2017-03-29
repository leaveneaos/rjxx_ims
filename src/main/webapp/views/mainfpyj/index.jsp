<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票库存管理</title>
<meta name="description" content="发票库存管理">
<meta name="keywords" content="发票库存管理">
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
	<div class="am-cf admin-main" style="width:98%;margin:0 auto">
			<form action="#" class="js-search-form  am-form">
				<div class="am-u-sm-12">
					<div>
						<table id="dyzytable" 
							class="js-table am-table am-table-bordered am-table-compact table-main am-text-nowrap">
							<thead>
								<tr>
									<th>序号</th>
									<th>销方名称</th>
									<th>销方税号</th>
									<th>税控盘号</th>
									<th>开票点名称</th>
									<th>剩余库存(张)</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</form>
	</div>
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script>
		$(function() {
			var table = $('#dyzytable')
					.DataTable(
							{
								"searching" : false,
								"serverSide" : true,
								"sServerMethod" : "POST",
								"bLengthChange" : false,
								"bSort" : false,								
								ajax : {
									"url" : "fpyjdy/zydy",
									type : 'post',
									data : function(d) {

									}
								},
								"columns" : [
										{
											"orderable" : false,
											"data" : null,
											"defaultContent" : ""
										},
										{
											"data" : "xfmc"
										},
										{
											"data" : "xfsh"
										},
										{
											"data" : "skph"
										},
										{
											"data" : "kpdmc"
										},
										{
											"orderable" : false,
											"data" : null,
											render : function(data, type, full,
													meta) {
												if (Number(data.yjkcl) < Number(data.kyl)
														&& Number(data.yjkcl) * 2 > Number(data.kyl)) {
													return '<span style="color:#FFD700;">'
															+ data.kyl
															+ '</span>';
												} else if (Number(data.kyl < data.yjkcl)) {
													return '<span style="color:red;">'
															+ data.kyl
															+ '</span>';
												} else {
													return '<span>' + data.kyl
															+ '</span>';
												}
											}
										} ]
							});
			table.on('draw.dt', function(e, settings, json) {
				var x = table, page = x.page.info().start; // 设置第几页
				table.column(0).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
			});
		})
	</script>
</body>
</html>