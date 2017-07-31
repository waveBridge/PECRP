package cn.pecrp.service;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;   //注意事务的配置引入的包一定不要错

import cn.pecrp.dao.UserDao;
import cn.pecrp.entity.User;

@Transactional
public class UserService {
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//登录
	public boolean login(String username, String password) {
		System.out.println("login...service...");
		
		int flag = userDao.searchUser(username, password);
		if(flag == 0) {
			//不存在
			return false;
		} else {
			//存在用户  uid放入session
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("uid", flag);
			return true;
		}
	}
	
	//根据用户名查找用户
	public boolean searchUser(String username) {
		System.out.println("searchUser...service...");
		
		int flag = userDao.searchUser(username);
		
		if(flag != 0) {
			return true;
		}
		else return false;
	}
	
	//注册
	public boolean register(User user) {
		System.out.println("register...service...");
		
		int flag = userDao.addUser(user);
		
		if(flag != 0) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("uid", flag);               //用户id放入 session
			return true;
		} else {
			return false;
		}
			
	}

}
