<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>发票重新打印</title>
<meta name="description" content="发票重打" />
<meta name="keywords" content="发票重打" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="renderer" content="webkit" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="../../assets/i/favicon.png" />
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css" />
<link rel="stylesheet" href="assets/css/amazeui.tree.min.css" />
<link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="assets/css/app.css">
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
	<OBJECT ID=sk CLASSID="clsid:003BD8F2-A6C3-48EF-9B72-ECFD8FC4D49F" style="height: 0px"
			codebase="NISEC_SKSCX.ocx#version=1,0,0,1">
	</OBJECT>
<script src="assets/js/loading.js"></script>
<style type="text/css">
.am-form-horizontal .am-form-label {
	text-align: left;
}
.data-ctr {
  text-align: center;
}
#doc-modal-fpyl{
	background-image: url("<%=request.getContextPath()%>/img/fapiaobiankuang.jpg");
	background-size:100% 100%;
	background-repeat:no-repeat;

}
</style>
</head>
<body>
	<div class="row-content am-cf">
		<div class="row">
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
				<div class="am-cf admin-main widget">
					<div class="am-tabs" data-am-tabs="{noSwipe: 1}">
						<!--<ul class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tab1">发票重打</a></li>
							<li><a href="#tab2">发票重打查询</a></li>
						</ul>-->

						<div class="am-tabs-bd">
							<div class="am-tab-panel am-fade am-in am-active" id="tab1">
								<div class="am-cf widget-head">
									<input type="hidden" id="djh" value="0">
									<input type="hidden" id="bz" value="0">
									<input type="hidden" id="bj" value="0">
									<input type="hidden" id="kplsh" value="0">
									<div class="widget-title am-cf">
										<strong id="yjcd" class="am-text-primary am-text-lg" style="color: #838FA1;"></strong> / <strong id="ejcd" style="color: #0e90d2;"></strong>
									</div>
									<div id="doc-oc-demo1" class="am-offcanvas">
										<div class="am-offcanvas-bar am-offcanvas-bar-flip">
											 <form  id ="ycform">
											    <div class="am-offcanvas-content">
													<div class="am-form-group">
														<label for="s_xfsh" class="am-u-sm-4 am-form-label">选择销方</label>
														<div class="am-u-sm-8">
															<select data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}" id="s_xfsh"
																name="s_xfsh">
																<option id="xzxfq" value="">选择销方</option>
																<c:forEach items="${xfList}" var="item">
																	<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
												<%--<div class="am-offcanvas-content" style="margin-top: 5px;">
													<div class="am-form-group">
														<label for="s_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
														<div class="am-u-sm-8">
															<input id="s_fpdm" type="text"
																placeholder="发票代码">
														</div>
													</div>
												</div>--%>
												<div class="am-offcanvas-content" style="margin-top: 5px;">
													<div class="am-form-group">
														<label for="s_fphm" class="am-u-sm-4 am-form-label">发票号码</label>
														<div class="am-u-sm-8">
															<input id="s_fphm" type="text"
																placeholder="发票号码">
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
																<%--<option value="12">电子发票(增普)</option>--%>
																<option value="01">增值税专用发票</option>
																<option value="02">增值税普通发票</option>
															</select>
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
														<label for="s_rqq" class="am-u-sm-4 am-form-label">开票日期</label>
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
														<label for="s_rqz" class="am-u-sm-4 am-form-label">开票日期</label>
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
													<button type="button" id="zf_search1"
														class="am-btn am-btn-default am-btn-secondary data-back">
														<span></span> 查询
													</button>
												</div>
											</form>
										</div>
									</div>
								</div>
								<div class="am-g" style="margin-top: 20px">
									<form action="#" class="js-search-form  am-form am-form-horizontal">
										<div class="am-u-sm-5">
											<div class="am-form-group">
												<div class="am-u-sm-3">
													开票日期
												</div>
												<div class="am-u-sm-4">
													<input type="text" id="w_kprqq" name="w_kprqq"
														   placeholder="开票起始时间"
														   onfocus="$(this).blur()"
														   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
												</div>
												<div class="am-u-sm-1">
													至
												</div>
												<div class="am-u-sm-4">
													<input type="text" id="w_kprqz" name="w_kprqz"
														   placeholder="开票截止时间"
														   onfocus="$(this).blur()"
														   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
												</div>
											</div>
										</div>
										<div class="am-u-sm-2">
											<select id="dxcsm" >
												<option value="ddh">订单号</option>
												<option value="fphm">发票号码</option>
												<option value="gfmc">购方名称</option>
											</select>
										</div>
										<div class="am-u-sm-2">
											<div
													class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
												<input id="dxcsz" type="text" class="am-form-field ">
												<span class="am-input-group-btn">
														<button id="zf_search"
																class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search"
																type="button"></button>
													</span>
											</div>
										</div>
										<button class="am-btn am-btn-secondary am-fr"
												data-am-offcanvas="{target: '#doc-oc-demo1'}" style="float: right">更多查询</button>

									</form>
								</div>


								<div class="am-g  am-padding-top">
									<form action="#"
										class="js-search-form  am-form am-form-horizontal">
										<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
											<div class="am-form-group">
												<div class="am-btn-toolbar">
													<div class="am-btn-group am-btn-group-xs">
														<button type="button" id="kp_cd"
															class="am-btn am-btn-default am-btn-secondary">
															 重打
														</button>
													</div>
												</div>
											</div>
										</div>
									</form>
									<div class="am-u-sm-12 am-padding-top">
										<div>
											<table style="margin-bottom: 0px;"
												class="js-table2 am-table am-table-bordered am-table-hover am-table-striped am-text-nowrap "
												id="fpTable">
												<thead>
													<tr>
													    <th><input type="checkbox" id="check_all" /></th>
														<th>序号</th>
														<th>操作</th>
														<th>订单号</th>
														<th>开票日期</th>
														<th>发票代码</th>
														<th>发票号码</th>
														<th>购方名称</th>
														<th>发票类型</th>
														<th class="data-ctr">金额</th>
														<th class="data-ctr">税额</th>
														<th class="data-ctr">价税合计</th>
													</tr>
												</thead>
											</table>
											<legend>商品明细列表</legend>
										</div>
									</div>

									<div style="margin-top: 0px; margin-left: 0px;"
										class="am-u-sm-12">
										<div>
											<table style="margin-bottom: 0px;"
												class="js-mxtable am-table am-table-bordered am-table-hover am-text-nowrap am-table-striped"
												id="mxTable">
												<thead>
													<tr>
														<th>序号</th>
														<th>商品名称</th>
														<th>商品规格型号</th>
														<th>商品金额</th>
														<th>商品税率</th>
														<th>商品税额</th>
														<th>价税合计</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="am-tab-panel am-fade" id="tab2">
							 <div class="am-cf widget-head">
									<div class="widget-title am-cf">
										<strong id="yjcd" class="am-text-primary am-text-lg" style="color: #838FA1;"></strong> / <strong id="ejcd" style="color: #0e90d2;"></strong>

									</div>
									<div id="doc-oc-demo2" class="am-offcanvas">
										<div class="am-offcanvas-bar am-offcanvas-bar-flip">
											 <form  id ="ycform1">
												 <div class="am-offcanvas-content">
														<div class="am-form-group">
															<label for="s_xfsh1" class="am-u-sm-4 am-form-label">选择销方</label>
															<div class="am-u-sm-8">
																<select data-am-selected="{maxHeight: 300,btnSize: 'sm',searchBox: 1}" id="s_xfsh1"
																	name="s_xfsh1">
																	<option id="xzxfq1" value="">选择销方</option>
																	<c:forEach items="${xfList}" var="item">
																		<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
																	</c:forEach>
																</select>
															</div>
														</div>
													</div>
												<div class="am-offcanvas-content" style="margin-top: 5px;">
													<div class="am-form-group">
														<label for="s_fpdm1" class="am-u-sm-4 am-form-label">发票代码</label>
														<div class="am-u-sm-8">
															<input id="s_fpdm1" type="text"
																placeholder="发票代码">
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content" style="margin-top: 5px;">
													<div class="am-form-group">
														<label for="s_fphm1" class="am-u-sm-4 am-form-label">发票号码</label>
														<div class="am-u-sm-8">
															<input id="s_fphm1" type="text"
																placeholder="发票号码">
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content" style="margin-top: 8px;">
													<div class="am-form-group">
														<label for="s_fplx1" class="am-u-sm-4 am-form-label">发票类型</label>
														<div class="am-u-sm-8">
															<select data-am-selected="{btnSize: 'sm'}" id="s_fplx1"
																name="xfsh">
																<option id="xzlxq1" value="">选择类型</option>
																<option value="12">电子发票(增普)</option>
																<option value="01">增值税专用发票</option>
																<option value="02">增值税普通发票</option>
															</select>
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content" style="margin-top: 5px;">
													<div class="am-form-group">
														<label for="s_gfmc1" class="am-u-sm-4 am-form-label">购方名称</label>
														<div class="am-u-sm-8">
															<input id="s_gfmc1" type="text"
																placeholder="购方名称">
														</div>
													</div>
												</div>
												<div class="am-offcanvas-content" style="margin-top: 5px;">
													<div class="am-form-group">
														<label for="s_ddh1" class="am-u-sm-4 am-form-label">订单号</label>
														<div class="am-u-sm-8">
															<input id="s_ddh1" type="text" 
																placeholder="订单号">
														</div>
													</div>
												</div>
			
											
												<div class="am-offcanvas-content" style="margin-top: 8px;">
													<div class="am-form-group">
														<label for="s_rqq1" class="am-u-sm-4 am-form-label">开票日期</label>
														<div class="am-input-group am-datepicker-date am-u-sm-8"
															data-am-datepicker="{format: 'yyyy-mm-dd'}">
															<input type="text" id="s_rqq1" class="am-form-field"
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
														<label for="s_rqz1" class="am-u-sm-4 am-form-label">开票日期</label>
														<div class="am-input-group am-datepicker-date am-u-sm-8"
															data-am-datepicker="{format: 'yyyy-mm-dd'}">
															<input type="text" id="s_rqz1" class="am-form-field"
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
													<button type="button" id="zf_search3"
														class="am-btn am-btn-default am-btn-secondary data-back">
														 查询
													</button>
												</div>
											</form>
										</div>
									</div>
								</div>


								<div class="am-g" style="margin-top: 20px">
									<form action="#" class="js-search-form  am-form am-form-horizontal">
										<div class="am-u-sm-5">
											<div class="am-form-group">
												<div class="am-u-sm-3">
													开票日期
												</div>
												<div class="am-u-sm-4">
													<input type="text" id="w_kprqq1" name="w_kprqq"
														   placeholder="开票起始时间"
														   onfocus="$(this).blur()"
														   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
												</div>
												<div class="am-u-sm-1">
													至
												</div>
												<div class="am-u-sm-4">
													<input type="text" id="w_kprqz1" name="w_kprqz"
														   placeholder="开票截止时间"
														   onfocus="$(this).blur()"
														   data-am-datepicker="{format: 'yyyy-mm-dd'}" />
												</div>
											</div>
										</div>
										<div class="am-u-sm-2">
											<select id="dxcsm1" data-am-selected="{btnSize: 'sm'}">
												<option value="ddh">订单号</option>
												<option value="fphm">发票号码</option>
												<option value="gfmc">购方名称</option>
											</select>
										</div>
										<div class="am-u-sm-2">
											<div
													class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
												<input id="dxcsz1" type="text" class="am-form-field ">
												<span class="am-input-group-btn">
														<button id="zf_search2"
																class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search"
																type="button"></button>
													</span>
											</div>
										</div>
										<button class="am-btn am-btn-secondary am-fr"
												data-am-offcanvas="{target: '#doc-oc-demo2'}" style="float: right">更多查询</button>

									</form>
								</div>



								<div class="am-g  am-padding-top">
									<%--<form action="#"--%>
										<%--class="js-search-form  am-form am-form-horizontal">--%>
										<%--<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">--%>
											<%--<div class="am-form-group">--%>
												<%--<div class="am-btn-toolbar">--%>
													<%--<div class="am-btn-group am-btn-group-xs">--%>
														<%--<!-- <button type="button" id="kp_kp"--%>
															<%--class="am-btn am-btn-default am-btn-success">--%>
															<%--<span></span> 开票--%>
														<%--</button> -->--%>
													<%--</div>--%>
												<%--</div>--%>
											<%--</div>--%>
										<%--</div>--%>
										<%--<div class="am-u-sm-12 am-u-md-6 am-u-lg-3">--%>
											<%--<div class="am-form-group tpl-table-list-select">--%>
												<%--<select id="dxcsm1" data-am-selected="{btnSize: 'sm'}">--%>
													<%--<option value="ddh">订单号</option>--%>
													<%--<option value="fphm">发票号码</option>--%>
													<%--<option value="gfmc">购方名称</option>--%>
												<%--</select>--%>
											<%--</div>--%>
										<%--</div>--%>
										<%--<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">--%>
											<%--<div--%>
												<%--class="am-input-group am-input-group-sm tpl-form-border-form cl-p">--%>
												<%--<input id="dxcsz1" type="text" class="am-form-field ">--%>
												<%--<span class="am-input-group-btn">--%>
													<%--<button id="zf_search2"--%>
														<%--class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"--%>
														<%--type="button"></button>--%>
												<%--</span>--%>
											<%--</div>--%>
										<%--</div>--%>
										<%--<div class="am-u-sm-12 am-u-md-6 am-u-lg-8">--%>
											<%--<div class="am-form-group">--%>
												<%--<div class="am-u-md-6 am-u-lg-2">--%>
													<%--开票日期--%>
												<%--</div>--%>
												<%--<div class="am-u-md-6 am-u-lg-3">--%>
													<%--<input type="text" id="w_kprqq1" name="w_kprqq"--%>
														   <%--placeholder="开票起始日期"--%>
														   <%--data-am-datepicker="{format: 'yyyy-mm-dd'}" />--%>
												<%--</div>--%>
												<%--<div class="am-u-md-6 am-u-lg-1">--%>
													<%--至--%>
												<%--</div>--%>
												<%--<div class="am-u-md-6 am-u-lg-3">--%>
													<%--<input type="text" id="w_kprqz1" name="w_kprqz"--%>
														   <%--placeholder="开票截止日期"--%>
														   <%--data-am-datepicker="{format: 'yyyy-mm-dd'}" />--%>
												<%--</div>--%>
											<%--</div>--%>
										<%--</div>--%>
									<%--</form>--%>
									<div class="am-u-sm-12 am-padding-top">
										<div>
											<table style="margin-bottom: 0px;"
												class="js-table2 am-table am-table-bordered am-table-hover am-table-striped am-text-nowrap "
												id="ysTable">
												<thead>
													<tr>
														<th>序号</th>
														<th>操作</th>
														<th>订单号</th>
														<th>开票日期</th>
														<th>发票代码</th>
														<th>发票号码</th>
														<th>购方名称</th>
														<th>发票类型</th>
														<th>金额</th>
														<th>税额</th>
														<th>价税合计</th>
													</tr>
												</thead>
											</table>
											<legend>商品明细列表</legend>
										</div>
									</div>

									<div style="margin-top: 0px; margin-left: 0px;"
										class="am-u-sm-12">
										<div>
											<table style="margin-bottom: 0px;"
												class="js-mxtable am-table am-table-bordered am-table-hover am-text-nowrap am-table-striped"
												id="mxTable1">
												<thead>
													<tr>
														<th>序号</th>
														<th>商品名称</th>
														<th>商品规格型号</th>
														<th>商品金额</th>
														<th>商品税率</th>
														<th>商品税额</th>
														<th>价税合计</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
						    </div>
						</div>
					</div>




					<!-- content end -->
				</div>
			</div>
		</div>
            <div style="margin-left: -40%; background-color: white;width:910px;padding: 0px 46px 35px;" class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-fpyl">
			     <div class="am-modal-hd">
			      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			    </div>
			  <div id="doc-modal-fpyll" style="background-color: white;"class="am-modal-dialog">
			       <div class="am-modal-bd" >
			       <div id="doc-modal-fpyll">
                      <%@ include file="fapiao.jsp" %>
					</div>
				</div>
			  </div>
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
	<script src="assets/js/fpcd.js"></script>
	<script src="assets/js/sweetalert.min.js"></script>
