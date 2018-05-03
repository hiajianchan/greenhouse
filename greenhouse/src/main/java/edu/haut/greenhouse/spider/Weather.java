package edu.haut.greenhouse.spider;
/**
 * @Description 天气
 * @author chen haijian
 * @date 2018年5月3日
 * @version 1.0
 */
public class Weather {

	private String date;
	
	private String wea;
	
	private String tem;
	
	private String img;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWea() {
		return wea;
	}

	public void setWea(String wea) {
		this.wea = wea;
	}

	public String getTem() {
		return tem;
	}

	public void setTem(String tem) {
		this.tem = tem;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "Weather [date=" + date + ", wea=" + wea + ", tem=" + tem + ", img=" + img + "]";
	}

}
