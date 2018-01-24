package cn.pecrp.dao;

import java.util.Set;

import cn.pecrp.entity.User;
import cn.pecrp.entity.Video;

public interface UserDao {
	
	public int searchUser(String username, String password);
	
	public int searchUser(String username);
	
	public int addUser(User user);

	public User getUserInfo(int uid);

	public Set<Video> getHistory(int uid);

	public Set<Video> deleteHistory(int uid, int vid);

	public Set<Video> getCollect(int uid);
}
