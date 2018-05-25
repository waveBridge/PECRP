package cn.pecrp.dao;

import java.util.List;
import java.util.Set;

import cn.pecrp.entity.Classify;
import cn.pecrp.entity.Hot;
import cn.pecrp.entity.Label;
import cn.pecrp.entity.Video;

public interface VideoDao {

	List<Video> getVideoByVids(List<Hot> hotVid);

	List<Hot> hotVid();

	List<Video> getRecommendVideo(int cid);

	List<Video> getHotVideo(int cid);

	List<Label> getRecommendLabel(int cid);

	Set<Classify> getClassifySet(int vid);

	List<Video> getClassifyVideo(Set<Classify> classifySet, int vid);

	List<Video> getSingleRecommendVideo(int vid, int uid);

	List<Label> getSingleLabel(int vid, int uid);

	int getCidByClassifyName(String classifyName);

}
