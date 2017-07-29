package cn.pecrp.action;

import com.opensymphony.xwork2.ActionSupport;

import cn.pecrp.service.UserService;

public class UserAction extends ActionSupport {
	
	private UserService UserService;
	public void setUserService(UserService userService) {
		UserService = userService;
	}

	public String login() {
		
		System.out.println("login..........");
		if(UserService.login() == true) {
			return SUCCESS;
		} else {
			return ERROR;
		}
		
	}
}
