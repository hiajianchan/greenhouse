package edu.haut.greenhouse.pojo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.haut.greenhouse.bean.user.PermissionStatus;

/**
 * 权限
 * @author chen haijian
 * @date 2018-04-14
 *
 */
@Table(name = "permission")
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
	 * 资源对象ID
	 */
	@Column(name = "obj_id")
	private Integer objId;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getPerNameCh() {
		return perNameCh;
	}

	public void setPerNameCh(String perNameCh) {
		this.perNameCh = perNameCh;
	}

	public String getPerUrl() {
		return perUrl;
	}

	public void setPerUrl(String perUrl) {
		this.perUrl = perUrl;
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
	
	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", perName=" + perName + ", perNameCh=" + perNameCh + ", objId=" + objId
				+ ", perUrl=" + perUrl + ", status=" + status + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
	
}
