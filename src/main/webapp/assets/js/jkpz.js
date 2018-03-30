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
                            "orderable" : false,
                            "data" : null,
                            "defaultContent" : ""
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
                        {
                            "data": "gsmc"
                        },
                        {
                            "data": "mbmc"
                        },
                        {
                            "data": "mbms"
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
                "ajax": {
                    url: _this.config.getInfoUrl,
                    type: 'POST',
                    data: function (d) {
                        d.mbid = $("#mbid").val();
                        d.loaddata=loaddata;
                    }
                },
                "columns": [
                    {"data": "id"},
                    {"data": "mbid"},
                    {"data": "cszff"},
                    {"data":"csm"},
                    {"data":"pzcsm"},
                    {"data":"pzcsmc"}
                ]
            });
            //分页
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {// 序号
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
                // 授权---数据
                _this.sq(da);
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
            $('#search1').click(function () {
                $("#dxcsz").val("");
                t.ajax.reload();
            });
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
                    "id": da.id
                },
                method: 'POST',
                success: function (data) {
                    if (data.success) {
                        // modal
                        swal(data.msg);
                        debugger
                        // 授权信息传递

                    } else {
                        swal('查看失败: ' + data.msg);
                    }
                },
                error: function () {
                    swal('查询信息失败, 请重新查看...!');
                }
            });
        },

        /**
         * search action
         */
        search_ac : function() {
            var _this = this;
            el.$jsSearch.on('click', function(e) {
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
            var _this = this;
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
            //_this.exportAc();
            _this.modalAction(); // hidden action
        }
    };
    action.init();
});


function dateFormat(str) {
    var pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
    var formatedDate = str.replace(pattern, '$1-$2-$3 $4:$5:$6');
    return formatedDate;
}






