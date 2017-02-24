/**
 * feel
 */
$(function () {
    "use strict";
    var ur ;
    var el = {
    	$jsTable: $('.js-table'),//查询table
    	$jsForm:$('.js-form-yjsz'),
        $jsSearch: $('#button1'),//查询按钮
        $jsTable1: $('#button2'),//设置按钮
        $xfid: $('#xfid'),//销方税号
        $jsdiv:$("#shezhi"),
        $jssave:$(".js-submit"),
        $jsClose:$('.js_close'),
        $checkAll: $('#selectAll'),
        $jsLoading: $('.js-modal-loading')//输在载入特效
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpyjdy/getItems',
            szUrl: 'fpyjdy/update',
            plszUrl:'fpyjdy/plupdate'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    "scrollX": true,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                            d.xfid= el.$xfid.val(); // search
                            $("#xfidhide").val($("#xfid").val());
                            d.skpider = $("#s_skpid").val();
                            d.fpzl = $("#s_fplx").val();
                        }
                    },
                    "columns": [
                         {
                            "orderable": false,
                            "data": null,
                            render: function (data, type, full, meta) {
                                 return '<input type="checkbox" value="'+data.skpid+'"/>';
                            }
                        },
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {"data": "xfid"},
                        {"data": "skpid"},
                        {"data": "xfmc"},
                        {"data": "xfsh"},
                        {"data": "kpdmc"},
                        {"data":"fpzlmc"},                 
                        {"data": "kyl"},
                        {"data": "sfsy"},
                        {"data": "sfemail"},
                        {"data": "yjkcl"},
                        {
                            "data": null,
                            "render": function (data) {
                                return '<a class="shezhi">设置</a>'
                            }
                        }]
                });
            	   
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                $('#yjdytable tr').find('td:eq(2)').hide();
                $('#yjdytable tr').find('td:eq(3)').hide();
            });

            // 批量设置
            el.$jsTable1.on('click', el.$jsTable1, function () {
            	$("#sfsy").attr("checked",false);
            	$("#sfemail").attr("checked",false);
            	$("#yjkcl").val("");
            	var xfid = $("#xfidhide").val();
            	var skpids = "";
            	_this.tableEx.column(0).nodes().each(function (cell, i) {
        			var $checkbox = $(cell).find('input[type="checkbox"]');     			
             		if ($checkbox.is(':checked')) {
             			skpids += $checkbox.val()+',';
             		}           		
        		 });
            	if(skpids==''){
            		alert("请至少选择一条记录！");
            	}else{
            		ur = _this.config.plszUrl+'?skpids='+skpids+'&xfid='+xfid;
                    el.$jsdiv.modal('open');
                    skpids="";
            	}
                
            });
            // 单个设置
            t.on('click', 'a.shezhi', function () {
                var row = t.row($(this).parents('tr')).data();
                if(row.sfsy=='已订阅'){
                	$("#sfsy").attr("checked",true);
                }
                if(row.sfemail=='已订阅'){
                	$("#sfemail").attr("checked",true);
                }
                $("#yjkcl").val(row.yjkcl);
                el.$jsdiv.modal('open');
                ur =_this.config.szUrl + '?xfid=' + row.xfid+'&skpid='+row.skpid;   			 
            });            
            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                e.preventDefault();
                _this.tableEx.ajax.reload();
                $("#xfidhide").val($("#xfid").val());
            });
        },
        /**
         * 新增保存
         */       
        xz: function () {
            var _this = this;
            el.$jsForm.validator({
                submit: function () {
                	var sfsy = '';
                	var sfemail ='';
                	if($("#sfsy").is(':checked')){
                		sfsy = "1";
                	}else{
                		sfsy="0";
                	}
                	if($("#sfemail").is(':checked')){
                		sfemail="1";
                	}else{
                		sfemail = "0";
                	}
                	var yjkcl = $('#yjkcl').val();
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                    	if(sfsy=="0" && sfemail=="0"){
                        	alert("请至少选择一种订阅方式！");
                        	return false;
                        }
                        el.$jsLoading.modal('toggle'); // show loading
                        $.ajax({
                            url: ur,
                            data: {"sfsy":sfsy,"sfemail":sfemail,"yjkcl":yjkcl},
                            method:"GET",
                            success: function (data) {
                             	el.$jsLoading.modal('close'); // close loading                              	
                                if (data.success) {
                                    alert(data.msg);
                                    el.$jsdiv.modal('close'); // close
                                    _this.tableEx.ajax.reload();
                                }else{
                                    alert(data.msg);                                  
                                }                             
                            },
                            error: function () {
                            	el.$jsLoading.modal('close'); // close loading
                                alert('保存失败，请检查!');
                            }
                        });
                        return false;
                    } else {
                        alert('数据验证失败，请检查！');
                        return false;
                    }
                }
            });
        },
       
        resetForm: function () {
            el.$jsForm[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$jsdiv.on('closed.modal.amui', function () {
                el.$jsForm[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$jsdiv.modal('close');
            });
        },
      //全选按钮
        checkAllAc: function () {
            var _this = this;
            el.$checkAll.on('change', function (e) {
                var $this = $(this),
                    check = null;
                if ($(this).is(':checked')) {
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                    });
                } else {
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.xz();
            _this.modalAction(); // hidden action
            _this.checkAllAc();
        }
    };
    action.init();
    

});