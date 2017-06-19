(function ($) {
    'use strict';

    $(function () {
        // var $fullText = $('.admin-fullText');
        // $('#admin-fullscreen').on('click', function () {
        //     $.AMUI.fullscreen.toggle();
        // });

        // $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function () {
        //     $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
        // });
        

        var cd1 = $('#cd1', parent.document).val();
        var cd2 = $('#cd2', parent.document).val();
        if (cd1 != null && cd1 != "") {
            $(".am-text-primary").next().html(cd1);
        }
        if (cd2 != null && cd2 != "") {
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
    $(document).ajaxSuccess(function (event, xhr, settings) {
        var text = xhr.responseText;
        if (text.indexOf("<!DOCTYPE html") != -1) {
            top.location = "/";
        }
    }).ajaxError(function (event, xhr, settings) {
        var text = xhr.responseText;
        if (text.indexOf("<!DOCTYPE html") != -1) {
            top.location = "/";
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
        $.AMUI.validator.patterns.Money = /^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d{2})?$/;

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

// 全屏切换
function toggleFullScreen() {  
    var docEle = document.documentElement;
    if (!document.fullscreenElement && // alternative standard method  
        !document.mozFullScreenElement && !document.webkitFullscreenElement) {// current working methods  
            if (docEle.requestFullscreen) {  
                docEle.requestFullscreen();  
            }else if (docEle.mozRequestFullScreen) {  
                docEle.mozRequestFullScreen();  
            }else if (docEle.msRequestFullscreen) {  
                docEle.msRequestFullscreen();  
            }else if (docEle.webkitRequestFullscreen) {  
                docEle.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);  
        }  
        document.getElementById('data-fullscreen').innerHTML = ('退出全屏');
    } else {  
        if (document.cancelFullScreen) {  
            document.cancelFullScreen();  
        } else if (document.mozCancelFullScreen) {  
            document.mozCancelFullScreen();  
        }else if (document.msExitFullscreen) {  
            document.msExitFullscreen();  
        }else if (document.webkitCancelFullScreen) {  
            document.webkitCancelFullScreen();  
        }  
        document.getElementById('data-fullscreen').innerHTML = ('开启全屏');
    }  
}
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
$('.sidebar-nav-sub-title').on('click', function () {
    $(this).siblings('.sidebar-nav-sub').slideToggle(80)
        .end()
        .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
})


//提示层封装
$('#ck').click(function () {
    $.alert({
            text: '指定传入html片断文本',
            // type: 'alert'
        },
        function () {
            alert('回调函数')
        }
    )
});

$.extend({
    alert: function (options, callback) {
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
            buttonContainer.on('click', function () {
                self.closeMsgBox();
                if ($.isFunction(callback)) {
                    callback();
                }
            });
            alertWindow.append(buttonContainer);
            if (!options.type || options.type != 'alert') {
                var buttonContainer2 = $('<div style="width:60px; float:right;padding:12px 12px 20px;text-align:center; color:#157efb;cursor:pointer;">取消</div>');
                buttonContainer2.on('click', function () {
                    self.closeMsgBox();
                });
                alertWindow.append(buttonContainer2);
            }
        }
        alertWindow.css('top', (($(window).height() - alertWindow.height()) / 2));
        self.extend({
            closeMsgBox: function () {
                $('.windowMask,.alertWindow').remove();
            }
        })
    }
})
// 查询按钮退出
var $amOffcanvas = $('.am-offcanvas');

$(".data-back").click(function() {
    $amOffcanvas.removeClass('am-active')
});
//按Esc弹出框退出功能
$(document).keyup(function(e){
    var key =  e.which;
    if(key == 27){
        $('body').removeClass('am-dimmer-active')
        $('.am-dimmer').removeClass('am-active')
        $('.am-modal').removeClass('am-modal-active').addClass('am-modal-out')
    }
});