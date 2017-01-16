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

        $jsAdd: $('.js-add'),   // 新增 button

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        zTreeEx: null,

        config: {
            getUrl: '/json/yhgl.json',
            delUrl: '/del/yhgl',
            addUrl: '/update/yhql'

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
                   { "data": "yhzh" },
                   { "data": "xm" },
                   { "data": "bm" },
                   { "data": "zw" },
                   { "data": "lxdh" },
                   { "data": "yx"},
                   { "data": "mm"},
                   {
                       "data": null,
                       "defaultContent": '<a class="modify">修改</a> <a class="del">删除</a>'
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

            // 修改 设置权限
            t.on( 'click', 'a.modify', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setData(data); // set data
                el.$modal.modal('open');

            });

            // 删除
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
        setData:  function (d) {
            // todo
            // getPermission(data) // 获取这个用户的权限

            var _this = this,
                i = 0,
                node = null,
                permission = { // need real data
                    11: 0,
                    12: 1,

                    21: 1,
                    22: 1,
                    23: 0
                };
            // set zTree data
            for (i in permission) {
                _this.zTreeEx.checkNode(_this.zTreeEx.getNodeByParam('id', i),  (permission[i] ? true: false), false );
            }

            // set form data
            el.$jsForm.find('[name="yhzh"]').val(d.yhzh);
            el.$jsForm.find('[name="bm"]').val(d.bm);
            el.$jsForm.find('[name="lxdh"]').val(d.lxdh);
            el.$jsForm.find('[name="mm"]').val(d.mm);
            el.$jsForm.find('[name="xm"]').val(d.xm);
            el.$jsForm.find('[name="zw"]').val(d.zw);
            el.$jsForm.find('[name="yx"]').val(d.yx);

            el.$jsForm.find('[name="id"]').val(d.id);


        },
        saveRow: function () {
            var _this = this;
            el.$jsForm.validator({
                submit: function() {
                    var formValidity = this.isFormValid(),
                        zTreeChecked = null;
                    if (formValidity) {

                        el.$jsLoading.modal('toggle');  // show loading

                        alert('验证成功');
                        var data = el.$jsForm.serialize(); // get form data data
                        // get zTree data

                        zTreeChecked = _this.zTreeEx.getCheckedNodes(true);
                        // TODO  data
                        $.each(zTreeChecked, function (i, item) {
                            data = data + '&checkedid' + '=' +  item.id;
                        });

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

                _this.zTreeEx.checkAllNodes(false);
            });
        },
        /**
         * 新增 action
         */
        addAc: function () {
            el.$jsAdd.on('click', function (e) {
                e.preventDefault();

                el.$modal.modal('open');
            });
        },
        zTree: function () {
            var setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                }
            };

            // default tree
            var zNodes =[
                { id:1, pId:0, name:"系统权限", open: true},
                { id:11, pId:1, name:"管理员权限", open:true},
                { id:12, pId:1, name:"数据查询权限", open:true},

                { id:2, pId:0, name:"功能权限", checked: false, open:true},
                { id:21, pId:2, name:"开票权限"},
                { id:22, pId:2, name:"红冲权限" },
                { id:23, pId:2, name:"纸质发票换开"}

            ];

            return $.fn.zTree.init($("#treeDemo"), setting, zNodes);


        },
        checkNode: function (e) {

        },

        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable();
            _this.modalAction();
            _this.saveRow();
            _this.addAc(); // 新增

            _this.zTreeEx = _this.zTree();


        }
    };

    action.init();

});