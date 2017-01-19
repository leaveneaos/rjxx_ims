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
<link rel="stylesheet" href="css/main.css" />
</head>
<body>
	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../../pages/menus.jsp"%>
		<!-- sidebar end -->
		<input type="hidden" id="xfidhide">
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">订阅管理</strong> / <small>订阅管理</small>
				</div>
			</div>
			<hr />

			<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">我的订阅</a></li>
					<li><a href="#tab2">订阅库</a></li>
				</ul>
				<div class="am-tabs-bd">	    
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
					<button class="am-btn am-btn-primary" style="float:right;" id="jsAdd">
						<i class="am-icon-plus"></i>添加订阅
					</button>
                         <table id="mydytable"
							class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
							<thead>
								<tr>
									<th>序号</th>
									<th>标题</th>									
									<th>订阅方式</th>
									<th>操作</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="am-tab-panel am-fade am-u-sm-12" id="tab2">
					    <div class="am-u-sm-12">
                    	<div class="am-u-sm-6">
	                        <div class="am-form-group">
	                            <label for="s_dymc" class="am-u-sm-3 am-form-label">订阅主题</label>
	                            <div class="am-u-sm-9">
	                                <select id="dybtid" name="dybtid" class="am-u-sm-12" onchange="getBz()">
											<option value="">----请选择----</option>
											<c:forEach items="${dybtList}" var="item">
												<option value="${item.id}">${item.dybt}</option>
											</c:forEach>
									</select>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="am-u-sm-6">
	                        <div class="am-form-group">
	                            <label for="gfmc" class="am-u-sm-3 am-form-label"></label>
	                            <div class="am-u-sm-9">
	                               
	                            </div>
	                        </div>
	                    </div>
                    </div>
                    <div class="am-u-sm-12">
                    	<div class="am-u-sm-6">
	                        <div class="am-form-group">
	                            <label for="s_ztxq" class="am-u-sm-3 am-form-label">主题详情</label>
	                            <div class="am-u-sm-9">
	                                <textarea id="ztxq" name="ztxq" class="am-u-sm-12" style="height:400px"></textarea>									
	                            </div>
	                        </div>
	                    </div>
	                    <div class="am-u-sm-6">
	                        <div class="am-form-group">
	                            <label for="gfmc" class="am-u-sm-3 am-form-label"></label>
	                            <div class="am-u-sm-9">
	                                
	                            </div>
	                        </div>
	                    </div>
                    </div>	
					</div>
				</div>
			</div>
		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="shezhi">
			<div class="am-modal-dialog" style="height: 400px; width: 480px">
				<form class="js-form-yjsz am-form">
					<div class="am-tabs" data-am-tabs>
						<div class="am-tabs-nav am-nav am-nav-tabs">
							<label>订阅设置</label>
						</div>
						<div class="am-tabs-bd">
							<div class="am-tab-panel am-in am-active" id="tab1">
								<div class="am-modal-bd">
									<div class="am-g">
										<div class="am-u-sm-12">
											<table
												class="am-table am-table-bordered am-table-striped am-text-nowrap">
												<tr>
													<td><span style="color: red;">*</span>订阅标题</td>
													<td><select id="dybtidinput" name="dybtid" 
														class="am-form-field" required>
														<option value="">----请选择----</option>
														    <c:forEach items="${dybtList}" var="item">
												            <option value="${item.id}">${item.dybt}</option>
											            </c:forEach>
													    </select></td>
												</tr>
												<c:forEach items="${dyfsList}" var="item">
												<tr>																					
													<td colspan="2"><input type="checkbox" id="${item.dyfsdm}" name="dyfs">&nbsp;&nbsp;${item.dyfsmc}</td>
												</tr>
												</c:forEach>							
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
			<!-- <div
				class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn"
				tabindex="-1">
				<div class="am-modal-dialog">
					<div class="am-modal-hd">正在载入...</div>
					<div class="am-modal-bd">
						<span class="am-icon-spinner am-icon-spin"></span>
					</div>
				</div>
			</div> -->
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<%@ include file="../../pages/foot.jsp"%>

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/mydy.js"></script>
	<script type="text/javascript">
	    function getBz(){
	    	var id = $('#dybtid option:selected').val();
			$.ajax({
				url : "mydy/getBz",
				data : {
					"id" : id
				},
				success : function(data) {
					$("#ztxq").val(data.bz);
				}
			});
	    }
	</script>
</body>
</html>