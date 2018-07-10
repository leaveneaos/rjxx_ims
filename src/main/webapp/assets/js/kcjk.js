/**
 * feel
 */
$(function () {
    "use strict";
    var ur ;
    var urls;
    var el = {
    	$jkTable: $('#dyzytable'),//库存table   
        $jsRefresh: $('#jsSearch'),//查询按钮
        $jkLoading: $('.js-modal-loading'),//输在载入特效
        $jsdiv:$("#shezhi"),
        $jssave:$(".js-submit"),
        $jsLoading: $('.js-modal-loading'),//输在载入特效
        $jsClose:$('.js_close')
    };
    var loaddata = false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fpkc/getAllkc',
            getKcmx:'fpkc/getKcmx',
            szUrl: 'fpkc/updateYj'
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jkTable
                .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                        	$("#fpkcMxtable").hide();
                        	$("#fpkcMxtable_wrapper").hide();
                        	var bz = $('#searchbz').val();
                            d.loaddata = loaddata;
                        	if(bz=='1'){
                        		 d.xfid = $('#s_xfid').val();
                                 d.skpid = $('#s_skpid').val();
                                 d.fplx = $('#s_fplx').val();
                                 //d.fpsl = $('#s_fpsl').val();
                        	}else{
                        		var item = $('#s_mainkey').val();
                        		if(item=='xfsh'){
                        			d.xfsh = $('#searchValue').val();
                        		}
                        	}
                           
                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {
                            "data":null,
                            "render" : function(data) {
                                if(data.fpkcl!=null && data.fpkcl>0){
                                    return "<a kcid='"+data.id+"' class='chakan'>查看</a>"+ "&nbsp;&nbsp" +'<a href="javascript:void(0)" class="modify1">设置</a>';
                                }else{
                                    return "";
                                }
                            }
                        },
                      /*  {
                            "data": null,
                            "render": function (data) {
                                return '<a href="javascript:void(0)" class="modify1" style="margin-right: 10px;">设置</a>'
                            }
                        },*/
                        {"data": "xfmc"},
                        {"data": "xfsh"},
                        {"data": "kpdmc"},
                        {"data":"fpzlmc"},                   
                        {
                        	//"data": "fpkcl"
                        	"data": null,
                        	"render":function(data){
                        		if(data.fpkcl !=null && data.yjyz!=null){
                        			if(data.yjyz>data.fpkcl){
                        				return "<span style='color:red;'>"+data.fpkcl+"<span>";
                        			}else {
                        			    return data.fpkcl;
                                    }
                        		}else{
                        			return data.fpkcl==null?"":data.fpkcl;
                        		}
                        		
                        	}
                        },{
                            "data":"xgsj"
                        },
                        {"data": "yjyz"}
                        ]
                });
            	   
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            t.on('click','a.chakan', function (e, settings, json) {
            	var kcid = $(e.currentTarget).attr("kcid");
            	$("#fpkcMxtable").show();
            	 var mx = $("#fpkcMxtable") .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    destroy:true,
                    "ajax": {
                        url: _this.config.getKcmx,
                        type: 'POST',
                        data: function (d) {
                            d.loaddata = loaddata;
                            d.kcid=kcid;
                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },                  
                        {"data": "fpdm"},
                        {"data": "qshm"},
                        {"data": "zzhm"},
                        {"data": "fpfs"},
                        {"data": "syfs"},
                        {"data": "lgrq"}
                        ]
                });
                mx.on('draw.dt', function (e, settings, json) {
                    var x = mx, page = x.page.info().start; // 设置第几页
                    mx.column(0).nodes().each(function (cell, i) {
                        cell.innerHTML = page + i + 1;
                    });
                });
            });
            t.on('click', 'a.modify1', function () {
                var row = t.row($(this).parents('tr')).data();
                $("#yjkcl").val(row.yjyz);
                $("#xg_id").val(row.id);
                if(row.fpkcl !='' && row.fpkcl  !=null){
                    el.$jsdiv.modal('open');
                }else{
                    swal("发票库存已获取才能进行预警设置！");
                    return;
                }
                urls =_this.config.szUrl;
            });
            return t;
        },
        /**
         * search action
         */
        refresh_ac: function () {
            var _this = this;
            el.$jsRefresh.on('click', function (e) {
            	$('#searchbz').val("1");
                e.preventDefault();
                loaddata = true;
                _this.tableEx.ajax.reload();
            });
        },
        find_mv:function(){
        	var _this = this;
        	$('#searchButton').on('click',function(e){
        		$('#searchbz').val("0");
        		e.preventDefault();
        		loaddata = true;
                _this.tableEx.ajax.reload();
        	})
        },
        /**
         * 修改预警值
         */

        xgyjz : function() {
            var _this = this;
            $("#xgform").validator({
                submit : function() {
                    var formValidity = this.isFormValid();
                    var yjkcl = document.getElementsByName("yjkcl");
                    if (yjkcl == null || yjkcl =='') {
                        swal("请填写发票库存预警值!");
                        return;
                    }
                    if (formValidity) {
                        var data = $("#xgform").serialize(); // get form data
                        // data
                        $.ajax({
                            url : urls,
                            data : data,
                            method : 'POST',
                            success : function(data) {
                                if (data.success) {
                                    el.$jsLoading.modal('close'); // close
                                    el.$jsdiv.modal('close');
                                    swal(data.msg);
                                    _this.tableEx.ajax.reload(); // reload
                                    // table
                                } else {
                                    el.$jsLoading.modal('close');
                                    swal(data.msg);
                                }
                            },
                            error : function() {
                                //el.$modalHongchong.modal('close'); // close
                                el.$jsLoading.modal('close'); // close loading
                                swal('操作失败!');
                            }
                        });
                        return false;
                    } else {
                        swal('验证失败,请注意红色输入框内格式!');
                        return false;
                    }
                }

            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.refresh_ac();
            _this.find_mv();
            _this.xgyjz();
        }
    };
    action.init();
    

});