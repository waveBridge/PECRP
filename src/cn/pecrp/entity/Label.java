package cn.pecrp.entity;

import java.util.HashSet;
import java.util.Set;

public class Label {
	private Integer lid;
	private String labelName;
	private Set<User> userSet = new HashSet<User>();
	private Set<Video> videoSet = new HashSet<Video>();
	
	public Set<Video> getVideoSet() {
		return videoSet;
	}
	public void setVideoSet(Set<Video> videoSet) {
		this.videoSet = videoSet;
	}
	public Set<User> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
