/**
 * feel
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('#fphk_table'),
        $modalhuankai: $('#fphkym'),
        //$modalExport: $('#export'), // 导出 modal

        //$jsExportSubmit: $('.js-export-submit'), // 导出 submit
        $jsSubmit: $('#js-submit'), // 换开提交
        $jsClose: $('#js-close'), // 关闭 modal

        $jsForm0: $('#main_form'), // 换开 form
        //$jsForm1: $('.js-form-1'), // 导出 form
        $s_kprqq: $('#s_rqq'), // search 开票日期起
        $s_kprqz: $('#s_rqz'), // search 开票日期止
        $s_gfmc: $('#s_gfmc'), // search 购方名称
        $s_fpdm: $('#s_fpdm'), // search
        $s_ddh: $('#s_ddh'), // search
        $s_fphm: $('#s_fphm'), // search
        $hkSearch1:$('#hk_search1'),
        $hkSearch:$('#hk_search'),

        $hk: $('#fp_hk'),
        //$jsExport: $('.js-export'),

        $jsLoading: $('.js-modal-loading')
    };
    var loaddata=false;
    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'fphk/getKplsList',
            hkUrl: 'fphk/ykfphk',
            exportUrl: '/export/fphk'

        },

        dataTable: function () {
            var _this = this;

            var t = el.$jsTable
                .DataTable({
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

                            d.kprqq = el.$s_kprqq.val(); // search 开票日期起
                            d.kprqz = el.$s_kprqz.val(); // search 开票日期止
                            d.fphm = el.$s_fphm.val(); // search 发票号码
                            d.fpdm = el.$s_fpdm.val();
                            d.gfmc = el.$s_gfmc.val();
                            d.ddh = el.$s_ddh.val();

                            var csm =  $('#dxcsm').val()
                            if("gfmc"==csm&&(d.gfmc==null||d.gfmc=="")){ //购方名称
                              d.gfmc = $('#dxcsz').val()
                          }else if("ddh"==csm&&(d.ddh==null||d.ddh=="")){//订单号
                              d.ddh = $('#dxcsz').val()
                          }
                            d.loaddata=loaddata;
                        }

                    },
                    "columns": [
            {
              "orderable" : true,
              "data" : null,
              render : function(data, type, full, meta) {
                return '<input type="checkbox" value="'
                  + data.kplsh + '" name="chk"  id="chk"/>';
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
                                return '<a href="'+ data.pdfurl+'" target="_blank">查看</a> ';                
                            }
                        },
                        {"data": "ddh"},
                        {"data": "kprq"},
                        {"data": "gfmc"},
                        {"data": "fpdm"},
                        {"data": "fphm"},
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
                        }]
                });

            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
                t.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = page + i + 1;
                });

            });

            // 换开
         /*   t.on('click', 'a.huankai', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setForm0(data);
                el.$modalhuankai.modal({"width": 820, "height": 500});


            });*/

            t.on('click', 'input#chk', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setForm0(data);
                var kplsh = [];
                $('input[name="chk"]:checked').each(function(){    
                 kplsh.push($(this).val()); 
                });
                if(kplsh.length >1){
                  // $("#alertt").html("不能批量换开！");
                  // $("#my-alert").modal('open');
                  swal("不能批量换开！");
                    $('input[type="checkbox"]').prop('checked', false);

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
            return t;
        },
        /**
         * 导出
         */
       /* saveExport: function () {
            var _this = this;
            el.$jsExportSubmit.on('click', function () {
                if (!$('#e_dcsjq').val() || !$('#e_dcsjz').val()) {
                    alert('请输入开始和结束时间!');
                    return false;
                }
                var data = el.$jsForm1.serialize(); // get data

                // todo 下载链接
                var url = 'https://datatables.net/releases/DataTables-1.10.10.zip?' + data;
                window.open(url);
            });
        },*/
        
        /**
         * 查询
         */
        hksearch: function(){
            var _this = this;

           $('#hk_search').click(function () {
              $("#ycform").resetForm();
               loaddata=true;
                _this.tableEx.ajax.reload();
             });
             $('#hk_search1').click(function () {
              $("#dxcsz").val("");
                 loaddata=true;
              /* if ((!el.$s_kprqq.val() && el.$s_kprqz.val())
                          || (el.$s_kprqq.val() && !el.$s_kprqz.val())) {
                          alert('Error,请选择开始和结束时间!');
                          return false;
                      }
                      var dt1 = new Date(el.$s_kprqq.val().replace(/-/g, "/"));
                      var dt2 = new Date(el.$s_kprqz.val().replace(/-/g, "/"));
                      if ((el.$s_kprqq.val() && el.$s_kprqz.val())) {// 都不为空
                          if (dt1.getYear() == dt2.getYear()) {
                              if (dt1.getMonth() == dt2.getMonth()) {
                                  if (dt1 - dt2 > 0) {
                                      alert('开始日期大于结束日期,Error!');
                                      return false;
                                  }
                              } else {
                                  // alert('月份不同,Error!');
                                  alert('Error,请选择同一个年月内的时间!');
                                  return false;
                              }
                          } else {
                              // alert('年份不同,Error!');
                              alert('Error,请选择同一个年月内的时间!');
                              return false;
                          }
                      }*/
              _this.tableEx.ajax.reload();
             });
        },
        /**
         * 换开
         */
        saveRow: function () {
            var _this = this;
            
            $("#huankai").click(function(){
             var kplsh = [];
                $('input[name="chk"]:checked').each(function(){    
                 kplsh.push($(this).val()); 
                });
                if (kplsh.length == 0) {
                  // $("#alertt").html("请勾选需要换开的开票流水");
                  // $("#my-alert").modal('open');
                  swal("请勾选需要换开的开票流水");
                    return;
                }else if(kplsh.length == 1){
                   $("#dybz").val("0");
                    el.$modalhuankai.modal({"width": 820, "height": 500});

                    el.$jsForm0.validator({
                        submit: function () {
                          //hidespan();
                            var formValidity = $("#main_form").validator("isFormValid");
                            if (formValidity) {
                                el.$jsLoading.modal('toggle'); // show loading
                                var data = el.$jsForm0.serialize(); // get form data
                                 $.ajax({
                                    url: _this.config.hkUrl,
                                    data: data,
                                    method: 'POST',
                                    success: function (data) {
                                        if (data.success) {
                                            el.$modalhuankai.modal('close'); // close
                                           //  $("#alertt").html(data.msg);
                                            // $("#my-alert").modal('open');
                                            swal(data.msg);
                                            _this.tableEx.ajax.reload(); // reload table
                                        } else {
                                          // $("#alertt").html(data.msg);
                                          // $("#my-alert").modal('open');
                                          swal(data.msg);
                                        }
                                        _this.tableEx.ajax.reload(); // reload table
                                        el.$jsLoading.modal('close'); // close loading

                                    },
                                    error: function () {
                                     //  $("#alertt").html('换开操作失败, 请重新登陆再试...!');
                                      // $("#my-alert").modal('open');
                                      swal('换开操作失败, 请重新登陆再试...!');
                                    }
                                });
                                return false;
                            } else {
                                return false;
                            }
                        }
                    });
                

                }else if(kplsh.length >1){
                  // $("#alertt").html("不能批量换开！");
                  // $("#my-alert").modal('open');
                  swal("不能批量换开！");
                  $('input[type="checkbox"]').prop('checked', false);

                }
              
            
         });
            
          /*  $("#huankaidy").click(function(){
               var kplsh = [];
                 $('input[name="chk"]:checked').each(function(){    
                   kplsh.push($(this).val()); 
                 });
                 if (kplsh.length == 0) {
                  $("#alertt").html("请勾选需要换开的开票流水");
                  $("#my-alert").modal('open');
                     return;
                 }else if(kplsh.length == 1){
                   $("#dybz").val("2");
                     el.$modalhuankai.modal({"width": 820, "height": 500});
                     el.$jsForm0.validator({
                         submit: function () {
                          //hidespan();
                             var formValidity = $("#main_form").validator("isFormValid");
                             if (formValidity) {
                                 el.$jsLoading.modal('toggle'); // show loading
                                 var data = el.$jsForm0.serialize(); // get form data
                                  $.ajax({
                                     url: _this.config.hkUrl,
                                     data: data,
                                     method: 'POST',
                                     success: function (data) {
                                         if (data.success) {
                                             el.$modalhuankai.modal('close'); // close
                                             alert(data.msg);

                                         } else {
                                             alert(data.msg);
                                         }
                                         _this.tableEx.ajax.reload(); // reload table
                                         el.$jsLoading.modal('close'); // close loading

                                     },
                                     error: function () {
                                         alert('换开操作失败, 请重新登陆再试...!');
                                     }
                                 });
                                 return false;
                             } else {
                                 return false;
                             }
                         }
                     });
                 

                 }else if(kplsh.length >1){
                  $("#alertt").html("不能批量换开！");
                  $("#my-alert").modal('open');
                    $('input[type="checkbox"]').prop('checked', false);

                 }
               
             
          });*/
            
           
        },
        setForm0: function (data) {
            var _this = this, i;
            el.$jsForm0.find('input[name="yfpdm"]').val(data.fpdm);
            el.$jsForm0.find('input[name="yfphm"]').val(data.fphm);
            el.$jsForm0.find('input[name="kpje"]').val(FormatFloat(data.jshj, "###,###.00"));
            el.$jsForm0.find('input[name="djh"]').val(data.djh);
            el.$jsForm0.find('input[name="kplsh"]').val(data.kplsh);
            
            el.$jsForm0.find('input[name="gfemail_edit"]').val(data.gfemail);
            el.$jsForm0.find('input[name="gfdh_edit"]').val(data.gfdh);
            el.$jsForm0.find('input[name="gfdz_edit"]').val(data.gfdz);
            el.$jsForm0.find('input[name="tqm_edit"]').val(data.tqm);
            el.$jsForm0.find('input[name="ddh_edit"]').val(data.ddh);
            el.$jsForm0.find('input[name="gfsjh_edit"]').val(data.gfsjh);
            el.$jsForm0.find('input[name="gfbz_edit"]').val(data.bz);
            el.$jsForm0.find('input[name="gfzh_edit"]').val(data.gfyhzh);
            el.$jsForm0.find('input[name="gfyh_edit"]').val(data.gfyh);
            el.$jsForm0.find('input[name="gfsh_edit"]').val(data.gfsh);
            el.$jsForm0.find('input[name="gfmc_edit"]').val(data.gfmc);
            //el.$jsForm0.find('select[name="fpzl"]').val(data.fpzldm);


            
        },
      
       modalAction: function () {
            var _this = this;
            el.$modalhuankai.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            /*el.$modalExport.on('closed.modal.amui', function () {
                el.$jsForm1[0].reset();
            });*/

            // close modal
            el.$jsClose.on('click', function () {
                el.$modalhuankai.modal('close');
                $('input[type="checkbox"]').prop('checked', false);
                //el.$modalExport.modal('close');
            });

            /*// 导出 modal
            el.$jsExport.on('click', function (e) {
                e.preventDefault();
                el.$modalExport.modal('open');

            });*/
        },
      
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable

            _this.saveRow();
            _this.modalAction(); // hidden action
            _this.hksearch();
           // _this.saveExport(); // export

        }
    };
    action.init();

});