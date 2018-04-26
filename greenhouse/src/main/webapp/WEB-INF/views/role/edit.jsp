<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>温室环境监测系统</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
    	function checkForm() {
    		
    		//角色不能为空
    		if ($('input[name="rolename"]').val() == '') {
    			alert("角色不能为空！");
    			$('input[name="rolename"]').focus();
    			return false;
    		}
    		//角色描述不能为空
    		if ($("input[name='rolenameCh']").val() == '') {
    			alert("角色描述不能为空");
    			$("input[name='rolenameCh']").focus();
    			return false;
    		}
    		//角色具有的权限不能为空
    		if ($('input[name="perList"]').val() == '') {
    			alert("角色不能为空！");
    			$('input[name="perList"]').focus();
    			return false;
    		}
    		return true;
    	}
    </script>
</head>

<body>

	<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
	        <li><a href="${pageContext.request.contextPath}/goIndex">首页</a></li>
	        <li><a href="${pageContext.request.contextPath}/role/toEdit">新增角色</a></li>
	    </ul>
	</div>
	
	<div class="formbody">
	    <div class="formtitle"><span>基本信息</span></div>
	    <form action="/role/save" method="post" onsubmit='return checkForm()'>
	    	<input type="hidden" id="rid" name="rid" value="${role.id }"/>
		    <ul class="forminfo">
		    	<li><label>角色(英文)</label><input name="rolename" type="text" class="dfinput" value="${role.rolename }"/><font color="red" id="rolename_msg"></font><i></i></li>
		    	<li><label>角色描述(中文)</label><input name="rolenameCh" type="text" class="dfinput" value="${role.rolenameCh}"/><i></i></li>
			    <li><label>分配角色</label><cite> 
			    <c:forEach items="${permissions}" var="per">
			        <input type="checkbox" name="perList" value="${per.id}" 
			           <c:forEach items="${perList}" var="perId">
			    	       <c:if test="${perId==per.id }">checked="checked"</c:if>
			    	   </c:forEach>
			        />${per.perNameCh}&nbsp;
			    </c:forEach></cite></li>
			    <li style="text-align:center"><label style="color:red">${msg}</label></li>
			    <li><label>&nbsp;</label><input type="submit" class="btn" value="提交"/>&nbsp;&nbsp;
			    <input type="reset" value="重置" class="btn"></li>
		    </ul>
	    </form>
	
	
	</div>
</body>
<script type="text/javascript">
	$(function(){
		
		//校验输入角色是否唯一
		$("input[name='rolename']").blur(function() {
			var rid = $("#rid").val();
			var rolename = $("input[name='rolename']").val();
			if (rolename == null || rolename == '') {
				return;
			}
			$.ajax({
				type:"GET",
				url:"/role/checkRoleOnly",
				dataType:"json",
				data:{rolename:rolename, rid:rid},
				success:function(data) {
					if (data['status'] == 200) {
						//该角色已经存在
						$('#rolename_msg').html(data['msg']);
						$("input[name='rolename']").focus();
					} else {
						//该角色未存在
						$('#rolename_msg').html('');
					}
				} 
			});
		});
		
	});
</script>
</html>
