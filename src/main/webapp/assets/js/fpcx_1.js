/**
 * feel
 */
$(function() {
	"use strict";
	var el = {
		$jsTable : $('.js-table'),
		$modalfpxx : $('#fpxx'),
		$jsSubmit : $('.js-submit'),
		$jsSubmit1 : $('.js-submit1'),
		$jsClose : $('.js-close'),
		$jsClose1 : $('.js-close1'),
		$jsAuto : $('.js-auto'),
		$jsOut : $('.js-out'),
		$jsForm0 : $('.js-form-0'), // form
		$s_ddh : $('#s_ddh'), // search 订单号
		$s_gfmc : $('#s_gfmc'), //关键字购方名称
		$s_fpdm : $('#s_fpdm'), //关键字发票代码
		$s_fphm : $('#s_fphm'), // search 发票号码
		$s_kprqq : $('#s_kprqq'), // search 开票日期
		$s_kprqz : $('#s_kprqz'), // search 开票日期
		$s_spmc : $('#s_spmc'), // search商品名称
		$s_fpzt : $('#s_fpzt'), //关键字发票状态
		$s_fpczlx : $('#s_fpczlx'),
		$s_dyzt : $('#s_dyzt'), // search 开票日期
		$jsSearch : $('.js-search'),
		$jsSearch1 : $('#button3'),
		$jsExport : $('.js-export'),
		$jsLoading : $('.js-modal-loading'),
		$jsPrint : $('.js-print'),
		$checkAll : $('#select_all')
	};

	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'fpcx/getKplsList'
		},
		dataTable : function() {
			var start = [
					{
						"orderable" : false,
						"data" : null,
						render : function(data, type, full, meta) {
							return '<input type="checkbox" value="'
									+ data.kplsh + '" />';
						}
					}, {
						"orderable" : false,
						"data" : null,
						"defaultContent" : ""
					}, {
						"data" : "ddh"
					}, {
						"data" : "fpczlxmc"
					}, {
						"data" : "fpdm"
					}, {
						"data" : "fphm"
					}, {
						"data" : function(data) {
							if (data.jshj) {
								return FormatFloat(data.jshj, "###,###.00");
							} else {
								return null;
							}
						},
						'sClass' : 'right'
					}, {
						"data" : "gfmc"
					}, {
						"data" : "kprq"
					}, {
						"data" : "fpzt"
					} ]
			$.ajax({
				url : 'zdyl/query',
				type : 'POST', //GET
				async: false,
				data : {},
				dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
				success : function(data) {
					$('#bt').append(data.bt);
					$.each(data.da, function(n, value) {
						if(value=="hjje"){
							var j = {
									"data" : function(data) {
										if (data.hjje) {
											return FormatFloat(data.hjje, "###,###.00");
										} else {
											return null;
										}
									}};
						}else if(value=="hjse"){
							var j = {
									"data" : function(data) {
										if (data.hjse) {
											return FormatFloat(data.hjse, "###,###.00");
										} else {
											return null;
										}
									}};	
						}else{
							var j = {"data":value};
						}
						start.push(j);
					});
				}
			})

			var end = {
				"data" : null,//
				//"defaultContent": '<a class="view">查看</a>'
				"render" : function(data) {
					if (data.pdfurl) {
						return '<a class="view" href="'
								+ data.pdfurl
								+ '" target="_blank">查看</a> <a class="yulan">打印</a>';
					} else {
						return null;
					}
				}
			}
			start.push(end);
			var _this = this;
			var t = el.$jsTable.DataTable({
				"processing" : true,
				"serverSide" : true,
				ordering : false,
				searching : false,
				"scrollX" : true,
				"ajax" : {
					url : _this.config.getUrl,
					type : 'POST',
					data : function(d) {
						var bj = $('#bj').val();
						var txt = $('#searchtxt').val();
						var tip = $('#tip').val();
						if (bj ==  "1") {
							if (tip == "1") {
								d.gfmc = txt; // search 订单号
							}else if (tip == "2") {
								d.ddh = txt;
							}else if (tip == "3") {
								d.spmc = txt;
							}else if (tip == "4"){
								d.xfmc = txt;
							}else if (tip == "5") {
								d.fphm = txt;
							}else if (tip == "6") {
								d.fpdm = txt;
							}
						}else{
							d.ddh = el.$s_ddh.val(); // search 订单号
							d.gfmc = el.$s_gfmc.val(); //购方名称
							d.fpdm = el.$s_fpdm.val(); //发票代码
							d.fphm = el.$s_fphm.val(); // search 发票号码
							d.kprqq = el.$s_kprqq.val(); // search 开票日期
							d.kprqz = el.$s_kprqz.val(); // search 开票日期
							d.spmc = el.$s_spmc.val(); //商品名称
							d.fpzt = el.$s_fpzt.val(); //发票状态
							d.fpczlx = el.$s_fpczlx.val();
							d.printflag = el.$s_dyzt.val();//打印状态
							d.xfsh=$("#mb_xfsh").val();
							d.sk =$("#mb_skp").val();
						}
					}
				},
				"columns" : start
			});

			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});

			});
			t
					.on(
							'click',
							'a.yulan',
							function() {
								var da = t.row($(this).parents('tr')).data();
								window
										.open('fpcx/printSingle?kplsh='
												+ da.kplsh, '',
												'scrollbars=yes,status=yes,left=0,top=0,menubar=yes,resizable=yes,location=yes');
								$('#kplshStr').val(da.kplsh);
							});
			t.on('click', 'a.fpck', function(e) {
				var _this = this;
				var da = t.row($(this).parents('tr')).data();
				var kplsh = da.kplsh;
				var djh = da.djh;
				$.ajax({
					url : 'fpcx/ck',
					data : {
						"kplsh" : kplsh,
						"djh" : djh,
						"yfpdm" : "",
						"yfphm" : "",
						"jylsh" : ""
					},
					success : function(data) {
						if (data.success) {
							$("#alertt").html(data.msg);
	                    	$("#my-alert").modal('open');
							e.preventDefault();
							t.ajax.reload();
						} else {
							$("#alertt").html(data.msg);
	                    	$("#my-alert").modal('open');
						}
					},
					error : function() {
						$("#alertt").html("出现错误，请稍后重试！");
                    	$("#my-alert").modal('open');
					}
				})
			});
			
			$("#mb_xfsh").change(function() {
			     $('#mb_skp').empty();
				 var xfsh = $(this).val();
				 if (xfsh == null || xfsh == '' || xfsh == "") {
					 return;
				 }
				 var url = "<%=request.getContextPath()%>/fpcx/getSkpList";
				 $.post(url, {
					 xfsh : xfsh
				 }, function(data) {
					 if (data) {
						 var option = $("<option>").text('请选择').val(-1);
						 $('#mb_skp').append(option);
						 for (var i = 0; i < data.skps.length; i++) {
							 option = $("<option>").text(data.skps[i].kpdmc).val(data.skps[i].id);
							 $('#mb_skp').append(option);
						 }
					 }
				 });
			 });
			
			
			
			
			/***发票开具*/
			$.ajax({
	            url: "kp/getXfxx", context: null, success: function (data) {
	                $("#qymc").val(data.xfmc);
	                $("#nsrsbh").val(data.xfsh);
	            }
	        });
	            
	        var jyls_table = $('#jyls_table').DataTable({
	            "searching": false,
	            "serverSide": true,
	            "sServerMethod": "POST",
	            "processing": true,
	            "scrollX": true,
	            ordering: false,
	            ajax: {
	                "url": "kp/getjylslist?clztdm=00",
	                type: 'post',
	                data: function (d) {
	                    d.xfsh = $('#s_xfsh1').val();   // search 销方
	                    d.gfmc = $('#s_gfmc1').val();	// search 购方名称
	                    d.ddh = $('#s_ddh1').val();   // search 订单号
	                    d.fpzldm = $('#s_fplx1').val();   // search 发票号码
	                    d.rqq = $('#s_rqq1').val(); // search 开票日期
	                    d.rqz = $('#s_rqz1').val(); // search 开票日期
	                    var csm =  $('#dxcsm1').val()
	                    if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){
	                    	  d.gfmc = $('#dxcsz1').val()
	                    }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){
	                    	  d.ddh = $('#dxcsz1').val()
	                    }
	                }
	            },
	            "columns": [
	                       	{
	                    		"orderable" : false,
	                    		"data" : null,
	                    		render : function(data, type, full, meta) {
	                    			return '<input type="checkbox" value="'
	                    				+ data.djh + '" name="chk"  />';
	                    		}
	                    	},
	                    {
		                    "orderable": false,
		                    "data": null,
		                    "defaultContent": ""
	                    },
	                {"data": "jylsh"},
	                {"data": "ddh"},
	                {"data": "jylssj"},
	                {"data": function(data){
	                	if(data.fpzldm=='12'){
	                		return "电子票";
	                	}else if(data.fpzldm=='01'){
	                		return "纸质专票";
	                	}else if(data.fpzldm=='02'){
	                		return "纸质普票";
	                	}
	                
	                }},
	                {"data": "gfsh"},
	                {"data": "gfmc"},
	                {"data": "gfyh"},
	                {"data": "gfyhzh"},
	                {"data": "gflxr"},
	                {"data": "gfdz"},
	                {"data": "gfsjh"},
	                {"data": "bz"},
	                {"data": function (data) {
	                    if (data.jshj) {
	                        return FormatFloat(data.jshj, "###,###.00");
	                    } else {
	                        return null;
	                    }
	                }, 'sClass': 'right'}/*,    {
	                    "data": null,
	                    "render": function (data) {
	                        return '<a class="view" href="#">开票</a>     '+
	                            '<a class="view" href="#">删除</a>'
	                    }
	                }*/
	            ]
	            // "columnDefs": [
	            //     {
	            //         "bVisible": false, "aTargets": [0]
	            //     }],
	        });
	        jyls_table.on('draw.dt', function(e, settings, json) {
				var x = jyls_table, page = x.page.info().start; // 设置第几页
				jyls_table.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});

			});
	   /*     jyls_table.on('click', 'tr', function () {
	              $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF");
	        });*/

	        var jyspmx_table = $('#jyspmx_table').DataTable({
	            "searching": false,
	            "serverSide": true,
	            "sServerMethod": "POST",
	            "processing": true,
	            ordering: false,
	            ajax: {
	                "url": "kp/getjyspmxlist",
	                data: function (d) {
	                	 var djhArr = [];
	                  /*   $('input[name="chk"]:checked').each(function(){    
	                             djhArr.push($(this).val()); 
	                     });
	                    d.djh = djhArr.join(",");*/
	                	 d.djh = $("#djh").val();
	                }
	            },
	            "columns": [
	                {"data": "spmxxh"},
	                {"data": "spmc"},
	                {"data": "spggxh"},
	                {"data": "spdw"},
	                {"data": function (data) {
	                    if (data.sps) {
	                        return FormatFloat(data.sps, "###,###.00");
	                    } else {
	                        return null;
	                    }
	                }, 'sClass': 'right'},
	                {"data": function (data) {
	                    if (data.spdj) {
	                        return FormatFloat(data.spdj, "###,###.00");
	                    } else {
	                        return null;
	                    }
	                }, 'sClass': 'right'},
	                {"data": function (data) {
	                    if (data.spje) {
	                        return FormatFloat(data.spje, "###,###.00");
	                    } else {
	                        return null;
	                    }
	                }, 'sClass': 'right'},
	                {"data": function (data) {
	                    if (data.spsl) {
	                        return FormatFloat(data.spsl, "###,###.00");
	                    } else {
	                        return null;
	                    }
	                }, 'sClass': 'right'},
	                {"data": function (data) {
	                    if (data.spse) {
	                        return FormatFloat(data.spse, "###,###.00");
	                    } else {
	                        return null;
	                    }
	                }, 'sClass': 'right'},
	                {"data": function (data) {
	                    if (data.jshj) {
	                        return FormatFloat(data.jshj, "###,###.00");
	                    } else {
	                        return null;
	                    }
	                }, 'sClass': 'right'}
	            ]
	        });
	        jyspmx_table.on('draw.dt', function(e, settings, json) {
				var x = jyspmx_table, page = x.page.info().start; // 设置第几页
				jyspmx_table.column(0).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});

			});
	        $('#kp_search').click(function () {
	        	$("#ycform").resetForm();
	        	$('#xzxfq').attr("selected","selected");
	         	$('#xzlxq').attr("selected","selected");
	        	jyls_table.ajax.reload();
	        });
	        $('#kp_search1').click(function () {
	        	$("#dxcsz").val("");
	        	jyls_table.ajax.reload();
	        });
	        //删除
	        $("#kp_del").click(function () {
	            var djhArr = [];
//	            $('input[name="chk"]:checked').each(function(){    
//	                djhArr.push($(this).val()); 
//	        	});
	            jyls_table.column(0).nodes().each(function(cell, i) {

					var $checkbox = $(cell).find('input[type="checkbox"]');
					if ($checkbox.is(':checked')) {

						var row =jyls_table.row(i).data().djh;
						djhArr.push(row);
					}

				});
	            if (djhArr.length == 0) {
	            	$("#alertt").html("请选择需要删除的交易流水...");
	            	$("#my-alert").modal('open');
	                return;
	            }
	      if (!confirm("确认删除么?")) {
							return;
						} 
	      $("#kp_kp").attr('disabled',"true"); 
	      $("#kp_kpdy").attr('disabled',"true"); 
	      $("#kp_del").attr('disabled',"true"); 
	                $.post("kp/doDel",
							"djhArr="+ djhArr.join(","),
							function(res) {
	                	$('#kp_kp').removeAttr("disabled"); 
	                	$('#kp_kpdy').removeAttr("disabled"); 
	                	$('#kp_del').removeAttr("disabled");  
								if (res) {
					            	$("#my-alert").modal("删除成功");
					            	jyls_table.ajax.reload();
								}
					});
	        });
	        
	        $('#jyls_table tbody').on('click', 'tr', function () {
	            if ($(this).hasClass('selected')) {
	                $(this).removeClass('selected');
	                $(this).find('td:eq(0) input').prop('checked',false) 
	            } else {
	               // jyls_table.$('tr.selected').removeClass('selected');
	                $(this).find('td:eq(0) input').prop('checked',true)  
	                $(this).addClass('selected');
	            }
	            var data = jyls_table.row($(this)).data();
	            $("#djh").val(data.djh);
	            //$("#formid").val(data.djh);
	            jyspmx_table.ajax.reload();
	        });
	        var mxarr = [];
	        var $modal = $("#my-alert-edit");
	        $("#my-alert-edit").on("open.modal.amui", function () {
	            $("#mx_form").validator("destroy");
	            $("#main_form").validator("destroy");
	            jyspmx_edit_table.clear();
	            jyspmx_edit_table.draw();
	        });
	        $('#kp_add').click(function () {
	            mxarr = [];
	            $('#mx_form').resetForm();
	            $('#main_form').resetForm();
	            $modal.modal({"width": 800, "height": 600});
	        });
	        $('#check_all').change(function () {
	        	if ($('#check_all').prop('checked')) {
	        		jyls_table.column(0).nodes().each(function (cell, i) {
	                    $(cell).find('input[type="checkbox"]').prop('checked', true);
	                });
	            } else {
	            	jyls_table.column(0).nodes().each(function (cell, i) {
	                    $(cell).find('input[type="checkbox"]').prop('checked', false);
	                });
	            }
	        	  jyspmx_table.ajax.reload();
	        });


	        $('#kp_kp').click(function () {
	            var djhArr = [];
	            var djhArr1 = [];
	            var bckpje = [];
//	            $('#jyspmx_table input[name="chk"]:checked').each(function(){    
//	                    djhArr.push($(this).val()); 
//	            });
	            jyls_table.column(0).nodes().each(function(cell, i) {

					var $checkbox = $(cell).find('input[type="checkbox"]');
					if ($checkbox.is(':checked')) {

						var row =jyls_table.row(i).data().djh;
						djhArr.push(row);
					}

				});
	            if (djhArr.length == 0) {
	            	$("#alertt").html("请勾选需要开票的交易流水");
	            	$("#my-alert").modal('open');
	                return;
	            }
	        	
	            $("#kp_kp").attr('disabled',"true"); 
	            $("#kp_kpdy").attr('disabled',"true"); 
	            $("#kp_del").attr('disabled',"true"); 
	            $("#conft").html("确认开票么")
	        if (!confirm("确认开票么")) {
	        	$('#kp_kp').removeAttr("disabled"); 
	        	$('#kp_kpdy').removeAttr("disabled"); 
	        	$('#kp_del').removeAttr("disabled"); 
							return;
						} 
	            $.ajax({
	                url: "kp/doKp", context: document.body, data:{ "djhArr" : djhArr.join(","),"dybz":"0"}, success: function (data) {
	                    if (data.success) {
	                    	$('#kp_kp').removeAttr("disabled"); 
	                    	$('#kp_kpdy').removeAttr("disabled"); 
	                    	$('#kp_del').removeAttr("disabled");  
	                    	$("#alertt").html("开票成功");
	                    	$("#my-alert").modal('open');
	                        jyls_table.ajax.reload();
	                    } else {
	                    	$('#kp_kp').removeAttr("disabled"); 
	                    	$('#kp_kpdy').removeAttr("disabled"); 
	                    	$('#kp_del').removeAttr("disabled"); 
	                    	jyls_table.ajax.reload();
	                    	$("#alertt").html(data.msg);
	                    	$("#my-alert").modal('open');
	                    }
	                 //   $("#fpjek").modal("close");
	                    
	              //      $('#savet').removeAttr("disabled");
	                }
	            });        
	        });
	        
	        $('#kp_kpdy').click(function () {
	            var djhArr = [];
//	            $('#jyspmx_table input[name="chk"]:checked').each(function(){    
//	                    djhArr.push($(this).val());
//	                    
//	            });
	            jyls_table.column(0).nodes().each(function(cell, i) {

					var $checkbox = $(cell).find('input[type="checkbox"]');
					if ($checkbox.is(':checked')) {

						var row =jyls_table.row(i).data().djh;
						djhArr.push(row);
					}

				});
	            if (djhArr.length == 0) {
	            	$("#alertt").html("请勾选需要开票的交易流水");
	            	$("#my-alert").modal('open');
	                return;
	            }

	            var skpid="";
	            var fpzldm="";
	/*            var d = jyls_table.rows().data();*/
	            var flag = true;
	            $(":checkbox:checked","#jyls_table").each(function(){
	            	var tr = $(this).parents("tr");
	            	var data = jyls_table.row(tr).data();
	        		if(skpid==""){
	        			skpid =  data.skpid;
	        		}else{
	        			if(skpid!=data.skpid){
	        				$("#alertt").html("批量勾选的开票点不一致,请重新勾选");
	                    	$("#my-alert").modal('open');
	        				flag = false;
	        				return false;
	        			}
	        		}
	        		if(fpzldm==""){
	        			fpzldm =  data.fpzldm;
	        		}else{
	        			if(fpzldm!=data.fpzldm){
	        				$("#alertt").html("批量勾选的发票种类不一致,请重新勾选");
	                    	$("#my-alert").modal('open');
	                    	flag = false;
	        				return false;
	        			}
	        		}
	            })
	/*            $.each(d,function(n,value) { 
	            	if($(this).find("td[checked='checked']").length>0){//表示此行选中
	            		var data = $("#jyls_table").row($(this)).data();
	            		if(skpid==""){
	            			skpid =  data.skpid;
	            		}else{
	            			if(skpid!=data.skpid){
	            				$("#alertt").html("批量勾选的开票点不一致,请重新勾选");
	                        	$("#my-alert").modal('open');
	            				return ;
	            			}
	            		}
	            		if(fpzldm==""){
	            			fpzldm =  data.fpzldm;
	            		}else{
	            			if(fpzldm!=data.fpzldm){
	            				$("#alertt").html("批量勾选的发票种类不一致,请重新勾选");
	                        	$("#my-alert").modal('open');
	            				return ;
	            			}
	            		}
	            	}
	            	});*/
	            if(flag){
	                $("#kp_kp").attr('disabled',"true"); 
	                $("#kp_kpdy").attr('disabled',"true"); 
	                $("#kp_del").attr('disabled',"true"); 
	            $.ajax({
	                url: "kp/hqfphm", data:{ "fpzldm" :fpzldm,"skpid":skpid }, success: function (data) {
	                    if (data.success) {
	                        $("#doc-modal-fphm").modal("open");
	                        $("#fpdm").val(data.fpdm);
	                        $("#fphm").val(data.fphm);
	                        $('#kp_kp').removeAttr("disabled"); 
	                    	$('#kp_kpdy').removeAttr("disabled"); 
	                    	$('#kp_del').removeAttr("disabled");     
	                    } else {
	                    	$("#alertt").html(data.msg);
	                    	$("#my-alert").modal('open');
	                    	$('#kp_kp').removeAttr("disabled"); 
	                    	$('#kp_kpdy').removeAttr("disabled"); 
	                    	$('#kp_del').removeAttr("disabled");      
	                    }
	                }
	            });
	        	   
	            }
	            
	        });
	        
	        $('#kp_kpdyqr').click(function () {
	        	   var djhArr = [];
//	               $('input[name="chk"]:checked').each(function(){    
//	                       djhArr.push($(this).val());
//	                       
//	               });
	               jyls_table.column(0).nodes().each(function(cell, i) {

						var $checkbox = $(cell).find('input[type="checkbox"]');
						if ($checkbox.is(':checked')) {

							var row =jyls_table.row(i).data().djh;
							djhArr.push(row);
						}

					});
	             $("#kp_kpdyqr").attr('disabled',"true"); 
	     /* 	  if (!confirm("确认全部开票打印么,请检查打印机是否放好发票?")) {
							return;
						}*/
	            $.ajax({
	                url: "kp/doKp", context: document.body, data:{ "djhArr" : djhArr.join(","),"dybz":"1"}, success: function (data) {
	                    if (data.success) {
	                    	  $("#doc-modal-fphm").modal("close");
	                    	$("#alertt").html("开票成功");
	                    	$('#kp_kpdyqr').removeAttr("disabled");  
	                    	$("#my-alert").modal('open');
	                        jyls_table.ajax.reload();
	                    } else {
	                    	  $("#doc-modal-fphm").modal("close");
	                    		$('#kp_kpdyqr').removeAttr("disabled");  
	                    	$("#alertt").html(data.msg);
	                    	
	                    	$("#my-alert").modal('open');
	                    }
	                 //   $("#fpjek").modal("close");
	                    
	              //      $('#savet').removeAttr("disabled");
	                }   	  
	      	  });
	      
	        });
	        
	        
	        $('#savet').click(function () {
	        	 $("#savet").attr('disabled',"true"); 
	            var djhArr = [];
//	            $('input[name="chk"]:checked').each(function(){    
//	                    djhArr.push($(this).val()); 
//	            });
	            jyls_table.column(0).nodes().each(function(cell, i) {

					var $checkbox = $(cell).find('input[type="checkbox"]');
					if ($checkbox.is(':checked')) {

						var row =jyls_table.row(i).data().djh;
						djhArr.push(row);
					}

				});
	            if (djhArr.length == 0) {
					$("#alertt").html("请勾选需要开票的交易流水...");
                	$("#my-alert").modal('open');
	                return;
	            }
	            if($("#s_xfsh").val()==""||$("#s_fplx").val()==""){
						$("#alertt").html("请选择销方和开票类型...");
                    	$("#my-alert").modal('open');
	                  return;
	            }
	            var kpxe = $("#fpjesrk").val()

	        })

	        $('#kp_all').click(function () {
	            if (!confirm("您确认开票？")) {
					return;
				}
	            $.ajax({
	                url: "kp/doAllKp?clztdm=00",
	                type: "post",
	                data: {
	                    xfsh : $('#xfsh1').val(),   // search 销方
	                    gfmc : $('#gfmc1').val(),	// search 购方名称
	                    ddh : $('#s_ddh1').val(),  // search 订单号
	                    jylsh : $('#s_lsh1').val(),   // search 发票号码
	                    rqq : $('#s_rqq1').val(), // search 开票日期
	                    rqz : $('#s_rqz1').val() // search 开票日期
	                    
	                }, 
	                success: function (data) {
	                    if (data.success) {
							$("#alertt").html("发送到开票队列成功!");
	                    	$("#my-alert").modal('open');
	                        jyls_table.ajax.reload();
	                    } else {
	                        alert(data.msg);
	                    }
	                }
	            });
	        });
	        var index = 1;

	        var jyspmx_edit_table = $('#jyspmx_edit_table').DataTable({
	            "searching": false,
	            "bPaginate": false,
	            "bSort": false,
	            "scrollY": "70",
	            "scrollCollapse": "true"
	        });
	        $('#my-alert-edit').on('open.modal.amui', function () {
//	            $("#main_tab").tabs('open', 0);
	        });
	        $('#main_tab').find('a.ai').on('opened.tabs.amui', function (e) {
	            jyspmx_edit_table.draw();
	        })
	        $("#addRow").click(function () {
	            var r = $("#mx_form").validator("isFormValid");
	            if (r) {
	                var spdm = $("#spdm_edit").val();
	                var mc = $("#mc_edit").val();
	                var ggxh = $("#ggxh_edit").val();
	                var dw = $("#dw_edit").val();
	                var sl = $("#sl_edit").val();

	                var dj = $("#dj_edit").val();
	                var je = $("#je_edit").val();
	                var sltaxrate = $("#sltaxrate_edit").val();
	                var se = $("#se_edit").val();
	                var jshj = $("#jshj_edit").val();
	                index = mxarr.length + 1;
	                jyspmx_edit_table.row.add([
	                    "<span class='index'>" + index + "</span>", spdm, mc, ggxh, dw, sl, dj, je, sltaxrate, se, jshj, "<a href='#'>删除</a>"
	                ]).draw();
	                mxarr.push(index);
	            }
	        });

