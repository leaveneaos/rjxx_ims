/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";


    var el = {
        $jsPre: $('.js-pre'),
        $jsLater: $('.js-later'),
        $jsDate: $('#s_xzrq'),

        $jsKcpl: $('.js-kcpl'),  // 库存票量

        $jsForm: $('.js-form'),
        $jsFrush: $('.js-frush'),
        $jsSave: $('.js-save'),
        $jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        config: {
            saveURL: '/save/frtibb'
        },
        /**
         * 前一天
         */
        preAc: function () {
            el.$jsPre.on('click', function (e) {
                e.preventDefault();
                // todo
                alert('前一天');

            });
        },
        /**
         * 后一天
         */
        laterAc: function () {
            el.$jsLater.on('click', function (e) {
                e.preventDefault();
                // todo
                alert('后一天');

            });
        },
        /**
         * change date
         */
        dateAc: function() {
            el.$jsDate.on('changeDate.datepicker.amui', function () {

                debugger
                // todo
                alert('date changed');
            });
        },
        /**
         * 保存
         */
        saveAc: function () {
            var _this = this;
            el.$jsSave.on('click', function (e) {
                e.preventDefault();
                if (!el.$jsForm.find('input[name="dy"]:checked').length) {
                    alert('请选择订阅方式');
                    return false;
                }
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

            _this.preAc();
            _this.laterAc();
            _this.dateAc();

            _this.saveAc();
            _this.frushAc();
            _this.exportAc();


        }
    };

    action.init();

});
