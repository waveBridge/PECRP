package cn.pecrp.service;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;   //注意事务的配置引入的包一定不要错

import cn.pecrp.dao.WatchDao;

@Transactional
public class WatchService {
	
	private WatchDao watchDao;
	public void setWatchDao(WatchDao watchDao) {
		
		this.watchDao = watchDao;
	}
	
	//准备看视频，加访问量
	public int watch(String svid) {
		System.out.println("watch...service...");
		
		try{
			int vid = Integer.parseInt(svid);
			int flag;		

			//修改视频访问量
			flag = watchDao.addPlayNum(vid,1);
			if(flag == -1){
				return -1;
			}
			
			//修改用户访问历史
			HttpSession session = ServletActionContext.getRequest().getSession();
			int uid = (int)session.getAttribute("uid");
			boolean flag2 = watchDao.addHistory(uid,vid);
			
			if(flag2 == false){
				return -1;
			}
			
			return flag;
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return -1;
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
	
}
