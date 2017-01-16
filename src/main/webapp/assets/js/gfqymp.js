$(function () {
    "use strict";
    var el = {
        $jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // 红冲 form
        $jsAdd: $('.js-add'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading')
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'gfqymp/getGfxxList'
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
                    type: 'POST',
                    data: function (d) {
                        d.ddh = "";   // search 订单号
                        d.kpddm = "";   // search 发票号码
                        d.ddrqq = ""; // search 开票日期
                        d.ddrqz = ""; // search 开票日期
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
                    {"data": "storeNo"},
                    {"data": "storeNo"},
                    {
						"data": null,
                        "defaultContent": "<a class='modify' href='javascript:void(0)'>修改</a> <a href='javascript:void(0)' class='del'>删除</a>"									
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
         // 新增
			el.$jsAdd.on('click', el.$jsAdd, function() {
				_this.resetForm();
				//ur = _this.config.addUrl;
				el.$modalHongchong.modal({"width": 600, "height": 500});
			});
            return t;
        },
        /**
		 * 新增保存
		 */

		xz : function() {
			var _this = this;
			el.$jsForm0.validator({
				submit : function() {
					var formValidity = this.isFormValid();
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading
						if (parseInt(el.$dpmax.val()) < parseInt(el.$fpfz.val())) {
                        	alert('电子发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
                        if (parseInt(el.$ppmax.val()) < parseInt(el.$ppfz.val())) {
                        	alert('普通发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
                        if (parseInt(el.$zpmax.val()) < parseInt(el.$zpfz.val())) {
                        	alert('专用发票分票金额大于开票限额！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
						var data = el.$jsForm0.serialize(); // get form data
						$.ajax({
							url : ur,
							data : data,
							method : 'POST',
							success : function(data) {
								if (data.success) {
									// loading
									el.$modalHongchong.modal('close'); // close
									alert(data.msg);
									_this.tableEx.ajax.reload(); // reload table
								} else if (data.repeat) {
									alert(data.msg);
								}else{
									alert(data.msg);
								}
								el.$jsLoading.modal('close'); // close

							},
							error : function() {
								el.$jsLoading.modal('close'); // close loading
								alert('保存失败, 请重新登陆再试...!');
							}
						});
						return false;
					} else {
						alert('验证失败');
						return false;
					}
				}
			});
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
        	_this.xz();
            //_this.exportAc();
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