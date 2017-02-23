/**
 * feel
 */
$(function () {
    "use strict";
    var ur ;
    var el = {
    	$jsTable: $('.js-table'),//查询table
    	$jsForm:$('.js-form-yjsz'),
    	$jsAdd: $('#jsAdd'),//添加按钮
        $jsdiv:$("#shezhi"),
        $jssave:$(".js-submit"),
        $jsClose:$('.js_close'),
        $checkAll: $('#selectAll'),
        $jsLoading: $('.js-modal-loading')//输在载入特效
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'mydy/getItems'
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
                           
                        }
                    },
                    "columns": [                    
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {"data": "dybt"},               
                        {"data": "dyfsmc"},
                        {
                            "data": null,
                            "render": function (data) {
                                return '<a class="update">修改</a> <a class="del">删除</a>';
                            }
                        }]
                });
            	   
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });

            // 新增
            el.$jsAdd.on('click', el.$jsAdd, function () {
            	$(".dyfs").attr("checked",false);
            	$("#dybtidinput").find("option").eq(0).attr("selected", true);
                el.$jsdiv.modal('open');
                ur="mydy/save";
                
            });
            // 修改
            t.on('click', 'a.update', function () {
                var row = t.row($(this).parents('tr')).data();
                var str=row.dyfs.split(",");
                for(var j=0;j<str.length;j++){
                	$("#"+str[j]+"").attr("checked",true);
                }                
                var dybt = row.dybt;
                var selectIndex = -1;
                var options = $("#dybtid").find("option");
                for (var j = 0; j < options.size(); j++) {
                    var text = $(options[j]).text();                  
                    if (text == dybt) {
                        selectIndex = j;                        
                        break;
                    }
                }
                $("#dybtidinput").find("option").eq(selectIndex).attr("selected", true);
                el.$jsdiv.modal('open');
                ur='mydy/update?id='+row.id;
            });
            //删除
            t.on('click','a.del',function(){
            	var row = t.row($(this).parents('tr')).data();
            	var id = row.id;
            	$.ajax({
            		url:'mydy/del?id='+id,
            		method:"GET",
            		success:function(data){
            			if(data.success){
            				alert(data.msg);
            				t.ajax.reload();
            			}else{
            				alert(data.msg);
            			}
            		}
            	})
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
                	var dyfs='';
                	var dyfsdms = $("input[name='dyfs']:checked");
                	for(var i=0;i<dyfsdms.length;i++){
                		var id = $(dyfsdms[i]).attr("id");                		
                		if(i!=dyfsdms.length-1){
                			dyfs+=id+",";
                		}else{
                			dyfs+=id;
                		}               		
                	}
                	var dybtid = $('#dybtidinput option:selected').val();
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle'); // show loading
                        $.ajax({
                            url: ur,
                            data: {"dyfs":dyfs,"dybtid":dybtid},
                            method:"GET",
                            success: function (data) {
                             	el.$jsLoading.modal('close');                              	
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
            _this.xz();
            _this.modalAction(); // hidden action
            _this.checkAllAc();
        }
    };
    action.init();
  
});