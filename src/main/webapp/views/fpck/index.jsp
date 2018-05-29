<%@ page  contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票重开</title>
<meta name="description" content="发票重开" />
<meta name="keywords" content="发票重开" />
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
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
.am-form-horizontal .am-form-label {
	text-align: left;
}
.data-ctr {
  text-align: center;
}
</style>
</head>
<body>
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="am-cf admin-main widget">
<%--
					<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
--%>
						<%--<ul class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tab1">发票异常重开</a></li>
						</ul>--%>

					<%--	<div class="am-tabs-bd">
							<div class="am-tab-panel am-fade am-in&lt;%&ndash; am-active&ndash;%&gt;" &lt;%&ndash;id="tab1"&ndash;%&gt;>
							--%>	<div class="admin-content">
									<input type="hidden" id="bj">
									<div class="am-cf widget-head">
										<div class="widget-title am-cf">
											<strong id="yjcd" class="am-text-primary am-text-lg" style="color: #838FA1;"></strong> / <strong id="ejcd" style="color: #0e90d2;"></strong>

										</div>
									</div>
									<div id="doc-oc-demo3" class="am-offcanvas">
										<form action="<c:url value='fpcx/exportExcel'/>"
											  id="searchform1">
											<div class="am-offcanvas-bar am-offcanvas-bar-flip">
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">订单号</label>
														<div class="am-u-sm-7">
															<input type="text" id="s_ddh" name="ddh"
																   placeholder="请输入订单号" />
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">购方名称</label>
														<div class="am-u-sm-7">
															<input type="text" id="s_gfmc" name="gfmc"
																   placeholder="请输入购方名称" />
														</div>
													</div>
												</div>
												<%--<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">商品名称</label>
														<div class="am-u-sm-7">
															<input type="text" id="s_spmc" name="spmc"
																   placeholder="请输入商品名称" />
														</div>
													</div>
												</div>--%>
												<%--<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
														<div class="am-u-sm-7">
															<input type="text" id="s_fpdm" name="fpdm"
																   placeholder="请输入发票代码" />
														</div>
													</div>
												</div>--%>
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票号码</label>
														<div class="am-u-sm-7">
															<input type="text" id="s_fphm" name="fphm"
																   placeholder="请输入发票号码" />
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票类型</label>
														<div class="am-u-sm-7">
															<select id="s_fpzldm" name="fpzldm" data-am-selected="{btnSize: 'sm'}">
																<option value="">----请选择----</option>
																<option value="01">增值税专用发票</option>
																<option value="02">增值税普通发票</option>
																<option value="12">电子发票(增普)</option>
															</select>
														</div>
													</div>
												</div>
												<%--<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">操作类型</label>
														<div class="am-u-sm-7">
															<select id="s_fpczlx" name="fpczlx"
																	data-am-selected="{btnSize: 'sm'}">
																<option value="">----请选择----</option>
																<option value="11">正常开具</option>
																<option value="12">红冲开具</option>
																&lt;%&ndash;<option value="13">纸质发票换开</option>&ndash;%&gt;
															</select>
														</div>
													</div>
												</div>--%>
												<%--<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票状态</label>
														<div class="am-u-sm-7">
															<select id="s_fpzt" name="fpzt"
																	data-am-selected="{btnSize: 'sm'}">
																<option value="">------请选择------</option>
																<c:forEach items="${ztList}" var="zt">
																	<option value="${zt.fpztdm}">${zt.fpztmc}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>--%>
												<%--<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">打印状态</label>
														<div class="am-u-sm-7">
															<select id="s_dyzt" name="printflag"
																	data-am-selected="{btnSize: 'sm'}">
																<option value="">------请选择------</option>
																<option value="0">未打印</option>
																<option value="1">已打印</option>
															</select>
														</div>
													</div>
												</div>--%>
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">销方</label>
														<div class="am-u-sm-7">
															<select id="mb_xfsh" name="mb_xfsh" class="am-u-sm-7"
																	data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}">
																<option value="-1">请选择</option>
																<c:forEach items="${xfList}" var="item">
																	<option value="${item.xfsh}">${item.xfmc}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">开票点</label>
														<div class="am-u-sm-7">
															<select id="mb_skp" name="mb_skp" class="am-u-sm-7"
																	data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}">

															</select>
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_kprqq" class="am-u-sm-4 am-form-label">订单日期</label>
														<div class="am-u-sm-7">
															<input type="text" id="s_kprqq" name="kprqq"
																   placeholder="点击选择开票起始日"
																   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content">
													<div class="am-form-group">
														<!-- 											<label for="s_kprqz" class="am-u-sm-4 am-form-label">开票日期止</label> -->
														<label for="s_kprqq" class="am-u-sm-4 am-form-label"></label>
														<div class="am-u-sm-7">
															<input type="text" id="s_kprqz" name="kprqz"
																   placeholder="点击选择开票终止日"
																   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content" style="padding: 32px;">
													<button id="button3" type="button"
															class="am-btn am-btn-default am-btn-secondary data-back">查询</button>
												</div>
											</div>
										</form>
									</div>
	<div class="am-g" style="margin-top: 20px">
		<form action="<c:url value='fpcx/exportExcel'/>"
			  id="searchform"
			  class="js-search-form  am-form am-form-horizontal">
			<input type="hidden" name="kplsh1" value="" id="kplsh1"/>
			<div class="am-u-sm-5">
				<div class="am-form-group">
					<div class="am-u-sm-3">
						订单日期
					</div>
					<div class="am-u-sm-4">
						<input type="text" id="w_kprqq" name="w_kprqq"
							   placeholder="开票起始日期"
							   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
					</div>
					<div class="am-u-sm-1">
						至
					</div>
					<div class="am-u-sm-4">
						<input type="text" id="w_kprqz" name="w_kprqz"
							   placeholder="开票截止日期"
							   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
					</div>
				</div>
			</div>
			<div class="am-u-sm-2">
				<select id="tip" name="tip"
						data-am-selected="{btnSize: 'sm'}">
					<option value="0">请选择</option>
					<option value="2" selected="selected">订单号</option>
					<%--<option value="3">商品名称</option>--%>
					<option value="5">发票号码</option>
					<option value="1" >购方名称</option>
					<%--<option value="6">发票代码</option>--%>
				</select>
			</div>
			<div class="am-u-sm-2">
				<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
					<input type="text" id="searchtxt" name="txt" class="am-form-field ">
					<span class="am-input-group-btn" id="button1">
					    <button class="am-btn js-search am-btn-default am-btn-secondary tpl-table-list-field am-icon-search" type="button"></button>
					</span>
				</div>
			</div>
			<button class="am-btn am-btn-secondary am-fr"
					data-am-offcanvas="{target: '#doc-oc-demo3'}" style="float: right">更多查询</button>
		</form>
	</div>
									<div class="am-g  am-padding-top">

											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<div class="am-form-group">
													<div class="am-btn-toolbar">
														<div class="am-btn-group am-btn-group-xs">
															<button id="fpck" type="button" class="js-fpck  am-btn am-btn-secondary">重新开具</button>
														</div>
													</div>
												</div>
											</div>


										<div class="am-u-sm-12 am-padding-top">
											<div>
												<table id="kpls_table"
													   class="js-table  am-table am-table-bordered am-table-hover am-table-striped am-text-nowrap">
													<thead>
													<tr id="bt">
														<th><input type="checkbox" id="select_all" /></th>
														<th>序号</th>
														<th>操作</th>
														<th>订单号</th>
														<th>操作类型</th>
														<th>发票代码</th>
														<th>发票号码</th>
														<th>发票类型</th>
														<th>价稅合计</th>
														<th>购方名称</th>
														<th>开票日期</th>
														<th>发票状态</th>
														<th>开票信息</th>
													</tr>
													</thead>
												</table>
											</div>
										</div>
									</div>
								</div>
						<%--	</div>
						</div>--%>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal"
		 title="发票信息修改">
		<div class="am-modal-dialog" style="">
			<div class="am-modal-hd">
				发票信息修改 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
			</div>

			<hr />
			<form action="fpck/fpckSave"  method="get" autocomplete="off" class="js-form am-form am-form-horizontal">
				<div class="am-g" style="padding: 20px;">
					<div class="am-form-group">
						<div class="am-g">
							<div class="am-u-md-6">
								<label for="fpzldm_ck" class="am-u-sm-3 am-form-label">发票类型</label>
								<div class="am-u-sm-7">
									<select id="fpzldm_ck" name="fpzldm_ck">
										<option value="">----请选择----</option>
										<option value="01">增值税专用发票</option>
										<option value="02">增值税普通发票</option>
										<option value="12">电子发票(增普)</option>
									</select>
								</div>
							</div>
							<div class="am-u-md-6">
								<label for="sfbx" class="am-u-sm-3 am-form-label data-cte"><span class="star"></span>用于报销</label>
								<div class="am-u-sm-3" >
									<input id="sfbx" name="sfbx" type="checkbox"  onclick="sf();"   checked />
									<input id="fpcklx" name="fpcklx" type="hidden" value="01" />
									<input id="kplshck" name="kplshck" type="hidden"  />
								</div>
							</div>
						</div>
						<div class="am-g">
							<div class="am-u-md-6">
								<label for="gfmcck" class="am-u-sm-3 am-form-label data-cte"><span class="star" style="color:red;">*</span>购方名称</label>
								<div class="am-u-sm-7" >
									<input id="gfmcck" name="gfmcck" type="text" value="" placeholder="请输入购方名称" required>
								</div>
							</div>
							<div class="am-u-md-6">
								<label for="gfshck" class="am-u-sm-3 am-form-label data-cte"><span class="star" id="show" style="color:red;">*</span>购方税号</label>
								<div class="am-u-sm-7">
									<input id="gfshck" name="gfshck" type="text" placeholder="请输入购方税号">
								</div>
							</div>
						</div>
						<div class="am-g">
							<div class="am-u-md-6">
								<label for="gfemailck" class="am-u-sm-3 am-form-label data-cte">购方邮箱</label>
								<div class="am-u-sm-7">
									<input id="gfemailck" name="gfemailck" type="text" placeholder="请输入邮箱" >
								</div>
							</div>
							<div class="am-u-md-6">
								<label for="gfdzck" class="am-u-sm-3 am-form-label data-cte">地址</label>
								<div class="am-u-sm-7">
									<input id="gfdzck" name="gfdzck" type="text" placeholder="请输入地址">
								</div>
							</div>
						</div>
						<div class="am-g">
							<div class="am-u-md-6">
								<label for="gfdhck" class="am-u-sm-3 am-form-label data-cte">电话</label>
								<div class="am-u-sm-7">
									<input id="gfdhck" name="gfdhck" type="text" placeholder="请输入电话号码">
								</div>
							</div>
							<div class="am-u-md-6">
								<label for="gfyhck" class="am-u-sm-3 am-form-label data-cte">开户行</label>
								<div class="am-u-sm-7">
									<input id="gfyhck" name="gfyhck" type="text" placeholder="请输入开户行">
								</div>
							</div>
						</div>
						<div class="am-g">
						<div class="am-u-md-6">
							<label for="gfyhzhck" class="am-u-sm-3 am-form-label data-cte">银行账号</label>
							<div class="am-u-sm-7">
								<input id="gfyhzhck" name="gfyhzhck" type="text" placeholder="请输入银行账号">
							</div>
						</div>
						<div class="am-u-md-6">
							<label for="bzck" class="am-u-sm-3 am-form-label data-cte">备注</label>
							<div class="am-u-sm-7">
								<input id="bzck" name="bzck" type="text" placeholder="请输入备注信息">
							</div>
						</div>
					</div>

						<div class="am-u-sm-12 am-margin-top-lg">
							<div class="am-form-group">
								<div class="am-u-sm-12  am-text-center">
									<button type="button" id="button2" class="am-btn am-btn-default am-btn-secondary">重新开具</button>
									<button type="button" class="js-close am-btn am-btn-default am-btn-secondary">取消</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
