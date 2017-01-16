<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8"/>
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/js/amazeui.min.js"></script>
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
            height: 800px;
            margin: 0 auto;
        }

        .i_l {
            width: 600px;
            height: 100%;
            float: left;
        }

        .demo p {
            height: 60px;
            line-height: 60px;
            margin-left: 15px;
            overflow: auto;
            zoom: 1;
        }

        .demo label {
            font-weight: 700;
            font-size: 18px;
            display: inline-block;
            width: 200px;
            text-align: right;
            float: left;
        }

        .demo input[type=text] {
            width: 300px;
            height: 45px;
            font-family: 'Arial Normal', 'Arial';
            font-style: normal;
            font-size: 18px;
            text-decoration: none;
            color: #000000;
            text-align: left;
            float: left;
        }

        .demo input[type=button] {
            width: 200px;
            height: 45px;
            font-family: 'Arial Normal', 'Arial';
            font-style: normal;
            font-size: 18px;
            text-decoration: none;
            color: #000000;
            text-align: center;
        }

        .demo select {
            width: 300px;
            height: 45px;
            font-family: 'Arial Normal', 'Arial';
            font-style: normal;
            font-size: 18px;
            text-decoration: none;
            color: #000000;
            text-align: left;
            float: left;
        }

        .i_r {
            width: 478px;
            height: 100%;
            float: right;
        }
    </style>
    <title>注册页面</title>
    <script language="javascript">
        function save() {
            $.ajax({
                type: "POST",
                url: "../zc/saveZcxx",
                data: $('#myform').serialize(),
                success: function (data) {
                    if (data.success) {
                        alert(data.msg);
                        window.location.href = "index2.jsp"
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    </script>
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
    <div class="i_l">
        <div class="demo">
            <form id="myform" method="post">
                <p><label>企业名称：</label><input placeholder="请输入企业名称（必填）" type="text" name="qymc"/></p>
                <p><label>企业税号：</label><input placeholder="请输入企业税号（必填）" type="text" name="qysh"/></p>
                <p><label>联系电话：</label><input placeholder="请输入您的联系电话（必填）" type="text" name="lxdh"/></p>
                <p><label>邮箱：</label><input placeholder="请输入邮箱"
                                            type="text" name="email"/>
                </p>
                <p><label>联 系 人：</label><input placeholder="请输入您的姓名（必填）" type="text" name="lxr"/></p>
                <p><label>职位：</label>
                    <select name="lxrzw">
                        <option value="1">财务</option>
                        <option value="2">IT主管</option>
                        <option value="3">行政人员</option>
                        <option value="4">管理人员</option>
                        <option value="5">其他</option>
                    </select>
                </p>
                <p><label>开票量：</label>
                    <select name="kpl">
                        <option value="1">＜500张/月</option>
                        <option value="2">500~1000张/月</option>
                        <option value="3">＞1000张/月</option>
                    </select>
                </p>
                <div style="clear: both;"></div>
                <p style="width: 100%;text-align: center;">
                    <input type="button" onclick="save()" value="提  交"/>
                </p>
            </form>
        </div>
        <div id="u151" class="ax_形状">

            <!-- Unnamed () -->
            <div style="color: red;font-size: 18px;margin-left: 18px;text-align: center;">
                <p>咨询电话：021-55571833</p>
                <p>服务热线：021-33566700</p>
            </div>
        </div>

    </div>
    <div class="i_r">
        <img src="img/2016-09-14_112340.png" alt=""/>
    </div>
</div>

</body>
</html>
