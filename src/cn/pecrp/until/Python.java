package cn.pecrp.until;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Python {
	
	public void updateSingleRecommend(int vid){
		try {	
			System.out.println("py...updateSingleRecommend...start...");
			
			String vids = "" + vid;
			String[] args = new String[] {"python", "/getSingle.py", vids};
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
}
