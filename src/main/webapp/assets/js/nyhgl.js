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
		$jsTable1 : $('#button2'),
		$yhmm : $('#yhmm'),
		$yhzh : $('#yhzh'),
		$qrmm : $('#qrmm'),
		$lab : $('#dlmm'),
		$sjhm : $('#sjhm'),
		$yx : $('#yx'),
		$lab1 : $('#dlmm1'),
		$jsLoading : $('.js-modal-loading')
	};
	el.$modalHongchong.draggable();
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
						"ajax" : {
							url : _this.config.getUrl,
							type : 'POST',
							data : function(d) {

								d.yhzh = el.$s_yhzh.val(); // search 用户账号
								d.yhmc = el.$s_yhmc.val(); // search 用户名称

							}
						},
						"columns" : [
								{
									"orderable" : false,
									"data" : null,
									"defaultContent" : ""
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
								{
									"data" : null,
									"render" : function(data) {
										if (data.sup == '否') {
											return '<a class="xiugai">修改</a> <a class="shanchu">删除</a> <a class="chongzhimima">重置密码</a>'
										} else {
											return '<a class="xiugai"></a> <a class="shanchu"></a>'
										}

									}
								// "defaultContent": ' <a class="view" href="' +
								// data.href+ '" target="_blank">查看</a> <a
								// class="hongchong">红冲</a> '
								} ]
					});
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(0).nodes().each(function(cell, i) {
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
				el.$yhzh.removeAttr("readonly");
				ur = _this.config.xzUrl
				el.$modalHongchong.modal({"width": 750, "height": 500});
			});
			/**
			 * 重置密码
			 */
			el.$jsSubmit1.on('click', el.$jsSubmit1, function(e) {
				e.preventDefault();
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
							for(i = 0; i < list1.length; i++){
								$('#roles-'+list1[i]).prop('checked', true);
							}
							el.$modalHongchong.modal({"width": 750, "height": 500});
						} else {
							alert('修改用户失败');
						}
					}
				});
				ur = _this.config.xgUrl
			});
			// 删除
			t.on('click', 'a.shanchu', function() {
				var da = t.row($(this).parents('tr')).data();
				if (confirm("确定要删除该用户吗")) {
					_this.sc(da);
				}
				_this.resetForm();
			});
			// 重置密码
			t.on('click', 'a.chongzhimima', function() {
				el.$chongzhi.modal('open');
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
					var xfids = document.getElementsByName("xfid");
					var jsids = document.getElementsByName("jsid");
					var fl = false;
					var ag = false;
					for (var i = 0; i < xfids.length; i++) {
						if (xfids[i].checked == true) {
							fl = true;
							break;
						}
					}
					if (fl == false) {
						alert("请选择可操作的销方企业!");
						return false;
					}
					if (jsids == null || jsids == "" || jsids == '') {
						alert("请选择角色!");
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
									alert(data.msg);
									_this.tableEx.ajax.reload(); // reload table
								} else {
									el.$jsLoading.modal('close'); // close
																	// loading
									// el.$modalHongchong.modal('close'); //
									// close
									alert(data.msg);

								}
								// data

							},
							error : function() {
								el.$modalHongchong.modal('close'); // close
								el.$jsLoading.modal('close'); // close loading
								alert('操作失败!');
							}
						});
						return false;
					} else {
						alert('验证失败,请注意红色输入框内格式!');
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
					"dlyhid" : da.dlyhid
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {

						// modal
						alert(data.msg);
					} else {

						alert('删除用户失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					alert('新增用户失败, 请重新登陆再试...!');
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

						// modal
						alert(data.msg);
					} else {

						alert('重置用户密码失败: ' + data.msg);

					}
					el.$chongzhi.modal('close');
					// _this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					alert('操作失败, 请重新登陆再试...!');
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
				$('#xb1').append('<option value = "0">男</option><option value = "1">女</option>');
			} else {
				$('#xb1').append('<option value = "1">女</option><option value = "0">男</option>');
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
			_this.modalAction(); // hidden action
		}
	};

	action.init();

});