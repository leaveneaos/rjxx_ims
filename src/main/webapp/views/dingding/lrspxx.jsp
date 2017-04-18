<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>  
    <meta charset="utf-8">  
    <title>录入商品信息</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">    
    <meta name="apple-mobile-web-app-capable" content="yes">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">    
    <script src="js/mui.min.js"></script>
    <link rel="stylesheet" href="css/mui.css">
        <script src="js/jquery.1.7.2.min.js"></script>
    
  </head>  
  <body>  
  	   <input type="hidden" id="corpid" value="<c:out value="${corpid}" />"/>    
  	   <input type="hidden" id="Jyxxsq" value="<c:out value="${Jyxxsq}" />"/>    
		<div class="mui-content">
			<div class="mui-content-padded">
			<h5 class="mui-content-padded">*商品名称</h5>
			<select id="lrselect_sp" name="lrselect_sp" class="mui-btn mui-btn-block">
					<option value="">选择商品</option>
					<c:forEach items="${spList}" var="item">
						<option value="${item.spbm}" class="${item.id}">${item.spmc}(${item.spbm})</option>
					</c:forEach>
			</select>
		</div>
	    <div class="mui-content-padded" style="margin: 5px;">    
	      <form class="mui-input-group">
			    <div class="mui-input-row">
			        <label>规格型号</label>
			        <input type="text" id="ggxh" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>单位</label>
			        <input type="text" id="spdw" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>数量</label>
			        <input type="text" id="spsl" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>单价</label>
			        <input type="text" id="spdj"  class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>金额(不含税)</label>
			        <input type="text" id="je" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>金额(含税)</label>
			        <input type="text"  id="hsje" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>税率</label>
			        <input type="text" id="splv" class="mui-input-clear" placeholder="">
			    </div>
			    <div class="mui-input-row">
			        <label>税额</label>
			        <input type="text"  id="se"  class="mui-input-clear" placeholder="">
			    </div>
			</form>
	    </div> 
    </div>

    
    <nav class="mui-bar mui-bar-tab">
			<a class="lrkpd" >
				<span class="mui-tab-label">价税合计：<font id="jshj">45.00</font> </span>
			</a>
			<a class="lrkpd" >
				<span class="mui-tab-label">继续添加</span>
			</a>
			<a class="lrkpd" href="dinglrkpd2?corpid=<c:out value="${corpid}" />" >
				<span class="mui-tab-label">完成（2）</span>
			</a>
		</nav>
  </body>  
  <script>
  $(function(){
	  document.getElementById("lrselect_sp").addEventListener('tap', function() {
		  var je = $('#je');
          var sl = $('#splv');
          var se = $('#se');
          var hsje = $('#hsje');
          var jshj = $('#jshj');
          var dj = $('#spdj');
          var sps = $('#spsl');
          var spsl;
          var spdm = $(this).val();
          var spmc = $("#lrselect_sp option:checked").text();
          var pos = spmc.indexOf("(");
          //var spid =  $("#select_sp option:checked").attr('class');
          spmc = spmc.substring(0, pos);
          if (!spdm) {
              $("#lrmx_form input").val("");
              return;
          }
          var ur = "<%=request.getContextPath()%>/lrkpd/getSpxq";
          $.ajax({
              url: ur,
              type: "post",
              async:false,
              data: {
              	spdm: spdm,   
              	spmc:spmc,	
              }, 
              success: function (res) {
              	 if (res) {
                       //$("#ggxh").val(res["spbm"]);
                       //$("#lrmx_form #lrmc_edit").val(res["spmc"]);
                       $("#ggxh").val(res["spggxh"] == null ? "" : res["spggxh"]);
                       $("#spdw").val(res["spdw"] == null ? "" : res["spdw"]);
                       $("#spdj").val(res["spdj"] == null ? "" : res["spdj"]);
                       $("#splv").val(res["sl"]);
                       spsl = res["sl"];
                   }
              }
          });
          if(null!=je && je.val() !=""){
          	//alert(spsl);
          	var temp = (100+sl.val()*100)/100;
				se.val(FormatFloat(je.val() * spsl, "#####0.00"));
				var je1 = parseFloat(je.val());
      		var se1 = parseFloat(se.val());
				hsje.val(FormatFloat(je1 + se1, "#####0.00"));
				jshj.val(FormatFloat(je1 + se1, "#####0.00"));
      		if (dj != null && dj.val() != "") {
      			sps.val(FormatFloat(je.val() / dj.val(), "#####0.00"));
				}else if(sps != null && sps.val() != ""){
					dj.val(FormatFloat(je.val() / spl.val(), "#####0.00"));
				}
          }
		  
	  });
	  document.getElementById("je").addEventListener('tap', function() {
		  var num = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			var je = $('#je');
			if (!num.test(je.val())) {
				if (je.val().length > 1) {
					$('#je').val(
							je.val().substring(0,
									je.val().length - 1))
				} else {
					$('#je').val("")
				}
				return;
			}
			var sl = $('#splv');
			var se = $('#se');
			var hsje = $('#hsje');
			var jshj = $('#jshj');
			var dj = $('#spdj');
			var sps = $('#spsl');
			var spsl;
			var temp = (100 + sl.val() * 100) / 100;
			se.val(FormatFloat(je.val() * sl.val(),
					"#####0.00"));
			var je1 = parseFloat(je.val());
			var se1 = parseFloat(se.val());
			hsje.val(FormatFloat(je1 + se1, "#####0.00"));
			jshj.val(FormatFloat(je1 + se1, "#####0.00"));
			if (dj != null && dj.val() != "") {
				sps.val(FormatFloat(je.val() / dj.val(),
						"#####0.00"));
			} else if (sps != null && sps.val() != "") {
				dj.val(FormatFloat(je.val() / spl.val(),
						"#####0.00"));
			}
		  
	  });
	  document.getElementById("hsje").addEventListener('tap', function() {
		  var num = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
			var hsje = $('#hsje');
			if (!num.test(hsje.val())) {
				if (hsje.val().length > 1) {
					$('#hsje').val(
							hsje.val().substring(0,
									hsje.val().length - 1))
				} else {
					$('#hsje').val("")
				}
				return;
			}
			var je = $('#je');
			var sl = $('#splv');
			var se = $('#se');

			var jshj = $('#jshj');
			var dj = $('#spdj');
			var sps = $('#spsl');
			var spsl;
			var temp = (100 + sl.val() * 100) / 100;
			je.val(FormatFloat(hsje.val() / (temp),
					"#####0.00"));
			se.val(FormatFloat(hsje.val() - je.val(),
					"#####0.00"));
			jshj.val(FormatFloat(hsje.val(), "#####0.00"));
		  
	  });
  });
 
  </script>
</html>  