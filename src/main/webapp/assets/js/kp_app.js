/**
 * Created by lenovo on 2015/12/14.
 */
(function ($) {
    'use strict';
    $(function () {
        $.ajax({
            url: "kp/getXfxx", context: null, success: function (data) {
                $("#qymc").val(data.xfmc);
                $("#nsrsbh").val(data.xfsh);
            }
        });
             


        var jyls_table = $('#jyls_table').DataTable({
            "searching": false,
            "serverSide": true,
            "sServerMethod": "POST",
            "processing": true,
            "scrollX": true,
            ordering: false,
            ajax: {
                "url": "kp/getjylslist?clztdm=00",
                type: 'post',
                data: function (d) {
                    d.xfsh = $('#xfsh').val();   // search 销方
                    d.gfmc = $('#gfmc').val();	// search 购方名称
                    d.ddh = $('#s_ddh').val();   // search 订单号
                    d.jylsh = $('#s_lsh').val();   // search 发票号码
                    d.rqq = $('#s_rqq').val(); // search 开票日期
                    d.rqz = $('#s_rqz').val(); // search 开票日期
                }
            },
            "columns": [
                {"data": "djh"},
                // {"data": "clztdm"},
                {"data": "jylsh"},
                {"data": "ddh"},
                {"data": "jylssj"},
                {"data": "gfsh"},
                {"data": "gfmc"},
                {"data": "gfyh"},
                {"data": "gfyhzh"},
             /*   {"data": "gflxr"},*/
                {"data": "gfdz"},
                {"data": "gfemail"},
                {"data": "gfsjh"},
                {"data": "bz"},
                {"data": function (data) {
                    if (data.jshj) {
                        return FormatFloat(data.jshj, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'},    {
                    "data": null,
                    "render": function (data) {
                        return '<a class="view" href="'
                            + data.pdfurl
                            + '" target="_blank">修改</a>     '+
                            '<a class="view" href="'
                            + data.pdfurl
                            + '" target="_blank">退回</a>'
                    }
                }
            ],
            // "columnDefs": [
            //     {
            //         "bVisible": false, "aTargets": [0]
            //     }],
            "createdRow": function (row, data, index) {
                $('td', row).eq(0).html('<input type="checkbox" data="' + data.djh + '" name="chk"/>');
            }
        });
        
        jyls_table.on('click', 'tr', function () {
              $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF");
        });

        var jyspmx_table = $('#jyspmx_table').DataTable({
            "searching": false,
            "serverSide": true,
            "sServerMethod": "POST",
            "processing": true,
            ajax: {
                "url": "kp/getjyspmxlist",
                data: function (d) {
                    d.djh = $("#djh").val();
                }
            },
            "columns": [
                {"data": "spmxxh"},
                {"data": "spmc"},
                {"data": "spggxh"},
                {"data": "spdw"},
                {"data": function (data) {
                    if (data.sps) {
                        return FormatFloat(data.sps, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'},
                {"data": function (data) {
                    if (data.spdj) {
                        return FormatFloat(data.spdj, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'},
                {"data": function (data) {
                    if (data.spje) {
                        return FormatFloat(data.spje, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'},
                {"data": function (data) {
                    if (data.spsl) {
                        return FormatFloat(data.spsl, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'},
                {"data": function (data) {
                    if (data.spse) {
                        return FormatFloat(data.spse, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'},
                {"data": function (data) {
                    if (data.jshj) {
                        return FormatFloat(data.jshj, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'}
            ]
        });

        $('#kp_search').click(function () {
        	var dt1 = new Date($('#s_rqq').val().replace(/-/g, "/"));
            var dt2 = new Date($('#s_rqz').val().replace(/-/g, "/"));
            if (($('#s_rqq').val() && $('#s_rqz').val())) {// 都不为空
                if (dt1.getYear() == dt2.getYear()) {
                    if (dt1.getMonth() == dt2.getMonth()) {
                        if (dt1 - dt2 > 0) {
                            alert('开始日期大于结束日期!');
                            return false;
                        }
                    } else {
                        alert('请选择同一个年月内的时间!');
                        return false;
                    }
                } else {
                    alert('请选择同一个年月内的时间!');
                    return false;
                }
            }
        	jyls_table.ajax.reload();
        });
        $('#jyls_table tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                jyls_table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
            var data = jyls_table.row($(this)).data();
            $("#djh").val(data.djh);
            //$("#formid").val(data.djh);
            jyspmx_table.ajax.reload();
        });
        var mxarr = [];
        var $modal = $("#my-alert-edit");
        $("#my-alert-edit").on("open.modal.amui", function () {
            $("#mx_form").validator("destroy");
            $("#main_form").validator("destroy");
            jyspmx_edit_table.clear();
            jyspmx_edit_table.draw();
        });
        $('#kp_add').click(function () {
            mxarr = [];
            $('#mx_form').resetForm();
            $('#main_form').resetForm();
            $modal.modal({"width": 800, "height": 600});
        });
        $('#check_all').change(function () {
        	if ($('#check_all').prop('checked')) {
        		jyls_table.column(0).nodes().each(function (cell, i) {
                    $(cell).find('input[type="checkbox"]').prop('checked', true);
                });
            } else {
            	jyls_table.column(0).nodes().each(function (cell, i) {
                    $(cell).find('input[type="checkbox"]').prop('checked', false);
                });
            }
        });


        $('#kp_kp').click(function () {
            var djhArr = [];
            $("input[type='checkbox']:checked").each(function (i, o) {
            	if ($(o).attr("data") != null) {
                    djhArr.push($(o).attr("data"));
				}
            });
            if (djhArr.length == 0) {
                alert("请选择需要开票的交易流水...");
                return;
            }
            $.ajax({
                url: "kp/doKp", context: document.body, data: "djhArr=" + djhArr.join(","), success: function (data) {
                    if (data.success) {
                        alert("发送到开票队列成功!");
                        jyls_table.ajax.reload();
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });


        $('#kp_all').click(function () {
            if (!confirm("您确认全部开票？")) {
				return;
			}
            $.ajax({
                url: "kp/doAllKp?clztdm=00",
                type: "post",
                data: {
                    xfsh : $('#xfsh').val(),   // search 销方
                    gfmc : $('#gfmc').val(),	// search 购方名称
                    ddh : $('#s_ddh').val(),  // search 订单号
                    jylsh : $('#s_lsh').val(),   // search 发票号码
                    rqq : $('#s_rqq').val(), // search 开票日期
                    rqz : $('#s_rqz').val() // search 开票日期
                }, 
                success: function (data) {
                    if (data.success) {
                        alert("发送到开票队列成功!");
                        jyls_table.ajax.reload();
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });
        var index = 1;

        var jyspmx_edit_table = $('#jyspmx_edit_table').DataTable({
            "searching": false,
            "bPaginate": false,
            "bSort": false,
            "scrollY": "70",
            "scrollCollapse": "true"
        });
        $('#my-alert-edit').on('open.modal.amui', function () {
//            $("#main_tab").tabs('open', 0);
        });
        $('#main_tab').find('a.ai').on('opened.tabs.amui', function (e) {
            jyspmx_edit_table.draw();
        })
        $("#addRow").click(function () {
            var r = $("#mx_form").validator("isFormValid");
            if (r) {
                var spdm = $("#spdm_edit").val();
                var mc = $("#mc_edit").val();
                var ggxh = $("#ggxh_edit").val();
                var dw = $("#dw_edit").val();
                var sl = $("#sl_edit").val();

                var dj = $("#dj_edit").val();
                var je = $("#je_edit").val();
                var sltaxrate = $("#sltaxrate_edit").val();
                var se = $("#se_edit").val();
                var jshj = $("#jshj_edit").val();
                index = mxarr.length + 1;
                jyspmx_edit_table.row.add([
                    "<span class='index'>" + index + "</span>", spdm, mc, ggxh, dw, sl, dj, je, sltaxrate, se, jshj, "<a href='#'>删除</a>"
                ]).draw();
                mxarr.push(index);
            }
        });

//        $("#sl_edit").change(function () {
//            var sl = $("#sl_edit").val();
//            var dj = $("#dj_edit").val();
//            $("#je_edit").val((sl * dj).toFixed(2));
//            var sltaxrate = $("#sltaxrate_edit").val();
//
//            $("#se_edit").val((sl * dj * sltaxrate).toFixed(2));
//            $("#jshj_edit").val($("#je_edit").val() / 1 + $("#se_edit").val() / 1);
//        });
//        $("#dj_edit").change(function () {
//            var sl = $("#sl_edit").val();
//            var dj = $("#dj_edit").val();
//            $("#je_edit").val((sl * dj).toFixed(2));
//            var sltaxrate = $("#sltaxrate_edit").val();
//
//            $("#se_edit").val((sl * dj * sltaxrate).toFixed(2));
//            $("#jshj_edit").val($("#je_edit").val() / 1 + $("#se_edit").val() / 1);
//        });

//        $("#sltaxrate_edit").change(function () {
//            var sl = $("#sl_edit").val();
//            var dj = $("#dj_edit").val();
//            $("#je_edit").val((sl * dj).toFixed(2));
//            var sltaxrate = $("#sltaxrate_edit").val();
//
//            $("#se_edit").val((sl * dj * sltaxrate).toFixed(2));
//            $("#jshj_edit").val($("#je_edit").val() / 1 + $("#se_edit").val() / 1);
//        });

        $('#jyspmx_edit_table tbody').on('click', 'a', function () {
            jyspmx_edit_table.row($(this).parents("tr")).remove().draw(false);
            mxarr.pop();
            $('#jyspmx_edit_table tbody').find("span.index").each(function (index, object) {
                $(object).html(index + 1);
            });
        });

        $("#save").click(function () {
            var r = $("#main_form").validator("isFormValid");
            if (r) {
                var ps = [];
                var d = jyspmx_edit_table.rows().data();
                if (d.length == 0) {
                    $("#main_tab").tabs('open', 1);
                    return;
                }
                ps.push("mxcount=" + d.length);
                d.each(function (data, index) {
                    $(data).each(function (i, c) {
                        if (i == 1) {
                            ps.push("spdm=" + c);
                        } else if (i == 2) {
                            ps.push("spmc=" + c);
                        } else if (i == 3) {
                            ps.push("ggxh=" + c);
                        } else if (i == 4) {
                            ps.push("dw=" + c);
                        } else if (i == 5) {
                            ps.push("sl=" + c);
                        } else if (i == 6) {
                            ps.push("dj=" + c);
                        } else if (i == 7) {
                            ps.push("je=" + c);
                        } else if (i == 8) {
                            ps.push("rate=" + c);
                        } else if (i == 9) {
                            ps.push("se=" + c);
                        } else if (i == 10) {
                            ps.push("jshj=" + c);
                        }
                    });
                });
                var frmData = $("#main_form").serialize() + "&" + ps.join("&");
                $.ajax({
                    url: "kp/save", "type": "POST", context: document.body, data: frmData, success: function (data) {
                        if (data.success) {
                            alert("保存成功!");
                            jyls_table.ajax.reload();
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            } else {
                ///如果校验不通过
                $("#main_tab").tabs('open', 0);
            }
        });
        $("#close").click(function () {
            $modal.modal("close");
        });
        //批量导入
        var $importModal = $("#bulk-import-div");
        $("#kp_dr").click(function () {
        	$('#importExcelForm').resetForm();
            $importModal.modal({"width": 600, "height": 350});
        });

        $("#close1").click(function () {
            $importModal.modal("close");
        });
        $("#close2").click(function () {
            $importConfigModal.modal("close");
//        	$importConfigModal.modal({"width": 600, "height": 480});
        });
        //导入配置
        var $importConfigModal = $("#import_config_div");
        $("#btnImportConfig").click(function () {
//            $importModal.modal("close");
            $('#importConfigForm').resetForm();
            $importConfigModal.modal({"width": 600, "height": 480});
        });
    });
})(jQuery);
