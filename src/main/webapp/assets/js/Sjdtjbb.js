/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
    	$jsTable: $('.js-table'),
    	$jsSlTable: $('.js-sltable'),
        $jsDate: $('#s_xzrq'),
        $jsDate1:$('#s_xzrq1'),
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
            	var bz = $('#searchbz').val();
            	if(bz=='1'){
            		d.xfid = $('#s_xfid option:selected').val(),
                    d.fpzl = $('#s_fpzl option:selected').val(),
                    d.kprqq = $('#s_kprqq').val(),
                    d.kprqz = $('#s_kprqq').val()
            	}else{
            		d.kprqq = el.$jsDate.val(),
                    d.kprqz = el.$jsDate1.val()
            	}
                
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
        	var bz = $('#searchbz').val();
        	if(bz=='1'){
        		var xfid = $('#s_xfid option:selected').val();
            	var fpzl = $('#s_fpzl option:selected').val();
            	var kprqq = $('#s_kprqq').val();
            	var kprqz = $('#s_kprqz').val();
        	}else{
        		var xfid = null;
        		var fpzl = null;
        		var kprqq = el.$jsDate.val();
            	var kprqz = el.$jsDate1.val();
        	}
        	el.$jsLoading.modal('toggle'),  // show loading         	
        	$.ajax({  		
                url: _this.config.getURL,
                data:{"xfid":xfid,"fpzl":fpzl,"kprqq":kprqq,"kprqz":kprqz},
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
                		$('#alert-msg').html(data.msg);
                		$('#my-alert').modal('open');    
                	}
                	el.$jsLoading.modal('close'); // close loading
                },
                error: function() {
                	$('#alert-msg').html("出错，请检查！");
                	$('#my-alert').modal('open');
                }        	
           });
        },       
        
        searchAc:function(){
        	var _this = this;
        	el.$jsSearch.on('click', function (e) {
        		$('#searchbz').val("1");
        		el.$jsLoading.modal('toggle');  // show loading
                var kprqq = $('#s_kprqq').val();
                var kprqz = $('#s_kprqz').val();
                if(kprqq==''||kprqz==''){
                	$('#alert-msg').html("请先选择起始月份，终止月份！");
    				$('#my-alert').modal('open');
                	el.$jsLoading.modal('toggle');
                	return false;               	
                }
                if(kprqq>kprqz){
                	$('#alert-msg').html("起始月份不能大于终止月份！");
    				$('#my-alert').modal('open');
                	el.$jsLoading.modal('toggle');
                	return false;               
                }
                _this.showFp();                
               e.preventDefault();              
               t.ajax.reload();
            })
        },
          
        find_mv:function(){
        	var _this = this;
        	$('#searchButton').on('click',function(e){
        		$('#searchbz').val("0");
        		el.$jsLoading.modal('toggle');  // show loading
                var kprqq = el.$jsDate.val();
                var kprqz = el.$jsDate1.val();
                if(kprqq==''||kprqz==''){
                	$('#alert-msg').html("请先选择起始月份，终止月份！");
    				$('#my-alert').modal('open');
                	el.$jsLoading.modal('toggle');
                	return false;               	
                }
                if(kprqq>kprqz){
                	$('#alert-msg').html("起始月份不能大于终止月份！");
    				$('#my-alert').modal('open');
                	el.$jsLoading.modal('toggle');
                	return false;               
                }
                _this.showFp();                
        		e.preventDefault();
                t.ajax.reload();
        	})
        },
        init: function () {
            var _this = this;
            _this.searchAc();
            _this.find_mv();
        }
    }; 
    action.init(); 
});
