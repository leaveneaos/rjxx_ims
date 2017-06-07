<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>参数配置</title>
<meta name="description" content="参数配置">
<meta name="keywords" content="参数配置">
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
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
<script src="assets/js/loading.js"></script>
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">系统管理</strong> / <strong>参数配置</strong>
				</div>
			</div>
			<hr />
			<div class="am-g  am-padding-top">
				<form action="#" class="js-search-form  am-form am-form-horizontal">
					<div class="am-g">
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-4 am-form-label">参数名</label>
								<div class="am-u-sm-8">
									<select id="csid" name="csid">
										<option value="">请选择</option>
										<c:forEach items="${csbs}" var="item">
											<option value="${item.id}">${item.csmc}(${item.csm})</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
<!-- 						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-3 am-form-label">参数级别</label>
								<div class="am-u-sm-9">
									<select id="csjb" name="csjb">
										<option value="">请选择</option>
										<option value="1">公司层面</option>
										<option value="2">销方层面</option>
										<option value="3">开票点层面</option>
									</select>
								</div>
							</div>
						</div> -->
					</div>
					<hr />
					<div class="am-u-sm-12  am-padding  am-text-center">
						<button id="button1" type="button"
							class="js-search  am-btn am-btn-primary">查询</button>
						<button id="button2" type="button"
							class="js-search  am-btn am-btn-secondary">新增</button>
					</div>
	             </form>
					<div class="am-u-sm-12  am-padding-top">
						<div>

							<table id="tbl"
								class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
								<thead>
									<tr>
										<th>序号</th>
										<th>操作</th>
										<th>参数名</th>
										<th>中文名称</th>
										<th style="display: none;">公司代码</th>
										<th>销方名称</th>
										<th>销方税号</th>
										<th>开票点名称</th>
										<th>参数值</th>
										<th style="display: none;">id</th>
										<th style="display: none;">xfid</th>
										<th style="display: none;">skpid</th>
										
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
					</div>
			
			</div>
		</div>
		<!-- content end -->

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
			<div class="am-modal-dialog">
				<form id="fomm" class="js-form-0 am-form am-form-horizontal">
					<div class="am-tabs" data-am-tabs>
						<div class="am-tab-panel am-fade am-in am-active" id="tab1">
							<div class="am-modal-hd">
								参数信息 <a href="javascript: void(0)"
									class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>
							<div class="am-modal-bd" style="overflow: auto;">
								<hr />
								<div class="am-g">
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<label for="s_fpdm" class="am-u-sm-4 am-form-label">请选择参数</label>
											<div class="am-u-sm-8">
												<select id="xzxzcs" name="xzxzcs" onchange="xzcs(this)">

												</select>
											</div>
										</div>
										<div class="am-form-group" id="xzxzcsz">
											<label for="hc_kpje" id="srcsz"
												class="am-u-sm-4 am-form-label">输入参数值</label>
											<div class="am-u-sm-8">
												<input type="text" id="xzxzcsz1" name="xzxzcsz"
													placeholder="请输入参数值" class="am-form-field" maxlength="50" />
											</div>
										</div>
										<div class="am-form-group" id="xzxzcsz2">
											<label for="hc_kpje" id="srcsz1"
												class="am-u-sm-4 am-form-label">请选择参数值</label>
											<div class="am-u-sm-8">
												<select id="xzxzcsz3" name="xzxzcsz1">
													<option value="">请选择</option>
													<option value="是">是</option>
													<option value="否">否</option>
												</select>
											</div>
										</div>
										<div class="am-form-group" hidden="true">
											<label for="hc_yfphm" class="am-u-sm-4 am-form-label">公司代码</label>
											<div class="am-u-sm-8">
												<input type="text" id="xzgddm" name="xzgsdm"
													value="${login_session_key.gsdm}" class="am-form-field"
													required readonly="readonly" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="s_fpdm" class="am-u-sm-4 am-form-label">请选择销方</label>
											<div class="am-u-sm-8">
												<select id="xzxzxf" name="xzxzxf" onchange="xzskp(this)">
													<option value="">请选择销方</option>
													<c:forEach items="${xfs}" var="item">
														<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="am-form-group">
											<label for="hc_yfpdm" class="am-u-sm-4 am-form-label">请选择开票点</label>
											<div class="am-u-sm-8">
												<select id="xzxzkpd" name="xzxzkpd">
													<option value="">请选择税控盘</option>
												</select>
											</div>
										</div>
									</div>
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="submit"
													class="js-submit  am-btn am-btn-primary">确定</button>
												<button type="button" class="js-close  am-btn am-btn-danger">取消</button>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>

					</div>
				</form>
			</div>


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
		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchonga">
			<div class="am-modal-dialog">
				<form id="fomma" class="js-form-1 am-form am-form-horizontal">
					<div class="am-tabs" data-am-tabs>
						<div class="am-tab-panel am-fade am-in am-active" id="tab1a">
							<div class="am-modal-hd">
								参数信息 <a href="javascript: void(0)"
									class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>
							<div class="am-modal-bd" style="overflow: auto;">
								<hr />
								<div class="am-g">
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<label for="s_fpdm" class="am-u-sm-4 am-form-label">参数</label>
											<input type="text" id="cszid" name="cszid"
													style="display: none;" class="am-form-field" maxlength="50" />
											<div class="am-u-sm-8">
												<input type="text" id="xgxzcs" name="xgxzcs"
													readonly="readonly" class="am-form-field" maxlength="50" />
											</div>
										</div>
										<div class="am-form-group" id="xgxzcsz">
											<label for="hc_kpje" id="srcsz"
												class="am-u-sm-4 am-form-label">输入参数值</label>
											<div class="am-u-sm-8">
												<input type="text" id="xgxzcsz1" name="xgxzcsz"
													placeholder="请输入参数值" class="am-form-field" maxlength="50" />
											</div>
										</div>
										<div class="am-form-group" id="xgxzcsz2">
											<label for="hc_kpje" id="srcsz1a"
												class="am-u-sm-4 am-form-label">请选择参数值</label>
											<div class="am-u-sm-8">
												<select id="xgxzcsz3" name="xgxzcsz1">
													<option value="">请选择</option>
													<option value="是">是</option>
													<option value="否">否</option>
												</select>
											</div>
										</div>
										<div class="am-form-group" hidden="true">
											<label for="hc_yfphm" class="am-u-sm-4 am-form-label">公司代码</label>
											<div class="am-u-sm-8">
												<input type="text" id="xggsdm" name="xggsdm"
													value="${login_session_key.gsdm}" class="am-form-field"
													required/>
											</div>
										</div>
										<div class="am-form-group">
											<label for="s_fpdm" class="am-u-sm-4 am-form-label">请选择销方</label>
											<div class="am-u-sm-8">
												<select id="xgxzxf" name="xgxzxf" onchange="xgskp1(this)">
													<option value="">请选择销方</option>
													<c:forEach items="${xfs}" var="item">
														<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="am-form-group">
											<label for="hc_yfpdm" class="am-u-sm-4 am-form-label">请选择开票点</label>
											<div class="am-u-sm-8">
												<select id="xgxzkpd" name="xgxzkpd">
														<option value="">请选择税控盘</option>
												</select>
											</div>
										</div>
									</div>
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="submit"
													class="js-submit1  am-btn am-btn-primary">确定</button>
												<button type="button"
													class="js-close1  am-btn am-btn-danger">取消</button>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>

					</div>
				</form>
			</div>


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
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
  <div class="am-modal-dialog">
    <div id="conft" class="am-modal-bd">
      你，确定要删除这条记录吗？
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
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
	<script src="assets/js/jquery-ui.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>

	<script src="assets/js/csb.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#xzxzcsz').hide();
		});
		function xzcs(obj) {
			$.ajax({
				url : 'csb/hqcszlx',
				data : {
					csid : obj.value
				},
				type : 'post',
				success : function(data) {
					if (null == data.csb) {

					} else {
						if ("1" == data.csb.cszlx) {
							$('#xzxzcsz').hide();
							$('#xzxzcsz2').show();
						} else if ("2" == data.csb.cszlx) {
							$('#xzxzcsz2').hide();
							$('#xzxzcsz').show();
						}
					}
				}
			})
		}
		function xzskp(obj){
			$.ajax({
				url : 'csb/xzskp',
				data : {
					xfid : obj.value
				},
				type : 'post',
				success : function(data) {
					$('#xzxzkpd').html("");
					trs="<option value=''>请选择</option>";
					$.each(data.skp, function(n, value) {
						trs +="<option value= '"+value.id+"'>"+value.kpdmc+"("+value.kpddm+")</option>"
					});
					$('#xzxzkpd').append(trs);
				}
			})
		}
		function xgskp1(obj){
			$.ajax({
				url : 'csb/xzskp',
				data : {
					xfid : obj.value
				},
				type : 'post',
				success : function(data) {
					$('#xgxzkpd').html("");
					trs="<option value=''>请选择</option>";
					$.each(data.skp, function(n, value) {
						trs +="<option value= '"+value.id+"'>"+value.kpdmc+"("+value.kpddm+")</option>"
					});
					$('#xgxzkpd').append(trs);
				}
			})
		}
	</script>
</body>
</html>
