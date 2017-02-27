/**
 * feel
 */
$(function() {
	"use strict";
	var ur = 'xfxxwh/getXfxx';
	var el = {
		$jsTable : $('.js-table'),
		$modalHongchong : $('#hongchong'),
		$jsSubmit : $('.js-submit'),
		$jsClose : $('.js-close'),
		$jsForm0 : $('.js-form-0'), // 红冲 form
		$s_xfsh : $('#s_xfsh'), // search
		$s_xfmc : $('#s_xfmc'), // search
		$dpmax:$('#dzpzdje'),
		$fpfz:$('#dzpfpje'),
		$zpmax:$('#zpzdje'),
		$zpfz:$('#zpfpje'),
		$ppmax:$('#ppzdje'),
		$ppfz:$('#ppzdje'),
		$jsSearch : $('#button1'),
		$jsSearch1 : $('#button3'),
		$importExcelForm : $('#importExcelForm'),
		$jsTable1 : $('#button2'),
		$jsLoading : $('.js-modal-loading')
	};
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'xfxxwh/getXfxx',
			addUrl : 'xfxxwh/save',
			editUrl : 'xfxxwh/update',
			scUrl : 'xfxxwh/destroy',
			importUrl : 'xfxxwh/importExcel'
		},
		dataTable : function() {
			var _this = this;
			var t = el.$jsTable
					.DataTable({
						"processing" : true,
						"serverSide" : true,
						ordering : false,
						searching : false,
						scrollX : true,
						"ajax" : {
							url : ur,
							type : 'POST',
							data : function(d) {
								d.xfsh = $('#s_xfsh').val(); 
								d.xfmc = $('#s_xfmc').val(); 
								d.tip = $('#tip1').val(); 
								d.txt = $('#searchtxt').val(); 

							}
						},
						"columns" : [
								{
									"orderable" : false,
									"data" : null,
									"defaultContent" : ""
								},
								{
									"data" : "id"
								},
								{
									"data" : "sjjgbm"
								},
								{
									"data" : "xfmc"
								},
								{
									"data" : "xfsh"
								},
								{
									"data" : "sjxfmc"
								},
								{
									"data" : "xfdz"
								},
								{
									"data" : "xfdh"
								},
								{
									"data" : "xfyh"
								},
								{
									"data" : "xfyhzh"
								},
								{
									"data" : "skr"
								},
								{
									"data" : "fhr"
								},
								{
									"data" : "kpr"
								},
								{
									"data" : "zfr"
								},
								{
			                        "data": function (data) {
			                            if (data.dzpzdje) {
			                                return FormatFloat(data.dzpzdje, "###,###.00");
			                            } else {
			                                return null;
			                            }
			                        }, 'sClass': 'right'
			                    },
			                    {
			                        "data": function (data) {
			                            if (data.dzpfpje) {
			                                return FormatFloat(data.dzpfpje, "###,###.00");
			                            } else {
			                                return null;
			                            }
			                        }, 'sClass': 'right'
			                    },
			                    {
			                        "data": function (data) {
			                            if (data.zpzdje) {
			                                return FormatFloat(data.zpzdje, "###,###.00");
			                            } else {
			                                return null;
			                            }
			                        }, 'sClass': 'right'
			                    },
			                    {
			                        "data": function (data) {
			                            if (data.zpfpje) {
			                                return FormatFloat(data.zpfpje, "###,###.00");
			                            } else {
			                                return null;
			                            }
			                        }, 'sClass': 'right'
			                    },
			                    {
			                        "data": function (data) {
			                            if (data.ppzdje) {
			                                return FormatFloat(data.ppzdje, "###,###.00");
			                            } else {
			                                return null;
			                            }
			                        }, 'sClass': 'right'
			                    },
			                    {
			                        "data": function (data) {
			                            if (data.ppfpje) {
			                                return FormatFloat(data.ppfpje, "###,###.00");
			                            } else {
			                                return null;
			                            }
			                        }, 'sClass': 'right'
			                    },
								{
									"data": null,
			                        "defaultContent": "<a class='modify' href='#'>修改</a> <a href='#' class='del'>删除</a>"									
								} ]
					});
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(0).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
				$('#tbl tr').find('td:eq(1)').hide();
				$('#tbl tr').find('td:eq(2)').hide();
			});

			// 新增
			el.$jsTable1.on('click', el.$jsTable1, function() {
				_this.resetForm();
				$('div').removeClass('am-form-error');
				$('input').removeClass('am-field-error');
				$('div').removeClass('am-form-success');
				$('input').removeClass('am-field-success');
				ur = _this.config.addUrl;
				el.$modalHongchong.modal({"width": 950, "height": 500});
			});
			// 修改
			t.on('click', 'a.modify', function() {
				$('div').removeClass('am-form-error');
				$('input').removeClass('am-field-error');
				$('div').removeClass('am-form-success');
				$('input').removeClass('am-field-success');
				var row = t.row($(this).parents('tr')).data();
				_this.setForm0(row);				
				el.$modalHongchong.modal({"width": 950, "height": 500});
				$('#xfid').val(row.id);
				ur = _this.config.editUrl;
			});
			// 删除
			t.on('click', 'a.del', function() {
				var da = t.row($(this).parents('tr')).data();
				if (confirm("确定要删除该用户吗")) {
					_this.sc(da);
				}
				var data = t.row($(this).parents('tr')).data();
				_this.resetForm();
			});

	        var $importModal = $("#bulk-import-div");
	        $("#close1").click(function () {
	            $importModal.modal("close");
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
		/**
		 * search action
		 */
		search_ac : function() {
			var _this = this;
			el.$jsSearch.on('click', function(e) {
				e.preventDefault();
				$('#searchForm1').resetForm();
				_this.tableEx.ajax.reload();
			});
			el.$jsSearch1.on('click', function(e) {
				e.preventDefault();
				$('#tip1').find('option[value=0]').attr('selected', true);
//				$('#searchTxt').val("111");
				$('#searchForm').resetForm();
				_this.tableEx.ajax.reload();
			});
		},
		/**
		 * 新增保存
		 */

		xz : function() {
			var _this = this;
			el.$jsForm0.validator({
				submit : function() {
					var formValidity = this.isFormValid();
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading

						var dpmax = $('#dzpzdje').val();
						var fpfz = $('#dzpfpje').val();
						var zpmax = $('#zpzdje').val();
						var zpfz = $('#zpfpje').val();
						var ppmax = $('#ppzdje').val();
						var ppfz = $('#ppfpje').val();
						if (parseFloat(dpmax) < parseFloat(fpfz)) {
                        	alert('电子发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
                        if (parseFloat(zpmax) < parseFloat(zpfz)) {
                        	alert('普通发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
                        if (parseFloat(ppmax) < parseFloat(ppfz)) {
                        	alert('专用发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
                        var sjxf = $('#sjxf').val();
        				var xfid = $('#xfid').val();
        				if (sjxf == xfid) {
        					alert("不能选择该销方本身做上级");
                            el.$jsLoading.modal('close'); 
        					return;
        				}
						var data = el.$jsForm0.serialize(); // get form data
						$.ajax({
							url : ur,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									// loading
									el.$modalHongchong.modal('close'); // close
									alert(data.msg);
									_this.tableEx.ajax.reload(); // reload table
								} else if (data.repeat) {
									alert(data.msg);
								}else{
									alert(data.msg);
								}
								el.$jsLoading.modal('close'); // close

							},
							error : function() {
								el.$jsLoading.modal('close'); // close loading
								alert('保存失败, 请重新登陆再试...!');
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
		/**
		 * 删除
		 */
		sc : function(da) {
			var _this = this;
			$.ajax({
				url : _this.config.scUrl,
				data : {
					"xfid" : da.id
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {

						// modal
						alert(data.msg);
					} else {

						alert('删除失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					alert('删除失败, 请重新登陆再试...!');
				}
			});

		},
		setForm0 : function(data) {
			var _this = this, i;
			// todo set data
			// debugger
			el.$jsForm0.find('input[name="xfmc"]').val(data.xfmc);
			el.$jsForm0.find('input[name="xfsh"]').val(data.xfsh);
			el.$jsForm0.find('input[name="xfyh"]').val(data.xfsh);
			el.$jsForm0.find('input[name="khyh"]').val(data.xfyh);
			el.$jsForm0.find('input[name="dz"]').val(data.xfdz);
			el.$jsForm0.find('input[name="xflxr"]').val(data.xflxr);
			el.$jsForm0.find('input[name="kpr"]').val(data.kpr);
			el.$jsForm0.find('input[name="skr"]').val(data.skr);
			el.$jsForm0.find('input[name="yhzh"]').val(data.xfyhzh);
			el.$jsForm0.find('input[name="xfdh"]').val(data.xfdh);
			el.$jsForm0.find('input[name="fhr"]').val(data.fhr);
			el.$jsForm0.find('input[name="zfr"]').val(data.zfr);
			el.$jsForm0.find('select[name="dzpzdje"]').val(data.dzpzdje);
			el.$jsForm0.find('input[name="dzpfpje"]').val(data.dzpfpje);
			el.$jsForm0.find('select[name="zpzdje"]').val(data.zpzdje);
			el.$jsForm0.find('input[name="zpfpje"]').val(data.zpfpje);
			el.$jsForm0.find('select[name="ppzdje"]').val(data.ppzdje);
			el.$jsForm0.find('input[name="ppfpje"]').val(data.ppfpje);
			$('#sjxf').val(data.sjjgbm);
		},
		setForm1 : function() {
			var _this = this, i;
			// todo set data
			// debugger
			el.$jsForm0.find('input[name="hc_yhmc"]').val(data.yhmc);
			el.$jsForm0.find('input[name="hc_yzh"]').val(data.dlyhid);
		},
		resetForm : function() {
			el.$jsForm0[0].reset();
		},
		modalAction : function() {
			var _this = this;
			el.$modalHongchong.on('closed.modal.amui', function() {
				el.$jsForm0.find('input').val("");
			});
			// close modal
			el.$jsClose.on('click', function() {
				el.$modalHongchong.modal('close');
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable();
			_this.search_ac();
			_this.xz();
			_this.modalAction(); // hidden action
			
		}
	};

	action.init();

});