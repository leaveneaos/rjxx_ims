/**
 * feel
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $modalhuankai: $('#huankai'),
        $modalExport: $('#export'), // 导出 modal

        $jsExportSubmit: $('.js-export-submit'), // 导出 submit
        $jsSubmit: $('.js-submit'), // 换开提交
        $jsClose: $('.js-close'), // 关闭 modal

        $jsForm0: $('.js-form-0'), // 换开 form
        $jsForm1: $('.js-form-1'), // 导出 form
        $s_kprqq: $('#s_kprqq'), // search 开票日期起
        $s_kprqz: $('#s_kprqz'), // search 开票日期止
        $s_gfmc: $('#s_gfmc'), // search 购方名称
        $s_fpdm: $('#s_fpdm'), // search
        $s_ddh: $('#s_ddh'), // search
        $s_fphm: $('#s_fphm'), // search

        $jsSearch: $('.js-search'),
        $jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };
	 $('#pxbz').click(function(){
		  if($('input[name="pxbz"]').prop("checked"))
	        {
			  $('#spmcbz').prop('checked', false);
			  $('#spbhb').prop('checked', false);
	        }
	 });
	  $('#spmcbz').click(function(){
		  if($('input[name="spmcbz"]').prop("checked"))
	        {
			  $('#pxbz').prop('checked', false);
			  $('#spbhb').prop('checked', false);
	        }
	 })
	   $('#spbhb').click(function(){
		  if($('input[name="spbhb"]').prop("checked"))
	        {
			  $('#pxbz').prop('checked', false);
			  $('#spmcbz').prop('checked', false);
	        }
	 })
var ur;
$("#qdbz").change(function () {
    if($(this).is(':checked')){
    	$("#zphs").attr("required",false);
    	$("#pphs").attr("required",false);
    	$("#dzphs").attr("required",false);
    	$("#zphs").attr("readonly",true);
    	$("#pphs").attr("readonly",true);
    	$("#dzphs").attr("readonly",true);
    	$("#zphs").val("");
    	$("#pphs").val("");
    	$("#dzphs").val("");
    }else{
    	$("#zphs").attr("readonly",false);
    	$("#pphs").attr("readonly",false);
    	$("#dzphs").attr("readonly",false);
      	$("#zphs").attr("required",true);
    	$("#pphs").attr("required",true);
       $("#dzphs").attr("required",true);
   	$("#zphs").val("");
	$("#pphs").val("");
	$("#dzphs").val("");
    }
});
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'hbgz/getList',
            xzUrl: 'hbgz/add',
            xgUrl: 'hbgz/update',
            scUrl: 'hbgz/delete'
        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
                    "scrollX": true,
                    "bPaginate":true,
                    ordering: false,
                    searching: false,

                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {

                        }
                    },
                    "columns": [
                            	{
                            		"orderable" : false,
                            		"data" : null,
                            		render : function(data, type, full, meta) {
                            			return '<input type="checkbox" value="'
                            				+ data.id + '" name="gzdxk"  />';
                            		}
                            	},
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {"data": "gzmc"},
                        {
                            "data": function (data) {
                                if (data.mrbz=="1") {
                                    return "是"
                                }else{
                                    return "否";
                                }
                            }
                        },
                        {"data":  function (data) {
                            if (data.pxbz=="1") {
                                return "商品按分类编码,名称,规格型号,单位,单价,税率合并;"
                            }else if(data.spmcbz=="1"){
                                return "商品按分类编码,名称,税率合并;";
                            }else{
                            	return "商品不合并;"
                            }
                        }
                        },
                        {"data": "bz"}, 
                        {
                            "data": null,
                            "render": function (data) {
                            		return '<a class="xiugai">修改</a>  '/*+'<a class="shanchu">删除</a>'*/;                
                            }
                        }]
                });
			// 新增
			$("#gz_xzgz").on('click', $("#gz_xzgz"), function() {
				$("#fpform")[0].reset();
				ur = _this.config.xzUrl;
			});
			$("#gz_cxgz").on('click', $("#gz_scgz"), function() {
				t.ajax.reload();
			});
			//删除
			$("#gz_scgz").on('click', $("#gz_scgz"), function() {
			      var djhArr = [];
		         $('input[name="gzdxk"]:checked').each(function(){    
		                djhArr.push($(this).val()); 
		        });
		            if (djhArr.length == 0) {
		            	$("#alertt").html("请选择需要删除的数据");
		            	$("#my-alert").modal('open');
		                return;
		            }
		            
		            
		            if (!confirm("确认删除么?")) {
						return;
					} 
						   ur = _this.config.scUrl;
							$.ajax({
								url : ur,
								data : {"gzid":djhArr.join(",")},
								method : 'POST',
								success : function(data) {
									$("#alertt").html(data.msg);
			                    	$("#my-alert").modal('open');
									t.ajax.reload(); // reload table
								},
								error : function() {
									$("#alertt").html("操作失败");
			                    	$("#my-alert").modal('open');
									t.ajax.reload(); // reload table
								}
							});
				
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
			
			// 修改
			t.on('click', 'a.xiugai', function() {
				var row = t.row($(this).parents('tr')).data();
				$("#fpform")[0].reset();
				$("#doc-modal-4").modal({"width": 650})
				$("#gzmc").val(row.gzmc);
				$("#bz").val(row.bz);
				if(row.mrbz=="1"){
					$("#mrbz").attr("checked","checked");
				}else{
					$("#mrbz").attr("checked",false);
				}
				if (row.pxbz == "1") {
					$('#pxbz').prop('checked', true);
				} else {
					$('#pxbz').prop('checked', false);
				}
				if (row.spmcbz == "1") {
					$('#spmcbz').prop('checked', true);
				} else {
					$('#spmcbz').prop('checked', false);
				}
				if (row.spbhb == "1") {
					$('#spbhb').prop('checked', true);
				} else {
					$('#spbhb').prop('checked', false);
				}
				$("#idd").val(row.id);
				ur = _this.config.xgUrl
			});

			// 删除
			t.on('click', 'a.shanchu', function() {
			
		           $("#conft").html("确认删除么")
		       	  $('#my-confirm').modal({
		       		 relatedTarget: this,
		 		   onConfirm: function(options) {  
		 				var row = t.row($(this.relatedTarget).parents('tr')).data();
				   ur = _this.config.scUrl;
					$.ajax({
						url : ur,
						data : {"id":row.id},
						method : 'POST',
						success : function(data) {
							$("#alertt").html(data.msg);
	                    	$("#my-alert").modal('open');
							t.ajax.reload(); // reload table
						},
						error : function() {
							$("#alertt").html("操作失败");
	                    	$("#my-alert").modal('open');
							t.ajax.reload(); // reload table
						}
					});
		 		        }})
			});
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });
            return t;
        },
        /**
		 * 新增保存
		 */

		xz : function() {
			var _this = this;
			el.$jsForm0.validator({
				submit : function() {
					if($('#pxbz').prop('checked') == false && $('#spmcbz').prop('checked') == false&& $('#spbhb').prop('checked') == false){
						$("#alertt").html("必选选中一条合并项");
		            	$("#my-alert").modal('open');
						return false;
					}
					var formValidity = this.isFormValid();
					if (formValidity) {
						var data = el.$jsForm0.serialize(); // get form data
						$.ajax({
							url : ur,
							data : data,
							method : 'POST',
							success : function(data) {
									el.$jsLoading.modal('close'); // close
									$("#alertt").html(data.msg);
			                    	$("#my-alert").modal('open');	
							     $("#doc-modal-4").modal('close');
								_this.tableEx.ajax.reload(); // reload table
								// data
							},
							error : function() {
								el.$jsLoading.modal('close'); // close loading
								$("#alertt").html("操作失败");
		                    	$("#my-alert").modal('open');
							}
						});
						return false;
					} else {
						$("#alertt").html("验证失败");
                    	$("#my-alert").modal('open');
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
			el.$jsForm0.validator({
				submit : function() {
					if($('#pxbz').prop('checked') == false && $('#spmcbz').prop('checked') == false&& $('#spbhb').prop('checked') == false){
						$("#alertt").html("必选选中一条合并项");
		            	$("#my-alert").modal('open');
						return false;
					}
					var formValidity = this.isFormValid();
					if (formValidity) {
						var data = el.$jsForm0.serialize(); // get form data
						$.ajax({
							url : ur,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									el.$jsLoading.modal('close'); // close
									$("#alertt").html(data.msg);
			                    	$("#my-alert").modal('open');
								} else {
									el.$jsLoading.modal('close'); // close
									$("#alertt").html(data.msg);
			                    	$("#my-alert").modal('open');
								}
							     $("#doc-modal-4").modal('close');
								_this.tableEx.ajax.reload(); // reload table
								// data

							},
							error : function() {
								el.$modalHongchong.modal('close'); // close
								el.$jsLoading.modal('close'); // close loading
								$("#alertt").html("操作失败");
		                    	$("#my-alert").modal('open');
							}
						});
						return false;
					} else {
						$("#alertt").html("验证失败");
                    	$("#my-alert").modal('open');
						return false;
					}
				}
			});
		},
     
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            // close modal
           $(".gz_qx").on('click', function () {
                $("#doc-modal-4").modal('close');
              //  el.$modalExport.modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.modalAction(); // hidden action
            _this.xz();
            _this.xg();

        }
    };
    action.init();

});