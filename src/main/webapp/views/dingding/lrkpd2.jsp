<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入开票单2</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">    
    <link rel="stylesheet" href="css/mui.css">
    <link rel="stylesheet" href="css/index.css">
    <script src="js/mui.min.js"></script>
    <script src="js/jquery.1.7.2.min.js"></script>
  </head>  
  <body>  
 
		<div class="mui-content">
			<div class="mui-card">
			   <input type="hidden"  id="sqlsh" value="<c:out value="${sqlsh}"/>"/>
				<div class="mui-card-header" id="before">
					<div class="zuo">
						<div class="z1" id="xfmc"  >上海容津信息技术有限公司（普票）</div>
						<div class="z2"  id="zcdz" >上海市漕宝路光大会展中心E座2802室</div>
					</div>
					<a href="" class="you">修改</a>
				</div>
				<!--<div class="mui-card-header">
					<div class="zuo" >
						<div class="z1">商品名称：xxxxx</div>
						<div class="z2">
							<div class="z2z">数量</div>
							<div class="z2z">税额</div>
							<div class="z2z">金额</div>
							<div class="z2z">税率</div>
						</div>
					</div>
					<a href="" class="you">修改</a>
				</div>
				 <div class="mui-card-header">
					<div class="zuo">
						<div class="z1">商品名称：xxxxx</div>
						<div class="z2">
							<div class="z2z">数量</div>
							<div class="z2z">税额</div>
							<div class="z2z">金额</div>
							<div class="z2z">税率</div>
						</div>
					</div>
					<a href="" class="you">修改</a>
				</div>
				<div class="mui-card-header">
					<div class="zuo">
						<div class="z1">商品名称：xxxxx</div>
						<div class="z2">
							<div class="z2z">数量</div>
							<div class="z2z">税额</div>
							<div class="z2z">金额</div>
							<div class="z2z">税率</div>
						</div>
					</div>
					<a href="" class="you">修改</a>
				</div> -->
			</div>
    </div>

    
    <nav class="mui-bar mui-bar-tab">
			<a class="lrkpd" style="width:50%" >
				<span class="mui-tab-label" id="jshj" >价税合计：45.00</span>
			</a>
			<a class="lrkpd" style="width:25%">
				<span class="mui-tab-label">返回</span>
			</a>
			<a class="lrkpd" href="dingqkp" style="width:25%">
				<span class="mui-tab-label">去开票</span>
			</a>
		</nav>
  </body>  
  <script>
     $(function(){
    	var sqlsh=$("#sqlsh").val();
    	alert(sqlsh);
    	 $.ajax({
        		 url:"dinglrkpd2/getjyxxsq",
                 data: {"sqlsh":sqlsh},
                 method: 'POST',
                 success: function (data) {	 
                	 $("#xfmc").html(data.jyxxsq.xfmc);
                	 $("#zcdz").html(data.jyxxsq.xfdz);
                	 var str="";
                	 var list=data.jymxsqlist;
                	 for(var i=0;i<list.length;i++){
                		 var s='<div class="mui-card-header"><div class="zuo" >'+
 						'<div class="z1">'+list[i].spmc+'</div>'+
 						'<div class="z2">'+
 							'<div class="z2z">'+list[i].sps+'</div>'+
 							'<div class="z2z">'+list[i].spse+'</div>'+
 							'<div class="z2z">'+list[i].spje+'</div>'+
 							'<div class="z2z">'+list[i].spsl+'</div>'+
 						'</div>'+
 					'</div>'+
 					'<a href="" class="you">修改</a>'+
 				'</div>';
                		 str=str+s;
                	 }
                	 $("#jshj").html("价税合计："+data.jyxxsq.jshj);
                	 $("#before").after(str); 
                 }
    	 });
     });
  </script>
</html>  