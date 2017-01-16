/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable1: $('.js-table-1'),
        $jsTable2: $('.js-table-2'),

        $modalHongchong: $('#hongchong'),

        $jsAdd: $('.js-add'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),

        $jsConfigForm: $('.js-config-form'),
        $jsConfigSave: $('.js-config-save'),

        $jsForm0: $('.js-form-0'),

        $radios: $('[name="options"]'),     // table 2 search
        $searchRange: $('.search_range'), // search 时间段

        $jsMonth: $('.js-month'), // search 交易日期
        $jsSeason: $('.js-season'), // search 发票代码
        $jsMore: $('.js-more'), // search 发票号码

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx1: null, // cache dataTable 1
        tableEx2: null, // cache dataTable 2

        config: {
            getUrl1: '/json/yjfs1.json',
            getUrl2: '/json/yjfs2.json',
            saveConfig: '/config/save',         // 保存设置的url
            addUrl: '/add/fphk'

        },

        dataTable1: function () {
            var _this = this;

            var t = el.$jsTable1.DataTable( {
                "processing": true,
                "serverSide": true,
                ordering:  false,
                searching: false,

                "ajax":{
                    url: _this.config.getUrl1,
                    type: 'GET'
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "xm" },
                    { "data": "ssbm" },
                    { "data": "dwmc" },
                    { "data": "yjdz" },
                    { "data": "bz" },
                    {
                        "data": null,
                        "defaultContent": '<a class="action"> no action</a>'
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
            t.on( 'click', 'a.action', function () {
                var data = t.row( $(this).parents('tr') ).data();
                // todo
                alert(' no action');

            });


            return t;
        },
        dataTable2: function () {
            var _this = this;

            var t = el.$jsTable2.DataTable( {
                "processing": true,
                "serverSide": true,
                ordering:  false,
                searching: false,

                "ajax":{
                    url: _this.config.getUrl2,
                    type: 'GET',
                    data: function (d) {
                        d.type = el.$radios.filter(':checked').val(); // search 类型 0 1
                        d.range = el.$searchRange.val(); // search 时间段 0 1 2
                    }
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "sjr" },
                    { "data": "yjbt" },
                    { "data": "zy" },
                    { "data": "fssj" },
                    { "data": "yjjb" },
                    {
                        "data": null,
                        "defaultContent": '<a class="view—detail">查看明细</a> <a class="resent">再次发送</a>'
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

            // 查看明细
            t.on( 'click', 'a.view—detail', function () {
                var data = t.row( $(this).parents('tr') ).data();

                // todo

                alert('查看明细');

            });
            // 再次发送
            t.on( 'click', 'a.resent', function () {
                var data = t.row( $(this).parents('tr') ).data();

                // todo
                // ajax

                alert('再次发送');

            });

            return t;
        },

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

        resetForm: function () {
            el.$jsForm0[0].reset();

        },
        /**
         * save config action
         */
        configAction: function () {
            var _this = this;
            el.$jsConfigSave.on('click', function (e) {
                e.preventDefault();
                var data = el.$jsConfigForm.serialize();
                el.$jsLoading.modal('open');

                $.ajax({
                    url: _this.config.saveConfig,
                    data: data,
                    dataType: 'json',
                    method: ' POST',
                    success: function (data) {
                        if (data.statu === '1') {

                        } else {
                            alert('服务器内部错误');
                        }
                        el.$jsLoading.modal('close');

                    },
                    error: function () {
                        alert('保存错误');
                    }

                });


            });
        },
        /**
         * modal action
         */
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
        /**
         * table 1 add click action
         */
        addAction: function () {
            el.$jsAdd.on('click', function (e) {
                el.$modalHongchong.modal('open');

            });
        },
        /**
         * table 2 click action
         */
        table2Action: function () {
            var _this = this;
            el.$radios.on('change',function() {
                _this.tableEx2.ajax.reload();
            });

            el.$jsMonth.on('click', function (e) {
                e.preventDefault();
                el.$searchRange.val(0);
                _this.tableEx2.ajax.reload();
            });

            el.$jsSeason.on('click', function (e) {
                e.preventDefault();
                el.$searchRange.val(1);
                _this.tableEx2.ajax.reload();
            });

            el.$jsMore.on('click', function (e) {
                e.preventDefault();
                el.$searchRange.val(2);
                _this.tableEx2.ajax.reload();
            });

        },
        init: function () {
            var _this = this;

            _this.tableEx1 = _this.dataTable1(); // cache variable
            _this.tableEx2 = _this.dataTable2(); // cache variable

            _this.saveRow();

            _this.configAction();
            _this.modalAction(); // hidden action
            _this.addAction();
            _this.table2Action();

        }
    };

    action.init();

});