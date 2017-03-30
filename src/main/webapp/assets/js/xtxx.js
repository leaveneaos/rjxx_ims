/**
 * feel
 */
$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $jsRead:$('#xx_qyd'),
        $jsSearch: $('#xx_cx'),
        $jsXxyd:$('#xx_yd'),
        $jsXxwd:$('#xx_wd'),
        $jsXxdel:$('#xx_del'),
        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'xtxx/getList'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable({
            	"processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                "scrollX": false,
                "ajax": {
                    url: _this.config.getUrl,
                    type: 'GET',
                    data: function (d) {
                      
                    }
                },
                "columns": [
                   {
                       "orderable": false,
                       "data": null,
                       render: function (data, type, full, meta) {
                            return '<input type="checkbox" value="'+data.id+'"/>';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {
                        "orderable": false,
                        "data": null,
                        render: function (data) {
                        	if(data.ydbz=='1'){
                        		return '<span class="am-icon-home am-icon-envelope-open-o"></span>';
                        	}else{
                        		return '<span class="am-icon-home am-icon-envelope" style="color:#FFD306"></span>';
                        	}
                             
                         }
                     },
                    {"data": "title"},
                    {"data": "xxlx"},
                    {"data":"lrsj"},
                    {
                        "data": null,
                        "render": function (data) {
                            return '<a class="des">详情</a> <a class="del">删除</a> '
                        }
                    }                                                                
                ]
            });

            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });
            
            t.on('click', 'a.des', function () {
          	    var data = t.row($(this).parents('tr')).data();
          	    $("#m_title").html(data.title);
				$("#m_content").html(data.content);
				$("#m_lrsj").html(data.lrsj);
				$("#m_div").modal("open");
            });
            
            t.on('click', 'a.del', function () {
            	var _this = this;
          	  var da = t.row($(this).parents('tr')).data();
          	  $('#my-confirm').modal({
                    relatedTarget: this,
                    onConfirm: function(options) {                   	
                  	    $.ajax({
                  	    	url:'xtxx/del',
                  	    	data:{"id":da.id},
                  	    	success:function(data){
                  	    		if(data.success){
                  	    			alert("删除成功！");
                  	    			e.preventDefault();
                  	                _this.tableEx.ajax.reload();
                  	    		}
                  	    	}
                  	    })
                    },
                    onCancel: function() {
                   
                    }
                });
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

            });
        },
        //全部已读
        readAll:function(){
        	var _this = this;
        	el.$jsRead.on('click',function(e){        		
        		$.ajax({
           	    	url:'xtxx/readAll',
           	    	success:function(data){
           	    		if(data.success){
           	    			e.preventDefault();
           	                _this.tableEx.ajax.reload();
           	    		}
           	    	}
           	    })
        	})
        	 
        },
        //批量删除
        del:function(){
        	var _this = this;
        	var ids = "";
        	el.$jsXxdel.on('click',function(e){
        		_this.tableEx.column(0).nodes().each(function (cell, i) {
        			var $checkbox = $(cell).find('input[type="checkbox"]');     			
             		if ($checkbox.is(':checked')) {
             			ids += $checkbox.val()+',';
             		}           		
        		 });
        		if(ids==''){
        			alert("请至少选中一条记录！");
        			return false;
        		}
        		$('#my-confirm').modal({
                    relatedTarget: this,
                    onConfirm: function(options) {                   	
                    	$.ajax({
                			url:'xtxx/delMany',
                			data:{"ids":ids},
                			success:function(data){
                				if(data.success){
                					alert("删除成功！");
                   	    			e.preventDefault();
                   	                _this.tableEx.ajax.reload();
                   	    		}
                			}
                		});
                    },
                    onCancel: function() {
                   
                    }
                });
        	})
    		
        },
        //标记为已读
        yd:function(){
        	var _this = this;
        	var ids = "";
        	el.$jsXxyd.on('click',function(e){
        		_this.tableEx.column(0).nodes().each(function (cell, i) {
        			var $checkbox = $(cell).find('input[type="checkbox"]');     			
             		if ($checkbox.is(':checked')) {
             			ids += $checkbox.val()+',';
             		}           		
        		 });
        		if(ids==''){
        			alert("请至少选中一条记录！");
        			return false;
        		}
        		$.ajax({
        			url:'xtxx/allRead',
        			data:{"ids":ids},
        			success:function(data){
        				if(data.success){
           	    			e.preventDefault();
           	                _this.tableEx.ajax.reload();
           	    		}
        			}
        		});
        	})
    		
        },
        //标记为未读
        wd:function(){
        	var _this = this;
        	var ids = "";
        	el.$jsXxwd.on('click',function(e){
        		_this.tableEx.column(0).nodes().each(function (cell, i) {
        			var $checkbox = $(cell).find('input[type="checkbox"]');     			
             		if ($checkbox.is(':checked')) {
             			ids += $checkbox.val()+',';
             		}           		
        		 });
        		if(ids==''){
        			alert("请至少选中一条记录！");
        			return false;
        		}
        		$.ajax({
        			url:'xtxx/allNotRead',
        			data:{"ids":ids},
        			success:function(data){
        				if(data.success){
           	    			e.preventDefault();
           	                _this.tableEx.ajax.reload();
           	    		}
        			}
        		});
        	})
    		
        },
        
        close:function(){
        	$("#closem").on('click',$("#closem"),function(e){
        		$("#m_div").modal('close');
        	})
        },
        checkAllAc: function () {
            var _this = this;
            $('#selectall').on('change', function (e) {
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
            _this.readAll();
            _this.close();
            _this.del();
            _this.yd();
            _this.wd();
        }
    };
    action.init();    
    
});