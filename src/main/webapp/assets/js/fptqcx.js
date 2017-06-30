/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
        $jsTable: $('#fptq_table'),
        $s_ddh: $('#s_ddh'), // search 订单号
        $s_gfmc:$('#s_gfmc'),  //关键字购方名称
        $s_kprqq: $('#s_rqq'), // search 开票日期
        $s_kprqz: $('#s_rqz'), // search 开票日期
        $s_tqsb: $('#s_tqsb'), // search 
        $s_jlly: $('#s_jlly'), // search 
        //$jsSearch: $('.js-search'),
        $jsLoading: $('.js-modal-loading')
    };
    var loaddata = false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fptqcx/getItems'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable({
            	"processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                "scrollX": false,
                "ajax": {
                    url: _this.config.getUrl,
                    type: 'GET',
                    data: function (d) {
                        d.ddh = el.$s_ddh.val();   // search 订单号
                        d.gfmc = el.$s_gfmc.val(); //购方名称                   
                        d.tqrqq = el.$s_kprqq.val(); // search 开票日期
                        d.tqrqz = el.$s_kprqz.val(); // search 开票日期
                        d.tqsb = el.$s_tqsb.val();
                        d.jlly = el.$s_jlly.val();
                        d.loaddata = loaddata;
                        var csm =  $('#dxcsm').val()
                        if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){ //购方名称
                      	  d.gfmc = $('#dxcsz').val()
                      }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){//订单号
                      	  d.ddh = $('#dxcsz').val()
                      }
                        
                        
                        
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
                    {
                        "data": function (data) {
                            if (data.jshj) {
                                return FormatFloat(data.jshj, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {"data":"tqsb"},
                    {"data":"jlly"},
                    {"data": "tqsj"}                               
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
            $("#kplscx_search1").on('click', function (e) {
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
                $("#dxcsz").val("");
                loaddata = true;
                _this.tableEx.ajax.reload();

            });
            
            $('#kplscx_search').click(function () {
             	$("#ycform").resetForm();
                loaddata = true;
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