package cn.pecrp.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.Label;
import cn.pecrp.entity.Search;
import cn.pecrp.entity.User;
import cn.pecrp.entity.Video;

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
			return flag;
		}catch (Exception e) {
			System.out.println(e.toString());
			return 0;
		}
		
	}

	//根据id返回用户所有信息
	@Override
	public User getUserInfo(int uid) {
		System.out.println("getUserInfo...dao...");
		
		try{
			User user = hibernateTemplate.get(User.class, uid);
			return user;
		}catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//得到历史记录
	@Override
	public Set<Video> getHistory(int uid) {
		System.out.println("getHistory...dao...");
		
		try{
			User user = hibernateTemplate.get(User.class, uid);
			return user.getWatchSet();
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//删除历史记录
	@Override
	public Set<Video> deleteHistory(int uid, int vid) {
		System.out.println("deleteHistory...dao...");
		
		try{
			User user = hibernateTemplate.get(User.class, uid);
			Video video = hibernateTemplate.get(Video.class, vid);
			
			Set<Video> historySet = user.getWatchSet();
			historySet.remove(video);
			user.setWatchSet(historySet);
			
			return historySet;
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
}
