package gswarm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pso.*;
import utils.FileUtils;
import generator.BehaviorGenerator;
import generator.RobotGenerator;

public class GSwarmRobotGenerator extends RobotGenerator {
	
	private ArrayList<GSwarmBehaviorGenerator> _gSwarmPrograms;
	
	public GSwarmRobotGenerator(String filename){
		super(filename);
		_gSwarmPrograms = new ArrayList<GSwarmBehaviorGenerator>();
		
		openTemplateFile();
		findProgramSpaces();
	}
	
	protected void findProgramSpaces(){
		String strLine;
		
		Pattern pattern = Pattern.compile("#.*\\.grm");
		Matcher matcher;
		String grammarFile, key;
		GSwarmBehaviorGenerator program;

		try {
			while ((strLine = _br.readLine()) != null)   {
				matcher = pattern.matcher(strLine);
				
				while(matcher.find()){
					grammarFile = matcher.group(0).substring(1);
					key = "#" + grammarFile;
					
					program = new GSwarmBehaviorGenerator(key, grammarFile);
					_gSwarmPrograms.add(program);
					
					//System.out.println(key + " " + grammarFile);
				}
			}
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	protected void generateRobot(Particle particle, String filepath){
		int programsNum = _gSwarmPrograms.size();
		int lastIndex = 0;
		GSwarmBehaviorGenerator program;
		
		String body = FileUtils.convertFileToString(_filename);
		
		for (int i = 0; i < programsNum; i++){
			program = _gSwarmPrograms.get(i);
			program.loadGrammar();
			lastIndex = program.generateBody(particle, lastIndex);
			
			body = body.replace(program.getKey(), program.getBody());
		}
		
		FileUtils.saveStringToFile(filepath, body);
	}
	
	protected void testRobot(Particle particle){
		// TODO - test robot in robocode
	}
	
	protected void calculateFitness(Particle particle){
		// TODO - fitness calculation
	}
	

}
