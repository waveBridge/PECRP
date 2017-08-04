package cn.pecrp.dao;

public interface InfoDao {
	
	String getPass(int uid);

	boolean changePass(int uid, String newPass);
}
