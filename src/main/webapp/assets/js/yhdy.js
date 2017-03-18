/**
 * feel
 */
$(function () {
    "use strict";
    var ur ;
    var el = {
    	$jsAdd: $('#jsAdd'),    //添加按钮
    	$jsClose:$('.js-close'),
    	$jsModal:$('#addModal'),
    	$jsForm:$('#dyform')
    };
    var action = {
        config: {
            getUrl: 'mydy/getItems'
        },
        /**
         * 订阅方式查询
         * */
        search:function(){
        	$.ajax({
        		url:'yhdy/getwddy',
        		method:'POST',
        		success:function(result){
        			for(var key in result){
        				var name = result[key];
        				$("#"+name+"").attr("checked",true);
        			}
        		}
        	});
        	$.ajax({
    			url:'yhdy/getyhxx',
    			method:'POST',
    			success:function(data){
    				if(data.success){
    					$("#s_yhmc").val(data.yhmc);
    					$("#s_sjhm").val(data.sjhm);
    					$("#s_email").val(data.email);
    					$("#s_openid").val(data.openid);
    				}else{
    					$('#alert-msg').html(data.msg);
        				$('#my-alert').modal('open');
    				}
    			}
    			
    		});
        },
        /**
         * 保存弹出
         * */
        xz:function(){
        	el.$jsAdd.on('click', el.$jsAdd, function () {
        		var dyfss = $('input[name="checkbox"]:checked');
        		if(dyfss==null ||dyfss.length==0){
        			$('#alert-msg').html("请至少选中一种订阅方式！");
    				$('#my-alert').modal('open');
    				return false;
        		}
        		var dys = '';
            	var dyArr = new Array();
            	$('#div-dx').hide();
            	$('#div-yx').hide();
            	$('#div-wx').hide();
            	$('#div-scewm').hide();
    			for(var i=0;i<dyfss.length;i++){
    				var cla = $(dyfss[i]).attr("class");
    				dyArr[i] = cla;    				
    			}
    			var openid = $("#s_openid").val();
    			for(var j=0;j<dyArr.length;j++){    				
    				var bz = dyArr[j];
    				if(bz=='dx'){
    					$('#div-dx').show();
    				}else if(bz=='yj'){
    					$('#div-yx').show();
    				}else if(bz=='wx'&&openid==''){				
    					var url = window.location.href;
    					//生成二维码
    					$.ajax({
    						url : 'wxdy/getEwm',
    						data:{"url":url},
    						method : 'post',
    						success : function(data) {
    							if (data.success) {
    								$('#doc-qrcode').attr("src",data.id);
    								//$('#doc-qrcode').qrcode({text:data.url,width:160,height:160});
    							}
    						}
    					});
    					$('#div-wx').show();
    				}else if(bz=='wx'&&openid !=''){
    					$('#div-scewm').show();
    				}
    			}       		
        		el.$jsModal.modal('open');
        	})
        },
        /**
         * 保存
         */       
        save: function () {
            var _this = this;
            el.$jsForm.validator({
                submit: function () {
                	var dys = '';
                	var dyArr = new Array();
                	var dyfss = $('input[name="checkbox"]:checked');
        			for(var i=0;i<dyfss.length;i++){
        				var dy = $(dyfss[i]).val();
        				var cla = $(dyfss[i]).attr("class");
        				dyArr[i] = cla;
        				if(i==dyfss.length-1){
        					dys += dy;
        				}else{
        					dys += dy+",";
        				}        				
        			}
        			var dxflag = false;
        			var yxflag = false;
        			var wxflag = false;
        			for(var j=0;j<dyArr.length;j++){
        				var bz = dyArr[j];
        				if(bz=='dx'){
        					dxflag = true;
        				}else if(bz=='yj'){
        					yxflag=true;
        				}else if(bz=='wx'){
        					wxflag=true;
        				}
        			}
        			var sjhm = $('#s_sjhm').val();
        			var email = $('#s_email').val();
        			var openid = $('#s_openid').val();
        			var yhmc = $('#s_yhmc').val();
        			if(dxflag==true&&sjhm==''){
        				$('#alert-msg').html("已勾选短信订阅，手机号码不能为空！");
        				$('#my-alert').modal('open');
        				return false;
        			}
        			if(yxflag==true&&email==''){
        				$('#alert-msg').html("已勾选邮箱订阅，邮箱不能为空！");
        				$('#my-alert').modal('open');
        				return false;
        			}
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        $.ajax({
                            url: 'yhdy/save',
                            data: {"dyfsstr":dys,"yhmc":yhmc,"sjhm":sjhm,"email":email,"openid":openid},
                            method:"POST",
                            success: function (data) {                             	
                                if (data.success) {
                                	el.$jsModal.modal('close');
                                	$('#alert-msg').html(data.msg);
                    				$('#my-alert').modal('open');
                    				$('#my-alert').on('closed.modal.amui', function(){
                    					window.location.reload();
                    				});                				
                                }else{
                                	$('#alert-msg').html(data.msg);
                    				$('#my-alert').modal('open');                                 
                                }                             
                            },
                            error: function () {
                            	$('#alert-msg').html("保存失败，请检查！");
                				$('#my-alert').modal('open');
                            }
                        });
                        return false;
                    } else {
                    	$('#alert-msg').html("数据验证失败，请检查！");
        				$('#my-alert').modal('open');
                        return false;
                    }
                }
            });
        },
       
        resetForm: function () {
            //el.$jsForm[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$jsModal.on('closed.modal.amui', function () {
                //el.$jsForm[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$jsModal.modal('close');
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
        
        //订阅方式选中function
        checkbox:function(){
        	$('.dyfs').on('click',function(){
        		if($("#01").is(':checked')){
            		$("#a_sjhm").show();
            	}else{
            		$("#a_sjhm").hide();
            	}
        		if($("#02").is(':checked')){
            		$("#a_email").show();
            	}else{
            		$("#a_email").hide();
            	}
        		if($("#03").is(':checked')){
            		$("#a_ewm").show();
            	}else{
            		$("#a_ewm").hide();
            	}
        	})       	
        },
        
        init: function () {
            var _this = this;
            _this.save();
            _this.xz();
            _this.modalAction(); // hidden action
            _this.search();
        }
    };
    action.init();
  
});