package cn.pecrp.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.User;

public class UserDaoImpl implements UserDao {
	
	private HibernateTemplate hibernateTemplate; 
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public void serachUser() {
		User user = (User)hibernateTemplate.get(User.class,1);
		System.out.println(user.getNickname());
	}
	
}
