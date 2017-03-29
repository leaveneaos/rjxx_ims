<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>销方信息维护</title>
    <meta name="description" content="销方信息维护">
    <meta name="keywords" content="销方信息维护">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="stylesheet" href="assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="assets/css/admin.css">
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">基础数据</strong> /
                <small>销方信息维护</small>
            </div>
        </div>
        <hr/>
        <div class="am-g">
            <div class="am-u-sm-12  am-text-center">
                <h2>基本信息</h2>
            </div>
        </div>
        <form action="/qykpxxwh_save" method="POST" class="am-form am-form-horizontal">
            <div class="am-g">
                <div class="am-u-sm-6">
                    <div class="am-form-group">
                        <label for="jgmc" class="am-u-sm-3 am-form-label">机构名称</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="jgmc" name="jgmc" placeholder="请输入机构名称"/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="xfsh" class="am-u-sm-3 am-form-label">销方税号</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="xfsh" name="xfsh" readonly/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="khyh" class="am-u-sm-3 am-form-label">开户银行</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="khyh" name="khyh" placeholder="请输入开户银行" required/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="xflxr" class="am-u-sm-3 am-form-label">销方联系人</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="xflxr" name="xflxr" placeholder="请输入销方联系人">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="dz" class="am-u-sm-3 am-form-label">地址</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="dz" name="dz" placeholder="请输入地址"/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="kpr" class="am-u-sm-3 am-form-label">开票人</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="kpr" name="kpr" placeholder="请输入开票人"/>

                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="skr" class="am-u-sm-3 am-form-label">收款人</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="skr" name="skr" placeholder="请输入收款人"/>
                        </div>
                    </div>
                </div>
                <div class="am-u-sm-6">
                    <div class="am-form-group">
                        <label for="sjjgmc" class="am-u-sm-3 am-form-label">上级机构名称</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="sjjgmc" name="sjjgmc" placeholder="请输入上级机构名称"/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="xfmc" class="am-u-sm-3 am-form-label">销方名称</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="xfmc" name="xfmc" readonly/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="yhzh" class="am-u-sm-3 am-form-label">银行账号</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="yhzh" name="yhzh" placeholder="请输入银行账号" required/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="xfdh" class="am-u-sm-3 am-form-label">销方电话</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="xfdh" name="xfdh" placeholder="请输入销方电话">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="xfyb" class="am-u-sm-3 am-form-label">销方邮编</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="xfyb" name="xfyb" placeholder="请输入销方邮编"/>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="fhr" class="am-u-sm-3 am-form-label">复核人</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="fhr" name="fhr" placeholder="请输入复核人"/>

                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="sqyhs" class="am-u-sm-3 am-form-label">授权用户数</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="sqyhs" name="sqyhs" readonly/>

                        </div>
                    </div>
                </div>
                <div class="am-u-sm-12">
                    <div class="am-form-group">
                        <div class="am-u-sm-12  am-text-center">
                            <button type="submit" class="am-btn am-btn-primary">保存修改</button>
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

<footer>
    <hr>
    <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
</footer>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="assets/js/amazeui.min.js"></script>
<script src="assets/js/amazeui.datatables.js"></script>
<script src="assets/js/app.js"></script>
<script src="assets/js/kpjbxxwh_app.js"></script>
<script>

</script>

</body>
</html>