//	        $("#sl_edit").change(function () {
//	            var sl = $("#sl_edit").val();
//	            var dj = $("#dj_edit").val();
//	            $("#je_edit").val((sl * dj).toFixed(2));
//	            var sltaxrate = $("#sltaxrate_edit").val();
	//
//	            $("#se_edit").val((sl * dj * sltaxrate).toFixed(2));
//	            $("#jshj_edit").val($("#je_edit").val() / 1 + $("#se_edit").val() / 1);
//	        });
//	        $("#dj_edit").change(function () {
//	            var sl = $("#sl_edit").val();
//	            var dj = $("#dj_edit").val();
//	            $("#je_edit").val((sl * dj).toFixed(2));
//	            var sltaxrate = $("#sltaxrate_edit").val();
	//
//	            $("#se_edit").val((sl * dj * sltaxrate).toFixed(2));
//	            $("#jshj_edit").val($("#je_edit").val() / 1 + $("#se_edit").val() / 1);
//	        });

//	        $("#sltaxrate_edit").change(function () {
//	            var sl = $("#sl_edit").val();
//	            var dj = $("#dj_edit").val();
//	            $("#je_edit").val((sl * dj).toFixed(2));
//	            var sltaxrate = $("#sltaxrate_edit").val();
	//
//	            $("#se_edit").val((sl * dj * sltaxrate).toFixed(2));
//	            $("#jshj_edit").val($("#je_edit").val() / 1 + $("#se_edit").val() / 1);
//	        });

	        $('#jyspmx_edit_table tbody').on('click', 'a', function () {
	            jyspmx_edit_table.row($(this).parents("tr")).remove().draw(false);
	            mxarr.pop();
	            $('#jyspmx_edit_table tbody').find("span.index").each(function (index, object) {
	                $(object).html(index + 1);
	            });
	        });

	        $("#save").click(function () {
	            var r = $("#main_form").validator("isFormValid");
	            if (r) {
	                var ps = [];
	                var d = jyspmx_edit_table.rows().data();
	                if (d.length == 0) {
	                    $("#main_tab").tabs('open', 1);
	                    return;
	                }
	                ps.push("mxcount=" + d.length);
	                d.each(function (data, index) {
	                    $(data).each(function (i, c) {
	                        if (i == 1) {
	                            ps.push("spdm=" + c);
	                        } else if (i == 2) {
	                            ps.push("spmc=" + c);
	                        } else if (i == 3) {
	                            ps.push("ggxh=" + c);
	                        } else if (i == 4) {
	                            ps.push("dw=" + c);
	                        } else if (i == 5) {
	                            ps.push("sl=" + c);
	                        } else if (i == 6) {
	                            ps.push("dj=" + c);
	                        } else if (i == 7) {
	                            ps.push("je=" + c);
	                        } else if (i == 8) {
	                            ps.push("rate=" + c);
	                        } else if (i == 9) {
	                            ps.push("se=" + c);
	                        } else if (i == 10) {
	                            ps.push("jshj=" + c);
	                        }
	                    });
	                });
	                var frmData = $("#main_form").serialize() + "&" + ps.join("&");
	                $.ajax({
	                    url: "kp/save", "type": "POST", context: document.body, data: frmData, success: function (data) {
	                        if (data.success) {
	        					$("#alertt").html("保存成功!");
	                        	$("#my-alert").modal('open');
	                            jyls_table.ajax.reload();
	                        } else {
	        					$("#alertt").html(data.msg);
	                        	$("#my-alert").modal('open');
	                        }
	                    }
	                });
	            } else {
	                ///如果校验不通过
	                $("#main_tab").tabs('open', 0);
	            }
	        });
	        $("#close").click(function () {
	            $modal.modal("close");
	        });
	        $("#closet").click(function () {
	        	 $('#kp_kp').removeAttr("disabled");
	        	  $('#savet').removeAttr("disabled");
	           $("#fpjek").modal("close");
	        });
	        //批量导入
	        var $importModal = $("#bulk-import-div");
	        $("#kp_dr").click(function () {
	        	$('#importExcelForm').resetForm();
	            $importModal.modal({"width": 600, "height": 350});
	        });

	        $("#close1").click(function () {
	            $importModal.modal("close");
	        });
	        $("#close2").click(function () {
	            $importConfigModal.modal("close");
//	        	$importConfigModal.modal({"width": 600, "height": 480});
	        });
	        //导入配置
	        var $importConfigModal = $("#import_config_div");
	        $("#btnImportConfig").click(function () {
//	            $importModal.modal("close");
	            $('#importConfigForm').resetForm();
	            $importConfigModal.modal({"width": 600, "height": 480});
	        });
			/***发票开具*/
	        
	        
	        
	        
			return t;
		},

		/**
		 * search action
		 */
		search_ac : function() {
			var _this = this;
			el.$jsSearch.on('click', function(e) {
				$('#bj').val('1');
				e.preventDefault();
				_this.tableEx.ajax.reload();

			});
			el.$jsSearch1.on('click', function(e) {
				$('#bj').val('2');
				if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
						|| (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
					$("#alertt").html('Error,请选择开始和结束时间!');
                	$("#my-alert").modal('open');
					return false;
				}
				var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
				var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
				if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
					if (dt1.getYear() == dt2.getYear()) {
						if (dt1.getMonth() == dt2.getMonth()) {
							if (dt1 - dt2 > 0) {
								$("#alertt").html('开始日期大于结束日期,Error!');
		                    	$("#my-alert").modal('open');
								return false;
							}
						} else {
							// alert('月份不同,Error!');
							$("#alertt").html('Error,请选择同一个年月内的时间!');
	                    	$("#my-alert").modal('open');
							return false;
						}
					} else {
						// alert('年份不同,Error!');
						$("#alertt").html('Error,请选择同一个年月内的时间!');
                    	$("#my-alert").modal('open');
						return false;
					}
				}
				e.preventDefault();
				_this.tableEx.ajax.reload();

			});
		},
		/**
		 * 导出按钮
		 */
		exportAc : function() {
			el.$jsExport.on('click', function(e) {
				var bj = $('#bj').val();
				if (bj == '1') {
					$('#searchform').submit();
				}else{
					var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
					var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
					if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
						if (dt1.getYear() == dt2.getYear()) {
							if (dt1.getMonth() == dt2.getMonth()) {
								if (dt1 - dt2 > 0) {
									$("#alertt").html('开始日期大于结束日期,Error!');
			                    	$("#my-alert").modal('open');
									return false;
								}
							} else {
								// alert('月份不同,Error!');
								$("#alertt").html('Error,请选择同一个年月内的时间!');
		                    	$("#my-alert").modal('open');
								return false;
							}
						} else {
							// alert('年份不同,Error!');
							$("#alertt").html('Error,请选择同一个年月内的时间!');
	                    	$("#my-alert").modal('open');
							return false;
						}
					}
					$("#searchform1").submit();
				}
				
			});
		},
		/**
		 * 自定义列
		 */
		autoColumn : function() {
			el.$jsAuto.on('click', function(e) {
				$.ajax({
					url : 'zdyl/query',
					type : 'POST', //GET
					async: false,
					data : {},
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {
						$.each(data.list, function(n, value) {
							var j ="#"+value.zddm;
							$(j).attr("checked",'true');
						});
					}
				})
				$('#biaoti').modal('open');
			});
		},
		/**
		 * 保存自定义列
		 */
		saveColumn : function() {
			el.$jsSubmit.on('click', function(e) {
				el.$jsSubmit.attr({"disabled":"disabled"});
				$.ajax({
					type : "POST",
					url : "zdyl/save",
					data : $('#biao').serialize(),// 你的formid
					success : function(data) {
						el.$jsSubmit.removeAttr("disabled");
						$('#biaoti').modal('close');
						$("#alertt").html(data.msg);
                    	$("#my-alert").modal('open');
						 refresh();
					}
				});
			});
		},
		/**
		 * 自定义导出列
		 */
		autoColumn1 : function() {
			el.$jsOut.on('click', function(e) {
				$.ajax({
					url : 'zdyl/queryOut',
					type : 'POST', //GET
					async: false,
					data : {},
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {
						$.each(data.list, function(n, value) {
							var j ="#"+value.zddm+"1";
							$(j).attr("checked",'true');
						});
					}
				})
				$('#biaoti1').modal('open');
			});
		},
		/**
		 * 保存自定义导出列
		 */
		saveColumn1 : function() {
			el.$jsSubmit1.on('click', function(e) {
				el.$jsSubmit1.attr({"disabled":"disabled"});
				$.ajax({
					type : "POST",
					url : "zdyl/saveOut",
					data : $('#biao1').serialize(),// 你的formid
					success : function(data) {
						el.$jsSubmit1.removeAttr("disabled");
						$('#biaoti1').modal('close');
						alert(data.msg)
					}
				});
			});
		},
		//批量打印
		printAc : function() {
			var _this = this;
			var ids = '';
			var flag = true;
			el.$jsPrint
					.on(
							'click',
							function(e) {
								_this.tableEx
										.column(0)
										.nodes()
										.each(
												function(cell, i) {
													if (flag) {
														var $checkbox = $(cell)
																.find(
																		'input[type="checkbox"]');
														if ($checkbox
																.is(':checked')) {
															var rows = $(
																	'.js-table')
																	.find('tr');
															var fphm = rows[Number(i) + 2].cells[6].innerHTML;
															if (fphm == null
																	|| fphm == '') {
																$("#alertt").html("存在正在开具或者开具失败的发票，不能批量打印！");
										                    	$("#my-alert").modal('open');
																flag = false;
															}
															ids += $checkbox
																	.val()
																	+ ',';
														}
													}
												});
								if (flag) {
									if (ids != '') {
										window
												.open('fpcx/printmany?ids='
														+ ids, '',
														'scrollbars=yes,status=yes,left=0,top=0,menubar=yes,resizable=yes,location=yes');
										$('#kplshStr').val(ids);
										ids = '';
									} else {
										$("#alertt").html("请先选中至少一条记录！");
				                    	$("#my-alert").modal('open');
									}
								}
							});
		},
		//全选按钮
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
		modalAction : function() {
			el.$jsClose.on('click', function() {
				$('#biaoti').modal('close');
			});
			el.$jsClose1.on('click', function() {
				$('#biaoti1').modal('close');
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
			_this.exportAc();
			_this.autoColumn();
			_this.autoColumn1();
			_this.printAc();
			_this.checkAllAc();
			_this.modalAction(); // hidden action
			_this.saveColumn();
			_this.saveColumn1();
		}
	};
	action.init();

});


function delcommafy(num){
	   if((num+"").trim()==""){
	      return "";
	   }
	   num=num.replace(/,/gi,'');
	   return num;
	}