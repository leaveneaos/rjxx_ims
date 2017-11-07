<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>录入开票单</title>
<meta name="description" content="录入开票单">
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
<script src="assets/js/loading.js"></script>
<style type="text/css">
.am-table {
	margin-bottom: 0rem;
}
table thead th { text-align: center; }


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
					<!-- sidebar start -->
					<!-- sidebar end -->
					<!-- content start -->
					<input type="hidden" id="djh" value="0">
					<input type="hidden" id="djh2" value="0">
<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
  <ul class="am-tabs-nav am-nav am-nav-tabs">
    <li class="am-active"><a href="#tab-1-1">录入开票单</a></li>
    <li><a href="#tab-1-2">已上传</a></li>
  </ul>

 <div class="am-tabs-bd am-tabs-bd-ofv">
					<div class="admin-content am-tab-panel am-active" id ="tab-1-1">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">业务处理</strong> /<strong>录入开票单</strong>
								<button class="am-btn am-btn-success am-fr"
									data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
							</div>

							<!-- 侧边栏内容 begin-->
							<div id="doc-oc-demo3" class="am-offcanvas">
								<div class="am-offcanvas-bar am-offcanvas-bar-flip">
									<form id="ycform">
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="xfsh" class="am-u-sm-4 am-form-label">选择销方</label>
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
										<div class="am-offcanvas-content" style="margin-top: 5px;">
											<div class="am-form-group">
												<label for="gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
												<div class="am-u-sm-8">
													<input id="gfmc" type="text" placeholder="购方名称">
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 5px;">
											<div class="am-form-group">
												<label for="ddh" class="am-u-sm-4 am-form-label">订单号</label>
												<div class="am-u-sm-8">
													<input id="ddh" type="text" placeholder="订单号">
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="fplxdm" class="am-u-sm-4 am-form-label">发票类型</label>
												<div class="am-u-sm-8">
													<select data-am-selected="{btnSize: 'sm'}" id="fplxdm"
														name="xfsh">
														<option id="xzlxq" value="">选择类型</option>
														<option value="12">电子发票</option>
														<option value="01">专用发票</option>
														<option value="02">普通发票</option>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="kssj" class="am-u-sm-4 am-form-label">开始时间</label>
												<div class="am-input-group am-datepicker-date am-u-sm-8"
													data-am-datepicker="{format: 'yyyy-mm-dd'}">
													<input type="text" id="kssj" class="am-form-field"
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
												<label for="jssj" class="am-u-sm-4 am-form-label">截止时间</label>
												<div class="am-input-group am-datepicker-date am-u-sm-8"
													data-am-datepicker="{format: 'yyyy-mm-dd'}">
													<input type="text" id="jssj" class="am-form-field"
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
												<button type="button" id="kp_add"
													class="am-btn am-btn-default am-btn-primary">
													录入
												</button>
												<button type="button" id="kp_dr"
													class="am-btn am-btn-default am-btn-default">
													导入
												</button>
												<button type="button" id="kp_del"
													class="am-btn am-btn-default am-btn-danger">
													删除
												</button>
												<button type="button" id="kp_kp"
													class="am-btn am-btn-default am-btn-secondary">
													 上传
												</button>
											</div>
										</div>
									</div>
								</div>
								<!-- <div class="am-u-sm-2">
							<div class="am-form-group" style="width: 100%;margin-top:16px">
								<a href="javascript:void(0)" id="day3"><u>近3天</u></a>
								<a href="javascript:void(0)" id="day7" ><u>近7天</u></a>
							    <a href="javascript:void(0)" id="day30" ><u>近30天</u></a>
							</div>
						</div> -->
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
							<div style="margin-left: 10px" class="am-u-sm-12 am-padding-top">
								<table
									class="js-table am-table am-table-bordered am-text-nowrap am-scrollable-horizontal"
									id="jyls_table">
									<thead>
										<tr>
											<th><input type="checkbox" id="check_all" /></th>
											<th width="80">交易流水号</th>
											<th width="80">订单号</th>
											<th width="80">订单日期</th>
											<th>发票类型</th>
											<th>购方名称</th>
											<th>购方税号</th>
											<th>地址</th>
											<th>电话</th>
											<th>开户行</th>
											<th>开户账号</th>
											<th>价税合计</th>
											<!-- <th>录入人员</th> -->
											<th>操作</th>
										</tr>
									</thead>
								</table>
							</div>
							<fieldset>
								<legend>商品明细列表</legend>
								<table
									class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap"
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
											<th>操作</th>
										</tr>
									</thead>
								</table>
							</fieldset>
						</div>
					</div>
					<!-- tab1-1-2 content begin -->

					<div class="am-tab-panel" id ="tab-1-2">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">业务处理</strong> /<strong>已上传</strong>
								<button class="am-btn am-btn-success am-fr"
									data-am-offcanvas="{target: '#doc-oc-demo4'}">更多查询</button>
							</div>

							<!-- 侧边栏内容 begin-->
							<div id="doc-oc-demo4" class="am-offcanvas">
								<div class="am-offcanvas-bar am-offcanvas-bar-flip">
									<form id="ycform2">
										<div class="am-offcanvas-content">
											<div class="am-form-group">
												<label for="xfsh2" class="am-u-sm-4 am-form-label">选择销方</label>
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
												<label for="gfmc2" class="am-u-sm-4 am-form-label">购方名称</label>
												<div class="am-u-sm-8">
													<input id="gfmc2" type="text" placeholder="购方名称">
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 5px;">
											<div class="am-form-group">
												<label for="ddh2" class="am-u-sm-4 am-form-label">订单号</label>
												<div class="am-u-sm-8">
													<input id="ddh2" type="text" placeholder="订单号">
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="fplxdm2" class="am-u-sm-4 am-form-label">发票类型</label>
												<div class="am-u-sm-8">
													<select data-am-selected="{btnSize: 'sm'}" id="fplxdm2"
														name="xfsh">
														<option id="xzlxq2" value="">选择类型</option>
														<option value="12">电子发票</option>
														<option value="01">专用发票</option>
														<option value="02">普通发票</option>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content" style="margin-top: 8px;">
											<div class="am-form-group">
												<label for="kssj2" class="am-u-sm-4 am-form-label">开始时间</label>
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
												<label for="jssj2" class="am-u-sm-4 am-form-label">截止时间</label>
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
						                 <p>
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
							<div style="margin-left: 10px" class="am-u-sm-12 am-padding-top">
								<table
									class="js-table am-table am-table-bordered am-text-nowrap am-scrollable-horizontal"
									id="jyls_table2">
									<thead>
										<tr>
											<th><input type="checkbox" id="check_all2" /></th>
											<th width="80">交易流水号</th>
											<th width="80">订单号</th>
											<th width="80">订单日期</th>
											<th>发票类型</th>
											<th>购方名称</th>
											<th>购方税号</th>
											<th>地址</th>
											<th>电话</th>
											<th>开户行</th>
											<th>开户账号</th>
											<th>价税合计</th>
										</tr>
									</thead>
								</table>
							</div>
							<fieldset>
								<legend>商品明细列表</legend>
								<table
									class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap"
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
							</fieldset>
						</div>
					</div>
					<!-- tab1-1-2 content end -->
				 </div>
					<!-- content end -->
					
			</div>
		</div>


				<div class="am-modal am-modal-no-btn" tabindex="-1"
					id="bulk-import-div">
					<div class="am-modal-dialog">
						<div class="am-modal-hd am-modal-footer-hd">
							批量导入
						</div>

						<div class="am-tab-panel am-fade am-in am-active">
							<form class="am-form am-form-horizontal" id="importExcelForm"
								method="post"
								action="<%=request.getContextPath()%>/lrkpd/importExcel"
								enctype="multipart/form-data">
								<div class="am-form-group">
									<div class="am-u-sm-12">
										<input type="file" class="am-u-sm-12" id="importFile"
											name="importFile" placeholder="选择要上传的文件"  accept="application/vnd.ms-excel"  required>
									</div>
									<div class="am-u-sm-12">
										<label class="am-u-sm-4 am-form-label">选择销方</label>
										<div class="am-u-sm-8">
											<select id="mb_xfsh" name="mb_xfsh" class="am-u-sm-12">
												<c:if test="${xfSum > 1}">
													<option value="1">---请选择---</option>
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
													<option value="1">---请选择---</option>
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
													<option value="1">---请选择---</option>
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
											style="text-decoration: underline;">下载默认模板</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="am-modal am-modal-no-btn" tabindex="-1"
					id="my-alert-edit">
					<div class="am-modal-dialog" style="overflow: auto">
						<div class="am-modal-hd am-modal-footer-hd">
							开票单录入<!-- <a href="javascript: void(0)"
								class="am-close am-close-spin" data-am-modal-close>&times;</a> -->
						</div>
						<div class="am-alert am-alert-success" data-am-alert
							id="myinfoalert" style="display: none">
							<button type="button" class="am-close">&times</button>
							<p id="infomessage"></p>
						</div>
						<div class="am-tabs am-margin" data-am-tabs="{noSwipe: 1}" id="main_tab">
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
												<label for="select_xfid" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>销方名称</label>
												<div class="am-u-sm-4">
													<select id="select_xfid" name="xfid_edit"
														onchange="getKpd()" required>
														<option value="">选择销方</option>
														<c:forEach items="${xfList}" var="item">
															<option value="${item.id}">${item.xfmc}</option>
														</c:forEach>
													</select>
												</div>
												<label for="select_skpid" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>开票点名称</label>
												<div class="am-u-sm-4">
													<select id="select_skpid" name="skpid_edit" required>

													</select>
												</div>
											</div>
											<div class="am-form-group">
												<label for="fpzl_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>发票种类</label>

												<div class="am-u-sm-4 am-u-end">
													<select id="fpzl_edit" name="fpzl_edit"
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
													<input type="text" id="gfmc_edit" name="gfmc_edit"
														placeholder="输入购方名称..." required>
												</div>

											</div>
											<div class="am-form-group">
												<label for="gfsh_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red; display: none" id="span_gfsh">*</span>购方税号</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfsh_edit" name="gfsh_edit" class ="js-pattern-Taxid"
														placeholder="输入购方税号...">
												</div>
												<label for="gfyh_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red; display: none" id="span_gfyh">*</span>购方银行</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfyh_edit" name="gfyh_edit"
														placeholder="输入购方银行...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="gfzh_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red; display: none" id="span_gfyhzh">*</span>银行账号</label>

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
												<label for="gfdh_edit" class="am-u-sm-2 am-form-label">购方电话</label>
												<div class="am-u-sm-4">
													<input type="text" id="gfdh_edit" name="gfdh_edit"
														placeholder="输入购方电话...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="gfdz_edit" class="am-u-sm-2 am-form-label">购方地址</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfdz_edit" name="gfdz_edit"
														placeholder="输入购方地址...">
												</div>
												<label for="tqm_edit" class="am-u-sm-2 am-form-label">提取码</label>

												<div class="am-u-sm-4">
													<input type="text" id="tqm_edit" name="tqm_edit"
														placeholder="输入提取码...">
												</div>
											</div>
											<div class="am-form-group">

												<label for="ddh_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>订单号</label>

												<div class="am-u-sm-4">
													<input type="text" id="ddh_edit" name="ddh_edit"
														placeholder="输入订单号..." required>
												</div>
												<label for="gfsjh_edit" class="am-u-sm-2 am-form-label">手机号</label>
												<div class="am-u-sm-4">
													<input type="text" id="gfsjh_edit" name="gfsjh_edit"
														placeholder="输入购方手机号...">
												</div>
											</div>
											<div class="am-form-group">

												<label for="gfbz_edit" class="am-u-sm-2 am-form-label">备注</label>

												<div class="am-u-sm-10">
													<input type="text" id="gfbz_edit" name="gfbz_edit"
														placeholder="输入备注信息...">
												</div>
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
														<option value="">选择商品</option>
														<c:forEach items="${spList}" var="item">
															<option value="${item.spbm}" class="${item.id}">${item.spmc}(${item.spbm})</option>
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
													<input type="text" id="mc_edit" placeholder="输入名称..."
														required>
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
									class="am-btn am-btn-xs am-btn-secondary">保存</button>
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


				<div class="am-modal am-modal-no-btn" tabindex="-1"
					id="my-alert-modify" style="width: 800px;">
					<div class="am-modal-dialog" style="overflow: auto; height: 450px;">
						<div class="am-modal-hd am-modal-footer-hd">
							修改开票单<!--  <a href="javascript: void(0)"
								class="am-close am-close-spin" data-am-modal-close>&times;</a> -->
						</div>
						<div class="am-alert am-alert-success" data-am-alert
							id="myinfoalert" style="display: none">
							<button type="button" class="am-close">&times</button>
							<p id="infomessage"></p>
						</div>
						<div class="am-tabs am-margin" id="main_tab">
							<form class="am-form am-form-horizontal" id="main_form_modify">
								<fieldset>
									<input type="hidden" id="formid_modify" name="sqlsh">
									<div class="am-form-group">
										<label for="select_xfid_modify" class="am-u-sm-2 am-form-label"><span
											style="color: red;">*</span>销方名称</label>
										<div class="am-u-sm-4">
											<select id="select_xfid_modify" name="xfid" required>
												<option value="">选择销方</option>
												<c:forEach items="${xfList}" var="item">
													<option value="${item.xfsh}">${item.xfmc}</option>
												</c:forEach>
											</select>
										</div>
										<label for="select_skpid_modify" class="am-u-sm-2 am-form-label"><span
											style="color: red;">*</span>开票点名称</label>
										<div class="am-u-sm-4">
											<select id="select_skpid_modify" name="skpid" required>

											</select>
										</div>
									</div>
									<div class="am-form-group">
										<label for="fpzl_edit" class="am-u-sm-2 am-form-label"><span
											style="color: red;">*</span>发票种类</label>

										<div class="am-u-sm-4 am-u-end">
											<select id="fpzl_modify" name="fpzldm" onchange="hidespan2()"
												required>
												<option value="">选择开票类型</option>
												<option value="01">专用发票</option>
												<option value="02">普通发票</option>
												<option value="12">电子发票</option>
											</select>
										</div>
										<label for="gfmc_edit" class="am-u-sm-2 am-form-label"><span
											style="color: red;">*</span>购方名称</label>

										<div class="am-u-sm-4">
											<input type="text" id="gfmc_modify" name="gfmc"
												placeholder="输入购方名称..." required>
										</div>

									</div>
									<div class="am-form-group">
										<label for="gfsh_edit" class="am-u-sm-2 am-form-label"><span
											style="color: red; display: none" id="span_gfsh_modify">*</span>购方税号</label>

										<div class="am-u-sm-4">
											<input type="text" id="gfsh_modify" name="gfsh" class ="js-pattern-Taxid"
												placeholder="输入购方税号...">
										</div>
										<label for="gfyh_edit" class="am-u-sm-2 am-form-label"><span
											style="color: red; display: none" id="span_gfyh_modify">*</span>购方银行</label>

										<div class="am-u-sm-4">
											<input type="text" id="gfyh_modify" name="gfyh"
												placeholder="输入购方银行...">
										</div>
									</div>
									<div class="am-form-group">
										<label for="gfzh_edit" class="am-u-sm-2 am-form-label"><span
											style="color: red; display: none" id="span_gfyhzh_modify">*</span>银行账号</label>

										<div class="am-u-sm-4">
											<input type="text" id="gfzh_modify" name="gfyhzh"
												placeholder="输入购方银行账号...">
										</div>
										<label for="gflxr_edit" class="am-u-sm-2 am-form-label">购方联系人</label>

										<div class="am-u-sm-4">
											<input type="text" id="gflxr_modify" name="gflxr"
												placeholder="输入购方联系人...">
										</div>
									</div>
									<div class="am-form-group">
										<label for="gfemail_edit" class="am-u-sm-2 am-form-label">购方邮件</label>

										<div class="am-u-sm-4">
											<input type="text" id="gfemail_modify" name="gfemail"
												placeholder="输入购方邮件地址...">
										</div>
										<label for="gfdh_edit" class="am-u-sm-2 am-form-label">购方电话</label>
										<div class="am-u-sm-4">
											<input type="text" id="gfdh_modify" name="gfdh"
												placeholder="输入购方电话...">
										</div>
									</div>
									<div class="am-form-group">
										<label for="gfdz_edit" class="am-u-sm-2 am-form-label">购方地址</label>

										<div class="am-u-sm-4">
											<input type="text" id="gfdz_modify" name="gfdz"
												placeholder="输入购方地址...">
										</div>
										<label for="tqm_edit" class="am-u-sm-2 am-form-label">提取码</label>

										<div class="am-u-sm-4">
											<input type="text" id="tqm_modify" name="tqm"
												placeholder="输入提取码...">
										</div>
									</div>
									<div class="am-form-group">

										<label for="ddh_edit" class="am-u-sm-2 am-form-label"><span
											style="color: red;">*</span>订单号</label>

										<div class="am-u-sm-4">
											<input type="text" id="ddh_modify" name="ddh"
												placeholder="输入订单号..." required>
										</div>
										<label for="gfsjh_edit" class="am-u-sm-2 am-form-label">手机号</label>
										<div class="am-u-sm-4">
											<input type="text" id="gfsjh_modify" name="gfsjh"
												placeholder="输入购方手机号...">
										</div>
									</div>
									<div class="am-form-group">

										<label for="gfbz_edit" class="am-u-sm-2 am-form-label">备注</label>

										<div class="am-u-sm-10">
											<input type="text" id="gfbz_modify" name="bz"
												placeholder="输入备注信息...">
										</div>
									</div>
								</fieldset>
							</form>

							<div class="am-margin">
								<button type="button" id="kpd_xgbc"
									class="am-btn am-btn-xs am-btn-secondary">提交保存</button>
								<button type="button" id="close3"
									class="am-btn am-btn-danger am-btn-xs">关闭</button>
							</div>

						</div>
					</div>
				</div>

				<div class="am-modal am-modal-no-btn" tabindex="-1"
					id="my-alert-edit1" style="width: 480px;">
					<div class="am-modal-dialog" style="overflow: auto; height: 500px;">
						<div class="am-modal-hd am-modal-footer-hd">
							修改商品明细 <!-- <a href="javascript: void(0)"
								class="am-close am-close-spin" data-am-modal-close>&times;</a> -->
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
										<label for="mx_spmc" class="am-u-sm-4 am-form-label">商品名称</label>
										<div class="am-u-sm-8">
											<input id="mx_spmc" name="spmc" type="text"
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
												class="am-text-money am-form-field" placeholder="商品数量">
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
											<input id="mx_spje" name="spje" onchange="jsje2()"
												type="text" class="am-text-money am-form-field"
												placeholder="商品金额">
										</div>
									</div>
								</div>
								<div class="am-u-lg-12">
									<div class="am-form-group">
										<label for="mx_sl" class="am-u-sm-4 am-form-label">商品税率</label>
										<div class="am-u-sm-8">
											<select id="mx_sl" name="spsl" onchange="jsje3()" name="sl">
												<c:forEach items="${slList}" var="item">
													<option value="${item}">${item}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="am-u-lg-12">
									<div class="am-form-group">
										<label for="mx_spse" class="am-u-sm-4 am-form-label">商品税额</label>
										<div class="am-u-sm-8">
											<input id="mx_spse" disabled="disabled" type="text"
												class="am-text-money am-form-field"> <input
												id="mx_spse1" name="spse" type="hidden"
												class="am-form-field">
										</div>
									</div>
								</div>
								<div class="am-u-lg-12">
									<div class="am-form-group">
										<label for="mx_jshj" class="am-u-sm-4 am-form-label">价税合计</label>
										<div class="am-u-sm-8">
											<input id="mx_jshj" name="jshj" onchange="jsje4()"
												type="text" class="am-text-money am-form-field"
												placeholder="价税合计">
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
	<script src="assets/js/lrkpd.js"></script>
	<script src="assets/js/kpdysc.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/autocomplete.js"></script>
	<script src="assets/js/getGfxxInput.js"></script>
	<script>
    $(document).ready(function () {  
    	
    	
    	$('#someTabs').tabs({noSwipe: 1});

        
        $('#jyls_table tr td').attr("class","weizhi");
        
        $('#mbid').hide();
        
        var je = $('#je_edit');
        var sl = $('#sltaxrate_edit');
        var se = $('#se_edit');
        var hsje = $('#hsje_edit');
        var jshj = $('#jshj_edit');
        var dj = $('#dj_edit');
        var sps = $('#sl_edit');
        var spsl;
        //录入订单时选择商品
        $("#select_sp").change(function () {
            var spdm = $(this).val();
            var spmc = $("#select_sp option:checked").text();
            var pos = spmc.indexOf("(");
            //var spid =  $("#select_sp option:checked").attr('class');
            spmc = spmc.substring(0, pos);
            if (!spdm) {
                $("#mx_form input").val("");
                return;
            }
            var ur = "<%=request.getContextPath()%>/lrkpd/getSpxq";

            
            $.ajax({
                url: ur,
                type: "post",
                async:false,
                data: {
                	spdm: spdm,   
                	spmc:spmc,	
                }, 
                success: function (res) {
                	 if (res) {
                         $("#mx_form #spdm_edit").val(res["spbm"]);
                         $("#mx_form #mc_edit").val(res["spmc"]);
                         $("#mx_form #ggxh_edit").val(res["spggxh"] == null ? "" : res["spggxh"]);
                         $("#mx_form #dw_edit").val(res["spdw"] == null ? "" : res["spdw"]);
                         $("#mx_form #dj_edit").val(res["spdj"] == null ? "" : res["spdj"]);
                         $("#mx_form #sltaxrate_edit").val(res["sl"]);
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
					dj.val(FormatFloat(je.val() / spl.val(), "#####0.00"));
				}
            }
        });
        
        
        $("#je_edit").keyup(function () {
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
        $("#hsje_edit").keyup(function () {
        	var temp = (100+sl.val()*100)/100;
        	je.val(FormatFloat(hsje.val()/(temp), "#####0.00"));
			se.val(FormatFloat(hsje.val() -je.val(), "#####0.00"));
			jshj.val(FormatFloat(hsje.val(), "#####0.00"));
        });
        //导入选择销方模板
        $("#mb_xfsh").change(function () {
            var xfsh = $(this).val();
            $('#mb').empty();
            $('#mb_skp').empty();
            if (xfsh == null || xfsh == '' || xfsh == "") {
				return;
			}
            var url = "<%=request.getContextPath()%>/lrkpd/getSkpList";
            $.post(url, {xfsh: xfsh}, function (data) {
                if (data) {
                	var option = $("<option>").text('---请选择---').val("1");
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
                	var option = $("<option>").text('---请选择---').val("1");
                	$('#mb').append(option);
                    for (var i = 0; i < data.mbs.length; i++) {
                    	option = $("<option>").text(data.mbs[i].mbmc).val(data.mbs[i].id);
                    	$('#mb').append(option);
					}
                }
            });
        });
        //下载默认导入模板
        $("#btnDownloadDefaultTemplate").click(function () {
        		window.location.href='lrkpd/downloadDefaultImportTemplate';
        });
        //导入excel
        $("#btnImport").click(function () {
            var filename = $("#importFile").val();
            var xfsh = $('#mb_xfsh').val();
            var mb = $('#mb').val();
            var skpid = $('#mb_skp').val();
            alert(mb);
            alert(skpid);
            alert(xfsh);
            if (xfsh=='1') {
                swal("请选择要导入的销方");
                return;
            }
            if (skpid=='1') {
                swal("请选择要导入的开票点");
                return;
            }
            if (mb=='1') {
                swal("请选择要导入的模板或设置默认模板,如无模板请添加模板后再导入");
                return;
            }
            if (!filename) {
                swal("请选择要导入的文件");
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
                			var txt = $('#mb').find("option:selected").text();
        					var option = $("<option>").text(txt).val(mbid);
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

        //删除
        $("#kp_del").click(function () {
            var djhArr = [];
            $("input[type='checkbox']:checked").each(function (i, o) {
            	if ($(o).attr("data") != null) {
                    djhArr.push($(o).attr("data"));
				}
            });
            if (djhArr.length == 0) {
                alert("请选择需要删除的交易流水...");
                return;
            }
            if (confirm("您确认删除？")) {
                $.post("<%=request.getContextPath()%>/lrkpd/doDel",
						"djhArr="+ djhArr.join(","),
						function(res) {
							if (res) {
								alert("删除成功");
								window.location.reload(true);
							}
				});
			}
		});
        
        
        
        $("#select_xfid_modify").change(
				function() {
		            $('#select_skpid_modify').empty();
				    var xfsh = $(this).val();
				    if (xfsh == null || xfsh == '' || xfsh == "") {
						return;
					}
				    var url = "<%=request.getContextPath()%>/kp/getSkpList";
								$.post(
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
														$('#select_skpid_modify')
																.append(option);
														for (var i = 0; i < data.skps.length; i++) {
															option = $(
																	"<option>")
																	.text(
																			data.skps[i].kpdmc)
																	.val(
																			data.skps[i].id);
															$('#select_skpid_modify')
																	.append(
																			option);
														}
													}
												});
							});
	});
        
	//选择销方取得税控盘
	function getKpd(){
		var xfid =  $('#select_xfid option:selected').val();
		//alert(xfid);
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
	

	
	
	function hidespan(){
		var fpzldm = $("#fpzl_edit").val();
		if(fpzldm=='01'){
			//$("#span_gfsh").style.display="";
			document.getElementById("span_gfsh").style.display=""; 
			//document.getElementById("span_gfdz").style.display=""; 
			//document.getElementById("span_gfdh").style.display=""; 
			document.getElementById("span_gfyh").style.display=""; 
			document.getElementById("span_gfyhzh").style.display=""; 
			//document.getElementById("gfmc_edit").setAttribute("required",true);
			$("#gfsh_edit").attr("required",true);
			//$("#gfdz_edit").attr("required",true);
			//$("#gfdh_edit").attr("required",true);
			$("#gfyh_edit").attr("required",true);
			$("#gfzh_edit").attr("required",true);
		 }else{
			document.getElementById("span_gfsh").style.display="none"; 
			//document.getElementById("span_gfdz").style.display="none"; 
			//document.getElementById("span_gfdh").style.display="none"; 
	        document.getElementById("span_gfyh").style.display="none"; 
			document.getElementById("span_gfyhzh").style.display="none"; 
			$("#gfsh_edit").attr("required",false);
			//$("#gfdz_edit").attr("required",false);
			//$("#gfdh_edit").attr("required",false);
			$("#gfyh_edit").attr("required",false);
			$("#gfzh_edit").attr("required",false);
		 }
		}
	
	
	function hidespan2(){
		var fpzldm = $("#fpzl_modify").val();
		if(fpzldm=='01'){
			//$("#span_gfsh").style.display="";
			document.getElementById("span_gfsh_modify").style.display=""; 
			//document.getElementById("span_gfdz_modify").style.display=""; 
			//document.getElementById("span_gfdh_modify").style.display=""; 
			document.getElementById("span_gfyh_modify").style.display=""; 
			document.getElementById("span_gfyhzh_modify").style.display=""; 
			//document.getElementById("gfmc_edit").setAttribute("required",true);
			$("#gfsh_modify").attr("required",true);
			//$("#gfdz_modify").attr("required",true);
			//$("#gfdh_modify").attr("required",true);
			$("#gfyh_modify").attr("required",true);
			$("#gfzh_modify").attr("required",true);
		 }else{
			document.getElementById("span_gfsh_modify").style.display="none"; 
			//document.getElementById("span_gfdz_modify").style.display="none"; 
			//document.getElementById("span_gfdh_modify").style.display="none"; 
	        document.getElementById("span_gfyh_modify").style.display="none"; 
			document.getElementById("span_gfyhzh_modify").style.display="none"; 
			$("#gfsh_modify").attr("required",false);
			//$("#gfdz_modify").attr("required",false);
			//$("#gfdh_modify").attr("required",false);
			$("#gfyh_modify").attr("required",false);
			$("#gfzh_modify").attr("required",false);
		 }
		}
	
	function hidespan3(str){
		var fpzldm = str;
		if(fpzldm=='01'){
			//$("#span_gfsh").style.display="";
			document.getElementById("span_gfsh_modify").style.display=""; 
			//document.getElementById("span_gfdz_modify").style.display=""; 
			//document.getElementById("span_gfdh_modify").style.display=""; 
			document.getElementById("span_gfyh_modify").style.display=""; 
			document.getElementById("span_gfyhzh_modify").style.display=""; 
			//document.getElementById("gfmc_edit").setAttribute("required",true);
			$("#gfsh_modify").attr("required",true);
			//$("#gfdz_modify").attr("required",true);
			//$("#gfdh_modify").attr("required",true);
			$("#gfyh_modify").attr("required",true);
			$("#gfzh_modify").attr("required",true);
		 }else{
			document.getElementById("span_gfsh_modify").style.display="none"; 
			//document.getElementById("span_gfdz_modify").style.display="none"; 
			//document.getElementById("span_gfdh_modify").style.display="none"; 
	        document.getElementById("span_gfyh_modify").style.display="none"; 
			document.getElementById("span_gfyhzh_modify").style.display="none"; 
			$("#gfsh_modify").attr("required",false);
			//$("#gfdz_modify").attr("required",false);
			//$("#gfdh_modify").attr("required",false);
			$("#gfyh_modify").attr("required",false);
			$("#gfzh_modify").attr("required",false);
		 }
		}
	
	//je
	function jsje2(){
		var sps = $('#mx_spsl').val();
		var spdj = $('#mx_spdj').val();
		var spje = $('#mx_spje').val();
		var spsl = $('#mx_sl').val();
		var jshj = $('#mx_jshj').val();
		if(""!=spje&&null!=spje){
			$('#mx_spse').val(Math.round((spje*spsl*1)*100)/100);
			$('#mx_jshj').val(Math.round((spje*1+spje*spsl*1)*100)/100);
			if(null!=spdj&&""!=spdj){
				$('#mx_spsl').val(Math.round((spje*1+spje*spsl*1)*100)/100/(spdj*1));
			}
		
			}
	}
	//sl
	function jsje3(){
		var sps = $('#mx_spsl').val();
		var spdj = $('#mx_spdj').val();
		var spje = $('#mx_spje').val();
		var spsl = $('#mx_sl').val();
		var jshj = $('#mx_jshj').val();
		if(""!=jshj&&null!=jshj){
			$('#mx_spje').val(Math.round((jshj/(1*1+spsl*1))*100)/100);
			$('#mx_spse').val(Math.round((jshj*1-jshj*1/(1*1+spsl*1))*100)/100);
			if(null!=spdj&&""!=spdj){
			$('#mx_spsl').val(Math.round(jshj*1/spdj*1)*100/100);
			}
			}else if(""!=spje&&null!=spje){
				$('#mx_spse').val(Math.round((spje*1*spsl)*100)/100);
				$('#mx_jshj').val(Math.round((spje*1+spje*spsl*1)*100)/100);
				if(null!=spdj&&""!=spdj){
				$('#mx_spsl').val(Math.round((spje*1+spje*spsl*1)*100)/100/(spdj*1));
				}
			}
		
	}
	//jshj
	function jsje4(){
		var sps = $('#mx_spsl').val();
		var spdj = $('#mx_spdj').val();
		var spje = $('#mx_spje').val();
		var spsl = $('#mx_sl').val();
		var jshj = $('#mx_jshj').val();
		if(""!=jshj&&null!=jshj){
			$('#mx_spje').val(Math.round((jshj*1/(1*1+spsl*1))*100)/100);
			$('#mx_spse').val(Math.round((jshj*1-jshj*1/(1*1+spsl*1))*100)/100);
			if(null!=spdj&&""!=spdj){
			$('#mx_spsl').val(Math.round((jshj*1/spdj*1)*100)/100);}
			}
	}
	</script>

</body>
</html>