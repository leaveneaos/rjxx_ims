<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>用户管理</title>
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
									<button class="am-btn am-btn-success am-fr"
										data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
								</div>
							</div>
							<div id="doc-oc-demo3" class="am-offcanvas">
								<form action="" id="searchform1">
									<div class="am-offcanvas-bar am-offcanvas-bar-flip">
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_fpdm" class="am-u-sm-4 am-form-label">用户账号</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_yhzh" name="s_yhzh"
														placeholder="请输入用户账号" />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_fphm" class="am-u-sm-4 am-form-label">用户名称</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_yhmc" name="s_yhmc"
														placeholder="请输入用户名称" />
												</div>
											</div>
										</div>
										<div style="padding: 32px;">
											<button id="button1" type="button"
												class="js-search am-btn am-btn-default am-btn-success data-back">
												查询</button>
										</div>
									</div>

								</form>

							</div>
							<div class="am-g am-padding-top">
								<form action="#" id="searchform"
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
												<option value="0">请选择</option>
												<option value="1">用户账号</option>
												<option value="2">用户名称</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
										<div
											class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input type="text" id="searchtxt" class="am-form-field ">
											<span class="am-input-group-btn" id="button3">
												<button
													class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search"
													type="button"></button>
											</span>
										</div>
									</div>
								</form>

								<div class="am-u-sm-12 am-padding-top">
									<div>

										<table
											class="js-table am-table am-table-bordered am-table-striped am-text-nowrap">
											<thead>
												<tr>
													<th><input type="checkbox" id="check_all" /></th>
													<th>序号</th>
													<th>操作</th>
													<th>用户名称</th>
													<th>性别</th>
													<th>账号</th>
													<th>角色</th>
													<th>手机号码</th>
													<th>用户邮箱</th>
													<th>管理员</th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!-- content end -->

						<!-- model -->
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
							<div class="am-modal-dialog" style="overflow: auto;">
								<div class="am-modal-hd">
									用户信息 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<form id="fomm" class="js-form-0 am-form am-form-horizontal">
									<div class="am-tabs" data-am-tabs>
										<ul class="am-tabs-nav am-nav am-nav-tabs">
											<li class="am-active"><a href="#tab1">用户信息</a></li>
											<li><a href="#tab2">角色</a></li>
											<li><a href="#tab3">销方和开票点</a></li>
										</ul>
										<div class="am-tabs-bd">
											<div class="am-tab-panel am-fade am-in am-active" id="tab1">
												<div class="am-modal-bd">
													<hr />
													<div class="am-g">
														<div class="am-form-group">
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label"><font
																color="red">*</font>用户账号</label>
															<div class="am-u-sm-10">
																<p style="float: left; text-align: center; width: 15%; color: red; height: 100%; padding-top: 5px;">${gsdm}_</p>
																<input type="text" id="yhzh" name="yhzh" autocomplete="off"
																	style="width: 85%; float: left;"
																	placeholder="请输入账号,不允许中文" pattern="^[\x01-\x7f]*$"
																	class="am-form-field" required maxlength="20" />
															</div>
														</div>
														<div class="am-form-group">
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label"><font
																color="red">*</font>用户名称</label>
															<div class="am-u-sm-4">
																<input type="text" id="yhmc" name="yhmc" autocomplete="off"
																	placeholder="用户名称" class="am-form-field" required
																	maxlength="50" />
															</div>
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label">用户性别</label>
															<div class="am-u-sm-4">
																<select name="xb" id="xb1"
																	data-am-selected="{btnWidth: '100%'}">
																	<option value="0">男</option>
																	<option value="1">女</option>
																</select>
															</div>
														</div>
														<div class="am-form-group">
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label">用户邮箱</label>
															<div class="am-u-sm-4">
																<input type="email" id="yx" name="yx" class="am-form-field" placeholder="用户邮箱" />
															</div>
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label">用户手机</label>
															<div class="am-u-sm-4">
																<input type="text" id="sjhm" name="sjhm"
																	placeholder="用户手机号" class="am-form-field"
																	maxlength="50" />
															</div>
														</div>
														<div class="am-form-group" id="mm1">
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label"><font
																color="red">*</font>用户密码</label>
															<div class="am-u-sm-10">
																<input type="password" id="yhmm" name="yhmm"
																	placeholder="请输入密码"
																	data-validation-message="大小写字母,数字,符号中三种,最少8位"
																	class="am-form-field" required
																	pattern="^(?![0-9a-z]+$)(?![0-9A-Z]+$)(?![0-9\W]+$)(?![a-z\W]+$)(?![a-zA-Z]+$)(?![A-Z\W]+$)[a-zA-Z0-9\W_]+$"
																	minlength="8" maxlength="50" />
															</div>
														</div>
														<div class="am-form-group" id="mm2">
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label"><font
																color="red">*</font>确认密码</label>
															<div class="am-u-sm-10">
																<input type="password" id="qrmm" name="qrmm"
																	placeholder="确认密码" data-equal-to="#yhmm"
																	class="am-form-field" required maxlength="50" />
															</div>
														</div>
														<div class="am-form-group" id="mm3">
															<label for="hc_yfphm" class="am-u-sm-2 am-form-label"></label>
															<div class="am-u-sm-10 am-text-left">
																<font color="red">注：密码必须为大、小写字母、数字、符号中三种,最少8位</font>

															</div>
														</div>

														<div class="am-form-group am-padding-top">
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
											<div class="am-tab-panel am-fade" id="tab2">
												<table style="width: 100%;">
													<tr align="left">
														<td><input type="checkbox" id="roles"
															onclick="qxjs(this)" name="roles" value="roles" />&nbsp;&nbsp;全选</td>
													</tr>
													<c:forEach items="${jss }" var="j">
														<tr align="left">
															<td style="width: 100%" colspan="2"><input
																type="checkbox" id="roles-${j.id }" name="jsid"
																value="${j.id }" />&nbsp;&nbsp;${j.name }</td>
														</tr>
													</c:forEach>
												</table>
												<div class="am-u-sm-12">
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
											<div class="am-tab-panel am-fade" id="tab3">
												<table style="width: 100%;">
													<tr align="left">
														<td style="width: 100%" colspan="2">
															<div title="用户机构" style="padding: 10px;" id="bm-box1">

																<div class="am-panel-group" id="accordion">
																	<input type="checkbox" onclick="xfqx(this)" id="all"
																		name="all" />&nbsp;&nbsp;全选
																	<c:forEach items="${xfs }" var="x" varStatus="i">
																		<div class="am-panel am-panel-default">
																			<div class="am-panel-hd">

																				<h4 class="am-panel-title"
																					data-am-collapse="{parent: '#accordion', target: '#do-not-say-${x.id }'}">
																					<input type="checkbox" id="yhjg1-${x.id }"
																						name="xfid" value="${x.id }" onclick="xzxf(this)" />&nbsp;&nbsp;${x.xfmc }<i
																						class="am-icon-angle-right am-fr am-margin-right"></i>
																				</h4>
																			</div>
																			<c:if test="${i.index==0 }">
																				<div id="do-not-say-${x.id }"
																					class="am-panel-collapse am-collapse am-in">
																					<div class="am-panel-bd">
																						<c:forEach items="${sksbs }" var="sksb"
																							varStatus="h">
																							<c:if test="${sksb.xfid == x.id}">
																			&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"
																									id="skp-${sksb.id }" onclick="xzskp(this)"
																									name="${x.id }" value="${sksb.id }" />&nbsp;&nbsp;${sksb.kpdmc }(${sksb.skph })<br>
																							</c:if>
																						</c:forEach>
																					</div>
																				</div>
																			</c:if>
																			<c:if test="${i.index!=0 }">
																				<div id="do-not-say-${x.id }"
																					class="am-panel-collapse am-collapse">
																					<div class="am-panel-bd">
																						<c:forEach items="${sksbs }" var="sksb"
																							varStatus="h">
																							<c:if test="${sksb.xfid == x.id}">
																			&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"
																									id="skp-${sksb.id }" onclick="xzskp(this)"
																									name="${x.id }" value="${sksb.id }" />${sksb.kpdmc }(${sksb.skph })<br>
																							</c:if>
																						</c:forEach>
																					</div>
																				</div>
																			</c:if>
																		</div>
																	</c:forEach>
																</div>
															</div>
														</td>
													</tr>
												</table>
												<div class="am-u-sm-12">
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
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="chongzhi"
							title="销方信息">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">
									重置密码 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<form class="js-form-1 am-form am-form-horizontal">
									<div class="am-g">
										<div class="am-form-group">
											<label for="hc_kpje" class="am-u-sm-4 am-form-label"
												style="margin-bottom: 30px;">重置密码</label>
											<div class="am-u-sm-8" style="margin-bottom: 30px;">
												<input type="password" id="yhmm2" name="yhmm2"
													placeholder="大,小写字母,数字,符号中三种,最少8位" class="am-form-field"
													required
													pattern="^(?![0-9a-z]+$)(?![0-9A-Z]+$)(?![0-9\W]+$)(?![a-z\W]+$)(?![a-zA-Z]+$)(?![A-Z\W]+$)[a-zA-Z0-9\W_]+$"
													minlength="8" maxlength="50" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="hc_kpje" class="am-u-sm-4 am-form-label">确认密码</label>
											<div class="am-u-sm-8">
												<input type="password" id="qrmm1" name="qrmm1"
													placeholder="请与上面输入的值一致" data-equal-to="#yhmm2"
													class="am-form-field" required maxlength="50" />
											</div>
										</div>
										<div class="am-u-sm-12 am-margin-top-lg">
											<div class="am-form-group">
												<div class="am-u-sm-12  am-text-center">
													<button type="button" id="jsSubmit"
														class=" am-btn am-radius am-btn-success">保存</button>
													<button type="button"
														class="js-close1  am-btn am-radius am-btn-warning">取消</button>
												</div>
											</div>
										</div>
									</div>
								</form>
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
	<script src="assets/js/jquery.form.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>

	<script src="assets/js/nyhgl.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script type="text/javascript">
		function xzxf(obj) {
			if (obj.checked == false) {
				var smObj = document.getElementsByName(obj.value);
				for (var i = 0; i < smObj.length; i++)
					smObj[i].checked = false;
			}
		}
		function xfqx(obj) {
			var xfids = document.getElementsByName('xfid');
			if (obj.checked == true) {
				for (var i = 0; i < xfids.length; i++) {
					xfids[i].checked = true;
				}
			} else {
				for (var i = 0; i < xfids.length; i++) {
					xfids[i].checked = false;
				}
			}
		}
		function xzskp(obj) {
			var smObj = document.getElementById('yhjg1-' + obj.name);
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
		function qxjs(obj) {
			var smObj = document.getElementById('roles');
			var smObj1 = document.getElementsByName("jsid");
			if (obj.checked == true) {
				for (var i = 0; i < smObj1.length; i++) {
					smObj1[i].checked = true;
				}
			} else {
				for (var i = 0; i < smObj1.length; i++) {
					smObj1[i].checked = false;
				}
			}
		}
	</script>
</body>
</html>
