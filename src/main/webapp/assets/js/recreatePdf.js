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
       // $s_fpzl:$('#s_fpzl'),
        $jsSearch: $('.js-search'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading'),
        $checkAll : $('#check_all')

    };
    var loaddata = false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'recreatePdf/getKplsList'
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
                        if(bz=='1'){
                            d.xfid = el.$s_xfid.val();
                            d.skpid = el.$s_skpid.val();
                            d.ddh = el.$s_ddh.val();   // search 订单号
                            d.gfmc = el.$s_gfmc.val();   // search 发票号码
                            d.kprqq = el.$s_kprqq.val(); // search 开票日期
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期
                          //  d.fpzl = el.$s_fpzl.val();
                            d.loaddata = loaddata;
                        }else{
                            var item = $('#s_mainkey').val();
                            if(item=='ddh'){
                                d.ddh = $('#searchValue').val();
                            }
                            if(item=='gfmc'){
                                d.gfmc = $('#searchValue').val();
                            }
                            d.loaddata = loaddata;
                        }
                    }
                },
                "columns": [
                    {
                        "orderable" : false,
                        "data" : null,
                        render : function(data, type, full, meta) {
                            return '<input type="checkbox" value="'
                                + data.kplsh + '" name="chk"  />';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {"data": "ddh"},
                    {"data": "fpzlmc"},
                    {"data": "gfmc"},
                    {"data": "fpdm"},
                    {"data": "fphm"},
                    {
                        "data": function (data) {
                            if (data.je) {
                                return FormatFloat(data.je, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {
                        "data": function (data) {
                            if (data.se) {
                                return FormatFloat(data.se, "###,###.00");
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
                ]
            });

            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });

            $('#jssearch').on('click',function(e){
                $('#searchbz').val("0");
                e.preventDefault();
                loaddata = true;
                t.ajax.reload();
            });
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
                t.ajax.reload();
            });
            //全选按钮
                el.$checkAll.on('change', function(e) {
                    var $this = $(this), check = null;
                    if ($(this).is(':checked')) {
                        t.column(0).nodes().each(
                            function(cell, i) {
                                $(cell).find('input[type="checkbox"]').prop(
                                    'checked', true);
                            });
                    } else {
                        t.column(0).nodes().each(
                            function(cell, i) {
                                $(cell).find('input[type="checkbox"]').prop(
                                    'checked', false);
                            });
                    }
                });
            $('#recreatePdf').click(function () {
                var djhArr = [];
                t.column(0).nodes().each(function (cell, i) {
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        var row = t.row(i).data().kplsh;
                        djhArr.push(row);
                    }
                });
                if (djhArr.length == 0) {
                    swal("请勾选需要开票的交易流水");
                    return;
                }
                swal({
                    title: "您是否需要重新生成PDF！",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "确 定",
                    confirmButtonColor: "#ec6c62"
                }, function() {
                    $.ajax({
                        url: "recreatePdf/recreate",
                        context: document.body,
                        data:{ "djhArr" : djhArr.join(",")},
                    }).done(function(data) {
                        if (data.success) {
                            swal({
                                title: "生成成功",
                                timer: 1500,
                                type: "success",
                                showConfirmButton: false
                            });
                            t.ajax.reload();
                        } else {
                            t.ajax.reload();
                            swal(data.msg);
                        }
                    });
                });
            });
            return t;
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
        }
    };
    action.init();
});