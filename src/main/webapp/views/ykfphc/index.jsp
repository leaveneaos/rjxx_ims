<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>已开发票红冲</title>
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">业务处理</strong> /
                <small>发票红冲</small>
            </div>
        </div>
        <hr/>
        <div class="am-g">
            <div class="am-u-sm-12  am-text-center">
                <h2>发票红冲</h2>
            </div>
        </div>

        <div class="am-g  am-padding-top">
            <form  action="#" class="js-search-form  am-form am-form-horizontal">
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_jyh" class="am-u-sm-3 am-form-label">交易号</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_jyh" name="s_jyh" placeholder="请输入交易号" />
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_jyrq" class="am-u-sm-3 am-form-label">交易日期</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_jyrq" name="s_jyrq"  data-am-datepicker="{format: 'yyyy/mm/dd'}" readonly  />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_fpdm" class="am-u-sm-3 am-form-label">发票代码</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_fpdm" name="s_fpdm" placeholder="请输入发票代码" />
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_fphm" class="am-u-sm-3 am-form-label">发票号码</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="s_fphm" name="s_fphm" placeholder="请输入发票号码" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            <label for="s_fpzt"  class="am-u-sm-3  am-form-label">发票状态</label>
                            <div class="am-u-sm-9">
                                <select id="s_fpzt" name="s_fpzt">
                                    <option value="0">正常/红冲</option>
                                    <option value="1">不正常</option>
                                    <option value="2">状态3</option>
                                </select>
                                <span class="am-form-caret"></span>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-sm-6">
                        <div class="am-form-group">
                            &nbsp;
                        </div>
                    </div>
                </div>

                <hr/>

                <div class="am-u-sm-12  am-padding  am-text-center">
                    <button type="button" class="js-search  am-btn am-btn-primary">查询</button>
                </div>

                <div class="am-u-sm-12">
                    <div class="am-scrollable-horizontal">

                        <table class="js-table  am-table am-table-bordered am-table-striped am-text-nowrap">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>交易号</th>
                                <th>交易日期</th>
                                <th>发票代码</th>
                                <th>发票号码</th>
                                <th>金额</th>
                                <th>税额</th>
                                <th>红字通知单</th>
                                <th>红冲发票代码</th>
                                <th>红冲发票号码</th>
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
            <div class="am-modal-hd">发票红冲
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form  action="#"  class="js-form-0  am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <label for="hc_yfpdm" class="am-u-sm-4 am-form-label">原发票代码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="hc_yfpdm" name="hc_yfpdm" placeholder="" />
                                    <input type="hidden" name="id"/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="hc_yfphm" class="am-u-sm-4 am-form-label">原发票号码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="hc_yfphm" name="hc_yfphm"  placeholder="" />
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="hc_kpje" class="am-u-sm-4 am-form-label">开票金额</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="hc_kpje"  name="hc_kpje" placeholder=""  />
                                </div>
                            </div>

                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <label for="hc_hztzdh" class="am-u-sm-4 am-form-label">红字通知单号</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="hc_hztzdh"  name="hc_hztzdh"  placeholder="" required/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="hc_hzfpdm" class="am-u-sm-4 am-form-label">红字发票代码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="hc_hzfpdm" name="hc_hzfpdm"  placeholder="" readonly/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="hc_hzfphm" class="am-u-sm-4 am-form-label">红字发票号码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="hc_hzfphm"  name="hc_hzfphm" placeholder="" readonly/>
                                </div>
                            </div>
                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="submit" class="js-submit  am-btn am-btn-primary">保存</button>
                                    <button type="button" class="js-close  am-btn am-btn-danger">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="am-modal am-modal-no-btn" tabindex="-1" id="original-detail-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">原发票明细
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form  action="#"  class="js-form-1  am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-u-sm-12">

                            <div class="am-form-group">
                                <label for="od_jyh" class="am-u-sm-4 am-form-label">交易号</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_jyh" name="od_jyh"  placeholder="" readonly/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_jyrq" class="am-u-sm-4 am-form-label">交易日期</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_jyrq"  name="od_jyrq" placeholder="" readonly />
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_fpdm"  name="od_fpdm"  placeholder="" readonly/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_fphm" class="am-u-sm-4 am-form-label">发票号码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_fphm" name="od_fphm"  placeholder="" readonly/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_je" class="am-u-sm-4 am-form-label">金额</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_je"  name="od_je" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_se" class="am-u-sm-4 am-form-label">税额</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_se"  name="od_se" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_hztzd" class="am-u-sm-4 am-form-label">红字通知单</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_hztzd"  name="od_hztzd" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_hcfpdm" class="am-u-sm-4 am-form-label">红冲发票代码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_hcfpdm"  name="od_hcfpdm" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_hcfphm" class="am-u-sm-4 am-form-label">红冲发票号码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_hcfphm"  name="od_hcfphm" placeholder="" readonly/>
                                </div>
                            </div>
                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="submit" class="js-close  am-btn am-btn-danger">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="am-modal am-modal-no-btn" tabindex="-1" id="hongchong-detail-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">添加设备
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <hr/>
                <form  action="#"  class="js-form-2 am-form am-form-horizontal">
                    <div class="am-g">
                        <div class="am-u-sm-12">

                            <div class="am-form-group">
                                <label for="od_jyh" class="am-u-sm-4 am-form-label">交易号</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_jyh" name="od_jyh"  placeholder="" readonly/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_jyrq" class="am-u-sm-4 am-form-label">交易日期</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_jyrq"  name="od_jyrq" placeholder="" readonly />
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_fpdm" class="am-u-sm-4 am-form-label">发票代码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_fpdm"  name="od_fpdm"  placeholder="" readonly/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_fphm" class="am-u-sm-4 am-form-label">发票号码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_fphm" name="od_fphm"  placeholder="" readonly/>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label for="od_je" class="am-u-sm-4 am-form-label">金额</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_je"  name="od_je" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_se" class="am-u-sm-4 am-form-label">税额</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_se"  name="od_se" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_hztzd" class="am-u-sm-4 am-form-label">红字通知单</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_hztzd"  name="od_hztzd" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_hcfpdm" class="am-u-sm-4 am-form-label">红冲发票代码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_hcfpdm"  name="od_hcfpdm" placeholder="" readonly/>
                                </div>
                            </div>
                            <div class="am-form-group">
                                <label for="od_hcfphm" class="am-u-sm-4 am-form-label">红冲发票号码</label>
                                <div class="am-u-sm-8">
                                    <input type="text" id="od_hcfphm"  name="od_hcfphm" placeholder="" readonly/>
                                </div>
                            </div>
                        </div>
                        <div class="am-u-sm-12">
                            <div class="am-form-group">
                                <div class="am-u-sm-12  am-text-center">
                                    <button type="submit" class="js-close  am-btn am-btn-danger">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>


    <!-- loading do not delete this -->
    <div class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn" tabindex="-1" >
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
<script src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
<script src="assets/js/amazeui.datatables.js"></script>
<script src="assets/js/amazeui.tree.min.js"></script>
<script src="assets/js/app.js"></script>

<script src="js/ykfphc.js"></script>

</body>
</html>
