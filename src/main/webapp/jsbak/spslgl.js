/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $modal: $('#your-modal'),

        $jsSubmit: $('.js-submit'),
        $jsForm: $('.js-form'),

        $s_splb: $('#s_splb'), // search 商品类别
        $s_spmc: $('#s_spmc'), // search 商品名称

        $jsSearch: $('.js-search'),
        $jsAdd: $('.js-add'),
        $jsImport: $('.js-import'),
        $jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: '/json/spslgl.json',
            delUrl: '/del/spslql',              // 禁用
            addUrl: '/add/spslql'

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
                        d.splb = el.$s_splb.val(); // search 商品类别
                        d.spmc = el.$s_spmc.val(); // search 商品类别
                    }
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "splb" },
                    { "data": "spdm" },
                    { "data": "spmc" },
                    { "data": "sm" },
                    { "data": "sl" },
                    { "data": "ggxh" },
                    { "data": "jldw"},
                    { "data": "dj"},
                    {
                        "data": null,
                        "defaultContent": '<a class="modify">修改</a> <a class="del">禁用</a>'
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

            t.on( 'click', 'a.modify', function () {
                var data = t.row( $(this).parents('tr') ).data();

                _this.setForm(data);
                el.$modal.modal('open');

            });
            /**
             * 禁用
             */
            t.on( 'click', 'a.del', function () {
                var data = t.row( $(this).parents('tr') ).data(),
                    isAgree = false,
                // TODO 删除这条数据
                    isAgree = confirm('确认要禁用这条数据么');
                //t.ajax.reload(); // 重新加载数据

                if (isAgree) {

                    el.$jsLoading.modal('open');  // show loading

                    $.ajax({
                        url: _this.config.delUrl,
                        data: data.id,  // 禁用这条数据的 id
                        method: 'POST',
                        success: function () {
                            t.ajax.reload(); // 重新加载数据

                            el.$jsLoading.modal('close'); // close loading

                        },
                        error: function () {
                            alert('删除数据失败,请稍后重试');
                        }

                    });
                }

            } );

            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                e.preventDefault();

                _this.tableEx.ajax.reload(); // 重新加载数据

            });
        },
        add_ac: function () {
            var _this = this;
            el.$jsAdd.on('click', function (e) {
                e.preventDefault();
                el.$modal.modal('toggle');

            });
        },
        import_ac: function () {
            // TODO you code here
            el.$jsImport.on('click', function (e) {
                e.preventDefault();
                alert('导入'); // del this line

            });
        },
        export_ac: function () {
            el.$jsExport.on('click', function (e) {
                e.preventDefault();
                // TODO you code here
                alert('导出'); // del this line

            });
        },
        saveRow: function () {
            var _this = this;
            el.$jsForm.validator({
                submit: function() {
                    var formValidity = this.isFormValid();
                    if (formValidity) {

                        el.$jsLoading.modal('toggle');  // show loading

                        alert('验证成功');
                        var data = el.$jsForm.serialize(); // get form data data
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
        setForm: function (data) {
            var _this = this,
                i;
            for (i in data) {
                el.$jsForm.find('[name="' + i + '"]').val(data[i]);
            }
        },
        resetForm: function () {
            el.$jsForm[0].reset();
        },
        modalAction: function () {
            var _this = this;
           el.$modal.on('closed.modal.amui', function () {
               _this.resetForm();
           });
        },
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable();

            _this.search_ac();
            _this.add_ac();
            _this.import_ac();
            _this.export_ac();

            _this.saveRow();
            _this.modalAction(); // hidden action

        }
    };

    action.init();

});