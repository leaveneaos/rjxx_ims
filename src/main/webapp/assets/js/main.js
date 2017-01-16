/**
 * feel
 */
/*
 * $(function () { "use strict"; var ur ; var el = { $jsModel:$('#addModel'),
 * $jsForm: $('.js-form'), $jsSubmit: $('.js-submit'), $jsAdd: $('.js-addmk') };
 * var action = { config: { xzUrl: 'qkgl/save', xgUrl: 'qkgl/update', scUrl:
 * 'qkgl/destroy' }, dataTable: function () { el.$jsAdd.on('click', el.$jsAdd,
 * function () { _this.resetForm(); ur = _this.config.xzUrl
 * el.$jsModel.modal('open'); }); },
 * 
 * resetForm: function () { el.$jsForm[0].reset(); }, init: function () { var
 * _this = this; } }; action.init(); });
 */

$(function() {
	
});

var url;
$('.js-addmk').on('click', $('.js-addmk'), function() {
	$('#addModel').modal('open');
	$('#mkmc').val("");
	$("#mkbl").find("option").eq(0).attr("selected", true);
	$("#mkurl").find("option").eq(0).attr("selected", true);
	url = 'main/save';
});

// 修改函数
function editItem(id, mkmc, mkbl, pzid) {
	$('#addModel').modal('open');
	$('#mkmc').val(mkmc);
	var selectIndex = -1;
	var options = $("#mkbl").find("option");
	for (var j = 0; j < options.size(); j++) {
		var value = $(options[j]).val();
		if (value == mkbl) {
			selectIndex = j;
			break;
		}
	}
	$("#mkbl").find("option").eq(selectIndex).attr("selected", true);
	var selectIndex = -1;
	var options = $("#mkurl").find("option");
	for (var j = 0; j < options.size(); j++) {
		var value = $(options[j]).val();
		if (value == pzid) {
			selectIndex = j;
			break;
		}
	}
	$("#mkurl").find("option").eq(selectIndex).attr("selected", true);
	url = 'main/update?id=' + id;
}

// 保存函数
function saveMk() {
	var mkmc = $("#mkmc").val();
	if (mkmc == '') {
		alert("区块名称不能为空！");
		return;
	}
	$.ajax({
		url : url,
		data : $('#addForm').serialize(),
		method : 'POST',
		success : function(data) {
			if (data.success) {
				alert(data.msg);
				$('#addModel').modal('close');
				window.location.reload();
			} else {
				alert('数据保存失败:' + data.msg);
			}
		},
		error : function() {
			alert('数据保存失败, 请重新登陆再试...!');
		}
	});
}

// 删除函数
function deleteItem(id) {
	$('#delete-confirm').modal({
		relatedTarget : this,
		onConfirm : function(options) {
			$.ajax({
				url : 'main/destory?id=' + id,
				method : 'post',
				success : function(data) {
					if (data.success) {
						alert(data.msg);
						window.location.reload();
					} else {
						alert('数据删除失败' + data.msg);
					}
				},
				error : function() {
					alert('数据删除失败, 请重新登陆再试...!');
				}
			});
		},
		onCancel : function() {

		}
	});
}