package cn.pecrp.until;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Python {
	
	public void updateSingleRecommend(int vid, int uid){
		try {	
			System.out.println("py...updateSingleRecommend...start...");
			
			String vids = "" + vid;
			String uids = "" + uid;
			String[] args = new String[] {"python", "/getSingle.py", vids, uids};
			Process pr = Runtime.getRuntime().exec(args);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				System.out.println(line);  
			}
			in.close();
			pr.waitFor();
			
			System.out.println("py...updateSingleRecommend...end...");
		} catch (Exception e) {
			System.out.println(e.toString());	
		}
	}

	public void updateHot() {
		try {	
			System.out.println("py...updateHot..start...");
			
			String[] args = new String[] {"python", "/getHot.py"};
			Process pr = Runtime.getRuntime().exec(args);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				System.out.println(line);  
			}
			in.close();
			pr.waitFor();
			
			System.out.println("py...updateHot...end...");
		} catch (Exception e) {
			System.out.println(e.toString());	
		}
	}
	
	public void updateClassifyRecommend(int cid) {
		try {	
			System.out.println("py...updateClassifyRecommend..start...");
			
			String cids = "" + cid;
			System.out.println(cids+"........");
			String[] args = new String[] {"python", "/getClassified.py", cids};
			Process pr = Runtime.getRuntime().exec(args);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				System.out.println(line);  
			}
			in.close();
			pr.waitFor();
			
			System.out.println("py...updateCClassifyRecommend...end...");
		} catch (Exception e) {
			System.out.println(e.toString());	
		}
	}
}
