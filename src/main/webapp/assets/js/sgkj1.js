$(function() {
    "use strict";
    var index = 1;
    var indexzf =0;
    var mxarr = [];
    var zfarr = [];
    var f=true;
    var isCha = false;
    var $modal = $("#discountInfo");
    var $moda2 = $("#difInfo");
    //good*****************************************8
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
            url: 'sgkj/getItems',
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
    //good*****************************************8
    $("#jyspmx_table").on("click",".tableCheckBox",function () {
        var _this=$(this);
        _this.toggleClass("tableCheckBoxSelect");
        // if(_this.hasClass("tableCheckBoxSelect")){
        //     _this.closest("tr").siblings("tr").find(".tableCheckBox").removeClass("tableCheckBoxSelect");
        // }
    });
    $('#button1').on('click', function(e) {
        e.preventDefault();
        detail_table.ajax.reload();
    });
    //good*****************************************8
    $("#add").click(function () {
        var addtr=true;
        var tr=$("#jyspmx_table").find("tr");
        var index1=tr.length+1;
        if($("#jyspmx_table").find("tr.ddh-seacher ").length>0){
            return
        }

     if(index1>1){
         var fwmc,je;
         tr.each(function () {
             var _this=$(this);
             fwmc=_this.find(".spmc").val();
             je=_this.find(".spje").val();
             if(fwmc=='' || je=='' || !(Number(je)!=0)){
                 swal("请输入货物或应税劳务、服务名称或金额不能为0");
                 addtr=false;
             }

         })
     }
        var arry=getAllRowDataArry();
        f=false;
        if(arry.length>1){
            index = arry.length + 1;
        }else if(arry.length==1){
            for(var i=0;i<arry.length;i++){
                if(arry[i].spmc==null){
                    index=1;
                }else {
                    if($("#jyspmx_table").find("tr.select-Zk").length>0){
                        swal("开具差额，商品最大行数为1，请重试！");
                        return ;
                    }
                    index=arry.length+1;
                }
            }
        }
        var trHtml='<tr><td style="width: 5%"><span class="tableCheckBox" id="xhf">'+index1+'</span></td>'
            + '<td style="width: 24%"><input type="text" class="spmc spmclick"  name="spmc" readonly><input type="hidden" class="spbm" name="spbm">'
            + '<input type="hidden" class="yhzcbs" name="yhzcbs"><input type="hidden" class="yhzcmc" name="yhzcmc"><input type="hidden" class="lslbz" name="lslbz"><input type="hidden" class="fphxz" name="fphxz"  value="0" ><input type="hidden" class="kche" name="kche"  value="" >'
            +'</td><td style="width: 18%"><input type="text" class="ggxh" name="ggxh"></td>'
            +'<td style="width: 8%"><input type="text" class="spdw" name="spdw"></td>'
            +'<td style="width: 8%"><input type="text" class="spsl" name="spsl" oninput="this.value=this.value.replace(/[^0-9.]/g,\'+"\'\'"+\')" style="text-align:right"></td>'
            +'<td style="width: 10%"><input type="text" class="spdj" name="spdj" oninput="this.value=this.value.replace(/[^0-9.]/g,\'+"\'\'"+\')" style="text-align:right"></td>'
            +'<td style="width: 10%"><input type="text" class="spje" name="spje" oninput="this.value=this.value.replace(/[^0-9.]/g,\'+"\'\'"+\')" onchange="this.value=fomart(this.value)" style="text-align:right"></td>'
            +'<td style="width: 8%"><input type="text" class="taxrate selected" name="taxrate"  readonly style="text-align:right"></td>'
            +'<td style="width: 9%"><input type="text" class="spse selected" name="spse" style="text-align:right" readonly></td></tr>';
           if(addtr){
               $("#jyspmx_table>tbody").append($(trHtml))
           }


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

    //折扣处理good*****************
    $("#discount").click(function () {

        $('#disAmount').val("");
        $('#xz_gfdz').val("");
        $('#zkl').val("");
        if($("#jyspmx_table").find("tr.ddh-seacher ").length>0){
            return
        }
        var tr=$("#jyspmx_table").find("tr");
        var trlenght=$("#jyspmx_table").find(".tableCheckBoxSelect").length;
        if(trlenght>1 || trlenght<1){
            swal("请选择一行进行折扣");
            return;
        }
        var selectRow=$("#jyspmx_table").find(".tableCheckBoxSelect").closest("tr").index();
        var selectTypr=$("#jyspmx_table").find("tr").eq(selectRow).find('input[name="fphxz"]').val();
        if(selectTypr=='1'){
            swal("该行已被折扣，请选择未被折扣的行");
            return;
        }
        if(selectTypr=='2'){
            swal("该行是折扣行，请选择未被折扣的行");
            return;
        }
        var firstje;
        if(tr.length>0){
            if(tr.eq(selectRow).find('input[name="spje"]').val()/1==0){
                swal("提示:\r\n"+"第"+Number(selectRow+1)+"行商品金额为0,不能添加折扣");
                return ;
                }
            if(tr.eq(selectRow).find('input[name="spmc"]').val()/1==""){
                swal("提示:\r\n"+"第"+Number(selectRow+1)+"行货物或应税劳务、服务名称不能为空,不能添加折扣");
                return ;
                }
            firstje=$("#jyspmx_table").find("tr").eq(selectRow).find('input[name="spje"]').val();
            $('#amount').val(firstje);
            $modal.modal({"width": 600, "height": 280});
        }
    });

    //删除明细good**************************************
    $("#del").click(function(){
        var nu = $("#jyspmx_table").find('.tableCheckBoxSelect');
        var tr=$("#jyspmx_table").find('tr');
        var suredel=true;
        var zkdel=true;
        if($("#jyspmx_table").find("tr.ddh-seacher ").length>0){
            return
        }
        if(nu.length<1){
            swal("请选中你要删除的行");
            return
        }
        if(tr.eq(0).hasClass("select-Zk")){
            $('#discount').attr("disabled",false);
            isCha=false;
        }
        var value1arry=[];
        nu.each(function () {
            var _this=$(this);
            var _index=_this.closest("tr").index();
            var value1=_this.closest("tr").find('input[name="fphxz"]').val();
            if(value1=='2'){
                value1arry.push(_index)
            }
        });
        if(value1arry.length>0){
            var x;
            for(x in value1arry){
                if(!tr.eq(value1arry[x]).next('tr').find('.tableCheckBox').hasClass("tableCheckBoxSelect")){
                    suredel=false;
                    break;
                }
            }
        }else{
            suredel=false;
            zkdel=false;
            nu.each(function () {
                var _this=$(this);
                 _this.closest("tr").remove();
            });
        }

        if(suredel){
            nu.each(function () {
                var _this=$(this);
                _this.closest("tr").remove();
            });
        }else{
            if(zkdel){
                swal("请先删除折扣行");
            }
        }

        var alltr=$("#jyspmx_table").find("tr");
        var delValue1=[];
        var delValue2=[];
        alltr.each(function () {
            var _this=$(this);
            var _index=_this.index();
            var value1=_this.find('input[name="fphxz"]').val();
            if(value1=='2'){
                delValue1.push(_index)
            }
            if(value1=='1'){
                delValue2.push(_index)
            }
        });
        if(delValue2.length==0){
            $('#cha').attr("disabled",false);
        }
        if(delValue1.length>0){
            var i;
            for(i in delValue1){
                if(alltr.eq(delValue1[i]).next('tr').find('input[name="fphxz"]').val()!='1'){
                    alltr.eq(delValue1[i]).find('input[name="fphxz"]').val('0');
                    alltr.eq(delValue1[i]).find("input.spsl").attr("readonly",false);
                    alltr.eq(delValue1[i]).find("input.spdj").attr("readonly",false);
                    alltr.eq(delValue1[i]).find("input.spje").attr("readonly",false);
                }
            }
        }
        if($("#jyspmx_table").find("tr").length<1){
            isCha=false;
        }
        resetTableListNumber();
        chnageFromValue();
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
                        if(jyxxsq[i].sqlsh!=null && jyxxsq[i].sqlsh!=""){
                            $("#isSave").val(jyxxsq[i].sqlsh);
                        }
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

                                    $("#fpzldm").val(jyxxsq[0].fpzldm);
                                    $("#ddh").attr("readonly",true);
                                    $("#xf").attr("disabled",true);
                                    $("#kpd").attr("disabled",true);
                                    $("#fpzldm").attr("disabled",true);


                                }
                            });
                        }

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
                    var jymxsq=data.jymxsq;
                    var tableHtml='';
                    for(var j=0;j<jymxsq.length;j++){
                        var _index=j+1;
                        var redClass='';
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
                        if(spse==''){
                            spse=FormatFloat(spje/(1+ spsl)*spsl,"#.00");
                        }

                        if(spje!=null && spje!=""){
                            spje=FormatFloat(spje,"#.00");
                            if(Number(spje)<0){
                                redClass="ddh-searchRed"
                            }else{
                                redClass=""
                            }
                        }
                        tableHtml+='<tr class="ddh-seacher '+redClass+'"><td style="width: 5%"><span class="tableCheckBox" id="xhf">'+_index+'</span></td>'
                            + '<td style="width: 24%"><input type="text" class="spmc"  name="spmc" value="'+jymxsq[j].spmc+'" readonly><input type="hidden" class="spbm" name="spbm" value="'+jymxsq[j].spdm+'">'
                            + '<input type="hidden" class="yhzcbs" name="yhzcbs" value="'+yhzcbs+'"><input type="hidden" class="yhzcmc" name="yhzcmc" value="'+yhzcmc+'"><input type="hidden" class="lslbz" name="lslbz" value="'+lslbz+'"><input type="hidden" class="fphxz" name="fphxz"  value="'+fphxz+'" >'
                            +'</td><td style="width: 18%"><input type="text" class="ggxh" name="ggxh" value="'+spggxh+'" readonly></td>'
                            +'<td style="width: 8%"><input type="text" class="spdw" name="spdw" value="'+spdw+'" readonly></td>'
                            +'<td style="width: 8%"><input type="text" class="spsl" name="spsl" style="text-align:right" value="'+sps+'" readonly></td>'
                            +'<td style="width: 10%"><input type="text" class="spdj" name="spdj" style="text-align:right" value="'+spdj+'" readonly></td>'
                            +'<td style="width: 10%"><input type="text" class="spje" name="spje"  style="text-align:right" value="'+spje+'" readonly></td>'
                            +'<td style="width: 8%"><input type="text" class="taxrate selected" name="taxrate"  readonly style="text-align:right" value="'+spsl+'"></td>'
                            +'<td style="width: 9%"><input type="text" class="spse selected" name="spse" style="text-align:right" readonly value="'+spse+'"></td></tr>';
                    }
                    $("#jyspmx_table>tbody").html($(tableHtml));
                    //塞消方信息
                    var dizhi=jyxxsq[0].xfdz +""+jyxxsq[0].xfdh;
                    var zh=jyxxsq[0].xfyh +""+jyxxsq[0].xfyhzh;
                    $("#xfmc").val(jyxxsq[0].xfmc);
                    $("#xfsh").val(jyxxsq[0].xfsh);
                    $("#xfdz").val(dizhi );
                    $("#xhzh").val(zh);
                    $("#skr").val(jyxxsq[0].skr);
                    $("#fh").val(jyxxsq[0].fhr);
                    $("#kpr").val(jyxxsq[0].kpr);
                    $("#bz").val(jyxxsq[0].bz);

                    //支付内容
                    var zhifuHtml='';
                    if(data.jyzfmx !=null && data.jyzfmx.length>0){
                        zhifuHtml='<li>支付明细</li>' +
                            '<li><span>支付宝:</span><span>￥20</span></li>'+
                            '<li><span>微信:</span><span>￥20</span></li>'+
                            '<li><span>银行卡:</span><span>￥20</span></li>'+
                            '<li><span>现金:</span><span>￥20</span></li>';
                        $("#zhifuList").html($(zhifuHtml));
                    }
                    chnageFromValue()
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
    var value;
//good*****************************************8
    $("#jyspmx_table").on('click', 'input.spmclick', function () {
        var  rowstr=$(this).closest("tr").index()+1;//获取当前行数
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
            value=$(this).closest("tr").index();
            $("#spxx").modal({"width": 720, "height": 500});
            detail_table.ajax.reload();
        }
    });
//good*****************************************8
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
                $(cell).find('input[name="spse"]').val("0.00");
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
        if($("#jyspmx_table").find("tr.select-Zk").length>0){
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
    //good*********************************************************************
    $("#jyspmx_table").on('change', 'input.spsl', function () {
        var  rowstr=$(this).closest("tr").index();//获取当前行数
        var spsl=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spsl"]');//商品数量
        if(!isRealNum(spsl.val())){
            spsl.val("0.00")
        }
        var spdj=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spdj"]');//商品单价
        var spje=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spje"]');//商品金额
        var taxrate=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="taxrate"]');//商品税率
        var spse=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spse"]');//商品税额

        if($("#jyspmx_table").find("tr.select-Zk").length>0){
                spdj.val(Number(FormatFloat((spje.val() / spsl.val()),"#.000"))!= Number(FormatFloat((spje.val() / spsl.val()),"#.00"))? FormatFloat((spje.val() / spsl.val()),"#.000000"):FormatFloat((spje.val() / spsl.val()),"#.00"))
        }else{
            if(spdj.val()=="" && spje.val() !==""){
                spdj.val(Number(FormatFloat((spje.val() / spsl.val()),"#.000"))!= Number(FormatFloat((spje.val() / spsl.val()),"#.00"))? FormatFloat((spje.val() / spsl.val()),"#.000000"):FormatFloat((spje.val() / spsl.val()),"#.00"))
            }
            if(spsl.val() == ""){
                spje.val('0.00');
            }
            if(spdj.val()!="" && spsl.val() !=""){
                spje.val(FormatFloat((spdj.val()* spsl.val()),"#.00"));
                // spse.val(FormatFloat((spje.val()/(1+ taxrate.val())*taxrate.val()),"#.00"))
            }
        }



        spse.val(FormatFloat((spje.val()/(1+ taxrate.val())*taxrate.val()),"#.00"))
        chnageFromValue()
    });
    $("#jyspmx_table").on('change', 'input.spdj', function () {
        var  rowstr=$(this).closest("tr").index();//获取当前行数
        var spdj=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spdj"]');//商品单价
        if(!isRealNum(spdj.val())){
            spdj.val("0.00")
        }

        var spsl=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spsl"]');//商品数量
        var spje=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spje"]');//商品金额
        var taxrate=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="taxrate"]');//商品税率
        var spse=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spse"]');//商品税额
        if(spdj.val()!="" && spje.val() !="" && spdj.val()!="0"){
            spsl.val(Number(FormatFloat((spje.val() / spdj.val()),"#.000")) != Number(FormatFloat((spje.val() / spdj.val()),"#.00"))? FormatFloat((spje.val() / spdj.val()),"#.000000"):FormatFloat((spje.val() / spdj.val()),"#.00"))
        }
        if(spdj.val()!="" && spsl.val() !="" && spdj.val()!="0"){
            spje.val(FormatFloat((spdj.val()* spsl.val()),"#.00"));
            // spse.val(FormatFloat((spje.val()/(1+ taxrate.val())*taxrate.val()),"#.00"))
        }
        spse.val(FormatFloat((spje.val()/(1+ taxrate.val())*taxrate.val()),"#.00"))
        chnageFromValue();

    });
    $("#jyspmx_table").on('change', 'input.spje', function () {

        if($("#jyspmx_table").find("tr.select-Zk").length>0){
            $moda2.modal({"width": 600, "height": 280});
            return;
        }
        var  rowstr=$(this).closest("tr").index();//获取当前行数
        var spje=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spje"]');//商品金额
        if(!isRealNum(spje.val())){
            spje.val("0.00")
        }
        var spsl=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spsl"]');//商品数量
        var spdj=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spdj"]');//商品单价

        var taxrate=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="taxrate"]');//商品税率
        var spse=$("#jyspmx_table").find("tr").eq(rowstr).find('input[name="spse"]');//商品税额

        if(spdj.val()!=""){
            spsl.val(Number(FormatFloat((spje.val() / spdj.val()),"#.000")) != Number(FormatFloat((spje.val() / spdj.val()),"#.00"))? FormatFloat((spje.val() / spdj.val()),"#.000000"):FormatFloat((spje.val() / spdj.val()),"#.00"));
            spse.val(FormatFloat((spje.val()/(1+ taxrate.val())*taxrate.val()),"#.00"))
        }
        spse.val(FormatFloat((spje.val()/(1+ taxrate.val())*taxrate.val()),"#.00"))
        chnageFromValue();
    });

    $("#kj").click(function(){
        var jyspje=$("#jyspmx_table").find("tr:last").find("input.spje").val();
        if(!(Number(jyspje)!=0)){
            swal("商品金额不能为0！");
            return;
        }
        if(jyspje==''){
            swal("商品金额不能为空！");
            return;
        }
        var xf=$("#xf").val();//消防
        var kpd=$("#kpd").val();//开票点名称
        var fpzldm=$("#fpzldm").val();//发票种类
        var ddh=$("#ddh").val();//订单号
        var yjdz=$("#yjdz").val();//购方邮箱
        var lxdh=$("#lxdh").val();//购方手机号

        var gfmc=$("#gfmc").val();//购方名称
        var gfsh=$("#gfsh").val();//购方税号
        var gfdz=$("#gfdz").val();//购方地址

        var gfdh=$("#gfdh").val();//购方电话
        var gfyh=$("#gfyh").val();//购方银行
        var yhzh=$("#yhzh").val();//购方银行账号

        var zfmc=$("#zfmc").val();//支付名称
        var zfje=$("#zfje").val();//支付名称
        var isSave=$("#isSave").val();
        var kce =$("#jyspmx_table").find('input[name="kche"]').val();//开出额

        var regsj = /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/;
        var regyx = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
        if(!regsj.test(lxdh)){
            swal("请输入正确的手机号");
            return;
        }
        if(!regyx.test(yjdz)){
            swal("请输入正确的邮箱地址！");
            return;
        }
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
        if(null == kpd || kpd=="" || kpd=="-1"){
            $("#kpd").focus();
            swal("开票点不能为空！");
            return;
        }
        if(fpzldm==null || fpzldm=="" || fpzldm =="-1"){
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
        // $("#jyzfmx_table").find("tr").each(function(j,row){
        //     $(row).children("td").each(function(i,cell){
        //         if(i==1){
        //             var  zfmc=$(cell).find('input[name="zfmc"]').val();
        //             var zffsDm= $(cell).find('input[name="zffsDm"]').val();
        //             zf.push("zffsDm=" + zffsDm);
        //             zf.push("zfmc=" + zfmc);
        //         }else if(i==2){
        //             var zfje= $(cell).find('input[name="zfje"]').val();
        //             zf.push("zfje=" + zfje);
        //         }
        //     });
        // });
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
        var tqm=null;
        var ischeck="0";
        var bz=$("#bz").val();
        if($("#jyspmx_table").find("tr.ddh-seacher ").length>0){
            tqm=$("#ddh").val();
            ischeck="1";
        }
        if(ps.length==0){
            swal("请选择商品！");
            return;
        }
        if(bz.length>120){
            swal("备注过长，最多120个字符！");
            return;
        }
        var _index=$("#jyspmx_table").find("tr").length;
        ps.push("kce=" + kce);
        ps.push("mxcount=" + _index);
        zf.push("zfcount=" + indexzf);
        var is="";
        if($("#jyspmx_table").find("tr.select-Zk").length>0){
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
            +gfdz+"&gfdh="+gfdh+"&gfyh="+gfyh+"&yhzh="+yhzh+"&yjdz="+yjdz+"&lxdh="+lxdh+"&tqm="+tqm+"&ischeck="+ischeck+"&isSave="+isSave+
            "&jshj="+jshj+"&hjje="+hjje+"&hjse="+hjse+"&sfbx="+sfbx+"&"+ps.join("&")+"&"+zf.join("&");
        console.log(data)
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
                        resetFromControl();
                    } else {
                        swal(data.msg);
                    }
                }
            });
        });
    });
    //添加重置功能good*******************************88
    $('#cz').on('click',function() {
        resetFromControl()
    });
    //good***************************************
    $("#disSave").click(function () {
        var zhkRow=$("#jyspmx_table").find(".tableCheckBoxSelect").closest("tr").index();
        var arry=getAllRowDataArry();
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
        var zspje=FormatFloat($("#amount").val(),"#.00");
         var zzkje=FormatFloat($("#disAmount").val(),"#.00");

        var zkje='-'+FormatFloat((arry[zhkRow].spje)/zspje*zzkje,"#.00");
        var temp = (100 + arry[zhkRow].taxrate * 100) / 100;//税率计算方式
        var zkse =FormatFloat((zkje / temp) * arry[zhkRow].taxrate,"#.00");
        $("#jyspmx_table").find("tr").eq(zhkRow).find(".fphxz").val('1');
        var zkhHtml='<tr><td style="width: 5%"><span class="tableCheckBox" style="color: red;"></span></td>'
            + '<td style="width: 24%"><input style="color: red;" type="text" class="spmc" value='+arry[zhkRow].spmc+'  name="spmc" readonly><input type="hidden" class="spbm" name="spbm" value='+arry[zhkRow].spbm+'>'
            + '<input type="hidden" class="yhzcbs" name="yhzcbs" value='+arry[zhkRow].yhzcbs+'><input type="hidden" class="yhzcmc" name="yhzcmc" value='+arry[zhkRow].yhzcmc+'><input type="hidden" class="lslbz" name="lslbz" value='+arry[zhkRow].lslbz+'><input type="hidden" class="fphxz" name="fphxz"  value="1" >'
            +'</td><td style="width: 18%"><input style="color: red;" type="text" class="ggxh" name="ggxh" readonly value='+arry[zhkRow].ggxh+'></td>'
            +'<td style="width: 8%"><input style="color: red;" type="text" class="spdw" name="spdw" readonly value='+arry[zhkRow].spdw+'></td>'
            +'<td style="width: 8%"><input style="color: red;text-align:right" type="text" class="spsl" name="spsl" readonly></td>'
            +'<td style="width: 10%"><input style="color: red;text-align:right" type="text" class="spdj" name="spdj" readonly></td>'
            +'<td style="width: 10%"><input style="color: red;text-align:right" type="text" class="spje" name="spje" readonly value='+zkje+'></td>'
            +'<td style="width: 8%"><input type="text" class="taxrate selected" name="taxrate"  style="color: red;text-align:right" readonly  value='+arry[zhkRow].taxrate+'></td>'
            +'<td style="width: 9%"><input type="text" class="spse selected" name="spse" style="color: red;text-align:right" readonly value='+zkse+'></td></tr>';
        $("#jyspmx_table").find("tr").eq(zhkRow).find("input.fphxz").val('2');
        $("#jyspmx_table").find("tr").eq(zhkRow).find("input.spsl").attr("readonly",true);
        $("#jyspmx_table").find("tr").eq(zhkRow).find("input.spdj").attr("readonly",true);
        $("#jyspmx_table").find("tr").eq(zhkRow).find("input.spje").attr("readonly",true);
        $(zkhHtml).insertAfter($("#jyspmx_table").find("tr").eq(zhkRow));
        resetTableListNumber();
        chnageFromValue();


        $('#cha').attr("disabled",true);
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
//good******************************
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
        return arry;
    };
    //good******************************
    function resetTableListNumber(){
        var $tableTr=$("#jyspmx_table").find("tr");
        $tableTr.each(function () {
            var _this=$(this);
            var _index=_this.index();
            _this.find(".tableCheckBox").text(_index+1);
        })
    }

    //good******************************
    function convertCurrency(money) {
        //汉字的数字
        var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
        //基本单位
        var cnIntRadice = new Array('', '拾', '佰', '仟');
        //对应整数部分扩展单位
        var cnIntUnits = new Array('', '万', '亿', '兆');
        //对应小数部分单位
        var cnDecUnits = new Array('角', '分', '毫', '厘');
        //整数金额时后面跟的字符
        var cnInteger = '整';
        //整型完以后的单位
        var cnIntLast = '元';
        //最大处理的数字
        var maxNum = 999999999999999.9999;
        //金额整数部分
        var integerNum;
        //金额小数部分
        var decimalNum;
        //输出的中文金额字符串
        var chineseStr = '';
        //分离金额后用的数组，预定义
        var parts;
        if (money == '') { return ''; }
        money = parseFloat(money);
        if (money >= maxNum) {
            //超出最大处理数字
            return '';
        }
        if (money == 0) {
            chineseStr = cnNums[0] + cnIntLast + cnInteger;
            return chineseStr;
        }
        //转换为字符串
        money = money.toString();
        if (money.indexOf('.') == -1) {
            integerNum = money;
            decimalNum = '';
        } else {
            parts = money.split('.');
            integerNum = parts[0];
            decimalNum = parts[1].substr(0, 4);
        }
        //获取整型部分转换
        if (parseInt(integerNum, 10) > 0) {
            var zeroCount = 0;
            var IntLen = integerNum.length;
            for (var i = 0; i < IntLen; i++) {
                var n = integerNum.substr(i, 1);
                var p = IntLen - i - 1;
                var q = p / 4;
                var m = p % 4;
                if (n == '0') {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        chineseStr += cnNums[0];
                    }
                    //归零
                    zeroCount = 0;
                    chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
                }
                if (m == 0 && zeroCount < 4) {
                    chineseStr += cnIntUnits[q];
                }
            }
            chineseStr += cnIntLast;
        }
        //小数部分
        if (decimalNum != '') {
            var decLen = decimalNum.length;
            for (var i = 0; i < decLen; i++) {
                var n = decimalNum.substr(i, 1);
                if (n != '0') {
                    chineseStr += cnNums[Number(n)] + cnDecUnits[i];
                }
            }
        }
        if (chineseStr == '') {
            chineseStr += cnNums[0] + cnIntLast + cnInteger;
        } else if (decimalNum == '') {
            chineseStr += cnInteger;
        }
        return chineseStr;
    }
