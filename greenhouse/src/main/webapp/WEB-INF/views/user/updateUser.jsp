<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>修改用户</title>
</head>
<body>
<form action="/user/updateUser">
    <input type="hidden" name="u_id" value="${user.u_id}">
    用户名:<input name="userName" value="${user.userName}"><br>
    密码:<input type="password" name="passWord" value="${user.passWord}"><br>
    角色:<select name="roleId">
    <c:forEach items="${roles}" var="r">
        <option value="${r.r_id}" <c:if test="${r.r_id==user.roleId}">selected="selected"</c:if> >${r.roledesc}</option>
    </c:forEach>
</select>
    <br>
    <input type="submit" value="提交">
    <input type="reset" value="重置">
</form>
</body>
</html>
