<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票查询</title>
<meta name="description" content="发票归档查询" />
<meta name="keywords" content="发票归档查询" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="renderer" content="webkit" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="../../assets/i/favicon.png" />
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css" />
<link rel="stylesheet" href="assets/css/amazeui.tree.min.css" />
<link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
</head>
<body>
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">查询统计</strong> / <strong>发票归档</strong>
				</div>
			</div>
			<hr />
			<div class="am-g  am-padding-top">
				<form action="<c:url value='fpcx/exportExcel'/>"
					class="js-search-form  am-form am-form-horizontal" id="ff">
					<div class="am-g">
					    <div class="am-u-sm-6">
					        <div class="am-form-group">
					             <label for="s_xfid" class="am-u-sm-3 am-form-label">销方名称</label>
					             <div class="am-u-sm-9">
					                  <select id="s_xfid" name="xfid">					                       
					                       <c:forEach items="${xfs}" var="xf">
										       <option value="${xf.id}">${xf.xfmc}</option>
										   </c:forEach>
					                  </select>
					             </div>
					        </div>
					    </div>
					 </div>
					 <div class="am-g">				
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_kprqq" class="am-u-sm-3 am-form-label">开票日期</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_kprqq" name="kprqq"
										placeholder="点击选择日期"
										data-am-datepicker="{format: 'yyyy-mm-dd'}" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_kprqz"
									class="am-u-sm-3 am-form-label am-text-center">-</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_kprqz" name="kprqz"
										placeholder="点击选择日期"
										data-am-datepicker="{format: 'yyyy-mm-dd'}" />
								</div>
							</div>
						</div>						
					</div>
					<hr />
					<div class="am-u-sm-12  am-padding  am-text-center">
						<button type="button" class="js-search am-btn am-btn-primary">刷新</button>
						<button type="button" class="js-compass am-btn am-btn-success">归档</button>
					</div>
				</form>
				<div class="am-u-sm-12">
					<div>
						<table
							class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
							<thead>
								<tr>								
									<th>序号</th>
									<th>销方名称</th>
									<th>起始日期</th>
									<th>终止日期</th>
									<th>状态</th>
									<th>文件数量</th>
									<th>操作人</th>
									<th>操作时间</th>
									<th>操作</th>						
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="fpxx">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">
					发票压缩下载 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<hr />
					<form action="#" class="js-form-0  am-form am-form-horizontal">
						<div class="am-g">
							<div class="am-u-sm-12">								
                                 <input type="text" id="path" name="path" readonly/>
							</div>
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<div class="am-u-sm-12  am-text-center">
										<button type="button" class="js-upload am-btn am-btn-danger" disabled style="margin-top:10px">下载</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

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
    <input type="hidden" id="kplshStr">
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/fpgdcx.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
</body>
</html>
