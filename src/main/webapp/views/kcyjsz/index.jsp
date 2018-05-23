<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票预警订阅</title>
<meta name="description" content="发票预警订阅">
<meta name="keywords" content="发票预警订阅">
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
						<input type="hidden" id="xfidhide">
						<!-- content start -->
						<div class="admin-content">
							<div class="am-cf widget-head">
								<div class="widget-title am-cf">
									<strong id="yjcd" class="am-text-primary am-text-lg" style="color: #838FA1;"></strong> / <strong id="ejcd" style="color: #0e90d2;"></strong>
								</div>
								<div id="doc-oc-demo3" class="am-offcanvas">
									<div class="am-offcanvas-bar am-offcanvas-bar-flip">
										<form class="js-search-form am-form">
											<div class="am-offcanvas-content">
												<div class="am-form-group">
													<label for="s_xfmc" class="am-u-sm-4 am-form-label">销方名称</label>
													<div class="am-u-sm-8">
														<select id="xfid" name="xfid"
																data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}">
															<option value="">请选择</option>
															<c:forEach items="${xfList}" var="xf">
																<option value="${xf.id}">${xf.xfmc}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content top-position">
												<div class="am-form-group">
													<label for="s_kpdmc" class="am-u-sm-4 am-form-label">开票点</label>
													<div class="am-u-sm-8">
														<select id="s_skpid" name="skpid" class="am-u-sm-7"
																data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}">

														</select>
													</div>
												</div>
											</div>
											<div class="am-offcanvas-content top-position">
												<div class="am-form-group">
													<label for="s_fplx" class="am-u-sm-4 am-form-label">发票种类</label>
													<div class="am-u-sm-8">
														<select id="s_fplx" name="fpzldm"
															data-am-selected="{btnSize: 'sm'}">
															<option value="">请选择发票类型</option>
															<c:forEach items="${fplxList}" var="item">
																<option value="${item.fpzldm}">${item.fpzlmc}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div style="padding: 32px;">
												<button type="button" id="button1"
													class="am-btn am-btn-default am-btn-secondary data-back">
													 查询
												</button>
											</div>
									</form>
								</div>
							</div>
								<div class="am-g">
									<form  id="searchForm" class="am-form am-form-horizontal">
										<div class="am-form-group am-form-group-sm" style="margin-top: 20px">
											<div class="am-u-sm-3">
												<select data-am-selected="{btnSize: 'sm'}" id="s_mainkey">
													<option value="xfsh">销方税号</option>
												</select>
											</div>
											<div class="am-u-sm-3">
												<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
													<input type="text" class="am-form-field" id="searchValue">
													<span class="am-input-group-btn">
											<button id="searchButton"
													class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search"
													type="button"></button>
										</span>
												</div>
											</div>
											<button class="am-btn am-btn-secondary am-fr"
													data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
										</div>
									</form>
								</div>
							<div class="am-g  am-padding-top">
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs ">
												<input type="hidden" id="searchbz">
												<%--<button class="am-btn am-btn-warning" id="button3">
													<i class="am-icon-plus"></i>&nbsp;新增预警
												</button>--%>
												<button class="am-btn am-btn-secondary" id="button2">
													设置通知方式
												</button>
											</div>
										</div>
									</div>
								</div>
								<div class="am-u-sm-12">
									<div >
										<table id="yjdytable"
											class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
											<thead>
												<tr>
													<th><input type="checkbox" id="selectAll" /></th>
													<th>序号</th>
													<th>操作</th>
													<th>销方名称</th>
													<th>销方税号</th>
                                                    <th>税控盘号</th>
													<th>门店名称</th>
													<th>发票种类</th>
                                                    <th>开票方式</th>
													<%--<th>剩余库存(张)</th>--%>
													<th>库存预警阈值(张)</th>
													<th>发票库存(张)</th>
													<th>库存更新时间</th>
													<th style="display: none">csz</th>
                                                    <%--<th style="display: none">skpid</th>
                                                    <th style="display: none">fpzldm</th>--%>
												</tr>
											</thead>
										</table>
									</div>
								</div>
                                <div class="am-u-sm-12">
                                    <div>
                                        <table id="yjszMxtable"
                                               class="am-table am-table-bordered am-table-compact am-table-hover am-table-striped am-text-nowrap">
                                            <thead>
                                            <tr>
                                                <th>序号</th>
                                                <th>销方名称</th>
                                                <th>销方税号</th>
                                                <th>门店名称</th>
                                                <th>发票种类</th>
                                                <th>通知方式</th>
                                                <th>通知人员</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </div>
							</div>
						</div>
						<!-- content end -->

						<!-- model begin-->
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="shezhi">
							<div class="am-modal-dialog" style="height: 240px; width: 400px">
								<form id ="xgform" class="js-form-yjsz am-form">
									<div class="am-tabs" data-am-tabs>
										<div class="am-tabs-nav am-nav am-nav-tabs">
											<label>预警值设置</label>
										</div>
										<div class="am-tabs-bd">
											<div class="am-tab-panel am-in am-active" id="tab1">
												<div class="am-modal-bd">
													<div class="am-g">
														<div class="am-u-sm-12">
															<table
																class="am-table am-table-bordered">								
																<tr>
																	<td><span style="color: red;">*</span>库存预警阈值</td>
																	<td><input type="text" id="yjkcl" name="yjkcl"
																		pattern="^\d{0,8}$" placeholder="请输入库存预警阈值"
																		class="am-form-field" required>
																		<input type="hidden" id="xg_id" name="xg_id">
																	</td>
																</tr>
															</table>
														</div>
														<div class="am-u-sm-12">
															<div class="am-form-group">
																<div class="am-u-sm-12  am-text-center">
																	<button type="submit"
																		class="js-submit am-btn am-btn-primary">确定</button>
																	<button type="button"
																		onclick="$('#shezhi').modal('close');"
																		class="js-close am-btn am-btn-danger">取消</button>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
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
	</div>
	<!-- model end-->
