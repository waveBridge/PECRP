package cn.pecrp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import cn.pecrp.service.WatchService;

public class WatchAction extends ActionSupport {
	
	private WatchService watchService;
	public void setWatchService(WatchService watchService) {
		this.watchService = watchService;
	}
	
	//用户点开视频，准备观看
	public String watch() throws IOException{
		System.out.println("watch...action...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
				
		JSONObject json = new JSONObject();
		try{
			String vid = request.getParameter("vid");
			boolean flag = watchService.watch(vid);
			if(flag == true){
				json.put("msg", "1");                    //可以观看
			} else {
				json.put("msg", "0");                    //未知错误
			}
		}catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "0");                         //异常
		}finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
}
