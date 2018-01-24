package cn.pecrp.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;   //注意事务的配置引入的包一定不要错

import cn.pecrp.dao.UserDao;
import cn.pecrp.entity.User;
import cn.pecrp.entity.Video;
import cn.pecrp.until.MailUtil;
import cn.pecrp.until.TimeUtil;

@Transactional
public class UserService {
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	private MailUtil mailUtil;
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}
	
	private TimeUtil timeUtil;
	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
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
			session.setAttribute("uid", flag);                //用户id放入 session
			return true;
		} else {
			return false;
		}
			
	}
	
	//发送邮件获取验证码
	public boolean getVCode(String email) {
		System.out.println("getVCode...service...");
		//随机生成5验证码
	    Integer x =(int)((Math.random()*9+1)*10000);  
	    String text = x.toString(); 
		boolean flag = mailUtil.sendMail(email, text);
		if(flag == true){
			//发送成功，把验证码和时间记录
			String nowTime = timeUtil.getTime();
			
			//存入session  验证码#时间
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("vcodeTime",text+"#"+nowTime);
			System.out.println(session.getAttribute("vcodeTime"));
			return true;
			
		} else {
			return false;
		}
	}
	
	//比较验证码是否正确以及是否失效
	public boolean cmpVCode(String vcode) {
		System.out.println("cmpVCode...service...");
		
		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			String vcodeTime =  (String) session.getAttribute("vcodeTime");
			String vcodeTimeArray[] = vcodeTime.split("#");
			
			//先比较验证码是否正确
			if(vcodeTimeArray[0].equals(vcode)) {
				boolean flag = timeUtil.cmpTime(vcodeTimeArray[1]);
				
				if(flag == true){
					return true;
				}	
			}	
			return false;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}	 
	}

	//返回所有的用户信息
	public User getUserInfo() {
		System.out.println("getUserInfo...service...");
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		try{
			//根据id得到该用户的所有信息
			User user = userDao.getUserInfo((int)session.getAttribute("uid"));
			return user;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//获取历史记录
	public Set<Video> getHistory() {
		System.out.println("getHistory...service...");
		
		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			int uid = (int)session.getAttribute("uid");			//获取用户id
			Set<Video> historySet = userDao.getHistory(uid);	//获取历史记录		
			return historySet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//删除历史记录
	public Set<Video> deleteHistory(String vvid) {
		System.out.println("deleteHistory...service...");
		
		try{
			int vid = Integer.parseInt(vvid);
			int uid = (int)ServletActionContext.getRequest().getSession().getAttribute("uid");
			Set<Video> historySet = userDao.deleteHistory(uid, vid);
			return historySet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//得到当前用户的收藏
	public Set<Video> getCollect() {
		System.out.println("getCollect...service...");
		
		try{
			int uid = (int) ServletActionContext.getRequest().getSession().getAttribute("uid");
			Set<Video> collectionSet = userDao.getCollect(uid);
//			Set<Video> collectionSet2 = new HashSet<Video>();
//			collectionSet2.addAll(collectionSet);
//			for(Video v : collectionSet2){
//				v.setCollectionUserSet(null);
//				v.setWatchUserSet(null);
//				v.setZanUserSet(null);
//			}
			return collectionSet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

}
