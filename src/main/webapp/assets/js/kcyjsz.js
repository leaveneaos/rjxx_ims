/**
 * feel
 */
$(function () {
    "use strict";
    var ur ;
    var urls;
    var el = {
    	$jsTable: $('.js-table'),//查询table
    	$jsForm:$('.js-form-yjsz'),
        $jsSearch: $('#button1'),//查询按钮
        $jsTable1: $('#button2'),//设置按钮
        $modalHongchong : $('#hongchong'),
        $xfid: $('#xfid'),//销方税号
        $jsdiv:$("#shezhi"),
        $jssave:$(".js-submit"),
        $jsClose:$('.js_close'),
        $checkAll: $('#selectAll'),
        $jsLoading: $('.js-modal-loading'),//输在载入特效
        $addModal:$("#bulk-add-div")
    };
    var loaddata = false;

    //新增预警值设置
    $("#button3").click(function () {
        $('#addDataForm').resetForm();
        el.$addModal.modal({"width": 600, "height": 350});
    });

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'kcyjsz/getItems',
            szUrl: 'kcyjsz/update',
            plszUrl:'kcyjsz/plupdate',
            xzszUrl: 'kcyjsz/addtzfs',
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
                        	 $("#xfidhide").val($("#xfid").val());
                        	 var bz = $('#searchbz').val();
                             d.loaddata = loaddata;
                        	 if(bz=='1'){
                        		 d.xfid= el.$xfid.val(); // search                           
                                 d.skpid = $("#s_skpid").val();
                                 d.fpzldm = $("#s_fplx").val();

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
                            render: function (data, type, full, meta) {
                                 return '<input type="checkbox" value="'+data.id+'"/>';
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
                                return '<a href="#" class="modify1" style="margin-right: 10px;">修改</a>'
                            }
                        },
                        {"data": "xfmc"},
                        {"data": "xfsh"},
                        {"data": "skph"},
                        {"data": "kpdmc"},
                        {"data":"fpzlmc"},
                        {"data":function(data){
                                var kpfs = data.kpfs;
                                switch (kpfs) {
                                    case '01':
                                        kpfs = '2.0客户端';
                                        break;
                                    case '02':
                                        kpfs = '1.0客户端';
                                        break;
                                    case '03':
                                        kpfs = '百旺税控服务器或盘阵';
                                        break;
                                    case '04':
                                        kpfs = 'Q200盒子';
                                        break;
                                    case '05':
                                        kpfs = 'E开票';
                                        break;
                                }
                                return kpfs;
                            }},
                        {"data": "yjyz"},
                        {"data": "csz"},/*
                        {
                            "data": null,
                            "render": function (data) {
                                return '<a class="shezhi">设置</a>'
                            }
                        }*/]
                });
            	   
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });
                $('#yjdytable tr').find('td:eq(10)').hide();
            });

            // 设置通知方式
            el.$jsTable1.on('click', el.$jsTable1, function() {
                //_this.resetForm();
                var tableids ="";
                var count =0;
                var flag = true;
                _this.tableEx.column(0).nodes().each(function (cell,i) {
                    var checkbox = $(cell).find('input[type="checkbox"]');
                    if(checkbox.is(':checked')){
                        count++;
                        tableids+=checkbox.val()+',';
                        var row = t.row(i).data();
                        if(null ==row.yjyz || row.yjyz ==''){
                            flag = false;
                            return;
                        }
                    }
                });
                if(flag == false){
                    swal("只能设置已有库存预警值的记录！");
                    return;
                }
                if(tableids =='' ){//|| count>1
                    swal("请选择一条记录且最多一条数据！");
                    return;
                }else{
                    $("#yjids").val(tableids.substr(0,(tableids.length-1)));
                    ur = _this.config.xzszUrl
                }
                el.$modalHongchong.modal({
                    "width" : 600,
                    "height" : 450
                });
            });

            el.$jsTable.on('click', 'a.modify1', function () {
                var row = t.row($(this).parents('tr')).data();
                $("#yjkcl").val(row.yjyz);
                $("#xg_id").val(row.id);
                if(row.csz =='03' || row.csz =='04'){
                    el.$jsdiv.modal('open');
                }else{
                    swal("目前只支持税控服务器和税空盒子开票方式库存预警！");
                    return;
                }

                urls =_this.config.szUrl + '?xfid=' + row.xfid+'&skpid='+row.skpid+'&fpzldm='+row.fpzldm+'&csz='+row.csz+'&skph='+row.skph;
            });


            $("#xfid").change(function() {
                $('#s_skpid').empty();
                var xfid = $(this).val();
                if (xfid == null || xfid == '' || xfid == "") {
                    return;
                }
                var url = "kcyjsz/getSkpList";
                $.ajax({
                    type : "POST",
                    url : url,
                    data : {
                        xfid : xfid
                    },
                    success : function(data) {
                        if (data) {
                            var option = $("<option>").text('请选择').val(-1);
                            $('#s_skpid').append(option);
                            for (var i = 0; i < data.skps.length; i++) {
                                option = $("<option>").text(data.skps[i].kpdmc).val(data.skps[i].id);
                                $('#s_skpid').append(option);
                            }
                        }
                    }
                });
            });

            $("#add_xfid").change(function() {
                $('#add_skp').empty();
                var xfid = $(this).val();
                if (xfid == null || xfid == '' || xfid == "") {
                    return;
                }
                var url = "kcyjsz/getSkpList";
                $.ajax({
                    type : "POST",
                    url : url,
                    data : {
                        xfid : xfid
                    },
                    success : function(data) {
                        if (data) {
                            var option = $("<option>").text('请选择').val(-1);
                            $('#add_skp').append(option);
                            for (var i = 0; i < data.skps.length; i++) {
                                option = $("<option>").text(data.skps[i].kpdmc).val(data.skps[i].id);
                                $('#add_skp').append(option);
                            }
                        }
                    }
                });
            });


            $("#add_skp").change(function() {
                $('#add_fpzldm').empty();
                var skpid = $(this).val();
                if (skpid == null || skpid == '' || skpid == "") {
                    return;
                }
                var url = "kcyjsz/getFpzlList";
                $.ajax({
                    type : "POST",
                    url : url,
                    data : {
                        skpid : skpid
                    },
                    success : function(data) {
                        if (data) {
                            var option = $("<option>").text('请选择').val(-1);
                            $('#add_fpzldm').append(option);
                            for (var i = 0; i < data.fpzls.length; i++) {
                                option = $("<option>").text(data.fpzls[i].fpzlmc).val(data.fpzls[i].fpzldm);
                                $('#add_fpzldm').append(option);
                            }
                            $('#add_kpfs').val(data.kpfs);
                            $('#show_kpfs').val(data.show_kpfs);
                        }
                    }
                });
            });


            //预警设置新增方法
            /*$("#btadd").click(function () {
                var xfid = $("#add_xfid").val();
                var skpid = $("#add_skp").val();
                var fpzldm = $("#add_fpzldm").val();
                var yjyz = $("#add_yjyz").val();
                var kpfs = $("#add_kpfs").val();
                if (!xfid) {
                    swal("请选择需要预警的销方");
                    return;
                }
                if (skpid==-1 || skpid =='-1' || skpid== '') {
                    swal("请选择需要预警的开票点");
                    return;
                }
                if (fpzldm==-1 || fpzldm =='-1' || fpzldm== '') {
                    swal("请选择需要预警的票种");
                    return;
                }
                if (!yjyz) {
                    swal("请填写预警值");
                    return;
                }
                if(kpfs!='03'){
                    swal("目前仅支持对\"百旺-服务器或盘组\"开票方式进行预警！");
                    return;
                }
                $("#btadd").attr("disabled", true);
                //$('.js-modal-loading').modal('open');
                var url = "kcyjsz/addyjz";
                $.ajax({
                    type : "POST",
                    url : url,
                    data : {
                        xfid:xfid,
                        skpid : skpid,
                        fpzldm:fpzldm,
                        yjkcl:yjyz,
                        kpfs:kpfs
                    },
                    success : function(data) {
                        if (data.success) {
                            el.$addModal.modal('close');
                            //$("#btadd").attr("disabled", false);
                            //$('.js-modal-loading').modal('close');
                            swal(data.msg);
                            _this.tableEx.ajax.reload();
                        }else{
                            $("#btadd").attr("disabled", false);
                            //$('.js-modal-loading').modal('close');
                            swal(data.msg);
                        }
                    }
                });
            });*/

            // 单个设置
            /*t.on('click', 'a.shezhi', function () {
                var row = t.row($(this).parents('tr')).data();       
                $("#yjkcl").val(row.yjkcl);
                el.$jsdiv.modal('open');
                ur =_this.config.szUrl + '?xfid=' + row.xfid+'&skpid='+row.skpid+'&fpzldm='+row.fpzldm;   			 
            });*/
            return t;
        },
        /**
         * search action
         */
        search_ac: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
            	$('#searchbz').val("1");
                e.preventDefault();
                loaddata = true;
                _this.tableEx.ajax.reload();
                $("#xfidhide").val($("#xfid").val());
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
        resetForm: function () {
            el.$jsForm[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$jsdiv.on('closed.modal.amui', function () {
                el.$jsForm[0].reset();
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$jsdiv.modal('close');
            });
            $("#btclose").on('click', function () {
                el.$addModal.modal('close');
            });
        },
      //全选按钮
        checkAllAc: function () {
            var _this = this;
            el.$checkAll.on('change', function (e) {
                var $this = $(this),
                    check = null;
                if ($(this).is(':checked')) {
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', true);
                    });
                } else {
                    _this.tableEx.column(0).nodes().each(function (cell, i) {
                        $(cell).find('input[type="checkbox"]').prop('checked', false);
                    });
                }
            });
        },

        /**
         * 预警用户通知设置
         */

        xz : function() {
            var _this = this;
            $("#fomm").validator({
                submit : function() {
                    var formValidity = this.isFormValid();
                    var tzfss = document.getElementsByName("tzfsid");
                    var tzyhids = document.getElementsByName("yhid");
                    var fl = false;
                    var ag = false;
                    for (var i = 0; i < tzfss.length; i++) {
                        if (tzfss[i].checked == true) {
                            fl = true;
                            break;
                        }
                    }
                    if (fl == false) {
                        swal("请选择通知方式!");
                        return false;
                    }
                    for (var i = 0; i < tzyhids.length; i++) {
                        if (tzyhids[i].checked == true) {
                            ag = true;
                            break;
                        }
                    }
                    if(ag == false){
                        swal("请选择需要通知的用户!");
                        return false;
                    }
                    if (formValidity) {
                        var data = $("#fomm").serialize(); // get form data
                        // data
                        // TODO save data to serve
                        $.ajax({
                            url : ur,
                            data : data,
                            method : 'POST',
                            success : function(data) {
                                if (data.success) {
                                    el.$jsLoading.modal('close'); // close
                                    // loading
                                    el.$modalHongchong.modal('close'); // close
                                    // modal
                                    // $('#msg').html(data.msg);
                                    // $('#my-alert').modal('open');
                                    swal(data.msg);
                                    _this.tableEx.ajax.reload(); // reload
                                    // table
                                } else {
                                    el.$jsLoading.modal('close');
                                    // $('#msg').html(data.msg);
                                    // $('#my-alert').modal('open');
                                    swal(data.msg);

                                }
                            },
                            error : function() {
                                el.$modalHongchong.modal('close'); // close
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
            _this.search_ac();
            _this.modalAction(); // hidden action
            _this.checkAllAc();
            _this.find_mv();
            _this.xz();
            _this.xgyjz();
        }
    };
    action.init();
    

});