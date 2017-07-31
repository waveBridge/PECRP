package cn.pecrp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.pecrp.entity.User;
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
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
				
		JSONObject json = new JSONObject();
		try{
			//获取值
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//尝试登录
			boolean flag = userService.login(username,password);
			
			//登录失败返回0   成功返回1
			if(flag == false) {
				json.put("msg", "0");
			} else {
				json.put("msg", "1");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg","0");
			
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		return null;
	}
	
	
	//用户注册
	public String register() throws IOException {
		System.out.println("register...action...");
		//获得request和response对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String nickname = request.getParameter("nickname");
			String email = request.getParameter("email");
			
			//先查找该用户名是否被注册
			boolean flag = userService.searchUser(username);
			
			if(flag == true) { 
				json.put("msg","3");                  //用户名重复
			} else {
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setNickname(nickname);
				user.setEmail(email);				
				boolean flag2 =userService.register(user);
				
				if(flag2 == true) {
					json.put("msg", "1");             //注册成功
				} else {
					json.put("msg","0");              //注册失败
				}
			}
			
		} catch(Exception e) {
			json.put("msg", "0");                     //注册 异常
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
}
