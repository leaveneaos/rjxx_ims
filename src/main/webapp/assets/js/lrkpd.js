/**
 * Created by lenovo on 2017/02/11.
 */
(function ($) {
    'use strict';
    $(function () {
      /*  $.ajax({
            url: "lrkpd/getXfxx", context: null, success: function (data) {
                $("#qymc").val(data.xfmc);
                $("#nsrsbh").val(data.xfsh);
            }
        });*/
             

        var jyls_table = $('#jyls_table').DataTable({
            "searching": false,
            "serverSide": true,
            "sServerMethod": "POST",
            "processing": true,
            "scrollX": true,
            ordering: false,
            ajax: {
                "url": "lrkpd/getjyxxsqlist?clztdm=00",
                type: 'post',
                data: function (d) {
                    d.xfsh = $('#xfsh').val();   // search 销方
                    d.gfmc = $('#gfmc').val();	// search 购方名称
                    d.ddh = $('#ddh').val();   // search 订单号
                    d.spmc = $('#spmc').val();   // search 订单号
                    //d.jylsh = $('#s_lsh').val();   // search 发票号码
                    d.rqq = $('#kssj').val(); // search 开票日期
                    d.rqz = $('#jssj').val(); // search 开票日期
                }
            },
            "columns": [
                {"data": "sqlsh"},
                // {"data": "clztdm"},
                {"data": "jylsh"},
                {"data": "ddh"},
                {"data": "ddrq"},
                {"data": function(data){
                	if("01"==data.fpzldm){
                	 	return "纸质专票";
                	}else if("02"==data.fpzldm){
                	 	return "纸质普票";
                	}
                	else if("12"==data.fpzldm){
                	 	return "电子票";
                	}else{
                		return "";
                	}
               
                }},
                {"data": "gfmc"},
                {"data": "gfsh"},
                {"data": "gfdz"},
                {"data": "gfdh"},
                {"data": "gfyh"},
                {"data": "gfyhzh"},
                {"data": function (data) {
                    if (data.jshj) {
                        return FormatFloat(data.jshj, "###,###.00");
                    } else {
                        return null;
                    }
                }, 'sClass': 'right'},
                {"data": "lrry"},
                {
                    "data": null,
                    "render": function (data) {
                        return '<a href="javascript:void(0)" class="modify1" style="margin-right: 10px;">修改</a>'
                    }
                }
            ],
            // "columnDefs": [
            //     {
            //         "bVisible": false, "aTargets": [0]
            //     }],
            "createdRow": function (row, data, index) {
                $('td', row).eq(0).html('<input type="checkbox" data="' + data.sqlsh + '" name="chk"/>');
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
                "url": "lrkpd/getjymxsqlist",
                data: function (d) {
                    d.sqlsh = $("#djh").val();
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
                }, 'sClass': 'right'},
                {
                    "data": null,
                    "render": function (data) {
                        return '<a href="#" class="modify2" style="margin-right: 10px;">修改</a>'
                    }
                }
            ]
        });

        $('#kp_search').click(function () {
        	var dt1 = new Date($('#kssj').val().replace(/-/g, "/"));
            var dt2 = new Date($('#jssj').val().replace(/-/g, "/"));
            if (($('#kssj').val() && $('#jssj').val())) {// 都不为空
                if (dt1.getYear() == dt2.getYear()) {
                    if (dt1.getMonth() == dt2.getMonth()) {
                        if (dt1 - dt2 > 0) {
                            alert('开始日期大于结束日期!');
                            return false;
                        }
                    } /*else {
                        alert('请选择同一个年月内的时间!');
                        return false;
                    }*/
                }/* else {
                    alert('请选择同一个年月内的时间!');
                    return false;
                }*/
            }
        	//alert("yt");
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
            $("#djh").val(data.sqlsh);
            //alert(data.sqlsh);
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
                alert("请选择需要提交开票申请的交易流水...");
                return;
            }
            $.ajax({
                url: "lrkpd/sqKp", context: document.body, data: "djhArr=" + djhArr.join(","), success: function (data) {
                    if (data.success) {
                        alert("已提交开票申请!");
                        jyls_table.ajax.reload();
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });
        //修改主表 begin
        jyls_table.on('click', 'a.modify1', function() {
        	var row = jyls_table.row($(this).parents('tr')).data();
        	$('#my-alert-modify').modal({"width": 800, "height": 500});
        	//$('#my-alert-modify').modal('open');
			$.ajax({
				url : 'kpdsh/xgkpd',
				data : {
					"sqlsh" : row.sqlsh,
				},
				success : function(data) {
					if (null!=data&&""!=data) {
						var jy= data.jyxx
						$('#select_xfid_modify').val(jy.xfsh);
						$.ajax({
							url:"kp/getSkpList",
							async:false,
							data:{
							"xfsh" : jy.xfsh
						}, success:function(data) {
							if (data) {
								var option;
								$('#select_skpid_modify').html("");
								for (var i = 0; i < data.skps.length; i++) {
									option+= "<option value='"+data.skps[i].id+"'>"+data.skps[i].kpdmc+"</option>";
									
								}
								$('#select_skpid_modify').append(option);

							}
						}});
						$('#select_skpid_modify').val(jy.skpid);
						$('#ddh_modify').val(jy.ddh);
						$('#gfsh_modify').val(jy.gfsh);
						$('#gfmc_modify').val(jy.gfmc);
						$('#gfyh_modify').val(jy.gfyh);
						$('#gfzh_modify').val(jy.gfyhzh);
						$('#gflxr_modify').val(jy.gflxr);
						$('#gfemail_modify').val(jy.gfemail);
						$('#gfsjh_modify').val(jy.gfsjh);
						$('#gfdz_modify').val(jy.gfdz);
						$('#gfdh_modify').val(jy.gfdh);
						$('#tqm_modify').val(jy.tqm);
						$('#gfbz_modify').val(jy.bz);
						$('#formid_modify').val(jy.sqlsh);
						$('#fpzl_modify').val(jy.fpzldm);
						hidespan3(jy.fpzldm);//为了设置当选择为专票时，其他几项购方信息为必录
					} else {
						alert(data.msg);
					}
				},
				error : function() {
					alert("出现错误，请稍后重试！");
				}
			})
        });
        
        $("#kpd_xgbc").on('click', function() {
        	 var r = $("#main_form_modify").validator("isFormValid");
             if (r) {
			 $.ajax({
                 url: "kpdsh/xgbckpd", 
                 "type": "POST",
                 data:{ 
                	sqlsh   : $('#formid_modify').val(),
                	skpid   : $('#select_skpid_modify').val(),
                	ddh	    : $('#ddh_modify').val(),
					gfsh	: $('#gfsh_modify').val(),
					gfmc 	: $('#gfmc_modify').val(),
					gfyh	: $('#gfyh_modify').val(),
					gfyhzh	: $('#gfzh_modify').val(),
					gflxr	: $('#gflxr_modify').val(),
					gfemail : $('#gfemail_modify').val(),
					gfsjh	: $('#gfsjh_modify').val(),
					gfdz 	: $('#gfdz_modify').val(),
					gfdh	: $('#gfdh_modify').val(),
					tqm		: $('#tqm_modify').val(),
					bz		: $('#gfbz_modify').val(),
					fpzldm  : $('#fpzl_modify').val(),
					xfsh	:$('#select_xfid_modify').val(),
                	 },
                 //data : $('#main_form_modify').serialize(),
                 success : function(data) {
					if(data.msg){
						alert("修改成功!");
						$('#my-alert-modify').modal('close');
						 jyls_table.ajax.reload();
					}
				}
             });
        	//alert($('#main_form_modify').serialize());
             }
		});
        //修改主表 end

        //修改明细数据 begin
        jyspmx_table.on('click', 'a.modify2', function () {
        	var row = jyspmx_table.row($(this).parents('tr')).data();
        	$('#my-alert-edit1').modal('open');
        	$('#mx_spmc').val(row.spmc);
        	$('#mx_ggxh').val(row.spggxh);
        	$('#mx_spdw').val(row.spdw);
        	$('#mx_spsl').val(row.sps);
        	$('#mx_spdj').val(row.spdj);
        	$('#mx_spje').val(row.spje);
        	$('#mx_sl').val(row.spsl);
        	$('#mx_spse').val(row.spse);
        	$('#mx_jshj').val(row.jshj);
        	$('#formid1').val(row.id);
        });
        
        $("#kpdmx_xgbc").click(function () {
            var r = $("#main_form1").validator("isFormValid");
            if (r) {
            	$('#mx_spse1').val($('#mx_spse').val());
                var frmData = $("#main_form1").serialize();
                $.ajax({
                    url: "kpdsh/xgbcmx", "type": "POST", context: document.body, data: frmData, success: function (data) {
                        if (data.msg) {
                            alert("保存成功!");
							$('#my-alert-edit1').modal('close');
                            jyls_table.ajax.reload();
                            jyspmx_table.ajax.reload();
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
        //修改明细数据 end
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
                    url: "lrkpd/save", "type": "POST", context: document.body, data: frmData, success: function (data) {
                        if (data.success) {
                            alert("保存成功!");
                            $modal.modal("close");
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
        $("#mxclose").on('click', function() {
			$('#my-alert-edit1').modal('close');
		});
        $("#close2").click(function () {
            $importConfigModal.modal("close");
//        	$importConfigModal.modal({"width": 600, "height": 480});
        });
        $("#close3").click(function () {
        	$('#my-alert-modify').modal('close');
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

	
	$("a").on('click',function() {
		var date =  $(this).attr("id")
		//alert( $(this).attr("id"));
		if(date=='day3'){
			var k =getToday();
			var j = getThreeday(2);
			$("#kssj").val(j);
			$("#jssj").val(k);
		}else if(date=='day7'){
			var k =getToday();
			var j = getThreeday(6);
			$("#kssj").val(j);
			$("#jssj").val(k);
		}else if(date=='day30'){
			var k =getToday();
			var j = getThreeday(29);
			$("#kssj").val(j);
			$("#jssj").val(k);
		}
	});

	
	//当前日期
	function getToday() {
		var date = new Date();
		var strYear = date.getFullYear();
		var strDay = date.getDate();
		var strMonth = date.getMonth() + 1;
		if (strMonth < 10) {
			strMonth = "0" + strMonth;
		}
		datastr = strYear + "-" + strMonth + "-" + strDay;
		return datastr;
	}
	//获取最近n天
	function getThreeday(day) {
		var date = new Date();
		var threeday_milliseconds = date.getTime() - 1000 * 60 * 60 * 24 * day;
		var threeday = new Date();
		threeday.setTime(threeday_milliseconds);
		var strYear = threeday.getFullYear();
		var strDay = threeday.getDate();
		var strMonth = threeday.getMonth() + 1;
		if (strMonth < 10) {
			strMonth = "0" + strMonth;
		}
		datastr = strYear + "-" + strMonth + "-" + strDay;
		return datastr;
	}