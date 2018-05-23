package cn.pecrp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

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
	
	
}
