<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>待办事项</title>
<meta name="description" content="待办事项">
<meta name="keywords" content="待办事项">
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
<link rel="stylesheet" href="assets/css/amazeui.datatables.css" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="assets/css/app.css" />
<link rel="stylesheet" href="plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.min.css"/>
<link rel="stylesheet" href="plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.css"/>
<style type="text/css">
   .plane {text-align: center;padding-top: 30px; font-size: 32px}
   .dbsl {font-size:20px;color:red;font-size: 32px}
</style>
</head>
<body>
	<div class="am-cf admin-main">
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">首页</strong> / <strong>待办事项</strong>
				</div>
			</div>
			<hr />

			<div class="am-g">
				<div class="am-u-sm-12">
				   <c:if test="${lrkpd==1}">
		 		   <div class="am-panel-default am-u-sm-2" style="height:160px">						
						<div class="plane">
						<a href="<%=request.getContextPath()%>/lrkpd">
						    <span class="am-icon-list-alt am-icon-lg" style="color:#FF44FF"></span></a>
						    <span class="dbsl">${lrkpddb}</span>
							<div style="text-align: center;font-size:14px">录入开票单</div>
						</div>
					</div>
					</c:if>
					<c:if test="${kpdsh==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
					    <div class="plane">
						<a href="<%=request.getContextPath()%>/kpdsh">
						    <span class="am-icon-check-square am-icon-lg" style="color:#AAAAFF"></span></a>
						    <span class="dbsl">${kpdshdb}</span>
						 </div>
						<div style="text-align: center;font-size:14px">开票单审核</div>						
					</div>
					</c:if>
					<c:if test="${fpkj==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
					    <div class="plane">
						<a href="<%=request.getContextPath()%>/kp">
						    <span class="am-icon-print am-icon-lg" style="color:#79FF79"></span></a>
						    <span class="dbsl">${fpkjdb}</span>
						</div>
						<div style="text-align: center;font-size:14px">发票开具</div>						
					</div>
					</c:if>
					<c:if test="${fphc==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
					    <div class="plane">
						<a href="<%=request.getContextPath()%>/fphc">
						    <span class="am-icon-reply am-icon-lg" style="color:#A3D1D1"></span></a>
						</div>
						<div style="text-align: center;font-size:14px">发票红冲</div>						
					</div>
					</c:if>
					<c:if test="${fphk==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
					    <div class="plane">
						<a href="<%=request.getContextPath()%>/fphk">
						    <span class="am-icon-random am-icon-lg" style="color:#C7C7E2"></span></a>
						    </div>
						<div style="text-align: center;font-size:14px">发票换开</div>						
					</div>
					</c:if>
					<c:if test="${fpzf==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
					    <div class="plane">
						<a href="<%=request.getContextPath()%>/fpzf">
						    <span class="am-icon-trash am-icon-lg" style="color:#FFBD9D"></span></a>
						</div>
						<div style="text-align: center;font-size:14px">发票作废</div>						
					</div>
					</c:if>
					<c:if test="${fpck==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
						<div class="plane">
						<a href="<%=request.getContextPath()%>/fpzf">
						    <span class="am-icon-refresh am-icon-lg" style="color:#95CACA"></span></a>
						</div>
						<div style="text-align: center;font-size:14px">发票重开</div>					
					</div>
					</c:if>
					<c:if test="${fpcd==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
						<div class="plane">
						<a href="<%=request.getContextPath()%>/fpzf">
						    <span class="am-icon-retweet am-icon-lg" style="color:#B8B8DC"></span></a>
						</div>
						<div style="text-align: center;font-size:14px">发票重打</div>						
					</div>
					</c:if>
					<c:if test="${fpfs==1}">
					<div class="am-panel-default am-u-sm-2" style="height:160px">
						<div class="plane">
						<a href="<%=request.getContextPath()%>/fpzf">
						    <span class="am-icon-envelope am-icon-lg" style="color:#B3D9D9"></span>
						    </a></div>
						<div style="text-align: center;font-size:14px">发票发送</div>					
					</div>
					</c:if>
					<c:if test="${fpyj==1}">
					<div class="am-panel-default am-u-sm-2 am-u-end" style="height:160px">
						<div class="plane">
						<a href="<%=request.getContextPath()%>/fpzf">
						    <span class="am-icon-send am-icon-lg" style="color:#BBFFBB"></span></a>
						</div>
					    <div style="text-align: center;font-size:14px">发票邮寄</div>		
					</div>
					</c:if>
				</div>
			</div>
			<hr/>
			<!-- 折线图开始 -->
			<div class="am-u-sm-12" style="padding-left:20px">
			    <div id="chart" style="width:1050px;height:400px">			
			    </div>
			</div>
			<!-- 折线图结束 -->
			<div
				class="js-modal-loading  am-modal am-modal-loading am-modal-no-btn"
				tabindex="-1">
				<div class="am-modal-dialog">
					<div class="am-modal-hd">正在载入...</div>
					<div class="am-modal-bd">
						<span class="am-icon-spinner am-icon-spin"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<div data-am-widget="gotop" class="am-gotop am-gotop-fixed">
		<a href="#top" title="回到顶部"> <i
			class="am-gotop-icon am-icon-hand-o-up"></i>
		</a>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script
		src="plugins/datatables-1.10.10/media/js/jquery.dataTables.min.js"></script>
	<script src="assets/js/amazeui.datatables.js"></script>
	<script src="assets/js/amazeui.tree.min.js"></script>	
	<script src="plugins/jquery.jqplot.1.0.8/dist/jquery.jqplot.min.js"></script>    
    <script src="plugins/jquery.jqplot.1.0.8/dist/excanvas.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/excanvas.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.pieRenderer.min.js"></script>    
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.pieRenderer.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.canvasAxisTickRenderer.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.canvasAxisLabelRenderer.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.canvasTextRenderer.min.js"></script>    
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.enhancedLegendRenderer.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.dateAxisRenderer.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.highlighter.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.pointLabels.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.cursor.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.categoryAxisRenderer.min.js"></script>
    <script src="plugins/jquery.jqplot.1.0.8/dist/jqplot.barRenderer.min.js"></script>
	<script src="assets/js/app.js"></script>
	<script src="assets/js/format.js"></script>
	<script src="assets/js/fpcx_4.js"></script>
	<script>
	$(function () {
		var line1 = new Array();
		var ticks = new Array();
		$.ajax({
			type: "POST",  
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",  
            url: 'dbsx/getPlot',    
            success: function(result){  
            	var i=0;
                for (key in result) {            	
                    ticks[i] = key;  
                    line1[i] = parseInt(result[key]);
                    i++;
                }
                //plot开始
            $('#chart').jqplot([line1],{
			 title:'近一个月已办事项统计',
			 //seriesColors:["#2894FF","#FFE153"],
			 //varyBarColor: true,
			 seriesDefaults:{
				pointLabels: { show: true },				
				shadow: false,
				showMarker: true, // 是否强调显示图中的数据节点
				renderer:$.jqplot.BarRenderer,
				rendererOptions: {
					barWidth: 18,
					//barMargin: 10
					barPadding: 8
				}
			}, 		 
			axes:{
				xaxis:{
					label:'日期',
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					renderer:$.jqplot.CategoryAxisRenderer,
					ticks:ticks,
					showTicks: true,        // 是否显示刻度线以及坐标轴上的刻度值  
					showTickMarks: true,    //设置是否显示刻度
					tickOptions: {						
					     show: true,					     
					     fontSize: '14px',
					     fontFamily: 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
					     angle:-30,
					     showLabel: true, //是否显示刻度线以及坐标轴上的刻度值
					     showMark: false,//设置是否显示刻度
					     showGridline: false // 是否在图表区域显示刻度值方向的网格
				    }
				},
				yaxis: {
					label:'已办事项数量',
					show: true,
					showTicks: false,        // 是否显示刻度线以及坐标轴上的刻度值  
					showTickMarks: false,     //设置是否显示刻度
					autoscale: true,
					borderWidth: 1,
					tickOptions: {
					     show: true,
					     showLabel: false,
					     showMark: false,
					     showGridline: true
					}
				}
			}
		});
                //plot结束
            }
		});
		
		/* $('#chart').jqplot([line1],{
			title:'近一周代办事项情况',
			 seriesDefaults:{
				pointLabels: { show: true },
				shadow: false,
				showMarker: true, // 是否强调显示图中的数据节点
				renderer:$.jqplot.BarRenderer,
				rendererOptions: {
					barWidth: 60,
					barMargin: 60
				}
			}, 		 
			axes:{
				xaxis:{
					label:'日期',
					renderer:$.jqplot.CategoryAxisRenderer,
					ticks:ticks,
					showTicks: true,        // 是否显示刻度线以及坐标轴上的刻度值  
					showTickMarks: true,    //设置是否显示刻度
					tickOptions: {
					     show: true,
					     fontSize: '14px',
					     fontFamily: 'tahoma,arial,"Hiragino Sans GB",宋体b8b体,sans-serif',
					     showLabel: true, //是否显示刻度线以及坐标轴上的刻度值
					     showMark: false,//设置是否显示刻度
					     showGridline: false // 是否在图表区域显示刻度值方向的网格
				    }
				},
				yaxis: {
					show: true,
					showTicks: false,        // 是否显示刻度线以及坐标轴上的刻度值  
					showTickMarks: false,     //设置是否显示刻度
					autoscale: true,
					borderWidth: 1,
					tickOptions: {
					     show: true,
					     showLabel: false,
					     showMark: false,
					     showGridline: true
					}
				}
			}
		}); */		
    });
	
	</script>
</body>
</html>
