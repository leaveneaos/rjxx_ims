<%@ page import="com.rjxx.taxeasy.security.SecurityContextUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	request.setAttribute("privilegeTypes",
			SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal().getPrivilegeTypesList());
	request.setAttribute("privileges",
			SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal().getPrivilegesList());
	request.setAttribute("login_session_key", SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal());
%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>泰易（TaxEasy）开票通V2.0</title>
    <meta name="description" content="这是一个 index 页面">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="icon" type="image/png" href="assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <script src="assets/js/echarts.min.js"></script>
    <link rel="stylesheet" href="assets/css/amazeui.css" />
    <link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
    <link rel="stylesheet" href="assets/css/app.css">
    <script src="assets/js/jquery.min.js"></script>

</head>

<body data-type="widgets">
    <script src="assets/js/theme.js"></script>
    <div class="am-g tpl-g">
        <!-- 头部 -->
        <header>
            <!-- logo -->
            <div class="am-fl tpl-header-logo">
                <a href="javascript:;" style="font-weight:900">泰易（TaxEasy）开票通V2.0</a>
            </div>
            <!-- 右侧内容 -->
            <div class="tpl-header-fluid">
                <!-- 侧边切换 -->
                <div style="padding: 16px 22px;" onclick="yccd();" class="am-fl tpl-header-switch-button am-icon-list">
                    <span>
                </span>
                </div>
                
                <!-- 其它功能-->
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
							class="am-icon-user"></span> 修改信息</a></li>
					<li><a href="#"
						data-am-modal="{target: '#doc-modal-2', closeViaDimmer: 0, width: 550}"><span
							class="am-icon-cog"></span> 修改密码</a></li>
					<li><a href="javascript:logout()"><span
							class="am-icon-power-off"></span> 退出</a></li>
				</ul></li>
			<li class="am-hide-sm-only am-text-sm"><a href="javascript:;"
				id="admin-fullscreen"><span class=" am-icon-arrows-alt"></span> <span
					class="admin-fullText">开启全屏</span></a></li>

                        <!-- 新邮件 -->
                       <!--  <li class="am-dropdown tpl-dropdown" data-am-dropdown>
                            <a href="javascript:;" class="am-dropdown-toggle tpl-dropdown-toggle" data-am-dropdown-toggle>
                                <i style="padding-top: 20px;" class="am-icon-envelope"></i>
                                <span  class="am-badge am-badge-success am-round item-feed-badge">4</span>
                            </a>
                            弹出列表
                            <ul class="am-dropdown-content tpl-dropdown-content">
                                <li class="tpl-dropdown-menu-messages">
                                    <a href="javascript:;" class="tpl-dropdown-menu-messages-item am-cf">
                                        <div class="menu-messages-ico">
                                            <img src="" alt="">
                                        </div>
                                        <div class="menu-messages-time">
                                            3小时前
                                        </div>
                                        <div class="menu-messages-content">
                                            <div class="menu-messages-content-title">
                                                <i class="am-icon-circle-o am-text-success"></i>
                                                <span>中原地产</span>
                                            </div>
                                            <div class="am-text-truncate"> 中原地产发来的消息xxxxxxxx。 </div>
                                            <div class="menu-messages-content-time">2017-01-18 下午 16:40</div>
                                        </div>
                                    </a>
                                </li>

                                <li class="tpl-dropdown-menu-messages">
                                    <a href="javascript:;" class="tpl-dropdown-menu-messages-item am-cf">
                                        <div class="menu-messages-ico">
                                            <img src="" alt="">
                                        </div>
                                        <div class="menu-messages-time">
                                            5天前
                                        </div>
                                        <div class="menu-messages-content">
                                            <div class="menu-messages-content-title">
                                                <i class="am-icon-circle-o am-text-warning"></i>
                                                <span>食其家</span>
                                            </div>
                                            <div class="am-text-truncate"> 食其家发来的消息xxxxxxxxxx。 </div>
                                            <div class="menu-messages-content-time">2017-01-13 上午 09:23</div>
                                        </div>
                                    </a>
                                </li>
                                <li class="tpl-dropdown-menu-messages">
                                    <a href="javascript:;" class="tpl-dropdown-menu-messages-item am-cf">
                                        <i class="am-icon-circle-o"></i> 进入列表…
                                    </a>
                                </li>
                            </ul>
                        </li> -->

                        <!-- 新提示 -->
                        <!-- <li class="am-dropdown" data-am-dropdown>
                            <a href="javascript:;" class="am-dropdown-toggle" data-am-dropdown-toggle>
                                <i style="padding-top: 20px;" class="am-icon-bell"></i>
                                <span class="am-badge am-badge-warning am-round item-feed-badge">5</span>
                            </a>

                            弹出列表
                            <ul class="am-dropdown-content tpl-dropdown-content">
                                <li class="tpl-dropdown-menu-notifications">
                                    <a href="javascript:;" class="tpl-dropdown-menu-notifications-item am-cf">
                                        <div class="tpl-dropdown-menu-notifications-title">
                                            <i class="am-icon-line-chart"></i>
                                            <span> 有6笔新的订单</span>
                                        </div>
                                        <div class="tpl-dropdown-menu-notifications-time">
                                            12分钟前
                                        </div>
                                    </a>
                                </li>
                                <li class="tpl-dropdown-menu-notifications">
                                    <a href="javascript:;" class="tpl-dropdown-menu-notifications-item am-cf">
                                        <div class="tpl-dropdown-menu-notifications-title">
                                            <i class="am-icon-star"></i>
                                            <span> 有3个新的管理员</span>
                                        </div>
                                        <div class="tpl-dropdown-menu-notifications-time">
                                            30分钟前
                                        </div>
                                    </a>
                                </li>
                                <li class="tpl-dropdown-menu-notifications">
                                    <a href="javascript:;" class="tpl-dropdown-menu-notifications-item am-cf">
                                        <div class="tpl-dropdown-menu-notifications-title">
                                            <i class="am-icon-folder-o"></i>
                                            <span> 上午开会记录存档</span>
                                        </div>
                                        <div class="tpl-dropdown-menu-notifications-time">
                                            1天前
                                        </div>
                                    </a>
                                </li>


                                <li class="tpl-dropdown-menu-notifications">
                                    <a href="javascript:;" class="tpl-dropdown-menu-notifications-item am-cf">
                                        <i class="am-icon-bell"></i> 进入列表…
                                    </a>
                                </li>
                            </ul>
                        </li> -->

                        <!-- 退出 -->
                        <li class="am-text-sm">
                            <a href="javascript:logout()">
                                <span class="am-icon-sign-out"></span> 退出
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

        </header>
        <!-- 侧边导航栏 -->
        <div class="left-sidebar" style="overflow-y: auto;">
            
            <!-- 菜单 -->
            <ul class="sidebar-nav">
                <li class="sidebar-nav-link">
                    <a href="main" class="sub-active">
                        <i class="am-icon-home sidebar-nav-link-logo"></i> 首页
                    </a>
                </li>
