<%--
  Created by IntelliJ IDEA.
  User: Him
  Date: 2015/11/24
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
        <ul class="am-list admin-sidebar-list" >
            <li><a href="main"><span class="am-icon-home"></span> 首页</a></li>
			<li><a href="yhgl"><span class="am-icon-home"></span> 用户管理</a></li>
            <li class="admin-parent">
                <a class="am-cf" data-am-collapse="{target: '#collapse-nav3'}"><span class="am-icon-inbox"></span> 基础数据
                    <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav3">
                    <li><a href="xfxxwh"><span class="am-icon-keyboard-o"></span> 销方信息维护</a></li>
                    <%--<li><a href="sksbxxzc"><span class="am-icon-magic"></span> 税控设备注册</a></li>--%>
                    <li><a href="spslgl"><span class="am-icon-magnet"></span> 商品税率管理</a></li>
                </ul>
            </li>

            <li class="admin-parent">
                <a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span class="am-icon-flag"></span> 业务处理
                    <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
                    <li><a href="kp"><span class="am-icon-shield"></span> 发票开具</a></li>
                    <li><a href="fphc"><span class="am-icon-shield"></span> 发票红冲</a></li>
                    <li><a href="fphk"><span class="am-icon-ticket"></span> 发票换开</a></li>
                    <li><a href="yjfs"><span class="am-icon-paper-plane"></span> 发票发送</a></li>
<!--                     <li><a href="jyls"><span class="am-icon-paper-plane"></span> 交易流水导出</a></li> -->
                 <!--    <li><a href="yccl"><span class="am-icon-question-circle"></span> 异常处理</a></li> -->
                </ul>
            </li>

            <li class="admin-parent">
                <a class="am-cf " data-am-collapse="{target: '#collapse-nav1'}"><span class="am-icon-line-chart"></span> 查询统计
                    <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav1">                  
                    <li><a href="fpcx"><span class="am-icon-search"></span> 发票查询</a></li>                  
                    <li><a href="fphccx"><span class="am-icon-search"></span> 红字发票查询</a></li>
                    <li><a href="fphkcx"><span class="am-icon-search"></span> 换开发票查询</a></li>
                    <li><a href="ddcx"><span class="am-icon-search"></span> 订单状态查询</a></li>
                    <li><a href="ybp"><span class="am-icon-bar-chart"></span> 仪表盘</a></li>
                    <li><a href="frtjbb"><span class="am-icon-calendar"></span> 分日统计报表</a></li>
                    <li><a href="fytjbb"><span class="am-icon-line-chart"></span> 分月统计报表</a></li>
                     <li><a href="sjdtjbb"><span class="am-icon-line-chart"></span> 时间段统计报表</a></li>
                    <%--<li><a href="sybg"><span class="am-icon-file-text"></span> 使用报告</a></li>--%>
                    <%--<li><a href="bbdy"><span class="am-icon-pencil"></span> 报表订阅</a></li>--%>
                </ul>
            </li>

            <%--<li class="admin-parent">--%>
                <%--<a class="am-cf" data-am-collapse="{target: '#collapse-nav4'}"><span class="am-icon-cogs"></span> 系统管理--%>
                    <%--<span class="am-icon-angle-right am-fr am-margin-right"></span></a>--%>
                <%--<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav4">--%>
                    <%--<li><a href="/yhgl"><span class="am-icon-users"></span> 用户管理</a></li>--%>
                    <%--<li><a href="rzjk"><span class="am-icon-eye"></span> 日志监控</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>
        </ul>

    </div>
</div>
