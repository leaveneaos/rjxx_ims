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
    <link rel="alternate icon" type="image/png" href="../assets/i/favicon.png">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css"/>
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
    <script type="text/javascript">
        window.onresize = function () {
            document.getElementsByTagName('html')[0].style.fontSize = (window.document.documentElement.getBoundingClientRect().width / 1920 * 100) + 'px';
        }
        document.getElementsByTagName('html')[0].style.fontSize = (window.document.documentElement.getBoundingClientRect().width / 1920 * 100) + 'px';

    </script>
</head>

<body>
<div class="_header">
    <div class="box">
        <a href="http://kpt.datarj.com/#/">
            <img src="<%=request.getContextPath()%>/img/loginimg//logo.png">
        </a>
        <ul>
            <li>
                <a class="fontSelect" href="http://kpt.datarj.com/#/">首页</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/product">产品介绍</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/getticket">领取电票</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/hysolut">行业解决方案</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/presale">售后服务</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/news/list">平台动态</a>
            </li>
        </ul>
        <div class="btnbox " styl1e="visibility: hidden;">
        <a href="http://kpt.datarj.com/#/experience">
            <button type="button" class="ant-btn ant-btn-primary">
                <span>免费体验</span>
            </button></a>
        </div>
    </div>
</div>
<div class="_login ">
    <div class="box ">
        <form action="<c:url value='/login/doLogin'/>" method="post">
            <div class="frombox ">
                <div class="header">用户登录</div>
                <div class="red" style="">${errors}</div>
                <input class="user" type="text" required name="dlyhid"  placeholder="请输入用户账号" />
                <input class="pwd" type="password" required name="yhmm" placeholder="请输入密码" />
                <div class="yzm">
                    <input type="text" name="code" class="code" placeholder="请输入验证码" required>
                    <img name="randImage" id="randImage" onclick="loadimage();"
                         src="<%=request.getContextPath()%>/image.jsp" width="100px" height="39px" border="1"
                         align="absmiddle">
                </div>
                <button type="primary" type="submit"  class="submit">立即登录</button>
                <p class="hide">还未注册？
                    <Link to="/reg">立即注册</Link>
                </p>
            </div>
        </form>
    </div>


</div>
<div class="_footer">
    <div class="box">
        <ul class="r0">
            <li>关于我们</li>
            <li>联系我们</li>
            <li>关注我们</li>
        </ul>
        <div class="r1">
            <div class="l0">
                <a href="http://www.datarj.com/" target="_blank">
                <img src="<%=request.getContextPath()%>/img/loginimg//logo.png"></a>
            </div>

            <ul class="l2">
                <li>Tel:021-33566700</li>
                <li>Fax:021-33566700</li>
                <li>Add:上海市漕宝路82号</li>
                <li>光大会展中心E座2802室</li>
            </ul>
            <ul class="l3">
                <li class="row0">
                    <img class="er0" src="<%=request.getContextPath()%>/img/loginimg//erweima.png">
                    <img class="er1" src="<%=request.getContextPath()%>/img/loginimg//erweima1.png">
                </li>
                <%--<ul class="row1">--%>
                    <%--<li>关注我们</li>--%>
                    <%--<li>关注我们</li>--%>
                <%--</ul>--%>
            </ul>
        </div>
        <div class="r2">© Copyright 2011-2015 上海容津信息技术有限公司 沪ICP备15020560号</div>
    </div>
</div>
<script>
    if (!!window.ActiveXObject || "ActiveXObject" in window)
    {
        sessionStorage.newcome12?null:location.reload()
        sessionStorage.newcome12 = 200
    }
</script>
</body>

</html>