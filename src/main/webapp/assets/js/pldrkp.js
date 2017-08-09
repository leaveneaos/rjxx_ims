/**
 * feel
 */
$(function() {
	"use strict";
	var el = {
		$jsTable : $('.js-table'),
		$jsExport : $('.js-export'),
		$jsLoading : $('.js-modal-loading'),
		$jsPrint : $('.js-print'),
		$checkAll : $('#select_all')
	};
    //批量导入
    var $importModal = $("#bulk-import-div");
    $("#kp_dr").click(function () {
    	$('#importExcelForm').resetForm();
        $importModal.modal({"width": 600, "height": 350});
    });
	
	  //开票商品明细table
    var t;
    var kpspmx_table3;
    var loaddata=false;
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'pldrkp/getItems'
		},
		dataTable : function() {
			var _this = this;
			t = el.$jsTable.DataTable({
				"processing" : true,
				"serverSide" : true,
				ordering : true,
				searching : false,
				"scrollX" : true,
				"order": [[ 7, "desc" ]],
				"ajax" : {
					url : _this.config.getUrl,
					type : 'POST',
					data : function(d) {
						d.jyrqq = $("#s_jyrqq").val(); // search 开票日期
						d.kprqz = $("#s_jyrqz").val(); // search 开票日期
					    d.xfsh = $('#s_xfsh').val();   // search 销方
		                d.jylsh = $('#s_jylsh').val();   // search 订单号
		                var csm =  $('#dxcsm').val()
		                if("jylsh"==csm&&(d.jylsh==null||d.jylsh=="")){
		                    d.jylsh = $('#dxcsz').val()
						 }
						 d.loaddata=loaddata;
					}
				},
	            "columns": [
	                        	{
	                        		"orderable" : false,
	                        		"data" : null,
	                        		render : function(data, type, full, meta) {
	                        			return '<input type="checkbox" name= "chk" data="'
	                        				+ data.xh + '" />';
	                        		}
	                        	},
	                        	{
									"orderable" : false,
									"data" : null,
									"defaultContent" : ""
								},
								
						  {
									"data": function(data) {
										if (data.ztbz == "1") {
											return "已处理";
										} else {
											return "<a class='kaiju' href='#'>开具</a>  <a class='shanchu' href='#'>删除</a> "	;	
										}
									}						
							},
	                        {"data": "jylsh"},
	                        {"data": "drwjm"},
	                        {"data": "lrsj"},
	                        {"data": "jlts"},
	                        {"data": function (data) {
	                            if (data.jshj) {
	                                return FormatFloat(data.jshj, "###,###.00");
	                            } else {
	                                return null;
	                            }
	                        }, 'sClass': 'right'},
							/*{"data":function(data){
								var ztbz = data.ztbz;
								switch (ztbz) {
									case '0':
										ztbz = '未处理2';
										break;
									case '1':
										ztbz = '已处理2';
										break;
									
								}
								return ztbz;
							}},*/
							 {"data": "xh"},
							 {"data": "xfid"},

	                    ]
			});
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
				$('#jyls_table tr').find('td:eq(8)').hide();
				$('#jyls_table tr').find('td:eq(9)').hide();
			});
			
			// 批量开具处理
			t.on('click', 'a.kaiju', function() {
				        	var row = t.row($(this).parents('tr')).data();
				        	swal({
				                title: "您确认处理该批数据吗？",
				                type: "warning",
				                showCancelButton: true,
				                closeOnConfirm: false,
				                confirmButtonText: "确 定",
				                confirmButtonColor: "#ec6c62"
				            }, function() {
				                $('.confirm').attr('disabled',"disabled");	
				                showMask();			  
				                $.ajax({
				                    type : "POST",
				                    url : 'pldrkp/plkjcl',
				                    data : {
				                    	jylsh:row.jylsh,
									    xfid:row.xfid,
									    lrsj:row.lrsj,
									    xh:row.xh
									},
				                }).done(function(data) {
				                    $('.confirm').removeAttr('disabled');
				                    hideMask()
				                    if(data.success){
				                    	swal({ 
					                          title: data.msg, 
					                          timer: 1500, 
					                          type: "success", 
					                          showConfirmButton: false 
					                        });
									}
				                    _this.tableEx.ajax.reload();
				                })
				            });
							/*$.ajax({
								url : 'pldrkp/plkjcl',
								data : {
										jylsh:row.jylsh,
									    xfid:row.xfid,
									    lrsj:row.lrsj,
									    xh:row.xh
									},
								method : 'POST',
								success : function(data) {
									if(data.success){
										swal(data.msg);
										_this.tableEx.ajax.reload();
									}
								},
								error : function() {									// $("#alertt").html("操作失败");
									// $("#my-alert").modal('open')
									swal("操作失败");
								}
							});*/
	
			});
	          //删除
			t.on('click', 'a.shanchu', function() {
	        	var row = t.row($(this).parents('tr')).data();
	        	
	        	swal({
	                title: "您确认删除？",
	                type: "warning",
	                showCancelButton: true,
	                closeOnConfirm: false,
	                confirmButtonText: "确 定",
	                confirmButtonColor: "#ec6c62"
	            }, function() {
	                $('.confirm').attr('disabled',"disabled");
	                $.ajax({
	                    type : "POST",
	                    url : 'pldrkp/plkjsh',
	                    data : {
							jylsh:row.jylsh,
						    xfid:row.xfid,
						    lrsj:row.lrsj,
						    xh:row.xh
						},
	                }).done(function(data) {
	                    $('.confirm').removeAttr('disabled');
	                    swal({ 
	                          title: "已成功删除", 
	                          timer: 1500, 
	                          type: "success", 
	                          showConfirmButton: false 
	                        });
	                    _this.tableEx.ajax.reload();
	                })
	            });
		/*		$.ajax({
					url : 'pldrkp/plkjsh',
					data : {
							jylsh:row.jylsh,
						    xfid:row.xfid,
						    lrsj:row.lrsj,
						    xh:row.xh
						},
					method : 'POST',
					success : function(data) {
						if(data.success){
							swal(data.msg);
							_this.tableEx.ajax.reload();
						}else{
							swal(data.msg);
							_this.tableEx.ajax.reload();
						}
					},
					error : function() {									// $("#alertt").html("操作失败");
						// $("#my-alert").modal('open')
						swal("操作失败");
					}
				});*/

			});
            $('#check_all').change(function () {
            	if ($('#check_all').prop('checked')) {
            		t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                        var row =t.row(i).data();
                    });
                } else {
                	t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);

                    });
                }
            });

            //选中列查询明细
           /* $('#jyls_table tbody').on('click', 'tr', function () {
                var data = t.row($(this)).data();
                if ($('#check_all').prop('checked')){
                    splsh.splice(0,splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
				}
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked',false);
                    splsh.splice($.inArray(data.sqlsh, splsh), 1);
                } else {
                    $(this).find('td:eq(0) input').prop('checked',true)
                    $(this).addClass('selected');
                    splsh.push(data.sqlsh);
                }
                $('#check_all').prop('checked',false);
                $("#kplsh").val(splsh.join(","));
                kpspmx_table.ajax.reload();
            });*/
			return t;
		},

		/**
		 * search action
		 */
		search_ac : function() {
			var _this = this;
			$("#kp_search").on('click', function(e) {
	        	$('#xzxfq').attr("selected","selected");
	         	$('#xzlxq').attr("selected","selected");
                loaddata=true;
				_this.tableEx.ajax.reload();
			});
			$("#kp_search1").on('click', function(e) {
				$("#dxcsz").val("");
                loaddata=true;
				_this.tableEx.ajax.reload();
			})
			},

	
		modalAction : function() {
			$("#close").on('click', function() {
				$('#my-alert-edit').modal('close');
			});
			$("#mxclose").on('click', function() {
				$('#my-alert-edit1').modal('close');
			});
			$("#kp_hbkp").on('click', function() {
				var chk_value="" ;
				$('input[name="dxk"]:checked').each(function(){
				chk_value+=$(this).val()+",";
				});
				var ddhs = chk_value.substring(0, chk_value.length-1);
				if(chk_value.length<2){
					swal("请至少选择2条数据!")
				}else{
					
				}
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
			_this.modalAction(); // hidden action
		}
	};
	action.init();

});

function yzje(je){
	var zdje = $(je).attr("max");
	var zhi= $(je).val();
	if(zdje==0){
        swal("最大金额为0 ,请维护开票限额");
    	return;
	}
	if(zhi*1>zdje*1){
		var msg = "不能超过分票金额"+zdje*1;
        swal(msg);
		$(je).val(zdje);
	}
}
function delcommafy(num){
	   if((num+"").trim()==""){
	      return "";
	   }
	   num=num.replace(/,/gi,'');
	   return num;
	}
