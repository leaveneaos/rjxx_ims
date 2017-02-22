<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票开具</title>
<meta name="description" content="发票开具">
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
<style type="text/css">
.am-table {
	margin-bottom: 0rem;
}

.right {
	text-align: right;
}
</style>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<!-- sidebar end -->
		<!-- content start -->
		<input type="hidden" id="djh" value="0">

		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>发票开具</strong>
				</div>
			</div>
			<hr />
			<div class="am-g">
				<form action="#"
								class="js-search-form  am-form am-form-horizontal">
					<div class="am-u-sm-12">
						<div class="am-u-lg-4">
							<div class="am-form-group">
								<label for="s_ddh" class="am-u-sm-4 am-form-label">选择销方</label>
								<div class="am-u-sm-8">
									<select id="s_xfsh" name="xfsh">
										<option value="">选择销方</option>
										<c:forEach items="${xfList}" var="item">
											<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="am-u-lg-4">
							<div class="am-form-group">
								<label for="s_gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
								<div class="am-u-sm-8">
									<input id="s_gfmc" type="text" class="am-form-field" placeholder="购方名称">
								</div>
							</div>
						</div>
						<!-- 		<div class="am-u-lg-3">
											<div class="am-input-group" style="width: 100%">
												<input type="text" class="am-form-field" placeholder="订单号">
											</div>
										</div> -->
						<div class="am-u-sm-2" style="margin-top: 13px;">
							<a></a>
						</div>
						<div class="am-u-sm-2 am-u-end">
							<div class="am-form-group" style="width: 100%">
								<button type="button" id="kp_kp" class="am-btn am-radius am-btn-success"
									style="width: 100%">
									<span></span> 开票
								</button>
							</div>
						</div>
					</div>
					<div class="am-u-sm-12">
						<div class="am-u-lg-4">
							<div class="am-form-group">
								<label for="s_ddh" class="am-u-sm-4 am-form-label">订单号</label>
								<div class="am-u-sm-8">
									<input id="s_ddh" type="text" class="am-form-field" placeholder="订单号">
								</div>
							</div>
						</div>
						<div class="am-u-lg-4">
							<div class="am-form-group">
								<label for="s_fplx" class="am-u-sm-4 am-form-label">发票类型</label>
								<div class="am-u-sm-8">
									<select id="s_fplx" name="xfsh">
										<option value="">选择类型</option>
										<option value="12">电子票</option>
										<option value="01">纸质专票</option>
										<option value="02">纸质普票</option>
									</select>
								</div>
							</div>
						</div>
						<div class="am-u-sm-2" style="margin-top: 13px;">
							<a><u>更多</u></a>
						</div>

						<div class="am-u-sm-2">
							<div class="am-form-group" style="width: 100%">
								<button type="button" id="kp_del" class="am-btn am-radius am-btn-danger"
									style="width: 100%">
									<span></span> 删除
								</button>
							</div>
						</div>
					</div>
					<div class="am-u-sm-12">
						<div class="am-u-sm-4">
							<label for="s_ddh" class="am-u-sm-4 am-form-label">开始时间</label>
							<div class="am-input-group am-datepicker-date am-u-sm-8"
								data-am-datepicker="{format: 'yyyy-mm-dd'}">
								<input type="text" id="s_rqq" class="am-form-field" placeholder="开始时间" readonly>
								<span class="am-input-group-btn am-datepicker-add-on">
									<button class="am-btn am-btn-default" type="button">
										<span class="am-icon-calendar"></span>
									</button>
								</span>
							</div>

						</div>
						<div class="am-u-sm-4">
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
						<div class="am-u-sm-2">
							<div class="am-form-group" style="margin-top: 13px;">
								<a href="javascript:void(0);" id="day3"><u>近3天</u></a> <a
									id="day7" href="javascript:void(0);" style="margin-left: 2%"><u>近7天</u></a>
								<a href="javascript:void(0);" id="day30"
									style="margin-left: 2%;"><u>近30天</u></a>
							</div>
						</div>
						<div class="am-u-sm-2">
							<div class="am-form-group" style="width: 100%">
								<button type="button" id="kp_search" style="width: 100%"
									class="am-btn am-radius am-btn-success">
									<span></span> 查询
								</button>
							</div>
						</div>
					</div>
				</form>
				<hr/>
				
				
				<%-- <fieldset>
					<hr>
					<div class="am-btn-toolbar ">
                     <div class="am-btn-group am-btn-group-xs ">
                         <span>处理状态：</span>
                         <span class="am-badge am-badge-success am-icon-check-circle">服务端生成XML并放入服务端IN队列完成</span>
                         <span class="am-badge am-badge-danger am-icon-clock-o">服务端接收数据完成</span>
                     </div>
                 </div>
				</fieldset>--%>
				<div class="am-u-sm-12">
				<hr/>
				<div>
					<table
						class="js-table2 am-table am-table-bordered am-text-nowrap"
						id="jyls_table">
						<thead>
							<tr>
								<th><input type="checkbox" id="check_all" /></th>
								<th>序号</th>
								<th>交易流水号</th>
								<th>订单号</th>
								<th>订单日期</th>
								<th>发票类型</th>
								<th>购方税号</th>
								<th>购方名称</th>
								<th>购方银行</th>
								<th>购方账号</th>
								<th>购方联系人</th>
								<th>购方地址</th>
								<!-- 	<th>购方邮件地址</th> -->
								<th>购方手机号</th>
								<th>备注</th>
								<th>价税合计</th>
							<!-- 	<th>操作</th> -->
							</tr>
						</thead>
					</table>
				</div>
				</div>
				<fieldset>
					<legend>商品明细列表</legend>
					<div class="am-u-sm-12">
					<div>
					<table
						class="js-mxtable am-table am-table-bordered am-table-striped am-text-nowrap"
						id="jyspmx_table">
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
				</fieldset>
			</div>
		</div>
		<!-- content end -->
	</div>


	<div class="am-modal am-modal-no-btn" tabindex="-1"
		id="bulk-import-div">
		<div class="am-modal-dialog">
			<div class="am-modal-hd am-modal-footer-hd">
				批量导入 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal" id="importExcelForm"
					method="post" action="<%=request.getContextPath()%>/kp/importExcel"
					enctype="multipart/form-data">
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<input type="file" class="am-u-sm-12" id="importFile"
								name="importFile" placeholder="选择要上传的文件" required>
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
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label">默认模板</label>
							<div class="am-u-sm-8">
								<select id="mrmb" name="mrmb" class="am-u-sm-12">
									<option value="${mrmb.id}">${mrmb.mbmc}</option>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12" style="margin-top: 10px;">
							<button type="button" id="btnImport"
								class="am-btn am-btn-xs am-btn-primary">导入</button>
							<button type="button" id="btnMrConfig"
								class="am-btn am-btn-success am-btn-xs">设置默认模板</button>
							<button type="button" id="close1"
								class="am-btn am-btn-danger am-btn-xs">关闭</button>
							<!-- 							<button type="button" id="btnImportConfig" -->
							<!-- 								class="am-btn am-btn-secondary am-btn-xs">新增模板</button> -->
							<button type="button" id="update1"
								class="am-btn am-btn-secondary am-btn-xs">查看模板</button>
							<!-- 							<button type="button" id="delete1" -->
							<!-- 								class="am-btn am-btn-danger am-btn-xs">删除模板</button> -->

						</div>
						<div class="am-u-sm-12" style="margin-top: 10px;">
							<a href="javascript:void(0)" id="btnDownloadDefaultTemplate"
								style="text-decoration: underline;">下载默认模板</a>
							<!-- 								<a -->
							<!-- 								href="javascript:void(0)" id="btnImportConfig" -->
							<!-- 								style="text-decoration: underline; margin-left: 10px;">配置导入文件对照关系</a> -->
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="am-modal am-modal-no-btn" tabindex="-1"
		id="import_config_div">
		<div class="am-modal-dialog">
			<div class="am-modal-hd am-modal-footer-hd">
				导入配置 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>

			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal" id="importConfigForm">
					<div class="am-form-group" style="overflow-y: auto; height: 400px;">
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
									<input type="hidden" id="pre_zd" name="pre_zd" placeholder="">
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
										placeholder="如无可不填" required>
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
										placeholder="如无可不填">
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
										placeholder="如无可不填" required>
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
										placeholder="如无可不填" required>
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
										placeholder="如无可不填" required>
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
							<button type="button" id="close2"
								class="am-btn am-btn-danger am-btn-xs">关闭</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>


	<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-alert-edit">
		<div class="am-modal-dialog" style="overflow: auto">
			<div class="am-modal-hd am-modal-footer-hd">
				录入开票 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<div class="am-alert am-alert-success" data-am-alert id="myinfoalert"
				style="display: none">
				<button type="button" class="am-close">&times</button>
				<p id="infomessage"></p>
			</div>
			<div class="am-tabs am-margin" data-am-tabs id="main_tab">
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">基础信息</a></li>
					<li><a href="#tab2" class="ai">商品明细</a></li>
				</ul>

				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
						<form class="am-form am-form-horizontal" id="main_form">
							<fieldset>
								<input type="hidden" id="formid">
								<div class="am-form-group">
									<label for="xfid_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>销方名称</label>
									<div class="am-u-sm-4">
										<select id="select_xfid" name="xfid_edit" onchange="getKpd()"
											required>
											<option value="">---选择销方---</option>
											<c:forEach items="${xfList}" var="item">
												<option value="${item.id}">${item.xfmc}</option>
											</c:forEach>
										</select>
									</div>
									<label for="skpid_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>开票点名称</label>
									<div class="am-u-sm-4">
										<select id="select_skpid" name="skpid_edit" required>

										</select>
									</div>
								</div>
								<div class="am-form-group">
									<label for="ddh_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>订单号</label>

									<div class="am-u-sm-4">
										<input type="text" id="ddh_edit" name="ddh_edit"
											placeholder="输入订单号..." required>
									</div>
									<label for="gfsh_edit" class="am-u-sm-2 am-form-label">购方税号</label>

									<div class="am-u-sm-4">
										<input type="text" id="gfsh_edit" name="gfsh_edit"
											placeholder="输入购方税号...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="gfmc_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>购方名称</label>

									<div class="am-u-sm-4">
										<input type="text" id="gfmc_edit" name="gfmc_edit"
											placeholder="输入购方名称..." required>
									</div>
									<label for="gfyh_edit" class="am-u-sm-2 am-form-label">购方银行</label>

									<div class="am-u-sm-4">
										<input type="text" id="gfyh_edit" name="gfyh_edit"
											placeholder="输入购方银行...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="gfzh_edit" class="am-u-sm-2 am-form-label">银行账号</label>

									<div class="am-u-sm-4">
										<input type="text" id="gfzh_edit" name="gfzh_edit"
											placeholder="输入购方银行账号...">
									</div>
									<label for="gflxr_edit" class="am-u-sm-2 am-form-label">购方联系人</label>

									<div class="am-u-sm-4">
										<input type="text" id="gflxr_edit" name="gflxr_edit"
											placeholder="输入购方联系人...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="gfemail_edit" class="am-u-sm-2 am-form-label">购方邮件</label>

									<div class="am-u-sm-4">
										<input type="text" id="gfemail_edit" name="gfemail_edit"
											placeholder="输入购方邮件地址...">
									</div>
									<label for="gfsjh_edit" class="am-u-sm-2 am-form-label">手机号</label>
									<div class="am-u-sm-4">
										<input type="text" id="gfsjh_edit" name="gfsjh_edit"
											placeholder="输入购方手机号...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="gfdz_edit" class="am-u-sm-2 am-form-label">购方地址</label>

									<div class="am-u-sm-4">
										<input type="text" id="gfdz_edit" name="gfdz_edit"
											placeholder="输入购方地址...">
									</div>
									<label for="tqm_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>提取码</label>

									<div class="am-u-sm-4">
										<input type="text" id="tqm_edit" name="tqm_edit"
											placeholder="输入提取码..." required>
									</div>
								</div>
								<div class="am-form-group">

									<label for="gfbz_edit" class="am-u-sm-2 am-form-label">备注</label>

									<div class="am-u-sm-10">
										<input type="text" id="gfbz_edit" name="gfbz_edit"
											placeholder="输入备注信息...">
									</div>
									<!-- 									<label for="gfsjh_edit" class="am-u-sm-2 am-form-label">购方手机号</label> -->
									<!-- 									<div class="am-u-sm-4"> -->
									<!-- 										<input type="text" id="gfsjh_edit" name="gfsjh_edit" -->
									<!-- 											placeholder="输入购方手机号..."> -->
									<!-- 									</div> -->
								</div>
							</fieldset>
						</form>
					</div>
					<div class="am-tab-panel am-fade" id="tab2">
						<form class="am-form am-form-horizontal" id="mx_form">
							<fieldset>
								<div class="am-form-group">
									<label for="select_sp" class="am-u-sm-2 am-form-label">选择商品</label>

									<div class="am-u-sm-4">
										<select id="select_sp" name="select_sp">
											<option value="">---选择商品---</option>
											<c:forEach items="${spList}" var="item">
												<option value="${item.spdm}">${item.spmc}(${item.spdm})</option>
											</c:forEach>
										</select>
									</div>

									<label for="spdm_edit" class="am-u-sm-2 am-form-label">商品代码</label>

									<div class="am-u-sm-4">
										<input type="text" id="spdm_edit" placeholder="输入商品代码..."
											readonly="readonly">
									</div>
								</div>
								<div class="am-form-group">
									<label for="mc_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>名称</label>

									<div class="am-u-sm-4">
										<input type="text" id="mc_edit" placeholder="输入名称..." required>
									</div>
									<label for="ggxh_edit" class="am-u-sm-2 am-form-label">规格型号</label>

									<div class="am-u-sm-4">
										<input type="text" id="ggxh_edit" placeholder="输入规格型号...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="dw_edit" class="am-u-sm-2 am-form-label">单位</label>

									<div class="am-u-sm-4">
										<input type="text" id="dw_edit" placeholder="输入单位...">
									</div>
									<label for="sl_edit" class="am-u-sm-2 am-form-label">数量</label>

									<div class="am-u-sm-4">
										<input type="text" id="sl_edit" placeholder="输入数量...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="dj_edit" class="am-u-sm-2 am-form-label">单价</label>

									<div class="am-u-sm-4">
										<input type="text" id="dj_edit" placeholder="输入单价...">
									</div>
									<label for="je_edit" class="am-u-sm-2 am-form-label"
										style="padding-left: 0px"><span style="color: red;">*</span>金额(不含税)</label>

									<div class="am-u-sm-4">
										<input type="text" id="je_edit" placeholder="输入金额(不含税)..."
											required>
									</div>
								</div>
								<label for="hsje_edit" class="am-u-sm-2 am-form-label"><span
									style="color: red;">*</span>金额(含税)</label>

								<div class="am-u-sm-4">
									<input type="text" id="hsje_edit" placeholder="输入金额(含税)..."
										required>
								</div>
								<div class="am-form-group">
									<label for="jshj_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>价税合计</label>

									<div class="am-u-sm-4">
										<input type="text" id="jshj_edit" readonly="readonly"
											placeholder="输入价税合计...">
									</div>
								</div>
								<div class="am-form-group">
									<label for="sltaxrate_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>税率</label>

									<div class="am-u-sm-4">
										<input type="text" id="sltaxrate_edit" value="0.17"
											placeholder="" required readonly>
									</div>
									<label for="se_edit" class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>税额</label>

									<div class="am-u-sm-4">
										<input type="text" id="se_edit" placeholder="" required
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
							class="am-table am-text-nowrap am-table-striped am-table-bordered am-table-compact table-main am-scrollable-horizontal"
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
					<button type="button" id="save"
						class="am-btn am-btn-xs am-btn-secondary">提交保存</button>
					<button type="button" id="close"
						class="am-btn am-btn-danger am-btn-xs">关闭</button>
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
	<div id="fpjek" class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn"
		tabindex="-1">
	<div class="am-modal-dialog" >
	<br/>
		<p>是否确认开票?分票金额为 </p>
		<input id="fpjesrk" type="text" class="am-text-money" value=""/>
		<br/>
		<div class="am-margin">
			<button type="button" id="savet"
				class="am-btn am-btn-xs am-btn-secondary">确认</button>
			<button type="button" id="closet"
				class="am-btn am-btn-danger am-btn-xs">关闭</button>
		</div>
	<br/>
	</div>
	</div>
	<form id="downloadDefaultImportTemplateForm"
		action="<%=request.getContextPath()%>/kp/downloadDefaultImportTemplate"
		method="post" target="downloadFrame"></form>
	<iframe name="downloadFrame" style="display: none;"> </iframe>
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
	<script src="assets/js/kp_app.js"></script>
	<script src="assets/js/format.js"></script>
	<script>
    $(document).ready(function () {   	
        //初始化导入配置
        var url = "<%=request.getContextPath()%>/kp/initImportConfig";
        $.post(url, {}, function (res) {
            if (res && res.length > 0) {
                for (var i = 0; i < res.length; i++) {
                    var obj = res[i];
                    var zdm = obj["zdm"];
                    var pzlx = obj["pzlx"];
                    var pzz = obj["pzz"];
                    $("input[name=config_" + zdm + "_radio][value=" + pzlx + "]").attr("checked", true);
                    if ("hsbz" == zdm) {
                        $("input[name=config_" + zdm + "][value=" + pzz + "]").attr("checked", true);
                    } else {
                        $("input[name=config_" + zdm + "]").val(pzz);
                    }
                }
            }
        });
        
        $('#jyls_table tr td').attr("class","weizhi");
        
        $('#mbid').hide();
        
        

        //录入订单时选择商品
        $("#select_sp").change(function () {
            var spdm = $(this).val();
            var spmc = $("#select_sp option:checked").text();
            var pos = spmc.indexOf("(");
            spmc = spmc.substring(0, pos);
            if (!spdm) {
                $("#mx_form input").val("");
                return;
            }
            var url = "<%=request.getContextPath()%>/kp/getSpxq";
            $.post(url, {spdm: spdm,spmc:spmc}, function (res) {
                if (res) {
                    $("#mx_form #spdm_edit").val(res["spdm"]);
                    $("#mx_form #mc_edit").val(res["spmc"]);
                    $("#mx_form #ggxh_edit").val(res["spggxh"] == null ? "" : res["spggxh"]);
                    $("#mx_form #dw_edit").val(res["spdw"] == null ? "" : res["spdw"]);
                    $("#mx_form #dj_edit").val(res["spdj"] == null ? "" : res["spdj"]);
                    $("#mx_form #sltaxrate_edit").val(res["sl"]);
                }
            })
        });
        
        var je = $('#je_edit');
        var sl = $('#sltaxrate_edit');
        var se = $('#se_edit');
        var hsje = $('#hsje_edit');
        var jshj = $('#jshj_edit');
        $("#je_edit").blur(function () {
        	var temp = (100+sl.val()*100)/100;
			se.val(FormatFloat(je.val() * sl.val(), "#####0.00"));
			var je1 = parseFloat(je.val());
        	var se1 = parseFloat(se.val());
			hsje.val(FormatFloat(je1 + se1, "#####0.00"));
			jshj.val(FormatFloat(je1 + se1, "#####0.00"));
        	if (dj != null && dj.val() != "") {
        		sps.val(FormatFloat(je.val() / dj.val(), "#####0.00"));
			}else if(sps != null && sps.val() != ""){
				dj.val(FormatFloat(je.val() / spl.val(), "#####0.00"));
			}
        });
        $("#hsje_edit").blur(function () {
        	var temp = (100+sl.val()*100)/100;
        	je.val(FormatFloat(hsje.val()/(temp), "#####0.00"));
			se.val(FormatFloat(hsje.val() -je.val(), "#####0.00"));
			jshj.val(FormatFloat(hsje.val(), "#####0.00"));
        });
        
        //导入配置选择商品
        $("#selectImportConfigSp").change(function () {
            var spdm = $(this).val();
            var spmc = $("#selectImportConfigSp option:checked").text();
            var pos = spmc.indexOf("(");
            spmc = spmc.substring(0, pos);
            var url = "<%=request.getContextPath()%>/kp/getSpxq";
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
        //导入选择销方模板
        $("#mb_xfsh").change(function () {
            var xfsh = $(this).val();
            $('#mb').empty();
            $('#mb_skp').empty();
            $('#mrmb').empty();
            if (xfsh == null || xfsh == '' || xfsh == "") {
				return;
			}
            var url = "<%=request.getContextPath()%>/kp/getSkpList";
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
            url = "<%=request.getContextPath()%>/kp/getTemplate";
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
            url = "<%=request.getContextPath()%>/kp/getMrmb";
            $.post(url, {xfsh: xfsh}, function (data) {
                if (data) {
                    	var option = $("<option>").text(data.mrmb.mbmc).val(data.mrmb.id);
                    	$('#mrmb').append(option);
                }
            });
        });
        //导入配置选择销方
        $("#selectImportConfigXf").change(function () {
            var xfid = $(this).val();
            var url = "<%=request.getContextPath()%>/kp/getXfxxById";
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
        //保存导入模板配置
        $("#btnImportConfigSave").click(function () {
            var data = $("#importConfigForm").serialize();
            var url = "<%=request.getContextPath()%>/kp/saveImportConfig";
            var mbmc = $('#mbmc').val();
            var xfsh = $('#config_xfsh').val();
            if (mbmc == null || mbmc == '') {
            	alert("请输入模板名称");
            	return;
			}
            if (xfsh == null || xfsh == '') {
            	alert("销方税号不能为空，请重新选择");
            	return;
			}
            $.post(url, data, function (res) {
                var success = res["success"];
                if (success) {
                    alert("保存成功");
                    var option = $("<option>").text(res.drmb.mbmc).val(res.drmb.id);
                	$('#mb').append(option);
                    $("#import_config_div").modal("close");
                } else {
                    var message = res["message"];
                    alert(message);
                }
            });
        });
        //切换默认与导入文件radio
        $("input[type=radio][name^=config_][name$=_radio]").click(function () {
            var obj = $(this);
            var name = obj.attr("name");
            if (name.indexOf("jylsh") != -1) {
                var value = obj.val();
                if (value == 'auto') {
                    $("input[name=config_jylsh]").val("").attr("readonly", true);
                } else {
                    $("input[name=config_jylsh]").attr("readonly", false);
                }
            } else if (name.indexOf("ddh") != -1) {
                var value = obj.val();
                if (value == 'auto') {
                    $("input[name=config_ddh]").val("").attr("readonly", true);
                } else {
                    $("input[name=config_ddh]").attr("readonly", false);
                }
            } else if (name.indexOf("spse") != -1) {
                var value = obj.val();
                if (value == 'auto') {
                    $("input[name=config_spse]").val("").attr("readonly", true);
                } else {
                    $("input[name=config_spse]").attr("readonly", false);
                }
            }
        });

        $("#update1").click(function () {
            //导入配置
            var $importConfigModal = $("#import_config_div");
            var $importModal = $("#importExcelForm");
        	var mbid = $('#mb').val();
        	var xfsh = $('#mb_xfsh').val();
        	var mbmc = $("#mb option:checked").text();
        	if (mbid == null || mbid == "" || mbid == -1) {
				alert('请选择要修改的销方导入模板');
				return;
			}
            $importModal.modal("close");
            var url = "<%=request.getContextPath()%>/kp/initImport";
            $.post(url, {mbid:mbid,xfsh:xfsh}, function (res) {
                if (res && res.length > 0) {
                    for (var i = 0; i < res.length; i++) {
                        var obj = res[i];
                        var zdm = obj["zdm"];
                        var pzlx = obj["pzlx"];
                        var pzz = obj["pzz"];
                        $("input[name=config_" + zdm + "_radio][value=" + pzlx + "]").prop("checked", true);
                        if ("hsbz" == zdm) {
                            $("input[name=config_" + zdm + "][value=" + pzz + "]").prop("checked", true);
                        } else {
                            $("input[name=config_" + zdm + "]").val(pzz);
                        }
                    }
					
                    $('#mbmc').val(mbmc);
                    $('#mbid').val(mbid);
                }
            });
            var url = "<%=request.getContextPath()%>/kp/getMb";
            $.post(url, {mbid:mbid}, function (res) {
                if (res) {
                    if (res.gxbz == "1") {
                    	$('#yes').prop("checked", true);						
					}else{
						$('#no').prop("checked", true);
					}
                }
            });
            $importConfigModal.modal({"width": 600, "height": 480});
            
        });
        $("#delete1").click(function () {
        	var mbid = $('#mb').val();
        	if (mbid == null || mbid == "") {
				alert('请选择要删除的销方导入模板');
				return;
			}
        	if (confirm("您确认删除？")) {
                $.post("<%=request.getContextPath()%>/kp/deleteMb",
						"mbid="+ mbid,
						function(res) {
							if (res.success) {
								$("#mb option[value="+mbid+"]").remove();
								alert("删除成功");
							}else{
								alert(res.msg);
							}
				});
			}
        });
        
        //下载默认导入模板
        $("#btnDownloadDefaultTemplate").click(function () {
        		window.location.href='kp/downloadDefaultImportTemplate';
//             $("#downloadDefaultImportTemplateForm").submit();
        });
        //导入excel
        $("#btnImport").click(function () {
            var filename = $("#importFile").val();
            var xfsh = $("#mb_xfsh").val();
            var mb = $("#mb").val();
            var mrmb = $("#mrmb").val();
            var skpid = $("#mb_skp").val();
            if (!xfsh) {
                alert("请选择要导入的销方");
                return;
            }
            if (!skpid) {
                alert("请选择要导入的开票点");
                return;
            }
            if (!mb && !mrmb) {
                alert("请选择要导入的模板或设置默认模板,如无模板请添加模板后再导入");
                return;
            }
            if (!filename) {
                alert("请选择要导入的文件");
                return;
            }
            var pos = filename.lastIndexOf(".");
            if (pos == -1) {
                alert("导入的文件必须是excel文件");
                return;
            }
            var extName = filename.substring(pos + 1);
            if ("xls" != extName && "xlsx" != extName) {
                alert("导入的文件必须是excel文件");
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
                        alert("导入成功，共导入" + count + "条数据");
                        window.location.reload();
                        if (res["yes"]) {
                			$('#mrmb').empty();
                			var txt = $('#mb').find("option:selected").text();
        					var option = $("<option>").text(txt).val(mbid);
                        	$('#mrmb').append(option);
						}
                    } else {
                        $("#btnImport").attr("disabled", false);
                        $('.js-modal-loading').modal('close');
                        alert(res["message"]);
                    }
                }
            };
            $("#importExcelForm").ajaxSubmit(options);
        });
        
        $('#btnMrConfig').click(function () {
        	var xfsh = $('#mb_xfsh').val();
        	var mbid = $('#mb').val();
        	if (!xfsh) {
				alert('请选择销方！')
				return;
			}
        	if (!mbid) {
				alert('请选择模板！')
				return;
			}
        	
        	var url = "<%=request.getContextPath()%>/kp/saveMb";
        	$.post(url, {mbid:mbid,xfsh:xfsh}, function (res) {
        		if (res.success) {
        			$('#mrmb').empty();
        			var txt = $('#mb').find("option:selected").text();
					var option = $("<option>").text(txt).val(mbid);
                	$('#mrmb').append(option);
					alert("设置成功");
				}else{
					alert(msg);
				}
            });
        });
        //删除
        $("#kp_del").click(function () {
            var djhArr = [];
            $('input[name="chk"]:checked').each(function(){    
                djhArr.push($(this).val()); 
        });
            if (djhArr.length == 0) {
                alert("请选择需要删除的交易流水...");
                return;
            }
            if (confirm("您确认删除？")) {
                $.post("<%=request.getContextPath()%>/kp/doDel",
						"djhArr="+ djhArr.join(","),
						function(res) {
							if (res) {
								alert("删除成功");
								window.location.reload(true);
							}
				});
			}
		});
	});
        
	//选择销方取得税控盘
	function getKpd(){
		var xfid =  $('#select_xfid option:selected').val();
		var skpid = $("#select_skpid");
		$("#select_skpid").empty();
		$.ajax({
			url:"fpkc/getKpd",
			data:{"xfid":xfid},
			success:function(data){
				for(var i=0;i<data.length;i++){
					var option = $("<option>").text(data[i].kpdmc).val(data[i].skpid);
				    skpid.append(option);
				}
			}
			
		});
	};
	</script>

</body>
</html>