<%--
					</div>
--%>
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
				<div class="am-modal" tabindex="-1" id="biaoti">
					<div class="am-modal-dialog">
						<form id="biao" class="js-form-0 am-form am-form-horizontal">

							<div class="am-modal-hd">
								自定义列显示<a href="javascript: void(0)"
										 class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>
							<div class="am-modal-bd" style="overflow: auto; max-height: 400px;">
								<hr />
								<div class="am-u-sm-12">
									<div class="am-form-group">
										<label class="am-u-sm-4 am-form-label"><input
												disabled="disabled" checked="checked" type="checkbox"
												value="" />订单号</label> <label class="am-u-sm-4 am-form-label"><input
											disabled="disabled" checked="checked" type="checkbox"
											value="" />操作类型 </label> <label class="am-u-sm-4 am-form-label"><input
											disabled="disabled" checked="checked" type="checkbox"
											value="" />发票代码</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												disabled="disabled" checked="checked" type="checkbox"
												value="" />发票号码 </label> <label class="am-u-sm-4 am-form-label"><input
											disabled="disabled" checked="checked" type="checkbox"
											value="" />价税合计 </label> <label class="am-u-sm-4 am-form-label"><input
											disabled="disabled" checked="checked" type="checkbox"
											value="" />购方名称</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												disabled="disabled" checked="checked" type="checkbox"
												value="" />开票日期 </label> <label class="am-u-sm-4 am-form-label"><input
											disabled="disabled" checked="checked" type="checkbox"
											value="" />发票状态</label> <label style="display: none;"
																		   class="am-u-sm-4 am-form-label"><input
											disabled="disabled" checked="checked" type="checkbox"
											value="" />操作</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="jylsh" id="jylsh" />交易流水号
										</label> <label class="am-u-sm-4 am-form-label"><input
											name="column" type="checkbox" value="jylssj" id="jylssj" />交易流水时间</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="xfsh" id="xfsh" />销方税号</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="xfmc" id="xfmc" />销方名称</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="xfyh" id="xfyh" />销方银行</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="xfyhzh" id="xfyhzh" />销方银行账号</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="xfdz" id="xfdz" />销方地址
										</label> <label class="am-u-sm-4 am-form-label"><input
											name="column" type="checkbox" value="xfdh" id="xfdh" />销方电话</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="xfyb" id="xfyb" />销方邮编</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="gfsh" id="gfsh" />购方税号
										</label><label class="am-u-sm-4 am-form-label"><input
											name="column" type="checkbox" value="gfyh" id="gfyh" />购方银行</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="gfsjh" id="gfsjh" />购方手机号</label>

									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="gfyhzh" id="gfyhzh" />购方银行账号
										</label> <label class="am-u-sm-4 am-form-label"><input
											name="column" type="checkbox" value="gfdz" id="gfdz" />购方地址</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="gfdh" id="gfdh" />购方电话</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="gfemail" id="gfemail" />购方email</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="bz" id="bz" />备注</label> <label
											class="am-u-sm-4 am-form-label"><input
											name="column" type="checkbox" value="skr" id="skr" />收款人</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="kpr" id="kpr" />开票人</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="fhr" id="fhr" />复核人</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="ssyf" id="ssyf" />所属月份</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="yfpdm" id="yfpdm" />原发票代码</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="yfphm" id="yfphm" />原发票号码</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="ykpjshj" id="ykpjshj" />已开票价税合计</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="tqm" id="tqm" />提取码</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="skpid" id="skpid" />开票点</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="kpddm" id="kpddm" />开票点代码</label>
									</div>
									<div class="am-form-group" id="xzxzcsz">
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="hjje" id="hjje" />合计金额</label>
										<label class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" value="hjse" id="hjse" />合计税额</label>
										<label style="display: none;"
											   class="am-u-sm-4 am-form-label"><input
												name="column" type="checkbox" disabled="disabled" />合计税额</label>
									</div>
								</div>
							</div>
							<div class="am-modal-footer">
								<div class="am-u-sm-12">
									<div class="am-form-group">
										<div class="am-u-sm-12  am-text-center" style="line-height:44px">
											<button type="button"
													class="js-submit  am-btn am-btn-primary">确定</button>
											<button type="button"
													class="js-close  am-btn am-btn-danger">取消</button>
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
				<div class="am-modal am-modal-no-btn" tabindex="-1" id="biaoti1">
					<div class="am-modal-dialog">
						<form id="biao1" class="js-form-0 am-form am-form-horizontal">
							<div class="am-tabs" data-am-tabs>
								<div class="am-tab-panel am-fade am-in am-active" id="tab1">
									<div class="am-modal-hd">
										自定义导出列<a href="javascript: void(0)"
											class="am-close am-close-spin" data-am-modal-close>&times;</a>
									</div>
									<div class="am-modal-bd" style="overflow: auto; height: 400px;">
										<hr />
										<div class="am-g">
											<div class="am-u-sm-12">
												<div class="am-form-group">
													<label class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" name="column"
														disabled="disabled" value="ddh" id="ddh1" />订单号</label> <label
														class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" disabled="disabled"
														value="fpczlxmc" id="fpczlxmc1" name="column" />操作类型 </label> <label
														class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" disabled="disabled"
														value="fpdm" id="fpdm1" name="column" />发票代码</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" disabled="disabled"
														value="fphm" id="fphm1" name="column" />发票号码 </label> <label
														class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" disabled="disabled"
														value="jshj" id="jshj1" name="column" />价税合计 </label> <label
														class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" disabled="disabled"
														value="gfmc" id="gfmc1" name="column" />购方名称</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" disabled="disabled"
														value="kprq" name="column" id="kprq1" />开票日期 </label> <label
														class="am-u-sm-4 am-form-label"><input
														checked="checked" type="checkbox" disabled="disabled"
														value="fpzt" name="column" id="fpzt1" />发票状态</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="jylsh" id="jylsh1" />交易流水号
													</label> <label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="jylssj" id="jylssj1" />交易流水时间</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="xfsh" id="xfsh1" />销方税号</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="xfmc" id="xfmc1" />销方名称</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="xfyh" id="xfyh1" />销方银行</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="xfyhzh" id="xfyhzh1" />销方银行账号</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="xfdz" id="xfdz1" />销方地址
													</label> <label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="xfdh" id="xfdh1" />销方电话</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="xfyb" id="xfyb1" />销方邮编</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="gfsh" id="gfsh1" />购方税号
													</label><label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="gfyh" id="gfyh1" />购方银行</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="gfsjh" id="gfsjh1" />购方手机号</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="gfyhzh" id="gfyhzh1" />购方银行账号
													</label> <label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="gfdz" id="gfdz1" />购方地址</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="gfdh" id="gfdh1" />购方电话</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="gfemail"
														id="gfemail1" />购方email</label> <label
														class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="bz" id="bz1" />备注</label> <label
														class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="skr" id="skr1" />收款人</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="kpr" id="kpr1" />开票人</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="fhr" id="fhr1" />复核人</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="ssyf" id="ssyf1" />所属月份</label>
												</div>
												<div class="am-form-group" id="xzxzcsz1">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="yfpdm" id="yfpdm1" />原发票代码</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="yfphm" id="yfphm1" />原发票号码</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="ykpjshj"
														id="ykpjshj1" />已开票价税合计</label>
												</div>
												<div class="am-form-group" id="xzxzcsz">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="tqm" id="tqm1" />提取码</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="skpid" id="skpid1" />开票点</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="kpddm" id="kpddm1" />开票点代码</label>
												</div>
												<div class="am-form-group" id="xzxzcsz">
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="hjje" id="hjje1" />合计金额</label>
													<label class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" value="hjse" id="hjse1" />合计税额</label>
													<label style="display: none;"
														class="am-u-sm-4 am-form-label"><input
														name="column" type="checkbox" disabled="disabled" />合计税额</label>
												</div>
											</div>
											<div class="am-u-sm-12">
												<div class="am-form-group">
													<div class="am-u-sm-12  am-text-center">
														<button type="button"
															class="js-submit1  am-btn am-btn-primary">确定</button>
														<button type="button"
															class="js-close1  am-btn am-btn-danger">取消</button>
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
<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-fphm">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">核对发票号码代码,打印机是否放好发票
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
		<div style="margin-top: 10px;">
			发票类型: <input id="fplxmc" type="text" disabled="disabled">
		</div>
		<div style="margin-top: 10px;">
			发票代码: <input id="fpdm2" type="text" disabled="disabled">
		</div>
		<div style="margin-top: 10px;">
			发票号码: <input id="fphm2" type="text" disabled="disabled">
		</div>
		<div style="margin-top: 20px;">
			<button id="kp_kp" type="button" class="am-btn am-btn-primary" data-am-modal="{target: '#doc-modal-fphm', closeViaDimmer: 0, width: 400, height: 225}">
			   确认开票
			</button>
		</div>
    </div>
  </div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-fpck">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">核对发票号码代码,打印机是否放好发票
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<div style="margin-top: 10px;">
					发票类型: <input id="fplxmc1" type="text" disabled="disabled">
				</div>
				<div style="margin-top: 10px;">
					发票代码: <input id="fpdm3" type="text" disabled="disabled">
				</div>
				<div style="margin-top: 10px;">
					发票号码: <input id="fphm3" type="text" disabled="disabled">
				</div>
				<div style="margin-top: 20px;">
					<button id="fpckqr" type="button" class="am-btn am-btn-primary"
					data-am-modal="{target: '#doc-modal-fpck', closeViaDimmer: 0, width: 400, height: 225}">
					确认重新开具
				    </button>
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
	<%--<div style="margin-left: -40%;overflow: auto; background-color: white;width:80%;" class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-fpyl">
         <div class="am-modal-hd">
          <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
      <div id="doc-modal-fpyll" style="background-color: white;"class="am-modal-dialog">
           <div class="am-modal-bd" >
           <div id="doc-modal-fpyll">
            <%@ include file="fapiao.jsp" %>
            </div>
        </div>
      </div>--%>
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
	<script src="assets/js/fpck.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
</body>
<script>

    function getCurrentMonthFirst(){
        var date=new Date();
        date.setDate(1);
        return date;
    }

    function formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }

    $(function() {
        var startDate = getCurrentMonthFirst();
        var endDate = new Date();
        //var $alert = $('#my-alert');
        $("#w_kprqq").val(formatDate(startDate));
        $("#w_kprqz").val(formatDate(endDate));
        $("#s_kprqq").val(formatDate(startDate));
        $("#s_kprqz").val(formatDate(endDate));
    });
    function sf(){
        if ($("#sfbx").is(':checked')) {
            $("#show").html("*");
        }else{
            $("#show").html("");
        }
    }
	function refresh() {
		this.location = this.location;
	}
</script>
</html>
