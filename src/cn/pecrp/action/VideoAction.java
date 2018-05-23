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
					popVideo.get(i).setZanUserSet(null);
					popVideo.get(i).setReviewSet(null);
					popVideo.get(i).setLabelSet(null);
					popVideo.get(i).setWatchUserSet(null);
					popVideo.get(i).setCollectionUserSet(null);
					popVideo.get(i).setClassifySet(null);
					popVideo.get(i).setHotClassifySet(null);
					popVideo.get(i).setRecommendClassifySet(null);
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
					video.setClassifySet(null);
					video.setZanUserSet(null);
					video.setReviewSet(null);
					video.setLabelSet(null);
					video.setWatchUserSet(null);
					video.setCollectionUserSet(null);
					video.setHotClassifySet(null);
					video.setRecommendClassifySet(null);
				}
				
				it = hotVideo.iterator();
				while(it.hasNext()){
					Video video = it.next();
					video.setClassifySet(null);
					video.setZanUserSet(null);
					video.setReviewSet(null);
					video.setLabelSet(null);
					video.setWatchUserSet(null);
					video.setCollectionUserSet(null);
					video.setHotClassifySet(null);
					video.setRecommendClassifySet(null);
				}
				
				Iterator<Label> it2 = recommendLabel.iterator();
				while(it2.hasNext()){
					Label label = it2.next();
					label.setRecommendClassifySet(null);
					label.setUserSet(null);
					label.setVideoSet(null);
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
	
	
}
