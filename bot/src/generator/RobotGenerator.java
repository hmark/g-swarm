package generator;

import java.io.*;
import java.util.regex.*;
import java.util.*;
import utils.FileUtils;

public class RobotGenerator {
	
	private String _filename;
	private BufferedReader _br;
	private ArrayList<BehaviorGenerator> _programs = new ArrayList<BehaviorGenerator>();
	
	public RobotGenerator(String filename){
		_filename = filename;
		
		openTemplateFile();
		findProgramSpaces();
		generateProgram();
		outputFullRobotProgram();
	}
	
	private void openTemplateFile(){
		try{
			FileInputStream fstream = new FileInputStream(_filename);
			DataInputStream in = new DataInputStream(fstream);
			_br = new BufferedReader(new InputStreamReader(in));
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private void findProgramSpaces(){
		String strLine;
		
		Pattern pattern = Pattern.compile("#.*\\.grm");
		Matcher matcher;
		String grammarFile, key;
		BehaviorGenerator program;

		try {
			while ((strLine = _br.readLine()) != null)   {
				matcher = pattern.matcher(strLine);
				
				while(matcher.find()){
					grammarFile = matcher.group(0).substring(1);
					key = "#" + grammarFile;
					
					program = new BehaviorGenerator(key, grammarFile);
					_programs.add(program);
					
					System.out.println(key + " " + grammarFile);
				}
			}
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private void generateProgram(){
		int programsNum = _programs.size();
		BehaviorGenerator program;
		
		for (int i = 0; i < programsNum; i++){
			program = _programs.get(i);
			program.loadGrammar();
			program.generateBody();
		}
	}
	
	private void outputFullRobotProgram(){
		String body = FileUtils.convertFileToString(_filename);
		int programsNum = _programs.size();
		BehaviorGenerator program;

		for (int i = 0; i < programsNum; i++){
			program = _programs.get(i);
			//System.out.println("REPL: " + program.getKey() + " - " + program.getBody());
			body = body.replace(program.getKey(), program.getBody());
		}
		
		System.out.println(body);
	}
}
