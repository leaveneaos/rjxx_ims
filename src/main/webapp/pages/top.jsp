<%@ page import="com.rjxx.taxeasy.security.SecurityContextUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	request.setAttribute("login_session_key", SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal());
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header class="am-topbar admin-header">
	
    <script src="assets/js/echarts.min.js"></script>
    <link rel="stylesheet" href="assets/css/amazeui.css" />
    <link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
    <link rel="stylesheet" href="assets/css/app.css">
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/theme.js"></script>
	 <div class="am-fl tpl-header-logo">
                <a href="javascript:;" style="font-weight:900">泰易（TaxEasy）开票通V2.0</a>
            </div>
	<button
		class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
		data-am-collapse="{target: '#topbar-collapse'}">
		<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
	</button>

	<div class="am-fr tpl-header-navbar">
                    <ul>
                        <!-- 欢迎语 -->
                       <%--  <li class="am-text-sm tpl-header-navbar-welcome">
                            <a href="javascript:;"><span>${login_session_key.yhmc} ,你好!</span> </a>
                        </li> --%>
                        <li class="am-dropdown" data-am-dropdown><a
				class="am-text-sm am-dropdown-toggle" data-am-dropdown-toggle
				href="javascript:;"> <span class="am-icon-users"></span>
					${login_session_key.yhmc} ,你好! <span class="am-icon-caret-down"></span>
			</a>
				<ul class="am-dropdown-content">
					<li id="zhxx"><a href="javascript:zhxx()"
						data-am-modal="{target: '#doc-modal-3', closeViaDimmer: 0, width: 550}"><span
							class="am-icon-user"></span> 账号信息</a></li>
					<li><a href="#"
						data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 550}"><span
							class="am-icon-user"></span> 修改用户资料</a></li>
					<li><a href="#"
						data-am-modal="{target: '#doc-modal-2', closeViaDimmer: 0, width: 550}"><span
							class="am-icon-user"></span> 修改密码</a></li>
				</ul></li>
			<li class="am-hide-sm-only am-text-sm"><a href="javascript:;"
				id="admin-fullscreen"><span class=" am-icon-arrows-alt"></span> <span
					class="admin-fullText">开启全屏</span></a></li>
					<li class="am-text-sm">
                            <a href="javascript:logout()">
                                <span class="am-icon-sign-out"></span> 退出
                            </a>
                        </li>
	</ul>
	</div>
	<%-- 	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				修改用户资料 <a href="javascript: void(0)" class="am-close am-close-spin"
					data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<table style="margin: 10px; padding: 20px; border: 20px;">
					<tr>
						<td>用户名称：</td>
						<td><input id="yhmc" value="${login_session_key.yhmc}"
							type="text" style="width: 250px;" placeholder="请输入用户名称"></td>
					</tr>
					<tr>
						<td>用户性别：</td>
						<td><select id="xb" style="width: 250px;">
								<option value="1">男</option>
								<option value="0">女</option>
						</select></td>
					</tr>
					<tr>
						<td>手机号：</td>
						<td><input id="sjhm" value="${login_session_key.sjhm}"
							type="text" style="width: 250px;" placeholder="请输入手机号"></td>
					</tr>
					<tr>
						<td>用户邮箱：</td>
						<td><input id="yx" value="${login_session_key.yx}"
							type="email" style="width: 250px;" placeholder="请输入邮箱"></td>
					</tr>
				</table>
				<button id="btnSaveUserInfo" type="button"
					class="am-btn am-btn-primary" onclick="save()">保存</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btnCancelUserInfo" type="button"
					class="am-btn am-btn-primary"
					onclick="$('#doc-modal-1').modal('close');">取消</button>
			</div>
		</div>
	</div> --%>
	
