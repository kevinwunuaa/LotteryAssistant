package pers.kw.lotteryassistant.ssq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ssq_num")
public class SsqNum {
	private Integer n, c;
	private Float p;
	
	@Id
	@Column(name = "n")
	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	@Column(name = "c")
	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	@Column(name = "p")
	public Float getP() {
		return p;
	}

	public void setP(Float p) {
		this.p = p;
	}

}
