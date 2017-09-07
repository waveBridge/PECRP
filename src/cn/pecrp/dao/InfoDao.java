package cn.pecrp.dao;

public interface InfoDao {
	
	String getPass(int uid);

	boolean changePass(int uid, String newPass);

	int searchUser(String username, String email);

	boolean changeNickname(int attribute, String nickname);

	boolean changeLabel(int uid, int[] lidInt);

	boolean upImg(int uid, String path);

}
