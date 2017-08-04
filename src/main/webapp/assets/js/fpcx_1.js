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
		$s_ddh : $('#s_ddh'), // search 订单号
		$s_gfmc : $('#s_gfmc'), //关键字购方名称
		$s_fpdm : $('#s_fpdm'), //关键字发票代码
		$s_fphm : $('#s_fphm'), // search 发票号码
		$s_kprqq : $('#s_kprqq'), // search 开票日期
		$s_kprqz : $('#s_kprqz'), // search 开票日期
		$s_spmc : $('#s_spmc'), // search商品名称
		$s_fpzt : $('#s_fpzt'), //关键字发票状态
		$s_fpczlx : $('#s_fpczlx'),
		$s_dyzt : $('#s_dyzt'), // search 开票日期
		$jsSearch : $('.js-search'),
		$jsSearch1 : $('#button3'),
		$jsExport : $('.js-export'),
		$jsLoading : $('.js-modal-loading'),
		$jsPrint : $('.js-print'),
		$checkAll : $('#select_all')
	};
    var loaddata2 = false;
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'fpcx/getKplsList'
		},
		dataTable : function() {
			var start = [
					{
						"orderable" : false,
						"data" : null,
						render : function(data, type, full, meta) {
							return '<input type="checkbox" id ="checkboxID" value="'
									+ data.kplsh + '" />';
						}
					}, {
						"orderable" : false,
						"data" : null,
						"defaultContent" : ""
					}, {
						"data" : "ddh"
					}, {
						"data" : "fpczlxmc"
					}, {
						"data" : "fpdm"
					}, {
						"data": null,
						"render":  function(data){
							if(data.fpzldm=="12"&&data.pdfurl!=null){
								return '<a  href="'+ data.pdfurl + '" target="_blank">'+data.fphm+'</a>';
							}else if(data.fpzldm=="12"&&data.pdfurl==null){
                                return data.fphm;
							}else if(data.fphm!=null&&data.fpzldm!="12"){
								return data.fphm;
							}else{
                               return "";
							}
						}
					}, {
						"data" : function(data) {
							if (data.fpzldm=="01") {
								return "增值税专用发票";
							} else if(data.fpzldm=="02"){
								return "增值税普通发票";
							}else if(data.fpzldm=="12"){
								return "电子发票(增普)";
							}else{
								return "";
							}
						}
					}, {
						"data" : function(data) {
							if (data.jshj) {
								return FormatFloat(data.jshj, "###,###.00");
							} else {
								return null;
							}
						},
						'sClass' : 'right'
					}, {
						"data" : "gfmc"
					}, {
						"data" : "kprq"
					}, {
                    "data" : "fpzt"
                },{
                    "data" :function (data) {
                        if(data.fpzldm =="12"){
                            return "";
                        }else {
                            return data.sfdy;
                        }
                    }
                },{
                    "data" :"errorReason"
                } ]
			$.ajax({
				url : 'zdyl/query',
				type : 'POST', //GET
				async: false,
				data : {},
				dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
				success : function(data) {
					$('#bt').append(data.bt);
					$.each(data.da, function(n, value) {
						if(value=="hjje"){
							var j = {
									"data" : function(data) {
										if (data.hjje) {
											return FormatFloat(data.hjje, "###,###.00");
										} else {
											return null;
										}
									}};
						}else if(value=="hjse"){
							var j = {
									"data" : function(data) {
										if (data.hjse) {
											return FormatFloat(data.hjse, "###,###.00");
										} else {
											return null;
										}
									}};	
						}else{
							var j = {"data":value};
						}
						start.push(j);
					});
				}
			});
			var _this = this;
			var t = el.$jsTable.DataTable({
				"processing" : true,
				"serverSide" : true,
				ordering : false,
				searching : false,
				"scrollX" : true,
				"ajax" : {
					url : _this.config.getUrl,
                    timeout: 600000,
					type : 'POST',
					data : function(d) {
						var bj = $('#bj').val();
						var txt = $('#searchtxt').val();
						var tip = $('#tip').val();
                        d.loaddata2=loaddata2;
						if (bj ==  "1") {
							if (tip == "1") {
								d.gfmc = txt; // search 订单号
							}else if (tip == "2") {
								d.ddh = txt;
							}else if (tip == "3") {
								d.spmc = txt;
							}else if (tip == "4"){
								d.xfmc = txt;
							}else if (tip == "5") {
								d.fphm = txt;
							}else if (tip == "6") {
								d.fpdm = txt;
							}
						}else if (bj ==  "2") {
							d.ddh = el.$s_ddh.val(); // search 订单号
							d.gfmc = el.$s_gfmc.val(); //购方名称
							d.fpdm = el.$s_fpdm.val(); //发票代码
							d.fphm = el.$s_fphm.val(); // search 发票号码
							d.kprqq = el.$s_kprqq.val(); // search 开票日期
							d.kprqz = el.$s_kprqz.val(); // search 开票日期
							d.spmc = el.$s_spmc.val(); //商品名称
							d.fpzt = el.$s_fpzt.val(); //发票状态
							d.fpczlx = el.$s_fpczlx.val();
							d.printflag = el.$s_dyzt.val();//打印状态
							d.xfsh=$("#mb_xfsh").val();
							d.sk =$("#mb_skp").val();
							d.fpzldm =$("#s_fpzldm").val();

						}else{
							d.ddh = "-11111111";
						}
					}
				},
				"columns" : start
			});

			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});

			});
			$("#mb_xfsh").change(function() {
			     $('#mb_skp').empty();
				 var xfsh = $(this).val();
				 if (xfsh == null || xfsh == '' || xfsh == "") {
					 return;
				 }
				 var url = "fpcx/getSkpList";
				 $.post(url, {
					 xfsh : xfsh
				 }, function(data) {
					 if (data) {
						 var option = $("<option>").text('请选择').val(-1);
						 $('#mb_skp').append(option);
						 for (var i = 0; i < data.skps.length; i++) {
							 option = $("<option>").text(data.skps[i].kpdmc).val(data.skps[i].id);
							 $('#mb_skp').append(option);
						 }
					 }
				 });
			 });
            /***发票打印*/
            $(".js-print").on('click',function() {
                var ids = '';
                var flag = true;
                t.column(0).nodes().each(
                    function(cell, i) {
                        if (flag) {
                            var $checkbox = $(cell).find('input[type="checkbox"]');
                            if ($checkbox.is(':checked')) {
                                var data =t.row(i).data();
                                if (data.fphm == null|| data.fphm == ''||data.pdfurl=='') {
                                    swal("存在正在开具或者开具失败的发票，不能批量打印！");
                                    flag = false;
                                    return false;
                                }
                                ids += $checkbox.val()+ ',';
                            }
                        }
                    });
                if (flag) {
                    if (ids != '') {
                        window.open('fpcx/printmany?ids='+ ids, '',
                            'scrollbars=yes,status=yes,left=0,top=0,menubar=yes,resizable=yes,location=yes');
                        $('#kplshStr').val(ids);
                        ids = '';
                    } else {
                        swal("请先选中至少一条记录！");
                    }
                }
            });
            //发票导出
            el.$jsExport.on('click',function (e) {
                var bj = $('#bj').val();
                var kplsh11 ='';
                t.column(0).nodes().each(//循环检测勾选复选框并取得kplsh
                    function (cell,i){
                            var $checkbox = $(cell).find('input[type="checkbox"]');
                            if($checkbox.is(':checked')){
                                kplsh11 += $checkbox.val()+ ',';
                            }
                    }
                )

                if(bj == '1'){
                        $('#kplsh1').val(kplsh11);
                        $('#searchform').submit();
                }else{
                    var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
                    var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
                    if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
                        if (dt1.getYear() == dt2.getYear()) {
                            if (dt1.getMonth() == dt2.getMonth()) {
                                if (dt1 - dt2 > 0) {
                                    swal('开始日期大于结束日期,Error!');
                                    return false;
                                }
                            } else {
                                swal('Error,请选择同一个年月内的时间!');
                                return false;
                            }
                        } else {
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    }
                        $('#kplsh1').val(kplsh11);
                        $("#searchform").submit();
				}

            })
			return t;
		},
		/**
		 * search action
		 */
		search_ac : function() {
			var _this = this;
			el.$jsSearch.on('click', function(e) {
				$('#bj').val('1');
				e.preventDefault();
                loaddata2=true;
				_this.tableEx.ajax.reload();

			});
			el.$jsSearch1.on('click', function(e) {
				$('#bj').val('2');
				if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
						|| (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
					// $("#alertt").html('Error,请选择开始和结束时间!');
     //            	$("#my-alert").modal('open');
     				swal('Error,请选择开始和结束时间!');
					return false;
				}
				var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
				var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
				if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
					if (dt1.getYear() == dt2.getYear()) {
						if (dt1.getMonth() == dt2.getMonth()) {
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
	      					swal('Error,请选择同一个年月内的时间!');
							return false;
						}
					} else {
						// alert('年份不同,Error!');
						// $("#alertt").html('Error,请选择同一个年月内的时间!');
      //               	$("#my-alert").modal('open');
      					swal('Error,请选择同一个年月内的时间!');
						return false;
					}
				}
				e.preventDefault();
                loaddata2=true;
				_this.tableEx.ajax.reload();

			});
		},
		/*/!**
		 * 导出按钮
		 *!/
		exportAc : function() {
			el.$jsExport.on('click', function(e) {
				var bj = $('#bj').val();
				if (bj == '1') {
					$('#searchform').submit();
				}else{
					var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
					var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
					if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
						if (dt1.getYear() == dt2.getYear()) {
							if (dt1.getMonth() == dt2.getMonth()) {
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
		      					swal('Error,请选择同一个年月内的时间!');
								return false;
							}
						} else {
							// alert('年份不同,Error!');
							// $("#alertt").html('Error,请选择同一个年月内的时间!');
	      //               	$("#my-alert").modal('open');
	      					swal('Error,请选择同一个年月内的时间!');
							return false;
						}
					}
					$("#searchform1").submit();
				}
				
			});
		},*/
		/**
		 * 自定义列
		 */
		autoColumn : function() {
			el.$jsAuto.on('click', function(e) {
				$.ajax({
					url : 'zdyl/query',
					type : 'POST', //GET
					async: false,
					data : {},
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {
						$.each(data.list, function(n, value) {
							var j ="#"+value.zddm;
							$(j).attr("checked",'true');
						});
					}
				})
				$('#biaoti').modal('open');
			});
		},
		/**
		 * 保存自定义列
		 */
		saveColumn : function() {
			el.$jsSubmit.on('click', function(e) {
				el.$jsSubmit.attr({"disabled":"disabled"});
				$.ajax({
					type : "POST",
					url : "zdyl/save",
					data : $('#biao').serialize(),// 你的formid
					success : function(data) {
						el.$jsSubmit.removeAttr("disabled");
						$('#biaoti').modal('close');
						// $("#alertt").html(data.msg);
      //               	$("#my-alert").modal('open');
      					swal(data.msg);
						 refresh();
					}
				});
			});
		},
		/**
		 * 自定义导出列
		 */
		autoColumn1 : function() {
			el.$jsOut.on('click', function(e) {
				$.ajax({
					url : 'zdyl/queryOut',
					type : 'POST', //GET
					async: false,
					data : {},
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data) {
						$.each(data.list, function(n, value) {
							var j ="#"+value.zddm+"1";
							$(j).attr("checked",'true');
						});
					}
				})
				$('#biaoti1').modal('open');
			});
		},
		/**
		 * 保存自定义导出列
		 */
		saveColumn1 : function() {
			el.$jsSubmit1.on('click', function(e) {
				el.$jsSubmit1.attr({"disabled":"disabled"});
				$.ajax({
					type : "POST",
					url : "zdyl/saveOut",
					data : $('#biao1').serialize(),// 你的formid
					success : function(data) {
						el.$jsSubmit1.removeAttr("disabled");
						$('#biaoti1').modal('close');
						swal(data.msg)
					}
				});
			});
		},
		//全选按钮
		checkAllAc : function() {
			var _this = this;
			el.$checkAll.on('change', function(e) {
				var $this = $(this), check = null;
				if ($(this).is(':checked')) {
					_this.tableEx.column(0).nodes().each(
							function(cell, i) {
								$(cell).find('input[type="checkbox"]').prop(
										'checked', true);
							});
				} else {
					_this.tableEx.column(0).nodes().each(
							function(cell, i) {
								$(cell).find('input[type="checkbox"]').prop(
										'checked', false);
							});
				}
			});
		},
		modalAction : function() {
			el.$jsClose.on('click', function() {
				$('#biaoti').modal('close');
			});
			el.$jsClose1.on('click', function() {
				$('#biaoti1').modal('close');
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
		/*	_this.exportAc();*/
			_this.autoColumn();
			_this.autoColumn1();
			_this.checkAllAc();
			_this.modalAction(); // hidden action
			_this.saveColumn();
			_this.saveColumn1();
		}
	};
	action.init();

});


function delcommafy(num){
	   if((num+"").trim()==""){
	      return "";
	   }
	   num=num.replace(/,/gi,'');
	   return num;
	}