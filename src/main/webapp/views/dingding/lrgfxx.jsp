<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入购方信息</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">    
    <script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.0.0/dingtalk.js"></script>
    <link rel="stylesheet" href="css/mui.css">
    <script src="js/mui.min.js"></script>
    <script src="js/jquery.1.7.2.min.js"></script>
  </head>  
  <body>  
  
		<div class="mui-content">
		<input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>
		<input type="hidden" id="userid" value="<c:out value="${userid}" />"/>    
		<input type="hidden" id="xfmc" value="<c:out value="${xfmc}" />"/> 
		<input type="hidden" id="kprq" value="<c:out value="${kprq}" />"/> 
		<input type="hidden" id="fpzldm" value="<c:out value="${fpzldm}" />"/> 
		<input type="hidden" id="bz" value="<c:out value="${bz}" />"/> 
		<input type="hidden" id="ddh" value="<c:out value="${ddh}" />"/>     
	    <div class="mui-content-padded" style="margin: 5px;">    
	      <form class="mui-input-group">
			    <div class="mui-input-row">
			        <label>*购方名称</label>
			        <input type="text" id="gfmc" class="mui-input-clear" placeholder="发票抬头">
			    </div>
			    <div class="mui-input-row">
			        <label>*纳税人识别号</label>
			        <input type="text"  id="nsrsbh" class="mui-input-clear" placeholder="本单位纳税人识别号，15位至20位">
			    </div>
			    <div class="mui-input-row">
			        <label>*注册地址</label>
			        <input type="text" id="zcdz" class="mui-input-clear" placeholder="购方注册地址（发票票面左下角显示）">
			    </div>
			    <div class="mui-input-row">
			        <label>*注册电话</label>
			        <input type="text" id="zcdh"  class="mui-input-clear" placeholder="购方注册电话，如021-55571833">
			    </div>
			    <div class="mui-input-row">
			        <label>*开户银行</label>
			        <input type="text" id="khyh" class="mui-input-clear" placeholder="购方开户银行">
			    </div>
			    <div class="mui-input-row">
			        <label>*银行账户</label>
			        <input type="text" id="yhzh" class="mui-input-clear" placeholder="购方银行账户">
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
									<input type="text" id="lxr"  class="mui-input-clear" placeholder="收件人姓名">
								</div>
								<div class="mui-input-row">
									<label>联系电话</label>
									<input type="text" id="lxdh" class="mui-input-clear" placeholder="收件人联系电话">
								</div>
								<div class="mui-input-row">
									<label>联系地址</label>
									<input type="text" id="lxdz" class="mui-input-clear" placeholder="收件人详细地址">
								</div>
								<div class="mui-input-row">
									<label>邮件地址</label>
									<input type="text" id="yjdz" class="mui-input-clear" placeholder="收件人邮箱地址，提示用户发票已开具">
								</div>
								<div class="mui-input-row" style="display:none" >
									<label>提取码</label>
									<input type="text" id="tqm" class="mui-input-clear" placeholder="用户提取电子发票时使用">
								</div>
										<li class="mui-table-view-cell" style="display:none">
											<span>订单/合同号作为提取码</span>
											<div id="mySwitch" class="mui-switch mui-switch-blue mui-switch-mini">
												<div  class="mui-switch-handle"></div>
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
			<a class="lrkpd" >
				<span class="mui-tab-label">返回</span>
			</a>
			<a class="lrkpd"  id="lrgfxx"  >
				<span class="mui-tab-label">下一步</span>
			</a>
		</nav>
  </body>  
  <script>
  
  $(function(){
	    var url= window.location.href;
		var corpId =$("#corpid").val();
		var signature = "";
		var nonce = "";
		var timeStamp = "";
		var agentId = "";
		document.getElementById("lrgfxx").addEventListener('tap', function() {
    		var xfmc=$("#xfmc").val();   
          	var kprq=$("#kprq").val();
          	var ddh=$("#ddh").val();
          	var fpzldm=$("#fpzldm").val();
          	var bz=$("#bz").val();
          	var gfmc=$("#gfmc").val();
          	var nsrsbh=$("#nsrsbh").val();
          	var zcdz=$("#zcdz").val();
          	var zcdh=$("#zcdh").val();
          	var khyh=$("#khyh").val();
          	var yhzh=$("#yhzh").val();
          	var lxr=$("#lxr").val();
          	var lxdh=$("#lxdh").val();
          	var lxdz=$("#lxdz").val();
          	var yjdz=$("#yjdz").val();
          	var userid=$("#userid").val();
          	var isActive = document.getElementById("mySwitch").classList.contains("mui-active");
          	if(isActive){
          	  var tqm=ddh;
          	}else{
          	  var tqm=$("#tqm").val();
          	}
            if(fpzldm=="01"){
            	if(gfmc==null||gfmc==""){
            		mui.alert('请输入购方名称！', function() {
						return ;
					});
            	}
            	if(nsrsbh==null||nsrsbh==""){
            		mui.alert('请输入购方纳税人识别号！', function() {
						return ;
					});
            	}
            	if(khyh==null||khyh==""){
            		mui.alert('请输入购方开户行名称！', function() {
						return ;
					});
            	}
            	if(yhzh==null||yhzh==""){
            		mui.alert('请输入购方银行账号！', function() {
						return ;
					});
            	}
            	
            }else if(fpzldm=="02"){
            	if(gfmc==null||gfmc==""){
            		mui.alert('请输入购方名称！', function() {
						return ;
					});
            	}
            }else if(fpzldm=="12"){
            	if(gfmc==null||gfmc==""){
            		mui.alert('请输入购方名称！', function() {
						return ;
					});
            	}
            }
          	href="dinglrspxx?corpid="+corpId+"&userid="+userid+"&xfmc="+(xfmc)+"&ddh="+ddh+"&kprq="+kprq+"&fpzldm="+fpzldm+"&bz="+(bz)
          	+"&gfmc="+(gfmc)+"&nsrsbh="+nsrsbh+"&zcdz="+(zcdz)+"&zcdh="+zcdh+"&khyh="+(khyh)+"&yhzh="+yhzh+"&lxr="+(lxr)+"&lxdh="+lxdh
          	+"&lxdz="+(lxdz)+"&yjdz="+(yjdz)+"&tqm="+tqm; 
          	$("#lrgfxx").attr("href",encodeURI(encodeURI(href)));
  	   });
		
		
	 /* $.ajax({
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
	               document.getElementById("lrgfxx").addEventListener('tap', function() {
	            		var xfmc=$("#xfmc").val();   
    		          	var kprq=$("#kprq").val();
    		          	var ddh=$("#ddh").val();
    		          	var fpzldm=$("#fpzldm").val();
    		          	var bz=$("#bz").val();
    		          	var gfmc=$("#gfmc").val();
    		          	var nsrsbh=$("#nsrsbh").val();
    		          	var zcdz=$("#zcdz").val();
    		          	var zcdh=$("#zcdh").val();
    		          	var khyh=$("#khyh").val();
    		          	var yhzh=$("#yhzh").val();
    		          	var lxr=$("#lxr").val();
    		          	var lxdh=$("#lxdh").val();
    		          	var lxdz=$("#lxdz").val();
    		          	var yjdz=$("#yjdz").val();
    		          	var isActive = document.getElementById("mySwitch").classList.contains("mui-active");
    		          	if(isActive){
    		          	  var tqm=ddh;
    		          	}else{
    		          	  var tqm=$("#tqm").val();
    		          	}
    		            if(fpzldm=="01"){
    		            	if(gfmc==null||gfmc==""){
    		            		mui.alert('请输入购方名称！', function() {
        							return ;
        						});
    		            	}
    		            	if(nsrsbh==null||nsrsbh==""){
    		            		mui.alert('请输入购方纳税人识别号！', function() {
        							return ;
        						});
    		            	}
    		            	if(khyh==null||khyh==""){
    		            		mui.alert('请输入购方开户行名称！', function() {
        							return ;
        						});
    		            	}
    		            	if(yhzh==null||yhzh==""){
    		            		mui.alert('请输入购方银行账号！', function() {
        							return ;
        						});
    		            	}
    		            	
    		            }else if(fpzldm=="02"){
    		            	if(gfmc==null||gfmc==""){
    		            		mui.alert('请输入购方名称！', function() {
        							return ;
        						});
    		            	}
    		            }else if(fpzldm=="12"){
    		            	
    		            }
    		          	href="dinglrspxx?corpid="+corpId+"&xfmc="+encodeURIComponent(xfmc)+"&ddh="+ddh+"&kprq="+kprq+"&fpzldm="+fpzldm+"&bz="+encodeURIComponent(bz)
    		          	+"&gfmc="+encodeURIComponent(gfmc)+"&nsrsbh="+nsrsbh+"&zcdz="+encodeURIComponent(zcdz)+"&zcdh="+zcdh+"&khyh="+encodeURIComponent(khyh)+"&yhzh="+yhzh+"&lxr="+encodeURIComponent(lxr)+"&lxdh="+lxdh
    		          	+"&lxdz="+encodeURIComponent(lxdz)+"&yjdz="+encodeURIComponent(yjdz)+"&tqm="+tqm; 
    		          	$("#lrgfxx").attr("href",href);
	          	   }); 
	          });

	          dd.error(function(err) {
	              alert('dd error: ' + JSON.stringify(err));
	          });
       }
	 }); */
  });
  </script>
</html>  