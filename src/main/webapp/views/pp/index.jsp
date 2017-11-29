<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>品牌信息维护</title>
<meta name="description" content="品牌信息维护">
<meta name="keywords" content="品牌信息维护">
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
							<%--<div class="widget-title am-cf">
								<strong id="yjcd" class="am-text-primary am-text-lg"></strong> / <strong id="ejcd"></strong>
								<button class="am-btn am-btn-success am-fr" data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
							</div>--%>
							<!-- 侧边栏内容 -->
								<%--<input type="text" hidden="true" id="bz">--%>
								<%--<div id="doc-oc-demo3" class="am-offcanvas">
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
								  
								</div>--%>
						</div>

						<div class="am-g  am-padding-top" style="margin-top: 2%">
							<form action="#" id="searchForm" class="js-search-form  am-form am-form-horizontal">
							<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                    <div class="am-form-group">
                                        <div class="am-btn-toolbar">
                                            <div class="am-btn-group am-btn-group-xs">
                                                <button type="button"  id="button2" class="am-btn am-btn-default am-btn-success"> 录入</button>
                                                <button type="button" id="deletexf" class="am-btn am-btn-default am-btn-danger js-sent"> 删除</button>
                                                <%--<button type="button" id="kp_dr" class="am-btn am-btn-default"> 导入</button>--%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
                                    <div class="am-form-group tpl-table-list-select">
                                        <select id="tip" data-am-selected="{btnSize: 'sm'}">
							              <option value="0">请选择</option>
							              <option value="1" selected="selected">品牌名称</option>
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
												<th>操作</th>
												<th>品牌名称</th>
												<th>品牌代码</th>
												<th>品牌地址</th>
												<th>发票管家品牌代码</th>
												<th>微信logo地址</th>
											</tr>
										</thead>
										<tbody>
										
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
			
					<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal"
						title="品牌信息">
						<div class="am-modal-dialog" style="overflow: auto">
							<div class="am-modal-hd">
								品牌信息 <a href="javascript: void(0)" class="am-close am-close-spin"
									data-am-modal-close>&times;</a>
							</div>
			
							<hr />
							<form action="pp/save" method="get" autocomplete="off" class="js-form am-form am-form-horizontal">
								<div class="am-g">
									<div class="am-form-group">
										
									<div class="am-form-group">
										<label for="ppmc" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>品牌名称</label>
										<div class="am-u-sm-4">
											<input type="text" id="ppmc" name="ppmc" required
												placeholder="请输入品牌名称"  />
										</div>
										<label for="ppdm" class="am-u-sm-2 am-form-label"><font
											color="red">*</font>品牌代码</label>
										<div class="am-u-sm-4">
											<input type="text" id="ppdm" name="ppdm" placeholder="请输入品牌代码" required
												 />
										</div>
									</div>
									<div class="am-form-group">
										<label for="ppurl" class="am-u-sm-2 am-form-label">品牌地址</label>
										<div class="am-u-sm-4">
											<input type="text" id="ppurl" name="ppurl" placeholder="请输入品牌地址"/>
										</div>
										<label for="aliMShortName" class="am-u-sm-2 am-form-label">发票管家代码</label>
										<div class="am-u-sm-4">
											<input type="text" id="aliMShortName" name="aliMShortName" placeholder="请输入发票管家品牌代码">
										</div>
									</div>
									<div class="am-form-group">
										<label for="wechatLogoUrl" class="am-u-sm-2 am-form-label">微信logoURL</label>
										<div class="am-u-sm-4">
											<input type="text" id="wechatLogoUrl" name="wechatLogoUrl" placeholder="请输入微信logoURL">
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
	<script src="assets/js/pp.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script>
		/*$(function() {
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
		});*/
	</script>

</body>
</html>
