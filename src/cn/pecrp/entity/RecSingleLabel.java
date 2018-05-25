package cn.pecrp.entity;

import java.io.Serializable;

public class RecSingleLabel implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer vid;
	private Integer uid;
	private Integer lid;
	
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
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
}
