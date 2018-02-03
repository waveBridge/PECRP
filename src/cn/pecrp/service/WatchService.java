package cn.pecrp.service;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;   //注意事务的配置引入的包一定不要错

import cn.pecrp.dao.WatchDao;
import cn.pecrp.entity.Video;

@Transactional
public class WatchService {
	
	private WatchDao watchDao;
	public void setWatchDao(WatchDao watchDao) {
		
		this.watchDao = watchDao;
	}
	
	//准备看视频，加访问量
	public Video watch(String svid) {
		System.out.println("watch...service...");
		
		try{
			int vid = Integer.parseInt(svid);
			Video video;		

			//修改视频访问量
			video = watchDao.addPlayNum(vid,1);
			if(video == null){
				return null;
			}
			
			//修改用户访问历史
			HttpSession session = ServletActionContext.getRequest().getSession();
			int uid = (int)session.getAttribute("uid");
			boolean flag = watchDao.addHistory(uid,vid);
			
			if(flag == false){
				return null;
			}
			
			return video;
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//点赞
	public String zan(String vids) {
		System.out.println("zan...service...");
		
		try{
			int vid = Integer.parseInt(vids);
			HttpSession session = ServletActionContext.getRequest().getSession();
			int uid = (int)session.getAttribute("uid");
			
			//赞一下或者取消赞
			String flag = watchDao.zan(uid,vid);
			return flag;
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return "-1";
		}
		
	}

	//收藏
	public String collect(String vvid) {
		System.out.println("collect...service...");
		
		try{
			int vid = Integer.parseInt(vvid);
			HttpSession session = ServletActionContext.getRequest().getSession();
			int uid = (int)session.getAttribute("uid");
			
			//收藏 返回是否收藏以及总收藏量
			String flag = watchDao.addACollect(vid, uid);
			return flag;
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		
	}

	//删除收藏
	public Set<Video> deleteCollect(String vvid) {
		System.out.println("deleteCollect...service");
		
		try{
			int vid = Integer.parseInt(vvid);
			int uid = (int)ServletActionContext.getRequest().getSession().getAttribute("uid");
			Set<Video> collectionSet = watchDao.deleteCollect(uid, vid);
			return collectionSet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
}
