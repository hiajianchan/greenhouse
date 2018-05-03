var cityCode = cityMap['郑州']
var mapUrl = "/json/citys/" + cityCode + ".json";
			
$.get(mapUrl, function(geoJson) {
	echarts.registerMap('map', geoJson);
    var chart = echarts.init(document.getElementById('map_div'));
    chart.setOption({
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


/*
 *显示实时时间 
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