package external;

import java.io.*;
import java.lang.Runtime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotTester {
	
	private static final String TEST_SCRIPT_PATH = "C:/Users/h/Workspace/_github/g-swarm/bot/script/robot_tester.py";
	
	public static void startTest(int particlesNum, int iteration, String testPath){
		try {
			String command = "python " + TEST_SCRIPT_PATH + " " + particlesNum + " " + iteration + " "  + testPath;
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			System.err.println("Error: " + e.getMessage());
		}	
	}
	
	public static int loadResult(String resultsPath){
		//System.out.println(resultsPath);
		//String body = FileUtils.convertFileToString(resultsPath);
		//System.out.println(body);
		
		try {
		    BufferedReader br = new BufferedReader(new FileReader(resultsPath));
		    String line = br.readLine();
		    
		    Pattern gswarmRobotPattern = Pattern.compile("GSwarmRobot");
			Pattern resultPattern = Pattern.compile("\\([0-9]*%\\)");
			Matcher matcher;
			String result;
		    
		    while (line != null){
		        line = br.readLine();
		        
		        matcher = gswarmRobotPattern.matcher(line);
				
		        while(matcher.find()){
		        	matcher = resultPattern.matcher(line);
		        	
		        	while(matcher.find()){
		        		result = matcher.group(0);
						return Integer.parseInt(result.substring(1, result.length() - 2)); 
		        	}
				}
				
		    }
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (java.lang.NullPointerException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return 0;
	}
}
