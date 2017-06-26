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
		$modalHongchong1 : $('#hongchonga'),
		$chongzhi : $('#chongzhi'),
		$jsSubmit : $('.js-submit'),
		$jsClose : $('.js-close'),
		$jsSubmit1 : $('.js-submit1'),
		$jsClose1 : $('.js-close1'),
		$jsForm0 : $('#fomm'), // 红冲 form
		$jsForma : $('#fomma'), // 红冲 form
		$s_yhzh : $('#csid'), // search
		$s_yhmc : $('#csjb'), // search
		$jsSearch : $('#button1'),
		$jsTable1 : $('#button2'),
		$xzcs : $('#xzxzcs'),
		$jsLoading : $('.js-modal-loading')
	};
	el.$modalHongchong.draggable();
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'csb/getCsbList',
			xzCsbUrl : 'csb/getXzCsb',
			xzUrl : 'csb/csxz',
			xgUrl : 'csb/xgCsb'
		},
		dataTable : function() {
			var _this = this;
			var t = el.$jsTable.DataTable({
				"processing" : true,
				"serverSide" : true,
				ordering : false,
				searching : false,
				"ajax" : {
					url : _this.config.getUrl,
					type : 'POST',
					data : function(d) {

						d.csid = el.$s_yhzh.val(); // search 用户账号
						d.csjb = el.$s_yhmc.val(); // search 用户名称

					}
				},
				"columns" : [ {
					"orderable" : false,
					"data" : null,
					"defaultContent" : ""
				}, {
					"data" : null,
					"render" : function(data) {
						return '<a class="xiugai">修改</a>   '+'<a class="shanchu">删除</a>';

					}
				// "defaultContent": ' <a class="view" href="' +
				// data.href+ '" target="_blank">查看</a> <a
				// class="hongchong">红冲</a> '
				}, {
					"data" : "csm"
				}, {
					"data" : "csmc"
				}, {
					"data" : "gsdm"
				}, {
					"data" : "xfmc"
				}, {
					"data" : "xfsh"
				}, {
					"data" : "kpdmc"
				}, {
					"data" : "csz"
				},
				 {
					"data" : "id"
				},
				 {
					"data" : "xfid"
				},
				 {
					"data" : "kpdid"
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
				t.column(0).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
				$('#tbl tr').find('td:eq(4)').hide();
				$('#tbl tr').find('td:eq(9)').hide();
				$('#tbl tr').find('td:eq(10)').hide();
				$('#tbl tr').find('td:eq(11)').hide();
			});

			// 新增
			el.$jsTable1.on('click', el.$jsTable1, function() {
				_this.resetForm();
				el.$xzcs.html("");
				var csid = el.$s_yhzh.val(); // search 用户账号
				var csjb = el.$s_yhmc.val(); // search 用户名称
				var trs;
				$.ajax({
					url : _this.config.xzCsbUrl,
					data : {
			/*			csid : csid,
						csjb : csjb*/
					},
					 async: false,
					type : 'post',
					success : function(data) {
						trs="<option value=''>请选择</option>";
						$.each(data.list, function(n, value) {
						
							trs +="<option value= '"+value.id+"'>"+value.csmc+"("+value.csm+")</option>"
						});
						el.$xzcs.append(trs);
					}
				})
				ur = _this.config.xzUrl
				el.$modalHongchong.modal('open');
			});
			// 修改
			t.on('click', 'a.xiugai', function() {
				var row = t.row($(this).parents('tr')).data();
				_this.resetForm1();
				$('#cszid').val(row.id);
				$('#xgxzcs').val(row.csmc+"("+row.csm+")");
				$('#xgxzxf').val(row.xfid);
				$.ajax({
					url : 'csb/xzskp',
					data : {
						xfid : row.xfid
					},
					type : 'post',
					 async: false,
					success : function(data) {
						$('#xgxzkpd').html("");
						var trs="<option value=''>请选择</option>";
						$.each(data.skp, function(n, value) {
							trs +="<option value= '"+value.id+"'>"+value.kpdmc+"("+value.kpddm+")</option>"
						});
						$('#xgxzkpd').append(trs);
					}
				})
				$('#xgxzkpd').val(row.kpdid)
				if("是"==row.csz||"否"==row.csz){
					$('#xgxzcsz').hide();
					$('#xgxzcsz2').show();
					$('#xgxzcsz3').val(row.csz);
					$('#xgxzcsz1').val("");
				}else{
					$('#xgxzcsz2').hide();
					$('#xgxzcsz').show();
					$('#xgxzcsz1').val(row.csz);
					$('#xgxzcsz3').val("");
				}
				ur = _this.config.xgUrl
				el.$modalHongchong1.modal('open');

			});
			// 删除
			t.on('click', 'a.shanchu', function() {
				
				  $('#my-confirm').modal({
				        relatedTarget: this,
				        onConfirm: function(options) {
				        	var row = t.row($(this.relatedTarget).parents('tr')).data();
							$.ajax({
								url : 'csb/scCsb',
								data : {"csid":row.id},
								method : 'POST',
								success : function(data) {
									if(data.msg){
										// $("#alertt").html("操作成功");
										// $("#my-alert").modal('open')
										swal("操作成功");
										_this.tableEx.ajax.reload();
									}
								},
								error : function() {
									el.$modalHongchong.modal('close'); // close
									el.$jsLoading.modal('close'); // close loading
									// $("#alertt").html("操作失败");
									// $("#my-alert").modal('open')
									swal("操作失败");
								}
							});
				        }
				      });
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
					var csb = $('#xzxzcs').val();
					var csz = $('#xzxzcsz1').val();
					var csz1 = $('#xzxzcsz3').val();
					if(null==csb||""==csb){
						// $("#alertt").html("请选择参数");
						// $("#my-alert").modal('open')
						swal("请选择参数");
						return false;
					}else if((null==csz&&null==csz1)||(""==csz&&""==csz1)){
						// $("#alertt").html("请选择或输入参数值");
						// $("#my-alert").modal('open')
						swal("请选择或输入参数值");
						return false;
					}
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading
						var data = el.$jsForm0.serialize(); // get form data
						$.ajax({
							url : ur,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									el.$jsLoading.modal('close'); // close
									el.$modalHongchong.modal('close'); // close
									// $("#alertt").html(data.msg);
									// $("#my-alert").modal('open')
									swal(data.msg);
								} else {
									el.$jsLoading.modal('close'); // close
									// $("#alertt").html(data.msg);
									// $("#my-alert").modal('open')
									swal(data.msg);
								}
								_this.tableEx.ajax.reload(); // reload table
								// data

							},
							error : function() {
								el.$modalHongchong.modal('close'); // close
								el.$jsLoading.modal('close'); // close loading
								// $("#alertt").html("操作失败");
								// $("#my-alert").modal('open')
								swal("操作失败");
							}
						});
						return false;
					} else {
						// $("#alertt").html("验证失败,请注意红色输入框内格式!");
						// $("#my-alert").modal('open')
						swal("验证失败,请注意红色输入框内格式!");
						return false;
					}
				}
			});
		},
		/**
		 * 修改保存
		 */
		xg : function() {
			var _this = this;
			el.$jsForma.validator({
				submit : function() {
					var formValidity = this.isFormValid();
					var csz = $('#xgxzcsz1').val();
					var csz1 = $('#xgxzcsz3').val();
					if((null==csz&&null==csz1)||(""==csz&&""==csz1)){
						// $("#alertt").html("请选择或输入参数值");
						// $("#my-alert").modal('open')
						swal("请选择或输入参数值");
						return false;
					}
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading
						var data = el.$jsForma.serialize(); // get form data
						$.ajax({
							url : ur,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									el.$jsLoading.modal('close'); // close
									el.$modalHongchong1.modal('close'); // close
									// $("#alertt").html(data.msg);
									// $("#my-alert").modal('open')
									swal(data.msg);
								} else {
									el.$jsLoading.modal('close'); // close
									// $("#alertt").html(data.msg);
									// $("#my-alert").modal('open')
									swal(data.msg);
								}
								_this.tableEx.ajax.reload(); // reload table
								// data

							},
							error : function() {
								el.$modalHongchong1.modal('close'); // close
								el.$jsLoading.modal('close'); // close loading
								// $("#alertt").html("操作失败");
								// $("#my-alert").modal('open')
								swal("操作失败");
							}
						});
						return false;
					} else {
						// $("#alertt").html("验证失败");
						// $("#my-alert").modal('open')
						swal("验证失败");
						return false;
					}
				}
			});
		},
		resetForm : function() {
			el.$jsForm0[0].reset();
		},
		resetForm1 : function() {
			el.$jsForma[0].reset();
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
				el.$modalHongchong1.modal('close');
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
			_this.xz();
			_this.xg();
			_this.modalAction(); // hidden action
		}
	};

	action.init();

});