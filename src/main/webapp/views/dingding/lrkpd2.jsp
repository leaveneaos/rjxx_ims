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
			   <input type="hidden"  id="corpid" value="<c:out value="${corpid}"/>"/>
			   <input type="hidden"  id="userid" value="<c:out value="${userid}"/>"/>
			   
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
			<a class="lrkpd" id="qkp" style="width:25%">
				<span class="mui-tab-label">去开票</span>
			</a>
		</nav>
  </body>  
  <script>
     $(function(){
    	var sqlsh=$("#sqlsh").val();
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
 							'<div class="z2z">'+list[i].jshj+'</div>'+
 							'<div class="z2z">'+list[i].spbz+'</div>'+
 							/* '<div class="z2z">'+list[i].spje+'</div>'+
 							'<div class="z2z">'+list[i].spsl+'</div>'+ */
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
	    	    		  var userid="";
	    	              document.addEventListener('pause', function() {
	    	                 
	    	              });
	    	              document.addEventListener('resume', function() {
	    	                  
	    	              });
	    	          
	    	  			dd.runtime.permission.requestAuthCode({
	    	  				corpId : corpId,
	    	  				onSuccess : function(info) {
	    	  					$.ajax({
	    	  						url : 'ding/userinfo',
	    	  						data: {"code":info.code,"corpid":corpId},
	    	  				        method: 'POST',
	    	  						success : function(data) {
	    	  							userid=data.userid;

	    	  						    }
	    	  					    });
	    	  				     },
	    	  				onFail : function(err) {
	    	  						alert('fail: ' + JSON.stringify(err));
	    	  					}     
	    	  				});
	    	  			 dd.runtime.permission.requestOperateAuthCode({
	    	  			        corpId: corpId,
	    	  			        agentId:agentId,
	    	  			    onSuccess: function(result) {
	  							$("#qkp").attr("href","dingqkp?corpid="+corpId+"&sqlsh="+sqlsh+"&code="+result.code+"&userid="+userid+"&agentId="+agentId);
	    	  			    	/* $.ajax({
	    	  						url : 'ding/sendmessage',
	    	  						data: {"code":result.code,"corpid":corpId,"agentId":agentId,"userid":userid},
	    	  				        method: 'POST',
	    	  						success : function(data) {
	    	  						    if(data=="success"){
	    	  						    	alert("sssss");
	    	  						       }
	    	  						    }
	    	  					    }); */
	    	  			    },
	    	  			    onFail : function(err) {
	    	  			    	
	    	  			    },
	    	  			 
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