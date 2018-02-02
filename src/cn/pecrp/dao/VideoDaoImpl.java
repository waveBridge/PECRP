package cn.pecrp.dao;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.pecrp.entity.Video;

public class VideoDaoImpl implements VideoDao {
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//得到所有视频
	@Override
	public List<Video> allVideos() {
		@SuppressWarnings("unchecked")
		List<Video> allVideos = (List<Video>) hibernateTemplate.find("from Video");
		return allVideos;
	}
	
}
