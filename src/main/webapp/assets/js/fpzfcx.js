$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $modalHuankai: $('#huankai'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // 红冲 form
        $s_xfid :$('#s_xfid'),
        $s_skpid:$('#s_skpid'),
        $s_ddh: $('#s_ddh'), // search 订单号
        $s_gfmc: $('#s_gfmc'), // search 发票号码
        $s_kprqq: $('#s_kprqq'), // search 开票日期
        $s_kprqz: $('#s_kprqz'), // search 开票日期
        $s_fpzl:$('#s_fpzl'),
        $jsSearch: $('.js-search'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading')
    };
    var loaddata = false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpzfcx/getKplsList'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                "ajax": {
                    url: _this.config.getUrl,
                    type: 'GET',
                    data: function (d) {
                        var bz = $('#searchbz').val();
                        d.loaddata = loaddata;
                        if(bz=='1'){
                            d.xfid = el.$s_xfid.val();
                            d.skpid = el.$s_skpid.val();
                            d.ddh = el.$s_ddh.val();   // search 订单号
                            d.gfmc = el.$s_gfmc.val();   // search 发票号码
                            d.kprqq = el.$s_kprqq.val(); // search 开票日期
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期
                            d.fpzl = el.$s_fpzl.val();
                            d.fphm = $('#s_fphm').val();
                        }else{
                            var item = $('#s_mainkey').val();
                            if(item=='ddh'){
                                d.ddh = $('#searchValue').val();
                            }
                            if(item=='gfmc'){
                                d.gfmc = $('#searchValue').val();
                            }if(item== 'fphm'){
                                d.fphm = $('#searchValue').val();
                            }

                            d.kprqq =$("#w_kprqq").val();
                            d.kprqz = $("#w_kprqz").val(); // search 开票日期
                        }
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {
                        "data": null,
                        "defaultContent": '<a class="view">查看</a>'
                    },
                    {"data": "ddh"},
                    {"data": "fpzlmc"},
                    {"data": "gfmc"},
                    {"data": "fpdm"},
                    {"data": "fphm"},
                    {
                        "data": function (data) {
                            if (data.hjje) {
                                return FormatFloat(data.hjje, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {
                        "data": function (data) {
                            if (data.hjse) {
                                return FormatFloat(data.hjse, "###,###.00");
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
                    },                   
                    {"data": "kprq"},
                    {"data": "kpr"},
                    
                ]
            });

            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });

            // 
            t.on('click', 'a.generates', function () {
                var data = t.row($(this).parents('tr')).data();
                swal('生成成功');

            });

            // 原发票明细
            t.on('click', 'a.view', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setForm0(data);
                el.$modalHuankai.modal('open');

            });

            // 
            t.on('click', 'a.sent', function () {
                var data = t.row($(this).parents('tr')).data();
                swal('发送成功');
            });

            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                if ((!el.$s_kprqq.val() && el.$s_kprqz.val()) || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
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
                            // swal('月份不同,Error!');
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    } else {
                        // swal('年份不同,Error!');
                        swal('Error,请选择同一个年月内的时间!');
                        return false;
                    }
                }
                $('#searchbz').val("1");
                e.preventDefault();
                loaddata = true;
                _this.tableEx.ajax.reload();
            });
        },
        
        find_mv:function(){
            var _this = this;
            $('#jssearch').on('click',function(e){
                $('#searchbz').val("0");
                e.preventDefault();
                loaddata = true;
                if ((!$("#w_kprqq").val() && $("#w_kprqz").val())
                    || ($("#w_kprqq").val() && !$("#w_kprqz").val())) {
                    // $("#alertt").html('Error,请选择开始和结束时间!');
                    //            	$("#my-alert").modal('open');
                    swal('Error,请选择开始和结束时间!');
                    return false;
                }
                var dt1 = new Date($("#w_kprqq").val().replace(/-/g, "/"));
                var dt2 = new Date($("#w_kprqz").val().replace(/-/g, "/"));
                if (($("#w_kprqq").val() && $("#w_kprqz").val())) {// 都不为空
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
                _this.tableEx.ajax.reload();
            })
        },
        /**
         * 导出按钮
         */
        exportAc: function () {
            el.$jsExport.on('click', function (e) {
                // todo
                swal('导出成功');
            });
        },
        setForm0: function (data) {
            var _this = this,
                i;
            // todo set data
            // ajax get data
            el.$jsForm0.find('[name="hc_fpdm"]').val(data.fpdm);
            el.$jsForm0.find('[name="hc_fphm"]').val(data.fphm);
            el.$jsForm0.find('[name="hc_je"]').val(FormatFloat(data.hjje, "###,###.00"));
            el.$jsForm0.find('[name="hc_se"]').val(FormatFloat(data.hjse, "###,###.00"));
            el.$jsForm0.find('[name="hc_gfmc"]').val(data.gfmc);
            /*el.$jsForm0.find('[name="hc_yfpdm"]').val(data.yfpdm);
            el.$jsForm0.find('[name="hc_yfphm"]').val(data.yfphm);*/
            el.$jsForm0.find('[name="id"]').val(data.id);
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modalHuankai.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$modalHuankai.modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.exportAc();
            _this.modalAction(); // hidden action
            _this.find_mv();
        }
    };
    action.init();
});