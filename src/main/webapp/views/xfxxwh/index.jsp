<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>销方信息维护</title>
<meta name="description" content="销方信息维护">
<meta name="keywords" content="销方信息维护">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style>
.am-u-left {
	padding-left: 0em;
}
.right{
	text-align: right;
}
</style>
</head>
<body>
<div class="row-content am-cf">
    <div class="row">
        <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
            <div class="widget am-cf">
				<!--[if lte IE 9]>
				<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>以获得更好的体验！</p>
				<![endif]-->
				<div class="am-cf admin-main">
					<!-- sidebar start -->
					<!-- sidebar end -->
					<!-- content start -->
					<div class="admin-content">
						<input type="hidden" id="bj">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong id="yjcd" class="am-text-primary am-text-lg"></strong> / <strong id="ejcd"></strong>
								<button class="am-btn am-btn-success am-fr" data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
							</div>
							<!-- 侧边栏内容 -->
								<input type="text" hidden="true" id="bz">
								<div id="doc-oc-demo3" class="am-offcanvas">
									<form action="" id="searchForm1">
										<div class="am-offcanvas-bar am-offcanvas-bar-flip">
										    <div class="am-offcanvas-content">		      
											    <div class="am-form-group">
													<label for="sjxf" class="am-u-sm-4 am-form-label">上级销方</label>
													<div class="am-u-sm-6">
														<select id="sjxf1" name="sjxf1" data-am-selected="{btnSize: 'sm'}">
															<option value="">请选择</option>
															<c:forEach items="${xfs }" var="x">
																<option value="${x.id }">${x.xfmc }</option>
															</c:forEach>
														</select>
													</div>
												</div>
										    </div>
										    <div class="am-offcanvas-content">
											    <div class="am-form-group">
													<label for="xfmc" class="am-u-sm-4 am-form-label">销方税号</label>
													<div class="am-u-sm-8">
														<input type="text" id="s_xfsh" name="xfsh" placeholder="请输入销方税号" onkeyup="this.value=this.value.replace(/[, ]/g,'')" required="required" />
													</div>
												</div>
										    </div>
										    <div class="am-offcanvas-content">		      
											    <div class="am-form-group">
													<label for="xfmc" class="am-u-sm-4 am-form-label">销方名称</label>
													<div class="am-u-sm-8">
														<input type="text" id="s_xfmc" name="xfmc" placeholder="请输入销方名称"
															required="required" />
													</div>
												</div>
										    </div>
										    <div class="am-offcanvas-content">		      
											    <div class="am-form-group">
													<label for="kpr1" class="am-u-sm-4 am-form-label">开票人</label>
													<div class="am-u-sm-8">
														<input type="text" id="kpr1" name="kpr1" placeholder="请输入开票人"
															required="required" />
													</div>
												</div>
										    </div>
										    <div style="padding: 32px;">
		                                        <button id="button3" type="button" class="am-btn am-btn-default am-btn-success data-back"> 查询</button>
		                                    </div>
										  </div>
										  
									</form>
								  
								</div>
						</div>
						
			
						<div class="am-g  am-padding-top">
							<form action="#" id="searchForm" class="js-search-form  am-form am-form-horizontal">
							<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                    <div class="am-form-group">
                                        <div class="am-btn-toolbar">
                                            <div class="am-btn-group am-btn-group-xs">
                                                <button type="button"  id="button2" class="am-btn am-btn-default am-btn-success"> 录入</button>
<!--                                                 <button type="button"  id="button4" class="modify am-btn am-btn-default am-btn-warning"> 修改</button> -->
                                                <button type="button" id="deletexf" class="am-btn am-btn-default am-btn-danger js-sent"> 删除</button>
                                                <button type="button" id="kp_dr" class="am-btn am-btn-default"> 导入</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
                                    <div class="am-form-group tpl-table-list-select">
                                        <select id="tip1" data-am-selected="{btnSize: 'sm'}">
							              <option value="0">请选择</option>
							              <option value="1" selected="selected">销方名称</option>
							              <option value="2">销方税号</option>
							              <option value="3">上级销方名称</option>
							              <option value="4">开票人</option>
							            </select>
                                    </div>
                                </div>
                                <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <input type="text" id="searchtxt" class="am-form-field ">
                                        <span class="am-input-group-btn" id="button1">
								            <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="button"></button>
								        </span>
                                    </div>
                                </div>
								
							
							</form>
							<div class="am-u-sm-12 am-padding-top">
								<div>
			
									<table id="tbl" style="margin: 0"
										class="js-table am-table am-table-bordered am-table-striped am-text-nowrap">
										<thead>
											<tr>

												<th><input type="checkbox" id="check_all" /></th>
												<th>序号</th>
