/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";


    var el = {

    };

    var action = {
        chartEx: null,

        chart: function () {
            // todo get data from serve
            // ajax
            var s1 = [[2002, 112000], [2003, 122000], [2004, 104000], [2005, 99000], [2006, 121000],
                [2007, 148000], [2008, 114000], [2009, 133000], [2010, 161000], [2011, 173000]];
            var s2 = [[2002, 10200], [2003, 10800], [2004, 11200], [2005, 11800], [2006, 12400],
                [2007, 12800], [2008, 13200], [2009, 12600], [2010, 13100]];

            var plot1 = $.jqplot("chart2", [s2, s1], {
                // Turns on animatino for all series in this plot.
                title: '分月统计报表',
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
                        label: '营业额',
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
                            barWidth: 15,
                            barPadding: -15,
                            barMargin: 0,
                            highlightMouseOver: false
                        }
                    },
                    {
                        label: '差异',
                        rendererOptions: {
                            // speed up the animation a little bit.
                            // This is a number of milliseconds.
                            // Default for a line series is 2500.
                            animation: {
                                speed: 2000
                            }
                        },
                        color: 'red'
                    }
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
                        label: '单位 秒/s',
                        show: false,
                        tickInterval: 1,
                        ticks: [2002, 2003, 2004,2005,2006,2007,2008,2009,2010,2011],
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

        init: function () {
            var _this = this;

            _this.chartEx = _this.chart();

        }
    };

    action.init();

});
