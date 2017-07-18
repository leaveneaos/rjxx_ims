<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>手工开具</title>
<meta name="description" content="手工开具">
<meta name="keywords" content="手工开具">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />

<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
	.admin-content {
	  min-height: 0px;
	}
	.selected {
		background-color: lightgray;
	}
	.star {
		color: red;
	}
	.data-buy {
		padding-top: 35px;
	}
	.data-cte {
		text-align: center;
		margin-top: 5px;
	}
	.botm {
		text-align: center;
	}
	.botm button {
		margin: 10px 100px;
	}
	tbody td input {
		width: 90px;
	}
	tbody td #spmc {
		width: 200px;
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
						<!-- content start -->
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>手工开具</strong>
							</div>
						</div>

						<div class="admin-content" style="border: 1px solid #ccc; margin-top: 10px;">
							<div class="am-g">
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
									<form  id="mainform" class="am-form am-form-horizontal" style="margin-top: 3px;">
										<div class="am-form-group">
											<label for="xf" class="am-u-sm-2 am-form-label" style="padding-top: 4px;"><span
													class="star">*</span>销方名称</label>
											<div class="am-u-sm-3">
												<select id="xf" name="xf" onchange="getKpd();" required>
													<c:if test="${xfnum>1}">
														<option value="">选择销方</option>
														<c:forEach items="${xfList}" var="item">
															<option value="${item.id}">${item.xfmc}</option>
														</c:forEach>
													</c:if>
													<c:if test="${xfnum==1}">
														<c:forEach items="${xfList}" var="item">
															<option value="${item.id}">${item.xfmc}</option>
														</c:forEach>
													</c:if>
												</select>
											</div>
											<label for="kpd" class="am-u-sm-2 am-form-label" style="padding-top: 4px;"><span
													class="star">*</span>开票点名称</label>
											<div class="am-u-sm-3">
												<select id="kpd" name="kpd" required>
													<c:if test="${xfnum==1}">
														<c:forEach items="${skpList}" var="item">
															<option value="${item.skpid}">${item.kpdmc}</option>
														</c:forEach>
													</c:if>
												</select>
											</div>
										</div>
										<div class="am-form-group">
											<label for="fpzldm" class="am-u-sm-2 am-form-label" style="padding-top: 4px;"><span
													class="star">*</span>发票种类</label>
											<div class="am-u-sm-3 am-u-end">
												<select id="fpzldm" name="fpzldm"  required>
													<%--<option value="">选择开票类型</option>
													<option value="01">专用发票</option>
													<option value="02">普通发票</option>--%>
													<option value="12">电子发票</option>
												</select>
											</div>
											<label for="ddh" class="am-u-sm-2 am-form-label" style="padding-top: 4px;"><span
													class="star">*</span>订单号</label>

											<div class="am-u-sm-3">
<%--
												<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
