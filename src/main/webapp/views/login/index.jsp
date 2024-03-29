<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>泰易电子发票云服务平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="/i/favicon.png">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/amazeui.min.css"/>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/web.css"/>
    <script src="../assets/js/jquery.min.js"></script>
    <script src="assets/js/loading.js"></script>
    <script language="javascript">
        if(window !=top){
            top.location.href=location.href;
        }
        function loadimage() {
            document.getElementById("randImage").src = "<%=request.getContextPath()%>/image.jsp?" + Math.random();
        }
    </script>
</head>
<body>

<!--[if lte IE 8]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-fixed-top">
    <div class="am-container">
        <div class="am-g am-padding-top am-padding-bottom">
            <div class="am-u-sm-4  am-padding-0">
                <img src="<%=request.getContextPath()%>/img/logo.png" alt="TaxEasy"/>
            </div>
            <div class="am-u-sm-8 menu  am-padding-0">
                <div class="am-btn-group am-btn-group-justify">
                    <a class="am-btn am-btn-default  am-text-primary" href="#" role="button"
                       style="color: #0e90d2;">首页</a>/
                    <a class="am-btn am-btn-default" href="<%=request.getContextPath()%>/web_products.jsp"
                       role="button">产品</a>/
                    <a class="am-btn am-btn-default" href="<%=request.getContextPath()%>/web_services.jsp"
                       role="button">服务</a>/
                    <a class="am-btn am-btn-default" href="<%=request.getContextPath()%>/web_validate.jsp"
                       role="button">发票查验</a>
                </div>
            </div>
        </div>
    </div>
</header>

<div class="get ">
    <div class="am-container  ">
        <div class="am-g">
            <div class="am-u-sm-12  description am-padding">
                <h1>欢迎来到 TaxEasy</h1>
                <p>专注税务服务, 深谙电子发票应用</p>
                <p>成长路上, 感谢您的支持陪伴</p>
                <div class="dl  ">
                    <form class="am-form am-form-horizontal" action="<c:url value='/login/doLogin'/>" method="post">
                        <div class="am-g ">
                            <div class="am-u-sm-12  am-text-center">
                                <div class="am-u-sm-12  title">
                                    <div class="am-form-group  am-margin-0 ">
                                        <div class="am-u-sm-12">
                                            <h2>企业用户</h2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <div class="am-u-sm-12">
                                        <font color="red">${errors}</font>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <div class="am-u-sm-12">
                                        <input type="text" name="dlyhid" class="" placeholder="请输入用户名" required>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <div class="am-u-sm-12">
                                        <input type="password" name="yhmm" class="" placeholder="请输入密码" required>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  am-text-center">
                                <div class="am-u-sm-6">
                                    <div class="am-u-sm-12 am-padding-0">
                                        <input type="text" name="code" class="code" placeholder="请输入验证码" required>
                                    </div>
                                </div>
                                <div class="am-u-sm-6 am-text-left">
                                    <div class="am-u-sm-12 am-padding-0">
                                        <img name="randImage" id="randImage" onclick="loadimage();"
                                             src="<%=request.getContextPath()%>/image.jsp" width="100px" height="39px" border="1"
                                             align="absmiddle">
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12 tj ">
                                <div class="am-form-group">
                                    <div class="am-u-sm-12">
                                        <button type="submit" class="js-submit am-btn am-btn-warning am-btn-block">登陆
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  am-text-center">
                                <div class="am-u-sm-6">
                                    <%--<a href="#">忘记登陆密码?</a>--%>
                                    &nbsp;
                                </div>
                                <div class="am-u-sm-6">
                                    <a href="<%=request.getContextPath()%>/zc/zcIndex.jsp">免费注册</a>
                                </div>
                            </div>

                        </div>
                    </form>
                </div>
                <div class="dl_back  ">
                    &nbsp;
                </div>
            </div>

        </div>
    </div>
</div>

