<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>批量导入开票</title>
<meta name="description" content="批量导入开票">
<meta name="keywords" content="user">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
.am-table {
	margin-bottom: 0rem;
}

table thead th {
	text-align: center;
}
.data-ctr {
  text-align: center;
}
/*模态框*/
.mask {      
	height: 100%; 
    position: absolute; 
    top: 0px; 
    left: 0px;  
    filter: alpha(opacity=60); 
    background-color: #777;     
    z-index: 1002;      
    opacity:0.8; 
    -moz-opacity:0.8;     
} 
#loading,.loadingImg {
    z-index:1000001;
    position:absolute;
    left:50%;
    top:50%;
    transform:translate(-50%,-50%);
} 
#loading {
	width: 70%;
}
.loadingImg {
	top: -5%;
	width: 60px;
}   
</style>
</head>
<body>
	<input type="hidden" id="kplsh" value="0">
	<input type="hidden" id="kplsh1" value="0">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
						<div id="nrbq" class="am-tabs-bd">
							<div id="tabs1" class="am-tab-panel am-active">
								<div class="admin-content">
									<div class="am-cf widget-head">
										<div class="widget-title am-cf">
											<strong id="yjcd" class="am-text-primary am-text-lg">业务处理</strong>
											/ <strong id="ejcd">批量导入开票</strong>
											<button class="am-btn am-btn-success am-fr"
												data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
										</div>
										<!-- 侧边栏内容 -->
										<div id="doc-oc-demo3" class="am-offcanvas">
											<div class="am-offcanvas-bar am-offcanvas-bar-flip">
												<form id="ycform">
													<div class="am-offcanvas-content">
														<div class="am-form-group">
															<label for="s_ddh" class="am-u-sm-4 am-form-label">选择销方</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{btnSize: 'sm'}" id="s_xfsh"
																	name="xfsh">
																	<option id="xzxfq" value="">选择销方</option>
																	<c:forEach items="${xfList}" var="item">
																		<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
																	</c:forEach>
																</select>
															</div>
														</div>
													</div>
													
													<div class="am-offcanvas-content" style="margin-top: 5px;">
														<div class="am-form-group">
															<label for="s_jylsh" class="am-u-sm-4 am-form-label">批次号</label>
															<div class="am-u-sm-8">
																<input id="s_jylsh" type="text" placeholder="批次号">
															</div>
														</div>
													</div>

													<div class="am-offcanvas-content" style="margin-top: 8px;">
														<div class="am-form-group">
															<label for="s_jyrqq" class="am-u-sm-4 am-form-label">订单时间起</label>
															<div class="am-input-group am-datepicker-date am-u-sm-8"
																data-am-datepicker="{format: 'yyyy-mm-dd'}">
																<input type="text" id="s_jyrqq" class="am-form-field"
																	placeholder="订单时间起" readonly> <span
																	class="am-input-group-btn am-datepicker-add-on">
																	<button class="am-btn am-btn-default" type="button">
																		<span class="am-icon-calendar"></span>
																	</button>
																</span>
															</div>
														</div>
													</div>

													<div class="am-offcanvas-content" style="margin-top: 8px;">
														<div class="am-form-group">
															<label for="s_jyrqz" class="am-u-sm-4 am-form-label">订单时间止</label>
															<div class="am-input-group am-datepicker-date am-u-sm-8"
																data-am-datepicker="{format: 'yyyy-mm-dd'}">
																<input type="text" id="s_jyrqz" class="am-form-field"
																	placeholder="订单时间止" readonly> <span
																	class="am-input-group-btn am-datepicker-add-on">
																	<button class="am-btn am-btn-default" type="button">
																		<span class="am-icon-calendar"></span>
																	</button>
																</span>
															</div>

														</div>
													</div>
													<div style="padding: 32px;">
														<button type="button" id="kp_search1"
															class="am-btn am-btn-default am-btn-success data-back">
															<span></span> 查询
														</button>
													</div>
												</form>
											</div>
										</div>
									</div>

									<div class="am-g  am-padding-top">
										<form action="#"
											class="js-search-form  am-form am-form-horizontal">
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<div class="am-form-group">
													<div class="am-btn-toolbar">
														<div class="am-btn-group am-btn-group-xs">
															<button type="button" id="kp_dr"
																class="am-btn am-btn-primary am-btn-default">
																<span></span>批量导入
															</button>
															<!-- <button type="button" id="kpd_sc"
																class="am-btn am-btn-default am-btn-danger">
																<span></span> 删除
															</button> -->
														</div>
													</div>
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
												<div class="am-form-group tpl-table-list-select">
													<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
														<option value="jylsh">批次号</option>
													</select>
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
												<div
													class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
													<input id="dxcsz" type="text" class="am-form-field ">
													<span class="am-input-group-btn">
														<button id="kp_search"
															class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
															type="button"></button>
													</span>
												</div>
											</div>
										</form>
										<div class="am-u-sm-12 am-padding-top">
											<div>
												<table style="margin-bottom: 0px;"
													class="js-table am-table am-table-bordered am-table-striped am-table-hover am-text-nowrap "
													id="jyls_table">
													<thead>
														<tr>
															<th><input type="checkbox" id="check_all" /></th>
															<th>序号</th>
															<th>操作</th>
															<th>批次号</th>
															<th>导入文件名</th>
															<th>导入日期</th>
															<th>导入条数</th>
															<th>金额</th>
															<!-- <th>批量处理标志</th> -->
														    <th style='display:none'>id</th>  
															<th style='display:none'>销方id</th>	
														</tr>
													</thead>
												</table>
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
	<!-- <div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div id="alertt" class="am-modal-bd">Hello world！</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div> -->
	<div class="am-modal am-modal-no-btn" tabindex="-1"
		id="bulk-import-div">
		<div class="am-modal-dialog">
			<div class="am-modal-hd am-modal-footer-hd">
				批量导入
				<!--  <a href="javascript: void(0)" class="am-close am-close-spin"
								data-am-modal-close>&times;</a> -->
			</div>

			<div class="am-tab-panel am-fade am-in am-active">
				<form class="am-form am-form-horizontal" id="importExcelForm"
					method="post"
					action="<%=request.getContextPath()%>/lrkpd/importPldrjl"
					enctype="multipart/form-data">
					<div class="am-form-group">
						<div class="am-u-sm-12">
							<input type="file" class="am-u-sm-12" id="importFile"
								name="importFile" placeholder="选择要上传的文件"
								accept="application/vnd.ms-excel" required>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label">选择销方</label>
							<div class="am-u-sm-8">
								<select id="mb_xfsh" name="mb_xfsh" class="am-u-sm-12">
									<c:if test="${xfSum > 1}">
										<option value="">请选择</option>
										<c:forEach items="${xfList}" var="item">
											<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</c:if>
									<c:if test="${xfSum == 1}">
										<c:forEach items="${xfList}" var="item">
											<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label">选择开票点</label>
							<div class="am-u-sm-8">
								<select id="mb_skp" name="mb_skp" class="am-u-sm-12">
									<c:if test="${skpSum == 1 && xfSum == 1 }">
										<c:forEach items="${skps}" var="item">
											<option value="${item.id}">${item.kpdmc}</option>
										</c:forEach>
									</c:if>
									<c:if test="${skpSum > 1 || xfSum > 1}">
										<option value="">请选择</option>
										<c:forEach items="${skps}" var="item">
											<option value="${item.id}">${item.kpdmc}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12">
							<label class="am-u-sm-4 am-form-label">选择模板</label>
							<div class="am-u-sm-8">
								<select id="mb" name="mb" class="am-u-sm-12">
									<c:if test="${mbSum == 1 && xfSum == 1 }">
										<c:forEach items="${mbList}" var="item">
											<option value="${item.id}">${item.mbmc}</option>
										</c:forEach>
									</c:if>
									<c:if test="${mbSum > 1 || xfSum > 1}">
										<option value="">请选择</option>
										<c:forEach items="${mbList}" var="item">
											<option value="${item.id}">${item.mbmc}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="am-u-sm-12" style="margin-top: 30px;">
							<button type="button" id="btnImport"
								class="am-btn am-btn-xs am-btn-primary">导入</button>
							<button type="button" id="close1"
								class="am-btn am-btn-danger am-btn-xs">关闭</button>
						</div>
						<div class="am-u-sm-12" style="margin-top: 10px;">
							<a href="javascript:void(0)" id="btnDownloadDefaultTemplate"
								style="text-decoration: underline;">下载模板</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 遮罩层 -->
	<div id="mask" class="mask"></div>   
	<div id="loading"></div> 
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
	<script src="assets/js/pldrkp.js"></script>
	<!-- <script src="assets/js/kpdys.js"></script> -->
	<script src="assets/js/sweetalert.min.js"></script>
	<!-- <script src="assets/js/loading.js"></script> -->
	<script type="text/javascript">
		$(function() {
			var tabCounter = 0;
			var $tab = $('#doc-tab-demo-1');
			tabCounter++;
			$tab.tabs('refresh');
		});
	</script>
	<script>
	function refresh() {
		this.location = this.location;
	}
	$(function() {
        $("#btnDownloadDefaultTemplate").click(function () {
        	var mbid = $('#mb').val();
        	if(null==mbid||""==mbid||"-1"==mbid){
            	swal("请选择模板后下载");
        	}else{
    			$.ajax({
    				type : "POST",
    				url : "kpdsh/bmlj",
    				data : {"mbid":mbid},
    				success : function(data) {
						if(data.drmb.xzlj!=null&&data.drmb.xzlj!=""){
						  window.location.href='lrkpd/downloadDefaultImportTemplate?xzlj='+data.drmb.xzlj;
						}else{
							window.location.href='kpdsh/xzmb?mbid='+mbid;
						}
    				}
    			});
        	}
    });
        //导入excel
        $("#btnImport").click(function () {
            var filename = $("#importFile").val();
            var xfsh = $("#mb_xfsh").val();
            var mb = $("#mb").val();
            var skpid = $("#mb_skp").val();
            if (!xfsh) {
                swal("请选择要导入的销方");
                return;
            }
            if (skpid==-1) {
                swal("请选择要导入的开票点");
                return;
            }
            if (mb==-1) {
                swal("请选择要导入的模板或设置默认模板,如无模板请添加模板后再导入");
                return;
            }
            if (!filename) {
                swal("请选择要导入的文件");
                return;
            }
            var pos = filename.lastIndexOf(".");
            if (pos == -1) {
                swal("导入的文件必须是excel文件");
                return;
            }
            var extName = filename.substring(pos + 1);
            if ("xls" != extName && "xlsx" != extName) {
                swal("导入的文件必须是excel文件");
                return;
            }
            $("#btnImport").attr("disabled", true);
            $('.js-modal-loading').modal('open');
            showMask();
            var options = {
                success: function (res) {
                    if (res["success"]) {
                        $("#btnImport").attr("disabled", false);
                        $('.js-modal-loading').modal('close');
                        var count = res["count"];
                        $.ajax({
							url:"pldrkp/importPldrjl",
							async:false,
							data:{
							"jlts" : count
						}, success:function(data) {
							if (data) {
								var option;
								$('#select_skpid').html("");
								for (var i = 0; i < data.skps.length; i++) {
									option+= "<option value='"+data.skps[i].id+"'>"+data.skps[i].kpdmc+"</option>";
								}
								$('#select_skpid').append(option);
							}
						}});
						hideMask();
                        swal({
                            title: "导入成功，共导入" + count + "条数据",
                            showCancelButton: false,
                            closeOnConfirm: false,
                            confirmButtonText: "确 定",
                            confirmButtonColor: "#ec6c62"
                        }, function() {
                            window.location.reload();
                        });
                        if (res["yes"]) {
                			$('#mrmb').empty();
                			var txt = $('#mb').find("option:selected").text();
        					var option = $("<option>").text(txt).val(mbid);
                        	$('#mrmb').append(option);
						}
                    } else {
                        $("#btnImport").attr("disabled", false);
                        $('.js-modal-loading').modal('close');
                        swal(res["message"]);
                    }
                }
            };
            $("#importExcelForm").ajaxSubmit(options);
        });
        //导入选择销方模板
        $("#mb_xfsh").change(function () {
            var xfsh = $(this).val();
            $('#mb').empty();
            $('#mb_skp').empty();
            //$('#mrmb').empty();
            if (xfsh == null || xfsh == '' || xfsh == "") {
				return;
			}
            var url = "<%=request.getContextPath()%>/lrkpd/getSkpList";
            $.post(url, {xfsh: xfsh}, function (data) {
                if (data) {
                	var option = $("<option>").text('请选择').val(-1);
                	$('#mb_skp').append(option);
                    for (var i = 0; i < data.skps.length; i++) {
                    	option = $("<option>").text(data.skps[i].kpdmc).val(data.skps[i].id);
                    	$('#mb_skp').append(option);
					}
                }
            });
            url = "<%=request.getContextPath()%>/lrkpd/getTemplate";
            $.post(url, {xfsh: xfsh}, function (data) {
                if (data) {
                	var option = $("<option>").text('请选择').val(-1);
                	$('#mb').append(option);
                    for (var i = 0; i < data.mbs.length; i++) {
                    	option = $("<option>").text(data.mbs[i].mbmc).val(data.mbs[i].id);
                    	$('#mb').append(option);
					}
                }
            });
			});
	}); 
		//选择销方取得税控盘
		function getKpd() {
			var xfid = $('#lrselect_xfid option:selected').val();
			//alert(xfid);
			var skpid = $("#lrselect_skpid");
			$("#lrselect_skpid").empty();
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
		};
		
		//批量导入
        var $importModal = $("#bulk-import-div");
      
        $("#close1").click(function () {
            $importModal.modal("close");
        });
        // 显示模态框
		function showMask(){    
	        $("#mask").css("height",$(document).height());     
	        $("#mask").css("width",$(document).width());     
	        $("#mask").show();  
	        loading.innerHTML = "<img src='img/loading2.gif' class='loadingImg'>";   
	    }
	    // 隐藏模态框
	    function hideMask(){  
	        $("#mask").hide(); 
	        loading.innerHTML = '';    
	    } 
	</script>

</body>
</html>