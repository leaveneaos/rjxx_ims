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
<link rel="stylesheet" href="css/main.css" />
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
.s_left{
	text-align: left;
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

						<div id="nrbq" class="">
							<div id="tabs1" class="am-tab-panel am-active">
								<div class="admin-content">
									<div class="am-cf widget-head">
										<div class="widget-title am-cf">
											<strong id="yjcd" class="am-text-primary am-text-lg" style="color: #838FA1;"></strong> / <strong id="ejcd" style="color: #0e90d2;"></strong>
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
															class="am-btn am-btn-default am-btn-secondary data-back">
															<span></span> 查询
														</button>
													</div>
												</form>
											</div>
										</div>
									</div>

									<div class="am-g" style="margin-top: 20px">
										<form action="#"
											  class="js-search-form  am-form am-form-horizontal">
											<div class="am-u-sm-3">
												<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
													<option value="fpdm">发票代码</option>
													<option value="fphm">发票号码</option>
												</select>
											</div>
											<div class="am-u-sm-3">
												<div
														class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
													<input id="dxcsz" type="text" class="am-form-field ">
													<span class="am-input-group-btn">
														<button id="kp_search"
																class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search"
																type="button"></button>
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
														<div class="am-btn-group am-btn-group-xs btn-listBox">
															<button type="button" id="kp_add"
																class="am-btn am-btn-default am-btn-secondary">
																<span></span>单张录入
															</button>
															<button type="button" id="kp_dr"
																class="am-btn am-btn-default am-btn-secondary">
																<span></span>批量录入
															</button>
															<button type="button" id="kpd_sc"
																class="am-btn am-btn-default am-btn-danger">
																<span></span>删除
															</button>
															<%--<button type="button" id="kpd_kp"
																	class="am-btn am-btn-default am-btn-secondary">
																<span></span> 导出
															</button>--%>
														</div>
													</div>
												</div>
											</div>
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
															<th>最近查验成功时间</th>
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
				单张录入
			</div>
			<div class="am-alert am-alert-success" data-am-alert id="myinfoalert"
				style="display: none">
				<button type="button" class="am-close">&times</button>
				<p id="infomessage"></p>
			</div>
			<div class="am-tabs am-margin" data-am-tabs="{noSwipe: 1}"
				id="lrmain_tab">
				<%--<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li><a href="#tab2" class="ai">扫码录入</a></li>
					<li class="am-active"><a href="#tab1">手工录入</a></li>
				</ul>
--%>
				<div class="am-tabs-bd" style="border-top:1px solid #ddd;">
					<div class="" id="tab1">
						<span class="">手工录入</span>
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
					<div class="" id="tab2">
						<span class="">扫码录入</span>
						<form class="am-form am-form-horizontal" id="main_form2">
							<fieldset>
								<input type="hidden" id="formid2">
								<div class="am-form-group">
									<input type="text" id="smlr_info"  name="smlr_info" onchange="smlr()">
								</div>
								<%--<div class="am-form-group">
									<input type="text" id="smlr_bxr" name="smlr_bxr"
										   placeholder="输入报销人"  >
								</div>--%>
							</fieldset>
						</form>
					</div>
				</div>

				<div class="am-margin">
					<button type="button" id="lrsave"
						class="am-btn am-btn-xs am-btn-secondary">查验</button>
					<button type="button" id="lrclose"
						class="am-btn am-btn-secondary am-btn-xs">关闭</button>
				</div>

			</div>
		</div>
	</div>
	<input type="hidden" id="cycsid">
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-cyjl">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				发票查验记录
			</div>
			<div class="am-modal-bd">
				<hr />
				<div class="am-u-sm-12 am-padding-top" style="margin-left: 10px">
					<div>
						<table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
							   id="detail_table">
							<thead>
							<tr>
								<th style="text-align:center">查验次数</th>
								<th style="text-align:center">查验日期</th>
								<th style="text-align:center">发票状态</th>
								<th style="text-align:center">查验结果</th>
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
			</div>

			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal"  id="importExcelForm"
					method="post"
					action="<%=request.getContextPath()%>/income/importExcel"
					enctype="multipart/form-data">
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<input type="file" class="am-u-sm-12" id="importFile"
								name="importFile" placeholder="选择要上传的文件" onchange="fileChange(this);"
								accept="application/vnd.ms-excel" required>
						</div>
						<div class="am-u-sm-12" style="margin-top: 30px;">
							<button type="button" id="btnImport"
								class="am-btn am-btn-xs am-btn-secondary">导入</button>
							<button type="button" id="close1"
								class="am-btn am-btn-secondary am-btn-xs">关闭</button>
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
		//下载默认导入模板
        $("#btnDownloadDefaultTemplate").click(function() {
            location.href = "income/downloadDefaultImportTemplate";
        });
        //导入excel
        $("#btnImport").click(function () {
            var filename = $("#importFile").val();
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
                    } else {
                        $("#btnImport").attr("disabled", false);
                        $('.js-modal-loading').modal('close');
                        swal(res["message"]);
                    }
                }
            };
            $("#importExcelForm").ajaxSubmit(options);
        });

		});

		function CheckAll(){
		    //专票选中时
            if ($("#sglr_zp").prop("checked")) {
                $("#sglr_jym").hide();
                $("#jym_lable").hide();
				$("#sglr_je").show();
				$("#je_lable").show();
            }
            //普票选中时
            else if($("#sglr_pp").prop("checked")) {
                $("#sglr_jym").show();
                $("#jym_lable").show();
                $("#sglr_je").hide();
                $("#je_lable").hide();
            }
		};
		function smlr() {
            var info = $("#smlr_info").val();
            if(info!=null && info!=""){
				var s = info.split(",");
				var fpzl = s[0]+s[1];
				if(s.length<6){
                    swal('请重新扫描!');
                    return;
				}
				if(fpzl!=null&&(fpzl=="0104"|| fpzl=="0110")){
                    $("#sglr_zp").prop("checked",false);
                    $("#sglr_pp").prop("checked",true);
                    CheckAll();
					$("#sglr_fpdm").val(s[2]);
					$("#sglr_fphm").val(s[3]);
					var jym = s[6];
                    $("#sglr_jym").val(jym.substring(jym.length-6,jym.length));
                    $("#sglr_kprq").val(dateFormat(s[5]));
				}
				else if(fpzl!=null&&fpzl=="0101"){
                    $("#sglr_pp").prop("checked",false);
                    $("#sglr_zp").prop("checked",true);
                    CheckAll();
                    $("#sglr_fpdm").val(s[2]);
                    $("#sglr_fphm").val(s[3]);
                    $("#sglr_je").val(s[4]);
                    $("#sglr_kprq").val(dateFormat(s[5]));
				}else {
                    swal('请重新扫描!');
                    return;
				}
			}else {
                swal('请重新扫描!');
                return;
			}
        };

    function dateFormat(str) {
        var pattern = /(\d{4})(\d{2})(\d{2})/;
        var formatedDate = str.replace(pattern, '$1-$2-$3');
        return formatedDate;
    }
	</script>

</body>
</html>