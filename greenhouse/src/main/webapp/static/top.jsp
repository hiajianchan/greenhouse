<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>温室环境监测系统</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            //顶部导航切换
            $(".nav li a").click(function () {
                $(".nav li a.selected").removeClass("selected")
                $(this).addClass("selected");
            })
        })
    </script>

</head>

<body style="background:url(${pageContext.request.contextPath}/images/topbg.gif) repeat-x;">

<div class="topleft">
    <a href="${pageContext.request.contextPath}/goMain" target="_parent"><img
            src="${pageContext.request.contextPath}/images/logo.png" title="系统首页"/></a>
</div>

<ul class="nav">
    <li><a href="${pageContext.request.contextPath}/user/pageList" target="rightFrame" class="selected"><img
            src="${pageContext.request.contextPath}/images/icon01.png" title="用户管理"/>
        <h2>用户管理</h2></a></li>
    <li><a href="${pageContext.request.contextPath}/role/pageList" target="rightFrame"><img
            src="${pageContext.request.contextPath}/images/icon02.png" title="角色管理"/>
        <h2>角色管理</h2></a></li>
    <li><a href="${pageContext.request.contextPath}/permission/pageList" target="rightFrame"><img
            src="${pageContext.request.contextPath}/images/icon03.png" title="权限管理"/>
        <h2>权限管理</h2></a></li>
    <%--<li><a href="../WEB-INF/View/tools.jsp" target="rightFrame"><img--%>
            <%--src="${pageContext.request.contextPath}/images/icon04.png" title="常用工具"/>--%>
        <%--<h2>常用工具</h2></a></li>--%>
    <%--<li><a href="../WEB-INF/View/computer.jsp" target="rightFrame"><img--%>
            <%--src="${pageContext.request.contextPath}/images/icon05.png" title="文件管理"/>--%>
        <%--<h2>文件管理</h2></a></li>--%>
    <%--<li><a href="../WEB-INF/View/tab.jsp" target="rightFrame"><img--%>
            <%--src="${pageContext.request.contextPath}/images/icon06.png" title="系统设置"/>--%>
        <%--<h2>系统设置</h2></a></li>--%>
</ul>
<div class="topright">
    <ul>
        <li><a>${currUser.name }  您好，欢迎使用信息管理系统</a></li>
        <li><a href="/logout" target="_parent">退出</a></li>
    </ul>
    <div class="user">
        <span>
        <shiro:hasRole name="admin">
            超级管理员
        </shiro:hasRole>
        <shiro:hasRole name="teacher">
             管理员
        </shiro:hasRole>
        <shiro:hasRole name="person">
            会员
        </shiro:hasRole>
        </span>
        <i></i>
        <b></b>
    </div>

</div>
</body>
</html>
