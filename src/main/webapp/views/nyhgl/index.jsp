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
					<strong class="am-text-primary am-text-lg">系统管理</strong> / <strong>用户管理</strong>
				</div>
			</div>
			<hr />
			<div class="am-g  am-padding-top">
				<form action="#" class="js-search-form  am-form am-form-horizontal">
					<div class="am-g">
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-3 am-form-label">用户账号</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_yhzh" name="s_yhzh"
										placeholder="请输入用户账号" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fphm" class="am-u-sm-3 am-form-label">用户名称</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_yhmc" name="s_yhmc"
										placeholder="请输入用户名称" />
								</div>
							</div>
						</div>
					</div>
					<hr />
					<div class="am-u-sm-12  am-padding  am-text-center">
						<button id="button1" type="button"
							class="js-search  am-btn am-btn-success">查询</button>
						<button id="button2" type="button"
							class="js-search  am-btn am-btn-success">新增</button>
					</div>

					<div class="am-u-sm-12">
						<div class="am-scrollable-horizontal">

							<table
								class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名称</th>
										<th>性别</th>
										<th>账号</th>
										<th>角色</th>
										<th>手机号码</th>
										<th>用户邮箱</th>
										<th>管理员</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
			<div class="am-modal-dialog" style="overflow: auto;">
				<form id="fomm" class="js-form-0 am-form am-form-horizontal">
					<div class="am-tabs" data-am-tabs>
						<ul class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tab1">用户信息</a></li>
							<li><a href="#tab2">角色</a></li>
							<li><a href="#tab3">销方和开票点</a></li>
						</ul>
						<div class="am-tabs-bd">
							<div class="am-tab-panel am-fade am-in am-active" id="tab1">
								<div class="am-modal-hd">
									用户信息 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<hr />
									<div class="am-g">
										<div class="am-form-group">
											<label for="hc_yfphm" class="am-u-sm-2 am-form-label"><font
												color="red">*</font>用户账号</label>
											<div class="am-u-sm-10">
												<p
														style="float: left; text-align: center; width: 15%; color: red; height: 100%; padding-top: 5px;">${login_session_key.gsdm}_</p>
													<input type="text" id="yhzh" name="yhzh"
														style="width: 85%; float: left;"
														placeholder="请输入账号,不允许中文" pattern="^[\x01-\x7f]*$"
														class="am-form-field" required maxlength="20" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="hc_yfphm" class="am-u-sm-2 am-form-label"><font
												color="red">*</font>用户名称</label>
											<div class="am-u-sm-4">
												<input type="text" id="yhmc" name="yhmc" placeholder="用户名称"
													class="am-form-field" required maxlength="50" />
											</div>
											<label for="hc_yfphm" class="am-u-sm-2 am-form-label">用户性别</label>
											<div class="am-u-sm-4">
												<input id="xb1" type="radio" value="0" name="xb"
													checked="checked">男 &nbsp;&nbsp;&nbsp;&nbsp; <input
													id="xb2" type="radio" value="1" name="xb">女
											</div>
										</div>
										<div class="am-form-group">
											<label for="hc_yfphm" class="am-u-sm-2 am-form-label">用户邮箱</label>
											<div class="am-u-sm-4">
												<input type="email" id="yx" name="yx" placeholder="用户邮箱" />
											</div>
											<label for="hc_yfphm" class="am-u-sm-2 am-form-label">用户手机</label>
											<div class="am-u-sm-4">
												<input type="text" id="sjhm" name="sjhm" placeholder="用户手机号"
													class="am-form-field" maxlength="50" />
											</div>
										</div>
										<div class="am-form-group" id="mm1">
											<label for="hc_yfphm" class="am-u-sm-2 am-form-label"><font
												color="red">*</font>用户密码</label>
											<div class="am-u-sm-10">
												<input type="password" id="yhmm" name="yhmm"
													placeholder="大小写字母,数字,符号中三种,最少8位" class="am-form-field"
													required
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

										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="submit"
													class="js-submit  am-btn am-btn-success">确定</button>
												<button type="button"
													class="js-close  am-btn am-btn-warning">取消</button>
											</div>
										</div>
									</div>

								</div>
							</div>
							<div class="am-tab-panel am-fade" id="tab2">
								<div class="am-modal-hd">
									角色<a href="javascript: void(0)" class="am-close am-close-spin"
										data-am-modal-close>&times;</a>
								</div>
								<table style="width: 100%;">
									<tr align="left">
										<td><input type="checkbox" id="roles"
											onclick="qxjs(this)" name="roles" value="roles" />&nbsp;&nbsp;全选</td>
									</tr>
									<c:forEach items="${jss }" var="j">
										<tr align="left">
											<td style="width: 100%" colspan="2"><input
												type="checkbox" id="roles-${j.id }"
												name="jsid" value="${j.id }" />&nbsp;&nbsp;${j.name }
	
											</td>
										</tr>
									</c:forEach>
								</table>
								<div class="am-u-sm-12">
									<div class="am-form-group">
										<div class="am-u-sm-12  am-text-center">
											<button type="submit"
												class="js-submit  am-btn am-btn-success">确定</button>
											<button type="button" class="js-close  am-btn am-btn-warning">取消</button>
										</div>
									</div>
								</div>
							</div>
							<div class="am-tab-panel am-fade" id="tab3">
								<div class="am-modal-hd">
									销方和开票点<a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<table style="width: 100%;">
									<tr align="left">
										<td style="width: 100%" colspan="2">
											<div title="用户机构" style="padding: 10px;" id="bm-box1">

												<div class="am-panel-group" id="accordion">
													<c:forEach items="${xfs }" var="x" varStatus="i">
														<div class="am-panel am-panel-default">
															<div class="am-panel-hd">

																<h4 class="am-panel-title"
																	data-am-collapse="{parent: '#accordion', target: '#do-not-say-${x.id }'}">
																	<input type="checkbox" id="yhjg1-${x.id }"
																		onclick="xzxf(this)" name="xfid" value="${x.id }" />&nbsp;&nbsp;${x.xfmc }<i
																		class="am-icon-angle-right am-fr am-margin-right"></i>
																</h4>
															</div>
															<c:if test="${i.index==0 }">
																<div id="do-not-say-${x.id }"
																	class="am-panel-collapse am-collapse am-in">
																	<div class="am-panel-bd">
																		<c:forEach items="${sksbs }" var="sksb" varStatus="h">
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
																		<c:forEach items="${sksbs }" var="sksb" varStatus="h">
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
												class="js-submit  am-btn am-btn-success">确定</button>
											<button type="button" class="js-close  am-btn am-btn-warning">取消</button>
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
					重置密码 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
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
							<div class="am-form-group">
								<label for="hc_kpje" class="am-u-sm-4 am-form-label">确认密码</label>
								<div class="am-u-sm-8">
									<input type="password" id="qrmm" name="qrmm1"
										placeholder="请与上面输入的值一致" data-equal-to="#yhmm2"
										class="am-form-field" required maxlength="50" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 am-margin-top-lg">
							<div class="am-form-group">
								<div class="am-u-sm-12  am-text-center">
									<button type="button" id="jsSubmit"
										class=" am-btn am-btn-success">保存</button>
									<button type="button" class="js-close1  am-btn am-btn-warning">取消</button>
								</div>
							</div>
						</div>
					</div>
				</form>
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

	<script src="assets/js/nyhgl.js"></script>
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
			}else{
				for (var i = 0; i < smObj1.length; i++) {
					smObj1[i].checked = false;
				}
			}
		}
	</script>
</body>
</html>
