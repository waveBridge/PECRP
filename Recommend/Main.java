

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		try {
			System.out.println("start");
			String vid = "" + 1;
			String uid = "" + 1;
			args = new String[] {"python", "E:/Mirror/创训etc/PECRP/Recommend/getSingle.py", vid, uid};
			Process pr = Runtime.getRuntime().exec(args);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				System.out.println(line);  
			}
			in.close();
			pr.waitFor();
			System.out.println("end");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		

	}

}