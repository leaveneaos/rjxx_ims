/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),
        $modalExport: $('#export'),          // 导出 modal

        $jsExportSubmit: $('.js-export-submit'), // 导出 submit
        $jsSubmit: $('.js-submit'),     // 红冲提交
        $jsClose: $('.js-close'),       // 关闭 modal


        $jsForm0: $('.js-form-0'),     // 红冲 form
        $jsForm1: $('.js-form-1'),     // 导出 form

        $s_jyrqq: $('#s_jyrqq'), // search 交易日期起
        $s_jyrqz: $('#s_jyrqz'), // search 交易日期止
        $s_kprqq: $('#s_kprqq'), // search 开票日期起
        $s_kprqz: $('#s_kprqz'), // search 开票日期止

        $jsSearch: $('.js-search'),
        $jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: '/json/fphk.json',
            addUrl: '/add/fphk',
            exportUrl: '/export/fphk'

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
                        d.jyrqq = el.$s_jyrqq.val(); // search 交易日期起
                        d.jyrqz = el.$s_jyrqz.val(); // search 交易日期止
                        d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                        d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                    }
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "jyh" },
                    { "data": "jyrq" },
                    { "data": "fpdm" },
                    { "data": "fphm" },
                    { "data": "je" },
                    { "data": "se" },
                    { "data": "hkfphm"},
                    { "data": "hkfpdm"},
                    {
                        "data": null,
                        "defaultContent": '<a href="http://www.baidu.com" target="_blank">查看</a> <a class="huankai">换开</a>'
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
            t.on( 'click', 'a.huankai', function () {
                var data = t.row( $(this).parents('tr') ).data();

                _this.setForm0(data);
                el.$modalHongchong.modal('open');

            });


            return t;
        },
        /**
         * 导出
         */
        saveExport: function () {
            var _this = this;
            el.$jsExportSubmit.on('click', function () {
                if (!$('#e_dcsjq').val() || !$('#e_dcsjz').val()) {
                    alert('请输入开始和结束时间!');
                    return false;
                }
                var data = el.$jsForm1.serialize(); // get data

                // todo 下载链接
                var url = 'https://datatables.net/releases/DataTables-1.10.10.zip?' + data;
                window.open(url);
            });
        },
        /**
         * 换开
         */
        saveRow: function () {
            var _this = this;
            el.$jsForm0.validator({
                submit: function() {
                    var formValidity = this.isFormValid();
                    if (formValidity) {

                        el.$jsLoading.modal('toggle');  // show loading

                        alert('验证成功');
                        var data = el.$jsForm0.serialize(); // get form data data
                        // TODO save data to serve
                        $.ajax({
                            url: _this.config.addUrl,
                            data:  data,
                            method: 'POST',
                            success: function (data) {
                                if (data.statu === '1') {
                                    el.$modal.modal('close');   // close modal

                                    _this.tableEx.ajax.reload(); // reload table data

                                } else {
                                    alert('后台错误: 保存数据失败' + data.message);
                                }

                                el.$jsLoading.modal('close'); // close loading

                            },
                            error: function () {
                                alert('保存数据失败, 请重新登陆再试...!');
                            }
                        });

                        return false;
                    } else {
                        alert('验证失败');
                        return false;
                    }
                }
            });
        },
        setForm0: function (data) {
            var _this = this,
                i;
            // todo set data
            // ajax get data
            el.$jsForm0.find('input[name="id"]').val(data.id);
            el.$jsForm0.find('input[name="hc_hcfpdm"]').val('****');
            el.$jsForm0.find('input[name="hc_hcfphm"]').val('****');
            el.$jsForm0.find('input[name="hc_hkfpdm"]').val(data.hkfphm);
            el.$jsForm0.find('input[name="hc_hkfphm"]').val(data.hkfpdm);

        },
        resetForm: function () {
            el.$jsForm0[0].reset();

        },
        modalAction: function () {
            var _this = this;
            el.$modalHongchong.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            el.$modalExport.on('closed.modal.amui', function () {
                el.$jsForm1[0].reset();
            });

            // close modal
            el.$jsClose.on('click' , function () {
                el.$modalHongchong.modal('close');
                el.$modalExport.modal('close');
            });

            // 导出 modal
            el.$jsExport.on('click', function (e) {
                e.preventDefault();

                el.$modalExport.modal('open');

            });
        },
        searchAc: function () {
            var _this = this;
            el.$jsSearch.on('click' ,function (e) {
                e.preventDefault();
                _this.tableEx.ajax.reload();

            });
        },
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable

            _this.saveRow();
            _this.modalAction(); // hidden action
            _this.searchAc();
            _this.saveExport(); // export

        }
    };

    action.init();

});