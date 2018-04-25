<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>大路网络IMS仓储管理系统</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="${pageContext.request.contextPath}/goIndex">首页</a></li>
        <li><a href="${pageContext.request.contextPath}/role/queryPermission">新增角色</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <ul class="forminfo">
        <form action="/role/insertRole" method="post">
        <li><label>角色</label><input name="rolename" type="text" class="dfinput"/><i></i></li>
        <li><label>角色描述</label><input name="roledesc" type="text" class="dfinput"/><i></i></li>
     <%--    <li><label>所具有的权限</label><cite> <c:forEach items="${perList}" var="p">
            <input type="checkbox" name="peridList" value="${p.p_id}">${p.perDesc}&nbsp;
        </c:forEach></cite></li> --%>
        <%--<li><label>引用地址</label><input name="" type="text" class="dfinput" value="http://www..com/html/uidesign/" /></li>--%>
        <%--<li><label>文章内容</label><textarea name="" cols="" rows="" class="textinput"></textarea></li>--%>
        <li><label>&nbsp;</label><input type="submit" class="btn" value="提交"/>&nbsp;&nbsp;<input type="reset" value="重置"
                                                                                                 class="btn"></li>
        </form>
    </ul>


</div>
</body>
</html>
