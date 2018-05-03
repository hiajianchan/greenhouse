<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<title>温湿度监测平台首页</title>
		<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/china.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/city-map.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myjs/index.js"></script>
		
		<script type="text/javascript">
		 		
		</script>
	
	</head>
	<body style="background: #ededed">
		<div id="nav1">
			<div id="user_div">
				<div id="user_info"><font color="white">陈海建 你好，欢迎使用系统</font></div>
				<div id="out"><a href="#"><font color="white">登出</font></a></div>
			</div>
		</div>  
			
		<div id="nav2">
			<div id="nav2_1">
				<div id="nav2_1_1">您当前所在城市：郑州</div>
				<div id="nav2_1_2">
						<div id="map_div"></div>
						<div id="time_div"><font id="cuTime"></font></div>
				</div>

				<div id="weather">天气</div>
					
			</div>


			<div id="nav2_2">
				<div id="tem_div"></div>
				<div id="hum_div"></div>
			</div>
		</div>

		<div id="nav3">折线图</div>
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/myjs/index.js"></script>
	</body>
</html>