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