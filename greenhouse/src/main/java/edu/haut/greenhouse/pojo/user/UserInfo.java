package edu.haut.greenhouse.pojo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户相关的信息
 * @author chen haijian
 * @date 2018-04-14
 *
 */
@Table(name = "user_info")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 用户主键ID
	 */
	@Column(name = "user_id")
	private Integer userId;
	
	/**
	 * 上次登录时间
	 */
	@Column(name = "last_login")
	private Date lastLogin;
	
	/**
	 * 上次登录IP
	 */
	@Column(name = "last_ip")
	private String lastIp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userId=" + userId + ", lastLogin=" + lastLogin + ", lastIp=" + lastIp + "]";
	}
	
	
}
