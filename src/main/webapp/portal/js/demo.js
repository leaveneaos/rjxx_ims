/// <reference path="jquery-1.10.2.min.js" />
$(function () {
    $(".regitem input").blur(function () {
        var id = $(this).attr("id");//获取到了当前鼠标离开的那个文本框
        Check(id);
		
    });

    $(".dregbtn a").click(function () {
        Check("txtPhone");
        Check("name");
        Check("txtarea");
		Check("mesage");
    });
});

function Check(id) {
    var regStr = "";
    var sResult = "";
    var txt = $.trim($("#" + id).val());
    if (txt == "") {
        sResult = "各项不能为空";
        layer.tips(sResult, "#" + id, {
            tips: [2, '#78BA32']
        });
    }
    else {
        switch (id) {
            case "name":
                regStr = /[\u4E00-\u9FA5]{2,4}/;
                break;
            case "txtPhone":
                regStr = /0?(13|14|15|18)[0-9]{9}/;
                break;
				
        }
        if (regStr && regStr.test && !regStr.test(txt)) {
            sResult = "输入的内容格式不对";
            layer.tips(sResult, "#" + id, {
                tips: [2, '#78BA32']
            });
        }
    }
}

