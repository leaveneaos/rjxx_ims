/**
 * Created by Administrator on 2018-01-22.
 */
/**
 * feel
 */
$(function() {
    "use strict";
    var el = {
        $jsTable : $('.js-table'),
        $modalfpxx : $('#fpxx'),
        $jsSubmit : $('.js-submit'),
        $jsSubmit1 : $('.js-submit1'),
        $jsClose : $('.js-close'),
        $jsClose1 : $('.js-close1'),
        $jsAuto : $('.js-auto'),
        $jsOut : $('.js-out'),
        $jsForm0 : $('.js-form-0'), // form
        $s_dyzt : $('#s_dyzt'), // search 开票日期
        $jsSearch : $('.js-search'),
        $jsExport : $('.js-export'),
        $jsLoading : $('.js-modal-loading'),
        $jsPrint : $('.js-print'),
        $checkAll : $('#select_all'),
        $xiugai: $('#doc-modal-cyjl'),
    };
    //批量导入
    var $importModal = $("#bulk-import-div");
    $("#kp_dr").click(function () {
        $('#importExcelForm').resetForm();
        $importModal.modal({"width": 600, "height": 350});
    });
    $(this).removeData('amui.modal');
    var mxarr = [];
    var $modal = $("#my-alert-edit2");
    var $tab = $('#doc-tab-demo-1');

    $("#my-alert-edit2").on("open.modal.amui", function () {
        $("#mx_form").validator("destroy");
        $("#main_form").validator("destroy");
    });

    $('#kp_add').click(function () {
        mxarr = [];
        $('#lrmx_form').resetForm();
        $('#main_form2').resetForm();
        $modal.modal({"width": 820, "height": 520});
    });
    $("#lrclose").click(function () {
        $modal.modal("close");
    });
    $("#close1").on('click', function () {
        $importModal.modal('close');
    });
    var index = 1;
    $('#lrmain_tab').find('a.ai').on('opened.tabs.amui', function (e) {
        //jyspmx_edit_table.draw();
    });
    $('#jyspmx_edit_table tbody').on('click', 'a', function () {
        jyspmx_edit_table.row($(this).parents("tr")).remove().draw(false);
        mxarr.pop();
        $('#jyspmx_edit_table tbody').find("span.index").each(function (index, object) {
            $(object).html(index + 1);
        });
    });
    //发票查验
    $("#lrsave").click(function () {
        var r = $("#main_form1").validator("isFormValid");
        var kprq = $("#sglr_kprq").val();
        var fpdm = $("#sglr_fpdm").val();
        var fphm = $("#sglr_fphm").val();
        if(kprq == ""){
            $("#sglr_kprq").focus();
            swal('开票日期不能为空!');
            return false;
        }
        if(fpdm ==null || fpdm==""){
            swal('发票代码不能为空!');
            return false;
        }
        if(fphm ==null || fphm==""){
            swal('发票号码不能为空!');
            return false;
        }
        if (r) {
           var frmData = $("#main_form1").serialize();
            $.ajax({
                url: "income/invoiceCheck", "type": "POST",  data: frmData, success: function (data) {
                    if (data.status) {
                        if(data.requery !=null && data.requery == "1"){
                            swal({
                                title: "提示",
                                text:data.msg,
                                //type: "warning",
                                showCancelButton: true,
                                closeOnConfirm: false,
                                confirmButtonText: "重新查询",
                                confirmButtonColor: "#ec6c62"
                            }, function () {
                                $('.confirm').attr('disabled', "disabled");
                                $.ajax({
                                    url: "income/requery", "type": "POST",  data: frmData,
                                }).done(function (data) {
                                    $('.confirm').removeAttr('disabled');
                                    swal(data.msg);
                                })
                            });
                            $modal.modal("close");
                            $("#bj").val('3');
                            loaddata=true;
                        }else {
                            swal(data.msg);
                            $modal.modal("close");
                            $("#bj").val('3');
                            loaddata=true;
                        }
                    } else {
                        swal(data.msg);
                    }
                }
            });
        } else {
            swal('校验不通过!');
        }
    });

    $('#smlr_info').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            // alert($("#smlr_info").val());
            var r = $("#main_form1").validator("isFormValid");
            var kprq = $("#sglr_kprq").val();
            var fpdm = $("#sglr_fpdm").val();
            var fphm = $("#sglr_fphm").val();
            if(kprq == ""){
                $("#sglr_kprq").focus();
                swal('开票日期不能为空!');
                return false;
            }
            if(fpdm ==null || fpdm==""){
                swal('请重新扫描!');
                return false;
            }
            if(fphm ==null || fphm==""){
                swal('请重新扫描!');
                return false;
            }
            if (r) {
                var frmData = $("#main_form1").serialize();
                $.ajax({
                    url: "income/invoiceCheck", "type": "POST",  data: frmData, success: function (data) {
                        if (data.status) {
                            if(data.requery !=null && data.requery == "1"){
                                swal({
                                    title: "提示",
                                    text:data.msg,
                                    //type: "warning",
                                    showCancelButton: true,
                                    closeOnConfirm: false,
                                    confirmButtonText: "重新查询",
                                    confirmButtonColor: "#ec6c62"
                                }, function () {
                                    $('.confirm').attr('disabled', "disabled");
                                    $.ajax({
                                        url: "income/requery", "type": "POST",  data: frmData,
                                    }).done(function (data) {
                                        $('.confirm').removeAttr('disabled');
                                        swal(data.msg);
                                    })
                                });
                                swal(data.msg);
                                // $modal.modal("close");
                                // $("#bj").val('3');
                                // loaddata=true;
                            }else {
                                swal(data.msg);
                                // $modal.modal("close");
                                // $("#bj").val('3');
                                // loaddata=true;
                            }
                        } else {
                            swal(data.msg);
                        }
                    }
                });
            } else {
                swal('校验不通过!');
            }
        }
    });

    var t;
    var splsh=[];
    var loaddata=false;
    var action;
    action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'income/getFpcyList'
        },
        dataTable: function () {
            var _this = this;
            t = el.$jsTable.DataTable({
                "processing" : true,
                "serverSide" : true,
                ordering : false,
                searching : false,
                "scrollX" : true,
                "ajax" : {
                    url : _this.config.getUrl,
                    type : 'POST',
                    data : function(d) {
                        if($("#bj").val()=='1'){
                            d.kprqq = $("#s_rqq").val(); // search 开票日期
                            //d.kprqz = $("#s_rqz").val(); // search 开票日期
                            d.gfsh = $('#s_xfsh').val();   // search 销方
                            d.fpdm = $('#s_fpdm').val();	// search 发票代码
                            d.fphm = $('#s_fphm').val();   // search 发票号码
                            d.fpzldm = $('#s_fplx').val();   // search
                        } else if ($("#bj").val() == '3') {
                            d.fpdm = $('#sglr_fpdm').val();	// search 发票代码
                            d.fphm = $('#sglr_fphm').val();   // search 发票号码
                        } else {
                            var csm = $('#dxcsm').val();
                            if ("fpdm" == csm) {
                                d.fpdm = $('#dxcsz').val();
                            } else if ("fphm" == csm) {
                                d.fphm = $('#dxcsz').val()
                            }
                            // d.kprqq =$("#w_kprqq").val();
                            // d.kprqz = $("#w_kprqz").val(); // search 开票日期
                        }

                        d.loaddata = loaddata;
                        splsh.splice(0, splsh.length);
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        render: function (data, type, full, meta) {
                            return '<input type="checkbox" name= "dxk" value="'
                                + data.id + '" />';
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
                            return '<a class="view"  target="_blank">' + data.fpdm + '</a>';
                        }
                    },
                    {"data": "fphm"},
                    {"data": "xfmc"},
                    {"data": "bxry","sClass":"x_bxry"},
                    {"data": "kprq"},
                    {
                        "data": function (data) {
                            if ("01" == data.fpzldm) {
                                return "增值税专用发票";
                            } else if ("04" == data.fpzldm) {
                                return "增值税普通发票";
                            } else if ("10" == data.fpzldm) {
                                return "电子发票(增普)";
                            } else if ("03" == data.fpzldm) {
                                return "机动统一发票";
                            } else if ("11" == data.fpzldm) {
                                return "卷式普通发票"
                            } else if ("20" == data.fpzldm) {
                                return "国税";
                            } else if ("30" == data.fpzldm) {
                                return "地税";
                            } else {
                                return "";
                            }
                        }
                    },
                    {
                        "data": null,
                        "render": function (data) {
                            return '<a class="showCycs"   >' + data.cycsTotal + '</a>';
                        }
                        },
                    {
                        "data": function (data) {
                            var sjly = data.sjly;
                            switch (sjly) {
                                case '01':
                                    sjly = '手工录入';
                                    break;
                                case '02':
                                    sjly = '扫码接入';
                                    break;
                                case '03':
                                    sjly = '平台导入';
                                    break;
                                case '04':
                                    sjly = '系统接入';
                                    break;
                            }
                            return sjly;
                        }
                    },
                    {
                        "data": function (data) {
                            if ("0" == data.fpzt) {
                                return "正常";
                            } else if ("1" == data.fpzt) {
                                return "作废";
                            } else if ("9" == data.fpzt) {
                                return "--";
                            } else {
                                return "";
                            }
                        }
                    },
                    {"data": "lrsj"},
                ]
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });


            /**
             * 查验次数
             */
            t.on('click', '.showCycs', function () {
                var data = t.row($(this).parents('tr')).data();
                $("#cycsid").val(data.id);
                t1.ajax.reload();
                el.$xiugai.modal({"width": 650, "height": 500});
            });
            var t1 = $('#detail_table').DataTable({
                "searching": false,
                "serverSide": true,
                "bLengthChange": false, //改变每页显示数据数量
                "sServerMethod": "POST",
                "processing": true,
                "scrollX": true,
                ordering: false,
                "ajax": {
                    url: "income/cycs",
                    type: 'POST',
                    data: function (d) {
                        d.id =$("#cycsid").val();  // search
                        d.loaddata = loaddata;
                    }
                },
                "columns": [
                    {"data": "cycs"},
                    {"data": "cyrq"},
                    {"data": "fpzt"},
                    {"data": "resultMsg","sClass" : "s_left"},
                ],

            });
            t1.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t1.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            /**
             * 发票预览
             */
            t.on('click', 'a.view', function () {
                var data = t.row($(this).parents('tr')).data();
                $("#doc-modal-fpyll").load('income/fpcyyl?id='+data.id);
                $("#doc-modal-fpyl").modal({"width": 890, "height": 530});
            });

            /**
             * 删除
             */
            $("#kpd_sc").click(function () {
                var chk_value = "";
                $('input[name="dxk"]:checked').each(function () {
                    chk_value += $(this).val() + ",";
                });
                var fpcyIds = chk_value.substring(0, chk_value.length - 1);
                if (chk_value.length == 0) {
                    swal("请至少选择一条数据");
                } else {
                    swal({
                        title: "您确认删除？",
                        type: "warning",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        confirmButtonText: "确 定",
                        confirmButtonColor: "#ec6c62"
                    }, function () {
                        $('.confirm').attr('disabled', "disabled");
                        $.ajax({
                            type: "POST",
                            url: "income/sc",
                            data: {"fpcyIds": fpcyIds},
                        }).done(function (data) {
                            $('.confirm').removeAttr('disabled');
                            swal(data.msg);
                            _this.tableEx.ajax.reload();
                        })
                    });

                }
            });

            $('#check_all').change(function () {
                if ($('#check_all').prop('checked')) {
                    splsh.splice(0, splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                        var row = t.row(i).data();
                        splsh.push(row.sqlsh);
                    });
                } else {
                    splsh.splice(0, splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);

                    });
                }
                //$("#kplsh").val(splsh.join(","));
                //kpspmx_table.ajax.reload();
            });
            //选中列查询明细
            $('#jyls_table tbody').on('click', 'tr', function () {
                var data = t.row($(this)).data();
                if ($('#check_all').prop('checked')) {
                    splsh.splice(0, splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked', false);
                    splsh.splice($.inArray(data.id, splsh), 1);
                } else {
                    $(this).find('td:eq(0) input').prop('checked', true)
                    $(this).addClass('selected');
                    splsh.push(data.id);
                }
                $('#check_all').prop('checked', false);
                //$("#kplsh").val(splsh.join(","));
                //kpspmx_table.ajax.reload();
            });
            return t;
        },

        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            $("#kp_search").on('click', function (e) {
                $("#ycform").resetForm();
                $("#bj").val('2');
                $('#xzxfq').attr("selected", "selected");
                $('#xzlxq').attr("selected", "selected");
                if ((!$("#w_kprqq").val() && $("#w_kprqz").val())
                    || ($("#w_kprqq").val() && !$("#w_kprqz").val())) {
                    // $("#alertt").html('Error,请选择开始和结束时间!');
                    //            	$("#my-alert").modal('open');
                    swal('Error,请选择开始和结束时间!');
                    return false;
                }
                //var dt1 = new Date($("#w_kprqq").val().replace(/-/g, "/"));
                //var dt2 = new Date($("#w_kprqz").val().replace(/-/g, "/"));
                //if (($("#w_kprqq").val() && $("#w_kprqz").val())) {// 都不为空
                // if (dt1.getYear() == dt2.getYear()) {
                /* if (dt1.getMonth() == dt2.getMonth()) {
                 if (dt1 - dt2 > 0) {
                 // $("#alertt").html('开始日期大于结束日期,Error!');
                 //               	$("#my-alert").modal('open');
                 swal('开始日期大于结束日期,Error!');
                 return false;
                 }
                 } else {
                 // alert('月份不同,Error!');
                 // $("#alertt").html('Error,请选择同一个年月内的时间!');
                 //               	$("#my-alert").modal('open');
                 swal('Error,选择日期不能跨月!');
                 return false;
                 }
                 } else {
                 // alert('年份不同,Error!');
                 // $("#alertt").html('Error,请选择同一个年月内的时间!');
                 //               	$("#my-alert").modal('open');
                 swal('Error,请选择同一个年月内的时间!');
                 return false;
                 }*/
                //}
                loaddata = true;
                _this.tableEx.ajax.reload();
            });
            $("#kp_search1").on('click', function (e) {
                $("#dxcsz").val("");
                $("#bj").val('1');
                //if ((!$("#s_rqq").val() && $("#s_rqz").val())
                //    || ($("#s_rqq").val() && !$("#s_rqz").val())) {
                // $("#alertt").html('Error,请选择开始和结束时间!');
                //            	$("#my-alert").modal('open');
                //   swal('Error,请选择开始和结束时间!');
                //    return false;
                // }
                // var dt1 = new Date($("#s_rqq").val().replace(/-/g, "/"));
                // var dt2 = new Date($("#s_rqz").val().replace(/-/g, "/"));
                // if (($("#s_rqq").val() && $("#s_rqz").val())) {// 都不为空
                //     if (dt1.getYear() == dt2.getYear()) {
                //         if (dt1.getMonth() == dt2.getMonth()) {
                //             if (dt1 - dt2 > 0) {
                // $("#alertt").html('开始日期大于结束日期,Error!');
                //               	$("#my-alert").modal('open');
                //                swal('开始日期大于结束日期,Error!');
                //                return false;
                //           }
                //       } else {
                // alert('月份不同,Error!');
                // $("#alertt").html('Error,请选择同一个年月内的时间!');
                //               	$("#my-alert").modal('open');
                //         swal('Error,选择日期不能跨月!');
                //           return false;
                //       }
                //   } else {
                // alert('年份不同,Error!');
                // $("#alertt").html('Error,请选择同一个年月内的时间!');
                //               	$("#my-alert").modal('open');
                //    swal('Error,请选择同一个年月内的时间!');
                //       return false;
                //   }
                // }
                loaddata = true;
                _this.tableEx.ajax.reload();
            })
        },


        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();

        }
    };
    action.init();

});



