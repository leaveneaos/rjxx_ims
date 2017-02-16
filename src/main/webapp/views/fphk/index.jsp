<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>发票换开</title>
    <meta name="description" content="发票换开">
    <meta name="keywords" content="发票换开">
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
                <strong>发票换开</strong>
            </div>
        </div>
        <hr/>

        <div class="am-g  am-padding-top">
            <form action="#" class="js-search-form  am-form am-form-horizontal">

                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_kprqq" class="am-u-sm-3 am-form-label">开票日期</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_kprqq" name="s_kprqq"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}" placeholder="点击选择时间"/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_kprqz" class="am-u-sm-3 am-form-label  am-text-center">-</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_kprqz" name="s_kprqz"
                                       data-am-datepicker="{format: 'yyyy-mm-dd'}" placeholder="点击选择时间"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_fpdm" class="am-u-sm-3 am-form-label">发票代码</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_fpdm" name="s_fpdm" placeholder="请输入发票代码"/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_fphm" class="am-u-sm-3 am-form-label">发票号码</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_fphm" name="s_fphm" placeholder="请输入发票号码"/>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_gfmc" class="am-u-sm-3 am-form-label">购方名称</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_gfmc" name="s_gfmc" placeholder="请输入购方名称"/>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_ddh" class="am-u-sm-3 am-form-label">订单号</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_ddh" name="s_ddh" placeholder="请输入订单号"/>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>

                <div class="am-u-sm-12  am-padding  am-text-center">
                    <button type="button" class="js-search  am-btn am-btn-primary">查询</button>
                    <%--<button type="button" class="js-export  am-btn am-btn-warning">导出</button>--%>
                </div>

                <div class="am-u-sm-12">
                    <div class="am-scrollable-horizontal">

                        <table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>订单号</th>
                                <th>开票日期</th>
                                <th>购方名称</th>
                                <th>发票代码</th>
                                <th>发票号码</th>
                                <th>金额</th>
                                <th>税额</th>
                                <th>价税合计</th>
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
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="huankai">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">发票换开详情
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form action="fphk/ykfphk" class="js-form-0  am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-u-sm-12">
                            <div class="am-form-group" style="display: none">
                                <label for="kplsh" class="am-u-sm-4 am-form-label">KPLSH</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="kplsh" name="kplsh" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group" style="display: none">
                                <label for="djh" class="am-u-sm-4 am-form-label">DJH</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="djh" name="djh" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="yfpdm" class="am-u-sm-4 am-form-label">原发票代码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="yfpdm" name="yfpdm" placeholder="" readonly/>
                                    <input type="hidden" name="id"/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="yfphm" class="am-u-sm-4 am-form-label">原发票号码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="yfphm" name="yfphm" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="kpje" class="am-u-sm-4 am-form-label">价税合计</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="kpje" name="kpje" placeholder="" readonly/>
                                </div>
                            </div>

                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="submit" class="js-submit  am-btn am-btn-primary">确定</button>
                                    <button type="button" class="js-close  am-btn am-btn-danger">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="am-modal am-modal-no-btn" tabindex="-1" id="export">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">发票导出
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form action="#" class="js-form-1  am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-g">
                            <div class="am-u-sm-12">
                                <div class="am-form-group">
                                    <label for="e_dcsjq" class="am-u-sm-3 am-form-label">导出时间起</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" id="e_dcsjq" name="e_dcsjq"
                                               data-am-datepicker="{format: 'yyyy/mm/dd'}" placeholder="点击选择时间" readonly
                                               required/>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12">
                                <div class="am-form-group">
                                    <label for="e_dcsjz" class="am-u-sm-3 am-form-label">导出时间止</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" id="e_dcsjz" name="e_dcsjz"
                                               data-am-datepicker="{format: 'yyyy/mm/dd'}" placeholder="点击选择时间" readonly
                                               required/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="submit" class="js-export-submit  am-btn am-btn-primary">确定</button>
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
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
<script src="assets/js/fphk.js"></script>
</body>
</html>
