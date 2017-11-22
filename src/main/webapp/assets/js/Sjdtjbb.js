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
            url: 'sjdtjbb/getList',
            type: 'POST',
            data: function (d) {
            	var bz = $('#searchbz').val();
            	if(bz=='1'){
            		d.xfid = $('#s_xfid').val(),
                    d.skpid = $('#s_skpid').val(),
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
            {
               "orderable": false,
               "data": null,
               "defaultContent": ""
            },
            {"data": "kpny"},
            {"data": function (data) {
                if (data.fpzldm =='00') {
                    return '小计';
                }else{
                    return data.fpzlmc;
                }
            },},
            {"data": "fpsl"},
            {"data": "jshj"}
            ]
    });
    t.on('draw.dt', function (e, settings, json) {
        var x = t,
            page = x.page.info().start; // 设置第几页
        t.column(0).nodes().each(function (cell, i) {
            cell.innerHTML = page + i + 1;
        });

    });
    var action = {
    	tableEx: null, // cache dataTable
        config: {
            getURL:'fytjbb/getfps',
            getJE:'fytjbb/getje'
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
            //_this.searchAc();
            //_this.find_mv();
            //_this.getPlot1();
            //_this.getPlot2();
            _this.kpd();
        }
    }; 
    action.init();


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
            if(kprqq.split("-")[0]<kprqz.split("-")[0]){
                swal("所选日期不能跨年！");
                //el.$jsLoading.modal('toggle');
                return false;
            }
            getPlot1();
            //_this.getPlot2();
            e.preventDefault();
            t.ajax.reload();
        });

    $('#searchButton').on('click',function(e){
        $('#searchbz').val("0");
        //el.$jsLoading.modal('toggle');  // show loading
        var kprqq = el.$jsDate.val();
        var kprqz = el.$jsDate1.val();
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
        if(kprqq.split("-")[0]<kprqz.split("-")[0]){
            swal("所选日期不能跨年！");
            //el.$jsLoading.modal('toggle');
            return false;
        }
        getPlot1();
        //_this.getPlot2();
        //e.preventDefault();
        t.ajax.reload();
    });


    function getPlot1(){       //用票量折线图
        //$('#chart1').html("");
        var bz = $('#searchbz').val();
        var xfid = null;
        var skpid = null;
        var kprqq = null;
        var kprqz = null;
        if(bz=='1'){
            xfid = $('#s_xfid').val(),
                skpid = $('#s_spkid').val(),
                kprqq = $('#s_kprqq').val(),
                kprqz = $('#s_kprqz').val()
        }else{
            xfid = $('#m_xfid').val();
            kprqq = el.$jsDate.val(),
                kprqz = el.$jsDate1.val()
        }
        var line1 = new Array();
        var ticks = new Array();
        var line2 = new Array();
        $.ajax({
            url:'sjdtjbb/getYplPlot',
            data:{"xfid":xfid,"skpid":skpid,"kprqq":kprqq,"kprqz":kprqz},
            success:function(msg){
                var dateArray = msg.dateArray;
                var zpArray = msg.zpArray;
                var zpjeArray = msg.zpjeArray;
                var ppArray = msg.ppArray;
                var ppjeArray = msg.ppjeArray;
                var dpArray = msg.dpArray;
                var dpjeArray = msg.dpjeArray;
                fpyl(dateArray,zpArray,zpjeArray,ppArray,ppjeArray,dpArray,dpjeArray);
            }
        });

    };


    function fpyl(dateArray,zpArray,zpjeArray,ppArray,ppjeArray,dpArray,dpjeArray) {
        var t3 = document.getElementById('main');
        var myChart = echarts.init(t3);
        myChart.showLoading(
            {text: 'Loding...'}
        );

        var colors = ['#5793f3', '#d14a61', '#675bba','#0000CD','#00BFFF','#ADFF2F'];

        var option = {
            color: colors,

            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross'
                }
            },
            grid: {
                right: '20%'
            },
            toolbox: {
                feature: {
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data:['电票量','专票量','普票量','电票金额','专票金额','普票金额']
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: true
                    },
                    //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                    data :dateArray
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '价税合计',
                    //min: 0,
                    //max: 25,
                    position: 'right',
                    axisLine: {
                        lineStyle: {
                            color: colors[0]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} 元'
                    }
                },
                {
                    type: 'value',
                    name: '票量',
                    min: 0,
                    //max: 250,
                    position: 'left',
                    //offset: 80,
                    axisLine: {
                        lineStyle: {
                            color: colors[2]
                        }
                    },
                    axisLabel: {
                        formatter: '{value} 张'
                    }
                }
            ],
            series: [
                {
                    name:'电票量',
                    type:'bar',
                    yAxisIndex: 1,
                    data:dpArray
                },
                {
                    name:'专票量',
                    type:'bar',
                    yAxisIndex: 1,
                    data:zpArray
                },
                {
                    name:'普票量',
                    type:'bar',
                    yAxisIndex: 1,
                    data:ppArray
                },

                {
                    name:'电票金额',
                    type:'line',
                    // yAxisIndex: 1,
                    data:dpjeArray
                },
                {
                    name:'专票金额',
                    type:'line',
                    //yAxisIndex: 1,
                    data:zpjeArray
                },
                {
                    name:'普票金额',
                    type:'line',
                    //yAxisIndex: 1,
                    data:ppjeArray
                }

            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.hideLoading();
        myChart.setOption(option);
    }
});


