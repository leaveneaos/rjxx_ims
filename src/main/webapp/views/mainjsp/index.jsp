<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.css" />
<link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
<link rel="stylesheet" href="assets/css/app.css">
<script src="assets/js/loading.js"></script>
</head>
<body>
 <div class="am-g tpl-g">
		<div class="row-content am-cf">
			<div class="row">
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
					<div class="widget am-cf">
						<div class="widget-head am-cf"></div>
						<div class="widget-body  am-fr">
							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
							<ul class="am-avg-sm-1 am-avg-md-6 am-margin am-text-center">
								<%-- <li><a href="#" class="am-text-success">
										<div style="font-size: 2.5rem; color: #5677FC;">
											<span class="am-icon-list am-icon-lg" data="<%=request.getContextPath()%>/fpkc" onclick="jump(this,'/fpkc')">
											</span>
											<br>
											发票库存查询
										</div>
								</a></li> --%>
								<c:if test="${kplscl==1}">
								<li><a href="#" class="am-text-success">
										<div style="font-size: 2.5rem; color: #03A9F4;">
											<span class="am-icon-pencil-square am-icon-lg" data="<%=request.getContextPath()%>/kpdshxb" onclick="jump(this,'/kpdshxb')"></span><br>
											开票流水处理
										</div>
								</a></li></c:if>
								<c:if test="${fpcx==1}">
								<li><a href="#" class="am-text-success">
										<div style="font-size: 2.5rem; color: #00BCD4;">
											<span class="am-icon-search am-icon-lg" data="<%=request.getContextPath()%>/fpcx" onclick="jump(this,'/fpcx')"></span><br> 
											发票查询
										</div>
								</a></li></c:if>
								<c:if test="${ytjbb==1}">
								<li><a href="#" class="am-text-success">
										<div style="font-size: 2.5rem; color: #009688;">
											<span class="am-icon-bar-chart am-icon-lg" data="<%=request.getContextPath()%>/fytjbb" onclick="jump(this,'/fytjbb')"></span><br>
											月统计报表
										</div>
								</a></li></c:if>
                                <c:if test="${fpgd==1}">
								<li><a href="#" class="am-text-success">
										<div style="font-size: 2.5rem; color: #259B24">
											<span class="am-icon-archive am-icon-lg"  data="<%=request.getContextPath()%>/fpgdcx" onclick="jump(this,'/fpgdcx')"></span><br>
											发票归档
										</div>
								</a></li></c:if>
							</ul>
							</div>
							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
								<div class="widget am-cf">
									<div class="widget-body  widget-body-lg am-fr">
										<table style="width:100%"
											class="am-table am-table-compact tpl-table-black "
											id="example-r">
											<thead>
												<tr>
													<th>系统公告</th>
													<th></th>
													<th></th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<tr class="gradeX">
													<td><a href="https://mp.weixin.qq.com/s?__biz=MzAxMTg2MzQ3NQ==&mid=2247483912&idx=1&sn=9a2316b7e5bf7744fabf32a29147f97c&chksm=9bbbd6d4accc5fc2f99d56b14b4f1fc67bdb186c642ae08b5884bd6283c1ac3e0bae59f5cff1#rd 
													">【泰易】开票通云服务平台2.0重磅发布</a></td>
													<td></td>
													<td>2017-03-01</td>
												</tr>												
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/theme.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script src="assets/js/amazeui.datatables.min.js"></script>
	<script src="assets/js/dataTables.responsive.min.js"></script>
	<script src="assets/js/app.js"></script>
   <script type="text/javascript">
   function jump(da,url){
	   $.ajax({
		   url:'mainjsp/getName',
		   data:{'url':url},
		   method:'POST',
		   success:function(data){
			   var id = data.name;
			   $("#"+id,parent.document).css('display','block'); 
		   }
	   })
		var v_id = $(da).attr('data');
    	$(".ejcd",parent.document).css('background','none');
    	var divs = $('.ejcd',parent.document);
    	for(var i=0;i<divs.length;i++){
    		if($(divs[i]).attr('data')==v_id){
    			$(divs[i]).css({"background-color":"#f2f6f9","color":"#000"});
    			$("#cd1",parent.document).val($(divs[i]).attr("dele"));
				$("#cd2",parent.document).val($(divs[i]).attr("parname"));
    		}
    	}
   	    $("#mainFrame",parent.document).attr("src",v_id); 
	}
   </script>
</body>
</html>