/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $s_ddh: $('#s_ddh'), // search 订单号
        $s_gfmc:$('#s_gfmc'),  //关键字购方名称
        $s_kprqq: $('#s_kprqq'), // search 申请日期
        $s_kprqz: $('#s_kprqz'), // search 申请日期
        $jsSearch: $('.js-search'),
        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'kpsqcx/getItems'
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
                    type: 'GET',
                    data: function (d) {
                        d.ddh = el.$s_ddh.val();   // search 订单号
                        d.gfmc = el.$s_gfmc.val(); //购方名称                   
                        d.sqrqq = el.$s_kprqq.val(); // search 申请日期
                        d.sqrqz = el.$s_kprqz.val(); // search 申请日期                
                    }
                },
                "columns": [                    
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {"data": "ddh"},
                    {"data": "gfmc"},
                    {"data":"nsrsbh"},
                    {
                        "data": function (data) {
                            if (data.zje) {
                                return FormatFloat(data.zje, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },                   
                    {"data": "yx"},
                    {"data":"jylssj"}
                ]
            });

            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

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
                    alert('Error,请选择开始和结束时间!');
                    return false;
                }
                var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
                var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
                if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
                    if (dt1.getYear() == dt2.getYear()) {
                        if (dt1.getMonth() == dt2.getMonth()) {
                            if (dt1 - dt2 > 0) {
                                alert('开始日期大于结束日期,Error!');
                                return false;
                            }
                        } else {
                            // alert('月份不同,Error!');
                            alert('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    } else {
                        // alert('年份不同,Error!');
                        alert('Error,请选择同一个年月内的时间!');
                        return false;
                    }
                }
                e.preventDefault();
                _this.tableEx.ajax.reload();

            });
        },
        
       
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
        }
    };
    action.init();    
    
});