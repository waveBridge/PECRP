package cn.pecrp.service;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import cn.pecrp.dao.InfoDao;

@Transactional
public class InfoService {
	
	private InfoDao infoDao;
	public void setInfoDao(InfoDao infoDao) {
		this.infoDao = infoDao;
	}
	
	
	//通过旧密码改密码
	public int changePass(String oldPass,String newPass) {
		System.out.println("changePass...service...");
		
		//先判断密码是否正确  获取该用户的密码 用户肯定存在session中
		HttpSession session = ServletActionContext.getRequest().getSession();
		int uid = (int)session.getAttribute("uid");
		
		//通过用户id找密码并比较oldPass
		String password = infoDao.getPass(uid);
		
		if(oldPass.equals(password)) {
			
			//通过uid修改密码
			boolean flag = infoDao.changePass(uid,newPass);
			if(flag == true) {
				return 1;           //修改成功
			} else {
				return 0;           //未知错误
			}
			
		} else {
			return -1;              //新旧密码不一致
		}
	}
	
}
