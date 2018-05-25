package cn.pecrp.entity;

import java.io.Serializable;

public class RecClassifyLabel implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer cid;
	private Integer lid;
	private Integer hotDegree;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public Integer getHotDegree() {
		return hotDegree;
	}
	public void setHotDegree(Integer hotDegree) {
		this.hotDegree = hotDegree;
	}
}
