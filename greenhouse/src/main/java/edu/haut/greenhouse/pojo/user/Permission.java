package edu.haut.greenhouse.pojo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.haut.greenhouse.bean.user.PermissionStatus;

/**
 * 权限
 * @author chen haijian
 * @date 2018-04-14
 *
 */
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 权限名称，英文
	 */
	@Column(name = "per_name")
	private String perName;
	
	/**
	 * 权限名称，中文
	 */
	@Column(name = "per_name_ch")
	private String perNameCh;
	
	/**
	 * url
	 */
	@Column(name = "per_url")
	private String perUrl;
	
	/**
	 * 状态值
	 * @see PermissionStatus
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
	
}
