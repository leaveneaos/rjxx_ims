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
<script src="assets/js/loading.js"></script>
<style>
 .am-u-left{
    padding-left:0em;
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
					<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>导入模板设置</strong>
					<button class="am-btn am-btn-success am-fr"
									data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
				</div>
				   <!-- 侧边栏内容 begin-->
							<div id="doc-oc-demo3" class="am-offcanvas">
								<div class="am-offcanvas-bar am-offcanvas-bar-flip">
									<form id="ycform">
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="s_ddh" class="am-u-sm-4 am-form-label">选择销方</label>
												<div class="am-u-sm-8">
													<select data-am-selected="{btnSize: 'sm'}" id="xfsh"
														name="xfsh">
														<option id="xzxfq" value="">选择销方</option>
														<c:forEach items="${xfList}" var="item">
															<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="s_fplx" class="am-u-sm-4 am-form-label">是否共享</label>
												<div class="am-u-sm-8">
													<select data-am-selected="{btnSize: 'sm'}" id="sfgx"
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
											<button type="button" id="kp_search1"
												class="am-btn am-btn-default am-btn-success">
												<span class="am-icon-search-plus"></span> 查询
											</button>
										</div>
									</form>
								</div>
							</div>
							<!-- 侧边内容end -->
			</div>
			<hr />

			<div class="am-g  am-padding-top">
				<form action="#" class="js-search-form  am-form am-form-horizontal">
					<div class="am-g">
						<%-- <div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-3 am-form-label">销方税号</label>
								<div class="am-u-sm-9">
									<select id="xfsh" name="xfsh">
										<option value="">请选择</option>
										<c:forEach items="${xfList}" var="item">
											<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_mbmc" class="am-u-sm-3 am-form-label">模板名称</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_mbmc" name="s_mbmc"
										placeholder="请输入模板名称" />
								</div>
							</div>
						</div> --%>
						<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button type="button" id="button2"
													class="am-btn am-btn-default am-btn-primary">
													录入
												</button>
												<button type="button" id="del"
													class="am-btn am-btn-default am-btn-danger">
													删除
												</button>
												<!-- <button type="button" id="modify"
													class="am-btn am-btn-default am-btn-secondary">
													修改
												</button> -->
											</div>
										</div>
									</div>
								</div>
						<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
									<div class="am-form-group tpl-table-list-select">
										<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
											<option value="s_mbmc">模板名称</option>
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
					</div>

					<hr />
					<!-- <div class="am-u-sm-12  am-padding  am-text-center">
						<button id="button1" type="button"
							class="js-search  am-btn am-btn-primary">查询</button>
						<button id="button2" type="button"
							class="js-search  am-btn am-btn-secondary">新增</button>

					</div> -->

				</form>
				<div class="am-u-sm-12  am-padding-top">
					<div>

						<table id="tbl"
							class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
							<thead>
								<tr>
								    <th><input type="checkbox" id="check_all" /></th>
								<!-- 	<th>序号</th -->
									<th style="display: none;">销方id</th>
									<th>模板名称</th>
									<th>销方名称</th>
									<th>销方税号</th>
									<th style="display: none;">是否共享</th>
									<th>是否共享</th>
									<th>操作</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong"
			title="导入配置">
			<div class="am-modal-dialog" style="overflow: auto">
				<div class="am-modal-hd">
					导入配置 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>

				<hr />
				<div class="am-modal-dialog">

			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal" id="importConfigForm">
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<label for="config_jylsh" class="am-u-sm-4 am-form-label">模板名称</label>
								<div class="am-u-sm-8">
									<input type="text" name="mbmc" id="mbmc" required="required">
									<input type="text" name="mbid" id="mbid" style="display: none">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_sfgx" class="am-u-sm-4 am-form-label">是否共享</label>
								<div class="am-u-sm-4">
									<input type="radio" id="yes" name="config_gs_radio" value="1">是
									<input type="radio" id="no" checked name="config_gs_radio"
										value="0">否
								</div>
								<div class="am-u-sm-4">
									<input type="hidden" id="pre_zd" name="pre_zd"
										placeholder="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_jylsh" class="am-u-sm-4 am-form-label">交易流水号</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_jylsh_radio" value="auto">自动
									<input type="radio" checked name="config_jylsh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_jylsh" name="config_jylsh"
										placeholder="" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_ddh" class="am-u-sm-4 am-form-label">订单号</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_ddh_radio" value="auto">自动
									<input type="radio" checked name="config_ddh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_ddh" name="config_ddh"
										placeholder="" required>
								</div>
							</div>
							<div class="am-form-group">
								<label class="am-u-sm-4 am-form-label">选择销方</label>
								<div class="am-u-sm-8">
									<select id="selectImportConfigXf" class="am-u-sm-12">
										<c:forEach items="${xfList}" var="item">
											<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</select>
								</div>
								<div class="am-u-sm-4"></div>
							</div>
							<div class="am-form-group">
								<label for="config_xfsh" class="am-u-sm-4 am-form-label">销方税号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfsh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfsh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfsh" name="config_xfsh"
										placeholder="" required value="${xf.xfsh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfmc" class="am-u-sm-4 am-form-label">销方名称</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfmc_radio"
										value="auto">默认 <input type="radio"
										name="config_xfmc_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfmc" name="config_xfmc"
										placeholder="" required value="${xf.xfmc}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfdz" class="am-u-sm-4 am-form-label">销方地址</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfdz_radio"
										value="auto">默认 <input type="radio"
										name="config_xfdz_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfdz" name="config_xfdz"
										placeholder="" required value="${xf.xfdz}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfdh" class="am-u-sm-4 am-form-label">销方电话</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfdh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfdh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfdh" name="config_xfdh"
										placeholder="" required value="${xf.xfdh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfyh" class="am-u-sm-4 am-form-label">销方银行</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfyh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfyh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfyh" name="config_xfyh"
										placeholder="" required value="${xf.xfyh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfyhzh" class="am-u-sm-4 am-form-label">销方银行账号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfyhzh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfyhzh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfyhzh" name="config_xfyhzh"
										placeholder="" required value="${xf.xfyhzh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_skr" class="am-u-sm-4 am-form-label">收款人</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_skr_radio"
										value="auto">默认 <input type="radio"
										name="config_skr_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_skr" name="config_skr"
										placeholder="" required value="${xf.skr}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_kpr" class="am-u-sm-4 am-form-label">开票人</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_kpr_radio"
										value="auto">默认 <input type="radio"
										name="config_kpr_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_kpr" name="config_kpr"
										placeholder="" required value="${xf.kpr}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_fhr" class="am-u-sm-4 am-form-label">复核人</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_fhr_radio"
										value="auto">默认 <input type="radio"
										name="config_fhr_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_fhr" name="config_fhr"
										placeholder="" required value="${xf.fhr}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfsh" class="am-u-sm-4 am-form-label">购方税号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfsh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfsh" name="config_gfsh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfmc_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfmc" name="config_gfmc"
										placeholder="" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfdz" class="am-u-sm-4 am-form-label">购方地址</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfdz_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfdz" name="config_gfdz"
										placeholder="如无可不填,专票必填">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfdh" class="am-u-sm-4 am-form-label">购方电话</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfdh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfdh" name="config_gfdh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfyh" class="am-u-sm-4 am-form-label">购方银行</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfyh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfyh" name="config_gfyh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfyhzh" class="am-u-sm-4 am-form-label">购方银行账号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfyhzh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfyhzh" name="config_gfyhzh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfsjh" class="am-u-sm-4 am-form-label">购方手机号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfsjh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfsjh" name="config_gfsjh"
										placeholder="如无可不填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_fpzldm" class="am-u-sm-4 am-form-label">发票种类</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_fpzldm_radio"
										value="config">导入文件<font size='1px' color='red'>(01专票，02普票，12电子发票)</font>
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_fpzldm" name="config_fpzldm"
										placeholder="必填" required>
								</div>
							</div>
							
							<div class="am-form-group">
								<label class="am-u-sm-4 am-form-label">选择商品</label>
								<div class="am-u-sm-8">
									<select id="selectImportConfigSp" class="am-u-sm-12">
										<c:forEach items="${spList}" var="item">
											<option value="${item.spdm}">${item.spmc}(${item.spdm})</option>
										</c:forEach>
									</select>
								</div>
								<div class="am-u-sm-4"></div>
							</div>
							<div class="am-form-group">
								<label for="config_spdm" class="am-u-sm-4 am-form-label">商品代码</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spdm_radio"
										value="auto">默认 <input type="radio"
										name="config_spdm_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spdm" name="config_spdm"
										placeholder="" required value="${sp.spdm}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spmc" class="am-u-sm-4 am-form-label">商品名称</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spmc_radio"
										value="auto">默认 <input type="radio"
										name="config_spmc_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spmc" name="config_spmc"
										placeholder="" required value="${sp.spmc}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spggxh" class="am-u-sm-4 am-form-label">规格型号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spggxh_radio"
										value="auto">默认 <input type="radio"
										name="config_spggxh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spggxh" name="config_spggxh"
										placeholder="" required value="${sp.spggxh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spdw" class="am-u-sm-4 am-form-label">商品单位</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spdw_radio"
										value="auto">默认 <input type="radio"
										name="config_spdw_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spdw" name="config_spdw"
										placeholder="" required value="${sp.spdw}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_sps" class="am-u-sm-4 am-form-label">商品数量</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_sps_radio"
										value="auto">默认 <input type="radio"
										name="config_sps_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_sps" name="config_sps"
										placeholder="" required value="1">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spdj" class="am-u-sm-4 am-form-label">商品单价</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spdj_radio"
										value="auto">默认 <input type="radio"
										name="config_spdj_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spdj" name="config_spdj"
										placeholder="" required value="${sp.spdj}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spje" class="am-u-sm-4 am-form-label">商品金额</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spje_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spje" name="config_spje"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spsl" class="am-u-sm-4 am-form-label">商品税率</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spsl_radio"
										value="auto">默认 <input type="radio"
										name="config_spsl_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spsl" name="config_spsl"
										placeholder="" required value="${sp.sl}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spse" class="am-u-sm-4 am-form-label">商品税额</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spse_radio"
										value="auto">自动 <input type="radio"
										name="config_spse_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" readonly id="config_spse" name="config_spse"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label class="am-u-sm-4 am-form-label">含税标志</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_hsbz_radio"
										value="auto">默认
								</div>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_hsbz" value="1">是
									<input type="radio" name="config_hsbz" value="0">否
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfemail" class="am-u-sm-4 am-form-label">购方邮箱</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_gfemail_radio" value="auto">默认
									<input type="radio" checked name="config_gfemail_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfemail" name="config_gfemail"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_bz" class="am-u-sm-4 am-form-label">备注</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_bz_radio" value="auto">默认
									<input type="radio" checked name="config_bz_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_bz" name="config_bz"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_tqm" class="am-u-sm-4 am-form-label">提取码</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_tqm_radio" value="auto">默认
									<input type="radio" checked name="config_tqm_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_tqm" name="config_tqm"
										placeholder="" required value="">
								</div>
							</div>
						</div>
						<div class="am-u-sm-12">
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
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="bulk-import-div"
			title="导入配置">
			<div class="am-modal-dialog" style="overflow: auto">
				<div class="am-modal-hd">
					修改模板 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>

				<hr />
				<div class="am-modal-dialog">

			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal" id="importForm1">
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<label for="config_jylsh" class="am-u-sm-4 am-form-label">模板名称</label>
								<div class="am-u-sm-8">
									<input type="text" name="mbmc1" id="mbmc1" required="required">
									<input type="text" name="mbid1" id="mbid1" style="display: none">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_sfgx" class="am-u-sm-4 am-form-label">是否共享</label>
								<div class="am-u-sm-4">
									<input type="radio" id="yes1" name="config_gs_radio" value="1">是
									<input type="radio" id="no1" checked name="config_gs_radio"
										value="0">否
								</div>
								<div class="am-u-sm-4">
									<input type="hidden" id="pre_zd" name="pre_zd"
										placeholder="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_jylsh" class="am-u-sm-4 am-form-label">交易流水号</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_jylsh_radio" value="auto">自动
									<input type="radio" checked name="config_jylsh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_jylsh" name="config_jylsh"
										placeholder="" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_ddh" class="am-u-sm-4 am-form-label">订单号</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_ddh_radio" value="auto">自动
									<input type="radio" checked name="config_ddh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_ddh" name="config_ddh"
										placeholder="" required>
								</div>
							</div>
							<div class="am-form-group">
								<label class="am-u-sm-4 am-form-label">选择销方</label>
								<div class="am-u-sm-8">
									<select id="selectImportConfigXf1" class="am-u-sm-12">
										<c:forEach items="${xfList}" var="item">
											<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</select>
								</div>
								<div class="am-u-sm-4"></div>
							</div>
							<div class="am-form-group">
								<label for="config_xfsh" class="am-u-sm-4 am-form-label">销方税号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfsh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfsh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfsh1" name="config_xfsh"
										placeholder="" required value="${xf.xfsh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfmc" class="am-u-sm-4 am-form-label">销方名称</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfmc_radio"
										value="auto">默认 <input type="radio"
										name="config_xfmc_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfmc" name="config_xfmc"
										placeholder="" required value="${xf.xfmc}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfdz" class="am-u-sm-4 am-form-label">销方地址</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfdz_radio"
										value="auto">默认 <input type="radio"
										name="config_xfdz_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfdz" name="config_xfdz"
										placeholder="" required value="${xf.xfdz}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfdh" class="am-u-sm-4 am-form-label">销方电话</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfdh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfdh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfdh" name="config_xfdh"
										placeholder="" required value="${xf.xfdh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfyh" class="am-u-sm-4 am-form-label">销方银行</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfyh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfyh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfyh" name="config_xfyh"
										placeholder="" required value="${xf.xfyh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_xfyhzh" class="am-u-sm-4 am-form-label">销方银行账号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_xfyhzh_radio"
										value="auto">默认 <input type="radio"
										name="config_xfyhzh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_xfyhzh" name="config_xfyhzh"
										placeholder="" required value="${xf.xfyhzh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_skr" class="am-u-sm-4 am-form-label">收款人</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_skr_radio"
										value="auto">默认 <input type="radio"
										name="config_skr_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_skr" name="config_skr"
										placeholder="" required value="${xf.skr}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_kpr" class="am-u-sm-4 am-form-label">开票人</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_kpr_radio"
										value="auto">默认 <input type="radio"
										name="config_kpr_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_kpr" name="config_kpr"
										placeholder="" required value="${xf.kpr}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_fhr" class="am-u-sm-4 am-form-label">复核人</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_fhr_radio"
										value="auto">默认 <input type="radio"
										name="config_fhr_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_fhr" name="config_fhr"
										placeholder="" required value="${xf.fhr}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfsh" class="am-u-sm-4 am-form-label">购方税号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfsh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfsh" name="config_gfsh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfmc_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfmc1" name="config_gfmc"
										placeholder="" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfdz" class="am-u-sm-4 am-form-label">购方地址</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfdz_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfdz" name="config_gfdz"
										placeholder="如无可不填,专票必填">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfdh" class="am-u-sm-4 am-form-label">购方电话</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfdh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfdh" name="config_gfdh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfyh" class="am-u-sm-4 am-form-label">购方银行</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfyh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfyh" name="config_gfyh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfyhzh" class="am-u-sm-4 am-form-label">购方银行账号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfyhzh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfyhzh" name="config_gfyhzh"
										placeholder="如无可不填,专票必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfsjh" class="am-u-sm-4 am-form-label">购方手机号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_gfsjh_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfsjh" name="config_gfsjh"
										placeholder="如无可不填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_fpzldm" class="am-u-sm-4 am-form-label">发票种类</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_fpzldm_radio"
										value="config">导入文件<font size='1px' color='red'>(01专票，02普票，12电子发票)</font>
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_fpzldm1" name="config_fpzldm"
										placeholder="必填" required>
								</div>
							</div>
							<div class="am-form-group">
								<label class="am-u-sm-4 am-form-label">选择商品</label>
								<div class="am-u-sm-8">
									<select id="selectImportConfigSp1" class="am-u-sm-12">
										<c:forEach items="${spList}" var="item">
											<option value="${item.spdm}">${item.spmc}(${item.spdm})</option>
										</c:forEach>
									</select>
								</div>
								<div class="am-u-sm-4"></div>
							</div>
							<div class="am-form-group">
								<label for="config_spdm" class="am-u-sm-4 am-form-label">商品代码</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spdm_radio"
										value="auto">默认 <input type="radio"
										name="config_spdm_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spdm" name="config_spdm"
										placeholder="" required value="${sp.spdm}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spmc" class="am-u-sm-4 am-form-label">商品名称</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spmc_radio"
										value="auto">默认 <input type="radio"
										name="config_spmc_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spmc" name="config_spmc"
										placeholder="" required value="${sp.spmc}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spggxh" class="am-u-sm-4 am-form-label">规格型号</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spggxh_radio"
										value="auto">默认 <input type="radio"
										name="config_spggxh_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spggxh" name="config_spggxh"
										placeholder="" required value="${sp.spggxh}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spdw" class="am-u-sm-4 am-form-label">商品单位</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spdw_radio"
										value="auto">默认 <input type="radio"
										name="config_spdw_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spdw" name="config_spdw"
										placeholder="" required value="${sp.spdw}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_sps" class="am-u-sm-4 am-form-label">商品数量</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_sps_radio"
										value="auto">默认 <input type="radio"
										name="config_sps_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_sps" name="config_sps"
										placeholder="" required value="1">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spdj" class="am-u-sm-4 am-form-label">商品单价</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spdj_radio"
										value="auto">默认 <input type="radio"
										name="config_spdj_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spdj" name="config_spdj"
										placeholder="" required value="${sp.spdj}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spje" class="am-u-sm-4 am-form-label">商品金额</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spje_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spje1" name="config_spje"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spsl" class="am-u-sm-4 am-form-label">商品税率</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spsl_radio"
										value="auto">默认 <input type="radio"
										name="config_spsl_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_spsl" name="config_spsl"
										placeholder="" required value="${sp.sl}">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_spse" class="am-u-sm-4 am-form-label">商品税额</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_spse_radio"
										value="auto">自动 <input type="radio"
										name="config_spse_radio" value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" readonly id="config_spse" name="config_spse"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label class="am-u-sm-4 am-form-label">含税标志</label>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_hsbz_radio"
										value="auto">默认
								</div>
								<div class="am-u-sm-4">
									<input type="radio" checked name="config_hsbz" value="1">是
									<input type="radio" name="config_hsbz" value="0">否
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_gfemail" class="am-u-sm-4 am-form-label">购方邮箱</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_gfemail_radio" value="auto">默认
									<input type="radio" checked name="config_gfemail_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_gfemail" name="config_gfemail"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_bz" class="am-u-sm-4 am-form-label">备注</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_bz_radio" value="auto">默认
									<input type="radio" checked name="config_bz_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_bz" name="config_bz"
										placeholder="" required value="">
								</div>
							</div>
							<div class="am-form-group">
								<label for="config_tqm" class="am-u-sm-4 am-form-label">提取码</label>
								<div class="am-u-sm-4">
									<input type="radio" name="config_tqm_radio" value="auto">默认
									<input type="radio" checked name="config_tqm_radio"
										value="config">导入文件
								</div>
								<div class="am-u-sm-4">
									<input type="text" id="config_tqm" name="config_tqm"
										placeholder="" required value="">
								</div>
							</div>
						</div>
						<div class="am-u-sm-12">
							<button type="button" id="btnImportConfigUpdate"
								class="am-btn am-btn-xs am-btn-secondary">保存</button>
							<button type="button" id="close1"
								class="am-btn am-btn-danger am-btn-xs">关闭</button>
						</div>
					</div>
				</form>
			</div>
			</div>
		</div>
		<!-- content end -->

		<!-- loading do not delete -->
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
	<script>
		$(function() {
			 $("#btnImportConfigSave").click(function () {
		            var data = $("#importConfigForm").serialize();
		            var url = "<%=request.getContextPath()%>/mbsz/saveImportConfig";
		            var mbmc = $('#mbmc').val();
		            var xfsh = $('#config_xfsh').val();
		            var gfmc = $('#config_gfmc').val();
		            var spje = $('#config_spje').val();
		            var fpzldm = $('#config_fpzldm').val();
		            if (mbmc == null || mbmc == '') {
		            	alert("请输入模板名称");
		            	return;
					}
		            if (xfsh == null || xfsh == '') {
		            	alert("销方税号不能为空，请重新选择");
		            	return;
					}
		            if (gfmc == null || gfmc == '') {
		            	alert("购方名称不能为空");
		            	return;
					}
		            if (spje == null || spje == '') {
		            	alert("商品金额不能为空");
		            	return;
					}
		            if (fpzldm == null || fpzldm == '') {
		            	alert("发票种类不能为空");
		            	return;
					}
		            $.post(url, data, function (res) {
		                var success = res["success"];
		                if (success) {
		                    alert("保存成功");
		                    $("#hongchong").modal("close");
	                        window.location.reload();
		                } else {
		                    var message = res["message"];
		                    alert(message);
		                }
		            });
		        });
			 $("#btnImportConfigUpdate").click(function () {
		            var data = $("#importForm1").serialize();
		            var url = "<%=request.getContextPath()%>/mbsz/saveConfig";
		            var mbmc = $('#mbmc1').val();
		            var xfsh = $('#config_xfsh1').val();
		            var gfmc = $('#config_gfmc1').val();
		            var spje = $('#config_spje1').val();
		            var fpzldm = $('#config_fpzldm1').val();
		            if (mbmc == null || mbmc == '') {
		            	alert("请输入模板名称");
		            	return;
					}
		            if (xfsh == null || xfsh == '') {
		            	alert("销方税号不能为空，请重新选择");
		            	return;
					}
		            if (gfmc == null || gfmc == '') {
		            	alert("购方名称不能为空");
		            	return;
					}
		            if (spje == null || spje == '') {
		            	alert("商品金额不能为空");
		            	return;
					}
		            if (fpzldm == null || fpzldm == '') {
		            	alert("发票种类不能为空");
		            	return;
					}
		            $.post(url, data, function (res) {
		                var success = res["success"];
		                if (success) {
		                    alert("保存成功");
		                    $("#bulk-import-div").modal("close");
	                        window.location.reload();
		                } else {
		                    var message = res["message"];
		                    alert(message);
		                }
		            });
		        });
		        //导入配置选择销方
		        $("#selectImportConfigXf").change(function () {
		            var xfid = $(this).val();
		            var url = "<%=request.getContextPath()%>/mbsz/getXfxxById";
		            $.post(url, {xfid: xfid}, function (res) {
		                if (res) {
		                    $("input[name=config_xfsh]").val(res["xfsh"]);
		                    $("input[name=config_xfmc]").val(res["xfmc"]);
		                    $("input[name=config_xfdz]").val(res["xfdz"]);
		                    $("input[name=config_xfdh]").val(res["xfdh"]);
		                    $("input[name=config_xfyh]").val(res["xfyh"]);
		                    $("input[name=config_xfyhzh]").val(res["xfyhzh"]);
		                    $("input[name=config_skr]").val(res["skr"]);
		                    $("input[name=config_kpr]").val(res["kpr"]);
		                    $("input[name=config_fhr]").val(res["fhr"]);
		                }
		            });
		        });
		        
		        //导入配置选择商品
		        $("#selectImportConfigSp").change(function () {
		            var spdm = $(this).val();
		            var spmc = $("#selectImportConfigSp option:checked").text();
		            var pos = spmc.indexOf("(");
		            spmc = spmc.substring(0, pos);
		            var url = "<%=request.getContextPath()%>/mbsz/getSpxq";
		            $.post(url, {spdm: spdm, spmc:spmc}, function (res) {
		                if (res) {
		                    $("input[name=config_spdm]").val(res["spdm"]);
		                    $("input[name=config_spmc]").val(res["spmc"]);
		                    $("input[name=config_spggxh]").val(res["spggxh"]);
		                    $("input[name=config_spdw]").val(res["spdw"]);
		                    $("input[name=config_spdj]").val(res["spdj"]);
		                    $("input[name=config_spsl]").val(res["sl"]);
		                }
		            })
		        });
		            
		            
		   
		            
		        //导入配置选择销方
		        $("#selectImportConfigXf1").change(function () {
		            var xfid = $(this).val();
		            var url = "<%=request.getContextPath()%>/mbsz/getXfxxById";
		            $.post(url, {xfid: xfid}, function (res) {
		                if (res) {
		                    $("input[name=config_xfsh]").val(res["xfsh"]);
		                    $("input[name=config_xfmc]").val(res["xfmc"]);
		                    $("input[name=config_xfdz]").val(res["xfdz"]);
		                    $("input[name=config_xfdh]").val(res["xfdh"]);
		                    $("input[name=config_xfyh]").val(res["xfyh"]);
		                    $("input[name=config_xfyhzh]").val(res["xfyhzh"]);
		                    $("input[name=config_skr]").val(res["skr"]);
		                    $("input[name=config_kpr]").val(res["kpr"]);
		                    $("input[name=config_fhr]").val(res["fhr"]);
		                }
		            });
		        });
		        
		        //导入配置选择商品
		        $("#selectImportConfigSp1").change(function () {
		            var spdm = $(this).val();
		            var spmc = $("#selectImportConfigSp option:checked").text();
		            var pos = spmc.indexOf("(");
		            spmc = spmc.substring(0, pos);
		            var url = "<%=request.getContextPath()%>/mbsz/getSpxq";
		            $.post(url, {spdm: spdm, spmc:spmc}, function (res) {
		                if (res) {
		                    $("input[name=config_spdm]").val(res["spdm"]);
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
