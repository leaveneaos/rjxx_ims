<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>开票点管理</title>
<meta name="description" content="开票点管理">
<meta name="keywords" content="开票点管理">
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
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>

<link rel="stylesheet" href="css/main.css" />
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="am-cf admin-main widget">
					<!-- sidebar start -->
					<!-- sidebar end -->

					<!-- content start -->
					<div class="admin-content">
						<input type="hidden" id="bj">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong id="yjcd" class="am-text-primary am-text-lg"></strong> /
								<strong id="ejcd"></strong>
								<button class="am-btn am-btn-success am-fr"
									data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
							</div>
						</div>
						<div id="doc-oc-demo3" class="am-offcanvas">
							<form action="" id="searchform1">
								<div class="am-offcanvas-bar am-offcanvas-bar-flip">
									<div class="am-offcanvas-content">
										<div class="am-form-group">
											<label for="s_kpddm" class="am-u-sm-4 am-form-label">开票点代码</label>
											<div class="am-u-sm-8">
												<input type="text" id="s_kpddm" name="s_kpddm"
													placeholder="开票点代码" />
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content">
										<div class="am-form-group">
											<label for="s_kpdmc" class="am-u-sm-4 am-form-label">开票点名称</label>
											<div class="am-u-sm-8">
												<input type="text" id="s_kpdmc" name="s_kpdmc"
													placeholder="开票点名称" />
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content">
										<div class="am-form-group">
											<label for="kpr1" class="am-u-sm-4 am-form-label">开票人</label>
											<div class="am-u-sm-8">
												<input type="text" id="kpr1" name="kpr1" placeholder="开票人" />
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content">
										<div class="am-form-group">
											<label for="xfid1" class="am-u-sm-4 am-form-label">销方</label>
											<div class="am-u-sm-7">
												<select id="xfid1" name="xfid1"
													data-am-selected="{btnSize: 'sm'}">
													<option value="0">请选择</option>
													<c:forEach items="${xfs}" var="item">
														<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content">
										<div class="am-form-group">
											<label for="sbcs1" class="am-u-sm-4 am-form-label">设备类型</label>
											<div class="am-u-sm-7">
												<select id="sbcs1" name="sbcs1"
													data-am-selected="{{btnSize: 'sm'}">
													<option value="">请选择</option>
													<option value="1">税控盘</option>
													<option value="2">金税盘</option>
												</select>
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content">
										<div class="am-form-group">
											<label for="kplx1" class="am-u-sm-4 am-form-label">开票类型</label>
											<div class="am-u-sm-7">
												<select id="kplx1" name="kplx1"
													data-am-selected="{{btnSize: 'sm'}">
													<option value="">请选择</option>
													<c:forEach items="${fpzls }" var="f">
														<option value="${f.fpzldm }">${f.fpzlmc }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div style="padding: 32px;">
										<button id="button3" type="button"
											class="am-btn am-btn-default am-btn-success data-back">查询</button>
									</div>
								</div>

							</form>

						</div>
						<div class="am-g am-padding-top">
							<form id="searchform"
								class="js-search-form am-form am-form-horizontal">
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button type="button" id="button2"
													class="am-btn am-btn-default am-btn-success">录入</button>
												<button type="button"
													class="js-sent am-btn am-btn-default am-btn-danger">
													删除</button>
												<button type="button" id="kp_dr"
													class="am-btn am-btn-default">导入</button>
											</div>
										</div>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
									<div class="am-form-group tpl-table-list-select">
										<select id="tip" data-am-selected="{btnSize: 'sm'}">
											<option value="0">请选择</option>
											<option value="1">开票点代码</option>
											<option value="2">开票点名称</option>
											<option value="3" selected="selected">销方名称</option>
											<option value="4">开票人</option>
										</select>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
									<div
										class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
										<input type="text" id="searchtxt" class="am-form-field ">
										<span class="am-input-group-btn" id="button1">
											<button
												class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search"
												type="button"></button>
										</span>
									</div>
								</div>

							</form>
							<div class="am-u-sm-12 am-padding-top">
								<div>

									<table id="tbl"
										class="js-table am-table am-table-bordered am-table-striped am-text-nowrap">
										<thead>
											<tr>
												<th><input type="checkbox" id="check_all" /></th>
												<th>序号</th>
												<th>操作</th>
												<th>销方名称</th>
												<th>开票点代码</th>
												<th>开票点名称</th>
												<th>设备类型</th>
												<th>设备号</th>
												<th>联系地址</th>
												<th>联系电话</th>
												<th>开户银行</th>
												<th>银行账号</th>
												<th>收款人</th>
												<th>复核人</th>
												<th>开票人</th>
												<th>开票类型</th>
												<th>是否无人值守</th>
												<th>品牌代码</th>
												<th>品牌名称</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>

							</div>
						</div>
					</div>

					<!-- model -->
					<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
						<div class="am-modal-dialog" style="overflow-y: auto;">
							<div class="am-modal-hd">
								开票点信息 <a href="javascript: void(0)"
									class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>
							<div class="am-modal-bd">
								<hr />
								<form action="sksbxxzc/save" method="get" autocomplete="off"
									class="js-form am-form am-form-horizontal">
									<div class="am-g">

										<div class="am-u-sm-12">

											<div class="am-form-group">
												<label for="xfid" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>销方名称</label>
												<div class="am-u-sm-10">
													<select id="xfid" name="xfid" required>
														<option value="0">请选择</option>
														<c:forEach items="${xfs}" var="item">
															<option value="${item.id}">${item.xfmc}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="am-form-group">
												<label for="kpddm" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>开票点代码</label>
												<div class="am-u-sm-4">
													<input type="text" id="kpddm" name="kpddm"
														placeholder="请输入开票点代码" required />
												</div>
												<label for="kpdmc" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>开票点名称</label>
												<div class="am-u-sm-4">
													<input type="text" id="kpdmc" name="kpdmc"
														placeholder="请输入开票点名称" required />
												</div>
											</div>
											<div class="am-form-group">
												<label for="sbcs" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>设备类型</label>
												<div class="am-u-sm-4">
													<select id="sbcs" name="sbcs">
														<option value="0">请选择</option>
														<option value="1">税控盘</option>
														<option value="2">金税盘</option>
													</select>
												</div>
												<label for="skph" class="am-u-sm-2 am-form-label">设备号</label>
												<div class="am-u-sm-4">
													<input type="text" id="skph" name="skph" autocomplete="off"
														placeholder="请输入设备号"/>
												</div>
											</div>
											<div class="am-form-group">
												<label for="lxdz" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>联系地址</label>
												<div class="am-u-sm-4">
													<input type="text" id="lxdz" name="lxdz"
														style="float: left;" placeholder="联系地址"
														value="${xf.xfdz }" class="am-form-field"
														required="required" />

												</div>
												<label for="lxdh" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>联系电话</label>
												<div class="am-u-sm-4">
													<input type="text" id="lxdh" name="lxdh"
														value="${xf.xfdh }" style="float: left;"
														placeholder="联系电话" class="am-form-field patternTelephone"
														required="required" />
												</div>
											</div>
											<div class="am-form-group">
												<label for="khyh" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>开户银行</label>
												<div class="am-u-sm-4">
													<input type="text" id="khyh" name="khyh"
														style="float: left;" placeholder="开户银行"
														value="${xf.xfyh }" class="am-form-field"
														required="required" />
												</div>
												<label for="yhzh" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>银行账号</label>
												<div class="am-u-sm-4">
													<input type="text" id="yhzh" name="yhzh"
														style="float: left;" placeholder="银行账号"
														value="${xf.xfyhzh }" class="am-form-field"
														required="required" />
												</div>
											</div>
											<div class="am-form-group">
												<label for="skr" class="am-u-sm-2 am-form-label">收款人</label>
												<div class="am-u-sm-4">
													<input type="text" id="skr" name="skr" style="float: left;"
														placeholder="收款人" value="${xf.skr }" class="am-form-field" />
												</div>
												<label for="fhr" class="am-u-sm-2 am-form-label">复核人</label>
												<div class="am-u-sm-4">
													<input type="text" id="fhr" name="fhr" style="float: left;"
														placeholder="复核人" value="${xf.fhr }" class="am-form-field" />
												</div>
											</div>
											<div class="am-form-group">
												<label for="kpr" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>开票人</label>
												<div class="am-u-sm-4">
													<input type="text" id="kpr" name="kpr" style="float: left;"
														placeholder="开票人" value="${xf.kpr }" class="am-form-field"
														required="required" />
												</div>
												<label for="kpdmc" class="am-u-sm-2 am-form-label">开票点品牌</label>
												<div class="am-u-sm-4">
													<select id="pid" name="pid">
														<option value="0">请选择</option>
														<c:forEach items="${pps}" var="item">
															<option value="${item.id}">${item.ppmc}(${item.ppdm})</option>
														</c:forEach>
													</select>
												</div>
											</div>

											<div class="am-form-group" style="text-align: left;">
												<label  class="am-u-sm-2 am-form-label"><font
													color="red">*</font>开票类型</label>
												<c:forEach items="${fpzls }" var="f">
													<label for="fplx-${f.fpzldm }" class="am-u-sm-5">
													<input type="checkbox" onclick="xzfp(this)" id="fplx-${f.fpzldm }" name="fplx"
														value="${f.fpzldm }">${f.fpzlmc }</label>
												</c:forEach>


												<label for="wrzs" class="am-u-sm-3">
													<input type="checkbox"  id="wrzs" name="wrzs" value="1"/>
													无人值守
												</label>
											</div>
											<div class="am-form-group" id="kplx-01">
												<label for="kpxe-01" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>专票限额</label>
												<div class="am-u-sm-4">
													<select id="kpxe-01" name="kpxe2" required="required">
														<option value="">请选择</option>
														<c:forEach items="${bc }" var="b">
															<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
														</c:forEach>
													</select>
												</div>
												<label for="fpje-01" class="am-u-sm-2 am-form-label">分票金额</label>
												<div class="am-u-sm-4">
													<input type="text" id="fpje-01" name="fpje2"
														style="float: left;" placeholder="分票金额"
														value="${skp.zpfz }"
														class="am-form-field am-text-right js-pattern-Money"/>
												</div>
											</div>
											<div class="am-form-group" id="kplx-02">
												<label for="kpxe-02" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>普票限额</label>
												<div class="am-u-sm-4">
													<select id="kpxe-02" name="kpxe1" required="required">
														<option value="">请选择</option>
														<c:forEach items="${bc }" var="b">
															<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
														</c:forEach>
													</select>
												</div>
												<label for="fpje-02" class="am-u-sm-2 am-form-label">分票金额</label>
												<div class="am-u-sm-4">
													<input type="text" id="fpje-02" name="fpje1"
														style="float: left;" placeholder="分票金额"
														value="${skp.ppfz }"
														class="am-form-field am-text-right js-pattern-Money" />
												</div>
											</div>
											<div class="am-form-group" id="kplx-12">
												<label for="kpxe-12" class="am-u-sm-2 am-form-label"><font
													color="red">*</font>电子发票限额</label>
												<div class="am-u-sm-4">
													<select id="kpxe-12" name="kpxe3" required="required">
														<option value="">请选择</option>
														<c:forEach items="${bc }" var="b">
															<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
														</c:forEach>
													</select>
												</div>
												<label for="fpje-12" class="am-u-sm-2 am-form-label">分票金额</label>
												<div class="am-u-sm-4">
													<input type="text" id="fpje-12" name="fpje3"
														style="float: left;" placeholder="分票金额"
														value="${skp.fpfz }"
														class="am-form-field am-text-right js-pattern-Money"/>
												</div>
											</div>
										</div>
									</div>

									<div class="am-u-sm-12">
										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="submit"
													class="js-submit  am-btn am-radius am-btn-success">保存</button>
												<button type="button"
													class="js-close  am-btn am-radius am-btn-warning"
													onclick="">取消</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="am-modal am-modal-no-btn" tabindex="-1"
						id="bulk-import-div">
						<div class="am-modal-dialog">
							<div class="am-modal-hd am-modal-footer-hd">
								批量导入 <a href="javascript: void(0)"
									class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>

							<div class="am-tab-panel am-fade am-in am-active">
								<form class="am-form am-form-horizontal" id="importExcelForm"
									method="post"
									action="<%=request.getContextPath()%>/sksbxxzc/importExcel"
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
												style="text-decoration: underline;">下载导入模板</a>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- content end -->

					<!-- loading do not delete -->
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
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>



	<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.form.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>

	<script src="assets/js/sksbxxzc.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script>
		$(function() {
			//批量导入
			var $importModal = $("#bulk-import-div");
			$("#kp_dr").click(function() {
				$importModal.modal({
					"width" : 600,
					"height" : 180
				});
			});
			//下载默认导入模板
			$("#btnDownloadDefaultTemplate").click(function() {
				location.href = "sksbxxzc/downloadDefaultImportTemplate";
			});
			// 修改样式位置
			$('#fplx-12').parent().css('margin-left','142px').removeClass('am-u-sm-5').addClass('am-u-sm-3');
			$('#fplx-03').parent().removeClass('am-u-sm-5').addClass('am-u-sm-4');
		});
		
		function xzfp(obj){
			var val = obj.value;
			var id = '#kplx-'+val;
			if (obj.checked == true) {
				$(id).show();
			}else{
				$(id).hide();
			}
		}

		

	</script>
</body>
</html>
