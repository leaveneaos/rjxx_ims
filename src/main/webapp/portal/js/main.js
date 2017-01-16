$(document).ready(function(){
	var urlinfo = window.location.href; //获取当前页面的url
	if (urlinfo.indexOf('?cs') > -1) {
		var len = urlinfo.length;//获取url的长度
		var offset = urlinfo.indexOf("?");//设置参数字符串开始的位置
		var newsidinfo = urlinfo.substr(offset, len);//取出参数字符串 这里会获得类似“id=1”这样的字符串
		var newsids = newsidinfo.split("=");//对获得的参数字符串按照“=”进行分割
		var csmc = newsids[1];//得到参数值
		$.post("../gszc/getLj", {id:csmc}, function(data) {
			$("#ghrzFrame").attr("src",data.tqlj.tqlj);
		});
		//$("#ghrzFrame").attr("src",csmc);
	/*	if("sqj"==csmc){
			$("#ghrzFrame").attr("src","http://fpj.datarj.com/smtq/sqj.html");
		}else if("zydc"==csmc){
			$("#ghrzFrame").attr('src','http://fpj.datarj.com/zydc.html');
		}else if("wljqr"==csmc){
			$("#ghrzFrame").attr('src','http://fpj.datarj.com/wljqr.html');
		}*/
	}
//    nav-li hover e
    var num;
    $('.nav-main>li[id]').hover(function(){
       /*图标向上旋转*/
        $(this).children().removeClass().addClass('hover-up');
        /*下拉框出现*/
        var Obj = $(this).attr('id');
        num = Obj.substring(3, Obj.length);
        $('#box-'+num).slideDown(300);
    },function(){
        /*图标向下旋转*/
        $(this).children().removeClass().addClass('hover-down');
        /*下拉框消失*/
        $('#box-'+num).hide();
    });
//    hidden-box hover e
    $('.hidden-box').hover(function(){
        /*保持图标向上*/
        $('#li-'+num).children().removeClass().addClass('hover-up');
        $(this).show();
    },function(){
        $(this).slideUp(200);
        $('#li-'+num).children().removeClass().addClass('hover-down');
    });
});
