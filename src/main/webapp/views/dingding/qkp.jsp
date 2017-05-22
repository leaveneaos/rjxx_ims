<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>去开票</title>
    <script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.0.0/dingtalk.js"></script>
    <script src="js/jquery.1.7.2.min.js"></script>
    <link href="css/mui.css" rel="stylesheet"/>
    <link href="css/index.css" rel="stylesheet"/>
    <script src="js/mui.min.js"></script>
    <script type="text/javascript" charset="utf-8">
      	mui.init();
    </script>
</head>
<body>
	<div class="mui-content">
        <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>
        <input type="hidden" id="userid" value="<c:out value="${userid}" />"/>
        <input type="hidden" id="sqlsh" value="<c:out value="${sqlsh}" />"/>
        <input type="hidden" id="jylsh" value="<c:out value="${jylsh}" />"/>
    	<div class="t1" style="font-size: 17px;">开票请求提交成功</div>
    	<div class="mui-button-row">
        <button type="button" class="mui-btn mui-btn-success" onclick="qkp();" >完成</button>
    </div>
    	
    </div>
</body>
<script>

    mui.toast('提交成功',{ duration:'long', type:'div' });
    function qkp(){
        var corpId =$("#corpid").val();
        window.location.href="ding?corpid="+corpId;
    }
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
                    dd.runtime.permission.requestOperateAuthCode({
                        corpId: corpId,
                        agentId:agentId,
                        onSuccess: function(result) {

                            $.ajax({
                                url :"dingqkp/sendmessage",
                                data:{"code":result.code,"corpid":corpId,"userid":$("#userid").val(),"agentId":agentId,"sqlsh":$("#sqlsh").val(),"jylsh":$("#jylsh").val()},
                                method:'post',
                                success:function(data){

                                }
                            });
                        },
                        onFail : function(err) {

                        }

                    })
                });

                dd.error(function(err) {
                    alert('dd error: ' + JSON.stringify(err));
                });
            }
        });
    });
</script>
</html>