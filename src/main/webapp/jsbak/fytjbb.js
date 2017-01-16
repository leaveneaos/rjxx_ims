/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";


    var el = {
        $jsTable: $('.js-table'),
        $jsForm: $('.js-form'),

        $jsFrush: $('.js-frush'),
        $jsSave: $('.js-save'),
        $jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        chartEx: null,
        tableEx: null,
        config: {
            getURL: '/json/fytjbb.json',
            saveURL: '/save/frtibb'
        },

        chart: function () {
            // todo get data from serve
            // ajax
            var s1 = [[1, 112000], [2, 122000], [3, 104000], [4, 99000], [5, 121000],
                [6, 148000], [7, 114000], [8, 133000], [9, 161000], [10, 173000] , [11, 173000], [12, 173000]];
            var s2 = [[1, 10200], [2, 10800], [3, 11200], [4, 11800], [5, 12400],
                [6, 12800], [7, 13200], [8, 12600], [9, 13100],[10, 13100],[11, 13100],[12, 13100]];

            var s3 = [[1, 11900], [2, 13800], [3, 12200], [4, 12800], [5, 17400],
                [6, 16800], [7, 15200], [8, 13600], [9, 14100],[10, 16100],[11, 17100],[12, 11100]];

            var plot1 = $.jqplot("chart2", [s3, s2, s1], {
                // Turns on animatino for all series in this plot.
                title: '',
                animate: true,
                // Will animate plot on calls to plot1.replot({resetAxes:true})
                animateReplot: true,
                cursor: {
                    show: true,
                    zoom: true,
                    looseZoom: true,
                    showTooltip: false
                },
                series:[
                    {
                        label: '平均',
                        rendererOptions: {
                            // speed up the animation a little bit.
                            // This is a number of milliseconds.
                            // Default for a line series is 2500.
                            animation: {
                                speed: 2000
                            }
                        }
                    },
                    {
                        color: 'red',
                        varyBarColor: false,
                        label: '发票换开',
                        pointLabels: {
                            show: true
                        },
                        renderer: $.jqplot.BarRenderer,
                        showHighlight: false,
                        yaxis: 'y2axis',
                        rendererOptions: {
                            // Speed up the animation a little bit.
                            // This is a number of milliseconds.
                            // Default for bar series is 3000.
                            animation: {
                                speed: 2500
                            },
                            barWidth: 20,
                            barPadding: 0,
                            barMargin: 0,
                            highlightMouseOver: false
                        }
                    },
                    {
                        color: 'blue',
                        varyBarColor: false,
                        label: '正常开具',
                        pointLabels: {
                            show: true
                        },
                        renderer: $.jqplot.BarRenderer,
                        showHighlight: false,
                        yaxis: 'y2axis',
                        rendererOptions: {
                            // Speed up the animation a little bit.
                            // This is a number of milliseconds.
                            // Default for bar series is 3000.
                            animation: {
                                speed: 2500
                            },
                            barWidth: 20,
                            barPadding: -25,
                            barMargin: 0,
                            highlightMouseOver: false
                        }
                    },
                ],
                legend: {
                    show: true,//设置是否出现分类名称框(即所有分类的名称出现在图的某个位置)
                    location: 'ne', //分类名称框出现位置, nw, n, ne, e, se, s, sw, w.
                    xoffset: 12, //分类名称框距图表区域上边框的距离(单位px)
                    yoffset: 12, //分类名称框距图表区域左边框的距离(单位px)
                    background:'', //分类名称框距图表区域背景色
                    textColor:'', //分类名称框距图表区域内字体颜色
                },
                axesDefaults: {
                    pad: 1
                },
                axes: {
                    // These options will set up the x axis like a category axis.
                    xaxis: {
                        label: '月份 /month',
                        show: false,
                        tickInterval: 1,
                        ticks: [1, 2, 3,4,5,6,7,8,9,10,11,12],
                        drawMajorGridlines: true,
                        drawMinorGridlines: true,
                        drawMajorTickMarks: false,
                        rendererOptions: {
                            tickInset: 0.5,
                            minorTicks: 1
                        },
                        tickOptions: {
                            formatString: '%d'
                        }

                    },
                    yaxis: {
                        label: '单位 /元',
                        tickOptions: {
                            formatString: "%'d"
                        },
                        rendererOptions: {
                            forceTickAt0: true
                        }
                    },
                    y2axis: {
                        label: '单位 /元',
                        tickOptions: {
                            formatString: "%'d"
                        },
                        rendererOptions: {
                            // align the ticks on the y2 axis with the y axis.
                            alignTicks: true,
                            forceTickAt0: true
                        }
                    }
                },
                highlighter: {
                    show: true,
                    showLabel: true,
                    tooltipAxes: 'y',
                    sizeAdjust: 7.5 ,
                    tooltipLocation : 'ne'
                }
            });

            return plot1;
        },
        dataTable: function () {
            var _this = this;

            var t = el.$jsTable.DataTable( {
                "processing": true,
                "serverSide": true,
                ordering:  false,
                searching: false,
                paging: false,

                "ajax":{
                    url: _this.config.getURL,
                    type: 'GET'
                },
                "columns": [
                    {
                        "orderable":      false,
                        "data": null,
                        "defaultContent": ""
                    },
                    { "data": "yf" },
                    { "data": "zpkjfs" },
                    { "data": "zpje" },
                    { "data": "fpkjfs" },
                    { "data": "fpje" },
                    { "data": "hcfpfs" },
                    { "data": "hcje"}
                ]
            });

            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each( function (cell, i) {
                    cell.innerHTML = page + i + 1;
                } );

            });


            return t;
        },


        /**
         * 保存
         */
        saveAc: function () {
            var _this = this;
            el.$jsSave.on('click', function (e) {
                e.preventDefault();
                var data = el.$jsForm.serialize();
                el.$jsLoading.modal('open');
                // todo

                $.ajax({
                    url: _this.config.saveURL,
                    data: data,
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        if (data.statu === '0') {
                            alert('保存成功');
                        } else {
                            alert('服务器异常' + data.message);
                        }
                        el.$jsLoading.modal('close');
                    },
                    error: function () {
                        alert('服务器错误,请稍后重试');
                    }
                });
            });
        },
        /**
         * 刷新
         */
        frushAc: function () {
            var _this = this;
            el.$jsFrush.on('click', function (e) {
                e.preventDefault();

                window.location.reload();
            });

        },
        /**
         * 导出
         */
        exportAc: function () {
            el.$jsExport.on('click', function (e) {
                e.preventDefault();

                // todo you code here

                alert('导出成功');
            });
        },
        init: function () {
            var _this = this;

            _this.chartEx = _this.chart();
            _this.tableEx = _this.dataTable();

            _this.saveAc();
            _this.frushAc();
            _this.exportAc();


        }
    };

    action.init();

});
