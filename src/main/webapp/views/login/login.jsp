<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html style="height: 100%;width: 100%;">
<head lang="en">
    <meta charset="UTF-8">
    <title>泰易电子发票云服务平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="../assets/i/favicon.png">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css"/>
    <script src="../assets/js/jquery.min.js"></script>
    <%--<script src="assets/js/loading.js"></script>--%>
    <script language="javascript">
        if(window !=top){
            top.location.href=location.href;
        }
        function loadimage() {
            document.getElementById("randImage").src = "<%=request.getContextPath()%>/image.jsp?" + Math.random();
        }
    </script>
    <script type="text/javascript">
        (function remInit(){
           /* window.onresize = function(){
                //changeWH();
                var  userW = window.document.documentElement.getBoundingClientRect().width;
                userW<1500?userW = 1500:null
                window.document.documentElement.getBoundingClientRect().width<1000?userW = window.document.documentElement.getBoundingClientRect().width:null
                document.getElementsByTagName('html')[0].style.fontSize = (userW/1920*100)+'px';
            }*/
            var  userW = window.document.documentElement.getBoundingClientRect().width;
            userW<1500?userW = 1500:null
            window.document.documentElement.getBoundingClientRect().width<1000?userW = window.document.documentElement.getBoundingClientRect().width:null
            document.getElementsByTagName('html')[0].style.fontSize = (userW/1920*100)+'px';
        })()
        function changeWH(){
            $('.login_row').css('display','block');
            var allH=$('body').height();
            var headerH=$('._header').height();
            var footerH=$('._footer').height();
            $('._login').height(allH-headerH-footerH-5);
            $('._footer').css('padding-bottom','0px');
            var loginH=$('._login').height();
            var loginW=$('._login').width();
            var oneH=loginH/10;
            var oneW=loginW/10;
            $('#loginBox form').width(oneW*3);//.height(oneH*6);
            var middelFormH=$('#loginBox form').height();
            $('#loginBox').height(oneH*9.6).css('padding-left',oneW*3.5).css('padding-top',((oneH*9.6)-middelFormH)/3);

            //$('#loginBox').css('height',(oneH*0.6)+'px');//.css('margin-top',())
        }
   /* $(function(){
        changeWH();
    })*/
    </script>
</head>

<body style="height: 100%;width: 100%;">
<div style="height: 100%;width: 100%; ">
<div class="_header"  >
    <div class="box">
        <a href="http://kpt.datarj.com/#/">
            <img src="<%=request.getContextPath()%>/img/loginimg//logo.png">
        </a>
        <ul>
            <li>
                    <a class="fontSelect" href="http://kpt.datarj.com/#/">首页</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/product">产品介绍</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/getticket">领取电票</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/hysolut">行业解决方案</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/presale">售后服务</a>
            </li>
            <li>
                <a class="" href="http://kpt.datarj.com/#/news/list">平台动态</a>
            </li>
        </ul>
        <div class="btnbox " styl1e="visibility: hidden;">
        <a href="http://kpt.datarj.com/#/experience">
            <button type="button" class="ant-btn ant-btn-primary">
                <span>立即体验</span>
            </button></a>
        </div>
    </div>
</div>
<div class="_login " >
    <div class="box " id="loginBox" style="text-align: center;" >
        <form  action="<c:url value='/login/doLogin'/>" method="post" style="">
            <div  class="frombox " >
               <%-- <div class="header">用户登录</div>
                <div class="red" style="">${errors}</div>
                <input class="user" type="text" required name="dlyhid"  placeholder="请输入用户账号" />
                <input class="pwd" type="password" required name="yhmm" placeholder="请输入密码" />
                <div class="yzm">
                    <input type="text" name="code" class="code" placeholder="请输入验证码" required>
                    <img name="randImage" id="randImage" onclick="loadimage();"
                         src="<%=request.getContextPath()%>/image.jsp" width="100px" height="39px" border="1"
                         align="absmiddle">
                </div>
                <button type="primary" type="submit"  class="submit">立即登录</button>
                <p class="hide">还未注册？
                    <Link to="/reg">立即注册</Link>
                </p>--%>
                <div class="login_row" id="headerNew" style="padding-bottom: 0.2rem;width:4.2rem;">
                    <div id="leftTitle" style="float:left;font-size: 0.21rem;opacity: 1;font-weight: bolder;color:#ffffff;">
                        欢迎登陆</div>
                    <div id="rightTitle" style="float: right;font-size: 0.18rem;opacity: 1;color:#ffffff;">
                        开票通云服务平台</div>

                </div>
                <div  class="login_row" id="usernameInput" >
                    <img class="icon-user" src="<%=request.getContextPath()%>/img/loginimg/yonghu.png" />
                    <input type="text" name="dlyhid"  placeholder="请输入用户账号"/>
                </div>
                <div class="login_row" id="pwdInput" >
                    <img class="icon-pwd" src="<%=request.getContextPath()%>/img/loginimg/mima.png" />
                    <input type="text" name="yhmm"  placeholder="请输入密码"/>
                </div>
                   <div class="login_row yzm_row" >
                       <input type="text" id="inputYzm"
                              name="code" class="code" placeholder="请输入验证码" required>
                       <img name="randImage" id="randImage" onclick="loadimage();"
                            src="<%=request.getContextPath()%>/image.jsp" width="28%"
                            align="absmiddle"
                            style="float: right;height: 0.45rem;border: 1px solid #d9d9d9;border-radius: 0.10rem; "
                       >
                       <div class="red" style="clear: both;">${errors}</div>
                   </div>
                <div class="login_row" style="clear:both;padding-top: 0.01rem">
                    <button type="submit" class="primary" style="float: left;width:100%;height: 0.45rem;">
                        <span>登 录</span>
                    </button>

                </div>
            </div>
        </form>
    </div>


</div>
<div class="_footer" >
    <div class="box">
        <ul class="r0">
            <li>关于我们</li>
            <a  href="#">
                <img src="<%=request.getContextPath()%>/img/loginimg/gongsiLogo.jpg"></a>
        </ul>
        <div class="r1">
            <ul class="l2">
                <li>Tel:021-33566700</li>
                <li>Fax:021-33566700</li>
            </ul>

            <div class="l0" >
                Add:上海市漕宝路82号光大会展中心E座2802室
            </div>
            <div class="l1"> © Copyright 2011-2015 上海容津信息技术有限公司 沪ICP备15020560号</div>
        </div>
        <div class="r2">
            <div class="r20 item">
                <img class="er0" src="<%=request.getContextPath()%>/img/loginimg//erweima.png">
                <div>微信公众号</div>
            </div>
            <div class="r21 item">
                <img class="er1" src="<%=request.getContextPath()%>/img/loginimg//erweima1.png">
                <div>微信公众号</div>
            </div>

        </div>
    </div>
</div>

<script>
    if (!!window.ActiveXObject || "ActiveXObject" in window)
    {
        sessionStorage.newcome12?null:location.reload()
        sessionStorage.newcome12 = 200
    }
</script>
</div>
</body>

</html>