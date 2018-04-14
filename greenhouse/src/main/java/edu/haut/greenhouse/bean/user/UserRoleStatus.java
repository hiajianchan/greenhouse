package edu.haut.greenhouse.bean.user;
/**
 * 用户角色关系的状态
 * @author chen haijian
 * @date 2018-04-14
 *
 */
public enum UserRoleStatus {
	USEABLE(1, "可用"), DELETED(2, "已删除");
	
	private Integer status;
	private String desc;
	
	private UserRoleStatus(Integer status, String desc) {
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
