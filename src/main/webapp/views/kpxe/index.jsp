<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>开票限额</title>
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
				<div class="am-fl am-cf" style="background: ccccff">
					<strong class="am-text-default am-text-lg">1创建企业名片&nbsp;></strong>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">2开票点信息</strong>&nbsp;>&nbsp;&nbsp;<strong class="am-text-primary am-text-lg">3开票限额</strong>
				</div>
			</div>
			<hr />
			<div style="padding: 100px; padding-top: 10px; width: 1200px;">
					<div class="am-modal-bd" style="border: none">
							<div class="am-g">
								<form id="frm" class="js-form-0 am-form am-form-horizontal">
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>企业</label>
									<div class="am-u-sm-9">
										<select id="xfid" name="xfid">
												<option value="${xf.id }">${xf.xfmc }&nbsp;${xf.xfsh }</option>
										</select>
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>增值税普通发票</label>
									<div class="am-u-sm-4">
										<input type="text" id="kpxe1" name="kpxe1" style="float: left;"
											placeholder="开票限额"  value="${xf.ppzdje }"
											pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
											class="am-form-field" required />
									</div>
									<div class="am-u-sm-5">
										<input type="text" id="fpje1" name="fpje1" style="float: left;"
											placeholder="分票金额" value="${xf.ppfpje }"
											pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
											class="am-form-field" required />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>增值税专用发票</label>
									<div class="am-u-sm-4">
										<input type="text" id="kpxe2" name="kpxe2" style="float: left;"
											placeholder="开票限额" value="${xf.zpzdje}"
											pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
											class="am-form-field" required />
									</div>
									<div class="am-u-sm-5">
										<input type="text" id="fpje2" name="fpje2" style="float: left;"
											placeholder="分票金额" value="${xf.zpfpje }"
											pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
											class="am-form-field" required />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>增值税电子普通发票</label>
									<div class="am-u-sm-4">
										<input type="text" id="kpxe3" name="kpxe3" style="float: left;"
											placeholder="开票限额" value="${xf.dzpzdje }"
											pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
											class="am-form-field" required />
									</div>
									<div class="am-u-sm-5">
										<input type="text" id="fpje3" name="fpje3" style="float: left;"
											placeholder="分票金额" value="${xf.dzpfpje }"
											pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
											class="am-form-field"  required />
									</div>
								</div>
								<div class="am-form-group">
									<a id="last" class="js-button  am-btn am-radius am-btn-primary">上一步</a>
									<button id="save" class="js-button  am-btn am-radius am-btn-success">保存</button>
									<a id="finish" class="js-button  am-btn am-radius am-btn-success">完成</a>
								</div>
								
								</form>
							</div>
					</div>
			</div>
		</div>
		<!-- content end -->

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

	<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
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
				$('#frm').validator({
					submit : function() {
						var formValidity = this.isFormValid();
						if (formValidity) {
							$('#save').attr("disabled", false);
							var fpje1 = $('#fpje1').val();
							var fpje2 = $('#fpje2').val();
							var fpje3 = $('#fpje3').val();
							var fpxe1 = $('#kpxe1').val();
							var fpxe2 = $('#kpxe2').val();
							var fpxe3 = $('#kpxe3').val();
							if (fpje1 - fpxe1 > 0) {
								alert('普票分票金额不能大于限额');
								return;
							}
							if (fpje2 - fpxe2 > 0) {
								alert('专票分票金额不能大于限额');
								return;
							}
							if (fpje3 - fpxe3 > 0) {
								alert('电子发票分票金额不能大于限额');
								return;
							}
							$.ajax({
								url : '<%=request.getContextPath()%>/xfxxwh/updateJe',
								data : data,
								method : 'POST',
								success : function(data) {
									if (data.success) {
										alert(data.msg);
										window.location.reload();
									} else if (data.repeat) {
										alert(data.msg);
									}else{
										alert(data.msg);
									}
								},
								error : function() {
									el.$jsLoading.modal('close'); // close loading
									alert('保存失败, 请重新登陆再试...!');
								}
							});
							return false;
						} else {
							alert('验证失败');
							return false;
						}
					}
				});
			});
			$('#xfid').change(function(){
				$.ajax({
					url : "<%=request.getContextPath()%>/xfxxwh/getJe",
					data : {
						id : $('#xfid').val()
					},
					method : 'POST',
					success : function(data) {
						if (data.success) {
							$('#fpje1').val(data.xf.ppzdje);
							$('#fpje2').val(data.xf.ppfpje);
							$('#fpje3').val(data.xf.zpzdje);
							$('#kpxe1').val(data.xf.zpfpje);
							$('#kpxe2').val(data.xf.dzpzdje);
							$('#kpxe3').val(data.xf.dzpfpje);
						}else{
							alert(data.msg);
						}
					},
					error : function() {
						el.$jsLoading.modal('close'); // close loading
						alert('保存失败, 请重新登陆再试...!');
					}
				});
			});
			$("#last").click(function(){
				location.href="kpd";
			});
			$("#finish").click(function(){
				location.href='main';
			});
			$('#kpxe1').blur(function(){
				$('#fpje1').val($('#kpxe1').val());
			});
			$('#kpxe2').blur(function(){
				$('#fpje2').val($('#kpxe2').val());
			});
			$('#kpxe3').blur(function(){
				$('#fpje3').val($('#kpxe3').val());
			});
		});
		
	</script>
</body>
</html>
