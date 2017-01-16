$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // 红冲 form
        $s_ddh: $('#s_ddh'), // search 订单号
        $s_kpddm: $('#s_kpddm'), // search 发票号码
        $s_ddrqq: $('#s_ddrqq'), // search 开票日期
        $s_ddrqz: $('#s_ddrqz'), // search 开票日期
        $jsSearch: $('.js-search'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading')
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'jyxxcx/getJyxxList'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                "ajax": {
                    url: _this.config.getUrl,
                    type: 'GET',
                    data: function (d) {
                        d.ddh = el.$s_ddh.val();   // search 订单号
                        d.kpddm = el.$s_kpddm.val();   // search 发票号码
                        d.ddrqq = el.$s_ddrqq.val(); // search 开票日期
                        d.ddrqz = el.$s_ddrqz.val(); // search 开票日期
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {"data": "orderNo"},
                    //{"data": "orderTime"},
                    {"data":  function (data) {
                        if (data.orderTime) {
                            return dateFormat(data.orderTime);
                        } else {
                            return null;
                        }
                     }
                    },
                    {
                        "data": function (data) {
                            if (data.price) {
                                return FormatFloat(data.price, "###,###.00");
                            } else {
                                return null;
                            }
                        }, 'sClass': 'right'
                    },
                    {"data": "storeNo"},
                    {
                        "data": null,
                        "defaultContent": '<a class="view">查看</a>'
                    }
                ]
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });
            // 红冲
            t.on('click', 'a.generates', function () {
                var data = t.row($(this).parents('tr')).data();
                // todo
                // ajax request
                alert('生成成功');
            });
            // 发票明细
            t.on('click', 'a.view', function () {
                var data = t.row($(this).parents('tr')).data();
                // todo
                _this.setForm0(data);
                el.$modalHongchong.modal('open');
            });
            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                if ((!el.$s_ddrqq.val() && el.$s_ddrqz.val()) || (el.$s_ddrqq.val() && !el.$s_ddrqz.val())) {
                    alert('Error,请选择开始和结束时间!');
                    return false;
                }
                var dt1 = new Date(el.$s_ddrqq.val().replace(/-/g, "/"));
                var dt2 = new Date(el.$s_ddrqz.val().replace(/-/g, "/"));
                if ((el.$s_ddrqq.val() && el.$s_ddrqz.val())) {// 都不为空
                    if (dt1.getYear() == dt2.getYear()) {
                        if (dt1.getMonth() == dt2.getMonth()) {
                            if (dt1 - dt2 > 0) {
                                alert('开始日期大于结束日期,Error!');
                                return false;
                            }
                        } else {
                            // alert('月份不同,Error!');
                            alert('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    } else {
                        // alert('年份不同,Error!');
                        alert('Error,请选择同一个年月内的时间!');
                        return false;
                    }
                }
                e.preventDefault();
                _this.tableEx.ajax.reload();
            });
        },
        /**
         * 导出按钮
         */
        exportAc: function () {
            el.$jsExport.on('click', function (e) {
                // todo
                alert('导出成功');
            });
        },
        setForm0: function (data) {
            var _this = this,
                i;
            // todo set data
            // ajax get data
            el.$jsForm0.find('[name="xq_ddh"]').val(data.orderNo);
            el.$jsForm0.find('[name="xq_ddrq"]').val(dateFormat(data.orderTime));
            el.$jsForm0.find('[name="xq_je"]').val(FormatFloat(data.price, "###,###.00"));
            el.$jsForm0.find('[name="xq_mdh"]').val(data.storeNo);
            //el.$jsForm0.find('[name="id"]').val(data.id);
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modalHongchong.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$modalHongchong.modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.exportAc();
            _this.modalAction(); // hidden action
        }
    };
    action.init();
});


	function dateFormat(str){
		var pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
		var formatedDate = str.replace(pattern, '$1-$2-$3 $4:$5:$6');
		return formatedDate;
	}