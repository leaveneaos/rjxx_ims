<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票预警订阅</title>
<meta name="description" content="发票预警订阅">
<meta name="keywords" content="发票预警订阅">
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
<style type="text/css">
.top-position {
	margin-top: 8px
}
</style>
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
									<br> <strong class="am-text-primary am-text-lg">发票库存</strong>
									/ <strong>库存预警订阅</strong>
									<button class="am-btn am-btn-success am-fr"
										data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
								</div>
								<div id="doc-oc-demo3" class="am-offcanvas">
									<div class="am-offcanvas-bar am-offcanvas-bar-flip">
										<form class="js-search-form am-form">
											<div class="am-offcanvas-content">
												<div class="am-form-group">
													<label for="s_xfmc" class="am-u-sm-4 am-form-label">销方名称</label>
													<div class="am-u-sm-8">
														<select id="xfid" name="xfid"
															data-am-selected="{btnSize: 'sm'}">
															<c:forEach items="${xfList}" var="xf">
																<option value="${xf.id}">${xf.xfmc}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content top-position">
												<div class="am-form-group">
													<label for="s_kpdmc" class="am-u-sm-4 am-form-label">开票点</label>
													<div class="am-u-sm-8">
														<select id="s_skpid" name="skpid"
															data-am-selected="{btnSize: 'sm'}">
															<option value="">请选择开票点</option>
															<c:forEach items="${skpList}" var="skp">
																<option value="${skp.id}">${skp.kpdmc}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content top-position">
												<div class="am-form-group">
													<label for="s_fplx" class="am-u-sm-4 am-form-label">发票种类</label>
													<div class="am-u-sm-8">
														<select id="s_fplx" name="fpzldm"
															data-am-selected="{btnSize: 'sm'}">
															<option value="">请选择发票类型</option>
															<c:forEach items="${fplxList}" var="item">
																<option value="${item.fpzldm}">${item.fpzlmc}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div style="padding: 32px;">
												<button type="button" id="button1"
													class="am-btn am-btn-default am-btn-success">
													<span class="am-icon-search-plus"></span> 查询
												</button>
											</div>
									</form>
								</div>
							</div>
							<hr/>
							<div class="am-g  am-padding-top">
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<input type="hidden" id="searchbz">
												<button class="am-btn am-btn-success" id="button2">
													<i class="am-icon-plus"></i>&nbsp;设置
												</button>
											</div>
										</div>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
									<div class="am-form-group tpl-table-list-select">
										<select data-am-selected="{btnSize: 'sm'}" id="s_mainkey">
											<option value="xfsh">销方税号</option>
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
								<div class="am-u-sm-12">
									<div >
										<table id="yjdytable"
											class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
											<thead>
												<tr>
													<th><input type="checkbox" id="selectAll" /></th>
													<th>序号</th>
													<th>销方名称</th>
													<th>销方税号</th>
													<th>开票点名称</th>
													<th>发票种类</th>
													<th>剩余库存(张)</th>
													<th>首页订阅</th>
													<th>Email订阅</th>
													<th>库存预警阈值</th>
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
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="shezhi">
							<div class="am-modal-dialog" style="height: 240px; width: 400px">
								<form class="js-form-yjsz am-form">
									<div class="am-tabs" data-am-tabs>
										<div class="am-tabs-nav am-nav am-nav-tabs">
											<label>预警订阅设置</label>
										</div>
										<div class="am-tabs-bd">
											<div class="am-tab-panel am-in am-active" id="tab1">
												<div class="am-modal-bd">
													<div class="am-g">
														<div class="am-u-sm-12">
															<table
																class="am-table am-table-bordered am-table-striped am-text-nowrap">
																<tr>
																	<td><input type="checkbox" id="sfsy" name="sfsy">首页订阅</td>
																	<td><input type="checkbox" id="sfemail"
																		name="sfemail">email订阅</td>
																</tr>
																<tr>
																	<td><span style="color: red;">*</span>库存预警阈值</td>
																	<td><input type="text" id="yjkcl" name="yjkcl"
																		pattern="^\d{0,8}$" placeholder="请输入库存预警阈值"
																		class="am-form-field" required></td>
																</tr>
															</table>
														</div>
														<div class="am-u-sm-12">
															<div class="am-form-group">
																<div class="am-u-sm-12  am-text-center">
																	<button type="submit"
																		class="js-submit am-btn am-btn-primary">确定</button>
																	<button type="button"
																		onclick="$('#shezhi').modal('close');"
																		class="js-close am-btn am-btn-danger">取消</button>
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
	<script src="assets/js/fpyjdy.js"></script>
</body>
</html>