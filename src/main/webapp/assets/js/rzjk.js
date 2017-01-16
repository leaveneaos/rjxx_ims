$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $s_tjsjq: $('#s_tjsjq'), // search 统计时间起
        $s_tjsjz: $('#s_tjsjz'), // search 统计时间止
        $s_jyh: $('#s_jyh'), // search 日志类型
        $s_ddh: $('#s_ddh'), // search 日志类型
        $jsSearch: $('.js-search'),    
        $jsLoading: $('.js-modal-loading')
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'rzjk/getLogList'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable( {
                "processing": true,
                "serverSide": true,
                ordering:  false,
                searching: false,
                "ajax":{
                    url: _this.config.getUrl,
                    type: 'GET',
                    data: function (d) {
                        d.lrsjq = el.$s_tjsjq.val(); // search 统计时间起
                        d.lrsjz = el.$s_tjsjz.val(); // search 统计时间止
                        d.jylsh = el.$s_jyh.val(); // search 日志类型
                        d.ddh = el.$s_ddh.val(); // search 日志类型
                    }
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "id" },
                    { "data": "jylsh" },
                    { "data": "lrsj",'sClass':'width'},                    
                    { "data": "clztmc" },
                    { "data": "cljgmc" },
                    { "data": "ycms" }
                ]
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each( function (cell, i) {
                    cell.innerHTML = page + i + 1;
                } );
            });
            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {           	          
            	if ((!el.$s_tjsjq.val() && el.$s_tjsjz.val()) || (el.$s_tjsjq.val() && !el.$s_tjsjz.val())) {
            		alert('Error,请选择开始和结束时间!');
            		return false;
            	}
            	var dt1 = new Date(el.$s_tjsjq.val().replace(/-/g, "/"));
            	var dt2 = new Date(el.$s_tjsjz.val().replace(/-/g, "/"));
            	if ((el.$s_tjsjq.val() && el.$s_tjsjz.val())) {// 都不为空
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