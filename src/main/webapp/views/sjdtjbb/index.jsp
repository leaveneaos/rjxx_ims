<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>统计报表</title>
<meta name="description" content="分月统计报表">
<meta name="keywords" content="分月统计报表">
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
	<div class="am-g tpl-g">
		<div class="row-content am-cf">
			<div class="row">
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
					<div class="am-cf admin-main">
						<div class="admin-content">
							<div class="am-cf widget-head">
								<div class="widget-title am-cf">
									<br> <strong class="am-text-primary am-text-lg">发票查询</strong>
									/ <strong>时间段统计报表</strong>
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
														<select id="s_xfid" name="xfid"
															data-am-selected="{btnSize: 'sm'}">
															<option value="">请选择销方</option>
															<c:forEach items="${xfs}" var="xf">
																<option value="${xf.id}">${xf.xfmc}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content top-position">
												<div class="am-form-group">
													<label for="s_fplx" class="am-u-sm-4 am-form-label">发票种类</label>
													<div class="am-u-sm-8">
														<select id="s_fpzl" name="fpzldm"
															data-am-selected="{btnSize: 'sm'}">
															<option value="">请选择发票种类</option>
															<c:forEach items="${fpzlList}" var="item">
																<option value="${item.fpzldm}">${item.fpzlmc}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content top-position">
												<div class="am-form-group">
													<label for="s_kprqq" class="am-u-sm-4 am-form-label">起始年月</label>
													<div class="am-input-group am-datepicker-date am-u-sm-8"
														data-am-datepicker="{format: 'yyyy-mm',viewMode: 'months', minViewMode: 'months'}">
														<input type="text" id="s_kprqq" class="am-form-field"
															placeholder="选择起始月份" readonly> <span
															class="am-input-group-btn am-datepicker-add-on">
															<button class="am-btn am-btn-default" type="button">
																<span class="am-icon-calendar"></span>
															</button>
														</span>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content top-position">
												<div class="am-form-group">
													<label for="s_kprqq" class="am-u-sm-4 am-form-label">终止年月</label>
													<div class="am-input-group am-datepicker-date am-u-sm-8"
														data-am-datepicker="{format: 'yyyy-mm',viewMode: 'months', minViewMode: 'months'}">
														<input type="text" id="s_kprqz" class="am-form-field"
															placeholder="选择起始月份" readonly> <span
															class="am-input-group-btn am-datepicker-add-on">
															<button class="am-btn am-btn-default" type="button">
																<span class="am-icon-calendar"></span>
															</button>
														</span>
													</div>
												</div>
											</div>
											<div style="padding: 32px;">
												<button type="button" id="jsSearch"
													class="am-btn am-btn-default am-btn-success">
													<span class="am-icon-search-plus"></span> 查询
												</button>
											</div>
										</form>
									</div>
								</div>
							</div>
							<form action="#" class="js-form am-form am-form-horizontal">
								<div class="am-g">
									<br>
									<div class="am-u-sm-12">
										<div class="am-u-sm-4">
											<div class="am-input-group am-datepicker-date am-u-sm-12"
												data-am-datepicker="{format: 'yyyy-mm',viewMode: 'months', minViewMode: 'months'}">
												<input type="text" id="s_xzrq" class="am-form-field"
													placeholder="选择起始月份" readonly> <span
													class="am-input-group-btn am-datepicker-add-on">
													<button class="am-btn am-btn-default" type="button">
														<span class="am-icon-calendar"></span>
													</button>
												</span>
											</div>
										</div>
										<div class="am-u-sm-4">
											<div class="am-input-group am-datepicker-date am-u-sm-12"
												data-am-datepicker="{format: 'yyyy-mm',viewMode: 'months', minViewMode: 'months'}">
												<input type="text" id="s_xzrq1" class="am-form-field"
													placeholder="选择终止月份" readonly> <span
													class="am-input-group-btn am-datepicker-add-on">
													<button class="am-btn am-btn-default" type="button">
														<span class="am-icon-calendar"></span>
													</button>
												</span>
											</div>
										</div>
										<div class="am-u-sm-4">
											<div class="am-u-sm-12">
												<button type="button" id="searchButton"
													class="am-btn am-btn-default am-btn-success">
													<span class="am-icon-search-plus"></span> 查询
												</button>
												<input type="hidden" id="searchbz">
											</div>
										</div>
									</div>
								</div>
							</form>
							<br> <br>
							<div class="am-u-sm-12">
								<table
									class="js-table am-table am-table-bordered am-table-striped am-text-nowrap">
									<thead>
										<tr>
											<th colspan="6">发票统计</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>正数票份数</td>
											<td><input type="text" id="zspfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
											<td>负数票份数</td>
											<td><input type="text" id="fspfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
											<td>合计</td>
											<td><input type="text" id="hjpfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
										</tr>
										<tr>
											<td>正常发票份数</td>
											<td><input type="text" id="zcpfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
											<td>红冲发票份数</td>
											<td><input type="text" id="hcpfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
											<td>换开发票份数</td>
											<td><input type="text" id="hkpfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
										</tr>
										<tr>
											<td>作废发票份数</td>
											<td><input type="text" id="zfpfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
											<td>重开发票份数</td>
											<td><input type="text" id="ckpfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
											<td>重打发票份数</td>
											<td><input type="text" id="cdpfs" placeholder="张"
												readonly style="border: none; background: transparent;" /></td>
										</tr>
									</tbody>
								</table>
								<br>
								<div class="am-u-sm-12" style="text-align: center">
									<span><strong>税率统计</strong></span>
								</div>
								<br>
								<table
									class="js-sltable am-table am-table-bordered am-table-striped am-text-nowrap">
									<thead>
										<tr>
											<th>税率(%)</th>
											<th>销方名称</th>
											<th>发票种类</th>
											<th>税率类型</th>
											<th>销项正数金额</th>
											<th>销项负数金额</th>
											<th>合计金额</th>
											<th>正数税额</th>
											<th>负数税额</th>
											<th>合计税额</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
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
	<script src="assets/js/Sjdtjbb.js"></script>

</body>
</html>