</header>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
		<div class="am-modal-dialog">
			<form class="js-form-0 am-form am-form-horizontal">
				<div class="am-modal-hd">
					修改用户资料 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<div class="am-modal-bd" style="overflow: auto;">
						<div class="am-g">
							<div class="am-u-sm-12">
								<div class="am-form-group">
									<label for="hc_kpje" class="am-u-sm-3 am-form-label">用户名称</label>
									<div class="am-u-sm-8">
										<input type="text" id="yhmc" value="${login_session_key.yhmc}"
											class="am-form-field" required maxlength="50" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_yfpdm" class="am-u-sm-3 am-form-label">选择性别</label>
									<div class="am-u-sm-8">
										<c:if test="${login_session_key.xb == 1}">
											<select id="xb" name="xb" class="am-field-valid">
												<option value="0" selected="selected">男</option>
												<option value="1">女</option>
											</select>
										</c:if>
										<c:if test="${login_session_key.xb == 0}">
											<select id="xb" name="xb" class="am-field-valid">
												<option value="0">男</option>
												<option value="1" selected="selected">女</option>
											</select>
										</c:if>
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_kpje" class="am-u-sm-3 am-form-label">手机号</label>
									<div class="am-u-sm-8">
										<input type="text" id="sjhm" value="${login_session_key.sjhm}"
											placeholder="请输入手机号" class="am-form-field" maxlength="50" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="hc_kpje" class="am-u-sm-3 am-form-label">用户邮箱</label>
									<div class="am-u-sm-8">
										<input type="email" id="yx" value="${login_session_key.yx}"
											placeholder="请输入用户邮箱" class="am-form-field" maxlength="100" />
									</div>
								</div>
								<div class="am-u-sm-12 am-margin-top-lg">
									<div class="am-form-group">
										<div class="am-u-sm-12  am-text-center">
											<button id="btnSaveUserInfo" type="button"
												class="am-btn am-btn-primary" data-am-modal-close
												onclick="save()">保存</button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button id="btnCancelUserInfo" type="button"
												class="am-btn am-btn-primary" data-am-modal-close>取消</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-2">
		<div class="am-modal-dialog">
			<form class="js-form-0 am-form am-form-horizontal">
				<div class="am-modal-hd">
					修改用户密码 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<div class="am-g">
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label">原密码</label>
							<div class="am-u-sm-8">
								<input type="password" id="oldPass" name="yhmm2" 	placeholder="请输入原密码"
									class="am-form-field" required maxlength="50" />
							</div>
						</div>
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label">新密码</label>
							<div class="am-u-sm-8">
								<input type="password" id="newPass" name="yhmm2"
									placeholder="大,小写字母,数字,符号中三种,最少8位" class="am-form-field"
									required
									pattern="^(?![0-9a-z]+$)(?![0-9A-Z]+$)(?![0-9\W]+$)(?![a-z\W]+$)(?![a-zA-Z]+$)(?![A-Z\W]+$)[a-zA-Z0-9\W_]+$"
									minlength="8" maxlength="50" />
							</div>
						</div>
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label">确认密码</label>
							<div class="am-u-sm-8">
								<input type="password" id="newPass1" name="qrmm1"
									placeholder="请与上面输入的值一致" data-equal-to="#yhmm2"
									class="am-form-field" required maxlength="50" />
							</div>
						</div>
						<div class="am-u-sm-12 am-margin-top-lg">
							<div class="am-form-group">
								<div class="am-u-sm-12  am-text-center">
									<button id="btnUpdatePassword" type="button"
									data-am-modal-close	class="am-btn am-btn-primary" onclick="updatePass()">保存</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<button type="button" class="am-btn am-btn-primary"
										data-am-modal-close>取消</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-3">
		<div class="am-modal-dialog">
			<form class="js-form-0 am-form am-form-horizontal">
				<div class="am-modal-hd">
					账户资料 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<div class="am-g"">
						<div class="am-form-group" style="height: 35px;">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label"><font color="#AAAAAA">账户类型</font></label>
							<div class="am-u-sm-8">
								<label id="yhlx" style="line-height: 44px">
									<c:if test="${login_session_key.zhlxdm == '01'}">集团账户</c:if>
									<c:if test="${login_session_key.zhlxdm == '02'}">代理账户</c:if>
									<c:if test="${login_session_key.zhlxdm == '03'}">企业账户</c:if>
								</label>
							</div>
						</div>
						<div class="am-form-group" style=" height: 35px;">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label"><font color="#AAAAAA">账户有效期</font></label>
							<div class="am-u-sm-8">
								<label id="yhyxq" style="line-height: 44px"></label>
							</div>
						</div>
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label"><font color="#AAAAAA">授权信息</font></label>
							<label class="am-u-sm-8 am-form-label"></label>
						</div>
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-3 am-form-label"></label>
							<label for="hc_kpje" class="am-u-sm-3">&nbsp;&nbsp;&nbsp;&nbsp;税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
							<label id="shsl" class="am-u-sm-6"></label>
						</div>
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-3 am-form-label"></label>
							<label for="hc_kpje" class="am-u-sm-3">&nbsp;&nbsp;&nbsp;&nbsp;税控设备：</label>
							<label id="sksb" class="am-u-sm-6"></label>
						</div>
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-3 am-form-label"></label>
							<label for="hc_kpje" class="am-u-sm-3">&nbsp;&nbsp;&nbsp;&nbsp;用户数量：</label>
							<label id="yhsl" class="am-u-sm-6"></label>
						</div>
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-3 am-form-label"></label>
							<label for="hc_kpje" class="am-u-sm-3">&nbsp;&nbsp;&nbsp;&nbsp;开票数量：</label>
							<label id="kpsl" class="am-u-sm-6"></label>
						</div>
						
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-bd">您确定要退出吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-confirm>确定</span> <span
					class="am-modal-btn" data-am-modal-cancel>取消</span>
			</div>
		</div>
	</div>
