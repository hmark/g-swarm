package external;

import java.io.*;
import java.lang.Runtime;

import pso.Particle;

public class RobotTester {
	
	private static String TEST_SCRIPT_PATH;
	
	public static void loadTestScriptPath(){
		File file = new File("conf/path.conf");
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			TEST_SCRIPT_PATH = br.readLine().replaceAll("TEST_SCRIPT=", "");
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (java.lang.NullPointerException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void startTest(int particlesNum, int iteration, String testPath, String swarmName){
		try {
			String command = "python " + TEST_SCRIPT_PATH + " " + particlesNum + " " + iteration + " "  + testPath + " " + swarmName;
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(command);
			
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
			StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

			// start gobblers
			outputGobbler.start();
			errorGobbler.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			System.err.println("Error: " + e.getMessage());
		}	
	}
	
	public static double loadTotalScore(Particle particle){
		File file = new File(particle.getDir() + "/score.log");
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line = br.readLine();
			return Double.parseDouble(line);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (java.lang.NullPointerException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	    return 0;
	}
}
