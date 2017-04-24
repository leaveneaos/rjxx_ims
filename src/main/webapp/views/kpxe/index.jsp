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
<script src="assets/js/loading.js"></script>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<%@ include file="../../pages/top.jsp"%>
	
	<div class="am-cf admin-main" style="margin-top: 0px;">
		<!-- sidebar start -->
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf" style="background: ccccff">
					<strong class="am-text-default am-text-lg">1创建企业名片&nbsp;></strong>&nbsp;&nbsp;<strong
						class="am-text-default am-text-lg">2开票点信息</strong>&nbsp;>&nbsp;&nbsp;<strong
						class="am-text-primary am-text-lg">3开票限额</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">4商品信息</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">5下载客户端</strong>
				</div>
			</div>
			<hr />
			<div style="padding: 100px; padding-top: 10px; width: 1200px;">
				<div class="am-modal-bd" style="border: none">
					<div class="am-g">
						<form id="frm" class="js-form-0 am-form am-form-horizontal">
							<div class="am-form-group">
								<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
									color="red">*</font>开票点</label>
								<div class="am-u-sm-9">
									<select id="skpid" name="skpid">
										<option value="${skp.id }">${skp.kpdmc }&nbsp;${skp.kpddm}</option>
									</select>
								</div>
							</div>
							<c:forEach items="${kplxs }" var="k">
								<c:if test="${k=='01' }">
									<div class="am-form-group">
										<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
											color="red">*</font>增值税专用发票</label>
										<div class="am-u-sm-4">
											<select id="kpxe2" name="kpxe2" required="required">
												<option value="">请选择</option>
												<c:forEach items="${bc }" var="b">
													<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
												</c:forEach>
											</select>
										</div>
										<div class="am-u-sm-5">
											<input type="text" id="fpje2" name="fpje2"
												style="float: left;" placeholder="分票金额" value="${skp.zpfz }"
												class="am-form-field am-text-right js-pattern-Money" required />
										</div>
									</div>
								</c:if>
								<c:if test="${k=='02' }">
									<div class="am-form-group">
										<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
											color="red">*</font>增值税普通发票</label>
										<div class="am-u-sm-4">
											<select id="kpxe1" name="kpxe1" required="required">
												<option value="">请选择</option>
												<c:forEach items="${bc }" var="b">
													<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
												</c:forEach>
											</select>
										</div>
										<div class="am-u-sm-5">
											<input type="text" id="fpje1" name="fpje1"
												style="float: left;" placeholder="分票金额" value="${skp.ppfz }"
												class="am-form-field am-text-right js-pattern-Money" required />
										</div>
									</div>
								</c:if>
								<c:if test="${k=='12' }">
									<div class="am-form-group">
										<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
											color="red">*</font>增值税电子普通发票</label>
										<div class="am-u-sm-4">
											<select id="kpxe3" name="kpxe3" required="required">
												<option value="">请选择</option>
												<c:forEach items="${bc }" var="b">
													<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
												</c:forEach>
											</select>
										</div>
										<div class="am-u-sm-5">
											<input type="text" id="fpje3" name="fpje3"
												style="float: left;" placeholder="分票金额" value="${skp.fpfz }"
												class="am-form-field am-text-right js-pattern-Money" required />
										</div>
									</div>
								</c:if>

							</c:forEach>
							<div class="am-form-group">
<!-- 								<a id="last" class="js-button  am-btn am-radius am-btn-primary">上一步</a> -->
								<button id="save"
									class="js-button  am-btn am-radius am-btn-primary">下一步</button>
<!-- 								<a id="finish" -->
<!-- 									class="js-button  am-btn am-radius am-btn-success">完成</a> -->
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


	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd" id="msg"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>

	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd">你确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
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
				var r = $("#frm").validator("isFormValid");
				if (r) {

							$('#save').attr("disabled", false);
							var fpje1 = $('#fpje1').val();
							var fpje2 = $('#fpje2').val();
							var fpje3 = $('#fpje3').val();
							var fpxe1 = $('#kpxe1').val();
							var fpxe2 = $('#kpxe2').val();
							var fpxe3 = $('#kpxe3').val();
							if (fpxe1 == "") {
								$('#msg').html('普票开票限额不能为空');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpxe2 == "") {
								$('#msg').html('专票开票限额不能为空');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpxe3 == "") {
								$('#msg').html('电子发票开票限额不能为空');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpje1 == "") {
								$('#msg').html('普票分票金额不能为空');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpje2 == "") {
								$('#msg').html('专票分票金额不能为空');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpje3 == "") {
								$('#msg').html('电子发票分票金额不能为空');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpje1 != "" && fpxe1 != "" && fpje1 - fpxe1 > 0) {
								$('#msg').html('普票分票金额不能大于限额');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpje2 != "" && fpxe2 != "" && fpje2 - fpxe2 > 0) {
								$('#msg').html('专票分票金额不能大于限额');
								$('#my-alert').modal('open');
								return false;
							}
							if (fpje3 != "" && fpxe3 != "" && fpje3 - fpxe3 > 0) {
								$('#msg').html('电子发票分票金额不能大于限额');
								$('#my-alert').modal('open');
								return false;
							}
							$.ajax({
								url : '<%=request.getContextPath()%>/sksbxxzc/updateJe',
								data : data,
								method : 'POST',
								success : function(data) {
									if (data.success) {
					                	$('#msg').html(data.msg);
					                	$('#my-alert').modal('open'); 
										location.href = 'spxx';
// 										window.location.reload();
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
		                	$('#msg').html('验证失败');
		                	$('#my-alert').modal('open'); 
							$('#save').attr("disabled", false);
							return false;
						}
				
			});
// 			$('#xfid').change(function(){
// 				$.ajax({
<%-- 					url : "<%=request.getContextPath()%> --%>
// 		/xfxxwh/getJe",
// 					data : {
// 						id : $('#xfid').val()
// 					},
// 					method : 'POST',
// 					success : function(data) {
// 						if (data.success) {
// 							$('#fpje1').val(data.xf.ppzdje);
// 							$('#fpje2').val(data.xf.ppfpje);
// 							$('#fpje3').val(data.xf.zpzdje);
// 							$('#kpxe1').val(data.xf.zpfpje);
// 							$('#kpxe2').val(data.xf.dzpzdje);
// 							$('#kpxe3').val(data.xf.dzpfpje);
// 						} else {
// 							alert(data.msg);
// 						}
// 					},
// 					error : function() {
// 						el.$jsLoading.modal('close'); // close loading
// 						alert('保存失败, 请重新登陆再试...!');
// 					}
// 				});
// 			});
			$("#last").click(function() {
				location.href = "kpd";
			});
			$("#finish").click(function() {
				location.href = 'main';
			});
			$('#kpxe1').change(function() {
				$('#fpje1').val($('#kpxe1').val());
			});
			$('#kpxe2').change(function() {
				$('#fpje2').val($('#kpxe2').val());
			});
			$('#kpxe3').change(function() {
				$('#fpje3').val($('#kpxe3').val());
			});
		});
	</script>
</body>
</html>
