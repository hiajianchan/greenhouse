package edu.haut.greenhouse.pojo.dictionary;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description 天气预报城市代码
 * @author chen haijian
 * @date 2018年5月3日
 * @version 1.0
 */
@Table(name = "wea_city")
public class WeatherCity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 城市名
	 */
	@Column(name = "city_name")
	private String cityName;
	
	/**
	 * code值
	 */
	@Column(name ="code")
	private String code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "WeatherCity [id=" + id + ", cityName=" + cityName + ", code=" + code + "]";
	}
	
	
}
