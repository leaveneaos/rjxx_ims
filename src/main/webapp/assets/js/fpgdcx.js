/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // form
        $s_xfid:$('#s_xfid'),
        $s_kprqq: $('#s_kprqq'), // search 开票日期
        $s_kprqz: $('#s_kprqz'), // search 开票日期
        $jsSearch: $('.js-search'),
        $jsCompass: $('.js-compass'),
        $jsLoading: $('.js-modal-loading'),
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpgdcx/getFPList'
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
                    	           
                    }
                },
                "columns": [                     
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {"data": "xfmc"}, 
                    {"data": "qsrq"},
                    {"data": "zzrq"},
                    {
                        "data": null,
                        "render": function (data) {
                            if(data.zt=='0'){
                                return '正在运行';
                            }else if(data.zt=='1'){
                            	return '已完成';
                            }else{
                            	return "";
                            }
                        }
                    },
                    {"data":"wjsl"},
                    {"data": "yhmc"},
                    {"data": "lrsj"},
                    {
                        "data": null,
                        "render": function (data) {
                            if(data.xzlj){
                                return '<a href='+data.xzlj+' target="_blank">下载</a>';
                            }else{
                            	return "";
                            }
                        }
                    }
                ]
            });
             t.on('draw.dt', function (e, settings, json) {
                var x = t,page = x.page.info().start; // 设置第几页
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
                e.preventDefault();
                _this.tableEx.ajax.reload();

            });
        },
        /**
         * 打包按钮
         */
        compassAc: function () {
            el.$jsCompass.on('click', function (e) {
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
                }else{
                	swal('Error,请录入开票日期起始终止时间');
                	return false;
                }
                swal("归档请求已发送，请稍后查询！");
                var xfid = el.$s_xfid.val();
                var kprqq = el.$s_kprqq.val();
                var kprqz = el.$s_kprqz.val();
                $.ajax({
                	url:'fpgdcx/compass',
                	data:{"xfid":xfid,"kprqq":kprqq,"kprqz":kprqz},
                	success:function(data){
                		if(!data.success){
                			swal(data.msg);
                		}
                	},
                	error:function(){
                		swal("程序出错");
                	}
                });
            });
        },
           
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.compassAc();
        }
    };
    action.init();    
    
});