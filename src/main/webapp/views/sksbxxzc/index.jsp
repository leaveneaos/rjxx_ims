<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>税控设备信息注册</title>
<meta name="description" content="用户管理">
<meta name="keywords" content="用户管理">
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
</head>
<body>
	<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../../pages/menus.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">基础数据</strong> / <strong>税控设备信息注册</strong>
				</div>
			</div>
			<hr />
			<div class="am-g">
				<form class="js-search-form am-form am-form-horizontal">
					<div class="am-g">
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-3 am-form-label">销方</label>
								<div class="am-u-sm-9">
									<select id="xfid1" name="xfid1">
										<option value="">请选择</option>
										<c:forEach items="${xfs}" var="item">
											<option value="${item.id}">${item.xfmc}(${item.xfsh})</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-3 am-form-label">开票点代码</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_kpddm" name="s_kpddm"
										placeholder="开票点代码" />
								</div>
							</div>
						</div>
						<div class="am-u-sm-6">
							<div class="am-form-group">
								<label for="s_fpdm" class="am-u-sm-3 am-form-label">开票点名称</label>
								<div class="am-u-sm-9">
									<input type="text" id="s_kpdmc" name="s_kpdmc"
										placeholder="开票点名称" />
								</div>
							</div>
						</div>
					</div>
					<hr />
					<div class="am-u-sm-12  am-padding  am-text-right">
						<button id="button1" type="button"
							class="js-search  am-btn am-radius am-btn-success">查询</button>
						<button type="button" class="am-btn am-radius am-btn-success js-modal-open">新增</button>
						<button type="button" class="js-sent  am-btn am-radius am-btn-danger">批量删除</button>
						<button type="button" id="kp_dr"
							class="am-btn am-radius am-btn-default"
							style="margin-right: 10px;">
							<span></span> 批量导入
						</button>
					</div>
					<div class="am-u-sm-12">
						<div class="am-scrollable-horizontal">

							<table id="tbl"
								class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
								<thead>
									<tr>
										<th><input type="checkbox" id="check_all" /></th>
										<th>序号</th>
										<th style="display: none;">id</th>
										<th style="display: none;">pid</th>																		
										<th style="display: none;">税控密码</th>
										<th style="display: none;">证书密码</th>	
										<th>销方名称</th>
										<th>开票点代码</th>
										<th>开票点名称</th>																				
										<th>设备厂商</th>
										<th>税控盘号</th>																			
										<th>税控密码</th>
										<th>证书密码</th>																			
										<th>联系地址</th>
										<th>联系电话</th>																			
										<th>开户银行</th>
										<th>银行账号</th>																			
										<th>收款人</th>
										<th>复核人</th>																			
										<th>开票人</th>
										<th>品牌代码</th>
										<th>品牌名称</th>	
