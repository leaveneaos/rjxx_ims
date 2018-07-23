/**
 * Created by jackwang on 12/15/15.
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $modalOriginalDetail: $('#original-detail-modal'),

        $jsSubmit: $('.js-submit'),
        $jsClose: $('.js-close'),


        $jsForm1: $('.js-form-1'),  // 原发票明细 form

        $s_jyrqq: $('#s_jyrqq'), // search 交易日期起
        $s_jyrqz: $('#s_jyrqz'), // search 交易日期止
        $s_kprqq: $('#s_kprqq'), // search 开票日期起
        $s_kprqz: $('#s_kprqz'), // search 开票日期止
        $s_gfmc: $('#s_gfmc'), // search 购房名称
        $s_ddh: $('#s_ddh'), // search 订单号
        $s_fpdm: $('#s_fpdm'), // search 购房名称
        $s_fphm: $('#s_fphm'), // search 订单号
        $xfid: $('#xfid'), // search 订单号

        $checkAll: $('#check_all'), // check all checkbox
        $jsSearch: $('.js-search'),
        $jsSent: $('.js-sent'),// sent all
        $button3: $('#button3'), 

        $jsLoading: $('.js-modal-loading')
    };
    var loaddata=false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'yjfs/getYjfsList',
            sentUrl: 'yjfs/send',
            updateUrl: 'yjfs/update',
            getDdmx:'yjfs/getDdmxList',
            checkUrl:'yjfs/check'
        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable.DataTable({
                "processing": true,
                "serverSide": true,
                ordering: false,
                searching: false,
                scrollX : true,

                "ajax": {
                    url: _this.config.getUrl,
                    type: 'POST',
                    data: function (d) {
                    $("#ddMxtable").hide();
                    $("#ddMxtable_wrapper").hide();
                       var tip = $('#tip').val();
                       var txt = $('#searchtxt').val();
                        d.loaddata=loaddata;
                       if ($('#bj').val() == 1) {
                           if (tip == "1") {
                               d.gfmc = txt;
                           }else if (tip == "2") {
                               d.ddh = txt;
                           }else if (tip == "3") {
                               d.fphm = txt;
                           }else if (tip == "4") {
                               d.fpdm = txt;
                           }else if (tip == "5") {
                               d.xfmc = txt;
                           }
                           if(txt ==null || txt ==""){
                               d.kprqq = $('#w_kprqq').val(); // search 开票日期
                               d.kprqz = $('#w_kprqz').val(); // search 开票日期
                           }
                       }else{
                           d.jyrqq = el.$s_jyrqq.val(); // search 交易日期起
                           d.jyrqz = el.$s_jyrqz.val(); // search 交易日期止
                           d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                           d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                           d.gfmc = el.$s_gfmc.val(); // search 购方名称
                           d.ddh = el.$s_ddh.val();//订单号
                           d.fpdm = el.$s_fpdm.val();//发票代码
                           d.fphm = el.$s_fphm.val();//发票号码
                           d.xfid = el.$xfid.val();//发票号码
                       }
                    }
                },
                "columns": [
                    {
                        "orderable": false,
                        "data": null,
                        //"defaultContent": '<input type="checkbox" />'
                        render: function (data, type, full, meta) {
                            return '<input type="checkbox" value="' + data.serialorder + '" />';
                        }
                    },
                    {
                        "orderable": false,
                        "data": null,
                        "defaultContent": ""
                    },
                    {
                        "data": null,
                        "render": function (data) {
                        	if(data.sfkp==1){
                        		//return '<a class="view" href="' + data.pdfurl + '"  target="_blank">查看</a> <a class="modify" title="修改接收邮件地址和手机号码">修改</a> <a class="sent">发送</a>';
                        		return '<a class="modify" title="修改接收邮件地址和手机号码">修改</a> <a class="sent">发送</a>';
                        	}else{
                        		return '<a class="modify" title="修改接收邮件地址和手机号码">修改</a>';
                        	}
                        	
                        }
                    },
                    {"data": "ddh"},
                    {"data": "jylssj"},
                    {"data": "gfmc"},
                    // {"data": "xfmc"},
                    // {"data": "kprq"},
                    // {"data": "fpdm"},
                    // {"data": "fphm"},
                    // {"data": function (data) {
                    //     if (data.je) {
                    //         return FormatFloat(data.je, "###,###.00");
                    //     } else {
                    //         return null;
                    //     }
                    // }, 'sClass': 'right'},
                    // {"data": function (data) {
                    //     if (data.se) {
                    //         return FormatFloat(data.se, "###,###.00");
                    //     } else {
                    //         return null;
                    //     }
                    // }, 'sClass': 'right'},
                    {"data": function (data) {
                        if (data.jshj) {
                            return FormatFloat(data.jshj, "###,###.00");
                        } else {
                            return null;
                        }
                    }, 'sClass': 'right'},
                    {"data": "gfemail"},
                    {"data": "gfsjh"},
                    {
                        "data": null,
                        "defaultContent": '<td>' +
                        '<label class="am-checkbox-inline"> <input class="sentType" type="checkbox" name="check" value="0"  checked>邮件</label>'+
                        '<label class="am-checkbox-inline"> <input class="sentType" type="checkbox" name="check" value="1" >短信</label></td>'
                        // '<label class="am-checkbox-inline"> <input class="sentType" type="checkbox" name="wechat" value="2" >微信</label></td>'
                    },
                    {
                    	"data":"sfkp",
                    	"sClass":'yinchang'
                    }
                ]
            });
            
            t.on("click","tr",function(){
            	var data = t.row($(this)).data();
//            	if($(this).find("td").eq(0).find("input").is(':checked')){
//            		$(this).find('td:eq(0) input').prop('checked',false);
//            	}else{
//            		$(this).find('td:eq(0) input').prop('checked',true); 
//            	}
            	
            	
            	if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    $(this).find('td:eq(0) input').prop('checked',false);
                } else {
                	$(this).find('td:eq(0) input').prop('checked',true); 
                    $(this).addClass('selected');
                }
            	
            	var params = "";
            	_this.tableEx.column(0).nodes().each(function (cell, i) {
                    if($(cell).find('input[type="checkbox"]').is(':checked')){
                    	params=params+";"+$(cell).find('input[type="checkbox"]').val();
                    }
                });
            	if(params!=""){
            		params=params.substring(1,params.length).trim();
            	}
            	
            	$("#ddMxtable").show();
            	var mx=$("#ddMxtable") .DataTable({
                    "processing": true,
                    "serverSide": true,
                    ordering: false,
                    searching: false,
                    destroy:true,
                    "ajax": {
                        url: _this.config.getDdmx,
                        type: 'POST',
                        data: function (d) {
                            d.loaddata = loaddata;
                            //d.serialorder = data.serialorder;
                            d.serialorders = params;
                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {
                        	"data": null,
                        	 "render": function (data) {
                        		 if(data.pdfurl !=null && data.pdfurl!=""){
                        			 return '<a class="view" href="' + data.pdfurl + '"  target="_blank">查看</a>';
                        		 }else{
                        			 return "";
                        		 }
                        	 }
                        },
                        {"data":"ddh"},
                        {"data": "xfmc"},
                        {"data": "fpdm"},
                        {"data": "fphm"},
                        {
	                		"data": function (data) {
	                            if (data.jshj) {
	                                return FormatFloat(data.jshj, "###,###.00");
	                            } else {
	                                return null;
	                            }
	                        }, 'sClass': 'right'
                        },
                        {"data": "kprq"}
                        ]
                });
            	
            	 mx.on('draw.dt', function (e, settings, json) {
                     var x = mx, page = x.page.info().start; // 设置第几页
                     mx.column(0).nodes().each(function (cell, i) {
                         cell.innerHTML = page + i + 1;
                     });
                 });
            	
            	
            	
            	
            	
            });
            
            t.on('draw.dt', function (e, settings, json) {
                var x = t,
                    page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });


            //
            t.on('click', 'a.modify', function () {
                var data = t.row($(this).parents('tr')).data();
                data.rowid = t.row($(this).parents('tr')).index();
                _this.setForm1(data);
                el.$modalOriginalDetail.modal('open');

            });

            t.on('click', 'a.sent', function () {
                var data = t.row($(this).parents('tr')).data(),
                    $sentType = null,
                    row = '',sends="";

                var groupCheckbox=$(this).closest("tr").find($("input[name='check']"));
                for(var i=0;i<groupCheckbox.length;i++){
                    if(groupCheckbox[i].checked){
                        var val =groupCheckbox[i].value;
                        if(val=="0"){
                            if (data.gfemail == null || data.gfemail == "") {
                                swal('没有邮箱，请增加邮箱后在发送');
                                return;
                            }
                        }else if(val=="1"){
                            if(data.gfsjh == null || data.gfsjh==""){
                                swal('没有手机号码，请增加手机号码后在发送');
                                return;
                            }
                        }
                        sends +=val;
                    }
                }
                $(this).parents('tr').find('.sentType').each(function (i, el) {
                    $sentType = $(el);
                    if ($sentType.is(':checked')) {
                        if (i === 0) { // 拼接 选中的数据
                            row = row + $sentType.val();
                        } else {
                            row = row + '-' + $sentType.val(); // split -
                        }
                    }
                });
                if(sends==null || sends==""){
                    swal('发送失败,请选择发票接收方式');
                    return;
                }
                // todo set key
                var s = data.serialorder ;
                _this.sentEmail(s,sends);
            });

            return t;
        },
        /**
         * 发送 email request
         * @param data { ids: '1,2,3,4' }
         */
        sentEmail: function (data,sends) {
            sends=(sends.substring(sends.length-1)==',')?sends.substring(0,sends.length-1):sends;
            $('.confirm').attr('disabled',"disabled");
            var _this = this;
            el.$jsLoading.modal('open');
            if(data.ids==''){
                // $('#msg').html('请先选择至少一条数据');
                // $('#my-alert').modal('open');
                swal('请先选择至少一条数据');
                el.$jsLoading.modal('close');
                return;
            }
           $.ajax({
                url: _this.config.sentUrl,
                data: {"ids":data,"st":sends},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    // todo 处理返回结果
                    if (data.statu === '0') {
                        // $('#msg').html(data.msg);
                        // $('#my-alert').modal('open');
                        $('.confirm').removeAttr('disabled');
                        swal(data.msg);
                    } else {
                        // $('#msg').html('发送失败,服务器错误' + data.message);
                        // $('#my-alert').modal('open');
                        swal('发送失败:' + data.msg);
                    }
                    el.$jsLoading.modal('close');
                },
                error: function () {
                    // $('#msg').html('请求失败,请刷新后稍后重试!');
                    // $('#my-alert').modal('open');
                    swal('请求失败,请刷新后稍后重试!');
                }
            });
        },
        /**
         * search action
         */
        searchAc: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                var dt1 = new Date($("#w_kprqq").val().replace(/-/g, "/"));
                var dt2 = new Date($("#w_kprqz").val().replace(/-/g, "/"));
                if (($("#w_kprqq").val() && $("#w_kprqz").val())) {// 都不为空
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
                e.preventDefault();
                $('#bj').val('1');
                loaddata=true;
                _this.tableEx.ajax.reload();
            });
            el.$button3.on('click', function (e) {
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
                            // alert('月份不同,Error!');
                            swal('Error,请选择同一个年月内的时间!');
                            return false;
                        }
                    } else {
                        // alert('年份不同,Error!');
                        swal('Error,请选择同一个年月内的时间!');
                        return false;
                    }
                }
                e.preventDefault();
                $('#bj').val('2');
                loaddata=true;
                _this.tableEx.ajax.reload();
            });
        },
        /**
         * check all action
         */
        checkAllAc: function () {
            var _this = this;
            el.$checkAll.on('change', function (e) {
                var $this = $(this),
                    check = null;
                if ($(this).is(':checked')) {
                	//全选判断，如果未全部开票不能选中
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                    	//var sfkp=$(cell).parent("tr").find("td:last-child").html().trim();
                    	//if(sfkp==1){
                    	$(cell).find('input[type="checkbox"]').prop('checked', true);
                    	//}
                    });
                } else {
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
                var ids ="";
                _this.tableEx.column(0).nodes().each(function (cell, i) {
                	if($(cell).find('input[type="checkbox"]').is(':checked')){
                		ids=ids+";"+$(cell).find('input[type="checkbox"]').val();
                		
                	}
                });
                
                	$("#ddMxtable").show();
                	var mx=$("#ddMxtable") .DataTable({
                        "processing": true,
                        "serverSide": true,
                        ordering: false,
                        searching: false,
                        destroy:true,
                        "ajax": {
                            url: _this.config.getDdmx,
                            type: 'POST',
                            data: function (d) {
                                d.loaddata = loaddata;
                                //d.serialorder = data.serialorder;
                                d.serialorders = ids.trim();
                            }
                        },
                        "columns": [
                            {
                                "orderable": false,
                                "data": null,
                                "defaultContent": ""
                            },
                            {
                            	"data": null,
                            	 "render": function (data) {
                            		 if(data.pdfurl !=null && data.pdfurl!=""){
                            			 return '<a class="view" href="' + data.pdfurl + '"  target="_blank">查看</a>';
                            		 }else{
                            			 return "";
                            		 }
                            	 }
                            },
                            {"data":"ddh"},
                            {"data": "xfmc"},
                            {"data": "fpdm"},
                            {"data": "fphm"},
                            {
    	                		"data": function (data) {
    	                            if (data.jshj) {
    	                                return FormatFloat(data.jshj, "###,###.00");
    	                            } else {
    	                                return null;
    	                            }
    	                        }, 'sClass': 'right'
                            },
                            {"data": "kprq"}
                            ]
                    });
                	 mx.on('draw.dt', function (e, settings, json) {
                         var x = mx, page = x.page.info().start; // 设置第几页
                         mx.column(0).nodes().each(function (cell, i) {
                             cell.innerHTML = page + i + 1;
                         });
                     });
                	 
            });
        },
        /**
         * 发送全部选中的
         */
        sentAllAc: function () {
            var _this = this;
            el.$jsSent.on('click', function (e) {
                e.preventDefault();
                var msg ='';
                var data = '',
                    row = '',
                    $tr = null,
                    $sentType = null;
                var sendTy="";
                var msg2="";
                _this.tableEx.column(0).nodes().each(function (cell, i) {
                    row = '';
                    var $checkbox = $(cell).find('input[type="checkbox"]');
                    if ($checkbox.is(':checked')) {
                    	 var sffs = $(cell).parent("tr").find("td:last-child").html().trim();
                         var ddh = $(cell).parent("tr").find("td:eq(3)").html().trim();
                         if(sffs==0){
                         	msg=msg+","+ddh;
                         }
                        // todo 设置数据格式 使用 id:发送方式-发送方式-发送方式,id:发送方式-发送方式 形式
                        // 1:0-1-2,2:2,3:0-2 数据格式
                        row = $checkbox.val(); // set id; 使用 : 分割 id 和 发送方式
                        var s = "";
                        $checkbox.parents('tr').find('.sentType').each(function (j, el) {
                            $sentType = $(this);
                            if ($sentType.is(':checked')) {
                                s+=$sentType.val();
                                /*if (j === 0) { // 拼接 选中的数据
                                    row = row + $sentType.val();
                                } else {
                                    row = row + '-' + $sentType.val(); // split -
                                }*/
                            }
                        });
                        if(s==""){
                            msg2+="第"+(i+1)+"行，请选择发票接收方式\r\n";
                        }
                        sendTy+= s+",";
                        // if (i === 0) { // 拼接 选中的数据
                        //     data = row;
                        // } else {
                        //     data = data + ',' + row;
                        // }
                        data +=  row+",";
                    }

                });
                //开始
                if(msg!=''){
                	swal("订单号【"+msg.substring(1,msg.length)+"】存在未开发票");
                	return;
                }else if(msg2!=""){
                    swal(msg2);
                    return;
                }else{
                    //发送
                    var s ={ids: data}.ids;
                    s=(s.substring(s.length-1)==',')?s.substring(0,s.length-1):s;
                    _this.sentEmail(s,sendTy);
                }

            });
        },
        saveRow: function () {
            var _this = this;
            el.$jsForm1.validator({
                submit: function (e) {
                    e.preventDefault();

                    var formValidity = this.isFormValid();
                    if (formValidity) {
                    	var email = $("#gfemail").val();
                    	var sj = $("#sj").val();
                    	var reg=/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
                    	var reg1=/^[1][3,4,5,7,8][0-9]{9}$/;
                        if(!reg.test(email)){
                            $("#gfemail").focus();
                            swal("请填写有效邮箱");
                        	return false;
                        }
                        if(!reg1.test(sj)){
                            $("#sj").focus();
                            swal("请填写有效的手机号码");
                            return false;
                        }
                        var data = el.$jsForm1.serialize(); // get form data data
                        el.$jsLoading.modal('open');  // show loading
                        // TODO save data to serve
                        $.ajax({
                            url: _this.config.updateUrl,
                            data: data,
                            method: 'POST',
                            success: function (data) {
                                if (data.statu === '0') {

                                    // update row data
                                    var rowId = el.$jsForm1.find('[name="rowId"]').val(),
                                        row = _this.tableEx.row(rowId),
                                        d = row.data();
                                    d.yx = el.$jsForm1.find('[name="yx"]').val();
                                    d.sj = el.$jsForm1.find('[name="sj"]').val();
                                    row.data(d).draw();
                                    swal("保存成功");
                                    el.$modalOriginalDetail.modal('close');  // close modal

                                    _this.tableEx.ajax.reload(); // reload table data

                                } else {
                                    // $('#msg').html('后台错误: 保存数据失败' + data.message);
                                    // $('#my-alert').modal('open');
                                    swal('后台错误: 保存数据失败' + data.message);
                                }


                                el.$jsLoading.modal('close'); // close loading

                            },
                            error: function () {
                                // $('#msg').html('保存数据失败, 请刷新后再试...!');
                                // $('#my-alert').modal('open');
                                swal('保存数据失败, 请刷新后再试...!');
                            }
                        });

                        return false;
                    } else {
                        // $('#msg').html('验证失败');
                        // $('#my-alert').modal('open');
                        swal('验证失败');
                        return false;
                    }
                }
            });
        },
        setForm1: function (data) {
            var _this = this,
                i;
            el.$jsForm1.find('input[name="gfemail"]').val(data.gfemail);
            el.$jsForm1.find('input[name="sj"]').val(data.gfsjh);
            el.$jsForm1.find('input[name="wx"]').val(''); // 你决定放什么
            el.$jsForm1.find('input[name="serialorder"]').val(data.serialorder);
            el.$jsForm1.find('input[name="rowId"]').val(data.rowid); // set rowid
        },
        resetForm: function () {
            el.$jsForm1[0].reset();

        },
        modalAction: function () {
            var _this = this;
            el.$modalOriginalDetail.on('closed.modal.amui', function () {
                el.$jsForm1[0].reset();
            });

            // close modal
            el.$jsClose.on('click', function () {
                el.$modalOriginalDetail.modal('close');
            });
        },
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable

            _this.searchAc();
            _this.checkAllAc();
            _this.sentAllAc();

            _this.saveRow();
            _this.modalAction(); // hidden action


        }
    };

    action.init();

});