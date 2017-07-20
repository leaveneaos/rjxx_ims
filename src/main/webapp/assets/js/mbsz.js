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

		$c_mbmc : $('#c_mbmc'), // search

		$jsSearch :$("#mb_search"),

		$jsSearch1 : $('#mb_search1'),

		$jsAdd : $('#button2'),

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
								//d.xfsh = el.$s_xfsh.val(); // search 用户账号
								d.mbmc = el.$c_mbmc.val(); // search 用户名称
								d.gxbz =  $('#sfgx').val();
								var csm =  $('#dxcsm').val();
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
								{
									"data" : "id"
								},
								{
									"data": function(data) {
										if (data.tybz == "1") {
											return "<a class='xiazai' href='#'>下载</a>";
										} else {
											return "<a class='modify' href='#'>修改</a>  <a class='xiazai' href='#'>下载</a> "	;	
										}
									}						
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
								]
					});
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
				$('#tbl tr').find('td:eq(1)').hide();
				$('#tbl tr').find('td:eq(6)').hide();
			});

			// 新增
			el.$jsAdd.on('click', el.$jsAdd, function() {
				_this.resetForm();
				ur = _this.config.addUrl;
				el.$modalHongchong.modal({"width": 700, "height": 650});
				$("#btnImportConfigSave").attr("disabled",false);
			});


			// 修改
			t.on('click', 'a.modify', function() {
                _this.resetForm();
                var row = t.row($(this).parents('tr')).data();
	        	var mbid = row.id;
				var url = "mbsz/initImport";
				$.post(url, {mbid:mbid}, function (res) {
					if (row.gxbz=="1") {
                        $("#config_gs_radio").find("option[value='1']").prop("selected",true);
					}else{
                        $("#config_gs_radio").find("option[value='0']").prop("selected",true);
                    }
                    $('#mbmc').val(row.mbmc);
                    $('#mbid').val(mbid);
	                if (res && res.length > 0) {
	                    for (var i = 0; i < res.length; i++) {
	                        var obj = res[i];
	                        var zdm = obj["zdm"];
	                        var pzlx = obj["pzlx"];
	                        var pzz = obj["pzz"];

	                        if(zdm.indexOf('xf')!=0&&zdm!="skr"&&zdm!="kpr"&&zdm!="fhr"){
								if (zdm != "gs") {
                                    $("#config_" + zdm + "_radio").val(pzlx);
                                    if ("hsbz" == zdm||"fpzldm" == zdm) {
										if(pzlx=="auto"){
                                            $("#config_" + zdm +"_input").css("display","none");
                                            $("#config_" + zdm ).css("display","");
                                            $("#config_" + zdm).val(pzz);
                                        }else if(pzlx=="config"){
                                            $("#config_" + zdm).css("display","none");
                                            $("#config_" + zdm +"_input").css("display","");
                                            $("#config_" + zdm +"_input").val(pzz);
										}
									}else{
                                        $("#config_" + zdm).val(pzz);
									}
								}
                            }
	                    }
	                }
	            });
				ur = _this.config.editUrl + "?mbid="+mbid;
                el.$modalHongchong.modal({"width": 700, "height": 650});
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
	                swal("请选择需要删除的模板记录...");
	                return;
	            }
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
				        $.post(
				            "mbsz/doDel",
				            "mbidArr="+ mbidArr.join(","),
				            function(res) {
				                if (res.success) {
				                	$('.confirm').removeAttr('disabled');
				                    swal({
				                        title: "删除成功", 
				                        timer: 1500, 
									  	type: "success", 
				                        showConfirmButton: false 
				                    });
				                    _this.tableEx.ajax.reload();
				                }else{
				                    swal(res.msg);
				                }
				            }
				        );
				    }
				)


			});
			

	        $("#close2").click(function () {
	        	$("#hongchong").modal("close");
	        });
            $("#btnImportConfigSave").click(function () {
                var data = $("#importConfigForm").serialize();
                var mbmc = $('#mbmc').val();
                var gfmc = $('#config_gfmc').val();
                var spje = $('#config_spje').val();
                var fpzldm = $('#config_fpzldm').val();
                if (mbmc == null || mbmc == '') {
                    swal("请输入模板名称");
                    return;
                }
                if (gfmc == null || gfmc == '') {
                    swal("购方名称不能为空");
                    return;
                }
                if (spje == null || spje == '') {
                    swal("商品金额不能为空");
                    return;
                }
                if (fpzldm == null || fpzldm == '') {
                    swal("发票种类不能为空");
                    return;
                }
                $.ajax({
                    url : ur,
                    data : data,
                    method : 'POST',
                    success : function(data) {
                        if (data.success) {
                            // loading
                            el.$modalHongchong.modal('close'); // close
                            swal({
                                title: data.message,
                                showCancelButton: false,
                                closeOnConfirm: false,
                                confirmButtonText: "确 定",
                                confirmButtonColor: "#ec6c62"
                            }, function() {
                                //_this.tableEx.ajax.reload(); // reload table
                                window.location.reload();
                            });
                        }else if(data.error){
                            swal(data.message);
                        }else{
                            swal(data.message);
						}
                        el.$jsLoading.modal('close'); // close

                    },
                    error : function() {
                        el.$jsLoading.modal('close'); // close loading
                        swal('保存失败, 请重新登陆再试...!');
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
			_this.modalAction(); // hidden action
			
		}
	};

	action.init();

});