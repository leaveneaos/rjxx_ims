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
            "url": "fpdy/getMx",
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
    kpspmx_table.on('draw.dt', function (e, settings, json) {
        var x = kpspmx_table, page = x.page.info().start; // 设置第几页
        kpspmx_table.column(0).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });
        //$('#fpTable tr').find('td:eq(0)').hide();
    });
 
    var fphm=[];
    var fpdm=[];
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpdy/getKplsList',
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
                    "scrollX": true,
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
                       {
							"orderable" : true,
							"data" : null,
							render : function(data, type, full, meta) {
								return '<input type="checkbox" value="'
									+ data.kplsh + '" name="chk'+data.kplsh+'"  id="chk"/>';
							}
						},
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },       
                        {"data": "ddh"},
                        {"data": "kprq"},
                        {"data": "fpdm"},
                        {"data": "fphm"},
                        {"data": "gfmc"},
                        {"data": "fpzlmc"},
                        {
                            "data": function (data) {
                                if (data.hjje) {
                                    return FormatFloat(data.hjje,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            	//"hjje",
                            'sClass': 'right'
                        },
                        {
                            "data": function (data) {
                                if (data.hjse) {
                                    return FormatFloat(data.hjse,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            	//"hjse",
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
                            	//"jshj",
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
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                //$('#fpTable tr').find('td:eq(0)').hide();
            });
       
            t.on('click', 'input#chk', function () {
                var data = t.row($(this).parents('tr')).data();
                if ($('input[name="chk'+data.kplsh+'"]').prop('checked')){
                	fphm.push(data.fphm);
                    fpdm.push(data.fpdm);
                }else{
                	var index = $.inArray(data.fphm,fphm);
                    fphm.splice(index,1);
                    var index2 = $.inArray(data.fpdm,fpdm);
                    fpdm.splice(index2,1);
                }
               
            });
            
            //选中列查询明细
            $('.js-table2 tbody').on('click', 'tr', function () {
            	var data = t.row($(this)).data();
                if ($(this).hasClass('selected')) {
	                $(this).find('td:eq(0) input').prop('checked',false); 
                    $(this).removeClass('selected');
                } else {
	                $(this).find('td:eq(0) input').prop('checked',true); 
                	t.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected'); 
                }
                if ($('input[name="chk'+data.kplsh+'"]').prop('checked')){
                	fphm.push(data.fphm);
                    fpdm.push(data.fpdm);
                }else{
                	var index = $.inArray(data.fphm,fphm);
                    fphm.splice(index,1);
                    var index2 = $.inArray(data.fpdm,fpdm);
                    fpdm.splice(index2,1);
                }
                $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF"); 
                $("#kplsh").val(data.kplsh);
                kpspmx_table.ajax.reload();
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
            $('#cd_search').click(function () {
	        	$("#bj").val("2");
	        	$('#xzxfq').attr("selected","selected");
	         	$('#xzlxq').attr("selected","selected");
	        	t.ajax.reload();
	        });
            
	        $('#cd_search1').click(function () {
	        	$("#bj").val("1");
	        	$("#dxcsz").val("");
	        	t.ajax.reload();
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
    //重打操作
    $('#cd_cd').click(function () {
    	  var kplsh = [];
          $('#chk:checked').each(function(){    
       	   kplsh.push($(this).val()); 
          });
    	 var fplx=$("#s_fplx").val();
    	 if(fplx==""){
             $("#alertt").html("请选择发票类型！");
             $("#my-alert").modal('open');
             return;
         }
    	 if(fplx=="12"){
    		 if (kplsh.length == 0) {
    	           	$("#alertt").html("请勾选需要重打的开票流水");
    	           	$("#my-alert").modal('open');
    	               return;
    	           }else if(kplsh.length>0){
    		 
    		 window.open('fpdy/printmany?ids='
						+ kplsh, '',
						'scrollbars=yes,status=yes,left=0,top=0,menubar=yes,resizable=yes,location=yes');
    	           }
    	 }else{
    	
    	 if (kplsh.length == 0) {
           	$("#alertt").html("请勾选需要重打的开票流水");
           	$("#my-alert").modal('open');
               return;
           }
		    	if(kplsh.length>=1){
    	             if(kplsh.length>1){
		    		 if(fplx==""){
		     			$("#alertt").html("请选择发票类型查询后再批量重打！");
		               	$("#my-alert").modal('open');
		               	fphm=[];
		 	          	fpdm=[];
		                 return;
		     	        }
                     }
		    		if(confirm("确定要重新打印该条数据吗？")){
		    			//alert(fphm);alert(fpdm);alert(kplsh);
		        		$.ajax({
		        			url:"fpdy/cd",
		        			data:{"kplsh":kplsh.join(","),"fphm":fphm.join(","),"fpdm":fpdm.join(",")},
		        		    success:function(data){
		        		    	if(data.success){
		        		    		//alert(data.msg);
		        		    		$("#alertt").html(data.msg);
		        		          	$("#my-alert").modal('open');
		        		          	fphm=[];
		        		          	fpdm=[];
		        		          	$('input[type="checkbox"]').prop('checked', false);  
		        		    	}else{
		        		    		$("#alertt").html(data.msg);
		        		          	$("#my-alert").modal('open');
		        		          	fphm=[];
		        		          	fpdm=[];
		        		          	$('input[type="checkbox"]').prop('checked', false);     
		        		          	}   		    	    		    	
		        		    },
		        		    error:function(){
		        		    	//alert("程序出错，请联系开发人员！");
		        		    	$("#alertt").html("程序出错，请联系开发人员！");
		    		          	$("#my-alert").modal('open');
		    		          	$('input[type="checkbox"]').prop('checked', false);    
		    		          	}
		        		});
		        	}
		    	}else{
		    		$("#alertt").html("请选择一条记录！");
		          	$("#my-alert").modal('open');
		    	}    
    	   }
      });
    
});