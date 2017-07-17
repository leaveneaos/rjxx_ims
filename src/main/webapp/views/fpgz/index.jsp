<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>分票规则</title>
<meta name="description" content="分票规则">
<meta name="keywords" content="分票规则">
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
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="css/main.css" />
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
	<!-- sidebar end -->

	<!-- content start -->
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="widget am-cf">
					<div class="admin-content">
						<div class="am-cf widget-head">
							<div class="widget-title am-cf">
								<strong class="am-text-primary am-text-lg">开票规则</strong> / <strong>分票规则</strong>
							</div>
						</div>
						<div class="am-g  am-padding-top">
							<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
								<div class="am-form-group">
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
											<button type="button" id="gz_xzgz"
												data-am-modal="{target: '#doc-modal-4', closeViaDimmer: 0, width: 600}"
												class="am-btn am-btn-default am-btn-success">
												<span></span> 新增
											</button>
											<button type="button" id="gz_scgz"
												class="am-btn am-btn-default am-btn-danger">
												<span></span> 删除
											</button>
												<button type="button" id="gz_cxgz"
												class="am-btn am-btn-default  am-btn-success">
												<span></span> 查询
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- 	<a type="button" id="gz_xzgz"
						class="am-btn am-btn-primary am-radius am-icon-plus"
						style="margin-left: 70%" href="#"
						data-am-modal="{target: '#doc-modal-4', closeViaDimmer: 0, width: 600}">增加规则</a> -->
						<div  class="am-u-sm-12">
							<table
								class="js-table am-table am-table-bordered am-text-nowrap am-scrollable-horizontal"
								id="jyls_table">
								<thead>
									<tr>
										<th><input type="checkbox" id="check_all" /></th>
										<th>序号</th>
										<th>操作</th>
										<th>规则名称</th>
										<th class="data-ctr">专票分票金额</th>
										<th class="data-ctr">专票分票行数</th>
										<th class="data-ctr">普票分票金额</th>
										<th class="data-ctr">普票分票行数</th>
										<th class="data-ctr">电子票分票金额</th>
										<th class="data-ctr">电子票分票行数</th>
										<th>是否默认</th>
										<th>是否含税</th>
										<th>是否打印清单</th>
										<th>是否强制分票</th>
										<th>是否按商品整数分票</th>
									</tr>
								</thead>
							</table>
						</div>
						<!-- model -->
						<div class="am-modal am-modal-no-btn" tabindex="-1"
							id="doc-modal-4">
							<div class="am-modal-dialog" style="overflow: auto;">
								<form id="fpform" class="js-form-0 am-form am-form-horizontal">
									<div class="am-tabs" data-am-tabs>
										<ul class="am-tabs-nav am-nav am-nav-tabs">
											<li class="am-active"><a href="#tab1">参数值</a></li>
											<li><a href="#tab3">销方</a></li>
										</ul>
										<div class="am-tabs-bd">
											<div class="am-tab-panel am-fade am-in am-active" id="tab1">
												<div class="am-modal-hd">
													参数值 <a href="javascript: void(0)"
														class="am-close am-close-spin" data-am-modal-close>&times;</a>
												</div>
												<div class="am-modal-bd" style="overflow: auto;">
													<hr />
													<div class="am-g">
														<input type="hidden" name="idd" id="idd">
														<div class="am-u-sm-12">
															<div class="am-form-group">
																<label for="gzmc" class="am-u-sm-4 am-form-label"><font
																	style="color: red;">*</font>规则名称</label>
																<div class="am-u-sm-8">
																	<input type="text" id="ggmc" name="ggmc"
																		placeholder="规则名称" class="am-text-left  am-form-field" required
																		maxlength="20" />
																</div>
															</div>
															<div class="am-form-group">
																<label for="gzmc" class="am-u-sm-4 am-form-label"><font
																	style="color: red;">*</font>专票分票金额</label>
																<div class="am-u-sm-8">
																	<input type="text" id="zpxe" name="zpxe"
																		placeholder="专票分票金额"
																		class="js-pattern-Money am-text-right am-form-field" required
																		maxlength="20" />
																</div>
															</div>
															<div class="am-form-group">
																<label for="gzmc" class="am-u-sm-4 am-form-label"><font
																	style="color: red;">*</font>专票分票行数</label>
																<div class="am-u-sm-8">
																	<input type="text" id="zphs" name="zphs"
																		placeholder="普票分票行数"
																		class="js-pattern-integer am-text-right am-form-field" max="8"
																		 required="required" maxlength="1" />
																</div>
															</div>
															<div class="am-form-group">
																<label for="gzmc" class="am-u-sm-4 am-form-label"><font
																	style="color: red;">*</font>普票分票金额</label>
																<div class="am-u-sm-8">
																	<input type="text" id="ppxe" name="ppxe"
																		placeholder="普票分票金额"
																		class="js-pattern-Money am-text-right am-form-field" required
																		maxlength="20" />
																</div>
															</div>
															<div class="am-form-group">
																<label for="gzmc" class="am-u-sm-4 am-form-label"><font
																	style="color: red;">*</font>普票分票行数</label>
																<div class="am-u-sm-8">
																	<input type="text" id="pphs" name="pphs"
																		placeholder="普票分票行数"
																		class="js-pattern-integer am-text-right am-form-field" max="8"
																		 required="required" maxlength="1" />
																</div>
															</div>
															<div class="am-form-group">
																<label for="gzmc" class="am-u-sm-4 am-form-label"><font
																	style="color: red;">*</font>电子票分票金额</label>
																<div class="am-u-sm-8">
																	<input type="text" id="dzpxe" name="dzpxe"
																		placeholder="电子票分票金额"
																		class="js-pattern-Money am-text-right am-form-field" required
																		maxlength="20" />
																</div>
															</div>
															<div class="am-form-group">
																<label for="gzmc" class="am-u-sm-4 am-form-label"><font
																	style="color: red;">*</font>电子票分票行数</label>
																<div class="am-u-sm-8">
																	<input type="text" id="dzphs" name="dzphs"
																		placeholder="电子票分票行数"
																		class="js-pattern-integer am-text-right am-form-field" max="100"
																		 required="required" maxlength="3" />
																</div>
															</div>
														</div>
														<div class="am-u-sm-12" style="margin-top: 10px;">
															<div class="am-form-group">
																<label for="mrbz" class="am-u-lg-4 am-form-label">默认标志</label>
																<div class="am-u-lg-2">
																	<input id="mrbz" value="1" type="checkbox" name="mrbz">
																</div>
																<label for="hsbz" class="am-u-lg-4 am-form-label">含税标志</label>
																<div class="am-u-lg-2">
																	<input id="hsbz" value="1" type="checkbox" name="hsbz">
																</div>
															
															</div>
														</div>
														<div class="am-u-sm-12">
																<div class="am-form-group">
																		<label for="qdbz" class="am-u-lg-4 am-form-label">清单标志</label>
																	<div class="am-u-lg-2">
																		<input id="qdbz" value="1" type="checkbox" name="qdbz">
																	</div>
																	<label for="qzfp" class="am-u-lg-4 am-form-label">强制分票</label>
																	<div class="am-u-lg-2">
																		<input id="qzfp" value="1" type="checkbox" name="sfqzfp">
																	</div>
																</div>
														</div>
														<div class="am-u-sm-12">
															<div class="am-form-group">
																<label for="sfspzsfp" class="am-u-lg-4 am-form-label">商品整数分票</label>
																<div class="am-u-lg-2">
																	<input id="sfspzsfp" value="1" type="checkbox" name="sfspzsfp">
																</div>
															</div>
														</div>
														<div class="am-u-sm-12" style="margin-top: 10px;">
															<div class="am-form-group">
																<div class="am-u-sm-12  am-text-center">
																	<button type="submit"
																		class="gz_xz am-btn am-btn-primary">确定</button>
																	<button type="button"
																		class="gz_qx am-btn am-btn-danger">取消</button>
																</div>
															</div>
														</div>
													</div>

												</div>
											</div>
											<div class="am-tab-panel am-fade" id="tab3">
												<div class="am-modal-hd">
													<a href="javascript: void(0)"
														class="am-close am-close-spin" data-am-modal-close>&times;</a>
												</div>
												<table style="width: 100%; overflow: auto;">
													<tr>
														<td>销方</td>

													</tr>
													<tr align="left">
														<td style="width: 100%" colspan="2">
															<div title="用户机构" id="bm-box1">
																<c:forEach items="${xfs }" var="x">
																	<div id="type-${x.id}" class="chk">
																		<label> <input type="checkbox"
																			id="yhjg1-${x.id }" name="xfid" value="${x.id }" />
																			<span>${x.xfmc }</span>
																		</label> <br>
																		<%-- 						<c:forEach items="${sksbs }" var="sksb" varStatus="h">

															<c:if test="${sksb.xfid == x.id}">
																<input type="checkbox" id="skp-${sksb.id }"
																	onclick="xzskp(this)" name="${x.id }"
																	value="${sksb.id }" />
																<span>${sksb.kpdmc }(${sksb.skph })</span>
																		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</c:if>
																
														</c:forEach> --%>

																	</div>
																</c:forEach>
															</div>
														</td>
													</tr>
												</table>
												<div class="am-u-sm-12">
													<div class="am-form-group">
														<div class="am-u-sm-12  am-text-center">
															<button type="submit" class="gz_xz am-btn am-btn-primary">确定</button>
															<button type="button" class="gz_qx  am-btn am-btn-danger">取消</button>
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
	<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/fpgz.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
</body>
</html>
