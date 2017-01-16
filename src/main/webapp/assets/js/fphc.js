/**
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
    
    
    
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fphc/getKplsList',
            hcUrl: 'fphc/ykfphc'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable
                .DataTable({
                	"searching": false,
                    "serverSide": true,
                    "sServerMethod": "POST",
                    "processing": true,
                    "bSort":false,
                    ordering: false,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                            d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                            d.fphm = el.$s_fphm.val(); // search 发票号码
                            d.fpdm = el.$s_fpdm.val();
                            d.gfmc = el.$s_gfmc.val(); // search 购方名称
                            d.ddh = el.$s_ddh.val();

                        }
                    },
                    "columns": [
                       {"data":"kplsh"},     
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
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                $('#fpTable tr').find('td:eq(0)').hide();
            });

            // 红冲
            /*t.on('click', 'a.hongchong', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setForm0(data);
                el.$modalHongchong.modal('open');
            });*/
            
            //选中列查询明细
            t.on('click', 'tr', function () {
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
                e.preventDefault();
                _this.tableEx.ajax.reload();
                $("#kplsh").val("");
            });
        },
        
        /**
         * 红冲
         */
        hc: function () {
            var _this = this;
            el.$jsForm0.validator({
                submit: function () {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle'); // show loading
                        // alert('验证成功');
                        var data = el.$jsForm0.serialize(); // get form data
                        // data
                        // TODO save data to serve
                        $.ajax({
                            url: _this.config.hcUrl,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.success) {
                                    el.$modalHongchong.modal('close'); // close
                                    // modal
                                    alert(data.msg);
                                } else {
                                    alert('后台错误: 红冲操作失败' + data.msg);
                                }
                                _this.tableEx.ajax.reload(); // reload table
                                // data
                                el.$jsLoading.modal('close'); // close loading
                            },
                            error: function () {
                                alert('红冲操作失败, 请重新登陆再试...!');
                            }
                        });
                        return false;
                    } else {
                        alert('验证失败');
                        return false;
                    }
                }
            });
        },
        setForm0: function (data) {
            var _this = this, i;
            // todo set data
            // debugger
            el.$jsForm0.find('input[name="hc_yfpdm"]').val(data.fpdm);
            el.$jsForm0.find('input[name="hc_yfphm"]').val(data.fphm);
            el.$jsForm0.find('input[name="hc_kpje"]').val(FormatFloat(data.jshj, "###,###.00"));
            el.$jsForm0.find('input[name="djh"]').val(data.djh);
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
            _this.hc();
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
    $('.js-hongchong').click(function () {
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
    })
});