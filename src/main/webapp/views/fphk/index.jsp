<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票换开</title>
<meta name="description" content="发票换开">
<meta name="keywords" content="user">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
<style type="text/css">
	.data-ctr {
	  text-align: center;
	}
</style>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
	<!-- sidebar start -->
	<!-- sidebar end -->
	<!-- content start -->
	<input type="hidden" id="djh" value="0">
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="admin-content">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>发票换开</strong>
								<button class="am-btn am-btn-success am-fr"
									data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
							</div>
							<!-- 侧边栏内容 -->
							<div id="doc-oc-demo3" class="am-offcanvas">
								<div class="am-offcanvas-bar am-offcanvas-bar-flip">
								<form  id ="ycform">
									<div class="am-offcanvas-content" style="margin-top: 5px;">
										<div class="am-form-group">
											<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
											<div class="am-u-sm-8">
												<input id="s_fpdm" type="text"
													placeholder="发票代码">
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content" style="margin-top: 5px;">
										<div class="am-form-group">
											<label for="s_fphm" class="am-u-sm-4 am-form-label">发票号码</label>
											<div class="am-u-sm-8">
												<input id="s_fphm" type="text"
													placeholder="发票号码">
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content" style="margin-top: 5px;">
										<div class="am-form-group">
											<label for="s_gfmc" class="am-u-sm-4 am-form-label">购方名称</label>
											<div class="am-u-sm-8">
												<input id="s_gfmc" type="text"
													placeholder="购方名称">
											</div>
										</div>
									</div>
									<div class="am-offcanvas-content" style="margin-top: 5px;">
										<div class="am-form-group">
											<label for="s_ddh" class="am-u-sm-4 am-form-label">订单号</label>
											<div class="am-u-sm-8">
												<input id="s_ddh" type="text" 
													placeholder="订单号">
											</div>
										</div>
									</div>

								
									<div class="am-offcanvas-content" style="margin-top: 8px;">
										<div class="am-form-group">
											<label for="s_ddh" class="am-u-sm-4 am-form-label">开票日期</label>
											<div class="am-input-group am-datepicker-date am-u-sm-8"
												data-am-datepicker="{format: 'yyyy-mm-dd'}">
												<input type="text" id="s_rqq" class="am-form-field"
													placeholder="开始时间" readonly> <span
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
											<label for="s_ddh" class="am-u-sm-4 am-form-label">开票日期</label>
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
									</div>
									<div style="padding: 32px;">
										<button type="button" id="hk_search1"
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
													<button type="button" id="huankai"
													class="am-btn am-btn-default am-btn-success">
													<span></span> 换开
												</button>
												
												<!-- <button type="button" id="huankaidy"
													class="am-btn am-btn-default am-btn-secondary">
													<span></span> 换开并打印
												</button> -->
											<!-- 	<button type="button" id="huankaipl"
													class="am-btn am-btn-default am-btn-success">
													<span></span> 批量换开
												</button> -->
												
											</div>
										</div>
									</div>
								</div> 
							
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
									<div class="am-form-group tpl-table-list-select">
										<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
											<option value="gfmc">购方名称</option>
											<option value="ddh">订单号</option>
										</select>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
									<div
										class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
										<input id="dxcsz" type="text" class="am-form-field "> <span
											class="am-input-group-btn">
											<button id="hk_search"
												class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
												type="button"></button>
										</span>
									</div>
								</div>
							</form>
							<div class="am-u-sm-12 am-padding-top">
								<div>
									<table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
										id="fphk_table">
										<thead>
											<tr>
												<th><input type="checkbox" id="check_all" /></th>
												<th>序号</th>
												<th>操作</th>
				                                <th>订单号</th>
				                                <th>开票日期</th>
				                                <th>购方名称</th>
				                                <th>发票代码</th>
				                                <th>发票号码</th>
				                                <th class="data-ctr">金额</th>
				                                <th class="data-ctr">税额</th>
				                                <th class="data-ctr">价税合计</th>
											</tr>
										</thead>
									</table>
							</div>
							</div>
						<!-- model -->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="fphkym">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">发票换开详情
               <!--  <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a> -->
            </div>
            <div class="am-tab-panel am-fade am-in am-active">
                <hr/>
                <form action="fphk/ykfphk" class="am-form am-form-horizontal" id="main_form">
										<fieldset>
											<div class="am-form-group" style="display: none">
											    <div style="display: none">
												    <label for="kplsh" class="am-u-sm-4 am-form-label">KPLSH</label>
					                                <div class="am-u-sm-8">
					                                    <input type="text" id="kplsh" name="kplsh" placeholder="" readonly/>
					                                </div>
				                                </div>
												<div style="display: none">
												    <label for="djh" class="am-u-sm-4 am-form-label">DJH</label>
					                                <div class="am-u-sm-8">
					                                    <input type="text" id="djh" name="djh" placeholder="" readonly/>
					                                    <input type="text" id="dybz" name="dybz" placeholder="" readonly/>
					                                </div>
												</div>
											
												</div>
											<div class="am-form-group">
												<label for="yfpdm" class="am-u-sm-2 am-form-label">原发票代码</label>

												<div class="am-u-sm-4">
													<input type="text" id="yfpdm" name="yfpdm"
														placeholder=""  readonly>
												</div>
												<label for="yfphm" class="am-u-sm-2 am-form-label">原发票号码</label>
												<div class="am-u-sm-4">
													<input type="text" id="yfphm" name="yfphm"
														placeholder="" readonly>
												</div>
											</div>
											<div class="am-form-group">
											    <label for="kpje" class="am-u-sm-2 am-form-label">价税合计</label>

												<div class="am-u-sm-4">
													<input type="text" id="kpje" name="kpje"
														placeholder="" readonly >
												</div>
												<label for="fpzl_edit" class="am-u-sm-2 am-form-label">发票种类</label>

												<div class="am-u-sm-4 am-u-end">
													<select id="fpzl_edit" name="fpzl_edit" onchange="hidespan()" required >
														<option value="">请选择换开发票类型</option>
														<option value="01">专用发票</option>
														<option value="02">普通发票</option>
													</select>
												</div>
											</div>
											<div class="am-form-group"  id="hide1" >
											
											    <label for="gfmc_edit" class="am-u-sm-2 am-form-label"><span
											        style="color: red; " id="span_gfmc">*</span>购方名称</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfmc_edit" name="gfmc_edit"
														placeholder="输入购方名称..."  required>
												</div>
											
											
												<label for="gfsh_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red; display: none" id="span_gfsh">*</span>购方税号</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfsh_edit" name="gfsh_edit" 
														placeholder="输入购方税号...">
												</div>
												
												
												
												
												
											</div>
											<div class="am-form-group" id="hide2" >
											
											    <label for="gfyh_edit" class="am-u-sm-2 am-form-label"><span
										         style="color: red; display: none" id="span_gfyh">*</span>购方银行</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfyh_edit" name="gfyh_edit"
														placeholder="输入购方银行...">
												</div>
											
											
												<label for="gfzh_edit" class="am-u-sm-2 am-form-label">
												<span
													style="color: red; display: none" id="span_gfyhzh">*</span>银行账号</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfzh_edit" name="gfzh_edit"
														placeholder="输入购方银行账号...">
												</div>
												
											</div>
											<div class="am-form-group">
												<label for="gfemail_edit" class="am-u-sm-2 am-form-label">购方邮件</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfemail_edit" name="gfemail_edit"
														placeholder="输入购方邮件地址...">
												</div>
												<label for="gfdh_edit" class="am-u-sm-2 am-form-label">购方电话</label>
												<div class="am-u-sm-4">
													<input type="text" id="gfdh_edit" name="gfdh_edit"
														placeholder="输入购方电话...">
												</div>
											</div>
											<div class="am-form-group">
												<label for="gfdz_edit" class="am-u-sm-2 am-form-label">购方地址</label>

												<div class="am-u-sm-4">
													<input type="text" id="gfdz_edit" name="gfdz_edit"
														placeholder="输入购方地址...">
												</div>
												
												<label for="ddh_edit" class="am-u-sm-2 am-form-label"><span
													style="color: red;">*</span>订单号</label>

												<div class="am-u-sm-4">
													<input type="text" id="ddh_edit" name="ddh_edit"
														placeholder="输入订单号..." required>
												</div>
												
												
											</div>
											<div class="am-form-group" style="display:none">

							                    <label for="tqm_edit" class="am-u-sm-2 am-form-label" style="display:none" >提取码</label>

												<div class="am-u-sm-4" style="display:none">
													<input type="text" id="tqm_edit" name="tqm_edit"
														placeholder="输入提取码...">
												</div>
												<label for="gfsjh_edit" class="am-u-sm-2 am-form-label" style="display:none" >手机号</label>
												<div class="am-u-sm-4" style="display:none">
													<input type="text" id="gfsjh_edit" name="gfsjh_edit"
														placeholder="输入购方手机号...">
												</div>
											</div>
											<div class="am-form-group">

												<label for="gfbz_edit" class="am-u-sm-2 am-form-label">备注</label>

												<div class="am-u-sm-10">
													<input type="text" id="gfbz_edit" name="gfbz_edit"
														placeholder="输入备注信息...">
												</div>
												
											</div>
											<div class="am-margin">
												 <button type="submit" id="js-submit"
															class="am-btn am-btn-default am-btn-success">
															<span></span> 确定
												</button>
												<button type="button" id="js-close"
															class="am-btn am-btn-default am-btn-danger">
															<span></span> 关闭
												</button>
											</div>
										</fieldset>
									</form>
            </div>
        </div>
    </div>
						</div>
					</div>
				</div>
				<!-- loading do not delete this -->
									<!-- content end -->
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

<script type="text/javascript">
function hidespan(){
	var fpzldm = $("#fpzl_edit").val();
	if(fpzldm=='01'){
	

		document.getElementById("span_gfsh").style.display=""; 
		document.getElementById("span_gfyh").style.display=""; 
		document.getElementById("span_gfyhzh").style.display="";
		//document.getElementById("span_gfmc").style.display=""; 
		//$("#gfmc").attr("required",true);
		$("#gfsh_edit").attr("required",true);
		$("#gfyh_edit").attr("required",true);
		$("#gfzh_edit").attr("required",true);
	 }else{
		document.getElementById("span_gfsh").style.display="none"; 
        document.getElementById("span_gfyh").style.display="none"; 
		document.getElementById("span_gfyhzh").style.display="none"; 
		//document.getElementById("span_gfmc").style.display="";
		
		$("#gfsh_edit").attr("required",false);
		$("#gfyh_edit").attr("required",false);
		$("#gfzh_edit").attr("required",false);
		//$("#gfmc").attr("required",true); 

	 }
	}

</script>
<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
  <div class="am-modal-dialog">
    <div id="alertt" class="am-modal-bd">
      Hello world！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>

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
    <script src="assets/js/fphk.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
</body>
</html>