
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
        $(".am-text-primary").html(cd2);
        $(".am-text-primary").next().html(cd1);
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