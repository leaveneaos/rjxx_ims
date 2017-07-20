$(function() {
	"use strict";
	var url;
	var url1;
	var el = {
		$jsTable : $('.js-table'),
		$modal2 : $('#import'),
		$jsForm2 : $('.js-form2'),// 导入

		$modal : $('#your-modal'),
		$modal1 : $('#your-modal1'),

		$jsSubmit : $('.js-submit'),
		$jsForm : $('.js-form'),
		$jsSubmit1 : $('.js-submit1'),
		$jsForm1 : $('.js-form1'),

		$js_close : $('.js-close'),
		$js_close1 : $('.js-close1'),

		$s_spdm : $('#s_spdm'), // search 商品类别
		$s_spmc : $('#s_spmc'), // search 商品名称

		$jsSearch : $('#button1'),
		$jsSearch1 : $('.js-search1'),
		$jsSearch2 : $('#button3'),
		$jsAdd : $('.js-add'),
		$jsDel : $('del'),
		$jsImport : $('.js-import'),
		$jsExport : $('.js-export'),
		$jsLoading : $('.js-modal-loading'),
		$checkAll : $('#check_all'), // check all checkbox
		$jsdel : $('.js-sent')
	// del all
	};
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'spslgl/getSplist',
			delUrl : 'spslgl/delete', // 禁用
			addUrl : 'spslgl/add',
			editUrl : 'spslgl/update',
			// getSlUrl:'spslgl/getSl',
			importUrl : 'spslgl/importFile'
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
							url : _this.config.getUrl,
							type : 'POST',
							data : function(d) {
								if ($('#bj').val() == "1") {
									var tip = $('#tip').val();
									if (tip == "1") {
										d.spdm = $('#searchtxt').val();
									} else if (tip == "2") {
										d.spmc = $('#searchtxt').val();
									} else if (tip == "3") {
										d.sl = $('#searchtxt').val();
									}

								} else if ($('#bj').val() == "2") {
									d.spdm = el.$s_spdm.val(); // search 商品类别
									d.spmc = el.$s_spmc.val(); // search 商品名称
									d.slid = $('#smid2').val();
								}

							}
						},
						"columns" : [
								{
									"orderable" : false,
									"data" : null,
									// "defaultContent": '<input type="checkbox"
									// />'
									render : function(data, type, full, meta) {
										return '<input type="checkbox" name="chkb" value="'
												+ data.id + '" class="chk"/>';
									}
								},
								{
									"orderable" : false,
									"data" : null,
									"defaultContent" : ""
								},
								// { "data": "id" },
								{
									"data" : null,
									"defaultContent" : '<a class="modify">修改</a>'
								},
								{
									"data" : "spdm"
								},
								{
									"data" : "spmc"
								},
								{
									"data" : "sl",
									'sClass' : 'right'
								},
								{
									"data" : "spggxh"
								},
								{
									"data" : "spdw"
								},
								{
									'data' : function(data) {
										if (data.spdj) {
											return FormatFloat(data.spdj,
													"###,###.00");
										} else {
											return null;
										}
									},
									'sClass' : 'right'
								},
								{
									"data" : "spbm1"
								},
								 ]
					});

			var spz_table = $('#tbl1')
					.DataTable(
							{
								"processing" : true,
								"serverSide" : true,
								ordering : false,
								searching : false,
								scrollX : true,

								"ajax" : {
									"url" : "spslgl/getSpzs",
									data : function(d) {
										d.spzmc = $("#s_spzmc").val();
									}
								},

								"columns" : [
										{
											"orderable" : false,
											"data" : null,
											// "defaultContent": '<input
											// type="checkbox" />'
											render : function(data, type, full,
													meta) {
												return '<input type="checkbox" name="chkb" value="'
														+ data.id
														+ '" class="chk"/>';
											}
										},
										{
											"orderable" : false,
											"data" : null,
											"defaultContent" : ""
										},
										// { "data": "id" },
										{
											"data" : "spzmc"
										},
										{
											"data" : function(data) {
												if (data.zbz == 1) {
													return "公共组";
												} else {
													return "私有组";
												}
											}
										},
										{
											"data" : null,
											"defaultContent" : '<a class="modify1">修改</a>'
										}

								]

							});

			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});

				// $('#tbl tr').find('td:eq(1)').hide();
			});

			spz_table.on('draw.dt', function(e, settings, json) {
				var x = spz_table, page = x.page.info().start; // 设置第几页
				spz_table.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});

				// $('#tbl1 tr').find('td:eq(1)').hide();
			});

			t.on('click', 'a.modify', function() {
				var data = t.row($(this).parents('tr')).data();
				url = _this.config.editUrl + "?id=" + data.id;
				_this.setForm(data);
				el.$modal.modal('open');
			});
			var $importModal = $("#bulk-import-div");
			$("#close").click(function() {
				var _this = this;
				$('#your-modal').modal('close');
			});
			$("#close1").click(function() {
				var _this = this;
				$('#your-modal1').modal('close');
			});
			$("#close2").click(function() {
				var _this = this;
				$('#bulk-import-div').modal('close');
			});
			$("#close3").click(function() {
				var _this = this;
				$('#hongchong').modal('close');
			});
			$("#search").click(function() {
				spz_table.ajax.reload();
			});
			$("#new").click(function() {
				$('#hongchong').modal({
					"width" : 700,
					"height" : 500
				});
				url1 = "spslgl/saveSpz";
			});
			$("#save").click(function() {
				var data = $("#form1").serialize();
				$('#save').attr("disabled", true);
				var spzmc = $("#spzmc").val();
				if (spzmc == null || spzmc == '' || spzmc == "") {
					// $('#msg').html('请输入商品组名称');
					// $('#my-alert').modal('open');
					swal('请输入商品组名称');
					$('#save').attr("disabled", false);
					return;
				}
				$.ajax({
					url : url1,
					data : data,
					method : 'POST',
					success : function(data) {
						$('#save').attr("disabled", false);
						if (data.success) {
							// $('#msg').html("保存成功");
							// $('#my-alert').modal('open');
							swal({ 
								  title: "保存成功", 
								  timer: 1500, 
								  type: "success", 
								  showConfirmButton: false 
								});
							$('#hongchong').modal('close');
							spz_table.ajax.reload();
						} else {
							// $('#msg').html(data.msg);
							// $('#my-alert').modal('open');
							swal(data.msg);
						}
					},
					error : function() {
						// $('#msg').html('保存失败, 请重新登陆再试...!');
						// $('#my-alert').modal('open');
						swal('保存失败, 请重新登陆再试...!');
						$('#save').attr("disabled", false);
					}
				});
			});
			// 导入excel
			$("#btnImport").click(function() {
				var filename = $("#importFile").val();
				if (filename == null || filename == "") {
					// $('#msg').html("请选择要导入的文件");
					// $('#my-alert').modal('open');
					swal("请选择要导入的文件");
					return;
				}
				var pos = filename.lastIndexOf(".");
				if (pos == -1) {
					// $('#msg').html("导入的文件必须是excel文件");
					// $('#my-alert').modal('open');
					swal("导入的文件必须是excel文件");
					return;
				}
				var extName = filename.substring(pos + 1);
				if ("xls" != extName && "xlsx" != extName) {
					// $('#msg').html("导入的文件必须是excel文件");
					// $('#my-alert').modal('open');
					swal("导入的文件必须是excel文件");
					return;
				}
				$("#btnImport").attr("disabled", true);
				$('.js-modal-loading').modal('toggle'); // show loading
				// alert('验证成功');
				var options = {
					success : function(res) {
						if (res.success) {
							$("#btnImport").attr("disabled", false);
							$('.js-modal-loading').modal('close');
							var count = res.count;
							// $('#msg').html("导入成功，共导入" + count + "条数据");
							// $('#my-alert').modal('open');
							swal("导入成功，共导入" + count + "条数据");
							window.location.reload();
						} else {
							$("#btnImport").attr("disabled", false);
							$('.js-modal-loading').modal('close');
							// $('#msg').html(res.message);
							// $('#my-alert').modal('open');
							swal(res.message);
						}
					}
				};
				$("#importExcelForm").ajaxSubmit(options);

			});

			/**
			 * 禁用
			 */
			t.on('click', 'a.del', function() {

				$('#my-confirm').modal(
						{
							relatedTarget : this,
							onConfirm : function(options) {
								var data = t.row(
										$(this.relatedTarget).parents('tr'))
										.data();
								el.$jsLoading.modal('open'); // show loading
								_this.bear({
									id : data.id
								});
							},
							// closeOnConfirm: false,
							onCancel : function() {

							}
						});
			});
			spz_table.on('click', 'a.modify1', function() {
				var data = spz_table.row($(this).parents('tr')).data();
				$("#form1").resetForm();
				$("#spzmc").val(data.spzmc);
				$("#zbz").val(data.zbz);
				$.ajax({
					url : "spslgl/getSpzsp",
					data : {
						spzid : data.id
					},
					type : 'post',
					success : function(data) {
						var i, rp, rp1, slt, slt1, slt2;
						var list = data.sps;
						var list1 = data.xfs;
						for (i = 0; i < list.length; i++) {
							rp = list[i];
							slt = '#spz-' + rp.spdm;
							$(slt).prop('checked', true);
						}
						for (i = 0; i < list1.length; i++) {
							$('#spz-' + list1[i].xfid).prop('checked', true);
						}
					}
				});
				$('#hongchong').modal({
					"width" : 700,
					"height" : 500
				});
				url1 = "spslgl/updateSpz?spzid=" + data.id;
			});
