$(function() {
    "use strict";
    var jyspmx_table = $('#jyspmx_table').DataTable({
        "searching": false,
        "bPaginate": false,
        "bAutoWidth": false,
        "bSort": false,
        "scrollX" : true,
    });
	 var jyzfmx_table = $('#jyzfmx_table').DataTable({
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
        "lengthMenu": [ 20, 30, 40, 50, 100 ],
        "pageLength": 100,
        "scrollY": "300px",
        "scrollCollapse": "true",
        ordering: false,
        "ajax": {
            url: 'pttqkp/getItems',
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
    var f=true;

    $("#searchddh").click(function(){
        var ddh=$("#ddh").val();
        var xfid=$("#xf").val();
        if(ddh==""){
            $("#ddh").focus();
            swal("订单号不能为空！");
            return;
        }
        $.ajax({
            url : "pttqkp/findjyxxsq",
            data : {
                "ddh" : ddh,
                "xfid" : xfid
            },
            success : function(data) {
                if(data.jyxxsq!=null){
                    var jyxxsq = data.jyxxsq;
                    for(var i=0;i<jyxxsq.length;i++){
                        $("#isSave").val(jyxxsq[i].sqlsh);
                        $("#xf").val(jyxxsq[i].xfid);
                        var kpd = $("#kpd");
                        $("#kpd").empty();
                        $.ajax({
                            url : "fpkc/getKpd",
                            data : {
                                "xfid" : jyxxsq[i].xfid
                            },
                            success : function(test) {
                                var option = $("<option>").text('请选择').val(-1);
                                kpd.append(option);
                                for (var i = 0; i < test.length; i++) {
                                     option = $("<option>").text(test[i].kpdmc).val(test[i].skpid);
                                    kpd.append(option);
                                }
                                if(test.length==1){
                                    $("#kpd").find("option").eq(1).attr("selected",true);
                                    var skpid = $('#kpd option:selected').val();
                                    if(skpid !=null && skpid !=""){
                                        var fpzldm = $("#fpzldm");
                                        $("#fpzldm").empty();
                                        $.ajax({
                                            url : "pttqkp/getFpzldm",
                                            data : {
                                                "skpid" : skpid
                                            },
                                            success : function(data) {
                                                var option = $("<option>").text('请选择').val(-1);
                                                fpzldm.append(option);
                                                for (var i = 0; i < data.length; i++) {
                                                    option = $("<option>").text(data[i].fpzlmc).val(
                                                        data[i].fpzldm);
                                                    fpzldm.append(option);
                                                }
                                                if(data.length==1){
                                                    $("#fpzldm").find("option").eq(1).attr("selected",true)
                                                }
                                            }
                                        })
                                    }
                                }
                            }
                        });
                        //$("#kpd").val(jyxxsq[i].skpid);
                        //$("#fpzldm").val(jyxxsq[i].fpzldm);
                        $("#gfmc").val(jyxxsq[i].gfmc);
                        $("#gfsh").val(jyxxsq[i].gfsh);//购方税号
                        $("#gfdz").val(jyxxsq[i].gfdz);//购方地址
                        $("#gfdh").val(jyxxsq[i].gfdh);//购方电话
                        $("#gfyh").val(jyxxsq[i].gfyh);//购方银行
                        $("#yhzh").val(jyxxsq[i].gfyhzh);//购方银行账号
                        $("#yjdz").val(jyxxsq[i].gfemail);
                        $("#lxdh").val(jyxxsq[i].gfdh);
                        $("#tqm").val(jyxxsq[i].tqm);
                        $("#jshj").val(jyxxsq[i].jshj);
                        $("#bz").val(jyxxsq[i].bz);
                    }
					 var jyzflist=data.jyzflist;
					 var a=1;
					 jyzfmx_table.clear();
					 for(var i=0;i<jyzflist.length;i++){
                         jyzfmx_table.row.add([
                         '<span class="index">' + a + '</span>',
                         '<input type="text" id="zfmc" name="zfmc" readonly value="'+jyzflist[i].zfmc +'">',
                         '<input type="text" id="zfje" name="zfje" readonly value="'+jyzflist[i].zfje +'">'
                         ]).draw();
                         a++;
					 }
                    var jymxsq=data.jymxsq;
                    var b=1;
                    jyspmx_table.clear();
                    var jehj=0;
                    var sehj=0;
                    for(var j=0;j<jymxsq.length;j++){
                        var spggxh=jymxsq[j].spggxh== null ? '' : jymxsq[j].spggxh;
                        var spdw=jymxsq[j].spdw== null ? '' : jymxsq[j].spdw;
                        var sps=jymxsq[j].sps== null ? '' : jymxsq[j].sps;
                        var spdj=jymxsq[j].spdj== null ? '' : jymxsq[j].spdj;
                        var spje=jymxsq[j].spje== null ? '' : jymxsq[j].spje;
                        var spsl=jymxsq[j].spsl== null ? '' : jymxsq[j].spsl;
                        var spse=jymxsq[j].spse== null ? '' : jymxsq[j].spse;
                        var yhzcbs=jymxsq[j].spse== null ? '' : jymxsq[j].yhzcbs;
                        var yhzcmc=jymxsq[j].spse== null ? '' : jymxsq[j].yhzcmc;
                        var lslbz=jymxsq[j].spse== null ? '' : jymxsq[j].lslbz;
                        jyspmx_table.row.add([
                            "<span class='index'>" + b + "</span>",
                            '<input type="text" id="spmc"  name="spmc" value="'+jymxsq[j].spmc+'" readonly><input type="hidden" id="spbm" name="spbm" readonly value="'+jymxsq[j].spdm+'">'+
                            '<input type="hidden" id="yhzcbs" name="yhzcbs" readonly value="'+yhzcbs+'"><input type="hidden" id="yhzcmc" name="yhzcmc" readonly value="'+yhzcmc+'"><input type="hidden" id="lslbz" name="lslbz" readonly value="'+lslbz+'">',
                            '<input type="text" id="ggxh" name="ggxh"  readonly value="'+spggxh+'" >',
                            '<input type="text" id="spdw" name="spdw"  readonly value="'+spdw+'" >',
                            '<input type="text" id="spsl" name="spsl"  readonly style="text-align:right" value="'+sps+'" >',
                            '<input type="text" id="spdj" name="spdj"  readonly style="text-align:right" value="'+spdj+'" >',
                            '<input type="text" id="spje" name="spje"  readonly style="text-align:right" value="'+spje+'" >',
                            '<input type="text" id="taxrate" name="taxrate" class="selected" readonly style="text-align:right" value="'+spsl+'" >',
                            '<input type="text" id="spse" name="spse" style="text-align:right" class="selected" readonly value="'+spse+'" >'
                        ]).draw();
                        b++;
                        jehj+=jymxsq[j].spje/1;
                        sehj+=jymxsq[j].spse/1;
                    }

                    $("#hjje").val(jehj);
                    $("#hjse").val(sehj);

                }else {
                    if(null!= data.error){
                        swal("提示:\r\n"+data.error);
                    }
                    if(null!=data.msg){
                        swal("提示:\r\n"+data.msg);
                    }
                    if(null!=data.temp){
                        swal("提示:\r\n"+data.temp);
                    }
                    jyspmx_table.clear().draw();
                    jyzfmx_table.clear().draw();
                    $("input").val('');
                    $("#ddh").val(ddh)
                }
            }
        });
    });
    $('#jyspmx_table tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            jyspmx_table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });
    var value;
    $('#detail_table tbody').on('click','tr',function(){
        var data = detail_table.row($(this)).data();
        $("#jyspmx_table").find("tr").eq(value).children("td").each(function(i,cell){
            if(i==1){
                $(cell).find('input[name="spmc"]').val(data.spmc);
                $(cell).find('input[name="spbm"]').val(data.spbm);
                $(cell).find('input[name="yhzcbs"]').val(data.yhzcbs==null?"":data.yhzcbs);
                $(cell).find('input[name="yhzcmc"]').val(data.yhzcmc==null?"":data.yhzcmc);
                $(cell).find('input[name="lslbz"]').val(data.lslbz==null?"":data.lslbz);
            }else if(i==2){
                $(cell).find('input[name="ggxh"]').val(data.spggxh);
            }else if(i==3){
                $(cell).find('input[name="spdw"]').val(data.spdw);
            }else if(i==4){
                $(cell).find('input[name="spsl"]').val("");
            }else if(i==5){
                $(cell).find('input[name="spdj"]').val(data.spdj == null ? '' : FormatFloat(data.spdj, "#.00####"));
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
        jshj.val(FormatFloat(jshjstr,"#.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#.00"));//合计税额
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
            spje.val(FormatFloat(spsl.val() * spdj.val(), "#.00"));
            var jj = spsl.val() * spdj.val();
            spse.val(FormatFloat((jj / temp) * taxrate.val(),"#.00"));
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
        jshj.val(FormatFloat(jshjstr,"#.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#.00"));//合计税额
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
            spje.val(FormatFloat(spsl.val() * spdj.val(), "#.00"));
            var jj = spsl.val() * spdj.val();
            spse.val(FormatFloat((jj / temp) * taxrate.val(),"#.00"));
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
        jshj.val(FormatFloat(jshjstr,"#.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#.00"));//合计税额
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
        spse.val(FormatFloat((jj / temp) * taxrate.val(),"#.00"));
        var jshjstr=0;
        var hjjestr=0;
        var hjsestr=0;
        if (spdj.val()!= "") {
            spsl.val(FormatFloat(spje.val() / spdj.val(), "#.00####"));
        }else if(spdj.val()==""&&spsl.val()!=""){
            spdj.val(FormatFloat(spje.val() / spsl.val(), "#.00####"));
        }
        $("#jyspmx_table").find("tr").each(function(i,row){
            if(i!=0){
                jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1-$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
                hjsestr+=$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
            }
        });
        jshj.val(FormatFloat(jshjstr,"#.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#.00"));//合计税额
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
        var isSave=$("#isSave").val();//判断本地是否有数据，有数据为t_jyxxsq的申请流水号
        var jshj=$("#jshj").val();//判断是否有数据
        if(jshj==null || jshj=="" || Number(jshj)==0){
            swal("可开具金额为0！");
            return;
        }
        if(xf==""){
            $("#xf").focus();
            swal("销方名称不能为空！");
            return;
        }
        if(null == kpd || kpd==""){
            $("#kpd").focus();
            swal("开票点不能为空！");
            return;
        }
        if(kpd == -1){
            $("#kpd").focus();
            swal("请选择开票点名称！");
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
        var  sfbx="0";
        if ($("#sfbx").is(':checked')) {
            if(gfsh==""){
                $("#gfsh").focus();
                swal("公司购方税号不能为空！");
                return;
            }
            sfbx="1";
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
        if(gfsh!=null && gfsh!=""){
            if(gfsh.length!=15&&gfsh.length!=18&&gfsh.length!=20&&gfsh.length!=9){
                swal("购方税号长度有误！");
                $("#gfsh").focus();
                return;
            }
        }
        var ps = [];
        var errorspmessage="";
        var errorspjemessage="";
        // $("#jyspmx_table").find("tr").each(function(j,row){
        //     $(row).children("td").each(function(i,cell){
        //         if(i==1){
        //             var  spmc=$(cell).find('input[name="spmc"]').val();
        //             if(spmc==""){
        //                 errorspmessage+="第"+j+"行，商品名称不能为空，请点击选择！";
        //             }
        //             var spbm= $(cell).find('input[name="spbm"]').val();
        //             var yhzcbs= $(cell).find('input[name="yhzcbs"]').val();
        //             var yhzcmc= $(cell).find('input[name="yhzcmc"]').val();
        //             var lslbz= $(cell).find('input[name="lslbz"]').val();
        //             ps.push("spmc=" + spmc);
        //             ps.push("spbm=" + spbm);
        //             ps.push("yhzcbs=" + yhzcbs);
        //             ps.push("yhzcmc=" + encodeURI(encodeURI(yhzcmc)));
        //             ps.push("lslbz=" + lslbz);
        //         }else if(i==2){
        //             var ggxh=$(cell).find('input[name="ggxh"]').val();
        //             ps.push("ggxh=" + ggxh);
        //         }else if(i==3){
        //             var spdw=$(cell).find('input[name="spdw"]').val();
        //             ps.push("spdw=" + spdw);
        //         }else if(i==4){
        //             var spsl=$(cell).find('input[name="spsl"]').val();
        //             ps.push("spsl=" + spsl);
        //         }else if(i==5){
        //             var spdj=$(cell).find('input[name="spdj"]').val();
        //             ps.push("spdj=" + spdj);
        //         }else if(i==6){
        //             var spje= $(cell).find('input[name="spje"]').val();
        //             if(spje==""){
        //                 errorspjemessage+="第"+j+"行，商品金额不能为空！";
        //             }
        //             ps.push("spje=" + spje);
        //         }else if(i==7){
        //             var taxrate=$(cell).find('input[name="taxrate"]').val();
        //             ps.push("taxrate=" + taxrate);
        //         }else if(i==8){
        //             var spse=$(cell).find('input[name="spse"]').val();
        //             ps.push("spse=" + spse);
        //         }
        //     });
        // });
        if(errorspmessage!=""||errorspjemessage!=""){
            swal(errorspmessage+errorspjemessage);
            return;
        }
        var yjdz=$("#yjdz").val();
        var lxdh=$("#lxdh").val();
        var bz=$("#bz").val();
        var tqm=$("#tqm").val();
        if(bz.length>65){
            swal("备注过长，只能为65个字符！");
            return;
        }

        var data="&xf="+xf+"&kpd="+kpd+"&fpzldm="+fpzldm+"&bz="+bz+"&isSave="+isSave+
            "&gfmc="+gfmc+"&gfsh="+gfsh+"&gfdz="
            +gfdz+"&gfdh="+gfdh+"&gfyh="+gfyh+"&yhzh="+yhzh+"&yjdz="+yjdz+"&lxdh="+lxdh+"&tqm="+tqm;
        swal({
            title: "提示",
            text: "您确定要申请开票吗？",
            type: "warning",
            showCancelButton: true,
            closeOnConfirm: false,
            confirmButtonText: "确 定",
            confirmButtonColor: "#ec6c62"
        }, function() {
            $('.confirm').attr('disabled',"disabled");
            $.ajax({
                url: "pttqkp/save", "type": "POST", context: document.body, data: data, success: function (data) {
                    if (data.success) {
                        $('.confirm').removeAttr('disabled');
                        swal("开票申请成功!");
                        $("input").val('');
                        $("textarea").val('');
                        var SelectArr = $("select");
                        for (var i = 0; i < SelectArr.length; i++) {
                            SelectArr[i].options[0].selected = true;
                            $("#kpd").val('');
                        }
                        jyspmx_table.clear().draw();
                        jyzfmx_table.clear().draw();
                        $("input").val('');
                        $("#isSave").val("-1");
                    } else {
                        swal(data.msg);
                    }
                }
            });
        });
    });
    //添加重置功能
    $('#cz').on('click',function() {
        $("input").val('');
        $("textarea").val('');
        var SelectArr = $("select");
        for (var i = 0; i < SelectArr.length; i++) {
            SelectArr[i].options[0].selected = true;
            $("#kpd").val('');
        }
    })
});
