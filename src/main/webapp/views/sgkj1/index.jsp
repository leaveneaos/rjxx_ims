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
	.fp-content{
		height: 730px;
		position: relative;
		padding: 0px 55px;
		border: 2px solid #ccc;
		box-shadow: 0 0 5px #e4e4e4;
		margin-top: 25px;
		min-width: 1010px;

	}
	.fayl-left{
		position: absolute;
		top: 0px;
		left: 0px;
		width: 60px;
		height: 100%;
		background: url("../../img/fpkjfapiaobeijingleft.png") 100% 100% no-repeat;
		background-position: 0 9px;
	}
	.fayl-right{
		position: absolute;
		top: 0px;
		right: 0px;
		width: 60px;
		height: 100%;
		background: url("../../img/fpkjfapiaobeijingright.png") 100% 100% no-repeat;
		background-position: 0 9px;
	}
	.fp-gmfBox{
		width: 100%;
	}
	.fp-gmfBox li{
		width: 100%;
		height: 35px;
		line-height: 35px;
		overflow: hidden;
	}
	.jshj-box span{
		display: inline-block;
		height: 36px;
		line-height: 36px;
		float: left;
	}

	.jshj-box span:nth-child(1){
         min-width: 250px;
	}
	.tableCheckBox{
		display:  inline-block;
		text-align: center;
		cursor: pointer;
		width: 100%;
		height: 100%;
	}
	.tableCheckBoxSelect{
		background-color: #0a628f;
		color: #fff;
	}
	#jyspmx_table tr input{
		width: 100%;
		height: 29px;
	}
	.ddh-searchRed td input,.ddh-searchRed td span{
		color: red;
	}
	#zhifuList{
		background-color: #f5f5f5;
		border: solid 1px #cccccc;
	}
	#zhifuList>li{
		display: inline-block;
		height: 62px;
		width: 120px;
		line-height: 62px;
		text-align: center;
		font-size: 18px;
		color: #838fa1;
		cursor: pointer;
	}
	#zhifuList>li:nth-child(1){
		background-color: #3bb4f2;
		font-family: SourceHanSansCN-Bold;
		font-size: 18px;
		color: #feffff;
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
								<strong class=" am-text-primary am-text-lg" style="color: #838FA1;">业务处理</strong> / <strong style="color: #0e90d2;">手工开具</strong>
							</div>
						</div>
						<div class="am-g">
							<form  id="mainform" class="am-form am-form-horizontal">
								<div class="am-form-group am-form-group-sm" style="margin-top: 20px">
									<label for="xf" class="am-u-sm-1 am-form-label"><span class="star">*</span>销方名称</label>
									<div class="am-u-sm-3">
										<select id="xf" name="xf" onchange="getKpd();" required>
													<option value="">选择销方</option>
													<c:forEach items="${xfList}" var="item">
														<option value="${item.id}">${item.xfmc}</option>
													</c:forEach>
												</select>
									</div>
									<label for="kpd" class="am-u-sm-1 am-form-label"><span class="star">*</span>开票点名称</label>
									<div class="am-u-sm-3">
										<select id="kpd" name="kpd" required onchange="getFplx();">
											<option value="">选择开票点</option>
													<%--<c:if test="${xfnum==1}">
                                                        <c:forEach items="${skpList}" var="item">
                                                            <option value="${item.skpid}">${item.kpdmc}</option>
                                                        </c:forEach>
                                                    </c:if>--%>
										</select>
									</div>
									<label for="fpzldm" class="am-u-sm-1 am-form-label"><span class="star">*</span>发票种类</label>
									<div class="am-u-sm-3">
										<select id="fpzldm" name="fpzldm"  required>
											<%--<option value="">选择开票类型</option>--%>
											<%--<option value="01">专用发票</option>
                                            <option value="02">普通发票</option>--%>
											<%--<option value="12">电子发票</option>--%>
										</select>
									</div>
								</div>
								<div class="am-form-group am-form-group-sm" style="margin-top: 20px">

									<label for="ddh" class="am-u-sm-1 am-form-label"><span class="star">*</span>订单号</label>
									<div class="am-u-sm-3">
										<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input id="ddh" name="ddh" type="text"  placeholder="输入订单号" required>
											<span class="am-input-group-btn">
												<button class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search" id="searchddh" type="button"></button>
											</span>
										</div>
									</div>
									<label for="fpzldm" class="am-u-sm-1 am-form-label"><span id="yxStar" class="star" style="display: none">*</span>购方邮箱</label>
									<div class="am-u-sm-3">
										<input id="yjdz" name="jydz" type="text"  placeholder="输入邮箱" required>
									</div>
									<label for="fpzldm" class="am-u-sm-1 am-form-label">购方手机号</label>
									<div class="am-u-sm-3">
										<input id="lxdh" name="lxdh" type="text"  placeholder="输入手机号" required>
									</div>
								</div>
							</form>
						</div>
						<div class="am-g" style="margin-top: 20px">
							<button id="add"  type="button" class="am-btn am-btn-secondary am-btn-sm " style="border-radius: 10px">增行</button>
							<button id="del" type="button" class="am-btn am-btn-secondary am-btn-sm" style="border-radius: 10px">减行</button>
							<button id="discount" type="button" class="am-btn am-btn-secondary am-btn-sm" style="border-radius: 10px">折扣</button>
							<button id="cha" type="button" class="am-btn am-btn-secondary am-btn-sm" style="border-radius: 10px">差额</button>
							<button id="kj" type="button" class="am-btn am-btn-secondary am-btn-sm" style="border-radius: 10px">开具</button>
							<button id="cz" type="button" class="am-btn am-btn-danger am-btn-sm" style="border-radius: 10px">重置</button>
						</div>
						<div class="am-g fp-content">
							<div class="fayl-left"></div>
							<div class="fayl-right"></div>
							<%--*******************************************************************************8--%>
							<div class="row" style="margin: 0px;padding: 0 50px">
								<div id="centerTitle" class="am-g" style="text-align: center;height:50px;font-size: 24px;line-height: 50px"></div>
								<div class="am-g" style="border:2px solid #ccc">
									<div class="am-g" style="border:  1px solid #ccc;height: 140px;margin: 0px">
										<div class="am-u-sm-1" style="height: 100%;border-right: 1px solid #ccc">
											<br />
											购<br />
											买<br />
											方
										</div>
										<div class="am-u-sm-2">
											<ul class="fp-gmfBox">
												<li><span style="color: red;">*</span>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 称 ：</li>
												<li>纳税人识别号 ：</li>
												<li>地址 &nbsp;  、 电话 ：</li>
												<li>开户行及账户 ：</li>
											</ul>
										</div>
										<div class="am-u-sm-4" style="height: 100%;border-right: 1px solid #ccc;padding: 0">
											<div class="am-u-sm-12" style="height: 35px;padding: 0px" >
												<input id="gfmc" name="gfmc" type="text" style="width: 100%;height: 100%;" value="" placeholder="请输入购方名称" onkeyup="query()">
											</div>
											<div class="am-u-sm-12" style="height: 35px;padding: 0px">
												<input id="gfsh" name="gfsh" type="text" style="width: 100%;height: 100%;" placeholder="请在半角字符下输入" oninput="this.value=this.value.replace(/[^0-9A-Z]/g,'')">
											</div>
											<div class="am-u-sm-12" style="height: 35px;padding: 0px">
												<input id="gfdz" name="gfdz" type="text" style="width: 50%;height: 100%;" placeholder="请输入地址"><input id="gfdh" name="gfdh" type="text" style="width: 50%;height: 100%;" placeholder="请输入电话 ">
											</div>
											<div class="am-u-sm-12" style="height: 35px;padding: 0px">
												<input id="gfyh" name="gfyh" type="text" style="width: 50%;height: 100%;" placeholder="请输入开户行"><input id="yhzh" name="yhzh" type="text" style="width: 50%;height: 100%;" placeholder="请输入账户">
											</div>
										</div>
										<div class="am-u-sm-1" style="height: 100%;border-right: 1px solid #ccc">
											<br />
											密<br />
											码<br />
											方
										</div>
										<div class="am-u-sm-4" style="height: 100%;"></div>
									</div>
									<div class="am-g" style="margin: 0px">
										<table border="1" style="width: 100%">
											<thead style="width: 100%">
											<tr style="width: 100%">
												<th style="width: 5%">序号</th>
												<th style="width: 24%">货物或应税劳务、服务名称</th>
												<th style="width: 18%">规格型号</th>
												<th style="width: 8%">单位</th>
												<th style="width: 8%">数量</th>
												<th style="width: 10%">单价</th>
												<th style="width: 10%">金额</th>
												<th style="width: 8%">税率</th>
												<th style="width: 9%">税额</th>
											</tr>
											</thead>
										</table>
									</div>

									<%--************--%>
									<div class="am-g" style="margin: 0px;height: 213px;overflow-y: auto;">
										<table border="1" style="width: 100%;" id="jyspmx_table">
											<tbody>

											</tbody>
										</table>
									</div>
									<%--*******************8--%>
									<div class="am-g" style="border:  1px solid #ccc;height: 36px;margin: 0px">
										<div class="am-u-sm-4" style="height: 100%;border-right: 1px solid #ccc;text-align: center;line-height: 36px">
                                                 合计
										</div>
										<div class="am-u-sm-4" style="height: 100%;border-right: 1px solid #ccc;line-height: 36px">
											金额合计(不含税):<input id="hjje" name="hjje" type="text" class="selected" readonly="" style="width: 100px" >
										</div>
										<div class="am-u-sm-4" style="height: 100%;border-right: 1px solid #ccc;line-height: 36px">
											税额合计:<input id="hjse" name="hjse" type="text" class="selected" readonly="" style="width: 100px">
										</div>

									</div>
									<div class="am-g" style="border:  1px solid #ccc;height: 36px;margin: 0px">
										<div class="am-u-sm-4" style="height: 100%;border-right: 1px solid #ccc;text-align: center;line-height: 36px">
											价税合计（大写）
										</div>
										<div class="am-u-sm-8 jshj-box" style="height: 100%;line-height: 36px">
											<span id="jshjdx"></span>
											<span style="height: 100%;line-height: 36px">（小写）</span>
											<span id="jshjxx"><input id="jshj" class="selected" style="width: 100px" readonly></span>
										</div>

									</div>
									<div class="am-g" style="border:  1px solid #ccc;height: 140px;margin: 0px">
										<div class="am-u-sm-1" style="height: 100%;border-right: 1px solid #ccc">
											<br />
											销<br />
											售<br />
											方
										</div>
										<div class="am-u-sm-2">
											<ul class="fp-gmfBox">
												<li>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 称 ：</li>
												<li>纳税人识别号 ：</li>
												<li>地址 &nbsp;  、 电话 ：</li>
												<li>开户行及账户 ：</li>
											</ul>
										</div>
										<div class="am-u-sm-4" style="height: 100%;border-right: 1px solid #ccc;padding: 0px">
											<div class="am-u-sm-12" style="height: 35px;padding: 0px" >
												<input id="xfmc" class="selected" style="width: 100%;height: 100%;" name="gfmc" type="text" value="" readonly>
											</div>
											<div class="am-u-sm-12" style="height: 35px;padding: 0px">
												<input id="xfsh" class="selected" style="width: 100%;height: 100%;" name="gfsh" type="text" readonly>
											</div>
											<div class="am-u-sm-12" style="height: 35px;padding: 0px">
												<input id="xfdz" class="selected" style="width: 100%;height: 100%;" name="gfdz" type="text" readonly>
											</div>
											<div class="am-u-sm-12" style="height: 35px;padding: 0px">
												<input id="xhzh" class="selected" style="width: 100%;height: 100%;" name="yhzh" type="text" readonly>
											</div>
										</div>
										<div class="am-u-sm-1" style="height: 100%;border-right: 1px solid #ccc">
											<br />
											备<br />
											注

										</div>
										<%--<div class="am-u-sm-4" style="height: 100%;">--%>
											<textarea id="bz" class="am-u-sm-4" style="height: 100%;"></textarea>
										<%--</div>--%>
									</div>


						<!-- loading do not delete this -->
						<div class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn" tabindex="-1">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">正在载入...</div>
								<div class="am-modal-bd">
									<span class="am-icon-spinner am-icon-spin"></span>
								</div>
							</div>
						</div>
						
					</div>
					</div>
							<div class="am-g" style="padding: 20px 0px;margin: 0px">
								<div class="am-form-group am-form-group-sm">
									<label for="ddh" class="am-u-sm-1 am-form-label">收款人：</label>
									<div class="am-u-sm-2">
										<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input id="skr" style="background-color: lightgray;" type="text" readonly>
										</div>
									</div>
									<label for="ddh" class="am-u-sm-1 am-form-label">复核：</label>
									<div class="am-u-sm-2">
										<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input id="fh" style="background-color: lightgray;" type="text" readonly>
										</div>
									</div>

									<label for="ddh" class="am-u-sm-1 am-form-label">开票人：</label>
									<div class="am-u-sm-2">
										<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input id="kpr" style="background-color: lightgray;" type="text" readonly>
										</div>
									</div>

									<label for="ddh" class="am-u-sm-2 am-form-label">销售方：（章）</label>
									<div class="am-u-sm-1">

									</div>
								</div>
							</div>
						</div>
		                <div  class="am-g" style="margin-top: 20px">
							<ul id="zhifuList">

							</ul>
						</div>
		         </div>
	        </div>
		</div>
	</div>
		<input type="hidden" name="isSave"  id="isSave" value="-1" >
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="discountInfo">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				折扣
			</div>
			<div class="am-modal-bd">
				<hr />
				<form action="#" class="js-form-0  am-form am-form-horizontal">
					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<%--<label for="disNum" class="am-u-sm-3 am-form-label"><span--%>
										<%--style="color: red;">*</span>折扣行数</label>--%>
								<%--<div class="am-u-sm-8">--%>
									<%--<input type="text" id="disNum" name="disNum"--%>
										   <%--placeholder="" required/>--%>
								<%--</div>--%>
							</div>
							<div class="am-form-group">
								<label for="amount" class="am-u-sm-3 am-form-label">商品金额</label>
								<div class="am-u-sm-8">
									<input type="text" id="amount" name="amount" readonly />
								</div>
							</div>
							<div class="am-form-group">
								<label for="xz_gfdz" class="am-u-sm-3 am-form-label">折扣率</label>
								<div class="am-u-sm-8">
									<input type="text" id="xz_gfdz" name="xz_gfdz"
										   placeholder="" readonly />
									<input type="hidden" id ="zkl" name="zkl">
								</div>
							</div>
							<div class="am-form-group">
								<label for="disAmount" class="am-u-sm-3 am-form-label">折扣金额</label>
								<div class="am-u-sm-8">
									<input type="text" id="disAmount" name="disAmount"
										   placeholder="" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<div class="am-u-sm-12  am-text-center">
									<button type="button" id="disSave"
											class="am-btn am-btn-default am-btn-success">确定</button>
									<button type="button"  id="close1" class="am-btn am-btn-default am-btn-danger">关闭</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="difInfo">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				差额
			</div>
			<div class="am-modal-bd">
				<hr />
				<form action="#" class="js-form-0  am-form am-form-horizontal">
					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<label for="disNum" class="am-u-sm-3 am-form-label"><span
										style="color: red;">*</span>含税销售额</label>
								<div class="am-u-sm-8">
									<input type="text" id="hsxse" name="hsxse" required/>
								</div>
							</div>
							<div class="am-form-group">
								<label for="amount" class="am-u-sm-3 am-form-label"><span
										style="color: red;">*</span>扣除额</label>
								<div class="am-u-sm-8">
									<input type="text" id="kce" name="kce" required />
								</div>
							</div>
						</div>
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<div class="am-u-sm-12  am-text-center">
									<button type="button" id="chaSave"
											class="am-btn am-btn-default am-btn-success">确定</button>
									<button type="button"  id="close2" class="am-btn am-btn-default am-btn-danger">关闭</button>
								</div>
							</div>
						</div>
					</div>
				</form>
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
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-6" style="float: right;">
					<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
						<input type="text" id="s_spmc" name="s_spmc" class="am-form-field " placeholder="请输入商品名称">
						<span class="am-input-group-btn" id="button1">
							<button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="button"></button>
						</span>
					</div>
				</div>
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

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="zffs">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				支付方式信息
			</div>
			<div class="am-modal-bd">
				<hr />
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
					<div>
						<table style="margin-bottom: 0px;" class="js-table am-table am-table-bordered am-table-hover am-text-nowrap"
							   id="zffs_table">
							<thead>
							<tr>
								<th style="text-align:center">支付方式名称</th>
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
    <script src="assets/js/sgkj1.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<%--<script src="//code.jquery.com/jquery-1.9.1.js"></script>--%>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
	<script>
        // 模糊查询
