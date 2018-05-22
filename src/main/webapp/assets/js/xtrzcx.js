$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // 红冲 form
        $jsLoading: $('.js-modal-loading')
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'xtrzcx/getXtrzList'
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
                       /* d.ddh = el.$s_ddh.val();   // search 订单号
                        d.kpddm = el.$s_kpddm.val();   // search 发票号码
                        d.ddrqq = el.$s_ddrqq.val(); // search 开票日期
                        d.ddrqz = el.$s_ddrqz.val(); // search 开票日期
*/                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {"data": "anctionobj"},
                    {"data": "description"},
                    {"data": "details"},
                    {"data": "requestip"},
                    {"data": "operator"},
                    {"data":  function (data) {
                        if (data.time) {
                            return data.time;
                        } else {
                            return null;
                        }
                     }
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
                swal('生成成功');
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
            //_this.search_ac();
            //_this.exportAc();
            _this.modalAction(); // hidden action
        }
    };
    action.init();
});


	/*function dateFormat(str){
		var pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
		var formatedDate = str.replace(pattern, '$1-$2-$3 $4:$5:$6');
		return formatedDate;
	}*/