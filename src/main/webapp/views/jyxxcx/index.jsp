<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>交易信息查询</title>
    <meta name="description" content="交易信息查询">
    <meta name="keywords" content="交易信息查询">
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

<div class="am-cf admin-main">
    <!-- sidebar start -->
    <!-- sidebar end -->

    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">查询统计</strong> /
                <strong>交易信息查询</strong>
            </div>
        </div>
        <hr/>

        <div class="am-g  am-padding-top">
            <form action="#" class="js-search-form  am-form am-form-horizontal">
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_ddh" class="am-u-sm-3 am-form-label">订单号</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_ddh" name="s_ddh" placeholder=""/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_kpddm" class="am-u-sm-3 am-form-label">门店号</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_kpddm" name="s_kpddm" placeholder=""/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_ddrqq" class="am-u-sm-3 am-form-label">订单日期</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_ddrqq" name="s_ddrqq" placeholder="点击选择日期"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}"/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_ddrqz" class="am-u-sm-3 am-form-label am-text-center">-</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_ddrqz" name="s_ddrqz" placeholder="点击选择日期"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}"/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        &nbsp;
                    </div>
                </div>

                <hr/>

                <div class="am-u-sm-12  am-padding  am-text-center">
                    <button type="button" class="js-search  am-btn am-btn-primary">查询</button>
                    <%--<button type="button" class="js-export  am-btn am-btn-success">导出</button>--%>
                </div>

                <div class="am-u-sm-12">
                    <div class="am-scrollable-horizontal">

                        <table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>订单号</th>
                                <th>订单日期</th>
                                <th>金额</th>
                                <th>门店号</th>
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
    <!-- content end -->

    <!-- model -->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">交易信息详情
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form action="#" class="js-form-0  am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <label for="xq_ddh" class="am-u-sm-4 am-form-label">订单号</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xq_ddh" name="xq_ddh" placeholder="" readonly/>
                                    <input type="hidden" name="id"/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="xq_ddrq" class="am-u-sm-4 am-form-label">订单日期</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xq_ddrq" name="xq_ddrq" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="xq_je" class="am-u-sm-4 am-form-label">金额</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xq_je" name="xq_je" placeholder="" readonly/>
                                </div>
                            </div>

                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <label for="xq_mdh" class="am-u-sm-4 am-form-label">门店号</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xq_mdh" name="xq_mdh" placeholder="" readonly/>
                                </div>
                            </div>
                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="button" class="js-close  am-btn am-btn-danger">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
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

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"></a>



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
<script src="assets/js/format.js"></script>
<script src="assets/js/jyxxcx.js"></script>

</body>
</html>