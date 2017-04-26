<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
										<label><span style="color: red">销方名称</span></label>
										<select id="xfmc" name="xfmc">
											<c:forEach items="${xflist}" var="item">
												<option value="${item.id}">${item.xfmc}</option>
											</c:forEach>
										</select>
									</div>
									<div class="mui-input-row">
										<label><span style="color: red">合同/订单号</span></label>
										<input type="text"  id="ddh" class="mui-input-clear" placeholder="请输入合同或订单号">
									</div>
									<div class="mui-input-row">
										<label>开票日期</label>
										<input    class="mui-input-clear" readonly="readonly" name="kprq" id="kprq" type="text">
									</div>
								</form>
								<h5 class="mui-content-padded"><span style="color: red">发票种类</span></h5>
								<div class="mui-card">
									<form class="mui-input-group">
										<div class="mui-input-row mui-radio">
											<label>电子发票</label>
											<input name="radio1" id="dzfp" value="12" type="radio" onclick="inputbt(this.value);" checked>
										</div>
										<div class="mui-input-row mui-radio">
											<label>纸质普票</label>
											<input name="radio1" id="zzpp" value="02" onclick="inputbt(this.value);" type="radio">
										</div>
										<div class="mui-input-row mui-radio">
											<label>纸质专票</label>
											<input name="radio1" id="zzzp" value="01" onclick="inputbt(this.value);" type="radio">
										</div>
									</form>
								</div>
							</div>
							<h5 class="mui-content-padded">发票备注：</h5>
							<div class="mui-input-row" style="margin: 10px 15px;">
								<textarea id="bz" rows="2" placeholder=""></textarea>
							</div>
							<h5 class="mui-content-padded">开票要求：</h5>
							<div class="mui-input-row" style="margin: 10px 15px;">
								<textarea id="kpyq" rows="2" placeholder=""></textarea>
							</div>
								<button type="button" class="mui-btn mui-btn-primary mui-btn-block" id="finish"  >完 成</button>
						</div>
					</div>
				</div>

				<div id="item2mobile" class="mui-slider-item mui-control-content">
					<div id="scroll2" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="mui-content-padded" style="margin: 5px;">
									<form class="mui-input-group">
										<div class="mui-input-row">
											<label><span id="gfmc_span" style="color: red">购方名称</span></label>
											<input type="text" id="gfmc" class="mui-input-clear" placeholder="发票抬头">
										</div>
										<div class="mui-input-row">
											<label><span id="nsrsbh_span">纳税人识别号</span></label>
											<input type="text"  id="nsrsbh" class="mui-input-clear" placeholder="购方税号，15位至20位">
										</div>
										<div class="mui-input-row">
											<label><span id="zcdz_span">购方地址</span></label>
											<input type="text" id="zcdz" class="mui-input-clear" placeholder="购方注册地址（发票票面左下角显示）">
										</div>
										<div class="mui-input-row">
											<label><span id="zcdh_span">购方电话</span></label>
											<input type="text" id="zcdh"  class="mui-input-clear" placeholder="购方注册电话，如021-55571833">
										</div>
										<div class="mui-input-row">
											<label><span id="khyh_span">开户银行</span></label>
											<input type="text" id="khyh" class="mui-input-clear" placeholder="购方开户银行">
										</div>
										<div class="mui-input-row">
											<label><span id="yhzh_span">银行账户</span></label>
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
							<button type="button" class="mui-btn mui-btn-primary mui-btn-block" id="finish1" >完 成</button>
						</div>
					</div>
				</div>

				<div id="item3mobile" class="mui-slider-item mui-control-content">
					<div id="scroll3" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="mui-content">
								<div class="mui-content-padded">
									<h5 class="mui-content-padded"><span style="color: red">*</span>商品名称</h5>
									<select id="lrselect_sp" name="lrselect_sp" class="mui-btn mui-btn-block" >
										<option value="">选择商品</option>
										<c:forEach items="${spList}" var="item">
											<option value="${item.spbm}" class="${item.id}">${item.spmc}(${item.spbm})</option>
										</c:forEach>
									</select>

								</div>
								<div class="mui-content-padded" style="margin: 5px; padding-bottom : 40px;">
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
											<label><span style="color: red">金额(不含税)</span></label>
											<input type="text" id="je" class="mui-input-clear" onclick="jyspxx();" placeholder="">
										</div>
										<div class="mui-input-row">
											<label><span style="color: red">金额(含税)</span></label>
											<input type="text"  id="hsje" class="mui-input-clear" onclick="jyspxx();" placeholder="">
											<input type="hidden"  id="jshj2"  class="mui-input-clear"  placeholder="">
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
							<div class="mui-bar mui-bar-tab">
								<a class="lrkpd" style="width:50%" >
									<span class="mui-tab-label" id="jshj" >价税合计：0</span>
								</a>
								<a class="lrkpd" id="add" style="width:50%" >
									<span class="mui-tab-label" id="show" >添加(已完成:0)</span>
								</a>
							</div>
							<button type="button" class="mui-btn mui-btn-primary mui-btn-block" id="finish2" >完 成</button>
						</div>
					</div>
				</div>
			</div>
			


		</div>
    </div>

