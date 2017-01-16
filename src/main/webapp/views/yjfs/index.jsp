<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>发票发送</title>
    <meta name="description" content="发票发送">
    <meta name="keywords" content="发票发送">
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">业务处理</strong> /
                <strong>发票发送</strong>
            </div>
        </div>
        <hr/>

        <div class="am-g  am-padding-top">
            <form action="#" class="js-search-form  am-form am-form-horizontal">
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_jyrqq" class="am-u-sm-3 am-form-label">交易日期</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_jyrqq" name="s_jyrqq"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}" placeholder="点击选择时间" readonly/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_jyrqz" class="am-u-sm-3 am-form-label am-text-center">-</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_jyrqz" name="s_jyrqz"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}" placeholder="点击选择时间" readonly/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_kprqq" class="am-u-sm-3 am-form-label">开票日期</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_kprqq" name="s_kprqq"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}" placeholder="点击选择时间" readonly/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_kprqz" class="am-u-sm-3 am-form-label  am-text-center">-</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_kprqz" name="s_kprqz"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}" placeholder="点击选择时间" readonly/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_gfmc" class="am-u-sm-3  am-form-label">购方名称</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_gfmc" name="s_gfmc" placeholder="请输入购方名称"/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_ddh" class="am-u-sm-3  am-form-label">订单号</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_ddh" name="s_ddh" placeholder="请输入订单号"/>
                            </div>
                        </div>
                    </div>
                </div>

                <hr/>

                <div class="am-u-sm-12  am-padding  am-text-center">
                    <button type="button" class="js-search  am-btn am-btn-primary">查询</button>
                    <button type="button" class="js-sent  am-btn am-btn-primary">发送</button>
                </div>

                <div class="am-u-sm-12">
                    <div class="am-scrollable-horizontal">

                        <table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
                            <thead>
                            <tr>
                                <th><input type="checkbox" id="check_all"/></th>
                                <th>序号</th>
                                <th>订单号</th>
                                <th>交易日期</th>
                                <th>购方名称</th>
                                <th>开票日期</th>
                                <th>发票代码</th>
                                <th>发票号码</th>
                                <th>金额</th>
                                <th>税额</th>
                                <th>价稅合计</th>
                                <th>邮箱</th>
                                <th>发票接收方式</th>
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


    <div class="am-modal am-modal-no-btn" tabindex="-1" id="original-detail-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">修改发票接收地址
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form action="#" class="js-form-1  am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-u-sm-12">

                            <div class="am-form-group">
                                <label for="gfemail" class="am-u-sm-4 am-form-label">邮件地址</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="gfemail" name="gfemail" placeholder=""/>
                                    <input type="hidden" name="kplsh" placeholder=""/>
                                    <input type="hidden" name="rowId" placeholder=""/>

                                </div>
                            </div>

                            <%--<div class="am-form-group">--%>
                            <%--<label for="sj" class="am-u-sm-4 am-form-label">手机号码</label>--%>
                            <%--<div class="am-u-sm-8">--%>
                            <%--<input type="text" id="sj" name="sj" placeholder=""/>--%>
                            <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="am-form-group">--%>
                            <%--<label for="wx" class="am-u-sm-4 am-form-label">微信账号</label>--%>
                            <%--<div class="am-u-sm-8">--%>
                            <%--<input type="text" id="wx" name="wx" placeholder=""/>--%>
                            <%--</div>--%>
                            <%--</div>--%>


                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="button" class="js-close  am-btn am-btn-danger">关闭</button>
                                    <button type="submit" class="js-submit  am-btn am-btn-primary">保存</button>

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
	<script src="assets/js/format.js"></script>

<script src="assets/js/fpfs.js"></script>

</body>
</html>
