<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="css/index.css">
    <style type="text/css">
        a, a:hover {
            text-decoration: none;
        }

        .zhuti {
            width: 1080px;
            height: 50px;
            margin: 0 auto;
            border-bottom: 1px solid #000000;
        }

        .z_zuo {
            float: left;
        }

        .z_you {
            float: right;
            width: 650px;
            height: 50px;
        }

        .z_kuai {
            width: 25%;
            height: 100%;
            float: left;
            line-height: 50px;
            font-size: 20px;
        }

        .z_kuai span {
            color: black;
        }

        .index {
            width: 1080px;
            height: 500px;
            margin: 0 auto;
            margin-top: 150px;
        }

        .top {
            widows: 100%;
            height: 50px;
            font-size: 25px;
            color: #000000;
            margin-top: 60px;
            text-align: center;
        }

        .neiron {
            width: 100%;
            height: 50px;
            font-size: 25px;
            color: #000000;
            margin-top: 110px;
            text-align: center;
        }

        .input {
            width: 230px;
            height: 50px;
            background-color: #EDEDED;
            border: 1px solid #000000;
            margin-left: 280px;
            float: left;
            line-height: 50px;
            font-weight: 700;
            color: blue;
        }

        .input_1 {
            width: 230px;
            height: 50px;
            background-color: #EDEDED;
            border: 1px solid #000000;
            margin-left: 40px;
            float: left;
            line-height: 50px;
            font-weight: 700;
            color: red;
        }
    </style>
    <title>页面选择</title>
</head>
<body>
<div class="zhuti">
    <div class="z_zuo">
        <img src="img/2016-09-14_095041.png" alt=""/>
    </div>
    <div class="z_you">
        <div class="z_kuai">
            <a href="<%=request.getContextPath()%>/" style="color: #0E90D2;">首页</a><span>&nbsp;&nbsp;&nbsp;&nbsp;/</span>
        </div>
        <div class="z_kuai">
            <a href="<%=request.getContextPath()%>/web_products.jsp" style="color: #000000;">产品</a><span>&nbsp;&nbsp;&nbsp;&nbsp;/</span>
        </div>
        <div class="z_kuai">
            <a href="<%=request.getContextPath()%>/web_services.jsp" style="color: #000000;">服务</a><span>&nbsp;&nbsp;&nbsp;&nbsp;/</span>
        </div>
        <div class="z_kuai">
            <a href="<%=request.getContextPath()%>/web_validate.jsp" style="color: #000000;">发票查验</a>
        </div>
    </div>
</div>

<div class="index">
    <div class="top">感谢您对<span style="font-weight: 700;font-size: 27px;">泰易电子发票云服务平台</span>的支持，我们的客服人员会<span
            style="font-weight: 700;font-size: 27px;color: red;">尽快与您联系</span></div>
    <div class="neiron">
        <a href="<%=request.getContextPath()%>/" class="input">返回首页</a>
        <a href="http://www.datarj.com/index.php" class="input_1">了解更多</a>
    </div>

</div>

</body>
</html>
