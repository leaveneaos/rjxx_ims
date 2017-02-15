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
<link rel="stylesheet" href="css/main.css" />
</head>
<body>
	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<%@ include file="../../pages/menus.jsp"%>
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
					    <div class="am-u-sm-3">							
							<div class="am-u-sm-12">
								<select id="s_xfid" name="xfid">
								<c:forEach items="${xfs}" var="item">
									<option value="${item.id}">${item.xfmc}</option>
								</c:forEach>
								</select>
							</div>
						</div>
						<div class="am-u-sm-3">							
							<div class="am-u-sm-12">
								<select id="s_fpzl" name="fpzl">
									<option value="">---请选择发票类型---</option>
									<c:forEach items="${fpzlList}" var="item">
									<option value="${item.fpzldm}">${item.fpzlmc}</option>
								</c:forEach>
								</select>
							</div>
						</div>
						<div class="am-u-sm-3">
							<div class="am-u-sm-12">
							    <input type="text" id="s_xzrq" name="s_xzrq" placeholder="日历组件"
									data-am-datepicker="{format: 'yyyy-mm', viewMode: 'years', minViewMode: 'months'}" 
									readonly />	
							</div>						
						</div>
						<div class="am-u-sm-3">
						    <div class="am-u-sm-12">
							   <button type="button" class="am-btn am-btn-primary" id="jsSearch">查询</button>
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
							<td><input type="text" id="zspfs" placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>负数票份数</td>
							<td><input type="text" id="fspfs"  placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>合计</td>
							<td><input type="text" id="hjpfs"  placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
						</tr>
						<tr>
							<td>正常发票份数</td>
							<td><input type="text" id="zcpfs"  placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>红冲发票份数</td>
							<td><input type="text" id="hcpfs"  placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
							<td>换开发票份数</td>
							<td><input type="text" id="hkpfs"  placeholder="张"
								readonly style="border: none; background: transparent;" /></td>
						</tr>
						<tr>
							<td>作废发票份数</td>
							<td><input type="text" id="zfpfs" 
								placeholder="张" readonly
								style="border: none; background: transparent;" /></td>
							<td>重开发票份数</td>
							<td><input type="text" id="ckpfs" 
								placeholder="张" readonly
								style="border: none; background: transparent;" /></td>
							<td>重打发票份数</td>
							<td><input type="text" id="cdpfs" 
								placeholder="张" readonly
								style="border: none; background: transparent;" /></td>
						</tr>
					</tbody>
				</table>
				<br>
				<div class="am-u-sm-12" style="text-align:center">
				     <span ><strong>税率统计</strong></span>
				</div>
				<br>
				<br>
				<table
					class="js-sltable am-table am-table-bordered am-table-striped am-text-nowrap">
					<thead>
						<tr>
					        <th>税率(%)</th>
					        <th>销方名称</th>
					        <th>发票种类</th>
					        <th>税率类型</th>
					        <th>销项正数金额</th>
					        <th>销项负数金额</th>
					        <th>合计金额</th>
					        <th>正数税额</th>
					        <th>负数税额</th>
					        <th>合计税额</th>
					  </tr>
					</thead>
					<tbody>
					 
					</tbody>
				</table>
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
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/fytjbb.js"></script>

</body>
</html>
