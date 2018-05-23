package cn.pecrp.dao;

import java.util.List;

import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Video;

public interface VideoDao {

	List<Video> getVideoByVids(List<Hot> hotVid);

	List<Hot> hotVid();

}