<div class="tzgg ">
    <div class="am-g  ">
        <div class="am-u-sm-1">
            &nbsp;
        </div>
        <div class="am-u-sm-10">
            <table class="am-table  am-table-radius am-table-striped">
                <thead>
                <tr>
                    <th colspan="4" class="am-primary">通知公告</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>2016-02-29</td>
                    <td><span class="am-badge am-badge-danger  am-round">HOT</span></td>
                    <td>上海市增值税电子普通发票企业端业务规范（暂行）</td>
                    <td><a href="http://www.tax.sh.gov.cn/pub/ssxc/zlzy/zcgll/zzsdzptfp/xgwj/201602/t20160229_422129.html"
                           target="_blank">查看详情</a></td>
                </tr>
                <tr>
                    <td>2015-11-26</td>
                    <td><span class="am-badge am-badge-danger  am-round">HOT</span></td>
                    <td>关于推行通过增值税电子发票系统开具的增值税电子普通发票有关问题的公告</td>
                    <td><a href="http://www.chinatax.gov.cn/n810341/n810755/c1919901/content.html"
                           target="_blank">查看详情</a></td>
                </tr>
                <tr>
                    <td>2015-11-30</td>
                    <td><span class="am-badge am-badge-danger  am-round">HOT</span></td>
                    <td>关于《国家税务总局关于推行通过增值税电子发票系统开具的增值税电子普通发票有关问题的公告》的解读</td>
                    <td><a href="http://www.chinatax.gov.cn/n810341/n810760/c1919925/content.html"
                           target="_blank">查看详情</a></td>
                </tr>
                <tr>
                    <td>2015-09-26</td>
                    <td><span class="am-badge am-badge-success  am-round">NEW</span></td>
                    <td>国务院关于加快构建大众创业万众创新
                        支撑平台的指导意见
                    </td>
                    <td><a href="http://www.gov.cn/zhengce/content/2015-09/26/content_10183.htm"
                           target="_blank">查看详情</a></td>
                </tr>
                <tr>
                    <td>2015-12-15</td>
                    <td><span class="am-badge am-badge-success  am-round">NEW</span></td>
                    <td>关于做好个人所得税金税三期客户端上线工作的通知</td>
                    <td><a href="http://www.tax.sh.gov.cn/pub/xxgk/zcfg/grsds/201512/t20151218_420740.html"
                           target="_blank">查看详情</a></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="am-u-sm-1">
            &nbsp;
        </div>
    </div>

</div>

<div class="detail">
    <div class="am-g am-container">
        <div class="am-u-lg-12">
            <h2 class="detail-h2"><span class="am-text-primary">TaxEasy 的优势</span></h2>

            <div class="am-g  am-text-center">
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">
                    <img src="<%=request.getContextPath()%>/img/index-1.gif" alt=""/>
                    <h3 class="detail-h3  am-text-center">
                        专业的发票管理功能
                    </h3>

                    <p class="detail-p">
                        提供电子发票开具、红冲、换开、查验、报表统计等全过程应用, 同时具备运行监控、预警提示等管理功能，支持多用户分角色的管理模式。
                    </p>
                </div>
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">
                    <img src="<%=request.getContextPath()%>/img/index-2.gif" alt=""/>
                    <h3 class="detail-h3  am-text-center">
                        灵活的移动应用
                    </h3>

                    <p class="detail-p">
                        采用HTML5技术开发,前端支持PC、PAD、手机等多种设备,方便企业用户和消费者多种方式的接入使用。
                    </p>
                </div>
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">

                    <img src="<%=request.getContextPath()%>/img/index-3.gif" alt=""/>
                    <h3 class="detail-h3  am-text-center">
                        基于云的性能及稳定性保障
                    </h3>

                    <p class="detail-p">
                        平台部署在阿里云上,可以弹性拓展满足电商 "双11" 等消费节日高并发的性能需求,并且支持双盘热备份的运行模式,保证系统99%的高可用性。
                    </p>
                </div>
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">
                    <img src="<%=request.getContextPath()%>/img/index-4.gif" alt=""/>
                    <h3 class="detail-h3  am-text-center">
                        以服务方式交付
                    </h3>

                    <p class="detail-p">
                        缩短建设周期,快速上线应用,成本只有以前的十分之一。
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>


<footer class="footer">
    <div class="am-container">
        <div class="am-g">

            <div style="width:60%;float: right;">
                <div style="width:100%;height:50px;line-height: 60px;text-align: left;">技术咨询热线: 021-5571833</div>
                <div style="width:100%;height:50px;line-height: 60px;text-align: left;">公司网址：<a href="http://www.datarj.com/index.php">http://www.datarj.com/index.php</a></div>
                <div style="width:100%;height:50px;line-height: 60px;text-align: left;">© Copyright 2011-2015 上海容津信息技术有限公司 沪ICP备15020560号</div>
            </div>
            <div style="width:20%;float: right;">
                <img src="<%=request.getContextPath()%>/img/fw.jpg" alt="" style="width:120px;height:120px;"/>
                <p>服务号</p>

            </div>
            <div style="width:20%;float: right;">
                <img src="<%=request.getContextPath()%>/img/dy.jpg" alt="" style="width:120px;height:120px;"/>
                <p>订阅号</p>
            </div>
            <!-- <div class="am-u-sm-3">
                <h1><span class="am-icon-phone-square"> </span></h1>
            </div>
            <div class="am-u-sm-3">
                <h1><span class="am-icon-weibo"></span> &nbsp;<span class="am-icon-weixin"></span></h1>
            </div>
            <div class="am-u-sm-3  am-text-sm  am-text-left">
                <p>Email: service@datarj.com</p>
                <p>Web: www.datarj.com</p>
            </div>
            <div class="am-u-sm-3  am-text-sm  am-text-left">
                <p>技术咨询热线: </p>
                <p>电话: 021-5571833</p>
            </div> -->
        </div>
    </div>
    <!-- <p class="am-text-sm">© Copyright 2011-2015 上海容津信息技术有限公司 沪ICP备15020560号</p> -->
</footer>
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="<%=request.getContextPath() %>/assets/js/amazeui.min.js"></script>
</body>
</html>