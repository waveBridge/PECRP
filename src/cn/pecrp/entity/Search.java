package cn.pecrp.entity;

import java.util.HashSet;
import java.util.Set;

public class Search {
	private Integer sid;
	private String content;
	private Set<User> searchUserSet = new HashSet<User>();
	
	public Set<User> getSearchUserSet() {
		return searchUserSet;
	}
	public void setSearchUserSet(Set<User> searchUserSet) {
		this.searchUserSet = searchUserSet;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