//good***********************************
    function chnageFromValue(){
        var jshj=$("#jshj");//价税合计
        var hjje=$("#hjje");//合计金额
        var hjse=$("#hjse");//合计税额
        var jshjd=$("#jshjdx");
        var jshjstr=0;
        var hjjestr=0;
        var hjsestr=0;

        $("#jyspmx_table").find("tr").each(function(i,row){
            jshjstr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1;
            hjjestr+=$(row).children("td").eq(6).find('input[name="spje"]').val()/1-$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
            hjsestr+=$(row).children("td").eq(8).find('input[name="spse"]').val()/1;
        });
        jshj.val(FormatFloat(jshjstr,"#.00"));//价税合计
        hjje.val(FormatFloat(hjjestr,"#.00"));//不含税金额合计
        hjse.val(FormatFloat(hjsestr,"#.00"));//合计税额
        jshjd.html(convertCurrency(FormatFloat(jshjstr,"#.00")));
    }
    //重置good******************************************************8
    function resetFromControl() {
        $("#xf").attr("disabled",false);
        $("#kpd").attr("disabled",false);
        $("#fpzldm").attr("disabled",false);
        $("#ddh").attr("readonly",false);
        $("input:visible").val('');
        $("#isSave").val("-1");
        $("textarea").val('');
        $("#jyspmx_table>tbody").html('');
        var SelectArr = $("select");
        for (var i = 0; i < SelectArr.length; i++) {
            SelectArr[i].options[0].selected = true;
            $("#kpd").val('');
        }
    }
