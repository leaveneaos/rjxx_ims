$(function () {
    "use strict";
    var url;
    var el = {
        $jsTable: $('.js-table'),
        $modal2: $('#import'),
        $jsForm2: $('.js-form2'),//导入

        $modal: $('#your-modal'),
        $modal1: $('#your-modal1'),

        $jsSubmit: $('.js-submit'),
        $jsForm: $('.js-form'),
        $jsSubmit1: $('.js-submit1'),
        $jsForm1: $('.js-form1'),
        

        $js_close: $('.js-close'),
        $js_close1: $('.js-close1'),

        $s_spdm: $('#s_spdm'), // search 商品类别
        $s_spmc: $('#s_spmc'), // search 商品名称

        $jsSearch: $('.js-search'),
        $jsAdd: $('.js-add'),
        $jsDel: $('del'),
        $jsImport: $('.js-import'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading')
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'spslgl/getSplist',
            delUrl: 'spslgl/delete',              // 禁用
            addUrl: 'spslgl/add',
            editUrl: 'spslgl/update',
            //getSlUrl:'spslgl/getSl',
            importUrl: 'spslgl/importFile'
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
                    type: 'POST',
                    data: function (d) {
                        d.spdm = el.$s_spdm.val(); // search 商品类别
                        d.spmc = el.$s_spmc.val(); // search 商品名称
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "id" },
                    {"data": "spdm"},
                    {"data": "spmc"},
                    {"data": "sl", 'sClass': 'right'},
                    {"data": "spggxh"},
                    {"data": "spdw"},
                    {
                        'data': function (data) {
                            if (data.spdj) {
                                return FormatFloat(data.spdj, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {"data": "spbm"},
                    {
                        "data": null,
                        "defaultContent": '<a class="modify">修改</a> <a class="del">删除</a>'
                    }
                ]
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

                $('#tbl tr').find('td:eq(1)').hide();
            });

            t.on('click', 'a.modify', function () {
                var data = t.row($(this).parents('tr')).data();
                url = _this.config.editUrl + "?id=" + data.id;
                _this.setForm(data);
                el.$modal.modal('open');
            });
            var $importModal = $("#bulk-import-div");
	        $("#close").click(function () {
	        	var _this = this;
	        	$('#your-modal').modal('close');
	        });
	        $("#close1").click(function () {
	        	var _this = this;
	        	$('#your-modal1').modal('close');
	        });
	        $("#close2").click(function () {
	        	var _this = this;
	        	$('#bulk-import-div').modal('close');
	        });
          //导入excel
	        $("#btnImport").click(function () {
	            var filename = $("#importFile").val();
	            if (filename == null || filename == "") {
	                alert("请选择要导入的文件");
	                return;
	            }
	            var pos = filename.lastIndexOf(".");
	            if (pos == -1) {
	                alert("导入的文件必须是excel文件");
	                return;
	            }
	            var extName = filename.substring(pos + 1);
	            if ("xls" != extName && "xlsx" != extName) {
	                alert("导入的文件必须是excel文件");
	                return;
	            }
	            $("#btnImport").attr("disabled", true);
				$('.js-modal-loading').modal('toggle'); // show loading
				// alert('验证成功');
				var options = {
		                success: function (res) {
		                    if (res.success) {
		                        $("#btnImport").attr("disabled", false);
		                        $('.js-modal-loading').modal('close');
		                        var count = res.count;
		                        alert("导入成功，共导入" + count + "条数据");
		                        window.location.reload();
		                    } else {
		                        $("#btnImport").attr("disabled", false);
		                        $('.js-modal-loading').modal('close');
		                        alert(res.message);
		                    }
		                }
		            };
		            $("#importExcelForm").ajaxSubmit(options);
							
	        });
            
            /**
             * 禁用
             */
            t.on('click', 'a.del', function () {
                var data = t.row($(this).parents('tr')).data(),
                    isAgree = false,
                // TODO 删除这条数据
                    isAgree = confirm('确认要删除这条数据么');
                if (isAgree) {
                    el.$jsLoading.modal('open');  // show loading
                    _this.bear({id: data.id});
                }
            });
            return t;
        },
        /**
         * 禁用的方法
         */
        bear: function (data) {
            var _this = this;
            $.ajax({
                url: _this.config.delUrl,
                data: data,  // 禁用这条数据的 id
                method: 'POST',
                //context: null,
                success: function (data) {
                    if (data.failure) {
                        alert(data.msg);
                    } else if (data.success){
                    	alert('删除成功');
                        _this.tableEx.ajax.reload(); // 重新加载数据
                    }
                    el.$jsLoading.modal('close'); // close loading
                },
                error: function () {
                    alert('删除数据失败,请稍后重试');
                }
            });
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
                var smdm = $("#smid1").val();
                var text = $("#smid1").find("option[value=" + smdm + "]").text();
                var pos = text.indexOf("(");
                var sl = text.substring(pos + 1, text.length - 1);
                el.$modal1.modal('toggle');
            });
        },
        import_ac: function () {
            // TODO you code here
            el.$jsImport.on('click', function (e) {
                el.$modal2.modal('open');
                e.preventDefault();
                //alert('导入'); // del this line
            });
        },
        import: function () {
            var _this = this;
            el.$jsForm2.validator({
                submit: function () {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle');  // show loading
                        //alert('验证成功');
                        var data = el.$jsForm2.serialize(); // get form data data
                        // TODO save data to serve
                        $.ajax({
                            url: _this.config.importUrl,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.success) {
                                    el.$modal2.modal('close');   // close modal
                                    alert(data.msg);
                                } else {
                                    alert('后台错误: 导入数据失败' + data.msg);
                                }
                                _this.tableEx.ajax.reload(); // 重新加载数据
                                el.$jsLoading.modal('close'); // close loading

                            },
                            error: function () {
                                alert('导入数据失败, 请重新登陆再试...!');
                            }

                        });
                        return false;
                    } else {
                        alert('验证失败,请选择文件！');
                        return false;
                    }
                }
            });
        },
        export_ac: function () {
            el.$jsExport.on('click', function (e) {
                e.preventDefault();
                // TODO you code here
                alert('导出'); // del this line
            });
        },
        /**
         * 添加商品
         */
        addRow: function () {
            var _this = this;
            // $("#sl1").val($("#smmc1").val());
            el.$jsForm1.validator({
                submit: function () {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle');  // show loading
                        //alert('验证成功');                        
                        var data = el.$jsForm1.serialize(); // get form data data
                        // TODO save data to serve
                        $.ajax({
                            url: _this.config.addUrl,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.success) {
                                    el.$modal1.modal('close');   // close modal
                                    alert(data.msg);
                                    _this.tableEx.ajax.reload(); // 重新加载数据
                                }else if(data.failure){
                                	alert(data.msg);
                                } else {
                                    alert(data.msg);
                                }
                                el.$jsLoading.modal('close'); // close loading
                            },
                            error: function () {
                                alert('添加数据失败，商品编码已经存在');
                                el.$jsLoading.modal('close'); // close loading
                            }

                        });
                        return false;
                    } else {
                        alert('验证失败，请注意格式！');
                        return false;
                    }
                }
            });
        },
        /**
         * 修改商品信息
         */
        editRow: function () {
            var _this = this;
            el.$jsForm.validator({
                submit: function () {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle');  // show loading

                        //alert('验证成功');
                        var data = el.$jsForm.serialize(); // get form data data
                        // TODO save data to serve
                        $.ajax({
                            url: url,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.success) {
                                    el.$modal.modal('close');   // close modal
                                    alert(data.msg);
                                    _this.tableEx.ajax.reload(); // reload table data
                                } else if(data.failure){
                                	alert(data.msg);
                                } else {
                                    alert('后台错误: 修改数据失败' + data.msg);
                                }
                                el.$jsLoading.modal('close'); // close loading

                            },
                            error: function () {
                                alert('修改数据失败!');
                            }
                        });

                        return false;
                    } else {
                        alert('验证失败，请注意格式！');
                        return false;
                    }
                }
            });
        },
        /**
         * 修改
         */
        setForm: function (data) {
            var _this = this,
                i;
            for (i in data) {
                if (i == "smid") {
                    continue;
                }
                el.$jsForm.find('[name="' + i + '"]').val(data[i]);
            }
            el.$jsForm.find('[name="spdj"]').val(data.spdj);
            var sl = data.sl;
            var selectIndex = -1;
            var options = $("#smid").find("option");
            for (var j = 0; j < options.size(); j++) {
                var text = $(options[j]).text();
                var pos = text.indexOf("(");
                text = text.substring(0, pos);
                if (text == sl) {
                    selectIndex = j;
                    break;
                }
            }
            var sm = $("#smid").find("option").eq(selectIndex);
            $("#smid").val(sm.val());
            $("#spbm").find('option[value='+data.spbm+']').attr('selected', true);
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

        /**
         * 添加model
         */
        resetForm1: function () {
            el.$jsForm1[0].reset();
        },
        modalAction1: function () {
            var _this = this;
            el.$modal1.on('closed.modal.amui', function () {
                _this.resetForm1();
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable();
            _this.search_ac();
            _this.add_ac();
            _this.import_ac();
            _this.import();
            _this.export_ac();
            _this.editRow();
            _this.addRow();
            _this.modalAction(); // hidden action
            _this.modalAction1(); // hidden action
        }
    };
    action.init();
    //增加
    $("#smmc1").on('change', function () {
        var smdm = $(this).val();
        var text = $(this).find("option[value=" + smdm + "]").text();
        var pos = text.indexOf("(");
        var sl = text.substring(pos + 1, text.length - 1);
        $("#sl1").val(sl);
    });
    //修改
    $("#smmc").on('change', function () {
        var smdm = $(this).val();
        var text = $(this).find("option[value=" + smdm + "]").text();
        var pos = text.indexOf("(");
        var sl = text.substring(pos + 1, text.length - 1);
        $("#sl").val(sl);
    });
});