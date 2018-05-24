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
	private Python python;
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}
	public void setPython(Python python) {
		this.python = python;
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

	//获取单个视频的推荐视频
	public Set<Video> getSingleRecommend(String vids) {
		System.out.println("getSingleRecommend...service...");
		
		try{
			int vid = Integer.parseInt(vids);
			int uid = (int)ServletActionContext.getRequest().getSession().getAttribute("uid");
			
			Set<Video> recommendVideoSet = videoDao.getSingleRecommendVideo(vid, uid);
			if(recommendVideoSet == null || recommendVideoSet.size() == 0){
				//调用py脚本，更新数据库中的single推荐视频数据
				System.out.println("调用py了->>>>>>>>>>>>>>>>>>>>>>>>>>>");
				python.updateSingleRecommend(vid);
				
				int i = 1;					
				while(i<=5 && (recommendVideoSet == null || recommendVideoSet.size() == 0)){
					System.out.println("第" + i + "次查询");
					Thread.sleep(1000);				//1秒钟查一次数据库，最多查5次
					recommendVideoSet = videoDao.getSingleRecommendVideo(vid, uid);
					i ++;
				}
			}
			return recommendVideoSet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//获取单个视频的推荐标签
	public Set<Label> getSingleLabel(String vids) {
		System.out.println("getSingleLabel...service...");
		
		try{
			int vid = Integer.parseInt(vids);
			int uid = (int)ServletActionContext.getRequest().getSession().getAttribute("uid");
			
			Set<Label> recommendLabelSet = videoDao.getSingleLabel(vid, uid);
			if(recommendLabelSet == null || recommendLabelSet.size() == 0){
				//调用py脚本，更新数据库中的single推荐视频数据
				System.out.println("调用py了->>>>>>>>>>>>>>>>>>>>>>>>>>>");
				python.updateSingleRecommend(vid);
				
				int i = 1;					
				while(i<=5 && (recommendLabelSet == null || recommendLabelSet.size() == 0)){
					System.out.println("第" + i + "次查询");
					Thread.sleep(1000);				//1秒钟查一次数据库，最多查5次
					recommendLabelSet = videoDao.getSingleLabel(vid, uid);
					i ++;
				}
			}
			return recommendLabelSet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	//获取单个视频的同类视频
	public Set<Video> getClassifyVideo(String vids) {
		System.out.println("getClassifyVideo...service...");
		
		try{
			int vid = Integer.parseInt(vids);
			
			//找到该视频对应的类别表
			Set<Classify> classifySet = videoDao.getClassifySet(vid);
			if(classifySet == null || classifySet.size() == 0){
				return null;
			} else {
				//找到类别表中所有类别对应的视频
				Set<Video> classifyVideoSet = videoDao.getClassifyVideo(classifySet, vid);
				return classifyVideoSet;
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
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
