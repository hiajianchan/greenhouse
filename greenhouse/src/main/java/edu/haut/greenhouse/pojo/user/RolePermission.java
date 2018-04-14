package edu.haut.greenhouse.pojo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.haut.greenhouse.bean.user.RolePermissionStatus;

/**
 * 角色和权限的关系
 * @author chen haijain
 *
 */
@Table(name = "role_permission")
public class RolePermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 角色ID
	 */
	@Column(name = "role_id")
	private Integer roleId;
	
	/**
	 * 权限ID
	 */
	@Column(name = "per_id")
	private Integer perId;
	
	/**
	 * 状态值
	 * @see RolePermissionStatus
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPerId() {
		return perId;
	}

	public void setPerId(Integer perId) {
		this.perId = perId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "RolePermission [id=" + id + ", roleId=" + roleId + ", perId=" + perId + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	
}
