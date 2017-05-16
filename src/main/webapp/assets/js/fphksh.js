/**
 * feel
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('.js-table'),
        $jsAgree:$('.js-agree'),
        $jsDisAgree:$('.js-disagree'),
        $jsSubmit: $('.js-submit'), // 换开提交
        $jsClose: $('.js-close'), // 关闭 modal
        $s_gfmc: $('#s_gfmc'), // search 购方名称
        $s_ddh: $('#s_ddh'), // search
        $s_ztbz: $('#s_ztbz'), // search
        $jsSearch: $('.js-search'),
        $jsModal:$('#disagreeModal'),
        $jsLoading: $('.js-modal-loading')
    };
  //开票商品明细table
    var kpls_table = $('#mxTable').DataTable({
        "searching": false,
        "serverSide": true,
        "sServerMethod": "POST",
        "processing": true,
        "bPaginate":false,
        "bLengthChange":false,
        "bSort":false,
        "bInfo": false,
        "scrollX": true,
        ajax: {
            "url": "fpcksh/getKpMx",
            data: function (d) {
                d.djh = $("#djh").val();
            }
        },
        "columns": [           
            {"data": "gfmc"},
            {"data": "fpdm"},
            {"data": "fphm"},           
            {"data":"kprq"},
            {
              "data": function (data) {
                     if (data.hjje) {
                         return FormatFloat(data.hjje,
                             "###,###.00");
                     }else{
                         return null;
                     }
                 },
                 'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.hjse) {
                    return FormatFloat(data.hjse,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            },
            { 
                "data": function (data) {
                if (data.jshj) {
                    return FormatFloat(data.jshj,
                        "###,###.00");
                }else{
                    return null;
                }
            },
            'sClass': 'right'
            },
            {"data":"fpzt"},
            {
                "data": null,
                "render": function (data) {
                    return '<a href="'+ data.pdfurl+'" target="_blank">查看</a>';                                                         
                }
            }
        ]
    });
    
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fphksh/getJylsList',
            hkUrl: 'fphksh/qrhk'
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
                            d.ztbz = el.$s_ztbz.val(); // search 
                            d.gfmc = el.$s_gfmc.val(); // search 购方名称
                            d.ddh = el.$s_ddh.val();
                        }
                    },
                    "columns": [
                        {
                            "orderable": false,
                            "data": null,
                            "defaultContent": ""
                        },
                        {"data": "ddh"},
                        {"data": "gfmc"},                     
                        {
                            "data": function (data) {
                                if (data.jshj) {
                                    return FormatFloat(data.jshj,
                                        "###,###.00");
                                }else{
                                    return null;
                                }
                            },
                            'sClass': 'right'
                        },                       
                        {"data":"ckhkyy"},
                        {"data":"sqsj"}
                        ]
                });
            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(0).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });              
            });
          //选中列查询明细
            t.on('click', 'tr', function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    t.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
                $(this).css("background-color", "#B0E0E6").siblings().css("background-color", "#FFFFFF");  
                var data = t.row($(this)).data();
                $("#djh").val(data.djh);
                $("#sqid").val(data.sqid);
                kpls_table.ajax.reload();
            });           
            return t;
        },
        
        /**
         * 确认换开
         */
        saveRow: function () {
            var _this = this;
            el.$jsAgree.on('click',function(){
                var djh = $('#djh').val();
                var sqid = $('#sqid').val();
                if(djh==''){
                    swal("请先选择一条记录！");
                }else{
                    $.ajax({
                        url:_this.config.hkUrl,
                        data:{"djh":djh,"sqid":sqid},
                        success:function(data){
                            if(data.success){
                                swal(data.msg);
                                $("#djh").val("");
                                $("#sqid").val("");
                                e.preventDefault();
                                _this.tableEx.ajax.reload();
                                kpls_table.ajax.reload();
                            }else{
                                swal(data.msg);
                            }
                            el.$jsLoading.modal('close'); // close loading
                        }
                    });
                }
            })
        },
        
        resetForm: function () {
            el.$jsForm0[0].reset();
        },
        modalAction: function () {
            var _this = this;
            el.$jsModal.on('closed.modal.amui', function () {
                $("#ckbtgyy").val("");
            });
            // close modal
            el.$jsClose.on('click', function () {
                el.$jsModal.modal('close');                
            });
            //撤销弹出页面
            el.$jsDisAgree.on('click',function(){
               var sqid=$('#sqid').val();
               if(sqid==''){
                   swal("请选选择一条记录！");
               }else{
                   el.$jsModal.modal({"width": 500, "height": 240});
               }           
            });
            //撤销操作
            el.$jsSubmit.on('click',function(e){
                    var sqid = $("#sqid").val();
                    var yy = $('#ckbtgyy').val();
                    if(yy!=''){
                        $.ajax({
                            url:'fphksh/update',
                            data:{"id":sqid,"reason":yy},
                            success:function(data){
                                if(data.success){
                                        swal(data.msg);                                  
                                     $("#djh").val("");
                                     $("#sqid").val("");
                                     el.$jsModal.modal('close');
                                     e.preventDefault();
                                    _this.tableEx.ajax.reload();
                                    kpls_table.ajax.reload();
                                }else{
                                 swal(data.msg);
                                }
                            }
                        });  
                    }else{
                        swal("撤销请填写原因！");
                    }                      
            });
        },
        searchAc: function () {
            var _this = this;
            el.$jsSearch.on('click', function (e) {
                $("#djh").val("");
                $("#sqid").val("");
                e.preventDefault();
                _this.tableEx.ajax.reload();
                kpls_table.ajax.reload();

            });
        },
        init: function () {
            var _this = this;
            _this.tableEx = _this.dataTable(); // cache variable
            _this.saveRow();
            _this.modalAction(); // hidden action
            _this.searchAc();
        }
    };
    action.init();

});