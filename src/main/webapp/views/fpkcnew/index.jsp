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
	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<%@ include file="../../pages/menus.jsp"%>
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">发票库存</strong> / <strong>发票库存管理</strong>
				</div>
			</div>
			<hr />
			<div class="am-g  am-padding-top">
				<form action="#" class="js-search-form  am-form am-form-horizontal">
					<div class="am-g">
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<div class="am-u-sm-9 am-input-group">
									<input type="text" class="am-form-field" id="cfpdm" name="fpdm"
										placeholder="发票代码" /> <span class="am-input-group-btn">
										<button class="am-btn am-btn-default" type="button">
											<span class="am-icon-search"></span>
										</button>
									</span>
								</div>
							</div>
						</div>
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-9 am-form-label"></label>
								<div class="am-u-sm-3">
									<button class="am-btn am-btn-primary">
										<i class="am-icon-plus"></i>添加发票库存
									</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				<br>
				<div class="am-u-sm-12">
					<div>
						<table id="search-table"
							class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
							<thead>
								<tr>
									<th>序号</th>
									<th style="display: none">id</th>
									<th>销方名称</th>
									<th>销方税号</th>
									<th style="display: none">skpid</th>
									<th>开票点名称</th>
									<th>发票代码</th>
									<th>起始发票号码</th>
									<th>终止发票号码</th>
									<th>库存(张)</th>
									<th>剩余库存(张)</th>
									<th>录入人</th>
									<th>录入时间</th>
									<th>操作</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<br> <br>
			<div class="am-u-sm-12" style="padding-left: 20px">
				<strong><span>库存监控</span></strong>
			</div>
			<hr />
			<br>
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
			<div class="am-u-sm-12  am-padding  am-text-center">
				<button type="button" class="am-btn am-btn-primary">
					<i class="am-icon-refresh"></i>刷新
				</button>
			</div>
		</div>

		<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
			<div class="am-modal-dialog" style="height: 320px; width: 700px">
				<form class="js-form-kc am-form">
					<div class="am-tabs" data-am-tabs>
						<div class="am-tabs-nav am-nav am-nav-tabs">
							<label>发票库存管理</label>
						</div>
						<div class="am-tabs-bd">
							<div class="am-tab-panel am-in am-active" id="tab1">
								<div class="am-modal-bd">
									<div class="am-g">
										<div class="am-u-sm-12">
											<table
												class="am-table am-table-bordered am-table-striped am-text-nowrap">
												<tr>
													<td>销方名称：</td>
													<td colspan="2"><select id="xfsh" name="xfid"
														class="am-form-field" required onchange="getKpd()">
															<option value="">---请选择---</option>
															<c:forEach items="${xfList}" var="xf">
																<option value="${xf.id}">${xf.xfmc}</option>
															</c:forEach>
													</select></td>
													<td></td>
												</tr>
												<tr>
													<td>开票点名称：</td>
													<td><select id="kpddm" name="skpid"
														class="am-form-field" required>

													</select></td>
													<td>发票代码：</td>
													<td><input type="text" id="fpdm" name="fpdm"
														maxlength="12" pattern="^\d{10,12}$" placeholder="请输入发票代码"
														class="am-form-field" required /></td>
												</tr>
												<tr>
													<td>发票号码：</td>
													<td><input type="text" id="fphms" name="fphms"
														maxlength="10" pattern="^\d{8}$" placeholder="请输入起始号码"
														class="am-form-field" required /></td>
													<td>——</td>
													<td><input type="text" id="fphmz" name="fphmz"
														maxlength="10" pattern="^\d{8}$" placeholder="请输入截止号码"
														class="am-form-field" required /></td>
												</tr>
											</table>
										</div>
										<div class="am-u-sm-12">
											<div class="am-form-group">
												<div class="am-u-sm-12  am-text-center">
													<button type="submit"
														class="js-submit  am-btn am-btn-primary">确定</button>
													<button type="button"
														class="js-close  am-btn am-btn-danger">取消</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
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
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<%@ include file="../../pages/foot.jsp"%>

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/fpkc.js"></script>
	<script>
		function getKpd() {
			var xfid = $('#xfsh option:selected').val();
			var skpid = $("#kpddm");
			$("#kpddm").empty();
			$.ajax({
				url : "fpkc/getKpd",
				data : {
					"xfid" : xfid
				},
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						var option = $("<option>").text(data[i].kpdmc).val(
								data[i].skpid);
						skpid.append(option);
					}
				}

			});
		}
	</script>
</body>
</html>