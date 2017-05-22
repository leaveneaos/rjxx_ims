<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>审核开票单</title>
<meta name="description" content="审核开票单">
<meta name="keywords" content="user">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<input type="hidden" id="kplsh" value="0">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="admin-content">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>开票单审核</strong>
								<button class="am-btn am-btn-success am-fr"
									data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
							</div>
							<!-- 侧边栏内容 -->
							<div id="doc-oc-demo3" class="am-offcanvas">
								<div class="am-offcanvas-bar am-offcanvas-bar-flip">
									<form id="ycform">
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_ddh" class="am-u-sm-4 am-form-label">选择销方</label>
												<div class="am-u-sm-8">
													<select data-am-selected="{btnSize: 'sm'}" id="s_xfsh"
														name="xfsh">
														<option id="xzxfq" value="">选择销方</option>
														<c:forEach items="${xfList}" var="item">
															<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 5px;">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
												<div class="am-u-sm-8">
													<input id="s_gfmc" type="text" placeholder="购方名称">
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 5px;">
											<div class="am-form-group">
												<label for="s_ddh" class="am-u-sm-4 am-form-label">订单号</label>
												<div class="am-u-sm-8">
													<input id="s_ddh" type="text" placeholder="订单号">
												</div>
											</div>
										</div>

										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="s_fplx" class="am-u-sm-4 am-form-label">发票类型</label>
												<div class="am-u-sm-8">
													<select data-am-selected="{btnSize: 'sm'}" id="s_fplx"
														name="xfsh">
														<option id="xzlxq" value="">选择类型</option>
														<option value="12">电子发票(增普)</option>
														<option value="01">增值税专用发票</option>
														<option value="02">增值税普通发票</option>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="s_ddh" class="am-u-sm-4 am-form-label">开始时间</label>
												<div class="am-input-group am-datepicker-date am-u-sm-8"
													data-am-datepicker="{format: 'yyyy-mm-dd'}">
													<input type="text" id="s_rqq" class="am-form-field"
														placeholder="开始时间" readonly> <span
														class="am-input-group-btn am-datepicker-add-on">
														<button class="am-btn am-btn-default" type="button">
															<span class="am-icon-calendar"></span>
														</button>
													</span>
												</div>
											</div>
										</div>

										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="s_ddh" class="am-u-sm-4 am-form-label">截止时间</label>
												<div class="am-input-group am-datepicker-date am-u-sm-8"
													data-am-datepicker="{format: 'yyyy-mm-dd'}">
													<input type="text" id="s_rqz" class="am-form-field"
														placeholder="截止时间" readonly> <span
														class="am-input-group-btn am-datepicker-add-on">
														<button class="am-btn am-btn-default" type="button">
															<span class="am-icon-calendar"></span>
														</button>
													</span>
												</div>

											</div>
										</div>
										<div style="padding: 32px;">
											<button type="button" id="kp_search1"
												class="am-btn am-btn-default am-btn-success data-back">
												<span></span> 查询
											</button>
										</div>
									</form>
								</div>
							</div>
						</div>

						<div class="am-g  am-padding-top">
							<form action="#"
								class="js-search-form  am-form am-form-horizontal">
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button type="button" id="kpd_kp"
													class="am-btn am-btn-default am-btn-secondary">
													<span></span> 审核开票单
												</button>
												<button type="button" id="kpd_th"
													class="am-btn am-btn-default am-btn-warning">
													<span></span> 退回
												</button>
											</div>
										</div>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
									<div class="am-form-group tpl-table-list-select">
										<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
											<option value="gfmc">购方名称</option>
											<option value="ddh">订单号</option>
										</select>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
									<div
										class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
										<input id="dxcsz" type="text" class="am-form-field ">
										<span class="am-input-group-btn">
											<button id="kp_search"
												class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
												type="button"></button>
										</span>
									</div>
								</div>
							</form>
							<div class="am-u-sm-12 am-padding-top">
								<div>
									<table style="margin-bottom: 0px;"
										class="js-table am-table am-table-bordered am-table-striped am-table-hover am-text-nowrap "
										id="jyls_table">
										<thead>
											<tr>
												<th><input type="checkbox" id="check_all" /></th>
												<th>序号</th>
												<!-- 	<th hidden="true">id</th> -->
												<th>订单号</th>
												<th>订单日期</th>
												<th>分票金额</th>
												<th>发票类型</th>
												<th>购方名称</th>
												<th>购方税号</th>
												<th>购方地址</th>
												<th>购方电话</th>
												<th>购方银行</th>
												<th>购方银行账号</th>
												<th>备注</th>
												<th>价税合计</th>
												<th>操作</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
							<fieldset>
								<legend>商品明细列表</legend>
								<table style="margin-bottom: 0px;"
									class="js-mxtable  am-table am-table-bordered am-table-striped  am-text-nowrap"
									id="mxTable1">
									<thead>
										<tr>
											<th>序号</th>
											<th>名称</th>
											<th>规格型号</th>
											<th>单位</th>
											<th>数量</th>
											<th>单价</th>
											<th>金额</th>
											<th>税率</th>
											<th>税额</th>
											<th>价税合计</th>
											<th>操作</th>
										</tr>
									</thead>
								</table>
							</fieldset>
						</div>

						<!-- content end -->
						<div class="am-modal am-modal-no-btn" tabindex="-1"
							id="my-alert-edit" style="width: 800px;">
							<div class="am-modal-dialog"
								style="overflow: auto; height: 450px;">
								<div class="am-modal-hd am-modal-footer-hd">
									修改开票单 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-alert am-alert-success" data-am-alert
									id="myinfoalert" style="display: none">
									<button type="button" class="am-close">&times</button>
									<p id="infomessage"></p>
								</div>
								<div class="am-tabs am-margin" id="main_tab">
									<form class="am-form am-form-horizontal" id="main_form">
										<fieldset>
											<input type="hidden" name="sqlsh" id="formid">
											<div class="am-form-group">
												<label for="xfid_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>销方名称</label>
												<div class="am-u-sm-4">
													<select id="select_xfid" name="xfsh" required>
														<option value="">---选择销方---</option>
														<c:forEach items="${xfList}" var="item">
															<option value="${item.xfsh}">${item.xfmc}</option>
														</c:forEach>
													</select>
												</div>
												<label for="skpid_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>开票点名称</label>
												<div class="am-u-sm-4">
													<select id="select_skpid" name="skpid" required>

													</select>
												</div>
											</div>
											<div class="am-form-group">
												<label for="fplx_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>销方名称</label>
												<div class="am-u-sm-4">
													<select id="select_fplx" name="fpzldm" onchange="tjbt()"
														required>
														<option value="11">电子票</option>
														<option value="01">纸质专票</option>
														<option value="02">纸质普票</option>
													</select>
												</div>
												<label for="skpid_gfdh" class="am-u-sm-2 am-form-label">购方电话</label>
												<div class="am-u-sm-4">
													<input type="text" id="gfdh_edit" name="gfdh"
														placeholder="输入电话号码...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="ddh_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>订单号</label>

												<div class="am-u-sm-4">
													<input type="text" id="ddh_edit" name="ddh"
														placeholder="输入订单号..." required>
												</div>
												<label for="gfsh_edit" class="am-u-sm-2 am-form-label">购方税号</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfsh_edit" name="gfsh"
														placeholder="输入购方税号...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="gfmc_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>购方名称</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfmc_edit" name="gfmc"
														placeholder="输入购方名称..." required>
												</div>
												<label for="gfyh_edit" class="am-u-sm-2 am-form-label">购方银行</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfyh_edit" name="gfyh"
														placeholder="输入购方银行...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="gfyhzh_edit" class="am-u-sm-2 am-form-label">银行账号</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfyhzh_edit" name="gfyhzh"
														placeholder="输入购方银行账号...">
												</div>
												<label for="gflxr_edit" class="am-u-sm-2 am-form-label">购方联系人</label>

												<div class="am-u-sm-4">
													<input type="text" id="gflxr_edit" name="gflxr"
														placeholder="输入购方联系人...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="gfemail_edit" class="am-u-sm-2 am-form-label">购方邮件</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfemail_edit" name="gfemail"
														placeholder="输入购方邮件地址...">
												</div>
												<label for="gfsjh_edit" class="am-u-sm-2 am-form-label">手机号</label>
												<div class="am-u-sm-4">
													<input type="text" id="gfsjh_edit" name="gfsjh"
														placeholder="输入购方手机号...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="gfdz_edit" class="am-u-sm-2 am-form-label">购方地址</label>

												<div class="am-u-sm-10">
													<input type="text" id="gfdz_edit" name="gfdz"
														placeholder="输入购方地址...">
												</div>
											</div>
											<div class="am-form-group">

												<label for="gfbz_edit" class="am-u-sm-2 am-form-label">备注</label>

												<div class="am-u-sm-10">
													<input type="text" id="bz" name="bz"
														placeholder="输入备注信息...">
												</div>
											</div>
										</fieldset>
									</form>

									<div class="am-margin">
										<button type="button" id="kpd_xgbc"
											class="am-btn am-btn-xs am-btn-secondary">提交保存</button>
										<button type="button" id="close"
											class="am-btn am-btn-danger am-btn-xs">关闭</button>
									</div>
								</div>
							</div>
						</div>

						<div class="am-modal am-modal-no-btn" tabindex="-1"
							id="my-alert-edit1" style="width: 480px;">
							<div class="am-modal-dialog"
								style="overflow: auto; height: 550px;">
								<div class="am-modal-hd am-modal-footer-hd">
									修改商品明细 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-alert am-alert-success" data-am-alert
									id="myinfoalert1" style="display: none">
									<button type="button" class="am-close">&times</button>
									<p id="infomessage"></p>
								</div>
								<form class="am-form am-form-horizontal" id="main_form1">
									<fieldset>
										<input type="hidden" name="id" id="formid1">
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_spmx" class="am-u-sm-4 am-form-label">商品名称</label>
												<div class="am-u-sm-8">
													<input id="mx_spmx" required="required" type="text"
														name="spmc" class="am-form-field" placeholder="商品名称">
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_ggxh" class="am-u-sm-4 am-form-label">规格型号</label>
												<div class="am-u-sm-8">
													<input id="mx_ggxh" type="text" name="spggxh"
														class="am-form-field" placeholder="规格型号">
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_dw" class="am-u-sm-4 am-form-label">商品单位</label>
												<div class="am-u-sm-8">
													<input id="mx_spdw" type="text" name="spdw"
														class="am-form-field" placeholder="商品单位">
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_spsl" class="am-u-sm-4 am-form-label">商品数量</label>
												<div class="am-u-sm-8">
													<input id="mx_spsl" name="sps" onchange="jsje()"
														type="text" class="am-text-money am-form-field"
														placeholder="商品数量">
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_spdj" class="am-u-sm-4 am-form-label">商品单价</label>
												<div class="am-u-sm-8">
													<input id="mx_spdj" name="spdj" onchange="jsje1()"
														type="text" class="am-text-money am-form-field"
														placeholder="商品单价">
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_spje" class="am-u-sm-4 am-form-label">商品金额</label>
												<div class="am-u-sm-8">
													<input id="mx_spje" required="required" name="spje"
														onchange="jsje2()" type="text"
														class="am-text-money am-form-field" placeholder="商品金额">
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_sl" class="am-u-sm-4 am-form-label">商品税率</label>
												<div class="am-u-sm-8">
													<select id="mx_sl" name="spsl" onchange="jsje3()" name="sl">
														<c:forEach items="${smlist}" var="item">
															<option value="${item.sl}">${item.sl}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_spse" class="am-u-sm-4 am-form-label">商品税额</label>
												<div class="am-u-sm-8">
													<input id="mx_spse" required="required" disabled="disabled"
														type="text" class="am-text-money am-form-field"> <input
														id="mx_spse1" name="spse" type="hidden"
														class="am-form-field">
												</div>
											</div>
										</div>
										<div class="am-u-lg-12">
											<div class="am-form-group">
												<label for="mx_jshj" class="am-u-sm-4 am-form-label">价税合计</label>
												<div class="am-u-sm-8">
													<input id="mx_jshj" required="required" name="jshj"
														onchange="jsje4()" type="text"
														class="am-text-money am-form-field" placeholder="商品金额">
												</div>
											</div>
										</div>
									</fieldset>
								</form>

								<div class="am-margin">
									<button type="button" id="kpdmx_xgbc"
										class="am-btn am-btn-xs am-btn-secondary">提交保存</button>
									<button type="button" id="mxclose"
										class="am-btn am-btn-danger am-btn-xs">关闭</button>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
  <div class="am-modal-dialog">
    <div id="conft" class="am-modal-bd">
      你，确定要删除这条记录吗？
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
  <div class="am-modal-dialog">
    <div id="alertt" class="am-modal-bd">
      Hello world！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.form.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/kpdsh.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script>
	function refresh() {
		this.location = this.location;
	}
	$(function() {
		$("#select_xfid").change(
				function() {
		            $('#select_skpid').empty();
				    var xfsh = $(this).val();
				    if (xfsh == null || xfsh == '' || xfsh == "") {
						return;
					}
				    var url = "<%=request.getContextPath()%>/kp/getSkpList";
								$
										.post(
												url,
												{
													xfsh : xfsh
												},
												function(data) {
													if (data) {
														var option = $(
																"<option>")
																.text('请选择')
																.val(-1);
														$('#select_skpid')
																.append(option);
														for (var i = 0; i < data.skps.length; i++) {
															option = $(
																	"<option>")
																	.text(
																			data.skps[i].kpdmc)
																	.val(
																			data.skps[i].id);
															$('#select_skpid')
																	.append(
																			option);
														}
													}
												});
							});

		});

		//je
		function jsje2() {
			var sps = $('#mx_spsl').val();
			var spdj = $('#mx_spdj').val();
			var spje = $('#mx_spje').val();
			var spsl = $('#mx_sl').val();
			var jshj = $('#mx_jshj').val();
			if ("" != spje && null != spje) {
				$('#mx_spse').val(Math.round((spje * spsl * 1) * 100) / 100);
				$('#mx_jshj').val(
						Math.round((spje * 1 + spje * spsl * 1) * 100) / 100);
				if (null != spdj && "" != spdj) {
					$('#mx_spsl').val(
							Math.round((spje * 1 + spje * spsl * 1) * 100)
									/ 100 / (spdj * 1));
				}

			}
		}
		//sl
		function jsje3() {
			var sps = $('#mx_spsl').val();
			var spdj = $('#mx_spdj').val();
			var spje = $('#mx_spje').val();
			var spsl = $('#mx_sl').val();
			var jshj = $('#mx_jshj').val();
			if ("" != jshj && null != jshj) {
				$('#mx_spje').val(
						Math.round((jshj / (1 * 1 + spsl * 1)) * 100) / 100);
				$('#mx_spse').val(
						Math
								.round((jshj * 1 - jshj * 1
										/ (1 * 1 + spsl * 1)) * 100) / 100);
				if (null != spdj && "" != spdj) {
					$('#mx_spsl').val(
							Math.round(jshj * 1 / spdj * 1) * 100 / 100);
				}
			} else if ("" != spje && null != spje) {
				$('#mx_spse').val(Math.round((spje * 1 * spsl) * 100) / 100);
				$('#mx_jshj').val(
						Math.round((spje * 1 + spje * spsl * 1) * 100) / 100);
				if (null != spdj && "" != spdj) {
					$('#mx_spsl').val(
							Math.round((spje * 1 + spje * spsl * 1) * 100)
									/ 100 / (spdj * 1));
				}
			}

		}
		//jshj
		function jsje4() {
			var sps = $('#mx_spsl').val();
			var spdj = $('#mx_spdj').val();
			var spje = $('#mx_spje').val();
			var spsl = $('#mx_sl').val();
			var jshj = $('#mx_jshj').val();
			if ("" != jshj && null != jshj) {
				$('#mx_spje')
						.val(
								Math
										.round((jshj * 1 / (1 * 1 + spsl * 1)) * 100) / 100);
				$('#mx_spse').val(
						Math
								.round((jshj * 1 - jshj * 1
										/ (1 * 1 + spsl * 1)) * 100) / 100);
				if (null != spdj && "" != spdj) {
					$('#mx_spsl').val(
							Math.round((jshj * 1 / spdj * 1) * 100) / 100);
				}
			}
		}
		function tjbt() {
			if ($("#select_fplx").val() == "01") {
				$('#gfdh_edit').attr("required", true);
				$('#gfsh_edit').attr("required", true);
				$('#gfmc_edit').attr("required", true);
				$('#gfyh_edit').attr("required", true);
				$('#gfyhzh_edit').attr("required", true);
				$('#gfdz_edit').attr("required", true);
			} else {
				$('#gfdh_edit').attr("required", false);
				$('#gfsh_edit').attr("required", false);
				$('#gfmc_edit').attr("required", true);
				$('#gfyh_edit').removeAttr("required");
				$('#gfyhzh_edit').attr("required", false);
				$('#gfdz_edit').attr("required", false);
			}
		}

		function tzsl() {
			var spid = $('#mx_spmc').val();
			$.ajax({
				type : "POST",
				url : "kpdsh/hqsl",
				data : {
					"spid" : spid
				},
				success : function(data) {
					$('#mx_sl').val(data.sm.sl);
					$('#mx_spmx').val(data.sp.spmc);
					$('#mx_spje').val(null);
					$('#mx_spse').val(null);
					$('#mx_jshj').val(null);
				}
			});
		}
	</script>

</body>
</html>