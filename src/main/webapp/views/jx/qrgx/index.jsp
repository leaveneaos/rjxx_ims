<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>确认勾选</title>
<meta name="description" content="确认勾选">
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
<link rel="stylesheet" href="assets/css/autocomplete.css" />
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
</style>
</head>
<body>
	<input type="hidden" id="kplsh" value="0">
	<input type="hidden" id="kplsh1" value="0">
	<input type="hidden" id="bj" value="2">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="am-tabs" data-am-tabs="{noSwipe: 1}"
						id="doc-tab-demo-1">

						<div id="nrbq" class="am-tabs-bd">
							<div id="tabs1" class="am-tab-panel am-active">
								<div class="admin-content">
									<div class="am-cf widget-head">
										<div class="widget-title am-cf">
											<strong id="yjcd" class="am-text-primary am-text-lg">发票查验管理</strong>
											/ <strong id="ejcd">确认勾选</strong>
											<button class="am-btn am-btn-success am-fr"
													data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
										</div>
										<!-- 侧边栏内容 -->
										<div id="doc-oc-demo3" class="am-offcanvas">
											<div class="am-offcanvas-bar am-offcanvas-bar-flip">
												<form id="ycform">
													<div class="am-offcanvas-content">
														<div class="am-form-group">
															<label for="s_gfsh" class="am-u-sm-4 am-form-label">选择购方</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{btnSize: 'sm'}" id="s_gfsh"
																	name="s_gfsh">
																	<option id="xzxfq" value="">选择购方</option>
																	<c:forEach items="${xfList}" var="item">
																		<option value="${item.xfsh}">${item.xfmc}(${item.xfsh})</option>
																	</c:forEach>
																</select>
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 5px;">
														<div class="am-form-group">
															<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
															<div class="am-u-sm-8">
																<input id="s_fpdm" type="text" placeholder="发票代码">
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 5px;">
														<div class="am-form-group">
															<label for="s_fphm" class="am-u-sm-4 am-form-label">发票号码</label>
															<div class="am-u-sm-8">
																<input id="s_fphm" type="text" placeholder="发票号码">
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 8px;">
														<div class="am-form-group">
															<label for="s_fplx" class="am-u-sm-4 am-form-label">发票类型</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{btnSize: 'sm'}" id="s_fplx"
																	name="s_fplx">
																	<option id="xzlxq" value="">选择类型</option>
																	<option value="01">增值税专用发票</option>
																	<option value="02">增值税普通发票</option>
																</select>
															</div>
														</div>
													</div>
													<div class="am-offcanvas-content" style="margin-top: 8px;">
														<div class="am-form-group">
															<label for="s_rqq" class="am-u-sm-4 am-form-label">开票日期</label>
															<div class="am-input-group am-datepicker-date am-u-sm-8"
																data-am-datepicker="{format: 'yyyy-mm-dd'}">
																<input type="text" id="s_rqq" class="am-form-field"
																	placeholder="开票日期" readonly> <span
																	class="am-input-group-btn am-datepicker-add-on">
																	<button class="am-btn am-btn-default" type="button">
																		<span class="am-icon-calendar"></span>
																	</button>
																</span>
															</div>
														</div>
													</div>

													<%--<div class="am-offcanvas-content" style="margin-top: 8px;">
														<div class="am-form-group">
															<label for="s_ddh" class="am-u-sm-4 am-form-label">截止时间</label>
															<div class="am-input-group am-datepicker-date am-u-sm-8"
																data-am-datepicker="{format: 'yyyy-mm-dd'}">
																<input type="text" id="s_rqz" class="am-form-field"
																	placeholder="截止时间" readonly> <span
																	class="am-input-group-btn am-datepicker-add-on">
																	<button class="am-btn am-btn-default" type="button">
																		<span class="am-icon-calendar"></span>
																	</button>
																</span>
															</div>

														</div>
													</div>--%>
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
															<button type="button" id="gxrz_qr"
																class="am-btn am-btn-default am-btn-primary">
																<span></span>勾选认证
															</button>
															<button type="button" id="gxrz_qx"
																class="am-btn am-btn-default am-btn-danger">
																<span></span>取消勾选
															</button>
															<%--<button type="button" id="kpd_sc"
																class="am-btn am-btn-default am-btn-danger">
																<span></span>删除
															</button>
															<button type="button" id="kpd_kp"
																	class="am-btn am-btn-default am-btn-secondary">
																<span></span> 导出
															</button>--%>
														</div>
													</div>
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
												<div class="am-form-group tpl-table-list-select">
													<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
														<option value="gfsh">购方税号</option>
														<option value="fpdm">发票代码</option>
														<option value="fphm">发票号码</option>
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
															<th>企业名称</th>
															<th>发票代码</th>
															<th>发票号码</th>
															<th>开票日期</th>
															<th>销方名称</th>
															<th>不含税金额</th>
															<th>税额</th>
															<th>价税合计</th>
															<th>发票标签</th>
															<th>勾选时间</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>

									</div>
								</div>
							</div>

	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div id="conft" class="am-modal-bd">你，确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div id="alertt" class="am-modal-bd">Hello world！</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>



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
	<script src="assets/js/qrgx.js"></script>
	<script src="assets/js/getGfxxInput.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#cljg").hide();
			$("#cljgbt").hide();
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

    function getCurrentMonthFirst(){
        var date=new Date();
        date.setDate(1);
        return date;
    }

    function formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }

    $(function() {
        var startDate = getCurrentMonthFirst();
        var endDate = new Date();
        //var $alert = $('#my-alert');
        //$("#sglr_kprq").val(formatDate(startDate));
        //$("#w_kprqz").val(formatDate(endDate));
        $("#s_rqq").val(formatDate(startDate));
        //$("#s_rqz").val(formatDate(endDate));
    });

	$(function() {
		$("#select_xfid").change(
				function() {
		            $('#select_skpid').empty();
				    var xfsh = $(this).val();
				    if (xfsh == null || xfsh == '' || xfsh == "") {
						return;
					}
				    var url = "<%=request.getContextPath()%>/kp/getSkpList";
								$
										.post(
												url,
												{
													xfsh : xfsh
												},
												function(data) {
													if (data) {
														var option = $(
																"<option>")
																.text('请选择')
																.val(-1);
														$('#select_skpid')
																.append(option);
														for (var i = 0; i < data.skps.length; i++) {
															option = $(
																	"<option>")
																	.text(
																			data.skps[i].kpdmc)
																	.val(
																			data.skps[i].id);
															$('#select_skpid')
																	.append(
																			option);
														}
													}
												});
							});
		
		 //录入订单时选择商品
        $("#lrselect_sp").change(function () {
            var je = $('#lrje_edit');
            var sl = $('#lrsltaxrate_edit');
            var se = $('#lrse_edit');
            var hsje = $('#lrhsje_edit');
            var jshj = $('#lrjshj_edit');
            var dj = $('#lrdj_edit');
            var sps = $('#lrsl_edit');
            var spsl;
            var spid = $(this).val();
            var spmc = $("#lrselect_sp option:checked").text();
            var pos = spmc.indexOf("(");
            spmc = spmc.substring(0, pos);
            if (!spid) {
                $("#lrmx_form input").val("");
                return;
            }
            var ur = "<%=request.getContextPath()%>/lrkpd/getSpxq";
            $.ajax({
                url: ur,
                type: "post",
                async:false,
                data: {
                    spid: spid

                }, 
                success: function (res) {
                	 if (res) {
                         $("#lrmx_form #lrspdm_edit").val(res["spbm"]);
                         $("#lrmx_form #lrmc_edit").val(res["spmc"]);
                         $("#lrmx_form #lrggxh_edit").val(res["spggxh"] == null ? "" : res["spggxh"]);
                         $("#lrmx_form #lrdw_edit").val(res["spdw"] == null ? "" : res["spdw"]);
                         $("#lrmx_form #lrdj_edit").val(res["spdj"] == null ? "" : res["spdj"]);
                         $("#lrmx_form #lrsltaxrate_edit").val(res["sl"]);
                         spsl = res["sl"];
                     }
                }
            })
            if(null!=je && je.val() !=""){
            	var temp = (100+sl.val()*100)/100;
				se.val(FormatFloat(je.val() * spsl, "#####0.00"));
				var je1 = parseFloat(je.val());
        		var se1 = parseFloat(se.val());
				hsje.val(FormatFloat(je1 + se1, "#####0.00"));
				jshj.val(FormatFloat(je1 + se1, "#####0.00"));
        		if (dj != null && dj.val() != "") {
        			sps.val(FormatFloat(je.val() / dj.val(), "#####0.00"));
				}else if(sps != null && sps.val() != ""){
					dj.val(FormatFloat(je.val() / sps.val(), "#####0.00"));
				}
            }
        });
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
            if (skpid==-1 || skpid =='-1') {
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
            var options = {
                success: function (res) {
                    if (res["success"]) {
                        $("#btnImport").attr("disabled", false);
                        $('.js-modal-loading').modal('close');
                        var count = res["count"];
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
		    $("#lrsl_edit").keyup(function(){
                var spsl = $('#lrsl_edit');//商品数量
                // var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
                // if (!num.test(spsl.val())) {
                //     if (spsl.val().length > 1) {
                //         $('#lrsl_edit').val(
                //             spsl.val().substring(0,
                //                 spsl.val().length - 1))
                //     } else {
                //         $('#lrsl_edit').val("")
                //     }
                //     return;
                // }
                var sl = $('#lrsltaxrate_edit');
                var se = $('#lrse_edit');
                var hsje = $('#lrhsje_edit');
                var jshj = $('#lrjshj_edit');
                var dj = $('#lrdj_edit');
                var je = $('#lrje_edit');
                var temp = (100 + sl.val() * 100) / 100;
                if(dj!=""){
                    jshj.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));
                    hsje.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));
                    var jj=spsl.val() * dj.val();
                    je.val(FormatFloat(jj/temp, "#####0.00"));
                    se.val(FormatFloat(je.val() * sl.val(),
                        "#####0.00"));
                }
            });
            $("#lrdj_edit").keyup(function(){
                var dj = $('#lrdj_edit');//单价
                // var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
                // if (!num.test(dj.val())) {
                //     if (dj.val().length > 1) {
                //         $('#dj_edit').val(
                //             dj.val().substring(0,
                //                 dj.val().length - 1))
                //     } else {
                //         $('#dj_edit').val("")
                //     }
                //     return;
                // }



                var sl = $('#lrsltaxrate_edit');
                var se = $('#lrse_edit');
                var hsje = $('#lrhsje_edit');
                var jshj = $('#lrjshj_edit');
                var spsl = $('#lrsl_edit');
                var je = $('#lrje_edit');
                var temp = (100 + sl.val() * 100) / 100;
                if(spsl!=""){
                    jshj.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));
                    hsje.val(FormatFloat(spsl.val() * dj.val(), "#####0.00"));

                    var jj=spsl.val() * dj.val();
                    je.val(FormatFloat(jj/temp, "#####0.00"));
                    se.val(FormatFloat(je.val() * sl.val(),
                        "#####0.00"));
                }
            });

			 $("#lrje_edit").keyup(
			 	function() {
			 		var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			 		var je = $('#lrje_edit');
			 		if (!num.test(je.val())) {
			 			if (je.val().length > 1) {
			 				$('#lrje_edit').val(
			 						je.val().substring(0,
			 								je.val().length - 1))
			 			} else {
			 				$('#lrje_edit').val("")
			 			}
			 			return;
			 		}
			 		var sl = $('#lrsltaxrate_edit');
			 		var se = $('#lrse_edit');
			 		var hsje = $('#lrhsje_edit');
			 		var jshj = $('#lrjshj_edit');
			 		var dj = $('#lrdj_edit');
			 		var sps = $('#lrsl_edit');
			 		var temp = (100 + sl.val() * 100) / 100;
			 		se.val(FormatFloat(je.val() * sl.val(),
			 				"#####0.00"));
			 		var je1 = parseFloat(je.val());
			 		var se1 = parseFloat(se.val());
			 		hsje.val(FormatFloat(je1 + se1, "#####0.00"));
			 		jshj.val(FormatFloat(je1 + se1, "#####0.00"));
			 		if (dj != null && dj.val() != "") {
			 			sps.val(FormatFloat(je.val() / dj.val(),
			 					"#####0.00"));
			 		} else if (sps != null && sps.val() != "") {
			 			dj.val(FormatFloat(je.val() / sps.val(),
			 					"#####0.00"));
			 		}
			 	}
			 );



			$("#lrhsje_edit").keyup(
				function() {
					// var num = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
					var hsje = $('#lrhsje_edit');
					// if (!num.test(hsje.val())) {
					// 	if (hsje.val().length > 1) {
					// 		$('#lrhsje_edit').val(
					// 				hsje.val().substring(0,
					// 						hsje.val().length - 1))
					// 	} else {
					// 		$('#lrhsje_edit').val("")
					// 	}
					// 	return;
					// }
					var je = $('#lrje_edit');
					var sl = $('#lrsltaxrate_edit');
					var se = $('#lrse_edit');
					var spsl = $('#lrsl_edit');
					var jshj = $('#lrjshj_edit');
					var dj = $('#lrdj_edit');
					var sps = $('#lrsl_edit');
					var temp = (100 + sl.val() * 100) / 100;
					je.val(FormatFloat(hsje.val() / (temp),"#####0.00"));

					se.val(FormatFloat(hsje.val() - je.val(),"#####0.00"));

					jshj.val(FormatFloat(hsje.val(), "#####0.00"));

					// spsl.val(FormatFloat(hsje.val() / dj.val(),"#####0.00"))
					if (dj.val()!= "") {
			            spsl.val(FormatFloat(hsje.val() / dj.val(), "#.00#############"));
			        }else if(dj.val()==""&&spsl.val()!=""){
			            dj.val(FormatFloat(hsje.val() / spsl.val(), "#.00#############"));
			        }
				}
			);

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
		function CheckAll(){
            if ($("#sglr_zp").prop("checked")) {
                $("#sglr_jym").hide();
                $("#jym_lable").hide();
				$("#sglr_je").show();
				$("#je_lable").show();
            } else if($("#sglr_pp").prop("checked")) {
                $("#sglr_jym").show();
                $("#jym_lable").show();
                $("#sglr_je").hide();
                $("#je_lable").hide();
            }
		}
	</script>

</body>
</html>