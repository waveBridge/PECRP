package cn.pecrp.dao;

import cn.pecrp.entity.User;

public interface UserDao {
	
	public int searchUser(String username, String password);
	
	public int searchUser(String username);
	
	public int addUser(User user);

	public User getUserInfo(int uid);
}
