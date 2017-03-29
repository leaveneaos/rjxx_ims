<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>绑定账号</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="<%=request.getContextPath()%>/css/mui.min.css" rel="stylesheet"/>
    <script src="assets/js/loading.js"></script>
</head>
<body>
<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title">绑定账号</h1>
</header>

<div class="mui-content">
    <form class="mui-input-group">
        <div class="mui-input-row">
            <label>手机号</label>
            <input type="text" id="mobile" class="mui-input-clear" placeholder="请输入手机号">
        </div>
        <div class="mui-input-row">
            <label>姓名</label>
            <input type="text" id="name" class="mui-input-clear" placeholder="请输入姓名">
        </div>
    </form>
    <div class="mui-content-padded">
        <button type="button" id="btnConfirm" class="mui-btn mui-btn-primary mui-btn-block">确定</button>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/js/mui.min.js"></script>
<script type="text/javascript">
    document.getElementById("btnConfirm").addEventListener("tap", function () {
        var mobile = document.getElementById("mobile");
        var name = document.getElementById("name");
        if (!mobile.value) {
            mui.toast("请输入手机号");
            return;
        }
        if (!name.value) {
            mui.toast("请输入姓名");
            return;
        }
        var obj = {
            mobile: mobile.value,
            name: mobile.value
        }
        localStorage.setItem("$state", JSON.stringify(obj));
        mui.toast("绑定成功");
        setTimeout(function () {
            mui.openWindow("<%=request.getContextPath()%>/wx/myFp");
        }, 1000)
    });
</script>
</body>
</html>
