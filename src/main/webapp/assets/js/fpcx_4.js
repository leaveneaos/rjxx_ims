/**
 * feel
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('#kpls_table'),
        //$jsTablemx: $('.js-table-mx'),
        $modalfpxx: $('#my-alert-edit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // form
        $jsExport : $('.js-export'),//导出
        $s_ddh: $('#s_ddh'), // search 订单号
        $s_lsh: $('#s_lsh'), // search 流水号
        $s_rqq: $('#s_rqq'), // search 开票日期
        $s_rqz: $('#s_rqz'),  // search 发票号码
        $xfsh: $('#s_xfsh'), // search 
        $gfmc: $('#s_gfmc'),// search 
        $ztbz: $('#s_ddzt'),// search
        $jsLoading: $('.js-modal-loading'),
        $checkAll : $('#select_all')
    };
    var mxarr = [];
    var index = 1;
    var loaddata=false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'ddcx/getJylsDd',
            getmxUrl: 'ddcx/getMx',
            getDHUrl: 'ddcx/getFp'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                "scrollX": true,
                "ajax": {
                    url: _this.config.getUrl,
                    type: 'post',
                    data: function (d) {
                        if($('#bj').val() =='1'){
                            d.xfsh = el.$xfsh.val();   // search 销方
                            d.gfmc = el.$gfmc.val();	// search 购方名称
                            d.ddh = el.$s_ddh.val();   // search 订单号
                            d.jylsh = el.$s_lsh.val();   // search 流水号
                            d.rqq = el.$s_rqq.val(); // search 开票日期
                            d.rqz = el.$s_rqz.val(); // search 开票日期
                            d.ztbz = el.$ztbz.val(); // search 开票日期
                        }else{
                            var csm =  $('#dxcsm').val();
                            //alert(csm);
                            if("gfmc"==csm){ //购方名称
                                d.gfmc = $('#dxcsz').val();
                            }else if("ddh"==csm){//订单号
                                d.ddh = $('#dxcsz').val();
                            }
                            d.rqq = $('#w_kprqq').val(); // search 开票日期
                            d.rqz = $('#w_kprqz').val(); // search 开票日期
                        }

                        d.loaddata=loaddata;
                    }
                },
                "columns": [
                    {
                        "orderable" : false,
                        "data" : null,
                        render : function(data, type, full, meta) {
                            return '<input type="checkbox" id ="checkboxID" value="'
                                + data.sqlsh + '" />';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    // {"data": "sqlsh"},
                    {"data": "jylsh"},
                    {"data": "ddh"},
                    {
                        "data": function (data) {
                            if (data.jshj) {
                                return FormatFloat(data.jshj, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {"data": "gfmc"},
                    {"data": "ddrq"},
                    {"data": "fpzlmc"},
                    // {"data":"tqm"},
                    {"data":function(data){
                    	var sjly = data.sjly;
                    	switch (sjly) {
                        case '0':
                        	sjly = '平台录入';
                            break;
                        case '1':
                        	sjly = '接口接入';
                            break;
                        case '2':
                        	sjly = '平台导入';
                            break;
                        case '3':
                            sjly = '钉钉录入';
                            break;
                        case '4':
                            sjly = '微信录入';
                            break;
                        case '5':
                            sjly = '支付宝录入';
                            break;
                        case '6':
                            sjly = '其他浏览器录入';
                            break;
                        }
                    	return sjly;
                    }},
                    {"data":"ztbzmc"}
                    /*{
                        "data": function (data) {
                            var zt = data.ztbz;
                            switch (zt) {
                                case '0':
                                    zt = '待提交';
                                    break;
                                case '1':
                                    zt = '已申请';
                                    break;
                                case '2':
                                    zt = '退回';
                                    break;
                                case '3':
                                    zt = '已处理';
                                    break;
                                case '4':
                                    zt = '删除';
                                    break;
                                case '5':
                                    zt = '部分处理';
                                    break;
                                case '6':
                                    zt = '待处理';
                                    break;
                                case '7':
                                    zt = '无效';
                                    break;  
                            }
                            // return "<a class = 'view'>" + zt + "</a>";
                            return zt ;
                        }
                    },*/
                    
                    /*  {
                     "data": null,
                     "render": function(data) {
                     return '<a class="view">查看</a>'
                     }
                     }*/
                ]
                // "columnDefs": [{"bVisible": false, "aTargets": [1]}],
            });

            t.on('draw.dt', function(e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function(cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });
            
            var jyspmx_table = $('#jyspmx_table').DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,

                "ajax": {
                    "url": "ddcx/getMx",
                    data: function (d) {
                        d.sqlsh = $("#djh").val();
                    }
                },

                "columns": [
                    {"data": "spmxxh"},
                    {"data": "spmc"},
                    {"data": "spggxh"},
                    {"data": "spdw"},
                    {"data": "sps"},
                    {"data": "spdj"},
                    {
                        "data": function (data) {
                            if (data.spje) {
                                return FormatFloat(data.spje, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {"data": "spsl"},
                    {
                        "data": function (data) {
                            if (data.spse) {
                                return FormatFloat(data.spse, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {
                        "data": function (data) {
                            if (data.jshj) {
                                return FormatFloat(data.jshj, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    }
                ]

            });
            $('#kpls_table tbody').on('dblclick', 'tr', function () {
                if ( $(this).find('td:eq(0) input').is(':checked')){
                    $(this).find('td:eq(0) input').prop('checked',false);
                }else {
                    $(this).find('td:eq(0) input').prop('checked',true);
                }
            });
             t.on( 'click', 'a.view', function () {
             var data = t.row( $(this).parents('tr')).data();
             // todo
             _this.setForm0(data);
             _this.fpdhAc(data.sqlsh);
             $("#djh").val(data.sqlsh);
             //$("#formid").val(data.djh);
             jyspmx_table.ajax.reload();
             el.$modalfpxx.modal({"width": 1000, "height": 550});

             });
            //发票导出
            el.$jsExport.on('click',function (e) {
                var bj = $('#bj').val();
                var sqlsh11 ='';
                t.column(0).nodes().each(//循环检测勾选复选框并取得sqlsh
                    function (cell,i){
                        var $checkbox = $(cell).find('input[type="checkbox"]');
                        if($checkbox.is(':checked')){
                            sqlsh11 += $checkbox.val()+ ',';
                        }
                    }
                );

                if($('#bj').val() =='2'){
                    var dt1 = new Date($("#w_kprqq").val().replace(/-/g, "/"));
                    var dt2 = new Date($("#w_kprqz").val().replace(/-/g, "/"));
                    if (($("#w_kprqq").val() && $("#w_kprqq").val())) {// 都不为空
                        if (dt1.getYear() == dt2.getYear()) {
                            if (dt1.getMonth() == dt2.getMonth()) {
                                if (dt1 - dt2 > 0) {
                                    swal('开始日期大于结束日期,Error!');
                                    return false;
                                }
                            } else {
                                swal('Error,选择日期不能跨月!');
                                return false;
                            }
                        } else {
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    }
                    $('#sqlsh1').val(sqlsh11);
                    $("#searchform").submit();
                }else{
                    var dt1 = new Date(el.$s_rqq.val().replace(/-/g, "/"));
                    var dt2 = new Date(el.$s_rqz.val().replace(/-/g, "/"));
                    if ((el.$s_rqq.val() && el.$s_rqz.val())) {// 都不为空
                        if (dt1.getYear() == dt2.getYear()) {
                            if (dt1.getMonth() == dt2.getMonth()) {
                                if (dt1 - dt2 > 0) {
                                    swal('开始日期大于结束日期!');
                                    return false;
                                }
                            } else {
                                swal('请选择同一个年月内的时间!');
                                return false;
                            }
                        } else {
                            swal('请选择同一个年月内的时间!');
                            return false;
                        }
                    }
                    $('#sqlsh').val(sqlsh11);
                    $("#ycform").submit();
                }
            });
            return t;
        },

        mx: function (djh) {
            var _this = this;
            $("#djh").val(djh);
            //$("#formid").val(data.djh);
            jyspmx_table.ajax.reload();
            
        },

        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            $("#kplscx_search1").on('click', function (e) {
                var dt1 = new Date(el.$s_rqq.val().replace(/-/g, "/"));
                var dt2 = new Date(el.$s_rqz.val().replace(/-/g, "/"));
                if ((el.$s_rqq.val() && el.$s_rqz.val())) {// 都不为空
                    if (dt1.getYear() == dt2.getYear()) {
                        if (dt1.getMonth() == dt2.getMonth()) {
                            if (dt1 - dt2 > 0) {
                                swal('开始日期大于结束日期!');
                                return false;
                            }
                        } else {
                            swal('请选择同一个年月内的时间!');
                            return false;
                        }
                    } else {
                        swal('请选择同一个年月内的时间!');
                        return false;
                    }
                }
                e.preventDefault();
                //$("#dxcsz").val("");
                $('#bj').val('1');
                loaddata=true;
                _this.tableEx.ajax.reload();

            });
            
            $('#kplscx_search').click(function () {
             	//$("#ycform").resetForm();
             	//alert($("#w_kprqq").val());
                if ((!$("#w_kprqq").val() && $("#w_kprqz").val())
                    || ($("#w_kprqq").val() && !$("#w_kprqz").val())) {
                    // $("#alertt").html('Error,请选择开始和结束时间!');
                    //            	$("#my-alert").modal('open');
                    swal('Error,请选择开始和结束时间!');
                    return false;
                }
                var dt1 = new Date($("#w_kprqq").val().replace(/-/g, "/"));
                var dt2 = new Date($("#w_kprqz").val().replace(/-/g, "/"));
                if (($("#w_kprqq").val() && $("#w_kprqq").val())) {// 都不为空
                    if (dt1.getYear() == dt2.getYear()) {
                        if (dt1.getMonth() == dt2.getMonth()) {
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
                    }
                }
                $('#bj').val('2');
                loaddata=true;
              	_this.tableEx.ajax.reload();
             });
            
        },
        /**
         * 导出按钮
         */
        /*exportAc: function () {
            //发票导出
            el.$jsExport.on('click',function (e) {
                var bj = $('#bj').val();
                var sqlsh11 ='';
                this.tableEx.column(0).nodes().each(//循环检测勾选复选框并取得kplsh
                    function (cell,i){
                        var $checkbox = $(cell).find('input[type="checkbox"]');
                        if($checkbox.is(':checked')){
                            sqlsh11 += $checkbox.val()+ ',';
                        }
                    }
                )

                if($('#bj').val() =='2'){
                    var dt1 = new Date($("#w_kprqq").val().replace(/-/g, "/"));
                    var dt2 = new Date($("#w_kprqz").val().replace(/-/g, "/"));
                    if (($("#w_kprqq").val() && $("#w_kprqq").val())) {// 都不为空
                        if (dt1.getYear() == dt2.getYear()) {
                            if (dt1.getMonth() == dt2.getMonth()) {
                                if (dt1 - dt2 > 0) {
                                    swal('开始日期大于结束日期,Error!');
                                    return false;
                                }
                            } else {
                                swal('Error,选择日期不能跨月!');
                                return false;
                            }
                        } else {
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    }
                    $("#searchform").submit();
                    $('#sqlsh1').val(sqlsh11);
                }else{
                    var dt1 = new Date(el.$s_rqq.val().replace(/-/g, "/"));
                    var dt2 = new Date(el.$s_rqz.val().replace(/-/g, "/"));
                    if ((el.$s_rqq.val() && el.$s_rqz.val())) {// 都不为空
                        if (dt1.getYear() == dt2.getYear()) {
                            if (dt1.getMonth() == dt2.getMonth()) {
                                if (dt1 - dt2 > 0) {
                                    swal('开始日期大于结束日期!');
                                    return false;
                                }
                            } else {
                                swal('请选择同一个年月内的时间!');
                                return false;
                            }
                        } else {
                            swal('请选择同一个年月内的时间!');
                            return false;
                        }
                    }
                    $("#ycform").submit();
                    $('#sqlsh').val(sqlsh11);
                }
            });
        },*/
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
        /**
         * 根据单据号来获得fphm、fpdm
         */
        fpdhAc: function (djh) {
            var _this = this;
            // todo
            $.ajax({
                url: "ddcx/getFp",
                data: {
                	'sqlsh' : djh
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        //$("input[type='hidden']").attr('type','text');
                        el.$jsForm0.find('[name="fpdm"]').val(data.fpdm);
                        el.$jsForm0.find('[name="fphm"]').val(data.fphm);
                        el.$jsForm0.find('[name="je"]').val((FormatFloat(data.hjje, "###,###.00")));
                        el.$jsForm0.find('[name="se"]').val((FormatFloat(data.hjse, "###,###.00")));
                    }else{
                        swal('您要查询的订单发票还未开具，请稍后再试！');
                    }
                },
                error: function () {
                    swal('后台错误,请稍后重试');
                }
            });
        },
        setForm0: function (data) {
            var _this = this,
                i;
            // todo set data
            // ajax get data   
            el.$jsForm0.find('[name="czlx"]').val(data.fpczlxmc);
            el.$jsForm0.find('[name="zl"]').val(data.fpzlmc);
            el.$jsForm0.find('[name="zt"]').val(data.clztmc);
            el.$jsForm0.find('[name="gfmc"]').val(data.gfmc);
            el.$jsForm0.find('[name="yh"]').val(data.gfyh);
            el.$jsForm0.find('[name="yhzh"]').val(data.gfyhzh);
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modalfpxx.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });

            // close modal
            el.$jsClose.on('click', function () {
                el.$modalfpxx.modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable     
            _this.search_ac();
            /*_this.exportAc();*/
            _this.modalAction(); // hidden action
            _this.checkAllAc();
        }
    };
    action.init();
});