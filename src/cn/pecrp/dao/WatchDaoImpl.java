package cn.pecrp.dao;

import java.util.HashSet;
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
	public int addPlayNum(int vid, int i) {
		try{
			Video video = hibernateTemplate.get(Video.class, vid);
			if(video == null){
				return -1;
			}
			
			//添加
			int tmp = video.getPlayNum() + 1;
			video.setPlayNum(tmp);
			hibernateTemplate.save(video);
			return tmp;
		}catch (Exception e) {
			System.out.println(e.toString());
			return -1;
		}
	}

	//添加用户观看历史
	@Override
	public boolean addHistory(int uid, int vid) {
		System.out.println("addHistory...dao...");
		
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

	//未赞则赞，已赞则删
	@Override
	public String zan(int uid, int vid) {
		System.out.println("zan...dao...");
		
		try{
			System.out.println("uid = "+ uid + " vid = "+vid);
			
			User user = hibernateTemplate.get(User.class, uid);
			Video video = hibernateTemplate.get(Video.class, vid);
			if(user == null || video == null){
				return "-1";
			}
			
			int isAdd;
			int num;
			Set<Video> userVideos =  (Set<Video>) user.getZanSet();
			Set<User> VideoUsers = (Set<User>) video.getZanUserSet();
			
			if(userVideos.contains(video)){
				isAdd = 0;
				num = video.getZanUserSet().size() - 1;
				userVideos.remove(video); 		                    //包含，就删除
				
			} else {
				isAdd = 1;
				num = video.getZanUserSet().size() + 1;
				userVideos.add(video);								//不包含，添加
			}
			
			user.setZanSet(userVideos);
			hibernateTemplate.update(user);
			
			return isAdd+"a"+num;                     				//返回目前赞的状态以及视频的赞数
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return "-1";
		}
		
	}

	//获取赞数
	@Override
	public int getZanNum(int vid) {
		System.out.println("getZanNum...dao...");
		
		try{
			Video video = hibernateTemplate.get(Video.class, vid);
			int num = video.getZanUserSet().size();                   //set中用户的数量就是视频获得的赞数
			System.out.println("num"+num);
			return num;
			
		}catch (Exception e) {
			return -1;
		}
	}

}