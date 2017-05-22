/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
    	$jsTable: $('.js-table'),   
        $jsPre: $('.js-pre'),
        $jsLater: $('.js-later'),
        $jsDate: $('#s_xzrq'),
       // $jsKcpl: $('.js-kcpl'),  // 库存票量
        $jsForm: $('.js-form'),
        $jsFresh: $('.js-fresh'),
        $jsSave: $('.js-save'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading')
    };
   
    var action = {
    	tableEx: null, // cache dataTable
        config: {
            saveURL: 'frtjbb/save',
            getURL:'frtjbb/getList',
            getPreDay:'frtjbb/getPre',
            getLaterDay:'frtjbb/getLater',
            exportURL:'frtjbb/export'
        },   
        showFp:function(data){
        	var _this = this;          	
        	el.$jsLoading.modal('toggle'),  // show loading         	
        	$.ajax({  		
                url: _this.config.getURL,
                data:data,
                type: 'POST',
                dataType: 'json',             
                success: function (data) {              	
                	if(data.success){
                		//正常开具
                		$("#je1").val(data.je1); 
                        $("#se1").val(data.se1);
                        $("#jshj1").val(data.jshj1);
                        $("#fpsl1").val(data.fpsl1);
                        //红冲
                        $("#je2").val(data.je2); 
                        $("#se2").val(data.se2);
                        $("#jshj2").val(data.jshj2);
                        $("#fpsl2").val(data.fpsl2);
                        //换开
                		$("#je3").val(data.je3); 
                        $("#se3").val(data.se3);
                        $("#jshj3").val(data.jshj3);
                        $("#fpsl3").val(data.fpsl3); 
                	}else{
                		 swal('数据读取失败,服务器错误:'+data.msg);     
                	}
                	el.$jsLoading.modal('close'); // close loading
                	el.$jsTable.ajax.reload(); // reload table data
                },
                error: function() {
                    swal('后台错误,请重新登录！');
                }
        	
           });
        },        
        /**
         * 前一天
         */
        qyt:function(data){
        	var _this = this;
        	el.$jsLoading.modal('toggle'),  // show loading
        	$.ajax({  		
                url: _this.config.getPreDay, 
                data:data,
                type: 'POST',
                dataType: 'json',           
                success: function (data) {  
                	if(data.success){
                      $("#s_xzrq").val(data.prerq);                            
                      _this.showFp({kprq:data.prerq}); 
                	}
                    el.$jsLoading.modal('close'); // close loading
                    el.$jsForm.ajax.reload(); 
                },
                error: function () {
                    swal('后台错误,请重新登录！');
                }
           }); 
        },
        preAc: function () {
        	var _this = this; 	
            el.$jsPre.on('click', function (e) { 
            	var kprq=el.$jsDate.val();       
            	if(!kprq){
            		swal('Error,请选择开票日期!');
                    return false;
            	}
            	_this.qyt({kprq:kprq});      
            	e.preventDefault();
                _this.tableEx.ajax.reload();
              
            });
        },
        
        /**
         * 后一天
         */
        hyt:function(data){
        	var _this = this;
        	el.$jsLoading.modal('toggle'),  // show loading
        	$.ajax({  		
                url: _this.config.getLaterDay, 
               data:data,
                 type: 'POST',
                 dataType: 'json', 
              // context: null, 
                success: function (data) {  
                	if(data.success){
                      $("#s_xzrq").val(data.laterrq);                            
                      _this.showFp({kprq:data.laterrq}); 
                	}
                    el.$jsLoading.modal('close'); // close loading
                    el.$jsForm.ajax.reload(); 
                },
                error: function () {
                    swal('后台错误,请重新登录！');
                }
           }); 
        },
        laterAc: function () {
        	var _this = this;
            el.$jsLater.on('click', function (e) {
            	var kprq=el.$jsDate.val();
            	if(!kprq){
            		swal('Error,请选择开票日期!');
                    return false;
            	}  
            	 _this.hyt({kprq:kprq});             	      	
                e.preventDefault();
                _this.tableEx.ajax.reload();
            });
        },
        /**
         * change date
         */
        dateAc: function() {
        	var _this = this;
            el.$jsDate.on('changeDate.datepicker.amui', function () {
                //debugger
                // todo  
                var kprq=el.$jsDate.val();
               _this.showFp({kprq:kprq}); //传参格式{'随便写':参数值}            
            });
        },
        /**
         * 保存
         */
        saveAc: function () {
            var _this = this;
            el.$jsSave.on('click', function (e) {
                e.preventDefault();
                if (!el.$jsForm.find('input[name="dy"]:checked').length) {
                    swal('请选择订阅方式');
                    return false;
                }
                var data = el.$jsForm.serialize();
                el.$jsLoading.modal('open');
                // todo
                $.ajax({
                    url: _this.config.saveURL,
                    data: data,
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            swal('保存成功');
                        } else {
                            swal('服务器异常' + data.msg);
                        }
                        el.$jsLoading.modal('close');
                    },
                    error: function () {
                        swal('服务器错误,请稍后重试');
                    }
                });
            });
        },
        /**
         * 刷新
         */
        freshAc: function () {
            var _this = this;
            el.$jsFresh.on('click', function (e) {
                e.preventDefault();               
                window.location.reload();
            });

        },
        /**
         * 导出
         */
        exportAc: function () {
        	 var _this = this;
            el.$jsExport.on('click', function (e) {
                e.preventDefault();
            	var kprq=el.$jsDate.val();
            	if(""==kprq||null==kprq){
            		swal("请选择日期");
            		return;
            	}
            	window.location.href = _this.config.exportURL+"?kprq=" +kprq;

   //             alert('导出成功');
            });
        },
        init: function () {
            var _this = this;        
            _this.preAc();
            _this.laterAc();           
            _this.saveAc();
            _this.freshAc();
            _this.exportAc();
            _this.tableEx=_this.dateAc();
        }
    }; 
    action.init();
});
