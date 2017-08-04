package cn.pecrp.dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.User;

public class InfoDaoImpl implements InfoDao {

	private HibernateTemplate hibernateTemplate; 
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//通过uid得到密码
	@Override
	public String getPass(int uid) {
		System.out.println("getPass...dao...");
		
		try{
			User user = hibernateTemplate.get(User.class, uid);
			if(user != null)
				return user.getPassword();
			else return null;
		}catch (Exception e) {
			return null;
		}
	}

	
	//通过uid修改密码
	@Override
	public boolean changePass(int uid, String newPass) {
		System.out.println("changPass...dao...");
		try{
			
			User user = hibernateTemplate.get(User.class, uid);
			user.setPassword(newPass);
			hibernateTemplate.update(user);
			return true;
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}
	
}
