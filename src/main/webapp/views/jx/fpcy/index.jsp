<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票查验</title>
<meta name="description" content="发票查验">
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
<link rel="stylesheet" href="assets/css/admin.css" />
<link rel="stylesheet" href="assets/css/amazeui.tree.min.css" />
<link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
<link rel="stylesheet" href="css/main.css" />s
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.form.js"></script>
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

						<div id="nrbq" class="am-tabs-bd">
							<div id="tabs1" class="am-tab-panel am-active">
								<div class="admin-content">
									<div class="am-cf widget-head">
										<div class="widget-title am-cf">
											<strong id="yjcd" class="am-text-primary am-text-lg">发票查验管理</strong>
											/ <strong id="ejcd">发票查验</strong>
											<button class="am-btn am-btn-success am-fr"
													data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
										</div>
										<!-- 侧边栏内容 -->
										<div id="doc-oc-demo3" class="am-offcanvas">
											<div class="am-offcanvas-bar am-offcanvas-bar-flip">
												<form id="ycform">
													<div class="am-offcanvas-content">
														<div class="am-form-group">
															<label for="s_xfsh" class="am-u-sm-4 am-form-label">选择购方</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{btnSize: 'sm'}" id="s_xfsh"
																	name="xfsh">
																	<option id="xzxfq" value="">选择购方</option>
																	<c:forEach items="${xfList}" var="item">
																		<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
																	</c:forEach>
																</select>
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 5px;">
														<div class="am-form-group">
															<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
															<div class="am-u-sm-8">
																<input id="s_fpdm" type="text" placeholder="发票代码">
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 5px;">
														<div class="am-form-group">
															<label for="s_fphm" class="am-u-sm-4 am-form-label">发票号码</label>
															<div class="am-u-sm-8">
																<input id="s_fphm" type="text" placeholder="发票号码">
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 8px;">
														<div class="am-form-group">
															<label for="s_fplx" class="am-u-sm-4 am-form-label">发票类型</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{btnSize: 'sm'}" id="s_fplx"
																	name="s_fplx">
																	<option id="xzlxq" value="">选择类型</option>
																	<option value="01">增值税专用发票</option>
																	<option value="02">增值税普通发票</option>
																</select>
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 8px;">
														<div class="am-form-group">
															<label for="s_rqq" class="am-u-sm-4 am-form-label">开票日期</label>
															<div class="am-input-group am-datepicker-date am-u-sm-8"
																data-am-datepicker="{format: 'yyyy-mm-dd'}">
																<input type="text" id="s_rqq" class="am-form-field"
																	placeholder="开票日期" readonly> <span
																	class="am-input-group-btn am-datepicker-add-on">
																	<button class="am-btn am-btn-default" type="button">
																		<span class="am-icon-calendar"></span>
																	</button>
																</span>
															</div>
														</div>
													</div>

													<%--<div class="am-offcanvas-content" style="margin-top: 8px;">
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
													</div>--%>
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
																<span></span>单张录入
															</button>
															<button type="button" id="kp_dr"
																class="am-btn am-btn-default am-btn-default">
																<span></span>批量录入
															</button>
															<button type="button" id="kpd_sc"
																class="am-btn am-btn-default am-btn-danger">
																<span></span>删除
															</button>
															<button type="button" id="kpd_kp"
																	class="am-btn am-btn-default am-btn-secondary">
																<span></span> 导出
															</button>
														</div>
													</div>
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
												<div class="am-form-group tpl-table-list-select">
													<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
														<option value="fpdm">发票代码</option>
														<option value="fphm">发票号码</option>
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
															<th>发票代码</th>
															<th>发票号码</th>
															<th>销方</th>
															<th>报销人</th>
															<th>开票日期</th>
															<th>发票类型</th>
															<th>查验次数</th>
															<th>数据来源</th>
															<th>发票状态</th>
														</tr>
													</thead>
												</table>
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

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-alert-edit2">
		<div class="am-modal-dialog" style="overflow: auto">
			<div class="am-modal-hd am-modal-footer-hd">
				单张查验发票录入
			</div>
			<div class="am-alert am-alert-success" data-am-alert id="myinfoalert"
				style="display: none">
				<button type="button" class="am-close">&times</button>
				<p id="infomessage"></p>
			</div>
			<div class="am-tabs am-margin" data-am-tabs="{noSwipe: 1}"
				id="lrmain_tab">
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li><a href="#tab2" class="ai">扫码录入</a></li>
					<li class="am-active"><a href="#tab1">手工录入</a></li>
				</ul>

				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
						<form class="am-form am-form-horizontal" id="main_form1">
							<fieldset>
								<input type="hidden" id="formid">

								<div class="am-form-group">
									<label  class="am-u-sm-2 am-form-label"><span
										style="color: red;">*</span>发票类型</label>

									<div class="am-u-sm-4 am-u-end">
										<input type="radio" name="sglr_fpzl" checked="checked" id="sglr_zp" onclick="CheckAll(this);"  value="01"  />
											<label for="sglr_zp">专用发票</label>
										<input type="radio" name="sglr_fpzl"  id="sglr_pp" onclick="CheckAll(this);"  value="02"  />
											<label for="sglr_pp">普通发票</label>
										<%--<select id="sglr_fpzl" name="sglr_fpzl">
											<option value="">选择开票类型</option>
											<option value="01">专用发票</option>
											<option value="02">普通发票</option>
										</select>--%>
									</div>

								</div>
								<div class="am-form-group">
									<label for="sglr_fpdm" class="am-u-sm-2 am-form-label"><span
										style="color: red;" >*</span>发票代码</label>

									<div class="am-u-sm-4">
										<input type="text" id="sglr_fpdm" name="sglr_fpdm"
											  placeholder="输入发票代码" required >
									</div>
									<label for="sglr_fphm" class="am-u-sm-2 am-form-label"><span
										style="color: red;" >*</span>发票号码</label>

									<div class="am-u-sm-4">
										<input type="text" id="sglr_fphm" name="sglr_fphm"
											placeholder="输入发票号码" required >
									</div>
								</div>
								<div class="am-form-group">
									<label for="sglr_kprq" class="am-u-sm-2 am-form-label"><span
											style="color: red;" >*</span>开票日期</label>
									<div class="am-input-group am-datepicker-date am-u-sm-4"
										 data-am-datepicker="{format: 'yyyy-mm-dd'}">
										<input type="text" id="sglr_kprq" class="am-form-field" name="sglr_kprq"
											   placeholder="开票日期" readonly > <span
											class="am-input-group-btn am-datepicker-add-on">
																	<button class="am-btn am-btn-default" type="button">
																		<span class="am-icon-calendar"></span>
																	</button>
																</span>
									</div>
									<label for="sglr_je" class="am-u-sm-2 am-form-label"
									id="je_lable" style="padding-left: 0px"><span style="color: red;" >*</span>金额</label>
									<label for="sglr_jym" id="jym_lable" style="display: none"
										   class="am-u-sm-2 am-form-label"><span style="color: red;" >*</span>校验码</label>
									<div class="am-u-sm-4">
										<input type="text" id="sglr_je" name="sglr_je"  required placeholder="输入金额(不含税)">
											<input type="text" id="sglr_jym" name="sglr_jym" style="display: none"
												   required	   placeholder="输入校验码后6位" >
									</div>
								</div>
								<div class="am-form-group">
									<label for="sglr_bxr" class="am-u-sm-2 am-form-label"
										   style="padding-left: 0px">报销人</label>
									<div class="am-u-sm-4">
										<input type="text" id="sglr_bxr" name="sglr_bxr"  placeholder="输入报销人">
									</div>
								</div>
							</fieldset>
						</form>
					</div>
					<%--扫码录入--%>
					<div class="am-tab-panel am-fade am-in am-active" id="tab2">
						<form class="am-form am-form-horizontal" id="main_form2">
							<fieldset>
								<input type="hidden" id="formid2">
								<div class="am-form-group">
									<input type="text" id="smlr_info"  name="smlr_info" required>
								</div>
								<div class="am-form-group">
										<input type="text" id="smlr_bxr" name="smlr_bxr"
											   placeholder="输入报销人"  >
								</div>
							</fieldset>
						</form>
					</div>
				</div>

				<div class="am-margin">
					<button type="button" id="lrsave"
						class="am-btn am-btn-xs am-btn-secondary">查验</button>
					<button type="button" id="lrclose"
						class="am-btn am-btn-danger am-btn-xs">关闭</button>
				</div>

			</div>
		</div>
	</div>
	<input type="" id="cycsid">
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-cyjl">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				发票查验次数信息
			</div>
			<div class="am-modal-bd">
				<hr />
				<div class="am-u-sm-12 am-padding-top" style="margin-left: 10px">
					<div>
						<table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
							   id="detail_table">
							<thead>
							<tr>
								<th style="text-align:center">序号</th>
								<th style="text-align:center">查验次数</th>
								<th style="text-align:center">查验日期</th>
								<th style="text-align:center">发票状态</th>
							</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="margin-left: -40%;overflow: auto; background-color: white;width:80%;" class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-fpyl">
		<div class="am-modal-hd">
			<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
		</div>
		<div id="doc-modal-fpyll" style="background-color: white;"class="am-modal-dialog">
			<div class="am-modal-bd" >
				<div id="doc-modal-fpyll">
					<%@ include file="fapiao.jsp" %>
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
								name="importFile" placeholder="选择要上传的文件" onchange="fileChange(this);"
								accept="application/vnd.ms-excel" required>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label"><font color="red">*</font>选择销方</label>
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
							<label class="am-u-sm-4 am-form-label"><font color="red">*</font>选择开票点</label>
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
							<label class="am-u-sm-4 am-form-label"><font color="red">*</font>选择模板</label>
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
						<!--[if (gte IE 9)|!(IE)]><!-->
						<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
			src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/fpcy.js"></script>
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
        //$("#sglr_kprq").val(formatDate(startDate));
        //$("#w_kprqz").val(formatDate(endDate));
        $("#s_rqq").val(formatDate(startDate));
        //$("#s_rqz").val(formatDate(endDate));
    });

	$(function() {
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
            if (skpid==-1 || skpid =='-1') {
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
			});

		});

		function CheckAll(){
            if ($("#sglr_zp").prop("checked")) {
                $("#sglr_jym").hide();
                $("#jym_lable").hide();
				$("#sglr_je").show();
				$("#je_lable").show();
            } else if($("#sglr_pp").prop("checked")) {
                $("#sglr_jym").show();
                $("#jym_lable").show();
                $("#sglr_je").hide();
                $("#je_lable").hide();
            }
		};
	</script>

</body>
</html>