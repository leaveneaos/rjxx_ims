<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>首页</title>
    <script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.0.0/dingtalk.js"></script>
    <script src="js/mui.min.js"></script>
    <link href="css/mui.css" rel="stylesheet"/>
    <link href="css/index.css" rel="stylesheet"/>
        <script src="js/jquery.1.7.2.min.js"></script>
    
</head>
<body>
	
	<div class="mui-content">
		<ul class="mui-table-view mui-grid-view"">
		    <li class="mui-table-view-cell mui-media mui-col-xs-12">
		        <a href="#">
		        	<img class="mui-media-object" src="img/aa.png">
		        	<div class="mui-media-body">泰易开票通云服务平台</div>
		        </a>
		    </li>
		</ul>   
		<div class="jieshao">国内领先的 “销项发票管理”SaaS云服务平台；为企业用户提供“纸质发票+电子发票”全票种服务；支持航信、百望金赋两家厂商的税控服务器、税控盘接入方式；企业与税务局之间销项发票数据传输。</div>
    	<div class="t1">企业还未完成初始化信息登记，不能开具发票哦！</div>
    	<div class="t2"><a href="">不会操作？请点这里</a></div>
    	<div class="mui-button-row">
    		    <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>    
    	
        <button id='alertBtn' type="button" class="mui-btn mui-btn-primary" >进入系统</button>
			<button type="button" class="mui-btn mui-btn-success" ><a href="dinglrkpd?corpid=<c:out value="${corpid}"/>">录入开票单</a></button>
    </div>
    	
    </div>
   
		<script type="text/javascript" >
			$(function(){
			var url= window.location.href;
			var corpId =$("#corpid").val();	
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
	    	            /*   document.getElementById("alertBtn").addEventListener('tap', function() {
	    	  				mui.alert('您还不是[开票通]系统管理员，不能【进入系统】哦！', function() {
	    	  					
	    	  				});
	    	  			}); */
	    	  			dd.runtime.permission.requestAuthCode({
	    	  				corpId : _config.corpId,
	    	  				onSuccess : function(info) {
	    	  					$.ajax({
	    	  						url : '/ding/userinfo',
	    	  						data: {"code":info.code,"corpid":corpId},
	    	  				        method: 'POST',
	    	  						success : function(data) {
	    	  							
                                        alert(data.name);
                                        alert(data.userid);
	    	  							/* document.getElementById("userName").innerHTML = data.name;
	    	  							document.getElementById("userId").innerHTML = data.userid; */
	    	  						    }
	    	  					    });
	    	  				     },
	    	  				onFail : function(err) {
	    	  						alert('fail: ' + JSON.stringify(err));
	    	  					}     
	    	  				});
	    	             
	    	          });

	    	          dd.error(function(err) {
	    	              alert('dd error: ' + JSON.stringify(err));
	    	          });
	             }
	    	   });
			});
		</script>
</body>
</html>