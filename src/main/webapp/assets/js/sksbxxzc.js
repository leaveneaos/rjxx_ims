$(function () {
    "use strict";
    var url;
    var el = {
        $jsTable: $('.js-table'),
        $jsModalOpem: $('.js-modal-open'),
        $modal: $('#your-modal'),
        $modal1: $('#your-modal1'),
        $jsSubmit: $('.js-submit'),
        $jsSubmit1: $('.js-submit1'),
		$jsSearch : $('#button1'),
		$jsClose : $('.js-close'),
		$jsClose1 : $('.js-close1'),
		$xfid : $('#xfid'),
		$s_kpdmc : $('#s_kpdmc'),
		$s_kpddm : $('#s_kpddm'),
        $jsForm: $('.js-form'),
        $jsForm1: $('.js-form1'),        
        $dpmax: $('#dpmax'),
        $ppmax: $('#ppmax'),
        $zpmax: $('#zpmax'),
        $fpfz: $('#fpfz'),
        $ppfz: $('#ppfz'),
        $zpfz: $('#zpfz'),
        $checkAll: $('#check_all'), // check all checkbox     
        $jsdel: $('.js-sent'), // del all
        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'sksbxxzc/getsksblist',
            delUrl: 'sksbxxzc/del',
            addUrl: 'sksbxxzc/save',
            deleteUrl: 'sksbxxzc/delele',
            editUrl: 'sksbxxzc/update',
            xfUrl: 'sksbxxzc/getXf'
        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
	            "ajax" : {
					url : _this.config.getUrl,
					type : 'POST',
					data : function(d) {
						d.xfid = el.$xfid.val(); // search 用户账号
						d.kpdmc = el.$s_kpdmc.val(); // search 用户名称
						d.kpddm = el.$s_kpddm.val(); // search 用户名称
						d.xfid1= $('#xfid1').val(); // search 用户名称
	
					}
				},
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        //"defaultContent": '<input type="checkbox" />'
                        render: function (data, type, full, meta) {
                            return '<input type="checkbox" value="' + data.id + '" />';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""

                    },
                    {"data": "id"},
                    {"data": "pid"},   
                    {"data": "skpmm"},  
                    {"data": "zsmm"},
                    {"data": "xfmc"},
                    {"data": "kpddm"},
                    {"data": "kpdmc"}, 
                    {"data": function(data){
                    	if (data.sbcs == 1) {
							return "百旺";
						}else{
							return "航信";
						}
                    }}, 
                    {"data": "skph"},   
                    {"data": function(data){
                    	if (data.skpmm == null || data.skpmm == "") {
							return "";
						}else{
							return "******";
						}
                    }},  
                    {"data": function(data){
                    	if (data.zsmm == null || data.zsmm == "") {
							return "";
						}else{
							return "******";
						}
                    }},  
                    {"data": "lxdz"},  
                    {"data": "lxdh"},  
                    {"data": "khyh"},  
                    {"data": "yhzh"},  
                    {"data": "skr"},  
                    {"data": "fhr"},  
                    {"data": "kpr"},  
                    {"data": "fpzl"},  
                    {"data": "ppdm"},
                    {"data": "ppmc"},  
                    {
                        "data": null,
                        "defaultContent": "<a class='modify'>修改</a> <a class='del'>删除</a>"
                    }
                ]
            });

            t.on('draw.dt', function (e, settings, json) {

                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {//序号
                    cell.innerHTML = page + i + 1;
                });
                $('#tbl tr').find('td:eq(2)').hide();
                $('#tbl tr').find('td:eq(3)').hide();
                $('#tbl tr').find('td:eq(4)').hide();
                $('#tbl tr').find('td:eq(5)').hide();
            });

            t.on('click', 'a.modify', function () {
                var data = t.row($(this).parents('tr')).data();
                el.$jsForm.find('[name="kpddm"]').val(data.kpddm);
                el.$jsForm.find('[name="kpdmc"]').val(data.kpdmc);
                el.$jsForm.find('[name="xfid"]').find('option[value='+data.xfid+']').prop('selected', true);
                el.$jsForm.find('[name="pid"]').find('option[value='+data.pid+']').prop('selected', true);
                el.$jsForm.find('[name="sbcs"]').find('option[value='+(data.sbcs== "百旺" ? 1 : 2)+']').prop('selected', true);
                el.$jsForm.find('[name="skpmm"]').val(data.skpmm);
                el.$jsForm.find('[name="zsmm"]').val(data.zsmm);
                el.$jsForm.find('[name="skph"]').val(data.skph);
                el.$jsForm.find('[name="lxdz"]').val(data.lxdz);
                el.$jsForm.find('[name="lxdh"]').val(data.lxdh);
                el.$jsForm.find('[name="khyh"]').val(data.khyh);
                el.$jsForm.find('[name="yhzh"]').val(data.yhzh);
                el.$jsForm.find('[name="skr"]').val(data.skr);
                el.$jsForm.find('[name="fhr"]').val(data.fhr);
                el.$jsForm.find('[name="kpr"]').val(data.kpr);
                var fps = data.kplx.split(",");
                for(var i = 0; i < fps.length; i++){
                	var fplx = '#fplx-'+fps[i];
                	$(fplx).prop('checked', true);
                }
                //el.$jsForm.find('[name="zcm"]').val(data.zcm);
                url = _this.config.editUrl + "?id=" + data.id;
                $('#your-modal').modal({"width": 800, "height": 500});
            });
           
            t.on('click', 'a.del', function () {
                var data = t.row($(this).parents('tr')).data();
                url = _this.config.delUrl;
                _this.del({ids: data.id});
            });
            
	        var $importModal = $("#bulk-import-div");
	        $("#close1").click(function () {
	            $importModal.modal("close");
	        });
	        
	        $('#xfid').change(function(){
	        	var lxdz = $('#lxdz').val();
                var lxdh = $('#lxdh').val();
                var khyh = $('#khyh').val();
                var yhzh = $('#yhzh').val();
                var skr = $('#skr').val();
                var fhr = $('#fhr').val();
                var kpr = $('#kpr').val();
	        	
	        		$.ajax({
		                 url: _this.config.xfUrl,
		                 data: {xfid : $('#xfid').val()},
		                 type: 'POST',
		                 success: function (data) {
//		                	 if ((lxdz == "" && lxdh == "" && khyh == "" && yhzh == "" && skr == "" 
//		     	        		&& fhr == "" && kpr == "") || (lxdz == data.xf.xfdz && lxdh == data.xf.xfdh 
//		     	        		&& khyh == data.xf.khyh && yhzh == data.xf.yhzh && skr == data.xf.skr &&
//		     	        		fhr == data.xf.fhr && kpr == data.xf.kpr)) {
		                		 $('#lxdz').val(data.xf.xfdz);
			                     $('#lxdh').val(data.xf.xfdh);
			                     $('#khyh').val(data.xf.xfyh);
			                     $('#yhzh').val(data.xf.yhzh);
			                     $('#skr').val(data.xf.skr);
			                     $('#fhr').val(data.xf.fhr);
			                     $('#kpr').val(data.xf.kpr);
//		                	 }
		                 },
		                 error: function () {
		                     
		                 }
		             });
	        	 
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
            return t;
        },
        modal: function () {
            var _this = this;
            el.$jsModalOpem.on('click', function (e) {
                e.preventDefault();
                url = _this.config.addUrl;
                $('#your-modal').modal({"width": 800, "height": 500});

            });
        },
        /**
         * del删除税控盘信息 request
         * @param data { ids: '1,2,3,4' }
         */
        del: function (data) {
            var _this = this;
            if (!confirm("您确认删除！！！")) {
                return;
            }
            el.$jsLoading.modal('open');
            $.ajax({
                url: url,
                data: {ids : data.ids},
                type: 'POST',
                success: function (data) {
                    // todo 处理返回结果
                    if (data.success) {
                        alert('删除成功');
                        _this.tableEx.ajax.reload(); // reload table data
                    } else {
                        alert('删除失败,服务器错误' + data.msg);
                    }
                    el.$jsLoading.modal('close');

                },
                error: function () {
                    alert('请求失败,请刷新后稍后重试!');
                    el.$jsLoading.modal('close');
                }
            });
        },
        /**
         * check all action
         */
        checkAllAc: function () {
            var _this = this;
            el.$checkAll.on('change', function (e) {
                var $this = $(this),
                    check = null;
                if ($(this).is(':checked')) {
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                    });
                } else {
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
            });
        },

        delAllAc: function () {
            var _this = this;
            el.$jsdel.on('click', function (e) {
                e.preventDefault();
                var data = '',
                    row = '',
                    $tr = null;
                _this.tableEx.column(0).nodes().each(function (cell, i) {

                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {

                        row = _this.tableEx.row(i).data().id;
                        data += row+ ',';
                    }

                });
                if (!data) {
                    alert("请选择要删除的税控盘")
                    return;
                }
                data = data.substring(0, data.length - 1);
                url = _this.config.deleteUrl;
                _this.del({ids: data});
                el.$checkAll.prop('checked', false);
            });

        },
        /**
         * 添加方法
         */
        saveRow: function () {
            var _this = this;
            el.$jsForm.validator({
                submit: function () {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle');  // show loading
                        //alert('验证成功');
                        if ($('#xfid').val() == 0) {
							alert('请选择销方');
							return;
						}
                        var data = el.$jsForm.serialize(); // get form data data                        
                        $.ajax({
                            url: url,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.success) {
                                    el.$modal.modal('close');   // close modal
                                    alert(data.msg);
                                    _this.tableEx.ajax.reload(); // reload table data
                                } else if (data.failure){ 
                                	alert(data.msg);
                                } else {
                                    alert('后台错误: 数据操作失败' + data.msg);
                                }// close loading

                                el.$jsLoading.modal('close'); 
                            },
                            error: function () {
                                alert('数据操作失败, 请重新登陆再试...!');
                                el.$jsLoading.modal('close');
                            }
                        });

                        return false;
                    } else {
                        alert('验证失败,请注意格式！');
                        return false;
                    }
                }
            });
        },
        /**
         * 修改方法
         */
        editRow: function () {
            var _this = this;
            el.$jsForm1.validator({
                submit: function () {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle');  // show loading
                        var data = el.$jsForm1.serialize(); // get form data data
                        $.ajax({
                            url: _this.config.editUrl,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.success) {
                                    el.$modal1.modal('close');   // close modal
                                    alert(data.msg);
                                } else {
                                    alert('后台错误: 数据操作失败' + data.msg);

                                }
                                _this.tableEx.ajax.reload(); // reload table data
                                el.$jsLoading.modal('close'); // close loading

                            },
                            error: function () {
                                alert('数据操作失败, 请重新登陆再试...!');
                                el.$jsLoading.modal('close');
                            }
                        });

                        return false;
                    } else {
                        alert('验证失败,请注意格式！');
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
				_this.tableEx.ajax.reload();
			});
		},
        /**
         * 添加model
         */
        resetForm: function () {
            el.$jsForm[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modal.on('closed.modal.amui', function () {
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
        resetForm1: function () {
            el.$jsForm1[0].reset();
        },
        modalAction1: function () {
            var _this = this;
            el.$modal1.on('closed.modal.amui', function () {
                _this.resetForm1();
            });
        },
        /**
         * 初始化
         */
        init: function () {
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