///////good**************************************
    function isRealNum(val){
        // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
        if(val === "" || val ==null){
            return false;
        }
        if(!isNaN(val)){
            return true;
        }else{
            return false;
        }
    }
    //差额
    $("#cha").click(function () {
        isCha = true;
        var tr = $("#jyspmx_table").find("tr");
         var ct = true;
        if($("#jyspmx_table").find("tr.ddh-seacher ").length>0){
            return
        }
        if(tr.length>1){
            swal("开具差额，商品最大行数为1，请重试！");
            ct=false;
            return ;
        }else{
            if(tr.length==0){
                swal("请先添加商品");
            }else{
                if(tr.find('input[name="spmc"]').val()/1==""){
                    swal("货物或应税劳务、服务名称不能为空,不能添加折扣");
                    ct=false;
                    return ;
                }
                if(ct){
                    $('#noo').css("display","");
                    $('#yss').css("display","none");
                    $('#noo1').css("display","");
                    $('#yss1').css("display","none");
                    tr.find('input[name="spdj"]').attr('disabled',true);
                    $moda2.modal({"width": 600, "height": 280});
                }
            }
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
        var spsl =  tr.find('input[name="spsl"]');
        var spdj =  tr.find('input[name="spdj"]');
        var spje =  tr.find('input[name="spje"]');
        var taxrate=tr.find('input[name="taxrate"]');//商品税率
        var spse=tr.find('input[name="spse"]');//商品税率
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
            jshjstr+=hsxse;
            hjjestr=tr.find('input[name="spje"]').val()/1;
            hjsestr=tr.find('input[name="spse"]').val()/1;
            tr.find('input[name="spje"]').attr('disabled',true);
            tr.find('input[name="spdj"]').attr('disabled',true);

            jshj.val(FormatFloat(jshjstr,"#.00"));//价税合计
            hjje.val(FormatFloat(hjjestr,"#.00"));//不含税金额合计
            hjse.val(FormatFloat(hjsestr,"#.00"));//合计税额
            $("#jyspmx_table").find('input[name="kche"]').val(kce)
            $("#hsxse").val("");
            $("#kce").val("");
        }
        tr.addClass("select-Zk");

        $('#discount').attr("disabled",true);
        $moda2.modal('close');
    })
});

function fomart(spje) {
   var  spjef = FormatFloat(spje,"#.00");
   return spjef;
}
