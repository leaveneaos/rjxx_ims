<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>时间段统计报表</title>
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

    <link rel="stylesheet" href="css/main.css"/>
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
                <strong>时间段统计报表</strong>
            </div>
        </div>
        <hr/>

        <form action="#" class="js-form am-form am-form-horizontal">
            <div class="am-g">
                <div class="am-u-sm-12  am-padding ">
                    <div class="am-u-sm-4">
                        <div class="am-form-group">
                            <input type="text" id="s_xzrq" name="s_xzrq" placeholder="日历组件"
                                   data-am-datepicker="{format: 'yyyy-mm-dd'}" readonly/>
                        </div>
                    </div>
                    <div class="am-u-sm-4">
                        <div class="am-form-group">
                            <input type="text" id="s_xzrq1" name="s_xzrq1" placeholder="日历组件"
                                   data-am-datepicker="{format: 'yyyy-mm-dd'}" readonly/>
                        </div>
                    </div>
                </div>
                <div style="margin-left: auto;margin-right: auto;text-align: center;">电子发票资料统计</div>
                <div class="am-u-sm-12  am-padding ">
                    <table class="js-table am-table  am-table-bordered  am-table-striped ">
                        <thead>
                        <tr>
                            <th colspan="1">序号</th>
                            <th colspan="1">项目</th>
                            <th colspan="1">金额</th>
                            <th colspan="1">税额</th>
                            <th colspan="1">张数</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>销项负数发票</td>
                            <td><input type="text" id="je2" name="je2" placeholder="金额" readonly
                                       style="border:none;background:transparent;"/></td>
                            <td><input type="text" id="se2" name="se2" placeholder="税额" readonly
                                       style="border:none;background:transparent;"/></td>
                            <td><input type="text" id="fpsl2" name="fpsl2" placeholder="张数" readonly
                                       style="border:none;background:transparent;"/></td>

                        </tr>
                        <tr>
                            <td>2</td>
                            <td>销项正数发票</td>
                            <td><input type="text" id="je1" name="je1" placeholder="金额" readonly
                                       style="border:none;background:transparent;"/></td>
                            <td><input type="text" id="se1" name="se1" placeholder="税额" readonly
                                       style="border:none;background:transparent;"/></td>
                            <td><input type="text" id="fpsl1" name="fpsl1" placeholder="张数" readonly
                                       style="border:none;background:transparent;"/></td>

                        </tr>
                        <tr>
                            <td></td>
                            <td>合计数</td>
                            <td><input type="text" id="je3" name="je3" placeholder="金额" readonly
                                       style="border:none;background:transparent;"/></td>
                            <td><input type="text" id="se3" name="se3" placeholder="税额" readonly
                                       style="border:none;background:transparent;"/></td>
                            <td><input type="text" id="fpsl3" name="fpsl3" placeholder="张数" readonly
                                       style="border:none;background:transparent;"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!--                 <div class="am-u-sm-12  ">
                                    <div class="am-form-group">
                                        <label class="am-checkbox-inline">
                                            <h2 class="am-margin-0">订阅:</h2></label>

                                        <label class="am-checkbox-inline">
                                            <input type="checkbox" value="0" name="dy"> 邮件订阅
                                        </label>
                                        <label class="am-checkbox-inline">
                                            <input type="checkbox" value="1" name="dy"> 短信订阅
                                        </label>
                                    </div>


                                </div> -->

                <div class="am-u-sm-12">
                    <div class="am-form-group">
                        <div class="am-u-sm-12  am-text-center">
                            <button type="button" class="js-fresh  am-btn am-btn-primary">刷新</button>
                            <!--                             <button type="submit" class="js-save  am-btn am-btn-primary">保存</button> -->
                            <!--             <button type="button" class="js-export  am-btn am-btn-primary">导出</button> -->

                        </div>
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

<script src="assets/js/Sjdtjbb.js"></script>

</body>
</html>
