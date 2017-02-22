<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>首页</title>
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
                <a href="javascript:;" style="font-weight:900">泰易（TaxEasy）电子发票v2.0</a>
            </div>
            <!-- 右侧内容 -->
            <div class="tpl-header-fluid">
                <!-- 侧边切换 -->
                <div  onclick="yccd();" class="am-fl tpl-header-switch-button am-icon-list">
                    <span>
                </span>
                </div>
                
                <!-- 其它功能-->
                <div class="am-fr tpl-header-navbar">
                    <ul>
                        <!-- 欢迎语 -->
                        <li class="am-text-sm tpl-header-navbar-welcome">
                            <a href="javascript:;">欢迎你, <span>管理员</span> </a>
                        </li>

                        <!-- 新邮件 -->
                        <li class="am-dropdown tpl-dropdown" data-am-dropdown>
                            <a href="javascript:;" class="am-dropdown-toggle tpl-dropdown-toggle" data-am-dropdown-toggle>
                                <i class="am-icon-envelope"></i>
                                <span class="am-badge am-badge-success am-round item-feed-badge">4</span>
                            </a>
                            <!-- 弹出列表 -->
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
                        </li>

                        <!-- 新提示 -->
                        <li class="am-dropdown" data-am-dropdown>
                            <a href="javascript:;" class="am-dropdown-toggle" data-am-dropdown-toggle>
                                <i class="am-icon-bell"></i>
                                <span class="am-badge am-badge-warning am-round item-feed-badge">5</span>
                            </a>

                            <!-- 弹出列表 -->
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
                        </li>

                        <!-- 退出 -->
                        <li class="am-text-sm">
                            <a href="javascript:;">
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
                    <a href="#" class="sub-active">
                        <i class="am-icon-home sidebar-nav-link-logo"></i> 首页
                    </a>
                </li>

                <li class="sidebar-nav-link">
                    <a href="#" class="sidebar-nav-sub-title active">
                        <i class="am-icon-table sidebar-nav-link-logo"></i> 基础数据
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico sidebar-nav-sub-ico-rotate"></span>
                    </a>
                    <ul class="sidebar-nav sidebar-nav-sub" style="display: block;">
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 销方信息维护
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 税控设备注册
                            </a>
                        </li>

                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 商品税率管理
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="sidebar-nav-link">
                    <a href="#" class="sidebar-nav-sub-title active">
                        <i class="am-icon-table sidebar-nav-link-logo"></i> 业务处理
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico sidebar-nav-sub-ico-rotate"></span>
                    </a>
                    <ul class="sidebar-nav sidebar-nav-sub" style="display: block;">
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票开具
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票红冲
                            </a>
                        </li>

                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票换开
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票发送
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 异常处理
                            </a>
                        </li>
                    </ul>
                </li>
				<li class="sidebar-nav-link">
                    <a href="#" class="sidebar-nav-sub-title active">
                        <i class="am-icon-table sidebar-nav-link-logo"></i> 业务处理
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico sidebar-nav-sub-ico-rotate"></span>
                    </a>
                    <ul class="sidebar-nav sidebar-nav-sub" style="display: block;">
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票开具
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票红冲
                            </a>
                        </li>

                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票换开
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票发送
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 异常处理
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="sidebar-nav-link">
                    <a href="#" class="sidebar-nav-sub-title active">
                        <i class="am-icon-table sidebar-nav-link-logo"></i> 业务处理
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico sidebar-nav-sub-ico-rotate"></span>
                    </a>
                    <ul class="sidebar-nav sidebar-nav-sub" style="display: block;">
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票开具
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票红冲
                            </a>
                        </li>

                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票换开
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票发送
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 异常处理
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="sidebar-nav-link">
                    <a href="#" class="sidebar-nav-sub-title active">
                        <i class="am-icon-table sidebar-nav-link-logo"></i> 业务处理
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico sidebar-nav-sub-ico-rotate"></span>
                    </a>
                    <ul class="sidebar-nav sidebar-nav-sub" style="display: block;">
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票开具
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票红冲
                            </a>
                        </li>

                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票换开
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 发票发送
                            </a>
                        </li>
                        <li class="sidebar-nav-link">
                            <a href="#">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 异常处理
                            </a>
                        </li>
                    </ul>
                </li>
                

            </ul>
        </div>

        <div  class="tpl-content-wrapper">
        <!-- 内容区域 -->
            <iframe id="mainFrame" src="./right.html" frameborder="0" style="min-height:900px;width:100%" onload="javascript:dyniframesize('mainFrame');"></iframe>
        </div>
        <footer>
                <p class="am-text-center">© Copyright 2014-2017 上海容津信息技术有限公司 沪ICP备15020560号</p>
        </footer>
    </div>
    </div>
    <script src="assets/js/amazeui.min.js"></script>
    <script src="assets/js/amazeui.datatables.min.js"></script>
    <script src="assets/js/dataTables.responsive.min.js"></script>
    <script src="assets/js/app.js"></script>
    <script language="javascript" type="text/javascript"> 
    function dyniframesize(down) { 
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
            if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
            //ns6 syntax 
                pTar.height = pTar.contentDocument.body.offsetHeight +20; 
                pTar.width = pTar.contentDocument.body.scrollWidth+20; 
            } else if (pTar.document && pTar.document.body.scrollHeight){ 
            //ie5+ syntax 
                pTar.height = pTar.document.body.scrollHeight; 
                pTar.width = pTar.document.body.scrollWidth; 
            }
        }
    } 
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
</script> 
</body>
</html>