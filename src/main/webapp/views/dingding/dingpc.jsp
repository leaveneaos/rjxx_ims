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
     <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>
	 <div class="mui-content">
			 <ul class="mui-table-view mui-grid-view">
			 <li class="mui-table-view-cell mui-media mui-col-xs-12">
				 <a href="#">
					 <img src="img/64.png">
					 <div class="mui-media-body">泰易开票通云服务平台</div>
				 </a>
			 </li>
			 </ul>
			 <div class="jieshao">开票通2.0，发票管理专家，从此开票再无忧</div>
			 <hr />
			 <br /><br /><br />
			 <div class="mui-button-row">
				 <button id='alertBtn' type="button" class="mui-btn mui-btn-primary" ><a href="http://test.datarj.com/ims/login/login">进入系统</a></button>
			 </div>
			 <div class="t1">或复制以下网址到浏览器登录</div>
			 <div class="t2">http://test.datarj.com/ims/login/login</div>
			 <div class="t2"><a href="">联系我们</a></div>
			 <div class="t2"><a href="">021-5571833</a></div>

	 </div>
		<script type="text/javascript" >
			$(function(){
			var url= window.location.href;
			var corpid =$("#corpid").val();

			var signature = "";
			var nonce = "";
			var timestamp = "";
			var agentid = "";
			 $.ajax({
	    		 url:"dinglrkpd/jssqm",
	             data: {"url":url,"corpid":corpid},
	             method: 'post',
	             success: function (data) {
	            	  signature = data.signature;
	    			  nonce = data.nonce;
	    			  timestamp = data.timestamp;
	    			  agentid = data.agentid;
	    			  corpid = data.corpid;
	    			  dd.config({
	    					"agentid": agentid,
	    					"corpid": corpid,
	    					"timestamp": timestamp,
	    					"noncestr": nonce,
	    					"signature": signature,
	    					jsapilist: ['runtime.info',
	    		                        'runtime.permission.requestauthcode',
	    		                        'runtime.permission.requestoperateauthcode', //反馈式操作临时授权码

	    		                        'biz.alipay.pay',
	    		                        'biz.contact.choose',
	    		                        'biz.contact.complexchoose',
	    		                        'biz.contact.complexpicker',
	    		                        'biz.contact.creategroup',
	    		                        'biz.customcontact.choose',
	    		                        'biz.customcontact.multiplechoose',
	    		                        'biz.ding.post',
	    		                        'biz.map.locate',
	    		                        'biz.map.view',
	    		                        'biz.util.openlink',
	    		                        'biz.util.open',
	    		                        'biz.util.share',
	    		                        'biz.util.ut',
	    		                        'biz.util.uploadimage',
	    		                        'biz.util.previewimage',
	    		                        'biz.util.datepicker',
	    		                        'biz.util.timepicker',
	    		                        'biz.util.datetimepicker',
	    		                        'biz.util.chosen',
	    		                        'biz.util.encrypt',
	    		                        'biz.util.decrypt',
	    		                        'biz.chat.pickconversation',
	    		                        'biz.telephone.call',
	    		                        'biz.navigation.setleft',
	    		                        'biz.navigation.settitle',
	    		                        'biz.navigation.seticon',
	    		                        'biz.navigation.close',
	    		                        'biz.navigation.setright',
	    		                        'biz.navigation.setmenu',
	    		                        'biz.user.get',

	    		                        'ui.progressbar.setcolors',

	    		                        'device.base.getinterface',
	    		                        'device.connection.getnetworktype',
	    		                        'device.launcher.checkinstalledapps',
	    		                        'device.launcher.launchapp',
	    		                        'device.notification.confirm',
	    		                        'device.notification.alert',
	    		                        'device.notification.prompt',
	    		                        'device.notification.showpreloader',
	    		                        'device.notification.hidepreloader',
	    		                        'device.notification.toast',
	    		                        'device.notification.actionsheet',
	    		                        'device.notification.modal',
	    		                        'device.geolocation.get',]
	    				});
	    	    	  dd.ready(function() {
	    	    		  var userid="";
	    	              document.addeventlistener('pause', function() {

	    	              });
	    	              document.addeventlistener('resume', function() {

	    	              });
	    	              document.getelementbyid("alertbtn").addeventlistener('tap', function() {
	    	  				mui.alert('您还不是[开票通]系统管理员，不能【进入系统】哦！', function() {

	    	  				});
	    	  			});
	    	  			dd.runtime.permission.requestauthcode({
	    	  				corpid : corpid,
	    	  				onsuccess : function(info) {
	    	  					$.ajax({
	    	  						url : 'ding/userinfo',
	    	  						data: {"code":info.code,"corpid":corpid},
	    	  				        method: 'post',
	    	  						success : function(data) {
	    	  							userid=data.userid;
	    	  							/* jobnumber=data.jobnumber;
                                        alert(userid); */
	    	  							$("#index").attr("href","dinglrkpd?corpid="+corpid+"&userid="+userid);

	    	  						    }
	    	  					    });
	    	  				     },
	    	  				onfail : function(err) {
	    	  						alert('fail: ' + json.stringify(err));
	    	  					}
	    	  				});


	    	  			/*  dd.biz.ding.post({
	    	  			    users : [userid],//用户列表，工号
	    	  			    corpid: corpid, //企业id
	    	  			    type: 2, //附件类型 1：image  2：link
	    	  			    alerttype: 2,
	    	  			    alertdate: {"format":"yyyy-mm-dd hh:mm","value":"2017-04-20 18:35"},
	    	  			    attachment: {
	    	  			        images: [''],
	    	  			    }, //附件信息
	    	  			    text: '你好我是钉钉', //消息
	    	  			    onsuccess : function() {
	    	  			    //onsuccess将在点击发送之后调用
	    	  			    },
	    	  			    onfail : function() {}
	    	  			}); */

	    	  			/* dd.runtime.permission.requestoperateauthcode({
	    	  			        corpid: corpid,
	    	  			        agentid:agentid,
	    	  			    onsuccess: function(result) {
	    	  			    //alert(result.code);
	    	  			    	$.ajax({
	    	  						url : 'ding/sendmessage',
	    	  						data: {"code":result.code,"corpid":corpid,"agentid":agentid,"userid":userid},
	    	  				        method: 'post',
	    	  						success : function(data) {
	    	  						    if(data=="success"){
	    	  						    	alert("sssss");
	    	  						       }
	    	  						    }
	    	  					    });
	    	  			    },
	    	  			    onfail : function(err) {

	    	  			    },

	    	  			}); */

	    	          });

	    	          dd.error(function(err) {
	    	              alert('dd error: ' + json.stringify(err));
	    	          });
	             }
	    	   });
			});
		</script>
</body>
</html>