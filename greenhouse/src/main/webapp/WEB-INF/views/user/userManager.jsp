<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>温室环境监测系统</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
  $(".tip").fadeOut(200);
});

  $(".sure").click(function(){
  $(".tip").fadeOut(100);
});

  $(".cancel").click(function(){
  $(".tip").fadeOut(100);
});

});
</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/goIndex">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/user/pageList">用户管理</a></li>
    <%--<li><a href="#">基本内容</a></li>--%>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<%--<ul class="toolbar">--%>
        <%--<li class="click"><span><img src="${pageContext.request.contextPath}/images/t01.png" /></span>添加</li>--%>
        <%--<li class="click"><span><img src="${pageContext.request.contextPath}/images/t02.png" /></span>修改</li>--%>
        <%--<li><span><img src="${pageContext.request.contextPath}/images/t03.png" /></span>删除</li>--%>
        <%--<li><span><img src="${pageContext.request.contextPath}/images/t04.png" /></span>统计</li>--%>
        <%--</ul>--%>
        
        
        <%--<ul class="toolbar1">--%>
        <%--<li><span><img src="${pageContext.request.contextPath}/images/t05.png" /></span>设置</li>--%>
        <%--</ul>--%>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <%--<th><input name="" type="checkbox" value="" checked="checked"/></th>--%>
        <%-- <th>用户ID<i class="sort"><img src="${pageContext.request.contextPath}/images/px.gif" /></i></th> --%>
        <th>姓名</th>
        <th>性别</th>
		<th>手机</th>
		<th>邮箱</th>
        <th>具有的角色</th>
        <th>状态</th>
        <th>上次登录时间</th>
        <th>上次登录地点</th>
        <%--<th>是否审核</th>--%>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
<c:forEach items="${result.rows}" var="ul">

        <tr>
        <%--<td><input name="" type="checkbox" value="" /></td>--%>
        <td>${ul.user.name}</td>
        <td>
        	<c:if test="${ul.user.gender == 1 }">男</c:if>
        	<c:if test="${ul.user.gender == 2 }">男</c:if>
        	<c:if test="${ul.user.gender == 0 }">未知</c:if>
        </td>
        <td>${ul.user.mobile}</td>
        <td>${ul.user.email}</td>
        <td>
            <c:forEach items="${ul.roles}" var="role">
                ${role.rolename}&nbsp;
            </c:forEach>
        </td>
        <td>
        	<c:if test="${ul.user.status == 1 }"><font color="green">正常</font></c:if>
        	<c:if test="${ul.user.status == 3 }"><font color="red">已冻结</font></c:if>
        </td>
        <td><fmt:formatDate value="${ul.info.lastLogin }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td>${ul.info.lastCountry }&nbsp;${ul.info.lastCity }</td>
        <%--<td>2013-09-09 15:05</td>--%>
        <td>
           <shiro:hasAnyRoles name="root,admin">
               <c:if test="${ul.user.status==1}"><a href="${pageContext.request.contextPath}/user/changeStat?id=${ul.user.id }&status=3"><font color="red">冻结</font></a></c:if>
               <c:if test="${ul.user.status==3}"><a href="${pageContext.request.contextPath}/user/changeStat?id=${ul.user.id }&status=1"><font color="green">解冻</font></a></c:if>
       		   <a href="${pageContext.request.contextPath}/user/toEdit?uid=${ul.user.id}" class="tablelink">修改</a>
           </shiro:hasAnyRoles>
       </td>
        </tr>

</c:forEach>
        </tbody>
    </table>
    
   
    <div class="pagin">
    	<div class="message">共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页</div>
        <ul class="paginList">
        <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
        <li class="paginItem"><a href="javascript:;">1</a></li>
        <li class="paginItem current"><a href="javascript:;">2</a></li>
        <li class="paginItem"><a href="javascript:;">3</a></li>
        <li class="paginItem"><a href="javascript:;">4</a></li>
        <li class="paginItem"><a href="javascript:;">5</a></li>
        <li class="paginItem more"><a href="javascript:;">...</a></li>
        <li class="paginItem"><a href="javascript:;">10</a></li>
        <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
        </ul>
    </div>
    
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的修改 ？</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    
    
    
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>
