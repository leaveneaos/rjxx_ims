<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>泰易电子发票云服务平台</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link href="<%=request.getContextPath()%>/css/mui.min.css" rel="stylesheet"/>
    <%--<script src="assets/js/jquery.min.js"></script>--%>
    <script src="assets/js/loading.js"></script>
</head>
<body>
<script src="<%=request.getContextPath()%>/assets/js/mui.min.js"></script>

<div id="zon" style="margin: 0 auto;background: #EAEAEC;" class="mui-content">
    <script type="text/javascript">
        function loadimage() {
            document.getElementById("randImage").src = '<%=request.getContextPath()%>/image.jsp?' + Math.random();
        }
        if (mui.os.ios || mui.os.android) {
        } else {
            document.getElementById("zon").style.width = "640px";
        }
    </script>
    <p></p>
    <div style="padding:0px 15px;color: red;">
        <img src="<%=request.getContextPath()%>/img/${gsdm}.png" style="width: 100%;margin-bottom: 10px;" alt=""/>
        感谢您对${gsmc}的支持，自2016年11月1日起我们将提供“增值税电子普通发票”，电子发票与纸质发票具有同等法律效力，请输入收款收据上提供的发票提取码，及时获得电子发票。
    </div>
    <p></p>
    <form class="mui-input-group" autocomplete="off" id="form1" method="post"
          action="<%=request.getContextPath()%>/tqm/fpsc">
        <div class="mui-input-row" style="height: 50px;">
            <label style="padding-top: 17px;">提取码</label>
            <input style="padding-top: 17px;" name="tqm" id="tqm" type="text" class="mui-input-clear"
                   placeholder="请输入您的提取码"
                   autocomplete="off" value="${tqm}" onkeyup="autoSubmit()">
            <input type="hidden" id="gsdm" value="${gsdm}" name="gsdm">
        </div>
        <div class="mui-input-row" style="height: 50px;">
            <input id="yzm" name="yzm" style="width: 50%;float: left;height: 100%;" type="text" placeholder="验证码"
                   autocomplete="off" onkeyup="autoSubmit()">
            <img name="randImage" id="randImage" onclick="loadimage();"
                 src="<%=request.getContextPath()%>/image.jsp" style="width: 20%;height: 100%;" border="1"
                 align="absmiddle">
        </div>
    </form>
    <p></p>
    <button type="button" onclick="searchFp();" class="mui-btn mui-btn-primary mui-btn-block">确 认</button>
    <font size="3" color="red" id="errorDiv">${error}</font>

</div>
<div style="text-align: center;position: fixed;bottom: 0;left: 0px;width: 100%;">技术支持：泰易电子发票云服务平台</div>
</body>

<script type="text/javascript">

    function autoSubmit() {
        if (event.keyCode == 13) {
            searchFp();
        }
    }

    function searchFp() {
        var gsdm = document.getElementById('gsdm').value;// 公司代码
        var tqm = document.getElementById('tqm').value; // 提取码
        var yzm = document.getElementById('yzm').value; // 验证码
        if (tqm == "") {
            document.getElementById("errorDiv").innerHTML = '请输入提取码';
            return;
        }
        if (yzm == "") {
            document.getElementById("errorDiv").innerHTML = '请输入验证码';
            return;
        }
        document.getElementById("form1").submit();
    }
</script>
</html>