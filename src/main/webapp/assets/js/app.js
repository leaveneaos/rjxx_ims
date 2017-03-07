
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
        $("#yjcd").html(cd2);
        $("#ejcd").html(cd1);
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




