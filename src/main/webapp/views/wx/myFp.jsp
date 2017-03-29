<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>我的发票</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="<%=request.getContextPath()%>/css/mui.min.css" rel="stylesheet"/>
    <script src="assets/js/loading.js"></script>
</head>
<body ng-app="ngApp" ng-controller="mainCtrl">
<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title">我的发票</h1>
</header>

<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view">
            <li class="mui-table-view-cell" ng-repeat="fpxx in fpList track by $index" data-pdfurl="{{fpxx.pdfurl}}">
                <div class="mui-h5" style="clear: both;">
                    <div style="clear: both;">
                        <div class="mui-pull-left mui-h4" ng-bind="fpxx.spmc">

                        </div>
                        <div class="mui-pull-right mui-h5" ng-bind="fpxx.jshj">

                        </div>
                    </div>
                    <div style="clear: both;">
                        <div class="mui-pull-left mui-h4" ng-bind="fpxx.xfmc">

                        </div>
                        <div class="mui-pull-right mui-h5" ng-bind="fpxx.lrsj">

                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/js/mui.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/angular.min.js"></script>
<script type="text/javascript">
    var ngApp = angular.module("ngApp", []);
    ngApp.controller("mainCtrl", function ($scope) {
        mui.init({
            pullRefresh: {
                container: '#pullrefresh',
                up: {
                    callback: pullUpRefresh //必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
                }
            }
        });

        //加载更多
        function pullUpRefresh() {
            currentPage++;
            getFpList();
        }

        var userInfoStr = localStorage.getItem("$state");
        if (!userInfoStr) {
            mui.toast("请先绑定账号");
            setTimeout(function () {
                mui.openWindow("<%=request.getContextPath()%>/wx/bind");
            }, 1000);
            return;
        }
        $scope.fpList = [];
        var currentPage = 1;
        var pageNumber = 10;
        var userInfo = JSON.parse(userInfoStr);
        var mobile = userInfo.mobile;
        var url = "<%=request.getContextPath()%>/wx/getMyFp";

        function getFpList() {
            mui.post(url, {mobile: mobile, currentPage: currentPage, pageNumber: pageNumber}, function (res) {
                var success = res["success"];
                if (success) {
                    var moreFpList = res["dataList"];
                    var length = moreFpList.length;
                    if (length > 0) {
                        var currentList = $scope.fpList;
                        currentList = currentList.concat(moreFpList);
                        $scope.fpList = currentList;
                        $scope.$apply();
                    }
                    if (length < pageNumber) {
                        mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
                    } else {
                        mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
                    }
                }
            })
        }

        getFpList();

        mui(".mui-table-view").on("tap", ".mui-table-view-cell", function () {
            var pdfUrl = this.getAttribute("data-pdfurl");
            if (!pdfUrl) {
                mui.toast("电子发票pdf不存在，请联系管理员");
                return;
            }
            window.open(pdfUrl);
        });
    });


</script>
</body>
</html>
