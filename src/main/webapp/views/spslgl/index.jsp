<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>商品管理</title>
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
<!-- <link rel="stylesheet" type="text/css" href="assets/css/easyui.css"> -->
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
		<%@ include file="../../pages/menus.jsp"%>
		<!-- sidebar end -->
		
		
		
		<!-- content start -->
		<div class="admin-content">
		
			<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
			  <ul class="am-tabs-nav am-nav am-nav-tabs">
			    <li class="am-active"><a href="#tab1">商品管理</a></li>
			    <li><a href="#tab2">商品组</a></li>
			  </ul>
			
			  <div class="am-tabs-bd">
			    <div class="am-tab-panel am-fade am-in am-active" id="tab1">
			     	<div class="am-cf am-padding">
						<div class="am-fl am-cf">
							<strong class="am-text-primary am-text-lg">基础数据</strong> / <strong>商品管理</strong>
						</div>
					</div>
					<hr />
		
					<div class="am-g  am-padding-top">
						<form action="#" class="js-search-form  am-form am-form-horizontal">
							<div class="am-g">
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_splb" class="am-u-sm-3 am-form-label">商品代码</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_spdm" name="s_spdm"
												placeholder="请输入商品代码" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_spmc" class="am-u-sm-3 am-form-label">商品名称</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_spmc" name="s_spmc"
												placeholder="请输入商品名称" />
										</div>
									</div>
								</div>
							</div>
		
							<hr />
		
							<div class="am-g am-text-right  am-padding-bottom ">
								<div class="am-u-sm-12">
									<button type="button" class="js-search  am-btn am-radius am-btn-success">查询</button>
									<button type="button" class="js-add  am-btn am-radius am-btn-success">新增</button>
									<button type="button" id="kp_dr"
										class="am-btn am-radius am-btn-default"
										style="margin-right: 10px;">
										<span></span> 批量导入
									</button>
								</div>
								<%--<div class="am-u-sm-6">--%>
								<%--<button type="button" class="js-import  am-btn am-btn-success">导入</button>--%>
								<%--<button type="button" class="js-export  am-btn am-btn-warning">导出</button>--%>
								<%--</div>--%>
							</div>
		
							<div class="am-u-sm-12">
								<div class="am-scrollable-horizontal">
		
									<table id="tbl"
										class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
										<thead>
											<tr>
												<th>序号</th>
												<th style="display: none;">id</th>
												<th>商品代码</th>
												<th>商品名称</th>
												<th>税率</th>
												<th>规格型号</th>
												<th>计量单位</th>
												<th>单价</th>
												<th>商品和服务税收分类编码</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
		
										</tbody>
									</table>
								</div>
							</div>
						</form>
		
					</div>
			    </div>
			    <div class="am-tab-panel am-fade" id="tab2">
					<div class="am-cf am-padding">
						<div class="am-fl am-cf">
							<strong class="am-text-primary am-text-lg">基础数据</strong> / <strong>商品组</strong>
						</div>
					</div>
					<hr />
		
					<div class="am-g  am-padding-top">
						<form action="#" class="js-search-form  am-form am-form-horizontal">
							<div class="am-g">
								<div class="am-u-sm-6">
									<div class="am-form-group">
										<label for="s_splb" class="am-u-sm-3 am-form-label">商品组名称</label>
										<div class="am-u-sm-9">
											<input type="text" id="s_spzmc" name="s_spzmc"
												placeholder="请输入商品代码" />
										</div>
									</div>
								</div>
								<div class="am-u-sm-6">
									
								</div>
							</div>
		
							<hr />
		
							<div class="am-g am-text-right am-padding-bottom ">
								<div class="am-u-sm-12">
									<button type="button" id="search" class="js-search1  am-btn am-radius am-btn-success">查询</button>
									<button type="button" id="new" class="js-add1  am-btn am-radius am-btn-success">新增</button>
									
								</div>
								<%--<div class="am-u-sm-6">--%>
								<%--<button type="button" class="js-import  am-btn am-btn-success">导入</button>--%>
								<%--<button type="button" class="js-export  am-btn am-btn-warning">导出</button>--%>
								<%--</div>--%>
							</div>
		
							<div class="am-u-sm-12">
								<div class="am-scrollable-horizontal">
		
									<table id="tbl1"
										class="js-table1  am-table am-table-bordered am-table-striped am-text-nowrap">
										<thead>
											<tr>
												<th>序号</th>
												<th style="display: none;">id</th>
												<th>商品组名称</th>
												<th>商品组类别</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
		
										</tbody>
									</table>
								</div>
							</div>
						</form>
		
					</div>
			    </div>
			  </div>
			</div>
		
		
			
		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">
					修改商品信息 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<hr />
					<form action="spslgl/edit"
						class="js-form  am-form am-form-horizontal">
						<div class="am-g">
							<div class="am-u-sm-12">
								<%--<div class="am-form-group">--%>
								<%--<label for="spflmc" class="am-u-sm-3 am-form-label">商品类别</label>--%>
								<%--<div class="am-u-sm-9">--%>
								<%--<!--  <input type="text" id="spflmc" name="spflmc" placeholder="请输入商品类别" />-->--%>
								<%--<select data-am-selected="{btnWidth: '100%'}" id="spflmc" name="spflmc">--%>
								<%--<option value="应税货物">应税货物</option>--%>
								<%--<option value="应税劳务">应税劳务</option>--%>
								<%--</select>--%>
								<%--</div>--%>
								<%--</div>--%>

								<div class="am-form-group">
									<label for="spdm" class="am-u-sm-4 am-form-label"><font color="red">*</font>商品代码</label>
									<div class="am-u-sm-8">
										<input type="text" id="spdm" name="spdm" placeholder="请输入商品代码" />
									</div>
								</div>

								<div class="am-form-group">
									<label for="spmc" class="am-u-sm-4 am-form-label"><font color="red">*</font>商品名称</label>
									<div class="am-u-sm-8">
										<input type="text" id="spmc" name="spmc" placeholder="请输入商品名称"
											required />
									</div>
								</div>

								<div class="am-form-group">
									<label for="smmc" class="am-u-sm-4 am-form-label"><font color="red">*</font>税率</label>
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
										<input type="text" id="spdw" name="spdw" placeholder="请输入计量单位" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="spdj" class="am-u-sm-4 am-form-label">单价</label>
									<div class="am-u-sm-8">
										<input type="text" id="spdj" pattern="^\d+(\.\d+)?$"
											name="spdj" placeholder="请输入单价(需为数字)" />
									</div>
								</div>
								<div class="am-form-group">
										<label for="spdj1" class="am-u-sm-4 am-form-label">商品和服务税收分类编码</label>
										<div class="am-u-sm-8" style="height: 35px;">
											<select id="spbm" name="spbm"
											data-am-dropdown="{boundary: '#spbm'} style="overflow:auto">
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
										<button type="submit" class="js-submit  am-btn am-radius am-btn-primary">保存</button>
										<button type="button" id="close"
											class="js-close  am-btn am-radius am-btn-danger" onclick="">取消</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal1">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">
					添加商品 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<hr />
					<form action="spslgl/add"
						class="js-form1  am-form am-form-horizontal">
						<div class="am-g">
							<div class="am-u-sm-12">
								<%--<div class="am-form-group">--%>
								<%--<label for="spflmc1" class="am-u-sm-3 am-form-label">商品类别</label>--%>
								<%--<div class="am-u-sm-9">--%>
								<%--<select data-am-selected="{btnWidth: '100%'}" id="spflmc1" name="spflmc1">--%>
								<%--<!--  <option value="" selected>点击选择...</option>-->--%>
								<%--<option value="应税货物">应税货物</option>--%>
								<%--<option value="应税劳务">应税劳务</option>--%>
								<%--</select>--%>
								<%--</div>--%>
								<%--</div>--%>

								<div class="am-form-group">
									<label for="spdm1" class="am-u-sm-4 am-form-label"><font color="red">*</font>商品编码</label>
									<div class="am-u-sm-8">
										<input type="text" id="spdm1" name="spdm1" maxlength="20"
											placeholder="请输入商品编码(不超过20位)" required />
									</div>
								</div>

								<div class="am-form-group">
									<label for="spmc1" class="am-u-sm-4 am-form-label"><font color="red">*</font>商品名称</label>
									<div class="am-u-sm-8">
										<input type="text" id="spmc1" name="spmc1"
											placeholder="请输入商品名称" required />
									</div>
								</div>

								<div class="am-form-group">
									<label for="smmc1" class="am-u-sm-4 am-form-label"><font color="red">*</font>税率</label>
									<div class="am-u-sm-8">
										<select data-am-selected="{btnWidth: '100%'}" id="smid1"
											name="smid1" required="required">
											<!--  <option value="" selected>点击选择...</option>-->
											<c:forEach items="${smlist}" var="ite">
												<option value="${ite.id}">${ite.sl}(税率)</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="am-form-group">
									<label for="spggxh1" class="am-u-sm-4 am-form-label">规格型号</label>
									<div class="am-u-sm-8">
										<input type="text" id="spggxh1" name="spggxh1"
											placeholder="请输入规格型号" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="spdw1" class="am-u-sm-4 am-form-label">计量单位</label>
									<div class="am-u-sm-8">
										<input type="text" id="spdw1" name="spdw1"
											placeholder="请输入计量单位" />
									</div>
								</div>
								<div class="am-form-group">
										<label for="spdj1" class="am-u-sm-4 am-form-label">单价</label>
										<div class="am-u-sm-8">
											<input type="text" id="spdj1" pattern="(^\d+(\.\d+)?$)?"
												name="spdj1" placeholder="请输入单价(需为数字)" />
										</div>
								</div>
								<div class="am-form-group">
										<label for="spdj1" class="am-u-sm-4 am-form-label">商品和服务税收分类编码</label>
										<div class="am-u-sm-8" style="height: 35px;">
											<select id="spbm1" name="spbm1"
											data-am-dropdown="{boundary: '#spbm1'} style="overflow:auto">
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
										<button type="submit"
											class="js-submit1  am-btn am-radius am-btn-primary">保存</button>
										<button type="button" id="close1"
											class="js-close1  am-btn am-radius am-btn-danger" onclick="">取消</button>
									</div>
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
					批量导入 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>

				<div class="am-tab-panel am-fade am-in am-active">
					<form class="am-form am-form-horizontal" id="importExcelForm"
						method="post"
						action="<%=request.getContextPath()%>/spslgl/importExcel"
						enctype="multipart/form-data">
						<div class="am-form-group">
							<div class="am-u-sm-12">
								<input type="file" class="am-u-sm-12" id="importFile"
									name="importFile" placeholder="选择要上传的文件" required>
							</div>
							<div class="am-u-sm-12" style="margin-top: 10px;">
								<button type="button" id="btnImport"
									class="am-btn am-radius am-btn-xs am-btn-secondary">导入</button>
								<button type="button" id="close2"
									class="am-btn am-radius am-btn-danger am-btn-xs">关闭</button>

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
		
		
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
			<div class="am-modal-dialog" style="overflow: auto;">
				<form id="form1" class="js-form-0 am-form am-form-horizontal">
					<div class="am-tabs" data-am-tabs>
						<ul class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tab1">商品组</a></li>
							<li><a href="#tab2">商品组商品</a></li>
							<li><a href="#tab3">商品组销方</a></li>
						</ul>
						<div class="am-tabs-bd">
							<div class="am-tab-panel am-fade am-in am-active" id="tab1">
								<div class="am-modal-hd" data-am-sticky>
									商品组 <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<hr />
									<div class="am-g">
										<div class="am-form-group">
											<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
												color="red">*</font>商品组名称</label>
											<div class="am-u-sm-8">
												<input type="text" id="spzmc" name="spzmc" placeholder="商品组名称"
													class="am-form-field" required maxlength="50" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="hc_yfphm" class="am-u-sm-4 am-form-label"><font
												color="red">*</font>商品组类别</label>
											<div class="am-u-sm-8">
												<select name="zbz" id="zbz" required>
													<option value="1">公共组</option>
													<option value="2">私有组</option>
												</select>
											</div>
										</div>
									</div>

								</div>
							</div>
							<div class="am-tab-panel am-fade" id="tab2">
								<div class="am-modal-hd" data-am-sticky>
									商品组商品<a href="javascript: void(0)" class="am-close am-close-spin"
										data-am-modal-close>&times;</a>
								</div>
								<table style="width: 100%;">
									<tr align="left">
										<td><input type="checkbox" id="spz"
											onclick="qxsp(this)" name="" value="roles" />&nbsp;&nbsp;全选</td>
									</tr>
									<c:forEach items="${sps }" var="s">
										<tr align="left">
											<td style="width: 100%" colspan="2"><input
												type="checkbox" id="spz-${s.spdm }"
												name="spz" value="${s.spdm }" />&nbsp;&nbsp;${s.spmc }
	
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div class="am-tab-panel am-fade" id="tab3">
								<div class="am-modal-hd" data-am-sticky>
									商品组销方<a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<table style="width: 100%;">
									<tr align="left">
										<td style="width: 100%" colspan="2">
											<div title="用户机构" style="padding: 10px;" id="bm-box1">

												<div class="am-panel-group" id="accordion">
													<input type="checkbox" onclick="xfqx(this)" id="all" name="all" />&nbsp;&nbsp;全选<br>
													<c:forEach items="${xfs }" var="x" varStatus="i">

