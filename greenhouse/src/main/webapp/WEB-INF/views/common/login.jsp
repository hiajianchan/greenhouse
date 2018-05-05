<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>温室温湿度监测平台</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/cloud.js" type="text/javascript"></script>

    <script language="JavaScript">
        $(function () {
            $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            $(window).resize(function () {
                $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            })
        });
    </script>

</head>

<body style="background-color:#1c77ac; background-image:url(${pageContext.request.contextPath}/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">


<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>


<div class="logintop">
    <span>温室温湿度监测平台</span>
    <ul>
        <li><a href="#">回首页</a></li>
        <li><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
    </ul>
</div>

<div class="loginbody">

    <span class="systemlogo"></span>

    <div class="loginbox">

        <form method="post" action="${pageContext.request.contextPath}/login">
            <ul>
                <li>
                    <input name="email" type="text" class="loginuser" onclick="JavaScript:this.value=''" value="<shiro:principal/>"/>
                </li>
                <li>
                    <input name="passwd" type="password" class="loginpwd" onclick="JavaScript:this.value=''"/>
                    <span><font color="red">${msg}</font></span>
                </li>
                <li>
                    <input type="submit" class="loginbtn" value="登录"/>
                    <label><input name="rememberme" type="checkbox" checked="checked"/>记住密码</label><label>
                    <a href="#">忘记密码？</a></label>
                </li>

            </ul>
        </form>

    </div>

</div>


<div class="loginbm">温湿度监测平台</div>
</body>
</html>