--%>
													<input id="ddh" name="ddh" type="text" onkeyup="this.value=this.value.replace(/[^u4e00-u9fa5w]/g,'')" placeholder="输入订单号" required>
													<%--<span class="am-input-group-btn">
														<button class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search" id="searchddh" type="button"></button>
													</span>--%>
												<%--</div>--%>
											</div>


											<!-- <div class="am-u-sm-3">
												<input type="text" id="ddh" name="ddh"
													   placeholder="输入订单号" required />
											</div> -->
										</div>
									</form>
								</div>
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="border-top:1px solid #ccc;border-bottom:1px solid #ccc">
								  <div class="am-u-sm-1 am-u-md-1 am-u-lg-1 data-buy" style="text-align: center;">购买方</div>
								  <div class="am-u-sm-11 am-u-md-11 am-u-lg-11" style="border-left: 1px solid #ccc;">
								  	<form id="gfform" style="overflow: hidden;">
								  		<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
											<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" >
												<div class="am-u-sm-12 am-u-md-5 am-u-lg-5" style="float: right;">
													<label for="sfbx" class="am-form-label data-cte"><span class="star"></span>用于报销</label>
													<input id="sfbx" name="sfbx" type="checkbox"  onclick="sf();"   checked />
												</div>
											</div>
										    <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfmc" class="am-u-sm-5 am-form-label data-cte"><span class="star">*</span>购方名称</label>
												<div class="am-u-sm-7" >
													<input id="gfmc" name="gfmc" type="text" value="" placeholder="请输入购方名称">
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfsh" class="am-u-sm-6 am-form-label data-cte"><span class="star" id="show">*</span>购方税号</label>
												<div class="am-u-sm-6">
													<input id="gfsh" name="gfsh" type="text" placeholder="请在半角字符下输入" oninput="this.value=this.value.replace(/[^0-9A-Z]/g,'')">
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfdz" class="am-u-sm-5 am-form-label data-cte">地址</label>
												<div class="am-u-sm-7">
													<input id="gfdz" name="gfdz" type="text" placeholder="请输入地址">
												</div>
											</div>
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfdh" class="am-u-sm-6 am-form-label data-cte">电话</label>
												<div class="am-u-sm-6">
													<input id="gfdh" name="gfdh" type="text" placeholder="请输入电话号码">
												</div>
											</div>
										
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="gfyh" class="am-u-sm-5 am-form-label data-cte">开户行</label>
												<div class="am-u-sm-7">
													<input id="gfyh" name="gfyh" type="text" placeholder="请输入开户行">
												</div>
											</div>
										
											<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
												<label for="yhzh" class="am-u-sm-6 am-form-label data-cte">银行账号</label>
												<div class="am-u-sm-6">
													<input id="yhzh" name="yhzh" type="text" placeholder="请输入银行账号">
												</div>
											</div>
										</div>
									</form>
								  </div>
								</div>
							</div>
							<%--<div class="am-u-sm-12 am-u-md-12 am-u-lg-12 am-padding-top">
								<div>
									<table id="jyzfmx_table" style="margin: 0"
										   class="js-table am-table am-table-bordered  am-text-nowrap">
										<thead>
										<tr>
											<th>序号</th>
											<th>支付方式</th>
											<th>支付金额</th>
										</tr>
										</thead>
									</table>
								</div>
							</div>--%>
						</div>

						

						<div class="admin-content" style="border: 1px solid #ccc; margin-top: 10px;">
							<legend>商品明细列表</legend>
							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button type="button" id="add" data-am-modal="{target: '#doc-modal-4', closeViaDimmer: 0, width: 600}" class="am-btn am-btn-default am-btn-success">
											<span></span> 新增
										</button>
										<button type="button" id="del" class="am-btn am-btn-default am-btn-danger">
											<span></span> 删除
										</button>
									</div>
								</div>
							</div>
							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12 am-padding-top">
								<div>
									<table id="jyspmx_table" style="margin: 0"
										class="js-table am-table am-table-bordered  am-text-nowrap">
										<thead>
											<tr>
												<th>序号</th>
												<th>货物或应税劳务、服务名称</th>
												<th>规格型号</th>
												<th>单位</th>
												<th>数量</th>
												<th>单价(含税)</th>
												<th>金额(含税)</th>
												<th>税率</th>
												<th>税额</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="text-align: center;margin: 10px auto;">
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
									<span>价税合计:</span>

									<input id="jshj" name="jshj" type="text"  class="selected" readonly>
								</div>
								
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
									<span>金额合计(不含税):</span>

									<input id="hjje" name="hjje" type="text" class="selected" readonly>
								</div>
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
									<span>税额合计:</span>

									<input id="hjse" name="hjse" type="text" class="selected" readonly>
								</div>
							</div>

							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="border: 1px solid #ccc;margin: 10px auto;">
							  <div class="am-u-sm-1" style="margin-top: 30px;">备 注 :</div>
							  <div class="am-u-sm-11">
								<textarea id="bz" name="bz" rows="3"  style="width: 100%;"></textarea>
							  </div>
							</div>
							<!-- <div class="am-u-sm-12" >  -->
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-12" style="text-align: center;">
									<div class="am-u-sm-4 am-u-md-4 am-u-lg-4">
				                    	<span style="vertical-align: middle;">Email地址</span>
										<input type="text" name="yjdz" id="yjdz">
									</div>
									<div class="am-u-sm-4 am-u-md-4 am-u-lg-4">
										<span style="vertical-align: middle;">联系电话</span>
										<input type="text" name="lxdh" id="lxdh">
									</div>
									<div class="am-u-sm-4 am-u-md-4 am-u-lg-4">
										<span style="vertical-align: middle;">提取码</span>
										<input type="text" name="tqm" id="tqm">
									</div>
								</div>
							<!-- </div> -->
							<div class="am-u-sm-12 botm" >
								<button id="kj" type="button" class="am-btn am-btn-secondary">开 具</button>
								<button id="cz" type="button" class="am-btn am-btn-danger">重 置</button>
							</div>
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
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="spxx">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				商品信息详情
			</div>
			<div class="am-modal-bd">
				<hr />
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
					<div>
						<table style="margin-bottom: 0px;" class="js-table am-table am-table-bordered am-table-hover am-text-nowrap"
							   id="detail_table">
							<thead>
							<tr>
								<th style="text-align:center">商品编码</th>
								<th style="text-align:center">商品名称</th>
								<th style="text-align:center">规格型号</th>
								<th style="text-align:center">单位</th>
								<th style="text-align:center">单价</th>
								<th style="text-align:center">税率</th>
							</tr>
							</thead>
						</table>
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
	<script src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
    <script src="assets/js/sgkj.js"></script>
	<script>
        //选择销方取得税控盘
        function getKpd() {
            var xfid = $('#xf option:selected').val();
            var kpd = $("#kpd");
            $("#kpd").empty();
            $.ajax({
                url : "fpkc/getKpd",
                data : {
                    "xfid" : xfid
                },
                success : function(data) {
                    for (var i = 0; i < data.length; i++) {
                        var option = $("<option>").text(data[i].kpdmc).val(
                            data[i].skpid);
                        kpd.append(option);
                    }
                }
            });
        }
        function sf(){
            if ($("#sfbx").is(':checked')) {
               $("#show").html("*");
            }else{
                $("#show").html("");
			}
		}

		$('#gfsh').blur(function() {
			var gfsh = $('#gfsh').val();
			if(null != gfsh && gfsh != ''){
				if(gfsh.length != 15 && gfsh.length != 18 && gfsh.length !=20 ){
					swal("购方税号由(15,18或20位)数字或大写字母组成")
					return;
				}
			};
		})
	</script>

</body>
</html>
