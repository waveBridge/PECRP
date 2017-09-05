package cn.pecrp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.pecrp.entity.Label;
import cn.pecrp.service.InfoService;
import net.sf.json.JSONObject;

public class InfoAction extends ActionSupport {
	
	private InfoService infoService;
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}


	//修改密码
	public String changePass() throws IOException {
		System.out.println("changePass...action...");
		
		//获得request和response对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
				
		JSONObject json = new JSONObject();
		try{
			
			String oldPass = request.getParameter("oldPass");
			String newPass = request.getParameter("newPass");
			
			int flag = infoService.changePass(oldPass,newPass);
			if(flag == 1) {
				json.put("msg", "1");                   //修改成功  
			} else if(flag == -1) { 
				json.put("msg", "-1");                  //原密码错误
			} else {
				json.put("msg", "0");                   //修改失败
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg","0");                      //修改失败
		} finally{
			out.write(json.toString());
			out.flush();
			out.close();
		}
		return null;
	}
	
	//忘记密码_获取验证码
	public String forgetPassGetVCode() throws IOException {
		System.out.println("forgetPassGetVCode...action...");
		
		//获得request和response对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			
			int flag = infoService.forgetPassGetVCode(username,email);
			if(flag == -1) {
				json.put("msg","-1");                   //用户名与邮箱不匹配
			} else if (flag == 1) {
				json.put("msg", "1");                   //发送了验证码
			} else {
				json.put("msg", "0");                   //未知错误
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "0");              
		} finally{
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//忘记密码_修改密码
	public String forgetPassChange() throws IOException {
		System.out.println("forgetPassChange...action...");
		//获得request和response对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			String vcode = request.getParameter("vcode");
			String password = request.getParameter("password");
			//看验证码是否正确
			boolean flag = infoService.forgetPassCmpVCode(vcode);
			if(flag == true) {
				//正确，可以修改密码
				flag = infoService.forgetPassChange(password);
				if(flag == true) {
					json.put("msg", "1");                   //修改成功
				} else {
					json.put("msg","0");                    //修改失败
				}
			} else {
				json.put("msg", "-1");                      //验证码错误
			} 
			
		}catch (Exception e) {
			json.put("msg","0");
		} finally{
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//修改昵称
	public String changeNickname() throws IOException {
		System.out.println("changeNickname...action...");
		//获得request和response对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			String nickname = request.getParameter("nickname");
			boolean flag = infoService.changeNickname(nickname);
			if(flag == false) {
				json.put("msg","0");                  //修改失败
			} else {
				json.put("msg",nickname);                  //修改成功
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg","0");              
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//修改用户的标签
	public String changeLabel() throws IOException {
		System.out.println("changeLabel...action...");
		
		//获得request和response对象
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			String lids = request.getParameter("lids");         //接收到字符串形式的labels集合
			boolean flag = infoService.changeLabel(lids);
			if(flag == true) {   
				json.put("msg", "1");                           //改变成功
			} else {
				json.put("msg", "0");                           //改变失败
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
