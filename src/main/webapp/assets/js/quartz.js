/**
 * feel
 */
$(function () {
    "use strict";

    var el = {
        $jsTable: $('#task_table'),
        $modaladd: $('#addtask'),
        
        $modaledit: $('#edittask'),

        $jsadd: $('#js-add'), // 换开提交
        
        $jsedit: $('#js-edit'), // 换开提交

        $jsClose: $('#js-addclose'), // 关闭 modal
        
        $jsClose1: $('#js-editclose'), // 关闭 modal

        
        $jsForm0: $('#add_form'), // 换开 form
       
        $jsForm1: $('#edit_form'), // 换开 form

        $jsLoading: $('.js-modal-loading')
    };

    var action = {
        tableEx: null, // cache dataTable
        config: {
            getUrl: 'quartz/getTaskList',

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


                        }
                    },
                    "columns": [
						{
							"orderable" : true,
							"data" : null,
							render : function(data, type, full, meta) {
								return '<input type="checkbox" value="'
									+ data.jobGroup + '" name="chk"  id="chk"/>';
							}
						},
                        {"data": "jobName"},
                        {"data": "jobGroup"},
                        {"data": "jobDescription"},
                        {"data": "jobStatus"},
                        {"data": "cronExpression"},
                        {"data": "createTime"},
                        {
                            "data": null,
                            "render": function (data) {
                                var val = '';
                                if(data.jobStatus == 'NORMAL') {
                                    val = '<a href="javascript:void(0)" id="zt" >暂停</a>'
                                }else if(data.jobStatus == 'PAUSED'){
                                    val = '<a href="javascript:void(0)" id="ks">开始</a>'
                                }
                                return val;
                           }
                        }]
                });

            t.on('draw.dt', function (e, settings, json) {
                var x = t, page = x.page.info().start; // 设置第几页
               /* t.column(0).nodes().each(function (cell, i) {
                    //cell.innerHTML = page + i + 1;
                });*/

            });


            
            t.on('click', 'a#zt', function () {
                var data = t.row($(this).parents('tr')).data();
                if (confirm("您确认暂停定时任务？")) {
                    $.ajax({
                        url: "quartz/pause/"+data.jobName+"/"+data.jobGroup,
                        type: "post",
                        data: {
                            jobName : data.jobName,
                            jobGroup :data.jobGroup
                        },
                        success: function (result) {
                            if(result.code=="0"){
                                $("#alertt").html("暂停成功！");
                                $("#my-alert").modal('open');
                                _this.tableEx.ajax.reload();
                            }else if(result.code=="1"){
                                swal(result.errortext);
                            }
                        }
                    });
                }

            });

            t.on('click', 'a#ks', function () {
                var data = t.row($(this).parents('tr')).data();
                if (confirm("您确认开始定时任务？")) {
                    $.ajax({
                        url:"quartz/resume/"+data.jobName+"/"+data.jobGroup,
                        type: "post",
                        data: {
                            jobName : data.jobName,
                            jobGroup :data.jobGroup
                        },
                        success: function (result) {
                            if(result.code=="0"){
                                $("#alertt").html("开始成功！");
                                $("#my-alert").modal('open');
                                _this.tableEx.ajax.reload();
                            }else if(result.code=="1"){
                                swal(result.errortext);
                            }
                        }
                    });
                }


           });
            t.on('click', 'input#chk', function () {
                var data = t.row($(this).parents('tr')).data();
                _this.setForm0(data);
                var group=[];
               $('input[name="chk"]:checked').each(function(){
                    group.push($(this).val());
                });
                if(group.length >1){
                    $("#alertt").html("不能批量操作定时任务！");
                    $("#my-alert").modal('open');
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
         * 修改任务
         */
        saveRow: function () {
            var _this = this;
               el.$jsedit.click(function(){
                        $.ajax({
                            url:"quartz/save",
                            type: "post",
                            data: {
                                bz:"1",
                                jobName : $("#jobname1").val(),
                                jobGroup :$("#jobgroup1").val(),
                                JobDescription : $("#jobdescription1").val(),
                                CronExpression :$("#cronexpression1").val()
                            },
                            success: function (result) {
                                console.log(result);
                                if(result.code=="0"){
                                    el.$modaledit.modal('close');
                                    $("#alertt").html("修改成功！");
                                    $("#my-alert").modal('open');
                                    _this.tableEx.ajax.reload();
                                }else if(result.code=="1"){
                                    el.$modaledit.modal('close');
                                    swal(result.errortext);
                                }
                            }
                        });
                   });
        },
        /**
         *  添加任务
         */
        saveRow1: function () {
            var _this = this;
        	        el.$jsadd.click(function(){
        	            $.ajax({
        	                url:"quartz/save",
        	                type: "post",
        	                data: {
        	                    bz:"0",
        	                    jobName : $("#jobname").val(),
        	                    jobGroup :$("#jobgroup").val(),
        	                    JobDescription : $("#jobdescription").val(),
        	                    CronExpression :$("#cronexpression").val()
        	                },
        	                success: function (result) {
        	                    if(result.code=="0"){
        	                        el.$modaladd.modal('close');
        	                        $("#alertt").html("保存成功！");
        	                        $("#my-alert").modal('open');
        	                        _this.tableEx.ajax.reload();
        	                    }else if(result.code=="1"){
        	                        el.$modaladd.modal('close');
                                    swal(result.errortext);
        	                    }
        	                }
        	            });
        	        });
        },
        
        deleteRow:function(){
        	
            var _this = this;

        	/**
             * 删除任务
             */
            $("#delete").click(function(){
            	  var group=[];
                  $('input[name="chk"]:checked').each(function(){
                      group.push($(this).val());
                  });
                  if(group.length==0){
                      $("#alertt").html("请选择要删除的任务！");
                      $("#my-alert").modal('open');
                  }
                  if(group.length==1){
                   if (confirm("您确认删除定时任务？")) {
                       $.ajax({
                           url:"quartz/delete/"+$("#jobname1").val()+"/"+$("#jobgroup1").val(),
                           type: "post",
                           data: {
                               jobName : $("#jobname").val(),
                               jobGroup : $("#jobgroup").val()
                           },
                           success: function (result) {
                               if(result.code=="0"){
                                   $("#alertt").html("删除成功！");
                                   $("#my-alert").modal('open');

                                   _this.tableEx.ajax.reload();
                               }else if(result.code=="1"){
                                   swal(result.errortext);
                               }
                           }
                       });
                      }
                   }

               });
        },
        setForm0: function (data) {
            el.$jsForm1.find('input[name="jobname1"]').val(data.jobName);
            el.$jsForm1.find('select[name="jobgroup1"]').val(data.jobGroup);
            el.$jsForm1.find('input[name="jobdescription1"]').val(data.jobDescription);
            el.$jsForm1.find('input[name="cronexpression1"]').val(data.cronExpression);

       },
       
       modalAction: function () {
            var _this = this;
            el.$modaladd.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });
            
            /*el.$modaledit.on('closed.modal.amui', function () {
                el.$jsForm0[0].reset();
            });*/


            // close modal
            el.$jsClose.on('click', function () {
                el.$modaladd.modal('close');
                //$('input[type="checkbox"]').prop('checked', false);
            });
            
            el.$jsClose1.on('click', function () {
                el.$modaledit.modal('close');
                //$('input[type="checkbox"]').prop('checked', false);
            });


        },
      
        init: function () {
            var _this = this;

            _this.tableEx = _this.dataTable(); // cache variable

            _this.saveRow();    
            
            _this.saveRow1();
            
            _this.deleteRow();
            
            _this.modalAction(); // hidden action
            //_this.hksearch();

        }
    };
    action.init();
    
   
  
    
    
    
   
});