<!-- 												<th style="display: none;">销方id</th> -->
<!-- 												<th style="display: none;">上级销方id</th> -->
												<th>操作</th>
												<th>销方名称</th>
												<th>销方税号</th>
												
												<th>销方地址</th>
												<th>销方电话</th>
												<th>开户银行</th>
												<th>银行账号</th>
												<th>收款人</th>
												<th>复核人</th>
												<th>开票人</th>
												<th>上级销方</th>
												<!-- <th>作废人</th> -->
												<!-- <th>电子票开票限额</th>
												<th>电子票分票金额</th>
												<th>专票开票限额</th>
												<th>专票分票金额</th>
												<th>普票开票限额</th>
												<th>普票分票金额</th> -->
												
											</tr>
										</thead>
										<tbody>
										
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
			
					<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong"
						title="销方信息">
						<div class="am-modal-dialog" style="overflow: auto">
							<div class="am-modal-hd">
								销方信息 <a href="javascript: void(0)" class="am-close am-close-spin"
									data-am-modal-close>&times;</a>
							</div>
			
							<hr />
							<form class="js-form-0 am-form am-form-horizontal">
								<input type="hidden" name="xfid" id="xfid" />
								<div class="am-g">
									<div class="am-form-group">
										
									</div>
									<div class="am-form-group">
										<label for="xfsh" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>销方税号</label>
										<div class="am-u-sm-4">
											<input type="text" id="xfsh" class="js-pattern-Taxid"
												 name="xfsh"
												placeholder="请输入税号,15位、18位或20位" required onkeyup="this.value=this.value.replace(/[, ]/g,'')"/>
										</div>
										<label for="xfmc" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>销方名称</label>
										<div class="am-u-sm-4">
											<input type="text" id="xfmc" name="xfmc" placeholder="请输入销方名称"
												required="required" />
										</div>
									</div>
									<div class="am-form-group">
										<label for="khyh" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>开户银行</label>
										<div class="am-u-sm-4">
											<input type="text" id="khyh" name="khyh" placeholder="请输入开户银行"/>
										</div>
										<label for="yhzh" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>银行账号</label>
										<div class="am-u-sm-4">
											<input type="text" id="yhzh" pattern="^[0-9a-zA-Z]{4,30}$"
												name="yhzh" placeholder="请输入销方银行帐号"/ onkeyup="this.value=this.value.replace(/[, ]/g,'')">
										</div>
									</div>
									<div class="am-form-group">
										<label for="dz" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>销方地址</label>
										<div class="am-u-sm-4">
											<input type="text" id="dz" name="dz" placeholder="请输入销方地址"
												 />
										</div>
										<label for="xfdh" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>销方电话</label>
										<div class="am-u-sm-4">
											<input type="text" maxlength="20" id="xfdh" name="xfdh" placeholder="销售方联系电话">
										</div>
									</div>
			
									<!-- <div class="am-form-group">
										<label for="xflxr" class="am-u-sm-2 am-form-label">联系人</label>
										<div class="am-u-sm-4">
											<input type="text" id="xflxr" name="xflxr"
												placeholder="请输入销方联系人">
										</div>
										<label for="xfyb" class="am-u-sm-2 am-form-label">销方邮编</label>
										<div class="am-u-sm-4">
											<input type="text" pattern="^\d{6}$" id="xfyb" name="xfyb"
												placeholder="输入应为国内邮政编码" />
										</div>
									</div> -->
									<div class="am-form-group">
										<label for="kpr" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>开票人</label>
										<div class="am-u-sm-4">
											<input type="text" id="kpr" name="kpr" placeholder="请输入开票人"/>
										</div>
										<label for="fhr" class="am-u-sm-2 am-form-label">复核人</label>
										<div class="am-u-sm-4">
											<input type="text" id="fhr" name="fhr" placeholder="请输入复核人" />
			
										</div>
										<!-- <label for="zfr" class="am-u-sm-2 am-form-label">作废人</label>
										<div class="am-u-sm-4">
											<input type="text" id="zfr" name="zfr" placeholder="请输入作废人" />
										</div> -->
									</div>
			
									<div class="am-form-group">
										<label for="skr" class="am-u-sm-2 am-form-label">收款人</label>
										<div class="am-u-sm-4">
											<input type="text" id="skr" name="skr" placeholder="请输入收款人" />
										</div>
										<label for="xfsh" class="am-u-sm-2 am-form-label">上级销方</label>
										<div class="am-u-sm-4">
											<select id="sjxf" name="sjxf">
											<option value="0">请选择</option>
												<c:forEach items="${xfs }" var="x">
													<option value="${x.id }">${x.xfmc }</option>
												</c:forEach>
											</select>
										</div>
										
									</div>
			
									
									<div class="am-form-group">
										<label for="dzpzdje" class="am-u-sm-2 am-form-label am-u-left">电子票开票限额</label>
											<div class="am-u-sm-4">
												<select id="dzpzdje" name="dzpzdje">
													<option value="">请选择</option>
													<c:forEach items="${bc }" var="b">
														<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
													</c:forEach>
												</select>
											</div>
										<label for="dzpfpje" class="am-u-sm-2 am-form-label am-u-left">电子票分票金额</label>
										<div class="am-u-sm-4">
											<input type="text" id="dzpfpje" name="dzpfpje" class="js-pattern-Money am-text-right"
												
												placeholder="大于零且小于开票限额"/>
										</div>
									</div>
									<div class="am-form-group">
										<label for="zpzdje" class="am-u-sm-2 am-form-label am-u-left">专票开票限额</label>
											<div class="am-u-sm-4">
												<select id="zpzdje" name="zpzdje">
													<option value="">请选择</option>
													<c:forEach items="${bc }" var="b">
														<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
													</c:forEach>
												</select>
											</div>
										<label for="zpfpje" class="am-u-sm-2 am-form-label am-u-left">专票分票金额</label>
										<div class="am-u-sm-4">
											<input type="text" id="zpfpje" name="zpfpje"
												 class="js-pattern-Money am-text-right"
												placeholder="大于零且小于开票限额"/>
										</div>
									</div>
									<div class="am-form-group">
										<label for="ppzdje" class="am-u-sm-2 am-form-label am-u-left">普票开票限额</label>
											<div class="am-u-sm-4">
												<select id="ppzdje" name="ppzdje">
													<option value="">请选择</option>
													<c:forEach items="${bc }" var="b">
														<option value="${b.zdkpxe }">${b.fpbcmc }(${b.zdkpxe })</option>
													</c:forEach>
												</select>
											</div>
										<label for="ppfpje" class="am-u-sm-2 am-form-label am-u-left">普票分票金额</label>
										<div class="am-u-sm-4">
											<input type="text" id="ppfpje" name="ppfpje"  class="js-pattern-Money am-text-right"
												
												placeholder="大于零且小于开票限额"/>
										</div>
									</div>
			
			
									<div class="am-u-sm-12 am-margin-top-lg">
										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="submit" class="am-btn am-btn-default am-btn-secondary"> 保存</button>
												<button type="button" class="js-close am-btn am-btn-default am-btn-warning">取消</button>
											</div>
										</div>
									</div>
								</div>
							</form>
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
									action="<%=request.getContextPath()%>/xfxxwh/importExcel"
									enctype="multipart/form-data">
									<div class="am-form-group">
										<div class="am-u-sm-12">
											<input type="file" class="am-u-sm-12" id="importFile"
												name="importFile" placeholder="选择要上传的文件" required>
										</div>
										<div class="am-u-sm-12" style="margin-top: 10px;">
											<button type="button" id="btnImport"
												class="am-btn am-btn-xs am-btn-success">导入</button>
											<button type="button" id="close1"
												class="am-btn am-btn-warning am-btn-xs">关闭</button>
			
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
	<script src="assets/js/format.js"></script>
	<script src="assets/js/xfxxwh_app.js"></script>
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
				location.href = "xfxxwh/downloadDefaultImportTemplate";
			});
		});
	</script>

</body>
</html>
