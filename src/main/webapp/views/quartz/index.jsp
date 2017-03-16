<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>定时任务</title>
<meta name="description" content="定时任务">
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
								<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>定时任务</strong>

							</div>


						<div class="am-g  am-padding-top">
							<form action="#"
								class="js-search-form  am-form am-form-horizontal">
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
													<button type="button" id="add"
													class="am-btn am-btn-default am-btn-success">
													<span></span> 增加
												</button>
												<button type="button" id="delete"
														class="am-btn am-btn-default am-btn-danger">
													<span></span> 删除
												</button>
												<button type="button" id="edit"
													class="am-btn am-btn-default am-btn-secondary">
													<span></span> 修改
												</button>


												
											</div>
										</div>
									</div>
								</div> 
							


							</form>
							<div class="am-u-sm-12 am-padding-top">
								<div>
									<table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
										id="task_table">
										<thead>
											<tr id="th">
												<th><input type="checkbox" id="check_all" /></th>
												<th>JobName</th>
				                                <th>JobGroup</th>
				                                <th>JobDescription</th>
				                                <th>JobStatus</th>
				                                <th>CronExpression</th>
				                                <th>CreateTime</th>
				                                <th>操作</th>
											</tr>
										</thead>
									</table>
							</div>
							</div>
						<!-- model -->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="dsrw">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">增加定时任务
            </div>
            <div class="am-tab-panel am-fade am-in am-active">
                <hr/>
                <form action="" class="am-form am-form-horizontal" id="main_form">
										<fieldset>

											<div class="am-form-group">
												<label for="jobname" class="am-u-sm-2 am-form-label">JobName</label>

												<div class="am-u-sm-4">
													<input type="text" id="jobname" name="jobname"
														placeholder="任务名" required >
												</div>
												<label for="jobgroup" class="am-u-sm-2 am-form-label">JobGroup</label>
                        					<div class="am-u-sm-4 am-u-end">
													<select id="jobgroup" name="jobgroup"  required >
														<option value="">请选择任务组</option>
                                                        <option value="Job_group">JobGroup</option>
													</select>
												</div>
											</div>
											<div class="am-form-group">
											    <label for="jobdescription" class="am-u-sm-2 am-form-label">JobDescription</label>

												<div class="am-u-sm-4">
													<input type="text" id="jobdescription" name="jobdescription"
														placeholder="任务描述"  required>
												</div>
												<label for="cronexpression" class="am-u-sm-2 am-form-label">CronExpression</label>

												<div class="am-u-sm-4">
													<input type="text" id="cronexpression" name="cronexpression"
														   placeholder="Cron表达式..."  required>
												</div>


											</div>
											<%--<div class="am-form-group"  id="hide1" >




												<label for="createtime" class="am-u-sm-2 am-form-label">CreateTime</label>

												<div class="am-u-sm-4">
													<input type="text" id="createtime" name="createtime"
														placeholder="创建时间...">
												</div>
											</div>--%>
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
	<script src="assets/js/quartz.js"></script>


</body>
</html>