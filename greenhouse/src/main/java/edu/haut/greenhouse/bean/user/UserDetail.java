package edu.haut.greenhouse.bean.user;
/**
 * @Description 用户详细信息
 * @author chen haijian
 * @date 2018年4月25日
 * @version 1.0
 */

import java.util.List;

import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.pojo.user.UserInfo;

public class UserDetail {

	private User user;
	
	private UserInfo info;
	
	private List<Role> roles;
	
	private List<Permission> permissions;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "UserDetail [user=" + user + ", info=" + info + ", roles=" + roles + ", permissions=" + permissions
				+ "]";
	}

}
