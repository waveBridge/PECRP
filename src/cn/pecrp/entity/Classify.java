package cn.pecrp.entity;

import java.util.HashSet;
import java.util.Set;

public class Classify {
	private Integer cid;
	private String classifyName;
	private Set<Video> videoSet = new HashSet<Video>();
	
	public Set<Video> getVideoSet() {
		return videoSet;
	}
	public void setVideoSet(Set<Video> videoSet) {
		this.videoSet = videoSet;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getClassifyName() {
		return classifyName;
	}
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}	
}
