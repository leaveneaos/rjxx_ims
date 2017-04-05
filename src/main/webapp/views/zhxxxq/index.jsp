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
<script src="assets/js/loading.js"></script>
</head>

<body data-type="widgets">
    <script src="assets/js/theme.js"></script>
    <input type="hidden" id="cd1">
     <input type="hidden" id="cd2">
    <div class="am-g tpl-g">
        <!-- 头部 -->
        <header>
            <!-- logo -->
            <div class="am-fl tpl-header-logo">
                <a href="<%=request.getContextPath()%>/main" style="font-weight:900">泰易（TaxEasy）开票通V2.0</a>
            </div>
            <!-- 右侧内容 -->
            <div class="tpl-header-fluid">
                <!-- 侧边切换 -->
                <div onclick="yccd();" class="am-fl tpl-header-switch-button am-icon-list">
                    <span>&thinsp; 
                </span>
                </div>
                
                <!-- 其它功能-->
                <div class="am-fr tpl-header-navbar">
                
                    <ul>
                    <li><a class="am-text-sm" href="qympk" target="_blank" ><span class=""></span> 企业名片库</a></li>
                    <li><a class="am-text-sm" href="khdxz" target="_blank" ><span class="am-icon-download"></span> 客户端下载</a></li>
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
					<li id="zhxx" class="am-u-sm-12"><a href="javascript:zhxx()"
						data-am-modal="{target: '#doc-modal-3', closeViaDimmer: 0, width: 550}"><span
							class="am-icon-user"></span> 账号信息</a></li>
					<li class="am-u-sm-12"><a href="#"
						data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 550}"><span
							class="am-icon-user"></span> 修改用户资料</a></li>
					<li class="am-u-sm-12"><a href="#"
						data-am-modal="{target: '#doc-modal-2', closeViaDimmer: 0, width: 550}"><span
							class="am-icon-cog"></span> 修改密码</a></li>
		<!-- 			<li><a href="javascript:logout()"><span
							class="am-icon-power-off"></span> 退出</a></li> -->
				</ul></li>
			<li class="am-hide-sm-only am-text-sm"><a href="javascript:;"
				id="admin-fullscreen"><span class=" am-icon-arrows-alt"></span> <span
					class="admin-fullText">开启全屏</span></a></li>

                        <!-- 新邮件 -->
                       <li class="am-dropdown tpl-dropdown" data-am-dropdown>
                            <a href="javascript:;" class="am-dropdown-toggle tpl-dropdown-toggle" data-am-dropdown-toggle>
                                <i style="padding-top: 20px;" class="am-icon-envelope"></i>
                                <c:if test="${xxsl !=null }">
								<span class="am-badge am-badge-success am-round item-feed-badge">${xxsl}</span>
							</c:if>
                            </a>
                            弹出列表
                            <ul class="am-dropdown-content tpl-dropdown-content" id="xxul">
                            <c:forEach items="${xxList}" var="var">
                                <li class="tpl-dropdown-menu-messages" id="${var.id}" onclick="look(this)">
                                    <a href="javascript:;" class="tpl-dropdown-menu-messages-item am-cf">
                                        <div class="menu-messages-ico">
                                            <img src="" alt="">
                                        </div>
                                        <div class="menu-messages-time">
                                            
                                        </div>
                                        <div class="menu-messages-content">
                                            <div class="menu-messages-content-title">
                                                <i class="am-icon-circle-o am-text-success"></i>
                                                <span>${var.title}</span>
                                            </div>
                                            <div class="am-text-truncate">${var.content} </div>
                                            <div class="menu-messages-content-time">${var.lrsj}</div>
                                        </div>
                                    </a>
                                </li>
                                </c:forEach>                            
                                <li class="tpl-dropdown-menu-messages">
                                    <a href="javascript:jumpJsp();" class="tpl-dropdown-menu-messages-item am-cf">
                                        <i class="am-icon-circle-o"></i> 进入列表…
                                    </a>
                                </li>
                            </ul>
                        </li> 
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
        <div class="row-content am-cf" >
          <div class="row">
			 <div class="am-u-sm-8 am-u-md-8 am-u-lg-8" style="margin-top:50px; margin-left: 16.7%;" >
				       <div style="border: 1px solid gray;min-height: 1350px;">
					 	     <div style="background-color: #D3D3D3; height: 50px;line-height: 50px;" >
					 	           <input type="hidden" id="nsrsbh">
					 	           <div class="am-u-lg-6" style="float:left;">
					 	     	                 账户详情
					 	           </div>
					 	    </div>
					 	    <div class="am-u-sm-2 am-u-md-2 am-u-lg-2" style="background-color:#00C957; height: 50px;color: white;text-align: center;line-height: 50px;">
								账户类型介绍
						 	</div>
					
						 	<div class="am-u-sm-10 am-u-md-10 am-u-lg-10" style="height: 50px;text-align: left;line-height:50px;border-bottom:1px solid gray;">
						 		账户类型介绍
						 	</div>
						 	<div class="am-u-sm-2 am-u-md-2 am-u-lg-2" style="border-right:1px solid gray;height: 1249px;">
						 	</div>
			 	
						 	<div class="am-u-sm-10 am-u-md-10 am-u-lg-10 .am-u-end">
						 		<ul style="font-size: 13px;color: gray;list-style-type: none;">
						 			<li>1、账户说明如下</li>
						 			<li>&nbsp;&nbsp;泰易的账户共分为3中类型，分别为：免费试用账户、企业账户和集团账户。</li>
						 			<li>免费试用账户：</li>
						 			<li>&nbsp;&nbsp;免费试用账户是容津隆重退出的构建在互联网+的基础上的发票管理系统，跨越了传统开票的地域性限制，同时满足企业批量开具发票发需求，帮助企业更加便捷的开具发票，但是每个税号的试用期只有一年，企业如需继续使用本平台，则必须升级为企业账户或集团账户；
                                    </li>
						 		    <li>企业账户：</li>
						 		    <li>&nbsp;&nbsp;企业账户是提供单税号注册，按年收费，提供纸质发票和电子发票批量导入、开具和打印功能，提高销项发票管理效率。
						 		    </li>
						 		    <li>集团账户：</li>
						 		    <li>&nbsp;&nbsp;集团账户提供多税号管理，用户可自行内部授权，提供集团企业的统计报表功能，按年收费。</li>
						 		    <li></li>
						 		    <li>&nbsp;&nbsp;如需详细了解账户信息，请咨询：021-55571833/021-33566700 &nbsp;&nbsp; QQ群：481606445</li>
						 		    <li>2、账户享有的服务</li>
						 		    <li>
						 		       <table style="margin-bottom: 0px;text-align:center;"   class="js-table am-table am-table-bordered am-table-hover">
						 		          <tr>
							 		           <th style="text-align: center;" >账户类型</th>
							 		           <th style="text-align: center;">免费试用账户</th>
							 		           <th style="text-align: center;">企业账户</th>
							 		           <th style="text-align: center;">集团账户</th>
						 		          </tr>
						 		          <tr>
						 		               <td>有效期</td>
						 		               <td>3个月</td>
						 		               <td>一年</td>
						 		               <td>一年</td>
						 		          </tr>
						 		           <tr>
						 		               <td>年用票量</td>
						 		               <td>1000张/年</td>
						 		               <td>2-10万张/年</td>
						 		               <td>60万张/年</td>
						 		          </tr> <tr>
						 		               <td>应用税号</td>
						 		               <td>一个</td>
						 		               <td>一个</td>
						 		               <td>多个</td>
						 		          </tr> <tr>
						 		               <td>应用税控设备</td>
						 		               <td>一个</td>
						 		               <td>多个</td>
						 		               <td>多个</td>
						 		          </tr> <tr>
						 		               <td>开票点接入（纸质发票和电子发票打印）</td>
						 		               <td>有</td>
						 		               <td>有</td>
						 		               <td>有</td>
						 		          </tr> <tr>
						 		               <td>安装服务</td>
						 		               <td>无</td>
						 		               <td>可选</td>
						 		               <td>有</td>
						 		          </tr> <tr>
						 		               <td>与企业ERP系统对接</td>
						 		               <td>提供标准接口</td>
						 		               <td>有</td>
						 		               <td>有</td>
						 		          </tr> <tr>
						 		               <td>税控设备授权（发票打印更加快捷、性能稳定）</td>
						 		               <td>无</td>
						 		               <td>可选</td>
						 		               <td>可选</td>
						 		          </tr> <tr>
						 		               <td>技术支持（5*8小时）</td>
						 		               <td>有</td>
						 		               <td>有</td>
						 		               <td>有</td>
						 		          </tr> <tr>
						 		               <td>现场培训</td>
						 		               <td>无</td>
						 		               <td>有</td>
						 		               <td>有</td>
						 		          </tr> <tr>
						 		               <td>短信包</td>
						 		               <td>可选</td>
						 		               <td>可选</td>
						 		               <td>可选</td>
						 		          </tr> <tr>
						 		               <td>软件功能</td>
						 		               <td>基础信息、业务处理、查询统计、发票库存、订阅管理、系统管理、设置</td>
						 		               <td>基础信息、业务处理、查询统计、发票库存、订阅管理、系统管理、设置</td>
						 		               <td>基础信息、业务处理、查询统计、发票库存、订阅管理、系统管理、设置<br/>
													1、	开票单移动端、外部接入；<br/>
													2、	统计分析用户、税号和开票点属性<br/>
													3、	根据企业需求进行个性化报表定制<br/>
													4、	自定义订阅内容<br/>
													5、	多用户多角色管理
						 		               </td>
						 		          </tr>
						 		       </table>
						 		    
						 		    </li>
						 		</ul>
						 		
						 	</div>
				 	  </div>
			    </div>
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
        
        <footer>
                <p class="am-text-center">© Copyright 2014-2017 上海容津信息技术有限公司 沪ICP备15020560号</p>
        </footer>
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
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="x_div">
		<div class="am-modal-dialog">
			<form class="js-form-0 am-form am-form-horizontal">
				<div class="am-modal-bd">
					<div class="am-g">
						<div class="am-form-group">							
							<div class="am-u-sm-12">
								<label id="x_title" class="am-u-sm-12"></label>
							</div>
						</div>
						<br>
						<div class="am-form-group">
							<div class="am-u-sm-12">
								<div id="x_content"></div>
							</div>
						</div>
						<br><br>
						<div class="am-form-group">
							<div class="am-u-sm-12">
								<div class="am-u-sm-5" id="x_lrsj"></div>
								<div class="am-u-sm-3"></div>
								<div class="am-u-sm-4">
								     <button type="button" onclick="closeModel()" class="am-btn am-btn-warning">确定</button>
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
								<label id="yhlx" style="line-height: 44px;">
									<c:if test="${login_session_key.zhlxdm == '01'}">集团账户</c:if>
									<c:if test="${login_session_key.zhlxdm == '02'}">代理账户</c:if>
									<c:if test="${login_session_key.zhlxdm == '03'}">企业账户</c:if>
								</label>
							</div>
						</div>
						<div class="am-form-group" style="border-bottom: solid 0px #CCC; height: 35px;">
							<label for="hc_kpje" class="am-u-sm-4 am-form-label"><font color="#AAAAAA">账户有效期</font></label>
							<div class="am-u-sm-8">
								<label id="yhyxq" style="line-height: 44px;"></label>
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
					<%-- 	<div class="am-form-group" style="height: 30px;">
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
						</div> --%>
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
     <script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
    <script src="assets/js/amazeui.datatables.min.js"></script>
   
    <script src="assets/js/app.js"></script>
    <script src="assets/js/qympk.js"></script>
    <script language="javascript" type="text/javascript"> 
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
    function jznr(th,bt){
    	//获取点击菜单的路劲
    	var v_id = $(th).attr('data');
    	var ejcd = $(th).attr('dele');
     	 $(".ejcd").css('background','none')
     	$(th).css("background-color","#f2f6f9");
     	 $("#cd1").val(ejcd);
     	 $("#cd2").val(bt.id);
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
			onCancel : function() {

			}
		});
	}
	function look(da){
		var id = $(da).attr("id");
		$.ajax({
			url:'main/getXxmx',
			data:{"id":id},
			success:function(data){
				$("#x_title").html(data.title);
				$("#x_content").html(data.content);
				$("#x_lrsj").html(data.lrsj);
				$("#x_div").modal("open");
			}
		})
	}
	function closeModel(){
		$("#x_div").modal("close");
		window.location.reload();
	}
	
	function jumpJsp(){
		$('#xxul').toggle();
		$("#mainFrame").attr("src","<%=request.getContextPath()%>/xtxx");
		
	}

	
</script> 

</body>
</html>