$(function() {
    "use strict";
    var url;
    var el = {
        $jsTable : $('.js-table'),
        $jsModalOpem : $('.js-modal-open'),
        $modal : $('#your-modal'),
        $modal1 : $('#your-modal1'),
        $jsSubmit : $('.js-submit'),
        $jsSubmit1 : $('.js-submit1'),
        $jsSearch : $('#button1'),
        $jsClose : $('.js-close'),
        $jsClose1 : $('.js-close1'),
        $xfid : $('#xfid'),
        $jsForm : $('.js-form'),
        $jsForm1 : $('.js-form1'),
        $checkAll : $('#check_all'), // check all checkbox
        $jsdel : $('.js-sent'), // del all
        $jsLoading : $('.js-modal-loading')
    };

    var action = {
        tableEx : null, // cache dataTable
        config : {
            getUrl : 'pp/getpplist',
            delUrl : 'pp/del',
            addUrl : 'pp/save',
            editUrl : 'pp/update',
            deleteUrl : 'pp/delele'
        },
        dataTable : function() {
            var _this = this;

            var t = el.$jsTable.DataTable({
                "processing" : true,
                "serverSide" : true,
                ordering : false,
                searching : false,
                scrollX : true,
                "ajax" : {
                    url : _this.config.getUrl,
                    type : 'POST',
                    data : function(d) {
                            var tip = $('#tip').val();
                            var txt = $('#searchtxt').val();
                            if (tip == "1") {
                                d.ppmc = txt;
                            }
                    }
                },
                "columns" : [
                    {
                        "orderable" : false,
                        "data" : null,
                        // "defaultContent": '<input type="checkbox" />'
                        render : function(data, type, full, meta) {
                            return '<input type="checkbox" value="'
                                + data.id + '" />';
                        }
                    },
                    {
                        "orderable" : false,
                        "data" : null,
                        "defaultContent" : ""
                    },
                    {
                        "data": null,
                        "defaultContent": "<a class='modify' href='javascript:void(0)'>修改</a> "
                    },
                    {
                        "data" : "ppmc"
                    }, {
                        "data" : "ppdm"
                    }, {
                        "data" : "ppurl"
                    },  {
                        "data" : "aliMShortName"
                    },  {
                        "data" : "aliSubMShortName"
                    },{
                        "data" : "wechatLogoUrl"
                    },]
            });

            t.on('draw.dt', function(e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function(cell, i) {// 序号
                    cell.innerHTML = page + i + 1;
                });
            });
            //修改
            t.on('click', 'a.modify', function() {
                var data = t.row($(this).parents('tr')).data();
                el.$jsForm.find('[name="ppmc"]').val(data.ppmc);
                el.$jsForm.find('[name="ppdm"]').val(data.ppdm);
                el.$jsForm.find('[name="ppurl"]').val(data.ppurl);
                el.$jsForm.find('[name="aliMShortName"]').val(data.aliMShortName);
                el.$jsForm.find('[name="aliSubMShortName"]').val(data.aliSubMShortName);
                el.$jsForm.find('[name="wechatLogoUrl"]').val(data.wechatLogoUrl);
                url = _this.config.editUrl + "?id=" + data.id;
                $('#your-modal').modal({
                    "width" : 870,
                    "height" :250
                });
            });
            //删除
            t.on('click', 'a.del', function() {

                url = _this.config.delUrl;
                $('#my-confirm').modal(
                    {
                        relatedTarget : this,
                        onConfirm : function(options) {
                            var data = t.row($(this.relatedTarget).parents('tr')).data();
                            el.$jsLoading.modal('open');
                            $('.confirm').attr('disabled',"disabled");
                            $.ajax({
                                url : url,
                                data : {
                                    ids : data.id
                                },
                                type : 'POST',
                                success : function(data) {
                                    // todo 处理返回结果
                                    if (data.success) {
                                        $('.confirm').removeAttr('disabled');
                                        // $('#msg').html('删除成功');
                                        // $('#my-alert').modal('open');
                                        swal({
                                            title: "已成功删除",
                                            timer: 1500,
                                            type: "success",
                                            showConfirmButton: false
                                        });
                                        _this.tableEx.ajax.reload(); // reload
                                        // table
                                        // data
                                    } else {
                                        // $('#msg').html('删除失败,服务器错误' + data.msg);
                                        // $('#my-alert').modal('open');
                                        swal('删除失败,服务器错误' + data.msg);
                                    }
                                    el.$jsLoading.modal('close');

                                },
                                error : function() {
                                    // $('#msg').html('请求失败,请刷新后稍后重试!');
                                    // $('#my-alert').modal('open');
                                    swal('请求失败,请刷新后稍后重试!');
                                    el.$jsLoading.modal('close');
                                }
                            });
                        },
                        // closeOnConfirm: false,
                        onCancel : function() {

                        }
                    });
                // var data = t.row($(this).parents('tr')).data();
                // _this.del({ids: data.id});
            });

            var $importModal = $("#bulk-import-div");
            $("#close1").click(function() {
                $importModal.modal("close");
            });
            //录入
            $("#button2").click(function() {
                url = _this.config.addUrl;
                $('#your-modal').modal({
                    "width" : 870,
                    "height" :250
                });
            });

            return t;
        },
        modal : function() {
            var _this = this;
            el.$jsModalOpem.on('click', function(e) {
                e.preventDefault();
                url = _this.config.addUrl;
                $('#your-modal').modal({
                    "width" : 870,
                    "height" :250
                });

            });
        },
        /**
         * del删除税控盘信息 request
         *
         * @param data {
		 *            ids: '1,2,3,4' }
         */
        del : function(data) {
            var _this = this;
            swal({
                title: "提示",
                text: "您确定要删除这条数据吗？",
                type: "warning",
                showCancelButton: true,
                closeOnConfirm: false,
                confirmButtonText: "确 定",
                confirmButtonColor: "#ec6c62"
            }, function() {
                $('.confirm').attr('disabled',"disabled");
                $.ajax({
                    url : url,
                    data : {
                        ids : data.ids
                    },
                    type : 'POST',
                }).done(function(data) {
                    if (data.success) {
                        $('.confirm').removeAttr('disabled');
                        _this.tableEx.ajax.reload(); // reload table data
                        swal({
                            title: "已成功删除",
                            timer: 1500,
                            type: "success",
                            showConfirmButton: false
                        });
                    } else {
                        swal('删除失败,服务器错误' + data.msg);
                    }

                }).error(function(data) {
                    swal('请求失败,请刷新后稍后重试!', "error");
                });
            });

        },
        /**
         * check all action
         */
        checkAllAc : function() {
            var _this = this;
            el.$checkAll.on('change', function(e) {
                var $this = $(this), check = null;
                if ($(this).is(':checked')) {
                    _this.tableEx.column(0).nodes().each(
                        function(cell, i) {
                            $(cell).find('input[type="checkbox"]').prop(
                                'checked', true);
                        });
                } else {
                    _this.tableEx.column(0).nodes().each(
                        function(cell, i) {
                            $(cell).find('input[type="checkbox"]').prop(
                                'checked', false);
                        });
                }
            });
        },

        delAllAc : function() {
            var _this = this;
            el.$jsdel.on('click', function(e) {
                e.preventDefault();
                var data = '', row = '', $tr = null;
                _this.tableEx.column(0).nodes().each(function(cell, i) {

                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        row = _this.tableEx.row(i).data().id;
                        data += row + ',';
                    }

                });
                if (!data) {
                    // $('#msg').html("请选择要删除的税控盘");
                    // $('#my-alert').modal('open');
                    swal("请选择要删除的品牌！");
                    return;
                }
                data = data.substring(0, data.length - 1);
                url = _this.config.deleteUrl;
                _this.del({
                    ids : data
                });
                el.$checkAll.prop('checked', false);
            });

        },
        /**
         * 添加方法
         */
        saveRow : function() {
            var _this = this;
            el.$jsForm.validator({
                submit : function() {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle'); // show loading
                        // alert('验证成功');
                        var data = el.$jsForm.serialize(); // get form data
                        // data
                       $.ajax({
                            url : url,
                            data : data,
                            method : 'POST',
                            success : function(data) {
                                if (data.success) {
                                    el.$modal.modal('close'); // close modal
                                    // $('#msg').html(data.msg);
                                    // $('#my-alert').modal('open');
                                    swal(data.msg);
                                    _this.tableEx.ajax.reload(); // reload
                                    // table
                                    // data
                                } else if (data.failure) {
                                    // $('#msg').html(data.msg);
                                    // $('#my-alert').modal('open');
                                    swal(data.msg);
                                } else {
                                    // $('#msg').html('后台错误: 数据操作失败' + data.msg);
                                    // $('#my-alert').modal('open');
                                    swal('后台错误: 数据操作失败' + data.msg);
                                }// close loading

                                el.$jsLoading.modal('close');
                            },
                            error : function() {
                                // $('#msg').html('数据操作失败, 请重新登陆再试...!');
                                // $('#my-alert').modal('open');
                                swal('数据操作失败, 请重新登陆再试...!');
                                el.$jsLoading.modal('close');
                            }
                        });
                        return false;
                    } else {
                        // $('#msg').html('验证失败');
                        // $('#my-alert').modal('open');
                        swal('验证失败');
                        return false;
                    }
                }
            });
        },
        /**
         * 修改方法
         */
        editRow : function() {
            var _this = this;
            el.$jsForm1.validator({
                submit : function() {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle'); // show loading
                        var data = el.$jsForm1.serialize(); // get form data
                        // data
                        $.ajax({
                            url : _this.config.editUrl,
                            data : data,
                            method : 'POST',
                            success : function(data) {
                                if (data.success) {
                                    el.$modal1.modal('close'); // close modal
                                    // $('#msg').html(data.msg);
                                    // $('#my-alert').modal('open');
                                    swal(data.msg);
                                } else {
                                    // $('#msg').html('后台错误: 数据操作失败' + data.msg);
                                    // $('#my-alert').modal('open');
                                    swal('后台错误: 数据操作失败' + data.msg);

                                }
                                _this.tableEx.ajax.reload(); // reload table
                                // data
                                el.$jsLoading.modal('close'); // close loading

                            },
                            error : function() {
                                // $('#msg').html('数据操作失败, 请重新登陆再试...!');
                                // $('#my-alert').modal('open');
                                swal('数据操作失败, 请重新登陆再试...!');
                                el.$jsLoading.modal('close');
                            }
                        });

                        return false;
                    } else {
                        // $('#msg').html('验证失败,请注意格式！');
                        // $('#my-alert').modal('open');
                        swal('验证失败,请注意格式！');
                        return false;
                    }
                }
            });
        },
        /**
         * 查询
         */
        search_ac : function() {
            var _this = this;
            el.$jsSearch.on('click', function(e) {
                e.preventDefault();
                $('#bj').val('1');
                _this.tableEx.ajax.reload();
            });
        },
        /**
         * 添加model
         */
        resetForm : function() {
            el.$jsForm[0].reset();
        },
        modalAction : function() {
            var _this = this;
            el.$modal.on('closed.modal.amui', function() {
                _this.resetForm();
            });
            el.$jsClose.on('click', function() {
                el.$modal.modal('close');
            });
            el.$jsClose1.on('click', function() {
                el.$modal1.modal('close');
            });
        },
        /**
         * 修改model
         */
        resetForm1 : function() {
            el.$jsForm1[0].reset();
        },
        modalAction1 : function() {
            var _this = this;
            el.$modal1.on('closed.modal.amui', function() {
                _this.resetForm1();
            });
        },
        /**
         * 初始化
         */
        init : function() {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.modal();
            _this.modalAction();// hidden action
            _this.modalAction1();// hidden action
            _this.checkAllAc();
            _this.delAllAc();
            _this.saveRow();
            _this.editRow();
        }
    };
    action.init();

});