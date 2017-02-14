<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>购方企业管理</title>
    <meta name="description" content="购方企业管理">
    <meta name="keywords" content="购方企业管理">
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">门店/品牌管理</strong> /
                <strong>购方管理</strong>
            </div>
        </div>
        <hr/>

                <div class="am-u-sm-12  am-padding  am-text-right">
                    <button type="button" class="js-add  am-btn am-btn-primary">✚添加购方信息</button>
                    <%--<button type="button" class="js-export  am-btn am-btn-success">导出</button>--%>
                </div>
                <div class="am-u-sm-12">
                    <div class="am-scrollable-horizontal">

                        <table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>企业名称</th>
                                <th>纳税人识别号</th>
                                <th>地址</th>
                                <th>电话</th>
                                <th>开户银行</th>
                                <th>开户账号</th>
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
            <div class="am-modal-hd">购方信息
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form action="#" class="js-form-0  am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <label for="xq_ddh" class="am-u-sm-4 am-form-label">企业名称</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xz_gfmc" name="xq_ddh" placeholder=""/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="xq_ddrq" class="am-u-sm-4 am-form-label">纳税人识别号</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xz_gfsh" name="xq_ddrq" placeholder=""/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="xq_je" class="am-u-sm-4 am-form-label">地址</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xz_gfdz" name="xq_je" placeholder=""/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="xq_mdh" class="am-u-sm-4 am-form-label">电话</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xz_gfdh" name="xq_mdh" placeholder=""/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="xq_je" class="am-u-sm-4 am-form-label">开户银行</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xz_gfyh" name="xq_je" placeholder=""/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="xq_mdh" class="am-u-sm-4 am-form-label">开户账号</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="xz_gfyhzh" name="xq_mdh" placeholder=""/>
                                </div>
                            </div>
						 </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="submit" id="save"  class="am-btn am-btn-secondary">保存</button>
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
<script src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
<script src="assets/js/amazeui.datatables.js"></script>
<script src="assets/js/amazeui.tree.min.js"></script>
<script src="assets/js/app.js"></script>
<script src="assets/js/format.js"></script>
<script src="assets/js/gfqymp.js"></script>
</body>
</html>