<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>发票库存监控</title>
	<meta name="description" content="发票库存监控">
	<meta name="keywords" content="发票库存监控">
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
													<select id="s_xfid" name="xfid" data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}">
														<option value="">请选择销方</option>
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
													<select id="s_skpid" name="skpid" data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}">
														<option value="">请选择开票点</option>
														<c:forEach items="${skpList}" var="skp">
															<option value="${skp.id}">${skp.kpdmc}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content top-position">
											<div class="am-form-group">
												<label for="s_fplx" class="am-u-sm-4 am-form-label">发票种类</label>
												<div class="am-u-sm-8">
													<select id="s_fplx" name="fpzldm" data-am-selected="{btnSize: 'sm'}">
														<option value="">请选择发票种类</option>
														<c:forEach items="${fplxList}" var="item">
															<option value="${item.fpzldm}">${item.fpzlmc}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<!-- <div class="am-offcanvas-content top-position">
                                            <div class="am-form-group">
                                                <label for="s_fpsl" class="am-u-sm-4 am-form-label">剩余库存</label>
                                                <div class="am-u-sm-8">
                                                    <select id="s_fpsl" name="fpsl" data-am-selected="{btnSize: 'sm'}">
                                                        <option value="">请选择</option>
                                                        <option value="500">小于500张</option>
                                                        <option value="200">小于200张</option>
                                                        <option value="100">小于100张</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div> -->
										<div style="padding: 32px;">
											<button type="button" id="jsSearch"
													class="am-btn am-btn-default am-btn-secondary data-back">
												查询
											</button>
										</div>
									</form>
								</div>
							</div>
						</div>
						<div class="am-g" style="margin-top: 20px">
							<form class="am-form">
								<div class="am-u-sm-2">
									<select  id="s_mainkey">
										<option value="xfsh">销方税号</option>
									</select>
								</div>
								<div class="am-u-sm-2">
									<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
										<input type="text" class="am-form-field" id="searchValue"> <span
											class="am-input-group-btn">
											<button id="searchButton"
													class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search"
													type="button"></button>
										</span>
									</div>
								</div>
								<button class="am-btn am-btn-secondary am-fr"
										data-am-offcanvas="{target: '#doc-oc-demo3'}" style="float: right">更多查询</button>
							</form>
						</div>

						<div class="am-g am-padding-top">
							<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
								<div class="am-form-group">
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
											<input type="hidden" id="searchbz">
										</div>
									</div>
								</div>
							</div>
							<div class="am-u-sm-12">
								<div>
									<table id="dyzytable"
										   class="am-table am-table-bordered am-table-compact am-table-striped am-text-nowrap">
										<thead>
										<tr>
											<th>序号</th>
											<th>操作</th>
											<%--<th>操作</th>--%>
											<th>销方名称</th>
											<th>销方税号</th>
											<th>开票点名称</th>
											<th>发票类型</th>
											<th>剩余库存(张)</th>
											<th>库存获取时间</th>
											<th>预警值</th>
										</tr>
										</thead>
									</table>
								</div>
							</div>
							<div class="am-u-sm-12">
								<div>
									<table id="fpkcMxtable"
										   class="am-table am-table-bordered am-table-compact am-table-striped am-text-nowrap">
										<thead>
										<tr>
											<th>序号</th>
											<th>发票代码</th>
											<th>发票起始号码</th>
											<th>发票终止号码</th>
											<th>发票份数</th>
											<th>剩余份数</th>
											<th>领购日期</th>
										</tr>
										</thead>
									</table>
								</div>
							</div>
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
																				class="js-submit am-btn am-btn-secondary">确定</button>
																		<button type="button"
																				onclick="$('#shezhi').modal('close');"
																				class="js-close am-btn am-btn-secondary">取消</button>
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
	</div>
</div>
<a href="#"
   class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>
<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
<script src="assets/js/amazeui.datatables.js"></script>
<script src="assets/js/amazeui.tree.min.js"></script>
<script src="assets/js/app.js"></script>
<script src="assets/js/kcjk.js"></script>
<script src="assets/js/sweetalert.min.js"></script>
<script>
    function getKpd() {
        var xfid = $('#xfsh option:selected').val();
        var skpid = $("#kpddm");
        $("#kpddm").empty();
        $.ajax({
            url : "fpkc/getKpd",
            data : {
                "xfid" : xfid
            },
            success : function(data) {
                for (var i = 0; i < data.length; i++) {
                    var option = $("<option>").text(data[i].kpdmc).val(
                        data[i].skpid);
                    skpid.append(option);
                }
            }

        });
    }
</script>
</body>
</html>