package cn.pecrp.until;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.pecrp.entity.Label;
import cn.pecrp.entity.User;
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

	//取VideoList的前10
	public static List<Video> getTenFirstVideo(List<Video> videoList) {
		System.out.println("getTenFirst");
		
		try{
			if(videoList == null){
				return null;
			} else if(videoList.size() <= 10){
				return videoList;
			} else {
				List<Video> videos = new ArrayList<Video>();
				for(int i = 0 ; i < 10 ;i ++){
					videos.add(videoList.get(i));
				}
				return videos;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return videoList;
		}
	}

	//返回LabelList的前10
	public static List<Label> getTenFirstLabel(List<Label> labelList) {
		System.out.println("getTenFirst");
		
		try{
			if(labelList == null){
				return null;
			} else if(labelList.size() <= 10){
				return labelList;
			} else {
				List<Label> labels = new ArrayList<Label>();
				for(int i = 0 ; i < 10 ;i ++){
					labels.add(labelList.get(i));
				}
				return labels;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return labelList;
		}
	}

	//删除user的冗余数据
	public static User rmRedundant(User user) {
		System.out.println("rmRedundant...user...");
		
		try{
			rmRedundantVideo(user.getCollectionSet());
			rmRedundantVideo(user.getWatchSet());
			rmRedundantVideo(user.getZanSet());
			rmRedundantLabel(user.getLabelSet());
			user.setSearchSet(null);
			return user;
		} catch (Exception e) {
			System.out.println(e.toString());
			return user;
		}		
	}

	public static void rmRedundantLabel(Set<Label> labelSet) {
		// TODO 自动生成的方法存根
		try{
			Iterator<Label> it = labelSet.iterator();
			while(it.hasNext()){
				rmRedundant(it.next());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}

	public static void rmRedundantVideo(Set<Video> videoSet) {
		// TODO 自动生成的方法存根
		try{
			Iterator<Video> it = videoSet.iterator();
			while(it.hasNext()){
				rmRedundant(it.next());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}

	public static Video rmRedundantExceptLabelAndReview(Video video) {
		video.setClassifySet(null);
		video.setZanUserSet(null);
		video.setWatchUserSet(null);
		video.setCollectionUserSet(null);
		return video;
	}

}