<script type="text/javascript">
	$(function(){

        $('.left-sidebar').addClass('active');
        if ($(window).width() > 1024) {
            $('.tpl-content-wrapper').addClass('active');
            $('.tpl-content-wrapper').css("margin-left","0");
        }
	});
	function zhxx(){
		$.ajax({
			url : "nyhgl/getGsxx",
			method : 'POST',
			success : function(data) {
				if (data.success) {
					$('#yhyxq').html(data.gsxx.qsrq + "-" +data.gsxx.jzrq);
					$('#shsl').html(data.gsxx.xfnum);
					$('#sksb').html(data.gsxx.kpdnum);
					$('#yhsl').html(data.gsxx.yhnum);
					$('#kpsl').html(data.gsxx.kpnum);
					$('#yhlx').html(data.yh.zhlxdm);
					$('#yhzh').html(data.yh.dlyhid);
				} else {
					alert('后台错误: 数据修改失败' + data.msg);
				}
			},
			error : function() {
				alert('数据修改失败, 请重新登陆再试...!');
			}
		});
	}
	
	function save() {
		var yhmc = $('#yhmc').val();// 用户名称
		var xb = $('#xb').val(); // 性别
		var sjhm = $('#sjhm').val(); // 性别
		var yx = $('#yx').val(); // 性别
		var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
		if(null==yhmc||""==yhmc){
			alert("用户名称不能为空!");
			return;
		}else if(!reg.test(yx)){
			alert("邮箱格式不对!");
			return;
		}
		$.ajax({
			url : 'nyhgl/update',
			data : {
				yhmc : yhmc,
				xb : xb,
				sjhm : sjhm,
				yx : yx
			},
			method : 'POST',
			success : function(data) {
				if (data.success) {
					$('#doc-modal-1').modal('close');
					alert(data.msg);
				} else {
					alert('后台错误: 数据修改失败' + data.msg);
				}
			},
			error : function() {
				alert('数据修改失败, 请重新登陆再试...!');
			}
		});
	}

	function updatePass() {
		var oldPass = $('#oldPass').val();// 原密码
		var newPass = $('#newPass').val(); // 新密码
		var newPass1 = $('#newPass1').val(); // 确认密码
		if (oldPass == null || oldPass == "") {
			alert('请输入原密码');
			return;
		}
		if (newPass == null || newPass == "") {
			alert('请输入新密码');
			return;
		}
		if (newPass1 == null || newPass1 == "") {
			alert('请输入确认密码');
			return;
		}
		if (newPass == oldPass) {
			alert('新密码与原密码一致，请重新输入');
			return;
		}
		if (newPass != newPass1) {
			alert('两次密码输入不一致，请重新输入');
			return;
		}
		$.ajax({
			url : 'nyhgl/updatePwd',
			data : {
				oldPass : oldPass,
				newPass : newPass
			},
			type : 'POST',
			success : function(data) {
				if (data.success) {
					$('#doc-modal-2').modal('close');
					alert(data.msg);
					window.location.href = "<c:url value='/login/logout'/>";
				} else if (data.nosame) {
					alert('原密码不正确，请重新输入');
				} else {
					alert('后台错误: 数据修改失败' + data.msg);
				}
			},
			error : function() {
				alert('数据修改失败, 请重新登陆再试...!');
			}
		});
	}

	function logout() {
		$('#my-confirm').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				window.location.href = "<c:url value='/login/logout'/>";
			},
			// closeOnConfirm: false,
			onCancel : function() {

			}
		});
		// 		if (confirm('是否退出？')) {
		// 			
		// 		}
	}
</script>
