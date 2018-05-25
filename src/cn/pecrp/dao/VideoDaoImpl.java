package cn.pecrp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.Classify;
import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Label;
import cn.pecrp.entity.RecClassifyHot;
import cn.pecrp.entity.RecClassifyLabel;
import cn.pecrp.entity.RecClassifyVideo;
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
	
	//根据classify得到cid
	@Override
	public int getCidByClassifyName(String classifyName) {
		System.out.println("getCidByClassifyName...dao...");
		
		try{
			String q = "from Classify";
			@SuppressWarnings("unchecked")
			List<Classify> classifies = (List<Classify>) hibernateTemplate.find(q);
			for(Classify c : classifies){
				if(c.getClassifyName().equals(classifyName)){
					return c.getCid();
				}
			}
			return 0;
		} catch (Exception e) {
			System.out.println(e.toString());
			return 0;
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
	public List<Video> getRecommendVideo(int cid) {
		System.out.println("getRecommendVideo...dao...");
		
		try{
			List<Video> videoList = new ArrayList<Video>();
			
			String q = "from RecClassifyVideo order by hotDegree";
			@SuppressWarnings("unchecked")
			List<RecClassifyVideo> recClassifyVideoList = (List<RecClassifyVideo>) hibernateTemplate.find(q);
			
			for(RecClassifyVideo rv : recClassifyVideoList){
				if(rv.getCid() == cid){
					Video video = hibernateTemplate.get(Video.class, rv.getVid());
					videoList.add(video);
				}
			}
			
			return videoList;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据类别名获取hot视频
	@Override
	public List<Video> getHotVideo(int cid) {
		System.out.println("getHotVideo...dao...");
		
		try{
			List<Video> videoList = new ArrayList<Video>();
			
			String q = "from RecClassifyHot order by hotDegree";
			@SuppressWarnings("unchecked")
			List<RecClassifyHot> recClassifyHotList = (List<RecClassifyHot>) hibernateTemplate.find(q);
			
			for(RecClassifyHot rh : recClassifyHotList){
				if(rh.getCid() == cid){
					Video video = hibernateTemplate.get(Video.class, rh.getVid());
					videoList.add(video);
				}
			}
			
			return videoList;
		
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	

	//根据类别名获取recommend标签
	@Override
	public List<Label> getRecommendLabel(int cid) {
		System.out.println("getRecommendLabel...dao...");
		
		try{
			List<Label> LabelList = new ArrayList<Label>();
			
			String q = "from RecClassifyLabel order by hotDegree";
			@SuppressWarnings("unchecked")
			List<RecClassifyLabel> recClassifyLabelList = (List<RecClassifyLabel>) hibernateTemplate.find(q);
			
			for(RecClassifyLabel rl : recClassifyLabelList){
				if(rl.getCid() == cid){
					Label label = hibernateTemplate.get(Label.class, rl.getLid());
					LabelList.add(label);
				}
			}
			
			return LabelList;
		
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
	public List<Video> getClassifyVideo(Set<Classify> classifySet, int vid) {
		System.out.println("getClassifyVideo...dao...");
		
		try{
//			System.out.println("classifySet size->>>>>" + classifySet.size());
			List<Video> videoList = new ArrayList<Video>();
			
			String q = "from RecClassifyVideo order by hotDegree";
			@SuppressWarnings("unchecked")
			List<RecClassifyVideo> recClassifyVideoList = (List<RecClassifyVideo>) hibernateTemplate.find(q);
			
			if(recClassifyVideoList == null || recClassifyVideoList.size() == 0){
				return null;
			} else {
				for(RecClassifyVideo rc : recClassifyVideoList){
					if(rc.getVid() != vid){	//不要推荐本视频
						for(Classify c : classifySet){
							if(c.getCid() == rc.getCid()){
								Video video = hibernateTemplate.get(Video.class, rc.getVid());
								videoList.add(video);
							}
						}
					}
				}
			}
		
			return videoList;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	//根据vid和uid获取推荐视频，single的推荐视频
	@Override
	public List<Video> getSingleRecommendVideo(int vid, int uid) {
		System.out.println("getSingleRecommendVideo...dao...");
		
		try{
			List<Video> recommendVideoSet = new ArrayList<Video>();
			String q = "from RecSingleVideo order by hotDegree";
			@SuppressWarnings("unchecked")
			List<RecSingleVideo> recSingleVideoSet = (List<RecSingleVideo>) hibernateTemplate.find(q);
			
			if(recSingleVideoSet == null || recSingleVideoSet.size() == 0){
				return null;
			} else {	
				for(RecSingleVideo rv : recSingleVideoSet){
					System.out.println(rv.getVid() + " " + rv.getUid() + " " + rv.getRecVid());
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
	public List<Label> getSingleLabel(int vid, int uid) {
		System.out.println("getSingleLabel...dao...");
		
		try{
			List<Label> recommendLabel = new ArrayList<Label>();
			
			String q = "from RecSingleLabel order by hotDegree";
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
