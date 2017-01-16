/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";


    var action = {
        chartEx: null,

        gauge: {    // gauge
            csv1: {
                id: 'csv1',     // dom id
                name: '分时统计',
                type: 1,        // 分时统计的唯一表示, ajax 请求数据区分那个 gauge
                instance: null, // instance
                interval: null   // inteval
            },
            csv2: {
                id: 'csv2',
                name: '当前发票的生成时间',
                type: 2,
                instance: null,
                interval: null
            },
            csv3: {
                id: 'csv3',
                name: '发票库存量',
                type: 3,
                instance: null,
                interval: null
            },
            csvPie: {   // pie 系统状态
                instance: null
            }

        },
        config: {
            UPDATEINTEVEL: 5000, // update date data time unit milliseconds (ms)
            getUrl: '/get/csv',
            alertTime: 2         // 获取数据几次后 提示给用户

        },
        requestError: 0,
        chart: function () {
            // todo get data from serve
            // ajax
            var s1 = [[2002, 112000], [2003, 122000], [2004, 104000], [2005, 99000], [2006, 121000],
                [2007, 148000], [2008, 114000], [2009, 133000], [2010, 161000], [2011, 173000]];
            var s2 = [[2002, 10200], [2003, 10800], [2004, 11200], [2005, 11800], [2006, 12400],
                [2007, 12800], [2008, 13200], [2009, 12600], [2010, 13100]];

            var plot1 = $.jqplot("chart1", [s2, s1], {
                // Turns on animatino for all series in this plot.
                title: '仪表盘',
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


        /**
         *
         * @param d { type: 'value', id: 'id' }  { type: 2 , id: 'csv2' } id是 gague 的 属性名
         */
        getData: function (d) {
            var _this = this;
            // todo for test
            var x = {
                    1: 10,
                    2: 20,
                    3: 30,
                    4: 40,
                    5: 50,
                    6: 60,
                    7: 70,
                    8: 80,
                    9: 90,
                    10: 100
                },
                value = Math.round(Math.random()*10);

            console.log(d.id + ': value' + value + ': ' + x[value]);
            _this.gauge[d.id].instance.value = (d.type === 1 ) ? value : x[value];
            _this.gauge[d.id].instance.grow();

            return;
            $.ajax({
                url: _this.config.getUrl,
                data: d,
                type: 'POST',
                dataType: 'json',       // { status: 0 ,value: 12, message: 'error message' } status: 0 成功 1 失败; value: 返回值,用该更新 gague; message: 错误信息, 失败时返回
                success: function (data, status, jqXHR) {
                    if ( data.status === 0 ) {

                        // update gague
                        _this.gauge[d.id].instance.value = data.value;
                        _this.gauge[d.id].instance.grow();

                    } else {
                        if (_this.requestError === _this.config.alertTime) {
                            alert('获取数据失败' + data.message);
                            _this.stopRequest();    // 停止获取数据
                        } else {
                            _this.requestError++;
                        }
                    }
                },
                error: function () {
                    if (_this.requestError === _this.config.alertTime) {
                        alert('获取数据失败');
                        _this.stopRequest();    // 停止获取数据
                    } else {
                        _this.requestError++;
                    }

                }
            });
        },
        /**
         * 停止所有 请求
         */
        stopRequest: function () {
            var x;
            for (x in _this.gauge) {
                clearInterval(_this.gauge[x].interval);
            }
        },
        /**
         * initCsv1
         */
        initCsv1: function () {
            var _this = this;
            _this.gauge.csv1.instance = new RGraph.Gauge({
                id: 'cvs1',
                min: 0,
                max: 10,
                value: 8,
                options: {
                    titleTop: '开票速度',
                    titleTopPos: 0.4,
                    titleTopSize: 22,

                    backgroundColor: 'Gradient(white:#00a)',
                    textColor: 'white',
                    tickmarksBigColor: 'white',
                    tickmarksSmallColor: 'white',
                    redColor: 'rgba(0,0,0,0)',
                    yellowColor: 'rgba(0,0,0,0)',
                    borderInner: '#00c',
                    borderOuter: 'Gradient(blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:blue:black)',
                    adjustable: true
                }
            }).grow();
        },
        /**
         * updave csv1
         */
        updateCsv1: function () {
            var _this = this;
            _this.gauge.csv1.interval = setInterval(function () {
                // todo
                _this.getData({type: 1, id: 'csv1'});
            }, _this.config.UPDATEINTEVEL);
        },
        /**
         * initCsv2
         */
        initCsv2: function () {
            var _this = this;
            _this.gauge.csv2.instance = new RGraph.Gauge({
                id: 'cvs2',
                min: 0,
                max: 1000,
                value: 80,
                options: {
                    titleTop: '分时统计',
                    titleTopPos: 0.4,
                    titleTopSize: 22
                }
            }).grow();
        },
        /**
         * updave csv2
         */
        updateCsv2: function () {
            var _this = this;
            _this.gauge.csv2.interval = setInterval(function () {
                // todo
                _this.getData({type: 2, id: 'csv2'});
            }, _this.config.UPDATEINTEVEL);
        },
        /**
         * csv3
         */
        initCsv3: function () {
            var _this = this;
            _this.gauge.csv2.instance = new RGraph.VProgress({
                id: 'cvs3',
                min: 0,
                max: 100,
                value: '100',
                options: {
                    title: '发票库存情况',
                    colors: ['Gradient(white:#00FF00)'],
                    gutterRight:35,
                    textSize: 12,
                    tickmarks: 100,
                    numticks: 20,
                    margin: 5
                }
            }).grow();
        },
        /**
         * updave csv3
         */
        updateCsv3: function () {
            var _this = this;
            _this.gauge.csv3.interval = setInterval(function () {
                // todo
                _this.getData({type: 3, id: 'csv3'});
            }, _this.config.UPDATEINTEVEL);
        },
        /**
         * 系统状态
         */
        initCsvPie: function () {
            var _this = this;
            _this.gauge.csvPie.instance = new RGraph.Pie({
                    id: 'cvs-pie',
                    data: [1],
                    options: {
                        title: '系统状态',
                        shadow: false,
                        colors: ['#00FF00'],
                        strokestyle: '#00FF00',
                        exploded: 4
                    }
                }).draw();
        },
        init: function () {
            var _this = this;
            //_this.chartEx = _this.chart();

            // csv1
            _this.initCsv1();
            _this.updateCsv1();

            // csv2
            _this.initCsv2();
            _this.updateCsv2();

            // csv3
            _this.initCsv3();
            _this.updateCsv3();

            // 系统状态
            _this.initCsvPie();

            // 系统是否可用
            //setTimeout(function () {
            //    _this.gauge.csvPie.instance.set('colors', ['#ff0000']);
            //    _this.gauge.csvPie.instance.set('strokestyle', '#ff0000').grow();
            //
            //}, 2000);

        }
    };

    action.init();


});
