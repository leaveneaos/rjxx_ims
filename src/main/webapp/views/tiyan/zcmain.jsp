<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
    <meta charset="utf-8"/>
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
            margin-top: 310px;
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
        
        .ceshi div{
       		width: 200px;
            height: 400px;
        	float:left;
        	margin-left:40px;
        	border:2px solid red;
        	background-color: #FFF;
        	text-align:center;
        }
        
        .ceshi input{
        	margin-top:150%;
        	background-color: yellow;
        }
        
    </style>
      <script language="javascript">
      
      	function save(){
      		var phone=$("#id_phone").val();
      		if(phone==null || ""==phone){
      			var url="javascript:void(0)";
               	$("#qq_page").attr('href',url);
               	$("#qq_page").trigger('click'); 
               	alert("请先注册");
               	window.location.href = "zcIndex.jsp";
      		}else{
      			//var url="http://wpa.qq.com/msgrd?v=3&uin=234067123&site=qq&menu=yes";
      			var url="tencent://message/?uin=234067123&site=qq&menu=yes";
      					
              	$("#qq_page").attr('href',url);
              	$("#qq_page").trigger('click'); 
              	$.ajax({
                    type: "POST",
                    url: "../zc/ljty",
                    data: {
                  	  "phone":phone
                    },
                    success: function (data) {
                    	if(!data.issuc){
                    		var url="javascript:void(0)";
                           	$("#qq_page").attr('href',url);
                           	$("#qq_page").trigger('click'); 
                           	alert("请先注册");
                           	window.location.href = "zcIndex.jsp";
                    	}
                    }
                });
      		}
      		
      	}
      </script>
      
    <title>产品选择</title>
</head>
<body>
<div class="zhuti">
    <div class="z_zuo">
        <img src="img/2016-09-14_095041.png" alt=""/>
    </div>
    <div class="z_you">
        <div class="z_kuai">
            <a href="<%=request.getContextPath()%>/login/login" style="color: #0E90D2;">首页</a><span>&nbsp;&nbsp;&nbsp;&nbsp;/</span>
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
    <div class="ceshi">
    <input id="id_phone" type="hidden" value="${phone}">
    	<div>
    		<p>套餐一</p>
    		<!-- <input type="button" onclick="save()" value="立即体验"/> -->
    	</div>
    	<div>
			<p>套餐二</p>
			
		</div>
    	<div>
    		<p>套餐三</p>
    		<!-- <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=234067123&site=qq&menu=yes"><img onclick="save()" border="0" src="http://wpa.qq.com/pa?p=2:234067123:53" alt="容津信息助力智能电子税务一体化" title="容津信息助力智能电子税务一体化"/></a> -->
    		<a id="qq_page" href="javascript:void(0);"><img border="0" onclick="save()" border="0" src="http://wpa.qq.com/pa?p=2:234067123:53" alt="容津信息助力智能电子税务一体化" title="容津信息助力智能电子税务一体化"/></a>
    	</div>
    </div>     
            
     
     <%--        
    <div>
        <a href="<%=request.getContextPath()%>/" class="input">返回首页</a>
        <a href="http://www.datarj.com/index.php" class="input_1">了解更多</a>
    </div> --%>

</div>

</body>
</html>
