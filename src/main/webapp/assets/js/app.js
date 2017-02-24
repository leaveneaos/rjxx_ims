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
    });
    //设置Ajax全局参数
    $.ajaxSetup({
        statusCode: {
            401: function () {
                window.top.location = "/login/login";
            }
        }
    });
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