//        function query(){
//            $("#gfmc").autocomplete({
//                autoFocus:true,
//                minLength: 2,
//                source:function(request,response){
//                    $.post(
//                        "companyInfo/getNames",
//                        {
//                            "name": $("#gfmc").val()
//                        },function (obj) {
//                            response(eval("("+obj+")"));
//                        });
//                },
////            source:[{
////                "label":"asd",
////                "value":"大帅比"
////            },{
////                "label":"asdf",
////                "value":"帅哭"
////            },{
////                "label":"asdfg",
////                "value":"男默女泪"
////            }],
//                select: function( event, ui ) {
//                    var txt = ui.item.label;
//                    var index = txt.indexOf("(");
//                    var taxNo = txt.substring(index + 1, txt.length-1);
//                    $("#gfsh").val(taxNo);
//                }
//            });
//        }

        function query(){
            $("#gfmc").autocomplete({
                autoFocus:true,
                minLength: 2,
                source:function(request,response){
                    $.post(
                        "companyInfo/list",
                        {
                            "name": $("#gfmc").val()
                        },function (obj) {
                            response(eval("("+obj+")"));
                        });
                },
                select: function( event, ui ) {
                    var name = ui.item.value;
                    $.ajax({
                        url : "companyInfo/single",
                        type:"post",
                        data : {
                            "name" : name
                        },
                        success : function(obj) {
                            $("#gfsh").val(obj);
                        }
                    });
                }
            });
        }

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
                    var option = $("<option>").text('请选择').val(-1);
                    kpd.append(option);
                    for (var i = 0; i < data.length; i++) {
                        var option = $("<option>").text(data[i].kpdmc).val(
                            data[i].skpid);
                        kpd.append(option);
                    }
                }
            });
            $.ajax({
                url : "xfxxwh/getXf",
                data : {
                    "xfid" : xfid
                },
                success : function(data) {
              var dizhi=data.xfdz +""+data.xfdh;
              var zh=data.xfyh +""+data.xfyhzh;
                    $("#xfmc").val(data.xfmc);
                    $("#xfsh").val(data.xfsh);
                    $("#xfdz").val(dizhi );
                    $("#xhzh").val(zh);
                    $("#skr").val(data.skr);
                    $("#fh").val(data.fhr);
                    $("#kpr").val(data.kpr);
                    $("#bz").val(data.bz);


                }
            });
        }

        //选取税控盘获取发票类型
        function getFplx() {
            var skpid = $('#kpd option:selected').val();
            if(skpid !=null && skpid !=""){
                var fpzldm = $("#fpzldm");
                $("#fpzldm").empty();
                $.ajax({
                    url : "pttqkp/getFpzldm",
                    data : {
                        "skpid" : skpid
                    },
                    success : function(data) {
                        var option = $("<option>").text('请选择').val(-1);
                        fpzldm.append(option);
                        for (var i = 0; i < data.length; i++) {
                            option = $("<option>").text(data[i].fpzlmc).val(
                                data[i].fpzldm);
                            fpzldm.append(option);
                        }
                    }
                });
            }
        };
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

      </div>
</body>
</html>
