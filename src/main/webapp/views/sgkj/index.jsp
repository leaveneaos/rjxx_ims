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
						<div class="admin-content">
							<div class="am-cf widget-head">
								<div class="widget-title am-cf">
									<strong id="sgkj" class="am-text-primary am-text-lg"></strong> / <strong id="sgkj"></strong>
								</div>
							</div>
							<div class="am-g  am-padding-top">
								<div class="am-u-sm-12" style="border:1px solid #ccc;border-bottom: 0;padding: 10px 0;">
									<form action="#" id="待定" class="js-search-form  am-form am-form-horizontal" style="margin-top: 3px;">
		                                <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
		                                	<span class="data-tz"><span class="data-must">*</span>合同 / 订单号</span>
		                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
		                                        <input type="text" id="searchtxt" class="am-form-field ">
		                                        <span class="am-input-group-btn" id="button1">
										            <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="button"></button>
										        </span>
		                                    </div>
		                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
		                                    <span class="data-tz"><span class="data-must">*</span>发票类型</span>
		                                    <div class="am-form-group tpl-table-list-select">
		                                        <select id="tip1" data-am-selected="{btnSize: 'sm'}">
									              <option value="1" selected="selected">选择发票类型</option>
									              <option value="4">电子发票</option>
									            </select>
		                                    </div>
		                                </div>
		                                <br/><br/>
		                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
		                                	<span class="data-tz"><span class="data-must">*</span>销方名称</span>
		                                    <div class="am-form-group tpl-table-list-select">
		                                        <select id="tip1" data-am-selected="{btnSize: 'sm'}">
									              <option value="1" selected="selected">选择销方</option>
									              <option value="2">后台返回</option>
									            </select>
		                                    </div>
		                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
		                                 	<span class="data-tz" style="margin-left: 90px;"><span class="data-must">*</span>开票点名称</span>
		                                    <div class="am-form-group tpl-table-list-select">
		                                        <select id="tip1" data-am-selected="{btnSize: 'sm'}">
									              <option value="1" selected="selected">选择开票点</option>
									              <option value="4">后台返回</option>
									            </select>
		                                    </div>
		                                </div>
		                             
									</form>
								</div>
								<div class="am-u-sm-12" style="border:1px solid #ccc;">

								  <div class="am-u-sm-1">购买方</div>

								  <div class="am-u-sm-5">
								  	<form id="待定">
									    <div class="am-offcanvas-content"">
											<div class="am-form-group">
												<label for="s_xfsh" class="am-u-sm-4 am-form-label"><span>*</span>购方名称</label>
												<div class="am-u-sm-8">
													<select data-am-selected="{btnSize: 'sm'}" id="s_xfsh" name="s_xfsh" style="display: none;">
														<option id="xzxfq" value="">选择销方</option>
														
														<option value="423">上海百旺测试3643(500102010003643)</option>
													
														<option value="430">升级版测试用户3697(1111111111111111)</option>
													</select>
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content"">
											<div class="am-form-group">
												<label for="s_fpdm" class="am-u-sm-4 am-form-label"><span>*</span>纳税人识别号</label>
												<div class="am-u-sm-8">
													<input id="s_fpdm" type="text" placeholder="请输入纳税人识别号">
												</div>
											</div>
										</div>
										<div class="am-offcanvas-content"">
											<div class="am-form-group">
												<label for="s_fphm" class="am-u-sm-4 am-form-label">地址</label>
												<div class="am-u-sm-8">
													<input id="s_fphm" type="text" placeholder="请输入地址">
												</div>
											</div>
										</div>
									
										<div class="am-offcanvas-content"">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4 am-form-label">电话</label>
												<div class="am-u-sm-8">
													<input id="s_gfmc" type="text" placeholder="请输入电话号码">
												</div>
											</div>
										</div>
									
										<div class="am-offcanvas-content"">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4 am-form-label">开户行</label>
												<div class="am-u-sm-8">
													<input id="s_gfmc" type="text" placeholder="请输入开户行">
												</div>
											</div>
										</div>
									
										<div class="am-offcanvas-content"">
											<div class="am-form-group">
												<label for="s_gfmc" class="am-u-sm-4 am-form-label">银行账号</label>
												<div class="am-u-sm-8">
													<input id="s_gfmc" type="text" placeholder="请输入银行账号">
												</div>
											</div>
										</div>
									</form>
								  </div>

								  <div class="am-u-sm-1" style="border-left:1px solid #ccc;">密码区</div>

								  <div class="am-u-sm-5"></div>
								</div>
							</div>
						</div>
						<div class="admin-content" style="border: 1px solid #ccc; margin-top: 10px;">
							<legend>商品明细列表</legend>
							<div class="am-form-group">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button type="button" id="gz_xzgz" data-am-modal="{target: '#doc-modal-4', closeViaDimmer: 0, width: 600}" class="am-btn am-btn-default am-btn-success">
											<span></span> 新增
										</button>
										<button type="button" id="gz_scgz" class="am-btn am-btn-default am-btn-danger">
											<span></span> 删除
										</button>
									</div>
								</div>
							</div>
							<div class="am-u-sm-12 am-padding-top">
								<div>
									<table id="tbl" style="margin: 0"
										class="js-table am-table am-table-bordered am-table-striped am-text-nowrap">
										<thead>
											<tr>
												<th><input type="checkbox" id="check_all" /></th>
												<th>货物或应税劳务、服务名称</th>
												<th>规格型号</th>
												<th>单位</th>
												<th>数量</th>
												<th>单价</th>
												<th>金额</th>
												<th>税率</th>
												<th>税额</th>
											</tr>
										</thead>
										<tbody>
										
										</tbody>
									</table>
								</div>
							</div>
							<div class="data-center" > 
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
									<span style="line-height: 37px;">价税合计</span>
									<button type="button" class="am-btn am-btn-primary" disabled="disabled" style="margin-right: 60px;">禁用状态</button>
			                    	<span style="line-height: 37px;">金额合计（不含税）</span>
									<button type="button" class="am-btn am-btn-primary" disabled="disabled" style="margin-right: 60px;">禁用状态</button>
									<span style="line-height: 37px;">税额合计</span>
									<button type="button" class="am-btn am-btn-primary" disabled="disabled">禁用状态</button>
								</div>
							</div>
					     	
							
							<div class="am-g doc-am-g" style="border: 1px solid #ccc;margin: 10px auto;">
							  <div class="am-u-sm-12 am-u-md-4 am-u-lg-2"">备 注 :</div>
							  <div class="am-u-sm-12 am-u-md-8 am-u-lg-10">
								<textarea class="" rows="3" id="doc-ta-1" style="width: 100%;"></textarea>
							  </div>
							</div>
							<div class="data-center" > 
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
			                    	<span style="line-height: 27px;">邮件地址</span>
									<input type="" name="">
									<span style="line-height: 27px;">联系电话</span>
									<input type="" name="">
									<span style="line-height: 27px;">提取码</span>
									<input type="" name="">
								</div>
							</div>
							<div class="data-center">
								<button id="kj" type="button" class="am-btn dtc am-btn-secondary">开 具</button>
								<button id="dy" type="button" class="am-btn dtc am-btn-secondary">打 印</button>
								<button id="cz" type="button" class="am-btn dtc am-btn-danger">重 置</button>
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
	<script src="assets/js/xfxxwh_app.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>

	<script>
		
	</script>

</body>
</html>
