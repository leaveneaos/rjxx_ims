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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/zhonkelogon.css"/>
    <script src="../assets/js/jquery.min.js"></script>
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
<!--border: solid 1px #139ae7-->
<!--[if lte IE 8]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-fixed-top">
    <div class="am-container">
        <div class="am-g am-padding-top am-padding-bottom">
            <div class="am-u-sm-4  am-padding-0">
                <img src="<%=request.getContextPath()%>/img/zhonkeLOGO.png" alt="TaxEasy" />
            </div>
            <div class="am-u-sm-7 menu  am-padding-0 am-u-sm-offset-1 ">
                <div class="am-btn-group am-btn-group-justify header-leftBox">
                    <a class="am-btn am-btn-default" href="#" role="button" style="color: #ffffff;">首页</a>
                    <a class="am-btn am-btn-default" href="" role="button">产品</a>
                    <a class="am-btn am-btn-default" href="" role="button">服务</a>
                    <a class="am-btn am-btn-default" href="" role="button">发票查验</a>
                </div>
            </div>
        </div>
    </div>
</header>

<div class="getpng">
    <div class="am-container  ">
        <div class="am-g">
            <div class="am-u-sm-12  description am-padding">
                <div class="d2  ">
                    <form class="am-form am-form-horizontal" action="<c:url value='/login/doLogin'/>" method="post">
                        <div class="am-g ">
                            <div class="am-u-sm-12  am-text-center">
                                <div class="am-u-sm-12  title">
                                    企业用户
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <div class="am-u-sm-12" style="padding: 0px;position: relative;">
                                        <span class="logonIcon usenameIcon"></span>
                                        <input type="text" name="dlyhid" class="logoinputstyle" placeholder="用户名" required>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  ">
                                <div class="am-form-group">
                                    <div class="am-u-sm-12" style="padding: 0px;position: relative;margin-top: 16px">
                                        <span class="logonIcon passwordIcon"></span>
                                        <input type="password" name="yhmm" class="logoinputstyle" placeholder="密码" required>
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12  am-text-center" style="padding: 0px;position: relative;margin-top: 16px;">
                                <span class="logonIcon checkIcon"></span>
                                <div class="am-u-sm-6">
                                    <div class="am-u-sm-12 am-padding-0">
                                        <input type="text" name="code" class="code logoinputstyle" placeholder="验证码" required>
                                    </div>
                                </div>
                                <div class="am-u-sm-6 am-text-left">
                                    <div class="am-u-sm-12 am-padding-0">
                                        <img name="randImage" id="randImage" onclick="loadimage();" src="<%=request.getContextPath()%>/image.jsp" width="100px" height="39px" border="1" align="absmiddle">
                                    </div>
                                </div>
                            </div>
                            <div class="am-u-sm-12 tj ">
                                <div class="am-form-group">
                                    <div class="am-u-sm-12" style="padding: 0;margin-top: 26px;">
                                        <button type="button" class="am-btn logon-register">新注册
                                        </button>
                                        <button type="submit" class="js-submit am-btn logon-landing">登录
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <!--<div class="am-u-sm-12  am-text-center">
                                <div class="am-u-sm-6">
                                    <%--<a href="#">忘记登陆密码?</a>--%>
                                    &nbsp;
                                </div>
                                <div class="am-u-sm-6">
                                    <a href="<%=request.getContextPath()%>/zc/zcIndex.jsp">免费注册</a>
                                </div>
                            </div>-->

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

<div class="detail">
    <div class="am-g am-container">
        <div class="am-u-lg-12">
            <h2 class="detail-h2"><span class="am-text-primary">中科联通电子发票平台的优势</span></h2>

            <div class="am-g  am-text-center logon-centerBox">
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">
                    <img src="<%=request.getContextPath()%>/img/managementfunction.png" alt="" />
                    <h3 class="detail-h3  am-text-center">
                        专业的发票管理功能
                    </h3>

                    <p class="detail-p">
                        提供电子发票开具、红冲、换开、查验、报表统计等全过程应用, 同时具备运行监控、预警提示等管理功能，支持多用户分角色的管理模式。
                    </p>
                </div>
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">
                    <img src="<%=request.getContextPath()%>/img/mobileapplication.png" alt="" />
                    <h3 class="detail-h3  am-text-center">
                        灵活的移动应用
                    </h3>

                    <p class="detail-p">
                        采用HTML5技术开发,前端支持PC、PAD、手机等多种设备,方便企业用户和消费者多种方式的接入使用。
                    </p>
                </div>
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">

                    <img src="<%=request.getContextPath()%>/img/stableguarantee.png" alt="" />
                    <h3 class="detail-h3  am-text-center">
                        基于云的性能及稳定性保障
                    </h3>

                    <p class="detail-p">
                        平台部署在阿里云上,可以弹性拓展满足电商 "双11" 等消费节日高并发的性能需求,并且支持双盘热备份的运行模式,保证系统99%的高可用性。
                    </p>
                </div>
                <div class="am-u-lg-3 am-u-md-6 am-u-sm-12 detail-mb">
                    <img src="<%=request.getContextPath()%>/img/servicemode.png" alt="" />
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
            <div style="width:100%;float: right;">
                <div style="width:100%;height:50px;line-height: 60px;text-align: center;">技术咨询热线: 010-62191980</div>
                <div style="width:100%;height:50px;line-height: 60px;text-align: center;">公司网址：
                    <a href="http://www.zhongkeliantong.com">www.zhongkeliantong.com</a>
                </div>
                <div style="width:100%;height:50px;line-height: 60px;text-align: center;">© Copyright 2018-2023 北京中科联通科技有限公司 京ICP备13015326号-1</div>
            </div>
        </div>
    </div>
</footer>
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="<%=request.getContextPath() %>/assets/js/amazeui.min.js"></script>
</body>
</html>