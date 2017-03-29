<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>使用报告</title>
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
    <!-- jqplot style -->
    <link rel="stylesheet" href="plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.min.css"/>
<script src="assets/js/loading.js"></script>
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
                <small>使用报告</small>
            </div>
        </div>
        <hr/>
        <div class="am-g">
            <div class="am-u-sm-12  am-text-center">
                <h2>使用报告</h2>
            </div>
        </div>

        <div class="am-g">
            <div class="am-u-sm-12 am-padding">
                <table class="am-table  am-table-bordered  am-table-striped ">
                    <thead>
                    <tr>
                        <th colspan="8" class="am-primary">统计报告(2015年11月01日 至 2015年11月30日)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="8"><strong>总体情况一览</strong></td>
                    </tr>
                    <tr>
                        <td><strong>核定用票量</strong></td>
                        <td>100000.00</td>
                        <td><strong>核定限额</strong></td>
                        <td>10000.00</td>
                        <td><strong>报告日期</strong></td>
                        <td>2015/12/03</td>
                        <td><strong>已用票量</strong></td>
                        <td>50000.00</td>
                    </tr>
                    <tr>
                        <td colspan="2" class="am-text-center"><strong>正常开具</strong></td>
                        <td colspan="2" class="am-text-center"><strong>红冲开具</strong></td>
                        <td colspan="2" class="am-text-center"><strong>发票换开</strong></td>
                        <td><strong>库存票量</strong></td>
                        <td>23</td>
                    </tr>
                    <tr>
                        <td><strong>金额</strong></td>
                        <td>123</td>
                        <td><strong>金额</strong></td>
                        <td>343</td>
                        <td><strong>金额</strong></td>
                        <td>322</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td><strong>发票数量</strong></td>
                        <td>3211</td>
                        <td><strong>发票数量</strong></td>
                        <td>2321</td>
                        <td><strong>发票数量</strong></td>
                        <td>23232</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="8" class="am-success"><strong>月统计</strong></td>
                    </tr>
                    <tr>
                        <td>没有数据</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>没有数据</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>

                    <tr>
                        <td colspan="8">
                            <div class="am-u-sm-12  am-padding ">
                                <div id="chart2" class="chart-2">

                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="8" class="am-danger"><strong>系统运行状况一览</strong></td>
                    </tr>
                    <tr>
                        <td><strong>本月开票总量</strong></td>
                        <td>2321</td>
                        <td><strong>无间断开票量</strong></td>
                        <td>3434</td>
                        <td><strong>异常交易量</strong></td>
                        <td>4343</td>
                        <td><strong>单张生成时间</strong></td>
                        <td>15s</td>
                    </tr>
                    <tr>
                        <td><strong>电子发票使用空间</strong></td>
                        <td>1231</td>
                        <td><strong>空闲空间</strong></td>
                        <td>2323</td>
                        <td><strong>占比</strong></td>
                        <td>20%</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    </tbody>
                    <tfoot>

                    </tfoot>
                </table>
            </div>
        </div>
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

<!-- jqplot chart -->
<script src="plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.min.js"></script>
<script src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.barRenderer.min.js"></script>
<script src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.highlighter.min.js"></script>
<script src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.cursor.min.js"></script>
<script src="plugins/jquery.jqplot.1.0.8/dist/plugins/jqplot.pointLabels.min.js"></script>


<script src="js/sybg.js"></script>

</body>
</html>