</body>
<script src="assets/js/format.js"></script>
<script>
	function jyspxx(){
       var spdm=$("#lrselect_sp") .val();
       if(spdm==""){
           mui.alert('请先选择商品！', function() {
               return ;
           });
           return;
	   }
	}
    function inputbt(fpzldm){
        var gfmc=$("#gfmc_span");
        var nsrsbh=$("#nsrsbh_span");
        var zcdz=$("#zcdz_span");
        var zcdh=$("#zcdh_span");
        var khyh=$("#khyh_span");
        var yhzh=$("#yhzh_span");
        if(fpzldm=="01"){
            //gfmc.html("*");
            gfmc.css("color","red");
           // nsrsbh.html("*");
            nsrsbh.css("color","red");
           // zcdz.html("*");
            zcdz.css("color","red");
            //zcdh.html("*");
            zcdh.css("color","red");
           // khyh.html("*");
            khyh.css("color","red");
          //  yhzh.html("*");
            yhzh.css("color","red");
        }else{
            //gfmc.html("*");
            gfmc.css("color","red");
            //nsrsbh.html("");
            nsrsbh.css("color","");
          //  zcdz.html("");
            zcdz.css("color","");
         //   zcdh.html("");
            zcdh.css("color","");
         //   khyh.html("");
            khyh.css("color","");
           // yhzh.html("");
            yhzh.css("color","");
        }
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
                    var totaljshj=0.00;
                    var str='';
                    var i=0;
                    $("#add").bind('click', function()  {
                        var jshj = $('#jshj');
                        var jshj2=$("#jshj2").val();
                        var je = $('#je');
                        var sl = $('#splv');//商品税率
                        var se = $('#se');
                        var hsje = $('#hsje');
                        var dj = $('#spdj');
                        var sps = $('#spsl');//商品数量
                        var ggxh =$("#ggxh");
                        var spdw =$("#spdw");
                        var lrselect_sp =$("#lrselect_sp");
                        var spdm = lrselect_sp.val();
                        var spmc = $("#lrselect_sp option:checked").text();
                        var pos = spmc.indexOf("(");
                        spmc = spmc.substring(0, pos);
                        if(lrselect_sp.val()==''||lrselect_sp.val()==null){
                            mui.alert('请选择商品！', function() {
                                return ;
                            });
                            return;
                        } if(je.val()==""||je.val()==null){
                            mui.alert('请填写金额（不含税）！', function() {
                                return;
                            });
                            return;
                        } if(hsje.val()==""||hsje.val()==null){
                            mui.alert('请填写金额（含税）！', function() {
                                return;
                            });
                            return;
                        }
                        totaljshj=parseFloat(totaljshj)+parseFloat(jshj2);
                        jshj.html("价税合计："+FormatFloat(totaljshj, "#####0.00"));
                        i=i+1;
                        $("#show").html("添加（已完成："+i+")");
                        var s="&mxxh="+i+"&ggxh="+ggxh.val()+"&spdm="+spdm+"&spmc="+spmc+"&spdw="+spdw.val()+"&spsl="+sps.val()+"&spdj="+dj.val()+"&hsje="+hsje.val()+"&se="+se.val()+"&sl="+sl.val()+"&je="+je.val();
                        str=str+s;
                        $('#je').val("");
                        $('#splv').val("");
                        $('#se').val("");
                        $('#hsje').val("");
                        $('#spdj').val("");
                        $('#spsl').val("");
                        $("#lrselect_sp").val("");
                        $("#ggxh").val("");
                        $("#spdw").val("");
                    });
                    $("#lrselect_sp").bind('input', function()  {
                        var je = $('#je');
                        var sl = $('#splv');//商品税率
                        var se = $('#se');
                        var hsje = $('#hsje');
                        var jshj = $('#jshj');
                        var dj = $('#spdj');
                        var sps = $('#spsl');//商品数量
                        var spsl;
                        var spdm = $(this).val();
                        var spmc = $("#lrselect_sp option:checked").text();
                        var pos = spmc.indexOf("(");
                        spmc = spmc.substring(0, pos);
                        if (!spdm) {
                            $("#lrmx_form input").val("");
                            return;
                        }
                        var ur = "dinglrkpd/getSpxq";
                        $.ajax({
                            url: ur,
                            type: "post",
                            async:false,
                            data: {
                                spdm: spdm,
                                spmc:spmc,
                            },
                            success: function (res) {
                                if (res) {
                                    $("#ggxh").val(res["spggxh"] == null ? "" : res["spggxh"]);
                                    $("#spdw").val(res["spdw"] == null ? "" : res["spdw"]);
                                    $("#spdj").val(res["spdj"] == null ? "" : res["spdj"]);
                                    $("#spsl").val("");
                                    $("#splv").val(res["sl"]);
                                    spsl = res["sl"];
                                }
                            }
                        });
                        if(null!=je && je.val() !=""){
                            var temp = (100+sl.val()*100)/100;
                            se.val(FormatFloat(je.val() * spsl, "#####0.00"));
                            var je1 = parseFloat(je.val());
                            var se1 = parseFloat(se.val());
                            hsje.val(FormatFloat(je1 + se1, "#####0.00"));
                            jshj.html("价税合计："+FormatFloat(je1 + se1, "#####0.00"));
                            $("#jshj2").val(FormatFloat(je1 + se1, "#####0.00"));
                            if (dj != null && dj.val() != "") {
                                sps.val(FormatFloat(je.val() / dj.val(), "#####0.00"));
                            }else if(sps != null && sps.val() != ""){
                                dj.val(FormatFloat(je.val() / sps.val(), "#####0.00"));
                            }
                        }

                    });

                    $("#je").bind('input', function() {
                        var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
                        var je = $('#je');
                        if (!num.test(je.val())) {
                            if (je.val().length > 1) {
                                $('#je').val(
                                    je.val().substring(0,
                                        je.val().length - 1));

                            } else {
                                $('#je').val("");
                            }
                            return;
                        }
                        var sl = $('#splv');
                        var se = $('#se');
                        var hsje = $('#hsje');
                        var jshj = $('#jshj');
                        var dj = $('#spdj');
                        var sps = $('#spsl');
                        var spsl;
                        var temp = (100 + sl.val() * 100) / 100;
                        se.val(FormatFloat(je.val() * sl.val(),
                            "#####0.00"));
                        var je1 = parseFloat(je.val());
                        var se1 = parseFloat(se.val());
                        hsje.val(FormatFloat(je1 + se1, "#####0.00"));
                        jshj.html("价税合计："+FormatFloat(je1 + se1, "#####0.00"));
                        $("#jshj2").val(FormatFloat(je1 + se1, "#####0.00"));
                        if (dj != null && dj.val() != "") {
                            sps.val(FormatFloat(je.val() / dj.val(),
                                "#0.00"));
                        }else  if (sps != null && sps.val() != "") {
                            dj.val(FormatFloat(je.val() / sps.val(),
                                "#####0.00"));
                        }

                    });
                    $("#hsje").bind('input', function() {

                        var num = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
                        var hsje = $('#hsje');
                        if (!num.test(hsje.val())) {
                            if (hsje.val().length > 1) {
                                $('#hsje').val(
                                    hsje.val().substring(0,
                                        hsje.val().length - 1))
                            } else {
                                $('#hsje').val("")
                            }
                            return;
                        }
                        var je = $('#je');
                        var sl = $('#splv');
                        var se = $('#se');

                        var jshj = $('#jshj');
                        var dj = $('#spdj');
                        var sps = $('#spsl');
                        var spsl;
                        var temp = (100 + sl.val() * 100) / 100;
                        je.val(FormatFloat(hsje.val() / (temp),
                            "#####0.00"));
                        se.val(FormatFloat(hsje.val() - je.val(),
                            "#####0.00"));
                        if (dj != null && dj.val() != "") {
                            sps.val(FormatFloat(je.val() / dj.val(),
                                "#0.00"));
                        }else  if (sps != null && sps.val() != "") {
                            dj.val(FormatFloat(je.val() / sps.val(),
                                "#####0.00"));
                        }
                        jshj.html("价税合计："+FormatFloat(hsje.val(), "#####0.00"));
                        $("#jshj2").val(FormatFloat(hsje.val(), "#####0.00"));
                    });
                    function  tijiao() {
                        var xfmc = $('#xfmc option:selected').text();
                        var xfid = $('#xfmc option:selected').val();
                        var kprq = $("#kprq").val();
                        var ddh = $("#ddh").val();
                        var userid = $("#userid").val();
                        var fpzldm = $("input[type='radio']:checked").val();
                        var bz = $("#bz").val();
                        var kpyq = $("#kpyq").val();

                        var gfmc = $("#gfmc").val();
                        var nsrsbh = $("#nsrsbh").val();
                        var zcdz = $("#zcdz").val();
                        var zcdh = $("#zcdh").val();
                        var khyh = $("#khyh").val();
                        var yhzh = $("#yhzh").val();
                        var lxr = $("#lxr").val();
                        var lxdh = $("#lxdh").val();
                        var lxdz = $("#lxdz").val();
                        var yjdz = $("#yjdz").val();

                        if (xfmc == null || xfmc == "") {
                            mui.alert('请选择销方名称！', function () {
                                $("#gfmc").focus();
                            });
                            return;
                        }
                        if (ddh == null || ddh == "") {
                            mui.alert('请输入订单号！', function () {
                                $("#ddh").focus();
                            });
                            return;
                        }
                        if (fpzldm == "01") {
                            if (gfmc == null || gfmc == "") {
                                mui.alert('请输入购方名称！', function () {
                                    $("#gfmc").focus();
                                });
                                return;
                            }
                            if (nsrsbh == null || nsrsbh == "") {
                                mui.alert('请输入购方纳税人识别号！', function () {
                                    $("#nsrsbh").focus();
                                });
                                return;
                            }
                            if (zcdz == null || zcdz == "") {
                                mui.alert('请输入购方注册地址！', function () {
                                    $("#zcdz").focus();
                                });
                                return;
                            }
                            if (zcdh == null || zcdh == "") {
                                mui.alert('请输入购方注册电话！', function () {
                                    $("#zcdh").focus();
                                });
                                return;
                            }
                            if (khyh == null || khyh == "") {
                                mui.alert('请输入购方开户行名称！', function () {
                                    $("#khyh").focus();
                                });
                                return;
                            }
                            if (yhzh == null || yhzh == "") {
                                mui.alert('请输入购方银行账号！', function () {
                                    $("#yhzh").focus();
                                });
                                return;
                            }

                        } else if (fpzldm == "02") {
                            if (gfmc == null || gfmc == "") {
                                mui.alert('请输入购方名称！', function () {
                                    $("#gfmc").focus();
                                });
                                return;
                            }
                        } else if (fpzldm == "12") {
                            if (gfmc == null || gfmc == "") {
                                mui.alert('请输入购方名称！', function () {
                                    $("#gfmc").focus();
                                });
                                return;
                            }
                        }
                        if (str == "") {
                            mui.alert('请填写商品信息！', function () {
                                $("#lrselect_sp").focus();
                            });
                            return;
                        }
                        var sss = "?corpid=" + corpId + "&userid=" + userid + "&xfid=" + xfid + "&xfmc=" + xfmc + "&ddh=" + ddh + "&kprq=" + kprq + "&fpzldm=" + fpzldm + "&bz=" + bz
                            + "&gfmc=" + gfmc + "&nsrsbh=" + nsrsbh + "&zcdz=" + zcdz + "&zcdh=" + zcdh + "&khyh=" + khyh + "&yhzh=" + yhzh + "&lxr=" + lxr + "&lxdh=" + lxdh
                            + "&lxdz=" + lxdz + "&yjdz=" + yjdz + str + "&mxcount=" + i;


                        var btnArray = ['否', '是'];
                        var div='<div style="margin:0 auto;">发票抬头：'+gfmc+'</div><br/>' +
                            '<div style="margin:0 auto;">发票明细：'+i+'</div><br/>' +
                            '<div style="margin:0 auto;">发票金额：'+totaljshj+'</div>'
                        mui.confirm('您确认提交开票申请？', div, btnArray, function (e) {
                            if (e.index == 1) {
                                window.location.href = encodeURI(encodeURI("dingqkp" + sss));
                            }
                            return;
                        });
                    }
                    document.getElementById("finish").addEventListener('tap', tijiao);
                    document.getElementById("finish1").addEventListener('tap', tijiao);
                    document.getElementById("finish2").addEventListener('tap', tijiao);
                });
                dd.error(function(err) {
                    alert('dd error: ' + JSON.stringify(err));
                });
            }
        });
    });
</script>
</html>  