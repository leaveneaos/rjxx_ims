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
</head>

<body>

<div id="zon" style="margin: 0 auto;background: #EAEAEC;" class="mui-content">
    <img src="<%=request.getContextPath()%>/img/LOGO-01.png" alt="" style="width:25%;"/>
    <p></p>
    <form id="myform" class="mui-input-group" method="post">
        <div class="mui-input-row">
            <label>邮箱：</label>
            <input type="text" id="gfemail" name="gfemail" class="mui-input-clear" placeholder="请输入邮箱号">
            <input type="hidden" id="tqm" name="tqm" value="${tqm}">
            <input type="hidden" id="gsdm" name="gsdm" value="${gsdm}">
        </div>
        <div class="mui-button-row">
            <button type="button" onclick="sendEmail()" id="btnSendMail"
                    class="mui-btn mui-btn-primary btn-loading-example"
                    style="width: 100%;height: 100%;"
                    data-am-loading="{spinner: 'circle-o-notch', loadingText: '发送中...', resetText: '发送'}">发送
            </button>
            <!-- 		        <button type="button" onclick="goback(-1)" class="mui-btn mui-btn-danger" >取消</button> -->
        </div>
    </form>
    <div style="text-align: center;position: fixed;bottom: 0;left: 0px;width: 100%;">技术支持：泰易电子发票云服务平台</div>
</div>
</body>
<script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('.btn-loading-example').click(function () {
            var $btn = $(this)
            setTimeout(function () {
                $btn.button('reset');
            }, 5000);
        });
    });
    function sendEmail() {
        $("#btnSendMail").attr("disabled", true);
        var gfemail = $('#gfemail').val();
        var gsdm = $('#gsdm').val();
        var tqm = $('#tqm').val();
        var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        // 验证发票抬头
        if (gfemail == "") {
            alert("请输入您的邮箱");
            return;
        }
        if (gfemail != "" && !filter.test(gfemail)) {
            alert("输入的邮箱格式有误，请重新输入！");
            return;
        }
        $.ajax({
            url: '<%=request.getContextPath()%>/tqm/sendmail',
            data: {
                gfemail: gfemail,
                gsdm: gsdm,
                tqm: tqm
            },
            method: 'POST',
            success: function (data) {
                if (data.success) {
                    $('#gfemail').val("");
                    alert(data.msg);
                } else {
                    alert('后台错误: 邮件发送失败' + data.msg);
                }
                $("#btnSendMail").attr("disabled", false);
            },
            error: function () {
                alert('邮件发送失败, 请稍后再试!');
            }
        });

    }
</script>
	
