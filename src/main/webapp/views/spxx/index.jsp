<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>商品信息</title>
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
					<strong class="am-text-default am-text-lg">1创建企业名片&nbsp;></strong>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">2开票点信息</strong>&nbsp;>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">3开票限额</strong>>&nbsp;&nbsp;<strong class="am-text-primary am-text-lg">4商品信息</strong>>&nbsp;&nbsp;<strong class="am-text-default am-text-lg">5下载客户端</strong>
				</div>
			</div>
			<hr />
			<div style="padding: 100px;padding-top: 10px;">
							<div  class="am-u-sm-12">
							<table
								class="js-table am-table am-table-bordered am-text-nowrap am-scrollable-horizontal"
								id="jyls_table">
								<thead>
									<tr>
										<th>商品代码</th>
										<th>商品名称</th>
										<th>税率</th>
										<th>单位</th>
										<th>规格型号</th>
										<th>单价</th>
										<th>商品和税收编码</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>
						</div>
						<div  class="am-u-sm-12">
						<button id="button2" type="button" style="width: 100%" class="am-btn">
  									<span class="am-icon-plus"></span>新增商品信息
							</button>
			</div>
			
			<div style="margin-top: 100px;" class="am-u-sm-12  am-text-center">
													<button type="submit" id="xyb" style="width: 30%;" onclick="xyb()"
														class="js-submit  am-btn am-radius am-btn-primary">下一步</button>
					
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
	
