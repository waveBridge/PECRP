package cn.pecrp.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.Hot;
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


	
}
