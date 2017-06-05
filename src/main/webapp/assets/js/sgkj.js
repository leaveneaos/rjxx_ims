$(function() {
    "use strict";
    var jyspmx_table = $('#jyspmx_table').DataTable({
        "searching": false,
        "bPaginate": false,
        "bAutoWidth": false,
        "bSort": false,
        "scrollX" : true,
    });
    var detail_table=$("#detail_table").DataTable({
        "searching": false,
        "serverSide": true,
        "sServerMethod": "POST",
        "processing": true,
        "bSort":true,
        "scrollX": true,
        ordering: false,
        "ajax": {
            url: 'sgkj/getItems',
            type: 'POST',
            data: function (d) {
            }
        },
        "columns": [
            {"data": "spbm"},
            {"data": "spmc"},
            {"data": "spggxh"},
            {"data": "spdw"},
            {"data": "spdj"},
            {"data": "sl"},
         ]
    });
    var index = 1;
    var mxarr = [];
    $("#add").click(function () {
            index = mxarr.length + 1;
            jyspmx_table.row.add([
                 "<span class='index'>" + index + "</span>",
                '<input type="text" id="spmc"  name="spmc" readonly><input type="hidden" id="spbm" name="spbm">',
                '<input type="text" id="ggxh" name="ggxh">',
                '<input type="text" id="spdw" name="spdw">',
                '<input type="text" id="spsl" name="spsl"  style="text-align:right">',
                '<input type="text" id="spdj" name="spdj"  style="text-align:right">',
                '<input type="text" id="spje" name="spje"  style="text-align:right">',
                '<input type="text" id="taxrate" name="taxrate" class="selected" readonly style="text-align:right">',
                '<input type="text" id="spse" name="spse" style="text-align:right" class="selected" readonly>'
            ]).draw();
            mxarr.push(index);
    });
    $('#jyspmx_table tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            jyspmx_table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
    $("#del").click(function(){
        jyspmx_table.row('.selected').remove().draw(false);
        mxarr.pop();
        $('#jyspmx_table tbody').find("span.index").each(function (index, object) {
            $(object).html(index + 1);
        });
    });
    var value;
    jyspmx_table.on('click', 'input#spmc', function () {
        value=$(this).parent("td").parent("tr").children("td").eq(0).text();
        $("#spxx").modal({"width": 600, "height": 330});
        detail_table.ajax.reload();
    });
    $('#detail_table tbody').on('dblclick','tr',function(){
        var data = detail_table.row($(this)).data();
        $("#jyspmx_table").find("tr").eq(value).children("td").each(function(i,cell){
            if(i==1){
                $(cell).find('input[name="spmc"]').val(data.spmc);
                $(cell).find('input[name="spbm"]').val(data.spbm);
            }else if(i==2){
                $(cell).find('input[name="ggxh"]').val(data.spggxh);
            }else if(i==3){
                $(cell).find('input[name="spdw"]').val(data.spdw);
            }else if(i==4){
                $(cell).find('input[name="spsl"]').val("");
            }else if(i==5){
                $(cell).find('input[name="spdj"]').val(data.spdj == null ? '' : FormatFloat(data.spdj, "#####0.00"));
            }else if(i==6){
                $(cell).find('input[name="spje"]').val("");
            }else if(i==7){
                $(cell).find('input[name="taxrate"]').val(data.sl);
            }else if(i==8){
                $(cell).find('input[name="spse"]').val("");
            }
        });
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额
        var jshjstr=0;
        var hjjestr=0;
        var hjsestr=0;
        var taxrate=data.sl;
        var temp = (100 + taxrate * 100) / 100;//税率计算方式
        $("#jyspmx_table").find("tr").each(function(i,row){
            if(i!=0){
                jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1-$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
                hjsestr+=$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
            }
        });
        jshj.val(FormatFloat(jshjstr,"#####0.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#####0.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#####0.00"));//合计税额
        $("#spxx").modal("close");
    });
    //定义鼠标样式
    $("#jyspmx_table").css("cursor","pointer");
    jyspmx_table.on('input', 'input#spsl', function () {
        var  rowstr=$(this).parent("td").parent("tr").children("td").eq(0).text();//获取当前行数
        var spsl=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(4).find('input[name="spsl"]');//商品数量
        var spdj=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(5).find('input[name="spdj"]');//商品单价
        var spje=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(6).find('input[name="spje"]');//商品金额
        var taxrate=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(7).find('input[name="taxrate"]');//商品税率
        var spse=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(8).find('input[name="spse"]');//商品税额
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额

        var temp = (100 + taxrate.val() * 100) / 100;//税率计算方式
        if (spdj!= "") {
            spje.val(FormatFloat(spsl.val() * spdj.val(), "#####0.00"));
            var jj = spsl.val() * spdj.val();
            spse.val(FormatFloat((jj / temp) * taxrate.val(),"#####0.00"));
        }
        var jshjstr=0;
        var hjjestr=0;
        var hjsestr=0;
        $("#jyspmx_table").find("tr").each(function(i,row){
            if(i!=0){
                jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1-$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
                hjsestr+=$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
              }
        });
        jshj.val(FormatFloat(jshjstr,"#####0.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#####0.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#####0.00"));//合计税额
    });
    jyspmx_table.on('input', 'input#spdj', function () {
        var  rowstr=$(this).parent("td").parent("tr").children("td").eq(0).text();//获取当前行数
        var spsl=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(4).find('input[name="spsl"]');//商品数量
        var spdj=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(5).find('input[name="spdj"]');//商品单价
        var spje=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(6).find('input[name="spje"]');//商品金额
        var taxrate=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(7).find('input[name="taxrate"]');//商品税率
        var spse=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(8).find('input[name="spse"]');//商品税额
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额

        var temp = (100 + taxrate.val() * 100) / 100;//税率计算方式
        if (spsl!= "") {
            spje.val(FormatFloat(spsl.val() * spdj.val(), "#####0.00"));
            var jj = spsl.val() * spdj.val();
            spse.val(FormatFloat((jj / temp) * taxrate.val(),"#####0.00"));
        }
        var jshjstr=0;
        var hjjestr=0;
        var hjsestr=0;
        $("#jyspmx_table").find("tr").each(function(i,row){
            if(i!=0){
                jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1-$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
                hjsestr+=$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
            }
        });
        jshj.val(FormatFloat(jshjstr,"#####0.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#####0.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#####0.00"));//合计税额
    });
    jyspmx_table.on('input', 'input#spje', function () {
        var  rowstr=$(this).parent("td").parent("tr").children("td").eq(0).text();//获取当前行数
        var spsl=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(4).find('input[name="spsl"]');//商品数量
        var spdj=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(5).find('input[name="spdj"]');//商品单价
        var spje=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(6).find('input[name="spje"]');//商品金额
        var taxrate=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(7).find('input[name="taxrate"]');//商品税率
        var spse=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(8).find('input[name="spse"]');//商品税额
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额

        var temp = (100 + taxrate.val() * 100) / 100;//税率计算方式
        var jj = spje.val();
        spse.val(FormatFloat((jj / temp) * taxrate.val(),"#####0.00"));
        var jshjstr=0;
        var hjjestr=0;
        var hjsestr=0;
        if (spdj.val()!= "") {
            spsl.val(FormatFloat(spje.val() / spdj.val(), "#####0.00"));
        }else if(spdj.val()==""&&spsl.val()!=""){
            spdj.val(FormatFloat(spje.val() / spsl.val(), "#####0.00"));
        }
        $("#jyspmx_table").find("tr").each(function(i,row){
            if(i!=0){
                jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1-$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
                hjsestr+=$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
            }
        });
        jshj.val(FormatFloat(jshjstr,"#####0.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#####0.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#####0.00"));//合计税额
    });
    $("#kj").click(function(){
         var xf=$("#xf").val();//销方
         var kpd=$("#kpd").val();//开票点
         var fpzldm=$("#fpzldm").val();//发票种类代码
         var ddh=$("#ddh").val();//订单号
         var gfmc=$("#gfmc").val();//购方名称
         var gfsh=$("#gfsh").val();//购方税号
         var gfdz=$("#gfdz").val();//购方地址
         var gfdh=$("#gfdh").val();//购方电话
         var gfyh=$("#gfyh").val();//购方银行
         var yhzh=$("#yhzh").val();//购方银行账号
        if(xf==""){
            $("#xf").focus();
            swal("销方名称不能为空！");
            return;
        }
        if(kpd==""){
            $("#kpd").focus();
            swal("开票点不能为空！");
            return;
        }
        if(fpzldm==""){
            $("#fpzldm").focus();
            swal("发票种类不能为空！");
            return;
        }
        if(ddh==""){
            $("#ddh").focus();
            swal("订单号不能为空！");
            return;
        }
        if(gfmc==""){
            $("#gfmc").focus();
            swal("购方名称不能为空！");
            return;
        }
        if(fpzldm=="01"){
            if(gfsh==""){
                $("#gfsh").focus();
                swal("纳税人识别号不能为空！");
                return;
            }
            if(gfdz==""){
                $("#gfdz").focus();
                swal("购买方地址不能为空！");
                return;
            }
            if(gfdh==""){
                $("#gfdh").focus();
                swal("购买方电话不能为空！");
                return;
            }
            if(gfyh==""){
                $("#gfyh").focus();
                swal("购买方开户行不能为空！");
                return;
            }
            if(yhzh==""){
                $("#yhzh").focus();
                swal("购买方银行账号不能为空！");
                return;
            }
        }
        var ps = [];
        var errorspmessage="";
        var errorspjemessage="";
        $("#jyspmx_table").find("tr").each(function(j,row){
            $(row).children("td").each(function(i,cell){
                if(i==1){
                 var  spmc=$(cell).find('input[name="spmc"]').val();
                 if(spmc==""){
                     errorspmessage+="第"+j+"行，商品名称不能为空，请点击选择！";
                 }
                var spbm= $(cell).find('input[name="spbm"]').val();
                 ps.push("spmc=" + spmc);
                 ps.push("spbm=" + spbm);
                }else if(i==2){
                var ggxh=$(cell).find('input[name="ggxh"]').val();
                ps.push("ggxh=" + ggxh);
                }else if(i==3){
                var spdw=$(cell).find('input[name="spdw"]').val();
                ps.push("spdw=" + spdw);
                }else if(i==4){
                var spsl=$(cell).find('input[name="spsl"]').val();
                ps.push("spsl=" + spsl);
                }else if(i==5){
                var spdj=$(cell).find('input[name="spdj"]').val();
                ps.push("spdj=" + spdj);
                }else if(i==6){
                  var spje= $(cell).find('input[name="spje"]').val();
                  if(spje==""){
                      errorspjemessage+="第"+j+"行，商品金额不能为空！";
                  }
                ps.push("spje=" + spje);
                }else if(i==7){
                var taxrate=$(cell).find('input[name="taxrate"]').val();
                ps.push("taxrate=" + taxrate);
                }else if(i==8){
                var spse=$(cell).find('input[name="spse"]').val();
                ps.push("spse=" + spse);
                }
            });
        });
        if(errorspmessage!=""||errorspjemessage!=""){
            swal(errorspmessage+errorspjemessage);
            return;
        }
        var jshj=$("#jshj").val();
        var hjje=$("#hjje").val();
        var hjse=$("#hjse").val();
        var yjdz=$("#yjdz").val();
        var lxdh=$("#lxdh").val();
        var tqm=$("#tqm").val();
        var bz=$("#bz").val();
        ps.push("mxcount=" + index);
        var data="&xf="+xf+"&kpd="+kpd+"&fpzldm="+fpzldm+"&bz="+bz+
            "&ddh="+ddh+"&gfmc="+gfmc+"&gfsh="+gfsh+"&gfdz="
            +gfdz+"&gfdh="+gfdh+"&gfyh="+gfyh+"&yhzh="+yhzh+"&yjdz="+yjdz+"&lxdh="+lxdh+"&tqm="+tqm+
            "&jshj="+jshj+"&hjje="+hjje+"&hjse="+hjse+"&"+ps.join("&");
        $.ajax({
            url: "sgkj/save", "type": "POST", context: document.body, data: data, success: function (data) {
                if (data.success) {
                    swal("开票申请成功!");
                } else {
                    swal(data.msg);
                }
            }
        });
    });
});
