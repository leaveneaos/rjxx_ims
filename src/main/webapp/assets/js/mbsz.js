/**
 * feel
 */
$(function() {
	"use strict";
	var ur;
	var el = {
		$jsTable : $('.js-table'),
		$modalHongchong : $('#hongchong'),
		$jsSubmit : $('.js-submit'),
		$jsClose : $('.js-close'),
		$jsForm0 : $('#importConfigForm'), // 红冲 form
		$s_xfsh : $('#xfsh'), // search
		$c_mbmc : $('#c_mbmc'), // search
		$dpmax:$('#dzpzdje'),
		$fpfz:$('#dzpfpje'),
		$zpmax:$('#zpzdje'),
		$zpfz:$('#zpfpje'),
		$ppmax:$('#ppzdje'),
		$ppfz:$('#ppzdje'),
		$jsSearch :$("#kp_search"),
		$jsSearch1 : $('#kp_search1'),
		$importExcelForm : $('#importExcelForm'),
		$jsTable1 : $('#button2'),
		$jsLoading : $('.js-modal-loading')
	};
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'mbsz/getItem',
			addUrl : 'mbsz/saveImportConfig',
			editUrl : 'mbsz/saveImportConfig',
			scUrl : 'mbsz/deleteMb',
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
						"ajax" : {
							url : _this.config.getUrl,
							type : 'POST',
							data : function(d) {
								d.xfsh = el.$s_xfsh.val(); // search 用户账号
								d.mbmc = el.$c_mbmc.val(); // search 用户名称
								d.gxbz =  $('#sfgx').val();
								var csm =  $('#dxcsm').val();
								//alert($('#sfgx').val());
					                if("s_mbmc"==csm&&(d.mbmc==null||d.mbmc=="")){
					                    d.mbmc = $('#dxcsz').val()
					                 }
							}
						},
						"columns" : [
								 {
	                        		"orderable" : false,
	                        		"data" : null,
	                        		render : function(data, type, full, meta) {
	                        			return '<input type="checkbox" name= "chk" data="'
	                        				+ data.id + '" />';
	                        		}
	                        	},
	                        	/*{
								"orderable" : false,
								"data" : null,
								"defaultContent" : ""
							    },*/
								{
									"data" : "id"
								},
								{
									"data" : "mbmc"
								},
								{
									"data" : "xfmc"
								},
								{
									"data" : "xfsh"
								},
								{
									"data" : "gxbz"
								},
								{
									"data": function (data) {
						                if (data.gxbz == "1") {
						                    return "共享";
						                }else{
						                    return "不共享";
						                }
									}
								},
								{
									"data": function(data) {
										if (data.tybz == "1") {
											return "<a class='xiazai' href='#'>下载</a>";
										} else {
											return "<a class='modify' href='#'>修改</a>  <a class='xiazai' href='#'>下载</a> "	;	
										}
									}						
								} ]
					});
			//<a href='#' class='del'>删除</a>
			 $('#check_all').change(function () {
	            	if ($('#check_all').prop('checked')) {
	            		t.column(0).nodes().each(function (cell, i) {
	                        $(cell).find('input[type="checkbox"]').prop('checked', true);
	                    });
	                } else {
	                	t.column(0).nodes().each(function (cell, i) {
	                        $(cell).find('input[type="checkbox"]').prop('checked', false);
	                    });
	                }
	            });
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				/*t.column(0).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});*/
				$('#tbl tr').find('td:eq(1)').hide();
				$('#tbl tr').find('td:eq(5)').hide();
			});

			// 新增
			el.$jsTable1.on('click', el.$jsTable1, function() {
				_this.resetForm();
				ur = _this.config.addUrl;
				el.$modalHongchong.modal({"width": 700, "height": 600});
				$("#btnImportConfigSave").attr("disabled",false);
			});
			// 查看
