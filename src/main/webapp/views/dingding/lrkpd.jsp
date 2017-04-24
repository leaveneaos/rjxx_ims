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

		<div id="slider" class="mui-slider">
			<div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
				<a class="mui-control-item" href="#item1mobile" id="lrkpd">
					录入开票单
				</a>
				<a class="mui-control-item" href="#item2mobile" id="lrgfxx">
					录入购方信息
				</a>
				<a class="mui-control-item" href="#item3mobile" id="lrspxx">
					录入商品信息
				</a>
			</div>


			<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4"></div>


			<div class="mui-slider-group" id="i">
				<div id="item1mobile" class="mui-slider-item mui-control-content mui-active">
					<div id="scroll1" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="mui-content-padded" style="margin: 5px;">
								<input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>
								<input type="hidden" id="userid" value="<c:out value="${userid}" />"/>

								<form class="mui-input-group">
									<div class="mui-input-row">
										<label>*销方名称</label>

										<select id="xfmc" name="xfmc"
												required>
											<c:forEach items="${xflist}" var="item">
												<option value="${item.id}">${item.xfmc}</option>
											</c:forEach>
										</select>

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
							<h5 class="mui-content-padded">开票要求：</h5>
							<div class="mui-input-row" style="margin: 10px 15px;">
								<textarea id="kpyq" rows="2" placeholder=""></textarea>
							</div>
							<h5 class="mui-content-padded">发票备注：</h5>
							<div class="mui-input-row" style="margin: 10px 15px;">
								<textarea id="bz" rows="2" placeholder=""></textarea>
							</div>
						</div>
					</div>
				</div>

				<div id="item2mobile" class="mui-slider-item mui-control-content">
					<div id="scroll2" class="mui-scroll-wrapper">
						<div class="mui-scroll">
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

							<div class="mui-card">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell mui-collapse">
										<a class="mui-navigate-right" href="#">收件人信息</a>
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
								</li>
							 </ul>
						   </div>
						  </div>
						</div>
				</div>

				<div id="item3mobile" class="mui-slider-item mui-control-content">
					<div id="scroll3" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="mui-content">
								<div class="mui-content-padded">
									<h5 class="mui-content-padded">*商品名称</h5>
									<select id="lrselect_sp" name="lrselect_sp" class="mui-btn mui-btn-block" >
										<option value="">选择商品</option>
										<c:forEach items="${spList}" var="item">
											<option value="${item.spbm}" class="${item.id}">${item.spmc}(${item.spbm})</option>
										</c:forEach>
									</select>

								</div>
								<div class="mui-content-padded" style="margin: 5px;">
									<form class="mui-input-group">
										<div class="mui-input-row" >
											<label>规格型号</label>
											<input type="text" id="ggxh" class="mui-input-clear" placeholder="">
										</div>
										<div class="mui-input-row" >
											<label>单位</label>
											<input type="text" id="spdw" class="mui-input-clear" placeholder="">
										</div>
										<div class="mui-input-row" >
											<label>数量</label>
											<input type="text" id="spsl" class="mui-input-clear" placeholder="">
										</div>
										<div class="mui-input-row" >
											<label>单价</label>
											<input type="text" id="spdj"  class="mui-input-clear" placeholder="">
										</div>
										<div class="mui-input-row" >
											<label>金额(不含税)</label>
											<input type="text" id="je" class="mui-input-clear" placeholder="">
										</div>
										<div class="mui-input-row">
											<label>金额(含税)</label>
											<input type="text"  id="hsje" class="mui-input-clear" placeholder="">
											<input type="hidden"  id="jshj2"  class="mui-input-clear" placeholder="">
										</div>
										<div class="mui-input-row" >
											<label>税率</label>
											<input type="text" id="splv" class="mui-input-clear" placeholder="" readonly>
										</div>
										<div class="mui-input-row" >
											<label>税额</label>
											<input type="text"  id="se"  class="mui-input-clear" placeholder="">
										</div>
									</form>
								</div>
							</div>
							<nav class="mui-bar mui-bar-tab">
								<a class="lrkpd" style="width:50%" >
									<span class="mui-tab-label" id="jshj" >价税合计：0</span>
								</a>
								<a class="lrkpd" id="add" style="width:25%" >
									<span class="mui-tab-label">继续添加</span>
								</a>
								<a class="lrkpd" id="submit" style="width:25%" >
									<span class="mui-tab-label" id="wc">完成</span>
								</a>
							</nav>
						</div>
					</div>
				</div>
			</div>
			


		</div>
		<!-- <ul class="mui-pager">
			<li class="mui-previous">
				<a href="#">
					上一页
				</a>
			</li>
			<li class="mui-next">
				<a href="#">
					下一页
				</a>
			</li>
		</ul> -->	
    </div>
	<button type="button" class="mui-btn mui-btn-primary mui-btn-block" onclick="lrkpd2();">完 成</button>
</body>
   
<script>

	function lrkpd2(){
		window.location.href="dinglrkpd2";
  	}

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
                });
                dd.error(function(err) {
                    alert('dd error: ' + JSON.stringify(err));
                });
            }
        });
    });
</script>
</html>  