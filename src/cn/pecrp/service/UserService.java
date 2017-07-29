package cn.pecrp.service;

import javax.transaction.Transactional;

import cn.pecrp.dao.UserDao;

@Transactional
public class UserService {
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public boolean login() {
		System.out.println("login...service...........");
		userDao.serachUser();
	}
	
	
}
