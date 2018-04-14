package edu.haut.greenhouse.pojo.temhum;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 温湿度
 * @author chen hiajian
 * @date 2018-04-14
 */
@Table(name = "tem_hum")
public class TemAndHum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tem")
	private BigDecimal tem;
	
	@Column(name = "hum")
	private BigDecimal hum;
	
	@Column(name = "create_time")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTem() {
		return tem;
	}

	public void setTem(BigDecimal tem) {
		this.tem = tem;
	}

	public BigDecimal getHum() {
		return hum;
	}

	public void setHum(BigDecimal hum) {
		this.hum = hum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TemAndHum [id=" + id + ", tem=" + tem + ", hum=" + hum + ", createTime=" + createTime + "]";
	}
	
	
}
