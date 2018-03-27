<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>接口配置</title>
    <meta name="description" content="接口配置">
    <meta name="keywords" content="user">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="icon" type="image/png" href="assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed"
          href="../../assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="assets/css/admin.css">
    <link rel="stylesheet" href="assets/css/app.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="assets/css/sweetalert.css">
    <script src="assets/js/loading.js"></script>

    <link rel="stylesheet" href="assets/css/amazeui.tree.min.css"/>

</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
<!-- sidebar start -->
<!-- sidebar end -->
<!-- content start -->
<input type="hidden" id="djh" value="0">
<div class="row-content am-cf">
    <div class="row">
        <input type="hidden" id="gfid" value="0">
        <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
            <div class="widget am-cf">
                <div class="admin-content">
                    <div class="am-cf widget-head">
                        <div class="widget-title am-cf">
                            <strong class="am-text-primary am-text-lg">业务处理</strong> / <strong>接口配置</strong>
                        </div>
                            <!--公司搜索结果-->
                            <div class="am-u-sm-12 am-u-md-6 am-u-lg-3 am-padding-top">
                                <div class="am-form-group tpl-table-list-select">
                                    <select id="dxcsm" data-am-selected="{btnSize: 'sm'}">
                                        <option value="gfmc">购方名称</option>
                                        <option value="nsrsbh">纳税人识别号</option>
                                    </select>
                                    <button type="button" id="companySearch"
                                            class="am-btn am-btn-default am-btn-success am-btn-sm">查询</button>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-3 am-padding-top">
                                <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    <input id="dxcsz" type="text" class="am-form-field "> <span
                                        class="am-input-group-btn">
											<button id="search"
                                                    class="am-btn am-btn-default am-btn-success tpl-table-list-field am-icon-search"
                                                    type="button"></button>
										</span>
                                </div>
                            </div>

                        <div class="am-g  am-padding-top">
                            <form action="#" class="js-search-form  am-form">
                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                    <div class="am-form-group">
                                        <div class="am-btn-toolbar">
                                            <div class="am-btn-group am-btn-group-sm">
                                                <button type="button" id="gf_add"
                                                        class="am-btn am-btn-default am-btn-success">
                                                    新增
                                                </button>
                                                <button type="button" id="gf_del"
                                                        class="am-btn am-btn-default am-btn-danger">
                                                    删除
                                                </button>
                                                <%--<button type="button" id="gf_xg"--%>
                                                        <%--class="am-btn am-btn-default am-btn-warning">--%>
                                                    <%--修改--%>
                                                <%--</button>--%>
                                                <button type="button"  id="addtionSelect"class="am-btn am-btn-default am-btn-warning" data-am-offcanvas="{target: '#doc-oc-demo3'}">授权</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                        </form>
                        <div class="am-u-sm-12 am-padding-top">
                            <div>
                                <table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
                                       id="gfxx_table">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="check_all" /></th>
                                        <th>操作</th>
                                        <th>购方企业名称</th>
                                        <th>纳税人识别号</th>
                                        <th>注册地址</th>
                                        <th>注册电话</th>
                                        <th>开户银行</th>
                                        <th>银行账号</th>
                                        <th>联系人</th>
                                        <th>联系电话</th>
                                        <th>邮寄地址</th>
                                        <th>Email</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                        <!-- model -->
                        <!--xiugai model -->
                        <div class="am-modal am-modal-no-btn" tabindex="-1" id="xiugai">
                            <div class="am-modal-dialog">
                                <div class="am-modal-hd">
                                    购方信息修改 <!-- <a href="javascript: void(0)"
								class="am-close am-close-spin" data-am-modal-close>&times;</a> -->
                                </div>
                                <div class="am-modal-bd">
                                    <hr />
                                    <form action="#" class="js-form-0  am-form am-form-horizontal">
                                        <div class="am-g">
                                            <div class="am-u-sm-12">
                                                <div class="am-form-group">
                                                    <label for="xg_gfmc" class="am-u-sm-4 am-form-label"><span
                                                            style="color: red;">*</span>企业名称</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_gfmc" name="xg_gfmc" placeholder=""  required/>
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_gfsh" class="am-u-sm-4 am-form-label">购方税号</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_gfsh" name="xg_gfsh" class ="js-pattern-Taxid" placeholder="购方税号(15,18,20位数)" onkeyup="this.value=this.value.replace(/[, ]/g,'')"/>
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_gfdz" class="am-u-sm-4 am-form-label">地址</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_gfdz" name="xg_gfdz" placeholder="" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_gfdh" class="am-u-sm-4 am-form-label ">电话</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_gfdh" name="xg_gfdh" placeholder="" class="js-pattern-Telephone"/>
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_gfyh" class="am-u-sm-4 am-form-label">开户银行</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_gfyh" name="xg_gfyh" placeholder="" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_gfyhzh" class="am-u-sm-4 am-form-label">开户账号</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_gfyhzh" name="xg_gfyhzh"
                                                               placeholder="" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xq_lxr" class="am-u-sm-4 am-form-label">联系人</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_lxr" name="xq_lxr"
                                                               placeholder="" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_lxdh" class="am-u-sm-4 am-form-label">联系电话</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_lxdh" name="xg_lxdh"  class="js-pattern-Telephone"
                                                               placeholder="" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_yjdz" class="am-u-sm-4 am-form-label">邮寄地址</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_yjdz" name="xg_yjdz"
                                                               placeholder="" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="xg_email" class="am-u-sm-4 am-form-label">Email</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="xg_email" name="xg_email"
                                                               placeholder="" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="am-u-sm-12">
                                                <div class="am-form-group">
                                                    <div class="am-u-sm-12  am-text-center">
                                                        <button type="button" id="update"
                                                                class="am-btn am-btn-default am-btn-success">保存</button>
                                                        <button type="button" id="close2" class="am-btn am-btn-default am-btn-danger">关闭</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- 侧边栏内容 -->
                        <%--此处动态创建菜单栏--%>

                        <div id="doc-oc-demo3" class="am-offcanvas">
                            <div class="am-offcanvas-bar am-offcanvas-bar-flip">
                                <div class="am-offcanvas-content" style="margin-top: 5px;">
                                    <ul class="am-tree am-tree-folder-select" role="tree"
                                        id="menuTree2">
                                        <li class="am-tree-branch am-hide" data-template="treebranch"
                                            role="treeitem" aria-expanded="false">
                                            <div class="am-tree-branch-header">
                                                <button class="am-tree-icon am-tree-icon-caret am-icon-caret-right">
                                                    <span class="am-sr-only">Open</span></button>
                                                <button class="am-tree-branch-name">
                                                    <span class="am-tree-icon am-tree-icon-folder"></span>
                                                    <span class="am-tree-icon am-tree-icon-item"></span>
                                                    <span class="am-tree-label"></span>
                                                    <span class="am-tree-status"></span>
                                                </button>
                                            </div>
                                            <ul class="am-tree-branch-children" role="group"></ul>
                                            <div class="am-tree-loader" role="alert">Loading...</div>
                                        </li>
                                        <li class="am-tree-item am-hide" data-template="treeitem" role="treeitem">
                                            <button class="am-tree-item-name">
                                                <span class="am-tree-icon am-tree-icon-item"></span>
                                                <span class="am-tree-label"></span>
                                                <span class="am-tree-status"></span>
                                            </button>
                                        </li>
                                    </ul>
                                    <div style="padding: 32px;">
                                        <button type="button" id="search1"
                                                class="am-btn am-btn-default am-btn-success data-back">
                                            <span></span> 保存
                                        </button>
                                        <button type="button" id="search1"
                                                class="am-btn am-btn-default am-btn-success data-back">
                                            <span></span> 关闭
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                        <c:forEach  items="${xfList}" var="li">
                            ${li.xfdz};
                        </c:forEach>


                        <div class="am-modal am-modal-no-btn" tabindex="-3" id="hongchong">
                            <div class="am-modal-dialog"  style="overflow: auto">
                                <div class="am-modal-hd">
                                    接口配置信息 <!-- <a href="javascript: void(0)" class="am-close am-close-spin"
								data-am-modal-close>&times;</a> -->
                                </div>
                                <div class="am-modal-bd">
                                    <hr />
                                    <form action="#" class="js-form-0  am-form am-form-horizontal">
                                            <div class="am-u-sm-12">
                                                <div class="am-form-group">
                                                    <label for="jkpz_serialNumber" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>交易流水号</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_serialNumber" name="jkpz_serialNumber"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="jkpz_orderNo" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>订单号</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_orderNo" name="jkpz_orderNo" required
                                                               placeholder="订单号(15,18,20位数)" onkeyup="this.value=this.value.replace(/[, ]/g,'')" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="jkpz_orderDate" class="am-u-sm-3 am-form-label">订单日期</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_orderDate" name="jkpz_orderDate"
                                                               placeholder="" />
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="jkpz_clientNo" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>开票点代码</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_clientNo" name="jkpz_clientNo"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>


                                                <div class="am-form-group">
                                                    <label for="jkpz_version" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>商品编码版本</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_version" name="jkpz_version"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="jkpz_person" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>落款人</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_person" name="jkpz_person"
                                                               placeholder="开票人，收款人，复核人" required/>
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_seller" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>销方全信息</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_seller" name="jkpz_seller"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_items" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>商品全信息</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_items" name="jkpz_items"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>


                                                <div class="am-form-group">
                                                    <label for="jkpz_policyMsg" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>商品优惠信息</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_policyMsg" name="jkpz_policyMsg"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_payments" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>支付信息</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_payments" name="jkpz_payments"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_buyer" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>购方信息</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_buyer" name="jkpz_buyer"
                                                               placeholder="" required/>
                                                    </div>
                                                </div>


                                                <div class="am-form-group">
                                                    <label for="jkpz_remark" class="am-u-sm-3 am-form-label">备注</label>
                                                    <div class="am-u-sm-8">
                                                        <input type="text" id="jkpz_remark" name="jkpz_remark"
                                                               placeholder="" />
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_invType" class="am-u-sm-3 am-form-label" id="jkpz_invType" name="jkpz_invType"><span
                                                            style="color: red;">*</span>发票种类</label>
                                                    <div class="am-u-sm-8">
                                                        <select id="jkpz_invType" data-am-selected required>
                                                            <option value="01" selected>专票</option>
                                                            <option value="02">普票</option>
                                                            <option value="12">电子票</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_chargeTaxWay" class="am-u-sm-3 am-form-label" id="jkpz_chargeTaxWay" name="jkpz_chargeTaxWay"><span
                                                            style="color: red;">*</span>征税方式</label>
                                                    <div class="am-u-sm-8">
                                                        <select id="jkpz_chargeTaxWay" data-am-selected required>
                                                            <option value="0" selected>普通征税</option>
                                                            <option value="1">减按征税</option>
                                                            <option value="2">差额征税</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_gfyh" class="am-u-sm-3 am-form-label" id="jkpz_gfyh" name="jkpz_taxMark"><span
                                                            style="color: red;">*</span>含税标志</label>
                                                    <div class="am-u-sm-8">
                                                        <select id="jkpz_taxMark" data-am-selected required>
                                                            <option value="0" selected>不含税</option>
                                                            <option value="1">含税</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="am-form-group">
                                                    <label for="jkpz_invoiceList" class="am-u-sm-3 am-form-label"id="jkpz_invoiceList" name="jkpz_invoiceList"><span
                                                            style="color: red;">*</span>打印清单（纸票）</label>
                                                    <div class="am-form-group" id="jkpz_invoiceList" name="jkpz_invoiceList" required>
                                                        <label class="am-radio-inline">
                                                            <input type="radio" name="radio10" value="true" data-am-ucheck> 是
                                                        </label>
                                                        <label class="am-radio-inline">
                                                            <input type="radio" name="radio10" value="false" data-am-ucheck checked> 否
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="jkpz_invoiceSplit" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>自动拆分</label>
                                                    <div class="am-form-group" id="jkpz_invoiceSplit" name="jkpz_invoiceSplit" required>
                                                        <label class="am-radio-inline">
                                                            <input type="radio" name="radio10" value="true" data-am-ucheck> 是
                                                        </label>
                                                        <label class="am-radio-inline">
                                                            <input type="radio" name="radio10" value="false" data-am-ucheck checked> 否
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="am-form-group">
                                                    <label for="jkpz_invoicePrint" class="am-u-sm-3 am-form-label"><span
                                                            style="color: red;">*</span>立即打印（纸票）</label>
                                                    <div class="am-form-group" id="jkpz_invoicePrint" name="jkpz_invoicePrint" required>
                                                        <label class="am-radio-inline">
                                                            <input type="radio" name="radio10" value="true" data-am-ucheck> 是
                                                        </label>
                                                        <label class="am-radio-inline">
                                                            <input type="radio" name="radio10" value="false" data-am-ucheck checked> 否
                                                        </label>
                                                    </div>
                                                </div>

                                            </div>
                                    </form>
                                            <div class="am-u-sm-12">
                                                <div class="am-form-group">
                                                    <div class="am-u-sm-12  am-text-center">
                                                        <button type="submit" id="save"
                                                                class="am-btn am-btn-default am-btn-success">保存</button>
                                                        <button type="button"  id="close1" class="am-btn am-btn-default am-btn-danger">关闭</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                            <br><br>

                    </div>
                </div>
            </div>
            <!-- loading do not delete this -->
            <!-- content end -->
            <div
                    class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn"
                    tabindex="-1">
                <div class="am-modal-dialog">
                    <div class="am-modal-hd">正在载入...</div>
                    <div class="am-modal-bd">
                        <span class="am-icon-spinner am-icon-spin"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