<!-- 	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd" id="msg"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div> -->
	
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
					<!-- model -->
					<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
						<div class="am-modal-dialog">
							<div class="am-modal-hd">
								商品信息 <a href="javascript: void(0)"
									class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>
							<div class="am-modal-bd">
								<hr />
								<form action="" id="spform"
									class="js-form  am-form am-form-horizontal">
									<div class="am-g">
										<div class="am-u-sm-12">

											<div class="am-form-group">
												<label for="spdm" class="am-u-sm-4 am-form-label"><font
													color="red">*</font>商品代码</label>
												<div class="am-u-sm-8">
													<input type="text" id="spdm" name="spdm"
														placeholder="请输入商品代码" />
												</div>
											</div>

											<div class="am-form-group">
												<label for="spmc" class="am-u-sm-4 am-form-label"><font
													color="red">*</font>商品名称</label>
												<div class="am-u-sm-8">
													<input type="text" id="spmc" name="spmc"
														placeholder="请输入商品名称" required />
												</div>
											</div>

											<div class="am-form-group">
												<label for="smid" class="am-u-sm-4 am-form-label"><font
													color="red">*</font>税率</label>
												<div class="am-u-sm-8">
													<select id="smid" name="smid">
														<c:forEach items="${smlist}" var="ite">
															<option value="${ite.id}">${ite.sl}(税率)</option>
														</c:forEach>
													</select>
												</div>
											</div>

											<div class="am-form-group">
												<label for="spggxh" class="am-u-sm-4 am-form-label">规格型号</label>
												<div class="am-u-sm-8">
													<input type="text" id="spggxh" name="spggxh"
														placeholder="请输入规格型号" />
												</div>
											</div>
											<div class="am-form-group">
												<label for="spdw" class="am-u-sm-4 am-form-label">计量单位</label>
												<div class="am-u-sm-8">
													<input type="text" id="spdw" name="spdw"
														placeholder="请输入计量单位" />
												</div>
											</div>
											<div class="am-form-group">
												<label for="spdj" class="am-u-sm-4 am-form-label">单价</label>
												<div class="am-u-sm-8">
													<input type="text" id="spdj" class="js-pattern-Money am-text-right"
														name="spdj" placeholder="请输入单价(需为数字)" />
												</div>
											</div>
											<div class="am-form-group">
												<label for="spbm" class="am-u-sm-4 am-form-label"><font
													color="red">*</font>商品和服务税收分类编码</label>
												<div class="am-u-sm-8" style="height: 35px;">
													<select id="spbm" name="spbm"
														data-am-dropdown="{boundary: '#spbm'}" style="overflow:auto">
														<c:forEach items="${spbms }" var="bm">
															<option value="${bm.spbm }">${bm.spbm }|${bm.spmc }</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-u-sm-12">
											<div class="am-form-group">
												<div class="am-u-sm-12  am-text-center">
													<button type="submit" id="bcmx"
														class="js-submit  am-btn am-radius am-btn-primary">保存</button>
													<button type="button" id="close"
														class="js-close  am-btn am-radius am-btn-danger"
														onclick="">取消</button>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
		<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog" style="z-index: 9999">
			<div class="am-modal-hd">提示</div>
			<div class="am-modal-bd" id="msg"></div>
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
		     var jyspmx_edit_table = $('#jyls_table').DataTable({
		         "searching": false,
		         "bPaginate": false,
		         "bAutoWidth": false,
		         "bSort": false,
		     });
			$("#button2").on('click',
					function(e) {
				 $("#spform")[0].reset();
			/* 	e.preventDefault();
				var smdm = $("#smid").val();
				var text = $("#smid")
						.find("option[value=" + smdm + "]").text();
				var pos = text.indexOf("(");
				var sl = text.substring(pos + 1, text.length - 1); */
				 $('#your-modal').modal({
						"width" : 700,
						"height" : 500
					});
			});
			$("#close").click(function() {
				$('#your-modal').modal('close');
			});
			$('#spbm').selected({
				searchBox: 1,
			     btnWidth: '400px',
			    btnSize: 'sm',
			    btnStyle: 'primary',
			    maxHeight: '200px',
			    maxWidth: '200px',
			    dropUp: 1
			  });
			$("#bcmx").on('click', function(e) {
				 $("#spform").validator({
					submit : function() {
						var formValidity = this.isFormValid();
						if (formValidity) {
							$(".js-modal-loading").modal('toggle'); // show loading
							var data = $("#spform").serialize(); // get form data
							$.ajax({
								url : 'spslgl/add',
								data : data,
								method : 'POST',
								success : function(data) {
									if (data.success) {
										var sp = data.spfh;
										  var d = jyspmx_edit_table.rows().length;
										  jyspmx_edit_table.row.add([sp.spdm, sp.spmc,data.spfhsl,sp.spdw,sp.spggxh,sp.spdj,sp.spbm,"<a href='#'>删除</a>"
										                         ]).draw();
										$('#your-modal').modal('close'); // close modal
										$('#msg').html(data.msg);
										$('#my-alert').modal('open');
									//	_this.tableEx.ajax.reload(); // 重新加载数据
									} else if (data.failure) {
										 //$('#your-modal').modal('close')// close loading
										$('#msg').html(data.msg);
										$('#my-alert').modal('open');
									} else {
										// $('#your-modal').modal('close')// close loading
										$('#msg').html(data.msg);
										$('#my-alert').modal('open');
									}
									$(".js-modal-loading").modal('close'); // close loading
								},
								error : function() {
									$(".js-modal-loading").modal('close');
									 $('#your-modal').modal('close')// close loading
									$('#msg').html('添加数据失败，商品编码已经存在');
									$('#my-alert').modal('open');
								}

							});
							return false;
						} else {
							$('#msg').html('验证失败，请注意格式！');
							$('#my-alert').modal('open');
							return false;
						}
					}
				});
			})
		    $('#jyls_table tbody').on('click', 'a', function () {
		    	var sc = jyspmx_edit_table.row($(this).parents("tr"));
		    	   if (!confirm("您确认删除？")) {
						return;
					}
				$.ajax({
					url : 'spxx/delete',
					data : {spdm:$(this).parents("tr").children().eq(0).html()},
					method : 'POST',
					success : function(data) {
						if (data.success) {
							sc.remove().draw(false);
							$('#msg').html(data.msg);
							$('#my-alert').modal('open');
						}
					},
					error : function() {
						$('#msg').html('删除失败');
						$('#my-alert').modal('open');
						$(".js-modal-loading").modal('close'); // close loading
					}

				});
		    });
		});
		function xyb(){
			location.href= "download";
		}
	</script>
</body>
</html>
