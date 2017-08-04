
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
        $s_ddh : $('#s_ddh'), // search 订单号
        $s_gfmc : $('#s_gfmc'), //关键字购方名称
        $s_fpdm : $('#s_fpdm'), //关键字发票代码
        $s_fphm : $('#s_fphm'), // search 发票号码
        $s_kprqq : $('#s_kprqq'), // search 开票日期
        $s_kprqz : $('#s_kprqz'), // search 开票日期
        $s_spmc : $('#s_spmc'), // search商品名称
        $s_fpzt : $('#s_fpzt'), //关键字发票状态
        $s_fpczlx : $('#s_fpczlx'),
        $s_dyzt : $('#s_dyzt'), // search 开票日期
        $jsSearch : $('.js-search'),
        $jsSearch1 : $('#button3'),
        $jsExport : $('.js-export'),
        $jsLoading : $('.js-modal-loading'),
        $jsPrint : $('.js-print'),
        $checkAll : $('#select_all')
    };

    var loaddata2=false;
    var action = {
        tableEx : null, // cache dataTable
        config : {
            getUrl : 'fpcx/getKplsList'
        },
        dataTable : function() {
            var start = [
                    {
                        "orderable" : false,
                        "data" : null,
                        render : function(data, type, full, meta) {
                            return '<input type="checkbox" value="'
                                    + data.kplsh + '" />';
                        }
                    }, {
                        "orderable" : false,
                        "data" : null,
                        "defaultContent" : ""
                    }, {
                        "data" : "ddh"
                    }, {
                        "data" : "fpczlxmc"
                    }, {
                        "data" : "fpdm"
                    }, {
                        "data": null,
                        "render":  function(data){
                            if(data.fpzldm=="12"&&data.pdfurl!=null){
                                return '<a  href="'+ data.pdfurl + '" target="_blank">'+data.fphm+'</a>';
                            }else if(data.fpzldm=="12"&&data.pdfurl==null){
                                return data.fphm;
                            }else if(data.fphm!=null&&data.fpzldm!="12"){
                                return data.fphm;
                            }else{
                                return "";
                            }
                        }
                    },{
                        "data" : function(data) {
                            if (data.fpzldm=="01") {
                                return "增值税专用发票";
                            } else if(data.fpzldm=="02"){
                                return "增值税普通发票";
                            }else if(data.fpzldm=="12"){
                                return "电子发票(增普)";
                            }else{
                                return "";
                            }
                        }
                    }, {
                        "data" : function(data) {
                            if (data.jshj) {
                                return FormatFloat(data.jshj, "###,###.00");
                            } else {
                                return null;
                            }
                        },
                        'sClass' : 'right'
                    }, {
                        "data" : "gfmc"
                    }, {
                        "data" : "kprq"
                    }, {
                        "data" : "fpzt"
                    },{
                        "data" :function (data) {
                            if(data.fpzldm =="12"){
                                return "";
                            }else {
                                return data.sfdy;
                            }
                        }
                        /*"data" : "sfdy"*/
                    },{
                        "data" :"errorReason"
                } ]
            $.ajax({
                url : 'zdyl/query',
                type : 'POST', //GET
                async: false,
                data : {},
                dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
                success : function(data) {
                    $('#bt').append(data.bt);
                    $.each(data.da, function(n, value) {
                        if(value=="hjje"){
                            var j = {
                                    "data" : function(data) {
                                        if (data.hjje) {
                                            return FormatFloat(data.hjje, "###,###.00");
                                        } else {
                                            return null;
                                        }
                                    }};
                        }else if(value=="hjse"){
                            var j = {
                                    "data" : function(data) {
                                        if (data.hjse) {
                                            return FormatFloat(data.hjse, "###,###.00");
                                        } else {
                                            return null;
                                        }
                                    }}; 
                        }else{
                            var j = {"data":value};
                        }
                        start.push(j);
                    });
                }
            })


            var _this = this;
            var t = el.$jsTable.DataTable({
                "processing" : true,
                "serverSide" : true,
                ordering : false,
                searching : false,
                "scrollX" : true,
                "ajax" : {
                    url : _this.config.getUrl,
                    type : 'POST',
                    data : function(d) {
                        var bj = $('#bj').val();
                        var txt = $('#searchtxt').val();
                        var tip = $('#tip').val();
                        if (bj ==  "1") {
                            if (tip == "1") {
                                d.gfmc = txt; // search 订单号
                            }else if (tip == "2") {
                                d.ddh = txt;
                            }else if (tip == "3") {
                                d.spmc = txt;
                            }else if (tip == "4"){
                                d.xfmc = txt;
                            }else if (tip == "5") {
                                d.fphm = txt;
                            }else if (tip == "6") {
                                d.fpdm = txt;
                            }
                        }else if (bj ==  "2") {
                            d.ddh = el.$s_ddh.val(); // search 订单号
                            d.gfmc = el.$s_gfmc.val(); //购方名称
                            d.fpdm = el.$s_fpdm.val(); //发票代码
                            d.fphm = el.$s_fphm.val(); // search 发票号码
                            d.kprqq = el.$s_kprqq.val(); // search 开票日期
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期
                            d.spmc = el.$s_spmc.val(); //商品名称
                            d.fpzt = el.$s_fpzt.val(); //发票状态
                            d.fpczlx = el.$s_fpczlx.val();
                            d.printflag = el.$s_dyzt.val();//打印状态
                            d.xfsh=$("#mb_xfsh").val();
                            d.sk =$("#mb_skp").val();
                            d.fpzldm =$("#s_fpzldm").val();
                        }else{
                            d.ddh = "-11111111";
                        }
                        d.loaddata2=loaddata2;
                    }
                },
                "columns" : start
            });

            t.on('draw.dt', function(e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function(cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            //预览
            $("#kp_yl").click(function () {
               if($("input[name='chk']:checked").length!=1){
                swal("请选择一条数据预览");
                return;
               }
              var djhArr = [];
                      $('input[name="chk"]:checked').each(function(cell,i){
                          djhArr.push($(this).val()); 
                      })
                    var djh= djhArr.join(",");
                      $("#doc-modal-fpyll").load('kp/kpyl?kpsqhs='+djh);
                        $("#doc-modal-fpyl").modal("open");

            })
            $("#mb_xfsh").change(function() {
                 $('#mb_skp').empty();
                 var xfsh = $(this).val();
                 if (xfsh == null || xfsh == '' || xfsh == "") {
                     return;
                 }
                 var url = "fpcx/getSkpList";
                 $.ajax({
                        type : "POST",
                        url : url,
                        data : {
                             xfsh : xfsh
                         },
                        success : function(data) {
                            if (data) {
                                 var option = $("<option>").text('请选择').val(-1);
                                 $('#mb_skp').append(option);
                                 for (var i = 0; i < data.skps.length; i++) {
                                     option = $("<option>").text(data.skps[i].kpdmc).val(data.skps[i].id);
                                     $('#mb_skp').append(option);
                                 }
                             }
                        }
                 });
             });
            
            
            
            
            /***发票开具*/
            $.ajax({
                url: "kp/getXfxx", context: null, success: function (data) {
                    $("#qymc").val(data.xfmc);
                    $("#nsrsbh").val(data.xfsh);
                }
            });
            var djh=[];
            var loaddata=false;
            var jyls_table = $('#jyls_table').DataTable({
                "searching": false,
                "serverSide": true,
                "sServerMethod": "POST",
                "processing": true,
                "scrollX": true,
                ordering: false,
                ajax: {
                    "url": "kp/getjylslist?clztdm=00",
                    type: 'post',
                    data: function (d) {
                        djh.splice(0,djh.length);
                        var bz = $('#bz123').val();
                        var dxcsm = $('#dxcsm').val();
                        var dxcsz = $('#dxcsz').val();
                        if (bz == "1") {
                            if (dxcsm == "gfmc") {
                                 d.gfmc = dxcsz;
                            }else if (dxcsm == "ddh"){
                                d.ddh = dxcsz;
                            }
                        }else if (bz == "2"){
                            d.xfsh = $('#s_xfsh1').val();   // search 销方
                            d.gfmc = $('#s_gfmc1').val();   // search 购方名称
                            d.ddh = $('#s_ddh1').val();   // search 订单号
                            d.fpzldm = $('#s_fplx').val();   // search 发票号码
                            d.rqq = $('#s_rqq1').val(); // search 开票日期
                            d.rqz = $('#s_rqz1').val(); // search 开票日期
                        }
                        d.loaddata=loaddata;
                    }
                },
                "columns": [
                            {
                                "orderable" : false,
                                "data" : null,
                                render : function(data, type, full, meta) {
                                    return '<input type="checkbox" value="'
                                        + data.djh + '" name="chk"  />';
                                }
                            },
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                    {"data": "jylsh"},
                    {"data": "ddh"},
                    {"data": "jylssj"},
                    {"data": function(data){
                        if(data.fpzldm=='12'){
                            return "电子票";
                        }else if(data.fpzldm=='01'){
                            return "纸质专票";
                        }else if(data.fpzldm=='02'){
                            return "纸质普票";
                        }
                    
                    }},
                    {"data": "gfsh"},
                    {"data": "gfmc"},
                    {"data": "gfyh"},
                    {"data": "gfyhzh"},
                    {"data": "gflxr"},
                    {"data": "gfdz"},
                    {"data": "gfsjh"},
                    {"data": "bz"},
                    {"data": function (data) {
                        if (data.jshj) {
                            return FormatFloat(data.jshj, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'}
                ]

            });
            jyls_table.on('draw.dt', function(e, settings, json) {
                var x = jyls_table, page = x.page.info().start; // 设置第几页
                jyls_table.column(1).nodes().each(function(cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });
            var jyspmx_table = $('#jyspmx_table').DataTable({
                "searching": false,
                "serverSide": true,
                "sServerMethod": "POST",
                "processing": true,
                ordering: false,
                ajax: {
                    "url": "kp/getjyspmxlist",
                    data: function (d) {
                         var djhArr = [];
                         d.djh = $("#djh").val();
                    }
                },
                "columns": [
                    {"data": "spmxxh"},
                    {"data": "spmc"},
                    {"data": "spggxh"},
                    {"data": "spdw"},
                    {"data": function (data) {
                        if (data.sps) {
                            return FormatFloat(data.sps, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'},
                    {"data": function (data) {
                        if (data.spdj) {
                            return FormatFloat(data.spdj, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'},
                    {"data": function (data) {
                        if (data.spje) {
                            return FormatFloat(data.spje, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'},
                    {"data": function (data) {
                        if (data.spsl) {
                            return FormatFloat(data.spsl, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'},
                    {"data": function (data) {
                        if (data.spse) {
                            return FormatFloat(data.spse, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'},
                    {"data": function (data) {
                        if (data.jshj) {
                            return FormatFloat(data.jshj, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'}
                ]
            });
            jyspmx_table.on('draw.dt', function(e, settings, json) {
                var x = jyspmx_table, page = x.page.info().start; // 设置第几页
                jyspmx_table.column(0).nodes().each(function(cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });
            $('#kp_search').click(function () {
                $("#bz123").val("1");
                $('#xzxfq').attr("selected","selected");
                $('#xzlxq').attr("selected","selected");
                loaddata=true;
                jyls_table.ajax.reload();
            });
            $('#kp_search1').click(function () {
                $("#bz123").val("2");
                loaddata=true;
                jyls_table.ajax.reload();
            });
            //删除
            $("#kp_del").click(function () {
                var djhArr = [];
                jyls_table.column(0).nodes().each(function(cell, i) {

                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {

                        var row =jyls_table.row(i).data().djh;
                        djhArr.push(row);
                    }

                });
                if (djhArr.length == 0) {
                    swal("请选择需要删除的交易流水...");
                    return;
                }
                swal({
                    title: "您确定要删除吗？",
                    text: "您确定要删除这条数据？",
                    type: "warning",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "确 定",
                    confirmButtonColor: "#ec6c62"
                }, function() {
                        $("#kp_kp").attr('disabled',"true"); 
                        $("#kp_kpdy").attr('disabled',"true"); 
                        $("#kp_del").attr('disabled',"true");

                        $.post(
                            "kp/doDel",
                            "djhArr="+ djhArr.join(","),
                            function(res) {
                                $('#kp_kp').removeAttr("disabled"); 
                                $('#kp_kpdy').removeAttr("disabled"); 
                                $('#kp_del').removeAttr("disabled");  
                                if (res) {
                                    swal({ 
                                          title: "已成功删除", 
                                          timer: 1500, 
                                          type: "success", 
                                          showConfirmButton: false 
                                        });
                                    jyls_table.ajax.reload();
                                }
                            }
                        );
                    }
                )
            });
            $('#check_all1').change(function () {
                if ($('#check_all1').prop('checked')) {
                    djh.splice(0,djh.length);
                    jyls_table.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                        var row =jyls_table.row(i).data();
                        djh.push(row.djh);
                    });
                } else {
                    djh.splice(0,djh.length);
                    jyls_table.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
                $("#djh").val(djh.join(","));
                jyspmx_table.ajax.reload();
            });

            $('#jyls_table tbody').on('click', 'tr', function () {
                var data = jyls_table.row($(this)).data();
                if ($('#check_all1').prop('checked')){
                    djh.splice(0,djh.length);
                    jyls_table.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }


                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked',false);
                    djh.splice($.inArray(data.djh, djh), 1);

                } else {
                    $(this).find('td:eq(0) input').prop('checked',true)
                    $(this).addClass('selected');
                    djh.push(data.djh);

                }
                $('#check_all1').prop('checked',false);
                $("#djh").val(djh.join(","));
                jyspmx_table.ajax.reload();
            });
            var mxarr = [];
            var $modal = $("#my-alert-edit");
            $("#my-alert-edit").on("open.modal.amui", function () {
                $("#mx_form").validator("destroy");
                $("#main_form").validator("destroy");
                jyspmx_edit_table.clear();
                jyspmx_edit_table.draw();
            });
            $('#kp_add').click(function () {
                mxarr = [];
                $('#mx_form').resetForm();
                $('#main_form').resetForm();
                $modal.modal({"width": 800, "height": 600});
            });



            $('#kp_kp').click(function () {
                $("#doc-modal-fphm").modal("close");
                var djhArr = [];
                var djhArr1 = [];
                var bckpje = [];
                jyls_table.column(0).nodes().each(function(cell, i) {
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        var row =jyls_table.row(i).data().djh;
                        djhArr.push(row);
                    }
                });
                if (djhArr.length == 0) {
                    swal("请勾选需要开票的交易流水");
                    return;
                }
                $("#kp_kp").attr('disabled',"true");
                $("#kp_kpdy").attr('disabled',"true"); 
                $("#kp_del").attr('disabled',"true"); 
                $("#conft").html("确认开票吗")

                $('#kp_kp').removeAttr("disabled"); 
                $('#kp_kpdy').removeAttr("disabled"); 
                $('#kp_del').removeAttr("disabled"); 
                $.ajax({
                    url: "kp/doKp", 
                    context: document.body,
                    data:{ "djhArr" : djhArr.join(","),"dybz":"0"},
                    success: function (data) {
                        if (data.success) {
                            $('#kp_kp').removeAttr("disabled"); 
                            $('#kp_kpdy').removeAttr("disabled"); 
                            $('#kp_del').removeAttr("disabled");  
                            swal("申请开票成功");
                            jyls_table.ajax.reload();
                        } else {
                            $('#kp_kp').removeAttr("disabled"); 
                            $('#kp_kpdy').removeAttr("disabled"); 
                            $('#kp_del').removeAttr("disabled"); 
                            jyls_table.ajax.reload();
                            swal(data.msg);
                        }
                    }
                }); 
            });
            $('#kp_kpdy').click(function () {
                var djhArr = [];
                jyls_table.column(0).nodes().each(function(cell, i) {
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        var row =jyls_table.row(i).data().djh;
                        djhArr.push(row);
                    }
                });
                if (djhArr.length == 0) {
                    swal("请勾选需要开票的交易流水");
                    return;
                }
                var skpid="";
                var fpzldm="";
                var flag = true;
                $(":checkbox:checked","#jyls_table").each(function(){
                    var tr = $(this).parents("tr");
                    var data = jyls_table.row(tr).data();
                    if(skpid==""){
                        skpid =  data.skpid;
                    }else{
                        if(skpid!=data.skpid){
                            swal("批量勾选的开票点不一致,请重新勾选");
                            flag = false;
                            return false;
                        }
                    }
                    if(fpzldm==""){
                        fpzldm =  data.fpzldm;
                    }else{
                        if(fpzldm!=data.fpzldm){
                            swal("批量勾选的发票种类不一致,请重新勾选");
                            flag = false;
                            return false;
                        }
                    }
                })
                if(flag){
                    $("#kp_kp").attr('disabled',"true"); 
                    $("#kp_kpdy").attr('disabled',"true"); 
                    $("#kp_del").attr('disabled',"true");
                    if(fpzldm!="12"){
                        $.ajax({
                            url:"kp/hqfphm",
                            "type": "POST",
                            data:{ "fpzldm" :fpzldm,"skpid":skpid }, 
                            success: function (data) {
                                if (data.success) {
                                    $("#doc-modal-fphm").modal("open");
                                    if(fpzldm=="12"){
                                        $("#fplxmc").val("电子发票(增普)");
                                    }
                                    if(fpzldm=="02"){
                                        $("#fplxmc").val("增值税普通发票");
                                    }
                                    if(fpzldm=="01"){
                                        $("#fplxmc").val("增值税专用发票");
                                    }
                                    $("#fpdm2").val(data.fpdm);
                                    $("#fphm2").val(data.fphm);
                                    $('#kp_kp').removeAttr("disabled");
                                    $('#kp_kpdy').removeAttr("disabled");
                                    $('#kp_del').removeAttr("disabled");
                                } else {
                                    swal({
                                        title: data.msg+"您是否离线开票！",
                                        showCancelButton: true,
                                        closeOnConfirm: false,
                                        confirmButtonText: "确 定",
                                        confirmButtonColor: "#ec6c62"
                                    }, function() {
                                        $('.confirm').attr('disabled',"disabled");
                                        $.ajax({
                                            url: "kp/doKp", 
                                            context: document.body,
                                            data:{ "djhArr" : djhArr.join(","),"dybz":"0"},
                                            }).done(function(data) {
                                                if (data.success) {
                                                    console.log(222);
                                                    $('.confirm').removeAttr('disabled');
                                                    $('#kp_kp').removeAttr("disabled");
                                                    $('#kp_kpdy').removeAttr("disabled");
                                                    $('#kp_del').removeAttr("disabled");
                                                    swal({
                                                          title: "申请开票成功", 
                                                          timer: 1500, 
                                                            type: "success",
                                                          showConfirmButton: false 
                                                        });
                                                    jyls_table.ajax.reload();
                                                } else {
                                                    $('#kp_kp').removeAttr("disabled");
                                                    $('#kp_kpdy').removeAttr("disabled");
                                                    $('#kp_del').removeAttr("disabled");
                                                    jyls_table.ajax.reload();
                                                    swal(data.msg);
                                                }
                                            })
                                        });
                                        $('#kp_kp').removeAttr("disabled");
                                        $('#kp_kpdy').removeAttr("disabled");
                                        $('#kp_del').removeAttr("disabled");
                                }
                            }
                        });
                    }else{
                        swal({
                            title: '您确认开票吗?',
                            showCancelButton: true,
                            closeOnConfirm: false,
                            confirmButtonText: "确 定",
                            confirmButtonColor: "#ec6c62"
                        }, function() {
                            $('.confirm').attr('disabled',"disabled");
                            $.ajax({
                                url: "kp/doKp",
                                context: document.body,
                                data:{ "djhArr" : djhArr.join(","),"dybz":"0"},
                            }).done(function(data) {
                                if (data.success) {
                                    $('.confirm').removeAttr('disabled');
                                    $('#kp_kp').removeAttr("disabled");
                                    $('#kp_kpdy').removeAttr("disabled");
                                    $('#kp_del').removeAttr("disabled");
                                    swal({
                                          title: "申请开票成功", 
                                          timer: 1500, 
                                          type: "success", 
                                          showConfirmButton: false 
                                        });
                                    jyls_table.ajax.reload();
                                } else {
                                    $('#kp_kp').removeAttr("disabled");
                                    $('#kp_kpdy').removeAttr("disabled");
                                    $('#kp_del').removeAttr("disabled");
                                    jyls_table.ajax.reload();
                                    swal(data.msg);
                                }
                            })
                        });
                        $('#kp_kp').removeAttr("disabled");
                        $('#kp_kpdy').removeAttr("disabled");
                        $('#kp_del').removeAttr("disabled");
                    }
                }
            });
            var kplsh = [];
            $("#fpck").click(function(){
                var ckbz="";
                kplsh.splice(0,kplsh.length);
                t.column(0).nodes().each(function(cell, i) {
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        var data =t.row(i).data();
                        if(data.fpztdm=='05'||data.fpztdm=='14'){
                            kplsh.push(data.kplsh);
                        }else{
                           ckbz="1";
                        }
                    }
                });
                if(ckbz=="1"){
                    swal("不能重开发票状态为正常发票的发票流水，请重新选择！");
                    return ;
                }
                if(kplsh.length==0){
                    swal("请选择一条记录！");
                    return ;
                }
                var skpid="";
                var fpzldm="";
                var flag = true;
                $(":checkbox:checked","#kpls_table").each(function(){
                    var tr = $(this).parents("tr");
                    var data = t.row(tr).data();
                    if(skpid==""){
                        skpid =  data.skpid;
                    }else{
                        if(skpid!=data.skpid){
                            swal("批量勾选的开票点不一致,请重新勾选");
                            flag = false;
                            return false;
                        }
                    }
                    if(fpzldm==""){
                        fpzldm =  data.fpzldm;
                    }else{
                        if(fpzldm!=data.fpzldm){
                            swal("批量勾选的发票种类不一致,请重新勾选");
                            flag = false;
                            return false;
                        }
                    }
                })
                if(flag){
                    if(fpzldm!="12"){
                        $.ajax({
                            url: "kp/hqfphm","type": "POST", data:{ "fpzldm" :fpzldm,"skpid":skpid }, success: function (data) {
                                if (data.success) {
                                    $("#doc-modal-fpck").modal("open");
                                    $("#fpdm3").val(data.fpdm);
                                    $("#fphm3").val(data.fphm);
                                    if(fpzldm=="12"){
                                        $("#fplxmc1").val("电子发票(增普)");
                                    }
                                    if(fpzldm=="02"){
                                        $("#fplxmc1").val("增值税普通发票");
                                    }
                                    if(fpzldm=="01"){
                                        $("#fplxmc1").val("增值税专用发票");
                                    }
                                } else {
                                    swal(data.msg);
                                }
                            }
                        });
                    }else{
                        swal({
                            title:"请确认发票是否已经开具成功以避免重复开具发票！",
                            showCancelButton: true,
                            closeOnConfirm: false,
                            confirmButtonText: "确 定",
                            confirmButtonColor: "#ec6c62"
                        }, function() {
                            $('.confirm').attr('disabled',"disabled");
                            $.ajax({
                                url:"kp/fpck",
                                data:{"kplshs" : kplsh.join(",")},
                            }).done(function(data) {
                                if(data.success){
                                    $('.confirm').removeAttr('disabled');
                                    swal(data.msg);
                                    t.ajax.reload();
                                }else{
                                    swal(data.msg);
                                }
                            })
                        });
                    }
                }
            });
            $("#fpckqr").click(function(){
                $("#doc-modal-fpck").modal("close");
                swal({
                    title:"请确认发票是否已经开具成功以避免重复开具发票！",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "确 定",
                    confirmButtonColor: "#ec6c62"
                }, function() {
                    $('.confirm').attr('disabled',"disabled");
                    $.ajax({
                        url:"kp/fpck",
                        data:{"kplshs" : kplsh.join(",")},
                    }).done(function(data) {
                        if(data.success){
                            swal(data.msg);
                            t.ajax.reload();
                        }else{
                            swal(data.msg);
                        }
                    })
                });
            });
            $('#savet').click(function () {
                 $("#savet").attr('disabled',"true"); 
                var djhArr = [];
                jyls_table.column(0).nodes().each(function(cell, i) {
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        var row =jyls_table.row(i).data().djh;
                        djhArr.push(row);
                    }
                });
                if (djhArr.length == 0) {
                    swal("请勾选需要开票的交易流水...");
                    return;
                }
                if($("#s_xfsh").val()==""||$("#s_fplx").val()==""){
                    swal("请选择销方和开票类型...");
                    return;
                }
                var kpxe = $("#fpjesrk").val()
            })

            $('#kp_all').click(function () {
                swal({
                    title: "您确认开票？",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "确 定",
                    confirmButtonColor: "#ec6c62"
                }, function() {
                    $.ajax({
                        url: "kp/doAllKp?clztdm=00",
                        type: "post",
                        data: {
                            xfsh : $('#xfsh1').val(),   // search 销方
                            gfmc : $('#gfmc1').val(),   // search 购方名称
                            ddh : $('#s_ddh1').val(),  // search 订单号
                            jylsh : $('#s_lsh1').val(),   // search 发票号码
                            rqq : $('#s_rqq1').val(), // search 开票日期
                            rqz : $('#s_rqz1').val() // search 开票日期
                        },
                    }).done(function(data) {
                        if (data.success) {
                            $('.confirm').removeAttr('disabled');
                            swal("发送到开票队列成功!");
                            jyls_table.ajax.reload();
                        } else {
                            alert(data.msg);
                        }
                    })
                });

            });
            var index = 1;
            var jyspmx_edit_table = $('#jyspmx_edit_table').DataTable({
                "searching": false,
                "bPaginate": false,
                "bSort": false,
                "scrollY": "70",
                "scrollCollapse": "true"
            });
            $('#my-alert-edit').on('open.modal.amui', function () {
            });
            $('#main_tab').find('a.ai').on('opened.tabs.amui', function (e) {
                jyspmx_edit_table.draw();
            })
            $("#addRow").click(function () {
                var r = $("#mx_form").validator("isFormValid");
                if (r) {
                    var spdm = $("#spdm_edit").val();
                    var mc = $("#mc_edit").val();
                    var ggxh = $("#ggxh_edit").val();
                    var dw = $("#dw_edit").val();
                    var sl = $("#sl_edit").val();

                    var dj = $("#dj_edit").val();
                    var je = $("#je_edit").val();
                    var sltaxrate = $("#sltaxrate_edit").val();
                    var se = $("#se_edit").val();
                    var jshj = $("#jshj_edit").val();
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

            $("#save").click(function () {
                var r = $("#main_form").validator("isFormValid");
                if (r) {
                    var ps = [];
                    var d = jyspmx_edit_table.rows().data();
                    if (d.length == 0) {
                        $("#main_tab").tabs('open', 1);
                        return;
                    }
                    ps.push("mxcount=" + d.length);
                    d.each(function (data, index) {
                        $(data).each(function (i, c) {
                            if (i == 1) {
                                ps.push("spdm=" + c);
                            } else if (i == 2) {
                                ps.push("spmc=" + c);
                            } else if (i == 3) {
                                ps.push("ggxh=" + c);
                            } else if (i == 4) {
                                ps.push("dw=" + c);
                            } else if (i == 5) {
                                ps.push("sl=" + c);
                            } else if (i == 6) {
                                ps.push("dj=" + c);
                            } else if (i == 7) {
                                ps.push("je=" + c);
                            } else if (i == 8) {
                                ps.push("rate=" + c);
                            } else if (i == 9) {
                                ps.push("se=" + c);
                            } else if (i == 10) {
                                ps.push("jshj=" + c);
                            }
                        });
                    });
                    var frmData = $("#main_form").serialize() + "&" + ps.join("&");
                    $.ajax({
                        url: "kp/save", "type": "POST", context: document.body, data: frmData, success: function (data) {
                            if (data.success) {
                                swal({
                                  title: "保存成功", 
                                  timer: 1500, 
                                  type: "success", 
                                  showConfirmButton: false 
                                });
                                jyls_table.ajax.reload();
                            } else {
                                swal(data.msg);
                            }
                        }
                    });
                } else {
                    ///如果校验不通过
                    $("#main_tab").tabs('open', 0);
                }
            });
            $("#close").click(function () {
                $modal.modal("close");
            });
            $("#closet").click(function () {
                 $('#kp_kp').removeAttr("disabled");
                  $('#savet').removeAttr("disabled");
               $("#fpjek").modal("close");
            });
            //批量导入
            var $importModal = $("#bulk-import-div");
            $("#kp_dr").click(function () {
                $('#importExcelForm').resetForm();
                $importModal.modal({"width": 600, "height": 350});
            });

            $("#close1").click(function () {
                $importModal.modal("close");
            });
            $("#close2").click(function () {
                $importConfigModal.modal("close");
            });
            //导入配置
            var $importConfigModal = $("#import_config_div");
            $("#btnImportConfig").click(function () {
//              $importModal.modal("close");
                $('#importConfigForm').resetForm();
                $importConfigModal.modal({"width": 600, "height": 480});
            });
            /***发票打印*/
            $(".js-print").on('click',function() {
                var ids = '';
                var flag = true;
                t.column(0).nodes().each(
                    function(cell, i) {
                        if (flag) {
                            var $checkbox = $(cell).find('input[type="checkbox"]');
                            if ($checkbox.is(':checked')) {
                                var data =t.row(i).data();
                                if (data.fphm == null|| data.fphm == ''||data.pdfurl=='') {
                                    swal("存在正在开具或者开具失败的发票，不能批量打印！");
                                    flag = false;
                                    return false;
                                }
                                ids += $checkbox.val()+ ',';
                            }
                        }
                    });
                if (flag) {
                    if (ids != '') {
                        window.open('fpcx/printmany?ids='+ ids, '',
                            'scrollbars=yes,status=yes,left=0,top=0,menubar=yes,resizable=yes,location=yes');
                        $('#kplshStr').val(ids);
                        ids = '';
                    } else {
                        swal("请先选中至少一条记录！");
                    }
                }
            });
            //发票导出
            el.$jsExport.on('click',function (e) {
                var bj = $('#bj').val();
                var kplsh11 ='';
                var flag = true;
                if(bj  == ""||bj == undefined||bj == null){
                    flag = false;
                    swal("请先加载数据!");
                }
                if(bj == '1'||bj=='2'){
                    t.column(0).nodes().each(
                        function (cell,i){
                            if(flag){
                                var $checkbox = $(cell).find('input[type="checkbox"]');
                                if($checkbox.is(':checked')){
                                    kplsh11 += $checkbox.val()+ ',';
                                }
                            }
                        }
                    )
                    /*if(kplsh11.length == 0){
                        flag = false;
                        swal("请选择需要导出的数据!");
                    }*/
                    if(flag){
                        $('#kplsh1').val(kplsh11);
                        $('#searchform').submit();
                    }
                }else{
                    var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
                    var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
                    if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
                        if (dt1.getYear() == dt2.getYear()) {
                            if (dt1.getMonth() == dt2.getMonth()) {
                                if (dt1 - dt2 > 0) {
                                    swal('开始日期大于结束日期,Error!');
                                    return false;
                                }
                            } else {
                                swal('Error,请选择同一个年月内的时间!');
                                return false;
                            }
                        } else {
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    }
                    if(flag){
                        $('#kplsh1').val(kplsh11);
                        $("#searchform").submit();
                    }else{
                        return false;
                    }
                }

            });
            return t;
        },

        /**
         * search action
         */
        search_ac : function() {
            var _this = this;
            el.$jsSearch.on('click', function(e) {
                $('#bj').val('1');
                e.preventDefault();
                loaddata2=true;
                _this.tableEx.ajax.reload();

            });
            el.$jsSearch1.on('click', function(e) {
                $('#bj').val('2');
                if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
                        || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
                    swal('Error,请选择开始和结束时间!');
                    return false;
                }
                var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
                var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
                if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
                    if (dt1.getYear() == dt2.getYear()) {
                        if (dt1.getMonth() == dt2.getMonth()) {
                            if (dt1 - dt2 > 0) {
                                swal('开始日期大于结束日期,Error!');
                                return false;
                            }
                        } else {
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    } else {
                        swal('Error,请选择同一个年月内的时间!');
                        return false;
                    }
                }
                e.preventDefault();
                loaddata2=true;
                _this.tableEx.ajax.reload();

            });
        },
        /**
         * 导出按钮
         */
    /*    exportAc : function() {
            el.$jsExport.on('click', function(e) {
                var bj = $('#bj').val();
                if (bj == '1') {
                    $('#searchform').submit();
                }else{
                    var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
                    var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
                    if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
                        if (dt1.getYear() == dt2.getYear()) {
                            if (dt1.getMonth() == dt2.getMonth()) {
                                if (dt1 - dt2 > 0) {
                                    swal('开始日期大于结束日期,Error!');
                                    return false;
                                }
                            } else {
                                swal('Error,请选择同一个年月内的时间!');
                                return false;
                            }
                        } else {
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    }
                    $("#searchform1").submit();
                }
                
            });
        },*/
        /**
         * 自定义列
         */
        autoColumn : function() {
            el.$jsAuto.on('click', function(e) {
                $.ajax({
                    url : 'zdyl/query',
                    type : 'POST', //GET
                    async: false,
                    data : {},
                    dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
                    success : function(data) {
                        $.each(data.list, function(n, value) {
                            var j ="#"+value.zddm;
                            $(j).attr("checked",'true');
                        });
                    }
                })
                $('#biaoti').modal('open');
            });
        },
        /**
         * 保存自定义列
         */
        saveColumn : function() {
            el.$jsSubmit.on('click', function(e) {
                el.$jsSubmit.attr({"disabled":"disabled"});
                $.ajax({
                    type : "POST",
                    url : "zdyl/save",
                    data : $('#biao').serialize(),// 你的formid
                    success : function(data) {
                        el.$jsSubmit.removeAttr("disabled");
                        $('#biaoti').modal('close');
                        swal(data.msg);
                        refresh();
                    }
                });
            });
        },
        /**
         * 自定义导出列
         */
        autoColumn1 : function() {
            el.$jsOut.on('click', function(e) {
                $.ajax({
                    url : 'zdyl/queryOut',
                    type : 'POST', //GET
                    async: false,
                    data : {},
                    dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
                    success : function(data) {
                        $.each(data.list, function(n, value) {
                            var j ="#"+value.zddm+"1";
                            $(j).attr("checked",'true');
                        });
                    }
                })
                $('#biaoti1').modal('open');
            });
        },
        /**
         * 保存自定义导出列
         */
        saveColumn1 : function() {
            el.$jsSubmit1.on('click', function(e) {
                el.$jsSubmit1.attr({"disabled":"disabled"});
                $.ajax({
                    type : "POST",
                    url : "zdyl/saveOut",
                    data : $('#biao1').serialize(),// 你的formid
                    success : function(data) {
                        el.$jsSubmit1.removeAttr("disabled");
                        $('#biaoti1').modal('close');
                        swal(data.msg)
                    }
                });
            });
        },

        //全选按钮
        checkAllAc : function() {
            var _this = this;
            el.$checkAll.on('change', function(e) {
                var $this = $(this), check = null;
                if ($(this).is(':checked')) {
                    _this.tableEx.column(0).nodes().each(
                            function(cell, i) {
                                $(cell).find('input[type="checkbox"]').prop(
                                        'checked', true);
                            });
                } else {
                    _this.tableEx.column(0).nodes().each(
                            function(cell, i) {
                                $(cell).find('input[type="checkbox"]').prop(
                                        'checked', false);
                            });
                }
            });
        },
        modalAction : function() {
            el.$jsClose.on('click', function() {
                $('#biaoti').modal('close');
            });
            el.$jsClose1.on('click', function() {
                $('#biaoti1').modal('close');
            });
        },
        init : function() {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
           /* _this.exportAc();*/
            _this.autoColumn();
            _this.autoColumn1();
            _this.checkAllAc();
            _this.modalAction(); // hidden action
            _this.saveColumn();
            _this.saveColumn1();
        }
    };
    action.init();

});


function delcommafy(num){
       if((num+"").trim()==""){
          return "";
       }
       num=num.replace(/,/gi,'');
       return num;
    }