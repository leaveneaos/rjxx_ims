/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // 红冲 form
        $s_fpdm: $('#s_fpdm'), // search 发票代码
        $s_fphm: $('#s_fphm'), // search 发票号码
        $s_jydh: $('#s_jydh'), // search 交易单号
        $s_yczl: $('#s_yczl'), // search 异常种类
        $jsSearch: $('.js-search'),
        $jsLoading: $('.js-modal-loading')
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'yccl/getLogList',
            generateUrl: '/generate/yccl',
            sentUrl: '/sent/yccl'
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
                        d.fpdm = el.$s_fpdm.val(); // search 发票代码
                        d.fphm = el.$s_fphm.val(); // search 发票号码
                        d.jydh = el.$s_jydh.val(); // search 交易单号
                        d.yczl = el.$s_yczl.val(); // search 异常种类
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    // {"data": "rzbh"},
                    {"data": "lrsj"},
                    {"data": "ddh"},
                    {"data": "gfmc"},
                    {"data": "jshj"},
                    //{"data": "yy"},
                    {"data": null, "defaultContent": "邮件发送失败"},
                    {
                        "data": null,
                        "defaultContent": '<a class="generates">重做</a> <a class="view">预览</a> <a class="sent">发送</a>'
                    }
                ]
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            // 红冲
            t.on('click', 'a.generates', function () {
                var data = t.row($(this).parents('tr')).data();
                // todo
                // ajax request
                alert('生成成功');
            });
            // 原发票明细
            t.on('click', 'a.view', function () {
                var data = t.row($(this).parents('tr')).data();
                // todo
                _this.setForm0(data);
                el.$modalHongchong.modal('open');
            });

            // 红冲发票明细
            t.on('click', 'a.sent', function () {
                var data = t.row($(this).parents('tr')).data();
                // todo
                // ajax request
                alert('发送成功');
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
        setForm0: function (data) {
            var _this = this,
                i;
            // todo set data
            // ajax get data

            el.$jsForm0.find('[name="hc_rzbh"]').val(data.rzbh);
            el.$jsForm0.find('[name="hc_sj"]').val(data.sj);
            el.$jsForm0.find('[name="hc_jyh"]').val(data.jyh);
            el.$jsForm0.find('[name="hc_fpdm"]').val(data.fpdm);
            el.$jsForm0.find('[name="hc_fphm"]').val(data.fphm);
            el.$jsForm0.find('[name="hc_ms"]').val(data.ms);
            el.$jsForm0.find('[name="id"]').val(data.id);
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modalHongchong.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$modalHongchong.modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.modalAction(); // hidden action
        }
    };
    action.init();

});