<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票提取查询</title>
<meta name="description" content="发票提取查询">
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
								<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>发票提取查询</strong>
								<button class="am-btn am-btn-success am-fr"
									data-am-offcanvas="{target: '#doc-oc-demo3'}">更多查询</button>
							</div>
							<!-- 侧边栏内容 -->
							<div id="doc-oc-demo3" class="am-offcanvas">
								<div class="am-offcanvas-bar am-offcanvas-bar-flip">
								<form  id ="ycform">
								     
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
											<label for="s_rqq" class="am-u-sm-4 am-form-label">提取日期</label>
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
											<label for="s_rqz" class="am-u-sm-4 am-form-label">提取日期</label>
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
									<div class="am-offcanvas-content">
														<div class="am-form-group">
															<label for="s_tqsb" class="am-u-sm-4 am-form-label">提取设备</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{btnSize: 'sm'}" id="s_tqsb"
																	name="tqsb">
																	 <option value="">请选择</option>
																     <option value="1">PC</option>
																     <option value="2">手机</option>
																</select>
															</div>
														</div>
									</div>
									<div class="am-offcanvas-content">
														<div class="am-form-group">
															<label for="s_jlly" class="am-u-sm-4 am-form-label">记录来源</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{btnSize: 'sm'}" id="s_jlly"
																	name="jlly">
																	 <option value="">请选择</option>
																      <option value="1">提取</option>
																      <option value="2">发邮件</option>
																      <option value="3">保存发票夹</option>
																</select>
															</div>
														</div>
									</div>
									<div style="padding: 32px;">
										<button type="button" id="kplscx_search1"
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
													<!-- <button type="button" id="huankai"
													class="am-btn am-btn-default am-btn-success">
													<span></span> 换开
												</button> -->
												
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
											<button id="kplscx_search"
												class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
												type="button"></button>
										</span>
									</div>
								</div>
							</form>
							<div class="am-u-sm-12 am-padding-top">
								<div>
									<table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
										id="fptq_table">
										<thead>
											<tr>
												<th>序号</th>
												<th>订单号</th>
												<th>购方名称</th>
												<th class="data-ctr">价税合计</th>
												<th>提取设备</th>
												<th>记录来源</th>
												<th>提取时间</th>
											</tr>
										</thead>
									</table>
							</div>
							</div>
						<!-- model -->
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
	<script src="assets/js/format.js"></script>
	<script src="assets/js/fptqcx.js"></script>
	
</body>
</html>