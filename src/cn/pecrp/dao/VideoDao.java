package cn.pecrp.dao;

import java.util.List;
import java.util.Set;

import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Label;
import cn.pecrp.entity.Video;

public interface VideoDao {

	List<Video> getVideoByVids(List<Hot> hotVid);

	List<Hot> hotVid();

	Set<Video> getRecommendVideo(String classifyName);

	Set<Video> getHotVideo(String classifyName);

	Set<Label> getRecommendLabel(String classifyName);

}
