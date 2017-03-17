
(function ($) {
    'use strict';

    $(function () {
        var $fullText = $('.admin-fullText');
        $('#admin-fullscreen').on('click', function () {
            $.AMUI.fullscreen.toggle();
        });

        $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function () {
            $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
        });
        
        
        
        var cd1 = $('#cd1', parent.document).val();
        var cd2 = $('#cd2', parent.document).val();
        if(cd1!=null&&cd1!=""){
        	   $(".am-text-primary").next().html(cd1);
        }
        if(cd2!=null&&cd2!=""){
            $(".am-text-primary").html(cd2);
        }

     
    });
    //设置Ajax全局参数
    $.ajaxSetup({
        statusCode: {
            401: function () {
                window.top.location = "/login/login";
            }
        }
    });
    if ($.AMUI && $.AMUI.validator) {
	    // 增加多个正则
	    $.AMUI.validator.patterns.Taxid = /^([a-z]|[A-Z]|[0-9]){15,20}$/;
	    //身份证
	    $.AMUI.validator.patterns.Id = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
	    
	    //电话号码
	    $.AMUI.validator.patterns.Telephone = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
   
	    //手机号码
	    $.AMUI.validator.patterns.Phone = /^(13[0-9])|(15[^4,\\D])|(18[0,5-9])/;
	    
	    //数字
	    $.AMUI.validator.patterns.Number = /^[0-9]*$/;
	    
	    //整数
	    $.AMUI.validator.patterns.Integer = /^-?\d+$/;
	    
	    //金额
	    $.AMUI.validator.patterns.Money = /^\d+\.?\d+?$/;
	    
	    //发票代码
	    $.AMUI.validator.patterns.Fpdm = /^\d{10,12}$/;
	    
	    //发票号码
	    $.AMUI.validator.patterns.Fphm = /^\d{8}$/;
	    
	    //银行账号
	    $.AMUI.validator.patterns.Yhzh = /^(\d{16}|\d{19})$/;
	    
	    //判断最多两位小数
	    $.AMUI.validator.patterns.lwxx = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	}
})(jQuery);


//风格切换
$('body').attr('class', "theme-white");
//$('.tpl-skiner-toggle').on('click', function() {
//$('.tpl-skiner').toggleClass('active');
//})
//
//$('.tpl-skiner-content-bar').find('span').on('click', function() {
//$('body').attr('class', $(this).attr('data-color'))
//saveSelectColor.Color = $(this).attr('data-color');
//// 保存选择项
//storageSave(saveSelectColor);
//
//})


//侧边菜单开关


function autoLeftNav() {
/* $('#uweuy').on('click', function() {

 })*/

 if ($(window).width() < 1024) {
     $('.left-sidebar').addClass('active');
 } else {
     $('.left-sidebar').removeClass('active');
 }
}


//侧边菜单
$('.sidebar-nav-sub-title').on('click', function() {
 $(this).siblings('.sidebar-nav-sub').slideToggle(80)
     .end()
     .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
})



//获取浏览器页面可见高度和宽度  
var _PageHeight = document.documentElement.clientHeight,  
    _PageWidth = document.documentElement.clientWidth;  
//计算loading框距离顶部和左部的距离（loading框的宽度为215px，高度为61px）  
var _LoadingTop = _PageHeight > 61 ? (_PageHeight - 61) / 2 : 0,  
    _LoadingLeft = _PageWidth > 215 ? (_PageWidth - 215) / 2 : 0;  
//在页面未加载完毕之前显示的loading Html自定义内容  
var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:' + _PageHeight + 'px;top:0;background:#f3f8ff;opacity:1;filter:alpha(opacity=80);z-index:10000;"><div style="position: absolute; cursor1: wait; left: ' + _LoadingLeft + 'px; top:' + _LoadingTop + 'px; width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px; background: #fff url(img/loading.gif) no-repeat scroll 5px 10px; border: 2px solid #95B8E7; color: #696969; font-family:\'Microsoft YaHei\';">页面加载中，请等待...</div></div>';  

  
//监听加载状态改变  
document.onreadystatechange = completeLoading;  

document.write(_LoadingHtml); 
  
//加载状态为complete时移除loading效果  
function completeLoading() {  
    if (document.readyState == "complete") {  
    setTimeout(function(){
    loadingMask = document.getElementById('loadingDiv');  
        document.getElementById('loadingDiv').parentNode.removeChild(loadingMask);
        console.log(this);
    },300);
    }  
} 

//提示层封装
$('#ck').click(function() {
    $.alert({
            text: '指定传入html片断文本',
           // type: 'alert'
        },
        function() {
            alert('回调函数')
        }
    )
});

$.extend({
    alert: function(options, callback) {
        var self = this;
        var mask = $('<div class="windowMask" style="width:100%; height:100%; position:fixed; z-index:100000; top:0px; left;0px; background-color:#000000; display:none"></div>');
        $('body').append(mask);
        mask.fadeTo(500, 0.6);
        var alertWindow = $('<div class="alertWindow" style="width:80%; left:10%; position:fixed; font-weight:bold; margin: 0 auto;z-index:1000001; font-size:17px; color:#000;  display:none; top:150px; background:#fafafa; box-shadow:0px 15px 12px 0px rgba(0,0,0,0.22), 0px 19px 38px 0px rgba(0,0,0,0.30);	text-align:center; -webkit-border-radius: 5px;-moz-border-radius: 5px;border-radius: 5px;">' +
            '<div style="width:auto; padding:25px 30px 5px;text-align:center; ">' + options.text + '</div>' +
            '</div>');
        $('body').append(alertWindow);
        alertWindow.delay(200).fadeIn();
        if (!options.type || options.type != 'loading') {
            if (!options.confirmBtnText) options.confirmBtnText = '确定';
            var buttonContainer = $('<div style="width:60px; float:right; padding:12px 12px 20px;text-align:center; color:#157efb;cursor:pointer;">' + options.confirmBtnText + '</div>');
            buttonContainer.on('click', function() {
                self.closeMsgBox();
                if ($.isFunction(callback)) {
                    callback();
                }
            });
            alertWindow.append(buttonContainer);
            if (!options.type || options.type != 'alert') {
                var buttonContainer2 = $('<div style="width:60px; float:right;padding:12px 12px 20px;text-align:center; color:#157efb;cursor:pointer;">取消</div>');
                buttonContainer2.on('click', function() {
                    self.closeMsgBox();
                });
                alertWindow.append(buttonContainer2);
            }
        }
        alertWindow.css('top', (($(window).height() - alertWindow.height()) / 2));
        self.extend({
            closeMsgBox: function() {
                $('.windowMask,.alertWindow').remove();
            }
        })
    }
})