<!-- 										<th>电子发票开票限额</th> -->
<!-- 										<th>电子发票分票金额</th> -->
<!-- 										<th style="display: none;">普通发票开票限额</th> -->
<!-- 										<th style="display: none;">普通发票分票金额</th> -->
<!-- 										<th style="display: none;">专用发票开票限额</th> -->
<!-- 										<th style="display: none;">专用发票分票金额</th> -->
<!-- 										<th style="display: none;">注册码</th> -->
										<th>操作</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>

					</div>
				</form>
			</div>
		</div>

		<!-- model -->
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
			<div class="am-modal-dialog" style="overflow-y: auto">
				<div class="am-modal-hd">
					税控设备信息 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<hr />
					<form action="sksbxxzc/save" method="get"
						class="js-form am-form am-form-horizontal">
						<div class="am-g">

							<div class="am-u-sm-12">

								<div class="am-form-group">
									<label for="xfmc" class="am-u-sm-4 am-form-label"><font color="red">*</font>销方名称</label>
									<div class="am-u-sm-8">
										<select id="xfid" name="xfid" required>
											<c:forEach items="${xfs}" var="item">
												<option value="${item.id}">${item.xfmc}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="am-form-group">
									<label for="kpddm" class="am-u-sm-4 am-form-label"><font color="red">*</font>开票点代码</label>
									<div class="am-u-sm-8">
										<input type="text" id="kpddm" name="kpddm"
											placeholder="请输入开票点代码" required />
									</div>
								</div>
								<div class="am-form-group">
									<label for="kpdmc" class="am-u-sm-4 am-form-label"><font color="red">*</font>开票点名称</label>
									<div class="am-u-sm-8">
										<input type="text" id="kpdmc" name="kpdmc"
											placeholder="请输入开票点名称" required />
									</div>
								</div>																
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">设备厂商</label>
									<div class="am-u-sm-8">
										<select id="sbcs" name="sbcs">
											<option value="1">百旺(黑色)</option>
											<option value="2">航行(白色)</option>
										</select>
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">税控盘号</label>
									<div class="am-u-sm-8">
										<input type="text" id="skph" name="skph"
											placeholder="请输入税控盘号" />
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">税控盘密码</label>
									<div class="am-u-sm-8">
										<input type="password" id="skpmm" name="skpmm" style="float: left;"
											placeholder="税控盘密码" class="am-form-field"
											maxlength="30" />
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">证书密码</label>
									<div class="am-u-sm-8">
										<input type="password" id="zsmm" name="zsmm" style="float: left;"
											placeholder="证书密码" class="am-form-field"
											maxlength="30" />
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">联系地址</label>
									<div class="am-u-sm-8">
											<input type="text" id="lxdz" name="lxdz" style="float: left;"
												placeholder="联系地址" value="${xf.xfdz }" class="am-form-field"
												/>
									
									</div>
								</div>														
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">联系电话</label>
									<div class="am-u-sm-8">
										<input type="text" id="lxdh" name="lxdh" value="${xf.xfdh }" style="float: left;"
											placeholder="联系电话" class="am-form-field"
											/>
									</div>
								</div>													
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">开户银行</label>
									<div class="am-u-sm-8">
										<input type="text" id="khyh" name="khyh" style="float: left;"
											placeholder="开户银行" value="${xf.xfyh }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">银行账号</label>
									<div class="am-u-sm-8">
										<input type="text" id="yhzh" name="yhzh" style="float: left;"
											placeholder="银行账号" value="${xf.xfyhzh }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">收款人</label>
									<div class="am-u-sm-8">
										<input type="text" id="skr" name="skr" style="float: left;"
											placeholder="收款人" value="${xf.skr }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">复核人</label>
									<div class="am-u-sm-8">
										<input type="text" id="fhr" name="fhr" style="float: left;"
											placeholder="复核人" value="${xf.fhr }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">开票人</label>
									<div class="am-u-sm-8">
										<input type="text" id="kpr" name="kpr" style="float: left;"
											placeholder="开票人" value="${xf.kpr }" class="am-form-field"
											/>
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-3 am-form-label"><font color="red">*</font>开票类型</label>
									<label for="hc_yfphm" class="am-u-sm-3"><input type="checkbox" name="fplx" value="02">普通发票</label>
									<label for="hc_yfphm" class="am-u-sm-3"><input type="checkbox" name="fplx" value="01">专用发票</label>
									<label for="hc_yfphm" class="am-u-sm-3"><input type="checkbox" name="fplx" value="12">电子发票</label>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">专用发票开票限额</label>
										<div class="am-u-sm-8">
											<input type="text" id="kpxe2" name="kpxe2"
												style="float: left;" placeholder="开票限额" value=""
												pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
												class="am-form-field" />
										</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">专用发票分票金额</label>
										<div class="am-u-sm-8">
											<input type="text" id="fpje2" name="fpje2"
												style="float: left;" placeholder="分票金额"
												value=""
												pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
												class="am-form-field" />
										</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">普通发票开票限额</label>
										<div class="am-u-sm-8">
											<input type="text" id="kpxe1" name="kpxe1"
												style="float: left;" placeholder="开票限额"
												value=""
												pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
												class="am-form-field"/>
										</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">普通发票分票金额</label>
										<div class="am-u-sm-8">
											<input type="text" id="fpje1" name="fpje1"
												style="float: left;" placeholder="分票金额"
												value=""
												pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
												class="am-form-field"/>
										</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">电子发票开票限额</label>
										<div class="am-u-sm-8">
											<input type="text" id="kpxe3" name="kpxe3"
												style="float: left;" placeholder="开票限额"
												value=""
												pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
												class="am-form-field"/>
										</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfphm" class="am-u-sm-4 am-form-label">电子发票分票金额</label>
										<div class="am-u-sm-8">
											<input type="text" id="fpje3" name="fpje3"
												style="float: left;" placeholder="分票金额"
												value=""
												pattern="^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$"
												class="am-form-field"/>
										</div>
								</div>
								<div class="am-form-group">
									<label for="kpdmc" class="am-u-sm-4 am-form-label">开票点品牌</label>
									<div class="am-u-sm-8">
										<select id="pid" name="pid" required>
											<option value="0">请选择</option>
											<c:forEach items="${pps}" var="item">
												<option value="${item.id}">${item.ppmc}(${item.ppdm})</option>
											</c:forEach>
										</select>
									</div>
								</div>	
							</div>
 						</div>
						<div class="am-u-sm-12">
							<div class="am-form-group">
								<div class="am-u-sm-12  am-text-center">
									<button type="submit" class="js-submit  am-btn am-radius am-btn-success">保存</button>
									<button type="button" class="js-close  am-btn am-radius am-btn-warning"
										onclick="">取消</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal1">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">
					修改设备 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<hr />
					<form action="sksbxxzc/edit" method="POST"
						class="js-form1  am-form am-form-horizontal">
						<div class="am-g">
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<label for="skph1" class="am-u-sm-3 am-form-label">税控设备号</label>
									<div class="am-u-sm-9">
										<input type="text" id="skph1" name="skph1"
											placeholder="请输入税控设备号" readonly /> <input type="hidden"
											name="id" placeholder="" />
									</div>
								</div>

								<div class="am-form-group">
									<label for="xfmc1" class="am-u-sm-3 am-form-label">销方名称</label>
									<div class="am-u-sm-9">
										<input type="text" id="xfmc1" name="xfmc1"
											placeholder="请输入销方名称" readonly />
									</div>
								</div>

								<div class="am-form-group">
									<label for="fpyz1" class="am-u-sm-3 am-form-label">阈值设置</label>
									<div class="am-u-sm-9">
										<input type="text" id="fpyz1" pattern="^[0-9]*[1-9][0-9]*$"
											name="fpyz1" placeholder="请输入阈值(需为整数)" required />
									</div>
								</div>

								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">设备厂商</label>
									<div class="am-u-sm-8">
										<select id="sbcs1" name="sbcs1">
											<option value="1">百旺(黑色)</option>
											<option value="2">航行(白色)</option>
										</select>
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">税控盘号</label>
									<div class="am-u-sm-8">
										<input type="text" id="skph1" name="skph1"
											placeholder="请输入税控盘号" />
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">税控盘密码</label>
									<div class="am-u-sm-8">
										<input type="password" id="skpmm1" name="skpmm1" style="float: left;"
											placeholder="税控盘密码" class="am-form-field"
											maxlength="30" />
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">证书密码</label>
									<div class="am-u-sm-8">
										<input type="password" id="zsmm1" name="zsmm1" style="float: left;"
											placeholder="证书密码" class="am-form-field"
											maxlength="30" />
									</div>
								</div>															
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">联系地址</label>
									<div class="am-u-sm-8">
											<input type="text" id="lxdz1" name="lxdz1" style="float: left;"
												placeholder="联系地址" value="${xf.xfdz }" class="am-form-field"
												/>
									
									</div>
								</div>														
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">联系电话</label>
									<div class="am-u-sm-8">
										<input type="text" id="lxdh1" name="lxdh1" value="${xf.xfdh }" style="float: left;"
											placeholder="联系电话" class="am-form-field"
											/>
									</div>
								</div>													
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">开户银行</label>
									<div class="am-u-sm-8">
										<input type="text" id="khyh1" name="khyh1" style="float: left;"
											placeholder="开户银行" value="${xf.xfyh }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">银行账号</label>
									<div class="am-u-sm-8">
										<input type="text" id="yhzh1" name="yhzh1" style="float: left;"
											placeholder="银行账号" value="${xf.xfyhzh }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">收款人</label>
									<div class="am-u-sm-8">
										<input type="text" id="skr1" name="skr1" style="float: left;"
											placeholder="收款人" value="${xf.skr }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">复核人</label>
									<div class="am-u-sm-8">
										<input type="text" id="fhr1" name="fhr1" style="float: left;"
											placeholder="复核人" value="${xf.fhr }" class="am-form-field"
											/>
									</div>
								</div>												
								<div class="am-form-group">
									<label for="bz" class="am-u-sm-4 am-form-label">开票人</label>
									<div class="am-u-sm-8">
										<input type="text" id="kpr1" name="kpr1" style="float: left;"
											placeholder="开票人" value="${xf.kpr }" class="am-form-field"
											/>
									</div>
								</div>
								<div class="am-form-group">
									<label for="kpdmc" class="am-u-sm-4 am-form-label">开票点品牌</label>
									<div class="am-u-sm-8">
										<select id="pid1" name="pid1" required>
											<option value="0">请选择</option>
											<c:forEach items="${pps}" var="item">
												<option value="${item.id}">${item.ppmc}(${item.ppdm})</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<div class="am-u-sm-12  am-text-center">
										<button type="submit"
											class="js-submit1  am-btn am-radius am-btn-success">保存</button>
										<button type="button" class="js-close1  am-btn am-radius am-btn-warning">取消</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="am-modal am-modal-no-btn" tabindex="-1"
			id="bulk-import-div">
			<div class="am-modal-dialog">
				<div class="am-modal-hd am-modal-footer-hd">
					批量导入 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>

				<div class="am-tab-panel am-fade am-in am-active">
					<form class="am-form am-form-horizontal" id="importExcelForm"
						method="post"
						action="<%=request.getContextPath()%>/sksbxxzc/importExcel"
						enctype="multipart/form-data">
						<div class="am-form-group">
							<div class="am-u-sm-12">
								<input type="file" class="am-u-sm-12" id="importFile"
									name="importFile" placeholder="选择要上传的文件" required>
							</div>
							<div class="am-u-sm-12" style="margin-top: 10px;">
								<button type="button" id="btnImport"
									class="am-btn am-radius am-btn-xs am-btn-success">导入</button>
								<button type="button" id="close1"
									class="am-btn am-radius am-btn-warning am-btn-xs">关闭</button>

							</div>
							<div class="am-u-sm-12" style="margin-top: 10px;">
								<a href="javascript:void(0)" id="btnDownloadDefaultTemplate"
									style="text-decoration: underline;">下载默认模板</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- content end -->

		<!-- loading do not delete -->
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

	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<%@ include file="../../pages/foot.jsp"%>


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

	<script src="assets/js/sksbxxzc.js"></script>
	<script src="assets/js/format.js"></script>
	<script>
		$(function() {
			//批量导入
			var $importModal = $("#bulk-import-div");
			$("#kp_dr").click(function() {
				$importModal.modal({
					"width" : 600,
					"height" : 180
				});
			});
			//下载默认导入模板
			$("#btnDownloadDefaultTemplate").click(function() {
				location.href = "sksbxxzc/downloadDefaultImportTemplate";
			});

		});
	</script>
</body>
</html>
