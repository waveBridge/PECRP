package cn.pecrp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.Classify;
import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Label;
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
	
}
