$(function () {
    "use strict";
    var ur;
    var el = {
        $jsTable: $('#jkpz_table'),
        $jsDetailTable:$('#jkpz_table_detail'),
        $modalHongchong: $('#hongchong'),
        $jsClose: $('#close1'),
        $jsClose1: $('#close2'),
        $jssq: $('#doc-oc-demo3'),
        $jsForm0: $('.js-form-0'),     //
        $jsAdd: $('#jkpz_add'),
        $jsExport: $('.js-export'),
        $jsLoading: $('.js-modal-loading'),
        $jsSave: $('#save'),
        $xiugai: $('#modify'),
        $jsUpdate: $('#update'),
        $jsCheck: $('#jkpz_ck'),
        $s_gsdm : $('#jkpz_gsmc'), // search
        $jsSearch : $('#companySearch') // 查询公司
    };
		var jsTreeFunction={
			subtemplateSlogger:function(){
				var _this=$(this);
				_this.find("i").toggleClass("fa-caret-right fa-caret-down");
				_this.siblings(".jstree-first-box").slideToggle();
			},
			clickCheck:function(){
				var _this=$(this);
				_this.find(".tree-check").toggleClass("fa-check");
				if(_this.find(".tree-check").hasClass("fa-check")){
					_this.attr("data-nowtemplate","good");
				}else{
					_this.attr("data-nowtemplate","good");
				}
			}
		}
		$(document).on("click",".tree-slogger",jsTreeFunction.subtemplateSlogger);
		$(document).on("click",".click-check",jsTreeFunction.clickCheck);
		$.fn.jstree=function(option){
			var data=option.data.length>0? option.data:0;
			var opTemplataId=option.templataId;
			var allHtml='';
			for (var i=0;i<data.length;i++) {
				var firstText=data[i].text;
				var firstId=data[i].id;
				var firstTemplateId=data[i].templateId;
				var firstCheckHtml='';
				    if(firstTemplateId==opTemplataId){
				    	firstCheckHtml='<span class="tree-check fa fa-check"></span>';
				    }else{
				    	firstCheckHtml='<span class="tree-check fa"></span>';
				    }
				var second=data[i].children;
				var treeSloger='<span class="tree-slogger"></span>';
				if(second.length>0){
					treeSloger='<span class="tree-slogger"><i class="fa fa-caret-down"></i></span>';
				}
				var firstHtml="";
				for (var j=0;j<second.length;j++) {				
					var secondText=second[j].text;
				    var secondId=second[j].id;
				    var secondTemplateId=second[j].templateId;
				    var secondCheckHtml='<span class="tree-check fa"></span>';
				    if(secondTemplateId==opTemplataId){
				    	secondCheckHtml='<span class="tree-check fa fa-check"></span>';
				    }
					firstHtml+='<li class="jstree-second"><p class="click-check" data-id="'+secondId+'" data-type="2" data-oldtemplate="'+secondTemplateId+'">'+secondCheckHtml+'<i>'+secondText+'</i></p></li>';
				}				
				allHtml+='<li class="jstree-first">'+treeSloger+'<p class="click-check" data-id="'+firstId+'" data-type="1" data-oldtemplate="'+firstTemplateId+'">'+secondCheckHtml+'<i>'+firstText+'</i></p><ul class="jstree-first-box">'+firstHtml+'</ul></li>';				
			}
			$(this).html($(allHtml));						
		}
    
//action.config.scSqUrl
    var loaddata=false;
    var action = {
        tableEx: null, // cache dataTable
        tableDetail:null, // 模板详情
        config: {
            getUrl: 'jkpz/getjkmbList',//模板列表
            getInfoUrl:"jkpz/getjkmbzb", // 查看模板详情
            addSqUrl: 'jkpz/savembsq',//新增授权
            scSqUrl: 'jkpz/scCsb',//删除授权
            updateSqUrl: 'jkpz/updatembsq',//修改授权
            empowerUrl: 'jkpz/getxxList',//授权列表
            addUrl: 'jkpz/save',//新增模板
            updateUrl:'jkpz/update',//修改模板
            deleteUrl : 'jkpz/delete'//删除模板
        },

        dataTable: function () {
            var _this = this;
            // 公司-模板
            var t = el.$jsTable.DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    "ajax": {
                        url: _this.config.getUrl,
                        type: 'POST',
                        data: function (d) {
                            d.gsdm = el.$s_gsdm.val(); // search 公司代码
                            d.loaddata=loaddata;
                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            render: function (data, type, full, meta) {
                                return '<input type="checkbox" name= "chk" value="'
                                    + data.id + '" />';
                            }
                        },
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {
                            "orderable": false,
                            data: null,
                            render: function (data, type, row, meta) {
                                var arr = []
                                arr.push('<button class="am-btn am-btn-success am-radius am-btn-xs lookfor" data-id="' +row.id + '">查看</button>');
                                arr.push('<button class="am-btn am-btn-warning am-radius am-btn-xs modify" data-id="' + row.id + '" >修改</button>');
                                arr.push('<button class="am-btn am-btn-danger am-radius am-btn-xs empower" data-am-offcanvas="{target: \'#doc-oc-demo3\'}" data-id="' + row.id + '">授权</button>');
                                return arr.join('');
                            }
                        },
                        {"data": "mbid"},
                        {
                            "data": "mbmc"
                        },
                        {
                            "data": "mbms"
                        },
                        {
                            "data": "gsmc"
                        },
                        {
                            "data": "lrry"
                        },
                        {
                            "data": "lrsj"
                        }
                    ]
                }
            );
            //模板详情
            var t_detail = el.$jsDetailTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                "iDisplayStart" : 0,
                "iDisplayLength" : 25,//每页显示25条记录
                "ajax": {
                    url: _this.config.getInfoUrl,
                    type: 'POST',
                    data: function (d) {
                        d.mbid = $("#mbid").val();
                        d.loaddata=loaddata;
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    /*{"data": "mbid"},*/
                    {"data": "cszff"},
                    {"data":"pzcsm"},
                    {"data":"pzcsmc"},
                    {"data":"csm"}
                ]
            });
            //分页
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {// 序号
                    cell.innerHTML = page + i + 1;
                });
            });
            t_detail.on('draw.dt', function(e, settings, json) {
                var x = t_detail, page = x.page.info().start; // 设置第几页
                t_detail.column(0).nodes().each(function(cell, i) {
                    cell.innerHTML = page + i + 1;
                });
            });
            //查看详情
            t.on('click','button.lookfor',function () {
                var da = t.row($(this).parents('tr')).data();
                $("#mbid").val(da.id);
                t_detail.ajax.reload();
            });

           t.on('click','button.empower',function () {
                var da = t.row($(this).parents('tr')).data();
                _this.sq(da);
                $("#menuTree2").attr({"data-mbid":da.mbid,"data-gsdm":da.gsdm,"data-id":da.id})
                
            });

            $('#jkpz_table_detail').on( 'draw.dt', function () {
                if($("input[name='chk']:checked").length>1){
                    var ycl = $("input[name='bckpje']");
                    for (var i = 0; i < ycl.length; i++) {
                        $(ycl).attr("readonly","readonly");
                    }
                }
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

            //删除
            $("#jkpz_del").click(function () {
                var chk_value="" ;
                $('input[name="chk"]:checked').each(function(){
                    chk_value+=$(this).val()+",";
                });
                var ids = chk_value.substring(0, chk_value.length-1);
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
                            url :_this.config.deleteUrl,
                            data : {"ids":ids},
                        }).done(function(data) {
                            $('.confirm').removeAttr('disabled');
                            swal(data.msg);
                            _this.tableEx.ajax.reload();
                        })
                    });

                }
            });

            // 修改
            t.on('click', 'button.modify', function () {
                $('div').removeClass('am-form-error');
                $('input').removeClass('am-field-error');
                $('div').removeClass('am-form-success');
                $('input').removeClass('am-field-success');
                var row = t.row($(this).parents('tr')).data();
                $('#mbxxid').val(row.id);
                $.ajax({
                    url:"jkpz/getmbzb",
                    async:false,
                    data:{
                        "mbid" : row.id
                    }, success:function(data) {
                        if (data) {
                             var list =data.data;
                            for (var i = 0; i < list.length; i++) {
                                if(list[i].pzcsm=="serialNumber"){
                                    el.$jsForm0.find('select[name="jkpz_jylsh"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="orderNo"){
                                    el.$jsForm0.find('select[name="jkpz_ddh"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="orderDate"){
                                    el.$jsForm0.find('select[name="jkpz_ddrq"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="clientNo"){
                                    el.$jsForm0.find('select[name="jkpz_kpddm"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="invType"){
                                    el.$jsForm0.find('select[name="jkpz_fpzl"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="invoiceList"){
                                    el.$jsForm0.find('select[name="jkpz_dyqd"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="invoiceSplit"){
                                    el.$jsForm0.find('select[name="jkpz_zdcf"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="invoicePrint"){
                                    el.$jsForm0.find('select[name="jkpz_ljdy"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="chargeTaxWay"){
                                    el.$jsForm0.find('select[name="jkpz_zsfs"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="taxMark"){
                                    el.$jsForm0.find('select[name="jkpz_hsbz"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="remark"){
                                    el.$jsForm0.find('select[name="jkpz_bz"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="version"){
                                    el.$jsForm0.find('select[name="jkpz_spbmbb"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="person"){
                                    el.$jsForm0.find('select[name="jkpz_lkr"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="seller"){
                                    el.$jsForm0.find('select[name="jkpz_xfqxx"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="items"){
                                    el.$jsForm0.find('select[name="jkpz_spqxx"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="policyMsg"){
                                    el.$jsForm0.find('select[name="jkpz_spyhxx"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="payments"){
                                    el.$jsForm0.find('select[name="jkpz_zfxx"]').val(list[i].cszffid);
                                }else if (list[i].pzcsm=="buyer"){
                                    el.$jsForm0.find('select[name="jkpz_gfxx"]').val(list[i].cszffid);
                                }
                            }
                        }
                    }});
                _this.setForm0(row);
                el.$xiugai.modal({"width": 800, "height": 500});
                el.$xiugai.modal('open');
                ur = _this.config.updateUrl;
            });

            // 界面新增按钮
            el.$jsAdd.on('click', el.$jsAdd, function () {
                _this.resetForm();
                ur = _this.config.addUrl;
                el.$xiugai.modal({"width": 800, "height": 500});
            });


            $('#search').click(function () {
                $("#ycform").resetForm();
                t.ajax.reload();
            });
  /*          $('#sqbutton').click(function () {
            	var allcheck=$("#menuTree2").find(".click-check");
            	all.each(function(){
            		var _this=$(this);
            		
            	})
            	
                //
                swal("保存成功");
            });*/
            $('#sqbutton').click(function () {
            	var allcheck=$("#menuTree2").find(".click-check[data-nowtemplate='good']");
            	var topid=$("#menuTree2").attr("data-id");
            	var mbid=$("#menuTree2").attr("data-mbid");
            	var gsdm=$("#menuTree2").attr("data-gsdm");
            	var addData={
            		"mbid":mbid,            		
            		"gsdm":gsdm,
            		"data":[],
            	},
            	delectData={
            		"mbid":mbid,
            		"gsdm":gsdm,
            		"data":[],
            	};
            	var addId=[],deleId=[];
            	allcheck.each(function(){
            		var _this=$(this);
            		var oldTemplate=_this.attr("data-oldtemplate");
            		var nowTemplate=_this.attr("data-nowtemplate");            		            		
            		if(_this.find(".tree-check ").hasClass("fa-check")){
            			if(oldTemplate != topid){
            				var dataType=_this.attr("data-type");
            				var dataId=_this.attr("data-id");
            				var templateId=oldTemplate != undefined? oldTemplate:"";
            				var xfid;
            				var a={};
            				if(dataType=="1"){
            					a={
            						"xfid": dataId,
            						"templateId":templateId,
		                            "skpid": ""
            					}
            				}else{  
            					xfid=_this.closest(".jstree-first-box").siblings("p").attr("data-id");
            					a={
            						"xfid":xfid,
            						"templateId":templateId,
		                            "skpid": dataId
            					}            					           				           				
            			    }
            				addId.push(a);
            		     }
            			}else{
            			if(oldTemplate==topid){
            				var dataType=_this.attr("data-type");
            				var dataId=_this.attr("data-id");
            				var xfid;
            				var a={};
            				if(dataType=="1"){
            					a={
            						"xfid": dataId,
		                            "skpid": ""
            					}
            				}else{        
            					xfid=_this.closest(".jstree-first-box").siblings("p").attr("data-id");
            					a={
            						"xfid":xfid,
		                            "skpid": dataId
            					}            					            				           				
            			}
            				deleId.push(a);
            		}
            	}	
            	})
            	addData.data=addId;
            	delectData.data=deleId;
            	$.ajax({
                    method: 'POST',
            		url:action.config.addSqUrl,
            		data:{"str":JSON.stringify(addData)},
            		success:function(data){
            			if(data.success){
            				$.ajax({
            					type:"post",
            					url:action.config.scSqUrl,
            					async:true,
            					data:{"str":JSON.stringify(delectData)},
            					success:function(data){
            						if(data.success){
            							swal(data.msg);
            						}else {
            						    swal(data.msg);
                                    }
            					}
            				});
            			}else {
                            swal(data.msg);
                        }
            		}
            	});
            	})
            return t;
        },


        /**
         * 授权
         */
        sq: function (da) {
            var _this = this;
            $.ajax({
                url: _this.config.empowerUrl,
                data: {
                    "gsdm": da.gsdm,
                    "mbid": da.id
                },
                cache:false,
                method: 'POST',
                success: function (data) {
                    if (data.success) {
                        var list =data.data;
                        tre(JSON.parse(list),da.gsdm,da.id);
                    } else {
                        swal('查看失败: ' + data.msg);
                    }
                },
                error: function () {
                    swal('授权失败!');
                }
            });
        },

        /**
         * search action
         */
        search_ac : function() {
            var _this = this;
            el.$jsSearch.on('click', function(e) {
                var gsdm=  $('#jkpz_gsmc').val();
                if(gsdm==null|| gsdm==""){
                    swal('请选择公司!');
                    return false;
                }
                loaddata = true;
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
                submit : function() {
                    var formValidity = this.isFormValid();
                    if (formValidity) {
                        el.$jsLoading.modal('toggle'); // show loading
                        var data = el.$jsForm0.serialize(); // get form data
                        $.ajax({
                            url : ur,
                            data : data,
                            method : 'POST',
                            success : function(data) {
                                if (data.success) {
                                    el.$xiugai.modal('close'); // close
                                    swal({
                                        title: "修改成功",
                                        timer: 1500,
                                        type: "success",
                                        showConfirmButton: false
                                    });
                                    loaddata = true;
                                    _this.tableEx.ajax.reload(); // reload table
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
        setForm0: function (data) {
            // todo set data
            el.$jsForm0.find('input[id="jkpz_mbmc"]').val(data.mbmc);
            el.$jsForm0.find('input[id="jkpz_mbms"]').val(data.mbms);
            el.$jsForm0.find('select[name="jkpz_gsdm"]').val(data.gsdm);
        },
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            // close modal
            $("#close1").on('click', function () {
                el.$xiugai.modal('close');
            });
            $("#close2").on('click', function () {
                el.$jssq.modal('close');
            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.xz();
            _this.search_ac();
            _this.modalAction(); // hidden action
        }
    };
    action.init();
});

function tre(data,gsdm,mbid) {
	$("#menuTree2").jstree({data:data,templataId:mbid})
   /* $('#menuTree2').jstree(
        {'core':{data:null, "check_callback" : true},
            plugins: ['state', "sort",'wholerow', 'contextmenu', 'types','checkbox'],
            types: {
                'default': {
                    'icon': false //设置图标
                },
                'file' : {
                    'icon' : 'fa fa-file-text-o'//可放置css样式
                }
            }
        });*/
    console.log(data);
    // for(var i=0; i<data.length; i++)
    // {
    //     if(data[i].state!=null){
    //         console.log(data[i].state);
    //         eval(data[i].state);
    //     }
    // }
    $('#menuTree2').jstree(true).settings.core.data=data;
    $('#menuTree2').jstree(true).refresh();
    $('#menuTree2').on('activate_node.jstree',function(e,data){
        var currentNode = data.node;
        var xfid;
        var kpdid;
        console.log(currentNode.state);
        // for(var i in currentNode.state){
        //     if(i=='selected'){
        //         alert(11);
        //     }
        // }
        if(currentNode.parent!=null &&currentNode.parent =='#'){
            xfid=currentNode.id;
            kpdid="";
        }else {
            xfid = currentNode.parent;
            kpdid=currentNode.id;
        }
        swal("保存成功");
        /*$.ajax({
            url:"jkpz/savembsq",
            async:false,
            data:{
                "mbid" : mbid,
                "gsdm" : gsdm,
                "xfid" : xfid,
                "kpdid": kpdid
            }, success:function(data) {
                if (data) {
                    swal(data.msg);
                }else {
                    swal(data.msg);
                }
            },
            error : function() {
                swal('授权失败, 请重新再试...!');
            }
        });*/
    });
    // $('#menuTree2').jstree({
    //     core: {
    //         data:null
    //         /*multiple: false,
    //         check_callback: true,
    //         data: json    //全局数组*/
    //     },
    //     plugins: ['wholerow', 'contextmenu', 'types','checkbox'],
    //     types: {
    //         'default': {
    //             'icon': false //设置图标
    //         },
    //         'file' : {
    //             'icon' : 'fa fa-file-text-o'//可放置css样式
    //         }
    //     }
    // });
    /*var datas = eval("("+data+")");
    $('#menuTree2').tree({
        dataSource: function(options, callback) {
            // 模拟异步加载
            setTimeout(function() {
                callback({data: options.products || datas});
            }, 400);
        },
        multiSelect: true,
        cacheItems: false,
        folderSelect: false
    }).on('selected.tree.amui', function (event, datas) {
        datas.target.selectedStatus = '授权';
        $el = $('#' + datas.target.attr.id + ' .am-tree-status');
        $el.text('selected').css('color', 'red');
    }).on('deselected.tree.amui', function (event, datas) {
        datas.target.selectedStatus = '取消授权';
        $el = $('#' + datas.target.attr.id  + ' .am-tree-status');
        $el.text('unselected').css('color', '#000');
        console.log(datas.selected);
    });*/

   /* var $selected = $('#js-selected');
    var i = 0;
    $('[data-selected]').on('click', function() {
        var action = $(this).data('selected');

        if (action === 'add') {
            $selected.append('<option value="o' + i +'">动态插入的选项 ' + i + '</option>');
            i++;
        }

        if (action === 'toggle') {
            $o.attr('selected', !$o.get(0).selected);
        }

        if (action === 'disable') {
            $m[0].disabled = !$m[0].disabled;
        }
        // 不支持 MutationObserver 的浏览器使用 JS 操作 select 以后需要手动触发 `changed.selected.amui` 事件
        if (!$.AMUI.support.mutationobserver) {
            $selected.trigger('changed.selected.amui');
        }
    });*/

    /*$selected.on('change', function() {
        $('#js-selected-info').html([
            '选中项：<strong class="am-text-danger">',
            [$(this).find('option').eq(this.selectedIndex).text()],
            '</strong> 值：<strong class="am-text-warning">',
            $(this).val(),
            '</strong>'
        ].join(''));
    });*/
}

function dateFormat(str) {
    var pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
    var formatedDate = str.replace(pattern, '$1-$2-$3 $4:$5:$6');
    return formatedDate;
}






