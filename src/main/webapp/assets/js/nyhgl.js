/**
 * feel
 */
$(function() {
	"use strict";
	var ur;
	var da1;
	var el = {
		$jsTable : $('.js-table'),
		$jsxb : $('#xb'),
		$modalHongchong : $('#hongchong'),
		$chongzhi : $('#chongzhi'),
		$jsSubmit : $('.js-submit'),
		$jsClose : $('.js-close'),
		$jsSubmit1 : $('#jsSubmit'),
		$jsClose1 : $('.js-close1'),
		$jsForm0 : $('#fomm'), // 红冲 form
		$jsForm1 : $('.js-form-1'), // 红冲 form
		$s_yhzh : $('#s_yhzh'), // search
		$s_yhmc : $('#s_yhmc'), // search
		$jsSearch : $('#button1'),
		$jsSearch1 : $('#button3'),
		$jsTable1 : $('#button2'),
		$yhmm : $('#yhmm'),
		$yhzh : $('#yhzh'),
		$qrmm : $('#qrmm'),
		$lab : $('#dlmm'),
		$sjhm : $('#sjhm'),
		$yx : $('#yx'),
		$lab1 : $('#dlmm1'),
		$checkAll : $('#check_all'), // check all checkbox
		$jsdel : $('.js-sent'), // del all
		$jsLoading : $('.js-modal-loading')
	};
	// el.$modalHongchong.draggable();
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'nyhgl/getYhList',
			xzUrl : 'nyhgl/yhxz',
			xgUrl : 'nyhgl/yhxg',
			xgcxUrl : 'nyhgl/yhxgcx',
			scUrl : 'nyhgl/yhsc',
			czUrl : 'nyhgl/resetPassword'
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
								var tip = $('#tip').val();
								var txt = $('#searchtxt').val();
								var yhzh = el.$s_yhzh.val();
								var yhmc = el.$s_yhmc.val();
								if (tip == 1) {
									yhzh = txt;
								} else if (tip == 2) {
									yhmc = txt;
								}
								d.yhzh = yhzh;
								d.yhmc = yhmc;

							}
						},
						"columns" : [
								{
									"orderable" : false,
									"data" : null,
									// "defaultContent": '<input type="checkbox"
									// />'
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
									"data" : null,
									"render" : function(data) {
										if (data.sup == '否') {
											return '<a class="xiugai">修改</a> <a class="chongzhimima">重置密码</a>'
										} else {
											return '<a class="xiugai"></a> <a class="shanchu"></a>'
										}

									}
								// "defaultContent": ' <a class="view" href="' +
								// data.href+ '" target="_blank">查看</a> <a
								// class="hongchong">红冲</a> '
								},
								{
									"data" : "yhmc"
								},
								{
									"data" : "xb"
								},
								{
									"data" : "dlyhid"
								},
								{
									"data" : "jsmc"
								},
								{
									"data" : "sjhm"
								},
								{
									"data" : "yx"
								},
								{
									"data" : "sup"
								},
								/*
								 * { "data": function (data) { if (data.je) {
								 * return FormatFloat(data.je, "###,###.00");
								 * }else{ return null; } }, 'sClass': 'right' }, {
								 * "data": function (data) { if (data.se) {
								 * return FormatFloat(data.se, "###,###.00");
								 * }else{ return null; } }, 'sClass': 'right' }, {
								 * "data": function (data) { if (data.jshj) {
								 * return FormatFloat(data.jshj, "###,###.00");
								 * }else{ return null; } }, 'sClass': 'right' },
								 */
								]
					});
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
			});

			// 新增
			el.$jsTable1.on('click', el.$jsTable1, function() {
				_this.resetForm();
				el.$yhmm.val();
				el.$qrmm.val();
				el.$yhmm.show();
				el.$qrmm.show();
				el.$lab.show();
				el.$lab1.show();
				$('#mm1').show();
				$('#mm2').show();
				$('#mm3').show();
				el.$yhzh.removeAttr("readonly");
				ur = _this.config.xzUrl
				el.$modalHongchong.modal({
					"width" : 750,
					"height" : 500
				});
			});
			/**
			 * 重置密码
			 */
			el.$jsSubmit1.on('click', el.$jsSubmit1, function(e) {
				e.preventDefault();
				var pass1 = $('#yhmm2').val();
				var pass2 = $('#qrmm1').val();
				if (pass1 == "") {
					// $('#msg').html('请重新输入密码');
					// $('#my-alert').modal('open');
					swal('请重新输入密码');
					return;
				}
				if (pass2 == "") {
					// $('#msg').html('请重新输入确认密码');
					// $('#my-alert').modal('open');
					swal('请重新输入确认密码');
					return;
				}
				if (pass1 != pass2) {
					// $('#msg').html('两次密码输入不一致，请重新输入');
					// $('#my-alert').modal('open');
					swal('两次密码输入不一致，请重新输入');
					return;
				}
				_this.cz(da1);
			});
			el.$chongzhi.modal('close');
			;
			// 修改
			t.on('click', 'a.xiugai', function() {
				var row = t.row($(this).parents('tr')).data();
				_this.setForm0(row);
				el.$yhmm.val("1qQ");
				el.$qrmm.val("1qQ");
				el.$yhmm.hide();
				el.$qrmm.hide();
				$('#mm1').hide();
				$('#mm2').hide();
				$('#mm3').hide();
				el.$lab.hide();
				el.$lab1.hide();
				el.$yhzh.attr({
					readonly : 'true'
				});
				$.ajax({
					url : _this.config.xgcxUrl,
					data : {
						dlyhid : row.dlyhid
					},
					type : 'post',
					success : function(data) {
						var i, rp, rp1, slt, slt1, slt2;
						var list = data.list;
						var list1 = data.list1;
						var $bmbox = $('#bm-box1');
						$bmbox.find(':checkbox').prop('checked', false);
						var $bmbox1 = $('#bm-box2');
						$bmbox1.find(':checkbox').prop('checked', false);
						if (data.msg) {
							for (i = 0; i < list.length; i++) {
								rp = list[i];
								slt = '#yhjg1-' + rp.xfid;
								slt2 = '#skp-' + rp.skpid
								$(slt).prop('checked', true);
								$(slt2).prop('checked', true);
							}
							for (i = 0; i < list1.length; i++) {
								$('#roles-' + list1[i]).prop('checked', true);
							}
							el.$modalHongchong.modal({
								"width" : 750,
								"height" : 500
							});
						} else {
							// $('#msg').html('修改用户失败');
							// $('#my-alert').modal('open');
							swal('修改用户失败');
						}
					}
				});
				ur = _this.config.xgUrl
			});
			// 删除
			t.on('click', 'a.shanchu', function() {
				$('#my-confirm').modal(
						{
							relatedTarget : this,
							onConfirm : function(options) {
								var da = t.row(
										$(this.relatedTarget).parents('tr'))
										.data();
								_this.sc(da);
							},
							// closeOnConfirm: false,
							onCancel : function() {

							}
						});
				_this.resetForm();
			});
			// 重置密码
			t.on('click', 'a.chongzhimima', function() {
				el.$chongzhi.modal({
					"width" : 600,
					"height" : 400
				});
				da1 = t.row($(this).parents('tr')).data();

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
				$('#searchform').resetForm();
				$('#tip').find('option[value=0]').attr('selected', true);
				_this.tableEx.ajax.reload();
			});
			el.$jsSearch1.on('click', function(e) {
				e.preventDefault();
				$('#searchform1').resetForm();
				_this.tableEx.ajax.reload();
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
					// $('#msg').html("请选择要删除的用户");
					// $('#my-alert').modal('open');
					swal("请选择要删除的用户");
					return;
				}
				data = data.substring(0, data.length - 1);
				var url = _this.config.scUrl;


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
                	el.$jsLoading.modal('open');
                    $.ajax({
                        url : url,
						data : {ids : data},
						type : 'POST',
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
  	                    el.$jsLoading.modal('close');
                        
                    }).error(function(data) {
                        swal('请求失败,请刷新后稍后重试!', "error");
                        el.$jsLoading.modal('close');
                    });
                });


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
					var xfids = document.getElementsByName("xfid");
					var jsids = document.getElementsByName("jsid");
					var pass1 = $('#yhmm').val();
					var pass2 = $('#qrmm').val();
					var fl = false;
					var ag = false;
					for (var i = 0; i < xfids.length; i++) {
						if (xfids[i].checked == true) {
							fl = true;
							break;
						}
					}
					if (fl == false) {
						// $('#msg').html("请选择可操作的销方企业!");
						// $('#my-alert').modal('open');
						swal("请选择可操作的销方企业!");
						return false;
					}
					for(var i = 0; i < jsids.length; i++) {
						// if (jsids[i] == null || jsids[i] == "" || jsids[i] == '') {
						// 	// $('#msg').html("请选择角色!");
						// 	// $('#my-alert').modal('open');
						// 	swal("请选择角色!");
						// 	return false;
						// }
						if (jsids[i].checked == true) {
							fl = true;
							break;
						}else{
							swal("请选择角色!");
							return false;
						}
					}
					
					if (pass1 != pass2) {
						// $('#msg').html("两次密码输入不一致，请重新输入!");
						// $('#my-alert').modal('open');
						swal("两次密码输入不一致，请重新输入!");
						return false;
					}
					if (formValidity) {

						el.$jsLoading.modal('toggle'); // show loading
						// alert('验证成功');
						var data = el.$jsForm0.serialize(); // get form data
						// data
						// TODO save data to serve
						$.ajax({
							url : ur,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									el.$jsLoading.modal('close'); // close
									// loading
									el.$modalHongchong.modal('close'); // close
									// modal
									// $('#msg').html(data.msg);
									// $('#my-alert').modal('open');
									swal(data.msg);
									_this.tableEx.ajax.reload(); // reload
									// table
								} else {
									el.$jsLoading.modal('close');
									// $('#msg').html(data.msg);
									// $('#my-alert').modal('open');
									swal(data.msg);

								}
								// data

							},
							error : function() {
								el.$modalHongchong.modal('close'); // close
								el.$jsLoading.modal('close'); // close loading
								// $('#msg').html('操作失败!');
								// $('#my-alert').modal('open');
								swal('操作失败!');
							}
						});
						return false;
					} else {
						// $('#msg').html('验证失败,请注意红色输入框内格式!');
						// $('#my-alert').modal('open');
						swal('验证失败,请注意红色输入框内格式!');
						return false;
					}
				}
			});
		},
		/**
		 * 删除
		 */
		sc : function(da) {
			$('.confirm').attr('disabled',"disabled");
			var _this = this;
			$.ajax({
				url : _this.config.scUrl,
				data : {
					"dlyhid" : da.dlyhid
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {
						// $('#msg').html(data.msg);
						// $('#my-alert').modal('open');
						$('.confirm').removeAttr('disabled');
						swal(data.msg);
					} else {
						// $('#msg').html('删除用户失败: ' + data.msg);
						// $('#my-alert').modal('open');
						swal('删除用户失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					// $('#msg').html('删除用户失败, 请重新登陆再试...!');
					// $('#my-alert').modal('open');
					swal('删除用户失败, 请重新登陆再试...!');
				}
			});

		},
		/**
		 * 重置密码
		 */
		cz : function(da) {
			var _this = this;
			var czmm = el.$jsForm1.find('input[name="yhmm2"]').val();
			$.ajax({
				url : _this.config.czUrl,
				data : {
					"dlyhid" : da.dlyhid,
					"czmm" : czmm
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {
						// $('#msg').html(data.msg);
						// $('#my-alert').modal('open');
						swal(data.msg);
					} else {
						// $('#msg').html('重置用户密码失败: ' + data.msg);
						// $('#my-alert').modal('open');
						swal('重置用户密码失败: ' + data.msg);

					}
					el.$chongzhi.modal('close');
					// _this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					// $('#msg').html('操作失败, 请重新登陆再试...!');
					// $('#my-alert').modal('open');
					swal('操作失败, 请重新登陆再试...!');
				}
			});

		},
		setForm0 : function(data) {
			var _this = this, i;
			// todo set data
			// debugger
			el.$jsForm0.find('input[name="yhmc"]').val(data.yhmc);
			el.$jsForm0.find('input[name="yhzh"]').val(
					data.dlyhid.substring(data.dlyhid.indexOf("_") + 1));
			el.$jsForm0.find('input[name="sjhm"]').val(data.sjhm);
			el.$jsForm0.find('input[name="yx"]').val(data.yx);
			var tmp;
			$('#xb1').empty();
			if ('男' == data.xb) {
				$('#xb1')
						.append(
								'<option value = "0">男</option><option value = "1">女</option>');
			} else {
				$('#xb1')
						.append(
								'<option value = "1">女</option><option value = "0">男</option>');
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
		resetForm1 : function() {
			el.$jsForm1[0].reset();
		},
		modalAction : function() {
			var _this = this;
			el.$modalHongchong.on('closed.modal.amui', function() {
				el.$jsForm0[0].reset();
			});
			// close modal
			el.$jsClose.on('click', function() {
				el.$modalHongchong.modal('close');
			});
			el.$jsClose1.on('click', function() {
				el.$chongzhi.modal('close');
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
			_this.xz();
			_this.checkAllAc();
			_this.delAllAc();
			_this.modalAction(); // hidden action
		}
	};

	action.init();

});