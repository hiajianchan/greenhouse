package edu.haut.greenhouse.pojo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.haut.greenhouse.bean.user.RoleStatus;

@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 角色名称，英文
	 */
	@Column(name = "rolename")
	private String rolename;
	
	/**
	 * 角色名称，中文
	 */
	@Column(name = "rolename_ch")
	private String rolenameCh;
	
	/**
	 * 状态值
	 * @see RoleStatus
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRolenameCh() {
		return rolenameCh;
	}

	public void setRolenameCh(String rolenameCh) {
		this.rolenameCh = rolenameCh;
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
		return "Role [id=" + id + ", rolename=" + rolename + ", rolenameCh=" + rolenameCh + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	
}