</div>
	<!-- model begin-->
	<div class="am-modal am-modal-no-btn" tabindex="-1"
		 id="bulk-add-div">
		<div class="am-modal-dialog">
			<div class="am-modal-hd am-modal-footer-hd">
				新增预警值
			</div>
			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal" id="addDataForm"
					  enctype="multipart/form-data">
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label"><font color="red">*</font>选择销方</label>
							<div class="am-u-sm-6">
								<select id="add_xfid" name="add_xfid" class="am-u-sm-6">
										<option value="">请选择</option>
										<c:forEach items="${xfList}" var="item">
											<option value="${item.id}">${item.xfmc}</option>
										</c:forEach>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label"><font color="red">*</font>选择开票点</label>
							<div class="am-u-sm-6">
								<input type="hidden" id="add_kpfs" name="add_kpfs" readonly/>
								<select id="add_skp" name="add_skp" class="am-u-sm-6">
								</select>
							</div>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label"><font color="red">*</font>开票方式</label>
							<div class="am-u-sm-6">
								<input type="text" id="show_kpfs" name="show_kpfs" readonly/>
							</div>
						</div>
                        <div class="am-u-sm-12">
                            <label class="am-u-sm-4 am-form-label"><font color="red">*</font>选择票种</label>
                            <div class="am-u-sm-6">
                                <select id="add_fpzldm" name="add_fpzldm" class="am-u-sm-6">
                                </select>
                            </div>
                        </div>
                        <div class="am-u-sm-12">
                            <label class="am-u-sm-4 am-form-label"><font color="red">*</font>预警值(张)</label>
                            <div class="am-u-sm-6">
                                <input type="text" id="add_yjyz" name="add_yjyz" class="am-u-sm-6" placeholder="请输入预警值">
                                </input>
                            </div>
                        </div>
						<div class="am-u-sm-12" style="margin-top: 30px;">
							<button type="button" id="btadd"
									class="am-btn am-btn-xs am-btn-primary">确定</button>
							<button type="button" id="btclose"
									class="am-btn am-btn-danger am-btn-xs">关闭</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- model begin-->

	<!-- model begin-->
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
		<div class="am-modal-dialog" style="overflow: auto;">
			<div class="am-modal-hd">
				预警通知方式 <a href="javascript: void(0)"
						class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<form id="fomm" class="js-form-0 am-form am-form-horizontal">
				<input type="hidden" id="yjids"  name="yjids" />
				<div class="am-tabs" data-am-tabs>
					<ul class="am-tabs-nav am-nav am-nav-tabs">
						<li class="am-active"><a href="#tab1">通知方式</a></li>
						<li><a href="#tab2">通知用户</a></li>
					</ul>
					<div class="am-tabs-bd">
						<div class="am-tab-panel am-fade am-in am-active" id="tab1">
									<table style="width: 100%;">
										<tr align="left">
											<td><input type="checkbox" id="tzfs"
													   onclick="qxtzfs(this)" name="tzfs" value="01" />&nbsp;&nbsp;全选</td>
										</tr>
										<tr align="left">
											<td style="width: 20%"><input
													type="checkbox" id="tzfs-1" name="tzfsid"
													value="02"/>&nbsp;&nbsp;邮件通知
												<input type="checkbox" id="tzfs-2" name="tzfsid"
														value="03"/>&nbsp;&nbsp;短信通知</td>
										</tr>
									</table>
									<div class="am-form-group am-padding-top">
										<div class="am-u-sm-12  am-text-center">
											<button type="submit"
													class="js-submit  am-btn am-radius am-btn-success">确定</button>
											<button type="button" onclick="$('#hongchong').modal('close');"
													class="js-close  am-btn am-radius am-btn-warning">取消</button>
										</div>
									</div>
						</div>
						<div class="am-tab-panel am-fade" id="tab2">
							<table style="width: 100%;">
								<tr align="left">
									<td><input type="checkbox" id="roles"
											   onclick="qxyh(this)" name="roles" value="roles" />&nbsp;&nbsp;全选</td>
								</tr>
								<c:forEach items="${yhList}" var="j">
									<tr align="left">
										<td style="width: 100%" colspan="2"><input
												type="checkbox" id="yh-${j.id }" name="yhid"
												value="${j.id }" />&nbsp;&nbsp;${j.yhmc }</td>
									</tr>
								</c:forEach>
							</table>
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<div class="am-u-sm-12  am-text-center">
										<button type="submit"
												class="js-submit  am-btn am-radius am-btn-success">确定</button>
										<button type="button" onclick="$('#hongchong').modal('close');"
												class="js-close  am-btn am-radius am-btn-warning">取消</button>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
			</form>
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/jquery.form.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/kcyjsz.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>

    <script type="text/javascript">
        function qxtzfs(obj) {
            //var smObj = document.getElementById('tzfs');
            var smObj1 = document.getElementsByName("tzfsid");
            if (obj.checked == true) {
                for (var i = 0; i < smObj1.length; i++) {
                    smObj1[i].checked = true;
                }
            } else {
                for (var i = 0; i < smObj1.length; i++) {
                    smObj1[i].checked = false;
                }
            }
        }

        function qxyh(obj) {
            //var smObj = document.getElementById('tzfs');
            var smObj1 = document.getElementsByName("yhid");
            if (obj.checked == true) {
                for (var i = 0; i < smObj1.length; i++) {
                    smObj1[i].checked = true;
                }
            } else {
                for (var i = 0; i < smObj1.length; i++) {
                    smObj1[i].checked = false;
                }
            }
        }
	</script>
</body>
</html>