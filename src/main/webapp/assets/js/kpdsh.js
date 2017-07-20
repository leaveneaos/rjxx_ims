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
    //批量导入
    var $importModal = $("#bulk-import-div");
    $("#kp_dr").click(function () {
    	$('#importExcelForm').resetForm();
        $importModal.modal({"width": 600, "height": 350});
    });
	
	
	
    $(this).removeData('amui.modal');
	 var mxarr = [];
	 var $modal = $("#my-alert-edit2");
	 var $tab = $('#doc-tab-demo-1');
	  //开票商品明细table

	    $("#my-alert-edit2").on("open.modal.amui", function () {
	        $("#mx_form").validator("destroy");
	        $("#main_form").validator("destroy");
	        jyspmx_edit_table.clear();
	        jyspmx_edit_table.draw();
	    });
     var jyspmx_edit_table = $('#jyspmx_edit_table').DataTable({
         "searching": false,
         "bPaginate": false,
         "bAutoWidth": false,
         "bSort": false,
         "scrollY": "100",
         "scrollCollapse": "true"
     });
    
    $("#yhqrbc").click(function(){
    	$("#yhqrbc").attr('disabled',"true"); 
		$.ajax({
			type : "POST",
			url : "kpdsh/yhqrbc",
			data : {},
			success : function(data) {
				if(data.msg){
				$('#yhqrbc').removeAttr("disabled");
                swal("保存成功");
            	$("#cljgbt").hide();
            	$tab.tabs('refresh');
            	$tab.tabs('open', 0);
            	t.ajax.reload();
				kpspmx_table.ajax.reload();
				}else{
					 swal("所选数据无法进行整数分票，请调整分票规则！");
					 $('#yhqrbc').removeAttr("disabled");
				}
			}
		});
    	});
    $("#yhqx").click(function(){
    	$("#cljgbt").hide();
    	$tab.tabs('refresh');
    	$tab.tabs('open', 0);
    	kpspmx_table.ajax.reload();
    	});
    $('#kp_add').click(function () {
        mxarr = [];
        $('#lrmx_form').resetForm();
        $('#main_form2').resetForm();
        $modal.modal({"width": 820, "height": 600});
        jyspmx_edit_table.clear();
        jyspmx_edit_table.draw();
    });
    $("#lrclose").click(function () {
        $modal.modal("close");
    });
    var index = 1;
    $('#lrmain_tab').find('a.ai').on('opened.tabs.amui', function (e) {
        jyspmx_edit_table.draw();
    });
    $("#addRow").click(function () {
        var r = $("#lrmx_form").validator("isFormValid");
        if (r) {
            var spdm = $("#lrspdm_edit").val();
            var mc = $("#lrmc_edit").val();
            var ggxh = $("#lrggxh_edit").val();
            var dw = $("#lrdw_edit").val();
            var sl = $("#lrsl_edit").val();//数量

            var dj = $("#lrdj_edit").val();
            var je = $("#lrje_edit").val();
            var sltaxrate = $("#lrsltaxrate_edit").val();//税率
            var se = $("#lrse_edit").val();
            var jshj = $("#lrjshj_edit").val();
            index = mxarr.length + 1;
            jyspmx_edit_table.row.add([
                "<span class='index'>" + index + "</span>", spdm, mc, ggxh, dw, sl, dj, je, sltaxrate, se, jshj, "<a href='#'>删除</a>"
            ]).draw();
            mxarr.push(index);
        }
    });
    $('#jyspmx_edit_table tbody').on('click', 'a', function () {
        jyspmx_edit_table.row($(this).parents("tr")).remove().draw(false);
        mxarr.pop();
        $('#jyspmx_edit_table tbody').find("span.index").each(function (index, object) {
            $(object).html(index + 1);
        });
    });
    $("#lrsave").click(function () {
        var r = $("#main_form2").validator("isFormValid");
        if (r) {
            var ps = [];
            var d = jyspmx_edit_table.rows().data();
            if (d.length == 0) {
                $("#lrmain_tab").tabs('open', 1);
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
            var frmData = $("#main_form2").serialize() + "&" + ps.join("&");
            $.ajax({
                url: "lrkpd/save1", "type": "POST", context: document.body, data: frmData, success: function (data) {
                    if (data.success) {
                        swal("保存成功!");
                        $modal.modal("close");
                        t.ajax.reload();
                    } else {
                        swal(data.msg);
                    }
                }
            });
        } else {
            ///如果校验不通过
            $("#lrmain_tab").tabs('open', 0);
        }
    });
	  //开票商品明细table
    var kpspmx_table = $('#mxTable1').DataTable({
        "paging": true,
        "lengthChange": true,
        "lengthMenu": [ 20, 30, 40, 50, 100 ,10000],
        "pageLength": 10000,
        "processing" : true,
        "serverSide" : true,
        ordering : false,
        searching : false,
        "scrollX" : true,
        ajax: {
            "url": "kpdsh/getMx",
            data: function (d) {
                d.sqlsh = $("#kplsh").val();

            }
        },
        "columns": [
            {"data": "spmxxh"},
            {
                "data": null,
                "render": function (data) {
                    return '<a href="#" class="modify1" style="margin-right: 10px;">修改</a>'
                }
            },
            {"data": "spmc"},
            {"data": function (data) {
            	var kkjje
            	if(data.kkjje!=0){
            		 kkjje = FormatFloat(data.kkjje, "###,###.00");
            	}else{
            		kkjje=data.kkjje;
            	}  
          	  if (data.kkjje) {
          		     return '<input type="text" Style="text-align:right;width:100px;"  class="bckpje" name="bckpje" value="'+kkjje+'">';
                } else {
                	  return '<input readonly="readonly" Style="text-align:right;width:100px;"  type="text"  class="js-pattern-Money bckpje" name="bckpje" value="'+kkjje+'">';
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
            },
        ]
    });
    $('#mxTable1').on( 'draw.dt', function () {
    	   if($("input[name='dxk']:checked").length>1){
    		 var ycl = $("input[name='bckpje']");
    			for (var i = 0; i < ycl.length; i++) {
        			$(ycl).attr("readonly","readonly");
    	          } 
    	   }
    });
    kpspmx_table.on('click', 'a.kpdmx', function () {
    	var id = kpspmx_table.row($(this).parents('tr')).data().id;
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
                    url : "kpdsh/mxsc",
                    data : {"id":id},
                }).done(function(data) {
                    $('.confirm').removeAttr('disabled');
                    swal({ 
                          title: "已成功删除", 
                          timer: 1500, 
                          type: "success", 
                          showConfirmButton: false 
                        });
                    kpspmx_table.ajax.reload();
                })
            });
   });
    kpspmx_table.on('click', 'a.modify1', function () {
    	var row = kpspmx_table.row($(this).parents('tr')).data();
    	if(row.ykjje!=null&&row.ykjje>0){
            swal("明细已经部分开具");
        	return;
    	}
    	$('#my-alert-edit1').modal({"width": 480, "height": 550});
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
    var splsh=[];
    var kpspmx_table3;
    var loaddata=false;
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
						 d.loaddata=loaddata;
                        splsh.splice(0,splsh.length);
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
                            {
                                "data": null,
                                "render": function (data) {
                                    return '<a href="#" class="modify" style="margin-right: 10px;">修改</a>'
                                }
                            },
	                        {"data": "ddh"},
	                        {"data": "ddrq"},
							{"data":function(data){
								var sjly = data.sjly;
								switch (sjly) {
									case '0':
										sjly = '平台录入';
										break;
									case '1':
										sjly = '接口接入';
										break;
									case '2':
										sjly = '平台导入';
										break;
									case '3':
										sjly = '钉钉录入';
										break;
								}
								return sjly;
							}},
	                        {"data": null,
	                           "render": function (data) {
	                        	   var zdjeh = FormatFloat(data.zdje, "###,###.00");
	                        	   var fpjeh = FormatFloat(data.fpje, "###,###.00")
	                                return '<input type="text" Style="text-align:right;width:100px;" onkeyup="yzje(this)" class="js-pattern-Money" max="'+zdjeh+'" name="fpje" value="'+fpjeh +'">';
	                                }, 'sClass': 'right'
	                        },
	                        {"data": null,
		                           "render": function (data) {
		                        	   if(data.fpjshsbz==1){
		                        		   return '<select name="hsbz" Style="width:100%"><option selected="selected" value="1">含税</option><option value="0">不含税</option></select>';
		                        	   }else{
		                        		   return '<select name="hsbz" Style="width:100%"><option value="1">含税</option><option selected="selected" value="0">不含税</option></select>';
		                        	   }   
		                                }
		                        },
		                    {"data": null,
			                       "render": function (data) {
			                    	   if(data.qdbz==1){
		                        		   return '<select  name="qdbz" Style="width:100%"><option selected="selected" value="1">打印</option><option value="0">不打印</option></select>';
		                        	   }else{
		                        		   return '<select name="qdbz" Style="width:100%"><option value="1">打印</option><option selected="selected" value="0">不打印</option></select>';
		                        	   }
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
	                        }, 'sClass': 'right'},
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
						var qdbzs = "";
						$('input[name="dxk"]:checked').each(function(cell,i){
						chk_value+=$(this).val()+",";
						var row = $(this).parents('tr').find('input[name="fpje"]');
						var row2 = $(this).parents('tr').find('select[name="hsbz"]');
						var row3 = $(this).parents('tr').find('select[name="qdbz"]');
						fpxes+=row.val().replace(/,/g,'')+","
						hsbzs+=row2.val()+","
						qdbzs+=row3.val()+","
						});
						d.fpjshsbz= hsbzs;
						d.qdbzs= qdbzs;
						var ddhs = chk_value.substring(0, chk_value.length-1);
						fpxes = fpxes.substring(0, fpxes.length-1);
						d.sqlshs= ddhs;
						d.fpxes=fpxes;
						var bckpje = [];
						if($("input[name='dxk']:checked").length==1){
						
				            var els1 =document.getElementsByName("bckpje");
							for(var i=0;i<els1.length;i++){
								var fpp = els1[i].value.replace(/,/g,'');
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
		                            "###,###.000000");
		                    }else{
		                        return null;
		                    }
		                },
		                'sClass': 'right'
		                
		            },
		            {"data": function (data) {
		                    if (data.spdj) {
		                        return FormatFloat(data.spdj,
		                            "###,###.000000");
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
                            swal(data.msg);
						}
					},
					error : function() {
                        swal("出现错误,请稍后再试");
					}
				});
            	$('#my-alert-edit').modal({"width": 800, "height": 450});
            });
            /*t.on('click', 'a.kpdth', function () {
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

            });*/
            //删除
            $("#kpd_sc").click(function () {
               
        		var chk_value="" ;
        		$('input[name="dxk"]:checked').each(function(){
        		chk_value+=$(this).val()+",";
        		});
        		var ddhs = chk_value.substring(0, chk_value.length-1);
        		if(chk_value.length==0){

                    swal("请至少选择一条数据");
        		}else{
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
                            url : "kpdsh/sc",
                            data : {"ddhs":ddhs},
                        }).done(function(data) {
                            $('.confirm').removeAttr('disabled');
                            swal(data.msg);
                            _this.tableEx.ajax.reload();
                        })
                    });
                    
        		}
            });

            $('#check_all').change(function () {
            	if ($('#check_all').prop('checked')) {
                    splsh.splice(0,splsh.length);
            		t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                        var row =t.row(i).data();
                        splsh.push(row.sqlsh);
                    });
                } else {
                    splsh.splice(0,splsh.length);
                	t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);

                    });
                }
                  $("#kplsh").val(splsh.join(","));
            	  kpspmx_table.ajax.reload();	
            });

            //选中列查询明细
            $('#jyls_table tbody').on('click', 'tr', function () {
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
                loaddata=true;
				_this.tableEx.ajax.reload();
			});
			$("#kp_search1").on('click', function(e) {
				$("#dxcsz").val("");
                loaddata=true;
				_this.tableEx.ajax.reload();
			})
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
					var fpje = els[i].value.replace(/,/g,'');
					if(fpje==0){
                        swal("第 "+(i+1)+"行分票金额为0,请重新填写或维护开票限额");
	                	fla=false;
	    				return false;
					}
					if(!fpje.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                        swal("第 "+(i+1)+"行分票金额格式有误，请重新填写！");
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
						var fpp = els1[i].value.replace(/,/g,'');
						je+=fpp*1;
						bckpje.push(fpp); 
						if(fpp==0){
						}else if(!fpp.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                            swal("第 "+(i+1)+"行明细金额格式有误，请重新填写！");
		    				return false;
		    			}else if(Number(fpp)>Number(delcommafy(rows1[i+1].cells[3].innerHTML))){
                            swal("第"+(i+1)+"条明细的本次开票金额不能大于可开票金额！");
		                	return false;
		    			}
					}
					if(je==0){
                        swal("本次开具金额为0,请重新填写");
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
                    swal("请至少选择一条数据");
				}else{
					if(!fla){
						return;
					}
        		     if (!confirm("您确认处理该记录？")) {
        				return;
        			}
	            	$("#cljg").show();
	            	$("#cljgbt").show();
	            	  $tab.tabs('refresh');
					kpspmx_table3.ajax.reload();
					 kpspmx_table.ajax.reload();
					$('#doc-tab-demo-1').tabs('open', 1)
				}
			});
		},
		/**
		 * 修改保存
		 */
		xgbc : function() {
			var _this = this;

			$("#kpd_xgbc").on('click', function(e) {
                $('.confirm').attr('disabled',"disabled");
				 var r = $("#main_form").validator("isFormValid");
		          if (r) {
				$.ajax({
					type : "POST",
					url : "kpdsh/xgbckpd",
					data : $('#main_form').serialize(),
					success : function(data) {
						if(data.msg){
                            $('.confirm').removeAttr('disabled');
                            swal("修改成功");
							$('#my-alert-edit').modal('close')
							_this.tableEx.ajax.reload();	
						}
					}
				});
		          }else{
		          }
			});
	         
		},
		/**
		 * 修改保存mx
		 */
		xgbcmx : function() {
			var _this = this;
			$("#kpdmx_xgbc").on('click', function(e) {
            $('.confirm').attr('disabled',"disabled");          
		var r = $("#main_form1").validator("isFormValid");
		if (r) {
				$('#mx_spse1').val($('#mx_spse').val());
				$.ajax({
					type : "POST",
					url : "kpdsh/xgbcmx",
					data : $('#main_form1').serialize(),
					success : function(data) {
						if(data.msg){
                            $('.confirm').removeAttr('disabled');
                            swal("修改成功");
							$('#my-alert-edit1').modal('close');
							_this.tableEx.ajax.reload();	
							 kpspmx_table.ajax.reload();
						}
					}
				});
		            }else{

			          }
			});
	           
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
			_this.kp();
			_this.xgbc();
			_this.xgbcmx();
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
