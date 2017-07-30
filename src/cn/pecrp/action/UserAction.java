package cn.pecrp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.pecrp.service.UserService;
import net.sf.json.JSONObject;

public class UserAction extends ActionSupport {
	
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	//用户登录
	public String login() throws IOException {
		System.out.println("login...action...");
		
		//获得request和response对象
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("username" + request.getParameter("username"));
		System.out.println("password" + request.getParameter("password"));
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out;
		
		try{	
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//尝试登录
			boolean flag = userService.login(username,password);
			
			out = response.getWriter();
			JSONObject json = new JSONObject();
			
			//登录失败返回0   成功返回1
			if(flag == false) {
				json.put("msg", "0");
			} else {
				json.put("msg", "1");
			}

			out.write(json.toString());
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
}
