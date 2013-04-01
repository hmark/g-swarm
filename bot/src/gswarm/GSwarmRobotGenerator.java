package gswarm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pso.*;
import utils.FileUtils;
import generator.RobotGenerator;

public class GSwarmRobotGenerator extends RobotGenerator {
	
	protected ArrayList<GSwarmBehaviorGenerator> _gSwarmPrograms;
	
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
				}
			}
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void generateRobot(Particle particle){
		int programsNum = _gSwarmPrograms.size();
		int lastIndex = 0;
		GSwarmBehaviorGenerator program;
		
		String body = FileUtils.convertFileToString(_filename);
		
		particle.setValid(true);

		for (int i = 0; i < programsNum; i++){
			program = _gSwarmPrograms.get(i);
			program.loadGrammar();
			lastIndex = program.generateBody(particle, lastIndex);
			
			body = body.replaceFirst(program.getKey(), program.getBody());
			body = body.replaceFirst("#ROBOTCLASS#", particle.getId());
			
			if (lastIndex == -1){
				particle.setValid(false);
				break;
			}
		}
		
		if (particle.isValid()){
			particle.setTreeSize(lastIndex);
			FileUtils.saveStringToFile(particle.getSrc(), body);
		}
		else	
			FileUtils.saveStringToFile("invalid_" + particle.getSrc(), body);
	}
	
	public String generateRobotToString(Particle particle){
		int programsNum = _gSwarmPrograms.size();
		int lastIndex = 0;
		GSwarmBehaviorGenerator program;
		
		String body = FileUtils.convertFileToString(_filename);
		
		particle.setValid(true);
		
		for (int i = 0; i < programsNum; i++){
			program = _gSwarmPrograms.get(i);
			program.loadGrammar();
			lastIndex = program.generateBody(particle, lastIndex);
			
			body = body.replaceFirst(program.getKey(), program.getBody());
			body = body.replaceFirst("#ROBOTCLASS#", particle.getId());
			
			if (lastIndex == -1){
				particle.setValid(false);
				break;
			}
		}
		
		if (particle.isValid()){
			particle.setTreeSize(lastIndex);
			return body;
		}
		else	
			return "0";
	}
	
	public String translateGlobalBestParticleToProgram(Particle particle){
		GSwarmBehaviorGenerator program = _gSwarmPrograms.get(0);
		program.loadGrammar();
		program.generateBodyFromBestLocation(particle, 0);
		return program.getBody();
	}

}
