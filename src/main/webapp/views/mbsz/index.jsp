<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>导入模板设置</title>
<meta name="description" content="导入模板设置">
<meta name="keywords" content="导入模板设置">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
	<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style>
 .am-u-left{
    padding-left:0em;
 }
 thead tr th {
 	text-align: center;
 }
 table tbody td a {
    text-decoration: underline;
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
				<div class="am-cf am-padding">
					<div class="widget-title am-cf">
						<strong id="yjcd" class="am-text-primary am-text-lg" style="color: #838FA1;"></strong> / <strong id="ejcd" style="color: #0e90d2;"></strong>
					</div>
					   <!-- 侧边栏内容 begin-->
								<div id="doc-oc-demo3" class="am-offcanvas">
									<div class="am-offcanvas-bar am-offcanvas-bar-flip">
										<form id="ycform">
											<%--<div class="am-offcanvas-content">
												<div class="am-form-group">
													<label for="xfsh" class="am-u-sm-4 am-form-label">选择销方</label>
													<div class="am-u-sm-8">
														<select  id="xfsh"  data-am-selected="{ btnSize: 'sm'}"
															name="xfsh">
															<option id="xzxfq" value="">选择销方</option>
															<c:forEach items="${xfList}" var="item">
																<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>--%>
											<div class="am-offcanvas-content" style="margin-top: 8px;">
												<div class="am-form-group">
													<label for="sfgx" class="am-u-sm-4 am-form-label">是否共享</label>
													<div class="am-u-sm-8">
														<select  id="sfgx"  data-am-selected="{ btnSize: 'sm'}"
															name="sfgx">
															<option id="xzlxq" value="">选择类型</option>
															<option value="0">不共享</option>
															<option value="1">共享</option>
														</select>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content" style="margin-top: 5px;">
												<div class="am-form-group">
													<label for="c_mbmc" class="am-u-sm-4 am-form-label">模板名称</label>
													<div class="am-u-sm-8">
														<input id="c_mbmc" type="text" placeholder="模板名称">
													</div>
												</div>
											</div>
											<div style="padding: 32px;">
												<button type="button" id="mb_search1"
													class="am-btn am-btn-default am-btn-secondary data-back">
													 查询
												</button>
											</div>
										</form>
									</div>
								</div>
								<!-- 侧边内容end -->
				</div>
		</div>
			<hr />

			<div class="am-g  am-padding-top">
				<form action="#" class="js-search-form  am-form am-form-horizontal">
					<div class="am-g">
						<%--<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">--%>
									<%--<div class="am-form-group">--%>
										<%--<div class="am-btn-toolbar">--%>
											<%--<div class="am-btn-group am-btn-group-xs">--%>
												<%--<button type="button" id="button2"--%>
													<%--class="am-btn am-btn-default am-btn-primary">--%>
													<%--录入--%>
												<%--</button>--%>
												<%--<button type="button" id="del"--%>
													<%--class="am-btn am-btn-default am-btn-danger">--%>
													<%--删除--%>
												<%--</button>--%>
											<%--</div>--%>
										<%--</div>--%>
									<%--</div>--%>
						<%--</div>--%>

					</div>

					<div class="am-form-group am-form-group-sm" style="margin-top: 20px">
						<div class="am-u-sm-3">
								<select id="dxcsm"  data-am-selected="{ btnSize: 'sm'}">
									<option value="s_mbmc">模板名称</option>
								</select>
						</div>
						<div class="am-u-sm-3">
							<div
									class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
								<input id="dxcsz" type="text" class="am-form-field ">
								<span class="am-input-group-btn">
											<button id="mb_search"
													class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search"
													type="button"></button>
										</span>
							</div>
						</div>
						<button class="am-btn am-btn-secondary am-fr"
								data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
					</div>
				</form>
				<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
					<div class="am-form-group">
						<div class="am-btn-toolbar">
							<div class="am-btn-group am-btn-group-xs btn-listBox">
								<button type="button"  id="button2" class="am-btn am-btn-secondary am-btn-sm "> 录入</button>
								<!--                                                 <button type="button"  id="button4" class="modify am-btn am-btn-default am-btn-warning"> 修改</button> -->
								<button type="button" id="del" class="am-btn am-btn-danger am-btn-sm js-sent" > 删除</button>

							</div>
						</div>
					</div>
				</div>
				<div class="am-u-sm-12  am-padding-top">
					<div>
						<table id="tbl"
							class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
							<thead>
								<tr>
								    <th><input type="checkbox" id="check_all" /></th>
								<!-- 	<th>序号</th -->
									<th style="display: none;">销方id</th>
									<th>操作</th>
									<th>模板名称</th>
									<th>销方名称</th>
									<th>销方税号</th>
									<th style="display: none;">是否共享</th>
									<th>是否共享</th>
									
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="am-modal" tabindex="-1" id="hongchong"
			title="导入配置">
			<div class="am-modal-dialog">
				<form class="am-form am-form-horizontal" id="importConfigForm">
					<div class="am-modal-hd">
						导入配置 <a href="javascript: void(0)" class="am-close am-close-spin"
								data-am-modal-close>&times;</a>
					</div>
					<hr />
					<div class="am-modal-bd" style="overflow: auto; max-height: 500px;">
						<div class="am-u-sm-12">
								<div class="am-form-group">
									<label for="config_jylsh" class="am-u-sm-2 am-form-label">模板名称</label>
									<div class="am-u-sm-10">
										<input type="text" name="mbmc" id="mbmc" required="required" style="width:519px;">
										<input type="text" name="mbid" id="mbid" style="display: none">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gs_radio" class="am-u-sm-2 am-form-label">是否共享</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gs_radio" name="config_gs_radio" >
												<option value="1" >是</option>
												<option value="0">否</option>
											</select>
										</div>

									</div>
									<div class="am-u-sm-6">
										<input type="hidden" id="pre_zd" name="pre_zd"
											   placeholder="">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_jylsh" class="am-u-sm-2 am-form-label">交易流水号</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_jylsh_radio" name="config_jylsh_radio" >
												<option value="auto" >自动</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_jylsh" name="config_jylsh"
											   placeholder="" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_ddh" class="am-u-sm-2 am-form-label">订单号</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_ddh_radio" name="config_ddh_radio" >
												<option value="auto" >自动</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_ddh" name="config_ddh"
											   placeholder="" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfsh" class="am-u-sm-2 am-form-label">购方税号</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfsh_radio" name="config_gfsh_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfsh" name="config_gfsh"
											   placeholder="如无可不填,专票必填" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfmc" class="am-u-sm-2 am-form-label">购方名称</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfmc_radio" name="config_gfmc_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfmc" name="config_gfmc"
											   placeholder="" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfdz" class="am-u-sm-2 am-form-label">购方地址</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfdz_radio" name="config_gfdz_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfdz" name="config_gfdz"
											   placeholder="如无可不填,专票必填">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfdh" class="am-u-sm-2 am-form-label">购方电话</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfdh_radio" name="config_gfdh_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfdh" name="config_gfdh"
											   placeholder="如无可不填,专票必填" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfyh" class="am-u-sm-2 am-form-label">购方银行</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfyh_radio" name="config_gfyh_radio"  >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfyh" name="config_gfyh"
											   placeholder="如无可不填,专票必填" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfyhzh" class="am-u-sm-2 am-form-label">购方银行账号</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfyhzh_radio" name="config_gfyhzh_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfyhzh" name="config_gfyhzh"
											   placeholder="如无可不填,专票必填" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfsjh" class="am-u-sm-2 am-form-label">购方手机号</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfsjh_radio" name="config_gfsjh_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfsjh" name="config_gfsjh"
											   placeholder="如无可不填" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_fpzldm" class="am-u-sm-2 am-form-label">发票种类</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_fpzldm_radio" name="config_fpzldm_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<div class="am-form-group tpl-table-list-select" id="fpzldiv">
											<select id="config_fpzldm" name="config_fpzldm" >
												<option value="01" >增值税专票</option>
												<option value="02">增值税普票</option>
												<option value="12">电子发票(增普)</option>
											</select>
										</div>
										<input type="text" id="config_fpzldm_input" name="config_fpzldm_input"
											   placeholder="" required  style="display:none">
									</div>
								</div>

								<div class="am-form-group">
									<label class="am-u-sm-2 am-form-label">选择商品</label>
									<div class="am-u-sm-10">
										<div class="am-form-group tpl-table-list-select" >
											<select id="selectImportConfigSp" data-am-selected="{btnWidth: 524,btnSize: 'sm'}"> >
												<c:forEach items="${spList}" var="item">
													<option value="${item.id}">${item.spmc}|${item.sl}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_spdm" class="am-u-sm-2 am-form-label">商品分类编码</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_spdm_radio" name="config_spdm_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_spdm" name="config_spdm"
											   placeholder="" required value="${sp.spbm}">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_spmc" class="am-u-sm-2 am-form-label">商品名称</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_spmc_radio" name="config_spmc_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_spmc" name="config_spmc"
											   placeholder="" required value="${sp.spmc}">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_spggxh" class="am-u-sm-2 am-form-label">规格型号</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_spggxh_radio" name="config_spggxh_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_spggxh" name="config_spggxh"
											   placeholder="" required value="${sp.spggxh}">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_spdw" class="am-u-sm-2 am-form-label">商品单位</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_spdw_radio" name="config_spdw_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_spdw" name="config_spdw"
											   placeholder="" required value="${sp.spdw}">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_sps" class="am-u-sm-2 am-form-label">商品数量</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_sps_radio" name="config_sps_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_sps" name="config_sps"
											   placeholder="" required value="1">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_spdj" class="am-u-sm-2 am-form-label">商品单价</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_spdj_radio" name="config_spdj_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_spdj" name="config_spdj"
											   placeholder="" required value="${sp.spdj}">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_spje" class="am-u-sm-2 am-form-label">商品金额</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_spje_radio" name="config_spje_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_spje" name="config_spje"
											   placeholder="" required value="">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_spsl" class="am-u-sm-2 am-form-label">商品税率</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_spsl_radio" name="config_spsl_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_spsl" name="config_spsl"
											   placeholder="" required value="${sp.sl}">
									</div>
								</div>
								<div class="am-form-group">
									<label  class="am-u-sm-2 am-form-label">商品税额</label>
									<div class="am-u-sm-4">
										<div class="tpl-table-list-select">
											<select id="config_spse_radio" name="config_spse_radio" >
												<option value="auto" >自动</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" readonly id="config_spse" name="config_spse"
											   placeholder="" required value="">
									</div>
								</div>
								<div class="am-form-group">
									<label class="am-u-sm-2 am-form-label">含税标志</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_hsbz_radio" name="config_hsbz_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<div class="am-form-group tpl-table-list-select" >
											<select id="config_hsbz" name="config_hsbz"  >
												<option value="1" >是</option>
												<option value="0">否</option>
											</select>
										</div>
										<input type="text"  id="config_hsbz_input" name="config_hsbz_input"
											   placeholder="" required   value=""  style="display:none" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfemail" class="am-u-sm-2 am-form-label">购方邮箱</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfemail_radio" name="config_gfemail_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfemail" name="config_gfemail"
											   placeholder="" required value="">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_bz" class="am-u-sm-2 am-form-label">备注</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_bz_radio" name="config_bz_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_bz" name="config_bz"
											   placeholder="" required value="">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_tqm" class="am-u-sm-2 am-form-label">提取码</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_tqm_radio" name="config_tqm_radio" >
												<option value="auto" >默认</option>
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_tqm" name="config_tqm"
											   placeholder="" required value="">
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_khh" class="am-u-sm-2 am-form-label">客户号</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_khh_radio" name="config_khh_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_khh" name="config_khh"
											   placeholder="" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_gfsjrdz" class="am-u-sm-2 am-form-label">收件地址</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_gfsjrdz_radio" name="config_gfsjrdz_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_gfsjrdz" name="config_gfsjrdz"
											   placeholder="" required>
									</div>
								</div>

								<div class="am-form-group">
									<label for="config_zsfs" class="am-u-sm-2 am-form-label">征收方式</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_zsfs_radio" name="config_zsfs_radio" >
												<option value="auto">默认</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<div class="am-form-group tpl-table-list-select" >
											<select id="config_zsfs" name="config_zsfs"  >
												<option value="0" >普通征税</option>
												<option value="2">差额征税</option>
											</select>
										</div>
									</div>
								</div>
								<div class="am-form-group">
									<label for="config_kce" class="am-u-sm-2 am-form-label">扣除额</label>
									<div class="am-u-sm-4">
										<div class="am-form-group tpl-table-list-select">
											<select id="config_kce_radio" name="config_kce_radio" >
												<option value="config">导入文件表头</option>
											</select>
										</div>
									</div>
									<div class="am-u-sm-6">
										<input type="text" id="config_kce" name="config_kce"
											   placeholder="" required>
									</div>
								</div>
							</div>
					</div>
					<div class="am-modal-footer">
						<div class="am-u-sm-12 am-text-center" style="line-height:44px">
							<button type="button" id="btnImportConfigSave"
									class="am-btn am-btn-xs am-btn-secondary">保存</button>
							<button type="button" id="close2"
									class="am-btn am-btn-danger am-btn-xs">关闭</button>
						</div>
					</div>

				</form>

		</div>
		</div>
</div>
</div>
</div>
</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>


	<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

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
	<script src="assets/js/format.js"></script>
	<script src="assets/js/mbsz.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script>
		$(function() {
		    $("#config_fpzldm_radio").change(function(){
                   var  config_fpzldm_radio=$("#config_fpzldm_radio").val();
                   if(config_fpzldm_radio=='auto'){
                      $("#config_fpzldm").css("display","");
                      $("#config_fpzldm_input").css("display","none");
                   }else if(config_fpzldm_radio=='config'){
                       $("#config_fpzldm").css("display","none");
                       $("#config_fpzldm_input").css("display","");
                   }
			});
		    $("#config_hsbz_radio").change(function(){
                 var config_hsbz_radio=$("#config_hsbz_radio").val();
                 if(config_hsbz_radio=="auto"){
                     $("#config_hsbz").css("display","");
                     $("#config_hsbz_input").css("display","none");
				 }else if(config_hsbz_radio=="config"){
                     $("#config_hsbz").css("display","none");
                     $("#config_hsbz_input").css("display","");
				 }
			});
		    $("#config_spse_radio").change(function(){
		        var config_spse_radio=$("#config_spse_radio").val();
		        if(config_spse_radio=='auto'){
                       $("#config_spse").attr("readonly",true);
				}else if(config_spse_radio=='config'){
                       $("#config_spse").attr("readonly",false);
				}
			});

		        //导入配置选择商品
		        $("#selectImportConfigSp").change(function () {
		            var spid = $(this).val();
		            var spmc = $("#selectImportConfigSp option:checked").text();
		            var pos = spmc.indexOf("|");
		            spmc = spmc.substring(0, pos);

		            $("#config_spdm_radio").find("option[value='auto']").prop("selected","true");
                    $("#config_spmc_radio").find("option[value='auto']").prop("selected","true");
                    $("#config_spggxh_radio").find("option[value='auto']").prop("selected","true");
                    $("#config_spdw_radio").find("option[value='auto']").prop("selected","true");
                    $("#config_spdj_radio").find("option[value='auto']").prop("selected","true");
                    $("#config_spsl_radio").find("option[value='auto']").prop("selected","true");

                    var url = "<%=request.getContextPath()%>/mbsz/getSpxq";
		            $.post(url, {spid: spid, spmc:spmc}, function (res) {
		                if (res) {
		                    $("input[name=config_spdm]").val(res["spbm"]);
		                    $("input[name=config_spmc]").val(res["spmc"]);
		                    $("input[name=config_spggxh]").val(res["spggxh"]);
		                    $("input[name=config_spdw]").val(res["spdw"]);
		                    $("input[name=config_spdj]").val(res["spdj"]);
		                    $("input[name=config_spsl]").val(res["sl"]);
		                }
		            })
		        });
		});
	</script>

</body>
</html>
