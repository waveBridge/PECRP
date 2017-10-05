package cn.pecrp.dao;

import java.util.Set;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.User;
import cn.pecrp.entity.Video;

public class WatchDaoImpl implements WatchDao {

	private HibernateTemplate hibernateTemplate; 
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//添加视频访问量
	@Override
	public boolean addPlayNum(int vid, int i) {
		try{
			Video video = hibernateTemplate.get(Video.class, vid);
			if(video == null){
				return false;
			}
			
			//添加
			video.setPlayNum(video.getPlayNum() + 1);
			hibernateTemplate.save(video);
			return true;
		}catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	@Override
	//添加用户观看历史
	public boolean addHistory(int uid, int vid) {
		try{
			User user = hibernateTemplate.get(User.class, uid);
			Video video = hibernateTemplate.get(Video.class, vid);
			if(user == null || video == null){
				return false;
			}
			
			//取出原set
			Set<Video> watchSet = user.getWatchSet();
			
			//添加
			watchSet.add(video);
			user.setWatchSet(watchSet);
			hibernateTemplate.save(user);
			
			return true;
		}catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

}
