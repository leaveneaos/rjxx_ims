<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>订阅管理</title>
<meta name="description" content="订阅管理">
<meta name="keywords" content="订阅管理">
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
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
.top-position {
	margin-top: 8px
}
</style>
</head>
<body>
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="am-cf admin-main">
						<!-- content start -->
						<div class="admin-content">
							<div class="am-cf widget-head">
								<div class="widget-title am-cf">
									<strong id="yjcd" class="am-text-primary am-text-lg"></strong>
									/ <strong id="ejcd"></strong>
								</div>
							</div>
							<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
								<ul class="am-tabs-nav am-nav am-nav-tabs">
									<li class="am-active"><a href="#tab1">消息订阅</a></li>
									<li><a href="#tab2">报表订阅</a></li>
								</ul>
								<div class="am-tabs-bd">
									<div class="am-tab-panel am-fade am-in am-active" id="tab1">
										<form action="#" class="jsform am-form">
											<div class="am-u-sm-12">
												<button type="button" id="jsAdd"
													class="am-btn am-btn-default am-btn-secondary">保存</button>
											</div>
											<br> <br>
											<div class="am-u-12">
												<table id="mydytable"
													class="js-table am-table am-table-bordered">
													<thead>
														<tr>
															<th>订阅种类</th>
															<th>订阅方式</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${dyzlList}" var="dyzl">
															<tr>
																<td>${dyzl.dyzlmc}<input type="hidden"
																	value="${dyzl.dyzldm}"></td>
																<td align="center"><c:forEach items="${dyfsList}"
																		var="dyfs">
																		<c:if test="${dyzl.dyzldm==dyfs.dyzldm }">
																			<input type="checkbox" name="checkbox"
																				class="${dyfs.dyfsdm}" id="${dyfs.dyzldm}-${dyfs.dyfsdm}"
																				value="${dyfs.dyzldm}-${dyfs.dyfsdm}">${dyfs.dyfsmc}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
																	</c:forEach></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
											<!-- 弹出框开始 -->

											<!-- 弹出框结束 -->
										</form>
									</div>
									<div class="am-tab-panel am-fade am-u-sm-12" id="tab2"></div>
								</div>
							</div>
						</div>
						<!-- content end -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="addModal">
		<div class="am-modal-dialog" style="height: auto; width: 520px">
			<div class="am-modal-hd">
				订阅信息录入<a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<hr />
				<form class="am-form" id="dyform">
					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<label for="xm" class="am-u-sm-4 am-form-label"><font
											color="red">*</font>用户名称</label>
								<div class="am-u-sm-8">
									<input type="text" class="am-form-field" id="s_yhmc" name="xm"
										required maxlength="50" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 top-position" id="div-dx">
							<div class="am-form-group">
								<label for="sjhm" class="am-u-sm-4 am-form-label">&nbsp;手机号码</label>
								<div class="am-u-sm-8">
									<input type="text"
										class="js-pattern-patternPhone am-form-field" id="s_sjhm"
										name="sjhm" placeholder="请输入手机号码" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 top-position" id="div-yx">
							<div class="am-form-group">
								<label for="kpddm" class="am-u-sm-4 am-form-label">邮箱</label>
								<div class="am-u-sm-8">
									<input type="email" class="am-form-field" id="s_email"
										name="yx" placeholder="请输入邮箱" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 top-position" id="div-scewm">
							<div class="am-form-group">
								<a href="javascript:scEwm()"><label class="am-u-sm-4 am-form-label">重新绑定微信</label></a>
								<div class="am-u-sm-8">
									
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 top-position" id="div-wx">
							<div class="am-form-group">
								<label for="ewm" class="am-u-sm-4 am-form-label">微信扫描关注</label>
								<div class="am-u-sm-8">
									<img id="doc-qrcode" 
									style="width: 160px;height: 160px;float: left;">
									<input type="hidden" name="wx_openid" id="s_openid">
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 top-position">
						<div class="am-form-group">
							<div class="am-u-sm-12  am-text-center">
								<button type="submit"
									class="js-submit am-btn am-radius am-btn-success">保存</button>
								&nbsp;&nbsp;
								<button type="button"
									class="js-close am-btn am-radius am-btn-warning" onclick="">取消</button>
							</div>
						</div>
					</div>
					</div>					
				</form>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd" id="alert-msg"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/yhdy.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script type="text/javascript">
	    function scEwm(){
	    	$('#div-scewm').hide();
	    	var url = window.location.href;
			//生成二维码
			$.ajax({
				url : 'wxdy/getEwm',
				data:{"url":url},
				method : 'post',
				success : function(data) {
					if (data.success) {
						$('#doc-qrcode').attr("src",data.id);
						//$('#doc-qrcode').qrcode({text:data.url,width:160,height:160});
					}
				}
			});
			$('#div-wx').show();
	    }
	</script>
</body>
</html>