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
            		d.xfid = $('#m_xfid').val();
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
        getPlot1:function(){
        	$('#chart1').html("");
        	var bz = $('#searchbz').val();
        	var xfid = null;
        	var skpid = null;
        	var kprqq = null;
        	var kprqz = null;
        	if(bz=='1'){
        		xfid = $('#s_xfid').val(),
                skpid = $('#s_spid').val(),
                kprqq = $('#s_kprqq').val(),
                kprqz = $('#s_kprqq').val()
        	}else{
        		xfid = $('#m_xfid').val();
        		kprqq = el.$jsDate.val(),
                kprqz = el.$jsDate1.val()
        	}
        	$.ajax({
        		url:'sjdtjbb/getYplPlot',
        		data:{"xfid":xfid,"skpid":skpid,"kprqq":kprqq,"kprqz":kprqz},
        		success:function(data){
        			var line1 = new Array();
        			var ticks = new Array();
        			var i = 0;
					for (var key in data) {
						ticks[i] = key;
						line1[i] = data[key];
						i++;
					}
        				//plot开始
        				$('#chart1').jqplot([line1],
        						{
        							title : '月开票量统计',
        							//seriesColors:["#2894FF","#FFE153"],
        							//varyBarColor: true,
        							seriesDefaults : {
        							pointLabels : {
        								show : true
        							},
        							shadow : false,
        							showMarker : true
        						},
        						axes : {
        							xaxis : {
        							label : '月份',
        							labelOptions: {
        						        fontFamily: 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
        						        fontSize: '14px'
        						    },
        							tickRenderer : $.jqplot.CanvasAxisTickRenderer,
        							renderer : $.jqplot.CategoryAxisRenderer,
        							ticks : ticks,
        							showTicks : true, // 是否显示刻度线以及坐标轴上的刻度值  
        							showTickMarks : true, //设置是否显示刻度
        							tickOptions : {
        								show : true,
        								fontSize : '14px',
        								fontFamily : 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
        								angle : -30,
        								showLabel : true, //是否显示刻度线以及坐标轴上的刻度值
        								showMark : false,//设置是否显示刻度
        								showGridline : false // 是否在图表区域显示刻度值方向的网格
        							}
        						},
        						yaxis : {
        							label : '开票量',
        							labelOptions: {
        						        fontFamily: 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
        						        fontSize: '14px'
        						    },
        							show : true,
        							showTicks : true, // 是否显示刻度线以及坐标轴上的刻度值  
        							showTickMarks : true, //设置是否显示刻度
        							autoscale : true,
        							borderWidth : 1,
        							tickOptions : {
        								show : true,
        								showLabel : true,
        								showMark : true,
        								showGridline : true
        							}
        						}
        					}
        			});
        		}
        	});
        },
        
        getPlot2:function(){
        	$('#chart2').html("");
        	var bz = $('#searchbz').val();
        	var xfid = null;
        	var skpid = null;
        	var kprqq = null;
        	var kprqz = null;
        	if(bz=='1'){
        		xfid = $('#s_xfid').val(),
                skpid = $('#s_skpid').val(),
                kprqq = $('#s_kprqq').val(),
                kprqz = $('#s_kprqq').val()
        	}else{
        		xfid = $('#m_xfid').val();
        		kprqq = el.$jsDate.val(),
                kprqz = el.$jsDate1.val()
        	}
        	$.ajax({
        		url:'sjdtjbb/getTqlPlot',
        		data:{"xfid":xfid,"skpid":skpid,"kprqq":kprqq,"kprqz":kprqz},
        		success:function(data){
        			var line1 = new Array();
        			var ticks = new Array();
        			var i = 0;
					for (var key in data) {
						ticks[i] = key;
						line1[i] = data[key];
						i++;
					}
        				//plot开始
        				$('#chart2').jqplot([line1],
        						{
        							title : '月提取量统计',
        							//seriesColors:["#2894FF","#FFE153"],
        							//varyBarColor: true,
        							seriesDefaults : {
        							pointLabels : {
        								show : true
        							},
        							shadow : false,
        							showMarker : true
        						},
        						axes : {
        							xaxis : {
        							label : '月份',
        							labelOptions: {
        						        fontFamily: 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
        						        fontSize: '14px'
        						    },
        							tickRenderer : $.jqplot.CanvasAxisTickRenderer,
        							renderer : $.jqplot.CategoryAxisRenderer,
        							ticks : ticks,
        							showTicks : true, // 是否显示刻度线以及坐标轴上的刻度值  
        							showTickMarks : true, //设置是否显示刻度
        							tickOptions : {
        								show : true,
        								fontSize : '14px',
        								fontFamily : 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
        								angle : -30,
        								showLabel : true, //是否显示刻度线以及坐标轴上的刻度值
        								showMark : false,//设置是否显示刻度
        								showGridline : false // 是否在图表区域显示刻度值方向的网格
        							}
        						},
        						yaxis : {
        							label : '提取量',
        							labelOptions: {
        						        fontFamily: 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
        						        fontSize: '14px'
        						    },
        							show : true,
        							showTicks : true, // 是否显示刻度线以及坐标轴上的刻度值  
        							showTickMarks : true, //设置是否显示刻度
        							autoscale : true,
        							borderWidth : 1,
        							tickOptions : {
        								show : true,
        								showLabel : true,
        								showMark : true,
        								showGridline : true
        							}
        						}
        					}
        			});
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
                _this.getPlot1();                
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
                	$('#alert-msg').html("请先选择起始月份，终止月份！");
    				$('#my-alert').modal('open');
                	//el.$jsLoading.modal('toggle');
                	return false;               	
                }
                if(kprqq>kprqz){
                	$('#alert-msg').html("起始月份不能大于终止月份！");
    				$('#my-alert').modal('open');
                	//el.$jsLoading.modal('toggle');
                	return false;               
                }
                _this.getPlot1(); 
                _this.getPlot2();
        		e.preventDefault();
                t.ajax.reload();
        	})
        },
        
        kpd:function(){
        	var xfid = $('#s_xfid').val();
			var skpid = $("#s_skpid");
			$("#s_skpid").empty();
			$.ajax({
				url : "fpkc/getKpd",
				data : {
					"xfid" : xfid
				},
				success : function(data) {
					var option = $("<option>").text("请选择开票点").val("");
					skpid.append(option);
					for (var i = 0; i < data.length; i++) {						
						option = $("<option>").text(data[i].kpdmc).val(
								data[i].skpid);
						skpid.append(option);
					}
				}

			});
        },
        
        init: function () {
            var _this = this;
            _this.searchAc();
            _this.find_mv();
            _this.getPlot1();
            _this.getPlot2();
            _this.kpd();
        }
    }; 
    action.init(); 
});
