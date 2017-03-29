<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>仪表盘</title>
    <meta name="description" content="用户管理">
    <meta name="keywords" content="用户管理">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="../../assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="../../assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="stylesheet" href="assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="assets/css/admin.css">
    <link rel="stylesheet" href="assets/css/amazeui.tree.min.css">
<script src="assets/js/loading.js"></script>
    <!-- jqplot style -->
    <link rel="stylesheet" href="/plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.min.css"/>
    <link rel="stylesheet" href="css/main.css"/>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

<%@ include file="../../pages/top.jsp" %>
<div class="am-cf admin-main">
    <!-- sidebar start -->
    <%@ include file="../../pages/menus.jsp" %>
    <!-- sidebar end -->

    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">查询统计</strong> /
                <small>仪表盘</small>
            </div>
        </div>
        <hr/>
        <div class="am-g">
            <div class="am-u-sm-12  am-text-center">
                <h2>仪表盘</h2>
            </div>
        </div>

        <form action="#" class="am-form am-form-horizontal">
            <div class="am-g  ybp-area">
                <div class="am-u-sm-12">
                    <div id="chart1" class="chart-1">

                    </div>
                </div>
                <div style="text-align: center">
                </div>
                <div class="am-u-sm-12  am-text-center">
                    <div class="am-u-sm-6">
                        <canvas id="cvs2" width="400" height="400">[No canvas support]</canvas>
                        <p>描述: 每小时开票数量 (张/小时)</p>
                    </div>
                    <div class="am-u-sm-6">
                        <canvas id="cvs1" width="400" height="400">[No canvas support]</canvas>
                        <p>描述: 每张发票的开具耗时 (秒/张)</p>
                    </div>
                    <div class="am-u-sm-6">
                        <canvas id="cvs3" width="160" height="400">[No canvas support]</canvas>
                        <p>描述: 发票使用量 已用量/总量 %</p>

                    </div>
                    <div class="am-u-sm-6">
                        <canvas id="cvs-pie" width="100" height="100">[No canvas support]</canvas>
                        <p>描述: 系统是否可用</p>
                    </div>
                </div>


            </div>
        </form>


    </div>
    <!-- content end -->

    <!-- loading do not delete -->
    <div class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn" tabindex="-1">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">正在载入...</div>
            <div class="am-modal-bd">
                <span class="am-icon-spinner am-icon-spin"></span>
            </div>
        </div>
    </div>
</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<%@ include file="../../pages/foot.jsp" %>


<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="assets/js/amazeui.min.js"></script>
<script src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
<script src="assets/js/amazeui.datatables.js"></script>
<script src="assets/js/amazeui.tree.min.js"></script>
<script src="assets/js/app.js"></script>

<!-- Gauge board -->
<script src="plugins/RGraph/libraries/RGraph.common.core.js"></script>
<script src="plugins/RGraph/libraries/RGraph.gauge.js"></script>
<script src="plugins/RGraph/libraries/RGraph.common.effects.js"></script>
<script src="plugins/RGraph/libraries/RGraph.common.dynamic.js"></script>
<script src="plugins/RGraph/libraries/RGraph.vprogress.js"></script>
<script src="plugins/RGraph/libraries/RGraph.pie.js"></script>
<script src="plugins/RGraph/libraries/RGraph.common.tooltips.js"></script>


<script src="js/ybp.js"></script>

</body>
</html>
