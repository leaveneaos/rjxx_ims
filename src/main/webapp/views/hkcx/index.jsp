<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>换开发票查询</title>
<meta name="description" content="换开发票查询">
<meta name="keywords" content="换开发票查询">
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
<link rel="stylesheet" href="css/main.css" />
<script src="assets/js/loading.js"></script>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../../pages/menus.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">查询统计</strong> / <strong>换开发票查询</strong>
				</div>
			</div>
			<hr />

			<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">电子发票</a></li>
					<li><a href="#tab2">纸质发票</a></li>
				</ul>
				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
						<form action="#"
							class="js-search-form  am-form am-form-horizontal">
							<div class="am-g">
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_ddh" class="am-u-sm-3 am-form-label">订单号</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_ddh" name="s_ddh" placeholder="" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_fphm" class="am-u-sm-3 am-form-label">发票号码</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_fphm" name="s_fphm" placeholder="" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_kprqq" class="am-u-sm-3 am-form-label">换开日期</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_kprqq" name="s_kprqq"
												placeholder="点击选择日期"
												data-am-datepicker="{format: 'yyyy-mm-dd'}" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_kprqz"
											class="am-u-sm-3 am-form-label am-text-center">-</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_kprqz" name="s_kprqz"
												placeholder="点击选择日期"
												data-am-datepicker="{format: 'yyyy-mm-dd'}" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">&nbsp;</div>
							</div>
						</form>
						<hr />
						<div class="am-u-sm-12  am-padding  am-text-center">
							<button type="button" class="js-search  am-btn am-btn-primary">查询</button>
						</div>
						<div class="am-u-sm-12">
							<div class="am-scrollable-horizontal">
								<table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
									<thead>
										<tr>
											<th>序号</th>
											<th>订单号</th>
											<th>购方名称</th>
											<th>发票代码</th>
											<th>发票号码</th>
											<th>原发票代码</th>
											<th>原发票号码</th>
											<th>金额</th>
											<th>税额</th>
											<th>价稅合计</th>											
											<th>换开日期</th>
											<th>开票人</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="am-tab-panel am-fade" id="tab2">
					<form action="#"
							class="js-search-form  am-form am-form-horizontal">
							<div class="am-g">
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_ddh" class="am-u-sm-3 am-form-label">订单号</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_ddh" name="s_ddh" placeholder="" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_fphm" class="am-u-sm-3 am-form-label">发票号码</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_fphm" name="s_fphm" placeholder="" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_kprqq" class="am-u-sm-3 am-form-label">换开日期</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_kprqq" name="s_kprqq"
												placeholder="点击选择日期"
												data-am-datepicker="{format: 'yyyy-mm-dd'}" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_kprqz"
											class="am-u-sm-3 am-form-label am-text-center">-</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_kprqz" name="s_kprqz"
												placeholder="点击选择日期"
												data-am-datepicker="{format: 'yyyy-mm-dd'}" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_zzfplx"
											class="am-u-sm-3 am-form-label am-text-center">发票类型</label>
										<div class="am-u-sm-9">
											<select id="s_zzfplx" name="s_zzfplx">
											      <option value="">----请选择----</option>
											      <option value="01">增值税专用发票</option>
											      <option value="02">增值税普通发票</option>
											</select>
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">&nbsp;</div>
							</div>
						</form>
						<hr />
						<div class="am-u-sm-12  am-padding  am-text-center">
							<button type="button" class="js-search  am-btn am-btn-primary">查询</button>
						</div>
						<div class="am-u-sm-12">
							<div class="am-scrollable-horizontal">
								<table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
									<thead>
										<tr>
											<th>序号</th>
											<th>订单号</th>
											<th>购方名称</th>
											<th>发票代码</th>
											<th>发票号码</th>
											<th>原发票代码</th>
											<th>原发票号码</th>
											<th>金额</th>
											<th>税额</th>
											<th>价稅合计</th>										
											<th>换开日期</th>
											<th>开票人</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>


		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">
					红字发票详情 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<hr />
					<form action="#" class="js-form-0  am-form am-form-horizontal">
						<div class="am-g">
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<label for="hc_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
									<div class="am-u-sm-8">
										<input type="text" id="hc_fpdm" name="hc_fpdm" placeholder=""
											readonly /> <input type="hidden" name="id" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_fphm" class="am-u-sm-4 am-form-label">发票号码</label>
									<div class="am-u-sm-8">
										<input type="text" id="hc_fphm" name="hc_fphm" placeholder=""
											readonly />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_je" class="am-u-sm-4 am-form-label">金额</label>
									<div class="am-u-sm-8">
										<input type="text" id="hc_je" name="hc_je" placeholder=""
											readonly />
									</div>
								</div>

							</div>
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<label for="hc_se" class="am-u-sm-4 am-form-label">税额</label>
									<div class="am-u-sm-8">
										<input type="text" id="hc_se" name="hc_se" placeholder=""
											readonly />
									</div>
								</div>

								<div class="am-form-group">
									<label for="hc_gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
									<div class="am-u-sm-8">
										<input type="text" id="hc_gfmc" name="hc_gfmc" placeholder=""
											readonly />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfpdm" class="am-u-sm-4 am-form-label">原发票代码</label>
									<div class="am-u-sm-8">
										<input type="text" id="hc_yfpdm" name="hc_yfpdm"
											placeholder="" readonly />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">原发票号码</label>
									<div class="am-u-sm-8">
										<input type="text" id="hc_yfphm" name="hc_yfphm"
											placeholder="" readonly />
									</div>
								</div>
							</div>
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<div class="am-u-sm-12  am-text-center">
										<button type="button" class="js-close  am-btn am-btn-danger">关闭</button>
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

	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<%@ include file="../../pages/foot.jsp"%>
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
	<script src="assets/js/fpcx_2.js"></script>

</body>
</html>