package edu.haut.greenhouse.bean.temhum;
/**
 * @Description 温湿度数据
 * @author chen haijian
 * @date 2018年5月3日
 * @version 1.0
 */

import java.util.Date;

public class TemHumItem {

	//时间
	private Date time;
	
	//温度
	private Double tem;
	
	//湿度
	private Double hum;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getTem() {
		return tem;
	}

	public void setTem(double tem) {
		this.tem = tem;
	}

	public double getHum() {
		return hum;
	}

	public void setHum(double hum) {
		this.hum = hum;
	}

	@Override
	public String toString() {
		return "TemHumItem [time=" + time + ", tem=" + tem + ", hum=" + hum + "]";
	}
	
}
