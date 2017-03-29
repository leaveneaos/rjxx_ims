<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>报表订阅</title>
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
    <link rel="stylesheet" href="assets/css/amazeui.datatables.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <script src="assets/js/loading.js"></script>
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
                <small>报表订阅</small>
            </div>
        </div>
        <hr/>
        <div class="am-g">
            <div class="am-u-sm-12  am-text-center">
                <h2>报表订阅</h2>
            </div>
        </div>
        <form action="#" class="js-search-form  am-form am-form-horizontal">

            <div class="am-g  am-padding-top">
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="sjh" class="am-u-sm-3 am-form-label">手机号</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="sjh" name="sjh" placeholder="请输入接收报表的手机号"/>

                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="yx" class="am-u-sm-3 am-form-label">邮箱</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="yx" name="yx" placeholder="请输入接收报表的邮箱"/>
                            </div>
                        </div>
                    </div>
                </div>

                <hr/>

                <div class="am-u-sm-12  am-padding  am-text-center">
                    <button type="button" class="js-save  am-btn am-btn-success">保存</button>

                </div>

                <div class="am-u-sm-12">
                    <div class="am-scrollable-horizontal">

                        <table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>报表名称</th>
                                <th>报表描述</th>
                                <th>订阅</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </form>

    </div>
    <!-- content end -->

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

<script src="js/bbdy.js"></script>

</body>
</html>
