<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>开票流水处理</title>
<meta name="description" content="开票流水处理">
<meta name="keywords" content="user">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/autocomplete.css" />
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
.am-table {
	margin-bottom: 0rem;
}

table thead th {
	text-align: center;
}
.data-ctr {
  text-align: center;
}
</style>
</head>
<body>
	<input type="hidden" id="kplsh" value="0">
	<input type="hidden" id="kplsh1" value="0">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="am-tabs" data-am-tabs="{noSwipe: 1}"
						id="doc-tab-demo-1">
						<ul id="btbq" class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tabs1">待处理</a></li>
							<li id="cljgbt"><a href="#cljg">处理结果</a></li>
							<li><a href="#tabs3">已处理</a></li>
						</ul>

						<div id="nrbq" class="am-tabs-bd">
							<div id="tabs1" class="am-tab-panel am-active">
								<div class="admin-content">
									<div class="am-cf widget-head">
										<div class="widget-title am-cf">
											<strong id="yjcd" class="am-text-primary am-text-lg">业务处理</strong>
											/ <strong id="ejcd">开票单审核</strong>
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
															<button type="button" id="kp_add"
																class="am-btn am-btn-default am-btn-primary">
																<span></span>录入
															</button>
															<button type="button" id="kp_dr"
																class="am-btn am-btn-default am-btn-default">
																<span></span>导入
															</button>
															<button type="button" id="kpd_kp"
																class="am-btn am-btn-default am-btn-secondary">
																<span></span> 处理
															</button>
															<!-- 	<button type="button" id="kpd_th"
																class="am-btn am-btn-default am-btn-warning">
																<span></span> 退回
															</button> -->
															<button type="button" id="kpd_sc"
																class="am-btn am-btn-default am-btn-danger">
																<span></span> 删除
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
															<th>操作</th>
															<th>订单号</th>
															<th>订单日期</th>
															<th>数据来源</th>
															<th>分票金额</th>
															<th>是否含税分票</th>
															<th>是否打印清单</th>
															<th>发票类型</th>
															<th>购方名称</th>
															<th>购方税号</th>
															<th>购方地址</th>
															<th>购方电话</th>
															<th>购方银行</th>
															<th>购方银行账号</th>
															<th>备注</th>
															<th class="data-ctr">价税合计</th>
														</tr>
													</thead>
												</table>
												<legend>商品明细列表</legend>
											</div>
										</div>
										<div style="margin-top: 0px; margin-left: 0px;"
											class="am-u-sm-12">
											<table style="margin-bottom: 0px;"
												class="js-mxtable  am-table am-table-bordered am-table-striped  am-text-nowrap"
												id="mxTable1">
												<thead>
													<tr>
														<th>序号</th>
														<th>操作</th>
														<th>名称</th>
														<th>本次开具金额</th>
														<th>可开具金额</th>
														<th>已开具金额</th>
														<th>规格型号</th>
														<th>单位</th>
														<th>数量</th>
														<th>单价</th>
														<th>金额</th>
														<th>税率</th>
														<th>税额</th>
														<th>价税合计</th>
														
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div id="cljg" class="am-tab-panel">
								<legend>处理结果展示列表</legend>
								<table style="margin-bottom: 0px;"
									class="js-mxtable  am-table am-table-bordered am-table-striped  am-text-nowrap"
									id="mxTable3">
									<thead>
										<tr>
											<th>数据条数</th>
											<th>发票序号</th>
											<th>申请流水号</th>
											<th>客户名称</th>
											<th>商品名称</th>
											<th>规格</th>
											<th>商品单位</th>
											<th>商品数</th>
											<th>商品单价</th>
											<th>商品金额</th>
											<th>税率</th>
											<th>商品税额</th>
											<th>价税合计</th>
										</tr>
									</thead>
								</table>

								<button id="yhqrbc" type="button"
									class="am-btn am-btn-primary js-append-tab">确认保存</button>
								<button id="yhqx" type="button"
									class="am-btn am-btn-primary js-append-tab">取消</button>
							</div>
							<div id="tabs3" class="am-tab-panel">
								<div class="am-cf widget-head">
									<div class="widget-title am-cf">
										<strong class="am-text-primary am-text-lg">业务处理</strong> /<strong>开票单审核</strong>
										<button class="am-btn am-btn-success am-fr"
											data-am-offcanvas="{target: '#doc-oc-demo4'}">更多查询</button>
									</div>

									<!-- 侧边栏内容 begin-->
									<div id="doc-oc-demo4" class="am-offcanvas">
										<div class="am-offcanvas-bar am-offcanvas-bar-flip">
											<form id="ycform2">
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_ddh" class="am-u-sm-4 am-form-label">选择销方</label>
														<div class="am-u-sm-8">
															<select data-am-selected="{btnSize: 'sm'}" id="xfsh2"
																name="xfsh">
																<option id="xzxfq2" value="">选择销方</option>
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
															<input id="gfmc2" type="text" placeholder="购方名称">
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content" style="margin-top: 5px;">
													<div class="am-form-group">
														<label for="s_ddh" class="am-u-sm-4 am-form-label">订单号</label>
														<div class="am-u-sm-8">
															<input id="ddh2" type="text" placeholder="订单号">
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content" style="margin-top: 8px;">
													<div class="am-form-group">
														<label for="s_fplx" class="am-u-sm-4 am-form-label">发票类型</label>
														<div class="am-u-sm-8">
															<select data-am-selected="{btnSize: 'sm'}" id="fplxdm2"
																name="xfsh">
																<option id="xzlxq2" value="">选择类型</option>
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
															<input type="text" id="kssj2" class="am-form-field"
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
															<input type="text" id="jssj2" class="am-form-field"
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
													<button type="button" id="kp_search3"
														class="am-btn am-btn-default am-btn-success data-back">
														<span class="am-icon-search-plus"></span> 查询
													</button>
												</div>
											</form>
										</div>
									</div>
									<!-- 侧边内容end -->
								</div>
								<div class="am-g am-padding-top">
									<form class=" am-form am-form-horizontal">
										<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
											<div class="am-form-group">
												<div class="am-btn-toolbar">
													<div class="am-btn-group am-btn-group-xs">

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
												<select id="dxcsm2" data-am-selected="{btnSize: 'sm'}">
													<option value="gfmc2">购方名称</option>
													<option value="ddh2">订单号</option>
												</select>
											</div>
										</div>
										<div class="am-u-sm-12 am-u-md-12 am-u-lg-3 am-u-end">
											<div
												class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
												<input id="dxcsz2" type="text" class="am-form-field ">
												<span class="am-input-group-btn">
													<button id="kp_search2"
														class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
														type="button"></button>
												</span>
											</div>
										</div>
									</form>
									<div class="am-u-sm-12 am-padding-top">
										<table style="margin-bottom: 0px;"
											class="am-table am-table-bordered am-text-nowrap am-table-hover am-table-striped am-scrollable-horizontal"
											id="jyls_table2">
											<thead>
												<tr>
													<th><input type="checkbox" id="check_all2" /></th>
													<th>交易流水号</th>
													<th>订单号</th>
													<th>订单日期</th>
													<th>发票类型</th>
													<th>购方名称</th>
													<th>购方税号</th>
													<th>地址</th>
													<th>电话</th>
													<th>开户行</th>
													<th>开户账号</th>
													<th>价税合计</th>
													<th>已开票价税合计</th>
												</tr>
											</thead>
										</table>
									</div>
										<legend>商品明细列表</legend>
										<table style="margin-bottom: 0px;"
											class=" am-table am-table-bordered am-table-striped am-text-nowrap"
											id="jyspmx_table2">
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
												</tr>
											</thead>
										</table>
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
			<div id="conft" class="am-modal-bd">你，确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div id="alertt" class="am-modal-bd">Hello world！</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-alert-edit">
		<div class="am-modal-dialog" style="overflow: auto; height: 450px;">
			<div class="am-modal-hd am-modal-footer-hd">
				修改开票单 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<div class="am-alert am-alert-success" data-am-alert id="myinfoalert"
				style="display: none">
				<button type="button" class="am-close">&times</button>
				<p id="infomessage"></p>
			</div>
			<div class="am-tabs am-margin" id="main_tab">
				<form class="am-form am-form-horizontal" id="main_form">
					<fieldset>
						<input type="hidden" name="sqlsh" id="formid">
						<div class="am-form-group">
							<label for="select_xfid" class="am-u-sm-2 am-form-label"><span
								style="color: red;">*</span>销方名称</label>
							<div class="am-u-sm-4">
								<select id="select_xfid" name="xfsh" required>
									<option value="">---选择销方---</option>
									<c:forEach items="${xfList}" var="item">
										<option value="${item.xfsh}">${item.xfmc}</option>
									</c:forEach>
								</select>
							</div>
							<label for="select_skpid" class="am-u-sm-2 am-form-label"><span
								style="color: red;">*</span>开票点名称</label>
							<div class="am-u-sm-4">
								<select id="select_skpid" name="skpid" required>

								</select>
							</div>
						</div>
						<div class="am-form-group">
							<label for="select_fplx" class="am-u-sm-2 am-form-label"><span
								style="color: red;">*</span>发票类型</label>
							<div class="am-u-sm-4">
								<select id="select_fplx" name="fpzldm" onchange="tjbt()">
									<option value="12">电子发票(增普)</option>
									<option value="01">增值税专用发票</option>
									<option value="02">增值税普通发票</option>
								</select>
							</div>
							<label for="gfdh_edit" class="am-u-sm-2 am-form-label">购方电话</label>
							<div class="am-u-sm-4">
								<input type="text" id="gfdh_edit" maxlength="50" name="gfdh"
									placeholder="输入电话号码...">
							</div>
						</div>
						<div class="am-form-group">
							<label for="ddh_edit" class="am-u-sm-2 am-form-label"><span
								style="color: red;">*</span>订单号</label>

							<div class="am-u-sm-4">
								<input type="text" id="ddh_edit" name="ddh" maxlength="50"
									placeholder="输入订单号..." required>
							</div>
							<label for="gfsh_edit" class="am-u-sm-2 am-form-label">购方税号</label>

							<div class="am-u-sm-4">
								<input type="text" id="gfsh_edit" name="gfsh" maxlength="20"
									placeholder="购方税号(15,18,20位数)" onkeyup="this.value=this.value.replace(/[, ]/g,'')">
							</div>
						</div>
						<div class="am-form-group">
							<label for="gfmc_edit" class="am-u-sm-2 am-form-label"><span
								style="color: red;">*</span>购方名称</label>

							<div class="am-u-sm-4">
								<input type="text" id="gfmc_edit" name="gfmc" maxlength="100"
									placeholder="输入购方名称..." required>
							</div>
							<label for="gfyh_edit" class="am-u-sm-2 am-form-label">购方银行</label>

							<div class="am-u-sm-4">
								<input type="text" id="gfyh_edit" name="gfyh" maxlength="50"
									placeholder="输入购方银行...">
							</div>
						</div>
						<div class="am-form-group">
							<label for="gfyhzh_edit" class="am-u-sm-2 am-form-label">银行账号</label>

							<div class="am-u-sm-4">
								<input type="text" id="gfyhzh_edit" name="gfyhzh" maxlength="50"
									placeholder="输入购方银行账号...">
							</div>
							<label for="gflxr_edit" class="am-u-sm-2 am-form-label">购方联系人</label>

							<div class="am-u-sm-4">
								<input type="text" id="gflxr_edit" name="gflxr" maxlength="50"
									placeholder="输入购方联系人...">
							</div>
						</div>
						<div class="am-form-group">
							<label for="gfemail_edit" class="am-u-sm-2 am-form-label">购方邮件</label>

							<div class="am-u-sm-4">
								<input type="text" id="gfemail_edit" name="gfemail"
									class="js-pattern-email" maxlength="100"
									placeholder="输入购方邮件地址...">
							</div>
							<label for="gfsjh_edit" class="am-u-sm-2 am-form-label">手机号</label>
							<div class="am-u-sm-4">
								<input type="text" id="gfsjh_edit" name="gfsjh"
									class="js-pattern-Phone" maxlength="50"
									placeholder="输入购方手机号...">
							</div>
						</div>
						<div class="am-form-group">
							<label for="gfdz_edit" class="am-u-sm-2 am-form-label">购方地址</label>

							<div class="am-u-sm-10">
								<input type="text" id="gfdz_edit" name="gfdz" maxlength="200"
									placeholder="输入购方地址...">
							</div>
						</div>
						<div class="am-form-group">

							<label for="bz" class="am-u-sm-2 am-form-label">备注</label>

							<div class="am-u-sm-10">
								<input type="text" id="bz" name="bz" placeholder="输入备注信息..."
									maxlength="200">
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
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-alert-edit1">
		<div class="am-modal-dialog" style="overflow: auto;">
			<div class="am-modal-hd am-modal-footer-hd">
				修改商品明细 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
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
								<input id="mx_spmx" required="required" type="text" name="spmc"
									class="am-form-field" placeholder="商品名称">
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
							<label for="mx_spdw" class="am-u-sm-4 am-form-label">商品单位</label>
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
								<input id="mx_spsl" name="sps" onchange="jsje()" type="text"
									class="js-pattern-Number am-text-right am-form-field"
									placeholder="商品数量">
							</div>
						</div>
					</div>
					<div class="am-u-lg-12">
						<div class="am-form-group">
							<label for="mx_spdj" class="am-u-sm-4 am-form-label">商品单价</label>
							<div class="am-u-sm-8">
								<input id="mx_spdj" name="spdj" onkeyup="jsje1()" type="text"
									class="js-pattern-Money am-text-right am-form-field"
									placeholder="商品单价">
							</div>
						</div>
					</div>
					<div class="am-u-lg-12">
						<div class="am-form-group">
							<label for="mx_spje" class="am-u-sm-4 am-form-label">商品金额</label>
							<div class="am-u-sm-8">
								<input id="mx_spje" required="required" name="spje"
									onkeyup="jsje2()" type="text"
									class="js-pattern-Money am-text-right am-form-field"
									placeholder="商品金额">
							</div>
						</div>
					</div>
					<div class="am-u-lg-12">
						<div class="am-form-group">
							<label for="mx_sl" class="am-u-sm-4 am-form-label">商品税率</label>
							<div class="am-u-sm-8">
								<select id="mx_sl" name="spsl" onkeyup="jsje3()" name="sl">
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
									type="text"
									class="js-pattern-Money am-text-right am-form-field"> <input
									id="mx_spse1" name="spse" type="hidden" class="am-form-field">
							</div>
						</div>
					</div>
					<div class="am-u-lg-12">
						<div class="am-form-group">
							<label for="mx_jshj" class="am-u-sm-4 am-form-label">价税合计</label>
							<div class="am-u-sm-8">
								<input id="mx_jshj" required="required" name="jshj"
									onkeyup="jsje4()" type="text"
									class="js-pattern-Money am-text-right am-form-field"
									placeholder="商品金额">
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
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-alert-edit2">
		<div class="am-modal-dialog" style="overflow: auto">
			<div class="am-modal-hd am-modal-footer-hd">
				开票单录入

			</div>
			<div class="am-alert am-alert-success" data-am-alert id="myinfoalert"
				style="display: none">
				<button type="button" class="am-close">&times</button>
				<p id="infomessage"></p>
			</div>
			<div class="am-tabs am-margin" data-am-tabs="{noSwipe: 1}"
				id="lrmain_tab">
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">基础信息</a></li>
					<li><a href="#tab2" class="ai">商品明细</a></li>
				</ul>

				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
						<form class="am-form am-form-horizontal" id="main_form2">
							<fieldset>
								<input type="hidden" id="formid">
								<div class="am-form-group">
									<label for="lrselect_xfid" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>销方名称</label>
									<div class="am-u-sm-4">
										<select id="lrselect_xfid" name="lrxfid_edit"
											onchange="getKpd()" required>
											<option value="">选择销方</option>
											<c:forEach items="${xfList}" var="item">
												<option value="${item.id}">${item.xfmc}</option>
											</c:forEach>
										</select>
									</div>
									<label for="lrselect_skpid" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>开票点名称</label>
									<div class="am-u-sm-4">
										<select id="lrselect_skpid" name="lrskpid_edit" required>

										</select>
									</div>
								</div>
								<div class="am-form-group">
									<label for="lrfpzl_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>发票种类</label>

									<div class="am-u-sm-4 am-u-end">
										<select id="lrfpzl_edit" name="lrfpzl_edit"
											onchange="hidespan()" required>
											<option value="">选择开票类型</option>
											<option value="01">专用发票</option>
											<option value="02">普通发票</option>
											<option value="12">电子发票</option>
										</select>
									</div>
									<label for="gfmc_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>购方名称</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrgfmc_edit" name="lrgfmc_edit"
											placeholder="输入购方名称..." required>
									</div>

								</div>
								<div class="am-form-group">
									<label for="gfsh_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red; display: none" id="lrspan_gfsh">*</span>购方税号</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrgfsh_edit" name="lrgfsh_edit"
											class="js-pattern-Taxid" placeholder="购方税号(15,18,20位数)" onkeyup="this.value=this.value.replace(/[, ]/g,'')">
									</div>
									<label for="gfyh_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red; display: none" id="lrspan_gfyh">*</span>购方银行</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrgfyh_edit" name="lrgfyh_edit"
											placeholder="输入购方银行...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="lrgfzh_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red; display: none" id="lrspan_gfyhzh">*</span>银行账号</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrgfzh_edit" name="lrgfzh_edit"
											placeholder="输入购方银行账号...">
									</div>
									<label for="gflxr_edit" class="am-u-sm-2 am-form-label">购方联系人</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrgflxr_edit" name="lrgflxr_edit"
											placeholder="输入购方联系人...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="gfemail_edit" class="am-u-sm-2 am-form-label">购方邮件</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrgfemail_edit" name="lrgfemail_edit"
											placeholder="输入购方邮件地址...">
									</div>
									<label for="gfdh_edit" class="am-u-sm-2 am-form-label">购方电话</label>
									<div class="am-u-sm-4">
										<input type="text" id="lrgfdh_edit" name="lrgfdh_edit"
											placeholder="输入购方电话...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="gfdz_edit" class="am-u-sm-2 am-form-label">购方地址</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrgfdz_edit" name="lrgfdz_edit"
											placeholder="输入购方地址...">
									</div>
									<label for="lrtqm_edit" class="am-u-sm-2 am-form-label">提取码</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrtqm_edit" name="lrtqm_edit"
											placeholder="输入提取码...">
									</div>
								</div>
								<div class="am-form-group">

									<label for="ddh_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>订单号</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrddh_edit" name="lrddh_edit"
											placeholder="输入订单号..." required>
									</div>
									<label for="gfsjh_edit" class="am-u-sm-2 am-form-label">手机号</label>
									<div class="am-u-sm-4">
										<input type="text" id="lrgfsjh_edit" name="lrgfsjh_edit"
											placeholder="输入购方手机号...">
									</div>
								</div>
								<div class="am-form-group">

									<label for="lrgfbz_edit" class="am-u-sm-2 am-form-label">备注</label>

									<div class="am-u-sm-10">
										<input type="text" id="lrgfbz_edit" name="lrgfbz_edit"
											placeholder="输入备注信息...">
									</div>
								</div>
							</fieldset>
						</form>
					</div>
					<div class="am-tab-panel am-fade" id="tab2">
						<form class="am-form am-form-horizontal" id="lrmx_form">
							<fieldset>
								<div class="am-form-group">
									<label for="lrselect_sp" class="am-u-sm-2 am-form-label">选择商品</label>

									<div class="am-u-sm-4">
										<select id="lrselect_sp" name="lrselect_sp">
											<option value="">选择商品</option>
											<c:forEach items="${spList}" var="item">
												<option value="${item.id}" class="${item.id}">${item.spmc}(${item.sl})</option>
											</c:forEach>
										</select>
									</div>

									<label for="lrspdm_edit" class="am-u-sm-2 am-form-label">商品分类编码</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrspdm_edit" placeholder="输入商品分类编码..."
											readonly="readonly">
									</div>
								</div>
								<div class="am-form-group">
									<label for="lrmc_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>名称</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrmc_edit" placeholder="输入名称..."
											required>
									</div>
									<label for="lrggxh_edit" class="am-u-sm-2 am-form-label">规格型号</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrggxh_edit" placeholder="输入规格型号...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="lrdw_edit"   class="am-u-sm-2 am-form-label">单位</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrdw_edit" placeholder="输入单位...">
									</div>
									<label for="lrsl_edit" class="am-u-sm-2 am-form-label">数量</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrsl_edit" oninput="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="输入数量...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="lrdj_edit" class="am-u-sm-2 am-form-label">单价(含税)</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrdj_edit" oninput="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="输入单价...">
									</div>
									<label for="lrje_edit" class="am-u-sm-2 am-form-label"
										style="padding-left: 0px"><span style="color: red;">*</span>金额(不含税)</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrje_edit" readonly="readonly" placeholder="输入金额(不含税)..."
											required>
									</div>
								</div>
								<label for="lrhsje_edit" class="am-u-sm-2 am-form-label"><span
									style="color: red;">*</span>金额(含税)</label>

								<div class="am-u-sm-4">
									<input type="text" id="lrhsje_edit" oninput="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="输入金额(含税)..."
										required>
								</div>
								<div class="am-form-group">
									<label for="lrjshj_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>价税合计</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrjshj_edit" readonly="readonly"
											placeholder="输入价税合计...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="lrsltaxrate_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>税率</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrsltaxrate_edit" value="0.17"
											placeholder="" required readonly>
									</div>
									<label for="lrse_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>税额</label>

									<div class="am-u-sm-4">
										<input type="text" id="lrse_edit" placeholder="" required
											readonly>
									</div>
								</div>
								<div>
									<button type="button" id="addRow"
										class="am-btn am-btn-xs am-btn-secondary">增加</button>
								</div>
							</fieldset>
						</form>
						<table
							class="am-text-nowrap am-table-striped am-table-bordered am-table-compact table-main am-scrollable-horizontal"
							id="jyspmx_edit_table">
							<thead>
								<tr>
									<th>序号</th>
									<th>商品代码</th>
									<th>名称</th>
									<th>规格型号</th>
									<th>单位</th>
									<th>数量</th>
									<th>单价</th>
									<th>金额</th>
									<th>税率</th>
									<th>税额</th>
									<th>价税合计</th>
									<th>删除</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>

				<div class="am-margin">
					<button type="button" id="lrsave"
						class="am-btn am-btn-xs am-btn-secondary">保存</button>
					<button type="button" id="lrclose"
						class="am-btn am-btn-danger am-btn-xs">关闭</button>
				</div>

			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1"
		id="bulk-import-div">
		<div class="am-modal-dialog">
			<div class="am-modal-hd am-modal-footer-hd">
				批量导入
				<!--  <a href="javascript: void(0)" class="am-close am-close-spin"
								data-am-modal-close>&times;</a> -->
			</div>

			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal" id="importExcelForm"
					method="post"
					action="<%=request.getContextPath()%>/lrkpd/importExcel"
					enctype="multipart/form-data">
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<input type="file" class="am-u-sm-12" id="importFile"
								name="importFile" placeholder="选择要上传的文件"
								accept="application/vnd.ms-excel" required>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label">选择销方</label>
							<div class="am-u-sm-8">
								<select id="mb_xfsh" name="mb_xfsh" class="am-u-sm-12">
									<c:if test="${xfSum > 1}">
										<option value="">请选择</option>
										<c:forEach items="${xfList}" var="item">
											<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</c:if>
									<c:if test="${xfSum == 1}">
										<c:forEach items="${xfList}" var="item">
											<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label">选择开票点</label>
							<div class="am-u-sm-8">
								<select id="mb_skp" name="mb_skp" class="am-u-sm-12">
									<c:if test="${skpSum == 1 && xfSum == 1 }">
										<c:forEach items="${skps}" var="item">
											<option value="${item.id}">${item.kpdmc}</option>
										</c:forEach>
									</c:if>
									<c:if test="${skpSum > 1 || xfSum > 1}">
										<option value="">请选择</option>
										<c:forEach items="${skps}" var="item">
											<option value="${item.id}">${item.kpdmc}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label">选择模板</label>
							<div class="am-u-sm-8">
								<select id="mb" name="mb" class="am-u-sm-12">
									<c:if test="${mbSum == 1 && xfSum == 1 }">
										<c:forEach items="${mbList}" var="item">
											<option value="${item.id}">${item.mbmc}</option>
										</c:forEach>
									</c:if>
									<c:if test="${mbSum > 1 || xfSum > 1}">
										<option value="">请选择</option>
										<c:forEach items="${mbList}" var="item">
											<option value="${item.id}">${item.mbmc}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12" style="margin-top: 30px;">
							<button type="button" id="btnImport"
								class="am-btn am-btn-xs am-btn-primary">导入</button>
							<button type="button" id="close1"
								class="am-btn am-btn-danger am-btn-xs">关闭</button>
						</div>
						<div class="am-u-sm-12" style="margin-top: 10px;">
							<a href="javascript:void(0)" id="btnDownloadDefaultTemplate"
								style="text-decoration: underline;">下载模板</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.form.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/kpdsh.js"></script>
	<script src="assets/js/kpdys.js"></script>
	<script src="assets/js/autocomplete.js"></script>
	<script src="assets/js/getGfxxInput.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#cljg").hide();
			$("#cljgbt").hide();
			var tabCounter = 0;
			var $tab = $('#doc-tab-demo-1');
			tabCounter++;
			$tab.tabs('refresh');
		});
	</script>
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
		
		 //录入订单时选择商品
        $("#lrselect_sp").change(function () {
            var je = $('#lrje_edit');
            var sl = $('#lrsltaxrate_edit');
            var se = $('#lrse_edit');
            var hsje = $('#lrhsje_edit');
            var jshj = $('#lrjshj_edit');
            var dj = $('#lrdj_edit');
            var sps = $('#lrsl_edit');
            var spsl;
            var spid = $(this).val();
            var spmc = $("#lrselect_sp option:checked").text();
            var pos = spmc.indexOf("(");
            spmc = spmc.substring(0, pos);
            if (!spid) {
                $("#lrmx_form input").val("");
                return;
            }
            var ur = "<%=request.getContextPath()%>/lrkpd/getSpxq";
            $.ajax({
                url: ur,
                type: "post",
                async:false,
                data: {
                    spid: spid

                }, 
                success: function (res) {
                	 if (res) {
                         $("#lrmx_form #lrspdm_edit").val(res["spbm"]);
                         $("#lrmx_form #lrmc_edit").val(res["spmc"]);
                         $("#lrmx_form #lrggxh_edit").val(res["spggxh"] == null ? "" : res["spggxh"]);
                         $("#lrmx_form #lrdw_edit").val(res["spdw"] == null ? "" : res["spdw"]);
                         $("#lrmx_form #lrdj_edit").val(res["spdj"] == null ? "" : res["spdj"]);
                         $("#lrmx_form #lrsltaxrate_edit").val(res["sl"]);
                         spsl = res["sl"];
                     }
                }
            })
            if(null!=je && je.val() !=""){
            	var temp = (100+sl.val()*100)/100;
				se.val(FormatFloat(je.val() * spsl, "#####0.00"));
				var je1 = parseFloat(je.val());
        		var se1 = parseFloat(se.val());
				hsje.val(FormatFloat(je1 + se1, "#####0.00"));
				jshj.val(FormatFloat(je1 + se1, "#####0.00"));
        		if (dj != null && dj.val() != "") {
        			sps.val(FormatFloat(je.val() / dj.val(), "#####0.00"));
				}else if(sps != null && sps.val() != ""){
					dj.val(FormatFloat(je.val() / sps.val(), "#####0.00"));
				}
            }
        });
        $("#btnDownloadDefaultTemplate").click(function () {
        	var mbid = $('#mb').val();
        	if(null==mbid||""==mbid||"-1"==mbid){
            	swal("请选择模板后下载");
        	}else{
    			$.ajax({
    				type : "POST",
    				url : "kpdsh/bmlj",
    				data : {"mbid":mbid},
    				success : function(data) {
						if(data.drmb.xzlj!=null&&data.drmb.xzlj!=""){
						  window.location.href='lrkpd/downloadDefaultImportTemplate?xzlj='+data.drmb.xzlj;
						}else{
							window.location.href='kpdsh/xzmb?mbid='+mbid;
						}
    				}
    			});
        	}
    });
        //导入excel
        $("#btnImport").click(function () {
            var filename = $("#importFile").val();
            var xfsh = $("#mb_xfsh").val();
            var mb = $("#mb").val();
            var skpid = $("#mb_skp").val();
            if (!xfsh) {
                swal("请选择要导入的销方");
                return;
            }
            if (skpid==-1) {
                swal("请选择要导入的开票点");
                return;
            }
            if (mb==-1) {
                swal("请选择要导入的模板或设置默认模板,如无模板请添加模板后再导入");
                return;
            }
            if (!filename) {
                swal("请选择要导入的文件");
                return;
            }
            var pos = filename.lastIndexOf(".");
            if (pos == -1) {
                swal("导入的文件必须是excel文件");
                return;
            }
            var extName = filename.substring(pos + 1);
            if ("xls" != extName && "xlsx" != extName) {
                swal("导入的文件必须是excel文件");
                return;
            }
            $("#btnImport").attr("disabled", true);
            $('.js-modal-loading').modal('open');
            var options = {
                success: function (res) {
                    if (res["success"]) {
                        $("#btnImport").attr("disabled", false);
                        $('.js-modal-loading').modal('close');
                        var count = res["count"];
                        swal({
                            title: "导入成功，共导入" + count + "条数据",
                            showCancelButton: false,
                            closeOnConfirm: false,
                            confirmButtonText: "确 定",
                            confirmButtonColor: "#ec6c62"
                        }, function() {
                            window.location.reload();
                        });
                        if (res["yes"]) {
                			$('#mrmb').empty();
                			var txt = $('#mb').find("option:selected").text();
        					var option = $("<option>").text(txt).val(mbid);
                        	$('#mrmb').append(option);
						}
                    } else {
                        $("#btnImport").attr("disabled", false);
                        $('.js-modal-loading').modal('close');
                        swal(res["message"]);
                    }
                }
            };
            $("#importExcelForm").ajaxSubmit(options);
        });
        //导入选择销方模板
        $("#mb_xfsh").change(function () {
            var xfsh = $(this).val();
            $('#mb').empty();
            $('#mb_skp').empty();
            //$('#mrmb').empty();
            if (xfsh == null || xfsh == '' || xfsh == "") {
				return;
			}
            var url = "<%=request.getContextPath()%>/lrkpd/getSkpList";
            $.post(url, {xfsh: xfsh}, function (data) {
                if (data) {
                	var option = $("<option>").text('请选择').val(-1);
                	$('#mb_skp').append(option);
                    for (var i = 0; i < data.skps.length; i++) {
                    	option = $("<option>").text(data.skps[i].kpdmc).val(data.skps[i].id);
                    	$('#mb_skp').append(option);
					}
                }
            });
            url = "<%=request.getContextPath()%>/lrkpd/getTemplate";
            $.post(url, {xfsh: xfsh}, function (data) {
                if (data) {
                	var option = $("<option>").text('请选择').val(-1);
                	$('#mb').append(option);
                    for (var i = 0; i < data.mbs.length; i++) {
                    	option = $("<option>").text(data.mbs[i].mbmc).val(data.mbs[i].id);
                    	$('#mb').append(option);
					}
                }
            });
            /*url = "<%=request.getContextPath()%>/lrkpd/getMrmb";
								$.post(url, {
									xfsh : xfsh
								}, function(data) {
									if (data) {
										var option = $("<option>").text(
												data.mrmb.mbmc).val(
												data.mrmb.id);
										$('#mrmb').append(option);
									}
								});*/
			});
		    $("#lrsl_edit").keyup(function(){
                var spsl = $('#lrsl_edit');//商品数量
                // var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
                // if (!num.test(spsl.val())) {
                //     if (spsl.val().length > 1) {
                //         $('#lrsl_edit').val(
                //             spsl.val().substring(0,
                //                 spsl.val().length - 1))
                //     } else {
                //         $('#lrsl_edit').val("")
                //     }
                //     return;
                // }
                var sl = $('#lrsltaxrate_edit');
                var se = $('#lrse_edit');
                var hsje = $('#lrhsje_edit');
                var jshj = $('#lrjshj_edit');
                var dj = $('#lrdj_edit');
                var je = $('#lrje_edit');
                var temp = (100 + sl.val() * 100) / 100;
                if(dj!=""){
                    jshj.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));
                    hsje.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));
                    var jj=spsl.val() * dj.val();
                    je.val(FormatFloat(jj/temp, "#####0.00"));
                    se.val(FormatFloat(je.val() * sl.val(),
                        "#####0.00"));
                }
            });
            $("#lrdj_edit").keyup(function(){
                var dj = $('#lrdj_edit');//单价
                // var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
                // if (!num.test(dj.val())) {
                //     if (dj.val().length > 1) {
                //         $('#dj_edit').val(
                //             dj.val().substring(0,
                //                 dj.val().length - 1))
                //     } else {
                //         $('#dj_edit').val("")
                //     }
                //     return;
                // }



                var sl = $('#lrsltaxrate_edit');
                var se = $('#lrse_edit');
                var hsje = $('#lrhsje_edit');
                var jshj = $('#lrjshj_edit');
                var spsl = $('#lrsl_edit');
                var je = $('#lrje_edit');
                var temp = (100 + sl.val() * 100) / 100;
                if(spsl!=""){
                    jshj.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));
                    hsje.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));

                    var jj=spsl.val() * dj.val();
                    je.val(FormatFloat(jj/temp, "#####0.00"));
                    se.val(FormatFloat(je.val() * sl.val(),
                        "#####0.00"));
                }
            });

			 $("#lrje_edit").keyup(
			 	function() {
			 		var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			 		var je = $('#lrje_edit');
			 		if (!num.test(je.val())) {
			 			if (je.val().length > 1) {
			 				$('#lrje_edit').val(
			 						je.val().substring(0,
			 								je.val().length - 1))
			 			} else {
			 				$('#lrje_edit').val("")
			 			}
			 			return;
			 		}
			 		var sl = $('#lrsltaxrate_edit');
			 		var se = $('#lrse_edit');
			 		var hsje = $('#lrhsje_edit');
			 		var jshj = $('#lrjshj_edit');
			 		var dj = $('#lrdj_edit');
			 		var sps = $('#lrsl_edit');
			 		var temp = (100 + sl.val() * 100) / 100;
			 		se.val(FormatFloat(je.val() * sl.val(),
			 				"#####0.00"));
			 		var je1 = parseFloat(je.val());
			 		var se1 = parseFloat(se.val());
			 		hsje.val(FormatFloat(je1 + se1, "#####0.00"));
			 		jshj.val(FormatFloat(je1 + se1, "#####0.00"));
			 		if (dj != null && dj.val() != "") {
			 			sps.val(FormatFloat(je.val() / dj.val(),
			 					"#####0.00"));
			 		} else if (sps != null && sps.val() != "") {
			 			dj.val(FormatFloat(je.val() / sps.val(),
			 					"#####0.00"));
			 		}
			 	}
			 );



			$("#lrhsje_edit").keyup(
				function() {
					// var num = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
					var hsje = $('#lrhsje_edit');
					// if (!num.test(hsje.val())) {
					// 	if (hsje.val().length > 1) {
					// 		$('#lrhsje_edit').val(
					// 				hsje.val().substring(0,
					// 						hsje.val().length - 1))
					// 	} else {
					// 		$('#lrhsje_edit').val("")
					// 	}
					// 	return;
					// }
					var je = $('#lrje_edit');
					var sl = $('#lrsltaxrate_edit');
					var se = $('#lrse_edit');
					var spsl = $('#lrsl_edit');
					var jshj = $('#lrjshj_edit');
					var dj = $('#lrdj_edit');
					var sps = $('#lrsl_edit');
					var temp = (100 + sl.val() * 100) / 100;
					je.val(FormatFloat(hsje.val() / (temp),"#####0.00"));

					se.val(FormatFloat(hsje.val() - je.val(),"#####0.00"));

					jshj.val(FormatFloat(hsje.val(), "#####0.00"));

					// spsl.val(FormatFloat(hsje.val() / dj.val(),"#####0.00"))
					if (dj.val()!= "") {
			            spsl.val(FormatFloat(hsje.val() / dj.val(), "#.00#############"));
			        }else if(dj.val()==""&&spsl.val()!=""){
			            dj.val(FormatFloat(hsje.val() / spsl.val(), "#.00#############"));
			        }
				}
			);

		});

		//je
		function jsje2() {
			var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			var spje = $('#mx_spje').val();
			if (!num.test(spje)) {
				if (spje.length > 1) {
					$('#mx_spje').val(spje.substring(0, spje.length - 1))
				} else {
					$('#mx_spje').val("")
				}
				return;
			}
			var sps = $('#mx_spsl').val();
			var spdj = $('#mx_spdj').val();
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
			var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			var jshj = $('#mx_jshj').val();
			if (!num.test(jshj)) {
				if (jshj.length > 1) {
					$('#mx_jshj').val(jshj.substring(0, jshj.length - 1))
				} else {
					$('#mx_jshj').val("")
				}
				return;
			}
			var sps = $('#mx_spsl').val();
			var spdj = $('#mx_spdj').val();
			var spje = $('#mx_spje').val();
			var spsl = $('#mx_sl').val();
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
		//选择销方取得税控盘
		function getKpd() {
			var xfid = $('#lrselect_xfid option:selected').val();
			//alert(xfid);
			var skpid = $("#lrselect_skpid");
			$("#lrselect_skpid").empty();
			$.ajax({
				url : "fpkc/getKpd",
				data : {
					"xfid" : xfid
				},
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						var option = $("<option>").text(data[i].kpdmc).val(
								data[i].skpid);
						skpid.append(option);
					}
				}

			});
		};
		function hidespan(){
			var fpzldm = $("#lrfpzl_edit").val();
				if(fpzldm=='01'){
					//$("#span_gfsh").style.display="";
					document.getElementById("lrspan_gfsh").style.display="";
					//document.getElementById("span_gfdz").style.display="";
					//document.getElementById("span_gfdh").style.display="";
					document.getElementById("lrspan_gfyh").style.display="";
					document.getElementById("lrspan_gfyhzh").style.display="";
					//document.getElementById("gfmc_edit").setAttribute("required",true);
					$("#lrgfsh_edit").attr("required",true);
					//$("#gfdz_edit").attr("required",true);
					//$("#gfdh_edit").attr("required",true);
					$("#lrgfyh_edit").attr("required",true);
					$("#lrgfzh_edit").attr("required",true);
				 }else{
					document.getElementById("lrspan_gfsh").style.display="none";
					//document.getElementById("span_gfdz").style.display="none";
					//document.getElementById("span_gfdh").style.display="none";
					document.getElementById("lrspan_gfyh").style.display="none";
					document.getElementById("lrspan_gfyhzh").style.display="none";
					$("#lrgfsh_edit").attr("required",false);
					//$("#gfdz_edit").attr("required",false);
					//$("#gfdh_edit").attr("required",false);
					$("#lrgfyh_edit").attr("required",false);
					$("#lrgfzh_edit").attr("required",false);
				 }
			}
	</script>

</body>
</html>