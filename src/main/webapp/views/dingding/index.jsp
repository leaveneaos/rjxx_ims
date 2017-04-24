<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入开票单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">    
  
    <link rel="stylesheet" href="css/mui.min.css">   
    <link rel="stylesheet" href="css/index.css">
    <script src="js/mui.min.js"></script>    
  </head>
  <body>
  <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>
  <div class="mui-content">
			<h5 class="mui-content-padded">待开票清单</h5>
			<div class="mui-card">
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z2">
							<div class="z2z">序号</div>
							<div class="z2z">发起日期</div>
							<div class="z2z">发票类型</div>
							<div class="z2z">金额</div>
							<div class="z2z">状态</div>
						</div>	
					</div>
				</div>
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z2">
							<div class="z2z">1</div>
							<div class="z2z">2017-4-22</div>
							<div class="z2z">普通发票</div>
							<div class="z2z">200.00</div>
							<div class="z2z">已处理</div>
						</div>
						
					</div>
				</div>
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z2">
							<div class="z2z">2</div>
							<div class="z2z">2017-4-23</div>
							<div class="z2z">专用发票</div>
							<div class="z2z">100.00</div>
							<div class="z2z">待处理</div>
						</div>
						
					</div>
				</div>
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z2">
							<div class="z2z">3</div>
							<div class="z2z">2017-4-24</div>
							<div class="z2z">电子发票</div>
							<div class="z2z">300.00</div>
							<div class="z2z">已开具</div>
						</div>
						
					</div>
				</div>
			</div>
    </div>

    <div class="mui-content">
			<h5 class="mui-content-padded">本月汇总</h5>
			<div class="mui-card">
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z3">
							<div class="z2z">发票类型</div>
							<div class="z2z">数量</div>
							<div class="z2z">税额</div>
							<div class="z2z">金额</div>
						</div>	
					</div>
				</div>
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z3">
							<div class="z2z">普通发票</div>
							<div class="z2z">200</div>
							<div class="z2z">0.5</div>
							<div class="z2z">200.00</div>
						</div>
					</div>
				</div>
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z3">
							<div class="z2z">专用发票</div>
							<div class="z2z">200</div>
							<div class="z2z">0.5</div>
							<div class="z2z">200.00</div>
						</div>
					</div>
				</div>
				<div class="mui-card-header">
					<div class="zuo1">
						<div class="z3">
							<div class="z2z">电子发票</div>
							<div class="z2z">200</div>
							<div class="z2z">0.5</div>
							<div class="z2z">200.00</div>
						</div>
					</div>
				</div>
			</div>
    </div>
    <br /><br />
    <div class="mui-button-row">
		<button id='alertBtn' type="button" class="mui-btn mui-btn-primary" ><a id="index">新建开票单</a></button>
    </div>
  </body>
  <script>
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
                      /*document.getelementbyid("alertbtn").addeventlistener('tap', function() {
                          mui.alert('您还不是[开票通]系统管理员，不能【进入系统】哦！', function() {

                          });
                      });*/
                      dd.runtime.permission.requestauthcode({
                          corpid : corpid,
                          onsuccess : function(info) {
                              $.ajax({
                                  url : 'ding/userinfo',
                                  data: {"code":info.code,"corpid":corpid},
                                  method: 'post',
                                  success : function(data) {
                                      userid=data.userid;
                                      $("#index").attr("href","dinglrkpd?corpid="+corpid+"&userid="+userid);

                                  }
                              });
                          },
                          onfail : function(err) {
                              alert('fail: ' + json.stringify(err));
                          }
                      });
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
</html>  