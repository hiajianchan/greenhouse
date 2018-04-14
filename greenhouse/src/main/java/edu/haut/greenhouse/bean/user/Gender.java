package edu.haut.greenhouse.bean.user;
/**
 * 用户性别的枚举
 * @author chen haijian
 * @date 2018-04-14
 *
 */
public enum Gender {

	UNKUOWN(0, "未知"), MALE(1, "男"), FEMALE(2, "女");
	
	private Integer type;
	private String typeName;
	private Gender(Integer type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
