/**
 * feel
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $jsAgree: $('.js-agree'), // 
        $jsDisAgree: $('.js-disagree'), // 关闭 modal
        $s_gfmc: $('#s_gfmc'), // search 购方名称
        $s_ddh: $('#s_ddh'), // search
        $s_ztbz: $('#s_ztbz'), // search
        $jsSearch: $('.js-search'),
        $jsModal:$('#disagreeModal'),
        $jsSubmit:$('.js-submit'),
        $jsClose:$('.js-close'),
        $jsLoading: $('.js-modal-loading')
    };
  //开票商品明细table
    var kpls_table = $('#mxTable').DataTable({
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
            "url": "fpcksh/getKpMx",
            data: function (d) {
                d.djh = $("#djh").val();
            }
        },
        "columns": [           
            {"data": "gfmc"},
            {"data": "fpdm"},
            {"data": "fphm"},           
            {"data":"kprq"},
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
            },
            {"data":"fpzt"},
            {
            	"data": null,
                "render": function (data) {
                		return '<a href="'+ data.pdfurl+'" target="_blank">查看</a>';              		               		                
                }
            }
        ]
    });
    
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpcksh/getJylsList',
            ckUrl: 'fpcksh/qrck'
        },

        dataTable: function () {
            var _this = this;
            var t = el.$jsTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                            d.ztbz = el.$s_ztbz.val();
                            d.gfmc = el.$s_gfmc.val(); // search 购方名称
                            d.ddh = el.$s_ddh.val();
                        }
                    },
                    "columns": [
                        {"data":"djh"},
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {"data": "ddh"},
                        {"data": "newgfmc"},
                        {"data": "gfmc"},                  
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
                        {"data":"ckhkyy"}, 
                        {"data":"sqsj"},
                        {"data":"sqid"}
                        ]
                });

            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                $('.js-table tr').find('td:eq(0)').hide();
                $('.js-table tr').find('td:eq(8)').hide();
            });
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
                $("#djh").val(data.djh);
                $("#sqid").val(data.sqid);
                kpls_table.ajax.reload();
            });           
            return t;
        },
        
        /**
         * 确认重开
         */
        saveRow: function () {
            var _this = this;
            el.$jsAgree.on('click', function (e) {
            	var djh = $('#djh').val();
            	if(djh==''){
            		swal("请先选中一条记录！");
            	}else{
            		$.ajax({
                        url: _this.config.ckUrl,
                        data: {"djh":djh},
                        method: 'POST',
                        success: function (data) {
                            if (data.success) {
                                swal(data.msg);
                                $('#djh').val("");
                                kpls_table.ajax.reload();                               
                            } else {
                                swal('后台错误: 操作失败' + data.msg);
                            }
                            _this.tableEx.ajax.reload(); // reload table
                            el.$jsLoading.modal('close'); // close loading

                        },
                        error: function () {
                            swal('操作失败, 请重新登陆再试...!');
                        }
                    });
            	}                
            });
        },
 
        /*resetForm: function () {
            el.$jsForm0[0].reset();
        },*/
        modalAction: function () {
           var _this = this;
           el.$jsModal.on('closed.modal.amui', function () {
           	$("#ckbtgyy").val("");
           });
           el.$jsDisAgree.on('click',function(){
        	   var sqid=$('#sqid').val();
        	   if(sqid==''){
        		   swal("请选选择一条记录！");
        	   }else{
        		   el.$jsModal.modal({"width": 500, "height": 240});
        	   }      	   
           });
            el.$jsClose.on('click', function () {
                el.$jsModal.modal('close');
                $("#ckbtgyy").val("");
            });
            //撤销操作
            el.$jsSubmit.on('click',function(e){        
            		var id = $('#sqid').val();
            		var yy = $("#ckbtgyy").val();
            		if(yy!=''){
            			$.ajax({
                    		url:'fpcksh/update',
                    		data:{"id":id,"reason":yy},
                    		success:function(data){
                    			if(data.success){
                    				swal(data.msg);
                    				el.$jsModal.modal('close');
                    				e.preventDefault();
                    				_this.tableEx.ajax.reload();
                    				$("#djh").val("");
                    				$("#sqid").val("");
                    				kpls_table.ajax.reload();
                    			}else{
                    				swal(data.msg);
                    			}
                    		}
                    	});
            		}else{
            			swal("请填写撤销原因！");
            		}                	           	
            })
        },
        searchAc: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {              
               e.preventDefault();
               _this.tableEx.ajax.reload();
               $('#djh').val("");
               kpls_table.ajax.reload();
               $('#sqid').val("");
           });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.saveRow();
            _this.modalAction(); // hidden action
            _this.searchAc();
        }
    };
    action.init();

});