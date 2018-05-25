package cn.pecrp.until;

import cn.pecrp.entity.Label;
import cn.pecrp.entity.Video;

public class Redundant {
	
	//去除video的冗余数据
	public static Video rmRedundant(Video video){
		video.setClassifySet(null);
		video.setZanUserSet(null);
		video.setReviewSet(null);
		video.setLabelSet(null);
		video.setWatchUserSet(null);
		video.setCollectionUserSet(null);
		return video;
	}
	
	//去除label的冗余数据
	public static Label rmRedundant(Label label){
		label.setUserSet(null);
		label.setVideoSet(null);
		return label;
	}
}
