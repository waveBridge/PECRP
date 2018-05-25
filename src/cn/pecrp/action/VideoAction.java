package cn.pecrp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.pecrp.entity.Label;
import cn.pecrp.entity.Video;
import cn.pecrp.service.VideoService;
import cn.pecrp.until.Redundant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class VideoAction extends ActionSupport {

	private VideoService videoService;
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	
	//返回热门视频
	public String getHotVideo() throws IOException{
		System.out.println("popVideo...action...");	
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
				
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		JSONObject json = new JSONObject();
		JSONArray json2;
		try{
			List<Video> popVideo = videoService.popVideo();
			if(popVideo == null){
				json.put("msg", "0");				//错误
			} else {
				
				for(int i = 0 ; i < popVideo.size(); i ++){
					Redundant.rmRedundant(popVideo.get(i));
				}

				//cnt最大为5，小于5说明视频不够
				json.put("cnt", popVideo.size());
				json2 = JSONArray.fromObject(popVideo, jsonConfig);
				json.put("msg", json2);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", "0");					//错误	
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		return null;
	}
	
	//首页，分类视频推荐，分类视频标签推荐，分类视频热门推荐
	public String getClassifyVideo() throws IOException{
		System.out.println("getClassifyVideo...action...");	
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
				
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		JSONObject json = new JSONObject();
		JSONArray json2;
		
		try{
			String classifyName = request.getParameter("classifyName");
			Set<Video> recommendVideo = videoService.getRecommendVideo(classifyName);
			Set<Video> hotVideo = videoService.getHotVideo(classifyName);
			Set<Label> recommendLabel = videoService.getrecommendLabel(classifyName); 
			
			if(recommendVideo == null || hotVideo == null || recommendLabel == null){
				json.put("msg", "0");		
			} else {
				json.put("msg", "1");							//正确
				
				//删除冗余数据
				Iterator<Video> it = recommendVideo.iterator();
				while(it.hasNext()){
					Video video = it.next();
					video = Redundant.rmRedundant(video);	//去除冗余
				}
				
				it = hotVideo.iterator();
				while(it.hasNext()){
					Video video = it.next();
					Redundant.rmRedundant(video);	//去除冗余
				}
				
				Iterator<Label> it2 = recommendLabel.iterator();
				while(it2.hasNext()){
					Label label = it2.next();
					Redundant.rmRedundant(label);	//去除冗余
				}
				
				//存入json
				json2 = JSONArray.fromObject(recommendVideo, jsonConfig);
				json.put("recommendVideo", json2);
				json2 = JSONArray.fromObject(hotVideo, jsonConfig);
				json.put("hotVideo", json2);
				json2 = JSONArray.fromObject(recommendLabel, jsonConfig);
				json.put("recommendLabel", json2);
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			json.put("msg", 0);
		} finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}
		
		return null;
	}
	
	//视频页面， 同类推荐，标签推荐，视频推荐
	public String getSingleVideo() throws IOException{
		System.out.println("getSingleVideo...action...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
				
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		JSONObject json = new JSONObject();
		JSONArray json2;
		
		try{
			String vid = request.getParameter("vid");
			Set<Video> recommendVideo = videoService.getSingleRecommend(vid);
			Set<Video> classifyVideo = videoService.getClassifyVideo(vid);
			Set<Label> recommendLabel = videoService.getSingleLabel(vid);
			
			if(recommendVideo == null || classifyVideo == null || recommendLabel == null){
				json.put("msg","0");
			} else {
				json.put("msg", "1");
				
				//删除冗余数据
				Iterator<Video> it = recommendVideo.iterator();
				while(it.hasNext()){
					Video video = it.next();
					video = Redundant.rmRedundant(video);	//去除冗余
				}
				
				it = classifyVideo.iterator();
				while(it.hasNext()){
					Video video = it.next();
					Redundant.rmRedundant(video);			//去除冗余
				}
				
				Iterator<Label> it2 = recommendLabel.iterator();
				while(it2.hasNext()){
					Label label = it2.next();
					Redundant.rmRedundant(label);			//去除冗余
				}
				
				//存入json
				json2 = JSONArray.fromObject(recommendVideo, jsonConfig);
				json.put("recommendVideo", json2);
				json2 = JSONArray.fromObject(classifyVideo, jsonConfig);
				json.put("classifyVideo", json2);
				json2 = JSONArray.fromObject(recommendLabel, jsonConfig);
				json.put("recommendLabel", json2);
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
