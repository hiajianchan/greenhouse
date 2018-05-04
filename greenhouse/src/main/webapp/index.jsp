<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<title>温湿度监测平台首页</title>
		<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/css/wea_headStyle.css" rel="stylesheet" type="text/css">
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
				<div id="nav2_1_1">
					<div style="height:100%;float:left;">
						<img src="${pageContext.request.contextPath}/images/address.png" height="36px" width="36px;"/>
					</div>
					<div style="float:left;">
						<font>您当前所在城市：郑州</font>
					</div>
				</div>
				<div id="nav2_1_2">
					<div id="map_div"></div>
					<div id="time_div"><font id="cuTime"></font></div>
				</div>

				<div id="weather_div">
					<!-- <div class="wea_div">
						<div class="wea_date">今天（阴转小雨）</div>
						<div class="wea_wea">
							<big class="png40 d02"></big>
							<big class="png40 n07"></big>
						</div>
						<div class="wea_tem">12/25</div>
					</div>
					<div class="wea_div">
						<div class="wea_date">明天（阴转小雨）</div>
						<div class="wea_wea">
							<big class="png40 d02"></big>
							<big class="png40 n07"></big>
						</div>
						<div class="wea_tem">12/25</div>
					</div>
					<div class="wea_div">
						<div class="wea_date">后天（阴转小雨）</div>
						<div class="wea_wea">
							<big class="png40 d02"></big>
							<big class="png40 n07"></big>
						</div>
						<div class="wea_tem">12/25</div>
					</div> -->
				
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
					<img alt="数据统计" width="36px;" height="36px;" src="${pageContext.request.contextPath}/images/statistic.png">
				</div>
				<div style="float:left;"><font>数据统计</font></div>
				<div id="data_btn">这是按钮区域</div>
			</div>
			<div id="data_area">这里是折线图</div>
		</div>
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/myjs/index.js"></script>
	</body>
	<script type="text/javascript">
	/**
	 * 天气
	 */
	$.ajax({
		type : "GET",
		url : "/api/getWeather",
		data : {
			cityName : "郑州"
		},
		dataType : "json",
		success : function(result) {
			// console.log(result);
			if (result['status'] == 200) {// 请求成功
				var data = result['result'];
				console.log(data.length);
				for (var i = 0; i < data.length; i++) {
					console.log(data[i]);
					var item = "<div class='wea_div'>" + "<div class='wea_date'>"
							+ data[i]['date'] + data[i]['wea'] + "</div>"
							+ "<div class='wea_wea'>" + "<big class='"
							+ data[i]['img1'] + "'></big>" + "<big class='"
							+ data[i]['img2'] + "'></big>" + "</div>"
							+ "<div class='wea_tem'>" + data[i]['tem'] + "</div>"
							+ "</div>"
					$("#weather_div").append(item);
				}
			}
		},
	});
	</script>
</html>