/**
 * Created by Administrator on 2018-01-30.
 */


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
        $checkAll : $('#select_all')
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
        $("#main_form").validator("destroy");
    });

    $("#lrclose").click(function () {
        $modal.modal("close");
    });
    var index = 1;


    var t;
    var splsh=[];
    var loaddata=false;
    var action;
    action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'qrgx/getJxfpxxList'
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
                            d.gfsh = $('#s_gfsh').val();   // search 销方
                            d.fpdm = $('#s_fpdm').val();	// search 发票代码
                            d.fphm = $('#s_fphm').val();   // search 发票号码
                            d.fpzldm = $('#s_fplx').val();   // search
                        } else if ($("#bj").val() == '3') {
                            d.gfsh = $('#zplr_sh').val();   // search 发票号码
                        } else {
                            var csm = $('#dxcsm').val();
                            if ("fpdm" == csm) {
                                d.fpdm = $('#dxcsz').val();
                            } else if ("fphm" == csm) {
                                d.fphm = $('#dxcsz').val()
                            }else if("gfsh" == csm){
                                d.gfsh = $('#dxcsz').val()
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
                                + data.fplsh + '" />';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {"data": "gfmc"},
                    {
                        "data": null,
                        "render": function (data) {
                            return '<a class="view"  target="_blank">' + data.fpdm + '</a>';
                        }
                    },
                    {"data": "fphm"},
                    {"data": "kprq"},
                    {"data": "xfmc"},
                    {"data": "hjje"},
                    {"data": "hjse"},
                    {"data": "jshj"},
                    {"data": "fpdm"},
                    {"data": "rzsj"},
                ]
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            //查看发票预览
            t.on('click', 'a.view', function () {
                var data = t.row($(this).parents('tr')).data();
                $("#doc-modal-fpyll").load('fpcj/fpcjyl?fplsh='+data.fplsh);
                $("#doc-modal-fpyl").modal("open");
            });
            //勾选认证
            $('#gxrz_qr').click(function () {
                var chk_value = "";
                $('input[name="dxk"]:checked').each(function () {
                    chk_value += $(this).val() + ",";
                });
                if (chk_value.length == 0) {
                    swal("请至少选择一条数据");
                    return false;
                }
                //修改勾选状态 为1：已勾选
                /*$.ajax({
                    type : "POST",
                    url : "fpcj/jxfpxxGx",
                    data : {"fplshs":chk_value},
                    success : function(data) {
                        if(data.status){
                            //页面跳转
                            var url ="/qrgx";
                            $.ajax({
                                url:'mainjsp/getName',
                                data:{'url':url},
                                method:'POST',
                                success:function(data){
                                    var id = data.name;
                                    $("#"+id,parent.document).css('display','block');
                                }
                            })
                            var v_id = '/qrgx';
                            $(".ejcd",parent.document).css('background','none');
                            var divs = $('.ejcd',parent.document);
                            for(var i=0;i<divs.length;i++){
                                if($(divs[i]).attr('data')==v_id){
                                    $(divs[i]).css("background-color","#f2f6f9");
                                    $("#cd1",parent.document).val($(divs[i]).attr("dele"));
                                    $("#cd2",parent.document).val($(divs[i]).attr("parname"));
                                }
                            }
                            $("#mainFrame",parent.document).attr("src",v_id);
                        }else {
                            swal("勾选失败，请联系开发人员");
                        }
                    }
                });*/
            });
            //取消勾选
            $("#gxrz_qx").click(function () {
                var chk_value = "";
                $('input[name="dxk"]:checked').each(function () {
                    chk_value += $(this).val() + ",";
                });
                var fplshs = chk_value.substring(0, chk_value.length - 1);
                if (chk_value.length == 0) {
                    swal("请至少选择一条数据！");
                } else {
                    swal({
                        title: "您确认取消勾选吗？",
                        type: "warning",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        confirmButtonText: "确 定",
                        confirmButtonColor: "#ec6c62"
                    }, function () {
                        $('.confirm').attr('disabled', "disabled");
                        $.ajax({
                            type: "POST",
                            url: "qrgx/qx",
                            data: {"fplshs": fplshs},
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
            });

            //选中列查询明细
            $('#fpcj_table tbody').on('click', 'tr', function () {
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
        /**
         * 修改保存
         */
        xgbc : function() {
            var _this = this;

            $("#jxfpxx_xgbc").on('click', function(e) {
                $('.confirm').attr('disabled',"disabled");
                var r = $("#main_form").validator("isFormValid");
                alert($('#main_form').serialize());
                if (r) {
                    $.ajax({
                        type : "POST",
                        url : "fpcj/xgjxfpxx",
                        data : $('#main_form').serialize(),
                        success : function(data) {
                            if(data.msg){
                                $('.confirm').removeAttr('disabled');
                                swal("修改成功");
                                $('#my-alert-edit').modal('close')
                                _this.tableEx.ajax.reload();
                            }else {
                                swal("修改失败");
                            }
                        }
                    });
                }else{
                    swal("修改失败");
                }
            });

        },
        modalAction: function () {
            $("#close").on('click', function () {
                $('#my-alert-edit').modal('close');
            });
            $("#mxclose").on('click', function () {
                $('#my-alert-edit1').modal('close');
            });
            $("#kp_hbkp").on('click', function () {
                var chk_value = "";
                $('input[name="dxk"]:checked').each(function () {
                    chk_value += $(this).val() + ",";
                });
                var ddhs = chk_value.substring(0, chk_value.length - 1);
                if (chk_value.length < 2) {
                    swal("请至少选择2条数据!")
                } else {

                }
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.search_ac();
            _this.xgbc();
            _this.modalAction(); // hidden action
        }
    };
    action.init();

});




