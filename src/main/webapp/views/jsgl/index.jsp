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
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
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
					<strong class="am-text-primary am-text-lg">业务处理</strong> / <small>角色管理</small>
				</div>
			</div>
			<hr />
			<div class="am-g">
				<div class="am-u-sm-12  am-text-center">
					<h2>用户管理</h2>
				</div>
			</div>

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
							class="js-search  am-btn am-btn-primary">查询</button>
						<button id="button2" type="button"
							class="js-search  am-btn am-btn-primary">新增</button>
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
										<th>录入人员</th>
										<th>角色</th>
										<th>用户机构</th>
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
			<div class="am-modal-dialog">
				<form class="js-form-0 am-form am-form-horizontal">
					<div class="am-tabs" data-am-tabs>
						<ul class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tab1">用户信息</a></li>
							<li><a href="#tab2">角色与销方</a></li>
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
										<div class="am-u-sm-12">
											<div class="am-form-group">
												<label for="hc_yfpdm" class="am-u-sm-4 am-form-label">用户名称</label>
												<div class="am-u-sm-8">
													<input type="text" id="yhmc" name="yhmc"
														placeholder="请输入名称" class="am-form-field" required maxlength="50"/>
												</div>
											</div>
											<div class="am-form-group">
												<label for="hc_yfpdm" class="am-u-sm-4 am-form-label">选择性别</label>
												<select id="xb" name="xb" style="width: 280px; margin-left: 171px;">
													<option value="0">男</option>
													<option value="1">女</option>
												</select>
											</div>
											<div class="am-form-group">
												<label for="hc_yfphm" class="am-u-sm-4 am-form-label">登录账号</label>
												<div class="am-u-sm-8">
													<input type="text" id="yhzh" name="yhzh"
														placeholder="请输入账号" class="am-form-field" required maxlength="20"/>
												</div>
											</div>
											<div class="am-form-group">
												<label for="hc_kpje" id="dlmm" class="am-u-sm-4 am-form-label">登录密码</label>
												<div class="am-u-sm-8">
													<input type="password" id="yhmm" name="yhmm"
														placeholder="请输入密码" class="am-form-field" required  maxlength="50"/>
												</div>
											</div>
											<div class="am-form-group">
												<label for="hc_kpje" id="dlmm1" class="am-u-sm-4 am-form-label">确认密码</label>
												<div class="am-u-sm-8">
													<input type="password" id="qrmm" name="qrmm"
														placeholder="请与上面输入的值一致" data-equal-to="#yhmm" class="am-form-field" required maxlength="50"/>
												</div>
											</div>
										</div>
										<div class="am-u-sm-12">
											<div class="am-form-group">
												<div class="am-u-sm-12  am-text-center">
													<button type="submit"
														class="js-submit  am-btn am-btn-primary">确定</button>
													<button type="button"
														class="js-close  am-btn am-btn-danger" >取消</button>
												</div>
											</div>
										</div>
									</div>

								</div>
							</div>
							<div class="am-tab-panel am-fade" id="tab2">
								<div class="am-modal-hd">
									<a href="javascript: void(0)" class="am-close am-close-spin"
										data-am-modal-close>&times;</a>
								</div>
								<table style="width: 100%">
									<tr>
										<td>角色</td>
										<td>销方</td>
									</tr>
									<tr>
										<td style="width: 50%"><div title="用户机构"
												style="padding: 10px;" id="bm-box">
												<c:forEach items="${jss }" var="x">
													<div style="padding-left: 10px;">
														<label> <input type="checkbox" id="yhjg-${x.id }"
															name="jsid" value="${x.id }" /> <span>${x.name }</span>
														</label>
													</div>
												</c:forEach>
											</div></td>
										<td style="width: 50%">
											<div title="用户机构" style="padding: 10px;" id="bm-box1">
												<c:forEach items="${xfs }" var="x">
													<div style="padding-left: 10px;">
														<label> <input type="checkbox"
															id="yhjg1-${x.xfid }" name="xfid" value="${x.xfid }" /> <span>${x.xfmc }</span>
														</label>
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</table>
									<div class="am-u-sm-12">
											<div class="am-form-group">
												<div class="am-u-sm-12  am-text-center">
													<button type="submit"
														class="js-submit  am-btn am-btn-primary">确定</button>
													<button type="button"
														class="js-close  am-btn am-btn-danger" >取消</button>
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
</body>
</html>
