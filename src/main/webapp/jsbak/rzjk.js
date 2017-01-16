/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),


        $s_tjsjq: $('#s_tjsjq'), // search 统计时间起
        $s_tjsjz: $('#s_tjsjz'), // search 统计时间止
        $s_rzlx: $('#s_rzlx'), // search 日志类型


        $jsSearch: $('.js-search'),
        $jsSave: $('.js-save'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: '/json/rzjk.json'

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
                        d.tjsjq = el.$s_tjsjq.val(); // search 统计时间起
                        d.tjsjz = el.$s_tjsjz.val(); // search 统计时间止
                        d.rzlx = el.$s_rzlx.val(); // search 日志类型

                    }
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "rzbh" },
                    { "data": "sj" },
                    { "data": "yjh" },
                    { "data": "fpdm" },
                    { "data": "fphm" },
                    { "data": "ms" }
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