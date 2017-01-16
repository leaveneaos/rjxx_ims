<%--
  Created by IntelliJ IDEA.
  User: guoyan
  Date: 2015/12/12
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>后台管理首页</title>
    <meta name="description" content="用户管理">
    <meta name="keywords" content="用户管理">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath()%>/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/admin.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/amazeui.tree.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"/>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar admin-header">
    <div class="am-topbar-brand">
        <strong>泰易（TaxEasy）电子发票</strong>
        <small>v1.0</small>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
            data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <!--<li><a href="javascript:;"><span class="am-icon-envelope-o"></span> 收件箱 <span-->
                    <!--class="am-badge am-badge-warning">5</span></a></li>-->
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    <span class="am-icon-users"></span> 管理员 <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
                    <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
                    <!--<li><a href="#"><span class="am-icon-power-off"></span> </a></li>-->
                </ul>
            </li>
            <li><a href="<c:url value="/login/logout"/>"><span class="am-icon-power-off"></span>退出</a></li>
            <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span
                    class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
        </ul>
    </div>
</header>

<div class="am-cf admin-main">
    <!-- sidebar start -->
    <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
        <div class="am-offcanvas-bar admin-offcanvas-bar">
            <ul class="am-list admin-sidebar-list" >
                <li><a href="<%=request.getContextPath()%>/views/login/index.jsp"><span class="am-icon-home"></span> 首页</a></li>

                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#collapse-nav3'}"><span class="am-icon-inbox"></span> 基础数据
                        <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                    <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav3">
                        <li><a href="<%=request.getContextPath()%>/uc_01_qykpxxwh.jsp"><span class="am-icon-keyboard-o"></span> 销方信息维护</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_02_sksbxxzc.jsp"><span class="am-icon-magic"></span> 税控设备注册</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_03_spslgl.jsp"><span class="am-icon-magnet"></span> 商品税率管理</a></li>
                    </ul>
                </li>

                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span class="am-icon-flag"></span> 业务处理
                        <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                    <ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav">
                        <li><a href="kp"><span class="am-icon-shield"></span> 发票开具</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_05_ykfphc.jsp"><span class="am-icon-shield"></span> 发票红冲</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_06_fphk.jsp"><span class="am-icon-ticket"></span> 发票换开</a></li>
                        <li><a href="yjfs"><span class="am-icon-paper-plane"></span> 发票发送</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_08_%20yccl.jsp"><span class="am-icon-question-circle"></span> 异常处理</a></li>
                    </ul>
                </li>

                <li class="admin-parent">
                    <a class="am-cf " data-am-collapse="{target: '#collapse-nav1'}"><span class="am-icon-line-chart"></span> 查询统计
                        <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                    <ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav1">
                        <li><a href="<%=request.getContextPath()%>/uc_09_%20fpcx_1.jsp"><span class="am-icon-search"></span> 发票查询</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_09_%20fpcx_3.jsp"><span class="am-icon-search"></span> 红字发票查询</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_09_%20fpcx_4.jsp"><span class="am-icon-search"></span> 换开发票查询</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_10_ybp.jsp"><span class="am-icon-bar-chart"></span> 仪表盘</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_11_frtjbb.jsp"><span class="am-icon-calendar"></span> 分日统计报表</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_12_fytjbb.jsp"><span class="am-icon-line-chart"></span> 分月统计报表</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_13_sybg.jsp"><span class="am-icon-file-text"></span> 使用报告</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_14_bbdy.jsp"><span class="am-icon-pencil"></span> 报表订阅</a></li>
                    </ul>
                </li>

                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#collapse-nav4'}"><span class="am-icon-cogs"></span> 系统管理
                        <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                    <ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav4">
                        <li><a href="<%=request.getContextPath()%>/uc_15_yhgl.jsp"><span class="am-icon-users"></span> 用户管理</a></li>
                        <li><a href="<%=request.getContextPath()%>/uc_16_rzjk.jsp"><span class="am-icon-eye"></span> 日志监控</a></li>
                    </ul>
                </li>
            </ul>

        </div>
    </div>
    <!-- sidebar end -->

    <!-- content start -->
    <div class="admin-content">

        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">首页</strong> / <small>常用模块</small></div>
        </div>
        <hr/>

        <ul class="am-avg-sm-1 am-avg-md-4 am-margin am-padding am-text-center admin-content-list ">
            <li><a href="http://120.26.45.211:8090/dzfp/kp" class="am-text-success"><span class="am-icon-btn am-icon-shield"></span><br>发票开具</a></li>
            <li><a href="<%=request.getContextPath()%>/uc_05_ykfphc.jsp" class="am-text-warning"><span class="am-icon-btn am-icon-shield"></span><br>发票红冲</a></li>
            <li><a href="<%=request.getContextPath()%>/uc_07_fpfs.jsp" class="am-text-danger"><span class="am-icon-btn am-icon-paper-plane"></span><br>发票发送</a></li>
            <li><a href="<%=request.getContextPath()%>/uc_09_%20fpcx_1.jsp" class="am-text-secondary"><span class="am-icon-btn am-icon-search"></span><br>发票查询</a></li>
        </ul>


        <div class="am-g">
            <div class="am-u-md-9">
                <div class="am-panel am-panel-default">
                    <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-1'}">仪表盘<span class="am-icon-chevron-down am-fr"></span></div>
                    <div class="am-panel-bd am-collapse am-in" id="collapse-panel-1" style="min-height:400px;">
                        <div class="am-g  ybp-area" >
                            <div class="am-u-sm-12">
                                <div id="chart1" class="chart-1">

                                </div>
                            </div>
                            <div style="text-align: center">
                            </div>
                            <div class="am-u-sm-12  am-text-center">
                                <div class="am-u-sm-6">
                                    <canvas id="cvs2" width="300" height="300">[No canvas support]</canvas>
                                    <p>描述: 每小时开票数量 (张/小时)</p>
                                </div>
                                <div class="am-u-sm-6">
                                    <canvas id="cvs1" width="300" height="300">[No canvas support]</canvas>
                                    <p>描述: 每张发票的开具耗时 (秒/张)</p>
                                </div>
                            </div>


                        </div>
                    </div>
                </div>
            </div>

            <div class="am-u-md-3">
                <div class="am-panel am-panel-default">
                    <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-4'}">公告<span class="am-icon-chevron-down am-fr"></span></div>
                    <div id="collapse-panel-4" class="am-panel-bd am-collapse am-in" style="min-height:400px;">
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<!-- content end -->
</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<footer>
    <hr>
    <p class="am-padding-left">© Copyright 2011-2015 上海容津信息技术有限公司 沪ICP备15020560号</p>
