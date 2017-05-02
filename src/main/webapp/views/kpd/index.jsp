<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>开票点信息</title>
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
				<div class="am-fl am-cf" style="background: ccccff">
					<strong class="am-text-default am-text-lg">1创建企业名片&nbsp;></strong>&nbsp;&nbsp;<strong class="am-text-primary am-text-lg">2开票点信息</strong>&nbsp;>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">3开票限额</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">4商品信息</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">5下载客户端</strong>
				</div>
			</div>
			<hr />
			<div style="padding-right: 100px; padding-top: 10px;padding-left: 10px;">
				<form id="frm" class="js-form-0 am-form am-form-horizontal">
					<div class="am-modal-bd" style="border: none">
						<div class="am-modal-bd" style=" border: none"">
							<div class="am-g">
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
										color="red">*</font>开票点代码</label>
									<div class="am-u-sm-3">
										<input type="text" id="kpddm" name="kpddm" value="${xf.gsdm }_01" style="float: left;"
											placeholder="开票点代码"
											class="am-form-field" required />
									</div>
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>开票点名称</label>
									<div class="am-u-sm-3">
										<input type="text" id="kpdmc" name="kpdmc" value="${xf.xfmc }_01" style="float: left;width: 90%;"
											placeholder="开票点名称"
											class="am-form-field" required />
									</div>
								</div>
								<div class="am-form-group">
									<label  for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>设备类型</label>
									<div class="am-u-sm-3">
										<select id="sbcs" name="sbcs">
											<option value="1">税控盘</option>
											<option value="2">金税盘</option>
										</select>
									</div>
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red"></font>设备号</label>
									<div class="am-u-sm-3">
										<input type="text" id="skph" name="skph" style="float: left;width: 90%;" value=""
											placeholder="税控盘号"
											class="am-form-field" />
											<span onclick="xssbh()" class="am-icon-question" style="color: blue;"></span>
									</div>
								</div>
							 	<div style="display: none;" class="am-form-group">
									<label style="display: none;" for="hc_yfphm" class="am-u-sm-3 am-form-label">设备密码</label>
									<div class="am-u-sm-3">
										<input type="password" id="skpmm" name="skpmm" style="float: left;"
											placeholder="税控盘密码" class="am-form-field"
											maxlength="30" />
									</div>
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label">证书密码</label>
									<div class="am-u-sm-3">
										<input type="password" id="zsmm" name="zsmm" style="float: left;"
											placeholder="证书密码" class="am-form-field"
											maxlength="30" />
									</div>
								</div>
								 <div style="display: none;" class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>联系地址</label>
									<div class="am-u-sm-3">
										<input type="text" id="lxdz" name="lxdz" style="float: left;"
											placeholder="联系地址" value="${xf.xfdz }" class="am-form-field" required="required"
											/>
									</div>
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>联系电话</label>
									<div class="am-u-sm-3">
										<input type="text" id="lxdh" name="lxdh" value="${xf.xfdh }" style="float: left;"
											placeholder="联系电话" class="js-pattern-Telephone" required="required"
											/>
											
									</div>
								</div>
								<div style="display: none;" class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>开户银行</label>
									<div class="am-u-sm-3">
										<input type="text" id="khyh" name="khyh" style="float: left;"
											placeholder="开户银行" value="${xf.xfyh }" class="am-form-field" required="required"
											/>
									</div>
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>银行账号</label>
									<div class="am-u-sm-3">
										<input type="text" id="yhzh" name="yhzh" style="float: left;"
											placeholder="银行账号" value="${xf.xfyhzh }" class="am-form-field" required="required"
											/>
									</div>
								</div> 
								<div style="display: none;" class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label">复核人</label>
									<div class="am-u-sm-3">
										<input type="text" id="fhr" name="fhr" style="float: left;"
											placeholder="复核人" value="${xf.fhr }" class="am-form-field"
											/>
									</div>
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label">收款人</label>
									<div class="am-u-sm-3">
										<input type="text" id="skr" name="skr" style="float: left;"
											placeholder="收款人" value="${xf.skr }" class="am-form-field"
											/>
									</div>
								</div> 
								<div style="display: none;" class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font
										color="red">*</font>开票人</label>
									<div class="am-u-sm-3">
										<input type="text" id="kpr" name="kpr" style="float: left;"
											placeholder="开票人" value="${xf.kpr }" class="am-form-field" required="required"
											/>
									</div> 
									<label style="display: none;" for="hc_yfphm" class="am-u-sm-3 am-form-label">商品编码版本</label>
									<div style="display: none;" class="am-u-sm-3">
										<select id="bmbb" name="bmbb">
											<option value="0">请选择</option>
											<c:forEach items="${bmbbs }" var="b">
												<option value="${b.id }">${b.bmbbh}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="am-form-group" style="padding-bottom: 1px;">
									<label for="hc_yfphm" class="am-u-sm-3" style="text-align: right;"><font color="red">*</font>开票类型</label>
									<c:forEach items="${fpzls }" var="f" varStatus="i">
										<c:if test="${i.index%3==0 }">
											<label for="hc_yfphm" class="am-u-sm-3"><input type="checkbox" id="fplx-${f.fpzldm }" name="fplx" value="${f.fpzldm }">${f.fpzlmc }</label>
										</c:if>
										<c:if test="${i.index%3!=0 }">
											<label for="hc_yfphm" class="am-u-sm-3"><input type="checkbox" id="fplx-${f.fpzldm }" name="fplx" value="${f.fpzldm }">${f.fpzlmc }</label>
										</c:if>
									</c:forEach>
								</div>
								<div class="am-form-group">
<!-- 									<a id="lastStep" class="js-button  am-btn am-radius am-btn-primary">上一步</a> -->
									<button id="save" class="js-submit  am-btn am-radius am-btn-primary">下一步</button>
<!-- 									<a id="nextStep" class="js-button  am-btn am-radius am-btn-secondary">下一步</a> -->
								</div>
							</div>
						</div>
					</div>
				</form>
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
<div class="am-modal am-modal-alert" tabindex="-1" id="sbh">
	<img alt="" src="img/sbh.png">
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
				var kpddm = $('#kpddm').val();
				$('#save').attr("disabled", true);
				var r = $("#frm").validator("isFormValid");
				if (r) {

							$('#save').attr("disabled", false);
							var data = $("#frm").serialize();
							$.ajax({
								url : "<%=request.getContextPath()%>/sksbxxzc/save",
								data : data,
								method : 'POST',
								success : function(data) {
									$('#save').attr("disabled", false);
									if (data.success) {
					                	$('#msg').html(data.msg);
					                	$('#my-alert').modal('open'); 
					                	location.href='xfxe';
									} else if (data.repeat) {
					                	$('#msg').html(data.msg);
					                	$('#my-alert').modal('open'); 
									}else{
					                	$('#msg').html(data.msg);
					                	$('#my-alert').modal('open'); 
									}
								},
								error : function() {
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
			$('#lastStep').click(function(){
				location.href='qymp';
			});
			$('#nextStep').click(function(){
				location.href='xfxe';
			});
		});
		function xssbh(){
			$("#sbh").modal("open");
		}
	</script>
</body>
</html>
