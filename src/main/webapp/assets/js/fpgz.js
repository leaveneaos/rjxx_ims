/**
 * feel
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $modalhuankai: $('#huankai'),
        $modalExport: $('#export'), // 导出 modal

        $jsExportSubmit: $('.js-export-submit'), // 导出 submit
        $jsSubmit: $('.js-submit'), // 换开提交
        $jsClose: $('.js-close'), // 关闭 modal

        $jsForm0: $('.js-form-0'), // 换开 form
        $jsForm1: $('.js-form-1'), // 导出 form
        $s_kprqq: $('#s_kprqq'), // search 开票日期起
        $s_kprqz: $('#s_kprqz'), // search 开票日期止
        $s_gfmc: $('#s_gfmc'), // search 购方名称
        $s_fpdm: $('#s_fpdm'), // search
        $s_ddh: $('#s_ddh'), // search
        $s_fphm: $('#s_fphm'), // search

        $jsSearch: $('.js-search'),
        $jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fphk/getKplsList',
            hkUrl: 'fphk/ykfphk',
            exportUrl: '/export/fphk'

        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,

                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {

                            d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                            d.fphm = el.$s_fphm.val(); // search 发票号码
                            d.fpdm = el.$s_fpdm.val();
                            d.gfmc = el.$s_gfmc.val(); // search 购方名称
                            d.ddh = el.$s_ddh.val();
                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {"data": "ddh"},
                        {"data": "kprq"},
                        {"data": "gfmc"},
                        {"data": "fpdm"},
                        {"data": "fphm"},
                        {
                            "data": function (data) {
                                if (data.hjje) {
                                    return FormatFloat(data.hjje,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },
                        {
                            "data": function (data) {
                                if (data.hjse) {
                                    return FormatFloat(data.hjse,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },
                        {
                            "data": function (data) {
                                if (data.jshj) {
                                    return FormatFloat(data.jshj,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },

                        {
                            "data": null,
                            "render": function (data) {
                            		return '<a href="'+ data.pdfurl+'" target="_blank">查看</a> <a class="huankai">换开</a>';                
                            }
                        }]
                });

            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });

            // 换开
            t.on('click', 'a.huankai', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setForm0(data);
                el.$modalhuankai.modal('open');

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
                submit: function () {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle'); // show loading
                        // alert('验证成功');
                        var data = el.$jsForm0.serialize(); // get form data
                        $.ajax({
                            url: _this.config.hkUrl,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.success) {
                                    el.$modalhuankai.modal('close'); // close
                                    alert(data.msg);

                                } else {
                                    alert('后台错误: 换开操作失败' + data.msg);
                                }
                                _this.tableEx.ajax.reload(); // reload table
                                el.$jsLoading.modal('close'); // close loading

                            },
                            error: function () {
                                alert('换开操作失败, 请重新登陆再试...!');
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
            var _this = this, i;
            el.$jsForm0.find('input[name="yfpdm"]').val(data.fpdm);
            el.$jsForm0.find('input[name="yfphm"]').val(data.fphm);
            el.$jsForm0.find('input[name="kpje"]').val(FormatFloat(data.jshj, "###,###.00"));
            el.$jsForm0.find('input[name="djh"]').val(data.djh);
            el.$jsForm0.find('input[name="kplsh"]').val(data.kplsh);
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modalhuankai.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            el.$modalExport.on('closed.modal.amui', function () {
                el.$jsForm1[0].reset();
            });

            // close modal
            el.$jsClose.on('click', function () {
                el.$modalhuankai.modal('close');
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
            el.$jsSearch.on('click', function (e) {

                if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
                    || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
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

            _this.saveRow();
            _this.modalAction(); // hidden action
            _this.searchAc();
            _this.saveExport(); // export

        }
    };
    action.init();

});