</footer>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="<%=request.getContextPath()%>/assets/js/amazeui.min.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/amazeui.datatables.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/amazeui.tree.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/app.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/yhgl_app.js"></script>
<script>

    (function ($) {
   /*      $.ajax({
            url: "yhgl/getUserTree", context: document.body, success: function (data) {
                $('#firstTree').tree({
                    dataSource: function (options, callback) {
                        callback({data: data});
                    },
                    multiSelect: false,
                    cacheItems: true,
                    folderSelect: false
                });
            }
        }); */
        $('#firstTree').on('selected.tree.amui', function (event, data) {
            // do something with data: { selected: [array], target: [object] }
            $("#xm").val(data.selected[0].title);
            $("#zh").val(data.selected[0].dlyhd);

            console.log(data.selected[0].title);
        });
    })(jQuery);
</script>

<!-- Gauge board -->
<script src="<%=request.getContextPath()%>/plugins/RGraph/libraries/RGraph.common.core.js"></script>
<script src="<%=request.getContextPath()%>/plugins/RGraph/libraries/RGraph.gauge.js"></script>
<script src="<%=request.getContextPath()%>/plugins/RGraph/libraries/RGraph.common.effects.js"></script>
<script src="<%=request.getContextPath()%>/plugins/RGraph/libraries/RGraph.common.dynamic.js"></script>

<script src="<%=request.getContextPath()%>/js/index.js"></script>
</body>
</html>