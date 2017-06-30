/**
 * feel
 */
$(function () {
	
    "use strict";
    var el = {
    		 $jsTable: $('#fpTable'),
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
    });
    var loaddata=false;
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
                            d.loaddata=loaddata;
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
                        }/*,
                        {
                            "data": null,
                            "render": function (data) {
                                if(data.fpzldm=="12"){
                                    return '<a class="view" href="'
                                        + data.pdfurl
                                        + '" target="_blank">查看</a>'
                                }else{
                                   $("#zubz").css("display","none");
                                }
                            }
                        }*/]
                });
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
       
            t.on('click', 'input#chk', function () {
                var data = t.row($(this).parents('tr')).data();
            });
            
            //选中列查询明细
            $('.js-table2 tbody').on('click', 'tr', function () {
            	var data = t.row($(this)).data();
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked',false)
                } else {
                    $(this).find('td:eq(0) input').prop('checked',true)
                    $(this).addClass('selected');
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
                loaddata=true;
	        	t.ajax.reload();
	        });
	        $('#cd_search1').click(function () {
	        	$("#bj").val("1");
	        	$("#dxcsz").val("");
                loaddata=true;
	        	t.ajax.reload();
	        });
            //重打操作
            $('#cd_cd').click(function () {
                var kplsh = [];
                t.column(0).nodes().each(function(cell, i) {
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        var data =t.row(i).data();
                        kplsh.push(data.kplsh);
                    }
                });
                if (kplsh.length == 0) {
                    // $("#alertt").html("请勾选需要重打的开票流水");
                    // $("#my-alert").modal('open');
                    swal("请勾选需要重打的开票流水");
                    return;
                }
                var skpid="";
                var fpzldm="";
                var flag = true;
                var fpzlbz="";
                var fphm=[];
                var fpdm=[];
                $(":checkbox:checked","#fpTable").each(function(){
                    var tr = $(this).parents("tr");
                    var data = t.row(tr).data();
                    if(skpid==""){
                        skpid =  data.skpid;
                    }else{
                        if(skpid!=data.skpid){
                            // $("#alertt").html("批量勾选的开票点不一致,请重新勾选");
                            // $("#my-alert").modal('open');
                            swal("批量勾选的开票点不一致,请重新勾选");
                            flag = false;
                            return false;
                        }
                    }
                    if(fpzldm==""){
                        fpzldm =  data.fpzldm;
                    }else{
                        if(fpzldm!=data.fpzldm){
                            fpzlbz="0";
                            flag = false;
                            return false;
                        }
                    }
                    fphm.push(data.fphm);
                    fpdm.push(data.fpdm);
                });
                if(fpzlbz=="0"){
                    // $("#alertt").html("批量勾选的发票种类不一致,请重新勾选");
                    // $("#my-alert").modal('open');
                    swal("批量勾选的发票种类不一致,请重新勾选");
                    return;
                }
                if(fpzldm=="12"){
                        window.open('fpdy/printmany?ids='
                            + kplsh, '',
                            'scrollbars=yes,status=yes,left=0,top=0,menubar=yes,resizable=yes,location=yes');
                }else{
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
                                    // $("#alertt").html("程序出错，请联系开发人员！");
                                    // $("#my-alert").modal('open');
                                    swal("程序出错，请联系开发人员！");
                                    $('input[type="checkbox"]').prop('checked', false);
                                }
                            });
                        }
                }
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
});