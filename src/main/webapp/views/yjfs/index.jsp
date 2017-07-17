<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票发送</title>
<meta name="description" content="发票发送">
<meta name="keywords" content="发票发送">
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
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
	.data-ctr {
	  text-align: center;
	}
</style>
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
							<input type="hidden" id="bj">
							<div class="am-cf widget-head">
								<div class="widget-title am-cf">
									<strong id="yjcd" class="am-text-primary am-text-lg"></strong>/
									<strong id="ejcd"></strong>
									<button class="am-btn am-btn-success am-fr"
										data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
								</div>
							</div>
							<div id="doc-oc-demo3" class="am-offcanvas">
								<form action="" id="searchForm1">
									<div class="am-offcanvas-bar am-offcanvas-bar-flip">
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4  am-form-label">销方</label>
												<div class="am-u-sm-7">
													<select id="xfid" name="xfid"
														data-am-selected="{btnSize: 'sm'}">
														<option value="">请选择</option>
														<c:forEach items="${xfs}" var="item">
															<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4  am-form-label">购方名称</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_gfmc" name="s_gfmc"
														placeholder="请输入购方名称" />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="xfmc" class="am-u-sm-4 am-form-label">订单号</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_ddh" name="s_ddh"
														placeholder="请输入订单号" />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4  am-form-label">发票代码</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_fpdm" name="s_fpdm"
														placeholder="请输入发票代码" />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4  am-form-label">发票号码</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_fphm" name="s_fphm"
														placeholder="请输入发票号码" />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="xfmc" class="am-u-sm-4 am-form-label">交易日期始</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_jyrqq" name="s_jyrqq"
														data-am-datepicker="{format: 'yyyy-mm-dd'}"
														placeholder="点击选择时间" readonly />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="kpr1" class="am-u-sm-4 am-form-label">交易日期止</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_jyrqz" name="s_jyrqz"
														data-am-datepicker="{format: 'yyyy-mm-dd'}"
														placeholder="点击选择时间" readonly />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="xfmc" class="am-u-sm-4 am-form-label">开票日期始</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_kprqq" name="s_kprqq"
														data-am-datepicker="{format: 'yyyy-mm-dd'}"
														placeholder="点击选择时间" readonly />
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="kpr1" class="am-u-sm-4 am-form-label">开票日期止</label>
												<div class="am-u-sm-8">
													<input type="text" id="s_kprqz" name="s_kprqz"
														data-am-datepicker="{format: 'yyyy-mm-dd'}"
														placeholder="点击选择时间" readonly />
												</div>
											</div>
										</div>
										<div style="padding: 32px;">
											<button id="button3" type="button"
												class="am-btn am-btn-default am-btn-success data-back">查询</button>
										</div>
									</div>

								</form>

							</div>
							<div class="am-g  am-padding-top">
								<form action="#" id="searchform"
									class="js-search-form  am-form am-form-horizontal">
									<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
										<div class="am-form-group">
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<button type="button" id="button2"
														class="js-sent am-btn am-btn-default am-btn-success">
														发送</button>
													<!--                                                 <button type="button"  id="button4" class="modify am-btn am-btn-default am-btn-warning"> 修改</button> -->
													<!--                                                 <button type="button" id="deletexf" class="am-btn am-btn-default am-btn-danger js-sent"> 删除</button> -->
													<!--                                                 <button type="button" id="kp_dr" class="am-btn am-btn-default"> 导入</button> -->
												</div>
											</div>
										</div>
									</div>
									<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
										<div class="am-form-group tpl-table-list-select">
											<select id="tip" data-am-selected="{btnSize: 'sm'}">
												<option value="0">请选择</option>
												<option value="1" selected="selected">购方名称</option>
												<option value="2">订单号</option>
												<option value="5">销方名称</option>
												<option value="3">发票号码</option>
												<option value="4">发票代码</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
										<div
											class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input type="text" id="searchtxt" class="am-form-field ">
											<span class="am-input-group-btn" id="button1">
												<button
													class="am-btn js-search am-btn-default am-btn-success tpl-table-list-field am-icon-search"
													type="button"></button>
											</span>
										</div>
									</div>
								</form>
								<div class="am-u-sm-12 am-padding-top">

									<table
										class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
										<thead>
											<tr>
												<th><input type="checkbox" id="check_all" /></th>
												<th>序号</th>
												<th>操作</th>
												<th>订单号</th>
												<th>交易日期</th>
												<th>购方名称</th>
												<!-- <th>销方名称</th>
												<th>开票日期</th>
												<th>发票代码</th>
												<th>发票号码</th>
												<th class="data-ctr">金额</th>
												<th class="data-ctr">税额</th> -->
												<th class="data-ctr">价税合计</th>
												<th>邮箱</th>
												<th>发票接收方式</th>
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


					<div class="am-modal am-modal-no-btn" tabindex="-1"
						id="original-detail-modal">
						<div class="am-modal-dialog">
							<div class="am-modal-hd">
								修改发票接收地址 <a href="javascript: void(0)"
									class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>
							<div class="am-modal-bd">
								<hr />
								<form action="#" class="js-form-1  am-form am-form-horizontal">
									<div class="am-g">
										<div class="am-u-sm-12">

											<div class="am-form-group">
												<label for="gfemail" class="am-u-sm-4 am-form-label">邮件地址</label>
												<div class="am-u-sm-8">
													<input type="text" id="gfemail" name="gfemail"
														placeholder="" /> <input type="hidden" name="kplsh"
														placeholder="" /> <input type="hidden" name="rowId"
														placeholder="" />

												</div>
											</div>

											<%--<div class="am-form-group">--%>
											<%--<label for="sj" class="am-u-sm-4 am-form-label">手机号码</label>--%>
											<%--<div class="am-u-sm-8">--%>
											<%--<input type="text" id="sj" name="sj" placeholder=""/>--%>
											<%--</div>--%>
											<%--</div>--%>

											<%--<div class="am-form-group">--%>
											<%--<label for="wx" class="am-u-sm-4 am-form-label">微信账号</label>--%>
											<%--<div class="am-u-sm-8">--%>
											<%--<input type="text" id="wx" name="wx" placeholder=""/>--%>
											<%--</div>--%>
											<%--</div>--%>


										</div>
										<div class="am-u-sm-12">
											<div class="am-form-group">
												<div class="am-u-sm-12  am-text-center">
													<button type="button"
														class="js-close  am-btn am-btn-danger">关闭</button>
													<button type="submit"
														class="js-submit  am-btn am-btn-primary">保存</button>

												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
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

	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	
	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog" style="overflow-y: auto;">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd" id="msg"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>

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

	<script src="assets/js/fpfs.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
</body>
</html>
