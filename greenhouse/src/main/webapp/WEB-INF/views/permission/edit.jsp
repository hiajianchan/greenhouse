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
    		
    		//资源对象不能为空
    		if ($("#obj_select option:selected").val() == '') {
    			alert("请选择资源对象");
    			$("#obj_select").focus();
    			return false;
    		} else {
    			$("#objId").val($("#obj_select option:selected").val());
    		}
    		//对资源对象的操作不能为空
    		if ($("#op_select option:selected").val() == '') {
    			alert("请选择对资源对象的操作");
    			$("#op_select").focus();
    			return false;
    		} else {
    			$("#op").val($("#op_select option:selected").val());
    		}
    		//权限描述不能为空
    		if ($("input[name='perNameCh']").val() == '') {
    			alert("权限描述不能为空");
    			$("input[name='perNameCh']").focus();
    			return false;
    		}
    		
 			var objId = $("#obj_select option:selected").val();
 			var opVal = $("#op_select option:selected").val();
 			
 			var exist = false;
    		
    		$.ajax({
				type:"GET",
				url:"/permission/checkOnly",
				async: false,
				dataType:"json",
				data:{objId:objId, opVal:opVal},
				success:function(data) {
					if (data['status'] == 200) {
						//该权限已经存在
						$('#perName_msg').html(data['msg']);
						$("#op_select").focus();
						exist = true;
					} else {
						//该角色未存在
						$('#rolename_msg').html('');
					}
				} 
			});
    		if (exist) {
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
	        <li><a href="${pageContext.request.contextPath}/permission/toEdit">新增权限</a></li>
	    </ul>
	</div>
	
	<div class="formbody">
	    <div class="formtitle"><span>基本信息</span></div>
	    <form action="/permission/save" method="post" onsubmit='return checkForm()'>
	    	<input type="hidden" id="pid" name="pid" value="${permission.id }"/>
	    	<input type="hidden" id="objId" name="objId" value="${permission.objId }"/>
	    	<input type="hidden" id="op" name="op" value="">
		    <ul class="forminfo">
		    	<li><label>资源对象</label>
		    		<select class="dfinput" id="obj_select">
		    			<option value="">请选择</option>
		    			<c:forEach items="${resobj}" var="obj">
		    				<option value="${obj.id }">${obj.nameCh }</option>
		    			</c:forEach>
		    		</select><i></i>
		    	</li>
		    	<li><label>操作方式</label>
		    		<select class="dfinput" id="op_select">
		    			<option value="">请选择</option>
		    			<option value="*">增删查改</option>
		    			<option value="read">查看</option>
		    			<option value="create">增加</option>
		    			<option value="update">修改</option>
		    			<option value="delete">删除</option>
		    		</select><font color="red" id="perName_msg"></font><i></i>
		    	</li>
		    	<li><label>权限描述(中文)</label><input name="perNameCh" type="text" class="dfinput" value="${permission.perNameCh}"/><i></i></li>
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
				async: false,
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
