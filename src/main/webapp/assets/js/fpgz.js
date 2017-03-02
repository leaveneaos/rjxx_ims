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
var ur;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpgz/getList',
            xzUrl: 'fpgz/xzgz',
            xgUrl: 'fpgz/xggz',
            scUrl: 'fpgz/scgz'
        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
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
                        {"data": "ggmc"},
                        {
                            "data": function (data) {
                                if (data.zpxe) {
                                    return FormatFloat(data.zpxe,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },
                        {"data": "zphs"},
                        {
                            "data": function (data) {
                                if (data.ppxe) {
                                    return FormatFloat(data.ppxe,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },
                        {"data": "pphs"},
                        {
                            "data": function (data) {
                                if (data.dzpxe) {
                                    return FormatFloat(data.dzpxe,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },
                        {"data": "dzphs"},
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
				$(".chk").show();
				 var t01 = $("#jyls_table tbody tr").length;
				 for(var i = 0;i<t01;i++){
					 var row =  t.row(i).data();
					 if(typeof(row)!="undefined"){
						 var xfds = row.xfids.split(',');
							for(var j in xfds){
								var bz = "#type-"+xfds[j];
								$(bz).hide();
								  } 
					 }	
				 }
				ur = _this.config.xzUrl;
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
								data : {"id":djhArr.join(",")},
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
			
			// 修改
			t.on('click', 'a.xiugai', function() {
				var row = t.row($(this).parents('tr')).data();
				$(".chk").show();
				$("#fpform")[0].reset();
				$("#doc-modal-4").modal('open');
				$("#ggmc").val(row.ggmc);
				$("#zpxe").val(row.zpxe);
				$("#zphs").val(row.zphs);
				$("#ppxe").val(row.ppxe);
				$("#pphs").val(row.pphs);
				$("#dzpxe").val(row.dzpxe);
				if(row.mrbz=="1"){
					$("#mrbz").attr("checked","checked");
				}else{
					$("#mrbz").attr("checked",false);
				}
				$("#idd").val(row.id);
				$("#dzphs").val(row.dzphs);
				var xfids = row.xfids.split(',');
				 var t01 = $("#jyls_table tbody tr").length;
				 for(var i = 0;i<t01;i++){
					 var row =  t.row(i).data();
						var xfds = row.xfids.split(',');
						for(var j in xfds){
							var bz = "#type-"+xfds[j];
							$(bz).hide();
						/*	$(bz).prop('checked', true);*/
							  }
				 }
				for(var i in xfids){
					var bz = "#yhjg1-"+xfids[i];
					var bz1 = "#type-"+xfids[i];
					$(bz1).show();
					$(bz).prop('checked', true);
					  }
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
					var formValidity = this.isFormValid();
					var chk_value =[];
					$('input[name="xfid"]:checked').each(function(){
					chk_value.push($(this).val());
					}); 
					if(chk_value.length<1){
						$("#alertt").html("请选择销方");
                    	$("#my-alert").modal('open');
						return false;
					}
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
					var formValidity = this.isFormValid();
					var chk_value =[];
					$('input[name="xfid"]:checked').each(function(){
					chk_value.push($(this).val());
					}); 
					if(chk_value.length<1){
						$("#alertt").html("请选择销方");
                    	$("#my-alert").modal('open');
						return false;
					}
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