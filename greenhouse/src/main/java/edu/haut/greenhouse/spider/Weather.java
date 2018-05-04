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
	
	private String img1;
	
	private String img2;

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

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	@Override
	public String toString() {
		return "Weather [date=" + date + ", wea=" + wea + ", tem=" + tem + ", img1=" + img1 + ", img2=" + img2 + "]";
	}

	
}
