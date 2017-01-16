(function ($) {
    'use strict';
    var el = {    
        $jsForm: $('.js-form'),
        $jsLoading: $('.js-modal-loading'),
        $jsSubmit: $('.js-submit')
        };
    $(function () {
    	el.$jsLoading.modal('toggle'),  // show loading
    	$.ajax({  		
            url: "xfxxwh/getXfxx", context: null, success: function (data) {  
            	
                $("#xfsh").val(data.xfsh);
                $("#khyh").val(data.xfyh);
                $("#xflxr").val(data.xflxr);
                $("#kpr").val(data.kpr);
                $("#skr").val(data.skr);
                
                $("#xfmc").val(data.xfmc);
                $("#yhzh").val(data.xfyhzh);
                $("#dz").val(data.xfdz);
                $("#xfdh").val(data.xfdh);
                $("#xfyb").val(data.xfyb);
                $("#fhr").val(data.fhr);
                $("#sqyhs").val(data.sqyhs);
                el.$jsLoading.modal('close'); // close loading
                $('#showXf').ajax.reload(); 
                
  		  }        
        }); 
    	$('#save').click(function () {
    		var r = el.$jsForm.validator("isFormValid");  
    		  if (r) {
    			  el.$jsLoading.modal('toggle');  // show loading
    			  $.ajax({
    				  url: "xfxxwh/save", method: 'POST', success: function (data) { 
    					  if(data.success){
    						  alert(data.msg);
    					      $('#showXf').ajax.reload();
    					  }else {
                              alert('后台错误: 保存数据失败,' + data.msg);
                          }  
    					  el.$jsForm.ajax.reload();
    					  el.$jsLoading.modal('close'); // close loading
    				  },
    				  error: function () {
                          alert('保存数据失败,请稍后重试');
                      }
    	          });	
    			  el.$jsLoading.modal('close'); // close loading
    		  }
        });        	
    });   
})(jQuery);