package pers.kw.lotteryassistant.ssq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ssq_miss")
public class SsqMiss {
	private String qh, missInfo;

	@Id
	@Column(name = "qh",length = 12)
	public String getQh() {
		return qh;
	}

	public void setQh(String qh) {
		this.qh = qh;
	}

	@Column(name = "miss_info")
	public String getMissInfo() {
		return missInfo;
	}

	public void setMissInfo(String missInfo) {
		this.missInfo = missInfo;
	}

}
