package cn.pecrp.dao;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.User;

public class UserDaoImpl implements UserDao {
	
	private HibernateTemplate hibernateTemplate; 
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//寻找用户By账号密码
	@Override
	public int searchUser(String username,String password) {
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) 
					hibernateTemplate.find("from User where username = ? and password = ?", username, password);
		if(list.size() == 0) {
			return 0;
		} else {
			return list.get(0).getUid();
		}
	}

	//寻找用户By账号
	@Override
	public int searchUser(String username) {
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>)hibernateTemplate.find("from User where username = ?",username);
		
		if(list.size() == 0) {
			return 0;
		} else return list.get(0).getUid();
		
	}

	//添加用户
	@Override
	public int addUser(User user) {
		System.out.println("addUser...dao...");
		try{
			int flag = (int)hibernateTemplate.save(user);
			System.out.println("save后返回的flag值" + flag);
			return flag;
		}catch (Exception e) {
			System.out.println(e.toString());
			return 0;
		}
		
	}
		
	
}
