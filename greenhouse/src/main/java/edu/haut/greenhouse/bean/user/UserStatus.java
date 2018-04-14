package edu.haut.greenhouse.bean.user;
/**
 * 用户的状态
 * @author chen haijian
 * @date 2018-04-14
 *
 */
public enum UserStatus {

	UNVERIFY(0, "未审核"), USEABLE(1, "可用"), NOTTHROUGH(2, "审核未通过"), DELETED(3, "已删除");
	
	private Integer status;
	private String name;
	
	private UserStatus(Integer status, String name) {
		this.status = status;
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
