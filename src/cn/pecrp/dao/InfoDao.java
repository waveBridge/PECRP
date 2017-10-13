package cn.pecrp.dao;

import java.util.Set;

import cn.pecrp.entity.Label;

public interface InfoDao {
	
	String getPass(int uid);

	boolean changePass(int uid, String newPass);

	int searchUser(String username, String email);

	boolean changeNickname(int attribute, String nickname);

	Set<Label> changeLabel(int uid, int[] lidInt);

	boolean upImg(int uid, String path);

}
