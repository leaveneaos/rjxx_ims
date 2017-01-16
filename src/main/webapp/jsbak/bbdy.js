/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $jsForm: $('.js-search-form'),

        $jsSearch: $('.js-search'),
        $jsSave: $('.js-save'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: '/json/bbdy.json',
            saveUrl: '/save/bbdy'

        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable.DataTable( {
                "processing": true,
                "serverSide": true,
                ordering:  false,
                searching: false,
                paging: false,

                "ajax":{
                    url: _this.config.getUrl,
                    type: 'GET'
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "bbmc" },
                    { "data": "bbms" },
                    {
                        "data": function (row, type, val, meta) {
                            var data = [];
                            data.push('<input type="hidden" name="id" value="' + row.id + '" />');
                            data.push('<label class="am-checkbox-inline">');
                                data.push(' <input type="checkbox" name="' + 'email' + '" value="' + row.email + '" ' +  ((row.email - 0) ?  "checked" : "" ) +' >');
                                data.push('邮件订阅');
                            data.push('</label>');

                            data.push('<label class="am-checkbox-inline">');
                                data.push(' <input type="checkbox" name="' + 'text'  + '" value="' + row.text + '" ' +  ((row.text - 0) ?  "checked" : "" ) +' >');
                                data.push('短信订阅');
                            data.push('</label>');

                            return data.join('');

                        }
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
        saveAc: function () {
            var _this = this;

            el.$jsSave.on('click', function (e) {
                var data = _this.getData();
                e.preventDefault();

                el.$jsLoading.modal('open');

                $.ajax({
                    url: _this.config.saveUrl,
                    data: data,
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        if (data.statu === '0') {

                            alert('保存成功');
                        } else {
                            alert('保存数据失败');
                        }
                        el.$jsLoading.modal('close');
                    },
                    error: function () {
                        alert('保存数据失败,请稍后重试');
                    }
                });
            });
        },
        getData: function () {
            var _this = this,
                data = {},
                tem,
                res;

            _this.tableEx.column(3).nodes().each(function (cell , i) {
                tem = $(cell);
                data[tem.find('input[name="id"]').val()] = "email=" + (tem.find('input[name="email"]').is(':checked') ? 1 : 0) + '&text=' + (tem.find('input[name="text"]').is(':checked') ? 1 : 0);
            });

            data.sjh = el.$jsForm.find('input[name="sjh"]').val();
            data.yx = el.$jsForm.find('input[name="yx"]').val();

            return data;
        },
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable

            _this.saveAc();

        }
    };

    action.init();

});