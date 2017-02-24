/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
    	$jsTable: $('.js-table'),
    	$jsSlTable: $('.js-sltable'),
        $jsDate: $('#s_xzrq'),
        $jsForm: $('.js-form'),
        $jsSearch: $('#jsSearch'),
        $jsLoading: $('.js-modal-loading')
    };
    
    var t = el.$jsSlTable.DataTable({
        "processing": true,
        "serverSide": true,
        ordering: false,
        searching: false,
        bInfo:false,
        bPaginate:false,
        "ajax": {
            url: 'fytjbb/getje',
            type: 'POST',
            data: function (d) {
                d.xfid = $('#s_xfid option:selected').val(),
                d.fpzl = $('#s_fpzl option:selected').val(),
                d.kprq = el.$jsDate.val()
            }
        },
        "columns": [                 
            {"data": "sl"},
            {"data": "xfmc"},
            {"data": "fpzl"},
            {"data": "jzjtbz"},                   
            {"data": "zsje"},
            {"data": "fsje"},
            {"data": "hjje"},
            {"data": "zsse"},
            {"data": "fsse"},
            {"data": "hjse"}
            ]
    });
    
    var action = {
    	tableEx: null, // cache dataTable
        config: {
            getURL:'fytjbb/getfps',
            getJE:'fytjbb/getje'
        },   
        showFp:function(){
        	var _this = this;
        	var xfid = $('#s_xfid option:selected').val();
        	var fpzl = $('#s_fpzl option:selected').val();
        	var kprq = el.$jsDate.val();
        	el.$jsLoading.modal('toggle'),  // show loading         	
        	$.ajax({  		
                url: _this.config.getURL,
                data:{"xfid":xfid,"fpzl":fpzl,"kprq":kprq},
                type: 'POST',
                dataType: 'json',             
                success: function (data) {              	
                	if(data.success){
                		$("#zspfs").val(data.zspfs); 
                        $("#fspfs").val(data.fspfs);
                        $("#hjpfs").val(data.hjpfs);
                        $("#zcpfs").val(data.zcpfs);
                        $("#hcpfs").val(data.hcpfs); 
                        $("#hkpfs").val(data.hkpfs);
                        $("#zfpfs").val(data.zfpfs);
                        $("#ckpfs").val(data.ckpfs);
                		$("#cdpfs").val(data.cdpfs); 
                	}else{
                		 alert('数据读取失败,服务器错误:'+data.msg);     
                	}
                	el.$jsLoading.modal('close'); // close loading
                },
                error: function() {
                    alert('后台错误,请重新登录！');
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
                },
                error: function () {
                    alert('后台错误,请重新登录！');
                }
           }); 
        },
        preAc: function () {
        	var _this = this; 	
            el.$jsPre.on('click', function (e) { 
            	var kprq=el.$jsDate.val();       
            	if(!kprq){
            		alert('Error,请选择开票日期!');
                    return false;
            	}
            	_this.qyt({kprq:kprq});      
            	e.preventDefault();
              
            });
        },
        
        searchAc:function(){
        	var _this = this;
        	//$(".js-sltable  tr:not(:first)").html("");
        	el.$jsSearch.on('click', function (e) { 
        		el.$jsLoading.modal('toggle');  // show loading
                var kprq = el.$jsDate.val();
                if(kprq==''){
                	alert("请先选择月份！");
                	el.$jsLoading.modal('toggle');
                	return false;               	
                }
                _this.showFp();                
               e.preventDefault();              
               t.ajax.reload();
            })
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
                	}else{
                		
                	}
                    el.$jsLoading.modal('close'); // close loading
                 
                },
                error: function () {
                    alert('后台错误,请重新登录！');
                }
           }); 
        },
        laterAc: function () {
        	var _this = this;
            el.$jsLater.on('click', function (e) {
            	var kprq=el.$jsDate.val();
            	if(!kprq){
            		alert('Error,请选择开票日期!');
                    return false;
            	}  
            	 _this.hyt({kprq:kprq});             	      	
                e.preventDefault();
            });
        },
        /**
         * 查询
         */
        	
      
        /**
         * 导出
         */
        exportAc: function () {
        	 var _this = this;
            el.$jsExport.on('click', function (e) {
                e.preventDefault();
            	var kprq=el.$jsDate.val();
            	if(""==kprq||null==kprq){
            		alert("请选择日期");
            		return;
            	}
            	window.location.href = _this.config.exportURL+"?kprq=" +kprq;
            });
        },
        init: function () {
            var _this = this;
            //_this.tableEx = _this.dataTable();
            _this.searchAc();            
        }
    }; 
    action.init(); 
});
