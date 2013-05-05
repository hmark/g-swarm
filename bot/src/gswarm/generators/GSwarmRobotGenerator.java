package gswarm.generators;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pso.*;
import utils.FileUtils;

/**
 * Trieda zabezpecujuca generovanie viacerych spravani (modulov) v ramci jedneho robota.
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class GSwarmRobotGenerator {
	
	protected BufferedReader _br;
	protected String _filename;
	protected ArrayList<GSwarmBehaviorGenerator> _gSwarmPrograms;
	
	/**
	 * Konstruktor.
	 * @param filename 
	 */
	public GSwarmRobotGenerator(String filename){
		_filename = filename;
		_gSwarmPrograms = new ArrayList<GSwarmBehaviorGenerator>();
		
		openTemplateFile();
		findProgramSpaces();
	}
	
	/**
	 * Otvorenie suboru so sablonou robota.
	 */
	protected void openTemplateFile(){
		try{
			FileInputStream fstream = new FileInputStream(_filename);
			DataInputStream in = new DataInputStream(fstream);
			_br = new BufferedReader(new InputStreamReader(in));
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Vyhladanie miest v programe, kde sa bude generovat spravanie.
	 */
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
	
	/**
	 * Generovanie spravania robota.
	 * @param particle
	 */
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
	
	/**
	 * Generuj spravanie pomocou castice do retazca.
	 * @param particle
	 * @return
	 */
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
	
	/**
	 * Vygeneruj robota pomocou najlepsej pozicie castice.
	 * @param particle
	 * @return
	 */
	public String translateGlobalBestParticleToProgram(Particle particle){
		GSwarmBehaviorGenerator program = _gSwarmPrograms.get(0);
		program.loadGrammar();
		program.generateBodyFromBestLocation(particle, 0);
		return program.getBody();
	}

}
