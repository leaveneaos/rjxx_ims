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
    })
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
            url: 'sgkj1/getItems',
            type: 'POST',
            data: function (d) {
                d.xfid = $("#xf").val();
                var spmc = $("#s_spmc").val();
                if(spmc !=null && spmc !=""){
                    d.spmc = spmc;
                }
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
    var zffs_table=$("#zffs_table").DataTable({
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
            url: 'sgkj1/getItems1',
            type: 'POST',
            data: function (d) {
                d.xfid = $("#xf").val();
            }
        },
        "columns": [
            {"data": "zffsMc"}
        ]
    });
    var index = 1;
    var indexzf =0;
    var mxarr = [];
    var zfarr = [];
    var f=true;
    var isCha = false;
    var $modal = $("#discountInfo");
    var $moda2 = $("#difInfo");
    $('#button1').on('click', function(e) {
        e.preventDefault();
        detail_table.ajax.reload();
    });
    $("#add").click(function () {

        var tr=$("#jyspmx_table").find("tr");
        var arry=getAllRowDataArry();
        f=false;
        if(arry.length>1){
            index = arry.length + 1;
        }else if(arry.length==1){
            for(var i=0;i<arry.length;i++){
                if(arry[i].spmc==null){
                    index=1;
                }else {
                    if(isCha){
                        swal("开具差额，商品最大行数为1，请重试！");
                        return ;
                    }
                    index=arry.length+1;
                }
            }
        }
        jyspmx_table.row.add([
            "<span class='index' id='xhf'>" + index + "</span>",
            '<input type="text" id="spmc"  name="spmc" readonly><input type="hidden" id="spbm" name="spbm">' +
            '<input type="hidden" id="yhzcbs" name="yhzcbs"><input type="hidden" id="yhzcmc" name="yhzcmc"><input type="hidden" id="lslbz" name="lslbz"><input type="hidden" id="fphxz" name="fphxz"  value="0" >',
            '<input type="text" id="ggxh" name="ggxh">',
            '<input type="text" id="spdw" name="spdw">',
            '<input type="text" id="spsl" name="spsl" oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right">',
            '<input type="text" id="spdj" name="spdj" oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right">',
            '<input type="text" id="spje" name="spje" oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" onchange="this.value=fomart(this.value)" style="text-align:right">',
            '<input type="text" id="taxrate" name="taxrate" class="selected" readonly style="text-align:right">',
            '<input type="text" id="spse" name="spse" style="text-align:right" class="selected" readonly>'
        ]).draw();
        mxarr.push(index);
    });

    $("#addzf").click(function () {
        var tr=$("#jyzfmx_table").find("tr");
        if(tr.length>2){
            if(f){
                tr.each(function(i,row){
                    if(i!=0){
                        zfarr.push($(row).children("td").eq(0).text()/1);
                    }
                });
            }
        }else if(tr.length==2){
            if(f){
                tr.each(function(i,row){
                    if(i!=0){
                        var text=$(row).children("td").eq(0).text();
                        if(text!="表中数据为空"){
                            zfarr.push($(row).children("td").eq(0).text()/1);
                        }
                    }
                });
            }
        }
        f=false;
        indexzf = zfarr.length + 1;
        jyzfmx_table.row.add([
            '<span class="index">' + indexzf + '</span>',
            '<input type="text" id="zfmc" name="zfmc"><input type="hidden" id="zffsDm" name="zffsDm">',
            '<input type="text" id="zfje" name="zfje">'
        ]).draw();
        zfarr.push(indexzf);
    });

    //折扣处理
    $("#discount").click(function () {
        $('#cha').attr("disabled",true);
        $('#disAmount').val("");
        $('#xz_gfdz').val("");
        $('#zkl').val("");
        var tr=$("#jyspmx_table").find("tr");
        var jshjstr=0;
        var disT= true;
        var j =0;
        var firstje;
        if(tr.length>2){
            tr.each(function(i,row){
                if(i==1){
                    firstje=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                }
                if(i!=0){
                    if($(row).children("td").eq(6).find('input[name="spje"]').val()/1==0){
                        swal("提示:\r\n"+"第"+i+"行商品金额为0,不能添加折扣");
                        disT=false;
                        return ;
                    }
                    if($(row).children("td").eq(1).find('input[name="spmc"]').val()/1==""){
                        swal("提示:\r\n"+"第"+i+"行货物或应税劳务、服务名称不能为空,不能添加折扣");
                        disT=false;
                        return ;
                    }
                    jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                    j=i;
                }
            });
            if(disT){
                $('#disNum').val(1);
                $('#amount').val(firstje);

                $modal.modal({"width": 600, "height": 280});
            }

        }else{
            tr.each(function(i,row){
                if(i==1){
                    firstje=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                }
                if(i!=0){
                    var text=$(row).children("td").eq(0).text();
                    if(text=="表中数据为空"){
                        swal("提示:\r\n"+"请先添加商品");
                    }else {
                        jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                        if($(row).children("td").eq(1).find('input[name="spmc"]').val()/1==""){
                            swal("提示:\r\n"+"第"+i+"行货物或应税劳务、服务名称不能为空,不能添加折扣");
                            disT=false;
                            return ;
                        }
                        if($(row).children("td").eq(6).find('input[name="spje"]').val()/1==0){
                            swal("提示:\r\n"+"第"+i+"行商品金额为0,不能添加折扣");
                            disT=false;
                            return ;
                        }
                        if(disT){
                            $('#amount').val(firstje);
                            $('#disNum').val(1);
                            $modal.modal({"width": 600, "height": 280});
                        }
                    }
                }
            });
        }
    });

    //删除明细
    $("#del").click(function(){
        // isCha=false;
        var nu = jyspmx_table.row('.selected').index()+1;
        var bl = true;
        var fphxz=$("#jyspmx_table").find("tr").eq(nu).children("td").eq(1).find('input[name="fphxz"]').val();
        if(fphxz!=null && fphxz =="2"){
            bl=false;
        }
        if(!bl){
            swal("请先删除折扣行");
            return;
        }
        // console.log(fphxz);
        if(fphxz!=null && fphxz=="1"){
            var index2 = jyspmx_table.row('.selected').index();
            $("#jyspmx_table").find("tr").eq(index2).children("td").eq(1).find('input[name="fphxz"]').val("0");
            jyspmx_table.draw();
        }
        jyspmx_table.row('.selected').remove().draw(false);
        mxarr.pop();
        $('#jyspmx_table tbody').find("span.index").each(function (index, object) {
            $(object).html(index + 1);
        });
        index=index-1;
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额
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
        jshj.val(FormatFloat(jshjstr,"#.##"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#.##"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#.##"));//合计税额
    });

    //删除支付明细
    $("#delzf").click(function(){
        jyzfmx_table.row('.selected').remove().draw(false);
        zfarr.pop();
        $('#jyzfmx_table tbody').find("span.index").each(function (indexzf, object) {
            $(object).html(indexzf + 1);
        });
        indexzf=indexzf-1;
        // indexzf = index;
    });
    //查询获取数据
    $("#searchddh").click(function(){
        var ddh=$("#ddh").val();
        var xfid=$("#xf").val();
        if(ddh==null|| ddh==""){
            swal("请输入订单号");
            return;
        }
        $.ajax({
            url : "sgkj1/findjyxxsq",
            data : {
                "ddh" : ddh,
                "xfid" : xfid
            },
            success : function(data) {
                if(data.jyxxsq!=null){
                    var jyxxsq = data.jyxxsq;
                    for(var i=0;i<jyxxsq.length;i++){
                        $("#xf").val(jyxxsq[i].xfid);
                        var kpd = $("#kpd");
                        $("#kpd").empty();
                        $.ajax({
                            url : "fpkc/getKpd",
                            data : {
                                "xfid" : jyxxsq[i].xfid
                            },
                            success : function(data) {
                                for (var i = 0; i < data.length; i++) {
                                    var option = $("<option>").text(data[i].kpdmc).val(
                                        data[i].skpid);
                                    kpd.append(option);
                                }
                            }
                        });
                        $("#kpd").val(jyxxsq[i].skpid);
                        var kpddm = jyxxsq[i].kpddm;
                        if(kpddm !=null && kpddm !=""){
                            var fpzldm = $("#fpzldm");
                            $("#fpzldm").empty();
                            $.ajax({
                                url : "pttqkp/getFpzldm",
                                data : {
                                    "kpddm" : kpddm
                                },
                                success : function(data) {
                                    var option = $("<option>").text('请选择').val(-1);
                                    fpzldm.append(option);
                                    for (var i = 0; i < data.length; i++) {
                                        option = $("<option>").text(data[i].fpzlmc).val(
                                            data[i].fpzldm);
                                        fpzldm.append(option);
                                    }
                                }
                            });
                        }
                        $("#mainform").find('select[name="fpzldm"]').val(jyxxsq[i].fpzldm);
                         // $("#fpzldm").val(jyxxsq[i].fpzldm);
                        $("#gfmc").val(jyxxsq[i].gfmc);
                        $("#gfsh").val(jyxxsq[i].gfsh);//购方税号
                        $("#gfdz").val(jyxxsq[i].gfdz);//购方地址
                        $("#gfdh").val(jyxxsq[i].gfdh);//购方电话
                        $("#gfyh").val(jyxxsq[i].gfyh);//购方银行
                        $("#yhzh").val(jyxxsq[i].gfyhzh);//购方银行账号
                        $("#jshj").val(jyxxsq[i].jshj);

                        $("#yjdz").val(jyxxsq[i].gfemail);
                        $("#lxdh").val(jyxxsq[i].gfdh);
                        $("#tqm").val(jyxxsq[i].tqm);
                    }

                    var jyzfmx=data.jyzfmx;
                    var a=1;
                    jyzfmx_table.clear();
                    for(var i=0;i<jyzfmx.length;i++){
                        jyzfmx_table.row.add([
                            '<span class="index">' + a + '</span>',
                            '<input type="text" id="zfmc" name="zfmc"  value="'+jyzfmx[i].zfmc +'"><input type="hidden" id="zffsDm" name="zffsDm"  value="'+jyzfmx[i].zffsDm +'">',
                            '<input type="text" id="zfje" name="zfje"  value="'+jyzfmx[i].zfje +'">'
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
                        var fphxz=jymxsq[j].fphxz== null ? 0 : jymxsq[j].fphxz;
                        var spdw=jymxsq[j].spdw== null ? '' : jymxsq[j].spdw;
                        var sps=jymxsq[j].sps== null ? '' : jymxsq[j].sps;
                        var spdj=jymxsq[j].spdj== null ? '' : jymxsq[j].spdj;
                        var spje=jymxsq[j].spje== null ? '' : jymxsq[j].spje;
                        var spsl=jymxsq[j].spsl== null ? '' : jymxsq[j].spsl;
                        var spse=jymxsq[j].spse== null ? '' : jymxsq[j].spse;
                        var yhzcbs=jymxsq[j].spse== null ? '' : jymxsq[j].yhzcbs;
                        var yhzcmc=jymxsq[j].spse== null ? '' : jymxsq[j].yhzcmc;
                        var lslbz=jymxsq[j].spse== null ? '' : jymxsq[j].lslbz;
                        if(spje!=null && spje!=""){
                            spje=FormatFloat(spje,"#.00")
                        }
                        jyspmx_table.row.add([
                            "<span class='index' id='xhf'>" + b + "</span>",
                            '<input type="text" id="spmc"  name="spmc" value="'+jymxsq[j].spmc+'" readonly><input type="hidden" id="spbm" name="spbm" value="'+jymxsq[j].spdm+'">'+
                            '<input type="hidden" id="yhzcbs" name="yhzcbs" value="'+yhzcbs+'"><input type="hidden" id="yhzcmc" name="yhzcmc" value="'+yhzcmc+'"><input type="hidden" id="lslbz" name="lslbz" value="'+lslbz+'">' +
                            '<input type="hidden" id="fphxz"   name="fphxz"  value="'+fphxz+'" >',
                            '<input type="text" id="ggxh" name="ggxh"  value="'+spggxh+'" readonly>',
                            '<input type="text" id="spdw" name="spdw"  value="'+spdw+'" readonly>',
                            '<input type="text" id="spsl" name="spsl"  style="text-align:right" value="'+sps+'" readonly>',
                            '<input type="text" id="spdj" name="spdj"  style="text-align:right" value="'+spdj+'" readonly>',
                            '<input type="text" id="spje" name="spje"  style="text-align:right" value="'+spje+'" readonly>',
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
                        swal(data.error);
                    }
                    if(null!=data.msg){
                        swal(data.msg);
                    }
                    if(null!=data.temp){
                        swal(data.temp);
                    }
                }
            }
        });
    });
    //商品明细选中
    $('#jyspmx_table tbody').on( 'click', 'tr', function () {
        // if ( $(this).hasClass('selected') ) {
        //     $(this).removeClass('selected');
        // }
        // else {
        jyspmx_table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        // }
    });
    //支付明细选中
    $('#jyzfmx_table tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            jyzfmx_table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });
    var value;

    jyspmx_table.on('click', 'input#spmc', function () {
        var  rowstr=$(this).parent("td").parent("tr").children("td").eq(0).text();//获取当前行数
        var fphxz=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(1).find('input[name="fphxz"]').val();
        var bl = true;
        if(fphxz!=null && fphxz !="0"){
            bl=false;
        }
        if(!bl){
            swal("请先删除折扣行");
            return;
        }
        if(bl){
            value=$(this).parent("td").parent("tr").children("td").eq(0).text();
            $("#spxx").modal({"width": 720, "height": 500});
            detail_table.ajax.reload();
        }

    });

    jyzfmx_table.on('click', 'input#zfmc', function () {
        value=$(this).parent("td").parent("tr").children("td").eq(0).text();
        $("#zffs").modal({"width": 720, "height": 500});
        zffs_table.ajax.reload();
    });

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
        if(isCha){
            $moda2.modal({"width": 600, "height": 280});
        }
    });

    $('#zffs_table tbody').on('click','tr',function(){
        var data = zffs_table.row($(this)).data();
        if(data !=null){
            $("#jyzfmx_table").find("tr").eq(value).children("td").each(function(i,cell){
                if(i==1){
                    $(cell).find('input[name="zfmc"]').val(data.zffsMc);
                    $(cell).find('input[name="zffsDm"]').val(data.zffsDm);
                }
            });
        }
        $("#zffs").modal("close");
    });

    //定义鼠标样式
    $("#jyspmx_table").css("cursor","pointer");
    jyspmx_table.on('input', 'input#spsl', function () {
        var  rowstr=$(this).parent("td").parent("tr").children("td").eq(0).text();//获取当前行数
        var fphxz=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(1).find('input[name="fphxz"]').val();
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
        var fphxz=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(1).find('input[name="fphxz"]').val();
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
        if(isCha){
            $moda2.modal({"width": 600, "height": 280});
            return;
        }
        var  rowstr=$(this).parent("td").parent("tr").children("td").eq(0).text();//获取当前行数
        var fphxz=$("#jyspmx_table").find("tr").eq(rowstr).children("td").eq(1).find('input[name="fphxz"]').val();
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
        // spje.val(FormatFloat(spje.val(),"#.00"));
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

        var zfmc=$("#zfmc").val();//支付名称
        var zfje=$("#zfje").val();//支付名称

        var kce =$("#kce").val();//可开额

        var filter  = /^[-_a-zA-Z0-9]+$/;
        if(indexzf !=null && indexzf !=0){
            if(null == zfmc || zfmc==""){
                $("#zfmc").focus();
                swal("支付名称不能为空！");
                return;
            }
            if(null == zfje || zfje==""){
                $("#zfje").focus();
                swal("支付金额不能为空！");
                return;
            }
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
        if(!filter.test(ddh)){
            $("#ddh").focus();
            swal("请输入正确的订单号！");
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
                swal("用于报销时，纳税人识别号不能为空！");
                return;
            }
            if(gfsh.length!=15&&gfsh.length!=18&&gfsh.length!=20){
                swal("购买方税号长度有误！");
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
            if(gfsh.length!=15&&gfsh.length!=18&&gfsh.length!=20){
                swal("购买方税号长度有误！");
                return;
            }
        }
        var ps = [];
        var zf = [];
        var errorspmessage="";
        var errorspjemessage="";
        $("#jyzfmx_table").find("tr").each(function(j,row){
            $(row).children("td").each(function(i,cell){
                if(i==1){
                    var  zfmc=$(cell).find('input[name="zfmc"]').val();
                    var zffsDm= $(cell).find('input[name="zffsDm"]').val();
                    zf.push("zffsDm=" + zffsDm);
                    zf.push("zfmc=" + zfmc);
                }else if(i==2){
                    var zfje= $(cell).find('input[name="zfje"]').val();
                    zf.push("zfje=" + zfje);
                }
            });
        });
        $("#jyspmx_table").find("tr").each(function(j,row){
            $(row).children("td").each(function(i,cell){
                if(i==1){
                    var  spmc=$(cell).find('input[name="spmc"]').val();
                    if(spmc==""){
                        errorspmessage+="第"+j+"行，商品名称不能为空，请点击选择！";
                    }
                    var spbm= $(cell).find('input[name="spbm"]').val();
                    var yhzcbs= $(cell).find('input[name="yhzcbs"]').val();
                    var yhzcmc= $(cell).find('input[name="yhzcmc"]').val();
                    var lslbz= $(cell).find('input[name="lslbz"]').val();
                    var fphxz= $(cell).find('input[name="fphxz"]').val();
                    ps.push("spmc=" + spmc);
                    ps.push("spbm=" + spbm);
                    ps.push("yhzcbs=" + yhzcbs);
                    ps.push("yhzcmc=" + encodeURI(encodeURI(yhzcmc)));
                    ps.push("lslbz=" + lslbz);
                    ps.push("fphxz=" + fphxz);
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
        if(ps.length==0){
            swal("请选择商品！");
            return;
        }
        if(bz.length>120){
            swal("备注过长，只能为120个字符！");
            return;
        }
        ps.push("kce=" + kce);
        ps.push("mxcount=" + index);
        zf.push("zfcount=" + indexzf);
        var is="";
        if(isCha){
            is="1"
        }else {
            is="0"
        }
        if(jshj==null || jshj=="" || jshj=="0.00"){
            swal("价税合计为0不能开具发票！");
            return;
        }
        var data="&xf="+xf+"&kpd="+kpd+"&fpzldm="+fpzldm+"&bz="+bz+"&is="+is+
            "&ddh="+ddh+"&gfmc="+gfmc+"&gfsh="+gfsh+"&gfdz="
            +gfdz+"&gfdh="+gfdh+"&gfyh="+gfyh+"&yhzh="+yhzh+"&yjdz="+yjdz+"&lxdh="+lxdh+"&tqm="+tqm+
            "&jshj="+jshj+"&hjje="+hjje+"&hjse="+hjse+"&sfbx="+sfbx+"&"+ps.join("&")+"&"+zf.join("&");
        // alert(data);
        swal({
            title:"提示",
            text: "您确定要申请开票吗？",
            type: "warning",
            showCancelButton: true,
            closeOnConfirm: false,
            confirmButtonText: "确 定",
            confirmButtonColor: "#ec6c62"
        }, function() {
            $('.confirm').attr('disabled',"disabled");
            $.ajax({
                url: "sgkj1/save", "type": "POST", context: document.body, data: data, success: function (data) {
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
                        isCha=false;
                        $('#discount').removeAttr("disabled");
                        $('#cha').removeAttr("disabled");
                        jyspmx_table.clear().draw();
                        jyzfmx_table.clear().draw();
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
    });
    $("#disSave").click(function () {
        var arry=getAllRowDataArry();
        var num= $("#disNum").val();
        var disAmount= $("#disAmount").val();
        if(disAmount==""|| disAmount==null){
            swal("请重新输入折扣金额");
            $('#disAmount').focus();
            return;
        }
        var zkl= FormatFloat($("#zkl").val());
        if(zkl>1){
            swal("折扣率格式有误，请重新输入折扣金额");
            $('#disAmount').focus();
            return;
        }
        jyspmx_table.clear().draw();
        var zspje=FormatFloat($("#amount").val(),"#.00");
        var zzkje=FormatFloat($("#disAmount").val(),"#.00");
        index = arry.length+parseInt(num);
        for(var i=0;i<arry.length;i++){
            var cishu1=2*i+1;
            var cishu2=2*i+2;
            var cishu3=i+parseInt(num)+1;
            var zkje='-'+FormatFloat((arry[i].spje)/zspje*zzkje,"#.00");
            var temp = (100 + arry[i].taxrate * 100) / 100;//税率计算方式
            var zkse =FormatFloat((zkje / temp) * arry[i].taxrate,"#.00");
            if(i<num){
                jyspmx_table.row.add([
                    "<span class='index' id='xhf'>" + cishu1 + "</span>",
                    '<input type="text" id="spmc"  name="spmc" value='+arry[i].spmc+' readonly><input type="hidden" id="spbm" name="spbm" value='+arry[i].spbm+'>' +
                    '<input type="hidden" id="yhzcbs"  name="yhzcbs" value='+arry[i].yhzcbs+'><input type="hidden" id="yhzcmc" name="yhzcmc" value='+arry[i].yhzcmc+'><input type="hidden" id="lslbz" name="lslbz" value='+arry[i].lslbz+' ><input type="hidden" id="fphxz" name="fphxz"  value="2" >',
                    '<input type="text" id="ggxh" name="ggxh" readonly value='+arry[i].ggxh+' >',
                    '<input type="text" id="spdw" name="spdw" readonly value='+arry[i].spdw+'>',
                    '<input type="text" id="spsl" name="spsl"  readonly oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right" value='+arry[i].spsl+' >',
                    '<input type="text" id="spdj" name="spdj" readonly oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right" value='+arry[i].spdj+'>',
                    '<input type="text" id="spje" name="spje" readonly oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right" value='+arry[i].spje+'>',
                    '<input type="text" id="taxrate" name="taxrate"  class="selected" readonly style="text-align:right" value='+arry[i].taxrate+'>',
                    '<input type="text" id="spse" name="spse" style="text-align:right" class="selected" readonly value='+arry[i].spse+' >'
                ]).draw();
                jyspmx_table.row.add([
                    "<span class='index' style='color: red;' id='xhf'>" + cishu2 + "</span>",
                    '<input style="color: red;" type="text" id="spmc"  name="spmc" value='+arry[i].spmc+' readonly><input type="hidden" id="spbm" name="spbm" value='+arry[i].spbm+'>' +
                    '<input type="hidden" id="yhzcbs"  name="yhzcbs" value='+arry[i].yhzcbs+'><input type="hidden" id="yhzcmc" name="yhzcmc" value='+arry[i].yhzcmc+'><input type="hidden" id="lslbz" name="lslbz" value='+arry[i].lslbz+'><input type="hidden" id="fphxz" name="fphxz"  value="1" >',
                    '<input style="color: red;" type="text" id="ggxh" name="ggxh" readonly value='+arry[i].ggxh+'  >',
                    '<input style="color: red;" type="text" id="spdw" name="spdw" readonly  value='+arry[i].spdw+' >',
                    '<input style="color: red;text-align:right" type="text" id="spsl" name="spsl" readonly>',
                    '<input style="color: red;text-align:right" type="text" id="spdj" name="spdj" readonly>',
                    '<input style="color: red;text-align:right" type="text" id="spje" name="spje" readonly value='+zkje+' >',
                    '<input style="color: red;text-align:right" type="text" id="taxrate" name="taxrate" class="selected" readonly  value='+arry[i].taxrate+'>',
                    '<input style="color: red;text-align:right" type="text" id="spse" name="spse"  class="selected" readonly value='+zkse+'>'
                ]).draw();
            }else{
                jyspmx_table.row.add([
                    "<span class='index' id='xhf'>" + cishu3 + "</span>",
                    '<input type="text" id="spmc"  name="spmc" value='+arry[i].spmc+' readonly><input type="hidden" id="spbm" name="spbm" value='+arry[i].spbm+'>' +
                    '<input type="hidden" id="yhzcbs"  name="yhzcbs" value='+arry[i].yhzcbs+'><input type="hidden" id="yhzcmc" name="yhzcmc" value='+arry[i].yhzcmc+'><input type="hidden" id="lslbz" name="lslbz" value='+arry[i].lslbz+' ><input type="hidden" id="fphxz" name="fphxz"  value="0" >',
                    '<input type="text" id="ggxh" name="ggxh" value='+arry[i].ggxh+'>',
                    '<input type="text" id="spdw" name="spdw" value='+arry[i].spdw+'>',
                    '<input type="text" id="spsl" name="spsl"   oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right" value='+arry[i].spsl+' >',
                    '<input type="text" id="spdj" name="spdj"  oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right" value='+arry[i].spdj+'>',
                    '<input type="text" id="spje" name="spje"  oninput="this.value=this.value.replace(/[^0-9.]/g,'+"''"+')" style="text-align:right" value='+arry[i].spje+'>',
                    '<input type="text" id="taxrate" name="taxrate"  class="selected" readonly style="text-align:right" value='+arry[i].taxrate+'>',
                    '<input type="text" id="spse" name="spse" style="text-align:right" class="selected" readonly value='+arry[i].spse+' >'
                ]).draw();
            }
        }
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额
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
        $modal.modal("close");
    });
    $("#disNum").on('change',function () {
        var r ;
        var je=0;
        var num=parseInt( $("#disNum").val());
        var arry=getAllRowDataArry();
        var ruilv=[];
        $("#jyspmx_table").find("tr").each(function(j,row){
            r=j;
        });
        if(num > r){
            num=r;
        }
        for (var x in arry) {
            ruilv.push(arry[x].taxrate)
        }
        if(new Set(ruilv).size === 1){
            for(var i=0;i<num;i++){
                je+=parseInt(arry[i].spje);
            }
        }else{
            je=arry[0].spje;
            num=1;
        }
        $("#disNum").val(num);
        $("#amount").val(je)
    });

    $("#disAmount").on('change',function (){
        var spje=FormatFloat($("#amount").val(),"#.00");
        var zkje=FormatFloat($(this).val(),"#.00");
        var zkl=zkje/spje;
        $("#xz_gfdz").val((zkl*100).toFixed(3)+'%');
        $('#zkl').val(zkl);
    });
    $("#close1").on('click', function () {
        $modal.modal('close');
    });
    $("#close2").on('click', function () {
        $moda2.modal('close');
    });

    function getAllRowDataArry(){
        var arry=[];
        var spmc,spbm,yhzcbs,yhzcmc,lslbz,fphxz,ggxh,spdw,spsl,spdj,spje,taxrate,spse;
        $("#jyspmx_table").find("tr").each(function(i,cell){
            spmc=$(cell).find('input[name="spmc"]').val();
            spbm= $(cell).find('input[name="spbm"]').val();
            yhzcbs= $(cell).find('input[name="yhzcbs"]').val();
            yhzcmc= $(cell).find('input[name="yhzcmc"]').val();
            lslbz= $(cell).find('input[name="lslbz"]').val();
            fphxz= $(cell).find('input[name="fphxz"]').val();
            ggxh=$(cell).find('input[name="ggxh"]').val();
            spdw=$(cell).find('input[name="spdw"]').val();
            spsl=$(cell).find('input[name="spsl"]').val();
            spdj=$(cell).find('input[name="spdj"]').val();
            spje= $(cell).find('input[name="spje"]').val();
            taxrate=$(cell).find('input[name="taxrate"]').val();
            spse=$(cell).find('input[name="spse"]').val();
            arry.push({'spmc':spmc,'spbm':spbm,'yhzcbs':yhzcbs,'yhzcmc':yhzcmc,'lslbz':lslbz,'fphxz':fphxz,'ggxh':ggxh,'spdw':spdw,'spsl':spsl,'spdj':spdj,'spje':spje,'taxrate':taxrate,'spse':spse});
        });
        arry.splice(0,1);
        return arry;
    };

    //差额
    $("#cha").click(function () {
        isCha = true;
        $('#discount').attr("disabled",true);
        var tr = $("#jyspmx_table").find("tr");
         var ct = true;
        if(tr.length>2){
            swal("开具差额，商品最大行数为1，请重试！");
            ct=false;
            return ;
        }else{
            tr.each(function(i,row){
                if(i!=0){
                    var text=$(row).children("td").eq(0).text();
                    if(text=="表中数据为空"){
                        swal("请先添加商品");
                    }else {
                        if($(row).children("td").eq(1).find('input[name="spmc"]').val()/1==""){
                            swal("第"+i+"行货物或应税劳务、服务名称不能为空,不能添加折扣");
                            ct=false;
                            return ;
                        }
                        if(ct){
                            //$('#hsbz').val("0");
                            $('#noo').css("display","");
                            $('#yss').css("display","none");
                            $('#noo1').css("display","");
                            $('#yss1').css("display","none");
                            $(row).children("td").eq(5).find('input[name="spdj"]').attr('disabled',true);
                            $moda2.modal({"width": 600, "height": 280});
                        }
                    }
                }
            });
        }
    });
    //差额处理
    $('#chaSave').click(function(){
        var hsxse = FormatFloat($('#hsxse').val(),"#.00");
        var kce = FormatFloat($('#kce').val(),"#.00");
        if(Number(kce)>Number(hsxse)){
            console.log("扣除额大于金额");
            kce = hsxse;
        }
        var sjje=hsxse-kce;
        var chaspje=0;
        if(hsxse==null || hsxse ==""){
            swal("含税销售额必须输入有效金额数字");
            return;
        }
        if(kce==null|| kce==""){
            swal("扣除额必须输入有效金额数字");
            return;
        }
        var tr = $("#jyspmx_table").find("tr");
        tr.each(function(i,row){
        var spsl =  $(row).children("td").eq(4).find('input[name="spsl"]');
        var spdj =  $(row).children("td").eq(5).find('input[name="spdj"]');
        var spje =  $(row).children("td").eq(6).find('input[name="spje"]');
        var taxrate=$(row).children("td").eq(7).find('input[name="taxrate"]');//商品税率
        var spse=$(row).children("td").eq(8).find('input[name="spse"]');//商品税率
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额
        if(taxrate.val()!=null){
            var temp = (100 + taxrate.val() * 100) / 100;//税率计算方式
            var jj = sjje;
            spse.val(FormatFloat((jj / temp) * taxrate.val(),"#.00"));
            var jshjstr=0;
            var hjjestr=0;
            var hjsestr=0;
            var  chaspse =FormatFloat((jj / temp) * taxrate.val(),"#.00");
            if (spdj.val()!= "") {
                spsl.val(FormatFloat(sjje / spdj.val(), "#.00####"));
            }else if(spdj.val()==""&&spsl.val()!=""){
                spdj.val(FormatFloat(sjje / spsl.val(), "#.00####"));
            }
            spje.val(hsxse-chaspse);
            $("#jyspmx_table").find("tr").each(function(i,row){
                if(i!=0){
                    // jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                    jshjstr+=hsxse;
                    // hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1-$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
                    hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
                    hjsestr+=$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
                    $(row).children("td").eq(6).find('input[name="spje"]').attr('disabled',true);
                    $(row).children("td").eq(5).find('input[name="spdj"]').attr('disabled',true);
                }
            });
            jshj.val(FormatFloat(jshjstr,"#.00"));//价税合计
            hjje.val(FormatFloat(hjjestr,"#.00"));//不含税金额合计
            hjse.val(FormatFloat(hjsestr,"#.00"));//合计税额
            $("#hsxse").val("");
            $("#kce").val("");
        }

        });
        $moda2.modal('close');
    })
});

function fomart(spje) {
   var  spjef = FormatFloat(spje,"#.00");
   return spjef;
}
