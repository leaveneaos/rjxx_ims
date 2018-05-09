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
    </style>
    <title>注册页面</title>
    <script language="javascript">

    //注册
        function zhuCe(){
        	var phone = $("#zc_phone").val();
        	if(phone==null || phone==""){
				alert("请输入手机号");
				return;
			}
        	
        	var reg = /^1[0-9]{10}$/;
        	if(!reg.test(phone.trim())){
        		alert("请输入有效手机号");
        		return;
        	}
        	var code =$("#zc_code").val();
        	
        	if(code==null || code==""){
        		alert("请输入验证码");
        		return;
        	}else{
        		var reg2 = /^[0-9]{6}$/;
        		if(!reg2.test(code.trim())){
        			alert("请输入正确的验证码");
        			return;
        		}
        	}
        	
        	 $.ajax({
                 type: "POST",
                 url: "../zc/zhuce",
                 data: {
               	  "phone":phone,
               	  "code":code
                 },
                 success: function (data) {
                     if (data.success) {
                    	 //成功跳转到指定页面
                    	// window.location.href = "zcmain.jsp?phone="+phone
                    	 var f=document.createElement('form');
                         f.style.display='none';
                         f.action='../zc/zcmain';
                         f.method='post';
                         f.innerHTML='<input type="hidden" name="phone" value="'+phone+'"/>';
                         document.body.appendChild(f);
                         f.submit();
                     } else {
                         alert(data.msg);
                     }
                 }
             });
        }
        
        
        //----验证码设置
        var clock = '';
 		var nums = 90;
        var btn;
		function sendCode(thisBtn) {
			var phone = $("#zc_phone").val();
			if(phone==null || phone==""){
				alert("请输入手机号");
				return;
			}
        	var reg = /^1[0-9]{10}$/;
        	if(!reg.test(phone.trim())){
        		alert("请输入有效手机号");
        		return;
        	}
			
			 $.ajax({
                 type: "POST",
                 url: "../zc/getYzm",
                 data: {
               	  "phone":phone
                 },
                 success: function (data) {
                     if (data.success) {
                    	 btn = thisBtn;
             			btn.disabled = true; //将按钮置为不可点击
             			btn.value = nums + '秒后可重新获取';
             			clock = setInterval(doLoop, 1000); //一秒执行一次
                     } else {
                         alert(data.msg);
                     }
                 }
             });
		}
		function doLoop() {
			nums--;
			if (nums > 0) {
				btn.value = nums + '秒后可重新获取';
			} else {
				clearInterval(clock); //清除js定时器
				btn.disabled = false;
				btn.value = '点击获取验证码';
				nums = 90; //重置时间
			}
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
                <p><label>手机号：</label><input placeholder="请输入手机号（必填）" type="text" id="zc_phone" name="phone"/></p>
                <p><label>验证码：</label><input placeholder="请输入验证码（必填）" style="width: 30%;" type="text" id="zc_code" name="code"/><input type="button" onclick="sendCode(this)" style="width: 23%;height:51px" value="点击获取验证码"/></p>
                <div style="clear: both;"></div>
                <p style="width: 100%;text-align: center;">
                    <input type="button" onclick="zhuCe()" value="注 册"/>
                </p>
        </div>
        <div id="u151" class="ax_形状">

            <!-- Unnamed () -->
            <div style="color: red;font-size: 18px;margin-left: 18px;text-align: center;">
                <p>咨询电话：021-55571833</p>
                <p>服务热线：021-33566700</p>
            </div>
        </div>

    </div>
</div>

</body>
</html>