//			t.on('click', 'a.detail', function() {
//				_this.resetForm();
//				var row = t.row($(this).parents('tr')).data();
//	        	var mbid = row.id;
//				var url = "mbsz/initImport";
//				$.post(url, {mbid:mbid}, function (res) {
//
//					if (row.gxbz=="1") {
//						$("#yes").prop("checked", true);
//					}else{
//						$("#no").prop("checked", true);
//					}
//					
//                    $('#mbmc').val(row.mbmc);
//                    $('#mbid').val(mbid);
//	                if (res && res.length > 0) {
//	                    for (var i = 0; i < res.length; i++) {
//	                        var obj = res[i];
//	                        var zdm = obj["zdm"];
//	                        var pzlx = obj["pzlx"];
//	                        var pzz = obj["pzz"];
//	                        if (zdm != "gs") {
//	                        	$("input[name=config_" + zdm + "_radio][value=" + pzlx + "]").prop("checked", true);
//		                        if ("hsbz" == zdm) {
//		                            $("input[name=config_" + zdm + "][value=" + pzz + "]").prop("checked", true);
//		                        } else {
//		                            $("input[name=config_" + zdm + "]").val(pzz);
//		                        }
//							}
//	                    }
//	                }
//	            });	
//				$("#btnImportConfigSave").attr("disabled",true);
//				el.$modalHongchong.modal({"width": 700, "height": 600});
//			});
			// 修改
			t.on('click', 'a.modify', function() {
				var row = t.row($(this).parents('tr')).data();
	        	var mbid = row.id;
				var url = "mbsz/initImport";
				$.post(url, {mbid:mbid}, function (res) {

					if (row.gxbz=="1") {
						$("#yes1").prop("checked", true);
					}else{
						$("#no1").prop("checked", true);
					}
					
                    $('#mbmc1').val(row.mbmc);
                    $('#mbid1').val(mbid);
	                if (res && res.length > 0) {
	                    for (var i = 0; i < res.length; i++) {
	                        var obj = res[i];
	                        var zdm = obj["zdm"];
	                        var pzlx = obj["pzlx"];
	                        var pzz = obj["pzz"];
	                        if (zdm != "gs") {
	                        	$("input[name=config_" + zdm + "_radio][value=" + pzlx + "]").prop("checked", true);
		                        if ("hsbz" == zdm) {
		                            $("input[name=config_" + zdm + "][value=" + pzz + "]").prop("checked", true);
		                        } else {
		                            $("input[name=config_" + zdm + "]").val(pzz);
		                        }
							}
	                    }
	                }
	            });	
				$("#btnImportConfigSave").attr("disabled",false);
				$('#bulk-import-div').modal({"width": 700, "height": 600});
				ur = _this.config.editUrl + "?mbid="+mbid;
			});
			
			// 下载
			t.on('click', 'a.xiazai', function() {
				var row = t.row($(this).parents('tr')).data();
	        	var mbid = row.id;
	        	var xzlj = row.xzlj;
	        	if(xzlj!=null&&xzlj!=""){
					  window.location.href='lrkpd/downloadDefaultImportTemplate?xzlj='+xzlj;
					}else{
						window.location.href='kpdsh/xzmb?mbid='+mbid;
					}
	        	})
			// 删除
			t.on('click', 'a.del', function() {
				var da = t.row($(this).parents('tr')).data();
				if (confirm("确定要删除该模板吗")) {
					_this.sc(da);
				}
				var data = t.row($(this).parents('tr')).data();
				_this.resetForm();
			});

		     //删除
			$("#del").on('click', function() {
	            var mbidArr = [];
	            $("input[type='checkbox']:checked").each(function (i, o) {
	            	if ($(o).attr("data") != null) {
	                    mbidArr.push($(o).attr("data"));
					}
	            });
	            if (mbidArr.length == 0) {
	                alert("请选择需要删除的模板记录...");
	                return;
	            }
	            if (confirm("您确认删除？")) {
	                $.post("mbsz/doDel",
							"mbidArr="+ mbidArr.join(","),
							function(res) {
								if (res.success) {
									alert("删除成功");
									_this.tableEx.ajax.reload();
								}else{
									alert(res.msg);
								}
					});
				}
			});
			
	        var $importModal = $("#bulk-import-div");
	        $("#close2").click(function () {
	        	$("#hongchong").modal("close");
	        });
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
				$("#ycform").resetForm();
				e.preventDefault();
				_this.tableEx.ajax.reload();
			});
			el.$jsSearch1.on('click', function(e) {
				e.preventDefault();
				  $("#dxcsz").val("");
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
					"mbid" : da.id
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {
						alert("删除成功");
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
			el.$jsForm0.find('input[name="dzpzdje"]').val(data.dzpzdje);
			el.$jsForm0.find('input[name="dzpfpje"]').val(data.dzpfpje);
			el.$jsForm0.find('input[name="zpzdje"]').val(data.zpzdje);
			el.$jsForm0.find('input[name="zpfpje"]').val(data.zpfpje);
			el.$jsForm0.find('input[name="ppzdje"]').val(data.ppzdje);
			el.$jsForm0.find('input[name="ppfpje"]').val(data.ppfpje);
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