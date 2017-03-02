<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>订阅管理</title>
<meta name="description" content="订阅管理">
<meta name="keywords" content="订阅管理">
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
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
</head>
<body>
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="am-cf admin-main">
						<input type="hidden" id="xfidhide">
						<!-- content start -->
						<div class="admin-content">
							<div class="am-cf widget-head">
								<div class="widget-title am-cf">
									<strong class="am-text-primary am-text-lg">订阅管理</strong> / <strong>我的订阅</strong>
								</div>
							</div>
							<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
								<ul class="am-tabs-nav am-nav am-nav-tabs">
									<li class="am-active"><a href="#tab1">我的订阅</a></li>
									<li><a href="#tab2">订阅库</a></li>
								</ul>
								<div class="am-tabs-bd">
									<div class="am-tab-panel am-fade am-in am-active" id="tab1">
										<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
											<div class="am-form-group">
												<div class="am-btn-toolbar">
													<div class="am-btn-group am-btn-group-xs">
														<input type="hidden" id="searchbz">
														<button class="am-btn am-btn-secondary" id="jsAdd">
															<i class="am-icon-plus"></i>&nbsp;新增
														</button>
													</div>
												</div>
											</div>
										</div>
										<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
											<div class="am-form-group tpl-table-list-select">
												<select data-am-selected="{btnSize: 'sm'}" id="s_mainkey">
													<option value="btmc">订阅标题</option>
												</select>
											</div>
										</div>
										<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
											<div
												class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
												<input type="text" class="am-form-field" id="searchValue">
												<span class="am-input-group-btn">
													<button id="searchButton"
														class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
														type="button"></button>
												</span>
											</div>
										</div>
										<table id="mydytable"
											class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
											<thead>
												<tr>
													<th>序号</th>
													<th>订阅标题</th>
													<th>销方名称</th>
													<th>开票点</th>
													<th>订阅方式</th>
													<th>手机号码</th>
													<th>邮箱</th>
													<th>操作</th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="am-tab-panel am-fade am-u-sm-12" id="tab2">
										<form class="am-form">
											<div class="am-u-sm-12">
												<div class="am-u-sm-6">
													<div class="am-form-group">
														<label for="s_dymc" class="am-u-sm-3 am-form-label">订阅主题</label>
														<div class="am-u-sm-9">
															<select id="dybtid" name="dybtid" class="am-u-sm-12"
																onchange="getBz()">
																<option value="">----请选择----</option>
																<c:forEach items="${dybtList}" var="item">
																	<option value="${item.id}">${item.dybt}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
												<div class="am-u-sm-6">
													<div class="am-form-group">
														<label for="gfmc" class="am-u-sm-3 am-form-label"></label>
														<div class="am-u-sm-9"></div>
													</div>
												</div>
											</div>
											<div class="am-u-sm-12" style="margin-top: 8px">
												<div class="am-u-sm-6">
													<div class="am-form-group">
														<label for="s_ztxq" class="am-u-sm-3 am-form-label">主题详情</label>
														<div class="am-u-sm-9">
															<textarea id="ztxq" name="ztxq" class="am-u-sm-12"
																style="height: 400px"></textarea>
														</div>
													</div>
												</div>
												<div class="am-u-sm-6">
													<div class="am-form-group">
														<label for="gfmc" class="am-u-sm-3 am-form-label"></label>
														<div class="am-u-sm-9"></div>
													</div>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
						<!-- content end -->

						<!-- model -->
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="shezhi">
							<div class="am-modal-dialog" style="height: auto; width: auto">
								<form class="js-form-yjsz am-form">
									<div class="am-tabs" data-am-tabs>
										<div class="am-tabs-nav am-nav am-nav-tabs">
											<label>订阅设置</label>
										</div>
										<div class="am-tabs-bd">
											<div class="am-tab-panel am-in am-active" id="tab1">
												<div class="am-modal-bd">
													<div class="am-g">
														<div class="am-u-sm-12">
															<table id="addTable"
																class="am-table am-table-bordered am-table-striped am-text-nowrap">
																<tr>
																	<td><span style="color: red;">*</span>订阅标题</td>
																	<td><select id="dybtidinput" name="dybtid"
																		class="am-form-field" required>
																			<option value="">----请选择----</option>
																			<c:forEach items="${dybtList}" var="item">
																				<option value="${item.id}">${item.dybt}</option>
																			</c:forEach>
																	</select></td>
																</tr>
																<tr>
																	<td>&nbsp;销方名称</td>
																	<td><select id="a_xfid" name="xfid"
																		class="am-form-field">
																			<option value="">----请选择----</option>
																			<c:forEach items="${xfList}" var="item">
																				<option value="${item.id}">${item.xfmc}</option>
																			</c:forEach>
																	</select></td>
																</tr>
																<tr>
																	<td>&nbsp;&nbsp;&nbsp;开票点</td>
																	<td><select id="a_skpid" name="skpid"
																		class="am-form-field">
																			<option value="">----请选择----</option>
																			<c:forEach items="${skpList}" var="item">
																				<option value="${item.id}">${item.kpdmc}</option>
																			</c:forEach>
																	</select></td>
																</tr>
																<c:forEach items="${dyfsList}" var="item">
																	<tr>
																		<td colspan="2"><input class="dyfs" name="dyfs"
																			type="checkbox" id="${item.dyfsdm}">&nbsp;&nbsp;${item.dyfsmc}</td>
																	</tr>
																</c:forEach>
																<tr id="a_sjhm" class="trHidden">
																	<td><span>&nbsp;手机号码</span></td>
																	<td><input
																		class="js-pattern-patternPhone am-form-field dyfsInput"
																		name="sjhm" placeholder="请输入手机号" id="h_sjhm"></td>
																</tr>
																<tr id="a_email" class="trHidden">
																	<td><span>&nbsp;邮箱地址</span></td>
																	<td><input type="email"
																		class="am-form-field dyfsInput" name="email"
																		placeholder="请输入邮箱" id="h_email"></td>
																</tr>
																<tr id="a_ewm" class="trHidden">
																	<td><span>扫描并关注</span></td>
																	<td><img src="img/dy.jpg"
																		style="height: 200px; width: 200px"> <input
																		class="dyfsInput" type="hidden" name="openid"
																		id="h_openid"></td>
																</tr>
															</table>
														</div>
														<div class="am-u-sm-12">
															<div class="am-form-group">
																<div class="am-u-sm-12  am-text-center">
																	<button type="submit"
																		class="js-submit am-btn am-radius am-btn-success">保存</button>
																	<button type="button"
																		onclick="$('#shezhi').modal('close');"
																		class="js-close am-btn am-radius am-btn-warning">取消</button>
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
						</div>
						<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">提示</div>
								<div class="am-modal-bd" id="alert-msg"></div>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/mydy.js"></script>
	<script type="text/javascript">
		function getBz() {
			var id = $('#dybtid option:selected').val();
			$.ajax({
				url : "mydy/getBz",
				data : {
					"id" : id
				},
				success : function(data) {
					$("#ztxq").val(data.bz);
				}
			});
		}
	</script>
</body>
</html>