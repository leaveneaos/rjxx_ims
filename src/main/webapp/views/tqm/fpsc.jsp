<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>泰易电子发票云服务平台</title>
    <%--<meta name="viewport"--%>
    <%--content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>--%>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1"/>
    <link href="<%=request.getContextPath()%>/css/mui.min.css" rel="stylesheet"/>

    <style type="text/css">
        .am-tab-panel {
            display: none;
        }

        .am-tab-panel:nth-child(1) {
            display: block;
        }

        .active {
            background-color: #D09B66;
        }
    </style>
</head>
<%
    List<String> jpgList = (List<String>) request.getAttribute("jpgList");
%>
<script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/mui.min.js"></script>
<body>

<%--<header class="mui-bar mui-bar-nav">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>
<%--<h1 class="mui-title">发票收藏</h1>--%>
<%--</header>--%>
<div class="am-tabs mui-content" id="doc-my-tabs" style="margin: 0 auto;padding: 10px 20px;">
    <script type="text/javascript">
        if (mui.os.ios || mui.os.android) {
        } else {
            document.getElementById("doc-my-tabs").style.width = "640px";
        }
    </script>
    <ul class="am-tabs-nav am-nav am-nav-tabs am-nav-justify" style="font-size:20px;list-style:none;">
        <%
            for (int i = 0; i < jpgList.size(); i++) {

        %>
        <li class="" style="float: left;margin-right: 10px;padding: 5px 5px"><a href="#tab2-<%=i%>">第<%=i + 1%>张</a>
        </li>
        <%} %>
    </ul>
    <div style="clear: both;"></div>
    <div class="am-tabs-bd" style="margin-top: 10px;">
        <%
            for (int i = 0; i < jpgList.size(); i++) {
        %>
        <div id="tab2-<%=i%>" class="am-tab-panel" style="text-align: center;">
            <img src="<%=jpgList.get(i)%>" alt="无发票jpg信息" style="width: 100%;">
        </div>
        <% }%>
    </div>
    <p></p>
    <form id="downloadForm" action="<%=request.getContextPath()%>/tqm/download" method="post" target="_blank">
        <input type="hidden" name="gsdm" value="${gsdm}">
        <input type="hidden" name="tqm" value="${tqm}">
    </form>
    <form action="<%=request.getContextPath()%>/tqm/email" method="post" id="sendMailForm">
        <input type="hidden" name="gsdm" value="${gsdm}">
        <input type="hidden" name="tqm" value="${tqm}">
    </form>
    <iframe name="iframe1" style="display: none;"></iframe>
    <div style="padding-left: 13px;margin: 0 auto;">
        <button type="button" id="btnDownload" class="mui-btn mui-btn-primary"
                style="color: #fff;border: 1px solid #007aff;background-color: #007aff;font-size: 14px; padding: 6px 12px;border-radius: 6px;">
            下载
        </button>
        <!-- 			<button type="button" class="mui-btn mui-btn-success">微信</button> -->
        <button type="button" onclick="sendMail()"
                class="mui-btn mui-btn-warning"
                style="color: #fff;border: 1px solid #3c8a2e;background-color: #3c8a2e;font-size: 14px; padding: 6px 12px;border-radius: 6px;">
            邮箱
        </button>
        <!-- 			<button type="button" class="mui-btn mui-btn-danger">泰易发票夹</button>	 -->
    </div>
    <p></p>
</div>
<div style="text-align: center;position: fixed;bottom: 0;left: 0px;width: 100%;">技术支持：泰易电子发票云服务平台</div>
</body>
<script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<script>
    $(function () {
        $('#tab-0').show();
        $("ul.am-tabs-nav").find("li:nth-child(1)").addClass("active");
//        $('#doc-my-tabs').tabs();
        $('#doc-my-tabs').find("li").click(function () {
            var anchor = $(this).find("a").attr("href");
            $(".am-tab-panel").hide();
            $(anchor).show();
            $("ul.am-tabs-nav").find("li").removeClass("active");
            $(this).addClass("active");
        });
        if (mui.os.ios || mui.os.android || mui.os.wechat) {
            $("#btnDownload").hide();
        }
    });
    //下载发票
    document.getElementById("btnDownload").addEventListener("tap", function () {
        $("#downloadForm").submit();
    }, false);
    //发送邮件
    function sendMail() {
        $("#sendMailForm").submit();
    }
</script>
</body>
</html>