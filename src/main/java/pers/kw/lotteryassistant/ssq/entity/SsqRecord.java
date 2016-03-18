package pers.kw.lotteryassistant.ssq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author kw 双色球各期开奖情况
 */

@Entity
@Table(name = "ssq_record")
public class SsqRecord {
	private String qh, rq; // 期号，开奖日期
	private Integer r1, r2, r3, r4, r5, r6, b, num; // 6个红球，1个蓝球，和值
	
	@Id
	@Column(name = "QH",length = 12)
	public String getQh() {
		return qh;
	}

	public void setQh(String qh) {
		this.qh = qh;
	}

	@Column(name = "RQ",length = 12)
	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	@Column(name = "R1")
	public Integer getR1() {
		return r1;
	}

	public void setR1(Integer r1) {
		this.r1 = r1;
	}

	@Column(name = "R2")
	public Integer getR2() {
		return r2;
	}

	public void setR2(Integer r2) {
		this.r2 = r2;
	}

	@Column(name = "R3")
	public Integer getR3() {
		return r3;
	}

	public void setR3(Integer r3) {
		this.r3 = r3;
	}

	@Column(name = "R4")
	public Integer getR4() {
		return r4;
	}

	public void setR4(Integer r4) {
		this.r4 = r4;
	}

	@Column(name = "R5")
	public Integer getR5() {
		return r5;
	}

	public void setR5(Integer r5) {
		this.r5 = r5;
	}

	@Column(name = "R6")
	public Integer getR6() {
		return r6;
	}

	public void setR6(Integer r6) {
		this.r6 = r6;
	}

	@Column(name = "B")
	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	@Column(name = "NUM")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Transient
	public Integer[] getReds(){
		Integer []array = {r1,r2,r3,r4,r5,r6};
		return array;
	}

}
