
/*
 * 画出地图
 */

var cityCode = cityMap[cityName]
var mapUrl = "/json/citys/" + cityCode + ".json";
			
$.get(mapUrl, function(geoJson) {
	echarts.registerMap('map', geoJson);
    var chartMap = echarts.init(document.getElementById('map_div'));
    chartMap.setOption({
		series: [{
			type: 'map',
			map: 'map',
			zoom: 1.2,
			itemStyle: {
				normal: {
					areaColor: '#8E8E8E',
						borderColor: '#111'
				 },
				emphasis: {
					 areaColor: '#8E8E8E'
				}
			}
		 }]
	});
});

/**
 * 画出温度饼状图
 */
var chartTem = echarts.init(document.getElementById('tem_div'));
tem_option = {
	
	series : [ {
		name : '业务指标',
		type : 'gauge',
		radius: "96%", //仪表大小
		startAngle: 210, //开始角度
		endAngle: -30, //结束角度
		min: -20, //开始最小值
		splitNumber:12,  //刻度分段
		axisLine : {
			 show : true,
			 lineStyle : { // 属性lineStyle控制线条样式
			 color : [ //表盘颜色
				 [0.1667, "#0072E3" ],//-20-0%处的颜色
				 [0.25, "#7D7DFF"],//0-20处的颜色
				 [0.46, "#93FF93" ],//20-35处的颜色
				 [0.75, "#FFE66F"],//35-70处的颜色
				 [1, "#FF5151"], //70-100的颜色
				 ],
				 width : 30//表盘宽度
				 }
			  },
		
		detail : {
			formatter : '{value}℃'
		},
		data : [ {
			value : 0,
			name : '温度'
		} ]
	} ]
};


/**
 * 画出湿度饼状图
 */

var chartHum = echarts.init(document.getElementById('hum_div'));
hum_option = {
	
	series : [ {
		name : '业务指标',
		type : 'gauge',
		radius: "96%", //仪表大小
		startAngle: 210, //开始角度
		endAngle: -30, //结束角度
		detail : {
			formatter : '{value}%'
		},
		data : [ {
			value : 0,
			name : '湿度'
		} ]
	} ]
};
chartTem.setOption(tem_option, true);
chartHum.setOption(hum_option, true);
//setInterval(function() {
//	tem_option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
//	chartTem.setOption(tem_option, true);
//}, 2000);
//
//setInterval(function() {
//	hum_option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
//	chartHum.setOption(hum_option, true);
//}, 2000);

/**
 * websocket 显示实时温湿度数据
 */

var socket;
if(!window.WebSocket){
	window.WebSocket = window.MozWebSocket;
}

if(window.WebSocket){
	socket = new WebSocket("ws://39.106.184.51:1125/websocket");
	socket.onmessage = function(event){
		var tem = event.data.split(',')[0];
		var hum = event.data.split(',')[1];
		
		tem_option.series[0].data[0].value = tem;
		hum_option.series[0].data[0].value = hum;
		chartTem.setOption(tem_option, true);
		chartHum.setOption(hum_option, true);
	};

	socket.onopen = function(event){
//		ta.value = "你当前的浏览器支持WebSocket,请进行后续操作\r\n";
	};

	socket.onclose = function(event){
//		var ta = document.getElementById('responseContent');
//		ta.value = "";
//		ta.value = "WebSocket连接已经关闭\r\n";
	};
}else{
	alert("您的浏览器不支持WebSocket");
}

/**
 * 画出当天温度和湿度
 */
var chartDay_data = echarts.init(document.getElementById('data_area'));
day_data_option = {
	title : {
		text : '本周数据',
		textStyle: {
			fontSize: 14
		}
	},
	tooltip : {
		trigger : 'axis'
	},
	legend : {
		data : [ '温度', '湿度' ]
	},
	grid : {
		left : '20%',
		right : '20%',
		top:'10%',
		bottom : '3%',
		containLabel : true
	},
	toolbox : {
		feature : {
			saveAsImage : {}
		}
	},
	xAxis : {
		type : 'category',
		boundaryGap : false,
		data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
	},
	yAxis : {
		type : 'value'
	},
	series : [ {
		name : '温度',
		type : 'line',
		stack : '总量',
		data : [ 120, 132, 101, 134, 90, 230, 210 ]
	}, {
		name : '湿度',
		type : 'line',
		stack : '总量',
		data : [ 220, 182, 191, 234, 290, 330, 310 ]
	}, ]
};
chartDay_data.setOption(day_data_option, true);
/*
 * 显示实时时间
 */
var nowTime;
function play() {
	var time = new Date();
	var month = time.getMonth();
	if (month < 10) {
		month = "0" + month;
	}
	var day = time.getDate();
	if (day < 10) {
		day = "0" + day;
	}
	var minute = time.getMinutes();
	if (minute < 10) {
		minute = "0" + minute;
	}
	var second = time.getSeconds();
	if (second < 10) {
		second = "0" + second;
	}
	nowTime = time.getFullYear() + "-" + month + "-" + day + "  "
			+ time.getHours() + ":" + minute + ":" + second;
	document.getElementById("cuTime").innerHTML = nowTime;
}
setInterval(play, 1000);