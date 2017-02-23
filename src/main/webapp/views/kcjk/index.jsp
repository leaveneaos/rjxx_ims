2<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票库存监控</title>
<meta name="description" content="发票库存监控">
<meta name="keywords" content="发票库存监控">
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
	<div class="am-cf admin-main">
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">发票库存</strong> / <strong>发票库存监控</strong>
				</div>
			</div>
			<hr />
			<div class="am-g  am-padding-top">
				<form action="#" class="js-search-form  am-form am-form-horizontal">
					<div class="am-g">
					<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_xfmc" class="am-u-sm-3 am-form-label">销方名称</label>
								<div class="am-u-sm-9">
									<select id="s_xfid" name="xfid">
										<option value="">请选择发票销方</option>
										<c:forEach items="${xfList}" var="xf">
											<option value="${xf.id}">${xf.xfmc}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_kpdmc" class="am-u-sm-3 am-form-label">开票点名称</label>
								<div class="am-u-sm-9">
									<select id="s_skpid" name="skpid">
									   <option value="">请选择发票开票点</option>
									   <c:forEach items="${skpList}" var="skp">
										  <option value="${skp.id}">${skp.kpdmc}</option>
									   </c:forEach>
									</select>
								</div>
							</div>
						</div>
					    <div class="am-u-sm-6 am-u-end">
							<div class="am-form-group">
								<label for="s_fplx" class="am-u-sm-3 am-form-label">发票类型</label>
								<div class="am-u-sm-9">
									<select id="s_fplx" name="fpzldm">
										<option value="">请选择发票类型</option>
										<c:forEach items="${fplxList}" var="item">
											<option value="${item.fpzldm}">${item.fpzlmc}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>									
					</div>
					<br>
					<div class="am-u-sm-12  am-padding  am-text-center">
				        <button class="am-btn am-btn-primary" id="jsSearch">
							<i class="am-icon-search"></i>&nbsp;查询
					    </button>
	               </div>
				</form>
			</div>
			<br>				
			<div class="am-u-sm-12">
				<div>
					<table id="dyzytable"
						class="am-table am-table-bordered am-table-compact am-text-nowrap">
						<thead>
							<tr>
								<th>序号</th>
								<th>销方名称</th>
								<th>销方税号</th>
								<th>开票点名称</th>
								<th>发票类型</th>
								<th>剩余库存(张)</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
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
	<script src="assets/js/kcjk.js"></script>
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