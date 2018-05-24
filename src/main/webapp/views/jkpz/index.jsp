<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
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
    <link rel="stylesheet" href="assets/css/font-awesome.min.css"/>
    <style type="text/css">
            #menuTree2{
                font-family: "Microsoft yahei", Arial;
                font-style:normal;
            }
            #menuTree2 i{
                font-style:normal;
            }
    		ul,li{
				list-style: none;
				margin: 0px;
				padding: 0px;
			}
            ul>li>ul{
                margin: 0px;
            }
			.tree-check{
				width: 12px;
				height: 12px;
				border: 1px solid #ccc;
				color: black;
				font-size: 12px;
			}
            .jstree-top>p{
                display:inline-block;
                margin: 0px;
                padding: 0px;
            }
			.jstree-first>p{
				display:inline-block;
				margin: 0px;
				padding: 0px;
			}
			.jstree-second>p{
				display:inline-block;
				margin: 0px;
				padding: 0px;
			}
			.jstree-first-box{
				margin-left: 25px;
			}		
			.tree-slogger{
				display: inline-block;
				width: 10px;
			}	
			.click-check{
				cursor: pointer;
			}
			.tree-templatename{
				color: dodgerblue;
			}
            .tree-nowtemplatename{
                color: red;
            }
            .tree-loading{
                cursor1: wait;
                margin: 0 auto;
                width: auto;
                height: 57px;
                line-height: 57px;
                padding-left: 50px;
                padding-right: 5px;
                background: #fff url(../img/loading.gif) no-repeat scroll 5px 10px;
                border: 1px solid #ccc;
                color: #696969;
                font-family: 'Microsoft YaHei';
            }
    </style>
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
                            <strong id="yjcd" class="am-text-primary am-text-lg" style="color: #838FA1;"></strong> / <strong id="ejcd" style="color: #0e90d2;"></strong>
                        </div>

                            <!--公司搜索结果-->
                        <div class="am-g am-padding-top" >
                            <div class="am-u-sm-5">
                                <select id="jkpz_gsmc" name="jkpz_gsmc" data-am-selected="{maxHeight: '300px',btnWidth: '60%'}">
                                    <option value="">请选择</option>
                                    <c:forEach items="${gsxx}" var="item">
                                        <option value="${item.gsdm}">${item.gsmc}</option>
                                    </c:forEach>
                                </select>
                                <%--<input type="text" id="gsmc" name="gsmc" placeholder="请输入公司名称" />--%>
                                    <%--<button type="button" id="companySearch"--%>
                                        <%--class="am-btn am-btn-default am-btn-success am-btn-sm">查询</button>--%>
                            </div>
                            <div class="am-u-sm-4">
                                <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    <input type="text" class="am-form-field" id="searchValue"> <span
                                        class="am-input-group-btn">
												<button type="button" id="companySearch"
                                                        class="am-btn am-btn-default am-btn-secondary tpl-table-list-field am-icon-search"
                                                        ></button>
											</span>
                                </div>
                            </div>
                        </div>
                        <div class="am-g  am-padding-top">
                            <form action="#" class="js-search-form  am-form">
                                <div class="am-u-sm-3">
                                    <div class="am-form-group">
                                        <div class="am-btn-toolbar">
                                            <div class="am-btn-group am-btn-group-sm btn-listBox">
                                                <button type="button" id="jkpz_add"
                                                        class="am-btn am-btn-default am-btn-secondary">
                                                    新增
                                                </button>
                                                <button type="button" id="jkpz_del"
                                                        class="am-btn am-btn-default am-btn-danger">
                                                    删除
                                                </button>
                                               <%-- <button type="button" id="jkpz_xg"
                                                        class="am-btn am-btn-default am-btn-warning">
                                                    修改
                                                </button>--%>
                                                <%--<button type="button"  id="addtionSelect"class="am-btn am-btn-default am-btn-warning" data-am-offcanvas="{target: '#doc-oc-demo3'}">授权</button>--%>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                        </form>
                        <div class="am-u-sm-12 am-padding-top">
                            <div>
                                <table style="margin-bottom: 0px;" class="js-table2 am-table am-table-bordered am-table-hover am-text-nowrap"
                                       id="jkpz_table">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="check_all" /></th>
                                        <th>序号</th>
                                        <th>操作</th>
                                        <th>模板id</th>
                                        <th>模板名称</th>
                                        <th>模板描述</th>
                                        <th>公司名称</th>
                                        <th>录入人员</th>
                                        <th>录入时间</th>
                                    </tr>
                                    </thead>
                                </table>
                                <legend>模板明细</legend>
                            </div>
                        </div>
                            <input type="hidden" id="mbid">
                            <div style="margin-top: 0px; margin-left: 0px;"
                                 class="am-u-sm-12 am-scrollable-horizontal">
                                <table style="margin-bottom: 0px;"
                                       class="jkpz-js-mxtable  am-table am-table-bordered am-table-striped  am-text-nowrap"
                                       id="jkpz_table_detail">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <%--<th>模板id</th>--%>
                                        <th>参数值方法</th>
                                        <th>配置参数</th>
                                        <th>配置参数名称</th>
                                        <th>参数名称</th>

                                    </tr>
                                    </thead>
                                </table>
                            </div>

                        <!-- model -->
                        <div class="am-modal" tabindex="-1" id="modify">
                            <div class="am-modal-dialog">
                                <form class="js-form-0 am-form am-form-horizontal">
                                   <div class="am-modal-hd">
                                    接口配置信息
                                </div>
                                   <div class="am-modal-bd" style="overflow: auto; max-height: 400px;">
                                    <hr />
                                        <input type="hidden" name="mbxxid" id="mbxxid" />
                                        <div class="am-form-group">
                                            <label for="jkpz_gsdm" class="am-u-sm-3 am-form-label"><span
                                                    style="color: red;">*</span>公司</label>
                                            <div class="am-u-sm-9">
                                                <select id="jkpz_gsdm" name="jkpz_gsdm" required>
                                                    <option value="">请选择</option>
                                                    <c:forEach items="${gsxx}" var="item">
                                                        <option value="${item.gsdm}">${item.gsmc}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="am-form-group">
                                            <label for="jkpz_mbmc" class="am-u-sm-3 am-form-label"><span
                                                    style="color: red;">*</span>模板名称</label>
                                            <div class="am-u-sm-9">
                                                <input id="jkpz_mbmc"  name="jkpz_mbmc" type="text" required>
                                            </div>
                                        </div>
                                        <div class="am-form-group">
                                            <label for="jkpz_mbms" class="am-u-sm-3 am-form-label"><span
                                                    style="color: red;">*</span>模板描述</label>
                                            <div class="am-u-sm-9">
                                                <input type="text" id="jkpz_mbms" name="jkpz_mbms" required>
                                            </div>
                                        </div>
                                        <div class="am-u-sm-12">
                                            <label for="jkpz_jylsh" class="am-u-sm-3 am-form-label"><span
                                                    style="color: red;">*</span>交易流水号</label>
                                            <div class="am-u-sm-9">
                                                <input type="hidden" id="lsh_pzbid" name="lsh_pzbid"  value="1">
                                                <select id="jkpz_jylsh"  name="jkpz_jylsh"  required>
                                                    <c:forEach items="${jylsh}" var="item">
                                                        <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="am-form-group">
                                                <label for="jkpz_ddh" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>订单号</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="ddh_pzbid" name="ddh_pzbid" value="2">
                                                    <select id="jkpz_ddh" name="jkpz_ddh" required>
                                                        <c:forEach items="${ddh}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="am-form-group">
                                                <label for="jkpz_ddrq" class="am-u-sm-3 am-form-label">
                                                    <span style="color: red;">*</span>订单日期</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="ddrq_pzbid" name="ddrq_pzbid" value="3">
                                                    <select id="jkpz_ddrq" name="jkpz_ddrq" required>
                                                        <c:forEach items="${ddrq}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="am-form-group">
                                                <label for="jkpz_kpddm" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>开票点代码</label>
                                                <input type="hidden" id="kpddm_pzbid" name="kpddm_pzbid" value="4">
                                                <div class="am-u-sm-9">
                                                    <select id="jkpz_kpddm" name="jkpz_kpddm" required>
                                                        <c:forEach items="${kpddm}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_fpzl" class="am-u-sm-3 am-form-label" id="jkpz_invType" name="jkpz_invType"><span
                                                        style="color: red;">*</span>发票种类</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="fpzl_pzbid" name="fpzl_pzbid" value="5">
                                                    <select id="jkpz_fpzl" name="jkpz_fpzl" required>
                                                        <c:forEach items="${fpzl}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_dyqd" class="am-u-sm-3 am-form-label"id="jkpz_invoiceList" name="jkpz_invoiceList"><span
                                                        style="color: red;">*</span>打印清单</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="dyqd_pzbid" name="dyqd_pzbid" value="6">
                                                    <select id="jkpz_dyqd" name="jkpz_dyqd" required>
                                                        <c:forEach items="${dyqd}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="am-form-group">
                                                <label for="jkpz_zdcf" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>自动拆分</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="zdcf_pzbid" name="zdcf_pzbid" value="7">
                                                    <select id="jkpz_zdcf"  name="jkpz_zdcf" required>
                                                        <c:forEach items="${zdcf}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="am-form-group">
                                                <label for="jkpz_ljdy" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>立即打印</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="ljdy_pzbid" name="ljdy_pzbid" value="8">
                                                    <select id="jkpz_ljdy" name="jkpz_ljdy" required>
                                                        <c:forEach items="${ljdy}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_zsfs" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>征税方式</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="zsfs_pzbid" name="zsfs_pzbid" value="9">
                                                    <select id="jkpz_zsfs" name="jkpz_zsfs" required>
                                                        <c:forEach items="${zffs}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_hsbz" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>含税标志</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="hsbz_pzbid" name="hsbz_pzbid" value="10">
                                                    <select id="jkpz_hsbz" name="jkpz_hsbz" required>
                                                        <c:forEach items="${hsbz}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="am-form-group">
                                                <label for="jkpz_bz" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>备注</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="bzjkpz" name="bzjkpz" value="11">
                                                    <select id="jkpz_bz" name="jkpz_bz" required>
                                                        <c:forEach items="${bz}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_spbmbb" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>商品编码版本</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="spbmbb_pzbid" name="spbmbb_pzbid" value="12">
                                                    <select id="jkpz_spbmbb" name="jkpz_spbmbb" required>
                                                        <c:forEach items="${spbbh}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="am-form-group">
                                                <label for="jkpz_lkr" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>落款人</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="lkr_pzbid" name="lkr_pzbid" value="13">
                                                    <select id="jkpz_lkr"  name="jkpz_lkr"  required>
                                                        <c:forEach items="${lkr}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_xfqxx" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>销方全信息</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="xfqxx_pzbid" name="xfqxx_pzbid" value="14">
                                                    <select id="jkpz_xfqxx"  name="jkpz_xfqxx"  required>
                                                        <c:forEach items="${xf}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_spqxx" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>商品全信息</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="spqxx_pzbid" name="spqxx_pzbid" value="15">
                                                    <select id="jkpz_spqxx" name="jkpz_spqxx" required>
                                                        <c:forEach items="${sp}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>


                                            <div class="am-form-group">
                                                <label for="jkpz_spyhxx" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>商品优惠信息</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="spyhxx_pzbid" name="spyhxx_pzbid" value="16">
                                                    <select id="jkpz_spyhxx"  name="jkpz_spyhxx"  required>
                                                        <c:forEach items="${spyh}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_zfxx" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>支付信息</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="zfxx_pzbid" name="zfxx_pzbid" value="17">
                                                    <select id="jkpz_zfxx"  name="jkpz_zfxx"  required>
                                                        <c:forEach items="${zf}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="am-form-group">
                                                <label for="jkpz_gfxx" class="am-u-sm-3 am-form-label"><span
                                                        style="color: red;">*</span>购方信息</label>
                                                <div class="am-u-sm-9">
                                                    <input type="hidden" id="gfxx_pzbid" name="gfxx_pzbid" value="18">
                                                    <select id="jkpz_gfxx"  name="jkpz_gfxx"  required>
                                                        <c:forEach items="${gf}" var="item">
                                                            <option value="${item.id}">${item.cszff}(${item.confDesc})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>



                                </div>

                            </div>
                                   <div class="am-modal-footer">
                                           <div class="am-u-sm-12  am-text-center" style="line-height:44px">
                                               <button type="submit" class="am-btn am-btn-default am-btn-secondary"> 保存</button>
                                               <button type="button" id="close1" class="js-close am-btn am-btn-default am-btn-secondary">取消</button>
                                           </div>
                               </div>
                                </form>
                            </div>
                        </div>

                        <!-- 侧边栏内容 -->
                        <%--此处动态创建菜单栏--%>
                        <div id="doc-oc-demo3" class="am-offcanvas">
                            <div class="am-offcanvas-bar am-offcanvas-bar-flip">
                                <div class="am-offcanvas-content" style="margin-top: 5px;">
                                    <div id="menuTree2" style=""></div>
                                <%--<ul class="am-tree am-tree-folder-select" role="tree"
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
                                    </ul>--%>
                                    <div style="padding: 32px;">
                                        <button type="button" id="sqbutton"
                                                class="am-btn am-btn-default am-btn-secondary data-back">
                                            <span></span> 保存
                                        </button>
                                        <button type="button" id=""
                                                class=" am-btn am-btn-default am-btn-secondary data-back">
                                            <span></span> 关闭
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
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
<script src="assets/js/sweetalert.min.js"></script>
<script src="assets/js/jkpz.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
<%--<script src="dist/jstree.min.js"></script>--%>
<link rel="stylesheet" href="dist/themes/default/style.min.css" />
<script>
    $(function() {

//        var json=[{"id":"1075","parent":"677","state":{"selected":true},"text":"升级版测试用户3699_01"},{"id":"677","parent":"#","text":"升级版测试用户3699"},{"id":"1077","parent":"679","text":"alipay_02"},{"id":"679","parent":"#","state":{"selected":true},"text":"上海百旺测试3643"},{"id":"680","parent":"#","text":"升级版测试用户3697"}];
//        $('#menuTree2').jstree(
//            {'core':{data:null, "check_callback" : true},
//                plugins: ['state', "sort",'wholerow', 'contextmenu', 'types','checkbox'],
//                checkbox: {
//                    "keep_selected_style": true,//是否默认选中
//                    "three_state": false,//父子级别级联选择
//                    "tie_selection": false
//                },
//                state:{
//                    "key" : "demo2"
//                },
//                types: {
//                    'default': {
//                        'icon': false //设置图标
//                    },
//                    'file' : {
//                        'icon' : 'fa fa-file-text-o'//可放置css样式
//                    }
//                }
//            });
//        $('#menuTree2').jstree(true).settings.core.data=json;
//        $('#menuTree2').jstree(true).refresh();

        /*var data = [{"attr":{"id":681,"originValue":357},"products":[{"attr":{"id":1080},"selectedStatus":"deselected","title":"腾讯计算机","type":"item"},{"attr":{"id":1085},"selectedStatus":"deselected","title":"我的开票点02","type":"item"}],"selectedStatus":"selected","title":"升级版测试用户3697","type":"folder"},{"attr":{"id":683},"products":[{"attr":{"id":1124},"selectedStatus":"deselected","title":"rrrr","type":"item"}],"selectedStatus":"deselected","title":"上海百旺测试3643","type":"folder"}];
        var $tree2 = $('#menuTree2');
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
            data.target.selectedStatus = 'selected';
            $el = $('#' + data.target.attr.id + ' .am-tree-status');
            $el.text('selected').css('color', 'red');

//            for (var  i = 0; i < selectList.length; i++) {
//                console.log(selectList[i].attr.id);
//            }
        }) .on('deselected.tree.amui', function (event, data) {
            data.target.selectedStatus = 'unselected';
            $el = $('#' + data.target.attr.id  + ' .am-tree-status');
            $el.text('unselected').css('color', '#000');
            console.log(data.selected);
        });

         // 发票种类
        var $selected = $('#js-selected');
//        var $o = $selected.find('option[value="o"]');
//        var $m = $selected.find('option[value="m"]');
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
        });*/
    });
</script>
</body>
</html>