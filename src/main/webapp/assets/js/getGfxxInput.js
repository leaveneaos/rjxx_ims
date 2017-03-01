$(function(){  
	     $("#gfmc_edit").on("input",(function(){
	    	var dataArray=[];  
	    	$("#gfsh_edit").val("");
	    	$("#gfdz_edit").val("");
	    	$("#gfdh_edit").val("");
	    	$("#gfyh_edit").val("");
	    	$("#gfzh_edit").val("");
		    var option = {  
		            max: 12,    //列表里的条目数  
		            minChars: 0,    //自动完成激活之前填入的最小字符  
		            width: 100,     //提示的宽度，溢出隐藏  
		            scrollHeight: 100,   //提示的高度，溢出显示滚动条  
		            matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示  
		            autoFill: true,    //自动填充  
		            minLength: 0,  
		            select: function (event, ui) {  
		                $("#gfmc_edit").val(ui.item.label); // display the selected text
		            	$("#gfsh_edit").val(ui.item.id);
		    	    	$("#gfdz_edit").val(ui.item.name);
		    	    	$("#gfdh_edit").val(ui.item.value1);
		    	    	$("#gfyh_edit").val(ui.item.value2);
		    	    	$("#gfzh_edit").val(ui.item.value3); 
		            }  
		    };

		    $.ajax({
				url : 'gfxxhq/getGfList',
				async:false,
				data : {
					mcszmsx :$('#gfmc_edit').val()
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {
						for(var i=0;i<data.resultList.length;i++){
							//alert(data.resultList[i].gfmc);
							 //var vo = data.resultList[i];  
	                   
							var gfmc=data.resultList[i].gfmc;
							var gfsh=data.resultList[i].gfsh;
							var gfdz=data.resultList[i].gfdz;
							var gfdh=data.resultList[i].gfdh;
							var gfyh=data.resultList[i].gfyh;
							var gfyhzh=data.resultList[i].gfyhzh;
							var mcszmsx=data.resultList[i].mcszmsx;
							//var tmp = "gfmc="+gfmc+"&gfsh="+gfsh+"&gfdz="+gfdz+"&gfdh="+gfdh+"&gfyh="+gfyh+"&gfyhzh="+gfyhzh;
							dataArray.push({id :gfsh, label:gfmc,label2:gfmc+mcszmsx,gfname:gfmc,name:gfdz,value1:gfdh,value2:gfyh,value3:gfyhzh});  
						} 
					} else {
						//alert('更新购方信息失败: ' + data.msg);

					}
				},
				error : function() {
					//alert('更新购方信息失败, 请重新登陆再试...!');
				}
			});
		   // alert(dataArray);
		   $("#gfmc_edit").autocomplete({source: dataArray}, option).on('focus', function() { $(this).keydown(); });  
	    }));
	});
	
	
	/**
	 * 需要引用  jquery-ui.css 和 jquery-ui2.js
	 * 
	 * 
	 * <div>
	 * <input id="autoByAjax" name="autoByAjax" type="text">
	 * <input id="autoByAjaxVal" name="autoByAjaxVal" type="text">
	 * </div>
	 * 
	 */