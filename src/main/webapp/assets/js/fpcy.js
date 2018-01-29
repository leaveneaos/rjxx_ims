/**
 * Created by Administrator on 2018-01-22.
 */
/**
 * feel
 */
$(function() {
    "use strict";
    var el = {
        $jsTable : $('.js-table'),
        $modalfpxx : $('#fpxx'),
        $jsSubmit : $('.js-submit'),
        $jsSubmit1 : $('.js-submit1'),
        $jsClose : $('.js-close'),
        $jsClose1 : $('.js-close1'),
        $jsAuto : $('.js-auto'),
        $jsOut : $('.js-out'),
        $jsForm0 : $('.js-form-0'), // form
        $s_dyzt : $('#s_dyzt'), // search 开票日期
        $jsSearch : $('.js-search'),
        $jsExport : $('.js-export'),
        $jsLoading : $('.js-modal-loading'),
        $jsPrint : $('.js-print'),
        $checkAll : $('#select_all')
    };
    //批量导入
    var $importModal = $("#bulk-import-div");
    $("#kp_dr").click(function () {
        $('#importExcelForm').resetForm();
        $importModal.modal({"width": 600, "height": 350});
    });



    $(this).removeData('amui.modal');
    var mxarr = [];
    var $modal = $("#my-alert-edit2");
    var $tab = $('#doc-tab-demo-1');
    //开票商品明细table

    $("#my-alert-edit2").on("open.modal.amui", function () {
        $("#mx_form").validator("destroy");
        $("#main_form").validator("destroy");
        jyspmx_edit_table.clear();
        jyspmx_edit_table.draw();
    });
    var jyspmx_edit_table = $('#jyspmx_edit_table').DataTable({
        "searching": false,
        "bPaginate": false,
        "bAutoWidth": false,
        "bSort": false,
        "scrollY": "100",
        "scrollCollapse": "true"
    });


    $('#kp_add').click(function () {
        mxarr = [];
        $('#lrmx_form').resetForm();
        $('#main_form2').resetForm();
        $modal.modal({"width": 820, "height": 600});
        jyspmx_edit_table.clear();
        jyspmx_edit_table.draw();
    });
    $("#lrclose").click(function () {
        $modal.modal("close");
    });
    var index = 1;
    $('#lrmain_tab').find('a.ai').on('opened.tabs.amui', function (e) {
        jyspmx_edit_table.draw();
    });
    $("#addRow").click(function () {
        var r = $("#lrmx_form").validator("isFormValid");
        if (r) {
            var spdm = $("#lrspdm_edit").val();
            var mc = $("#lrmc_edit").val();
            var ggxh = $("#lrggxh_edit").val();
            var dw = $("#lrdw_edit").val();
            var sl = $("#lrsl_edit").val();//数量

            var dj = $("#lrdj_edit").val();
            var je = $("#lrje_edit").val();
            var sltaxrate = $("#lrsltaxrate_edit").val();//税率
            var se = $("#lrse_edit").val();
            var jshj = $("#lrjshj_edit").val();
            index = mxarr.length + 1;
            jyspmx_edit_table.row.add([
                "<span class='index'>" + index + "</span>", spdm, mc, ggxh, dw, sl, dj, je, sltaxrate, se, jshj, "<a href='#'>删除</a>"
            ]).draw();
            mxarr.push(index);
        }
    });
    $('#jyspmx_edit_table tbody').on('click', 'a', function () {
        jyspmx_edit_table.row($(this).parents("tr")).remove().draw(false);
        mxarr.pop();
        $('#jyspmx_edit_table tbody').find("span.index").each(function (index, object) {
            $(object).html(index + 1);
        });
    });
    //发票查验
    $("#lrsave").click(function () {
        var r = $("#main_form1").validator("isFormValid");
        var bhsje = $("#sglr_je").val();
        var jym = $("#sglr_jym").val();
        var fpdm = $("#sglr_fpdm").val();
        var fphm = $("#sglr_fphm").val();
        var kprq = $("#sglr_kprq").val();
        if(kprq == ""){
            $("#sglr_kprq").focus();
            swal('开票日期不能为空!');
            return false;
        }
        if (r) {
           var frmData = $("#main_form1").serialize();
            $.ajax({
                url: "income/invoiceCheck", "type": "POST",  data: frmData, success: function (data) {
                    if (data.status) {
                        //type=01 用于报销，检验是否查验过，并提示是否继续查验
                        $.ajax({
                            type : "POST",
                            url : "income/invoiceQuery",
                            data : {"fpdm":fpdm,
                                    "fphm":fphm
                            },
                        }).done(function(data) {
                            $('.confirm').removeAttr('disabled');
                            swal({
                                title: "已成功删除",
                                timer: 1500,
                                type: "success",
                                showConfirmButton: false
                            });
                            kpspmx_table.ajax.reload();
                        })
                        $modal.modal("close");
                        t.ajax.reload();
                    } else {
                        swal(data.msg);
                    }
                }
            });
        } else {
            ///如果校验不通过
            swal('校验不通过!');
           // $("#lrmain_tab").tabs('open', 0);
        }
    });

    var t;
    var splsh=[];
    //var kpspmx_table3;
    var loaddata=false;
    var action = {
        tableEx : null, // cache dataTable
        config : {
            getUrl : 'income/getFpcyList'
        },
        dataTable : function() {
            var _this = this;
            t = el.$jsTable.DataTable({
                "processing" : true,
                "serverSide" : true,
                ordering : false,
                searching : false,
                "scrollX" : true,
                "ajax" : {
                    url : _this.config.getUrl,
                    type : 'POST',
                    data : function(d) {
                        if($("#bj").val()=='1'){
                            d.kprqq = $("#s_rqq").val(); // search 开票日期
                            //d.kprqz = $("#s_rqz").val(); // search 开票日期
                            d.xfsh = $('#s_xfsh').val();   // search 销方
                            d.gfmc = $('#s_fpdm').val();	// search 发票代码
                            d.ddh = $('#s_fphm').val();   // search 发票号码
                            d.fpzldm = $('#s_fplx').val();   // search
                        }else{
                            var csm =  $('#dxcsm').val();
                            if("fpdm"==csm){
                                d.fpdm = $('#dxcsz').val();
                            }else if("fphm"==csm){
                                d.fphm = $('#dxcsz').val()
                            }
                           // d.kprqq =$("#w_kprqq").val();
                           // d.kprqz = $("#w_kprqz").val(); // search 开票日期
                        }

                        d.loaddata=loaddata;
                        splsh.splice(0,splsh.length);
                    }
                },
                "columns": [
                    {
                        "orderable" : false,
                        "data" : null,
                        render : function(data, type, full, meta) {
                            return '<input type="checkbox" name= "dxk" value="'
                                + data.id + '" />';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {"data": "fpdm",},
                    {"data": "fphm"},
                    {"data": "xfmc"},
                    {"data": "bxry"},
                    {"data": "kprq"},
                    {"data": function(data){
                        if("01"==data.fpzldm){
                            return "增值税专用发票";
                        }else if("02"==data.fpzldm){
                            return "增值税普通发票";
                        }
                        else if("12"==data.fpzldm){
                            return "电子发票(增普)";
                        }else{
                            return "";
                        }
                    }},
                    {"data": "cycs"},
                    {"data":function(data){
                        var sjly = data.sjly;
                        switch (sjly) {
                            case '01':
                                sjly = '手工录入';
                                break;
                            case '02':
                                sjly = '扫码接入';
                                break;
                            case '03':
                                sjly = '平台导入';
                                break;
                            case '04':
                                sjly = '系统接入';
                                break;
                        }
                        return sjly;
                    }},
                    {"data": "fpzt"},
                ]
            });
            t.on('draw.dt', function(e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function(cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });

            t.on('click', 'a.modify', function () {
                var row = t.row($(this).parents('tr')).data();
                $.ajax({
                    url : 'kpdsh/xgkpd',
                    data : {
                        "sqlsh" : row.sqlsh,
                    },
                    success : function(data) {
                        if (null!=data&&""!=data) {
                            var jy= data.jyxx
                            $('#select_xfid').val(jy.xfsh);
                            $.ajax({
                                url:"kp/getSkpList",
                                async:false,
                                data:{
                                    "xfsh" : jy.xfsh
                                }, success:function(data) {
                                    if (data) {
                                        var option;
                                        $('#select_skpid').html("");
                                        for (var i = 0; i < data.skps.length; i++) {
                                            option+= "<option value='"+data.skps[i].id+"'>"+data.skps[i].kpdmc+"</option>";
                                        }
                                        $('#select_skpid').append(option);
                                    }
                                }});
                            $('#select_skpid').val(jy.skpid);
                            $('#ddh_edit').val(jy.ddh);
                            $('#ddh_fplx').val(jy.fpzldm);
                            $('#gfdh_edit').val(jy.gfdh);
                            $('#gfsh_edit').val(jy.gfsh);
                            $('#gfmc_edit').val(jy.gfmc);
                            $('#gfyh_edit').val(jy.gfyh);
                            $('#gfyhzh_edit').val(jy.gfyhzh);
                            $('#gflxr_edit').val(jy.gflxr);
                            $('#gfemail_edit').val(jy.gfemail);
                            $('#gfsjh_edit').val(jy.gfsjh);
                            $('#gfdz_edit').val(jy.gfdz);
                            $('#tqm').val(jy.tqm);
                            $('#bz').val(jy.bz);
                            $('#formid').val(jy.sqlsh);
                        } else {
                            swal(data.msg);
                        }
                    },
                    error : function() {
                        swal("出现错误,请稍后再试");
                    }
                });
                $('#my-alert-edit').modal({"width": 800, "height": 450});
            });
            /*t.on('click', 'a.kpdth', function () {
             var ddhstha=t.row($(this).parents('tr')).data().sqlsh;
             if (!confirm("您确认退回么？")) {
             return;
             }
             $.ajax({
             type : "POST",
             url : "kpdsh/th",
             data : {"ddhs":ddhstha},
             success : function(data) {
             $("#alertt").html(data.msg);
             $("#my-alert").modal('open');
             _this.tableEx.ajax.reload();
             }
             });

             });*/
            //删除
            $("#kpd_sc").click(function () {

                var chk_value="" ;
                $('input[name="dxk"]:checked').each(function(){
                    chk_value+=$(this).val()+",";
                });
                var ddhs = chk_value.substring(0, chk_value.length-1);
                if(chk_value.length==0){

                    swal("请至少选择一条数据");
                }else{
                    swal({
                        title: "您确认删除？",
                        type: "warning",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        confirmButtonText: "确 定",
                        confirmButtonColor: "#ec6c62"
                    }, function() {
                        $('.confirm').attr('disabled',"disabled");
                        $.ajax({
                            type : "POST",
                            url : "kpdsh/sc",
                            data : {"ddhs":ddhs},
                        }).done(function(data) {
                            $('.confirm').removeAttr('disabled');
                            swal(data.msg);
                            _this.tableEx.ajax.reload();
                        })
                    });

                }
            });

            $('#check_all').change(function () {
                if ($('#check_all').prop('checked')) {
                    splsh.splice(0,splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                        var row =t.row(i).data();
                        splsh.push(row.sqlsh);
                    });
                } else {
                    splsh.splice(0,splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);

                    });
                }
                $("#kplsh").val(splsh.join(","));
                kpspmx_table.ajax.reload();
            });

            //选中列查询明细
            $('#jyls_table tbody').on('click', 'tr', function () {
                var data = t.row($(this)).data();
                if ($('#check_all').prop('checked')){
                    splsh.splice(0,splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked',false);
                    splsh.splice($.inArray(data.id, splsh), 1);
                } else {
                    $(this).find('td:eq(0) input').prop('checked',true)
                    $(this).addClass('selected');
                    splsh.push(data.id);
                }
                $('#check_all').prop('checked',false);
                alert(splsh);
                //$("#kplsh").val(splsh.join(","));
                //kpspmx_table.ajax.reload();
            });
            return t;
        },

        /**
         * search action
         */
        search_ac : function() {
            var _this = this;
            $("#kp_search").on('click', function(e) {
                $("#ycform").resetForm();
                $("#bj").val('2');
                $('#xzxfq').attr("selected","selected");
                $('#xzlxq').attr("selected","selected");
                if ((!$("#w_kprqq").val() && $("#w_kprqz").val())
                    || ($("#w_kprqq").val() && !$("#w_kprqz").val())) {
                    // $("#alertt").html('Error,请选择开始和结束时间!');
                    //            	$("#my-alert").modal('open');
                    swal('Error,请选择开始和结束时间!');
                    return false;
                }
                //var dt1 = new Date($("#w_kprqq").val().replace(/-/g, "/"));
                //var dt2 = new Date($("#w_kprqz").val().replace(/-/g, "/"));
                //if (($("#w_kprqq").val() && $("#w_kprqz").val())) {// 都不为空
                   // if (dt1.getYear() == dt2.getYear()) {
                       /* if (dt1.getMonth() == dt2.getMonth()) {
                            if (dt1 - dt2 > 0) {
                                // $("#alertt").html('开始日期大于结束日期,Error!');
                                //               	$("#my-alert").modal('open');
                                swal('开始日期大于结束日期,Error!');
                                return false;
                            }
                        } else {
                            // alert('月份不同,Error!');
                            // $("#alertt").html('Error,请选择同一个年月内的时间!');
                            //               	$("#my-alert").modal('open');
                            swal('Error,选择日期不能跨月!');
                            return false;
                        }
                    } else {
                        // alert('年份不同,Error!');
                        // $("#alertt").html('Error,请选择同一个年月内的时间!');
                        //               	$("#my-alert").modal('open');
                        swal('Error,请选择同一个年月内的时间!');
                        return false;
                    }*/
                //}
                loaddata=true;
                _this.tableEx.ajax.reload();
            });
            $("#kp_search1").on('click', function(e) {
                $("#dxcsz").val("");
                $("#bj").val('1');
                //if ((!$("#s_rqq").val() && $("#s_rqz").val())
                //    || ($("#s_rqq").val() && !$("#s_rqz").val())) {
                    // $("#alertt").html('Error,请选择开始和结束时间!');
                    //            	$("#my-alert").modal('open');
                 //   swal('Error,请选择开始和结束时间!');
                //    return false;
               // }
               // var dt1 = new Date($("#s_rqq").val().replace(/-/g, "/"));
               // var dt2 = new Date($("#s_rqz").val().replace(/-/g, "/"));
               // if (($("#s_rqq").val() && $("#s_rqz").val())) {// 都不为空
               //     if (dt1.getYear() == dt2.getYear()) {
               //         if (dt1.getMonth() == dt2.getMonth()) {
               //             if (dt1 - dt2 > 0) {
                                // $("#alertt").html('开始日期大于结束日期,Error!');
                                //               	$("#my-alert").modal('open');
                //                swal('开始日期大于结束日期,Error!');
                //                return false;
                 //           }
                 //       } else {
                            // alert('月份不同,Error!');
                            // $("#alertt").html('Error,请选择同一个年月内的时间!');
                            //               	$("#my-alert").modal('open');
                   //         swal('Error,选择日期不能跨月!');
                 //           return false;
                 //       }
                 //   } else {
                        // alert('年份不同,Error!');
                        // $("#alertt").html('Error,请选择同一个年月内的时间!');
                        //               	$("#my-alert").modal('open');
                    //    swal('Error,请选择同一个年月内的时间!');
                 //       return false;
                 //   }
               // }
                loaddata=true;
                _this.tableEx.ajax.reload();
            })
        },

        /**
         * kp
         */
        kp : function() {
            var _this = this;
            $("#kpd_kp").on('click', function(e) {
                var chk_value="" ;
                var fpxes = "";
                var fla = true;
                var els =document.getElementsByName("fpje");
                for(var i=0;i<els.length;i++){
                    var fpje = els[i].value.replace(/,/g,'');
                    if(fpje==0){
                        swal("第 "+(i+1)+"行分票金额为0,请重新填写或维护开票限额");
                        fla=false;
                        return false;
                    }
                    if(!fpje.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                        swal("第 "+(i+1)+"行分票金额格式有误，请重新填写！");
                        fla=false;
                        return false;
                    }
                }
                if($("input[name='dxk']:checked").length==1){
                    var bckpje = [];
                    var je=0;
                    var rows1 = $("#mxTable1").find('tr');
                    var els1 =document.getElementsByName("bckpje");
                    for(var i=0;i<els1.length;i++){
                        var fpp = els1[i].value.replace(/,/g,'');
                        je+=fpp*1;
                        bckpje.push(fpp);
                        if(fpp==0){
                        }else if(!fpp.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                            swal("第 "+(i+1)+"行明细金额格式有误，请重新填写！");
                            return false;
                        }else if(Number(fpp)>Number(delcommafy(rows1[i+1].cells[3].innerHTML))){
                            swal("第"+(i+1)+"条明细的本次开票金额不能大于可开票金额！");
                            return false;
                        }
                    }
                    if(je==0){
                        swal("本次开具金额为0,请重新填写");
                        return false;
                    }
                }
                $('input[name="dxk"]:checked').each(function(){
                    chk_value+=$(this).val()+",";
                    var row = $(this).parents('tr').find('input[name="fpje"]');
                    fpxes+=row.fpje+","
                });

                var ddhsthan = chk_value.substring(0, chk_value.length-1);
                fpxes = fpxes.substring(0, fpxes.length-1);
                if(chk_value.length==0){
                    swal("请至少选择一条数据");
                }else{
                    if(!fla){
                        return;
                    }
                    if (!confirm("您确认处理该记录？")) {
                        return;
                    }
                    $("#cljg").show();
                    $("#cljgbt").show();
                    $tab.tabs('refresh');
                    kpspmx_table3.ajax.reload();
                    kpspmx_table.ajax.reload();
                    $('#doc-tab-demo-1').tabs('open', 1)
                }
            });
        },
        /**
         * 修改保存
         */
        xgbc : function() {
            var _this = this;

            $("#kpd_xgbc").on('click', function(e) {
                $('.confirm').attr('disabled',"disabled");
                var r = $("#main_form").validator("isFormValid");
                if (r) {
                    $.ajax({
                        type : "POST",
                        url : "kpdsh/xgbckpd",
                        data : $('#main_form').serialize(),
                        success : function(data) {
                            if(data.msg){
                                $('.confirm').removeAttr('disabled');
                                swal("修改成功");
                                $('#my-alert-edit').modal('close')
                                _this.tableEx.ajax.reload();
                            }
                        }
                    });
                }else{
                }
            });

        },
        /**
         * 修改保存mx
         */
        xgbcmx : function() {
            var _this = this;
            $("#kpdmx_xgbc").on('click', function(e) {
                $('.confirm').attr('disabled',"disabled");
                var r = $("#main_form1").validator("isFormValid");
                if (r) {
                    $('#mx_spse1').val($('#mx_spse').val());
                    $.ajax({
                        type : "POST",
                        url : "kpdsh/xgbcmx",
                        data : $('#main_form1').serialize(),
                        success : function(data) {
                            if(data.msg){
                                $('.confirm').removeAttr('disabled');
                                swal("修改成功");
                                $('#my-alert-edit1').modal('close');
                                _this.tableEx.ajax.reload();
                                kpspmx_table.ajax.reload();
                            }
                        }
                    });
                }else{

                }
            });

        },
        modalAction : function() {
            $("#close").on('click', function() {
                $('#my-alert-edit').modal('close');
            });
            $("#mxclose").on('click', function() {
                $('#my-alert-edit1').modal('close');
            });
            $("#kp_hbkp").on('click', function() {
                var chk_value="" ;
                $('input[name="dxk"]:checked').each(function(){
                    chk_value+=$(this).val()+",";
                });
                var ddhs = chk_value.substring(0, chk_value.length-1);
                if(chk_value.length<2){
                    swal("请至少选择2条数据!")
                }else{

                }
            });
        },
        init : function() {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.kp();
            _this.xgbc();
            _this.xgbcmx();
            _this.modalAction(); // hidden action
        }
    };
    action.init();

});

function yzje(je){
    var zdje = $(je).attr("max");
    var zhi= $(je).val();
    if(zdje==0){
        swal("最大金额为0 ,请维护开票限额");
        return;
    }
    if(zhi*1>zdje*1){
        var msg = "不能超过分票金额"+zdje*1;
        swal(msg);
        $(je).val(zdje);
    }
}
function delcommafy(num){
    if((num+"").trim()==""){
        return "";
    }
    num=num.replace(/,/gi,'');
    return num;
}

