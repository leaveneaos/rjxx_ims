<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>基本信息维护</title>
    <meta name="description" content="这是一个 user 页面">
    <meta name="keywords" content="user">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="../../assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="../../assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="stylesheet" href="../../assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="../../assets/css/admin.css">
    <script src="assets/js/loading.js"></script>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
<%@ include file="../../pages/top.jsp"%>
<div class="am-cf admin-main">
    <!-- sidebar start -->
    <%@ include file="../../pages/menus.jsp" %>
    <!-- sidebar end -->
    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">基本信息维护</strong> /
                <small>Personal information</small>
            </div>
        </div>
        <hr/>
        <div class="am-g table-main">
            <div class="am-u-sm-12">
                <div class="am-panel am-panel-default">
                    <div class="am-panel-hd">查询条件</div>
                    <div class="am-panel-bd">
                        <form class="am-form am-form-horizontal">
                            <div class="am-form-group">
                                <label for="nsrsbh" class="am-u-sm-2 am-form-label">税号</label>

                                <div class="am-u-sm-4">
                                    <input type="text" id="nsrsbh" placeholder="输入...">
                                </div>
                                <label for="qymc" class="am-u-sm-2 am-form-label">企业名称</label>

                                <div class="am-u-sm-4">
                                    <input type="text" id="qymc" placeholder="输入...">
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="khyh" class="am-u-sm-2 am-form-label">开户银行</label>

                                <div class="am-u-sm-4">
                                    <select data-am-selected="{btnWidth: '100%'}" id="khyh">
                                        <option value="310106550096887">310106550096887</option>
                                    </select>

                                </div>
                                <label for="yhzh" class="am-u-sm-2 am-form-label">银行账号</label>

                                <div class="am-u-sm-4">
                                    <input type="text" id="yhzh" placeholder="输入...">
                                </div>
                            </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-12 am-u-sm-push-5">
                                    <button type="button" class="am-btn am-btn-success am-btn-xs " id="search_button">
                                        查询
                                    </button>
                                    <button type="button" class="am-btn am-btn-secondary am-btn-xs">新增</button>
                                </div>
                            </div>
                        </form>


                    </div>
                </div>
            </div>

            <div class="am-u-sm-12">
                <table class="am-table am-table-striped am-table-bordered am-table-compact table-main" id="searchtable">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>税号</th>
                        <th>企业名称</th>
                        <th>开户银行</th>
                        <th>银行账号</th>
                        <th>地址</th>
                        <th>电话</th>
                        <th width="150px">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-alert-edit">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">编辑
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <form class="am-form am-form-horizontal">
                <input type="hidden" id="formid">

                <div class="am-form-group">
                    <label for="nsrsbh_edit" class="am-u-sm-2 am-form-label">QQ</label>

                    <div class="am-u-sm-4">
                        <input type="email" id="nsrsbh_edit" placeholder="输入你的QQ号码">
                    </div>
                    <label for="user-QQ" class="am-u-sm-2 am-form-label">QQ</label>

                    <div class="am-u-sm-4">
                        <input type="email" id="user-QQ" placeholder="输入你的QQ号码">
                    </div>
                </div>
                <div class="am-form-group">
                    <label for="user-QQ" class="am-u-sm-2 am-form-label">QQ</label>

                    <div class="am-u-sm-4">
                        <select data-am-selected="{btnWidth: '100%'}" id="yhzh_edit">
                            <option value="a">Apple</option>
                            <option value="b">Banana</option>
                            <option value="o">Orange</option>
                            <option value="m">Mango</option>
                            <option value="d" disabled>禁用鸟</option>
                        </select>
                    </div>
                    <label for="user-QQ" class="am-u-sm-2 am-form-label">QQ</label>

                    <div class="am-u-sm-4">
                        <input type="email" id="user-QQ" placeholder="输入你的QQ号码">
                    </div>
                </div>
                <div class="am-form-group">
                    <div class="am-u-sm-12">
                        <button type="button" class="am-btn" onclick="alert($('#formid').val());">保存修改</button>
                        <button type="button" class="am-btn am-btn-secondary">保存修改</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<footer>
    <hr>
    <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
</footer>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="../assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="../assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="../assets/js/amazeui.min.js"></script>
<script src="../assets/js/amazeui.datatables.js"></script>
<script src="../assets/js/app.js"></script>
<script src="../assets/js/jbxxwh_app.js"></script>
<script>

</script>

</body>
</html>