</script>
<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
    <div class="am-modal-dialog">
        <div id="alertt" class="am-modal-bd">
            Hello world！
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>

<!--[if lt IE 9]>

<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.form.js"></script>
<!--<![endif]-->
<script src="assets/js/amazeui.min.js"></script>
<script src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
<script src="assets/js/amazeui.datatables.js"></script>
<script src="assets/js/amazeui.tree.js"></script>
<script src="assets/js/app.js"></script>
<script src="assets/js/format.js"></script>
<%--<script src="assets/js/gfqymp.js"></script>--%>
<script src="assets/js/sweetalert.min.js"></script>
<script src="assets/js/jkpz.js"></script>
<script>
    $(function() {
        // demo 1
        var data = [
            {
                title: 'APPLE',
                type: 'folder',
                selectedStatus: 'selected',
                attr: {
                    id: 'A_APPLE',
                    className:'first',
                },
                products: [
                    {
                        title: 'iPhone',
                        type: 'folder',
                        selectedStatus: 'selected',
                        attr: {
                            id: 'B_iPhone',
                            className:'second',
                        },
                        products: [
                            {
                                title: 'HUAWEI',
                                type: 'item',
                                attr: {
                                    id: 'C_HUAWEI',
                                    className:'third',
                                }
                            },
                            {
                                title: 'iMac',
                                type: 'item',
                                attr: {
                                    id: 'C_iMac',
                                    className:'third',
                                }
                            },
                            {
                                title: 'MacBook Pro',
                                type: 'item',
                                attr: {
                                    id: 'C_MacBookPro',
                                    className:'third',
                                }
                            }
                        ]
                    },
                    {
                        title: 'iMac',
                        type: 'item',
                        attr: {
                            id: 'B_iMac',
                            className:'second',
                        }
                    },
                    {
                        title: 'MacBook Pro',
                        type: 'item',
                        attr: {
                            id: 'B_MacBookPro',
                            className:'second',
                        }
                    }
                ]
            },
            {
                title: 'MacroSoft',
                type: 'folder',
                attr: {
                    id: 'A_MacroSoft',
                    className:'first'
                }
            },
            {
                title: 'GitHub',
                type: 'folder',
                attr: {
                    id: 'A_GitHub',
                    className:'first'
                },
            }
        ];
        var $tree2 = $('#menuTree2')
        $tree2.tree({
            dataSource: function(options, callback) {
                // 模拟异步加载
                setTimeout(function() {
                    callback({data: options.products || data});
                }, 400);
            },
            multiSelect: true,
            cacheItems: true,
            folderSelect: true
        }) .on('selected.tree.amui', function (event, data) {
            data.target.selectedStatus = 'selected'
            $el = $('#' + data.target.attr.id + ' .am-tree-status');
            $el.text('selected').css('color', 'red')

//            for (var  i = 0; i < selectList.length; i++) {
//                console.log(selectList[i].attr.id);
//            }
        }) .on('deselected.tree.amui', function (event, data) {
            data.target.selectedStatus = 'unselected'
            $el = $('#' + data.target.attr.id  + ' .am-tree-status');
            $el.text('unselected').css('color', '#000')
            console.log(data.selected);
        });




         // 发票种类
        var $selected = $('#js-selected');
        var $o = $selected.find('option[value="o"]');
        var $m = $selected.find('option[value="m"]');
        var i = 0;

        $('[data-selected]').on('click', function() {
            var action = $(this).data('selected');

            if (action === 'add') {
                $selected.append('<option value="o' + i +'">动态插入的选项 ' + i + '</option>');
                i++;
            }

            if (action === 'toggle') {
                $o.attr('selected', !$o.get(0).selected);
            }

            if (action === 'disable') {
                $m[0].disabled = !$m[0].disabled;
            }

            // 不支持 MutationObserver 的浏览器使用 JS 操作 select 以后需要手动触发 `changed.selected.amui` 事件
            if (!$.AMUI.support.mutationobserver) {
                $selected.trigger('changed.selected.amui');
            }
        });

        $selected.on('change', function() {
            $('#js-selected-info').html([
                '选中项：<strong class="am-text-danger">',
                [$(this).find('option').eq(this.selectedIndex).text()],
                '</strong> 值：<strong class="am-text-warning">',
                $(this).val(),
                '</strong>'
            ].join(''));
        });
    });
</script>
</body>
</html>