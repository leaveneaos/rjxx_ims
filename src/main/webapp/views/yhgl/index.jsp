<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>用户管理</title>
    <meta name="description" content="用户管理">
    <meta name="keywords" content="用户管理">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="../../assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="../../assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="stylesheet" href="../../assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="../../assets/css/admin.css">
    <link rel="stylesheet" href="../../assets/css/amazeui.tree.min.css">
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">系统管理</strong> /
                <small>用户管理</small>
            </div>
        </div>
        <div class="am-g">

            <div class="am-u-sm-2">

                <ul class="am-list am-list-static am-list-border">
                    <li>
                        <button type="button" id="add" class="am-btn-link am-badge">增加</button>
                        <button type="button" id="delete" class="am-btn-link am-badge">删除</button>
                        用户列表
                    </li>
                </ul>
                <ul class="am-tree" id="firstTree">
                    <li class="am-tree-branch am-hide" data-template="treebranch">
                        <div class="am-tree-branch-header">
                            <button class="am-tree-branch-name">
                                <span class="am-tree-icon am-tree-icon-folder"></span>
                                <span class="am-tree-label"></span>
                            </button>
                        </div>
                        <ul class="am-tree-branch-children"></ul>
                        <div class="am-tree-loader"><span class="am-icon-spin am-icon-spinner"></span></div>
                    </li>
                    <li class="am-tree-item am-hide" data-template="treeitem">
                        <button class="am-tree-item-name">
                            <span class="am-tree-icon am-tree-icon-item"></span>
                            <span class="am-tree-label"></span>
                        </button>
                    </li>
                </ul>
            </div>
            <div class="am-u-sm-10">
                <form class="am-form">
                    <fieldset>
                        <legend></legend>

                        <div class="am-form-group">
                            <label for="zh">账号</label>
                            <input type="text" class="" id="zh" placeholder="账号">
                        </div>

                        <div class="am-form-group">
                            <label for="xm">姓名</label>
                            <input type="text" class="" id="xm" placeholder="姓名">
                        </div>

                        <div class="am-form-group">
                            <label for="zw">职务</label>
                            <input type="text" class="" id="zw" placeholder="职务">
                        </div>
                        <div class="am-form-group">
                            <label for="lxdh">联系电话</label>
                            <input type="text" class="" id="lxdh" placeholder="联系电话">
                        </div>
                        <div class="am-form-group">
                            <label for="yx">联系电话</label>
                            <input type="email" class="" id="yx" placeholder="联系电话">
                        </div>
                    </fieldset>
                </form>


                <p>
                    <button type="button" id="save" class="am-btn am-btn-default am-btn-secondary">保存</button>
                </p>
                </fieldset>
                </form>
            </div>
        </div>

    </div>
</div>
<!-- content end -->
</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"></a>


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
<script src="../assets/js/amazeui.tree.min.js"></script>
<script src="../assets/js/app.js"></script>
<script src="../assets/js/yhgl_app.js"></script>
<script>

    (function ($) {
        $.ajax({
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
        });
        $('#firstTree').on('selected.tree.amui', function (event, data) {
            // do something with data: { selected: [array], target: [object] }
            $("#xm").val(data.selected[0].title);
            $("#zh").val(data.selected[0].dlyhd);

            console.log(data.selected[0].title);
        });
    })(jQuery);
</script>
</body>
</html>
