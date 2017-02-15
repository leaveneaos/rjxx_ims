<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>角色管理</title>
<meta name="description" content="角色管理">
<meta name="keywords" content="角色管理">
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
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../../pages/menus.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">系统管理</strong> / <strong>角色管理</strong>
				</div>
			</div>
			<hr />
			<div class="am-g  am-padding-top">
				<form action="#" class="js-search-form  am-form am-form-horizontal">
					<div class="am-g">
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-3 am-form-label">角色名称</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_jsmc" name="s_jsmc"
										placeholder="请输入角色名称" />
								</div>
							</div>
						</div>
					</div>
					<hr />
					<div class="am-u-sm-12  am-padding  am-text-right">
						<button id="button1" type="button"
							class="js-search  am-btn am-radius am-btn-success">查询</button>
						<button id="button2" type="button"
							class="js-search  am-btn am-radius am-btn-success">新增</button>
					</div>

					<div class="am-u-sm-12">
						<div class="am-scrollable-horizontal">

							<table id="tbl"
								class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
								<thead>
									<tr>
										<th>序号</th>
										<th>角色名称</th>
										<th style="display: none;">id</th>
										<th>录入人员</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn"  tabindex="-1" id="hongchong" data-am-sticky>
			<div class="am-modal-dialog" style="overflow-y: auto;" data-am-sticky>
				<form id="form1" class="js-form-0 am-form am-form-horizontal">
					<div class="am-tabs" data-am-tabs>
						<ul class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tab1">角色信息</a></li>
						</ul>

						<div class="am-tabs-bd">
							<div class="am-tab-panel am-fade am-in am-active" id="tab1">
								<div class="am-modal-hd" data-am-sticky>
									 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<hr />


									<div class="am-g">
										<div class="am-u-sm-12" style=" margin-bottom: 5px;">
											<div class="am-form-group">
												<label for="hc_yfpdm" class="am-u-sm-4 am-form-label" style=" margin-bottom: 40px;"><font color="red">*</font>角色名称</label>
												<div class="am-u-sm-8" style=" margin-bottom: 30px;">
													<input type="text" id="name" name="name"
														placeholder="请输入名称" class="am-form-field" required
														maxlength="50" />
														<input id = "roleid" name = "id" type="hidden"/>
												</div>
											</div>
										</div>
										<div class="am-u-sm-12">
											<div style="border: 1px solid #000;padding: 5px;" title="授权">
												<label> 授权</label>
												<table style="overflow: auto;">
													<tr>
														<td align="left">
															<table>
																<c:forEach items="${types}" var="type">
																		<tr><td colspan="3"><input type="checkbox" id="type-${type.id }" onclick="xzxf(this)"
																name="firstId" value="${type.id }" />&nbsp;&nbsp;<label  style="text-align: left;"><span>${type.name }</span></label></td></tr>
																		<c:forEach items="${type.privileges }" var="p" varStatus="h">
																				<c:if test="${h.count==0 }">
																					<tr>
																				</c:if>
																				<c:if test="${(h.count-1)%3==0 && (h.count != 0)}" >
																					 </tr>
																					 <tr>
																				</c:if>
																					<td>
																						&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"
																						id="privilege-${p.id }" sora="${type.id==11?'a':'s'}"
																						name="${type.id}" value="${p.id }" onclick="xzskp(this)"/> <span>${p.name }</span>
																						&nbsp;&nbsp;&nbsp;&nbsp;
																					</td>
																		</c:forEach>
																		</tr>
																</c:forEach>
															
															</table>
														</td>
													</tr>
												</table>
											</div>
										</div>
										<div class="am-u-sm-12" style="margin-top: 5px;">
											<div class="am-form-group">
												<div class="am-u-sm-12  am-text-center">
													<button type="submit"
														class="js-submit  am-btn am-radius am-btn-success">确定</button>
													<button type="button"
														class="js-close  am-btn am-radius am-btn-warning">取消</button>
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
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/role.js" type="text/javascript"></script>
	<script type="text/javascript">
		function xzxf(obj) {
			if (obj.checked == false) {
				var smObj = document.getElementsByName(obj.value);
				for (var i = 0; i < smObj.length; i++)
					smObj[i].checked = false;
			} else {
				var smObj = document.getElementsByName(obj.value);
				for (var i = 0; i < smObj.length; i++)
					smObj[i].checked = true;
			}
		}
		function xzskp(obj) {
			var smObj = document.getElementById('type-' + obj.name);
			var smObj1 = document.getElementsByName(obj.name);
			if (obj.checked == true) {
				if (smObj.checked == false) {
					smObj.checked = true;
				}
			} else {
				var flag = true;
				for (var i = 0; i < smObj1.length; i++) {
					if (smObj1[i].checked == true) {
						flag = false;
					}
				}
				if (flag) {
					smObj.checked = false;
				}
			}
		}
	</script>
</body>
</html>
