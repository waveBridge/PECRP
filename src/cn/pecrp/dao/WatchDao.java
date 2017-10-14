package cn.pecrp.dao;

public interface WatchDao {

	int addPlayNum(int vid, int i);

	boolean addHistory(int uid, int vid);

	String zan(int uid, int vid);

	int getZanNum(int vid);

}
