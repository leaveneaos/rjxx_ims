$(function () {
    "use strict";
    var ur;
    var el = {
        $jsTable: $('#gfxx_table'),
        $modalHongchong: $('#hongchong'),
        $jsClose: $('#close1'),
        $jsForm0: $('.js-form-0'),     // 红冲 form
        $jsAdd: $('#gf_add'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading'),
        $jsSave: $('#save'),
        $xiugai: $('#xiugai'),
        $jsUpdate: $('#update'),
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'gfqymp/getGfxxList',
            addUrl: 'gfqymp/saveGfxx',
            scUrl:'gfqymp/delete',
            updateUrl:'gfqymp/update'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                "scrollX": true,
                searching: false,
                  "ajax": {
                    url: _this.config.getUrl,
                    type: 'POST',
                    data: function (d) {
                    	d.gfmc = $("#s_gfmc").val(); // search 
                        d.nsrsbh = $("#s_nsrsbh").val(); // search 
                        
                        var csm =  $('#dxcsm').val()
                        if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){ //购方名称
                      	  d.gfmc = $('#dxcsz').val()
                      }else if("nsrsbh"==csm&&(d.nsrsbh==null||d.nsrsbh=="")){//订单号
                      	  d.nsrsbh = $('#dxcsz').val()
                      }
                    }
                },
              "columns": [
                    /*{
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },*/
                    {
	                        		"orderable" : false,
	                        		"data" : null,
	                        		render : function(data, type, full, meta) {
	                        			return '<input type="checkbox" name= "chk" data="'
	                        				+ data.id + '" />';
	                        		}
	                        	},

                    {"data": "gfmc"},
                    {"data": "gfsh"},
                    {"data": "gfdz"},
                    {"data": "gfdh"},
                    {"data": "gfyh"},
                    {"data": "gfyhzh"},
                    {"data": "lxr"},
                    {"data": "lxdh"},
                    {"data": "yjdz"},
                    {"data": "email"}
                    
                ],
                /*"createdRow": function (row, data, index) {
                    $('td', row).eq(0).html('<input type="checkbox" data="' + data.id + '" name="chk"/>');
                }*/
            });
            
            $('#check_all').change(function () {
            	if ($('#check_all').prop('checked')) {
            		t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                    });
                } else {
                	t.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
            /*    t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });*/

            });
         // 删除
			t.on('click', 'a.del', function() {
				var da = t.row($(this).parents('tr')).data();
				if (confirm("确定要删除该条信息吗")) {
					_this.sc(da);
					//alert(da.id);
				}
				_this.resetForm();
			});
			
		    //删除
	        $("#gf_del").click(function () {
	            var djhArr = [];
	            $("input[type='checkbox']:checked").each(function (i, o) {
	            	if ($(o).attr("data") != null) {
	                    djhArr.push($(o).attr("data"));
					}
	            });
	            if (djhArr.length == 0) {
	                swal("请选择需要删除的购方信息...");
	                return;
	            }
	            //alert(djhArr);
	            if (confirm("您确认删除？")) {
	                $.post("gfqymp/doDel",
							"djhArr="+ djhArr.join(","),
							function(res) {
								if (res) {
									alert("删除成功");
									window.location.reload(true);
								}
					});
				}
			});
	        
	        
	        //修改
	        $("#gf_xg").click(function () {
	            var djhArr = [];
	            $("input[type='checkbox']:checked").each(function (i, o) {
	            	if ($(o).attr("data") != null) {
	                    djhArr.push($(o).attr("data"));
					}
	            });
	            if (djhArr.length == 0) {
	                swal("请选择需要修改的购方信息...");
	                return;
	            }else if(djhArr.length >=2){
	            	swal("每次只能修改一条数据...");
	                return;
	            }
	            //alert(djhArr);
	            var ipts = t.row($("input[type='checkbox']:checked").parents("tr")).data();
	            _this.setForm0(ipts);
	            el.$xiugai.modal({"width": 600, "height": 500});
                el.$xiugai.modal('open');
			});
			
			 // 修改
            t.on('click', 'a.modify', function () {
                var data = t.row($(this).parents('tr')).data();
                // todo
                _this.setForm0(data);
                el.$xiugai.modal({"width": 600, "height": 500});
                el.$xiugai.modal('open');
            });
            
         // 界面新增按钮
			el.$jsAdd.on('click', el.$jsAdd, function() {
				_this.resetForm();
				ur = _this.config.addUrl;
				//alert("新增");
				el.$modalHongchong.modal({"width": 600, "height": 500});
			});	
			
			// 修改数据保存按钮
			el.$jsUpdate.on('click', el.$jsUpdate, function() {
				ur = _this.config.updateUrl;
				var t = _this.update();
				/*if(t==true){
					_this.resetForm();
				}*/
				
			});	
			 $('#search').click(function () {
	             	$("#ycform").resetForm();
	              	t.ajax.reload();
	             });
	             $('#search1').click(function () {
	             	$("#dxcsz").val("");
	             	t.ajax.reload();
	             });
            return t;
        },
        
        /**
		 * 更新
		 */
		update : function() {
			var _this = this;
			if (null ==$('#xg_gfmc').val()|| $('#xg_gfmc').val()=='') {
            	swal('企业名称不能为空！');
                //el.$jsLoading.modal('close'); 
                return false;
			}
			$.ajax({
				url : ur,
				data : {
					 gfid : $('#gfid').val(), 
					 gfmc : $('#xg_gfmc').val(),   // 购方名称
                     gfsh : $('#xg_gfsh').val(),   // 购方税号
                     gfdz : $('#xg_gfdz').val(), // 购方地址
                     gfdh : $('#xg_gfdh').val(), // 购方电话
                     gfyh : $('#xg_gfyh').val(), // 购方银行
                     gfyhzh : $('#xg_gfyhzh').val(), // 购方银行账号     
                     lxr : $('#xg_lxr').val(), // 联系人
                     lxdh : $('#xg_lxdh').val(), // 联系电话
                     yjdz : $('#xg_yjdz').val(), // 邮寄地址
                     email : $('#xg_email').val()  //email
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {

						// modal
						 swal(data.msg);
						 el.$xiugai.modal('close');
					} else {

						swal('更新购方信息失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					swal('更新购方信息失败, 请重新登陆再试...!');
				}
			});
           return true;
		},
        
        /**
		 * 删除
		 */
		sc : function(da) {
			var _this = this;
			$.ajax({
				url : _this.config.scUrl,
				data : {
					"id" : da.id
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {

						// modal
						alert(data.msg);
					} else {

						swal('删除购方信息失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					swal('删除购方信息失败, 请重新登陆再试...!');
				}
			});

		},
        
        /**
		 * 新增保存
		 */
		xz : function() {
			var _this = this;
			//alert("12345");
			el.$jsForm0.validator({
				submit : function() {
					var formValidity = this.isFormValid();
					if (formValidity) {
						el.$jsLoading.modal('toggle'); // show loading
						if (null ==$('#xz_gfmc').val()|| $('#xz_gfmc').val()=='') {
                        	swal('企业名称不能为空！');
                            el.$jsLoading.modal('close'); 
                            return false;
						}
						//var data = el.$jsForm0.serialize(); // get form data
						//alert($('#xz_gfmc').val());
						$.ajax({
							url : ur,
							 data: {
			                        gfmc : $('#xz_gfmc').val(),   // 购方名称
			                        gfsh : $('#xz_gfsh').val(),   // 购方税号
			                        gfdz : $('#xz_gfdz').val(), // 购方地址
			                        gfdh : $('#xz_gfdh').val(), // 购方电话
			                        gfyh : $('#xz_gfyh').val(), // 购方银行
			                        gfyhzh : $('#xz_gfyhzh').val(), // 购方银行账号   
			                        lxr : $('#xz_lxr').val(), // 联系人
			                        lxdh : $('#xz_lxdh').val(), // 联系电话
			                        yjdz : $('#xz_yjdz').val(), // 邮寄地址
			                        email : $('#xz_email').val() // email
			                    },
							method : 'POST',
							success : function(data) {
								if (data.success) {
									// loading
									el.$modalHongchong.modal('close'); // close
									swal(data.msg);
									_this.tableEx.ajax.reload(); // reload table
								} else if (data.repeat) {
									swal(data.msg);
								}else{
									swal(data.msg);
								}
								el.$jsLoading.modal('close'); // close

							},
							error : function() {
								el.$jsLoading.modal('close'); // close loading
								swal('保存失败, 请重新登陆再试...!');
							}
						});
						return false;
					} else {
						swal('验证失败');
						return false;
					}
				}
			});
		},
		setForm0 : function(data) {
			//var _this = this, i;
			// todo set data
			// debugger
			el.$jsForm0.find('input[id="xg_gfmc"]').val(data.gfmc);
			el.$jsForm0.find('input[id="xg_gfsh"]').val(data.gfsh);
			el.$jsForm0.find('input[id="xg_gfdz"]').val(data.gfdz);
			el.$jsForm0.find('input[id="xg_gfdh"]').val(data.gfdh);
			el.$jsForm0.find('input[id="xg_gfyh"]').val(data.gfyh);
			el.$jsForm0.find('input[id="xg_gfyhzh"]').val(data.gfyhzh);
			el.$jsForm0.find('input[id="xg_lxr"]').val(data.lxr);
			el.$jsForm0.find('input[id="xg_lxdh"]').val(data.lxdh);
			el.$jsForm0.find('input[id="xg_yjdz"]').val(data.yjdz);
			el.$jsForm0.find('input[id="xg_email"]').val(data.email);
			$('#gfid').val(data.id);
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
            $("#close2").on('click', function () {
                el.$xiugai.modal('close');
            });
            
            $("#close1").on('click', function () {
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