package cn.pecrp.entity;

import java.util.HashSet;
import java.util.Set;

public class Classify {
	private Integer cid;
	private String classifyName;
	private Set<Video> videoSet = new HashSet<Video>();
	private Set<Video> recommendVideoSet = new HashSet<Video>();
	private Set<Video> hotVideoSet = new HashSet<Video>();
	private Set<Label> recommendLabelSet = new HashSet<Label>();
	
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
	public Set<Video> getRecommendVideoSet() {
		return recommendVideoSet;
	}
	public void setRecommendVideoSet(Set<Video> recommendVideoSet) {
		this.recommendVideoSet = recommendVideoSet;
	}
	public Set<Video> getHotVideoSet() {
		return hotVideoSet;
	}
	public void setHotVideoSet(Set<Video> hotVideoSet) {
		this.hotVideoSet = hotVideoSet;
	}
	public Set<Label> getRecommendLabelSet() {
		return recommendLabelSet;
	}
	public void setRecommendLabelSet(Set<Label> recommendLabelSet) {
		this.recommendLabelSet = recommendLabelSet;
	}	
}
