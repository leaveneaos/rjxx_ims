<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>统计报表</title>
<meta name="description" content="分月统计报表">
<meta name="keywords" content="分月统计报表">
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

<!-- jqplot style -->
<link rel="stylesheet"
	href="/plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.min.css" />

<link rel="stylesheet" href="css/main.css" />
</head>
<body>
	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../../pages/menus.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">查询统计</strong> / <i>统计报表</i>
				</div>
			</div>
			<hr />
			<form action="#" class="js-form am-form am-form-horizontal">
				<div class="am-g">
					<div class="am-u-sm-12">
						<div class="am-u-sm-6">
							<label for="s_fpzl" class="am-u-sm-3 am-form-label">发票种类</label>
							<div class="am-u-sm-9">
								<select id="s_fpzl" name="fpzl">
									<option value="">------请选择------</option>
									<option value="01">增值税专用发票</option>
									<option value="02">增值税普通发票</option>
									<option value="12">电子发票(增普)</option>
								</select>
							</div>
						</div>
						<div class="am-u-sm-6">
							<div class="am-u-sm-9">
								<input type="text" id="s_xzrq" name="s_xzrq" placeholder="日历组件"
									data-am-datepicker="{format: 'yyyy-mm', viewMode: 'years', minViewMode: 'months'}"
									readonly />
							</div>
							<div class="am-u-sm-3">
							   <button type="button" class="am-btn am-btn-primary">查询</button>
						    </div>
						</div>					
					</div>
				</div>
			</form>
			<br>
			<br>
			<div class="am-u-sm-12">
				<table
					class="js-table am-table am-table-bordered am-table-striped">
					<thead>
						<tr>
							<th colspan="6">发票统计</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>正数票份数</td>
							<td><input type="text" id="je1" name="je1" placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>负数票份数</td>
							<td><input type="text" id="je2" name="je2" placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>合计</td>
							<td><input type="text" id="je3" name="je3" placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
						</tr>
						<tr>
							<td>红冲发票票份数</td>
							<td><input type="text" id="se1" name="se1" placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>换开发票份数</td>
							<td><input type="text" id="se2" name="se2" placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>作废发票份数</td>
							<td><input type="text" id="se3" name="se3" placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
						</tr>
						<tr>
							<td>重开发票份数</td>
							<td><input type="text" id="jshj1" name="jshj1"
								placeholder="张" readonly
								style="border: none; background: transparent;" /></td>
							<td>重打发票份数</td>
							<td><input type="text" id="jshj2" name="jshj2"
								placeholder="张" readonly
								style="border: none; background: transparent;" /></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
				<table
					class="js-sltable am-table  am-table-bordered  am-table-striped ">
					<thead>
						<tr>
							<th colspan="6">税率统计</th>
						</tr>
					</thead>
					<tbody>
					    <tr>
							<td>项目名称</td>
							<td>合计</td>
							<td>0%</td>
							<td>5%</td>
							<td>6%</td>
							<td>11%</td>
							<td>17%</td>
						</tr>
						<tr>
							<td>正常开具金额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>正常开具税额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>红冲开具金额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>红冲开具税额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>换开开具金额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>换开开具税额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>发票作废金额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>发票作废税额</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- content end -->

		<!-- loading do not delete -->
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

	<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>

	<!-- jqplot chart -->
	<script src="plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.min.js"></script>
	<script
		src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.barRenderer.min.js"></script>
	<script
		src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.highlighter.min.js"></script>
	<script
		src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.cursor.min.js"></script>
	<script
		src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.pointLabels.min.js"></script>


	<script src="assets/js/fytjbb.js"></script>

</body>
</html>
