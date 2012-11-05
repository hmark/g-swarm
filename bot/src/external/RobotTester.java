package external;

import java.io.*;
import java.lang.Runtime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pso.Particle;
import pso.Setup;

public class RobotTester {
	
	public static final String ROBOCODE_PATH = "c:/robocode";
	
	public static final String ROBOTS_PATH = "c:/robocode/robots";
	
	public static final String PROJECT_PATH = "c:/users/h/workspace/_github/g-swarm/bot/";
	
	public static final String JAVA_COMPILER_PATH = "C:/Program Files/Java/jdk1.7.0_03/bin/javac";
	
	public static final String BATTLE_CONFIG_PATH = "battles/gswarm.battle";
	
	public static void startTest(int particlesNum, int iteration, String testPath){
		try {
			String command = "python " + particlesNum + " " + iteration + " "  + testPath;
			System.out.println(command);
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}	
	}
	
	public static void testRobot(Particle particle){
		String name = particle.getSrc();
		String resultsPath = name + ".rsl";
		
		compileRobot(name);
		startBattle(resultsPath);
		calculateFitness(particle, resultsPath);
	}

	public static void compileRobot(String src){
		try {
			String command = JAVA_COMPILER_PATH + " -d " + ROBOTS_PATH + " -cp " + ROBOCODE_PATH + "/libs/*; test/GSwarmRobot.java";
			System.out.println(command);
			
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void startBattle(String resultsPath){
		try {
			String line;
			String command = "java -Xmx512M -Dsun.io.useCanonCaches=false -cp " + "libs/robocode.jar robocode.Robocode -battle " + BATTLE_CONFIG_PATH + " -nodisplay -results " + PROJECT_PATH + resultsPath;
			System.out.println(command);
			
			Process p = Runtime.getRuntime().exec(command, new String[0], new File(ROBOCODE_PATH));
			
			//BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()) );
			BufferedReader errIn = new BufferedReader(new InputStreamReader(p.getErrorStream()) );
			
		    //while ((line = in.readLine()) != null) {
		    //	System.out.println(line);
		    //}
		    
		    while ((line = errIn.readLine()) != null) {
		    	System.out.println(line);
		    }
		    
		    //in.close();
		    errIn.close();
		    
		    p.waitFor();
		    
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void calculateFitness(Particle particle, String resultsPath){
		int result = loadResult(resultsPath);
		particle.setFitness(result);
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
