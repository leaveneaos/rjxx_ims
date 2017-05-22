<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>开票通</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	  <script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.0.0/dingtalk.js"></script>
	  <script src="js/jquery.1.7.2.min.js"></script>
	  <link rel="stylesheet" href="css/mui.min.css">
      <link rel="stylesheet" href="css/index.css">
      <link rel="stylesheet" type="text/css" href="css/iconfont.css">
      <script src="js/mui.min.js"></script>
  </head>
  <body>
  <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>
  <div class="mui-content">
    <ul class="mui-table-view mui-grid-view mui-grid-9" style="background-color: #5C96F7;">
        <li class="mui-table-view-cell mui-media mui-col-xs-6">
          <a id='newInvoice'>
            <span class="mui-icon iconfont icon-kaipiao"></span>
            <div class="mui-media-body">新建开票单</div>
          </a>
        </li>
        <li class="mui-table-view-cell mui-media mui-col-xs-6">
          <a id="house" href="<%=request.getContextPath()%>/dinggfgl">
            <span class="mui-icon iconfont icon-xinxi"></span>
            <div class="mui-media-body">购方管理</div>
          </a>
        </li>
    </ul>
	  <h5 class="mui-content-padded">待开票清单</h5>
	  <div class="mui-card">
		  <div class="mui-card-header">
			  <div class="zuo1">
				  <div class="z2">
					  <div class="z2z1">序号</div>
					  <div class="z2z2">发起日期</div>
					  <div class="z2z3">发票类型</div>
					  <div class="z2z3">金额</div>
					  <div class="z2z3">状态</div>
				  </div>
			  </div>
		  </div>
		  <div class="mui-card-header">
			  <div class="zuo1">
				  <div class="z2">
					  <div class="z2z1">1</div>
					  <div class="z2z2">2017/4/22</div>
					  <div class="z2z3">普通发票</div>
					  <div class="z2z3">200.00</div>
					  <div class="z2z3">已处理</div>
				  </div>

			  </div>
		  </div>
		  <div class="mui-card-header">
			  <div class="zuo1">
				  <div class="z2">
					  <div class="z2z1">2</div>
					  <div class="z2z2">2017/4/23</div>
					  <div class="z2z3">专用发票</div>
					  <div class="z2z3">100.00</div>
					  <div class="z2z3">待处理</div>
				  </div>

			  </div>
		  </div>
		  <div class="mui-card-header">
			  <div class="zuo1">
				  <div class="z2">
					  <div class="z2z1">3</div>
					  <div class="z2z2">2017/4/24</div>
					  <div class="z2z3">电子发票</div>
					  <div class="z2z3">300.00</div>
					  <div class="z2z3">已开具</div>
				  </div>

			  </div>
		  </div>
	  </div>
  <!-- </div> -->

  <!-- <div class="mui-content"> -->
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
	</div>
  </body>
  <script>
      mui.plusReady(function () {
        plus.webview.currentWebview().setStyle({
          scrollIndicator: 'none'
        });
      });
      $(function(){
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
                      dd.runtime.permission.requestAuthCode({
                          corpId : corpId,
                          onSuccess : function(info) {
                              $.ajax({
                                  url : 'ding/userinfo',
                                  data: {"code":info.code,"corpid":corpId},
                                  method: 'post',
                                  success : function(data) {
                                      userid=data.userid;

                                  }
                              });
                          },
                          onFail : function(err) {
                              alert('fail: ' + json.stringify(err));
                          }
                      });
                      $("#newInvoice").bind("click",function(){
                          $.ajax({
                              url : 'ding/getsqzhinfo',
                              data: {"userid":userid,"corpid":corpId},
                              method: 'post',
                              success : function(data) {
                                  window.location.href="dinglrkpd?corpid="+corpId+"&userid="+userid;
                              }
                          });
                      });
                     /* $("#house").bind("click",function(){
                          $.ajax({
                              url : 'ding/getsqzhinfo',
                              data: {"userid":userid,"corpid":corpId},
                              method: 'post',
                              success : function(data) {
                                  window.location.href = 'gfgl.html';
                          //     }
                          // });
                      });*/

                  });

                  dd.error(function(err) {
                      alert('dd error: ' + JSON.stringify(err));
                  });
              }
          });
      });
  </script>
</html>  