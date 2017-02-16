﻿/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table2'),
        $modalHongchong: $('#hongchong'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'), // 红冲 form
        $s_kprqq: $('#s_kprqq'), // search 开票日期起
        $s_kprqz: $('#s_kprqz'), // search 开票日期止
        $s_kprqq1: $('#s_kprqq1'), // search 开票日期起
        $s_kprqz1: $('#s_kprqz1'), // search 开票日期止
        $s_gfmc: $('#s_gfmc'), // search 购方名称
        $s_fpdm: $('#s_fpdm'), // search
        $s_ddh: $('#s_ddh'), // search
        $s_fphm: $('#s_fphm'), // search
        $jsSearch: $('.js-search'),
        $jsLoading: $('.js-modal-loading'),
        $jsHcButton:$('.js-hongchong')
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
            "url": "fphc/getMx",
            data: function (d) {
                d.kplsh = $("#kplsh").val();
            }
        },
        "columns": [
            {"data": "spmxxh"},
            {"data": "spmc"},
            {"data": "spggxh"},
            {
            	"data": null,
                "render": function (data) {
                   return '<input type="text" name="hcje" value="'+data.khcje+'">';
                	/*   if (data.khcje) {
                           return FormatFloat(data.khcje,
                               "###,###.00");
                       }else{
                           return null;
                       }*/
                }
            },
            {
            	"data": function (data) {
                    if (data.khcje) {
                        return FormatFloat(data.khcje,
                            "###,###.00");
                    }else{
                        return null;
                    }
                },
                'sClass': 'right'
            		},
            {
            	"data": function (data) {
                    if (data.yhcje) {
                        return FormatFloat(data.yhcje,
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
            }
        ]
    });
    
    //开票商品明细table
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
            "url": "fphc/getMx",
            data: function (d) {
                d.kplsh = $("#kplsh").val();
            }
        },
        "columns": [
            {"data": "spmxxh"},
            {"data": "spmc"},
            {"data": "spggxh"},
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
            }
        ]
    });
   
    
    kpspmx_table.on('draw.dt', function (e, settings, json) {
        var x = kpspmx_table, page = x.page.info().start; // 设置第几页
        kpspmx_table.column(1).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });
        //$('#fpTable tr').find('td:eq(0)').hide();
    });
    kpspmx_table1.on('draw.dt', function (e, settings, json) {
        var x = kpspmx_table1, page = x.page.info().start; // 设置第几页
        kpspmx_table1.column(1).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });
        //$('#fpTable tr').find('td:eq(0)').hide();
    });
    var t1;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fphc/getKplsList',
            hcUrl: 'fphc/hc'
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
                            d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                            d.gfmc = $('#zf_gfmc').val(); // search 购方名称
                            d.xfi = $('#zf_xfsh').val();
                        }
                    },
                    "columns": [
    
                       {"data":null},        
                        {"data": "ddh"},
                        {"data": "kprq"},
                        {"data": "fpdm"},
                        {"data": "fphm"},
                        {"data": "gfmc"},
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
            t1 = $('#ysTable')
            .DataTable({
            	"searching": false,
                "serverSide": true,
                "sServerMethod": "POST",
                "processing": true,
                "bSort":true,
                ordering: false,
                "ajax": {
                    url: 'fphc/getKplsList1',
                    type: 'POST',
                    data: function (d) {
                        d.kprqq = el.$s_kprqq1.val(); // search 开票日期起
                        d.kprqz = el.$s_kprqz1.val(); // search 开票日期止
                        d.gfmc = $('#zf_gfmc1').val(); // search 购方名称
                        d.xfi = $('#zf_xfsh1').val();
                    }
                },
                "columns": [
  
                   {"data":null},        
                    {"data": "ddh"},
                    {"data": "kprq"},
                    {"data": "fpdm"},
                    {"data": "fphm"},
                    {"data": "gfmc"},
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
                    }],
                  
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
     /*       $('.js-table2 tbody').on('click', 'tr', function () {
                $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF");
          });*/

            // 红冲
            /*t.on('click', 'a.hongchong', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setForm0(data);
                el.$modalHongchong.modal('open');
            });*/
            
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
            $('#ysTable tbody').on('click', 'tr', function () {
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
            $('#check_all').change(function () {
            	if ($('#check_all').prop('checked')) {
            		t1.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                    });
                } else {
                	t1.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
            });

            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
                    || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
                    alert('Error,请选择开始和结束时间!');
                    return false;
                }
                e.preventDefault();
                _this.tableEx.ajax.reload();
                $("#kplsh").val("");
            });
        },
        /**
         * search action1
         */
        search_ac1: function () {
            var _this = this;
            $('#kp_cx1').on('click', function (e) {
                if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
                    || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
                    alert('Error,请选择开始和结束时间!');
                    return false;
                }
                e.preventDefault();
                t1.ajax.reload();
            });
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modalHongchong.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$modalHongchong.modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.search_ac1();
            _this.modalAction(); // hidden action
           // _this.bfhc();
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
    //红冲操作
    $("#kp_zf").click(function () {
    	var kplsh = $('#kplsh').val();
    	if(kplsh!=0){
    		if(confirm("确定要红冲该条数据吗？")){
        		var xhStr = "";
            	var hcjeStr="";
            	var zhcje = 0;
        		var rows = $("#mxTable").find('tr');		
        		for(var j=1;j<rows.length;j++){
        			xhStr +=rows[j].cells[0].innerHTML+",";
        		}
        		var els =document.getElementsByName("hcje");
        		for(var i=0;i<els.length;i++){
        			var hcje = els[i].value;
        			if(hcje==''){
        				hcje = 0;
        			}
        			if(!hcje.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
        				alert("第"+(i+1)+"条明细的本次红冲金额格式错误，请重新填写！");
        				return;
        			}
        			if(Number(hcje)>Number(delcommafy(rows[i+1].cells[4].innerHTML))){
        				alert("第"+(i+1)+"条明细的本次红冲金额不能大于可红冲金额！");
        				return;
        			}
        			zhcje+=hcje;
        			hcjeStr += hcje+",";
        		}
        		if(zhcje==0){
        			alert("红冲金额不能为0或空！");
        			return;
        		}   		
        		$.ajax({
        			url:"fphc/hc",
        			data:{"xhStr":xhStr,"hcjeStr":hcjeStr,"kplsh":kplsh},
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