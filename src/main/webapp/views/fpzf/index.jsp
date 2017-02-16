<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>已开发票作废</title>
<meta name="description" content="发票作废">
<meta name="keywords" content="发票作废">
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
		<input type="hidden" id="kplsh" value="0">
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>发票作废</strong>
				</div>
			</div>
			<hr />
			<div class="am-tabs"  data-am-tabs="{noSwipe: 1}">
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">发票作废</a></li>
					<li><a href="#tab2">已作废</a></li>
				</ul>
				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-active am-in" id="tab1">
						<div class="am-g  am-padding-top">
							<form action="#"
								class="js-search-form  am-form am-form-horizontal">
								<div class="am-g">
									<div class="am-u-sm-12">
										<div class="am-u-lg-4">
											<div class="am-form-group">
												<label for="zf_xfsh" class="am-u-sm-4 am-form-label">选择销方</label>
												<div class="am-u-sm-8">
													<select id="zf_xfsh" name="xfsh">
														<option value="">选择销方</option>
														<c:forEach items="${xfList}" var="item">
															<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-u-lg-4">
											<div class="am-form-group">
												<label for="zf_gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
												<div class="am-u-sm-8">
													<input id="zf_gfmc" type="text" class="am-form-field" placeholder="购方名称">
												</div>
											</div>
										</div>
										<!-- 		<div class="am-u-lg-3">
											<div class="am-input-group" style="width: 100%">
												<input type="text" class="am-form-field" placeholder="订单号">
											</div>
										</div> -->
										<div class="am-u-sm-2" style="margin-top: 13px;">
											<a><u>更多</u></a>
										</div>
										<div class="am-u-sm-2 am-u-end">
											<div class="am-form-group" style="width: 100%">
												<button type="button" id="kp_zf"
													class="am-btn am-radius am-btn-danger" style="width: 100%">
													<span></span> 作废
												</button>
											</div>
										</div>
									</div>
									<div class="am-u-sm-12">
										<div class="am-u-sm-4">
											<label for="s_ddh" class="am-u-sm-4 am-form-label">开始时间</label>
											<div class="am-input-group am-datepicker-date am-u-sm-8"
												data-am-datepicker="{format: 'yyyy-mm-dd'}">
												<input type="text" id="s_kprqq" class="am-form-field" placeholder="开始时间" readonly>
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
												<input type="text" id="s_kprqz" class="am-form-field"
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
													id="day7" href="javascript:void(0);"
													><u>近7天</u></a> <a
													href="javascript:void(0);" id="day30"
													><u>近30天</u></a>
											</div>
										</div>
										<div class="am-u-sm-2">
											<div class="am-form-group" style="width: 100%">
												<button type="button" id="kp_cx" style="width: 100%"
													class="js-search am-btn am-radius am-btn-success">
													<span></span> 查询
												</button>
											</div>
										</div>
									</div>
								</div>
								<br>
							</form>
							


							<div class="am-u-sm-12">
								<div>
									<table
										class="js-table2 am-table am-table-bordered am-text-nowrap am-scrollable-horizontal"
										id="fpTable">
										<thead>
											<tr>
												<th>序号</th>
												<th>订单号</th>
												<th>开票日期</th>
												<th>发票代码</th>
												<th>发票号码</th>
												<th>购方名称</th>
												<th>金额</th>
												<th>税额</th>
												<th>价税合计</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</div>
							<fieldset>
								<legend>商品明细列表</legend>
								<div class="am-u-sm-12">
									<div>
										<table
											class="js-mxtable am-table am-table-bordered am-text-nowrap am-scrollable-horizontal"
											id="mxTable">
											<thead>
												<tr>
													<th>序号</th>
													<th>商品名称</th>
													<th>商品规格型号</th>
													<th>商品金额</th>
													<th>商品税率</th>
													<th>商品税额</th>
													<th>价税合计</th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
						</div>
						<!-- model -->
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">
									发票红冲详情 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<hr />
									<form action="fphc/ykfphc"
										class="js-form-0  am-form am-form-horizontal">
										<div class="am-g">
											<div class="am-u-sm-12">
												<div class="am-form-group" style="display: none">
													<label for="hc_yfphm" class="am-u-sm-4 am-form-label">DJH</label>
													<div class="am-u-sm-8">
														<input type="text" id="djh" name="djh" placeholder=""
															readonly />
													</div>
												</div>
												<div class="am-form-group">
													<label for="hc_yfpdm" class="am-u-sm-4 am-form-label">原发票代码</label>
													<div class="am-u-sm-8">
														<input type="text" id="hc_yfpdm" name="hc_yfpdm"
															placeholder="" readonly /> <input type="hidden"
															name="id" />
													</div>
												</div>
												<div class="am-form-group">
													<label for="hc_yfphm" class="am-u-sm-4 am-form-label">原发票号码</label>
													<div class="am-u-sm-8">
														<input type="text" id="hc_yfphm" name="hc_yfphm"
															placeholder="" readonly />
													</div>
												</div>
												<div class="am-form-group">
													<label for="hc_kpje" class="am-u-sm-4 am-form-label">价税合计</label>
													<div class="am-u-sm-8">
														<input type="text" id="hc_kpje" name="hc_kpje"
															placeholder="" readonly />
													</div>
												</div>

											</div>
											<div class="am-u-sm-12">
												<div class="am-form-group">
													<div class="am-u-sm-12  am-text-center">
														<button type="submit"
															class="js-submit  am-btn am-btn-primary">确定</button>
														<button type="button"
															class="js-close  am-btn am-btn-danger">取消</button>
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
						<a href="#"
							class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
							data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
					</div>
					<div class="am-tab-panel am-fade" id="tab2">
 						<div class="am-g  am-padding-top">
							<form action="#"
								class="js-search-form  am-form am-form-horizontal">
								<div class="am-g">
									<div class="am-u-sm-12">
										<div class="am-u-lg-4">
											<div class="am-form-group">
												<label for="zf_xfsh1" class="am-u-sm-4 am-form-label">选择销方</label>
												<div class="am-u-sm-8">
													<select id="zf_xfsh1" name="xfsh">
														<option value="">选择销方</option>
														<c:forEach items="${xfList}" var="item">
															<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-u-lg-4">
											<div class="am-form-group">
												<label for="zf_gfmc1" class="am-u-sm-4 am-form-label">购方名称</label>
												<div class="am-u-sm-8">
													<input id="zf_gfmc1" type="text" class="am-form-field" placeholder="购方名称">
												</div>
											</div>
										</div>
										<!-- 		<div class="am-u-lg-3">
											<div class="am-input-group" style="width: 100%">
												<input type="text" class="am-form-field" placeholder="订单号">
											</div>
										</div> -->
										<div class="am-u-sm-2 am-u-end" style="margin-top: 13px;">
											<a><u>更多</u></a>
										</div>
									</div>
									<div class="am-u-sm-12">
										<div class="am-u-sm-4">
											<label for="s_kprqq1" class="am-u-sm-4 am-form-label">开始时间</label>
											<div class="am-input-group am-datepicker-date am-u-sm-8"
												data-am-datepicker="{format: 'yyyy-mm-dd'}">
												<input type="text" id="s_kprqq1" class="am-form-field" placeholder="开始时间" readonly>
												<span class="am-input-group-btn am-datepicker-add-on">
													<button class="am-btn am-btn-default" type="button">
														<span class="am-icon-calendar"></span>
													</button>
												</span>
											</div>
			
										</div>
										<div class="am-u-sm-4">
											<label for="s_kprqz1" class="am-u-sm-4 am-form-label">截止时间</label>
											<div class="am-input-group am-datepicker-date am-u-sm-8"
												data-am-datepicker="{format: 'yyyy-mm-dd'}">
												<input type="text" id="s_kprqz1" class="am-form-field"
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
													id="day7" href="javascript:void(0);"
													><u>近7天</u></a> <a
													href="javascript:void(0);" id="day30"
													><u>近30天</u></a>
											</div>
										</div>
										<div class="am-u-sm-2">
											<div class="am-form-group" style="width: 100%">
												<button type="button" id="kp_cx1" style="width: 100%"
													class=" am-btn am-radius am-btn-success">
													<span></span> 查询
												</button>
											</div>
										</div>
									</div>
								</div>
								<br>
							</form>
							<hr />


							<div class="am-u-sm-12">
								<div class="am-scrollable-horizontal">
									<table
										class="js-table1 am-table am-table-bordered am-text-nowrap"
										id="ysTable">
										<thead>
											<tr>
												<th>序号</th>
												<th>订单号</th>
												<th>开票日期</th>
												<th>发票代码</th>
												<th>发票号码</th>
												<th>购方名称</th>
												<th>金额</th>
												<th>税额</th>
												<th>价税合计</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</div>
							<fieldset>
								<br>
								<legend>商品明细列表</legend>
								<div class="am-u-sm-12">
									<div>
										<table
											class="js-mxtable am-table am-table-bordered am-table-striped am-text-nowrap"
											id="mxTable1">
											<thead>
												<tr>
													<th>序号</th>
													<th>商品名称</th>
													<th>商品规格型号</th>
													<th>商品金额</th>
													<th>商品税率</th>
													<th>商品税额</th>
													<th>价税合计</th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- content end -->




	</div>



	<%@ include file="../../pages/foot.jsp"%>

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
	<script src="assets/js/fpzf.js"></script>
	<script type="text/javascript">
		$("a").on('click', function() {
			var date = $(this).attr("id")
			//alert( $(this).attr("id"));
			if (date == 'day3') {
				var k = getToday();
				var j = getThreeday(2);
				$("#s_kprqq").val(j);
				$("#s_kprqz").val(k);
			} else if (date == 'day7') {
				var k = getToday();
				var j = getThreeday(6);
				$("#s_kprqq").val(j);
				$("#s_kprqz").val(k);
			} else if (date == 'day30') {
				var k = getToday();
				var j = getThreeday(29);
				$("#s_kprqq").val(j);
				$("#s_kprqz").val(k);
			}
		});
		//当前日期
		function getToday() {
			var date = new Date();
			var strYear = date.getFullYear();
			var strDay = date.getDate();
			var strMonth = date.getMonth() + 1;
			if (strMonth < 10) {
				strMonth = "0" + strMonth;
			}
			datastr = strYear + "-" + strMonth + "-" + strDay;
			return datastr;
		}
		//获取最近n天
		function getThreeday(day) {
			var date = new Date();
			var threeday_milliseconds = date.getTime() - 1000 * 60 * 60 * 24
					* day;
			var threeday = new Date();
			threeday.setTime(threeday_milliseconds);
			var strYear = threeday.getFullYear();
			var strDay = threeday.getDate();
			var strMonth = threeday.getMonth() + 1;
			if (strMonth < 10) {
				strMonth = "0" + strMonth;
			}
			datastr = strYear + "-" + strMonth + "-" + strDay;
			return datastr;
		}
	</script>
</body>
</html>
