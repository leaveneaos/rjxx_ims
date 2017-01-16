<%@ page import="com.rjxx.taxeasy.security.SecurityContextUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	request.setAttribute("privilegeTypes",
			SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal().getPrivilegeTypesList());
	request.setAttribute("privileges",
			SecurityContextUtils.getCurrentLoginInfo().getWebPrincipal().getPrivilegesList());
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
		<div class="am-offcanvas-bar admin-offcanvas-bar">
			<ul class="am-list admin-sidebar-list" id="collapase-nav-1">
				<li><a id="span" href="main"><span class="am-icon-home"></span>
						首页</a></li>
				<c:forEach items="${privilegeTypes}" varStatus="i"
					var="privilegeType">
					<li class="am-panel"><a class="${privilegeType.description }"
						data-am-collapse="{parent: '#collapase-nav-1',target: '#collapse-nav${i.index}'}"><span></span>${privilegeType.name}
							<span class="am-icon-angle-right am-fr am-margin-right"></span></a>
						<ul class="am-list am-collapse admin-sidebar-sub"
							id="collapse-nav${i.index}">
							<c:forEach items="${privileges}" varStatus="j" var="privilege">
								<c:if test="${privilege.privilegetypeid == privilegeType.id}">
									<li><a id="${privilege.urls}"
										href="<%=request.getContextPath()%>${privilege.urls}"><span
											class="${privilege.description }"></span> ${privilege.name}</a></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script type="text/javascript">
 	$(function(){
		var url = window.location.href;
		var urls= url.substring(url.lastIndexOf("/"), url.length)
		 var as = document.getElementsByTagName('a');
		 for(var i=0,j=as.length;i<j;i++){
				if(urls=="/main"){
					var a = document.getElementById("span")
					a.style.color ='#1EA9F0';
					return;
				}else if(urls==as[i].id)
					{
						var a = document.getElementById(as[i].id);
						var bt = as[i].id;
						a.style.color ='#1EA9F0';
						$(a).parents("ul").addClass("am-in");
						return;
					} 
		 }
	})
	 
	</script>
</body>