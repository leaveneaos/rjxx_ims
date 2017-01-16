<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>后台管理首页</title>
<meta name="description" content="首页">
<meta name="keywords" content="首页">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="../../assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/amazeui.tree.min.css">
<link rel="stylesheet" href="css/main.css" />
<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>
</head>
<body>
	<%@ include file="../../pages/top.jsp"%>
	<div class="am-cf admin-main">
		<%@ include file="../../pages/menus.jsp"%>
		<div class="admin-content">
		<div style="padding-right:15px;padding-top:5px">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">&nbsp;&nbsp;&nbsp;&nbsp;首页</strong>
			</div>					
			<button type="button" class="js-addmk am-btn am-btn-secondary" style="float:right">新增区块</button>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
			<hr />

			<div class="am-g" id="sortable">
			<c:if test="${mkglList!=null}">
			<c:forEach items="${mkglList}" var="item">
				<div class="${item.mkbl} dragMove" id="${item.id}-fdiv" style="top:0;left:0">
					<div class="am-panel am-panel-default">
						<div class="am-panel-hd am-cf" style="height:45px">
							<span>${item.mkmc}</span>
							<ul style="float:right;" class="am-nav">
								<li class="am-dropdown" data-am-dropdown><a
				                    class="am-dropdown-toggle" data-am-dropdown-toggle
				                    href="javascript:;">操&nbsp;作<span
					                class="am-icon-caret-down"></span>
			                        </a>
									<ul class="am-dropdown-content">
										<li><a href="javascript:$('#div-${item.id}').load('<%=request.getContextPath()%>${item.url}')">
											<span class="am-icon-refresh"></span>刷新</a></li>
										<li><a href="javascript:editItem(${item.id},'${item.mkmc}','${item.mkbl}','${item.pzid}')">
											<span class="am-icon-edit"></span>修改</a></li>
										<li><a href="javascript:deleteItem(${item.id})"><span class="am-icon-remove"></span>删除</a></li>
									</ul>
								</li>
							</ul>
						</div>
						<div class="am-collapse am-in" id="div-${item.id}"
							style="height:280px;overflow:auto;"></div>
							<script type="text/javascript">
							       $("#div-${item.id}").load("<%=request.getContextPath()%>${item.url}");  							    
							</script>
							
					</div>
				</div>
				</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="addModel">
		<div class="am-modal-dialog" style="height: 320px; width: 500px">
			<form class="js-form-0 am-form" id="addForm">
				<div class="am-tabs" data-am-tabs>
					<div class="am-tabs-nav am-nav am-nav-tabs">
						<label>新增区块</label>
					</div>
					<div class="am-tabs-bd">
						<div class="am-tab-panel am-in am-active" id="tab1">
							<div class="am-modal-bd">
								<div class="am-g">
									<div class="am-u-sm-12">
										<table
											class="am-table am-table-bordered am-table-striped am-text-nowrap">
											<tr>
												<td><span style="color: red;">*</span>区块名称</td>
												<td><input type="text" id="mkmc" name="mkmc" required
													placeholder="请输入区块名称" /></td>
											<tr>
											<tr>
												<td><span style="color: red;">*</span>区块比例</td>
												<td><select id="mkbl" name="mkbl" required>
												        <option value="am-u-sm-3 am-u-md-3 am-u-lg-3">33.3%</option>
														<option value="am-u-sm-6 am-u-md-6 am-u-lg-6">50%</option>
														<option value="am-u-sm-9 am-u-md-9 am-u-lg-9">66.7%</option>
														<option value="am-u-sm-12 am-u-md-12 am-u-lg-12">100%</option>
												</select></td>
											</tr>
											<tr>
												<td><span style="color: red;">*</span>区块内容</td>
												<td><select id="mkurl" name="pzid" required>
												<c:if test="${mkglList!=null}">
												    <c:forEach items="${mkpzList}" var="var">
												    <option value="${var.id}">${var.urlmc}</option>
												    </c:forEach>
												    </c:if>
												</select></td>
											</tr>
										</table>
									</div>
									<div class="am-u-sm-12">
										<div class="am-form-group">
											<div class="am-u-sm-12  am-text-center">
												<button type="button" onclick="saveMk();"
													class="js-submit am-btn am-btn-primary">确定</button>
												<button type="button" class="js-close  am-btn am-btn-danger" 
												onclick="$('#addModel').modal('close');">取消</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="delete-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-bd">您确定要删除该区块吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-confirm>确定</span> <span
					class="am-modal-btn" data-am-modal-cancel>取消</span>
			</div>
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<%@ include file="../../pages/foot.jsp"%>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/yhgl_app.js"></script>
	<script src="js/index.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="assets/js/jquery-ui.js"></script>
	<script>
		(function($) {
			 $('#firstTree').on('selected.tree.amui', function(event, data) {
				$("#xm").val(data.selected[0].title);
				$("#zh").val(data.selected[0].dlyhd);
				console.log(data.selected[0].title);
			}); 
            var divs = $(".dragMove");
            $(divs[divs.length-1]).addClass("am-u-end");
			//拖拽功能
			$(".dragMove").css("cursor","move");			  
			$( "#sortable" ).sortable({
                update: function(){
                	var ids = $(".dragMove");
                	var data = "";
                	for(var i=0;i<ids.length;i++){
                		var id = $(ids[i]).attr("id");
                		if(i!=ids.length-1){
                			data+=id+",";
                		}else{
                			data+=id;
                		}
                		
                	}
                	$.ajax({
                		cache:false,
                	    type:"POST",
                	    data:{"idStr":data},
                	    url:'main/updateSort',
                	    async:true,
                	    success:function(){
                	    	var divs = $(".dragMove");
                            $(divs[divs.length-1]).addClass("am-u-end");
                	    }
                	});
                } 
             }).disableSelection();      			
		})(jQuery);
		
	</script>
</body>
</html>