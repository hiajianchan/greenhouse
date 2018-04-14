package edu.haut.greenhouse.bean.user;
/**
 * 权限的状态
 * @author chen haijain
 * @date 2018-04-14
 */
public enum PermissionStatus {

	USEABLE(1, "可用"), UNUSEABLE(2, "不可用");
	
	private Integer status;
	private String desc;
	
	private PermissionStatus(Integer status, String desc) {
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
