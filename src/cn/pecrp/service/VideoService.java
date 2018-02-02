package cn.pecrp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.pecrp.dao.VideoDao;
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
			List<Video> allVideos = videoDao.allVideos();
			List<Video> allVideo = new ArrayList<Video>();
			
			//先复制一份到allVideo的list
			for(Video v : allVideos){
				allVideo.add(v);
			}
			
			//再按照热门度排序
			Collections.sort(allVideo, new SortByPlayNum());
			
			//最多取前十
			if(allVideo.size() <= 10){
				return allVideo;
			} else {
				//将前十放到popVideo
				List<Video> popVideo = new ArrayList<Video>();
				for(int i = 0; i < 10 ;i ++){
					popVideo.add(allVideo.get(i));							
				}
				return popVideo;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
}

class SortByPlayNum implements Comparator<Video> {
	public int compare(Video v1, Video v2){
		if(v1.getPlayNum() < v2.getPlayNum()){			//按照playnum降序
			return 1;
		} else {
			return -1;
		}
	}
}
