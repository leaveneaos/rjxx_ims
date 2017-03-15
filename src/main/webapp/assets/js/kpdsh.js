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
		$s_dyzt : $('#s_dyzt'), // search 开票日期
		$jsSearch : $('.js-search'),
		$jsExport : $('.js-export'),
		$jsLoading : $('.js-modal-loading'),
		$jsPrint : $('.js-print'),
		$checkAll : $('#select_all')
	};
		  $(this).removeData('amui.modal');

	
	 var $tab = $('#doc-tab-demo-1');
	  //开票商品明细table

    

    
    $("#yhqrbc").click(function(){
		$.ajax({
			type : "POST",
			url : "kpdsh/yhqrbc",
			data : {},
			success : function(data) {
				$("#alertt").html("保存成功");
            	$("#my-alert").modal('open');
            /*	$("#cljg").hide();*/
            	$("#cljgbt").hide();
            	$tab.tabs('refresh');
            	$tab.tabs('open', 0);
            	t.ajax.reload();
				kpspmx_table.ajax.reload();
			}
		});
    	});
    $("#yhqx").click(function(){
     	/*$("#cljg").hide();*/
    	$("#cljgbt").hide();
    	$tab.tabs('refresh');
    	$tab.tabs('open', 0);
    	kpspmx_table.ajax.reload();
    	});
    
	  //开票商品明细table
    var kpspmx_table = $('#mxTable1').DataTable({
        "searching": false,
        "serverSide": true,
        "sServerMethod": "POST",
        "processing": true,
        "bPaginate":false,
        "bLengthChange":false,
        "bSort":false,
        "bInfo": false,
        "scrollX": true,
        ajax: {
            "url": "kpdsh/getMx",
            data: function (d) {
                d.sqlsh = $("#kplsh").val();
            }
        },
        "columns": [
            {"data": "spmxxh"},
            {"data": "spmc"},
            {"data": function (data) {
          	  if (data.kkjje) {
          		     return '<input type="text" class="bckpje" name="bckpje" value="'+data.kkjje+'">';
                } else {
                	  return '<input readonly="readonly" type="text" class="bckpje" name="bckpje" value="'+data.kkjje+'">';
                }
         
          }, 'sClass': 'right'},
          {"data": function (data) {
              if (data.kkjje) {
                  return FormatFloat(data.kkjje, "###,###.00");
              } else {
                  return 0;
              }
          }, 'sClass': 'right'},
          {"data": function (data) {
              if (data.ykjje) {
                  return FormatFloat(data.ykjje, "###,###.00");
              } else {
                  return 0;
              }
          }, 'sClass': 'right'},
            {"data": "spggxh"},
            {"data": "spdw"},
            {
            	"data": null,
                "render": function (data) {
                    if (data.sps) {
                        return FormatFloat(data.sps,
                            "###,###.00");
                    }else{
                        return null;
                    }
                },
                'sClass': 'right'
                
            },
            {
            	"data": function (data) {
                    if (data.spdj) {
                        return FormatFloat(data.spdj,
                            "###,###.00");
                    }else{
                        return null;
                    }
                },
                'sClass': 'right'
            		},
            {
              "data": function (data) {
                     if (data.spje) {
                         return FormatFloat(data.spje,
                             "###,###.00");
                     }else{
                         return null;
                     }
                 },
                 'sClass': 'right'
            },
            { 
            	"data": function (data) {
                if (data.spsl) {
                    return FormatFloat(data.spsl,
                        "###,###.00");
                }else{
                    return null;
                }
              },
              'sClass': 'right'
            },
            { 
            	"data": function (data) {
                if (data.spse) {
                    return FormatFloat(data.spse,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            },
            { 
            	"data": function (data) {
                if (data.jshj) {
                    return FormatFloat(data.jshj,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            }/*,{
                "data": null,
                "render": function (data) {
                    return '<a href="#" class="modify1" style="margin-right: 10px;">修改</a>     '+
                        '<a class="kpdmx" href="#">删除</a>'
                }
            }*/
        ]
    });
    $('#mxTable1').on( 'draw.dt', function () {
    	   if($("input[name='dxk']:checked").length>1){
    		 var ycl = $("input[name='bckpje']");
    			for (var i = 0; i < ycl.length; i++) {
        			$(ycl).attr("readonly","readonly");
    	          } 
    	   }
    } );
    kpspmx_table.on('click', 'a.kpdmx', function () {
    	var id = kpspmx_table.row($(this).parents('tr')).data().id;

    	     if (!confirm("您确认删除？")) {
				return;
			}
			$.ajax({
			type : "POST",
			url : "kpdsh/mxsc",
			data : {"id":id},
			success : function(data) {
				$("#alertt").html("删除成功");
            	$("#my-alert").modal('open');
				  kpspmx_table.ajax.reload();
			}
		});
   });
    kpspmx_table.on('click', 'a.modify1', function () {
    	var row = kpspmx_table.row($(this).parents('tr')).data();
/*		$.ajax({
			type : "POST",
			url : "kpdsh/cxsp",
			 async: false,
			data : {},
			success : function(data) {
				var option;
				$('#mx_spmc').html("");
				for (var i = 0; i < data.sps.length; i++) {
					option+= "<option value='"+data.sps[i].id+"'>"+data.sps[i].spmc+"("+data.sps[i].spbm+")</option>";
				}
				$('#mx_spmc').append(option);
				
			}
		});*/
    	$('#my-alert-edit1').modal({"width": 480, "height": 550});
    	/*$('#mx_spmc').val(row.spid);*/
    	$('#mx_spmx').val(row.spmc);
    	$('#mx_ggxh').val(row.spggxh);
    	$('#mx_spdw').val(row.spdw);
    	$('#mx_spsl').val(row.sps);
    	$('#mx_spdj').val(row.spdj);
    	$('#mx_spje').val(row.spje);
    	$('#mx_sl').val(row.spsl);
    	$('#mx_spse').val(row.spse);
    	$('#mx_jshj').val(row.jshj);
    	$('#formid1').val(row.id);
    });
    var t;
    var kpspmx_table3
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'kpdsh/getItems'
		},
		dataTable : function() {
			var _this = this;
			t = el.$jsTable.DataTable({
				"processing" : true,
				"serverSide" : true,
				ordering : false,
				searching : false,
				"scrollX" : true,
				"ajax" : {
					url : _this.config.getUrl,
					type : 'POST',
					data : function(d) {
						d.kprqq = $("#s_rqq").val(); // search 开票日期
						d.kprqz = $("#s_rqz").val(); // search 开票日期
					    d.xfsh = $('#s_xfsh').val();   // search 销方
		                d.gfmc = $('#s_gfmc').val();	// search 购方名称
		                d.ddh = $('#s_ddh').val();   // search 订单号
		                d.fpzldm = $('#s_fplx').val();   // search 发票号码
		                var csm =  $('#dxcsm').val()
		                if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){
		                    d.gfmc = $('#dxcsz').val()
		                 }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){
		                    d.ddh = $('#dxcsz').val()
		                  }
					}
				},
	            "columns": [
	                        	{
	                        		"orderable" : false,
	                        		"data" : null,
	                        		render : function(data, type, full, meta) {
	                        			return '<input type="checkbox" name= "dxk" value="'
	                        				+ data.sqlsh + '" />';
	                        		}
	                        	},
	                        {
	    	                    "orderable": false,
	    	                    "data": null,
	    	                    "defaultContent": ""
	                        },
	                      /*  {"data": "sqlsh"},*/
	                        {"data": "ddh"},
	                        {"data": "ddrq"},
	                        {"data": null,
	                           "render": function (data) {
	                        	   //var fpjee = FormatFloat(data.fpje, "###,###.00")
	                                return '<input type="text" onkeyup="yzje(this)" class="am-text-money" max="'+data.zdje+'" name="fpje" value="'+data.fpje +'">';
	                                }
	                        },
	                        {"data": function(data){
	                        	if("01"==data.fpzldm){
	                        	 	return "增值税专用发票";
	                        	}else if("02"==data.fpzldm){
	                        	 	return "增值税普通发票";
	                        	}
	                        	else if("12"==data.fpzldm){
	                        	 	return "电子发票(增普)";
	                        	}else{
	                        		return "";
	                        	}
	                       
	                        }},
	                        {"data": "gfmc"},
	                        {"data": "gfsh"},
	                        {"data": "gfdz"},
	                     /*   {"data": "gflxr"},*/
	                        {"data": "gfdh"},
	                        {"data": "gfyh"},
	                        {"data": "gfyhzh"},
	                        {"data": "bz"},
	                        {"data": function (data) {
	                            if (data.jshj) {
	                                return FormatFloat(data.jshj, "###,###.00");
	                            } else {
	                                return null;
	                            }
	                        }, 'sClass': 'right'},    {
	                            "data": null,
	                            "render": function (data) {
	                                return '<a href="#" class="modify" style="margin-right: 10px;">修改</a>     '/*+
	                                    '<a class="kpdth" href="#">退回</a>'*/
	                            }
	                        }
	                    ]
			});
		    kpspmx_table3 = $('#mxTable3').DataTable({
		        "searching": false,
		        "serverSide": true,
		        "sServerMethod": "POST",
		        "processing": true,
		        "bPaginate":false,
		        "bLengthChange":false,
		        "bSort":false,
		        "bInfo": false,
		        "scrollX": true,
		        ajax: {
		            "url": "kpdsh/kpdshkp",
		            async:false,
		            data: function (d) {
		        		var chk_value="" ;
						var fpxes = "";
						var hsbzs = "";
						
						$('input[name="dxk"]:checked').each(function(cell,i){
						chk_value+=$(this).val()+",";
						var row = $(this).parents('tr').find('input[name="fpje"]');
						fpxes+=row.val()+","
						});
						t.column(0).nodes().each(function(cell, i) {
							var $checkbox = $(cell).find('input[type="checkbox"]');
							if ($checkbox.is(':checked')) {
								var hsbz =t.row(i).data().fpjshsbz;
								hsbzs+=hsbz+","
							}

						});
						d.fpjshsbz= hsbzs;
						var ddhs = chk_value.substring(0, chk_value.length-1);
						fpxes = fpxes.substring(0, fpxes.length-1);
						d.sqlshs= ddhs;
						d.fpxes=fpxes;
						var bckpje = [];
						if($("input[name='dxk']:checked").length==1){
						
				            var els1 =document.getElementsByName("bckpje");
							for(var i=0;i<els1.length;i++){
								var fpp = els1[i].value;
								bckpje.push(fpp); 
							}
						}
						d.bckpje=bckpje.join(",");
		            }
		        },
		        "columns": [
		            {"data": "sjts"},
		            {"data": "fpnum"},
		            {"data": "djh"},
		            {"data": "gfmc"},
		            {"data": "spmc"},
		            {"data": "spggxh"},
		            {"data": "spdw"},
		            {"data": function (data) {
		                    if (data.sps) {
		                        return FormatFloat(data.sps,
		                            "###,###.00");
		                    }else{
		                        return null;
		                    }
		                },
		                'sClass': 'right'
		                
		            },
		            {"data": function (data) {
		                    if (data.spdj) {
		                        return FormatFloat(data.spdj,
		                            "###,###.00");
		                    }else{
		                        return null;
		                    }
		                },
		                'sClass': 'right'
		            		},
		            {"data": function (data) {
		                     if (data.spje) {
		                         return FormatFloat(data.spje,
		                             "###,###.00");
		                     }else{
		                         return null;
		                     }
		                 },
		                 'sClass': 'right'
		            },
		            {"data": function (data) {
		                if (data.spsl) {
		                    return FormatFloat(data.spsl,
		                        "###,###.00");
		                }else{
		                    return null;
		                }
		              },
		              'sClass': 'right'
		            },
		            {"data": function (data) {
		                if (data.spse) {
		                    return FormatFloat(data.spse,
		                        "###,###.00");
		                }else{
		                    return null;
		                }
		            },
		            'sClass': 'right'
		            },
		            {"data": function (data) {
		                if (data.jshj) {
		                    return FormatFloat(data.jshj,
		                        "###,###.00");
		                }else{
		                    return null;
		                }
		            },
		            'sClass': 'right'
		            }
		        ]
		    });
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
				 //$('#jyls_table tr').find('td:eq(2)').hide();
			});
/*			t.on('click','a.yulan',function() {
				var da = t.row($(this).parents('tr')).data();
				window.open('fpcx/printSingle?kplsh='+ da.kplsh, '',
				'scrollbars=yes,status=yes,left=0,top=0,menubar=yes,resizable=yes,location=yes');
				$('#kplshStr').val(da.kplsh);
							});*/
/*			t.on('click', 'a.fpck', function(e) {
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
							alert(data.msg);
							e.preventDefault();
							t.ajax.reload();
						} else {
							alert(data.msg);
						}
					},
					error : function() {
						alert("出现错误，请稍后重试！");
					}
				})
			});*/
            //选中列查询明细
            $('#jyls_table tbody').on('click', 'tr', function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked',false) 
                } else {
                /*	t.$('tr.selected').removeClass('selected');*/
                    $(this).find('td:eq(0) input').prop('checked',true)  
                    $(this).addClass('selected'); 
                }
               /* $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "red");  */
             
                var data = t.row($(this)).data();
                $("#kplsh").val(data.sqlsh);
                kpspmx_table.ajax.reload();
            });
            t.on('click', 'a.modify', function () {
            	var row = t.row($(this).parents('tr')).data();

				$.ajax({
					url : 'kpdsh/xgkpd',
					data : {
						"sqlsh" : row.sqlsh,
					},
					success : function(data) {
						if (null!=data&&""!=data) {
							var jy= data.jyxx
							$('#select_xfid').val(jy.xfsh);
							$.ajax({
								url:"kp/getSkpList",
								async:false,
								data:{
								"xfsh" : jy.xfsh
							}, success:function(data) {
								if (data) {
									/*var option = $("<option>").text('请选择').val(-1);
									$('#select_skpid').append(option);*/
									var option;
									$('#select_skpid').html("");
									for (var i = 0; i < data.skps.length; i++) {
										option+= "<option value='"+data.skps[i].id+"'>"+data.skps[i].kpdmc+"</option>";
									}
									$('#select_skpid').append(option);
								}
							}});
							$('#select_skpid').val(jy.skpid);
							$('#ddh_edit').val(jy.ddh);
							$('#ddh_fplx').val(jy.fpzldm);
							$('#gfdh_edit').val(jy.gfdh);
							$('#gfsh_edit').val(jy.gfsh);
							$('#gfmc_edit').val(jy.gfmc);
							$('#gfyh_edit').val(jy.gfyh);
							$('#gfyhzh_edit').val(jy.gfyhzh);
							$('#gflxr_edit').val(jy.gflxr);
							$('#gfemail_edit').val(jy.gfemail);
							$('#gfsjh_edit').val(jy.gfsjh);
							$('#gfdz_edit').val(jy.gfdz);
							$('#tqm').val(jy.tqm);
							$('#bz').val(jy.bz);
							$('#formid').val(jy.sqlsh);
						} else {
							$("#alertt").html(data.msg);
	                    	$("#my-alert").modal('open');
						}
					},
					error : function() {
						$("#alertt").html("出现错误,请稍后再试");
                    	$("#my-alert").modal('open');
					}
				});
            	$('#my-alert-edit').modal({"width": 800, "height": 450});
            });
            t.on('click', 'a.kpdth', function () {
            	var ddhstha=t.row($(this).parents('tr')).data().sqlsh;
                if (!confirm("您确认退回么？")) {
    				return;
    			}
					$.ajax({
					type : "POST",
					url : "kpdsh/th",
					data : {"ddhs":ddhstha},
					success : function(data) {
						$("#alertt").html(data.msg);
                    	$("#my-alert").modal('open');
						_this.tableEx.ajax.reload();	
					}
				});

            });
            //删除
            $("#kpd_sc").click(function () {
        		var chk_value="" ;
        		$('input[name="dxk"]:checked').each(function(){
        		chk_value+=$(this).val()+",";
        		});
        		var ddhs = chk_value.substring(0, chk_value.length-1);
        		if(chk_value.length==0){
        			$("#alertt").html("请至少选择一条数据");
                	$("#my-alert").modal('open');
        		}else{
             if (!confirm("您确认删除么？")) {
        		return;
        	}
        			$.ajax({
        			type : "POST",
        			url : "kpdsh/sc",
        			data : {"ddhs":ddhs},
        			success : function(data) {
        				$("#alertt").html(data.msg);
                    	$("#my-alert").modal('open');
        				_this.tableEx.ajax.reload();	
        			}
        		});
        		}
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
			return t;
		},

		/**
		 * search action
		 */
		search_ac : function() {
			var _this = this;
			$("#kp_search").on('click', function(e) {
				$("#ycform").resetForm();
	        	$('#xzxfq').attr("selected","selected");
	         	$('#xzlxq').attr("selected","selected");
				_this.tableEx.ajax.reload();
			});
			$("#kp_search1").on('click', function(e) {
				$("#dxcsz").val("");
				_this.tableEx.ajax.reload();
			})
			},
		/**
		 * 退回
		 */
		th : function() {
			var _this = this;
			$("#kpd_th").on('click', function(e) {
				var chk_value="" ;
				$('input[name="dxk"]:checked').each(function(){
				chk_value+=$(this).val()+",";
				});
				var ddhs = chk_value.substring(0, chk_value.length-1);
				if(chk_value.length==0){
					$("#alertt").html("请至少选择一条数据");
                	$("#my-alert").modal('open');
				}else{
		     if (!confirm("您确认退回么？")) {
				return;
			}
					$.ajax({
					type : "POST",
					url : "kpdsh/th",
					data : {"ddhs":ddhs},
					success : function(data) {
						$("#alertt").html(data.msg);
                    	$("#my-alert").modal('open');
						_this.tableEx.ajax.reload();	
					}
				});
				}
			});
		},
		/**
		 * kp
		 */
		kp : function() {
			var _this = this;
			$("#kpd_kp").on('click', function(e) {
				var chk_value="" ;
				var fpxes = "";
				var fla = true;
				var els =document.getElementsByName("fpje");
				for(var i=0;i<els.length;i++){
					var fpje = els[i].value;
					if(fpje==0){
						$("#alertt").html("第 "+(i+1)+"行分票金额为0,请重新填写或维护开票限额");
	                	$("#my-alert").modal('open');
	                	fla=false;
	    				return false;
					}
					if(!fpje.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
        				$("#alertt").html("第 "+(i+1)+"行分票金额格式有误，请重新填写！");
	                	$("#my-alert").modal('open');
	                	fla=false;
	    				return false;
        			}
				}
				if($("input[name='dxk']:checked").length==1){
					var bckpje = [];
					var je=0;
					var rows1 = $("#mxTable1").find('tr');	
		            var els1 =document.getElementsByName("bckpje");
					for(var i=0;i<els1.length;i++){
						var fpp = els1[i].value;
						je+=fpp*1;
						bckpje.push(fpp); 
						if(fpp==0){
						}else if(!fpp.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
		    				$("#alertt").html("第 "+(i+1)+"行明细金额格式有误，请重新填写！");
		                	$("#my-alert").modal('open');
		    				return false;
		    			}else if(Number(fpp)>Number(delcommafy(rows1[i+1].cells[3].innerHTML))){
		    				$("#alertt").html("第"+(i+1)+"条明细的本次开票金额不能大于可开票金额！");
		                	$("#my-alert").modal('open');
		                	return false;
		    			}
					}
					if(je==0){
						$("#alertt").html("本次开具金额为0,请重新填写");
	                	$("#my-alert").modal('open');
	                	return false;
					}
				}
				$('input[name="dxk"]:checked').each(function(){
				chk_value+=$(this).val()+",";
				var row = $(this).parents('tr').find('input[name="fpje"]');
				fpxes+=row.fpje+","
				});
		
				var ddhsthan = chk_value.substring(0, chk_value.length-1);
				fpxes = fpxes.substring(0, fpxes.length-1);
				if(chk_value.length==0){
					$("#alertt").html("请至少选择一条数据");
                	$("#my-alert").modal('open');
				}else{
					if(!fla){
						return;
					}
		     if (!confirm("您确认审核么？")) {
				return;
			}
				/*	$.ajax({
					type : "POST",
					url : "kpdsh/kpdshkp",
					data : {"sqlshs":ddhsthan,"fpxes":fpxes},
					success : function(data) {*/
		            	$("#cljg").show();
		            	$("#cljgbt").show();
		            	  $tab.tabs('refresh');
						kpspmx_table3.ajax.reload();
						 kpspmx_table.ajax.reload();
						$('#doc-tab-demo-1').tabs('open', 1)
			/*		}
				});*/
				}
			});
		},
		/**
		 * 修改保存
		 */
		xgbc : function() {
			var _this = this;

			$("#kpd_xgbc").on('click', function(e) {
				 var r = $("#main_form").validator("isFormValid");
		          if (r) {
				$.ajax({
					type : "POST",
					url : "kpdsh/xgbckpd",
					data : $('#main_form').serialize(),
					success : function(data) {
						if(data.msg){
							$("#alertt").html("修改成功");
	                    	$("#my-alert").modal('open');
							$('#my-alert-edit').modal('close')
							_this.tableEx.ajax.reload();	
						}
					}
				});
		          }else{
		        /*	  $("#alertt").html("验证不成功");
                  	$("#my-alert").modal('open');*/
		          }
			});
	         
		},
		/**
		 * 修改保存mx
		 */
		xgbcmx : function() {
			var _this = this;
			$("#kpdmx_xgbc").on('click', function(e) {
		var r = $("#main_form1").validator("isFormValid");
	 if (r) {
				$('#mx_spse1').val($('#mx_spse').val());
				$.ajax({
					type : "POST",
					url : "kpdsh/xgbcmx",
					data : $('#main_form1').serialize(),
					success : function(data) {
						if(data.msg){
							$("#alertt").html("修改成功");
	                    	$("#my-alert").modal('open');
							$('#my-alert-edit1').modal('close');
							_this.tableEx.ajax.reload();	
							 kpspmx_table.ajax.reload();
						}
					}
				});
		            }else{
		            /*	$("#alertt").html("验证不通过");
                    	$("#my-alert").modal('open');*/
			          }
			});
	           
		},
		/**
		 * 导出按钮
		 */
/*		exportAc : function() {
			el.$jsExport.on('click', function(e) {
				var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
				var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
				if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
					if (dt1.getYear() == dt2.getYear()) {
						if (dt1.getMonth() == dt2.getMonth()) {
							if (dt1 - dt2 > 0) {
								alert('开始日期大于结束日期,Error!');
								return false;
							}
						} else {
							// alert('月份不同,Error!');
							alert('Error,请选择同一个年月内的时间!');
							return false;
						}
					} else {
						// alert('年份不同,Error!');
						alert('Error,请选择同一个年月内的时间!');
						return false;
					}
				}
				$("#ff").submit();
			});
		},*/
		/**
		 * 自定义列
		 */
/*		autoColumn : function() {
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
		},*/
		/**
		 * 保存自定义列
		 */
/*		saveColumn : function() {
			el.$jsSubmit.on('click', function(e) {
				el.$jsSubmit.attr({"disabled":"disabled"});
				$.ajax({
					type : "POST",
					url : "zdyl/save",
					data : {},// 你的formid
					success : function(data) {
						el.$jsSubmit.removeAttr("disabled");
						$('#biaoti').modal('close');
						alert(data.msg)
						 refresh();
					}
				});
			});
		},*/
		/**
		 * 自定义导出列
		 */
/*		autoColumn1 : function() {
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
		},*/
		/**
		 * 保存自定义导出列
		 */
/*		saveColumn1 : function() {
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
		},*/
		//批量打印
/*		printAc : function() {
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
																alert("存在正在开具或者开具失败的发票，不能批量打印！");
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
										alert("请先选中至少一条记录！");
									}
								}
							});
		},*/
		//全选按钮
/*		checkAllAc : function() {
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
		},*/
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
					alert("请至少选择2条数据!")
				}else{
					
				}
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
			_this.th();
			_this.kp();
			_this.xgbc();
			_this.xgbcmx();
		/*	_this.exportAc();*/
		/*	_this.autoColumn();
			_this.autoColumn1();
			_this.printAc();*/
/*			_this.checkAllAc();*/
			_this.modalAction(); // hidden action
/*			_this.saveColumn();
			_this.saveColumn1();*/
		}
	};
	action.init();

});

function yzje(je){
	var zdje = $(je).attr("max");
	var zhi= $(je).val();
	if(zdje==0){
		$("#alertt").html("最大金额为0 ,请维护开票限额");
    	$("#my-alert").modal('open');
    	return;
	}
	if(zhi*1>zdje*1){
		var msg = "不能超过分票金额"+zdje*1;
		$("#alertt").html(msg);
    	$("#my-alert").modal('open');
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