//			spz_table.on('click', 'a.del1', function() {
//				// TODO 删除这条数据
//				$('#my-confirm').modal(
//						{
//							relatedTarget : this,
//							onConfirm : function(options) {
//								var data = spz_table.row(
//										$(this.relatedTarget).parents('tr'))
//										.data();
//								el.$jsLoading.modal('open'); // show loading
//								$.ajax({
//									url : "spslgl/deleteSpz",
//									data : {
//										spzid : data.id
//									}, // 禁用这条数据的 id
//									method : 'POST',
//									// context: null,
//									success : function(data) {
//										if (data.failure) {
//											$('#msg').html(data.msg);
//											$('#my-alert').modal('open');
//										} else if (data.success) {
//											$('#msg').html('删除成功');
//											$('#my-alert').modal('open');
//											spz_table.ajax.reload(); // 重新加载数据
//										}
//										el.$jsLoading.modal('close'); // close
//										// loading
//									},
//									error : function() {
//										$('#msg').html('删除数据失败,请稍后重试');
//										$('#my-alert').modal('open');
//									}
//								});
//							},
//							// closeOnConfirm: false,
//							onCancel : function() {
//
//							}
//						});
//			});

			$('#check_all1').change(
					function() {
						var $this = $(this), check = null;
						if ($(this).is(':checked')) {
							spz_table.column(0).nodes().each(
									function(cell, i) {
										$(cell).find('input[type="checkbox"]')
												.prop('checked', true);
									});
						} else {
							spz_table.column(0).nodes().each(
									function(cell, i) {
										$(cell).find('input[type="checkbox"]')
												.prop('checked', false);
									});
						}
					});
			$('#deletespz').click(function() {
				var data = '', row = '', $tr = null;
				spz_table.column(0).nodes().each(function(cell, i) {

					var $checkbox = $(cell).find('input[type="checkbox"]');
					if ($checkbox.is(':checked')) {

						row =spz_table.row(i).data().id;
						data += row + ',';
					}

				});
				if (!data) {
					// $('#msg').html("请选择要删除的商品组");
					// $('#my-alert').modal('open');
					swal("请选择要删除的商品组");
					return;
				}
				data = data.substring(0, data.length - 1);
				var url = _this.config.delUrl;

				swal({
	                title: "您确定要删除吗？",
	                text: "您确定要删除这条数据？",
	                type: "warning",
	                showCancelButton: true,
	                closeOnConfirm: false,
	                confirmButtonText: "确 定",
	                confirmButtonColor: "#ec6c62"
	            }, function() {
	            	$('.confirm').attr('disabled',"disabled");
	                $.ajax({
	                    url : "spslgl/deleteSpz",
						data : {
							ids : data
						},
						type : 'POST',
	                }).done(function(data) {
	                	if (data.success) {
	                		$('.confirm').removeAttr('disabled');
		                        spz_table.ajax.reload(); // reload table data
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
				/*if (!confirm('是否删除')) {
					return;
				}
				$.ajax({
					url : "spslgl/deleteSpz",
					data : {
						ids : data
					},
					type : 'POST',
					success : function(data) {
						// todo 处理返回结果
						if (data.success) {
							$('#msg').html('删除成功');
							$('#my-alert').modal('open');
							spz_table.ajax.reload(); // reload table data
						} else {
							$('#msg').html('删除失败,服务器错误' + data.msg);
							$('#my-alert').modal('open');
						}
						el.$jsLoading.modal('close');

					},
					error : function() {
						$('#msg').html('请求失败,请刷新后稍后重试!');
						$('#my-alert').modal('open');
						el.$jsLoading.modal('close');
					}
				});
*/

			})

			return t;
		},
		/**
		 * 禁用的方法
		 */
		bear : function(data) {
			var _this = this;
			$.ajax({
				url : _this.config.delUrl,
				data : data, // 禁用这条数据的 id
				method : 'POST',
				// context: null,
				success : function(data) {
					if (data.failure) {
						// $('#msg').html(data.msg);
						// $('#my-alert').modal('open');
						swal(data.msg);
					} else if (data.success) {
						// $('#msg').html('删除成功');
						// $('#my-alert').modal('open');
						swal({ 
							  title: "已成功删除", 
							  timer: 1500, 
							  type: "success", 
							  showConfirmButton: false 
							});
						_this.tableEx.ajax.reload(); // 重新加载数据
					}
					el.$jsLoading.modal('close'); // close loading
				},
				error : function() {
					// $('#msg').html('删除数据失败,请稍后重试');
					// $('#my-alert').modal('open');
					swal('删除数据失败,请稍后重试');
				}
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
					// $('#msg').html("请选择要删除的商品");
					// $('#my-alert').modal('open');
					swal("请选择要删除的商品");
					return;
				}
				data = data.substring(0, data.length - 1);
				var url = _this.config.delUrl;

				swal({
	                title: "您确定要删除吗？",
	                text: "您确定要删除这条数据？",
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
							ids : data
						},
						type : 'POST',
	                }).done(function(data) {
	                	if (data.success) {
	                			$('.confirm').removeAttr('disabled');
		                        _this.tableEx.ajax.reload();// reload table data
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
				/*if (!confirm('是否删除')) {
					return;
				}
				// $('#my-confirm').modal({
				// relatedTarget: this,
				// onConfirm: function(options) {
				el.$jsLoading.modal('open');
				$.ajax({
					url : url,
					data : {
						ids : data
					},
					type : 'POST',
					success : function(data) {
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
					error : function() {
						$('#msg').html('请求失败,请刷新后稍后重试!');
						$('#my-alert').modal('open');
						el.$jsLoading.modal('close');
					}
				});*/

				// },
				// // closeOnConfirm: false,
				// onCancel: function() {
				//          	          
				// }
				// });
				// _this.del({ids: data});
				el.$checkAll.prop('checked', false);
			});

		},
		/**
		 * search action
		 */
		search_ac : function() {
			var _this = this;
			el.$jsSearch
					.on(
							'click',
							function(e) {
								e.preventDefault();
								$('#bj').val('1');
								// $('#searchform1').resetForm();
								if ($('#tip').val() == '3') {
									var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
									if (!$('#searchtxt').val().match(reg)) {
										// $('#msg').html('税率格式有误');
										// $('#my-alert').modal('open');
										swal('税率格式有误');
										return;
									}
								}
								_this.tableEx.ajax.reload(); // 重新加载数据
							});
			el.$jsSearch2.on('click', function(e) {
				e.preventDefault();
				$('#bj').val('2');
				// $('#tip').find('option[value=0]').prop('selected', true);
				// $('#searchform').resetForm();
				_this.tableEx.ajax.reload(); // 重新加载数据
			});
		},
		add_ac : function() {
			var _this = this;
			el.$jsAdd.on('click',
					function(e) {
						e.preventDefault();
						var smdm = $("#smid").val();
						var text = $("#smid")
								.find("option[value=" + smdm + "]").text();
						var pos = text.indexOf("(");
						var sl = text.substring(pos + 1, text.length - 1);
						el.$modal.modal('toggle');
						url = _this.config.addUrl;
					});
		},
		import_ac : function() {
			// TODO you code here
			el.$jsImport.on('click', function(e) {
				el.$modal2.modal('open');
				e.preventDefault();
				// alert('导入'); // del this line
			});
		},
		import : function() {
			var _this = this;
			el.$jsForm2.validator({
				submit : function() {
					$('.confirm').attr('disabled',"disabled");
					var formValidity = this.isFormValid();
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading
						// alert('验证成功');
						var data = el.$jsForm2.serialize(); // get form data
						// data
						// TODO save data to serve
						$.ajax({
							url : _this.config.importUrl,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									$('.confirm').removeAttr('disabled');
									el.$modal2.modal('close'); // close modal
									// $('#msg').html(data.msg);
									// $('#my-alert').modal('open');
									swal(data.msg);
								} else {
									// $('#msg').html('后台错误: 导入数据失败' + data.msg);
									// $('#my-alert').modal('open');
									swal('后台错误: 导入数据失败' + data.msg);
								}
								_this.tableEx.ajax.reload(); // 重新加载数据
								el.$jsLoading.modal('close'); // close loading

							},
							error : function() {
								// $('#msg').html('导入数据失败, 请重新登陆再试...!');
								// $('#my-alert').modal('open');
								swal('导入数据失败, 请重新登陆再试...!');
							}

						});
						return false;
					} else {
						// $('#msg').html('验证失败,请选择文件！');
						// $('#my-alert').modal('open');
						swal('验证失败,请选择文件！');
						return false;
					}
				}
			});
		},
		export_ac : function() {
			el.$jsExport.on('click', function(e) {
				e.preventDefault();
				// TODO you code here
				swal('导出'); // del this line
			});
		},
		/**
		 * 添加商品
		 */
		addRow : function() {
			var _this = this;
			// $("#sl1").val($("#smmc1").val());
			el.$jsForm.validator({
				submit : function() {
					var formValidity = this.isFormValid();
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading
						// alert('验证成功');
						var data = el.$jsForm.serialize(); // get form data
						// data
						// TODO save data to serve
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
									_this.tableEx.ajax.reload(); // 重新加载数据
								} else if (data.failure) {
									// $('#msg').html(data.msg);
									// $('#my-alert').modal('open');
									swal(data.msg);
								} else {
									// $('#msg').html(data.msg);
									// $('#my-alert').modal('open');
									swal(data.msg);
								}
								el.$jsLoading.modal('close'); // close loading
							},
							error : function() {
								// $('#msg').html('添加数据失败，商品编码已经存在');
								// $('#my-alert').modal('open');
								swal('添加数据失败，商品编码已经存在');
								el.$jsLoading.modal('close'); // close loading
							}

						});
						return false;
					} else {
						// $('#msg').html('验证失败，请注意格式！');
						// $('#my-alert').modal('open');
						swal('验证失败，请注意格式！');
						return false;
					}
				}
			});
		},
		/**
		 * 修改商品信息
		 */
		editRow : function() {
			var _this = this;
			el.$jsForm.validator({
				submit : function() {
					var formValidity = this.isFormValid();
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading

						// alert('验证成功');
						var data = el.$jsForm.serialize(); // get form data
						// data
						// TODO save data to serve
						$.ajax({
							url : url,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									el.$modal.modal('close'); // close modal
									// $('#msg').html(data.msg);
									// $('#my-alert').modal('open');
									swal({ 
									  title: "保存成功", 
									  timer: 1500, 
									  type: "success", 
									  showConfirmButton: false 
									});
									_this.tableEx.ajax.reload(); // reload
									// table
									// data
								} else if (data.failure) {
									// $('#msg').html(data.msg);
									// $('#my-alert').modal('open');
									swal(data.msg);
								} else {
									// $('#msg').html('后台错误: 修改数据失败' + data.msg);
									// $('#my-alert').modal('open');
									swal('后台错误: 修改数据失败' + data.msg);
								}
								el.$jsLoading.modal('close'); // close loading

							},
							error : function() {
								// $('#msg').html('修改数据失败!');
								// $('#my-alert').modal('open');
								swal('修改数据失败!');
							}
						});

						return false;
					} else {
						// $('#msg').html('验证失败，请注意格式！');
						// $('#my-alert').modal('open');
						swal('验证失败，请注意格式！');
						return false;
					}
				}
			});
		},
		/**
		 * 修改
		 */
		setForm : function(data) {
			var _this = this, i;
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
			$("#spbm").find('option[value=' + data.spbm + ']').attr('selected',
					true);
		},
		resetForm : function() {
			el.$jsForm[0].reset();
		},
		modalAction : function() {
			var _this = this;
			el.$modal.on('closed.modal.amui', function() {
				_this.resetForm();
			});
		},

		/**
		 * 添加model
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
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable();
			_this.search_ac();
			_this.add_ac();
			_this.import_ac();
			_this.import();
			_this.export_ac();
			_this.checkAllAc();
			_this.delAllAc();
			_this.editRow();
			_this.addRow();
			_this.modalAction(); // hidden action
			_this.modalAction1(); // hidden action
		}
	};
	action.init();
	// 增加
	$("#smmc1").on('change', function() {
		var smdm = $(this).val();
		var text = $(this).find("option[value=" + smdm + "]").text();
		var pos = text.indexOf("(");
		var sl = text.substring(pos + 1, text.length - 1);
		$("#sl1").val(sl);
	});
	// 修改
	$("#smmc").on('change', function() {
		var smdm = $(this).val();
		var text = $(this).find("option[value=" + smdm + "]").text();
		var pos = text.indexOf("(");
		var sl = text.substring(pos + 1, text.length - 1);
		$("#sl").val(sl);
	});
});