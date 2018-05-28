package cn.pecrp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import cn.pecrp.dao.VideoDao;
import cn.pecrp.entity.Classify;
import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Label;
import cn.pecrp.entity.Video;
import cn.pecrp.until.Python;

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
			if(hotVids == null || hotVids.size() == 0){
				return null;
			}
			
			List<Hot> hotVid = new ArrayList<Hot>();
			
			//先复制一份到allVideo的list
			for(Hot h : hotVids){
				hotVid.add(h);
			}
			
			//再按照热门度排序
			Collections.sort(hotVid, new SortByHotDegree());
			
			List<Video> popVideo;
			//最多取前10
			if(hotVid.size() <= 10){
				popVideo = videoDao.getVideoByVids(hotVid);
			} else {
				//将前10放到popVideo
				List<Hot> fiveVid = new ArrayList<Hot>();
				for(int i = 0; i < 10 ;i ++){
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
	public List<Video> getRecommendVideo(String classifyName) {
		System.out.println("getRecommendVideo...service...");
		
		try{
			int cid = videoDao.getCidByClassifyName(classifyName);
			if(cid == 0){
				return null;
			}
			List<Video> recommendVideo = videoDao.getRecommendVideo(cid);
			return recommendVideo;
		} catch (Exception e) {
			return null;
		}
	}

	//根据classifyName获取HotVideo
	public List<Video> getHotVideo(String classifyName) {
		System.out.println("getRecommendVideo...service...");
		
		try{
			int cid = videoDao.getCidByClassifyName(classifyName);
			if(cid == 0){
				return null;
			}
			
			List<Video> hotVideo = videoDao.getHotVideo(cid);
			return hotVideo;
		} catch (Exception e) {
			return null;
		}
	}

	//根据classifyName获取recommendLabel
	public List<Label> getrecommendLabel(String classifyName) {
		System.out.println("getRecommendLabel...service...");
		
		try{
			int cid = videoDao.getCidByClassifyName(classifyName);
			if(cid == 0){
				return null;
			}
			
			List<Label> recommendLabel = videoDao.getRecommendLabel(cid);
			return recommendLabel;
		} catch (Exception e) {
			return null;
		}
	}

	//获取单个视频的推荐视频
	public List<Video> getSingleRecommend(String vids) {
		System.out.println("getSingleRecommend...service...");
		
		try{
			int vid = Integer.parseInt(vids);
			int uid = (int)ServletActionContext.getRequest().getSession().getAttribute("uid");
			List<Video> recommendVideoSet = videoDao.getSingleRecommendVideo(vid, uid);
			return recommendVideoSet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//获取单个视频的推荐标签
	public List<Label> getSingleLabel(String vids) {
		System.out.println("getSingleLabel...service...");
		
		try{
			int vid = Integer.parseInt(vids);
			int uid = (int)ServletActionContext.getRequest().getSession().getAttribute("uid");
			List<Label> recommendLabelSet = videoDao.getSingleLabel(vid, uid);
			return recommendLabelSet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	//获取单个视频的同类视频
	public List<Video> getClassifyVideo(String vids) {
		System.out.println("getClassifyVideo...service...");
		
		try{
			int vid = Integer.parseInt(vids);
			
			//找到该视频对应的类别表
			Set<Classify> classifySet = videoDao.getClassifySet(vid);
			if(classifySet == null || classifySet.size() == 0){
				return null;
			} else {
				//找到类别表中所有类别对应的视频
				List<Video> classifyVideoSet = videoDao.getClassifyVideo(classifySet, vid);
				return classifyVideoSet;
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据classifyName得到cid
	public int getCidByClassifyName(String classifyName) {
		System.out.println("getCidByClassifyName...service...");
		int cid = videoDao.getCidByClassifyName(classifyName);
		return cid;
	}
	
}

//排序
class SortByHotDegree implements Comparator<Hot> {
	public int compare(Hot v1, Hot v2){
		if(v1.getHotDegree() < v2.getHotDegree()){			//按照playnum降序
			return 1;
		} else {
			return -1;
		}
	}
}
