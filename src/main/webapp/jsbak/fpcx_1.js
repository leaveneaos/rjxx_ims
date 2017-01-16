/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),


        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),


        $jsForm0: $('.js-form-0'),     // 红冲 form

        $s_ddh: $('#s_ddh'), // search 订单号
        $s_fphm: $('#s_fphm'), // search 发票号码
        $s_kprq: $('#s_kprq'), // search 开票日期


        $jsSearch: $('.js-search'),
        $jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: '/fpcx/getFpcxList'

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
                    type: 'POST',
                    data: function (d) {
                        d.ddh = el.$s_ddh.val();   // search 订单号
                        d.fphm = el.$s_fphm.val();   // search 发票号码
                        d.kprq = el.$s_kprq.val(); // search 开票日期

                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "fpdm" },
                    { "data": "fphm" },
                    { "data": "je" },
                    { "data": "se" },
                    { "data": "jshj" },
                    { "data": "spfmc" },
                    { "data": "sh" },
                    {
                        "data": null,
                        "defaultContent": '<a class="" href="http://www.baidu.com" target="_blank" >查看</a>'
                    }
                ]
            });

            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each( function (cell, i) {
                    cell.innerHTML = page + i + 1;
                } );

            });

            // 红冲
            t.on( 'click', 'a.generates', function () {
                var data = t.row( $(this).parents('tr') ).data();

                // todo
                // ajax request

                alert('生成成功');

            });

            // 原发票明细
            t.on( 'click', 'a.view', function () {
                var data = t.row( $(this).parents('tr') ).data();
                // todo
                _this.setForm0(data);

                el.$modalHongchong.modal('open');

            });

            // 红冲发票明细
            t.on( 'click', 'a.sent', function () {
                var data = t.row( $(this).parents('tr') ).data();
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
        /**
         * 导出按钮
         */
        exportAc: function () {
            el.$jsExport.on('click', function (e) {
                // todo
                alert('导出成功');
            });
        },
        setForm0: function (data) {
            var _this = this,
                i;
            // todo set data
            // ajax get data

            el.$jsForm0.find('[name="hc_fpdm"]').val(data.fpdm);
            el.$jsForm0.find('[name="hc_fphm"]').val(data.fphm);
            el.$jsForm0.find('[name="hc_je"]').val(data.je);
            el.$jsForm0.find('[name="hc_se"]').val(data.se);
            el.$jsForm0.find('[name="hc_skfmc"]').val(data.spfmc);
            el.$jsForm0.find('[name="hc_sh"]').val(data.sh);
            el.$jsForm0.find('[name="hc_yh"]').val(data.yh);
            el.$jsForm0.find('[name="hc_yhzh"]').val(data.yhzh);


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
            el.$jsClose.on('click' , function () {
                el.$modalHongchong.modal('close');
            });
        },
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable

            _this.search_ac();
            _this.exportAc();
            _this.modalAction(); // hidden action


        }
    };

    action.init();

});