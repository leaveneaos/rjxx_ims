<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>购方管理</title>
<meta name="description" content="购方管理">
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
		    <input type="hidden" id="gfid" value="0">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="admin-content">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>购方管理</strong>
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
											<label for="s_nsrsbh" class="am-u-sm-4 am-form-label">纳税人识别号</label>
											<div class="am-u-sm-8">
												<input id="s_nsrsbh" type="text"
													placeholder="纳税人识别号">
											</div>
										</div>
									</div>
									<div style="padding: 32px;">
										<button type="button" id="search1"
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
												<button type="button" id="gf_add" 
												    class="am-btn am-btn-default am-btn-success">
													录入
												</button>
												<button type="button" id="gf_del"
													class="am-btn am-btn-default am-btn-danger">
													删除
												</button>
												<button type="button" id="gf_xg"
													class="am-btn am-btn-default am-btn-warning">
													修改
												</button>
												
											</div>
										</div>
									</div>
								</div> 
							
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
									<div class="am-form-group tpl-table-list-select">
										<select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
											<option value="gfmc">购方名称</option>
											<option value="nsrsbh">纳税人识别号</option>
										</select>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
									<div
										class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
										<input id="dxcsz" type="text" class="am-form-field "> <span
											class="am-input-group-btn">
											<button id="search"
												class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
												type="button"></button>
										</span>
									</div>
								</div>
							</form>
							<div class="am-u-sm-12 am-padding-top">
								<div>
									<table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
										id="gfxx_table">
										<thead>
											<tr>
												<th><input type="checkbox" id="check_all" /></th>
												<th>购方企业名称</th>
												<th>纳税人识别号</th>
												<th>注册地址</th>
												<th>注册电话</th>
												<th>开户银行</th>
												<th>银行账号</th>
												<th>联系人</th>
												<th>联系电话</th>
												<th>邮寄地址</th>
												<th>Email</th>
											</tr>
										</thead>
									</table>
							</div>
							</div>
						<!-- model -->
                           <!--xiugai model -->
				<div class="am-modal am-modal-no-btn" tabindex="-1" id="xiugai">
					<div class="am-modal-dialog">
						<div class="am-modal-hd">
							购方信息修改 <!-- <a href="javascript: void(0)"
								class="am-close am-close-spin" data-am-modal-close>&times;</a> -->
						</div>
						<div class="am-modal-bd">
							<hr />
							<form action="#" class="js-form-0  am-form am-form-horizontal">
								<div class="am-g">
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<label for="xg_gfmc" class="am-u-sm-4 am-form-label"><span
												style="color: red;">*</span>企业名称</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_gfmc" name="xg_gfmc" placeholder=""  required/>
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_gfsh" class="am-u-sm-4 am-form-label">纳税人识别号</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_gfsh" name="xg_gfsh" class ="js-pattern-Taxid"
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_gfdz" class="am-u-sm-4 am-form-label">地址</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_gfdz" name="xg_gfdz" placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_gfdh" class="am-u-sm-4 am-form-label ">电话</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_gfdh" name="xg_gfdh" placeholder="" class="js-pattern-Telephone"/>
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_gfyh" class="am-u-sm-4 am-form-label">开户银行</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_gfyh" name="xg_gfyh" placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_gfyhzh" class="am-u-sm-4 am-form-label">开户账号</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_gfyhzh" name="xg_gfyhzh"
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xq_lxr" class="am-u-sm-4 am-form-label">联系人</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_lxr" name="xq_lxr"
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_lxdh" class="am-u-sm-4 am-form-label">联系电话</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_lxdh" name="xg_lxdh"  class="js-pattern-Telephone"
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_yjdz" class="am-u-sm-4 am-form-label">邮寄地址</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_yjdz" name="xg_yjdz"
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xg_email" class="am-u-sm-4 am-form-label">Email</label>
											<div class="am-u-sm-8">
												<input type="text" id="xg_email" name="xg_email"
													placeholder="" />
											</div>
										</div>
									</div>
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="button" id="update"
													class="am-btn am-btn-default am-btn-success">保存</button>
												<button type="button" id="close2" class="am-btn am-btn-default am-btn-danger">关闭</button>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
					<div class="am-modal-dialog">
						<div class="am-modal-hd">
							购方信息 <!-- <a href="javascript: void(0)" class="am-close am-close-spin"
								data-am-modal-close>&times;</a> -->
						</div>
						<div class="am-modal-bd">
							<hr />
							<form action="#" class="js-form-0  am-form am-form-horizontal">
								<div class="am-g">
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<label for="xz_gfmc" class="am-u-sm-4 am-form-label"><span
												style="color: red;">*</span>企业名称</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_gfmc" name="xz_gfmc"
													placeholder="" required/>
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_gfsh" class="am-u-sm-4 am-form-label">纳税人识别号</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_gfsh" name="xz_gfsh" required
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_gfdz" class="am-u-sm-4 am-form-label">地址</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_gfdz" name="xz_gfdz" 
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_gfdh" class="am-u-sm-4 am-form-label">电话</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_gfdh" name="xz_gfdh" 
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_gfyh" class="am-u-sm-4 am-form-label">开户银行</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_gfyh" name="xz_gfyh" 
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_gfyhzh" class="am-u-sm-4 am-form-label">开户账号</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_gfyhzh" name="xz_gfyhzh" 
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_lxr" class="am-u-sm-4 am-form-label">联系人</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_lxr" name="xz_lxr" 
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_lxdh" class="am-u-sm-4 am-form-label">联系电话</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_lxdh" name="xz_lxdh"  
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_yjdz" class="am-u-sm-4 am-form-label">邮寄地址</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_yjdz" name="xz_yjdz" 
													placeholder="" />
											</div>
										</div>
										<div class="am-form-group">
											<label for="xz_email" class="am-u-sm-4 am-form-label">Email</label>
											<div class="am-u-sm-8">
												<input type="text" id="xz_email" name="xz_email" 
													placeholder="" />
											</div>
										</div>
									</div>
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="submit" id="save"
													class="am-btn am-btn-default am-btn-success">保存</button>
												<button type="button"  id="close1" class="am-btn am-btn-default am-btn-danger">关闭</button>
											</div>
										</div>
									</div>
								</div>
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
    <script src="assets/js/gfqymp.js"></script>
</body>
</html>