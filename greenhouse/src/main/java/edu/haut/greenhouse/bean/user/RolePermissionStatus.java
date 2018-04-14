package edu.haut.greenhouse.bean.user;
/**
 * 角色权限的关系状态
 * @author chen haijian
 * @date 2018-04-14
 *
 */
public enum RolePermissionStatus {

	USEABLE(1, "可用"), UNUSEABLE(2, "不可用");
	
	private Integer status;
	private String desc;
	
	private RolePermissionStatus(Integer status, String desc) {
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
