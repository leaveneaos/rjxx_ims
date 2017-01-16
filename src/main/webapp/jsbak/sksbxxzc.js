/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
   "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $jsModalOpem: $('.js-modal-open'),
        $modal: $('#your-modal'),
        $jsSubmit: $('.js-submit'),
        $jsForm: $('.js-form'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: '/json/sksbxxzc.json',
            delUrl: '',
            addUrl: ''

        },

        dataTable: function () {
            var _this = this;

           var t = el.$jsTable.DataTable( {
               "processing": true,
               "serverSide": true,
               ordering:  false,
               searching: false,

               "ajax": _this.config.getUrl,
               //"columnDefs": [ {
               //    "targets": 1,
               //    "data": "skph",
               //    "render": function ( data, type, full, meta ) {
               //        return '<input value="'+data+'" />';
               //    }
               //} ],
               "columns": [
                   {
                       "orderable":      false,
                       "data": null,
                       "defaultContent": ""
                   },
                   { "data": "skph" },
                   { "data": "xfmc" },
                   { "data": "fzsz" },
                   { "data": "bz"},
                   {
                       "data": null,
                       "defaultContent": "<a class='modify'>修改</a> <a class='del'>删除</a>"
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

                el.$jsForm.find('[name="skph"]').val(data.skph);
                el.$jsForm.find('[name="xfmc"]').val(data.xfmc);
                el.$jsForm.find('[name="fzsz"]').val(data.fzsz);
                el.$jsForm.find('[name="bz"]').val(data.bz);
                el.$jsForm.find('[name="id"]').val(data.id);


                el.$modal.modal('open');

            } );

            t.on( 'click', 'a.del', function () {
                var data = t.row( $(this).parents('tr') ).data(),
                    isAgree = false,
                // TODO 删除这条数据
                isAgree = confirm('确认要删除这条数据么');
                //t.ajax.reload(); // 重新加载数据

                if (isAgree) {

                    el.$jsLoading.modal('toggle');  // show loading

                    $.ajax({
                        url: _this.config.delUrl,
                        data: data.id,
                        method: 'post',
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
        modal: function () {
            el.$jsModalOpem.on('click', function (e) {
                e.preventDefault();
                el.$modal.modal('toggle');

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
            _this.modal();
            _this.modalAction();
            _this.saveRow();

        }
    };

    action.init();

});