<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入购方信息</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">    
  
    <link rel="stylesheet" href="css/mui.min.css">
    <script src="js/mui.min.js"></script>
    
  </head>  
  <body>  
  	<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"><span style="font-size: 15px;">开票通</span></a>
		</header>
		<div class="mui-content">
	    <div class="mui-content-padded" style="margin: 5px;">    
	      <form class="mui-input-group">
			    <div class="mui-input-row">
			        <label>*购方名称</label>
			        <input type="text" class="mui-input-clear" placeholder="发票抬头">
			    </div>
			    <div class="mui-input-row">
			        <label>*纳税人识别号</label>
			        <input type="text" class="mui-input-clear" placeholder="本单位纳税人识别号，15位至20位">
			    </div>
			    <div class="mui-input-row">
			        <label>*注册地址</label>
			        <input type="text" class="mui-input-clear" placeholder="销售方注册地址（发票票面左下角显示）">
			    </div>
			    <div class="mui-input-row">
			        <label>*注册电话</label>
			        <input type="text" class="mui-input-clear" placeholder="销售方注册电话，如021-55571833">
			    </div>
			    <div class="mui-input-row">
			        <label>*开户银行</label>
			        <input type="text" class="mui-input-clear" placeholder="销售方开户银行">
			    </div>
			    <div class="mui-input-row">
			        <label>*银行账户</label>
			        <input type="text" class="mui-input-clear" placeholder="销售方银行账户">
			    </div>
			</form>
			
	    </div> 
    </div>

			<div class="mui-card">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell mui-collapse">
						<a class="mui-navigate-right" href="#">表单</a>
						<div class="mui-collapse-content">
							<form class="mui-input-group">
								<div class="mui-input-row">
									<label>联系人</label>
									<input type="text" class="mui-input-clear" placeholder="收件人姓名">
								</div>
								<div class="mui-input-row">
									<label>联系电话</label>
									<input type="text" class="mui-input-clear" placeholder="收件人联系电话">
								</div>
								<div class="mui-input-row">
									<label>联系地址</label>
									<input type="text" class="mui-input-clear" placeholder="收件人详细地址">
								</div>
								<div class="mui-input-row">
									<label>邮件地址</label>
									<input type="text" class="mui-input-clear" placeholder="收件人邮箱地址，提示用户发票已开具">
								</div>
								<div class="mui-input-row">
									<label>提取码</label>
									<input type="text" class="mui-input-clear" placeholder="用户提取电子发票时使用">
								</div>
										<li class="mui-table-view-cell">
											<span>订单/合同号作为提取码</span>
											<div class="mui-switch mui-switch-blue mui-switch-mini">
												<div class="mui-switch-handle"></div>
											</div>
										</li>
									
								<div class="mui-input-row">
									
								</div>
							</form>
						</div>
					</li>
					

				</ul>
			</div>
		
    
    <nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item" >
				<span class="mui-tab-label">返回</span>
			</a>
			<a class="mui-tab-item" onclick="lrspxx();">
				<span class="mui-tab-label">下一步</span>
			</a>
		</nav>
  </body>  
  <script>
     function lrspxx(){
    	 window.location.href="dinglrspxx";
     }
  
  </script>
</html>  