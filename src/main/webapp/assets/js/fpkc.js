/**
 * feel
 */
$(function () {
    "use strict";
    var ur ;
    var el = {
    	$jsTable: $('.js-table'),//库存table
        $modalHongchong: $('#hongchong'),//弹出div
        $jsSubmit: $('.js-submit'),//提交按钮
        $jsClose: $('.js-close'),//取消按钮
        $jsForm0: $('.js-form-kc'), //  form
        $s_fplx: $('#cfplx'), // search关键字
        $s_fpdm: $('#cfpdm'), // search关键字
        $jsSearch: $('#jsSearch'),//查询按钮
        $jsAdd: $('#jsAdd'),//新增按钮
        $xfsh: $('#xfsh'),//销方税号
        $kpddm: $('#kpddm'),//开票点代码
        $kpdmc: $('#kpdmc'),//开票点名称
        $fphms: $('#fphms'),//发票号码起
        $fphmz: $('#fphmz'),//发票号码止
        $fpdm: $('#fpdm'),//发票代码
        $fplx:$('#fplx'),
        $jsLoading: $('.js-modal-loading')//输在载入特效
    };
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpkc/getItems',
            xzUrl: 'fpkc/save',
            xgUrl: 'fpkc/update',
            xgcxUrl: 'fpkc/getXfmc',
            scUrl: 'fpkc/destory'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    "scrollX": true,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                        	d.xfids = $('#xfid').val();
                        	d.skpids = $('#s_skpid').val();
                            d.fpdm= el.$s_fpdm.val(); // search 用户账号
                            d.fplx = el.$s_fplx.val();
                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },                  
                        {"data": "xfmc"},
                        {"data": "xfsh"},
                        {"data": "kpdmc"},
                        {"data":"fpzlmc"},
                        {"data": "fpdm"},
                        {"data": "fphms"},
                        {"data": "fphmz"},
                        {"data": "fpkcl"},
                        {"data": "kyl"},
                        {"data": "yhmc"},
                        {"data": "lrsj"},
                        {
                            "data": null,
                            "render": function (data) {
                                return '<a class="xiugai">修改</a> <a class="shanchu">删除</a> '
                            }
                        }]
                });
            	   
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                //$('#search-table tr').find('td:eq(1)').hide();
                //$('#search-table tr').find('td:eq(4)').hide();
            });

            // 新增
            el.$jsAdd.on('click', el.$jsAdd, function () {
            	 _this.resetForm();
            	 $("#xfsh").find("option").eq(0).attr("selected", true);          	 
                 ur = _this.config.xzUrl;
                el.$modalHongchong.modal('open');
            });
            // 修改
            t.on('click', 'a.xiugai', function () {
                var row = t.row($(this).parents('tr')).data();
                var xfmc = row.xfmc;
                var selectIndex = -1;
                var options = $("#xfsh").find("option");
                for (var j = 0; j < options.size(); j++) {
                    var text = $(options[j]).text();                  
                    if (text == xfmc) {
                        selectIndex = j;                        
                        break;
                    }
                }
                $("#xfsh").find("option").eq(selectIndex).attr("selected", true);
                var kpdmc = row.kpdmc;
                selectIndex = -1;               
                var xfid =  $('#xfsh option:selected').val();
        		var skpid = $("#kpddm");
        		$("#kpddm").empty();
        		$.ajax({
        			url:"fpkc/getKpd",
        			data:{"xfid":xfid},
        			success:function(data){
        				for(var i=0;i<data.length;i++){
        					if(data[i].skpid != row.skpid){
        						var option = $("<option>").text(data[i].kpdmc).val(data[i].skpid);
            				    skpid.append(option);
        					}
        					
        				}
        			}
        			
        		});
        		var option = $("<option selected>").text(kpdmc).val(row.skpid);
        		skpid.append(option);
        		var fpzlmc = row.fpzlmc;
                var selectIndex = -1;
                var options = $("#fplx").find("option");
                for (var j = 0; j < options.size(); j++) {
                    var text = $(options[j]).text();                  
                    if (text == fpzlmc) {
                        selectIndex = j;                        
                        break;
                    }
                }
                $("#fplx").find("option").eq(selectIndex).attr("selected", true);
                el.$fpdm.val(row.fpdm);
                el.$fphms.val(row.fphms);
                el.$fphmz.val(row.fphmz);
                el.$modalHongchong.modal('open');
                ur =_this.config.xgUrl + '?id=' + row.id;   			 
            });
            // 删除
            t.on('click', 'a.shanchu', function () {
            	  var da = t.row($(this).parents('tr')).data();
            	 if(confirm("确定要删除该条数据吗？"))
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
                	var fpdm = $("#fpdm").val().trim();
                	var fphms = $("#fphms").val().trim();          
                    var fphmz = $("#fphmz").val().trim();                           
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                    	if(parseInt(fphms)>parseInt(fphmz)){
                        	alert("起始号码不能大于截止号码！");
                        	return false;
                        }
                        el.$jsLoading.modal('toggle'); // show loading
                        var data = el.$jsForm0.serialize(); // get form data
                        $.ajax({
                            url: ur,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                             	el.$jsLoading.modal('close'); // close loading                              	
                                if (data.success) {
                                    alert(data.msg);
                                    el.$modalHongchong.modal('close'); // close
                                    _this.tableEx.ajax.reload();
                                }else{
                                    alert(data.msg);                                  
                                }                             
                            },
                            error: function () {
                            	el.$jsLoading.modal('close'); // close loading
                                alert('保存失败，请检查!');
                            }
                        });
                        return false;
                    } else {
                        alert('数据验证失败，请检查！');
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
                //method: 'POST',
                success: function (data) {
                    if (data.success) {
                        alert(data.msg);
                    } else {    
                        alert('删除失败: ' + data.msg);                       
                    }
                    _this.tableEx.ajax.reload(); // reload table                 
                },
                error: function () {
                    alert('删除失败，请检查!');
                }
            });
            	
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$modalHongchong.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
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