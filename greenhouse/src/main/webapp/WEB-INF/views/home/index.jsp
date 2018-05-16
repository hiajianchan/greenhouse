<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8" content="width=device-width, initial-scale=1">
		<title>温湿度监测平台首页</title>
		<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/css/wea_headStyle.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/china.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/city-map.js"></script>
		
		<script type="text/javascript">
		 	var cityName= '${cityName}';
			if (cityName == '' ||cityName == '内网IP') {
				cityName ="郑州";
			}
		</script>
	
	</head>
	<body style="background: #ededed;">
		<div id="nav1">
			<div id="log_div">
				<div style="height:100%;float:left;">
					<img src="${pageContext.request.contextPath}/images/index_logo.png" height="40px" width="40px;"/>
				</div>
				<div style="float:left;height: 100%;text-align: center;">
						<p style="height: 100%;">
							<font style="font-size:20px; color:white;">温室环境监测系统</font>
						</p>
				</div>
			</div>
			<shiro:hasRole name="root">
				<div id="manage_div">
					<a href="${pageContext.request.contextPath}/main" title="进入后台">
						<img alt="用户管理" src="${pageContext.request.contextPath}/images/manage.png" style="height: 40px;width:40px">
					</a>
				</div>
			</shiro:hasRole>
			<div id="user_div">
				<div id="user_info"><p style="height: 100%;vertical-align:middle;"><font color="white">${currUser.name } 你好，欢迎使用系统</font></p></div>
				<div id="out"><a href="${pageContext.request.contextPath}/logout"><font color="white">登出</font></a></div>
			</div>
		</div>  
			
		<div id="nav2">
			<div id="nav2_1">
				<div id="nav2_1_1">
					<div style="height:100%;float:left;">
						<img src="${pageContext.request.contextPath}/images/earth55.png" height="36px" width="36px;"/>
					</div>
					<div style="float:left;">
						<font>您当前所在城市：${cityName}</font>
					</div>
				</div>
				<div id="nav2_1_2">
					<div id="map_div"></div>
					<div id="time_div"><p style="height:100%;"><font id="cuTime"></font></p></div>
				</div>

				<div id="weather_div">
									
				</div>
			</div>

			<div id="nav2_2">
				<div id="tem_div"></div>
				<div id="hum_div"></div>
			</div>
		</div>

		<div id="nav3">
			<div id="nav3_1">
				<div id="data_info">
					<img alt="数据统计" width="30px;" height="30px;" src="${pageContext.request.contextPath}/images/i05.png">
				</div>
				<div style="float:left;"><font>数据统计</font></div>
				<div id="data_btn">
					<div class="btn_by_date">
						<a href="javascript:void(0)" id="today_temhum_data"><font size="4px">今天</font></a>
					</div>
					<div class="btn_by_date">
						<a href="javascript:void(0)"><font size="4px">本周</font></a>
					</div>
					<div class="btn_by_date">
						<a href="javascript:void(0)"><font size="4px">本月</font></a>
					</div>
					<div class="btn_by_date">
						<a href="javascript:void(0)"><font size="4px">本年</font></a>
					</div>
				</div>
			</div>
			<div id="data_area">这里是折线图</div>
		</div>
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/myjs/index.js"></script>
	</body>
	<script type="text/javascript">
	
	
	</script>
</html>