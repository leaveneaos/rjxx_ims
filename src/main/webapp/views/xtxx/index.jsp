<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>系统消息</title>
<meta name="description" content="系统消息" />
<meta name="keywords" content="系统消息" />
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
</head>
<body>
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="am-cf admin-main">
						<div class="admin-content">
							<div class="am-cf am-padding">
								<div class="am-fl am-cf">
									<strong class="am-text-primary am-text-lg">系统消息</strong>
								</div>
							</div>
							<hr />
							<div class="am-g  am-padding-top">
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button type="button" id="xx_cx"
													class="am-btn am-btn-default am-btn-primary">
													<span></span>查询
												</button>
												<button type="button" id="xx_del"
													class="am-btn am-btn-default am-btn-danger">
													<span></span>删除
												</button>
												<button type="button" id="xx_yd"
													class="am-btn am-btn-default am-btn-success">
													<span></span>标记为已读
												</button>
												<button type="button" id="xx_wd"
													class="am-btn am-btn-default am-btn-secondary">
													<span></span>标记为未读
												</button>
												<button type="button" id="xx_qyd"
													class="am-btn am-btn-default am-btn-warning">
													<span></span>全部已读
												</button>
											</div>
										</div>
									</div>
								</div>
								<br><br>
								<div class="am-u-sm-12">
									<div>
										<table
											class="js-table am-table am-table-bordered am-table-striped am-text-nowrap">
											<thead>
												<tr>
												　　　<th><input type="checkbox" id="selectall"></th>
													<th>序号</th>
													<th><span class="am-icon-home am-icon-envelope"></span></th>
													<th>消息标题</th>
													<th>消息类型</th>
													<th>发送时间</th>
													<th>操作</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="m_div">
							<div class="am-modal-dialog">
								<form class="js-form-0 am-form am-form-horizontal">
									<div class="am-modal-bd">
										<div class="am-g">
											<div class="am-form-group">
												<div class="am-u-sm-12">
													<label id="m_title" class="am-u-sm-12"></label>
												</div>
											</div>
											<br>
											<div class="am-form-group">
												<div class="am-u-sm-12">
													<div id="m_content"></div>
												</div>
											</div>
											<br> <br>
											<div class="am-form-group">
												<div class="am-u-sm-12">
													<div class="am-u-sm-5" id="m_lrsj"></div>
													<div class="am-u-sm-4">　</div>
													<div class="am-u-sm-3">
														<button type="button" id="closem"
															class="am-btn am-btn-warning">确定</button>
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
	<script src="assets/js/xtxx.js"></script>
</body>
</html>