</body>
<script>
	function refresh() {
		this.location = this.location;
	}
</script>
<script type="text/javascript">
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
    function cssz1(servletip,servletport,keypwd) {
        //var servletip ="180.153.192.171";
        //var servletport ="18080";
        //默认8个8
        //var keypwd ="88888888";
        var csInfo = "<?xml version=\"1.0\" encoding=\"gbk\"?>\r\n<business id=\"20001\" comment=\"参数设置\">\r\n<body yylxdm=\"1\">\r\n<servletip>"+servletip+"</servletip>\r\n<servletport>"+servletport+"</servletport>\r\n<keypwd>"+keypwd+"</keypwd>\r\n</body>\r\n</business>\r\n";
        try
        {
            var ret = sk.Operate(csInfo);
            var xmlDoc = $.parseXML(ret);
            var returncode= xmlDoc.getElementsByTagName('returncode')[0].textContent;
            var returnmsg= xmlDoc.getElementsByTagName('returnmsg')[0].textContent;
            if(returncode !=null && returncode !="0"){
                swal(returnmsg);
                return false;
            }
        }catch(e) {
            swal(e.message + ",errno:" + e.number);
            return false;
        }
        return true;
    };
    $(function() {
        var startDate = getCurrentMonthFirst();
        var endDate = new Date();
        //var $alert = $('#my-alert');
        $("#w_kprqq").val(formatDate(startDate));
        $("#w_kprqz").val(formatDate(endDate));
        $("#s_rqq").val(formatDate(startDate));
        $("#s_rqz").val(formatDate(endDate));
        $("#w_kprqq1").val(formatDate(startDate));
        $("#w_kprqz1").val(formatDate(endDate));
        $("#s_rqq1").val(formatDate(startDate));
        $("#s_rqz1").val(formatDate(endDate));
    });
</script>
</html>
