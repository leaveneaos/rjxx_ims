/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
    		 $jsTable: $('#fpTable'),
    	        //$modalHongchong: $('#hongchong'),
    	        //$jsSubmit: $('.js-submit'),
    	        //$jsClose: $('.js-close'),
    	        $jsForm0: $('#ycform'), // 查询form 
    	        $jsForm1: $('#ycform1'), // 查询form 

    	        
    	        $s_kprqq: $('#s_rqq'), // search 开票日期起
    	        $s_kprqz: $('#s_rqz'), // search 开票日期止   s_gfmc  s_ddh s_fplx  s_rqq  s_rqz
    	        $s_gfmc: $('#s_gfmc'), // search 购方名称
    	        $s_fpdm: $('#s_fpdm'), // search 发票代码
    	        $s_fphm: $('#s_fphm'), // search 发票号码
    	        $s_ddh: $('#s_ddh'), // search   订单号
    	        $s_xfsh: $('#s_xfsh'), // search   销方税号
    	        $s_fplx: $('#s_fplx'), // search   发票类型
    	        
    	        $s_kprqq1: $('#s_rqq1'), // search 开票日期起
    	        $s_kprqz1: $('#s_rqz1'), // search 开票日期止   s_gfmc  s_ddh s_fplx  s_rqq  s_rqz
    	        $s_gfmc1: $('#s_gfmc1'), // search 购方名称
    	        $s_fpdm1: $('#s_fpdm1'), // search 发票代码
    	        $s_fphm1: $('#s_fphm1'), // search 发票号码
    	        $s_ddh1: $('#s_ddh1'), // search   订单号
    	        $s_xfsh1: $('#s_xfsh1'), // search   销方税号
    	        $s_fplx1: $('#s_fplx1'), // search   发票类型
    	        $jsLoading: $('.js-modal-loading'),
    };

  //开票商品明细table
    var kpspmx_table = $('#mxTable').DataTable({
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
            "url": "fpzf/getMx",
            data: function (d) {
                d.kplsh = $("#kplsh").val();
            }
        },
        "columns": [
            {"data": null},
            {"data": "spmc"},
            {"data": "spggxh"},
            {
            	"data": null,
                "render": function (data) {
                 //   return '<input type="text" name="hcje" value="'+data.khcje+'">';
                	   if (data.spje) {
                           return FormatFloat(data.spje,
                               "###,###.00");
                       }else{
                           return null;
                       }
                }
            },
            {
            	"data": "spsl",
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
            }
        ]
    });
    var t1;
    //开票商品明细table已审
    var kpspmx_table1 = $('#mxTable1').DataTable({
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
            "url": "fpzf/getMx",
            data: function (d) {
                d.kplsh = $("#kplsh").val();
            }
        },
        "columns": [
            {"data": null},
            {"data": "spmc"},
            {"data": "spggxh"},
            {
            	"data": null,
                "render": function (data) {
                 //   return '<input type="text" name="hcje" value="'+data.khcje+'">';
                	   if (data.spje) {
                           return FormatFloat(data.spje,
                               "###,###.00");
                       }else{
                           return null;
                       }
                }
            },
            {
            	"data": "spsl",
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
            }
        ]
    });
    
    kpspmx_table.on('draw.dt', function (e, settings, json) {
        var x = kpspmx_table, page = x.page.info().start; // 设置第几页
        kpspmx_table.column(0).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });
        //$('#fpTable tr').find('td:eq(0)').hide();
    });
    kpspmx_table1.on('draw.dt', function (e, settings, json) {
        var x = kpspmx_table1, page = x.page.info().start; // 设置第几页
        kpspmx_table1.column(0).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });
        //$('#fpTable tr').find('td:eq(0)').hide();
    });
    
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpzf/getKplsList',
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable
                .DataTable({
                	"searching": false,
                    "serverSide": true,
                    "sServerMethod": "POST",
                    "processing": true,
                    "bSort":true,
                    ordering: false,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                            var bj=$("#bj").val();
                        	
                        	if(bj=="1"){
                            d.xfi = $('#s_xfsh').val();//销方税号
                            d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                            d.fphm = el.$s_fphm.val();   // search 发票号码
                            d.fpdm = el.$s_fpdm.val();   // 发票代码
                            d.gfmc = el.$s_gfmc.val();   //购方名称
                            d.ddh = el.$s_ddh.val();     //订单号
                            d.fplx = el.$s_fplx.val();     //发票类型
                        	}if(bj=="2"){
                        		   var csm =  $('#dxcsm').val();
                                   if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){ //购方名称
                                 	  d.gfmc = $('#dxcsz').val();
                                 }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){//订单号
                                 	  d.ddh = $('#dxcsz').val();
                                 }
                        	}
                        }
                    },
                    "columns": [
                       {"data":null},        
                        {"data": "ddh"},
                        {"data": "kprq"},
                        {"data": "fpdm"},
                        {"data": "fphm"},
                        {"data": "gfmc"},
                        {"data": "fpzlmc"},
                        {
                            "data":/* function (data) {
                                if (data.hjje) {
                                    return FormatFloat(data.hjje,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            }*/
                            	"hjje",
                            'sClass': 'right'
                        },
                        {
                            "data":/* function (data) {
                                if (data.hjse) {
                                    return FormatFloat(data.hjse,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            }*/
                            	"hjse",
                            'sClass': 'right'
                        },
                        {
                            "data":/* function (data) {
                                if (data.jshj) {
                                    return FormatFloat(data.jshj,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            }*/
                            	"jshj",
                            'sClass': 'right'
                        },
                        {
                            "data": null,
                            "render": function (data) {
                                return '<a class="view" href="'
                                    + data.pdfurl
                                    + '" target="_blank">查看</a>'
                            }
                        }]
                });
             t1 = $("#ysTable")
            .DataTable({
            	"searching": false,
                "serverSide": true,
                "sServerMethod": "POST",
                "processing": true,
                "bSort":true,
                ordering: false,
                "ajax": {
                    url: "fpzf/getKplsList1",
                    type: 'POST',
                    data: function (d) {
                    	if(bz=="1"){
	                    	d.xfi = $('#s_xfsh').val();//销方税号
	                        d.kprqq = el.$s_kprqq1.val(); // search 开票日期起
	                        d.kprqz = el.$s_kprqz1.val(); // search 开票日期止
	                        d.fphm = el.$s_fphm1.val();   // search 发票号码
	                        d.fpdm = el.$s_fpdm1.val();   // 发票代码
	                        d.gfmc = el.$s_gfmc1.val();   //购方名称
	                        d.ddh = el.$s_ddh1.val();     //订单号
	                        d.fplx = el.$s_fplx1.val();     //发票类型
                    	}if(bz=="2"){
                    		 var csm =  $('#dxcsm1').val();
                             if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){ //购方名称
                           	  d.gfmc = $('#dxcsz1').val();
                             }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){//订单号
                           	  d.ddh = $('#dxcsz1').val();
                             }
                    	}
                    }
                },
                "columns": [
                   {"data":null},        
                    {"data": "ddh"},
                    {"data": "kprq"},
                    {"data": "fpdm"},
                    {"data": "fphm"},
                    {"data": "gfmc"},
                    {"data": "fpzlmc"},
                    {
                        "data":/* function (data) {
                            if (data.hjje) {
                                return FormatFloat(data.hjje,
                                    "###,###.00");
                            }else{
                                return null;
                            }
                        }*/
                        	"hjje",
                        'sClass': 'right'
                    },
                    {
                        "data":/* function (data) {
                            if (data.hjse) {
                                return FormatFloat(data.hjse,
                                    "###,###.00");
                            }else{
                                return null;
                            }
                        }*/
                        	"hjse",
                        'sClass': 'right'
                    },
                    {
                        "data":/* function (data) {
                            if (data.jshj) {
                                return FormatFloat(data.jshj,
                                    "###,###.00");
                            }else{
                                return null;
                            }
                        }*/
                        	"jshj",
                        'sClass': 'right'
                    },
                    {
                        "data": null,
                        "render": function (data) {
                            return '<a class="view" href="'
                                + data.pdfurl
                                + '" target="_blank">查看</a>'
                        }
                    }]
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                //$('#fpTable tr').find('td:eq(0)').hide();
            });
            t1.on('draw.dt', function (e, settings, json) {
                var x = t1, page = x.page.info().start; // 设置第几页
                t1.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                //$('#fpTable tr').find('td:eq(0)').hide();
            });
     
            
            //选中列查询明细
            $('.js-table2 tbody').on('click', 'tr', function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                	t.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected'); 
                }
                $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF"); 
                var data = t.row($(this)).data();
                $("#kplsh").val(data.kplsh);
                kpspmx_table.ajax.reload();
            });
            $('#ysTable').on('click', 'tr', function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                	t1.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected'); 
                }
                $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF"); 
                var data = t1.row($(this)).data();
                $("#kplsh").val(data.kplsh);
                kpspmx_table1.ajax.reload();
            });
          /*$('#check_all').change(function () {
            	if ($('#check_all').prop('checked')) {
            		t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                    });
                } else {
                	t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
            });*/
            $('#zf_search').click(function () {
	        	$("#bj").val("2");
	        	$('#xzxfq').attr("selected","selected");
	         	$('#xzlxq').attr("selected","selected");
	        	t.ajax.reload();
	        });
            
	        $('#zf_search1').click(function () {
	        	$("#bj").val("1");
	        	$("#dxcsz").val("");
	        	t.ajax.reload();
	        });
            
            
            $('#zf_search2').click(function () {
	        	$("#bz").val("2");
            	$('#xzxfq1').attr("selected","selected");
	         	$('#xzlxq1').attr("selected","selected");
              	t1.ajax.reload();
             });
            $('#zf_search3').click(function () {
	        	$("#bz").val("1");
             	$("#dxcsz1").val("");
             	t1.ajax.reload();

            });
            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
         /*   el.$jsSearch.on('click', function (e) {
            	  if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
                          || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
                          alert('Error,请选择开始和结束时间!');
                          return false;
                      }
                      e.preventDefault();
                      _this.tableEx.ajax.reload();
                      $("#kplsh").val("");
            });*/
        },
        /**
         * search action1
         */
        search_ac1: function () {
            var _this = this;
          /*  $('#kp_cx1').on('click', function (e) {
                if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
                    || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
                    alert('Error,请选择开始和结束时间!');
                    return false;
                }
                e.preventDefault();
                t1.ajax.reload();
            });*/
        },
      
     /*
        modalAction: function () {
            var _this = this;
            el.$modalHongchong.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$modalHongchong.modal('close');
            });
        },*/
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
        }
    };
    action.init();
    //定义鼠标样式
    $("#fpTable").css("cursor","pointer");
    function delcommafy(num){
    	   if((num+"").trim()==""){
    	      return "";
    	   }
    	   num=num.replace(/,/gi,'');
    	   return num;
    	}
    //作废操作
    $('#kp_zf').click(function () {
    	var kplsh = $('#kplsh').val();
    	if(kplsh!=0){
    		if(confirm("确定要作废该条数据吗？")){
        		var xhStr = "";
            	var hcjeStr="";
            	var zhcje = 0;
        		var rows = $("#mxTable").find('tr');		
        		$.ajax({
        			url:"fpzf/zf",
        			data:{"kplsh":kplsh},
        		    success:function(data){
        		    	if(data.success){
        		    		alert(data.msg);
        		    		$("#kplsh").val("");
        		    		window.location.reload();
        		    	}else{
        		    		alert(data.msg);
        		    	}   		    	    		    	
        		    },
        		    error:function(){
        		    	alert("程序出错，请联系开发人员！");
        		    }
        		});
        	}
    	}else{
    		alert("请选择一条记录！");
    	}    	  
    });
});