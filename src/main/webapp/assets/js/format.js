/**
 * 格式化数字为1,111,111.00
 */
var isMoz = (typeof document.implementation != 'undefined')
		&& (typeof document.implementation.createDocument != 'undefined');
var isIE = (window.ActiveXObject !== undefined);
function GetXMLDOM() {
	var xmlDoc;
	if (isIE) {
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	} else if (isMoz) {
		xmlDoc = document.implementation.createDocument("", "", null);
	}
	return xmlDoc;
}
function parseXML(st) {
	var result;
	if (isIE) {
		result = GetXMLDOM();
		result.loadXML(st);
	} else {
		var parser = new DOMParser();
		result = parser.parseFromString(st, "text/xml");
	}
	return result;
}
function FormatFloat(value, mask) {
	if (value > -1 && value < 1) {
		return BasicFormat(value, mask, 'FormatNumber').toString().replace(".",
				"0.");
	}
	return BasicFormat(value, mask, 'FormatNumber');
}
function FormatDate(varDate, bstrFormat, varDestLocale) {
	return BasicFormat(varDate, bstrFormat, 'FormatDate', varDestLocale);
}
function FormatTime(varTime, bstrFormat, varDestLocale) {
	return BasicFormat(varTime, bstrFormat, 'FormatTime', varDestLocale);
}
function BasicFormat(value, mask, action, param) {
	var xmlDoc;
	var xslDoc;
	var v = '<formats><format><value>' + value + '</value><mask>' + mask
			+ '</mask></format></formats>';
	xmlDoc = parseXML(v);

	var x;
	if (isIE)
		x = '<xsl:stylesheet xmlns:xsl="uri:xsl">'
	else
		x = '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">';
	x += '<xsl:template match="/">';
	if (isIE) {
		x += '<xsl:eval>' + action + '(' + value + ',"' + mask + '"';
		if (param)
			x += ',' + param;
		x += ')</xsl:eval>';
	} else
		x += '<xsl:value-of select="format-number(' + value + ',\'' + mask
				+ '\')" />';

	x += '</xsl:template></xsl:stylesheet>';
	xslDoc = parseXML(x);
	var s;
	if (isIE)
		s = xmlDoc.transformNode(xslDoc)
	else {
		// for mozilla/netscape
		var processor = new XSLTProcessor();
		processor.importStylesheet(xslDoc);
		var result = processor.transformToFragment(xmlDoc, xmlDoc);
		var xmls = new XMLSerializer();
		s = xmls.serializeToString(result);
	}
	return s;
}

//导入文件校验，大小和类型
var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
function fileChange(target) {
    var fileSize = 0;
    var filetypes =[".jpg",".png",".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",".xlsx"];
    var filepath = target.value;
    var filemaxsize = 1024*2;//2M
    if(filepath){
        var isnext = false;
        var fileend = filepath.substring(filepath.indexOf("."));
        if(filetypes && filetypes.length>0){
            for(var i =0; i<filetypes.length;i++){
                if(filetypes[i]==fileend){
                    isnext = true;
                    break;
                }
            }
        }
        if(!isnext){
            swal("不接受此文件类型！");
            target.value ="";
            return false;
        }
    }else{
        return false;
    }
    if (isIE && !target.files) {
        var filePath = target.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        if(!fileSystem.FileExists(filePath)){
            swal("附件不存在，请重新输入！");
            return false;
        }
        var file = fileSystem.GetFile (filePath);
        fileSize = file.Size;
    } else {
        fileSize = target.files[0].size;
    }

    var size = fileSize / 1024;
    if(size>filemaxsize){
        swal("附件大小不能大于"+filemaxsize/1024+"M");
        target.value ="";
        return false;
    }
    if(size<=0){
        swal("附件大小不能为0M！");
        target.value ="";
        return false;
    }
}