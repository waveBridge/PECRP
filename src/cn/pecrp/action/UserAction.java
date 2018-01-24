package cn.pecrp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.pecrp.entity.User;
import cn.pecrp.entity.Video;
import cn.pecrp.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class UserAction extends ActionSupport {
	
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	//用户登录
	public String login() throws IOException {
		System.out.println("login...action...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
				
		JSONObject json = new JSONObject();
		try{
			//获取值ֵ
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
			String email 	= request.getParameter("email");
			String vcode 	= request.getParameter("vcode");
			
			//先查找该用户名是否被注册
			boolean flag = userService.searchUser(username);
			
			if(flag == true) { 
				json.put("msg","3");                  //用户名重复
			} else {
				//看验证码是否正确以及是否失效
				flag = userService.cmpVCode(vcode);
				
				if(flag == true){
					
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
				} else {
					System.out.println("验证码匹配失败");
					json.put("msg", "2");                 //验证码匹配失败
				}
			}
			
		} catch(Exception e) {
			System.out.println("注册异常");
			json.put("msg", "0");                     //注册 异常
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//获取邮箱验证码
	public String getVCode() throws IOException {
		System.out.println("getVCode...action...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			String email = request.getParameter("email");
			System.out.println(email);
			boolean flag = userService.getVCode(email);
			if(flag == true) {
				json.put("msg","1");                 //生成了验证码并发送给了用户
			} else {
				json.put("msg","0");                 //未获取到
			}
			
		}catch (Exception e) {
			json.put("msg","0");
		}finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//得到该用户的所有信息
	public String getUserInfo()  throws IOException {
		System.out.println("getUserInfo...action...");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		//设置jsonConfig是为了摆脱死循环，因为是多对多级联关系
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		JSONObject json; 
		JSONObject json2 = new JSONObject();
		
		try {
			User user  = userService.getUserInfo();
			if(user != null) {
				json = JSONObject.fromObject(user, jsonConfig);
				json2.put("msg",json);                //有信息则输出信息
			} else {
				json2.put("msg","0");                 //没有信息 0
			}
			
		}catch (Exception e) {
			System.out.println(e.toString());
			json2.put("msg","0");                     //若没有信息则为0
		}finally {
			out.write(json2.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//查看历史记录
	public String getHistory() throws IOException{
		System.out.println("getHistory...action...");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		//设置jsonConfig是为了摆脱死循环，因为是多对多级联关系
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		JSONArray json2;
		JSONObject json = new JSONObject();
		try{
			Set<Video> historySet = userService.getHistory();
			if(historySet == null) {
				json.put("msg", "0");
				json.put("cnt", "0");
			} else {
				json.put("cnt", historySet.size());				//返回长度
				json2 = JSONArray.fromObject(historySet, jsonConfig);
				json.put("msg", json2);							//放入历史表
			}	
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "0");								//异常
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		return null;
	}
	
	//删除历史记录
	public String deleteHistory() throws IOException{
		System.out.println("deleteHistory...action...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		//设置jsonConfig是为了摆脱死循环，因为是多对多级联关系
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		JSONArray json2;
		JSONObject json = new JSONObject();
		
		try{
			String vid = request.getParameter("vid");
			Set<Video> historySet = userService.deleteHistory(vid);
			if(historySet == null){
				json.put("msg", "-1");								//错误
			} else {
				json.put("cnt", historySet.size());					//个数
				json2 = JSONArray.fromObject(historySet, jsonConfig); //内容
				json.put("msg", json2);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "-1");									//错误																	
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//退出登录
	public String logout() throws IOException{
		System.out.println("logout...action...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			HttpSession session = request.getSession();
			session.removeAttribute("uid");
			json.put("msg", "1");
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "0");
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		return null;
	}
	
	//得到当前用户的收藏
	public String getCollect() throws IOException{
		System.out.println("getCollect...action...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		//设置jsonConfig是为了摆脱死循环，因为是多对多级联关系
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		
		JSONArray json2;
		JSONObject json = new JSONObject();
		try{
			Set<Video> collectionSet = userService.getCollect();
			if(collectionSet == null){
				json.put("msg", "0");
			} else {
				json.put("cnt", collectionSet.size());
				json2 = JSONArray.fromObject(collectionSet, jsonConfig);
				json.put("msg", json2);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "0");
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		
		return null;
	}
}
