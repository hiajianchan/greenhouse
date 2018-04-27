package edu.haut.greenhouse.bean.user;
/**
 * 
 * @Description 权限详情
 * @author chen haijian
 * @date 2018年4月27日
 * @version 1.0
 */

import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.ResourceObj;

public class PermissionDetail {

	private Permission permission;
	
	private ResourceObj resourceObj;

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public ResourceObj getResourceObj() {
		return resourceObj;
	}

	public void setResourceObj(ResourceObj resourceObj) {
		this.resourceObj = resourceObj;
	}

	@Override
	public String toString() {
		return "PermissionDetail [permission=" + permission + ", resourceObj=" + resourceObj + "]";
	}
	
}
