$(function() {
	"use strict";
	var url;
	var el = {
		$jsTable : $('.js-table'),
		$jsModalOpem : $('.js-modal-open'),
		$modal : $('#your-modal'),
		$modal1 : $('#your-modal1'),
		$jsSubmit : $('.js-submit'),
		$jsSubmit1 : $('.js-submit1'),
		$jsSearch : $('#button1'),
		$jsSearch1 : $('#button3'),
		$jsClose : $('.js-close'),
		$jsClose1 : $('.js-close1'),
		$xfid : $('#xfid'),
		$s_kpdmc : $('#s_kpdmc'),
		$s_kpddm : $('#s_kpddm'),
		$jsForm : $('.js-form'),
		$jsForm1 : $('.js-form1'),
		$dpmax : $('#dpmax'),
		$ppmax : $('#ppmax'),
		$zpmax : $('#zpmax'),
		$fpfz : $('#fpfz'),
		$ppfz : $('#ppfz'),
		$zpfz : $('#zpfz'),
		$checkAll : $('#check_all'), // check all checkbox
		$jsdel : $('.js-sent'), // del all
		$jsLoading : $('.js-modal-loading')
	};

	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'sksbxxzc/getsksblist',
			delUrl : 'sksbxxzc/del',
			addUrl : 'sksbxxzc/save',
			deleteUrl : 'sksbxxzc/delele',
			editUrl : 'sksbxxzc/update',
			xfUrl : 'sksbxxzc/getXf'
		},

		dataTable : function() {
			var _this = this;

			var t = el.$jsTable.DataTable({
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
							var txt = $('#searchtxt').val();
							if (tip == "1") {
								d.kpddm = txt;
							} else if (tip == "2") {
								d.kpdmc = txt;
							} else if (tip == "3") {
								d.xfmc = txt;
							} else if (tip == "4") {
								d.kpr = txt;
							}
						} else {
							d.kpdmc = el.$s_kpdmc.val();
							d.kpddm = el.$s_kpddm.val();
							d.xfid1 = $('#xfid1').val();
							d.kpr = $('#kpr1').val();
							d.sbcs = $('#sbcs1').val();
							d.kplx = $('#kplx1').val();
						}

					}
				},
				"columns" : [
						{
							"orderable" : false,
							"data" : null,
							// "defaultContent": '<input type="checkbox" />'
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
							"data": null,
	                        "defaultContent": "<a class='modify' href='#'>修改</a> "									
						},
						{
							"data" : "xfmc"
						}, {
							"data" : "kpddm"
						}, {
							"data" : "kpdmc"
						}, {
							"data" : function(data) {
								if (data.sbcs == 1) {
									return "税控盘";
								} else if (data.sbcs == 2) {
									return "金税盘";
								} else {
									return "";
								}
							}
						}, {
							"data" : "skph"
						}, /*{
							"data" : function(data) {
								if (data.skpmm == null || data.skpmm == "") {
									return "";
								} else {
									return "******";
								}
							}
						}, {
							"data" : function(data) {
								if (data.zsmm == null || data.zsmm == "") {
									return "";
								} else {
									return "******";
								}
							}
						},*/ {
							"data" : "lxdz"
						}, {
							"data" : "lxdh"
						}, {
							"data" : "khyh"
						}, {
							"data" : "yhzh"
						}, {
							"data" : "skr"
						}, {
							"data" : "fhr"
						}, {
							"data" : "kpr"
						}, {
							"data" : "fpzl"
						}, {
                            "data" : function(data){
                            	if(data.wrzs=='1'){
                            		return "是";
								}else{
                            		return "否";
								}
							}
                        }, {
							"data" : "ppdm"
						}, {
							"data" : "ppmc"
						},]
			});

			t.on('draw.dt', function(e, settings, json) {

				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {// 序号
					cell.innerHTML = page + i + 1;
				});
				// $('#tbl tr').find('td:eq(2)').hide();
				// $('#tbl tr').find('td:eq(3)').hide();
				// $('#tbl tr').find('td:eq(4)').hide();
				// $('#tbl tr').find('td:eq(5)').hide();
			});

			t.on('click', 'a.modify', function() {
				var data = t.row($(this).parents('tr')).data();
				el.$jsForm.find('[name="kpddm"]').val(data.kpddm);
				el.$jsForm.find('[name="kpdmc"]').val(data.kpdmc);
				el.$jsForm.find('[name="xfid"]').find(
						'option[value=' + data.xfid + ']').prop('selected',
						true);
				el.$jsForm.find('[name="pid"]').find(
						'option[value=' + data.pid + ']')
						.prop('selected', true);
				el.$jsForm.find('[name="sbcs"]').find(
						'option[value=' + (data.sbcs == "1" ? 1 : 2) + ']')
						.prop('selected', true);
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
                el.$jsForm.find('select[name="province"]').val(data.provinceid);
                var city = $("#city");
                $("#city").empty();
                $.ajax({
                    url : "xfxxwh/getCity",
                    async:false,
                    data : {
                        "provinceid" : data.provinceid
                    },
                    success : function(data) {
                        var option = $("<option>").text('请选择').val(-1);
                        city.append(option);
                        for (var i = 0; i < data.length; i++) {
                            var option = $("<option>").text(data[i].city).val(
                                data[i].cityid);
                            city.append(option);
                        }
                    }
                });
                el.$jsForm.find('select[name="city"]').val(data.cityid);
                var area = $("#area");
                $("#area").empty();
                $.ajax({
                    url : "xfxxwh/getArea",
                    async:false,
                    data : {
                        "cityid" : data.cityid
                    },
                    success : function(data) {
                        var option = $("<option>").text('请选择').val(-1);
                        area.append(option);
                        for (var i = 0; i < data.length; i++) {
                            option = $("<option>").text(data[i].area).val(
                                data[i].areaid);
                            area.append(option);
                        }
                    }
                });
                el.$jsForm.find('select[name="area"]').val(data.areaid);
                el.$jsForm.find('input[name="address"]').val(data.address);
                if (data.kplx != null) {
					var fps = data.kplx.split(",");
					for (var i = 0; i < fps.length; i++) {
						var fplx = '#fplx-' + fps[i];
						var id = '#kplx-'+fps[i]
						$(fplx).prop('checked', true);
						$(id).show();
					}
				}
				if(data.wrzs=="1"){
                    $("#wrzs").prop('checked', true);
                }
                var fplx12=$("#fplx-12");
                if(fplx12.is(':checked')){
                    if(data.dpmax!=0&&data.dpmax!=null){
                        el.$jsForm.find('select[id="kpxe-12"]').val(data.dpmax);
                        el.$jsForm.find('[id="fpje-12"]').val(data.fpfz);
                        el.$jsForm.find('[id="kplx-12"]').show();
                    }
				}else {
                    el.$jsForm.find('select[id="kpxe-12"]').val(null);
                    el.$jsForm.find('[id="fpje-12"]').val(null);
                    el.$jsForm.find('[id="kplx-12"]').hide();
                }
                var fplx01=$("#fplx-01");
                if(fplx01.is(':checked')) {
                    if (data.zpmax != 0 && data.zpmax != null) {
                        el.$jsForm.find('select[id="kpxe-01"]').val(data.zpmax);
                        el.$jsForm.find('[id="fpje-01"]').val(data.zpfz);
                        el.$jsForm.find('[id="kplx-01"]').show();
                    }
                }else{
                    el.$jsForm.find('select[id="kpxe-01"]').val(null);
                    el.$jsForm.find('[id="fpje-01"]').val(null);
                    el.$jsForm.find('[id="kplx-01"]').hide();
                }
                var fplx02=$("#fplx-02");
                if(fplx02.is(':checked')) {
                    if (data.ppmax != 0 && data.ppmax != null) {
                        el.$jsForm.find('select[id="kpxe-02"]').val(data.ppmax);
                        el.$jsForm.find('[id="fpje-02"]').val(data.ppfz);
                        el.$jsForm.find('[id="kplx-02"]').show();
                    }
                }else {
                    el.$jsForm.find('select[id="kpxe-02"]').val(null);
                    el.$jsForm.find('[id="fpje-02"]').val(null);
                    el.$jsForm.find('[id="kplx-02"]').hide();
                }
				url = _this.config.editUrl + "?id=" + data.id;
				$('#your-modal').modal({
					"width" : 900,
					"height" : 500
				});
			});
			var $importModal = $("#bulk-import-div");
			$("#close1").click(function() {
				$importModal.modal("close");
			});
			$("#button2").click(function() {
				url = _this.config.addUrl;
				$('#kplx-01').hide();
				$('#kplx-02').hide();
				$('#kplx-12').hide();
				$('#your-modal').modal({
					"width" : 900,
					"height" : 500
				});
			});

			$('#xfid').change(function() {
				$.ajax({
					url : _this.config.xfUrl,
					data : {
						xfid : $('#xfid').val()
					},
					type : 'POST',
					success : function(data) {
						$('#lxdz').val(data.xf.xfdz);
						$('#lxdh').val(data.xf.xfdh);
						$('#khyh').val(data.xf.xfyh);
						$('#yhzh').val(data.xf.xfyhzh);
						$('#skr').val(data.xf.skr);
						$('#fhr').val(data.xf.fhr);
						$('#kpr').val(data.xf.kpr);
						el.$jsForm.find('select[id="kpxe-12"]').val(data.xf.dzpzdje);
						el.$jsForm.find('[id="fpje-12"]').val(data.xf.dzpfpje);
						el.$jsForm.find('select[id="kpxe-01"]').val(data.xf.zpzdje);
						el.$jsForm.find('[id="fpje-01"]').val(data.xf.zpfpje);
						el.$jsForm.find('select[id="kpxe-02"]').val(data.xf.ppzdje);
						el.$jsForm.find('[id="fpje-02"]').val(data.xf.ppfpje);
					},
					error : function() {

					}
				});

			});
			$('#kpxe-01').change(function() {
				$('#fpje-01').val($('#kpxe-01').val());
			});
			$('#kpxe-02').change(function() {
				$('#fpje-02').val($('#kpxe-02').val());
			});
			$('#kpxe-12').change(function() {
				$('#fpje-12').val($('#kpxe-12').val());
			});
			// 导入excel
			$("#btnImport").click(function() {
				var filename = $("#importFile").val();
				if (filename == null || filename == "") {
					swal("请选择要导入的文件");
					return;
				}
				var pos = filename.lastIndexOf(".");
				if (pos == -1) {
					swal("导入的文件必须是excel文件");
					return;
				}
				var extName = filename.substring(pos + 1);
				if ("xls" != extName && "xlsx" != extName) {
					swal("导入的文件必须是excel文件");
					return;
				}
				$("#btnImport").attr("disabled", true);
				$('.js-modal-loading').modal('toggle'); // show loading
				var options = {
					success : function(res) {
						if (res.success) {
							$("#btnImport").attr("disabled", false);
							$('.js-modal-loading').modal('close');
							var count = res.count;
							swal("导入成功，共导入" + count + "条数据");
							window.location.reload();
						} else {
							$("#btnImport").attr("disabled", false);
							$('.js-modal-loading').modal('close');
							swal(res.message);
						}
					}
				};
				$("#importExcelForm").ajaxSubmit(options);
			});
			return t;
		},
		modal : function() {
			var _this = this;
			el.$jsModalOpem.on('click', function(e) {
				e.preventDefault();
				url = _this.config.addUrl;
				$('#your-modal').modal({
					"width" : 900,
					"height" : 500
				});
			});
		},
		/**
		 * del删除税控盘信息 request
		 * 
		 * @param data {
		 *            ids: '1,2,3,4' }
		 */
		del : function(data) {
			var _this = this;
			swal({
                title: "提示",
                text: "您确定要删除这条数据吗？",
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
						ids : data.ids
					},
					type : 'POST',
                }).done(function(data) {
                	if (data.success) {
                		$('.confirm').removeAttr('disabled');
	                        _this.tableEx.ajax.reload(); // reload table data
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
					swal("请选择要删除的税控盘");
					return;
				}
				data = data.substring(0, data.length - 1);
				url = _this.config.deleteUrl;
				_this.del({
					ids : data
				});
				el.$checkAll.prop('checked', false);
			});
		},
		/**
		 * 添加方法
		 */
		saveRow : function() {
			var _this = this;
			el.$jsForm.validator({
				submit : function() {
					var formValidity = this.isFormValid();
					if (formValidity) {
						if ($('#xfid').val() == 0) {
							swal('请选择销方');
							return false;
						}
						if ($('#sbcs').val() == 0) {
							swal('请选择设备厂商');
							return false;
						}
						if($('#skph').val()!=null&&($('#skph').val()!="")){
							if($('#skph').val().length != 12){
								swal('请输入正确的设备号');
								return false;
							}
						}
						var data = el.$jsForm.serialize(); // get form data
						$.ajax({
							url : url,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									el.$modal.modal('close'); // close modal
									swal(data.msg);
									_this.tableEx.ajax.reload(); // reload
								} else if (data.failure) {
									swal(data.msg);
								} else {
									swal('后台错误: 数据操作失败' + data.msg);
								}
								el.$jsLoading.modal('close');
							},
							error : function() {
								swal('数据操作失败, 请重新登陆再试...!');
								el.$jsLoading.modal('close');
							}
						});
						return false;
					} else {
						swal('验证失败');
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
				$('#bj').val('1');
				_this.tableEx.ajax.reload();
			});
			el.$jsSearch1.on('click', function(e) {
				e.preventDefault();
				$('#bj').val('2');
				_this.tableEx.ajax.reload();
			});
		},
		/**
		 * 添加model
		 */
		resetForm : function() {
			el.$jsForm[0].reset();
		},
		modalAction : function() {
			var _this = this;
			el.$modal.on('closed.modal.amui', function() {
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
		resetForm1 : function() {
			el.$jsForm1[0].reset();
		},
		modalAction1 : function() {
			var _this = this;
			el.$modal1.on('closed.modal.amui', function() {
				_this.resetForm1();
			});
		},
		/**
		 * 初始化
		 */
		init : function() {
			var _this = this;

			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
			_this.modal();
			_this.modalAction();// hidden action
			_this.modalAction1();// hidden action
			_this.checkAllAc();
			_this.delAllAc();
			_this.saveRow();
		}
	};
	action.init();

});