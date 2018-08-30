<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>开票合并处理</title>
<meta name="description" content="开票合并处理">
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
	<input type="hidden" id="bj" value="2">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="am-tabs" data-am-tabs="{noSwipe: 1}"
						id="doc-tab-demo-1">
						<ul id="btbq" class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tabs1">待处理</a></li>
							<li id="cljgbt"><a href="#cljg">处理结果</a></li>

						</ul>
						<div id="nrbq" class="am-tabs-bd">

							<div id="tabs1" class="am-tab-panel am-active">
								<div class="">
									<div class="am-cf widget-head">

							           <%--<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">--%>
								         <%--<form  id="mainform" class="am-form am-form-horizontal" style="margin-top: 3px;">--%>
											 <%--<div class="am-form-group">--%>
												 <%--<label for="xf" class="am-u-sm-2 am-form-label" style="padding-top: 4px;"><span--%>
														 <%--class="star">*</span>订单日期</label>--%>
												 <%--<div class="am-u-sm-3">--%>
													 <%--<input type="text" id="w_kprqq" name="w_kprqq"--%>
															<%--placeholder="订单起始时间"--%>
															<%--data-am-datepicker="{format: 'yyyy-mm-dd'}" />--%>
												 <%--</div>--%>
												 <%--<label for="xf" class="am-u-sm-2 am-form-label" style="padding-top: 4px;text-align: center;"><span--%>
														 <%--class="star"></span>至</label>--%>
												 <%--<div class="am-u-sm-3">--%>
													 <%--<input type="text" id="w_kprqz" name="w_kprqz"--%>
															<%--placeholder="订单截止时间"--%>
															<%--data-am-datepicker="{format: 'yyyy-mm-dd'}" />--%>
												 <%--</div>--%>
											 <%--</div>--%>

									<%--<div class="am-form-group">--%>
										<%--<label for="ddh" class="am-u-sm-2 am-form-label" style="padding-top: 4px;"><span--%>
												<%--class="star">*</span>订单号</label>--%>
										<%--<div class="am-u-sm-3">--%>
                                            <%--<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">--%>
											<%--<input id="ddh" name="ddh" type="text" onkeyup="this.value=this.value.replace(/[^u4e00-u9fa5w]/g,'')" placeholder="输入订单号" required>--%>
											<%--<span class="am-input-group-btn">--%>
                                                <%--<button class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search" id="kp_search" type="button"></button>--%>
                                            <%--</span>--%>
											<%--</div>--%>
										<%--</div>--%>
									<%--</div>--%>
								<%--</form>--%>
							<%--</div>--%>

										<div class="am-g" style="margin:10px 0px;">
											<form action="#" class="js-search-form  am-form am-form-horizontal">
												<div class="am-u-sm-8">
													<div class="am-form-group">
														<div class="am-u-sm-3">
															<span class="star">*</span>订单日期
														</div>
														<div class="am-u-sm-4">
															<input type="text" id="w_kprqq" name="w_kprqq"
																   placeholder="订单起始时间"
																   onfocus="$(this).blur()"
																   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
														</div>
														<div class="am-u-sm-1">
															至
														</div>
														<div class="am-u-sm-4">
															<input type="text" id="w_kprqz" name="w_kprqz"
																   placeholder="订单截止时间"
																   onfocus="$(this).blur()"
																   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
														</div>
													</div>
												</div>
												<div class="am-u-sm-1">
													<span class="star">*</span>订单号
												</div>
												<div class="am-u-sm-2">
													<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
														<input id="ddh" name="ddh" type="text" onkeyup="this.value=this.value.replace(/[^u4e00-u9fa5w]/g,'')" placeholder="输入订单号" required>
														<span class="am-input-group-btn">
                                                <button class="am-btn am-btn-default am-btn-secondary tpl-table-list-field" style="margin-left:${par.btMarginLeft}px" id="kp_search" type="button">${par.btText}</button>
                                            </span>
													</div>
												</div>

											</form>
										</div>
							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="border-top:1px solid #ccc;border-bottom:1px solid #ccc">
								<div class="am-u-sm-1 am-u-md-1 am-u-lg-1 data-buy" style="text-align: center;">购买方</div>
								<div class="am-u-sm-11 am-u-md-11 am-u-lg-11" style="border-left: 1px solid #ccc;">
									<form id="gfform" style="overflow: hidden;">
										<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
											<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" >
												<div class="am-u-sm-12 am-u-md-5 am-u-lg-5" style="float: right;">
													<label for="sfbx" class="am-form-label data-cte"><span class="star"></span>公司</label>
													<input id="sfbx" name="sfbx" type="checkbox"  onclick="sf();"   checked />
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfmc" class="am-u-sm-5 am-form-label data-cte"><span class="star">*</span>购方名称</label>
												<div class="am-u-sm-7" >
													<input id="gfmc" name="gfmc" type="text" value="" placeholder="请输入购方名称">
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfsh" class="am-u-sm-6 am-form-label data-cte"><span class="star" id="show">*</span>购方税号</label>
												<div class="am-u-sm-6">
													<input id="gfsh" name="gfsh" type="text" placeholder="请在半角字符下输入" oninput="this.value=this.value.replace(/[^0-9A-Z]/g,'')">
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfdz" class="am-u-sm-5 am-form-label data-cte">地址</label>
												<div class="am-u-sm-7">
													<input id="gfdz" name="gfdz" type="text" placeholder="请输入地址">
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfdh" class="am-u-sm-6 am-form-label data-cte">电话</label>
												<div class="am-u-sm-6">
													<input id="gfdh" name="gfdh" type="text" placeholder="请输入电话号码">
												</div>
											</div>

											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfyh" class="am-u-sm-5 am-form-label data-cte">开户行</label>
												<div class="am-u-sm-7">
													<input id="gfyh" name="gfyh" type="text" placeholder="请输入开户行">
												</div>
											</div>

											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="yhzh" class="am-u-sm-6 am-form-label data-cte">银行账号</label>
												<div class="am-u-sm-6">
													<input id="yhzh" name="yhzh" type="text" placeholder="请输入银行账号">
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
						
									</div>

									<div class="am-g  am-padding-top">

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
															<th>总条数</th>
															<th>总金额</th>
														</tr>
													</thead>
												</table>
												<%--<legend>商品明细列表</legend>--%>
											</div>
										</div>
										<div class="am-u-sm-12 botm">
											<button id="kpd_kp" type="button" class="am-btn am-btn-secondary">合并</button>
											<button id="cz" type="button" class="am-btn am-btn-danger">重 置</button>
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
											<th>序号</th>
											<th>销方税号</th>
											<th>销方名称</th>
											<th>购方名称</th>
											<th>购方税号</th>
											<th>购方地址</th>
											<th>购方电话</th>
											<th>银行账号</th>
										</tr>
									</thead>
								</table>

								<button id="yhqrbc" type="button"
									class="am-btn am-btn-primary js-append-tab">确认保存</button>
								<button id="yhqx" type="button"
									class="am-btn am-btn-primary js-append-tab">取消</button>
							</div>
						</div>
					</div>

					</div>
				</div>
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
	<script src="assets/js/fphbcl.js"></script>
	<script src="assets/js/kpdys.js"></script>
	<%--<script src="assets/js/autocomplete.js"></script>--%>
	<%--<script src="assets/js/getGfxxInput.js"></script>--%>
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
    function sf(){
        if ($("#sfbx").is(':checked')) {
            $("#show").html("*");
        }else{
            $("#show").html("");
        }
    }
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
        $("#s_rqq").val(formatDate(startDate));
        $("#s_rqz").val(formatDate(endDate));
    });

	</script>

</body>
</html>