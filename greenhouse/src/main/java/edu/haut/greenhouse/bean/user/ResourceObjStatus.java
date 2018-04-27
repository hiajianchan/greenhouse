package edu.haut.greenhouse.bean.user;
/**
 * 
 * @Description 资源对象的状态
 * @author chen haijian
 * @date 2018年4月27日
 * @version 1.0
 */
public enum ResourceObjStatus {

	USEABLE(1, "可用"), UNUSEABLE(2, "不可用");
	
	private Integer status;
	private String desc;
	
	private ResourceObjStatus(Integer status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
