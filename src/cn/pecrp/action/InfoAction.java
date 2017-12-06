package cn.pecrp.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.pecrp.service.InfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class InfoAction extends ActionSupport {
	
	private InfoService infoService;
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}
	
	//上传文件，采用表单形式，注入
	private File upload;                  //上传文件
	private String uploadFileName;        //上传文件名
	private String uploadContentType; //上传文件类型

    private long maximumSize;  
    private String allowedTypes;  

    public File getUpload() {  
        return upload;  
    }  
    public void setUpload(File upload) {  
        this.upload = upload;  
    }  
    public String getUploadFileName() {  
        return uploadFileName;  
    }  
    public void setUploadFileName(String uploadFileName) {  
        this.uploadFileName = uploadFileName;  
    }  

    public String getUploadContentType() {  
        return uploadContentType;  
    }  
    public void setUploadContentType(String uploadContentType) {  
        this.uploadContentType = uploadContentType;  
    }  
    public long getMaximumSize() {  
        return maximumSize;  
    }  
    public void setMaximumSize(long maximumSize) {  
        this.maximumSize = maximumSize;  
    }  
    public String getAllowedTypes() {  
        return allowedTypes;  
    }  
    public void setAllowedTypes(String allowedTypes) {  
        this.allowedTypes = allowedTypes;  
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
			json.put("msg","0");                        //修改失败
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
				json.put("msg","0");                 	   //修改失败
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
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		
		//设置jsonConfig是为了摆脱死循环，因为是多对多级联关系
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		JSONObject json2;											//把对象转变为json2
		JSONObject json = new JSONObject();                     	//传输的json
		try{
			String lids = request.getParameter("lids");         	//接收到字符串形式的labels集合由a隔开
			HashMap<Integer,String> flag = infoService.changeLabel(lids);
			if(flag == null) {   
				json.put("msg", "0");                          	 	//改变失败
			} else {
				int cnt = flag.size();
				json2 = JSONObject.fromObject(flag, jsonConfig);
				json.put("msg", json2);                           	//改变成功
				json.put("cnt", cnt);
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
	
	//上传头像
	public String upImg() throws IOException {
		System.out.println("upImg...action...");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter outt = response.getWriter();
		
		JSONObject json = new JSONObject();
		try{
			//上传文件
			String flag = infoService.upFile(maximumSize,allowedTypes,upload,uploadFileName,uploadContentType);
			
			//地址为上传成功   0失败  -1太大  -2类型不符合
			json.put("msg", flag);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "0");                    //失败
		} finally {
			outt.write(json.toString());
			outt.flush();
			outt.close();
		}
		
		return null;
	}
}
