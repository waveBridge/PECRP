package cn.pecrp.dao;

import java.util.Set;

import cn.pecrp.entity.Video;

public interface WatchDao {

	int addPlayNum(int vid, int i);

	boolean addHistory(int uid, int vid);

	String zan(int uid, int vid);

	int getZanNum(int vid);

	String addACollect(int vid, int uid);

}