<!-- 																<h4 class="am-panel-title" -->
<%-- 																	data-am-collapse="{parent: '#accordion', target: '#do-not-say-${x.id }'}"> --%>
																	<input type="checkbox" id="spz-${x.id }"
																		 name="xfid" value="${x.id }" />&nbsp;&nbsp;${x.xfmc }<br>
<!-- 																</h4> -->
													</c:forEach>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</div>
							<div class="am-text-center">
								<button type="button" id="save"
									class="am-btn am-btn-success">确定</button>
								<button type="button" id="close3"
									class="am-btn am-btn-warning">关闭</button>
							</div>
						</div>

					</div>
				</form>
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
	<script src="assets/js/jquery.form.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/spslgl.js"></script>
<!-- 	<script type="text/javascript" src="assets/js/jquery.easyui.min.js"></script> -->
<!-- <script type="text/javascript" src="assets/js/easyui-lang-zh_CN.js"></script> -->
	<script>
		$(function() {

// 			var selectbox=document.getElementById("spbm1");
			 
// 			//生成树下拉菜单
// 			var j="-";//前缀符号，用于显示父子关系，这里可以使用其它符号
// 			var datatree;
// 			$.ajax({
<%-- 				url : "<%=request.getContextPath()%>/spslgl/getSpbms", --%>
// 				async : false,
// 				method : 'POST',
// 				success : function(data) {
// 					selectbox.innerHTML=creatSelectTree(data);
// 				},
// 				error : function() {
// 					alert('操作失败!');
// 				}
// 			});
			$('#spbm').selected({
				searchBox: 1,
			    btnWidth: '300px',
			    btnSize: 'sm',
			    btnStyle: 'primary',
			    maxHeight: '100px',
			    dropUp: 1
			  });
			$('#spbm1').selected({
				searchBox: 1,
			    btnWidth: '300px',
			    btnSize: 'sm',
			    btnStyle: 'primary',
			    maxHeight: '100px',
			    dropUp: 1
			  });
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
				location.href = "spslgl/downloadDefaultImportTemplate";
			});
			
			function creatSelectTree(dt){
				 var option="";
				 for(var i=0;i<dt.length;i++){
				 if(dt[i].pid != null && dt[i].pid != 0){//如果有子集
				  option+="<option value='"+dt[i].id+"'>"+j+dt[i].text+"</option>";
				 j+="-";//前缀符号加一个符号
				  option+=creatSelectTree(dt[i]);//递归调用子集
				 j=j.slice(0,j.length-1);//每次递归结束返回上级时，前缀符号需要减一个符号
				  }else{//没有子集直接显示
				  option+="<option value='"+dt[i].id+"'>"+j+dt[i].text+"</option>";
				  }
				  }
				 return option;//返回最终html结果
			}

			
		});
		
		function qxsp(obj) {
			var smObj = document.getElementById('spz');
			var smObj1 = document.getElementsByName("spz");
			if (obj.checked == true) {
				for (var i = 0; i < smObj1.length; i++) {
					smObj1[i].checked = true;
				}
			}else{
				for (var i = 0; i < smObj1.length; i++) {
					smObj1[i].checked = false;
				}
			}
		}
		function xfqx(obj){
			var xfids = document.getElementsByName('xfid');
			if (obj.checked == true) {
				for (var i = 0; i < xfids.length; i++) {
					xfids[i].checked = true;
				}
			}else{
				for (var i = 0; i < xfids.length; i++) {
					xfids[i].checked = false;
				}
			}
		}
		
	</script>
</body>
</html>
