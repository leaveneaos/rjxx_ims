(function ($) {
    'use strict';
    $(function () {
        var searchtable = $('#searchtable').DataTable({
            "searching": false,
            "serverSide": true,
            "sServerMethod": "POST" ,
            "processing": true,
            ajax: {
                "url":"/jbxx/getjbxxlist",
                data:function(d){
                    d.nsrsbh = $("#nsrsbh").val();
                    d.qymc = $("#qymc").val();
                    d.khyh = $("#khyh").val();
                    d.yhzh = $("#yhzh").val();

                    console.log(d);
                }
            },
            "columns": [
                {"data": "id"},
                {"data": "nsrsbh"},
                {"data": "yqmc"},
                {"data": "khyh"},
                {"data": "yhzh"},
                {"data": "dz"},
                {"data": "dh"},
                {"data": null}
            ],
            "columnDefs": [
                {
                    // The `data` parameter refers to the data for the cell (defined by the
                    // `data` option, which defaults to the column being worked with, in
                    // this case `data: 0`.<a href='/log/edit/"+data.id+"'>编辑</a>
                    "render": function ( data, type, row ) {
                        return '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs"><button class="am-btn am-btn-default am-btn-xs am-text-primary" data-am-modal="{target: \'#my-alert-edit\' ,closeViaDimmer: 0, width: 600, height: 225}"><span class="am-icon-pencil-square-o"></span> 编辑</button><button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 删除</button></div></div>';
                        //return   "<button type='button'   class='am-btn-sm am-btn-success' data-am-modal='{target: \"#my-alert-edit\" ,closeViaDimmer: 0, width: 600, height: 225}'>修改</button>  <button type='button' class='am-btn-sm am-btn-primary' data-am-modal='{target: \"#my-alert\"}'> 删除 </button>";
                    },
                    "targets": -1
                }
            ]
        });
        $('#searchtable tbody').on( 'click', 'button', function () {
            var data = searchtable.row( $(this).parents('tr') ).data();
            console.log( data.id  );
            $("#nsrsbh_edit").val(data.id);

            $("#yhzh_edit").find('option[value="b"]').attr('selected',true);
        } );

        $('#search_button').click(function () {
            searchtable.ajax.reload();
            //获取dt请求参数
            var args = searchtable.ajax.params();
            console.log("额外传到后台的参数值extra_search为："+args.qymc);
        });
    });
})(jQuery);
