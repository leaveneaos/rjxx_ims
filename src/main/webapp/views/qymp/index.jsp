<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>企业名片</title>
<meta name="description" content="用户管理">
<meta name="keywords" content="用户管理">
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
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf" style="background: ccccff;">
					<strong class="am-text-primary am-text-lg">1创建企业名片</strong>&nbsp;>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">2开票点信息&nbsp;</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">3开票限额</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">4商品信息</strong>><strong class="am-text-default am-text-lg">5下载客户端</strong>

				</div>
			</div>
			<hr />
			<div style="padding: 50px; padding-top: 10px;">
				<form id="frm" class="js-form-0 am-form am-form-horizontal">
					<div class="am-modal-bd" style="float: left; width: 50%; border: none">
						<div class="am-modal-bd" style=" border: none"">
							<div class="am-g">
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
										color="red">*</font>企业名称</label>
									<div class="am-u-sm-8">
										<input type="text" id="xfmc" name="xfmc" style="float: left;"
											placeholder="企业名称" class="" required
											maxlength="50" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
										color="red">*</font>企业税号</label>
									<div class="am-u-sm-8">
										<input type="text" id="xfsh" name="xfsh" style="float: left;"
											placeholder="15、18或20位"
											pattern="^([0-9|a-zA-Z]{15}|[0-9|a-zA-Z]{18}|[0-9|a-zA-Z]{20})$"
											class=" js-pattern-taxid" required maxlength="20" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
										color="red">*</font>企业地址</label>
									<div class="am-u-sm-8">
										<input type="text" id="xfdz" name="dz" style="float: left;"
											placeholder="企业地址(发票票面)" required class="" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
										color="red">*</font>企业电话</label>
									<div class="am-u-sm-8">
										<input type="text" id="xfdh" name="xfdh" style="float: left;"
											placeholder="企业电话" required  maxlength="30" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
										color="red">*</font>企业银行</label>
									<div class="am-u-sm-8">
										<input type="text" id="khyh" name="khyh" style="float: left;"
											placeholder="企业银行(发票票面)" required class="" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
										color="red">*</font>银行账号</label>
									<div class="am-u-sm-8">
										<input type="text" id="yhzh" name="yhzh" style="float: left;"
											placeholder="银行账号(发票票面)" required class=""
											maxlength="50" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">收款人</label>
									<div class="am-u-sm-8">
										<input type="text" id="skr" name="skr" style="float: left;"
											placeholder="收款人(发票票面)" class=""
											maxlength="50" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">复核人</label>
									<div class="am-u-sm-8">
										<input type="text" id="fhr" name="fhr" style="float: left;"
											placeholder="复核人(发票票面)" class=""
											maxlength="50" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
										color="red">*</font>开票人</label>
									<div class="am-u-sm-8">
										<input type="text" id="kpr" name="kpr" style="float: left;"
											placeholder="开票人(发票票面)" required class=""
											maxlength="50" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div style="float: left; padding: 50px; padding-top: 10px;width: 50%;">
							<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="7">"</font>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">完善后</font><p>
						
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4">将作为您开票的</font><font size="6" color="orange">销售方信息</font><p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="7">"</font><p>
												
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button style="width: 200px" id="save" class="js-submit  am-btn am-radius am-btn-secondary">下一步</button><p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" style="width: 200px" id="export" class="js-submit  am-btn am-btn-default">批量导入</button><p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" style="width: 200px" id="nextStep" class="js-submit  am-btn am-btn-secondary">下一步</button>
											</div> -->
						<img src="img/ticket.png">
						<p style="margin: 0 auto;">完善开票的&nbsp;&nbsp;<span style="color: orange;font-size: 28px;font-weight: 700;">销售方信息</span></p>
						<button type="button" style="width: 230px;border-radius: 10px;margin: 10px 50%;" id="save" class="js-submit  am-btn am-btn-secondary">下一步</button>
				</form>
			</div>
		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1"
			id="bulk-import-div">
			<div class="am-modal-dialog">
				<div class="am-modal-hd am-modal-footer-hd">
					批量导入 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>

				<div class="am-tab-panel am-fade am-in am-active">
					<form class="am-form am-form-horizontal" id="importExcelForm"
						method="post"
						action="<%=request.getContextPath()%>/xfxxwh/importExcel"
						enctype="multipart/form-data">
						<div class="am-form-group">
							<div class="am-u-sm-12">
								<input type="file" class="am-u-sm-12" id="importFile"
									name="importFile" placeholder="选择要上传的文件" required>
							</div>
							<div class="am-u-sm-12" style="margin-top: 10px;">
								<button type="button" id="btnImport"
									class="am-btn am-radius am-btn-xs am-btn-success">导入</button>
								<button type="button" id="close1"
									class="am-btn am-radius am-btn-warning am-btn-xs">关闭</button>

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
	</div>
		<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div id="msg" class="am-modal-bd">Hello world！</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<%@ include file="../../pages/foot.jsp"%>

	<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
	<script src="assets/js/jquery.form.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>

	<script type="text/javascript">
		$(function(){
			$('#save').click(function(){
				var data = $("#frm").serialize();
				$('#save').attr("disabled", true);
// 				alert($("#xfdh").val());
				var r = $("#frm").validator("isFormValid");
						if (r) {

							$.ajax({
								url : "<%=request.getContextPath()%>/xfxxwh/save",
								data : {
									xfsh : $('#xfsh').val(),
									xfmc : $('#xfmc').val(),
									khyh : $('#khyh').val(),
									yhzh : $('#yhzh').val(),
									xfdh : $('#xfdh').val(),
									dz : $('#xfdz').val(),
									skr : $('#skr').val(),
									fhr : $('#fhr').val(),
									kpr : $('#kpr').val()
								},
								method : 'POST',
								success : function(data) {
									$('#save').attr("disabled", false);
									if (data.success) {
					                	$('#msg').html(data.msg);
					                	$('#my-alert').modal('open'); 
										location.href= "kpd";
									} else if (data.repeat) {
					                	$('#msg').html(data.msg);
					                	$('#my-alert').modal('open'); 
									}else{
					                	$('#msg').html(data.msg);
					                	$('#my-alert').modal('open'); 
									}

								},
								error : function() {
									el.$jsLoading.modal('close'); // close loading
				                	$('#msg').html('保存失败, 请重新登陆再试...!');
				                	$('#my-alert').modal('open'); 
								}
							});
							return false;
						} else {
							$('#save').attr("disabled", false);
		                	$('#msg').html('验证失败');
		                	$('#my-alert').modal('open'); 
							return false;
						}
				
			});
			$('#export').click(function(){
				$("#bulk-import-div").modal({"width": 500, "height": 250});
			});
			$('#nextStep').click(function(){
				location.href= "kpd";
			});
			//下载默认导入模板
			$("#btnDownloadDefaultTemplate").click(function() {
				location.href = "xfxxwh/downloadDefaultImportTemplate";
			});
			$("#btnImport").click(function () {
	            var filename = $("#importFile").val();
	            if (filename == null || filename == "") {
                	$('#msg').html("请选择要导入的文件");
                	$('#my-alert').modal('open'); 
	                return;
	            }
	            var pos = filename.lastIndexOf(".");
	            if (pos == -1) {
                	$('#msg').html("导入的文件必须是excel文件");
                	$('#my-alert').modal('open'); 
	                return;
	            }
	            var extName = filename.substring(pos + 1);
	            if ("xls" != extName && "xlsx" != extName) {
                	$('#msg').html("导入的文件必须是excel文件");
                	$('#my-alert').modal('open'); 
	                return;
	            }
	            $("#btnImport").attr("disabled", true);
				$('.js-modal-loading').modal('toggle'); // show loading
				// alert('验证成功');
				var options = {
		                success: function (res) {
		                    if (res.success) {
		                        $("#btnImport").attr("disabled", false);
		                        $('.js-modal-loading').modal('close');
		                        var count = res.count;
			                	$('#msg').html("导入成功，共导入" + count + "条数据");
			                	$('#my-alert').modal('open'); 
		                    } else {
		                        $("#btnImport").attr("disabled", false);
		                        $('.js-modal-loading').modal('close');
			                	$('#msg').html(res.message);
			                	$('#my-alert').modal('open'); 
		                    }
		                }
		            };
		            $("#importExcelForm").ajaxSubmit(options);
							
	        });
		});
		
	</script>
</body>
</html>
