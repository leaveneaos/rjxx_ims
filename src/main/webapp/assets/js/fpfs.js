/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $modalOriginalDetail: $('#original-detail-modal'),

        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),


        $jsForm1: $('.js-form-1'),  // 原发票明细 form

        $s_jyrqq: $('#s_jyrqq'), // search 交易日期起
        $s_jyrqz: $('#s_jyrqz'), // search 交易日期止
        $s_kprqq: $('#s_kprqq'), // search 开票日期起
        $s_kprqz: $('#s_kprqz'), // search 开票日期止
        $s_gfmc: $('#s_gfmc'), // search 购房名称
        $s_ddh: $('#s_ddh'), // search 订单号
        $s_fpdm: $('#s_fpdm'), // search 购房名称
        $s_fphm: $('#s_fphm'), // search 订单号
        $xfid: $('#xfid'), // search 订单号

        $checkAll: $('#check_all'), // check all checkbox
        $jsSearch: $('.js-search'),
        $jsSent: $('.js-sent'),// sent all
        $button3: $('#button3'), 

        $jsLoading: $('.js-modal-loading')
    };
    var loaddata=false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'yjfs/getYjfsList',
            sentUrl: 'yjfs/send',
            updateUrl: 'yjfs/update'

        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                scrollX : true,

                "ajax": {
                    url: _this.config.getUrl,
                    type: 'POST',
                    data: function (d) {
                       var tip = $('#tip').val();
                       var txt = $('#searchtxt').val();
                        d.loaddata=loaddata;
                       if ($('#bj').val() == 1) {
                           if (tip == "1") {
                               d.gfmc = txt;
                           }else if (tip == "2") {
                               d.ddh = txt;
                           }else if (tip == "3") {
                               d.fphm = txt;
                           }else if (tip == "4") {
                               d.fpdm = txt;
                           }else if (tip == "5") {
                               d.xfmc = txt;
                           }
                       }else{
                           d.jyrqq = el.$s_jyrqq.val(); // search 交易日期起
                           d.jyrqz = el.$s_jyrqz.val(); // search 交易日期止
                           d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                           d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                           d.gfmc = el.$s_gfmc.val(); // search 购方名称
                           d.ddh = el.$s_ddh.val();//订单号
                           d.fpdm = el.$s_fpdm.val();//发票代码
                           d.fphm = el.$s_fphm.val();//发票号码
                           d.xfid = el.$xfid.val();//发票号码
                       }
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        //"defaultContent": '<input type="checkbox" />'
                        render: function (data, type, full, meta) {
                            return '<input type="checkbox" value="' + data.kplsh + '" />';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {
                        "data": null,
                        "render": function (data) {
                            return '<a class="view" href="' + data.pdfurl + '"  target="_blank">查看</a> <a class="modify" title="修改接收邮件地址和手机号码">修改</a> <a class="sent">发送</a>';
                        }
                    },
                    {"data": "ddh"},
                    {"data": "jylssj"},
                    {"data": "gfmc"},
                    // {"data": "xfmc"},
                    // {"data": "kprq"},
                    // {"data": "fpdm"},
                    // {"data": "fphm"},
                    // {"data": function (data) {
                    //     if (data.je) {
                    //         return FormatFloat(data.je, "###,###.00");
                    //     } else {
                    //         return null;
                    //     }
                    // }, 'sClass': 'right'},
                    // {"data": function (data) {
                    //     if (data.se) {
                    //         return FormatFloat(data.se, "###,###.00");
                    //     } else {
                    //         return null;
                    //     }
                    // }, 'sClass': 'right'},
                    {"data": function (data) {
                        if (data.jshj) {
                            return FormatFloat(data.jshj, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'},
                    {"data": "gfemail"},
                    {
                        "data": null,
                        "defaultContent": '<td>' +
                        '<label class="am-checkbox-inline"> <input class="sentType" type="checkbox" name="email" value="0" checked>邮件</label>'
                        // '<label class="am-checkbox-inline"> <input class="sentType" type="checkbox" name="text" value="1" >短信</label></td>' +
                        // '<label class="am-checkbox-inline"> <input class="sentType" type="checkbox" name="wechat" value="2" >微信</label></td>'
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


            //
            t.on('click', 'a.modify', function () {
                var data = t.row($(this).parents('tr')).data();
                data.rowid = t.row($(this).parents('tr')).index();
                _this.setForm1(data);
                el.$modalOriginalDetail.modal('open');

            });

            // 红冲发票明细
            t.on('click', 'a.sent', function () {
                var data = t.row($(this).parents('tr')).data(),
                    $sentType = null,
                    row = '';
                if (data.gfemail == null || data.gfemail == "") {
                    // $('#msg').html('没有邮箱，请增加邮箱后在发送');
                    // $('#my-alert').modal('open');
                    swal('没有邮箱，请增加邮箱后在发送');
                    return;    
                }
                $(this).parents('tr').find('.sentType').each(function (i, el) {
                    $sentType = $(el);
                    if ($sentType.is(':checked')) {
                        if (i === 0) { // 拼接 选中的数据
                            row = row + $sentType.val();
                        } else {
                            row = row + '-' + $sentType.val(); // split -
                        }
                    }
                });

                // todo set key
                _this.sentEmail({ids: data.kplsh + ':' + row});
            });

            return t;
        },
        /**
         * 发送 email request
         * @param data { ids: '1,2,3,4' }
         */
        sentEmail: function (data) {
            $('.confirm').attr('disabled',"disabled");
            var _this = this;
            el.$jsLoading.modal('open');
            if(data.ids==''){
                // $('#msg').html('请先选择至少一条数据');
                // $('#my-alert').modal('open');
                swal('请先选择至少一条数据');
                el.$jsLoading.modal('close');
                return;             
            }
            $.ajax({
                url: _this.config.sentUrl,
                data: data,
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    // todo 处理返回结果
                    if (data.statu === '0') {
                        // $('#msg').html(data.msg);
                        // $('#my-alert').modal('open');
                        $('.confirm').removeAttr('disabled');
                        swal(data.msg);
                    } else {
                        // $('#msg').html('发送失败,服务器错误' + data.message);
                        // $('#my-alert').modal('open');
                        swal('发送失败,服务器错误' + data.message);
                    }
                    el.$jsLoading.modal('close');
                },
                error: function () {
                    // $('#msg').html('请求失败,请刷新后稍后重试!');
                    // $('#my-alert').modal('open');
                    swal('请求失败,请刷新后稍后重试!');
                }
            });


        },
        /**
         * search action
         */
        searchAc: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                e.preventDefault();
                $('#bj').val('1');
                loaddata=true;
                _this.tableEx.ajax.reload();
            });
            el.$button3.on('click', function (e) {
                e.preventDefault();
                $('#bj').val('2');
                loaddata=true;
                _this.tableEx.ajax.reload();
            });
        },
        /**
         * check all action
         */
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
        /**
         * 发送全部选中的
         */
        sentAllAc: function () {
            var _this = this;
            el.$jsSent.on('click', function (e) {
                e.preventDefault();

                var data = '',
                    row = '',
                    $tr = null,
                    $sentType = null;
                _this.tableEx.column(0).nodes().each(function (cell, i) {
                    row = '';
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                        // todo 设置数据格式 使用 id:发送方式-发送方式-发送方式,id:发送方式-发送方式 形式
                        // 1:0-1-2,2:2,3:0-2 数据格式
                        row = $checkbox.val() + ':'; // set id; 使用 : 分割 id 和 发送方式
                        $checkbox.parents('tr').find('.sentType').each(function (j, el) {
                            $sentType = $(this);
                            if ($sentType.is(':checked')) {
                                if (j === 0) { // 拼接 选中的数据
                                    row = row + $sentType.val();
                                } else {
                                    row = row + '-' + $sentType.val(); // split -
                                }
                            }
                        });

                        if (i === 0) { // 拼接 选中的数据
                            data = row;
                        } else {
                            data = data + ',' + row;
                        }
                    }

                });

                _this.sentEmail({ids: data});
            });
        },
        saveRow: function () {
            var _this = this;
            el.$jsForm1.validator({
                submit: function (e) {
                    e.preventDefault();

                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('open');  // show loading

                        var data = el.$jsForm1.serialize(); // get form data data
                        // TODO save data to serve
                        $.ajax({
                            url: _this.config.updateUrl,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.statu === '0') {

                                    // update row data
                                    var rowId = el.$jsForm1.find('[name="rowId"]').val(),
                                        row = _this.tableEx.row(rowId),
                                        d = row.data();
                                    d.yx = el.$jsForm1.find('[name="yx"]').val();
                                    d.sj = el.$jsForm1.find('[name="sj"]').val();
                                    row.data(d).draw();

                                    el.$modalOriginalDetail.modal('close');  // close modal

                                    _this.tableEx.ajax.reload(); // reload table data

                                } else {
                                    // $('#msg').html('后台错误: 保存数据失败' + data.message);
                                    // $('#my-alert').modal('open');
                                    swal('后台错误: 保存数据失败' + data.message);
                                }


                                el.$jsLoading.modal('close'); // close loading

                            },
                            error: function () {
                                // $('#msg').html('保存数据失败, 请刷新后再试...!');
                                // $('#my-alert').modal('open');
                                swal('保存数据失败, 请刷新后再试...!');
                            }
                        });

                        return false;
                    } else {
                        // $('#msg').html('验证失败');
                        // $('#my-alert').modal('open');
                        swal('验证失败');
                        return false;
                    }
                }
            });
        },
        setForm1: function (data) {
            var _this = this,
                i;
            el.$jsForm1.find('input[name="gfemail"]').val(data.gfemail);
            el.$jsForm1.find('input[name="sj"]').val('');
            el.$jsForm1.find('input[name="wx"]').val(''); // 你决定放什么
            el.$jsForm1.find('input[name="kplsh"]').val(data.kplsh);
            el.$jsForm1.find('input[name="rowId"]').val(data.rowid); // set rowid
        },
        resetForm: function () {
            el.$jsForm1[0].reset();

        },
        modalAction: function () {
            var _this = this;
            el.$modalOriginalDetail.on('closed.modal.amui', function () {
                el.$jsForm1[0].reset();
            });

            // close modal
            el.$jsClose.on('click', function () {
                el.$modalOriginalDetail.modal('close');
            });
        },
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable

            _this.searchAc();
            _this.checkAllAc();
            _this.sentAllAc();

            _this.saveRow();
            _this.modalAction(); // hidden action


        }
    };

    action.init();

});