<!--循环大菜单 -->
			<c:forEach items="${privilegeTypes}" varStatus="i" var="privilegeType">
                <li class=" sidebar-nav-link">
                    <a href="#" class="sidebar-nav-sub-title active">
                        <i class="${privilegeType.description } sidebar-nav-link-logo"></i>${privilegeType.name}
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico sidebar-nav-sub-ico-rotate"></span>
                    </a>
                    <!--循环小菜单 -->
                    <ul class="sidebar-nav sidebar-nav-sub" id="${privilegeType.name}" style="display: none;">
                    	<c:forEach items="${privileges}" varStatus="j" var="privilege">
                    	<c:if test="${privilege.privilegetypeid == privilegeType.id}">
                        <li class="sidebar-nav-link">
                            <a class="ejcd" href="javascript:void(0)" data="<%=request.getContextPath()%>${privilege.urls}" onclick="jznr(this)">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> ${privilege.name}
                            </a>
                        </li>
                        </c:if>
                        </c:forEach>
                    </ul>
                </li>

               </c:forEach>
            </ul>
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
        <div  class="tpl-content-wrapper">
        <!-- 内容区域 -->
            <iframe id="mainFrame" src="<%=request.getContextPath()%>/mainjsp" frameborder="0" width="100%" onload="javascript:dyniframesize('mainFrame');"></iframe>
        </div>
        <footer>
                <p class="am-text-center">© Copyright 2014-2017 上海容津信息技术有限公司 沪ICP备15020560号</p>
        </footer>
    </div>
    </div>
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
		<div class="am-modal-dialog">
			<form class="js-form-0 am-form am-form-horizontal">
				<div class="am-modal-hd">
					修改用户资料 <a href="javascript: void(0)" class="am-close am-close-spin"
						data-am-modal-close>&times;</a>
				</div>
				<div class="am-modal-bd">
					<div class="am-modal-bd" style="overflow: auto;" >
					<div class="am-g">
						<div class="am-u-sm-12">
						<div class="am-form-group">
							<label for="hc_kpje" class="am-u-sm-3 am-form-label">用户名称</label>
							<div class="am-u-sm-8">
								<input type="text" id="yhmc"
									value="${login_session_key.yhmc}" class="am-form-field"
									required maxlength="50" />
							</div>
						</div>
						<div class="am-form-group">
							<label for="hc_yfpdm" class="am-u-sm-3 am-form-label">选择性别</label>
							<div class="am-u-sm-8">
								<c:if test="${login_session_key.xb == 1}">
									<select id="xb" name="xb" class="am-field-valid"
										>
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
								<input type="text" id="sjhm"
									value="${login_session_key.sjhm}" placeholder="请输入手机号"
									class="am-form-field" maxlength="50" />
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
										class="am-btn am-btn-primary" data-am-modal-close onclick="save()">保存</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button id="btnCancelUserInfo" type="button"
										class="am-btn am-btn-primary" data-am-modal-close
										>取消</button>
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
					<div class="am-g" style="border: solid 0px #CCC;">
						<div class="am-form-group" style="border-bottom: solid 0px #CCC; height: 35px;">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label"><font color="#AAAAAA">账户类型</font></label>
							<div class="am-u-sm-8">
								<label id="yhlx">
									<c:if test="${login_session_key.zhlxdm == '01'}">集团账户</c:if>
									<c:if test="${login_session_key.zhlxdm == '02'}">代理账户</c:if>
									<c:if test="${login_session_key.zhlxdm == '03'}">企业账户</c:if>
								</label>
							</div>
						</div>
						<div class="am-form-group" style="border-bottom: solid 0px #CCC; height: 35px;">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label"><font color="#AAAAAA">账户有效期</font></label>
							<div class="am-u-sm-8">
								<label id="yhyxq"></label>
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
						<div class="am-form-group" style="border-bottom: solid ppx #CCC;">
							<label for="hc_kpje" class="am-u-sm-3 am-form-label"></label>
							<label for="hc_kpje" class="am-u-sm-3">&nbsp;&nbsp;&nbsp;&nbsp;开票数量：</label>
							<label id="kpsl" class="am-u-sm-6"></label>
						</div>
						<div class="am-form-group" style="height: 30px;">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label"><font color="#AAAAAA">登录账号</font></label>
							<div class="am-u-sm-8" style="margin-top: 8px">
								<div style="float: left;">
									<label id="yhzh">${login_session_key.dlyhid}</label>
								</div>
								<div style="float: right">
									<font color="blue"><a href="#"
						data-am-modal="{target: '#doc-modal-2', closeViaDimmer: 0, width: 550}" style="color: blue" class="am-modal-btn" data-am-modal-cancel>修改</a></font>
								</div>
							</div>
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
    <script src="assets/js/amazeui.min.js"></script>
    <script src="assets/js/amazeui.datatables.min.js"></script>
    <script src="assets/js/dataTables.responsive.min.js"></script>
    <script src="assets/js/app.js"></script>
    <script language="javascript" type="text/javascript"> 
    $(function(){
    	$("#业务处理").css('display','block'); 
    })
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
    function dyniframesize(down) {
    	if(!down){
    		down="mainFrame";
    	}
        var pTar = null; 
        if (document.getElementById){ 
            pTar = document.getElementById(down); 
        } 
        else{ 
            eval('pTar = ' + down + ';'); 
        } 
        if (pTar && !window.opera){
        //begin resizing iframe 
            pTar.style.display="block";
            if (pTar.contentDocument && pTar.contentDocument.body && pTar.contentDocument.body.offsetHeight){ 
            //ns6 syntax 
            //console.log(pTar.contentDocument.body.offsetHeight);
                pTar.height = pTar.contentDocument.body.offsetHeight + 50; 
                //pTar.width = pTar.contentDocument.body.scrollWidth+20; 
            } else if (pTar.document && pTar.document.body.scrollHeight){ 
            //ie5+ syntax 
                pTar.height = pTar.document.body.scrollHeight + 50; 
                //pTar.width = pTar.document.body.scrollWidth; 
            }
        }
    }
    self.setInterval(dyniframesize,200);//0.2秒刷新一次
    function yccd(){
        if ($('.left-sidebar').is('.active')) {
            if ($(window).width() > 1024) {
                $('.tpl-content-wrapper').removeClass('active');
                $('.tpl-content-wrapper').css("margin-left","240px");
            }
            $('.left-sidebar').removeClass('active');

        } else {

            $('.left-sidebar').addClass('active');
            if ($(window).width() > 1024) {
                $('.tpl-content-wrapper').addClass('active');
                $('.tpl-content-wrapper').css("margin-left","0");
            }
            
        }
    }
    function jznr(th){
    	//获取点击菜单的路劲
    	var v_id = $(th).attr('data');
     	 $(".ejcd").css('background','none')
     	$(th).css("background-color","#f2f6f9");
    	$("#mainFrame").attr("src",v_id); 
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

</body>
</html>