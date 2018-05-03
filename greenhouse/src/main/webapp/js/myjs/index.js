/*
 * 画出地图
 */

var cityCode = cityMap['郑州']
var mapUrl = "/json/citys/" + cityCode + ".json";
			
$.get(mapUrl, function(geoJson) {
	echarts.registerMap('map', geoJson);
    var chartMap = echarts.init(document.getElementById('map_div'));
    chartMap.setOption({
		series: [{
			type: 'map',
			map: 'map',
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
		
		axisLine : {
			 show : true,
			 lineStyle : { // 属性lineStyle控制线条样式
			 color : [ //表盘颜色
				 [0.2, "#0072E3" ],//0%-20%处的颜色
				 [0.4, "#28FF28"],//20%-40%处的颜色
				 [0.8, "#FFD306" ],//40%-80%处的颜色
				 [1, "#EA0000"],//80%-100%处的颜色
				 ],
				 width : 30//表盘宽度
				 }
			  },
		
		detail : {
			formatter : '{value}%'
		},
		data : [ {
			value : 50,
			name : '温度'
		} ]
	} ]
};

setInterval(function() {
	tem_option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
	chartTem.setOption(tem_option, true);
}, 2000);

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
			value : 50,
			name : '湿度'
		} ]
	} ]
};

setInterval(function() {
	hum_option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
	chartHum.setOption(hum_option, true);
}, 2000);

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