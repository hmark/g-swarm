package generator;

import java.io.*;
import java.util.Random;
import java.util.regex.*;

import grammar.*;
import gswarm.Conf;

public class BehaviorGenerator {
	
	private String _key;
	private String _filename;
	
	protected Grammar _grammar = new Grammar();
	protected String _body;

	public BehaviorGenerator(String key, String filename){
		_key = key;
		_filename = filename;
	}
	
	public void loadGrammar(){
		try{
			// load grammar file
			FileInputStream fstream = new FileInputStream("conf/" + _filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			// load grammar and rules
			Rule rule;
			String strLine, nonTerminal;
			String []expressions;
			int rulesNum;
			Pattern nontermPattern = Pattern.compile("^<!...> =");
			Matcher matcher;
			
			while ((strLine = br.readLine()) != null)   {
				rule = new Rule();
				matcher = nontermPattern.matcher(strLine);
				matcher.find();
				
				nonTerminal = matcher.group(0).substring(0, 6);
				rule.setNonTerminal(nonTerminal);
				//System.out.println("NON: " + nonTerminal);
				
				expressions = strLine.substring(9).split("= | \\| ");
				rulesNum = expressions.length;
				for (int i = 0; i < rulesNum; i++){
					rule.addExpression(expressions[i]);
					//System.out.println("TERM: " + expressions[i]);
				}
				
				_grammar.addRule(rule);
			}
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void generateBody(){
		resetBody();
		
		String nonTerminal, expression;
		int randIndex, expNum, rulesNum = _grammar.getRulesNum();
		Rule rule;
		Random generator = new Random();
		
		for (int i = 0; i < rulesNum; i++){
			rule = _grammar.getRuleAt(i);
			nonTerminal = rule.getNonTerminal();
			expNum = rule.getExpressionsNumber();
			
			while ((_body.indexOf(nonTerminal)) != -1){ // TODO - optimalization issue, WHILE cycle is ineffective because we are searching and replacing in two steps
				randIndex = generator.nextInt(expNum);
				expression = rule.getExpressionAt(randIndex);
				
				//System.out.println("replace " + nonTerminal + " with " + expression);
				_body = _body.replaceFirst(nonTerminal, expression);
				//System.out.println(_body);
			}
		}
	}
	
	protected void resetBody(){
		_body = Conf.INIT_STRING;
	}
	
	public String getKey(){
		return _key;
	}
	
	public String getBody(){
		return _body;
	}
}
