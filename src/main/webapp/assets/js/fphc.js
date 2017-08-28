/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
        $jsTable: $('#fpTable'),
        //$modalHongchong: $('#hongchong'),
        $jsSubmit: $('#js-submit'),
        $jsClose: $('#js-close'),
        $jsForm0: $('#ycform'), // 查询form 
        $jsForm1: $('#ycform1'), // 查询form 

        
        $s_kprqq: $('#s_rqq'), // search 开票日期起
        $s_kprqz: $('#s_rqz'), // search 开票日期止
        $s_gfmc: $('#s_gfmc'), // search 购方名称
        $s_fpdm: $('#s_fpdm'), // search 发票代码
        $s_fphm: $('#s_fphm'), // search 发票号码
        $s_ddh: $('#s_ddh'), // search   订单号
        $s_xfsh: $('#s_xfsh'), // search   销方税号
        $s_fplx: $('#s_fplx'), // search   发票类型
        
        $s_kprqq1: $('#s_rqq1'), // search 开票日期起
        $s_kprqz1: $('#s_rqz1'), // search 开票日期止
        $s_gfmc1: $('#s_gfmc1'), // search 购方名称
        $s_fpdm1: $('#s_fpdm1'), // search 发票代码
        $s_fphm1: $('#s_fphm1'), // search 发票号码
        $s_ddh1: $('#s_ddh1'), // search   订单号
        $s_xfsh1: $('#s_xfsh1'), // search   销方税号
        $s_fplx1: $('#s_fplx1'), // search   发票类型
        $jsLoading: $('.js-modal-loading'),
    };

  //开票商品明细table
    var kpspmx_table = $('#mxTable').DataTable({
        "searching": false,
        "serverSide": true,
        "sServerMethod": "POST",
        "processing": true,
        "bPaginate":false,
        "bLengthChange":false,
        "bSort":false,
        "bInfo": false,
        "scrollX": true,
        ajax: {
            "url": "fphc/getMx",
            data: function (d) {
                d.kplsh = $("#kplsh").val();
            }
        },
        "columns": [
            {"data": "spmxxh"},
            {"data": "spmc"},
            {"data": "spggxh"},
            {
                "data": null,
                "render": function (data) {
                   return '<input type="text" name="hcje" value="'+data.khcje+'">';
                    
                }
            },
            {
                "data": function (data) {
                    if (data.khcje) {
                        return FormatFloat(data.khcje,
                            "###,###.00");
                    }else{
                        return null;
                    }
                },
                'sClass': 'right'
                    },
            {
                "data": function (data) {
                    if (data.yhcje) {
                        return FormatFloat(data.yhcje,
                            "###,###.00");
                    }else{
                        return null;
                    }
                },
                'sClass': 'right'
                    },
            {
              "data": function (data) {
                     if (data.spje) {
                         return FormatFloat(data.spje,
                             "###,###.00");
                     }else{
                         return null;
                     }
                 },
                 'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.spsl) {
                    return FormatFloat(data.spsl,
                        "###,###.00");
                }else{
                    return null;
                }
              },
              'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.spse) {
                    return FormatFloat(data.spse,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.jshj) {
                    return FormatFloat(data.jshj,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            }
        ]
    });
    
    //开票商品明细table
    var kpspmx_table1 = $('#mxTable1').DataTable({
        "searching": false,
        "serverSide": true,
        "sServerMethod": "POST",
        "processing": true,
        "bPaginate":false,
        "bLengthChange":false,
        "bSort":false,
        "bInfo": false,
        "scrollX": true,
        ajax: {
            "url": "fphc/getMx",
            data: function (d) {
               d.kplsh = $("#kplsh").val();
            }
        },
        "columns": [
            {"data": "spmxxh"},
            {"data": "spmc"},
            {"data": "spggxh"},
            {
              "data": function (data) {
                     if (data.spje) {
                         return FormatFloat(data.spje,
                             "###,###.00");
                     }else{
                         return null;
                     }
                 },
                 'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.spsl) {
                    return FormatFloat(data.spsl,
                        "###,###.00");
                }else{
                    return null;
                }
              },
              'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.spse) {
                    return FormatFloat(data.spse,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.jshj) {
                    return FormatFloat(data.jshj,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            }
        ]
    });
   
    
    kpspmx_table.on('draw.dt', function (e, settings, json) {
        var x = kpspmx_table, page = x.page.info().start; // 设置第几页
        /*kpspmx_table.column(0).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });*/
    });
    kpspmx_table1.on('draw.dt', function (e, settings, json) {
        var x = kpspmx_table1, page = x.page.info().start; // 设置第几页
        kpspmx_table1.column(0).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });
    });
    var t1;
    var loaddata=false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fphc/getKplsList',
            hcUrl: 'fphc/hc'
        },
        dataTable: function () {
            var _this = this;
            var t = $("#fpTable")
                .DataTable({
                    "searching": false,
                    "serverSide": true,
                    "sServerMethod": "POST",
                    "processing": true,
                    "bSort":true,
                    "scrollX": true,
                    ordering: false,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                            var bj=$("#bj").val();
                            
                            if(bj=="1"){
                            d.xfi = $('#s_xfsh').val();//销方税号
                            d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                            d.fphm = el.$s_fphm.val();   // search 发票号码
                            d.fpdm = el.$s_fpdm.val();   // 发票代码
                            d.gfmc = el.$s_gfmc.val();   //购方名称
                            d.ddh = el.$s_ddh.val();     //订单号
                            d.fplx = el.$s_fplx.val();     //发票类型
                            }if(bj=="2"){
                                   var csm =  $('#dxcsm').val();
                                   if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){ //购方名称
                                      d.gfmc = $('#dxcsz').val();
                                 }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){//订单号
                                      d.ddh = $('#dxcsz').val();
                                 }
                            }
                            d.loaddata=loaddata;
                        }
                    },
                    "columns": [
                        {
                            "orderable" : true,
                            "data" : null,
                            render : function(data, type, full, meta) {
                                return '<input type="checkbox" value="'
                                    + data.kplsh + '" name="chk"  id="chk"/>';
                            }
                        },
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                         {
                            "data": null,
                            "render": function (data) {
                                if(data.fpzldm=='12'){
                                    return '<a class="dzfpview" href="'
                                    + data.pdfurl
                                    + '" target="_blank">查看</a>'
                                }else{
                                    return '<a class="view"  target="_blank">查看</a>'
                                    
                                }
                            }
                        },        
                        {"data": "ddh"},
                        {"data": "kprq"},
                        {"data": "fpdm"},
                        {"data": "fphm"},
                        {"data": "gfmc"},
                        {"data": "fpzlmc"},
                        {
                            "data": function (data) {
                                if (data.hjje) {
                                    return FormatFloat(data.hjje,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },
                        {
                            "data":function (data) {
                                 if (data.hjse) {
                                     return FormatFloat(data.hjse,
                                         "###,###.00");
                                 }else{
                                     return null;
                                 }
                            },
                                
                            'sClass': 'right'
                        },
                        {
                            "data": function (data) {
                                if (data.jshj) {
                                    return FormatFloat(data.jshj,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                                
                            'sClass': 'right'
                        }]
                });
            var loaddata2=false;
            t1 = $('#ysTable')
            .DataTable({
                "searching": false,
                "serverSide": true,
                "sServerMethod": "POST",
                "processing": true,
                "bSort":true,
                "scrollX": true,
                ordering: false,
                "ajax": {
                    url: 'fphc/getKplsList1',
                    type: 'POST',
                    data: function (d) {
                        
                        var bz=$("#bz").val();
                        if(bz=="1"){
                            d.xfi = $('#s_xfsh').val();//销方税号
                            d.kprqq = el.$s_kprqq1.val(); // search 开票日期起
                            d.kprqz = el.$s_kprqz1.val(); // search 开票日期止
                            d.fphm = el.$s_fphm1.val();   // search 发票号码
                            d.fpdm = el.$s_fpdm1.val();   // 发票代码
                            d.gfmc = el.$s_gfmc1.val();   //购方名称
                            d.ddh = el.$s_ddh1.val();     //订单号
                            d.fplx = el.$s_fplx1.val();     //发票类型
                        }if(bz=="2"){
                             var csm =  $('#dxcsm1').val();
                             if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){ //购方名称
                              d.gfmc = $('#dxcsz1').val();
                             }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){//订单号
                              d.ddh = $('#dxcsz1').val();
                             }
                        }
                        d.loaddata2=loaddata2;
                    }
                },
                "columns": [
  
                    {"data":null}, 
                    {
                        "data": null,
                        "render": function (data) {
                            if(data.fpzldm=='12'){
                                return '<a class="dzfpview" href="'
                                + data.pdfurl
                                + '" target="_blank">查看</a>'
                            }else{
                                return '<a class="view1"  target="_blank">查看</a>'
                            }
                        }
                    },       
                    {"data": "ddh"},
                    {"data": "kprq"},
                    {"data": "fpdm"},
                    {"data": "fphm"},
                    {"data": "gfmc"},
                    {"data": "fpzlmc"},
                    {
                        "data": function (data) {
                            if (data.hjje) {
                                return FormatFloat(data.hjje,
                                    "###,###.00");
                            }else{
                                return null;
                            }
                        },
                        'sClass': 'right'
                    },
                    {
                        "data": function (data) {
                            if (data.hjse) {
                                return FormatFloat(data.hjse,
                                    "###,###.00");
                            }else{
                                return null;
                            }
                        },
                        'sClass': 'right'
                    },
                    {
                        "data": function (data) {
                            if (data.jshj) {
                                return FormatFloat(data.jshj,
                                    "###,###.00");
                            }else{
                                return null;
                            }
                        },
                        'sClass': 'right'
                    }],
                  
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            t1.on('draw.dt', function (e, settings, json) {
                var x = t1, page = x.page.info().start; // 设置第几页
                t1.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            $('#ysTable').on('click', 'tr', function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    t1.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected'); 
                }
                $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF"); 
                var data = t1.row($(this)).data();
                $("#kplsh").val(data.kplsh);
                kpspmx_table1.ajax.reload();
            });
            $('#check_all').change(function () {
                if ($('#check_all').prop('checked')) {
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                    });
                } else {
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
            });
            t.on('click', 'a.view', function () {
                 var data = t.row($(this).parents('tr')).data();
                 $("#doc-modal-fpyll").load('fphc/kpyl?kpsqhs='+data.djh);
                 $("#doc-modal-fpyl").modal("open");
            });
            t1.on('click', 'a.view1', function () {
             var data = t.row($(this).parents('tr')).data();
             $("#doc-modal-fpyll").load('kp/kpyl?kpsqhs='+data.djh);
                 $("#doc-modal-fpyl").modal("open");
           });
            t.on('click', 'input#chk', function () {
                var data = t.row($(this).parents('tr')).data();
                var kplshStr = [];
                $('#chk:checked').each(function(){    
                 kplshStr.push($(this).val()); 
                });
                if(kplshStr.length>1){
                    swal("不能批量红冲！");
                    $('input[type="checkbox"]').prop('checked', false);     
                      return;
                }
                $("#kplsh").val(data.kplsh);
                $("#fpzldm").val(data.fpzldm);
                kpspmx_table.ajax.reload();
            });
            
            $('#hc_search').click(function () {
                $("#bj").val("2");
                $('#xzxfq').attr("selected","selected");
                $('#xzlxq').attr("selected","selected");
                loaddata=true;
                t.ajax.reload();
            });
            
            $('#hc_search1').click(function () {
                $("#bj").val("1");
                $("#dxcsz").val("");
                loaddata=true;
                t.ajax.reload();
            });
            
            
            $('#hc_search2').click(function () {
                $("#bz").val("2");
                $('#xzxfq1').attr("selected","selected");
                $('#xzlxq1').attr("selected","selected");
                loaddata2=true;
                t1.ajax.reload();
             });
            $('#hc_search3').click(function () {
                $("#bz").val("1");
                $("#dxcsz1").val("");
                loaddata2=true;
                t1.ajax.reload();

            });
            //红冲操作
            $("#hc_hc").click(function () {
                 var kplshStr = [];
                 $('#chk:checked').each(function(){    
                     kplshStr.push($(this).val()); 
                 });
                if(kplshStr.length>1){
                    swal("不能批量红冲！");
                    $('input[type="checkbox"]').prop('checked', false);     
                       return;
                }else if(kplshStr.length==0){
                    swal("请选择一条记录！");
                    $('input[type="checkbox"]').prop('checked', false);     
                       return;
                }
                var kplsh = $('#kplsh').val();
                var fpzldm = $('#fpzldm').val();
                if(fpzldm=="01"){
                    $("#hztzdh").modal({"width": 520, "height": 300});
                    $("#js-submit").click(function(){
                        var hztzdh=$("#hztzdh_edit").val();
                            swal({
                                title: "确定要红冲该条数据吗？",
                                showCancelButton: true,
                                closeOnConfirm: false,
                                confirmButtonText: "确 定",
                                confirmButtonColor: "#ec6c62"
                            }, function() {
                                $('.confirm').attr('disabled',"disabled");
                                var xhStr = "";
                                var hcjeStr="";
                                var zhcje = 0;
                                var rows = $("#mxTable").find('tr');        
                                for(var j=1;j<rows.length;j++){
                                    xhStr +=rows[j].cells[0].innerHTML+",";
                                }
                                var els =document.getElementsByName("hcje");
                                for(var i=0;i<els.length;i++){
                                    var hcje = els[i].value;
                                    if(hcje==''){
                                        hcje = 0;
                                    }
                                    if(!hcje.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                                        swal("第"+(i+1)+"条明细的本次红冲金额格式错误，请重新填写！");
                                        return;
                                    }
                                    if(Number(hcje)>Number(delcommafy(rows[i+1].cells[4].innerHTML))){
                                        swal("第"+(i+1)+"条明细的本次红冲金额不能大于可红冲金额！");
                                        return;
                                    }
                                    zhcje+=hcje;
                                    hcjeStr += hcje+",";
                                }
                                if(zhcje==0){
                                    swal("红冲金额不能为0或空！");
                                    return;
                                } 
                                $.ajax({
                                    url:"fphc/hc",
                                    data:{"xhStr":xhStr,"hcjeStr":hcjeStr,"kplsh":kplsh,"hztzdh":hztzdh},
                                }).done(function(data) {
                                    if(data.success){
                                        $('.confirm').removeAttr('disabled');
                                        swal(data.msg);
                                        $("#kplsh").val("");
                                        t.ajax.reload();
                                    }else{
                                        swal(data.msg);
                                    }
                                }).error(function(data) {
                                    swal("程序出错，请联系开发人员！");
                                });
                            });
                    });
                }else{
                            swal({
                                title: "确定要红冲该条数据吗？",
                                showCancelButton: true,
                                closeOnConfirm: false,
                                confirmButtonText: "确 定",
                                confirmButtonColor: "#ec6c62"
                            }, function() {
                                $('.confirm').attr('disabled',"disabled");
                                var xhStr = "";
                                    var hcjeStr="";
                                    var zhcje = 0;
                                    var rows = $("#mxTable").find('tr');        
                                    for(var j=1;j<rows.length;j++){
                                        xhStr +=rows[j].cells[0].innerHTML+",";
                                    }
                                    var els =document.getElementsByName("hcje");
                                    for(var i=0;i<els.length;i++){
                                        var hcje = els[i].value;
                                        if(hcje==''){
                                            hcje = 0;
                                        }
                                        if(!hcje.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                                            swal("第"+(i+1)+"条明细的本次红冲金额格式错误，请重新填写！");
                                            return;
                                        }
                                        if(Number(hcje)>Number(delcommafy(rows[i+1].cells[4].innerHTML))){
                                            swal("第"+(i+1)+"条明细的本次红冲金额不能大于可红冲金额！");
                                            return;
                                        }
                                        zhcje+=hcje;
                                        hcjeStr += hcje+",";
                                    }
                                    if(zhcje==0){
                                        swal("红冲金额不能为0或空！");
                                        return;
                                    }  
                                $.ajax({
                                    url:"fphc/hc",
                                    data:{"xhStr":xhStr,"hcjeStr":hcjeStr,"kplsh":kplsh,"hztzdh":""},
                                }).done(function(data) {
                                    if(data.success){
                                        $('.confirm').removeAttr('disabled');
                                        swal(data.msg);
                                        $("#kplsh").val("");
                                        t.ajax.reload();
                                    }else{
                                        swal(data.msg);
                                    }
                                }).error(function(data) {
                                    swal("程序出错，请联系开发人员！");
                                });
                            });
                }
            });
            return t;
        },
        modalAction: function () {
            var _this = this;
            $("#hztzdh").on('closed.modal.amui', function () {
            });
            el.$jsClose.on('click', function () {
                $("#hztzdh").modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.modalAction(); // hidden action
        }
    };
    action.init();
    //定义鼠标样式
    $("#fpTable").css("cursor","pointer");
        function delcommafy(num){
               if((num+"").trim()==""){
                  return "";
               }
           num=num.replace(/,/gi,'');
           return num;
        }
});