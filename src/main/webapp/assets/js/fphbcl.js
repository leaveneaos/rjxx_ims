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
    $(this).removeData('amui.modal');
	 var mxarr = [];
	 var $modal = $("#my-alert-edit2");
	 var $tab = $('#doc-tab-demo-1');

	 //确定合并按钮点击事件
    $("#yhqrbc").click(function(){
    	$("#yhqrbc").attr('disabled',"true");
    	var sqlshs;
        var sqkp=[];
        //sqlsh
        var dataTable = $('#mxTable3').DataTable();
        var info = dataTable.page.info();
        var dataRows = info.recordsTotal;
        for(var i=0;i<dataRows;i++){
            sqkp.push($('#mxTable3').DataTable().rows(i).data()[0].sqlsh)
        }
        sqlshs=sqkp.join(",");
		$.ajax({
			type : "POST",
			url : "fphbcl/fphbSave",
			data : {"sqlshs":sqlshs},
			success : function(data) {
				if(data.msg){
				$('#yhqrbc').removeAttr("disabled");
                swal(data.msg);
            	$("#cljgbt").hide();
            	$tab.tabs('refresh');
            	$tab.tabs('open', 0);
            	t.ajax.reload();
				}else{
					 swal("所选数据无法进行整数分票，请调整分票规则！");
					 $('#yhqrbc').removeAttr("disabled");
				}
			}
		});
    	});
    //取消按钮点击事件
    $("#yhqx").click(function(){
        var sqlshs;
        var sqkp=[];
        //sqlsh
        var dataTable = $('#mxTable3').DataTable();
        var info = dataTable.page.info();
        var dataRows = info.recordsTotal;
        for(var i=0;i<dataRows;i++){
            sqkp.push($('#mxTable3').DataTable().rows(i).data()[0].sqlsh)
        }
        sqlshs=sqkp.join(",");
        $.ajax({
            type : "POST",
            url : "/fphbcl/fphbCancle",
            data : {"sqlshs":sqlshs},
            success : function(data) {
                if(data.msg){
                    swal(data.msg);
                }else{
                    swal("无法取消");
                    $('#yhqx').removeAttr("disabled");
                }
            }
        });

    	$("#cljgbt").hide();
    	$tab.tabs('refresh');
    	$tab.tabs('open', 0);
    	});

    var t;
    var splsh=[];
    var kpspmx_table3;
    var loaddata=false;
	var action = {
		tableEx : null, // cache dataTable
		config : {
			getUrl : 'fphbcl/getItems'
		},//jyls_table el.$jsTable
		dataTable : function() {
			var _this = this;
			t = $("#jyls_table").DataTable({
				"processing" : true,
				"serverSide" : true,
				ordering : false,
				searching : false,
				"scrollX" : true,
				"ajax" : {
					url : _this.config.getUrl,
					type : 'POST',
					data : function(d) {
					    /*if($("#bj").val()=='1'){*/
                            d.kprqq = $("#w_kprqq").val(); // search 开票日期
                            d.kprqz = $("#w_kprqz").val(); // search 开票日期
                            d.ddh = $('#ddh').val();
						 d.loaddata=loaddata;
                        splsh.splice(0,splsh.length);
					}
				},
	            "columns": [
	                        	{
	                        		"orderable" : false,
	                        		"data" : null,
	                        		render : function(data, type, full, meta) {
	                        			return '<input type="checkbox" name= "dxk" value="'
	                        				+ data.sqlshs + '" />';
	                        		}
	                        	},
	                        {
	    	                    "orderable": false,
	    	                    "data": null,
	    	                    "defaultContent": ""
	                        },
	                        {
	                            "data": "sqlshs",
                                "visible": false,
                                "searchable": false
                            },
	                        {"data": "countTotal"},
	                        {"data": "jshjSum"}

	                    ]
			});
		    kpspmx_table3 = $('#mxTable3').DataTable({
                "processing" : true,
                "serverSide" : true,
                ordering : false,
                searching : false,
                "scrollX" : true,
		        ajax: {
		            url: "fphbcl/fphb",
		            async:false,
		            data: function (d) {
		        		var gfmc=$("#gfmc").val();
						var gfsh = $("#gfsh").val();
						var gfdz = $("#gfdz").val();
						var gfdh = $("#gfdh").val();
                        var gfyh = $("#gfyh").val();
                        var yhzh = $("#yhzh").val();
						d.gfdz= gfdz;
						d.yhzh = yhzh;
						d.gfmc= gfmc;
						d.gfsh=gfsh;
                        d.gfdh= gfdh;
                        d.gfyh=gfyh;
                        var sqlshs ="";
                        var sqkp=[];
                         $('input[name="dxk"]:checked').each(function(cell,i){
                            var row3 = $(this).parents('tr').find('input[name="dxk"]');
                             sqkp.push(row3.val());
                         });
                        sqlshs=sqkp.join(",");
                        d.sqlshs=sqlshs;
		            }
		        },
		        "columns": [
                    {
                        "data": null,
                        "bSortable" : false,
                        "defaultContent": "",
                        "targets"    : 0

                    },
		            {"data": "xfsh"},
		            {"data": "xfmc"},
		            {"data": "gfmc"},
		            {"data": "gfsh"},
		            {"data": "gfdz"},
		            {"data": "gfdh"},
		            {"data": "jshj"}
		        ],
                "fnDrawCallback" : function(){
                    this.api().column(0).nodes().each(function(cell, i) {
                        cell.innerHTML =  i + 1;
                    });
                },
		    });
			t.on('draw.dt', function(e, settings, json) {
				var x = t, page = x.page.info().start; // 设置第几页
				t.column(1).nodes().each(function(cell, i) {
					cell.innerHTML = page + i + 1;
				});
			});

            t.on('click', 'a.modify', function () {
            	var row = t.row($(this).parents('tr')).data();
				$.ajax({
					url : 'kpdsh/xgkpd',
					data : {
						"sqlsh" : row.sqlsh,
					},
					success : function(data) {
						if (null!=data&&""!=data) {
							var jy= data.jyxx
							$('#select_xfid').val(jy.xfsh);
							$.ajax({
								url:"kp/getSkpList",
								async:false,
								data:{
								"xfsh" : jy.xfsh
							}, success:function(data) {
								if (data) {
									var option;
									$('#select_skpid').html("");
									for (var i = 0; i < data.skps.length; i++) {
										option+= "<option value='"+data.skps[i].id+"'>"+data.skps[i].kpdmc+"</option>";
									}
									$('#select_skpid').append(option);
								}
							}});
							$('#select_skpid').val(jy.skpid);
							$('#ddh_edit').val(jy.ddh);
							$('#ddh_fplx').val(jy.fpzldm);
							$('#gfdh_edit').val(jy.gfdh);
							$('#gfsh_edit').val(jy.gfsh);
							$('#gfmc_edit').val(jy.gfmc);
							$('#gfyh_edit').val(jy.gfyh);
							$('#gfyhzh_edit').val(jy.gfyhzh);
							$('#gflxr_edit').val(jy.gflxr);
							$('#gfemail_edit').val(jy.gfemail);
							$('#gfsjh_edit').val(jy.gfsjh);
							$('#gfdz_edit').val(jy.gfdz);
							$('#tqm').val(jy.tqm);
							$('#bz').val(jy.bz);
							$('#formid').val(jy.sqlsh);
						} else {
                            swal(data.msg);
						}
					},
					error : function() {
                        swal("出现错误,请稍后再试");
					}
				});
            	$('#my-alert-edit').modal({"width": 800, "height": 450});
            });

            //删除
            $("#kpd_sc").click(function () {
               
        		var chk_value="" ;
        		$('input[name="dxk"]:checked').each(function(){
        		chk_value+=$(this).val()+",";
        		});
        		var ddhs = chk_value.substring(0, chk_value.length-1);
        		if(chk_value.length==0){

                    swal("请至少选择一条数据");
        		}else{
                    swal({
                        title: "您确认删除？",
                        type: "warning",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        confirmButtonText: "确 定",
                        confirmButtonColor: "#ec6c62"
                    }, function() {
                        $('.confirm').attr('disabled',"disabled");
                        $.ajax({
                            type : "POST",
                            url : "kpdsh/sc",
                            data : {"ddhs":ddhs},
                        }).done(function(data) {
                            $('.confirm').removeAttr('disabled');
                            swal(data.msg);
                            _this.tableEx.ajax.reload();
                        })
                    });
                    
        		}
            });

            $('#check_all').change(function () {
            	if ($('#check_all').prop('checked')) {
                    splsh.splice(0,splsh.length);
            		t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                        var row =t.row(i).data();
                        splsh.push(row.sqlsh);
                    });
                } else {
                    splsh.splice(0,splsh.length);
                	t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);

                    });
                }
                  $("#kplsh").val(splsh.join(","));
            });

            //选中列查询明细
            $('#jyls_table tbody').on('click', 'tr', function () {
                var data = t.row($(this)).data();
                if ($('#check_all').prop('checked')){
                    splsh.splice(0,splsh.length);
                    t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
				}
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked',false);
                    splsh.splice($.inArray(data.sqlsh, splsh), 1);
                } else {
                    $(this).find('td:eq(0) input').prop('checked',true)
                    $(this).addClass('selected');
                    splsh.push(data.sqlsh);
                }
                $('#check_all').prop('checked',false);
                $("#kplsh").val(splsh.join(","));
            });
			return t;
		},
		/**
		 * search action
		 */
		search_ac : function() {
			var _this = this;
			$("#kp_search").on('click', function(e) {
                loaddata=true;
				_this.tableEx.ajax.reload();
			});
			$("#kp_search1").on('click', function(e) {
				$("#dxcsz").val("");
                $("#bj").val('1');
                if ((!$("#s_rqq").val() && $("#s_rqz").val())
                    || ($("#s_rqq").val() && !$("#s_rqz").val())) {
                    // $("#alertt").html('Error,请选择开始和结束时间!');
                    //            	$("#my-alert").modal('open');
                    swal('Error,请选择开始和结束时间!');
                    return false;
                }
                var dt1 = new Date($("#s_rqq").val().replace(/-/g, "/"));
                var dt2 = new Date($("#s_rqz").val().replace(/-/g, "/"));
                if (($("#s_rqq").val() && $("#s_rqz").val())) {// 都不为空
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
                            swal('Error,选择日期不能跨月!');
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
                loaddata=true;
				_this.tableEx.ajax.reload();
			})
			},

		/**
		 * kp
		 */
		kp : function() {
			var _this = this;
			$("#kpd_kp").on('click', function(e) {
				var chk_value="" ;
				var fpxes = "";
				var fla = true;
                var gfmc=$("#gfmc").val();
                var gfsh=$("#gfsh").val();
                if ($("#sfbx").is(':checked')) {
                    if(gfsh==""){
                        $("#gfsh").focus();
                        swal("公司购方税号不能为空！");
                        return;
                    }
                }
                if(gfmc==""){
                    $("#gfsh").focus();
                    swal("公司购方名称不能为空！");
                    return;
                }
				var els =document.getElementsByName("fpje");
				for(var i=0;i<els.length;i++){
					var fpje = els[i].value.replace(/,/g,'');
					if(fpje==0){
                        swal("第 "+(i+1)+"行分票金额为0,请重新填写或维护开票限额");
	                	fla=false;
	    				return false;
					}
					if(!fpje.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                        swal("第 "+(i+1)+"行分票金额格式有误，请重新填写！");
	                	fla=false;
	    				return false;
        			}
				}
				if($("input[name='dxk']:checked").length==1){
					var bckpje = [];
					var je=0;
					var rows1 = $("#mxTable1").find('tr');	
		            var els1 =document.getElementsByName("bckpje");
					for(var i=0;i<els1.length;i++){
						var fpp = els1[i].value.replace(/,/g,'');
						je+=fpp*1;
						bckpje.push(fpp); 
						if(fpp==0){
						}else if(!fpp.match("^(([1-9]+)|([0-9]+\.[0-9]{0,2}))$")){
                            swal("第 "+(i+1)+"行明细金额格式有误，请重新填写！");
		    				return false;
		    			}else if(Number(fpp)>Number(delcommafy(rows1[i+1].cells[3].innerHTML))){
                            swal("第"+(i+1)+"条明细的本次开票金额不能大于可开票金额！");
		                	return false;
		    			}
					}
					/*if(je==0){
                        swal("本次开具金额为0,请重新填写");
	                	return false;
					}*/
				}
				$('input[name="dxk"]:checked').each(function(){
				chk_value+=$(this).val()+",";
				var row = $(this).parents('tr').find('input[name="fpje"]');
				fpxes+=row.fpje+","
				});
		
				var ddhsthan = chk_value.substring(0, chk_value.length-1);
				fpxes = fpxes.substring(0, fpxes.length-1);
				if(chk_value.length==0){
                    swal("请至少选择一条数据");
				}else{
					if(!fla){
						return;
					}
        		     if (!confirm("您确认处理该记录？")) {
        				return;
        			}
                    $("#cljg").click();
	            	$("#cljg").show();
	            	$("#cljgbt").show();
	            	  $tab.tabs('refresh');
					kpspmx_table3.ajax.reload();
					$('#doc-tab-demo-1').tabs('open', 1)
				}
			});
		},
		/**
		 * 修改保存
		 */
		xgbc : function() {
			var _this = this;

			$("#kpd_xgbc").on('click', function(e) {
                $('.confirm').attr('disabled',"disabled");
				 var r = $("#main_form").validator("isFormValid");
		          if (r) {
				$.ajax({
					type : "POST",
					url : "kpdsh/xgbckpd",
					data : $('#main_form').serialize(),
					success : function(data) {
						if(data.msg){
                            $('.confirm').removeAttr('disabled');
                            swal("修改成功");
							$('#my-alert-edit').modal('close')
							_this.tableEx.ajax.reload();	
						}
					}
				});
		          }else{
		          }
			});
	         
		},
		/**
		 * 修改保存mx
		 */
		xgbcmx : function() {
			var _this = this;
			$("#kpdmx_xgbc").on('click', function(e) {
            $('.confirm').attr('disabled',"disabled");          
		var r = $("#main_form1").validator("isFormValid");
		if (r) {
				$('#mx_spse1').val($('#mx_spse').val());
				$.ajax({
					type : "POST",
					url : "kpdsh/xgbcmx",
					data : $('#main_form1').serialize(),
					success : function(data) {
						if(data.msg){
                            $('.confirm').removeAttr('disabled');
                            swal("修改成功");
							$('#my-alert-edit1').modal('close');
							_this.tableEx.ajax.reload();
						}
					}
				});
		            }else{

			          }
			});
	           
		},
		modalAction : function() {
			$("#close").on('click', function() {
				$('#my-alert-edit').modal('close');
			});
            $("#xwclose1").on('click', function() {
                $('#bulk-xwimport-div').modal('close');
            });
			$("#mxclose").on('click', function() {
				$('#my-alert-edit1').modal('close');
			});
			$("#kp_hbkp").on('click', function() {
				var chk_value="" ;
				$('input[name="dxk"]:checked').each(function(){
				chk_value+=$(this).val()+",";
				});
				var ddhs = chk_value.substring(0, chk_value.length-1);
				if(chk_value.length<2){
					swal("请至少选择2条数据!")
				}else{
					
				}
			});
		},
		init : function() {
			var _this = this;
			_this.tableEx = _this.dataTable(); // cache variable
			_this.search_ac();
			_this.kp();
			_this.xgbc();
			_this.xgbcmx();
			_this.modalAction(); // hidden action
		}
	};
	action.init();

    $('#cz').on('click',function() {
        $("input").val('');
        $("textarea").val('');
        var SelectArr = $("select");
        for (var i = 0; i < SelectArr.length; i++) {
            SelectArr[i].options[0].selected = true;
            $("#kpd").val('');
        }
    })

});

function delcommafy(num){
	   if((num+"").trim()==""){
	      return "";
	   }
	   num=num.replace(/,/gi,'');
	   return num;
	}
