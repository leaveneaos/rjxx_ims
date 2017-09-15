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
                    d.xfid = $('#s_xfid option:selected').val();
                    d.skpid  = $('#s_skpid option:selected').val();
                    d.ppid = $('#s_ppid option:selected').val();
                    d.fpzl = $('#s_fpzl option:selected').val();
                    d.kprqq = $('#s_kprqq').val();
                    d.kprqz = $('#s_kprqz').val();
                }else{
                    d.xfid = $('#xfid option:selected').val();
                    d.skpid  = $('#skpid option:selected').val();
                    d.ppid = $('#ppid option:selected').val();
                    d.kprqq = el.$jsDate.val();
                    d.kprqz = el.$jsDate1.val();
                }
            }
        },
        "columns": [                 
            {"data": "sl"},
            {"data": "xfmc"},
            {"data": "fpzl"},
            // {"data": "jzjtbz"},
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
        	var xfid = null;
        	var skpid = null;
        	var ppid = null;
        	var fpzl = null;
            var kprqq = null;
            var kprqz = null;
        	if(bz=='1'){
        		 xfid = $('#s_xfid option:selected').val();
                 skpid  = $('#s_skpid option:selected').val();
                 ppid = $('#s_ppid option:selected').val();
            	 fpzl = $('#s_fpzl option:selected').val();
            	 kprqq = $('#s_kprqq').val();
            	 kprqz = $('#s_kprqz').val();
        	}else{
                xfid = $('#xfid option:selected').val();
                skpid = $('#kpd option:selected').val();
                ppid = $('#ppid option:selected').val();
                kprqq = el.$jsDate.val();
                kprqz = el.$jsDate1.val();
        	}
        	//el.$jsLoading.modal('toggle'),  // show loading
        	$.ajax({
                url: _this.config.getURL,
                data:{"xfid":xfid,"fpzl":fpzl,"skpid":skpid,"ppid":ppid,"kprqq":kprqq,"kprqz":kprqz},
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
                		// $('#alert-msg').html(data.msg);
                		// $('#my-alert').modal('open');
                        swal(data.msg);
                	}
                	el.$jsLoading.modal('close'); // close loading
                },
                error: function() {
                	// $('#alert-msg').html("出错，请检查！");
                	// $('#my-alert').modal('open');
                    swal("出错，请检查！");
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
                    swal('后台错误,请重新登录！');
                }
           }); 
        },
        preAc: function () {
        	var _this = this; 	
            el.$jsPre.on('click', function (e) {
                $('#searchbz').val("1");
                var kprqq = $('#s_kprqq').val();
                var kprqz = $('#s_kprqz').val();
                if(kprqq==''||kprqz==''){
                    //         	$('#alert-msg').html("请先选择起始月份，终止月份！");
                    // $('#my-alert').modal('open');
                    swal("请先选择起始月份，终止月份！");
                    //el.$jsLoading.modal('toggle');
                    return false;
                }
                if(kprqq>kprqz){
                    //         	$('#alert-msg').html("起始月份不能大于终止月份！");
                    // $('#my-alert').modal('open');
                    swal("起始月份不能大于终止月份！");
                    //el.$jsLoading.modal('toggle');
                    return false;
                }
            	_this.qyt({kprq:kprq});      
            	e.preventDefault();
              
            });
        },
        
        searchAc:function(){
        	var _this = this;
        	el.$jsSearch.on('click', function (e) {
        		$('#searchbz').val("1");
                //el.$jsLoading.modal('toggle');  // show loading
                var kprqq = $('#s_kprqq').val();
                var kprqz = $('#s_kprqz').val();
                if(kprqq==''||kprqz==''){
                    //         	$('#alert-msg').html("请先选择起始月份，终止月份！");
                    // $('#my-alert').modal('open');
                    swal("请先选择起始月份，终止月份！");
                    //el.$jsLoading.modal('toggle');
                    return false;
                }
                if(kprqq>kprqz){
                    //         	$('#alert-msg').html("起始月份不能大于终止月份！");
                    // $('#my-alert').modal('open');
                    swal("起始月份不能大于终止月份！");
                    //el.$jsLoading.modal('toggle');
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
        		//el.$jsLoading.modal('toggle');  // show loading
                var kprqq = el.$jsDate.val();
                var kprqz = el.$jsDate1.val();
                if(kprqq==''||kprqz==''){
                    //         	$('#alert-msg').html("请先选择起始月份，终止月份！");
                    // $('#my-alert').modal('open');
                    swal("请先选择起始日期，终止日期！");
                    //el.$jsLoading.modal('toggle');
                    return false;
                }
                if(kprqq>kprqz){
                    //         	$('#alert-msg').html("起始月份不能大于终止月份！");
                    // $('#my-alert').modal('open');
                    swal("起始日期不能大于终止日期！");
                    //el.$jsLoading.modal('toggle');
                    return false;
                }
                _this.showFp();                
               e.preventDefault();              
               t.ajax.reload();
        	})
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
            		// $('#alert-msg').html("请选择年月！");
            		// $('#my-alert').modal('open');
                    swal("请选择年月！");
            		return;
            	}
            	window.location.href = _this.config.exportURL+"?kprq=" +kprq;
            });
        },
        init: function () {
            var _this = this;
            //_this.tableEx = _this.dataTable();
            _this.searchAc(); 
            _this.find_mv();
        }
    }; 
    action.init(); 
});
