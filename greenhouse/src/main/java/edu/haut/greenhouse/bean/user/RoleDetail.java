package edu.haut.greenhouse.bean.user;

import java.util.List;

import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;

/**
 * 
 * @Description 角色信息
 * @author chen haijian
 * @date 2018年4月26日
 * @version 1.0
 */
public class RoleDetail {

	private Role role;
	
	private List<Permission> perList;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Permission> getPerList() {
		return perList;
	}

	public void setPerList(List<Permission> perList) {
		this.perList = perList;
	}

	@Override
	public String toString() {
		return "RoleDetail [role=" + role + ", perList=" + perList + "]";
	}
	
	
}
