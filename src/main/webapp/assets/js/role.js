/**
 * feel
 */
$(function () {
    "use strict";
    var ur ;
    var el = {
    	$jsTable: $('.js-table'),
        $modalHongchong: $('#hongchong'),
        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),
        $jsForm0: $('#form1'), // 红冲 form
        $s_jsmc: $('#s_jsmc'), // search
        $jsSearch: $('#button1'),
        $jsTable1: $('#button2'),
        $name: $('#name'),
        $description: $('#description'),
        $jsLoading: $('.js-modal-loading')
    };
	el.$modalHongchong.draggable();
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'role/getRole',
            xzUrl: 'role/save',
            xgUrl: 'role/update',
            xgcxUrl: 'role/getRolePrivs',
            scUrl: 'role/destroy'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {

                            d.roleName= el.$s_jsmc.val(); // search 用户账号

                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {"data": "name"},
                        {"data": "id"},
                        {"data": "lrr"},
                        {
                            "data": null,
                            "render": function (data) {
                                return '<a class="xiugai">修改</a> <a class="shanchu">删除</a> '
                            }
                            // "defaultContent": ' <a class="view" href="' +
                            // data.href+ '" target="_blank">查看</a> <a
                            // class="hongchong">红冲</a> '
                        }]
                });
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
        		$('#tbl tr').find('td:eq(2)').hide();
            });

            // 新增
            el.$jsTable1.on('click', el.$jsTable1, function () {
            	 _this.resetForm();
//            	 el.$yhmm.val();
//            	 el.$qrmm.val();
//            	 el.$yhmm.show();
//                 el.$qrmm.show();
//                 el.$lab.show();
//                 el.$lab1.show();
                 ur = _this.config.xzUrl
                el.$modalHongchong.modal({"width": 600, "height": 500});
            });
            // 修改
            t.on('click', 'a.xiugai', function () {
                var row = t.row($(this).parents('tr')).data();
                _this.setForm0(row);
                el.$description.val(row.description);
                el.$name.val(row.name);
    			$.ajax({
    	            url : _this.config.xgcxUrl,
    				data : {
    					id : row.id
    				},
    				type : 'post',
    				success : function(data) {
    					var i, rp,rp1, slt,slt1, type;
    					var list = data.list;
    					if(data.success){
    						for (i = 0; i < list.length; i++) {
        						rp = list[i];
        						slt = '#privilege-' + rp.privid;
        						type = '#type-' + $(slt).attr("name");
        						$(type).prop('checked', true);
        						$(slt).prop('checked', true);
        					}
    						$('#roleid').val(row.id);
    						  el.$modalHongchong.modal({"width": 600, "height": 500});
    					}else{
                              alert('查询角色权限异常');        
    					}
    				}
    			});
    			  ur =_this.config.xgUrl;
            });
            // 删除
            t.on('click', 'a.shanchu', function () {
            	  var da = t.row($(this).parents('tr')).data();
            	 if(confirm("确定要删除该角色吗"))
            	   {
            		 _this.sc(da);
            	   }
                var data = t.row($(this).parents('tr')).data();
           	 _this.resetForm();
            });
            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                e.preventDefault();
                _this.tableEx.ajax.reload();
            });
        },
        /**
         * 新增保存
         */
       
        xz: function () {
            var _this = this;
            el.$jsForm0.validator({
                submit: function () {
                    var formValidity = this.isFormValid();
            		var ids = document.getElementsByName("firstId");
					var fl = false;
					for (var i = 0; i < ids.length; i++) {
						if (ids[i].checked == true) {
							fl = true;
							break;
						}
					}
					if (fl == false) {
						alert("请选择用户权限!");
						return false;
					}
                    if (formValidity) {
                        el.$jsLoading.modal('toggle'); // show loading
                        // alert('验证成功');
                        var data = el.$jsForm0.serialize(); // get form data
                        // data
                        // TODO save data to serve
                        $.ajax({
                            url: ur,
                            data: data,
                            method: 'post',
                            success: function (data) {
                             	el.$jsLoading.modal('close'); // close loading
                              	el.$modalHongchong.modal('close'); // close
                                if (data.success) {
                                    // modal
                                    alert(data.msg);
                                } else if (!data.success) {
                                    alert(data.msg);
                                  
                                }else if (data.nopeat){
                           
	                                alert(data.msg);
                                }
                                _this.tableEx.ajax.reload(); // reload table
                                // data
                              
                            },
                            error: function () {
                            	  el.$jsLoading.modal('close'); // close loading
                                alert('保存角色失败, 请重新登陆再试...!');
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
        /**
         * 删除
         */
        sc: function (da) {
        	   var _this = this;
            $.ajax({
                url: _this.config.scUrl,
                data: {"id":da.id},
                method: 'POST',
                success: function (data) {
                    if (data.success) {
                      
                        // modal
                        alert(data.msg);
                    } else {
    
                        alert('删除角色失败: ' + data.msg);
                       
                    }
                    _this.tableEx.ajax.reload(); // reload table
                    // data
                  
                },
                error: function () {
                    alert('删除角色失败, 请重新登陆再试...!');
                }
            });
            	
        },
        setForm0: function (data) {
            var _this = this, i;
            // todo set data
            // debugger
            el.$jsForm0.find('input[name="yhmc"]').val(data.yhmc);
            el.$jsForm0.find('input[name="yhzh"]').val(data.dlyhid);
            if('男'==data.xb){   el.$jsForm0.find('select[name="xb"]').val(0);}
            else{  el.$jsForm0.find('select[name="xb"]').val(1);};
           
        },
        setForm1: function () {
            var _this = this, i;
            // todo set data
            // debugger
            el.$jsForm0.find('input[name="hc_yhmc"]').val(data.yhmc);
            el.$jsForm0.find('input[name="hc_yzh"]').val(data.dlyhid);
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
            _this.search_ac();
            _this.xz();
            _this.modalAction(); // hidden action
        }
    };

    action.init();

});