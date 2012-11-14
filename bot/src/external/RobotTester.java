package external;

import java.io.*;
import java.lang.Runtime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pso.Particle;

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
	
	public static int loadTotalScore(Particle particle){
		File file = new File(particle.getDir() + "/score.log");
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line = br.readLine();
			return Integer.parseInt(line);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (java.lang.NullPointerException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	    return 0;
	}
}
