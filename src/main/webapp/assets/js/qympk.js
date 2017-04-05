$(function () {
    "use strict";
    var ur;
    var el = {
        $jsTable: $('#mpk_table'),
        $jsClose: $('#close2'),
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
            getUrl: 'qympk/getQympkList'
           
        },
        dataTable: function () {
            var _this = this;
            var t = el.$jsTable.DataTable({
            	"searching": false,
                "serverSide": true,
                "sServerMethod": "POST",
                "processing": true,
                "scrollX": true,
                ordering: false,
                  "ajax": {
                    url: _this.config.getUrl,
                    type: 'POST',
                    data: function (d) {
                        d.searchtext = $("#searchtext").val()  // search 
                    }
                },
              "columns": [
                    
                    /*{
	                        		"orderable" : false,
	                        		"data" : null,
	                        		render : function(data, type, full, meta) {
	                        			return '<input type="checkbox" name= "chk" data="'
	                        				+ data.mpkid + '" />';
	                        		}
	                 },*/
	               {
	                 "orderable": false,
	                 "data": null,
	                 "defaultContent": ""
	                },
	                {
					"data": null,
                    "defaultContent": "<a class='add' href='javascript:void(0)'>添加</a> "									
				    },
                    {"data": "dwmc"},
                    {"data": "nsrsbh"},
                    {"data": "zcdz"},
                    {"data": "zcdh"},
                    {"data": "khyh"},
                    {"data": "yhzh"},
                    {"data": "email"},
                    {
    					"data": null,
                        "defaultContent": "<a class='view' href='javascript:void(0)'>查看</a> "									
    				}
                   
                ],
               
            });
            var t1 = $('#detail_table').DataTable({
            	"searching": false,
                "serverSide": true,
                "sServerMethod": "POST",
                "processing": true,
                "scrollX": true,
                ordering: false,
                  "ajax": {
                    url: "qympk/getDetailList",
                    type: 'POST',
                    data: function (d) {
                        d.nsrsbh =$("#nsrsbh").val();  // search 
                    }
                },
              "columns": [
                   
	               {
	                 "orderable": false,
	                 "data": null,
	                 "defaultContent": ""
	                },
                    {"data": "lxr"},
                    {"data": "lxdh"},
                    {"data": "yjdz"},
                    {"data": "email"},
                ],
               
            });
            t1.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t1.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                var trs=$('#mpk_table').find('tr');
                var rows = 1;
                for(var i=trs.length;i>0;i--){
                    var cur = $($(trs[i]).find("td")[3]).text();
                    var next = $($(trs[i-1]).find("td")[3]).text();
                    if(cur==next){
                        rows++;
                        $($(trs[i]).find("td")[9]).remove();
                        $($(trs[i]).find("td")[1]).remove();
                        $($(trs[i]).find("td")[1]).remove();
                        $($(trs[i]).find("td")[1]).remove();
                    } else {
                    	if(rows>1){
                    		 $($(trs[i]).find("td")[1]).attr("rowspan",rows);
                    		 $($(trs[i]).find("td")[1]).css({"text-align":"center","line-height":40*rows+"px"});
                    		 $($(trs[i]).find("td")[2]).attr("rowspan",rows);
                             $($(trs[i]).find("td")[2]).css({"text-align":"center","line-height":40*rows+"px"});
                             $($(trs[i]).find("td")[3]).attr("rowspan",rows);
                             $($(trs[i]).find("td")[3]).css({"text-align":"center","line-height":40*rows+"px"});
                             $($(trs[i]).find("td")[9]).attr("rowspan",rows);
                             $($(trs[i]).find("td")[9]).css({"text-align":"center","line-height":40*rows+"px"});
                    	}
                        rows=1;
                    }
                }
            });
            // 添加
			t.on('click', 'a.add', function() {
				var data = t.row($(this).parents('tr')).data();
				if (confirm("确定要添加该条购方信息吗？")) {
					$.ajax({
						url : "qympk/saveGfxx",
						 data: {
		                        gfmc : data.dwmc,   // 购方名称
		                        gfsh : data.nsrsbh,   // 购方税号
		                        gfdz : data.zcdz, // 购方地址
		                        gfdh : data.zcdh, // 购方电话
		                        gfyh : data.khyh, // 购方银行
		                        gfyhzh :data.yhzh, // 购方银行账号   
		                        lxr : data.lxr, // 联系人
		                        lxdh : data.lxdh, // 联系电话
		                        yjdz : data.yjdz // 邮寄地址
		                    },
						method : 'POST',
						success : function(data) {
							if (data.success) {
								alert(data.msg);
							}else{
								alert(data.msg);
							}
							
						}
						
					});
				}
				_this.resetForm();
			});
			 //查看
            t.on('click', 'a.view', function () {
                var data = t.row($(this).parents('tr')).data();
                $("#nsrsbh").val(data.nsrsbh);
                t1.ajax.reload();
                el.$xiugai.modal({"width": 600, "height": 500});
            });
            $("#search").click(function(){
            	t.ajax.reload();
            });
            return t;
        },
        setForm0 : function(data) {
			el.$jsForm0.find('input[id="xg_gfmc"]').val(data.dwmc);
			el.$jsForm0.find('input[id="xg_gfsh"]').val(data.nsrsbh);
			el.$jsForm0.find('input[id="xg_gfdz"]').val(data.zcdz);
			el.$jsForm0.find('input[id="xg_gfdh"]').val(data.zcdh);
			el.$jsForm0.find('input[id="xg_gfyh"]').val(data.khyh);
			el.$jsForm0.find('input[id="xg_gfyhzh"]').val(data.yhzh);
			el.$jsForm0.find('input[id="xg_lxr"]').val(data.lxr);
			el.$jsForm0.find('input[id="xg_lxdh"]').val(data.lxdh);
			el.$jsForm0.find('input[id="xg_yjdz"]').val(data.yjdz);
			
			$('#gfid').val(data.id);
		},
     
        modalAction: function () {
            var _this = this;
         
            // close modal
            el.$jsClose.on('click', function () {
                el.$xiugai.modal('close');
            });
        },
       
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
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