package cn.pecrp.entity;

import java.io.Serializable;

public class RecSingleVideo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer vid;
	private Integer uid;
	private Integer recVid;
	private Integer hotDegree;
	
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getRecVid() {
		return recVid;
	}
	public void setRecVid(Integer recVid) {
		this.recVid = recVid;
	}
	public Integer getHotDegree() {
		return hotDegree;
	}
	public void setHotDegree(Integer hotDegree) {
		this.hotDegree = hotDegree;
	}
}
