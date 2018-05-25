package cn.pecrp.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.Classify;
import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Label;
import cn.pecrp.entity.RecSingleLabel;
import cn.pecrp.entity.RecSingleVideo;
import cn.pecrp.entity.Video;

public class VideoDaoImpl implements VideoDao {
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//得到Hot
	@Override
	public List<Hot> hotVid() {
		System.out.println("hotVid...dao...");
		
		try{
			@SuppressWarnings("unchecked")
			List<Hot> hotVid = (List<Hot>) hibernateTemplate.find("from Hot");
			return hotVid;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	//根据vid集合获取视频集合
	@Override
	public List<Video> getVideoByVids(List<Hot> hotVid) {
		System.out.println("getVideoByVids...dao...");
		
		try{
			List<Video> video = new ArrayList<Video>();
			for(Hot h : hotVid){
				Video v = hibernateTemplate.get(Video.class, h.getVid());
				video.add(v);
			}
			
			return video;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}		
	}

	//根据类别名获取recommend视频
	@Override
	public Set<Video> getRecommendVideo(String classifyName) {
		System.out.println("getRecommendVideo...dao...");
		
		try{
			String q = "from Classify";
			@SuppressWarnings("unchecked")
			List<Classify> classify = (List<Classify>) hibernateTemplate.find(q);
			for(Classify c : classify){
				if(c.getClassifyName().equals(classifyName)){
					return c.getRecommendVideoSet();
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据类别名获取hot视频
	@Override
	public Set<Video> getHotVideo(String classifyName) {
		System.out.println("getHotVideo...dao...");
		
		try{
			String q = "from Classify";
			@SuppressWarnings("unchecked")
			List<Classify> classify = (List<Classify>) hibernateTemplate.find(q);
			for(Classify c : classify){
				if(c.getClassifyName().equals(classifyName)){
					return c.getHotVideoSet();
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	

	//根据类别名获取recommend标签
	@Override
	public Set<Label> getRecommendLabel(String classifyName) {
		System.out.println("getRecommendLabel...dao...");
		
		try{
			String q = "from Classify";
			@SuppressWarnings("unchecked")
			List<Classify> classify = (List<Classify>) hibernateTemplate.find(q);
			for(Classify c : classify){
				if(c.getClassifyName().equals(classifyName)){
					return c.getRecommendLabelSet();
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据vid获取类别set
	@Override
	public Set<Classify> getClassifySet(int vid) {
		System.out.println("getClassifySet...dao...");
		
		try{
			Video video = hibernateTemplate.get(Video.class, vid);
			return video.getClassifySet();
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	
	//根据类别set获取所有同类视频
	@Override
	public Set<Video> getClassifyVideo(Set<Classify> classifySet, int vid) {
		System.out.println("getClassifyVideo...dao...");
		
		try{
			System.out.println("classifySet size->>>>>" + classifySet.size());
			Set<Video> videoSet = new HashSet<Video>();
			for(Classify c : classifySet){
				videoSet.addAll(c.getVideoSet());
			}
			
			System.out.println("videoSet size->>>>>" + videoSet.size());
			Video video = hibernateTemplate.get(Video.class, vid);
			videoSet.remove(video);
			
			System.out.println("videoSet size->>>>>" + videoSet.size());
			return videoSet;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据vid和uid获取推荐视频，single的推荐视频
	@Override
	public Set<Video> getSingleRecommendVideo(int vid, int uid) {
		System.out.println("getSingleRecommendVideo...dao...");
		
		try{
			Set<Video> recommendVideoSet = new HashSet<Video>();
			String q = "from RecSingleVideo";
			@SuppressWarnings("unchecked")
			List<RecSingleVideo> recSingleVideoSet = (List<RecSingleVideo>) hibernateTemplate.find(q);
			if(recSingleVideoSet == null || recSingleVideoSet.size() == 0){
				return null;
			} else {
				for(RecSingleVideo rv : recSingleVideoSet){
					if(rv.getVid() == vid && rv.getUid() == uid){
						Video video = hibernateTemplate.get(Video.class, rv.getRecVid());
						recommendVideoSet.add(video);
					}
				}
				return recommendVideoSet;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据vid和uid获取推荐标签，single的推荐标签
	@Override
	public Set<Label> getSingleLabel(int vid, int uid) {
		System.out.println("getSingleLabel...dao...");
		
		try{
			Set<Label> recommendLabel = new HashSet<Label>();
			
			String q = "from RecSingleLabel";
			@SuppressWarnings("unchecked")
			List<RecSingleLabel> recSingleLabelSet = (List<RecSingleLabel>) hibernateTemplate.find(q);
			if(recSingleLabelSet == null || recSingleLabelSet.size() == 0){
				return null;
			} else {
				for(RecSingleLabel l : recSingleLabelSet){
					if(l.getVid() == vid && l.getUid() == uid){
						Label label = hibernateTemplate.get(Label.class, l.getLid());
						recommendLabel.add(label);
					}
				}
				return recommendLabel;
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
}
