package edu.haut.greenhouse.spider;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.haut.greenhouse.common.util.StringUtil;
import edu.haut.greenhouse.mapper.dictionary.WeatherCityMapper;
import edu.haut.greenhouse.pojo.dictionary.WeatherCity;

/**
 * @Description 天气预报爬虫
 * @author chen haijian
 * @date 2018年5月3日
 * @version 1.0
 */
@Component
public class WeatherSpider {

	@Autowired
	private WeatherCityMapper weatherCityMapper;
	
	public List<Weather> getWeather(String cityname) {
//		String code = getCityCode(cityname);
		String code = "101180101";
		String url = "http://www.weather.com.cn/weather/" + code + ".shtml";
	
		List<Weather> weaList = null;
		try {
			Document doc = Jsoup.connect(url).get();
			Element div = doc.getElementById("7d");
			
			Elements ul = div.getElementsByClass("t");
			Element lis = ul.get(0);                  //七天天气
			Elements liList = lis.getElementsByClass("sky");
			weaList = new ArrayList<>();
			int i = 0;    //获取三天数据
			for (Element item : liList) {
				if (i >= 3) {
					break;
				}
				Weather weather = new Weather();
				String date = item.getElementsByTag("h1").text();  //时间
				weather.setDate(date);
				
				String wea = item.getElementsByClass("wea").get(0).text();  //天气
				weather.setWea(wea);
				
				String tem = item.getElementsByClass("tem").get(0).text();  //温度
				weather.setTem(tem);
				
				Elements bigs = item.getElementsByTag("big");
				if (bigs.size() > 0) {
					weather.setImg1(bigs.get(0).attr("class"));
				}
				if (bigs.size() >1) {
					weather.setImg2(bigs.get(1).attr("class"));
				}
				
				weaList.add(weather);
				i++;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return weaList;
	}
	
	/**
	 * 根据城市名获取城市code值
	 * @param cityName
	 * @return
	 */
	public String getCityCode(String cityName) {
		if (!StringUtil.isNotEmpty(cityName)) {
			return null;
		}
		
		WeatherCity wc1 = new WeatherCity();
		wc1.setCityName(cityName);
		WeatherCity weatherCity = weatherCityMapper.selectOne(wc1);
		 
		if (weatherCity != null) {
			return weatherCity.getCode();
		}
		return null;
	}
	
	public static void main(String[] args) {
		WeatherSpider spider = new WeatherSpider();
		List<Weather> list = spider.getWeather("郑州");
		System.out.println(list);
	}
}
