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

  	//全选或者全不选
	$('#rid_th').change(function(){
		if ($('#rid_th').is(':checked')) {
			//选中，则id全选中
			$("input[name='rid_td']").attr("checked",true);
		} else {
			//未选中，则ID全未选中
			$("input[name='rid_td']").attr("checked",false);
		}
	});
	
  
});

	
	
	//删除用户
	function changeStat(status) {
		//获取选中的用户id
		var chk_value =[];
		$("input[name='rid_td']:checked").each(function(){
			chk_value.push(this.value);
		});
		window.location.href = "/role/changeStat?id="+chk_value+"&status="+status;
	}
</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/goIndex">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/role/pageList">角色管理</a></li>
    <%--<li><a href="#">基本内容</a></li>--%>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
    	<li><a href="${pageContext.request.contextPath}/role/toEdit"><span><img src="${pageContext.request.contextPath}/images/t01.png"/></span>添加</a></li>
       <%--<li class="click"><span><img src="${pageContext.request.contextPath}/images/t02.png" /></span>修改</li>--%>        
 		<li><a href="javascript:;" onclick="changeStat(2)"><span><img src="${pageContext.request.contextPath}/images/t03.png" /></span>冻结</a></li>
 		<li><a href="javascript:;" onclick="changeStat(1)"><span><img src="${pageContext.request.contextPath}/images/t02.png" /></span>解冻</a></li>
        </ul>
        
        <%--<ul class="toolbar1">--%>
        <%--<li><span><img src="${pageContext.request.contextPath}/images/t05.png" /></span>设置</li>--%>
        <%--</ul>--%>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <th><input id="rid_th" type="checkbox" value=""/></th>
        <%-- <th>用户ID<i class="sort"><img src="${pageContext.request.contextPath}/images/px.gif" /></i></th> --%>
        <th>角色</th>
        <th>角色描述</th>
        <th>具有的权限</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>修改时间</th>
        <%--<th>是否审核</th>--%>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
<c:forEach items="${result.rows}" var="ul">

        <tr>
        <td><input name="rid_td" type="checkbox" value="${ul.role.id }" /></td>
        <td>${ul.role.rolename}</td>
        <td>${ul.role.rolenameCh}</td>
        <td>
            <c:forEach items="${ul.perList}" var="per">
                ${per.perName}&nbsp;
            </c:forEach>
        </td>
        <td>
        	<c:if test="${ul.role.status == 1 }"><font color="green">正常</font></c:if>
        	<c:if test="${ul.role.status == 2 }"><font color="red">已冻结</font></c:if>
        </td>
        <td><fmt:formatDate value="${ul.role.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><fmt:formatDate value="${ul.role.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td>
           <shiro:hasAnyRoles name="root,admin">
               <c:if test="${ul.role.status==1}"><a href="${pageContext.request.contextPath}/role/changeStat?id=${ul.role.id }&status=2"><font color="red">冻结</font></a></c:if>
               <c:if test="${ul.role.status==2}"><a href="${pageContext.request.contextPath}/role/changeStat?id=${ul.role.id }&status=1"><font color="green">解冻</font></a></c:if>
       		   <a href="${pageContext.request.contextPath}/role/toEdit?rid=${ul.role.id}" class="tablelink">修改</a>
           </shiro:hasAnyRoles>
       </td>
        </tr>

</c:forEach>
        </tbody>
    </table>
   
    <div class="pagin">
    	<div class="message">共<i class="blue">${result.total}</i>条记录，当前显示第&nbsp;<i class="blue">${result.pageNum}&nbsp;</i>页</div>
        <ul class="paginList">
	        <c:if test="${result.hasPreviousPage == true }">
	        	<li class="paginItem"><a href="${pageContext.request.contextPath }/role/pageList?page=${result.pageNum-1}"><span class="pagepre"></span></a></li>
       		</c:if>
       		<c:forEach var="i" begin="${fromPage }" end="${toPage }">
       			<li class="paginItem <c:if test='${i == result.pageNum}'>current</c:if>">
       				<a href="${pageContext.request.contextPath }/role/pageList?page=${i}">${i }</a>
       			</li>
       		</c:forEach>
       		<c:if test="${result.hasNextPage == true }">
       			<li class="paginItem"><a href="${pageContext.request.contextPath }/role/pageList?page=${result.pageNum+ 1}"><span class="pagenxt"></span></a></li>
        	</c:if>
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
