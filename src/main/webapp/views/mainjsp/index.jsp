<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.css" />
<link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
<link rel="stylesheet" href="assets/css/app.css">
</head>
<body>
 <div class="am-g tpl-g">
		<div class="row-content am-cf">
			<div class="row">
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
					<div class="widget am-cf">
						<div class="widget-head am-cf"></div>
						<div class="widget-body  am-fr">
							<ul class="am-avg-sm-1 am-avg-md-6 am-margin am-text-center">
								<li><a href="./dbsx" class="am-text-success">
										<div style="font-size: 2.5rem; color: #3F51B5;">
											<span class="am-icon-clipboard am-icon-lg"></span><span
												style="font-size: 2.6rem; font-weight: 900; color: 	#FF0000;">&nbsp;${dbsl}</span><br>
											待办事项
										</div>
								</a></li>
								<li><a href="./fpkc" class="am-text-success">
										<div style="font-size: 2.5rem; color: #5677FC;">
											<span class="am-icon-list am-icon-lg"></span><br>
											发票库存
										</div>
								</a></li>
								<li><a href="./lrkpd" class="am-text-success">
										<div style="font-size: 2.5rem; color: #03A9F4;">
											<span class="am-icon-pencil-square am-icon-lg"></span><br>
											录入开票单
										</div>
								</a></li>
								<li><a href="./fpcx" class="am-text-success">
										<div style="font-size: 2.5rem; color: #00BCD4;">
											<span class="am-icon-search am-icon-lg"></span><br> 发票查询
										</div>
								</a></li>
								<li><a href="./fytjbb" class="am-text-success">
										<div style="font-size: 2.5rem; color: #009688;">
											<span class="am-icon-bar-chart am-icon-lg"></span><br>
											统计报表
										</div>
								</a></li>

								<li><a href="./fpgz" class="am-text-success">
										<div style="font-size: 2.5rem; color: #259B24">
											<span class="am-icon-archive am-icon-lg"></span><br>
											发票归档
										</div>
								</a></li>
							</ul>

							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
								<div class="widget am-cf">
									<div class="widget-body  widget-body-lg am-fr">
										<table style="width:100%"
											class="am-table am-table-compact tpl-table-black "
											id="example-r">
											<thead>
												<tr>
													<th>系统公告</th>
													<th></th>
													<th></th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<tr class="gradeX">
													<td><a href="">恭祝泰易发票2.0平台正式上线</a></td>
													<td></td>
													<td>2017-03-01</td>
												</tr>												
											</tbody>
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
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/theme.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script src="assets/js/amazeui.datatables.min.js"></script>
	<script src="assets/js/dataTables.responsive.min.js"></script>
	<script src="assets/js/app.js"></script>

</body>
</html>