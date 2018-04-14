package edu.haut.greenhouse.pojo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.haut.greenhouse.bean.user.Gender;
import edu.haut.greenhouse.bean.user.UserStatus;
/**
 * 用户实体类
 * @author chj
 * @date 2018-04-14
 */
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 用户名
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 性别
	 * @see Gender
	 */
	@Column(name ="gender")
	private Integer gender;
	
	/**
	 * 密码
	 */
	@Column(name = "passwd")
	private String passwd;
	
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;
	
	/**
	 * 手机号
	 */
	@Column(name = "mobile")
	private String mobile;
	
	/**
	 * 状态
	 * @see UserStatus
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 注册时间
	 */
	@Column(name = "register_time")
	private Date registerTime;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", gender=" + gender + ", passwd=" + passwd + ", email=" + email
				+ ", mobile=" + mobile + ", status=" + status + ", registerTime=" + registerTime + ", updateTime="
				+ updateTime + "]";
	}
	
	
}
