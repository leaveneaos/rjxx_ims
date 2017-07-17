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
		$jsSearch2 : $('#button4'),  
        $jsdel: $('.js-sent'), // del all
		$importExcelForm : $('#importExcelForm'),
		$jsTable1 : $('#button2'),
		$jsLoading : $('.js-modal-loading'),
        $checkAll: $('#check_all')// check all checkbox 
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
								var tip = $('#tip1').val();
								var txt = $('#searchtxt').val(); 
								if ($('#bj').val() == "1") {
									if (tip == "1") {
										d.xfmc = txt; 
									}else if (tip == "2") {
										d.xfsh = txt;
									}else if (tip == "3") {
										d.sjxfmc = txt;
									}else if (tip == "4") {
										d.kpr = txt;
									}
								}else{
									d.xfsh = $('#s_xfsh').val(); 
									d.xfmc = $('#s_xfmc').val(); 
									d.kpr = $('#kpr1').val();
									d.sjgj = $('#sjxf1').val();
								}
							}
						},
						"columns" : [
					                    {
					                        "orderable": false,
					                        "data": null,
					                        //"defaultContent": '<input type="checkbox" />'
					                        render: function (data, type, full, meta) {
					                            return '<input type="checkbox" name="chkb" value="' + data.id + '" class="chk"/>';
					                        }
					                    },
								{
									"orderable" : false,
									"data" : null,
									"defaultContent" : ""
								},
//								{
//									"data" : "id"
//								},
//								{
//									"data" : "sjjgbm"
//								},
								{
									"data": null,
			                        "defaultContent": "<a class='modify' href='#'>修改</a> "									
								},
								{
									"data" : "xfmc"
								},
								{
									"data" : "xfsh"
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
									"data" : "sjxfmc"
								},
								// {
								// 	"data" : "zfr"
								// },
								/*{
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
			                    },*/
			                    ]
					});
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
//				$('#tbl tr').find('td:eq(2)').hide();
//				$('#tbl tr').find('td:eq(3)').hide();
			});

			// 新增
			el.$jsTable1.on('click', el.$jsTable1, function() {
				_this.resetForm();
				$('div').removeClass('am-form-error');
				$('input').removeClass('am-field-error');
				$('div').removeClass('am-form-success');
				$('input').removeClass('am-field-success');
				ur = _this.config.addUrl;
				el.$modalHongchong.modal({"width": 950, "height": 400});
			});
			// 修改
			t.on('click', 'a.modify', function() {
				$('div').removeClass('am-form-error');
				$('input').removeClass('am-field-error');
				$('div').removeClass('am-form-success');
				$('input').removeClass('am-field-success');
				var row = t.row($(this).parents('tr')).data();
				_this.setForm0(row);				
				el.$modalHongchong.modal({"width": 950, "height": 400});
				$('#xfid').val(row.id);
				ur = _this.config.editUrl;
			});
			
