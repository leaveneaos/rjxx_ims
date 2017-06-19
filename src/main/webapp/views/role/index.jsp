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
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="am-cf admin-main">
						<!-- sidebar start -->
						<!-- sidebar end -->

						<!-- content start -->
						<div class="admin-content">
							<div class="am-cf widget-head">
								<div class="widget-title am-cf">
									<strong id="yjcd" class="am-text-primary am-text-lg"></strong> / <strong id="ejcd"></strong>
								</div>
							</div>
							<div class="am-g  am-padding-top">
								<form action="#"
									class="js-search-form  am-form am-form-horizontal">

									<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
										<div class="am-form-group">
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<button type="button" id="button2"
														class="am-btn am-btn-default am-btn-success">录入</button>
													<button type="button"
														class="js-sent am-btn am-btn-default am-btn-danger">
														删除
													</button>
												</div>
											</div>
										</div>
									</div>
									<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
										<div class="am-form-group tpl-table-list-select">
											<select id="tip" data-am-selected="{btnSize: 'sm'}">
												<option value="2">角色名称</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
										<div
											class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input type="text" id="s_jsmc" class="am-form-field ">
											<span class="am-input-group-btn" id="button1">
												<button
													class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search"
													type="button"></button>
											</span>
										</div>
									</div>
								</form>

								<div class="am-u-sm-12 am-padding-top">
									<div>

										<table id="tbl"
											class="js-table am-table am-table-bordered am-table-striped am-text-nowrap">
											<thead>
												<tr>
													<th class="am-text-center"><input type="checkbox" id="check_all" /></th>
													<th>序号</th>
													<th>操作</th>
													<th>角色名称</th>
													<th>录入人员</th>
													<th>修改人员</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!-- content end -->

						<!-- model -->
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
							<div class="am-modal-dialog" style="overflow-y: auto;">
								<div class="am-modal-hd">
									角色信息<a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<form id="form1" class="js-form-0 am-form am-form-horizontal">
									<div class="am-tabs" data-am-tabs>
										<div class="am-tabs-bd">
											<div class="am-tab-panel am-fade am-in am-active" id="tab1">
												<div class="am-modal-bd">
													<div class="am-g">
														<div class="am-u-sm-12">
															<div class="am-form-group">
																<label for="hc_yfpdm" class="am-u-sm-4 am-form-label"
																	style="margin-bottom: 40px;"><font color="red">*</font>角色名称</label>
																<div class="am-u-sm-8" style="margin-bottom: 30px;">
																	<input type="text" id="name" name="name"
																		placeholder="请输入名称" class="am-form-field" required
																		maxlength="50" /> <input id="roleid" name="id"
																		type="hidden" />
																</div>
															</div>
														</div>
														<div class="am-u-sm-12">
															<div title="授权">
																<label> 授权</label>
																<table style="overflow: auto;">
																	<tr>
																		<td align="left">
																			<table>
																				<c:forEach items="${types}" var="type">
																					<tr>
																						<td colspan="3"><input type="checkbox"
																							id="type-${type.id }" onclick="xzxf(this)"
																							name="firstId" value="${type.id }" />&nbsp;&nbsp;<label
																							style="text-align: left;"><span>${type.name }</span></label></td>
																					</tr>
																					<c:forEach items="${type.privileges }" var="p"
																						varStatus="h">
																						<c:if test="${h.count==0 }">
																							<tr>
																						</c:if>
																						<c:if test="${(h.count-1)%3==0 && (h.count != 0)}">
																							</tr>
																							<tr>
																						</c:if>
																						<td>&nbsp;&nbsp;&nbsp;&nbsp;<input
																							type="checkbox" id="privilege-${p.id }"
																							sora="${type.id==11?'a':'s'}" name="${type.id}"
																							value="${p.id }" onclick="xzskp(this)" /> <span>${p.name }</span>
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
								</form>
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
				</div>
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
	<script src="assets/js/sweetalert.min.js"></script>
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
