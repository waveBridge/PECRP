package cn.pecrp.entity;

import java.util.HashSet;
import java.util.Set;

public class User {
	private Integer	uid;
	private String	username;
	private String	password;
	private String	nickname;
	private String	email;
	private String	photo;
	private Set<Label> labelSet = new HashSet<Label>();
	private Set<Video> collectionSet = new HashSet<Video>();
	private Set<Video> zanSet = new HashSet<Video>();
	private Set<Video> watchSet = new HashSet<Video>();
	private Set<Search> searchSet = new HashSet<Search>();
	
	public Set<Video> getZanSet() {
		return zanSet;
	}
	public void setZanSet(Set<Video> zanSet) {
		this.zanSet = zanSet;
	}
	public Set<Search> getSearchSet() {
		return searchSet;
	}
	public void setSearchSet(Set<Search> searchSet) {
		this.searchSet = searchSet;
	}
	public Set<Video> getCollectionSet() {
		return collectionSet;
	}
	public void setCollectionSet(Set<Video> collectionSet) {
		this.collectionSet = collectionSet;
	}
	public Set<Video> getWatchSet() {
		return watchSet;
	}
	public void setWatchSet(Set<Video> watchSet) {
		this.watchSet = watchSet;
	}
	public Set<Label> getLabelSet() {
		return labelSet;
	}
	public void setLabelSet(Set<Label> labelSet) {
		this.labelSet = labelSet;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", photo=" + photo + ", labelSet=" + labelSet + ", collectionSet="
				+ collectionSet + ", watchSet=" + watchSet + ", searchSet=" + searchSet + "]";
	}
	
	
}
