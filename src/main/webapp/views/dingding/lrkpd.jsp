<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入开票单</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black"> 
    <link rel="stylesheet" href="css/mui.css">
    <script src="js/mui.min.js"></script>
    <script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.0.0/dingtalk.js"></script>
    <script src="js/jquery.1.7.2.min.js"></script>
    <script src="js/mobiscroll_002.js" type="text/javascript"></script>
	<script src="js/mobiscroll_004.js" type="text/javascript"></script>
	<link href="css/mobiscroll_002.css" rel="stylesheet" type="text/css">
	<link href="css/mobiscroll.css" rel="stylesheet" type="text/css">
	<script src="js/mobiscroll.js" type="text/javascript"></script>
	<script src="js/mobiscroll_003.js" type="text/javascript"></script>
	<script src="js/mobiscroll_005.js" type="text/javascript"></script>
	<link href="css/mobiscroll_003.css" rel="stylesheet" type="text/css">
  </head>  
  <body>  
  	
		<div class="mui-content">
	    <div class="mui-content-padded" style="margin: 5px;">
	    <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/> 
	    <input type="hidden" id="userid" value="<c:out value="${userid}" />"/>    
	      <form class="mui-input-group">
			    <div class="mui-input-row">
			        <label>*销方名称</label>
			        <input type="text" id="xfmc" class="mui-input-clear" placeholder="上海容津信息技术有限公司">
			    </div>
			    <div class="mui-input-row">
			        <label>*合同/订单号</label>
			        <input type="text"  id="ddh" class="mui-input-clear" placeholder="请输入合同或订单号">
			    </div>
			    <div class="mui-input-row">
			        <label>*开票日期</label>
			        <input    class="mui-input-clear" readonly="readonly" name="kprq" id="kprq" type="text">
			    </div>
			</form>
			<h5 class="mui-content-padded">*发票种类</h5>
			<div class="mui-card">
				<form class="mui-input-group">
					<div class="mui-input-row mui-radio">
						<label>电子发票</label>
						<input name="radio1" id="dzfp" value="12" type="radio" checked>
					</div>
					<div class="mui-input-row mui-radio">
						<label>纸质普票</label>
						<input name="radio1" id="zzpp" value="02"  type="radio">
					</div>
					<div class="mui-input-row mui-radio">
						<label>纸质专票</label>
						<input name="radio1" id="zzzp" value="01" type="radio">
					</div>
				</form>
			</div>
	    </div> 
	    <h5 class="mui-content-padded">备注：</h5>
	    <div class="mui-input-row" style="margin: 10px 15px;">
				<textarea id="bz" rows="5" placeholder=""></textarea>
			</div>
    </div>
    
    
        <nav class="mui-bar mui-bar-tab">
			<a class="lrkpd" >
				<span class="mui-tab-label">返回</span>
			</a>
			<a class="lrkpd" id="baocun">
				<span class="mui-tab-label">下一步</span>
			</a>
		</nav>
  </body>
   
  <script>
     $(function () {
    	 var currYear = (new Date()).getFullYear();	
			  var opt={};
			  opt.date = {preset : 'date'};
			  opt.appdate = {
				theme: 'android-ics light', //皮肤样式
		        display: 'modal', //显示方式 
		        mode: 'scroller', //日期选择模式
				dateFormat: 'yyyy-mm-dd',
				lang: 'zh',
				showNow: true,
				nowText: "今天",
		        startYear: currYear - 10, //开始年份
		        endYear: currYear + 10 //结束年份
			  };
		  	$("#kprq").mobiscroll($.extend(opt['date'], opt['appdate']));
    	 var url= window.location.href;
			var corpId =$("#corpid").val();
			var signature = "";
			var nonce = "";
			var timeStamp = "";
			var agentId = "";
    	 $.ajax({
    		 url:"dinglrkpd/jssqm",
             data: {"url":url,"corpId":corpId},
             method: 'POST',
             success: function (data) {
            	  signature = data.signature;
    			  nonce = data.nonce;
    			  timeStamp = data.timeStamp;
    			  agentId = data.agentId;
    			  corpId = data.corpId;
    			  dd.config({
    					"agentId": agentId,
    					"corpId": corpId,
    					"timeStamp": timeStamp,
    					"nonceStr": nonce,
    					"signature": signature,
    					jsApiList: ['runtime.info',
    		                        'runtime.permission.requestAuthCode',
    		                        'runtime.permission.requestOperateAuthCode', //反馈式操作临时授权码

    		                        'biz.alipay.pay',
    		                        'biz.contact.choose',
    		                        'biz.contact.complexChoose',
    		                        'biz.contact.complexPicker',
    		                        'biz.contact.createGroup',
    		                        'biz.customContact.choose',
    		                        'biz.customContact.multipleChoose',
    		                        'biz.ding.post',
    		                        'biz.map.locate',
    		                        'biz.map.view',
    		                        'biz.util.openLink',
    		                        'biz.util.open',
    		                        'biz.util.share',
    		                        'biz.util.ut',
    		                        'biz.util.uploadImage',
    		                        'biz.util.previewImage',
    		                        'biz.util.datepicker',
    		                        'biz.util.timepicker',
    		                        'biz.util.datetimepicker',
    		                        'biz.util.chosen',
    		                        'biz.util.encrypt',
    		                        'biz.util.decrypt',
    		                        'biz.chat.pickConversation',
    		                        'biz.telephone.call',
    		                        'biz.navigation.setLeft',
    		                        'biz.navigation.setTitle',
    		                        'biz.navigation.setIcon',
    		                        'biz.navigation.close',
    		                        'biz.navigation.setRight',
    		                        'biz.navigation.setMenu',
    		                        'biz.user.get',

    		                        'ui.progressBar.setColors',

    		                        'device.base.getInterface',
    		                        'device.connection.getNetworkType',
    		                        'device.launcher.checkInstalledApps',
    		                        'device.launcher.launchApp',
    		                        'device.notification.confirm',
    		                        'device.notification.alert',
    		                        'device.notification.prompt',
    		                        'device.notification.showPreloader',
    		                        'device.notification.hidePreloader',
    		                        'device.notification.toast',
    		                        'device.notification.actionSheet',
    		                        'device.notification.modal',
    		                        'device.geolocation.get',]
    				});
    	    	  dd.ready(function() {
    	              document.addEventListener('pause', function() {
    	                 
    	              });
    	              document.addEventListener('resume', function() {
    	                  
    	              });
    	              
    	              document.getElementById("baocun").addEventListener('tap', function() {
    		          	var xfmc=$("#xfmc").val();   
    		          	var kprq=$("#kprq").val();
    		          	var ddh=$("#ddh").val();
    		          	var userid=$("#userid").val();
    		          	var fpzl=$("input[type='radio']:checked").val();
    		          	var bz=$("#bz").val();
    		          	if(xfmc==null||xfmc==""){
    		          		mui.alert('请输入销方名称！', function() {
    							return;
    						});
    		          	 }
    		          	if(ddh==null||ddh==""){
    		          		mui.alert('请输入订单号！', function() {
    							return;
    						});
    		          	 }
    		          	/* if(kprq==null||kprq==""){
    		          		mui.alert('请输入开票日期！', function() {
    							return;
    						});
    		          	 } */
    		          	if(fpzl==null||fpzl==""){
    		          		mui.alert('请选择发票种类！', function() {
    							return;
    						});
    		          	 }
    		          	href="dinglrgfxx?corpid="+corpId+"&userid="+userid+"&xfmc="+(xfmc)+"&ddh="+ddh+"&kprq="+kprq+"&fpzldm="+fpzl+"&bz="+(bz); 
    		          	
    		          	$("#baocun").attr("href",encodeURI(encodeURI(href)));
    		          	 });
    	          });

    	          dd.error(function(err) {
    	              alert('dd error: ' + JSON.stringify(err));
    	          });
             }
    	 });
    	 
    	
     });
  </script>
</html>  