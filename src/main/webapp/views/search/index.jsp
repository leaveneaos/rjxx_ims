<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="css/mui.min.css" rel="stylesheet" />
		 <style>
            .state1{
                color:#aaa;
            }
            .state2{
                color:#000;
            }
            .state3{
                color:red;
            }
            .state4{
                color:green;
            }
        </style>
	</head>

	<body>
		<script src="js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init()
		</script>
	</body>
	<img src="img/LOGO-01.png" alt="" style="width:25%;height: 25%" />
	<p></p>
    <form id="myform" class="mui-input-group" method="post" action="SearchServlet">
    <div class="mui-input-row" style="height: 50px;">
        <label style="padding-top: 17px;">发票抬头</label>
        <input style="padding-top: 17px;" id="gfmc" type="text" name="gfmc">
    </div>
    <div class="mui-input-row" style="height: 50px;">
        <label style="padding-top: 17px;">金额</label>
        <input style="padding-top: 17px;" id="fpje" type="text" name="fpje">
    </div>
    <div class="mui-input-row" style="height: 50px;">
        <label style="padding-top: 17px;">开票方</label>
        <input style="padding-top: 17px;" id="xfmc" type="text" name="xfmc">
    </div>
<button type="submit" class="mui-btn mui-btn-primary mui-btn-block submit">确 认</button>
</form>
    <p></p>
	<script src="assets/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#myform").submit(function(){
			var flag = true;
	        var gfmc = $('#gfmc').val();
	        var xfmc = $('#xfmc').val();
	        var fpje = $('#fpje').val();
	        // 验证发票抬头
	        if (gfmc == "") {
	        	alert("请输入发票抬头"); 
	        	flag = false;
	        	return;
			}
	        // 验证发票金额
// 	        if (fpje == "") {
// 	        	alert("请输入金额"); 
// 	        	flag = false;
// 	        	return;
// 			}
	        if (fpje != "" && isNaN(fpje)) {
	        	alert("金额格式有误"); 
	        	flag = false;
	        	return;
			}
	        // 验证开票方
	        if (xfmc == "") {
	        	alert("请输入开票方"); 
	        	flag = false;
	        	return;
			}
	
	        //提交按钮,所有验证通过方可提交
	        $('.submit').click(function(){
	             if (flag) {
	            	  $('form').submit();
				 }
	        });
        });
	})
</script>

</html>