//			 t.on('click', 'tr', function () {
//	              $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF");
//	        });
			 $('#tbl tbody').on('click', 'tr', function () {
		            if ($(this).hasClass('selected')) {
		                $(this).removeClass('selected');
		            } else {
		                t.$('tr.selected').removeClass('selected');
		                $(this).addClass('selected');
		            }
		            var data = t.row($(this)).data();
		            //$("#formid").val(data.djh);
		        });

	        var $importModal = $("#bulk-import-div");
	        $("#close1").click(function () {
	            $importModal.modal("close");
	        });


	        $('#dzpzdje').change(function() {
				$('#dzpfpje').val($('#dzpzdje').val());
			});
			$('#zpzdje').change(function() {
				$('#zpfpje').val($('#zpzdje').val());
			});
			$('#ppzdje').change(function() {
				$('#ppfpje').val($('#ppzdje').val());
			});


			
	      //导入excel
	        $("#btnImport").click(function () {
	            var filename = $("#importFile").val();
	            if (filename == null || filename == "") {
	             //    $('#msg').html('请选择要导入的文件');
		            // $('#my-alert').modal('open'); 
		            swal('请选择要导入的文件');
	                return;
	            }
	            var pos = filename.lastIndexOf(".");
	            if (pos == -1) {
	             //    $('#msg').html('导入的文件必须是excel文件');
		            // $('#my-alert').modal('open'); 
		            swal('导入的文件必须是excel文件');
	                return;
	            }
	            var extName = filename.substring(pos + 1);
	            if ("xls" != extName && "xlsx" != extName) {
	             //    $('#msg').html("导入的文件必须是excel文件");
		            // $('#my-alert').modal('open'); 
		            swal("导入的文件必须是excel文件");
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
		    	             //    $('#msg').html("导入成功，共导入" + count + "条数据");
		    		            // $('#my-alert').modal('open'); 
		    		            swal("导入成功，共导入" + count + "条数据");
		                        window.location.reload();
		                    } else {
		                        $("#btnImport").attr("disabled", false);
		                        $('.js-modal-loading').modal('close');
		    	             //    $('#msg').html(res.message);
		    		            // $('#my-alert').modal('open'); 
		    		            swal(res.message);
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
				$('#bj').val('1')
				_this.tableEx.ajax.reload();
			});
			el.$jsSearch1.on('click', function(e) {
				e.preventDefault();
				
//				$('#searchTxt').val("111");
				$('#bj').val('2')
//				$('#searchForm').resetForm();
				_this.tableEx.ajax.reload();
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
                	// $('#msg').html("请选择要删除的销方");
                	// $('#my-alert').modal('open'); 
                	swal("请选择要删除的销方");
                    return;
                }
                data = data.substring(0, data.length - 1);
                var url = _this.config.scUrl;
                /* if (!confirm('是否删除')) {
					return;
				}
               $('#my-confirm').modal({
         	        relatedTarget: this,
         	        onConfirm: function(options) {
          	        	el.$jsLoading.modal('open');
          	            $.ajax({
          	                url: url,
          	                data: {ids : data},
          	                type: 'POST',
          	                success: function (data) {
          	                    // todo 处理返回结果
          	                    if (data.success) {
          		                	$('#msg').html('删除成功');
          		                	$('#my-alert').modal('open'); 
          	                        _this.tableEx.ajax.reload(); // reload table data
          	                    } else {
          		                	$('#msg').html('删除失败,服务器错误' + data.msg);
          		                	$('#my-alert').modal('open'); 
          	                    }
          	                    el.$jsLoading.modal('close');

          	                },
          	                error: function () {
          	                	$('#msg').html('请求失败,请刷新后稍后重试!');
          	                	$('#my-alert').modal('open'); 
          	                    el.$jsLoading.modal('close');
          	                }
          	            });*/
          	            swal({
	                        title: "您确定要删除这条数据吗？",
	                        type: "warning",
	                        showCancelButton: true,
	                        closeOnConfirm: false,
	                        confirmButtonText: "确 定",
	                        confirmButtonColor: "#ec6c62"
	                    }, function() {
	                    	$('.confirm').attr('disabled',"disabled");
	                        $.ajax({
	                            url: url,
	          	                data: {ids : data},
	          	                type: 'POST',
	                        }).done(function(data) {
	                        	if (data.success) {
	                        		$('.confirm').removeAttr('disabled');
      		                	 	swal({ 
										  title: "已成功删除", 
										  timer: 1500, 
										  type: "success", 
										  showConfirmButton: false 
										});
          	                        _this.tableEx.ajax.reload(); // reload table data
          	                    } else {
          		                	swal('删除失败,服务器错误' + data.msg);
          	                    }
	                            
	                        }).error(function(data) {
	                            swal('请求失败,请刷新后稍后重试!', "error");
	                        });
	                    });
//          	        },
//          	        // closeOnConfirm: false,
//          	        onCancel: function() {
//          	          
//          	        }
//          	      });
//                _this.del({ids: data});
                el.$checkAll.prop('checked', false);
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
						// el.$jsLoading.modal('toggle'); // show loading



						
						var dpmax = $('#dzpzdje').val();
						var fpfz = $('#dzpfpje').val();
						var zpmax = $('#zpzdje').val();
						var zpfz = $('#zpfpje').val();
						var ppmax = $('#ppzdje').val();
						var ppfz = $('#ppfpje').val();
						if (parseFloat(dpmax) < parseFloat(fpfz)) {
        	             //    $('#msg').html('电子发票分票金额大于开票限额');
        		            // $('#my-alert').modal('open'); 
        		            swal('电子发票分票金额大于开票限额');
                            el.$jsLoading.modal('close'); 
                            return false;
						}




						var xfmc = $('#xfmc').val(),
							khyh = $('#khyh').val(),
							yhzh = $('#yhzh').val(),
							dz = $('#dz').val(),
							xfdh = $('#xfdh').val(),
							kpr = $('#kpr').val();
						if(!xfmc) {
							swal('请输入销方名称');
							return false;
						}
						if(!khyh) {
							swal('请输入开户银行');
							return false;
						}
						if(!yhzh) {
							swal('请输入银行账号');
							return false;
						}
						if(!dz) {
							swal('请输入销方地址');
							return false;
						}
						if(!xfdh) {
							swal('请输入销方电话');
							return false;
						}
						if(!kpr) {
							swal('请输入开票人');
							return false;
						}


                        if (parseFloat(zpmax) < parseFloat(zpfz)) {
        	             //    $('#msg').html('普通发票分票金额大于开票限额！');
        		            // $('#my-alert').modal('open'); 
        		            swal('普通发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
                        if (parseFloat(ppmax) < parseFloat(ppfz)) {
        	             //    $('#msg').html('专用发票分票金额大于开票限额！');
        		            // $('#my-alert').modal('open'); 
        		            swal('专用发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}


                        var sjxf = $('#sjxf').val();
        				var xfid = $('#xfid').val();
        				if (sjxf == xfid) {
        	             //    $('#msg').html("不能选择该销方本身做上级");
        		            // $('#my-alert').modal('open'); 
        		            swal("不能选择该销方本身做上级");
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
									// $('#msg').html(data.msg);
						   			//$('#my-alert').modal('open'); 
						  			swal({ 
									  title: "保存成功", 
									  timer: 1500, 
									  type: "success", 
									  showConfirmButton: false 
									});
									_this.tableEx.ajax.reload(); // reload table
								} else if (data.repeat) {
									// $('#msg').html(data.msg);
						   			//$('#my-alert').modal('open'); 
						   			swal(data.msg);
								}else{
									// $('#msg').html(data.msg);
					   				//$('#my-alert').modal('open'); 
						  			 swal(data.msg);
								}
								el.$jsLoading.modal('close'); // close

							},
							error : function() {
								el.$jsLoading.modal('close'); // close loading
				             //    $('#msg').html('保存失败, 请重新登陆再试...!');
					            // $('#my-alert').modal('open'); 
					            swal('保存失败, 请重新登陆再试...!');
							}
						});
						return false;
					} else {
		             //    $('#msg').html('验证失败');
			            // $('#my-alert').modal('open'); 
			            swal('验证失败');
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
		             //    $('#msg').html(data.msg);
			            // $('#my-alert').modal('open'); 
			            swal(data.msg);
					} else {

		             //    $('#msg').html('删除失败: ' + data.msg);
			            // $('#my-alert').modal('open'); 
			            swal('删除失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
	             //    $('#msg').html('删除失败, 请重新登陆再试...!');
		            // $('#my-alert').modal('open'); 
		            swal('删除失败, 请重新登陆再试...!');
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
			// el.$jsForm0.find('input[name="xflxr"]').val(data.xflxr);
			el.$jsForm0.find('input[name="kpr"]').val(data.kpr);
			el.$jsForm0.find('input[name="skr"]').val(data.skr);
			el.$jsForm0.find('input[name="yhzh"]').val(data.xfyhzh);
			el.$jsForm0.find('input[name="xfdh"]').val(data.xfdh);
			el.$jsForm0.find('input[name="fhr"]').val(data.fhr);
			// el.$jsForm0.find('input[name="zfr"]').val(data.zfr);


			el.$jsForm0.find('select[name="dzpzdje"]').val(data.dzpzdje);
			el.$jsForm0.find('input[name="dzpfpje"]').val(data.dzpfpje);
			el.$jsForm0.find('select[name="zpzdje"]').val(data.zpzdje);
			el.$jsForm0.find('input[name="zpfpje"]').val(data.zpfpje);
			el.$jsForm0.find('select[name="ppzdje"]').val(data.ppzdje);
			el.$jsForm0.find('input[name="ppfpje"]').val(data.ppfpje);


			
			el.$jsForm0.find('select[id="sjxf"]').val(data.sjjgbm == null ? "0" : data.sjjgbm);
			$('#sjxf').val(data.sjjgbm == null ? 0 : data.sjjgbm);
			if (data.sjjgbm != null) {
				$("#sjxf").find('option[value=' + data.sjjgbm + ']').attr('selected',
						true);
			}else{
				$("#sjxf").find('option[value=0]').attr('selected',
						true);
			}
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
            _this.checkAllAc();
            _this.delAllAc();
			_this.xz();
			_this.modalAction(); // hidden action
			
		}
	};

	action.init();

});