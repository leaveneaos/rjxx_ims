<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<!-- 此控件用来直接调用浏览器的一些方法 -->
<object id="WebBrowser"
	classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"
	style="display: none"> </object>
<!-- 适用于打印机的样式设置 -->
<style type="text/css" media="print">　　
  .Noprint {display:none;}
  .PageNext {page-break-after: always;}　
</style>
<script type="text/javascript" language="javascript">
var hkey_root,hkey_path,hkey_key;
    hkey_root="HKEY_CURRENT_USER";
    hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
//配置网页打印的页眉页脚为空
function pagesetup_null(){  
    try{
        var RegWsh = new ActiveXObject("WScript.Shell");          
        hkey_key="header";          
        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
        hkey_key="footer";
        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
    }catch(e){}
}
//页眉页脚默认
function pagesetup_default()
{
  try{
    var RegWsh = new ActiveXObject("WScript.Shell")
    hkey_key="header"    
    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P")
    hkey_key="footer"
    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&u&b&d")
  }catch(e){}
}

	//打印
	function printWithAlert() {
		if(window.ActiveXObject !== undefined){
			document.all.Noprint.style.display="none";
			pagesetup_default();
			document.all.WebBrowser.ExecWB(6, 1);
			document.all.Noprint.style.display="block";
			var $ = window.parent.opener.$;
			var id = $("#kplshStr").val();
			$.ajax({
				url:'fpcx/update',
				data:{"ids":id},
				success:function(){
					
				}
			})
			window.parent.opener.refresh();
		}else{
			document.getElementById('Noprint').style.display = 'none';
			window.print()
		}
		
	};
	//直接打印
	function printWithoutAlert() {
		document.all.WebBrowser.ExecWB(6, 6);
	};
	//打印设置
	function printSetup() {
		document.all.WebBrowser.ExecWB(8, 1);
	};

	//打印预览
	// function printPrieview() {
	// 	if(window.ActiveXObject !== undefined){
	// 		document.all.Noprint.style.display="none";
	// 		pagesetup_default();
	// 		document.all.WebBrowser.ExecWB(7, 1);
	// 		document.all.Noprint.style.display="block";
	// 	}else{
	// 		alert("请使用ie浏览器！");
	// 	}
		
		
	// };

	function printImmediately() {
		document.all.WebBrowser.ExecWB(6, 6);
		window.close();
	};
	
</script>
</head>
<body>
	<center>
		<div style="width: 100%">
		     <div class="Noprint" id="Noprint" style="display:block">
				<input type="button" style="width: 100px; height: 35px"
						onclick="printWithAlert()" value="打印">
				<!-- <input type="button" style="width: 100px; height: 35px"
						onclick="printPrieview()" value="预览"> -->
			 </div>
			<c:forEach items="${kpList}" var="kp" varStatus="status">
				<c:if test="${num!=status.index+1}">
					<div class="PageNext" style="width: 100%" id="${kp.kplsh}">
						<img  style="width: 100%;" src="${kp.pdfurl}"/>
					</div>
				</c:if>
				<c:if test="${num==status.index+1}">
					<div  style="width: 100%" id="${kp.kplsh}">
						<img  style="width: 100%;" src="${kp.pdfurl}"/>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</center>
</body>
</html>