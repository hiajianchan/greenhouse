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
    		//姓名不能为空
    		if ($('input[name="name"]').val() == '') {
    			alert("姓名不能为空！");
    			$('input[name="name"]').focus();
    			return false;
    		}
    		//手机号不能为空
    		if ($('input[name="mobile"]').val() == '') {
    			alert("手机号不能为空！");
    			$('input[name="mobile"]').focus();
    			return false;
    		}
    		//邮箱不能为空
    		if ($('input[name="email"]').val() == '') {
    			alert("邮箱不能为空！");
    			$('input[name="email"]').focus();
    			return false;
    		}
    		//密码不能为空
    		if ($('input[name="passwd"]').val() == '') {
    			alert("密码不能为空！");
    			$('input[name="passwd"]').focus();
    			return false;
    		}
    		//必须选择角色
    		if ($('input[name="roleList"]').val() == '') {
    			alert("请选择角色！");
    			$('input[name="roleList"]').focus();
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
	        <li><a href="${pageContext.request.contextPath}/user/toEdit">新增用户</a></li>
	    </ul>
	</div>
	
	<div class="formbody">
	    <div class="formtitle"><span>基本信息</span></div>
	    <form action="/user/save" method="post" onsubmit='return checkForm()'>
	    	<input type="hidden" id="uid" name="id" value="${user.id }"/>
		    <ul class="forminfo">
		    	<li><label>姓名</label><input name="name" type="text" class="dfinput" value="${user.name }"/><i></i></li>
		    	<li><label>性别</label>
		    		<input type="radio" name="gender" value="1" <c:if test="${1==user.gender }">checked="checked"</c:if>/>男&nbsp;&nbsp;
		    		<input type="radio" name="gender" value="2" <c:if test="${2==user.gender }">checked="checked"</c:if>/>女&nbsp;&nbsp;
		    		<input type="radio" name="gender" value="0" <c:if test="${0==user.gender }">checked="checked"</c:if>/>未知<i></i>
		    	</li>
		    	<li><label>手机号</label><input name="mobile" type="text" class="dfinput" value="${user.mobile}"/><i></i></li>
			    <li><label>用户邮箱</label><input id="email" name="email" type="text" class="dfinput" value="${user.email}"/><font color="red" id="email_msg"></font><i></i></li>
			    <li><label>密码</label><input name="passwd" type="text" class="dfinput" value="${user.passwd}"/><i></i></li>
			    <li><label>分配角色</label><cite> 
			    <c:forEach items="${roles}" var="role">
			        <input type="checkbox" name="roleList" value="${role.id}" 
			           <c:forEach items="${roleList}" var="roleId">
			    	       <c:if test="${roleId==role.id }">checked="checked"</c:if>
			    	   </c:forEach>
			        />${role.rolenameCh}&nbsp;
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
		
		//邮箱输入失去焦点事件
		$('#email').blur(function() {
			var uid = $('#uid').val();
			var value = $('#email').val();
			if (value == null || value == '') {
				return;
			}
			$.ajax({
				type:"GET",
				url:"/user/checkUserOnly",
				dataType:"json",
				data:{email:value, uid:uid},
				success:function(data) {
					if (data['status'] == 200) {
						//该邮箱已被注册
						$('#email_msg').html(data['msg']);
						$('#email').focus();
					} else {
						//该邮箱未被注册
						$('#email_msg').html('');
					}
				} 
			});
		});
		
	});
</script>
</html>
