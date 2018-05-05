<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>温室环境监测系统</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.js"></script>

    <script type="text/javascript">
        $(function(){
            //导航切换
            $(".menuson li").click(function(){
                $(".menuson li.active").removeClass("active")
                $(this).addClass("active");
            });

            $('.title').click(function(){
                var $ul = $(this).next('ul');
                $('dd').find('ul').slideUp();
                if($ul.is(':visible')){
                    $(this).next('ul').slideUp();
                }else{
                    $(this).next('ul').slideDown();
                }
            });
        })
    </script>


</head>

<body style="background:#f0f9fd;">
<div class="lefttop"><span></span>管理信息</div>

<dl class="leftmenu">

    <dd>
        <div class="title">
            <span><img src="${pageContext.request.contextPath}/images/leftico01.png" /></span>用户管理
        </div>
        <ul class="menuson">
            <li ><cite></cite><a href="${pageContext.request.contextPath}/user/pageList" target="rightFrame">用户信息</a><i></i></li>
            <li><cite></cite><a href="${pageContext.request.contextPath}/user/toEdit" target="rightFrame">新增用户</a><i></i></li>
        </ul>
    </dd>


    <dd>
        <div class="title">
            <span><img src="${pageContext.request.contextPath}/images/leftico02.png" /></span>角色管理
        </div>
        <ul class="menuson">
            <li><cite></cite><a href="${pageContext.request.contextPath}/role/pageList"  target="rightFrame">角色信息</a><i></i></li>
            <li><cite></cite><a href="${pageContext.request.contextPath}/role/toEdit"  target="rightFrame">新增角色</a><i></i></li>
            <%--<li><cite></cite><a href="#">档案列表显示</a><i></i></li>--%>
        </ul>
    </dd>


    <dd><div class="title"><span><img src="${pageContext.request.contextPath}/images/leftico03.png" /></span>权限管理</div>
        <ul class="menuson">
            <li><cite></cite><a href="${pageContext.request.contextPath}/permission/pageList"  target="rightFrame">权限信息</a><i></i></li>
            <li><cite></cite><a href="${pageContext.request.contextPath}/permission/toEdit"  target="rightFrame">新增权限</a><i></i></li>
            <%--<li><cite></cite><a href="#">信息列表</a><i></i></li>--%>
            <%--<li><cite></cite><a href="#">其他</a><i></i></li>--%>
        </ul>
    </dd>


    <%--<dd><div class="title"><span><img src="${pageContext.request.contextPath}/images/leftico04.png" /></span>日期管理</div>--%>
        <%--<ul class="menuson">--%>
            <%--<li><cite></cite><a href="#">自定义</a><i></i></li>--%>
            <%--<li><cite></cite><a href="#">常用资料</a><i></i></li>--%>
            <%--<li><cite></cite><a href="#">信息列表</a><i></i></li>--%>
            <%--<li><cite></cite><a href="#">其他</a><i></i></li>--%>
        <%--</ul>--%>

    <%--</dd>--%>

</dl>
</body>
</html>
