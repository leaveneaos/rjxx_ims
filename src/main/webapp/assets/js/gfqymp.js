$(function () {
    "use strict";
    var ur;
    var el = {
        $jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),
        $jsClose: $('.js-close'),
        $jsForm0: $('.js-form-0'),     // 红冲 form
        $jsAdd: $('.js-add'),
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
                    //{"data": "orderTime"},
                    {"data": "gfyh"},
                    {"data": "gfyhzh"},
                   /* {
						"data": null,
                        "defaultContent": "<a class='modify' href='javascript:void(0)'>修改</a> <a href='javascript:void(0)' class='del'>删除</a>"									
					}*/
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
	                alert("请选择需要删除的购方信息...");
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
	                alert("请选择需要修改的购方信息...");
	                return;
	            }else if(djhArr.length >=2){
	            	alert("每次只能修改一条数据...");
	                return;
	            }
	            //alert(djhArr);
	            var ipts = t.row($("input[type='checkbox']:checked").parents("tr")).data();
	            _this.setForm0(ipts);
	            el.$xiugai.modal({"width": 600, "height": 400});
                el.$xiugai.modal('open');
			});
			
			 // 修改
            t.on('click', 'a.modify', function () {
                var data = t.row($(this).parents('tr')).data();
                // todo
                _this.setForm0(data);
                el.$xiugai.modal({"width": 600, "height": 400});
                el.$xiugai.modal('open');
            });
            
         // 界面新增按钮
			el.$jsAdd.on('click', el.$jsAdd, function() {
				_this.resetForm();
				ur = _this.config.addUrl;
				//alert("新增");
				el.$modalHongchong.modal({"width": 600, "height": 400});
			});	
			
			// 修改数据保存按钮
			el.$jsUpdate.on('click', el.$jsUpdate, function() {
				ur = _this.config.updateUrl;
				var t = _this.update();
				/*if(t==true){
					_this.resetForm();
				}*/
				
			});	
            return t;
        },
        
        /**
		 * 更新
		 */
		update : function() {
			var _this = this;
			if (null ==$('#xg_gfmc').val()|| $('#xg_gfmc').val()=='') {
            	alert('企业名称不能为空！');
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
                     gfyhzh : $('#xg_gfyhzh').val() // 购方银行账号      
				},
				method : 'POST',
				success : function(data) {
					if (data.success) {

						// modal
						 alert(data.msg);
						 el.$xiugai.modal('close');
					} else {

						alert('更新购方信息失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					alert('更新购方信息失败, 请重新登陆再试...!');
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

						alert('删除购方信息失败: ' + data.msg);

					}
					_this.tableEx.ajax.reload(); // reload table
					// data

				},
				error : function() {
					alert('删除购方信息失败, 请重新登陆再试...!');
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
                        	alert('企业名称不能为空！');
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
			                        gfyhzh : $('#xz_gfyhzh').val() // 购方银行账号        
			                    },
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
            el.$jsClose.on('click', function () {
                el.$modalHongchong.modal('close');
                el.$xiugai.modal('close');
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