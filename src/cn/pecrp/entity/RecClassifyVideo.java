package cn.pecrp.entity;

import java.io.Serializable;

public class RecClassifyVideo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer cid;
	private Integer vid;
	private Integer hotDegree;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public Integer getHotDegree() {
		return hotDegree;
	}
	public void setHotDegree(Integer hotDegree) {
		this.hotDegree = hotDegree;
	}
}
