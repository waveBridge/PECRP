package cn.pecrp.entity;

import java.util.HashSet;
import java.util.Set;

public class Video {
	private Integer vid;
	private String	videoName;
	private String	link;
	private String	picture;
	private String	time;   
	private Integer	playNum;
	private Set<Label> labelSet = new HashSet<Label>();
	private Set<User> collectionUserSet = new HashSet<User>();
	private Set<User> zanUserSet = new HashSet<User>();
	private Set<User> watchUserSet = new HashSet<User>();
	private Set<Classify> classifySet = new HashSet<Classify>();
	private Set<Review> reviewSet = new HashSet<Review>();
	
	public Set<User> getZanUserSet() {
		return zanUserSet;
	}
	public void setZanUserSet(Set<User> zanUserSet) {
		this.zanUserSet = zanUserSet;
	}
	public Set<Review> getReviewSet() {
		return reviewSet;
	}
	public void setReviewSet(Set<Review> reviewSet) {
		this.reviewSet = reviewSet;
	}
	public Set<Classify> getClassifySet() {
		return classifySet;
	}
	public void setClassifySet(Set<Classify> classifySet) {
		this.classifySet = classifySet;
	}
	public Set<User> getCollectionUserSet() {
		return collectionUserSet;
	}
	public void setCollectionUserSet(Set<User> collectionUserSet) {
		this.collectionUserSet = collectionUserSet;
	}
	public Set<User> getWatchUserSet() {
		return watchUserSet;
	}
	public void setWatchUserSet(Set<User> watchUserSet) {
		this.watchUserSet = watchUserSet;
	}
	public Set<Label> getLabelSet() {
		return labelSet;
	}
	public void setLabelSet(Set<Label> labelSet) {
		this.labelSet = labelSet;
	}
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getPlayNum() {
		return playNum;
	}
	public void setPlayNum(Integer playNum) {
		this.playNum = playNum;
	}
}
