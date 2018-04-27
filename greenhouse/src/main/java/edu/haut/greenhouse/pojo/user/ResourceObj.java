package edu.haut.greenhouse.pojo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.haut.greenhouse.bean.user.ResourceObjStatus;

/**
 * 
 * @Description 资源对象
 * @author chen haijian
 * @date 2018年4月27日
 * @version 1.0
 */
@Table(name = "resource_obj")
public class ResourceObj {

	/**
	 * id，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 资源对象名称-英文
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 中文名称
	 */
	@Column(name = "nameCh")
	private String nameCh;
	
	/**
	 * 资源对象状态
	 * @see ResourceObjStatus
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	

	public String getNameCh() {
		return nameCh;
	}

	public void setNameCh(String nameCh) {
		this.nameCh = nameCh;
	}

	@Override
	public String toString() {
		return "ResourceObj [id=" + id + ", name=" + name + ", nameCh=" + nameCh + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

	
	
}
