<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/easyui14/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/easyui14/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/easyui14/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css">
<script type="text/javascript">var baseUrl = "<%=request.getContextPath()%>";</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/easyui14/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/easyui14/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/easyui14/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validaterules.js"></script>
<script type="text/javascript">
    function getpageQx(yhid, yhqx, ztbz, lrry, butName) {
        return true;
        if (butName == 'new') {
            if (yhqx.indexOf('m') >= 0) {
                return true;
            } else {
                return false;
            }
        } else if (butName == 'modify') {
            if (ztbz == '0') {
                if (yhqx.indexOf('m') >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (butName == 'del') {
            if (ztbz == '0') {
                if (yhqx.indexOf('m') >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (butName == 'check') {
            if (ztbz == '0') {
                if (yhid == lrry) {
                    //return false;
                    return true;
                } else {
                    if (yhqx.indexOf('c') >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }

        } else if (butName == 'back') {
            if (ztbz == '1') {
                if (yhid == lrry) {
                    //return false;
                    return true;
                } else {
                    if (yhqx.indexOf('c') >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }
    //把数字转换成千分位
    function formatDecimal(value) {
        if (value == null || value == "" || value == '') {
            return '';
        }
        var flag = false;
        var str = value.toString();
        if (str.indexOf("-") >= 0) {
			str = str.substr(1,str.length);
			flag = true;
		}
        
        val = parseFloat(str);
        var vz = isNaN(val) ? str : val.toFixed(2)
        var v, j, sj, rv = "";
        //v = id.value.replace(/,/g,"").split(".");
        v = vz.replace(/,/g, "").split(".");
        j = v[0].length % 3;
        sj = v[0].substr(j).toString();
        for (var i = 0; i < sj.length; i++) {
            rv = (i % 3 == 0) ? rv + "," + sj.substr(i, 1) : rv + sj.substr(i, 1);
        }
        var rvalue = (v[1] == undefined) ? v[0].substr(0, j) + rv : v[0].substr(0, j) + rv + "." + v[1];
        if (rvalue.charCodeAt(0) == 44) {
            rvalue = rvalue.substr(1);
        }
        if (flag) {
			rvalue = "-" + rvalue;
		}
        return rvalue;
        // var val = parseFloat(value);
        // return isNaN(val) ? value : val.toFixed(2);
    }
    //把千分位转换成正常数字
    function tran2(id) {
        var v;// v = id.value.replace(/,/g,"");
        v = id.replace(/,/g, "");
        return v;
    }

    //显示body
    $(document).ready(function () {
        $("body").css("visibility", "visible");
    });

    //设置Ajax全局参数
    $.ajaxSetup({
        statusCode: {
            401: function () {
                window.location = baseUrl + "/home/login";
            }
        }
    });
    

    $.ajaxSetup({
        statusCode: {
            501: function () {
                window.location.href =baseUrl + "/exception/index";
            }
        }
    });

</script>