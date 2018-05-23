package cn.pecrp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import cn.pecrp.dao.VideoDao;
import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Label;
import cn.pecrp.entity.Video;

@Transactional
public class VideoService {
	private VideoDao videoDao;
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}
	
	//返回热门视频
	public List<Video> popVideo() {
		System.out.println("popVideo...service..");
		
		try{
			List<Hot> hotVids = videoDao.hotVid();
			List<Hot> hotVid = new ArrayList<Hot>();
			
			//先复制一份到allVideo的list
			for(Hot h : hotVids){
				hotVid.add(h);
			}
			
			//再按照热门度排序
			Collections.sort(hotVid, new SortByHotDegree());
			
			List<Video> popVideo;
			//最多取前五
			if(hotVid.size() <= 5){
				popVideo = videoDao.getVideoByVids(hotVid);
			} else {
				//将前五放到popVideo
				List<Hot> fiveVid = new ArrayList<Hot>();
				for(int i = 0; i < 5 ;i ++){
					fiveVid.add(hotVid.get(i));							
				}
				popVideo = videoDao.getVideoByVids(fiveVid);
			}
			
			return popVideo;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据classifyName获取recommendVideo
	public Set<Video> getRecommendVideo(String classifyName) {
		System.out.println("getRecommendVideo...service...");
		
		try{
			Set<Video> recommendVideo = videoDao.getRecommendVideo(classifyName);
			return recommendVideo;
		} catch (Exception e) {
			return null;
		}
	}

	//根据classifyName获取HotVideo
	public Set<Video> getHotVideo(String classifyName) {
		System.out.println("getRecommendVideo...service...");
		
		try{
			Set<Video> hotVideo = videoDao.getHotVideo(classifyName);
			return hotVideo;
		} catch (Exception e) {
			return null;
		}
	}

	//根据classifyName获取recommendLabel
	public Set<Label> getrecommendLabel(String classifyName) {
		System.out.println("getRecommendLabel...service...");
		
		try{
			Set<Label> recommendLabel = videoDao.getRecommendLabel(classifyName);
			return recommendLabel;
		} catch (Exception e) {
			return null;
		}
	}
	
}

class SortByHotDegree implements Comparator<Hot> {
	public int compare(Hot v1, Hot v2){
		if(v1.getHotDegree() < v2.getHotDegree()){			//按照playnum降序
			return 1;
		} else {
			return -1;
		}
	}
}
